package com.allwayup.mq.impl;

import cn.hutool.core.lang.TypeReference;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.allwayup.mq.MqService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MqServiceImpl implements MqService {

    private final ConcurrentHashMap<String, byte[]> queueMessage = new ConcurrentHashMap<>(100);

    @Resource
    private AmqpTemplate amqpTemplate;

    @RabbitListener(queues = "test.out.queue")
    public void out(Message message) {
        String msg = new String(message.getBody(), StandardCharsets.UTF_8);
        System.out.println("服务端收到了:" + msg);
        amqpTemplate.convertAndSend("test.in.queue", msg);
    }

    @RabbitListener(queues = "test.in.queue")
    public void in(Message message) {
        String msg = new String(message.getBody(), StandardCharsets.UTF_8);
        JSONObject parse = JSONUtil.parseObj(msg);
        queueMessage.put(parse.getStr("requestId"), message.getBody());
    }

    @Override
    public Map<String, String> get(Map<String, String> map) {
        String requestId = UUID.randomUUID().toString();
        map.put("requestId", requestId);
        amqpTemplate.convertSendAndReceive("test.out.queue", JSONUtil.toJsonStr(map));
        byte[] b = null;
        while (Objects.isNull(b)) {
            b = queueMessage.get(requestId);
        }
        String msg = new String(b, StandardCharsets.UTF_8);
        queueMessage.remove(requestId);
        TypeReference<Map<String, String>> typeReference = new TypeReference<>() {
        };
        return JSONUtil.toBean(msg, typeReference, true);
    }

}
