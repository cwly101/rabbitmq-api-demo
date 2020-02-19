package com.rabbit.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author caowei
 * @create 2020/2/11
 */
public class RabbitMQConfig {

    private String host;
    private Integer port;
    private String virtualHost;

    public RabbitMQConfig() {

        // ÿ�ζ���ȡ����֤�����ļ�֧�����޸ġ�
        // ������õ���ģʽ�������ļ��Ͳ�֧���ȸ����ˡ���Ϊֻ�ڳ�ʼ��ʱ��ȡһ�顣���Ӳ���������ļ�û����
        File file = new File("config.properties");
        if(!file.exists())
            return;
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream(file));
            this.host = properties.getProperty("host");
            this.port = Integer.valueOf(properties.getProperty("port"));
            this.virtualHost = properties.getProperty("virtualHost");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public String getHost() {
        return host;
    }

    public Integer getPort() {
        return port;
    }

    public String getVirtualHost() {
        return virtualHost;
    }

    @Override
    public String toString() {
        return "RabbitMQConfig{" +
                "host='" + host + '\'' +
                ", port='" + port + '\'' +
                ", virtualHost='" + virtualHost + '\'' +
                '}';
    }
}
