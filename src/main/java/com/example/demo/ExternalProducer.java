package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableKafka
public class ExternalProducer {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @GetMapping("test")
    public String send(){
        kafkaTemplate.send("my-topic","some-message "+System.currentTimeMillis());
        return "success";
    }
}
