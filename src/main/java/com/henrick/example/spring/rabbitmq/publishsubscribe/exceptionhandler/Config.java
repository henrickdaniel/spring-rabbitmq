package com.henrick.example.spring.rabbitmq.publishsubscribe.exceptionhandler;

import org.springframework.amqp.core.*;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.HashMap;
import java.util.Map;

@Profile("publish-subscribe-exception-handler")
@Configuration
public class Config {

    @Bean
    public FanoutExchange fanout() {
        return new FanoutExchange("tut.fanout");
    }

    @Bean
    public FanoutExchange fanoutDlx() {
        return new FanoutExchange("tut.fanout.dlx");
    }

    private static class ReceiverConfig {

        @Bean
        public Queue autoDeleteQueue1() {
            Map<String, Object> args = new HashMap<>();
            args.put("x-dead-letter-exchange", "tut.fanout.dlx");
//            args.put("x-dead-letter-routing-key", "retry-test-dlq)");
            return new Queue("retry-test", true, false, false, args);
        }

        @Bean
        public Queue autoDeleteQueue2() {
            Map<String, Object> args = new HashMap<>();
            args.put("x-dead-letter-exchange", "tut.fanout.dlx");
//            args.put("x-dead-letter-routing-key", "retry-test-dlq)");
            return new Queue("retry-test", true, false, false, args);
        }

        @Bean
        public Queue autoDeleteQueueDlq() {
            return new Queue("retry-test-dlq");
        }

        @Bean
        public Queue parkingLot() {
            return new Queue("parking-lot");
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
        public Binding bindingDlq(FanoutExchange fanout, Queue autoDeleteQueueDlq) {
            return BindingBuilder.bind(autoDeleteQueueDlq).to(fanout);
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