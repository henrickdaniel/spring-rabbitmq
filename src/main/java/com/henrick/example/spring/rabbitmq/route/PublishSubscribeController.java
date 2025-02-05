package com.henrick.example.spring.rabbitmq.route;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Profile("route")
public class PublishSubscribeController {

    private final Sender sender;

    @PostMapping("/publishSubscribe/send")
    public ResponseEntity<String> send(){
        for(int i = 0; i < 30; i++)
            sender.send();
        return new ResponseEntity<>("Enviado com sucesso!!!", HttpStatus.OK);
    }


}
