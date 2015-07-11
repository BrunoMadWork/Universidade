package servlet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.ws.WebServiceException;

import client.InsulinDoseCalculatorImp;
import client.InsulinDoseCalculatorImpService;
import client2.*;
import client3.*;
//http://stackoverflow.com/questions/1952315/create-a-reusable-stub-for-multiple-webservices-asmx
public class Version extends Thread{
	public InsulinDoseCalculatorImp calculator;
	public InsulinDoseCalculator calculator2;
	public Insulin calculator3;
	public int id;
	public Version(int a){
		try{
			id=a;
		if(id==0){
		InsulinDoseCalculatorImpService calulatorservice= new InsulinDoseCalculatorImpService();
		 calculator=calulatorservice.getInsulinDoseCalculatorImpPort();
		}else if(id==1){
			client2.InsulinDoseCalculatorService calulatorservice= new client2.InsulinDoseCalculatorService();
			 calculator2= calulatorservice.getInsulinDoseCalculatorPort();

		}else if (id==2){
			client3.InsulinService calulatorservice= new client3.InsulinService();
			calculator3=calulatorservice.getInsulinPort();
		}
		}catch(WebServiceException e){
			System.out.println("Thread number: "+id+" Inicialization failed");
			this.stop();
		}
		
	}
	public void TestPrint(){
		System.out.print("Threadd");
		
	}
	public synchronized int getMealtimeInsulinDose(int carbohydrateAmount,
			int carbohydrateToInsulinRatio, int preMealBloodSugar,
			int targetBloodSugar, int personalSensitivity){
	//	System.out.println(id+"-getMealtimeInsulinDose running  ");
		Voter.saida++;
		try{
		try{
			int value=-1;
			if(id==0){
				 value=calculator.mealtimeInsulinDose(carbohydrateAmount, carbohydrateToInsulinRatio, preMealBloodSugar, targetBloodSugar, personalSensitivity);

			}else if(id==1){
				 value=calculator2.mealtimeInsulinDose(carbohydrateAmount, carbohydrateToInsulinRatio, preMealBloodSugar, targetBloodSugar, personalSensitivity);

			}else if(id==2){
				 value=calculator3.mealtimeInsulinDose(carbohydrateAmount, carbohydrateToInsulinRatio, preMealBloodSugar, targetBloodSugar, personalSensitivity);

			}
			Voter.InfoText=Voter.InfoText+"<p> A thread "+id+" votou:"+value+"<p>";
				return value;	
		}catch(WebServiceException e){
			System.out.println("Thread number: "+id+" failed");
		}
		}catch(NullPointerException e){
			System.out.println("Thread number: "+id+" failed");
		}
		return -1;
		
	}
	public synchronized int getBackgroundInsulinDose(int bodyWeight){
	//	System.out.println(id+"-getBackgroundInsulinDoseg  ");
		
		Voter.saida++;
		try{
		try{
		int value=-1;
		if(id==0){
			value=calculator.backgroundInsulinDose(bodyWeight);
		}else if(id==1){
			value=calculator2.backgroundInsulinDose(bodyWeight);
		}else if(id==2){
			value=calculator3.backgroundInsulinDose(bodyWeight);
		}
			
		
		Voter.InfoText=Voter.InfoText+"<p> A thread "+id+" votou:"+value+"<p>";

		return value;
		}catch(WebServiceException e){
			System.out.println("Thread number: "+id+" failed");
		}
		}catch(NullPointerException e){
			System.out.println("Thread number: "+id+" failed");
		}
		
		return -1;
		
		
	
		
	}
	public synchronized int getPersonalSensitivityToInsulin(int physicalActivityLevel,
			int[] physicalActivitySamples, int[] bloodSugarDropSamples){
		//System.out.println(id+"-getPersonalSensitivityToInsulin  ");
		
		  List<Integer> arg2 = new ArrayList<Integer>();
		    for (int index = 0; index < physicalActivitySamples.length; index++)
		    {
		    	arg2.add(physicalActivitySamples[index]);
		    }
		    List<Integer> arg3 = new ArrayList<Integer>();
		    for (int index = 0; index < bloodSugarDropSamples.length; index++)
		    {
		    	arg3.add(bloodSugarDropSamples[index]);
		    }
		
		
		Voter.saida++;
		try{
		try{
			
			
			int value=-1;
			if(id==0){
				 value=calculator.personalSensitivityToInsulin(physicalActivityLevel,arg2, arg3);
			}else if(id==1){
				 value=calculator2.personalSensitivityToInsulin(physicalActivityLevel,arg2, arg3);
			}else if(id==2){
				 value=calculator3.personalSensitivityToInsulin(physicalActivityLevel,arg2, arg3);
			}
				
			Voter.InfoText=Voter.InfoText+"<p> A thread "+id+" votou:"+value+"<p>";

		return value;
		}catch(WebServiceException e){
			System.out.println("Thread number: "+id+" failed");
		}
		}catch(NullPointerException e){
			System.out.println("Thread number: "+id+" failed");
		}
		return -1;
		
	}

}
