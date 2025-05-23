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
 * <p>Clase Java para TypeReportMessageElementCr complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera
 * que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="TypeReportMessageElementCr"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="message" type="{http://www.amx.com.co/SIEC/ETAdire
 ct/WS_SIEC_CrearOrdenMGWInbound}TypeMessageResponseElementCr"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TypeReportMessageElement", propOrder =
{
    "message"
})
public class TypeReportMessageElementCr
{
    /**
     *
     */
    @XmlElement(required = true)
    protected TypeMessageResponseElementCr message;

    /**
     * Obtiene el valor de la propiedad message.
     * 
     * @return
     *     possible object is
     *     {@link TypeMessageResponseElementCr }
     *     
     */
    public TypeMessageResponseElementCr getMessage()
    {
        return message;
    }

    /**
     * Define el valor de la propiedad message.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeMessageResponseElementCr }
     *     
     */
    public void setMessage(TypeMessageResponseElementCr value)
    {
        this.message = value;
    }
}
