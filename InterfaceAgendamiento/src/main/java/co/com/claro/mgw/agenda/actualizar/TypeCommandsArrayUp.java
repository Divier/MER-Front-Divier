/**
 * 
 * Objetivo: Clase data Ws
 * Descripcion: Clase data Ws
 *
 * @author imartipe-everis
 * 
 */
package co.com.claro.mgw.agenda.actualizar;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para TypeCommandsArray complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera
 * que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="TypeCommandsArray"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="command" type="{http://www.amx.com.co/SIEC/ETAdire
 * ct/WS_SIEC_RealizarReagendacionMGWInbound}TypeCommandElement" maxOccurs=
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
@XmlType(name = "TypeCommandsArray", propOrder =
{
    "command"
})
public class TypeCommandsArrayUp
{
    /**
     *
     */
    @XmlElement(required = true)
    protected List<TypeCommandElementUp> command;
    /**
     * 
     * @param command 
     */
    public TypeCommandsArrayUp(List<TypeCommandElementUp> command)
    {
        this.command = command;
    }

    /**
     * 
     */
    public TypeCommandsArrayUp()
    {}


    /**
     * Gets the value of the command property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the command
     * property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCommand().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TypeCommandElement }
     * 
     * 
     * @return 
     */
    public List<TypeCommandElementUp> getCommand()
    {
        if (command == null)
        {
            command = new ArrayList();
        }
        return this.command;
    }
    
    /**
     * 
     * @param c 
     */
    public void add(TypeCommandElementUp c)
    {
        if (command == null)
        {
            command = new ArrayList();
            command.add(c);
        }
        else
        {
            command.add(c);
        }
    }
}
