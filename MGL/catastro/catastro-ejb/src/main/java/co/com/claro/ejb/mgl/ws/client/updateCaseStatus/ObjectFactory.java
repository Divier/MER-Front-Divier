
package co.com.claro.ejb.mgl.ws.client.updateCaseStatus;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the co.com.claro.ejb.mgl.ws.client.updateCaseStatus package. 
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

    private final static QName _EricssonFaultMessageTypeOriginSystem_QNAME = new QName("http://www.ericsson.com/esb/data/generico/CommonTypes/v2/", "originSystem");
    private final static QName _UpdateCaseStatusResponse_QNAME = new QName("http://www.ericsson.com/esb/message/customer/updateCaseStatusResponse/v1.0", "updateCaseStatusResponse");
    private final static QName _UpdateCaseStatusRequest_QNAME = new QName("http://www.ericsson.com/esb/message/customer/updateCaseStatusRequest/v1.0", "updateCaseStatusRequest");
    private final static QName _EricssonFault_QNAME = new QName("http://www.ericsson.com/esb/data/generico/CommonTypes/v2/", "ericssonFault");
    private final static QName _HeaderResponse_QNAME = new QName("http://www.ericsson.com/esb/data/generico/CommonTypes/v2/", "headerResponse");
    private final static QName _HeaderRequest_QNAME = new QName("http://www.ericsson.com/esb/data/generico/CommonTypes/v2/", "headerRequest");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: co.com.claro.ejb.mgl.ws.client.updateCaseStatus
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link EricssonFaultMessageType }
     * 
     * @return 
     */
    public EricssonFaultMessageType createEricssonFaultMessageType() {
        return new EricssonFaultMessageType();
    }

    /**
     * Create an instance of {@link HeaderRequestType }
     * 
     * @return 
     */
    public HeaderRequestType createHeaderRequestType() {
        return new HeaderRequestType();
    }

    /**
     * Create an instance of {@link HeaderResponseType }
     * 
     * @return 
     */
    public HeaderResponseType createHeaderResponseType() {
        return new HeaderResponseType();
    }

    /**
     * Create an instance of {@link UpdateCaseStatusType }
     * 
     * @return 
     */
    public UpdateCaseStatusType createUpdateCaseStatusType() {
        return new UpdateCaseStatusType();
    }

    /**
     * Create an instance of {@link UpdateCaseStatusResponseTYPE }
     * 
     * @return 
     */
    public UpdateCaseStatusResponseTYPE createUpdateCaseStatusResponseTYPE() {
        return new UpdateCaseStatusResponseTYPE();
    }

    /**
     * Create an instance of {@link Notes }
     * 
     * @return 
     */
    public Notes createNotes() {
        return new Notes();
    }

    /**
     * Create an instance of {@link UtilityFields }
     * 
     * @return 
     */
    public UtilityFields createUtilityFields() {
        return new UtilityFields();
    }

    /**
     * Create an instance of {@link Quantity }
     * 
     * @return 
     */
    public Quantity createQuantity() {
        return new Quantity();
    }

    /**
     * Create an instance of {@link Money }
     * 
     * @return 
     */
    public Money createMoney() {
        return new Money();
    }

    /**
     * Create an instance of {@link Threshold }
     * 
     * @return 
     */
    public Threshold createThreshold() {
        return new Threshold();
    }

    /**
     * Create an instance of {@link ResponseStatus }
     * 
     * @return 
     */
    public ResponseStatus createResponseStatus() {
        return new ResponseStatus();
    }

    /**
     * Create an instance of {@link AttributeValuePair }
     * 
     * @return 
     */
    public AttributeValuePair createAttributeValuePair() {
        return new AttributeValuePair();
    }

    /**
     * Create an instance of {@link TimePeriod }
     * 
     * @return 
     */
    public TimePeriod createTimePeriod() {
        return new TimePeriod();
    }

    /**
     * Create an instance of {@link URL }
     * 
     * @return 
     */
    public URL createURL() {
        return new URL();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     * @param value
     * @return 
     */
    @XmlElementDecl(namespace = "http://www.ericsson.com/esb/data/generico/CommonTypes/v2/", name = "originSystem", scope = EricssonFaultMessageType.class)
    public JAXBElement<String> createEricssonFaultMessageTypeOriginSystem(String value) {
        return new JAXBElement<String>(_EricssonFaultMessageTypeOriginSystem_QNAME, String.class, EricssonFaultMessageType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateCaseStatusResponseTYPE }{@code >}}
     * 
     * @param value
     * @return 
     */
    @XmlElementDecl(namespace = "http://www.ericsson.com/esb/message/customer/updateCaseStatusResponse/v1.0", name = "updateCaseStatusResponse")
    public JAXBElement<UpdateCaseStatusResponseTYPE> createUpdateCaseStatusResponse(UpdateCaseStatusResponseTYPE value) {
        return new JAXBElement<UpdateCaseStatusResponseTYPE>(_UpdateCaseStatusResponse_QNAME, UpdateCaseStatusResponseTYPE.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateCaseStatusType }{@code >}}
     * 
     * @param value
     * @return 
     */
    @XmlElementDecl(namespace = "http://www.ericsson.com/esb/message/customer/updateCaseStatusRequest/v1.0", name = "updateCaseStatusRequest")
    public JAXBElement<UpdateCaseStatusType> createUpdateCaseStatusRequest(UpdateCaseStatusType value) {
        return new JAXBElement<UpdateCaseStatusType>(_UpdateCaseStatusRequest_QNAME, UpdateCaseStatusType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EricssonFaultMessageType }{@code >}}
     * 
     * @param value
     * @return 
     */
    @XmlElementDecl(namespace = "http://www.ericsson.com/esb/data/generico/CommonTypes/v2/", name = "ericssonFault")
    public JAXBElement<EricssonFaultMessageType> createEricssonFault(EricssonFaultMessageType value) {
        return new JAXBElement<EricssonFaultMessageType>(_EricssonFault_QNAME, EricssonFaultMessageType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HeaderResponseType }{@code >}}
     * 
     * @param value
     * @return 
     */
    @XmlElementDecl(namespace = "http://www.ericsson.com/esb/data/generico/CommonTypes/v2/", name = "headerResponse")
    public JAXBElement<HeaderResponseType> createHeaderResponse(HeaderResponseType value) {
        return new JAXBElement<HeaderResponseType>(_HeaderResponse_QNAME, HeaderResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HeaderRequestType }{@code >}}
     * 
     * @param value
     * @return 
     */
    @XmlElementDecl(namespace = "http://www.ericsson.com/esb/data/generico/CommonTypes/v2/", name = "headerRequest")
    public JAXBElement<HeaderRequestType> createHeaderRequest(HeaderRequestType value) {
        return new JAXBElement<HeaderRequestType>(_HeaderRequest_QNAME, HeaderRequestType.class, null, value);
    }

}
