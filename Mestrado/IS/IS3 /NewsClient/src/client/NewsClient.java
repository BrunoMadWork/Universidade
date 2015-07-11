package client;

import java.util.Scanner;


public class NewsClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		NewsSoup tp =new NewsSoupService().getNewsSoupPort();
		System.out.println("Insira xml e 'OUT' no fim para finalizar:");
		Scanner sc=new Scanner(System.in);
		String toSend=null;
		toSend="";
		String temp;
		do{
			temp=sc.nextLine();
			if(!temp.equals("OUT")){
				toSend=toSend+temp+"\n";
			}
		
		}while(!temp.equals("OUT"));
		
		sc.close();
		System.out.println(toSend);

		tp.sendStringNews(toSend);
	}

}
