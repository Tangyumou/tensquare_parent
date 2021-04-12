package com.tensquare.rabbit.Consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "abc")
public class ConsumerB {
    @RabbitHandler
    public void showMessage(String message){
        System.out.println("abc接收到消息："+message);
    }
}