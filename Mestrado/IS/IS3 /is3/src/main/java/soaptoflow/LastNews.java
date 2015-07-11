package soaptoflow;

import java.util.ArrayList;

import org.mule.api.MuleMessage;
import org.mule.api.annotations.param.Payload;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageTransformer;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.text.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.lang.Object;


import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.Hours;
import transformed.NewNews;

public class LastNews  extends AbstractMessageTransformer{

	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding)
			throws TransformerException {
			ArrayList<NewNews> old= (ArrayList<NewNews>) message.getPayload();
			ArrayList<NewNews> novas =new ArrayList<NewNews>();
			System.out.println("o antigo tinha:"+old.size());
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			Date dataatual = new Date();
			for(int i=0;i<old.size();i++){
				int hours = 0;
				try {
					hours = Hours.hoursBetween(new DateTime(dateFormat.parse(old.get(i).getDate())), new DateTime(dataatual)).getHours();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(hours < 12342342)
				{
					
					if(old.get(i).getPhotos() != null){

					novas.add(old.get(i));
					}
				}
			}
			System.out.println("o novo tem :"+novas.size());

		
		
		
		return novas;
	}
	
}
