package com.bigdata.bookenizer.utils.schedule;

import com.bigdata.bookenizer.utils.kafka.Consumer;
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
    public static KafkaEmbedded embeddedKafka = new KafkaEmbedded(1, true,"testtopic" );

    @Scheduled(fixedRate = 10000) // интервал между запусками 10 секунд
    public void scheduleTaskWithFixedRate() {
         // System.out.println("Хоп! Сработал планировщик!");
         // String msg = consumer.receive();
         // System.out.println(msg);

        // и надо очистить топик:
        // consumer.clear();

        // И еще логику написать для переноса рекомендаций в бд
    }


}
