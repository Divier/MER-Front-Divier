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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Clase Java para TypeCommandResponseElement complex type.
 *
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera
 * que haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="TypeCommandResponseElement"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="date" type="{http://www.w3.org/2001/XMLSchema}
 * string" minOccurs="0"/&gt;
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}
 * string"/&gt;
 *         &lt;element name="appointment" type="{http://www.amx.com.co/SIEC/
 * ETAdirect/WS_SIEC_CancelarOrdenMGWInbound}TypeAppointmentResponseElement"
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
@XmlType(name = "TypeCommandResponseElement", propOrder = {
    "date",
    "type",
    "appointment"
})
public class TypeCommandResponseElement {

    /**
     *
     */
    protected String date;
    /**
     *
     */
    @XmlElement(required = true)
    protected String type;
    /**
     *
     */
    protected TypeAppointmentResponseElement appointment;

    /**
     * Obtiene el valor de la propiedad date.
     *
     * @return possible object is {@link String }
     *
     */
    public String getDate() {
        return date;
    }

    /**
     * Define el valor de la propiedad date.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setDate(String value) {
        this.date = value;
    }

    /**
     * Obtiene el valor de la propiedad type.
     *
     * @return possible object is {@link String }
     *
     */
    public String getType() {
        return type;
    }

    /**
     * Define el valor de la propiedad type.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Obtiene el valor de la propiedad appointment.
     *
     * @return possible object is {@link TypeAppointmentResponseElement }
     *
     */
    public TypeAppointmentResponseElement getAppointment() {
        return appointment;
    }

    /**
     * Define el valor de la propiedad appointment.
     *
     * @param value allowed object is {@link TypeAppointmentResponseElement }
     *
     */
    public void setAppointment(TypeAppointmentResponseElement value) {
        this.appointment = value;
    }

}
