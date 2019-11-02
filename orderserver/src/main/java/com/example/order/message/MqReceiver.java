package com.example.order.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MqReceiver {

    //1. @RabbitListener(queues = "myQueue")
    //2. 自动创建队列 @RabbitListener(queuesToDeclare = @Queue("myQueue"))
    //3.
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("myQueue"),
            exchange = @Exchange("myExchange")
    ))
    public void process(String message){
        log.info("MqReceiver: {}", message);
    }

    //消息分组
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("computerQueue"),
            exchange = @Exchange("myOrder"),
            key = "computer"
    ))
    public void computerProcess(String message){
        log.info("computer MqReceiver: {}", message);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("fruitOrder"),
            exchange = @Exchange("myOrder"),
            key = "fruit"
    ))
    public void fruitProcess(String message){
        log.info("fruit MqReceiver: {}", message);
    }
}
