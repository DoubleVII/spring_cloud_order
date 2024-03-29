package com.example.order.message;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class MqReceiverTest {

    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * 发送MQ消息测试
     */
    @Test
    public void send(){
        amqpTemplate.convertAndSend("myQueue", "now: " + new Date());
    }

}