package com.rabbit.direct;

import com.rabbit.MyConsumer;
import com.rabbit.util.RabbitMQUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * RabbitMQ Exchange Topic 消息消费者
 * @author caowei
 * @create 2020/2/11
 */
public class DirectConsumer {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = RabbitMQUtil.connectionFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        String exchangeName = "cw_direct_exchange";
        String exchangeType = "direct";
        String queueName = "cw_direct_queue";
        String routingKey = "cw.direct";

        channel.exchangeDeclare(exchangeName,exchangeType,false,false,false,null);
        channel.queueDeclare(queueName,false,false,false,null);
        channel.queueBind(queueName,exchangeName,routingKey);

        System.out.println("监听中...");
        DefaultConsumer consumer = new MyConsumer(channel);

        channel.basicQos(0,1,false); // 开启消费端限流模式
        channel.basicConsume(queueName,false,consumer);  // 限流模式必须关闭自动应答，设置为手动应答。

    }
}
