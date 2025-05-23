/**
 * 
 * Objetivo: Clase data Ws
 * Descripcion: Clase data Ws
 *
 * @author imartipe-everis
 * 
 */
package co.com.claro.mgw.agenda.shareddata;

import co.com.claro.mgw.agenda.agendar.crear.TypeMessageResponseElementCr;
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
@XmlType(name = "TypeReportElementError", propOrder =
{
    "message"
})
public class MgwTypeErrorReportElement
{
    /**
     * 
     */
    public MgwTypeErrorReportElement()
    {}

    /**
     * 
     * @param message 
     */
    public MgwTypeErrorReportElement(TypeMessageResponseElementCr message)
    {
        this.message = message;
    }

    /**
     *
     */
    @XmlElement(name = "message", required = false)
    protected TypeMessageResponseElementCr message;

    /**
     * 
     * @return TypeMessageResponseElementCr
     */
    public TypeMessageResponseElementCr getMessage()
    {
        return message;
    }

    /**
     * 
     * @param message 
     */
    public void setMessage(TypeMessageResponseElementCr message)
    {
        this.message = message;
    }
}
