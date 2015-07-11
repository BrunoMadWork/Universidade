package servlet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
//-1 codigo de erro

public class Voter {
	public static int webservicesNumber;
	public Version[] ArrayServicos ;	
	public static int saida;
	public static String InfoText;
	public static String FinalResultsInfotext;
	Voter(){
		// TODO Auto-generated method stub
		 webservicesNumber=3;
		 ArrayServicos =new Version[webservicesNumber];
		for(int i=0; i<ArrayServicos.length;i++){
			ArrayServicos[i]=new Version(i);
		}
		

		

		//como sincronizar isto?
	}
	public  Integer getMealTimeInsulinDose(int carbohydrateAmount,
			int carbohydrateToInsulinRatio, int preMealBloodSugar,
			int targetBloodSugar, int personalSensitivity){
		//meter todos os webservices
				ArrayList<Integer> resultados= new  ArrayList<Integer>();
				 saida = 0;
				 InfoText="";
				for(int i=0; i<ArrayServicos.length;i++){
					resultados.add((ArrayServicos[i].getMealtimeInsulinDose(carbohydrateAmount, carbohydrateToInsulinRatio, preMealBloodSugar, targetBloodSugar, personalSensitivity))); 
					//System.out.println(ArrayServicos[i].calculator.backgroundInsulinDose(bodyWeight));
					//System.out.println(resultados.get(i));
					
					
					//TRATAR DOS QUE NAO RESPONDEM
				}
				while(saida!=webservicesNumber){
					
				} 
				Integer majority= getMajority(resultados);
				int SucessVotes=getSucessVotes(resultados);
				
				FinalResultsInfotext="The number of services that voted were: "+SucessVotes+ "<p> The vote result was :"+isItValidator(majority)+InfoText+"<p>";
				System.out.println(FinalResultsInfotext);
				return majority;
		
	}
	public  int getBackgroundInsulinDose(int bodyWeight){
		
		//meter todos os webservices
		ArrayList<Integer> resultados= new  ArrayList<Integer>();
		 saida=0;
		 InfoText="";
		for(int i=0; i<ArrayServicos.length;i++){
			resultados.add((ArrayServicos[i].getBackgroundInsulinDose(bodyWeight))); 
			//System.out.println(ArrayServicos[i].calculator.backgroundInsulinDose(bodyWeight));
			//System.out.println(resultados.get(i));
			
			
			//TRATAR DOS QUE NAO RESPONDEM
		}
		while(saida!=webservicesNumber){
			
		}
		Integer majority= getMajority(resultados);
		int SucessVotes=getSucessVotes(resultados);
		FinalResultsInfotext="The number of services that voted were: "+SucessVotes+ "<p> The vote result was :"+isItValidator(majority)+InfoText+"<p>";
		System.out.println(FinalResultsInfotext);
		return majority;
			
			
		
	}
	public  Integer getPersonalSensitivityToInsulin(int physicalActivityLevel,
			int[] physicalActivitySamples, int[] bloodSugarDropSamples){
		
		//meter todos os webservices
		ArrayList<Integer> resultados= new  ArrayList<Integer>();
		saida=0;
		InfoText="";
		for(int i=0; i<ArrayServicos.length;i++){
			resultados.add((ArrayServicos[i].getPersonalSensitivityToInsulin(physicalActivityLevel,physicalActivitySamples,bloodSugarDropSamples))); 
			//System.out.println(resultados.get(i));
			
			
			//TRATAR DOS QUE NAO RESPONDEM
		}
		while(saida!=webservicesNumber){
			
		}
		
			Integer majority= getMajority(resultados);
			int SucessVotes=getSucessVotes(resultados);
			FinalResultsInfotext="The number of services that voted were: "+SucessVotes+ "<p> The vote result was :"+isItValidator(majority)+InfoText+"<p>";
			System.out.println(FinalResultsInfotext);
			return majority;
	
	}
	public  Integer getMajority(ArrayList<Integer> toCount){
		for(int i=0;i<(toCount.size()/2)+1;i++){
			int contador=0;
			for(int j=0;j<toCount.size();j++){
				if(toCount.get(i)==toCount.get(j)||toCount.get(i)==(toCount.get(j)-1)||(toCount.get(i)==toCount.get(j)+1)){
					contador++;
					if(contador>toCount.size()/2){
						return toCount.get(i);
					}
				}
			}
		}
		   
		   
		return -1;
	}
	public  String getInfoText(){
		return FinalResultsInfotext;
		
	}
	public  int getSucessVotes(ArrayList<Integer> toCount){
		int count=0;
		for(int i=0;i<ArrayServicos.length;i++){
			if(toCount.get(i)!=-1){
				count++;
			}
		}
		return count;
	}
	public  String isItValidator(int number){
		if(number>(webservicesNumber/2)){
			return ""+number;
		}
		return "No satisfatory result";
		
	}
	
	
}