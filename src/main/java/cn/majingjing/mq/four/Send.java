package cn.majingjing.mq.four;


import cn.majingjing.mq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Send {

    private static final String EXCHANGE_NAME = "test_exchange_direct";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();

        //There are a few exchange types available: direct, topic, headers and fanout
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        String routingKey = "orange";//orange,black,green
        String message = "Hello World!--four"+"-->"+routingKey;
        channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes("UTF-8"));
        System.out.println(" [x] Sent '" + message + "' --routingKey:"+routingKey);

        channel.close();
        connection.close();
    }

}
