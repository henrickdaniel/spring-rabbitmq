package com.henrick.example.spring.rabbitmq.publishsubscribe.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class DeadLetterQueueListener {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final String DLQ = "retry-test-dlq";

    private static final String X_RETRY_HEADER = "x-dlq-retry";

    private static final String PARKING_LOT = "parking-lot";

    @RabbitListener(queues = DLQ)
    public void process(Message message, @Headers Map<String, Object> headers) throws InterruptedException {

        Integer retryHeader = (Integer) headers.getOrDefault(X_RETRY_HEADER, 0);

        if (retryHeader == null) {
            retryHeader = 0;
        }

        if (retryHeader < 3) {
            Map<String, Object> updateHeaders = new HashMap<>(headers);
            int tryCount = retryHeader + 1;
            log.info("Reprocessing event {} tryCount {}", new String(message.getBody()), tryCount);
            updateHeaders.put(X_RETRY_HEADER, tryCount);

            try{
                doWork(new String(message.getBody()));
            }catch (Exception e){
                final MessagePostProcessor messagePostProcessor = msg -> {
                    MessageProperties messageProperties = msg.getMessageProperties();
                    updateHeaders.forEach(messageProperties::setHeader);
                    return msg;
                };

                this.rabbitTemplate.convertAndSend(DLQ, message, messagePostProcessor);
            }
        }else{
            log.info("Send to parking-lot");
            this.rabbitTemplate.convertAndSend(PARKING_LOT, new String(message.getBody()));
        }
    }

    private void doWork(String in) throws InterruptedException {
        for (char ch : in.toCharArray()) {
            if (ch == '.') {
                Thread.sleep(1000);
            }
        }
        throw new RuntimeException("must reprocess");
    }

}
