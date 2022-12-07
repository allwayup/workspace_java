package com.allwayup.mq;

import com.allwayup.annotation.LinkMqQueue;

import java.util.Map;

@LinkMqQueue(out = "test.out.queue", in = "test.in.queue")
public interface MqService {

    Map<String, String> get(Map<String, String> map);

}
