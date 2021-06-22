package com.example.demo;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.ClassLoaderUtil;

import java.util.Properties;

@RestController
public class RestApi {
    private static byte[] bytes;
private static KafkaProducer<String, String> producer;
static {
    Properties configProps = new Properties();
    System.out.println(ClassLoader.getSystemResource("truststore.jks").getPath());
    configProps.put("security.protocol","ssl");
    configProps.put("ssl.truststore.location", ClassLoader.getSystemResource("truststore.jks").getPath());
    configProps.put("ssl.truststore.password","password");
    configProps.put("ssl.truststore.type","JKS");
    configProps.put(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
            "my-cluster-kafka-bootstrap.default:9093");
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
