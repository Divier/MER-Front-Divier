
package co.claro.wcc.schema.schemaoperations;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the co.claro.wcc.schema.schemaoperations package. 
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

    private final static QName _DocumentRequest_QNAME = new QName("http://claro.co/wcc/schema/SchemaOperations", "documentRequest");
    private final static QName _DocumentResponse_QNAME = new QName("http://claro.co/wcc/schema/SchemaOperations", "documentResponse");
    private final static QName _FaultResponse_QNAME = new QName("http://claro.co/wcc/schema/SchemaOperations", "faultResponse");
    private final static QName _DocumentFileRequest_QNAME = new QName("http://claro.co/wcc/schema/SchemaOperations", "documentFileRequest");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: co.claro.wcc.schema.schemaoperations
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ExceptionType }
     * 
     */
    public ExceptionType createExceptionType() {
        return new ExceptionType();
    }

    /**
     * Create an instance of {@link FaultType }
     * 
     */
    public FaultType createFaultType() {
        return new FaultType();
    }

    /**
     * Create an instance of {@link ResponseType }
     * 
     */
    public ResponseType createResponseType() {
        return new ResponseType();
    }

    /**
     * Create an instance of {@link FileRequestType }
     * 
     */
    public FileRequestType createFileRequestType() {
        return new FileRequestType();
    }

    /**
     * Create an instance of {@link RequestType }
     * 
     */
    public RequestType createRequestType() {
        return new RequestType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://claro.co/wcc/schema/SchemaOperations", name = "documentRequest")
    public JAXBElement<RequestType> createDocumentRequest(RequestType value) {
        return new JAXBElement<RequestType>(_DocumentRequest_QNAME, RequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://claro.co/wcc/schema/SchemaOperations", name = "documentResponse")
    public JAXBElement<ResponseType> createDocumentResponse(ResponseType value) {
        return new JAXBElement<ResponseType>(_DocumentResponse_QNAME, ResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FaultType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://claro.co/wcc/schema/SchemaOperations", name = "faultResponse")
    public JAXBElement<FaultType> createFaultResponse(FaultType value) {
        return new JAXBElement<FaultType>(_FaultResponse_QNAME, FaultType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FileRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://claro.co/wcc/schema/SchemaOperations", name = "documentFileRequest")
    public JAXBElement<FileRequestType> createDocumentFileRequest(FileRequestType value) {
        return new JAXBElement<FileRequestType>(_DocumentFileRequest_QNAME, FileRequestType.class, null, value);
    }

}
