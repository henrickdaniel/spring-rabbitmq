package com.henrick.example.spring.rabbitmq.publishsubscribe.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

@Profile("publish-subscribe-exception-handler")
@Service
@Slf4j
public class Sender {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private FanoutExchange fanout;

    AtomicInteger dots = new AtomicInteger(0);

    AtomicInteger count = new AtomicInteger(0);

    public void send() {
        StringBuilder builder = new StringBuilder("Hello");
        if (dots.getAndIncrement() == 3) {
            dots.set(1);
        }
        for (int i = 0; i < dots.get(); i++) {
            builder.append('.');
        }
        builder.append(count.incrementAndGet());
        String message = builder.toString();
        template.convertAndSend(fanout.getName(), "", getMessage(message));
        System.out.println(" [x] Sent '" + message + "'");
    }

    public void sendSingle() {
        StringBuilder builder = new StringBuilder("Hello");
        builder.append('.');
        builder.append(count.incrementAndGet());
        String message = builder.toString();
        template.convertAndSend(fanout.getName(), "", getMessage(message));
    }

    private static Message getMessage(String message) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setTimestamp(new Date()); // Set the current timestamp
        Message amqpMessage = new Message(message.getBytes(), messageProperties);
        log.info("Generating message {}", amqpMessage);
        return amqpMessage;
    }

}