package server;

import server.InsulinDoseCalculator;
import server.InsulinDoseCalculatorImp;
import javax.xml.ws.Endpoint;

public class Calculator {
	public static void main(String[] args) {
		InsulinDoseCalculator calculator = new InsulinDoseCalculatorImp();
		Endpoint endpoint = Endpoint.publish("http://qcs20.dei.uc.pt:8080/Calculator", calculator);
	}
}
