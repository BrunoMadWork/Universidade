import java.net.*;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.Hashtable;
import java.io.*;

public class Servidor_TCP{
	static boolean  IsMAIN;
	public static void main(String args[]){
		int numero=0;
		System.out.println("Vou criar um udp");
		IsMAIN=true;
		UDP udp=new UDP();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("Foi criado um udp");
		while(true){
			System.out.println(IsMAIN);
			System.out.println("A ver se há server online");
			if(IsMAIN==false){
				System.out.println("Tornou se principal");
				work(numero);
			}
			try {
				Thread.sleep(1000);
				System.out.println("Não se tornou principal");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void work(int numero){
		try{
			int serverPort = 4011;
			ServerSocket listenSocket = new ServerSocket(serverPort);

			while(true) {
				Socket clientSocket = listenSocket.accept();
				System.out.println("CLIENT_SOCKET (created at accept())="+clientSocket);
				numero ++;
				new Connection(clientSocket, numero);
			}
		}catch(IOException e)
		{System.out.println("Listen:" + e.getMessage());}
	}
}

// Thread para tratar de cada canal de comunicação com um cliente
class Connection extends Thread {
	DataInputStream in;
	DataOutputStream out;
	Socket clientSocket;
	int thread_number;
	String nome_cliente;
	String password;
	int n_acções=1000;
	
	public Connection (Socket aClientSocket, int numero) {
		thread_number = numero;
		try{
			clientSocket = aClientSocket;
			in = new DataInputStream(clientSocket.getInputStream());
			out = new DataOutputStream(clientSocket.getOutputStream());
			this.start();
		}catch(IOException e){System.out.println("Connection:" + e.getMessage());}
	}

	//=============================
	public void run(){
		System.getProperties().put("java.security.policy","java.policy");
		System.setSecurityManager(new RMISecurityManager());
		Bd bd=null;
		try {
			bd = (Bd) Naming.lookup("rmi://169.254.46.108:7000/Bd");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String resposta_cliente;
		int n_shares_cliente=1000;//n shares

		try{
			while(true){
				resposta_cliente=null;
				out.writeUTF("1-login\n2-registar\nEXIT-sair");
				resposta_cliente = in.readUTF();

				//login
				if(resposta_cliente.compareTo("1")==0){
					do{
						out.writeUTF("Insira o nome do utilizador");
						resposta_cliente=null;
						nome_cliente = in.readUTF();
					}while(bd.has_user(nome_cliente)==false);

					do{
						out.writeUTF("Insira a password");
						password=in.readUTF();

					}while(!bd.get_password(nome_cliente).equals(password));
					out.writeUTF("Está autentificado\nO seu login é "+nome_cliente+" e a password é "+bd.get_password(nome_cliente)+" e tem "+bd.get_creditos(nome_cliente)+" creditos!");

					String menu2="0";
					while(menu2.equals("0")){
						out.writeUTF( "\n(1) Submeter ideia\n(2) Ver ideias \n(3) Apagar ideias\n(4) Mudar preço de ideias\n(5) Mostrar historico de transações\nBREAK- ir para tras");

						//listagem de cada opção
						menu2=in.readUTF();
						if(menu2.equals("1")){

							//CODIGO PARA CRIACAO DE UM TOPICO
							ArrayList<String> hashtag;
							String hashtag_string;
							hashtag=null;
							float preço_acção;
							String texto_ideia=null;
							//inserir titulo

							do{
								out.writeUTF("Insira as hashtag");
								hashtag_string=in.readUTF();

							}while(hashtag_string=="\n"||hashtag_string=="\0"||hashtag_string.isEmpty());
							//insere nome

							//meter numero acções
							do{
								out.writeUTF("Insira o preço de cada acção de acções(Preço>0)");
								String preço_acção_string=in.readUTF();
								preço_acção=Float.parseFloat(preço_acção_string);
							}while(preço_acção<=0);

							//escrever texto
							do{
								out.writeUTF("Insira o texto");
								texto_ideia=in.readUTF();

							}while(texto_ideia==null||texto_ideia=="\n"||texto_ideia=="\0"||texto_ideia.isEmpty());

							hashtag=(new Elementtokenizer()).getHash(hashtag_string);

							bd.put_all_things(nome_cliente, hashtag, n_acções,0, preço_acção,texto_ideia);		
							menu2="0";
						}
						
						else if(menu2.equals("2")){
							//CRIAR UM ARRAY E CONSOANTE O NUMERO QUE O UTILIZADOR ESCOLHER O TOPICO ƒ apresentado

							String em2;
							ArrayList<Topico> topicos=bd.getTopicos();
							if(!topicos.isEmpty()){
							int i;
							String send_string="Escolha um do seguintes topicos:\n";

							for(i=0;i<topicos.size();i++){
								send_string=send_string.concat((i+1)+"-"+topicos.get(i).getTexto()+"\n");
							}
							out.writeUTF(send_string);
							em2=in.readUTF();
							Topico topico=topicos.get(Integer.parseInt(em2)-1);
							ArrayList<Idea> ideias=bd.getIdeas(topico.getTopic_key());
							System.out.println(ideias);

							//Imprimir ideias

							String send_ideais_string="Escolha uma das seguintes ideias:\n";
							for(i=0;i<ideias.size();i++){
								if(ideias.get(i).getTipo()==-1){
									send_ideais_string=send_ideais_string.concat((i+1)+" Discorda-- "+ideias.get(i).getTexto()+"\n");
								}
								else if(ideias.get(i).getTipo()==1){
									send_ideais_string=send_ideais_string.concat((i+1)+" Concorda--"+ideias.get(i).getTexto()+"\n");

								}
								else{
									send_ideais_string=send_ideais_string.concat((i+1)+"--"+ideias.get(i).getTexto()+"\n");

								}
							}
							out.writeUTF(send_ideais_string);
							String em3;
							em3=in.readUTF();
							Idea ideia=ideias.get((Integer.parseInt(em3)-1));
							String em4 = "-1";
							while(em4.equals("0")==false){
								out.writeUTF("Escolha uma opção\n 1-responder\n 2-comprar \n 3-back");
								em4=in.readUTF();
								if(em4.equals("1")){
									int tipo = -2;
									float preço_acção;
									String texto_ideia=null;
									do{
										out.writeUTF("Insira o tipo:\n-1 descordar\n0-neutro\n1-concordar");
										tipo=Integer.parseInt(in.readUTF());
									}while(tipo<-1||tipo>1);

									do{
										out.writeUTF("Insira o preço de cada acção de acções(Preço>0)");
										preço_acção=Float.parseFloat(in.readUTF());
									}while(preço_acção<=0);

									//escrever texto
									do{
										out.writeUTF("Insira o texto");
										texto_ideia=in.readUTF();

									}while(texto_ideia==null||texto_ideia=="\n"||texto_ideia=="\0"||texto_ideia.isEmpty());
									ArrayList<String> hashtag=bd.getCorrespondentIdeas(ideia.getIdea_key());
									bd.put_all_things(nome_cliente, hashtag, n_acções,tipo, preço_acção,texto_ideia);
									out.writeUTF("DONE!");
									em4 = "-1"; //216
								}
								if(em4.equals("2")){

									//mostrar o que cada pessoa tem.
									out.writeUTF("Numero total de acções:"+bd.getNumeroAcções(ideia.getIdea_key())+"\n Tuas acções:"+bd.get_creditos(nome_cliente) );

									String ch1=null;
									ArrayList<SharesTableCell> array_shares=bd.getsharesTable(ideia.getIdea_key());
									System.out.println(array_shares);
									String string_s_compra="Selecione qual quer comprar:\n";
									for(i=0;i<array_shares.size();i++){

										string_s_compra=string_s_compra.concat((i+1)+"- "+array_shares.get(i).getNome()+"---Numero de acções:"+array_shares.get(i).getAcções()+"---Preço por acção:"+array_shares.get(i).getPreço()+"\n");
									}
									out.writeUTF(string_s_compra);

									ch1=in.readUTF();//escolher qual comprar
									System.out.println(ch1);
									SharesTableCell cell=array_shares.get((Integer.parseInt(ch1)-1) );
									int n_compra=0;
									do{
										out.writeUTF("H‡ "+cell.getAcções()+" acções a venda, tem "+bd.get_creditos(nome_cliente)+" para usar");
										n_compra=Integer.parseInt(in.readUTF());
									}while((n_compra>cell.getAcções())||(n_compra*cell.getPreço()>bd.get_creditos(nome_cliente)));

									//compra em si
									System.out.println("a");
									if(n_compra==0){
										out.writeUTF("Foram compradas 0 shares");
										System.out.println("b");
									}

									else if(n_compra!=0){
										System.out.println("c");
										//ver se ja tinha shares
										int n_shares_topico=0;
										int n_shares_vendedor;
										float creditos_vendedor;
										System.out.println("d");
										n_shares_topico=bd.getExistingShares(nome_cliente, ideia.getIdea_key());
										System.out.println("e");
										n_shares_vendedor=bd.getExistingShares(cell.getNome(), ideia.getIdea_key());
										System.out.println("f");
										//aumentar shares de um, diminuir shares de outro e meter duas entradas de historico
										creditos_vendedor=(float) bd.get_creditos(cell.getNome());
										System.out.println("g");
										bd.transação(cell.getNome(), nome_cliente, n_compra, cell.getPreço(), cell.getIdea_key(), n_shares_topico, n_shares_vendedor,bd.getCreditosCliente(nome_cliente), n_shares_vendedor);
										System.out.println("h");
										out.writeUTF("Feito");
									}
									menu2="0";
								}
								else if(em4.equals("3")){
									menu2="0";
									em4="0";
								}
								em4="0";
								menu2="0";
							}
							
							}}
						else if(menu2.equals("3")){
							int i;
							ArrayList<Idea> array=bd.getfullyownIdeas(nome_cliente);
							if(!array.isEmpty()){
								String re3;
								String string_para_apagar="Escolha a ideia a apagar\n";
								for(i=0;i<array.size();i++){
									if(array.get(i).getTipo()==-1){
										string_para_apagar=string_para_apagar.concat((i+1)+"-Discorda-- "+array.get(i).getTexto()+"\n");
									}
									else if(array.get(i).getTipo()==1){
										string_para_apagar=string_para_apagar.concat((i+1)+"-Concorda-- "+array.get(i).getTexto()+"\n");

									}
									else{
										string_para_apagar=string_para_apagar.concat((i+1)+"-- "+array.get(i).getTexto()+"\n");
									}
								}
								out.writeUTF(string_para_apagar);

								re3=in.readUTF();
								System.out.println((Integer.parseInt(re3)-1));
								bd.deleteIdeas(array.get((Integer.parseInt(re3)-1)), nome_cliente);
								out.writeUTF("APAGADO");
							}
							else{
								out.writeUTF("Sem ideias");
							}

							menu2="0";
						}
						else if(menu2.equals("4")){
							//alterar preço das shares

							int i;
							ArrayList<Idea> array=bd.getIdeas_to_change(nome_cliente);
							if(!array.isEmpty()){
								String re3;
								int preço;
								String string_mudar_preço="Escolha a ideia a mudar preço\n";
								for(i=0;i<array.size();i++){
									if(array.get(i).getTipo()==-1){

										string_mudar_preço=string_mudar_preço.concat((i+1)+"-Discorda-- "+array.get(i).getTexto()+"\n");
									}
									else if(array.get(i).getTipo()==1){
										string_mudar_preço=string_mudar_preço.concat((i+1)+"-Concorda--"+array.get(i).getTexto()+"\n");

									}
									else{
										string_mudar_preço=string_mudar_preço.concat((i+1)+"-- "+array.get(i).getTexto()+"\n");

									}
								}

								out.writeUTF(string_mudar_preço);
								re3=in.readUTF();
								Idea ideia=array.get((Integer.parseInt(re3)-1));
								out.writeUTF("Preço actual: "+ bd.getPreco(nome_cliente, ideia.getIdea_key())+"\nInsira novo preço");
								preço=Integer.parseInt(in.readUTF());
								bd.alterar_preço( preço,ideia.getIdea_key(), nome_cliente);
							}
							else{
								out.writeUTF("Sem ideias");
							}
							menu2="0";
						}
						else if(menu2.equals("5")){
							//mostrar historico
							ArrayList<Entrada_historico> array_historico;
							array_historico=bd.get_historico();
							String stp="";
							int i;
							for(i=0;i<array_historico.size();i++){
								stp= stp.concat(array_historico.get(i).printEntry());
							}
							out.writeUTF(stp);
							menu2="0";
						}
						else if(menu2.equals("6")){
							//mostrar ideias
							menu2="0";
						}	
						else if(menu2.equals("BACK")){
							menu2="0";
						}	
					}
				}
				//registar
				else if(resposta_cliente.compareTo("2")==0){
					
					//pedir o nome
					do{
						out.writeUTF("Insira o nome do utilizador");
						nome_cliente=null;
						nome_cliente = in.readUTF();

					}while(bd.has_user(nome_cliente)==true);

					//pedir passe
					do{
						out.writeUTF("Insira a password");
						password=null;
						password=in.readUTF();
						System.out.println(password);
					}while(password==null||password=="\n"||password=="\0"||password.isEmpty());

					//verificar dados
					out.writeUTF("Está autentificado\nO seu login é "+nome_cliente+" e a password é "+password);

					//se a pass e utilizador s‹o validos
					n_shares_cliente=1000;
					double saldo=1000;
					bd.put_reg_data(nome_cliente, password);
					bd.put_beg_acount_data(nome_cliente, saldo);
				}
				else{
					out.writeUTF("Opção não valida");
				}
				resposta_cliente=null;
			}
		}catch(EOFException e){System.out.println("EOF:" + e);
		}catch(IOException e){System.out.println("IO:" + e);}
	}
}
