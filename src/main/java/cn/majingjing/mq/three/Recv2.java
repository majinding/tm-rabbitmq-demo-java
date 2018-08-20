package cn.majingjing.mq.three;

import cn.majingjing.mq.util.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

public class Recv2 {
    private static final String QUEUE_NAME = "queue_two";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [2-x] Received '" + message + "'");

                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };

        boolean autoAck=true;
        channel.basicConsume(QUEUE_NAME,autoAck , consumer);
    }


}
