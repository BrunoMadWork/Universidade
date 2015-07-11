import java.net.*;
import java.util.Timer;
import java.util.TimerTask;
import java.io.*;
public class UDP extends Thread {
	static int[]  data= {5001,5003,5002};
	DatagramSocket aSocket;
	
	public UDP( ){	
		this.start();
	}

	public void run(){
		aSocket = null;
		try {
			aSocket = new DatagramSocket(data[0]);// 5001  5002
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Socket Datagram à escuta no porto ");

		Enviar enviar=new Enviar();
			timeout();	
	}
		public void timeout( ){
			Timer timer=new Timer();
			timer.schedule(new Timeout(), 1000); //1000 milisegundos
			
			byte[] buffer = new byte[1000]; 			
			DatagramPacket request = new DatagramPacket(buffer, buffer.length);
			try {
				aSocket.receive(request);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Servidor_TCP.IsMAIN = true;
			timer.cancel();
			timeout();
		}
		
	public class Timeout extends TimerTask{
		public Timeout( ){	
		}
		public void run( ) {
			setFalse();
		}
		public void setFalse(){
			Servidor_TCP.IsMAIN = false;
		}
	}
}
	 
class Enviar extends Thread{
		DatagramSocket bSocket;
		Timer dispatch;
		byte [] m ;
		InetAddress aHost;
		int serverPort;
		
		public Enviar(){
			bSocket = null;
			 dispatch=new Timer();
			 try {
					bSocket=new DatagramSocket(UDP.data[1]); 
				} catch (SocketException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String texto = "---------";
								
				 m = texto.getBytes();
				 aHost = null;
				try {
					aHost = InetAddress.getByName("169.254.46.108");
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 serverPort = UDP.data[2];	                    
			this.start();
		}
		
		public void run(){
				dispatch();
		}
		
		public void dispatch(){               
			DatagramPacket request = new DatagramPacket(m,m.length,aHost,serverPort);
			try {
				bSocket.send(request);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			try {
				Thread.sleep(500);//2000
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dispatch();			
		}	
}
