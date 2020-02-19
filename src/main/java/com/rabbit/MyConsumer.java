package com.rabbit;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author caowei
 * @create 2020/2/11
 */
public class MyConsumer extends DefaultConsumer {

    private Channel channel;

    public MyConsumer(Channel channel) {
        super(channel);
        this.channel = channel;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
            throws IOException {
        super.handleDelivery(consumerTag, envelope, properties, body);
//        System.out.println("-----------consume message----------");
//        System.out.println("consumerTag: " + consumerTag);
//        System.out.println("envelope: " + envelope);
//        System.out.println("properties: " + properties);
        System.out.println("body: " + new String(body));
        // 休眠一秒，为了测试手动应答Ack是否生效
        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
        channel.basicAck(envelope.getDeliveryTag(),false);
    }

    // 参见：https://www.cnblogs.com/nfsnyy/p/12264590.html
    // https://www.cnblogs.com/lflying/p/11107299.html
}
