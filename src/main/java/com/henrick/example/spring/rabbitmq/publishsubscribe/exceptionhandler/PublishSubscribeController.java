package com.henrick.example.spring.rabbitmq.publishsubscribe.exceptionhandler;

import com.henrick.example.spring.rabbitmq.model.SendDto;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Profile("publish-subscribe-exception-handler")
public class PublishSubscribeController {

    private final Sender sender;

    @PostMapping("/publishSubscribe/send")
    public ResponseEntity<String> send(@RequestBody SendDto sendDto){
        for(int i = 0; i < sendDto.getMessagesToBeSent(); i++)
            sender.send();
        return new ResponseEntity<>("Enviado com sucesso!!!", HttpStatus.OK);
    }


    @PostMapping("/publishSubscribe/sendSingle")
    public ResponseEntity<String> sendSingle(){
        sender.sendSingle();
        return new ResponseEntity<>("Enviado com sucesso!!!", HttpStatus.OK);
    }



}
