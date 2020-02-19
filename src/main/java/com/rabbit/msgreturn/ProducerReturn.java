package com.rabbit.msgreturn;

import com.rabbit.util.RabbitMQUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Return ��Ϣ���� (ǰ�᣺������������ڡ�ֻ��·�ɲ����ڻ����return���Ʋ���Ч��
 * @author caowei
 * @create 2020/2/15
 */
public class ProducerReturn {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = RabbitMQUtil.connectionFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        String msg ="����һ������Return���Ƶ���Ϣ��û��·�ɽ��ո���Ϣ";
        channel.basicPublish("cw_direct_exchange","no.save",true,null,msg.getBytes());
        System.out.println("��Ϣ�ѷ���");

        channel.addReturnListener(new ReturnListener() {

            @Override
            public void handleReturn(int replyCode, String replyText, String exchange, String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("========== Return Message =========");
                System.out.println(replyCode);  // ��Ӧ��
                System.out.println(replyText);  // ��Ӧ�ı�
                System.out.println(exchange);
                System.out.println(routingKey);
                System.out.println(properties);
                System.out.println(new String(body));
            }
        });


    }
}
