import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;


public interface Bd extends Remote {

	public void send_querry_nome_passe( ) throws RemoteException;
	public boolean has_user(String nome) throws RemoteException;
	public String get_password(String nome) throws RemoteException;
	public void put_reg_data(String nome, String password) throws RemoteException;
	public void put_beg_acount_data(String nome, double creditos) throws RemoteException;
	public double get_creditos(String nome) throws RemoteException;
	public void put_idea_table_data(String nome,String hashtag, int acções, int acções_totais,float preco,String texto,int tipo) throws RemoteException;
	public int get_n_topicos() throws RemoteException;
	 public int get_n_posts() throws RemoteException;
	public void puthashtag(String hashtag,int numero) throws RemoteException;
	public void put_all_things(String nome,ArrayList<String> hashtag_arrray,int acções,int tipo,float preco,String texto) throws RemoteException;
	public ArrayList<Topico> getTopicos() throws RemoteException;
	public ArrayList<Idea> getIdeas(int topic_key) throws RemoteException;
	public ArrayList<String> getCorrespondentIdeas(int topic_key) throws RemoteException;
	public ArrayList<Idea> getfullyownIdeas(String nome) throws RemoteException;
	public void deleteIdeas(Idea idea, String nome) throws RemoteException;
	public ArrayList<Idea> getIdeas_to_change(String nome) throws RemoteException;
	public float getPreco(String nome, int idea_key) throws RemoteException;
	public int getNumeroAcções(int idea_key) throws RemoteException;
    public int getNumeroAcçõesCliente(int idea_key) throws RemoteException;
    public ArrayList<SharesTableCell> getsharesTable(int idea_key) throws RemoteException;
    public int getCreditosCliente(String name) throws RemoteException;
    public int getExistingShares(String name,int idea_key) throws RemoteException;
    public boolean jaTemAlgumasShares(String name,int idea_key) throws RemoteException;
    public int getNumberhistorico() throws RemoteException;
    public void transação(String vendedor, String comprador,int acções,float preço,int key, int acções_comprador, int acções_vendedor,int creditos_comprador,int creditos_vendedor) throws RemoteException;
    public void alterar_preço(int preço, int idea_key, String name) throws RemoteException;    
    public ArrayList<Entrada_historico> get_historico() throws RemoteException;
}
