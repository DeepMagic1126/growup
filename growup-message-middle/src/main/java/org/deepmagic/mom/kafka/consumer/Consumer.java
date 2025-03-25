package org.deepmagic.mom.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

/**
 * Consumer
 *
 * @author chenbin
 * @apiNote TODO
 * @since 2025/3/9 21:24
 */
public class Consumer {

    public static void main(String[] args) {
        // Kafka消费者配置
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092"); // Kafka服务器地址
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group"); // 消费者组ID

        // earliest：最早 latest：最新
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // 从最早的偏移量开始消
//     fetch.min.bytes
//含义：消费者从broker获取消息的最小字节数。只有大于此值时，消费者才会拉取消息。
//默认值：通常为1字节。
//调优建议：设置为一个较大的值（如1MB），以减少消费者拉取消息的频率，提高性能。但在低延迟场景中，可能需要设置为较小的值以确保实时性。
//fetch.max.wait.ms
//含义：当fetch.min.bytes不满足时，消费者从broker获取消息的最大等待时间。
//默认值：通常为500ms。
//调优建议：根据业务需求调整，较大的值可以减少消费者拉取消息的频率，但可能会增加延迟。
//max.poll.records 默认为500
//含义：消费者每次拉取消息的最大数量。
//默认值：根据Kafka版本和配置可能有所不同。
//调优建议：根据消费者的处理能力和业务需求调整此值，以避免积压过多消息或处理不及时。
//enable.auto.commit
//含义：是否开启自动提交消费者偏移量。
//默认值：通常为true。
//调优建议：在数据可靠性优先的场景下，建议设置为false，并在事务提交后再手动提交偏移量。这可以避免因自动提交而导致的重复消费或消息丢失问题。

        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");



        // 创建Kafka消费者
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList("test-topic")); // 订阅主题

        // 拉取消息
        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100)); // 拉取消息
                for (ConsumerRecord<String, String> record : records) {
                    System.out.printf("Consumed record with key %s and value %s from partition %d with offset %d%n",
                            record.key(), record.value(), record.partition(), record.offset());
                }
            }
        } finally {
            consumer.close(); // 关闭消费者
        }
    }
}
