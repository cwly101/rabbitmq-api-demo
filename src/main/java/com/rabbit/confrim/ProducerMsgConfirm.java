package com.rabbit.confrim;

import com.rabbit.util.RabbitMQUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * ��Ϣ������ ������Ϣȷ�ϻ���
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
             * @param deliveryTag Ψһ����Ϣ��ǩ
             * @param multiple �Ƿ�������
             * @throws IOException
             */
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("ack");
                // ע��Ϊ�˼�����Broker����Ϣȷ�ϣ�û�йر�ͨ���Ͽ����ӡ�
            }

            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("no ack");
                /**
                 * ʲô�������� no ack?
                 * 1 ����д����
                 * 2 RabbitMQ�쳣
                 * 3 Queue�����ﵽ����
                 */
            }
        });

        String exchangeName = "test_fanout_exchange";
        channel.basicPublish(exchangeName,"",null,"hi,msg send...".getBytes());
    }
}
