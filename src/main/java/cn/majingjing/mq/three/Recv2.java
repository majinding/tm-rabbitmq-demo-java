package cn.majingjing.mq.three;

import cn.majingjing.mq.util.ConnectionUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

public class Recv2 {
    private static final String QUEUE_NAME = "queue_three_r2";
    private static final String EXCHANGE_NAME="test_exchange_fanout";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtils.getConnection();
        final Channel channel = connection.createChannel();

        //可以知道队列的名称也可以使用匿名队列名
//        String queueName = QUEUE_NAME;
        String queueName = channel.queueDeclare().getQueue();
        //channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        //将队列绑定到交换机上
        channel.queueBind(queueName,EXCHANGE_NAME,"");

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
        channel.basicConsume(queueName, autoAck, consumer);
    }

}
