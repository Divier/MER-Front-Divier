
package com.oracle.xmlns.odi.odiinvoke.dto.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para OdiStartLoadPlanType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="OdiStartLoadPlanType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="StartedRunInformation" type="{xmlns.oracle.com/odi/OdiInvoke/}OdiLoadPlanInstanceRunInformationType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OdiStartLoadPlanType", propOrder = {
    "startedRunInformation"
})
public class OdiStartLoadPlanType {

    @XmlElement(name = "StartedRunInformation", required = true)
    protected OdiLoadPlanInstanceRunInformationType startedRunInformation;

    /**
     * Obtiene el valor de la propiedad startedRunInformation.
     * 
     * @return
     *     possible object is
     *     {@link OdiLoadPlanInstanceRunInformationType }
     *     
     */
    public OdiLoadPlanInstanceRunInformationType getStartedRunInformation() {
        return startedRunInformation;
    }

    /**
     * Define el valor de la propiedad startedRunInformation.
     * 
     * @param value
     *     allowed object is
     *     {@link OdiLoadPlanInstanceRunInformationType }
     *     
     */
    public void setStartedRunInformation(OdiLoadPlanInstanceRunInformationType value) {
        this.startedRunInformation = value;
    }

}
