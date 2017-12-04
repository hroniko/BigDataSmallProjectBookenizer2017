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

    @ResponseBody
    @RequestMapping(value = "/sendBook", method = RequestMethod.POST)
    public void sendBook(HttpSession session,
                         @RequestParam(value = "id") Integer id,
                         @RequestParam(value = "name") String name,
                         @RequestParam(value = "author") String author)
    {
        System.out.println("Пользователь " + id + " добавил книгу: " + name + " : " + author);

        // Надо попробовать положить ее в базу, затем кинуть в кафку айди этого юзера:
        producer.send("spring", "id", id.toString());
        producer.send("spring", "id", "1"); // Вот тут попытка, просто левые данные для проверки
        //producer.send("spring", "id", "2"); // Просто так, а то данных нет в бд
        //producer.send("spring", "id", "5");

    }
}
