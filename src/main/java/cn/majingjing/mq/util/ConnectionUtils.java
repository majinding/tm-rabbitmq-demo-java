package cn.majingjing.mq.util;


import com.rabbitmq.client.*;

public class ConnectionUtils {

    /**
     * 获取MQ的连接
     *
     * @return
     * @throws Exception
     */
    public static Connection getConnection() throws Exception {
        //定义一个连接工厂
        ConnectionFactory factory = new ConnectionFactory();

        //设置服务地址
        factory.setHost("www.majingjing.cn");

        //AMQP 5672
        factory.setPort(5672);

        //vhost
        factory.setVirtualHost("/vhost_tm_1");

        //用户名
        factory.setUsername("tm_user1");

        //密码
        factory.setPassword("123");
        return factory.newConnection();
    }

}
