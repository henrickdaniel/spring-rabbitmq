package com.henrick.example.spring.rabbitmq.topic;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@Slf4j
@Profile("topic")
public class SendController {

    private final Sender sender;

    @PostMapping("/topic/send")
    public ResponseEntity<String> send(){
        log.info("O eu passando pelo send");
        sender.send();
        return new ResponseEntity<>("Enviado com sucesso!!!", HttpStatus.OK);
    }

}
