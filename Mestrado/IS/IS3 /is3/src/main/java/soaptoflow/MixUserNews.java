package soaptoflow;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractMessageTransformer;

public class MixUserNews extends AbstractMessageTransformer {
	
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
			HashMap<String,Object> payload=(HashMap<String,Object>)message.getPayload();
			String stm = "SELECT eMail from users where digest='true'";
			PreparedStatement pst;
			List<String> mailsList=new ArrayList<String>();

			try {
				
				pst = con.prepareStatement(stm);          
				ResultSet rs=pst.executeQuery();
		
		        while(rs.next()){
		        	mailsList.add(rs.getString(1));
		        }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

		
			payload.put("mails", mailsList);

		return payload;
	}
}
