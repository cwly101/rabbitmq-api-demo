package com.rabbit.topic;

import com.rabbit.util.RabbitMQUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Topic Exchange 消息生产者
 * @author caowei
 * @create 2020/2/12
 */
public class TopicProducer {

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = null;
        Channel channel = null;
        try {
            ConnectionFactory factory = RabbitMQUtil.connectionFactory();
            connection = factory.newConnection();
            channel = connection.createChannel();

            String exchangeName = "cw_topic_exchange";
            String routingKey1 = "user.save";
            String routingKey2 = "user.update";
            String routingKey3 = "user.delete.abc";

            channel.basicPublish(exchangeName,routingKey1,null,("message from:"+routingKey1).getBytes());
            channel.basicPublish(exchangeName,routingKey2,null,("message from:"+routingKey2).getBytes());
            channel.basicPublish(exchangeName,routingKey3,null,("message from:"+routingKey3).getBytes());
            System.out.println("cw_topic_exchange 发送消息完成。");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } finally {
            channel.close();
            connection.close();
            System.out.println("通道、连接关闭");
        }

    }
}
