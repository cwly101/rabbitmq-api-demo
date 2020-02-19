package com.rabbit.direct;

import com.rabbit.config.RabbitMQConfig;
import com.rabbit.util.RabbitMQUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * RabbitMQ Exchange Topic 消息生产者
 * @author caowei
 * @create 2020/2/11
 */
public class DirectProducer {

    public static void main(String[] args) throws IOException, TimeoutException {

        Channel channel = null;
        Connection connection = null;
        try {
            ConnectionFactory factory = RabbitMQUtil.connectionFactory();
            connection = factory.newConnection();
            channel = connection.createChannel();

            String exchangeName = "cw_direct_exchange";
            String routingKey = "cw.direct";

            String msg = "Hello RabbitMQ !!";
            for (int i = 0; i < 6; i++) {
                channel.basicPublish(exchangeName,routingKey,null,msg.getBytes());
            }
            System.out.println("消息发送完成");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } finally {
            channel.close();
            connection.close();
            System.out.println("通道、连接 已关闭");
        }

    }
}
