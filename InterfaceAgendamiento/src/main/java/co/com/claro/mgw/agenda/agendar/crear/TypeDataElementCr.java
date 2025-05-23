/**
 *
 * Objetivo: Clase data Ws Descripcion: Clase data Ws
 *
 * @author imartipe-everis
 *
 */
package co.com.claro.mgw.agenda.agendar.crear;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Clase Java para TypeDataElementCr complex type.
 *
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera
 * que haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="TypeDataElementCr"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="commands" type="{http://www.amx.com.co/SIEC/ETAdi
 * rect/WS_SIEC_CrearOrdenMGWInbound}TypeCommandsArrayCr" minOccurs="0"/&gt;
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
public class TypeDataElementCr {

    /**
     *
     */
    public TypeDataElementCr() {
    }

    /**
     *
     * @param commands
     */
    public TypeDataElementCr(TypeCommandsArrayCr commands) {
        this.commands = commands;
    }
    /**
     *
     */
    protected TypeCommandsArrayCr commands;

    /**
     * Obtiene el valor de la propiedad commands.
     *
     * @return possible object is {@link TypeCommandsArrayCr }
     *
     */
    public TypeCommandsArrayCr getCommands() {
        return commands;
    }

    /**
     * Define el valor de la propiedad commands.
     *
     * @param value allowed object is {@link TypeCommandsArrayCr }
     *
     */
    public void setCommands(TypeCommandsArrayCr value) {
        this.commands = value;
    }

}
