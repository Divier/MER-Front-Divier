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
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Clase Java para TypeDataResponseElementCr complex type.
 *
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera
 * que haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="TypeDataResponseElementCr"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="commands" type="{http://www.amx.com.co/SIEC/ETAdir
 * ect/WS_SIEC_CrearOrdenMGWInbound}TypeCommandsResponseArrayCr" minOccurs="0"
 * /&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TypeDataResponseElement", propOrder = {
    "commands"
})
public class TypeDataResponseElementCr {

    /**
     *
     */
    protected TypeCommandsResponseArrayCr commands;

    /**
     * Obtiene el valor de la propiedad commands.
     *
     * @return possible object is {@link TypeCommandsResponseArrayCr }
     *
     */
    public TypeCommandsResponseArrayCr getCommands() {
        return commands;
    }

    /**
     * Define el valor de la propiedad commands.
     *
     * @param value allowed object is {@link TypeCommandsResponseArrayCr }
     *
     */
    public void setCommands(TypeCommandsResponseArrayCr value) {
        this.commands = value;
    }

}
