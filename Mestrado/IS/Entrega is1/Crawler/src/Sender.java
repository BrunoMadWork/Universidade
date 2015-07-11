import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class Sender {
 protected ConnectionFactory cf;
 protected Connection c;
 protected Session s;
 protected Destination d;
 protected MessageProducer mp;

 public Sender() throws NamingException, JMSException {
  InitialContext init = new InitialContext();
  this.cf = (ConnectionFactory) init.lookup("jms/RemoteConnectionFactory");
  this.d = (Destination) init.lookup("jms/topic/test");
  
  this.c = (Connection) this.cf.createConnection("jboss", "is2014..");
  this.c.start();
  this.s = this.c.createSession(false, Session.AUTO_ACKNOWLEDGE);
  this.mp = this.s.createProducer(this.d);
 }

  void send(String string) throws JMSException {
  TextMessage tm = this.s.createTextMessage(string);
  this.mp.send(tm);
 }

  void close() throws JMSException {
  this.c.close();
 }

 /**
  * @param args
  * @throws JMSException 
  * @throws NamingException 
  */


}