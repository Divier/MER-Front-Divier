
package co.com.telmex.catastro.services.wservicelocal;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the co.com.telmex.catastro.services.wservicelocal package. 
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

    private final static QName _EnrichAssistResponse_QNAME = new QName("http://service.sitidata.encapsular.claro.com.co/", "enrichAssistResponse");
    private final static QName _EnrichResponse_QNAME = new QName("http://service.sitidata.encapsular.claro.com.co/", "enrichResponse");
    private final static QName _EnrichXmlBatch_QNAME = new QName("http://service.sitidata.encapsular.claro.com.co/", "enrichXmlBatch");
    private final static QName _EnrichXmlBatchResponse_QNAME = new QName("http://service.sitidata.encapsular.claro.com.co/", "enrichXmlBatchResponse");
    private final static QName _Enrich_QNAME = new QName("http://service.sitidata.encapsular.claro.com.co/", "enrich");
    private final static QName _EnrichAssist_QNAME = new QName("http://service.sitidata.encapsular.claro.com.co/", "enrichAssist");
    private final static QName _Return_QNAME = new QName("http://service.sitidata.encapsular.claro.com.co/", "return");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: co.com.telmex.catastro.services.wservicelocal
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ReturnDto }
     * 
     * @return 
     */
    public ReturnDto createReturnDto() {
        return new ReturnDto();
    }

    /**
     * Create an instance of {@link EnrichAssist }
     * 
     * @return 
     */
    public EnrichAssist createEnrichAssist() {
        return new EnrichAssist();
    }

    /**
     * Create an instance of {@link Enrich }
     * 
     * @return 
     */
    public Enrich createEnrich() {
        return new Enrich();
    }

    /**
     * Create an instance of {@link EnrichXmlBatchResponse }
     * 
     * @return 
     */
    public EnrichXmlBatchResponse createEnrichXmlBatchResponse() {
        return new EnrichXmlBatchResponse();
    }

    /**
     * Create an instance of {@link EnrichXmlBatch }
     * 
     * @return 
     */
    public EnrichXmlBatch createEnrichXmlBatch() {
        return new EnrichXmlBatch();
    }

    /**
     * Create an instance of {@link EnrichResponse }
     * 
     * @return 
     */
    public EnrichResponse createEnrichResponse() {
        return new EnrichResponse();
    }

    /**
     * Create an instance of {@link EnrichAssistResponse }
     * 
     * @return 
     */
    public EnrichAssistResponse createEnrichAssistResponse() {
        return new EnrichAssistResponse();
    }

    /**
     * Create an instance of {@link ReturnDto.Geo }
     * 
     * @return 
     */
    public ReturnDto.Geo createReturnDtoGeo() {
        return new ReturnDto.Geo();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EnrichAssistResponse }{@code >}}
     * 
     * @return 
     */
    @XmlElementDecl(namespace = "http://service.sitidata.encapsular.claro.com.co/", name = "enrichAssistResponse")
    public JAXBElement<EnrichAssistResponse> createEnrichAssistResponse(EnrichAssistResponse value) {
        return new JAXBElement<EnrichAssistResponse>(_EnrichAssistResponse_QNAME, EnrichAssistResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EnrichResponse }{@code >}}
     * 
     * @return 
     */
    @XmlElementDecl(namespace = "http://service.sitidata.encapsular.claro.com.co/", name = "enrichResponse")
    public JAXBElement<EnrichResponse> createEnrichResponse(EnrichResponse value) {
        return new JAXBElement<EnrichResponse>(_EnrichResponse_QNAME, EnrichResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EnrichXmlBatch }{@code >}}
     * 
     * @return 
     */
    @XmlElementDecl(namespace = "http://service.sitidata.encapsular.claro.com.co/", name = "enrichXmlBatch")
    public JAXBElement<EnrichXmlBatch> createEnrichXmlBatch(EnrichXmlBatch value) {
        return new JAXBElement<EnrichXmlBatch>(_EnrichXmlBatch_QNAME, EnrichXmlBatch.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EnrichXmlBatchResponse }{@code >}}
     * 
     * @return 
     */
    @XmlElementDecl(namespace = "http://service.sitidata.encapsular.claro.com.co/", name = "enrichXmlBatchResponse")
    public JAXBElement<EnrichXmlBatchResponse> createEnrichXmlBatchResponse(EnrichXmlBatchResponse value) {
        return new JAXBElement<EnrichXmlBatchResponse>(_EnrichXmlBatchResponse_QNAME, EnrichXmlBatchResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Enrich }{@code >}}
     * 
     * @return 
     */
    @XmlElementDecl(namespace = "http://service.sitidata.encapsular.claro.com.co/", name = "enrich")
    public JAXBElement<Enrich> createEnrich(Enrich value) {
        return new JAXBElement<Enrich>(_Enrich_QNAME, Enrich.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EnrichAssist }{@code >}}
     * 
     * @return 
     */
    @XmlElementDecl(namespace = "http://service.sitidata.encapsular.claro.com.co/", name = "enrichAssist")
    public JAXBElement<EnrichAssist> createEnrichAssist(EnrichAssist value) {
        return new JAXBElement<EnrichAssist>(_EnrichAssist_QNAME, EnrichAssist.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReturnDto }{@code >}}
     * 
     * @return 
     */
    @XmlElementDecl(namespace = "http://service.sitidata.encapsular.claro.com.co/", name = "return")
    public JAXBElement<ReturnDto> createReturn(ReturnDto value) {
        return new JAXBElement<ReturnDto>(_Return_QNAME, ReturnDto.class, null, value);
    }

}
