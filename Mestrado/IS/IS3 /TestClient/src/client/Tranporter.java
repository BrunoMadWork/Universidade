
package client;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "Tranporter", targetNamespace = "http://soaptoflow/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface Tranporter {


    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.String
     */
    @WebMethod(operationName = "SendString")
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "SendString", targetNamespace = "http://soaptoflow/", className = "client.SendString")
    @ResponseWrapper(localName = "SendStringResponse", targetNamespace = "http://soaptoflow/", className = "client.SendStringResponse")
    public String sendString(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

}
