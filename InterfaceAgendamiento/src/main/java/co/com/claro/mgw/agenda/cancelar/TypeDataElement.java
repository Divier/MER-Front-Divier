/**
 * 
 * Objetivo: Clase data Ws
 * Descripcion: Clase data Ws
 *
 * @author imartipe-everis
 * 
 */
package co.com.claro.mgw.agenda.cancelar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Clase Java para TypeDataElement complex type.
 *
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera
 * que haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="TypeDataElement"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="commands" type="{http://www.amx.com.co/SIEC/ETAdir
 * ect/WS_SIEC_CancelarOrdenMGWInbound}TypeCommandsArray" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TypeDataElement", propOrder = {
    "commands"
})
public class TypeDataElement {

    /**
     *
     */
    public TypeDataElement() {
    }

    /**
     *
     * @param commands
     */
    public TypeDataElement(TypeCommandsArray commands) {
        this.commands = commands;
    }
    /**
     *
     */
    protected TypeCommandsArray commands;

    /**
     * Obtiene el valor de la propiedad commands.
     *
     * @return possible object is {@link TypeCommandsArray }
     *
     */
    public TypeCommandsArray getCommands() {
        return commands;
    }

    /**
     * Define el valor de la propiedad commands.
     *
     * @param value allowed object is {@link TypeCommandsArray }
     *
     */
    public void setCommands(TypeCommandsArray value) {
        this.commands = value;
    }

}
