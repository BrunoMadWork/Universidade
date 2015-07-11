import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.InitialContext;
import javax.naming.NamingException;


public class ToHtml {
	static TopicSubscriber topicSubscriber;

	public static void main(String[] args) throws NamingException, JMSException, FileNotFoundException, UnsupportedEncodingException {
		Receiver receiver=new Receiver();
		
		String received= receiver.receive();
		String toFinal = received.substring(0, 56) + "<?xml-stylesheet type=\"text/xsl\" href=\"fileXSL.xsl\"?>\n" + received.substring(56, received.length());

		PrintStream out = null;
		out = new PrintStream(new FileOutputStream("fileHTML.xml"),true, "UTF-8");
		out.print(toFinal);
		out.close();
	}
}
