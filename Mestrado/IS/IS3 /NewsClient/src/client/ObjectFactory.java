
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

    private final static QName _SendStringNewsResponse_QNAME = new QName("http://soaptoflow/", "SendStringNewsResponse");
    private final static QName _SendStringNews_QNAME = new QName("http://soaptoflow/", "SendStringNews");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SendStringNewsResponse }
     * 
     */
    public SendStringNewsResponse createSendStringNewsResponse() {
        return new SendStringNewsResponse();
    }

    /**
     * Create an instance of {@link SendStringNews }
     * 
     */
    public SendStringNews createSendStringNews() {
        return new SendStringNews();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendStringNewsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soaptoflow/", name = "SendStringNewsResponse")
    public JAXBElement<SendStringNewsResponse> createSendStringNewsResponse(SendStringNewsResponse value) {
        return new JAXBElement<SendStringNewsResponse>(_SendStringNewsResponse_QNAME, SendStringNewsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendStringNews }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://soaptoflow/", name = "SendStringNews")
    public JAXBElement<SendStringNews> createSendStringNews(SendStringNews value) {
        return new JAXBElement<SendStringNews>(_SendStringNews_QNAME, SendStringNews.class, null, value);
    }

}
