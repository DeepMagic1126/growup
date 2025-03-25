package org.deepmagic.mom.kafka.provider;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.concurrent.Future;

/**
 * Producer
 *
 * @author chenbin
 * @apiNote TODO
 * @since 2025/3/9 21:21
 */
public class Producer {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092"); // Kafka服务器地址
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        // 批量发送的数据大小 default 16384 16kb
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384 * 64); // 1MB batch size
        // 发送间隔时间，如果超过了这个时间也会发送一次 default 10ms
        props.put(ProducerConfig.LINGER_MS_CONFIG, 100); // 100ms linger time
        // 压缩算法，提高吞吐量，但是会加大producer压力
        props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "lz4"); // lz4 compression
        // 0 不会等broker确认，默认为成功 1 leader成功就行，不用等followers同步 all leader 和 followers 全部同步才行
        props.put(ProducerConfig.ACKS_CONFIG, "all"); // Wait for all in-sync replicas to ack
        // 生产者用于缓存消息的内存大小。
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432 * 2); // 64MB buffer memory
        // 通常为32MB。 根据业务场景估算一个合理的值，建议设置为64MB以上，以避免因内存不足而阻塞生产者线程。
        props.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, 10485760); // 10MB max request size
        // 生产者发送的最大请求大小。 如果发送的消息较大，需要适当调大此值。
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);
        // 发送消息
        String topic = "test-topic";
        String key = "key1";
        String value = "Hello, Kafka!";
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, value);

        try {
            Future<RecordMetadata> future = producer.send(record);
            RecordMetadata metadata = future.get(); // 等待消息发送完成并获取元数据
            System.out.printf("Sent record with key %s and value %s to partition %d with offset %d%n",
                    key, value, metadata.partition(), metadata.offset());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.close(); // 关闭生产者
        }
    }
}
