
package net.telmex.unit.factory;

import net.telmex.unit.dto.BasicMessage;
import net.telmex.unit.dto.ModifyResponse;
import net.telmex.unit.exceptions.PcmlException;
import net.telmex.unit.implement.ModifyUnit;
import net.telmex.unit.implement.ModifyUnitRequest;
import net.telmex.unit.implement.ModifyUnitResponse;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the net.telmex.unit package. 
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

    private final static QName _ModifyUnitResponse_QNAME = new QName("http://unit.telmex.net/", "modifyUnitResponse");
    private final static QName _PcmlException_QNAME = new QName("http://unit.telmex.net/", "PcmlException");
    private final static QName _ModifyUnit_QNAME = new QName("http://unit.telmex.net/", "modifyUnit");
    
    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: net.telmex.unit
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ModifyUnitResponse }
     * 
     */
    public ModifyUnitResponse createModifyUnitResponse() {
        return new ModifyUnitResponse();
    }

    /**
     * Create an instance of {@link ModifyUnit }
     * 
     */
    public ModifyUnit createModifyUnit() {
        return new ModifyUnit();
    }

    /**
     * Create an instance of {@link PcmlException }
     * 
     */
    public PcmlException createPcmlException() {
        return new PcmlException();
    }

    /**
     * Create an instance of {@link ModifyResponse }
     * 
     */
    public ModifyResponse createModifyResponse() {
        return new ModifyResponse();
    }

    /**
     * Create an instance of {@link BasicMessage }
     * 
     */
    public BasicMessage createBasicMessage() {
        return new BasicMessage();
    }

    /**
     * Create an instance of {@link ModifyUnitRequest }
     * 
     */
    public ModifyUnitRequest createModifyUnitRequest() {
        return new ModifyUnitRequest();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyUnitResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://unit.telmex.net/", name = "modifyUnitResponse")
    public JAXBElement<ModifyUnitResponse> createModifyUnitResponse(ModifyUnitResponse value) {
        return new JAXBElement<ModifyUnitResponse>(_ModifyUnitResponse_QNAME, ModifyUnitResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PcmlException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://unit.telmex.net/", name = "PcmlException")
    public JAXBElement<PcmlException> createPcmlException(PcmlException value) {
        return new JAXBElement<PcmlException>(_PcmlException_QNAME, PcmlException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyUnit }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://unit.telmex.net/", name = "modifyUnit")
    public JAXBElement<ModifyUnit> createModifyUnit(ModifyUnit value) {
        return new JAXBElement<ModifyUnit>(_ModifyUnit_QNAME, ModifyUnit.class, null, value);
    }

}
