package com.rabbit.confrim;

import com.rabbit.MyConsumer;
import com.rabbit.util.RabbitMQUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author caowei
 * @create 2020/2/15
 */
public class ConsumerMsgConfirm {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = RabbitMQUtil.connectionFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        String exchangeName = "test_fanout_exchange";
        String exchangeType = "fanout";
        String queueName = "test_fanout_queue";
        String routingKey = ""; // 不设置路由键

        channel.exchangeDeclare(exchangeName,exchangeType,false,true,false,null);
        channel.queueDeclare(queueName,false,false,true,null);
        channel.queueBind(queueName,exchangeName,routingKey);

        System.out.println("fanout 监听中...");
        DefaultConsumer consumer = new MyConsumer(channel);
        channel.basicConsume(queueName,true,consumer);
    }
}
