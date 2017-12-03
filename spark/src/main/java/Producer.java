import kafka.Kafka;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class Producer {
    public  static  void  main(String[] args)
    {
        //здесь отправлем одно сообщение
        String topic = "spring"; // String topic = "recomendation";
        Properties prop = new Properties();
        prop.put("bootstrap.servers","localhost:9092");
        prop.put("batch.size",65536);
        prop.put("buffer.memory",1000000);
        prop.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        prop.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
        KafkaProducer<String ,String> producer = new KafkaProducer<>(prop);
        producer.send(new ProducerRecord<>(topic, "id", "1")); // producer.send(new ProducerRecord<String, String>(topic, "id", "name"));
        producer.send(new ProducerRecord<>(topic, "id", "2")); // producer.send(new ProducerRecord<String, String>(topic, "id", "name"));
        producer.send(new ProducerRecord<>(topic, "id", "5")); // producer.send(new ProducerRecord<String, String>(topic, "id", "name"));
        producer.send(new ProducerRecord<>(topic, "id", "1")); // producer.send(new ProducerRecord<String, String>(topic, "id", "name"));
        producer.send(new ProducerRecord<>(topic, "id", "1")); // producer.send(new ProducerRecord<String, String>(topic, "id", "name"));
        producer.close();

    }
}
