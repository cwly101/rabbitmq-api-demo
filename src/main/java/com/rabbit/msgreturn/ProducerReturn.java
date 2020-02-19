package com.rabbit.msgreturn;

import com.rabbit.util.RabbitMQUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Return 消息机制 (前提：交换机必须存在。只有路由不存在或错误，return机制才生效）
 * @author caowei
 * @create 2020/2/15
 */
public class ProducerReturn {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = RabbitMQUtil.connectionFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        String msg ="这是一条测试Return机制的消息，没有路由接收该消息";
        channel.basicPublish("cw_direct_exchange","no.save",true,null,msg.getBytes());
        System.out.println("消息已发送");

        channel.addReturnListener(new ReturnListener() {

            @Override
            public void handleReturn(int replyCode, String replyText, String exchange, String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("========== Return Message =========");
                System.out.println(replyCode);  // 响应码
                System.out.println(replyText);  // 响应文本
                System.out.println(exchange);
                System.out.println(routingKey);
                System.out.println(properties);
                System.out.println(new String(body));
            }
        });


    }
}
