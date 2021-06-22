package com.example.demo;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.ClassLoaderUtil;

import java.io.*;
import java.util.Properties;

@RestController
public class RestApi {


    private static byte[] bytes;
private static KafkaProducer<String, String> producer;
static {
    try {
        InputStream fis = RestApi.class.getClassLoader().getResourceAsStream("truststore.jks");
        byte[] bytes = new byte[fis.available()];
        fis.read(bytes);
        FileOutputStream fos = new FileOutputStream("/home/passwd/truststore.jks");
        fos.write(bytes);
        fos.flush();
        fos.close();
        fis.close();


    }catch (Exception ex){
        ex.printStackTrace();
    }
    Properties configProps = new Properties();
    configProps.put("security.protocol","ssl");
    configProps.put("ssl.truststore.location", "/home/passwd/truststore.jks");
    configProps.put("ssl.truststore.password","password");
    configProps.put("ssl.truststore.type","JKS");
    configProps.put(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
            "my-cluster-kafka-ext-bootstrap-default.dte-ocp46-j2zwxa-915b3b336cabec458a7c7ec2aa7c625f-0000.us-east.containers.appdomain.cloud:443");
    configProps.put(
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
            StringSerializer.class);
    configProps.put(
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
            StringSerializer.class);

    producer = new KafkaProducer<String, String>(configProps);

}
    @GetMapping("/test")
    public String test(@RequestParam String param){


         ProducerRecord<String, String > producerRecord = new ProducerRecord<>("my-topic",param);
         producer.send(producerRecord);
         System.out.println(">> Sending something >>");

        return "Success";
    }
}
