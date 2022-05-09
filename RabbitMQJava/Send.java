import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Send {

  private final static String QUEUE_1_NAME = "hello1";
  private final static String QUEUE_2_NAME = "hello2";

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    channel.queueDeclare(QUEUE_1_NAME, false, false, false, null);
    String message1 = "Hello World!";
    channel.basicPublish("", QUEUE_1_NAME, null, message1.getBytes("UTF-8"));
    System.out.println(" [Queue 1] Sent '" + message1 + "'");

    channel.queueDeclare(QUEUE_2_NAME, false, false, false, null);
    String message2 = "Hello World!";
    channel.basicPublish("", QUEUE_2_NAME, null, message2.getBytes("UTF-8"));
    System.out.println(" [Queue 2] Sent '" + message2 + "'");

    channel.close();
    connection.close();
  }
}
