package com.henrick.example.spring.rabbitmq.singlecosumerinstance;

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
@Profile("tut1")
public class SendController {

    private final Sender tut1Sender;

    @PostMapping("/send")
    public ResponseEntity<String> send(){
        log.info("O eu passando pelo send");
        tut1Sender.send();
        return new ResponseEntity<>("Enviado com sucesso!!!", HttpStatus.OK);
    }

}
