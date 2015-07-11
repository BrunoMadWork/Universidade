import java.util.ArrayList;
import java.util.StringTokenizer;


public class Elementtokenizer {
	private StringTokenizer st;
	public ArrayList<String> getHash(String texto){
		st=new StringTokenizer(texto);
		ArrayList<String> array=new ArrayList<String>();
		while(st.hasMoreTokens()){
			array.add(st.nextToken());
		}
		return array;
	}	
}
