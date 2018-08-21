package cn.majingjing.mq.extensions.tx;

import cn.majingjing.mq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class TxSend {

    private static final String QUEUE_NAME = "queue_extensions_tx";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String message = "Hello World!";

        try {
            channel.txSelect();
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
            int a = 1 / 0;
            System.out.println(" [x] Sent '" + message + "'");
            channel.txCommit();
        } catch (Exception e) {
            channel.txRollback();
            System.out.println(" send message txRollback");
        }

        channel.close();
        connection.close();

    }

}
