package cn.majingjing.mq.extensions.confirm;

import cn.majingjing.mq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * Created by JingjingMa on 2018/8/22 10:54
 */
public class Send2 {
    private static final String QUEUE_NAME = "queue_extensions_confirm2";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        channel.confirmSelect();

        for (int i = 0; i < 5; i++) {
            String message = "Hello World! ---"+i;
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
            System.out.println(" [2-x] Sent '" + message + "'");
        }

        if (channel.waitForConfirms()) {
            System.out.println(" [2-x] Sent ok");
        } else {
            System.out.println(" [2-x] Sent fail");
        }

        channel.close();
        connection.close();

    }
}
