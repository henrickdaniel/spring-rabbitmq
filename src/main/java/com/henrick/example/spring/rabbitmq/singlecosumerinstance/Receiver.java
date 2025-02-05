package com.henrick.example.spring.rabbitmq.singlecosumerinstance;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Profile;

@RabbitListener(queues = "hello")
@Profile("tut1")
public class Receiver {

    @RabbitHandler
    public void receive(String in) {
        System.out.println(" [x] Received '" + in + "'");
    }
}