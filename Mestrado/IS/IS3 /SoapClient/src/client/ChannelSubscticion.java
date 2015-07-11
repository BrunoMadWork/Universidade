package client;
import java.util.Scanner;


public class ChannelSubscticion {

	private String eMail;
	private String favoriteRegion;
	private boolean digest;
	private boolean isSoap;
	
	
	
	public ChannelSubscticion(){
		Scanner sc=new Scanner(System.in);
		//mail
		System.out.println("Insert your e-mail please");
		eMail=sc.nextLine();
		//favoriteRegion
		System.out.println("Choose your favorite region:\n1-World\n2-Us\n3-Africa\n4-Asia\n5-Europe\n6-Latin-america\n7-midlle east");
		int temp;
		do{
			temp=Integer.parseInt(sc.nextLine());
		}while(temp<1||temp>7);
		switch(temp) {
		
			case 1: favoriteRegion="nav-world";
				break;
			case 2: favoriteRegion="nav-us";
			break;
			case 3: favoriteRegion="nav-africa";
			break;
			case 4: favoriteRegion="nav-asia";
			break;
			case 5: favoriteRegion="nav-europe";
			break;
			case 6: favoriteRegion="nav-latin-america";
			break;
			case 7: favoriteRegion="nav-middle-east";
			break;
			
		}
		
		
		//Digest
		System.out.println("Do you want a digest?!\n1-yes\n2-no");
		int temp2 = 0;
		do{
			temp2=Integer.parseInt(sc.nextLine());
		}while(temp2<1||temp2>2);
		if(temp2==1){
			digest=true;
			
		}else{
			digest=false;
		}
		
		
		//soap
		isSoap=true;
		System.out.println("Sucess");
		sc.close();
		
	}
	public ChannelSubscticion(String eMail, String favoriteRegion,
			boolean digest, boolean isSoap) {
		super();
		this.eMail = eMail;
		this.favoriteRegion = favoriteRegion;
		this.digest = digest;
		this.isSoap = isSoap;
	}
	public String geteMail() {
		return eMail;
	}
	public void seteMail(String eMail) {
		this.eMail = eMail;
	}
	public String getFavoriteRegion() {
		return favoriteRegion;
	}
	public void setFavoriteRegion(String favoriteRegion) {
		this.favoriteRegion = favoriteRegion;
	}
	public boolean isDigest() {
		return digest;
	}
	public void setDigest(boolean digest) {
		this.digest = digest;
	}
	public boolean isSoap() {
		return isSoap;
	}
	public void setSoap(boolean isSoap) {
		this.isSoap = isSoap;
	}
	
	
}
