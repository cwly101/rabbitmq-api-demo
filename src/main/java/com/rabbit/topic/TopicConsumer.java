package com.rabbit.topic;

import com.rabbit.MyConsumer;
import com.rabbit.util.RabbitMQUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Topic Exchange ��Ϣ������
 *
 * @author caowei
 * @create 2020/2/12
 */
public class TopicConsumer {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = RabbitMQUtil.connectionFactory();
        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();

        String exchangeName = "cw_topic_exchange";
        String exchangeType = "topic";
        String queueName = "cw_topic_queue";
        String routingKey = "user.*";
        channel.exchangeDeclare(exchangeName, exchangeType, false, false, false, null);
        channel.queueDeclare(queueName, false, false, true, null);
        channel.queueBind(queueName, exchangeName, routingKey);

        // �����߼�����Ϣ ��ʽ1
//        DefaultConsumer consumer = new MyConsumer(channel);  // ����������
//        System.out.println("cw_topic_queue ���м�����...");
//        channel.basicConsume(queueName, true, consumer);  // ����channel

        // �����߼�����Ϣ ��ʽ2
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String body = new String(delivery.getBody());
            // System.out.println(consumerTag);
            System.out.println(body);
        };
        CancelCallback cancelCallback = consumerTag ->{
            System.out.println("cancelCallback ..."+consumerTag);
        };
        System.out.println("cw_topic_queue ���м�����...");
        channel.basicConsume(queueName,true,deliverCallback,cancelCallback);
    }
}
