package client;

public class TestClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			TranporterService tp =new TranporterService();
			ChannelSubscticion sub= new ChannelSubscticion();	
			System.out.println(Marshelling.Marshalls(sub));
			tp.getTranporterPort().sendString(Marshelling.Marshalls(sub));
	}

}




