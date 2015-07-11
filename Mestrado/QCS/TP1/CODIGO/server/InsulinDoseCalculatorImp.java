package server;

import javax.jws.WebService;
import javax.jws.WebMethod;

@WebService
public class InsulinDoseCalculatorImp implements InsulinDoseCalculator {
		@WebMethod

	@Override
	public int mealtimeInsulinDose(int carbohydrateAmount,
			int carbohydrateToInsulinRatio, int preMealBloodSugar,
			int targetBloodSugar, int personalSensitivity) {
		if(targetBloodSugar>preMealBloodSugar){
			return 0;
		}
		double CarbohydrateDose=(double) ((((double)carbohydrateAmount/carbohydrateToInsulinRatio)/(double) personalSensitivity)*50);
		System.out.println(CarbohydrateDose);
		double HighbloodSugarDose=(double) ((preMealBloodSugar - targetBloodSugar) /(double) personalSensitivity);
		System.out.println(HighbloodSugarDose);

		return (int)  Math.round((CarbohydrateDose+HighbloodSugarDose));
		
	}
			@WebMethod

	@Override
	public int backgroundInsulinDose(int bodyWeight) {
		
		return (int) Math.round(0.5*(0.55*bodyWeight));
	}
		@WebMethod

	@Override
	public int personalSensitivityToInsulin(int physicalActivityLevel,
			int[] physicalActivitySamples, int[] bloodSugarDropSamples ) {
			double sumx = 0.0, sumy = 0.0, sumx2 = 0.0;
			int i=0;
			int n=physicalActivitySamples.length;
			for(i=0;i<physicalActivitySamples.length;i++){
				sumx  += (double) physicalActivitySamples[i]; //0 10
								System.out.println(" --------------------act------"+bloodSugarDropSamples[i]);

			}
 			for(i=0;i<physicalActivitySamples.length;i++){
				sumx2 += (((double) physicalActivitySamples[i]) * physicalActivitySamples[i]); //100
			}
			for(i=0;i<bloodSugarDropSamples.length;i++){
				sumy += (double) bloodSugarDropSamples[i]; //100
				System.out.println(" --------------------bloood------"+bloodSugarDropSamples[i]);
			}

            double xbar = (double) sumx / n; //5
        	double ybar = (double) sumy / n;  //50
        	double xxbar = 0.0, yybar = 0.0, xybar = 0.0;
        	            System.out.println("xbar:"+xbar);
        	            System.out.println("ybar:"+ybar);


        	for ( i = 0; i < n; i++) {
            xxbar += ((physicalActivitySamples[i] - xbar) * (physicalActivitySamples[i] - xbar)); // 50 
            yybar += ((bloodSugarDropSamples[i] - ybar) * (bloodSugarDropSamples[i] - ybar)); // 0 0
            xybar += ((physicalActivitySamples[i] - xbar) * (bloodSugarDropSamples[i] - ybar)); //0 
            System.out.println("xybar:"+xybar);
        }
        	System.out.println("xxbar:"+xxbar);
        	System.out.println("xybar:"+xybar);


        double beta1 = xybar / (double) xxbar; //0
        double beta0 = ybar - (double) (beta1 * xbar); //50
        System.out.println("APROXIMACAO: "+ (beta0+beta1*physicalActivityLevel));

        return (int)  Math.round(beta0+beta1*physicalActivityLevel);



















		/* int i;

		    // Compute averages.
		    double xbar = 0;
		    for (i=0; i<physicalActivitySamples.length; i++)
		      xbar =xbar +(double) physicalActivitySamples[i];
		    xbar = xbar/((double) physicalActivitySamples.length);

		    double ybar = 0;
		    for (i=0; i<bloodSugarDropSamples.length; i++)
		      ybar = ybar +(double) bloodSugarDropSamples[i];
		    ybar = ybar/((double) bloodSugarDropSamples.length);
		    
		    // Compute Sxx and Syy
		    double Sxx = 0;
		    for (i=0; i<physicalActivitySamples.length; i++)
		      Sxx += ((double) physicalActivitySamples[i] - xbar) * ((double) physicalActivitySamples[i] - xbar);

		    double Sxy = 0;
		    for (i=0; i<bloodSugarDropSamples.length; i++)
		      Sxy += ((double) bloodSugarDropSamples[i] - xbar) * ((double) bloodSugarDropSamples[i] - ybar);

		    // Hence find maximum likelihood
		    // estimators.
		    double m = (double) Sxy / Sxx;

		    double b = ybar - m * xbar;
		    System.out.println(" Sxx=  "+Sxx);
		    System.out.println(" Sxy=  "+Sxy);
		    System.out.println(" m=  "+m);
		    System.out.println(" b=  "+b);
			System.out.println("SENSIBILIDADE: "+(int) Math.round((b+ (double) physicalActivityLevel*m)));
		return  (int) Math.round((b+ (double) physicalActivityLevel*m));*/

	}

}
