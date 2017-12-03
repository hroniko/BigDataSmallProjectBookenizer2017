import kafka.Kafka;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;


import java.util.Arrays;
import java.util.Properties;

public class Consumer{
    public  static  void  main(String[] args)
    {
        //получаем сообщения
        String topic = "spark"; // String topic = "recomendation";
        Properties prop = new Properties();
        prop.put("bootstrap.servers","localhost:9092");
        prop.put("session.timeout",10000);
        prop.put("group.id","test");
        prop.put("key.deserializer","org.apache.kafka.common.serialization.StringDeserializer");
        prop.put("value.deserializer","org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String ,String> consumer = new KafkaConsumer<>(prop);
        //подписываемся на топик
        consumer.subscribe(Arrays.asList(topic));
        //постоянно читаем что приходит за 1 секунду
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(1000);
            records.forEach( e -> System.out.println( e.value().toString()));
        }

    }
}
