/**
 * 
 * Objetivo: Clase data Ws
 * Descripcion: Clase data Ws
 *
 * @author imartipe-everis
 * 
 */
package co.com.claro.mgw.agenda.shareddata;

import co.com.claro.mgw.agenda.cancelar.TypeDataResponseElement;
import co.com.claro.mgw.agenda.cancelar.TypeHeadResponseElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Clase Java para MgwTypeCancelResponseElement complex type.
 *
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera
 * que haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="MgwTypeCancelResponseElement"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="user" type="{http://www.amx.com.co/SIEC/ETAdirect/
 * WS_SIEC_CancelarOrdenMGWInbound}MgwUserElement" minOccurs="0"/&gt;
 *         &lt;element name="head" type="{http://www.amx.com.co/SIEC/ETAdirect/
 * WS_SIEC_CancelarOrdenMGWInbound}TypeHeadResponseElement" minOccurs="0"/&gt;
 *         &lt;element name="data" type="{http://www.amx.com.co/SIEC/ETAdirect/
 * WS_SIEC_CancelarOrdenMGWInbound}TypeDataResponseElement" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TypeInboundInterfaceResponseElement", propOrder = {
    "user",
    "head",
    "data",
    "report"
})
public class MgwTypeCancelResponseElement {

    /**
     *
     */
    @XmlElement(name = "user")
    protected MgwUserElement user;
    /**
     *
     */
    @XmlElement(name = "head")
    protected TypeHeadResponseElement head;
    /**
     *
     */
    @XmlElement(name = "data")
    protected TypeDataResponseElement data;
    /**
     *
     */
    @XmlElement(name = "report", required = false)
    protected MgwTypeErrorReportElement report;

    /**
     * Obtiene el valor de la propiedad user.
     *
     * @return possible object is {@link MgwUserElement }
     *
     */
    public MgwUserElement getUser() {
        return user;
    }

    /**
     * Define el valor de la propiedad user.
     *
     * @param value allowed object is {@link MgwUserElement }
     *
     */
    public void setUser(MgwUserElement value) {
        this.user = value;
    }

    /**
     * Obtiene el valor de la propiedad head.
     *
     * @return possible object is {@link TypeHeadResponseElement }
     *
     */
    public TypeHeadResponseElement getHead() {
        return head;
    }

    /**
     * Define el valor de la propiedad head.
     *
     * @param value allowed object is {@link TypeHeadResponseElement }
     *
     */
    public void setHead(TypeHeadResponseElement value) {
        this.head = value;
    }

    /**
     * Obtiene el valor de la propiedad data.
     *
     * @return possible object is {@link TypeDataResponseElement }
     *
     */
    public TypeDataResponseElement getData() {
        return data;
    }

    /**
     * Define el valor de la propiedad data.
     *
     * @param value allowed object is {@link TypeDataResponseElement }
     *
     */
    public void setData(TypeDataResponseElement value) {
        this.data = value;
    }

    /**
     *
     * @return MgwTypeErrorReportElement
     */
    public MgwTypeErrorReportElement getReport() {
        return report;
    }

    /**
     *
     * @param report
     */
    public void setReport(MgwTypeErrorReportElement report) {
        this.report = report;
    }

}
