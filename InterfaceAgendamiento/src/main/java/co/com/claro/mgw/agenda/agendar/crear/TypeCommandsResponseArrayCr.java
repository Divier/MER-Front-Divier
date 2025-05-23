/**
 * 
 * Objetivo: Clase data Ws
 * Descripcion: Clase data Ws
 *
 * @author imartipe-everis
 * 
 */
package co.com.claro.mgw.agenda.agendar.crear;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Clase Java para TypeCommandsResponseArrayCr complex type.
 *
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera
 * que haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="TypeCommandsResponseArrayCr"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="command" type="{http://www.amx.com.co/SIEC/ETAdire
 * ct/WS_SIEC_CrearOrdenMGWInbound}TypeCommandResponseElementCr" maxOccurs=
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
@XmlType(name = "TypeCommandsResponseArray", propOrder = {
    "command"
})
public class TypeCommandsResponseArrayCr {

    /**
     *
     */
    @XmlElement(required = true)
    protected List<TypeCommandResponseElementCr> command;

    /**
     * Gets the value of the command property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the command property.
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
     * {@link TypeCommandResponseElementCr }
     *
     *
     * @return
     */
    public List<TypeCommandResponseElementCr> getCommand() {
        if (command == null) {
            command = new ArrayList();
        }

        return this.command;
    }

    /**
     *
     * @param c
     */
    public void add(TypeCommandResponseElementCr c) {
        if (command == null) {
            command = new ArrayList();
            command.add(c);
        } else {
            command.add(c);
        }
    }

}
