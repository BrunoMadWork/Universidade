package soaptoflow;

import javax.jws.WebService;

@WebService
public interface Tranporter {
	public String SendString(String a);
}
