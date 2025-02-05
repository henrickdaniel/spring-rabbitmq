package com.henrick.example.spring.rabbitmq.workqueues;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile("workqueues")
@RestController
@AllArgsConstructor
public class SendController {

    private final Sender sender;

    @PostMapping("/send")
    public ResponseEntity<String> send(){
        for (int i = 0; i < 20; i++)
            sender.send();
        return new ResponseEntity<>("Enviado com sucesso", HttpStatus.OK);
    }

}
