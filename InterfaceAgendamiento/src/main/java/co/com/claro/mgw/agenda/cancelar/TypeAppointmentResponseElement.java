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
 * <p>
 * Clase Java para TypeAppointmentResponseElement complex type.
 *
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que
 * haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="TypeAppointmentResponseElement"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="appt_number" type="{http://www.w3.org/2001/XMLSche
 * ma}string" minOccurs="0"/&gt;
 *         &lt;element name="aid" type="{http://www.w3.org/2001/XMLSchema}int"
 * minOccurs="0"/&gt;
 *         &lt;element name="report" type="{http://www.amx.com.co/SIEC/ETAdirect
 * /WS_SIEC_CancelarOrdenMGWInbound}TypeReportElement" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TypeAppointmentResponseElement", propOrder = {
    "appt_number",
    "aid",
    "report"
})
public class TypeAppointmentResponseElement {

    /**
     *
     */
    @XmlElement(name = "appt_number")
    protected String appt_number;
    /**
     *
     */
    protected Integer aid;
    /**
     *
     */
    protected TypeReportElement report;

    /**
     * Obtiene el valor de la propiedad appt_number.
     *
     * @return possible object is {@link String }
     *
     */
    public String getAppt_number() {
        return appt_number;
    }

    /**
     * Define el valor de la propiedad appt_number.
     *
     *
     * @param appt_number
     */
    public void setAppt_number(String appt_number) {
        this.appt_number = appt_number;
    }

    /**
     * Obtiene el valor de la propiedad aid.
     *
     * @return possible object is {@link Integer }
     *
     */
    public Integer getAid() {
        return aid;
    }

    /**
     * Define el valor de la propiedad aid.
     *
     * @param value allowed object is {@link Integer }
     *
     */
    public void setAid(Integer value) {
        this.aid = value;
    }

    /**
     * Obtiene el valor de la propiedad report.
     *
     * @return possible object is {@link TypeReportElement }
     *
     */
    public TypeReportElement getReport() {
        return report;
    }

    /**
     * Define el valor de la propiedad report.
     *
     * @param value allowed object is {@link TypeReportElement }
     *
     */
    public void setReport(TypeReportElement value) {
        this.report = value;
    }

}
