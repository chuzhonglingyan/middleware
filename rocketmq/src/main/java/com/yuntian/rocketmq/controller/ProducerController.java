package com.yuntian.rocketmq.controller;

import com.yuntian.rocketmq.base.Result;
import com.yuntian.rocketmq.constant.Topic;
import com.yuntian.rocketmq.service.ProducerService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author guangleilei.
 * @date Created in 10:53 2019/11/19
 * @description
 */
@RestController
@RequestMapping(value = "/producer")
public class ProducerController {


    @Resource
    private ProducerService producerService;

    @RequestMapping(value = {"/sendTestTopic1"}, method = RequestMethod.GET)
    public Result send(String msg) {
        Result result=new Result();
        result.setCode(99);
        result.setMsg("成功");
        producerService.send(Topic.TEST_TOPIC_1,msg);
        return  result;
    }

    @RequestMapping(value = {"/syncSendTestTopic1"}, method = RequestMethod.GET)
    public Result syncSend(String msg) {
        Result result=new Result();
        result.setCode(99);
        result.setMsg("成功");
        producerService.syncSend(Topic.TEST_TOPIC_1,msg);
        return  result;
    }


}
