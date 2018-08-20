package cn.majingjing.mq.four;

import cn.majingjing.mq.util.ConnectionUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

public class Recv1 {
    private static final String EXCHANGE_NAME="test_exchange_direct";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtils.getConnection();
        final Channel channel = connection.createChannel();

        //使用匿名队列,因为消息是发送到交换机上,由交换机进行选择发送到绑定的队列,所以此时队列的名字其实没有什么意义,并且匿名队列在断开连接后会自动删除
        String queueName = channel.queueDeclare().getQueue();

        //将队列绑定到交换机上
        channel.queueBind(queueName,EXCHANGE_NAME,"orange");

        channel.basicQos(1);
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
                } finally {
                    System.out.println(" [1-x] Done");
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }

            }
        };

        boolean autoAck = false;
        channel.basicConsume(queueName, autoAck, consumer);
    }

}
