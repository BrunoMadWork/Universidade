import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class Bd_implementation  extends UnicastRemoteObject implements Bd  {
	private static final long serialVersionUID = 1L;
	private Connection connection;

	public Bd_implementation() throws RemoteException {
		System.out.println("-------- Oracle JDBC Connection Testing ------");
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your Oracle JDBC Driver?");
			e.printStackTrace();
			return;
		}
		System.out.println("Oracle JDBC Driver Registered!");
		connection = null;
		try {
			connection = DriverManager.getConnection(
					"jdbc:oracle:thin:@192.168.56.101:1521:XE", "bruno",
					"bruno8701");
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
		if (connection != null) {
			System.out.println("You made it, take control your database now!");
		} else {
			System.out.println("Failed to make connection!");
		}
	}

	public synchronized void send_querry_nome_passe() throws RemoteException{
		Statement stmt;
		ResultSet rs ;
		try {		       
			stmt =  connection.createStatement();
			rs =  stmt.executeQuery("SELECT name,password FROM login");
			while (rs.next()) {
				String nome = rs.getString(1);
				String password = rs.getString(2);
				System.out.println(nome + " " + password);
			}
		} catch (SQLException ex) {
			System.out.println("error code : " + ex.getErrorCode());
			System.out.println("error message : " + ex.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized Connection getConnection() {
		return connection;
	}

	public synchronized void setConnection(Connection connection) {
		this.connection = connection;
	}

	public synchronized boolean has_user(String nome) throws RemoteException {
		// TODO Auto-generated method stub
		Statement stmt;
		Boolean bool=false;
		ResultSet rs ;
		try {		       
			stmt =  connection.createStatement();
			rs =  stmt.executeQuery("SELECT name FROM login WHERE name='"+nome+"'");
			if(rs!=null){
				while(rs.next()){

					if(rs.getString(1).equals(nome)){
						return true;
					}
				}
				return false;
			}
		}
		catch (SQLException ex) {
			System.out.println("error code : " + ex.getErrorCode());
			System.out.println("error message : " + ex.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bool;
	}

	public synchronized String get_password(String nome) throws RemoteException {
		Statement stmt;
		ResultSet rs ;
		String temp=null;
		try {		       
			stmt =  connection.createStatement();
			rs =  stmt.executeQuery("SELECT password FROM login WHERE name='"+nome+"'");
			rs.next();		        	
			temp= rs.getString(1);
		}  catch (SQLException ex) {
			System.out.println("error code : " + ex.getErrorCode());
			System.out.println("error message : " + ex.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(temp);
		return temp;
	}

	public synchronized void put_reg_data(String nome, String password) throws RemoteException {
		Statement stmt;
		try {		       
			stmt =  connection.createStatement();
			stmt.executeQuery("Insert into login (name, password) VALUES('"+nome +"','"+ password+"')");
		}
		catch (SQLException ex) {
			System.out.println("error code : " + ex.getErrorCode());
			System.out.println("error message : " + ex.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized void  put_beg_acount_data(String nome, double creditos) throws RemoteException{
		Statement stmt;
		try {		       
			stmt =  connection.createStatement();
			stmt.executeQuery("Insert into saldos (name, creditos) VALUES('"+nome +"',"+ creditos+")");
		}
		catch (SQLException ex) {
			System.out.println("error code : " + ex.getErrorCode());
			System.out.println("error message : " + ex.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized double  get_creditos(String nome) throws RemoteException{
		Statement stmt;
		ResultSet rs ;
		double temp = 0;
		try {		       
			stmt =  connection.createStatement();
			rs =  stmt.executeQuery("SELECT creditos FROM saldos WHERE name='"+nome+"'");
			rs.next();
			temp= rs.getDouble(1);
		}  catch (SQLException ex) {
			System.out.println("error code : " + ex.getErrorCode());
			System.out.println("error message : " + ex.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}

	public synchronized void put_idea_table_data(String nome,String hashtag, int acções, int acções_totais,float preco,String texto,int tipo) throws RemoteException{
		Statement stmt;
		try {		       
			stmt =  connection.createStatement();
			stmt.executeQuery("INSERT Into idea_table(acções,acções_totais,preco,texto,tipo,idea_key) Values("+acções+","+acções_totais+","+preco+",'"+texto+"',"+tipo+","+0+")");
			//altera valor
		}
		catch (SQLException ex) {
			System.out.println("error code : " + ex.getErrorCode());
			System.out.println("error message : " + ex.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public synchronized void  puthashtag(String hashtag,int numero) throws RemoteException{
		ArrayList<String> array_temp=new ArrayList<String>();
		Statement stmt;
		ResultSet rs ;
		try {		       
			stmt =  connection.createStatement();
			rs =  stmt.executeQuery(
					"SELECT hashtag from topicos");
			if(rs!=null){
				while(rs.next()){

					array_temp.add(rs.getString(1));
				}
			}	
		}  catch (SQLException ex) {
			System.out.println("error code : " + ex.getErrorCode());
			System.out.println("error message : " + ex.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}

		int i;
		boolean esta=false;

		for(i=0;i<array_temp.size();i++){
			if(array_temp.get(i).equals(hashtag)){
				esta=true;
			}
		}

		if (esta==false){
			try {		       
				stmt =  connection.createStatement();
				stmt.executeQuery("Insert into topicos (hashtag,topic_key) VALUES("+hashtag+","+numero+1+")");
			}
			catch (SQLException ex) {
				System.out.println("error code : " + ex.getErrorCode());
				System.out.println("error message : " + ex.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public synchronized int  get_n_topicos() throws RemoteException{
		Statement stmt;
		ResultSet rs ;
		int n_topicos = 0;
		try {		       
			stmt =  connection.createStatement();
			rs =  stmt.executeQuery("Select count(*)from topicos");
			rs.next();
			n_topicos= rs.getInt(1);
		}  catch (SQLException ex) {
			System.out.println("error code : " + ex.getErrorCode());
			System.out.println("error message : " + ex.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return n_topicos;
	}
	public synchronized int  get_n_posts() throws RemoteException{
		Statement stmt;
		ResultSet rs ;
		int n_posts = 0;
		try {		       
			stmt =  connection.createStatement();
			rs =  stmt.executeQuery("Select count(*)from idea_table");
			rs.next();
			n_posts= rs.getInt(1);
		}  catch (SQLException ex) {
			System.out.println("error code : " + ex.getErrorCode());
			System.out.println("error message : " + ex.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return n_posts;
	}

	public synchronized void put_all_things(String nome,ArrayList<String> hashtag_arrray,int acções,int tipo,float preco,String texto) throws RemoteException{
		ArrayList<String> temp=hashtag_arrray;
		Statement stmt;
		ResultSet rs ;
		System.out.println(temp);

		try {		       
			stmt =  connection.createStatement();
			stmt.executeQuery("INSERT into idea_table(acções_totais , tipo ,texto , idea_key ) VALUES ("+acções+","+tipo+",'"+texto+"',"+(getMaxIdeaKey()+1)+")");
			stmt.executeQuery("INSERT into shares_table(nome ,acções,idea_key,preco) VALUES ('"+nome+"',"+acções+","+(getMaxIdeaKey())+","+preco+")");

			int i;
			for(i=0;i<temp.size();i++){
				int n_hashtags=get_n_topicos();
				System.out.println("Ola");
				rs=stmt.executeQuery("select count(*) from topicos where hashtag='"+temp.get(i)+"'");
				rs.next();
				if(rs.getInt(1)==0){
					stmt.executeQuery("Insert into topicos (hashtag,topic_key) VALUES('"+temp.get(i)+"',"+(n_hashtags+1)+")");
					stmt.executeQuery("Insert into i_t (idea_key ,topic_key ) VALUES("+(getMaxIdeaKey())+","+(n_hashtags+1)+")"); //atenção aqui
				}else{	
					int id_hashtag;
					rs=stmt.executeQuery("SELECT topic_key from topicos where hashtag='"+temp.get(i)+"'");
					rs.next();
					id_hashtag=rs.getInt(1);
					stmt.executeQuery("Insert into i_t (idea_key ,topic_key ) VALUES("+(getMaxIdeaKey())+","+id_hashtag+")"); //tava trocado?
				}
			}

		}  catch (SQLException ex) {
			System.out.println("error code : " + ex.getErrorCode());
			System.out.println("error message : " + ex.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized ArrayList<Topico> getTopicos() throws RemoteException{
		ArrayList<Topico> array=new ArrayList<Topico>();
		Statement stmt;
		ResultSet rs ;

		try {		       
			stmt =  connection.createStatement();
			rs =  stmt.executeQuery("Select hashtag,topic_key from topicos");
			while (rs.next()) {
				array.add(new Topico(rs.getString(1), rs.getInt(2)));
			}	

		}  catch (SQLException ex) {
			System.out.println("error code : " + ex.getErrorCode());
			System.out.println("error message : " + ex.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return array;
	}

	public synchronized ArrayList<Idea> getIdeas(int topic_key) throws RemoteException{
		ArrayList<Idea> array=new ArrayList<Idea>();
		Statement stmt;
		ResultSet rs ;
		try {		       
			stmt =  connection.createStatement();
			rs =  stmt.executeQuery("Select i.acções_totais, i.tipo,i.texto,i.idea_key from idea_table i, i_t it, topicos t where t.topic_key=it.topic_key and it.idea_key=i.idea_key and it.topic_key="+topic_key);
			while (rs.next()) {
				array.add(new Idea(rs.getInt(1), 0,rs.getInt(2), rs.getString(3),rs.getInt(4)));
			}	

		}  catch (SQLException ex) {
			System.out.println("error code : " + ex.getErrorCode());
			System.out.println("error message : " + ex.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return array;
	}


	public synchronized ArrayList<String> getCorrespondentIdeas(int topic_key) throws RemoteException{
		ArrayList<String> array=new ArrayList<String>();
		Statement stmt;
		ResultSet rs ;
		try {		       
			stmt =  connection.createStatement();
			rs =  stmt.executeQuery("Select hashtag from idea_table i, i_t it, topicos t where t.topic_key=it.topic_key and it.idea_key=i.idea_key and i.idea_key='1'");
			while (rs.next()) {
				array.add(rs.getString(1));
			}	

		}  catch (SQLException ex) {
			System.out.println("error code : " + ex.getErrorCode());
			System.out.println("error message : " + ex.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return array;
	}
	public synchronized ArrayList<Idea> getfullyownIdeas(String nome) throws RemoteException{
		ArrayList<Idea> array=new ArrayList<Idea>();
		Statement stmt;
		ResultSet rs ;
		try {		       
			stmt =  connection.createStatement();
			rs =  stmt.executeQuery("Select distinct s.acções, i.tipo,i.texto,i.idea_key from i_t it, shares_table s, topicos t, idea_table i where i.idea_key=s.idea_key and i.idea_key=it.idea_key and it.topic_key=t.topic_key and s.acções=i.acções_totais and s.nome='"+nome+"'");
			while (rs.next()) {
				array.add(new Idea(rs.getInt(1), 0,rs.getInt(2), rs.getString(3),rs.getInt(4)));
			}	

		}  catch (SQLException ex) {
			System.out.println("error code : " + ex.getErrorCode());
			System.out.println("error message : " + ex.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return array;
	}

	public synchronized void deleteIdeas(Idea idea, String nome) throws RemoteException{
		Statement stmt;
		try {		       
			stmt =  connection.createStatement();
			stmt.executeQuery("delete from idea_table where texto='"+idea.getTexto()+"' and idea_key=" +idea.getIdea_key());
			stmt.executeQuery("delete from shares_table where nome='"+ nome+"' and idea_key="+idea.getIdea_key());
			stmt.executeQuery("delete from i_t where idea_key="+idea.getIdea_key());

		}  catch (SQLException ex) {
			System.out.println("error code : " + ex.getErrorCode());
			System.out.println("error message : " + ex.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized ArrayList<Idea> getIdeas_to_change(String nome) throws RemoteException{
		ArrayList<Idea> array=new ArrayList<Idea>();
		Statement stmt;
		ResultSet rs ;
		try {		       
			stmt =  connection.createStatement();
			rs =  stmt.executeQuery("Select distinct s.acções, i.tipo,i.texto,i.idea_key from i_t it, shares_table s, topicos t, idea_table i where i.idea_key=s.idea_key and i.idea_key=it.idea_key and it.topic_key=t.topic_key  and s.nome='"+nome+"'");
			while (rs.next()) {
				array.add(new Idea(rs.getInt(1),0,rs.getInt(2), rs.getString(3),rs.getInt(4)));
			}	

		}  catch (SQLException ex) {
			System.out.println("error code : " + ex.getErrorCode());
			System.out.println("error message : " + ex.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return array;
	}

	public synchronized float getPreco(String nome, int idea_key) throws RemoteException{
		float preço = 0;
		Statement stmt;
		ResultSet rs ;
		try {		       
			stmt =  connection.createStatement();
			rs =  stmt.executeQuery("Select preco from shares_table where nome='"+nome+"' and idea_key="+idea_key);
			rs.next() ;
			preço=rs.getFloat(1);
		}  catch (SQLException ex) {
			System.out.println("error code : " + ex.getErrorCode());
			System.out.println("error message : " + ex.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return preço;
	}
	public synchronized void settPreco(String nome, int idea_key, int preço) throws RemoteException{
		Statement stmt;
		try {		       
			stmt =  connection.createStatement();
			stmt.executeQuery("update shares_table SET preco='"+preço+"' where nome='"+nome+"' and idea_key="+idea_key);
		}  catch (SQLException ex) {
			System.out.println("error code : " + ex.getErrorCode());
			System.out.println("error message : " + ex.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public synchronized int getNumeroAcções(int idea_key) throws RemoteException{
		Statement stmt;
		ResultSet rs ;
		int numero_acções = 1000;
		try {		       
			stmt =  connection.createStatement();
			rs=stmt.executeQuery("SELECT acções_totais from idea_table where idea_key="+idea_key);
			rs.next() ;
			numero_acções=rs.getInt(1);
		}  catch (SQLException ex) {
			System.out.println("error code : " + ex.getErrorCode());
			System.out.println("error message : " + ex.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return numero_acções;
	}

	public synchronized int getNumeroAcçõesCliente(int idea_key) throws RemoteException{
		Statement stmt;
		ResultSet rs ;
		int numero_acções = 1000;
		try {		       
			stmt =  connection.createStatement();
			rs=stmt.executeQuery("SELECT s.acções from idea_table i ,shares_table s where i.idea_key="+idea_key+" and s.idea_key=i.idea_key");
			rs.next() ;
			numero_acções=rs.getInt(1);
		}  catch (SQLException ex) {
			System.out.println("error code : " + ex.getErrorCode());
			System.out.println("error message : " + ex.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return numero_acções;
	}
	public synchronized ArrayList<SharesTableCell> getsharesTable(int idea_key) throws RemoteException{
		ArrayList<SharesTableCell> array=new ArrayList<SharesTableCell>();
		Statement stmt;
		ResultSet rs ;
		try {		       
			stmt =  connection.createStatement();
			rs =  stmt.executeQuery("SELECT nome,acções,idea_key, preco from shares_table where idea_key="+idea_key);
			while (rs.next()) {
				array.add(new SharesTableCell(rs.getString(1),rs.getInt(2),rs.getInt(3),rs.getInt(4)));
			}	

		}  catch (SQLException ex) {
			System.out.println("error code : " + ex.getErrorCode());
			System.out.println("error message : " + ex.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return array;
	}

	public  synchronized int getCreditosCliente(String name) throws RemoteException{
		Statement stmt;
		ResultSet rs ;
		int numero_creditos=1000;
		try {		       
			stmt =  connection.createStatement();
			rs=stmt.executeQuery("SELECT creditos from saldos where name='"+name +"'");
			rs.next() ;
			numero_creditos=rs.getInt(1);
		}  catch (SQLException ex) {
			System.out.println("error code : " + ex.getErrorCode());
			System.out.println("error message : " + ex.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return numero_creditos;
	}
	public synchronized  boolean jaTemAlgumasShares(String name,int idea_key) throws RemoteException{
		System.out.println("a1");
		Statement stmt;
		ResultSet rs ;
		boolean tem_shares=false; 
		try {		       
			stmt =  connection.createStatement();
			rs=stmt.executeQuery("select count(*) from shares_table where nome='"+name+"' and idea_key="+idea_key);
			rs.next() ;
			int temp=rs.getInt(1);
			if(temp>0){
				tem_shares=true;
			}
		}  catch (SQLException ex) {
			System.out.println("error code : " + ex.getErrorCode());
			System.out.println("error message : " + ex.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tem_shares;
	}
	public synchronized int getExistingShares(String name,int idea_key) throws RemoteException{ 
		Statement stmt;
		ResultSet rs ;
		int temp=0; 
		try {		       
			stmt =  connection.createStatement();
			System.out.println("a2");
			rs=stmt.executeQuery("select acções from shares_table where nome='"+name+"' and idea_key="+idea_key);
			System.out.println("a3");
			rs.next() ;
			temp=rs.getInt(1);
		}  catch (SQLException ex) {
			System.out.println("error code : " + ex.getErrorCode());
			System.out.println("error message : " + ex.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}
	public synchronized int getNumberhistorico() throws RemoteException{
		Statement stmt;
		ResultSet rs ;
		int n=0; 
		try {		       
			stmt =  connection.createStatement();
			rs=stmt.executeQuery("select count(*) from historico");
			rs.next() ;
			n =rs.getInt(1);
		}  catch (SQLException ex) {
			System.out.println("error code : " + ex.getErrorCode());
			System.out.println("error message : " + ex.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return n;
	}

	public synchronized void transação(String vendedor, String comprador,int acções,float preço,int key, int acções_comprador, int acções_vendedor,int creditos_comprador,int creditos_vendedor) throws RemoteException{
		Statement stmt;
		try {		       
			stmt =  connection.createStatement();
			System.out.println("00000000000");
			stmt.executeQuery("UPDATE shares_table SET acções="+(acções_comprador+acções)+" where nome='"+comprador+"' and idea_key="+key);
			System.out.println("111111111");
			stmt.executeQuery("UPDATE shares_table SET acções="+(acções_vendedor-acções)+" where nome='"+vendedor+"' and idea_key="+key);
			stmt.executeQuery("UPDATE saldos SET creditos="+(get_creditos(comprador)-(acções*preço))+"  where name='"+comprador+"'");
			System.out.println("333333333");
			stmt.executeQuery("UPDATE saldos SET creditos="+(get_creditos(vendedor)+(acções*preço))+"  where name='"+vendedor+"'");
			System.out.println("4444444444");
			stmt.executeQuery("Insert into historico(vendedor,comprador,acções,preco,idea_key,n_compra,tipo) Values('"+ vendedor+"','"+comprador+"',"+acções+","+preço+","+key+","+(getMaxn_compra()+1)+","+0+")");	
			System.out.println("55555555");
			stmt.executeQuery("Insert into historico(vendedor,comprador,acções,preco,idea_key,n_compra,tipo) Values('"+ vendedor+"','"+comprador+"',"+acções+","+preço+","+key+","+(getMaxn_compra()+1)+","+1+")");	
			System.out.println("666666666");
		}  catch (SQLException ex) {
			System.out.println("error code : " + ex.getErrorCode());
			System.out.println("error message : " + ex.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public synchronized int getMaxIdeaKey()throws RemoteException{
		Statement stmt;
		ResultSet rs ;
		int max=0; 
		try {		       
			stmt =  connection.createStatement();
			rs=stmt.executeQuery("SELECT max(idea_key) from idea_table");
			if(rs.next()==false){

				max=0;
			}
			else{
				max=rs.getInt(1);
			}
		}  catch (SQLException ex) {
			System.out.println("error code : " + ex.getErrorCode());
			System.out.println("error message : " + ex.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return max;
	}

	public synchronized int getMaxn_compra()throws RemoteException{
		Statement stmt;
		ResultSet rs ;
		int max=0; 
		try {		       
			stmt =  connection.createStatement();
			rs=stmt.executeQuery("SELECT max(n_compra) FROM historico");
			if(rs.next()==false){

				max=0;
			}
			else{
				max=rs.getInt(1);
			}
		}  catch (SQLException ex) {
			System.out.println("error code : " + ex.getErrorCode());
			System.out.println("error message : " + ex.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return max;
	}
	
	public synchronized void alterar_preço(int preço, int idea_key, String name) throws RemoteException{
		Statement stmt;
		try {		       
			stmt =  connection.createStatement();
			stmt.executeQuery("UPDATE shares_table set(preco) = ("+ preço+") where idea_key="+idea_key+" and nome='"+name +"'");

		}  catch (SQLException ex) {
			System.out.println("error code : " + ex.getErrorCode());
			System.out.println("error message : " + ex.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public synchronized ArrayList<Entrada_historico> get_historico() throws RemoteException{
		Statement stmt;
		ResultSet rs ;
		ArrayList<Entrada_historico> array_historico=new ArrayList<Entrada_historico>();

		try {		       
			stmt =  connection.createStatement();
			rs=stmt.executeQuery("SELECT * from historico");
			while (rs.next()) {
				array_historico.add(new Entrada_historico(rs.getString(1), rs.getString(2), rs.getInt(3),	rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7)) );		
			}
		}  catch (SQLException ex) {
			System.out.println("error code : " + ex.getErrorCode());
			System.out.println("error message : " + ex.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return array_historico;
	}
}
