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
 * <p>Clase Java para TypeCommandElement complex type.
 *
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera
 * que haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="TypeCommandElement"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="appointment" type="{http://www.amx.com.co/SIEC/ETA
 * direct/WS_SIEC_CancelarOrdenMGWInbound}TypeAppointmentElement" minOccurs="0"
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
@XmlType(name = "TypeCommandElement", propOrder = {
    "appointment"
})
public class TypeCommandElement {

    /**
     *
     */
    public TypeCommandElement() {
    }

    /**
     *
     * @param appointment
     */
    public TypeCommandElement(TypeAppointmentElement appointment) {
        this.appointment = appointment;
    }
    /**
     *
     */
    protected TypeAppointmentElement appointment;

    /**
     * Obtiene el valor de la propiedad appointment.
     *
     * @return possible object is {@link TypeAppointmentElement }
     *
     */
    public TypeAppointmentElement getAppointment() {
        return appointment;
    }

    /**
     * Define el valor de la propiedad appointment.
     *
     * @param value allowed object is {@link TypeAppointmentElement }
     *
     */
    public void setAppointment(TypeAppointmentElement value) {
        this.appointment = value;
    }

}
