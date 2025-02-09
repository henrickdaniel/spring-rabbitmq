package com.henrick.example.spring.rabbitmq.publishsubscribe.exceptionhandler;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.handler.annotation.Header;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Profile("publish-subscribe-exception-handler")
@Slf4j
public class Receiver {


    @RabbitListener(queues = "#{autoDeleteQueue1.name}")
    public void receiveListener1(String in, Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException, InterruptedException {
        log.info("Receive message {} at {}", in, LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss)")));
        doWork(in);
        throw new RuntimeException("must reprocess");
    }

    @RabbitListener(queues = "#{autoDeleteQueue2.name}")
    public void receiveListener2(String in, Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException, InterruptedException {
        log.info("Receive message {} at {}", in, LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss)")));
        doWork(in);
        throw new RuntimeException("must reprocess");
    }

    private void doWork(String in) throws InterruptedException {
        for (char ch : in.toCharArray()) {
            if (ch == '.') {
                Thread.sleep(1000);
            }
        }
       log.info("throwing exception");
        throw new RuntimeException("must reprocess");
    }

}
