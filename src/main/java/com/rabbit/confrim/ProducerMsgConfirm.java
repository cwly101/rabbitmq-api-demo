package com.rabbit.confrim;

import com.rabbit.util.RabbitMQUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消息生产者 发送消息确认机制
 * @author caowei
 * @create 2020/2/15
 */
public class ProducerMsgConfirm {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = RabbitMQUtil.connectionFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.confirmSelect();
        channel.addConfirmListener(new ConfirmListener() {
            /**
             *
             * @param deliveryTag 唯一的消息标签
             * @param multiple 是否批量的
             * @throws IOException
             */
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("ack");
                // 注：为了监听到Broker的消息确认，没有关闭通道断开连接。
            }

            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("no ack");
                /**
                 * 什么情况会出现 no ack?
                 * 1 磁盘写满了
                 * 2 RabbitMQ异常
                 * 3 Queue容量达到上限
                 */
            }
        });

        String exchangeName = "test_fanout_exchange";
        channel.basicPublish(exchangeName,"",null,"hi,msg send...".getBytes());
    }
}
