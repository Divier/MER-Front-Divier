
package com.amx.co.schema.claroheaders.v1;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.amx.co.schema.claroheaders.v1 package. 
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

    private final static QName _HeaderResponse_QNAME = new QName("http://www.amx.com/CO/Schema/ClaroHeaders/v1", "headerResponse");
    private final static QName _HeaderRequest_QNAME = new QName("http://www.amx.com/CO/Schema/ClaroHeaders/v1", "headerRequest");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.amx.co.schema.claroheaders.v1
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link HeaderRequest }
     * 
     */
    public HeaderRequest createHeaderRequest() {
        return new HeaderRequest();
    }

    /**
     * Create an instance of {@link HeaderResponse }
     * 
     */
    public HeaderResponse createHeaderResponse() {
        return new HeaderResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HeaderResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.amx.com/CO/Schema/ClaroHeaders/v1", name = "headerResponse")
    public JAXBElement<HeaderResponse> createHeaderResponse(HeaderResponse value) {
        return new JAXBElement<HeaderResponse>(_HeaderResponse_QNAME, HeaderResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HeaderRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.amx.com/CO/Schema/ClaroHeaders/v1", name = "headerRequest")
    public JAXBElement<HeaderRequest> createHeaderRequest(HeaderRequest value) {
        return new JAXBElement<HeaderRequest>(_HeaderRequest_QNAME, HeaderRequest.class, null, value);
    }

}
