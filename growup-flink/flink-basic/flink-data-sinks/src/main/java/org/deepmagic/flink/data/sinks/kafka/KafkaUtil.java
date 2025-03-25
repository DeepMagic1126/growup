package org.deepmagic.flink.data.sinks.kafka;

import com.google.gson.Gson;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.deepmagic.flink.data.entity.Student;

import java.util.Properties;

/**
 * KafkaUtil
 *
 * @author chenbin
 * @apiNote kafka
 * @since 2025/3/13 22:05
 */
public class KafkaUtil {

    public static final String broker_list = "localhost:9092";
    public static final String topic = "student";  //kafka topic 需要和 flink 程序用同一个 topic

    public static Properties kafkaProperties() {
        Properties props = new Properties();
        props.put("bootstrap.servers", broker_list);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//        props.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        return props;
    }

    public static void writeToKafka() throws InterruptedException {
        Gson gson = new Gson();

        KafkaProducer<String, String> producer = new KafkaProducer<>(kafkaProperties());
        for (int i = 1; i <= 1000; i++) {
            Student student = new Student(i, "Meek" + i, "HEllo" + i, 18 + i);
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, null, null, gson.toJson(student));
            producer.send(record);
            System.out.println("发送数据: " + gson.toJson(student));
        }
        producer.flush();
        producer.close();
    }

    public static void main(String[] args) throws InterruptedException {
        writeToKafka();
    }
}
