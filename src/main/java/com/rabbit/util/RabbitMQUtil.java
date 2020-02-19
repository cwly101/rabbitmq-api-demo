package com.rabbit.util;

import com.rabbit.config.RabbitMQConfig;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author caowei
 * @create 2020/2/11
 */
public class RabbitMQUtil {

    public static ConnectionFactory connectionFactory(){
        RabbitMQConfig config = new RabbitMQConfig();
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(config.getHost());
        factory.setPort(config.getPort());
        factory.setVirtualHost(config.getVirtualHost());

        return  factory;
    }
}
