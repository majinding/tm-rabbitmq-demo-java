package cn.majingjing.mq.one;

import cn.majingjing.mq.util.ConnectionUtils;
import com.rabbitmq.client.*;

public class Send {

    private final static String QUEUE_NAME = "quene_one";

    public static void main(String[] argv) throws Exception {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String message = "Hello World!";
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
        System.out.println(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();
    }
}
