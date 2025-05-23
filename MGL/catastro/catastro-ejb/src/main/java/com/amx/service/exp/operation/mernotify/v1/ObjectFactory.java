
package com.amx.service.exp.operation.mernotify.v1;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import com.amx.co.schema.claroheaders.v1.HeaderRequest;
import com.amx.co.schema.claroheaders.v1.HeaderResponse;
import com.amx.co.schema.mobile.common.aplicationintegration.comunes.v1.FaultMessageType;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.amx.service.exp.operation.mernotify.v1 package. 
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

    private final static QName _HeaderResponse_QNAME = new QName("http://www.amx.com/Service/exp/Operation/MERNotify/v1.0", "headerResponse");
    private final static QName _HeaderRequest_QNAME = new QName("http://www.amx.com/Service/exp/Operation/MERNotify/v1.0", "headerRequest");
    private final static QName _MERNotifyRequest_QNAME = new QName("http://www.amx.com/Service/exp/Operation/MERNotify/v1.0", "MERNotifyRequest");
    private final static QName _MERNotifyResponse_QNAME = new QName("http://www.amx.com/Service/exp/Operation/MERNotify/v1.0", "MERNotifyResponse");
    private final static QName _Fault_QNAME = new QName("http://www.amx.com/Service/exp/Operation/MERNotify/v1.0", "fault");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.amx.service.exp.operation.mernotify.v1
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link MERNotifyRequestType }
     * 
     */
    public MERNotifyRequestType createMERNotifyRequestType() {
        return new MERNotifyRequestType();
    }

    /**
     * Create an instance of {@link MERNotifyResponseType }
     * 
     */
    public MERNotifyResponseType createMERNotifyResponseType() {
        return new MERNotifyResponseType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HeaderResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.amx.com/Service/exp/Operation/MERNotify/v1.0", name = "headerResponse")
    public JAXBElement<HeaderResponse> createHeaderResponse(HeaderResponse value) {
        return new JAXBElement<>(_HeaderResponse_QNAME, HeaderResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HeaderRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.amx.com/Service/exp/Operation/MERNotify/v1.0", name = "headerRequest")
    public JAXBElement<HeaderRequest> createHeaderRequest(HeaderRequest value) {
        return new JAXBElement<>(_HeaderRequest_QNAME, HeaderRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MERNotifyRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.amx.com/Service/exp/Operation/MERNotify/v1.0", name = "MERNotifyRequest")
    public JAXBElement<MERNotifyRequestType> createMERNotifyRequest(MERNotifyRequestType value) {
        return new JAXBElement<>(_MERNotifyRequest_QNAME, MERNotifyRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MERNotifyResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.amx.com/Service/exp/Operation/MERNotify/v1.0", name = "MERNotifyResponse")
    public JAXBElement<MERNotifyResponseType> createMERNotifyResponse(MERNotifyResponseType value) {
        return new JAXBElement<MERNotifyResponseType>(_MERNotifyResponse_QNAME, MERNotifyResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FaultMessageType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.amx.com/Service/exp/Operation/MERNotify/v1.0", name = "fault")
    public JAXBElement<FaultMessageType> createFault(FaultMessageType value) {
        return new JAXBElement<>(_Fault_QNAME, FaultMessageType.class, null, value);
    }

}
