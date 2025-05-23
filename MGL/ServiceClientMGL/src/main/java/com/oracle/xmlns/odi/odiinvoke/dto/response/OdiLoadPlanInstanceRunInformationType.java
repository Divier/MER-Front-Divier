
package com.oracle.xmlns.odi.odiinvoke.dto.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para OdiLoadPlanInstanceRunInformationType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="OdiLoadPlanInstanceRunInformationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OdiLoadPlanInstanceId" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="RunCount" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="MasterRepositoryId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="MasterRepositoryTimestamp" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OdiLoadPlanInstanceRunInformationType", propOrder = {
    "odiLoadPlanInstanceId",
    "runCount",
    "masterRepositoryId",
    "masterRepositoryTimestamp"
})
public class OdiLoadPlanInstanceRunInformationType {

    @XmlElement(name = "OdiLoadPlanInstanceId")
    protected long odiLoadPlanInstanceId;
    @XmlElement(name = "RunCount")
    protected long runCount;
    @XmlElement(name = "MasterRepositoryId")
    protected int masterRepositoryId;
    @XmlElement(name = "MasterRepositoryTimestamp", required = true)
    protected String masterRepositoryTimestamp;

    /**
     * Obtiene el valor de la propiedad odiLoadPlanInstanceId.
     * 
     */
    public long getOdiLoadPlanInstanceId() {
        return odiLoadPlanInstanceId;
    }

    /**
     * Define el valor de la propiedad odiLoadPlanInstanceId.
     * 
     */
    public void setOdiLoadPlanInstanceId(long value) {
        this.odiLoadPlanInstanceId = value;
    }

    /**
     * Obtiene el valor de la propiedad runCount.
     * 
     */
    public long getRunCount() {
        return runCount;
    }

    /**
     * Define el valor de la propiedad runCount.
     * 
     */
    public void setRunCount(long value) {
        this.runCount = value;
    }

    /**
     * Obtiene el valor de la propiedad masterRepositoryId.
     * 
     */
    public int getMasterRepositoryId() {
        return masterRepositoryId;
    }

    /**
     * Define el valor de la propiedad masterRepositoryId.
     * 
     */
    public void setMasterRepositoryId(int value) {
        this.masterRepositoryId = value;
    }

    /**
     * Obtiene el valor de la propiedad masterRepositoryTimestamp.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMasterRepositoryTimestamp() {
        return masterRepositoryTimestamp;
    }

    /**
     * Define el valor de la propiedad masterRepositoryTimestamp.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMasterRepositoryTimestamp(String value) {
        this.masterRepositoryTimestamp = value;
    }

}
