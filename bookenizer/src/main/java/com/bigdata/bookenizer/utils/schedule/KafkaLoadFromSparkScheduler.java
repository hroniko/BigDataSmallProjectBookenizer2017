package com.bigdata.bookenizer.utils.schedule;

import com.bigdata.bookenizer.utils.kafka.Consumer;
import com.bigdata.bookenizer.utils.kafka.MessageStorage;
import org.junit.ClassRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


// Планировщик для забора данных из Кафки с периодичностью
@Component
public class KafkaLoadFromSparkScheduler {

    @Autowired
    private Consumer consumer;

    @ClassRule
    public static KafkaEmbedded embeddedKafka = new KafkaEmbedded(1, true,"spring" );

    @Autowired
    private MessageStorage storage;

    @Scheduled(fixedRate = 10000) // интервал между запусками 10 секунд
    public void scheduleTaskWithFixedRate() {
        System.out.println("Хоп! Сработал планировщик!");
        String messages = storage.toString();
        storage.clear(); // и надо очистить хранилище

        System.out.println(messages);


        // И еще логику написать для переноса рекомендаций в бд
    }


}
