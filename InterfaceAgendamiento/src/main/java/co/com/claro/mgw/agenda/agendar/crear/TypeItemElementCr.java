/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgw.agenda.agendar.crear;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author bocanegravm
 */
/**
 * <p>
 * Clase Java para TypeCommandElementCr complex type.
 *
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que
 * haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="TypeCommandElementCr"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="date" type="{http://www.w3.org/2001/XMLSchema}
 * string" minOccurs="0"/&gt;
 *         &lt;element name="external_id" type="{http://www.w3.org/2001/XMLSche
 * ma}string" minOccurs="0"/&gt;
 *         &lt;element name="fallback_external_id" type="{http://www.w3.org/2001
 * /XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="appointment" type="{http://www.amx.com.co/SIEC/ETA
 * direct/WS_SIEC_CrearOrdenMGWInbound}TypeAppointmentElementCr" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TypeItemElementCr", propOrder = {
    "resourceId",
    "preferenceType"
})
public class TypeItemElementCr {

    /**
     *
     */
    public TypeItemElementCr() {
    }

    /**
     *
     * @param resourceId
     * @param preferenceType
     */
    public TypeItemElementCr(String resourceId, String preferenceType) {
        this.resourceId = resourceId;
        this.preferenceType = preferenceType;
    }
    /**
     *
     */
    @XmlElement(name = "resourceId")
    protected String resourceId;
    /**
     *
     */
    @XmlElement(name = "preferenceType")
    protected String preferenceType;

    /**
     * Obtiene el valor de la propiedad resourceId.
     *
     * @return possible object is {@link String }
     *
     */
    public String getResourceId() {
        return resourceId;
    }

    /**
     * Define el valor de la propiedad resourceId.
     *
     * @param resourceId allowed object is {@link String }
     *
     */
    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    /**
     * Obtiene el valor de la propiedad preferenceType.
     *
     * @return possible object is {@link String }
     *
     */
    public String getPreferenceType() {
        return preferenceType;
    }

    /**
     * Define el valor de la propiedad preferenceType.
     *
     *
     * @param preferenceType
     */
    public void setPreferenceType(String preferenceType) {
        this.preferenceType = preferenceType;
    }

}
