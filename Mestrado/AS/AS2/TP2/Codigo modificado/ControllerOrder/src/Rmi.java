/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author brunomadureira
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.*;
import java.rmi.server.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Rmi extends UnicastRemoteObject
           implements Rmiinterface {
	private static final long serialVersionUID = 1L;

           private String message;
        Boolean connectError = false;   // Error flag
        Connection DBConn = null;       // MySQL connection handle
        String description;             // Tree, seed, or shrub description
        Boolean executeError = false;   // Error flag
        String errString = null;        // String for displaying errors
        int executeUpdateVal;           // Return value from execute indicating effected rows
        Boolean fieldError = true;      // Error flag
        String msgString = null;        // String for displaying non-error messages
        ResultSet res = null;           // SQL query result set pointer
        String tableSelected = null;    // String used to determine which data table to use
        Integer quantity;               // Quantity of trees, seeds, or shrubs
        Float perUnitCost;              // Cost per tree, seed, or shrub unit
        String productID = null;        // Product id of tree, seed, or shrub
        java.sql.Statement s = null;    // SQL statement pointer
        String SQLstatement = null;     // String for building SQL queries
        
           public Rmi() throws RemoteException {
               
           }
           public String say() throws RemoteException {
                      return "oooooooolaaaaaaaaaaa";
           }

    @Override
    public void ConnectDatabase(String textArea1, String textField1) throws RemoteException {
 {
               try
            {
                
                msgString = ">> Establishing Driver...";

                //load JDBC driver class for MySQL
                Class.forName( "com.mysql.jdbc.Driver" );

              //  msgString = ">> Setting up URL...";
                //temp1=temp1+("\n"+msgString);

                //define the data source
                String SQLServerIP = textField1;
                String sourceURL = "jdbc:mysql://" + SQLServerIP + ":3306/inventory";
               //  String sourceURL = "jdbc:mysql://127.0.0.1:3306/inventory";


              //  msgString = ">> Establishing connection with: " + sourceURL + "...";
               // textArea1.append("\n"+msgString);

                //create a connection to the db
                System.out.println(sourceURL);
                DBConn = DriverManager.getConnection(sourceURL,"remote","remote_pass");
                

            } catch (Exception e) {
                System.out.println("ERRO AO LIGAR A BD!!!!");
                errString =  "\nProblem connecting to database:: " + e;
             //   textArea1.append(errString);
                connectError = true;

            } // end try-catch    }
               System.out.println("ligou-se");
 }
         
 
 }

    @Override
    public String Button1Read() throws RemoteException {
        String temp="";
            

            try {
                s = DBConn.createStatement();
                res = s.executeQuery( "Select * from trees;" );
            } catch (SQLException ex) {
                Logger.getLogger(Rmi.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("ERRO Botao1!!!!");

            }
            try {
                while (res.next())
                {
                    msgString = res.getString(1) + " : " + res.getString(2) +
                            " : $"+ res.getString(4) + " : " + res.getString(3)
                            + " units in stock";
                    temp=temp+("\n"+msgString);

                }
            } catch (SQLException ex) {
                Logger.getLogger(Rmi.class.getName()).log(Level.SEVERE, null, ex);
            }
            return temp;    }

    @Override
    public String Button2Read() throws RemoteException {
            String temp="";

            try {
                s = DBConn.createStatement();
                res = s.executeQuery( "Select * from shrubs;" );
            } catch (SQLException ex) {
                Logger.getLogger(Rmi.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                while (res.next())
                {
                    msgString = res.getString(1) + " : " + res.getString(2) +
                            " : $"+ res.getString(4) + " : " + res.getString(3)
                            + " units in stock";
                    temp=temp+("\n"+msgString);
 
                }
            } catch (SQLException ex) {
                Logger.getLogger(Rmi.class.getName()).log(Level.SEVERE, null, ex);
            }
            return temp;      }

    @Override
    public String Button3Read() throws RemoteException {
    String temp="";

            try {
                 s = DBConn.createStatement();
                res = s.executeQuery( "Select * from seeds;" );
            } catch (SQLException ex) {
                Logger.getLogger(Rmi.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                while (res.next())
                {
                     msgString = res.getString(1) + " : " + res.getString(2) +
                            " : $"+ res.getString(4) + " : " + res.getString(3)
                            + " units in stock";

                }
            } catch (SQLException ex) {
                Logger.getLogger(Rmi.class.getName()).log(Level.SEVERE, null, ex);
            }
            return temp;    }

    @Override
    public Boolean Login(String user, String pass) throws RemoteException {
   Boolean bool=false;
             try {
                s = DBConn.createStatement();
                res = s.executeQuery( "Select * from utilizadores where nome='"+user+"' and password='"+pass+"';" );
            } catch (SQLException ex) {
                Logger.getLogger(Rmi.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                
                while (res.next())
                {
                    System.out.println("Login feito");
                    bool=true;
                    

                }
            } catch (SQLException ex) {

                Logger.getLogger(Rmi.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(bool){
             Calendar rightNow = Calendar.getInstance();

            int TheHour = rightNow.get(rightNow.HOUR_OF_DAY);
            int TheMinute = rightNow.get(rightNow.MINUTE);
            int TheSecond = rightNow.get(rightNow.SECOND);
            int TheDay = rightNow.get(rightNow.DAY_OF_WEEK);
            int TheMonth = rightNow.get(rightNow.MONTH);
            int TheYear = rightNow.get(rightNow.YEAR);

            String dateTimeStamp = TheMonth + "/" + TheDay + "/" + TheYear + " "
                    + TheHour + ":" + TheMinute  + ":" + TheSecond;
                String toSendFile=dateTimeStamp+" "+user;
               
                
                
                try{
          File file =new File("Logins.txt");
    	  if(!file.exists()){
    	 	file.createNewFile();
    	  }
    	  FileWriter fw = new FileWriter(file,true);
    	  BufferedWriter bw = new BufferedWriter(fw);
    	  PrintWriter pw = new PrintWriter(bw);
          //This will add a new line to the file content
    	  pw.println(toSendFile+"\n");
          /* Below three statements would add three 
           * mentioned Strings to the file in new lines.
           */
    	 
    	  pw.close();

	  System.out.println("Data successfully appended at the end of file");

       }catch(IOException ioe){
    	   System.out.println("Exception occurred:");
    	   ioe.printStackTrace();
      }
            }
            return bool;    }

    @Override
    public void ConnectDatabaseOrderInfo(String textArea1, String textField1) throws RemoteException {
        
               try
            {
                
                msgString = ">> Establishing Driver...";

                //load JDBC driver class for MySQL
                Class.forName( "com.mysql.jdbc.Driver" );

              //  msgString = ">> Setting up URL...";
                //temp1=temp1+("\n"+msgString);

                //define the data source
                String SQLServerIP = textField1;
                String sourceURL = "jdbc:mysql://" + SQLServerIP + ":3306/orderinfo";
               //  String sourceURL = "jdbc:mysql://127.0.0.1:3306/inventory";


              //  msgString = ">> Establishing connection with: " + sourceURL + "...";
               // textArea1.append("\n"+msgString);

                //create a connection to the db
                System.out.println(sourceURL);
                DBConn = DriverManager.getConnection(sourceURL,"remote","remote_pass");
                

            } catch (Exception e) {
                System.out.println("ERRO AO LIGAR A BD!!!!");
                errString =  "\nProblem connecting to database:: " + e;
             //   textArea1.append(errString);
                connectError = true;

            // end try-catch    }
               System.out.println("ligou-se");
 }
         
        
    }

    @Override
    public void newOrder(String Field1,String Field3,String Field4,String Field5,String Field6,String Area1,String Area2,String Area3,String Area4) throws RemoteException {
  // database as well.
        
        int beginIndex;                 // String parsing index
        String customerAddress;         // Buyers mailing address
        int endIndex;                   // String paring index
        String firstName = null;        // Customer's first name
        float fCost;                    // Total order cost
        String lastName = null;         // Customer's last name
        String orderTableName = null;   // This is the name of the table that lists the items
        String sTotalCost = null;       // String representing total order cost
        String sPerUnitCost = null;     // String representation of per unit cost
        String orderItem = null;        // Order line item from jTextArea2
        String phoneNumber = null;      // Customer phone number

        // Check to make sure there is a first name, last name, address and phone
        if ((Field3.length()>0) && (Field4.length()>0)
                && (Field5.length()>0)
                && (Area4.length()>0))
        {
            try {
                ConnectDatabaseOrderInfo("as",Field1);
            } catch (RemoteException ex) {
                    System.out.println("Erro ao ligar-se");
            }

        } else {

            System.out.println("\nMissing customer information!!!\n");
            connectError = true;

        }// customer data check

        //If there is not a connection error, then we form the SQL statement
        //to submit the order to the orders table and then execute it.

        if (!connectError )
        {
            Calendar rightNow = Calendar.getInstance();

            int TheHour = rightNow.get(rightNow.HOUR_OF_DAY);
            int TheMinute = rightNow.get(rightNow.MINUTE);
            int TheSecond = rightNow.get(rightNow.SECOND);
            int TheDay = rightNow.get(rightNow.DAY_OF_WEEK);
            int TheMonth = rightNow.get(rightNow.MONTH);
            int TheYear = rightNow.get(rightNow.YEAR);
            orderTableName = "order" + String.valueOf(rightNow.getTimeInMillis());

            String dateTimeStamp = TheMonth + "/" + TheDay + "/" + TheYear + " "
                    + TheHour + ":" + TheMinute  + ":" + TheSecond;

            // Get the order data
            firstName = Field3;
            lastName = Field4;
            phoneNumber = Field5;
            customerAddress = Area4;
            sTotalCost = Field6;
            beginIndex = 0;
            beginIndex = sTotalCost.indexOf("$",beginIndex)+1;
            sTotalCost = sTotalCost.substring(beginIndex, sTotalCost.length());
            fCost = Float.parseFloat(sTotalCost);
                
            try
            {
                s = DBConn.createStatement();

                SQLstatement = ( "CREATE TABLE " + orderTableName +
                            "(item_id int unsigned not null auto_increment primary key, " +
                            "product_id varchar(20), description varchar(80), " +
                            "item_price float(7,2) );");

                executeUpdateVal = s.executeUpdate(SQLstatement);

            } catch (Exception e) {

                System.out.println( "\nProblem creating order table " + orderTableName +":: " + e);
                executeError = true;

            } // try

            if ( !executeError )
            {
                try
                {
                    SQLstatement = ( "INSERT INTO orders (order_date, " + "first_name, " +
                        "last_name, address, phone, total_cost, shipped, " +
                        "ordertable) VALUES ( '" + dateTimeStamp + "', " +
                        "'" + firstName + "', " + "'" + lastName + "', " +
                        "'" + customerAddress + "', " + "'" + phoneNumber + "', " +
                        fCost + ", " + false + ", '" + orderTableName +"' );");

                    executeUpdateVal = s.executeUpdate(SQLstatement);
                  ///FILEEEEEEEEE  
                    
                     String toSendFile= dateTimeStamp+" "+ firstName + "" + lastName + " " +
                        " " + customerAddress +  " " + phoneNumber + " " +
                        fCost + "  " + "false" + " '" + orderTableName +"\n";
                     
                     
                      try{
          File file =new File("Log.txt");
    	  if(!file.exists()){
    	 	file.createNewFile();
    	  }
    	  FileWriter fw = new FileWriter(file,true);
    	  BufferedWriter bw = new BufferedWriter(fw);
    	  PrintWriter pw = new PrintWriter(bw);
          //This will add a new line to the file content
    	  pw.println(toSendFile+"\n");
          /* Below three statements would add three 
           * mentioned Strings to the file in new lines.
           */
    	 
    	  pw.close();

	  System.out.println("Data successfully appended at the end of file");

       }catch(IOException ioe){
    	   System.out.println("Exception occurred:");
    	   ioe.printStackTrace();
      }
                    
                } catch (Exception e1) {

                    System.out.println( "\nProblem with inserting into table orders:: " + e1);
                    executeError = true;

                    try
                    {
                        SQLstatement = ( "DROP TABLE " + orderTableName + ";" );
                        executeUpdateVal = s.executeUpdate(SQLstatement);

                    } catch (Exception e2) {

                        System.out.println( "\nProblem deleting unused order table:: " +
                                orderTableName + ":: " + e2);

                    } // try

                } // try

            } //execute error check

        } 

        // Now, if there is no connect or SQL execution errors at this point, 
        // then we have an order added to the orderinfo::orders table, and a 
        // new ordersXXXX table created. Here we insert the list of items in
        // jTextArea2 into the ordersXXXX table.

        if ( !connectError && !executeError )
        {
            // Now we create a table that contains the itemized list
            // of stuff that is associated with the order

            String[] items = Area2.split("\\n");

            for (int i = 0; i < items.length; i++ )
            {
                orderItem = items[i];

                // Check just to make sure that a blank line was not stuck in
                // there... just in case.
                
                if (orderItem.length() > 0 )
                {
                    // Parse out the product id
                    beginIndex = 0;
                    endIndex = orderItem.indexOf(" : ",beginIndex);
                    productID = orderItem.substring(beginIndex,endIndex);

                    // Parse out the description text
                    beginIndex = endIndex + 3; //skip over " : "
                    endIndex = orderItem.indexOf(" : ",beginIndex);
                    description = orderItem.substring(beginIndex,endIndex);

                    // Parse out the item cost
                    beginIndex = endIndex + 4; //skip over " : $"
                    //endIndex = orderItem.indexOf(" : ",orderItem.length());
                    //sPerUnitCost = orderItem.substring(beginIndex,endIndex);
                    sPerUnitCost = orderItem.substring(beginIndex,orderItem.length());
                    perUnitCost = Float.parseFloat(sPerUnitCost);

                    SQLstatement = ( "INSERT INTO " + orderTableName +
                        " (product_id, description, item_price) " +
                        "VALUES ( '" + productID + "', " + "'" +
                        description + "', " + perUnitCost + " );");
                    try
                    {
                        executeUpdateVal = s.executeUpdate(SQLstatement);

                        // Clean up the display

                        
                    } catch (Exception e) {

                        System.out.println( "\nProblem with inserting into table " + orderTableName +
                            ":: " + e);

                        
                    } // try
                    
                   

                } // line length check

            } //for each line of text in order table
                
        }
    }

    @Override
    public String getLogins() throws RemoteException {
BufferedReader br = null;
String temp="";
		try {
 
			String sCurrentLine;
 
			br = new BufferedReader(new FileReader("logins.txt"));
 
			while ((sCurrentLine = br.readLine()) != null) {
				temp=temp+sCurrentLine+"\n";
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
              return temp;
    }

    @Override
    public String getLogs() throws RemoteException {
        BufferedReader br = null;
        String temp="";
		try {
 
			String sCurrentLine;
 
			br = new BufferedReader(new FileReader("Log.txt"));
 
			while ((sCurrentLine = br.readLine()) != null) {
				temp=temp+sCurrentLine+"\n";
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
              return temp;    }

    @Override
    public String getShip() throws RemoteException {
BufferedReader br = null;
        String temp="";
		try {
 
			String sCurrentLine;
 
			br = new BufferedReader(new FileReader("Ship.txt"));
 
			while ((sCurrentLine = br.readLine()) != null) {
				temp=temp+sCurrentLine+"\n";
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
              return temp;     }

    
    

  
    

     
      
           

}

