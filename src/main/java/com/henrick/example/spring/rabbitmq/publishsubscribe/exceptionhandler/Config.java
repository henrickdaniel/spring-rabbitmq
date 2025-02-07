package com.henrick.example.spring.rabbitmq.publishsubscribe.exceptionhandler;

import org.springframework.amqp.core.*;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("publish-subscribe-exception-handler")
@Configuration
public class Config {

    @Bean
    public FanoutExchange fanout() {
        return new FanoutExchange("tut.fanout");
    }

    private static class ReceiverConfig {

        @Bean
        public Queue autoDeleteQueue1() {
            return new Queue("retry-test");
        }

        @Bean
        public Queue autoDeleteQueue2() {
            return new Queue("retry-test");
        }

        @Bean
        public Binding binding1(FanoutExchange fanout, Queue autoDeleteQueue1) {
            return BindingBuilder.bind(autoDeleteQueue1).to(fanout);
        }

        @Bean
        public Binding binding2(FanoutExchange fanout, Queue autoDeleteQueue2) {
            return BindingBuilder.bind(autoDeleteQueue2).to(fanout);
        }

        @Bean
        public Receiver receiver() {
            return new Receiver();
        }
    }

    @Bean
    public Sender sender() {
        return new Sender();
    }
}