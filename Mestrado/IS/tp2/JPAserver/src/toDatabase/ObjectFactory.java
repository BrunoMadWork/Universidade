//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.10.11 at 04:23:04 PM WEST 
//


package toDatabase;

import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the generated package. 
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

    private final static QName _Month_QNAME = new QName("", "month");
    private final static QName _Year_QNAME = new QName("", "year");
    private final static QName _Author_QNAME = new QName("", "author");
    private final static QName _Highlits_QNAME = new QName("", "highlits");
    private final static QName _Time_QNAME = new QName("", "time");
    private final static QName _Text_QNAME = new QName("", "text");
    private final static QName _Video_QNAME = new QName("", "video");
    private final static QName _Title_QNAME = new QName("", "title");
    private final static QName _Day_QNAME = new QName("", "day");
    private final static QName _Photos_QNAME = new QName("", "photos");
    private final static QName _Url_QNAME = new QName("", "url");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: generated
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link News }
     * 
     */
    public DbNews createNews() {
        return new DbNews();
    }


    /**
     * Create an instance of {@link Regions }
     * 
     */
    public DbRegions createRegions() {
        return new DbRegions();
    }

    /**
     * Create an instance of {@link Region }
     * 
     */
    public DbRegion createRegion() {
        return new DbRegion();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "month")
    public JAXBElement<String> createMonth(String value) {
        return new JAXBElement<String>(_Month_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "year")
    public JAXBElement<Integer> createYear(Integer value) {
        return new JAXBElement<Integer>(_Year_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "author")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    public JAXBElement<String> createAuthor(String value) {
        return new JAXBElement<String>(_Author_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "highlits")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    public JAXBElement<String> createHighlits(String value) {
        return new JAXBElement<String>(_Highlits_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "time")
    public JAXBElement<BigInteger> createTime(BigInteger value) {
        return new JAXBElement<BigInteger>(_Time_QNAME, BigInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "text")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    public JAXBElement<String> createText(String value) {
        return new JAXBElement<String>(_Text_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "video")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    public JAXBElement<String> createVideo(String value) {
        return new JAXBElement<String>(_Video_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "title")
    public JAXBElement<String> createTitle(String value) {
        return new JAXBElement<String>(_Title_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "day")
    public JAXBElement<Integer> createDay(Integer value) {
        return new JAXBElement<Integer>(_Day_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "photos")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    public JAXBElement<String> createPhotos(String value) {
        return new JAXBElement<String>(_Photos_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "url")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    public JAXBElement<String> createUrl(String value) {
        return new JAXBElement<String>(_Url_QNAME, String.class, null, value);
    }

}
