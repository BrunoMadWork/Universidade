import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.text.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.lang.Object;

import com.ocpsoft.pretty.time.*;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.Hours;

import java.io.StringReader;

import javax.xml.transform.stream.StreamSource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import generated.Regions;
import generated.News;


public class StatsProducer extends Receiver{
	private static String dirFile = "news.xml";
	private static String outFile = "menos12h.xml";
	public StatsProducer() throws NamingException, JMSException {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public static void calculatempo(Regions reg) throws ParseException
	{
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Date dataatual = new Date();
		PrettyTime p = new PrettyTime();
		ArrayList<News> noticias = new ArrayList<News>();
		News nn = new News();	
		int j = 0;
		
		System.out.println("Not�cias com menos de 12H - " + dataatual +"\n");
	
		for(int i = 0; i < reg.getRegion().size();i++)
		{
			//System.out.println(reg.getRegion().get(i).getNews().get(i).getTitle());
			List<News> neww = reg.getRegion().get(i).getNews();
			noticias.clear();
			for(News n : neww)
			{	
				if(n.getDate() != null && n.getDate().getTime() != null)
				{
					String data = arranjadata(n.getDate().getDay(),n.getDate().getMonth(),n.getDate().getYear(), n.getDate().getTime());
					Date dataconvertida = dateFormat.parse(data);
						
					//org.joda.time.format.DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm");
					//DateTime dataconvertida = formatter.parseDateTime(data);
					//int horanew = n.getDate().getTime().intValue();

					int hours = Hours.hoursBetween(new DateTime(dataconvertida), new DateTime(dataatual)).getHours();
					if(hours < 12)
					{
						j++;
						System.out.println(n.getTitle() + " --- " + dataconvertida + " --- " + p.format(dataconvertida));
						noticias.add(n);
					}
				}			
			}
			reg.getRegion().get(i).getNews().clear();
			for(int z = 0;z<noticias.size();z++)
			{
				reg.getRegion().get(i).getNews().add(noticias.get(z));
			}		
		}
		System.out.println("-------------------------");
		System.out.println("Total de not�cias: " + j);
		Marshelling.Marshalls(outFile, reg);
	}
	

	
	public static String arranjadata(int dia, String mes, int ano, int hora)
	{
		int num = 0;
		String horanova = "";
		String modificada = "";
		switch(mes)
		{
		case "January": num = 1;
						break;
		case "February": num = 2;
						break;
		case "March": num = 3;
						break;
		case "April": num = 4;
						break;
		case "May": num = 5;
						break;
		case "June": num = 6;
						break;
		case "July": num = 7;
						break;
		case "August": num = 8;
						break;
		case "September": num = 9;
						break;
		case "October": num = 10;
						break;
		case "November": num = 11;
						break;
		case "December": num = 12;
						break;
		}
		
		int length = String.valueOf(hora).length();
		if (length == 1)
		{
			int primeiro = Integer.parseInt(Integer.toString(hora).substring(0, 1));
			horanova = "" + "00:0" + primeiro;
		}
		else if (length == 2)
		{
			int primeiro = Integer.parseInt(Integer.toString(hora).substring(0, 1));
			int segundo = Integer.parseInt(Integer.toString(hora).substring(1, 2));
			horanova = "" + "00:" + primeiro + segundo ;
		}
		else if(length == 3)
		{
			int primeiro = Integer.parseInt(Integer.toString(hora).substring(0, 1));
			int segundo = Integer.parseInt(Integer.toString(hora).substring(1, 2));
			int terceiro = Integer.parseInt(Integer.toString(hora).substring(2, 3));
			
			horanova = "" + primeiro + ":" + segundo + terceiro;

		}
		else if (length == 4)
		{
			int primeiro = Integer.parseInt(Integer.toString(hora).substring(0, 1));
			int segundo = Integer.parseInt(Integer.toString(hora).substring(1, 2));
			int terceiro = Integer.parseInt(Integer.toString(hora).substring(2, 3));
			int quarto = Integer.parseInt(Integer.toString(hora).substring(3, 4));
			
			horanova = "" + primeiro + segundo + ":" + terceiro + quarto;

		}

		modificada = dia + "/" + num + "/" + ano + " " + horanova;

		return modificada;
	}
	
	public static void main(String [] args) throws NamingException, JMSException
	{
		Receiver r = new Receiver();
		String msg = r.receive();
		
		//System.out.println("Mensagem: " + msg);
		StreamSource streamSource = new StreamSource(new StringReader(msg));
		Regions todas = Marshelling.Unmarshalls(streamSource);
		System.out.println("-------------------------------------------------------");
		try {
			calculatempo(todas);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
