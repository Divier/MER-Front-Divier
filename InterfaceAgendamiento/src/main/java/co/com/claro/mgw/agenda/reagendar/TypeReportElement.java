/**
 * 
 * Objetivo: Clase data Ws
 * Descripcion: Clase data Ws
 *
 * @author imartipe-everis
 * 
 */
package co.com.claro.mgw.agenda.reagendar;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element name="message" type="{http://www.amx.com.co/SIEC/ETAdirec
 * t/WS_SIEC_RealizarReagendacionMGWInbound}TypeMessageResponseElement"
 * maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TypeReportElement", propOrder =
{
    "message"
})
public class TypeReportElement
{
    /**
     *
     */
    @XmlElement(required = true)
    protected List<TypeMessageResponseElement> message;

    /**
     * Gets the value of the message property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the message property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMessage().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TypeMessageResponseElement }
     * 
     * 
     * @return 
     */
    public List<TypeMessageResponseElement> getMessage()
    {
        if (message == null)
        {
            message = new ArrayList();
        }
        return this.message;
    }

    /**
     * 
     * @param c 
     */
    public void addCommand(TypeMessageResponseElement c)
    {
        if (message == null)
        {
            message = new ArrayList();
            message.add(c);
        }
        else
        {
            message.add(c);
        }
    }
}
