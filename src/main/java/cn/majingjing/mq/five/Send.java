package cn.majingjing.mq.five;


import cn.majingjing.mq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Send {

    private static final String EXCHANGE_NAME = "test_exchange_topic";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();

        //There are a few exchange types available: direct, topic, headers and fanout
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");

        String[] routingKeys = {"quick.orange.rabbit", "lazy.orange.elephant", "quick.orange.fox", "lazy.brown.fox", "lazy.pink.rabbit", "quick.brown.fox", "quick.orange.male.rabbit", "lazy.orange.male.rabbit"};
        for(String routingKey:routingKeys){
            String message = "Hello World!--five" + "-->" + routingKey;
            channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + message + "' --routingKey:" + routingKey);
        }

        channel.close();
        connection.close();
    }

}
