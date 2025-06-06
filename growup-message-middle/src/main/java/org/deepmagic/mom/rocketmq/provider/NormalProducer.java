package org.deepmagic.mom.rocketmq.provider;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;


/**
 * SyncProducer
 *
 * @author chenbin
 * @apiNote 同步消息Producer
 * @since 2025/3/6 22:42
 */
public class NormalProducer {
    public static void main(String[] args) throws Exception {
        // 初始化一个producer并设置Producer group name

        DefaultMQProducer producer = new DefaultMQProducer("test_group_name"); //（1）
        // 设置NameServer地址
        producer.setNamesrvAddr("127.0.0.1:9876");  //（2）
        producer.setSendMsgTimeout(10000);  //（2）
        // 启动producer
        producer.start();

        for (int i = 100; i < 10000; i++) {
            // 创建一条消息，并指定topic、tag、body等信息，tag可以理解成标签，对消息进行再归类，RocketMQ可以在消费端对tag进行过滤
            Message msg = new Message(
                    "topic_test" /* Topic */,
                    "tag_test" /* Tag */,
                    ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );   //（3）
            // 利用producer进行发送，并同步等待发送结果
            SendResult sendResult = producer.send(msg);   //（4）
            System.out.printf("%s%n", sendResult);
        }
        // 一旦producer不再使用，关闭producer
        producer.shutdown();
    }
}
