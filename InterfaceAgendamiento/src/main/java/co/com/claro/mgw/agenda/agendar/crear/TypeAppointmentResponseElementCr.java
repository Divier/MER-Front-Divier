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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Clase Java para TypeAppointmentResponseElementCr complex type.
 *
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que
 * haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="TypeAppointmentResponseElementCr"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="appt_number" type="{http://www.w3.org/2001/XMLSch
 * ema}string" minOccurs="0"/&gt;
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}st
 * ring" minOccurs="0"/&gt;
 *         &lt;element name="customer_number" type="{http://www.w3.org/2001/X
 * MLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="aid" type="{http://www.w3.org/2001/XMLSchema}int"
 * minOccurs="0"/&gt;
 *         &lt;element name="report" type="{http://www.amx.com.co/SIEC/ETAdire
 * ct/WS_SIEC_CrearOrdenMGWInbound}TypeReportElementCr" minOccurs="0"/&gt;
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
    "name",
    "customer_number",
    "aid",
    "inventories",
    "report"
})
public class TypeAppointmentResponseElementCr {

    /**
     *
     */
    @XmlElement(name = "appt_number")
    protected String appt_number;
    /**
     *
     */
    protected String name;
    /**
     *
     */
    @XmlElement(name = "customer_number")
    protected String customer_number;
    /**
     *
     */
    protected Integer aid;
    /**
     *
     */
    protected TypeReportElementCr report;
    /**
     *
     */
    protected TypeInventoriesArrayDataCr inventories;

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
     * Obtiene el valor de la propiedad name.
     *
     * @return possible object is {@link String }
     *
     */
    public String getName() {
        return name;
    }

    /**
     * Define el valor de la propiedad name.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Obtiene el valor de la propiedad customer_number.
     *
     * @return possible object is {@link String }
     *
     */
    public String getCustomer_number() {
        return customer_number;
    }

    /**
     * Define el valor de la propiedad customer_number.
     *
     *
     * @param customer_number
     */
    public void setCustomer_number(String customer_number) {
        this.customer_number = customer_number;
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
     * @return possible object is {@link TypeReportElementCr }
     *
     */
    public TypeReportElementCr getReport() {
        return report;
    }

    /**
     * Define el valor de la propiedad report.
     *
     * @param value allowed object is {@link TypeReportElementCr }
     *
     */
    public void setReport(TypeReportElementCr value) {
        this.report = value;
    }

    /**
     *
     * @return TypeInventoriesArrayDataCr
     */
    public TypeInventoriesArrayDataCr getInventories() {
        return inventories;
    }

    /**
     *
     * @param inventories
     */
    public void setInventories(TypeInventoriesArrayDataCr inventories) {
        this.inventories = inventories;
    }

}
