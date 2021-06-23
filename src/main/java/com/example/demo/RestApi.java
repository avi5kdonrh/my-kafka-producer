package com.example.demo;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;

@Configuration
public class RestApi {

    static {
        try {
            InputStream fis = RestApi.class.getClassLoader().getResourceAsStream("truststore.jks");
            byte[] bytes = new byte[fis.available()];
            fis.read(bytes);
            FileOutputStream fos = new FileOutputStream("/home/jboss/truststore.jks");
            fos.write(bytes);
            fos.flush();
            fos.close();
            fis.close();


        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
