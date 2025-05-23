
package co.com.claro.wsresourcehomepass.factory;

import co.com.claro.wsresourcehomepass.dto.Clftthr04Response;
import co.com.claro.wsresourcehomepass.dto.CurDirAndStatusUnit;
import co.com.claro.wsresourcehomepass.dto.CurFraudMarking;
import co.com.claro.wsresourcehomepass.dto.ValidateReactivationResourceResponseDto;
import co.com.claro.wsresourcehomepass.headers.HeaderError;
import co.com.claro.wsresourcehomepass.headers.HeaderRequest;
import co.com.claro.wsresourcehomepass.headers.HeaderResponse;
import co.com.claro.wsresourcehomepass.implement.ValidateReactivationResource;
import co.com.claro.wsresourcehomepass.implement.ValidateReactivationResourceRequest;
import co.com.claro.wsresourcehomepass.implement.ValidateReactivationResourceResponse;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the co.com.claro.wsresourcehomepass.implement package. 
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

    private final static QName _ValidateReactivationResource_QNAME = new QName("http://implement.wsresourcehomepass.claro.com.co/", "ValidateReactivationResource");
    private final static QName _HeaderResponse_QNAME = new QName("http://implement.wsresourcehomepass.claro.com.co/", "headerResponse");
    private final static QName _HeaderRequest_QNAME = new QName("http://implement.wsresourcehomepass.claro.com.co/", "headerRequest");
    private final static QName _ValidateReactivationResourceResponse_QNAME = new QName("http://implement.wsresourcehomepass.claro.com.co/", "ValidateReactivationResourceResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: co.com.claro.wsresourcehomepass.implement
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ValidateReactivationResourceResponse }
     * 
     */
    public ValidateReactivationResourceResponse createValidateReactivationResourceResponse() {
        return new ValidateReactivationResourceResponse();
    }

    /**
     * Create an instance of {@link HeaderRequest }
     * 
     */
    public HeaderRequest createHeaderRequest() {
        return new HeaderRequest();
    }

    /**
     * Create an instance of {@link ValidateReactivationResource }
     * 
     */
    public ValidateReactivationResource createValidateReactivationResource() {
        return new ValidateReactivationResource();
    }

    /**
     * Create an instance of {@link HeaderResponse }
     * 
     */
    public HeaderResponse createHeaderResponse() {
        return new HeaderResponse();
    }


    /**
     * Create an instance of {@link CurDirAndStatusUnit }
     * 
     */
    public CurDirAndStatusUnit createCurDirAndStatusUnit() {
        return new CurDirAndStatusUnit();
    }

    /**
     * Create an instance of {@link ValidateReactivationResourceRequest }
     * 
     */
    public ValidateReactivationResourceRequest createValidateReactivationResourceRequest() {
        return new ValidateReactivationResourceRequest();
    }

    /**
     * Create an instance of {@link Clftthr04Response }
     * 
     */
    public Clftthr04Response createClftthr04Response() {
        return new Clftthr04Response();
    }

    /**
     * Create an instance of {@link ValidateReactivationResourceResponseDto }
     * 
     */
    public ValidateReactivationResourceResponseDto createValidateReactivationResourceResponseDto() {
        return new ValidateReactivationResourceResponseDto();
    }

    /**
     * Create an instance of {@link CurFraudMarking }
     * 
     */
    public CurFraudMarking createCurFraudMarking() {
        return new CurFraudMarking();
    }

    /**
     * Create an instance of {@link HeaderError }
     * 
     */
    public HeaderError createHeaderError() {
        return new HeaderError();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateReactivationResource }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://implement.wsresourcehomepass.claro.com.co/", name = "ValidateReactivationResource")
    public JAXBElement<ValidateReactivationResource> createValidateReactivationResource(ValidateReactivationResource value) {
        return new JAXBElement<ValidateReactivationResource>(_ValidateReactivationResource_QNAME, ValidateReactivationResource.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HeaderResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://implement.wsresourcehomepass.claro.com.co/", name = "headerResponse")
    public JAXBElement<HeaderResponse> createHeaderResponse(HeaderResponse value) {
        return new JAXBElement<HeaderResponse>(_HeaderResponse_QNAME, HeaderResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HeaderRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://implement.wsresourcehomepass.claro.com.co/", name = "headerRequest")
    public JAXBElement<HeaderRequest> createHeaderRequest(HeaderRequest value) {
        return new JAXBElement<HeaderRequest>(_HeaderRequest_QNAME, HeaderRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateReactivationResourceResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://implement.wsresourcehomepass.claro.com.co/", name = "ValidateReactivationResourceResponse")
    public JAXBElement<ValidateReactivationResourceResponse> createValidateReactivationResourceResponse(ValidateReactivationResourceResponse value) {
        return new JAXBElement<ValidateReactivationResourceResponse>(_ValidateReactivationResourceResponse_QNAME, ValidateReactivationResourceResponse.class, null, value);
    }

}
