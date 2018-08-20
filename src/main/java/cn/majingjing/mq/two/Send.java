package cn.majingjing.mq.two;


import cn.majingjing.mq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Send {

    private static final String QUEUE_NAME = "queue_two";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        for (int i = 0; i < 20; i++) {
            String message = "Hello World!--" + i;
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + message + "'");

            Thread.sleep(10 * i);
        }

        channel.close();
        connection.close();
    }

}
