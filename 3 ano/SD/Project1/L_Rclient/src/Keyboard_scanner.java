import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class Keyboard_scanner extends Thread {
	private DataInputStream in;
	private DataOutputStream out;
	private InputStreamReader input ;
	private BufferedReader reader ;
	private Keyboard_scanner scanner;
	private Socket s;
	public Keyboard_scanner( BufferedReader reader, DataOutputStream out,Socket s){
		this.reader=reader;
		this.out=out;
		this.s=s;
		this.start();
	}
	
	public void run(){
		while(true){
			String texto=null;		
			try {
				texto = reader.readLine();
			} catch (Exception e) {
			}	
			try {
				if(texto.equals("EXIT")){
					s.close();
					System.exit(0);
				}
				out.writeUTF(texto);
			} catch (IOException e) {				
			}
		}
	}
}