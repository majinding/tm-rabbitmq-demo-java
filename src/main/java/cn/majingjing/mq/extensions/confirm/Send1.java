package cn.majingjing.mq.extensions.confirm;

import cn.majingjing.mq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * Created by JingjingMa on 2018/8/22 10:54
 */
public class Send1 {
    private static final String QUEUE_NAME = "queue_extensions_confirm1";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String message = "Hello World!";

        channel.confirmSelect();
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
        System.out.println(" [x] Sent '" + message + "'");

        if (channel.waitForConfirms()) {
            System.out.println(" [x] Sent ok");
        } else {
            System.out.println(" [x] Sent fail");
        }

        channel.close();
        connection.close();

    }
}
