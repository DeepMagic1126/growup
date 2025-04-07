package org.deepmagic.flink.data.sinks.sinks;

import org.apache.flink.connector.base.DeliveryGuarantee;
import org.apache.flink.connector.jdbc.JdbcConnectionOptions;
import org.apache.flink.connector.jdbc.JdbcExactlyOnceOptions;
import org.apache.flink.connector.jdbc.JdbcExecutionOptions;
import org.apache.flink.connector.jdbc.datasource.connections.SimpleJdbcConnectionProvider;
import org.apache.flink.connector.jdbc.datasource.statements.JdbcQueryStatement;
import org.apache.flink.connector.jdbc.sink.JdbcSink;
import org.deepmagic.flink.data.entity.Student;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * CustomJdbcSink
 *
 * @author chenbin
 * @apiNote
 * @since 2025/3/13 22:23
 */
public class SinkToMySQL {

    public SinkToMySQL() {
    }

    public static JdbcSink<Student> getSink() {
        JdbcConnectionOptions jdbcConnectionOptions = new JdbcConnectionOptions.JdbcConnectionOptionsBuilder()
                .withUrl("jdbc:mysql://localhost:3306/project")
                .withDriverName("com.mysql.cj.jdbc.Driver")
                .withUsername("root")
                .withPassword("123456")
                .build();

        JdbcExecutionOptions options = JdbcExecutionOptions.builder().withBatchSize(100).build();

        return new JdbcSink<>(DeliveryGuarantee.NONE
                , new SimpleJdbcConnectionProvider(jdbcConnectionOptions)
                , options
                , JdbcExactlyOnceOptions.defaults()
                , new JdbcQueryStatement<>() {
            @Override
            public String query() {
                return "INSERT INTO users (name,age) VALUES (?,?)";
            }

            @Override
            public void statement(PreparedStatement ps, Student student) throws SQLException {
                ps.setString(1, student.getName());
                ps.setInt(2, student.getAge());
            }
        });
    }

}
