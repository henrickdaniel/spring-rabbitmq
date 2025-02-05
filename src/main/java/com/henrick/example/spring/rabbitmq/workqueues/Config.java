package com.henrick.example.spring.rabbitmq.workqueues;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("workqueues")
@Configuration
public class Config {

    @Bean
    public Queue hello() {
        return new Queue("hello");
    }

    @Profile("workqueues")
    private static class ReceiverConfig {

        @Bean
        public Receiver receiver1() {
            return new Receiver(1);
        }

        @Bean
        public Receiver receiver2() {
            return new Receiver(2);
        }
    }

    @Profile("workqueues")
    @Bean
    public Sender sender() {
        return new Sender();
    }
}
