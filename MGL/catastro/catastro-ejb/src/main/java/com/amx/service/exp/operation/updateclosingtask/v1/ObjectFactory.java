
package com.amx.service.exp.operation.updateclosingtask.v1;

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
 * generated in the com.amx.service.exp.operation.updateclosingtask.v1 package. 
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

    private final static QName _HeaderResponse_QNAME = new QName("http://www.amx.com/Service/exp/Operation/updateClosingTask/v1.0", "headerResponse");
    private final static QName _HeaderRequest_QNAME = new QName("http://www.amx.com/Service/exp/Operation/updateClosingTask/v1.0", "headerRequest");
    private final static QName _UpdateClosingTaskResponse_QNAME = new QName("http://www.amx.com/Service/exp/Operation/updateClosingTask/v1.0", "updateClosingTaskResponse");
    private final static QName _Fault_QNAME = new QName("http://www.amx.com/Service/exp/Operation/updateClosingTask/v1.0", "fault");
    private final static QName _UpdateClosingTaskRequest_QNAME = new QName("http://www.amx.com/Service/exp/Operation/updateClosingTask/v1.0", "updateClosingTaskRequest");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.amx.service.exp.operation.updateclosingtask.v1
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link UpdateClosingTaskResponse }
     * 
     */
    public UpdateClosingTaskResponse createUpdateClosingTaskResponse() {
        return new UpdateClosingTaskResponse();
    }

    /**
     * Create an instance of {@link UpdateClosingTaskRequest }
     * 
     */
    public UpdateClosingTaskRequest createUpdateClosingTaskRequest() {
        return new UpdateClosingTaskRequest();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HeaderResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.amx.com/Service/exp/Operation/updateClosingTask/v1.0", name = "headerResponse")
    public JAXBElement<HeaderResponse> createHeaderResponse(HeaderResponse value) {
        return new JAXBElement<HeaderResponse>(_HeaderResponse_QNAME, HeaderResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HeaderRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.amx.com/Service/exp/Operation/updateClosingTask/v1.0", name = "headerRequest")
    public JAXBElement<HeaderRequest> createHeaderRequest(HeaderRequest value) {
        return new JAXBElement<HeaderRequest>(_HeaderRequest_QNAME, HeaderRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateClosingTaskResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.amx.com/Service/exp/Operation/updateClosingTask/v1.0", name = "updateClosingTaskResponse")
    public JAXBElement<UpdateClosingTaskResponse> createUpdateClosingTaskResponse(UpdateClosingTaskResponse value) {
        return new JAXBElement<UpdateClosingTaskResponse>(_UpdateClosingTaskResponse_QNAME, UpdateClosingTaskResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FaultMessageType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.amx.com/Service/exp/Operation/updateClosingTask/v1.0", name = "fault")
    public JAXBElement<FaultMessageType> createFault(FaultMessageType value) {
        return new JAXBElement<FaultMessageType>(_Fault_QNAME, FaultMessageType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateClosingTaskRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.amx.com/Service/exp/Operation/updateClosingTask/v1.0", name = "updateClosingTaskRequest")
    public JAXBElement<UpdateClosingTaskRequest> createUpdateClosingTaskRequest(UpdateClosingTaskRequest value) {
        return new JAXBElement<UpdateClosingTaskRequest>(_UpdateClosingTaskRequest_QNAME, UpdateClosingTaskRequest.class, null, value);
    }

}
