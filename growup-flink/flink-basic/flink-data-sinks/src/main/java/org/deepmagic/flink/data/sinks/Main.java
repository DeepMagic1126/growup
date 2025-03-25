package org.deepmagic.flink.data.sinks;

import com.google.gson.Gson;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.RestOptions;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.KafkaSourceBuilder;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.connector.kafka.source.reader.deserializer.KafkaRecordDeserializationSchema;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.deepmagic.flink.data.entity.Student;
import org.deepmagic.flink.data.sinks.kafka.KafkaUtil;
import org.deepmagic.flink.data.sinks.sinks.SinkToMySQL;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Main
 *
 * @author chenbin
 * @apiNote TODO
 * @since 2025/3/13 15:53
 */
public class Main {


    public static class StudentDeserializer implements Deserializer<Student> {

        private final Gson gson = new Gson();
        private final String encoding = StandardCharsets.UTF_8.name();

        @Override
        public Student deserialize(String topic, byte[] data) {
            try {
                String result = data == null ? null : new String(data, this.encoding);
                return result == null ? null : gson.fromJson(result, Student.class);
            } catch (UnsupportedEncodingException var4) {
                throw new SerializationException("Error when deserializing byte[] to string due to unsupported encoding " + this.encoding);
            }
        }
    }

    public static void main(String[] args) throws Exception {

        Configuration jobConfiguration = new Configuration();
        //设置WebUI绑定的本地端口
        //jobConfiguration.setInteger(RestOptions.PORT, 18081);//"rest.port" / RestOptions.PORT 均可 | 注: 8081 是默认端口
        jobConfiguration.setString(RestOptions.BIND_PORT, "8081");

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment(jobConfiguration);

//        WatermarkStrategy<Student> watermarkStrategy = WatermarkStrategy
//                .forBoundedOutOfOrderness(Duration.ofSeconds(20));
        WatermarkStrategy<Student> watermarkStrategy = WatermarkStrategy
                .noWatermarks();

        Properties props = new Properties();

        props.put("enable.auto.commit", "true");

        KafkaSource<Student> source = KafkaSource.<Student>builder()
                .setBootstrapServers(KafkaUtil.broker_list)
                .setTopics(List.of(KafkaUtil.topic))
                .setGroupId("test-group1")
                .setStartingOffsets(OffsetsInitializer.earliest())
                .setProperties(props)
                .setDeserializer(KafkaRecordDeserializationSchema.valueOnly(StudentDeserializer.class))
                .build();


        DataStreamSource<Student> student = env.fromSource(source,
                watermarkStrategy,
                "kafka source");

        student.sinkTo(SinkToMySQL.getSink())
                .setParallelism(1)
        ; //数据 sink 到 mysql
        env.execute("Job 测试flink1.20版本JdbcSink from flink");


    }


}
