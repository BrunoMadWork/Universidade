import java.net.*;
import java.io.*;

public class L_Rclient {
			static String ipPrimario="169.254.75.19";
			static String ipSecundario="127.0.0.1";
			static Socket s ;
			static int serversocket = 4011;//4011 4012
			static int backupsocket=4011;
			static DataInputStream in;
			static DataOutputStream out;
			static InputStreamReader input ;
			static BufferedReader reader ;
			static Keyboard_scanner scanner;
	public static void main(String args[]) {
		try { 
			s =novoSocket();
			in = new DataInputStream(s.getInputStream());
			out = new DataOutputStream(s.getOutputStream());
			input = new InputStreamReader(System.in);
			reader = new BufferedReader(input);
			scanner=new Keyboard_scanner(reader,out,s);														
		} catch (UnknownHostException e) {
			System.out.println("Sock:" + e.getMessage());
			novoSocket();
			novoStream();
		} catch (EOFException e) {
			System.out.println("EOF:" + e.getMessage());
			novoSocket();
			novoStream();
		} catch (IOException e) {					
			novoSocket();
			novoStream();			
		}
		
		while (true) {
			String data="";
			try {
				data = in.readUTF();
			} catch (IOException e) {
				novoSocket();
				novoStream();
				e.printStackTrace();
			}
			System.out.println(data);
		}
	}

	public static Socket novoSocket(){
		boolean ligado=false;
		while(ligado==false){
			ligado=true;
			try {	
				s = new Socket(ipPrimario, serversocket);
				System.out.println(s);
				if(ligado==false){
					ligado=true;
					s = new Socket(ipSecundario, backupsocket);
					System.out.println(s);
				}
			} catch (UnknownHostException e) {
				ligado=false;
				System.out.println("Tentou ligar se ao principal e falhou");
				e.printStackTrace();
			} catch (IOException e) {
				ligado=false;
				System.out.println("Tentou ligar se ao secundario e falhou");
				e.printStackTrace();
			}
		}
		return s;
	}
	
	public static void novoStream(){
		try{
			in = new DataInputStream(s.getInputStream());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			out = new DataOutputStream(s.getOutputStream());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		scanner=new Keyboard_scanner(reader,out,s);															
	}
}
