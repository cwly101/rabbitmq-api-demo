package com.rabbit.direct;

import com.rabbit.MyConsumer;
import com.rabbit.util.RabbitMQUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * RabbitMQ Exchange Topic ��Ϣ������
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

        System.out.println("������...");
        DefaultConsumer consumer = new MyConsumer(channel);

        channel.basicQos(0,1,false); // �������Ѷ�����ģʽ
        channel.basicConsume(queueName,false,consumer);  // ����ģʽ����ر��Զ�Ӧ������Ϊ�ֶ�Ӧ��

    }
}
