package com.bigdata.bookenizer.utils.schedule;

import com.bigdata.bookenizer.model.entity.RecomendationsEntity;
import com.bigdata.bookenizer.model.entity.UsertagsEntity;
import com.bigdata.bookenizer.services.RecomendationsEntityDao;
import com.bigdata.bookenizer.utils.kafka.Consumer;
import com.bigdata.bookenizer.utils.kafka.MessageStorage;
import org.junit.ClassRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


// Планировщик для забора данных из Кафки с периодичностью
@Component
public class KafkaLoadFromSparkScheduler {


    @Autowired
    private RecomendationsEntityDao recomendationsEntityDao;

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
        //

        System.out.println(messages);

        // Формируем ссылки на новые книги!


        // Получаем всю мапу полученных чрез Кафку из Спарка айдишников юзеров и книг
        HashMap<Integer, ArrayList<Integer>> allBookId = storage.getAllId();

        storage.clear(); // и надо очистить хранилище

        // Обходим мапу:


        Iterator<Map.Entry<Integer, ArrayList<Integer>>> it = allBookId.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, ArrayList<Integer>> pair = it.next();

            Integer userId = pair.getKey(); // Получаем айди пользователя, для которого формируем рекомендации

            ArrayList<Integer> bookIdList = pair.getValue(); // Получаем список айди всех книг, которые надо добавить в пекомендаци

            // Обходим список всех айди книг
            for (Integer bookId : bookIdList) {

                // Книга точно уже есть в базе! Нужно просто создать на нее ссылку в рекомендациях!

                // и надо создать recomendation на книгу

                // создаем ссылку
                RecomendationsEntity linkToBook = new RecomendationsEntity(0L+userId, 0L+bookId);

                // И не нужно проверять, есть ли у юзера такая ссылка в рекомендациях, так как Спарк точно вернул то, чего у него нет

                // Сохраняем в базу
                recomendationsEntityDao.save(linkToBook);

            }






        }



        // И еще логику написать для переноса рекомендаций в бд  - Написал
    }


}
