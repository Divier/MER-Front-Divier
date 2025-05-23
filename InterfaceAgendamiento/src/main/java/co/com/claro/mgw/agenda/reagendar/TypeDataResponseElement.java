/**
 * 
 * Objetivo: Clase data Ws
 * Descripcion: Clase data Ws
 *
 * @author imartipe-everis
 * 
 */
package co.com.claro.mgw.agenda.reagendar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para TypeDataResponseElement complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera
 * que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="TypeDataResponseElement"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="commands" type="{http://www.amx.com.co/SIEC/ET
 * Adirect/WS_SIEC_RealizarReagendacionMGWInbound}TypeCommandsResponseArray"
 * minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TypeDataResponseElement", propOrder =
{
    "commands"
})
public class TypeDataResponseElement
{
    /**
     *
     */
    protected TypeCommandsResponseArray commands;

    /**
     * Obtiene el valor de la propiedad commands.
     * 
     * @return
     *     possible object is
     *     {@link TypeCommandsResponseArray }
     *     
     */
    public TypeCommandsResponseArray getCommands()
    {
        return commands;
    }

    /**
     * Define el valor de la propiedad commands.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeCommandsResponseArray }
     *     
     */
    public void setCommands(TypeCommandsResponseArray value)
    {
        this.commands = value;
    }
}
