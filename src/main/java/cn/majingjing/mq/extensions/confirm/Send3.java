package cn.majingjing.mq.extensions.confirm;

import cn.majingjing.mq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * Created by JingjingMa on 2018/8/22 10:54
 */
public class Send3 {
    private static final String QUEUE_NAME = "queue_extensions_confirm3";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        channel.confirmSelect();

        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("---handleAck---deliveryTag:"+deliveryTag+",multiple:"+multiple);
            }

            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("---handleAck---deliveryTag:"+deliveryTag+",multiple:"+multiple);
            }
        });


        for (int i = 0; i < 5; i++) {
            String message = "Hello World! ---" + i;
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
            System.out.println(" [3-x] Sent '" + message + "'");
        }

        System.in.read();
        channel.close();
        connection.close();

    }
}
