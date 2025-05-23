/**
 * 
 * Objetivo: Clase data Ws
 * Descripcion: Clase data Ws
 *
 * @author imartipe-everis
 * 
 */
package co.com.claro.mgw.agenda.actualizar;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *         &lt;element name="date" type="{http://www.w3.org/2001/XMLSchema}
 * string" minOccurs="0"/&gt;
 *         &lt;element name="external_id"
 * type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="appointment"
 * type="{http://www.amx.com.co/SIEC/ETAdirect/WS_SIEC_RealizarReagendacionMGW
 * Inbound}TypeAppointmentElement" minOccurs="0"/&gt;
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
    "date",
    "external_id",
    "appointment"
})
public class TypeCommandElementUp
{
    /**
     * 
     */
    public TypeCommandElementUp()
    {}

    /**
     * 
     * @param date
     * @param externalId
     * @param appointment 
     */
    public TypeCommandElementUp(String date, String externalId, TypeAppointmentElementUp appointment)
    {
        this.date = date;
        this.external_id = externalId;
        this.appointment = appointment;
    }

    /**
     *
     */
    protected String date;

    /**
     *
     */
    @XmlElement(name = "external_id")
    protected String external_id;

    /**
     *
     */
    protected TypeAppointmentElementUp appointment;

    /**
     * Obtiene el valor de la propiedad date.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDate()
	{
        return date;
    }

    /**
     * Define el valor de la propiedad date.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDate(String value)
	{
        this.date = value;
    }

    /**
     * Obtiene el valor de la propiedad external_id.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExternal_id()  
	{
        return external_id;
    }

    /**
     * Define el valor de la propiedad external_id.
     * 
     *     
     * @param external_id
     */
    public void setExternal_id(String external_id)    
	{
        this.external_id = external_id;
    }

    /**
     * Obtiene el valor de la propiedad appointment.
     * 
     * @return
     *     possible object is
     *     {@link TypeAppointmentElement }
     *     
     */
    public TypeAppointmentElementUp getAppointment() {
        return appointment;
    }

    /**
     * Define el valor de la propiedad appointment.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeAppointmentElement }
     *     
     */
    public void setAppointment(TypeAppointmentElementUp value)
	{
        this.appointment = value;
    }
}
