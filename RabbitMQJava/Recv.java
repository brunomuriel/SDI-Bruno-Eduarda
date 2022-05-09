import com.rabbitmq.client.*;
import java.util.HashMap;
import java.io.IOException;

public class Recv {

  private final static String QUEUE_1_NAME = "hello1";
  private final static String QUEUE_2_NAME = "hello2";

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    Map<String, Object> args = new HashMap<String, Object>();
    args.put("x-max-priority", 10);

    channel.queueDeclare(QUEUE_1_NAME, false, false, false, args);
    channel.queueDeclare(QUEUE_2_NAME, false, false, false, args);

    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

    Consumer consumer1 = new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
          throws IOException {
        String message = new String(body, "UTF-8");
        System.out.println(" [Queue 1] Received '" + message + "'");
      }
    };

     Consumer consumer2 = new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
          throws IOException {
        String message = new String(body, "UTF-8");
        System.out.println(" [Queue 2] Received '" + message + "'");
      }
    };

    channel.basicConsume(QUEUE_1_NAME, true, consumer1);
    channel.basicConsume(QUEUE_2_NAME, true, consumer2);
  }
}
