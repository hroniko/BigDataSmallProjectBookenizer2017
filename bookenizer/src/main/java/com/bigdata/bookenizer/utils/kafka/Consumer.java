package com.bigdata.bookenizer.utils.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class Consumer {

    @Autowired
    MessageStorage storage;

    @KafkaListener(topics = "${kafka.topic.input}")
    public void processMessage(String content) {
        storage.put(content);
    }


}