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
 * <p>Clase Java para TypeReportElementCr complex type.
 *
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera
 * que haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="TypeReportElementCr"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="message" type="{http://www.amx.com.co/SIEC/ETAdire
 * ct/WS_SIEC_CrearOrdenMGWInbound}TypeReportMessageElement" maxOccurs=
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
@XmlType(name = "TypeReportElement", propOrder = {
    "message"
})
public class TypeReportElementCr {

    /**
     *
     */
    @XmlElement(required = true)
    protected TypeMessageResponseElementCr message;

    /**
     *
     * @return TypeMessageResponseElementCr
     */
    public TypeMessageResponseElementCr getMessage() {
        return message;
    }

    /**
     *
     * @param message
     */
    public void setMessage(TypeMessageResponseElementCr message) {
        this.message = message;
    }

}
