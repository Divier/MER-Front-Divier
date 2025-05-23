
package com.oracle.xmlns.odi.odiinvoke.dto.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Credentials" type="{xmlns.oracle.com/odi/OdiInvoke/}OdiCredentialType"/>
 *         &lt;element name="StartLoadPlanRequest" type="{xmlns.oracle.com/odi/OdiInvoke/}StartLoadPlanRequestType"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {

})
@XmlRootElement(name = "OdiStartLoadPlanRequest")
public class OdiStartLoadPlanRequest {

    @XmlElement(name = "Credentials", required = true)
    protected OdiCredentialType credentials;
    @XmlElement(name = "StartLoadPlanRequest", required = true)
    protected StartLoadPlanRequestType startLoadPlanRequest;

    /**
     * Obtiene el valor de la propiedad credentials.
     * 
     * @return
     *     possible object is
     *     {@link OdiCredentialType }
     *     
     */
    public OdiCredentialType getCredentials() {
        return credentials;
    }

    /**
     * Define el valor de la propiedad credentials.
     * 
     * @param value
     *     allowed object is
     *     {@link OdiCredentialType }
     *     
     */
    public void setCredentials(OdiCredentialType value) {
        this.credentials = value;
    }

    /**
     * Obtiene el valor de la propiedad startLoadPlanRequest.
     * 
     * @return
     *     possible object is
     *     {@link StartLoadPlanRequestType }
     *     
     */
    public StartLoadPlanRequestType getStartLoadPlanRequest() {
        return startLoadPlanRequest;
    }

    /**
     * Define el valor de la propiedad startLoadPlanRequest.
     * 
     * @param value
     *     allowed object is
     *     {@link StartLoadPlanRequestType }
     *     
     */
    public void setStartLoadPlanRequest(StartLoadPlanRequestType value) {
        this.startLoadPlanRequest = value;
    }

}
