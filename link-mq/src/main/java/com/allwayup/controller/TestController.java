package com.allwayup.controller;

import com.allwayup.mq.MqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class TestController {

    @Autowired
    private MqService mqService;

    @RequestMapping(path = "/get", method = RequestMethod.GET)
    public Object get() {
        HashMap<String, String> map = new HashMap<>();
        map.put("name", "zs");
        return mqService.get(map);
    }
}
