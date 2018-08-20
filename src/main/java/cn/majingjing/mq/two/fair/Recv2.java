package cn.majingjing.mq.two.fair;

import cn.majingjing.mq.util.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

public class Recv2 {
    private static final String QUEUE_NAME = "queue_two_fail";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtils.getConnection();
        final Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        channel.basicQos(1);
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
                } finally {
                    System.out.println(" [2-x] Done");
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }

            }
        };

        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME, autoAck, consumer);
    }


}
