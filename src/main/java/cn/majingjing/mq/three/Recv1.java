package cn.majingjing.mq.three;

import cn.majingjing.mq.util.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

public class Recv1 {
    private static final String QUEUE_NAME = "queue_three";
    private static final String EXCHANGE_NAME="test_exchange_direct";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();

        //There are a few exchange types available: direct, topic, headers and fanout
        channel.exchangeDeclare("","");

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [1-x] Received '" + message + "'");

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };

        boolean autoAck = true;
        channel.basicConsume(QUEUE_NAME, autoAck, consumer);
    }

}
