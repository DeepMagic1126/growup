package org.deepmagic.flink.data.sinks;


import org.apache.flink.connector.base.DeliveryGuarantee;
import org.apache.flink.connector.jdbc.JdbcConnectionOptions;
import org.apache.flink.connector.jdbc.JdbcExactlyOnceOptions;
import org.apache.flink.connector.jdbc.JdbcExecutionOptions;
import org.apache.flink.connector.jdbc.datasource.connections.SimpleJdbcConnectionProvider;
import org.apache.flink.connector.jdbc.datasource.statements.JdbcQueryStatement;
import org.apache.flink.connector.jdbc.sink.JdbcSink;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.deepmagic.flink.data.entity.Student;
import org.deepmagic.flink.data.sinks.sinks.SinkToMySQL;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Main2
 *
 * @author chenbin
 * @apiNote
 * @since 2025/3/13 15:54
 */
public class Main2 {
    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

//        DataStreamSource<String> source = env.fromData("vincent","meek","carl","HelloWorld");
//        JdbcConnectionOptions jdbcConnectionOptions = new JdbcConnectionOptions.JdbcConnectionOptionsBuilder()
//                .withUrl("jdbc:mysql://localhost:3306/project")
//                .withDriverName("com.mysql.cj.jdbc.Driver")
//                .withUsername("root")
//                .withPassword("123456")
//                .build();
//        JdbcExecutionOptions options = JdbcExecutionOptions.builder().withBatchSize(1000).build();
//        JdbcSink<String> objectJdbcSink = new JdbcSink<>(DeliveryGuarantee.AT_LEAST_ONCE
//                , new SimpleJdbcConnectionProvider(jdbcConnectionOptions)
//                , options
//                , JdbcExactlyOnceOptions.defaults()
//                , new JdbcQueryStatement<>() {
//            @Override
//            public String query() {
//                return  "INSERT INTO users (name) VALUES (?)";
//            }
//
//            @Override
//            public void statement(PreparedStatement ps, String record) throws SQLException {
//                ps.setString(1, record);
//            }
//        });

        List<Student> studentList = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            Student student = new Student(i, "Vincent" + i, "password" + i, 18 + i);
            studentList.add(student);
        }

        DataStreamSource<Student> source = env.fromData(studentList);
        source.sinkTo(SinkToMySQL.getSink()).setParallelism(1);
        env.execute("测试flink1.20版本JdbcSink");
    }
}
