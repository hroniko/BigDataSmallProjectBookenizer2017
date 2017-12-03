package com.bigdata.bookenizer.utils.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class Producer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    public void send( String topic, String key, String value) {
        kafkaTemplate.send(topic,key,value);
    }
}