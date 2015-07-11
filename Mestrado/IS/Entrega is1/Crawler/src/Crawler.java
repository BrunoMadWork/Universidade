import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.UnknownHostException;
import java.nio.charset.Charset;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xml.sax.SAXException;

import generated.Regions;

public class Crawler 
{ 
	public static void main(String [] args) throws JMSException, InterruptedException, IOException 
	{
		Parser parser;
		Regions regions;
		String toSend = null;
		
		File f= new File("news.xml");
		if(!f.exists()){
			 parser=new Parser("http://cnn.com");
			 regions =parser.parse_other_languages();
			Marshelling.Marshalls("news.xml",regions); //TESTING
			 toSend=Marshelling.Marshalls(regions);
			
		}else{
				InputStream in = new FileInputStream(new File("news.xml"));
		        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		        StringBuilder out = new StringBuilder();
		        String line="";
		        while ((line = reader.readLine()) != null) {
		            out.append(line);
		        }
		        if(!line.isEmpty()){
		        	System.out.println(out.toString());   //Prints the string content read from input stream
		        	toSend=out.toString();
		        	}
		        reader.close();

			
		}
		
		Sender sd = null;
		Boolean bool = null;
		do{
		try {
			bool=false;
			System.out.println("A tentar enviar");
			sd = new Sender();
		} catch (NamingException e) {
			//
			bool=true;
			System.out.println("CATCHING!!!!!");
			Thread.sleep(5000);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}while(bool==true);
		sd.send(toSend);
		File fa= new File("news.xml");
		fa.delete();
		
		
		
	}

}