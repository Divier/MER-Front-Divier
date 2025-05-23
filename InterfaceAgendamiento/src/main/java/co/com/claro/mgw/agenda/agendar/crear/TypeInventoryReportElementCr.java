/**
 * 
 * Objetivo: Clase data Ws
 * Descripcion: Clase data Ws
 *
 * @author imartipe-everis
 * 
 */
package co.com.claro.mgw.agenda.agendar.crear;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Clase Java para TypeReportElement complex type.
 *
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera
 * que haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="TypeReportElement"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="message" type="{http://www.amx.com.co/SIEC/ETAdire
 * ct/WS_SIEC_CrearOrdenMGWInbound}TypeReportMessageElementCr" maxOccurs=
 * "unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TypeInventoryReportElement", propOrder = {
    "report"
})
public class TypeInventoryReportElementCr {

    /**
     *
     */
    @XmlElement(required = true)
    protected TypeReportMessageElementCr report;

    /**
     *
     * @return TypeReportMessageElementCr
     */
    public TypeReportMessageElementCr getMessage() {
        return report;
    }

    /**
     *
     * @param message
     */
    public void setMessage(TypeReportMessageElementCr message) {
        this.report = message;
    }

}
