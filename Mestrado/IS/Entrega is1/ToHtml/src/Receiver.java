
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class Receiver {
 private ConnectionFactory cf;
 private Connection c;
 private Session s;
 private Destination d;
 private MessageConsumer mc;

 public Receiver() throws NamingException, JMSException {
  InitialContext init = new InitialContext();
  this.cf = (ConnectionFactory) init.lookup("jms/RemoteConnectionFactory");
  this.d = (Destination) init.lookup("jms/topic/test");
  this.c = (Connection) this.cf.createConnection("jboss", "is2014..");
  c.setClientID("jboss1");
  this.c.start();
  this.s = this.c.createSession(false, Session.AUTO_ACKNOWLEDGE);
   mc = s.createDurableSubscriber((Topic) d, "jboss1");
  mc = s.createConsumer(d);
 }

 protected String receive() throws JMSException {
  TextMessage msg = (TextMessage) mc.receive();
  return msg.getText();
 }

 private void close() throws JMSException {
  this.c.close();
 }

 /**
  * @param args
  * @throws JMSException 
  * @throws NamingException 
  */
 public static void main(String[] args) throws NamingException, JMSException {
  Receiver r = new Receiver();

  String msg = r.receive();
  System.out.println("Mensagem: " + msg);
  r.close();
 }

}