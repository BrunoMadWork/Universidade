package soaptoflow;

import java.util.ArrayList;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageTransformer;

public class TransformerStatistics extends AbstractMessageTransformer  {

	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding)
			throws TransformerException {
			ArrayList<String> strings =new ArrayList <String>();
			String treated="";
			for(int i=0;i<strings.size();i++){
				System.out.println(strings.get(i));
				treated=treated+strings.get(i)+"\n";
			}
		return treated;
	}

}
