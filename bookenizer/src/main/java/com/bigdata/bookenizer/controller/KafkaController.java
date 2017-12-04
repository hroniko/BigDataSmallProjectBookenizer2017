package com.bigdata.bookenizer.controller;

import com.bigdata.bookenizer.utils.kafka.Consumer;
import com.bigdata.bookenizer.utils.kafka.MessageStorage;
import com.bigdata.bookenizer.utils.kafka.Producer;
import org.junit.ClassRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


@RestController
public class KafkaController {
    @Autowired
    private Producer producer;

    @Autowired
    private MessageStorage storage;

    @ClassRule
    public static KafkaEmbedded embeddedKafka = new KafkaEmbedded(1, true,"spring" );

    @RequestMapping("/send_msg")
    public void send(){
        producer.send("spring", "id", "1");
        producer.send("spring", "id", "2");
        producer.send("spring", "id", "5");

    }

    /*
    @RequestMapping("/get_msg")
    public void getMsg()
    {
        consumer.receive();
        System.out.println(msg);
    }
    */

    @GetMapping(value="/get_msg")
    public String getAllRecievedMessage(){
        String messages = storage.toString();
        storage.clear();

        System.out.println(messages);
        return messages;
    }


}
