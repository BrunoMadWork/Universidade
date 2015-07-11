package file;
import generated.News;
import generated.Regions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageTransformer;

import soaptoflow.Marshelling;
import transformed.NewNews;
import transformed.NewRegion;

public class XmlToDatabase extends AbstractMessageTransformer{
Connection con;
	
	void connect(){
		String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "postgres";
        String password = "bjbm8701";
        
		try{
		
	        con = DriverManager.getConnection(url, user, password);
     
	    }catch(SQLException e){
            System.out.println(e.getMessage());
        }
	}
	@Override
	public Object transformMessage(MuleMessage message, String outputEncoding)
			throws TransformerException {
			
		connect();
		
		String xmlString=null;
		try {
			xmlString = message.getPayloadAsString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StreamSource streamSource = new StreamSource(new StringReader(xmlString));
		Regions regions = Marshelling.Unmarshalls(streamSource);
		
		ArrayList<NewNews> arrayNewNews= new ArrayList<NewNews>();
		//System.out.println(xmlString);
		//System.out.println("NUMERO DE REGIOES : " +regions.getRegion().size()+ "\n\n\n");
		for(int i=0;i<regions.getRegion().size();i++){
			for(int j=0;j<regions.getRegion().get(i).getNews().size();j++){
				News temp=regions.getRegion().get(i).getNews().get(j);
				String date;
				if(temp.getDate()!=null){
				 date= arranjadata(temp.getDate().getDay(),temp.getDate().getMonth(),temp.getDate().getYear(),temp.getDate().getTime());
						 
				}
				else{
					date="null";
				}
					if(temp.getUrl()!=null){
						arrayNewNews.add(new NewNews( temp.getTitle(),temp.getUrl(),temp.getHighlits(),date ,temp.getAuthor(),temp.getText(), temp.getPhotos(),temp.getVideo(),regions.getRegion().get(i).getArea()));
					}
				}
			

				
			
		}
		ArrayList<NewNews> finalNewNews= new ArrayList<NewNews>();
		for(int i=0;i<arrayNewNews.size();i++){
			if(!(arrayNewNews.get(i).getTitle()==null)&&!(arrayNewNews.get(i).getDate().equals("null"))){
				finalNewNews.add(arrayNewNews.get(i));
			}
		}
		//ver se ja tao na bd
		/*  	connect();
		String stm;
		PreparedStatement pst;
		ResultSet rs;
		System.out.println("Numero de news no array antes :"+arrayNewNews.size());
		ArrayList<NewNews> FinalNews= new ArrayList<NewNews>();

		for(int i=0;i<arrayNewNews.size();i++){
			if(arrayNewNews.get(i).getUrl()!=null){
			stm = "SELECT url from news where url='"+arrayNewNews.get(i).getUrl()+"';";
			
			try {
				pst = con.prepareStatement(stm);          
				rs=pst.executeQuery();
		
		        if(rs.next()){
		        	//arrayNewNews.remove(i);
		        	continue;
		        }
		        FinalNews.add(arrayNewNews.get(i));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		}
		System.out.println("Numero de news no array depois :"+FinalNews.size());
*/

		
		System.out.println("Noticias finais : " +finalNewNews.size()+ "\n\n\n");
		
		for(int i=0;i<finalNewNews.size();i++){
			System.out.println(finalNewNews.get(i).getTitle());
		}
		//return arrayNewNews;
		return finalNewNews;
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
}
