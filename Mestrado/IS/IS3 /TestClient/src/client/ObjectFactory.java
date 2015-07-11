
package client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the client package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _SendString_QNAME = new QName("http://soaptoflow/", "SendString");
    private final static QName _SendStringResponse_QNAME = new QName("http://soaptoflow/", "SendStringResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SendString }
     * 
     */
    public SendString createSendString() {
        return new SendString();
    }

    /**
     * Create an instance of {@link SendStringResponse }
     * 
     */
    public SendStringResponse createSendStringResponse() {
        return new SendStringResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendString }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soaptoflow/", name = "SendString")
    public JAXBElement<SendString> createSendString(SendString value) {
        return new JAXBElement<SendString>(_SendString_QNAME, SendString.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendStringResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soaptoflow/", name = "SendStringResponse")
    public JAXBElement<SendStringResponse> createSendStringResponse(SendStringResponse value) {
        return new JAXBElement<SendStringResponse>(_SendStringResponse_QNAME, SendStringResponse.class, null, value);
    }

}
