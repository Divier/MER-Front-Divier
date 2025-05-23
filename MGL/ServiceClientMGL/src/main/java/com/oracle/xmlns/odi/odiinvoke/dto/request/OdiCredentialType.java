
package com.oracle.xmlns.odi.odiinvoke.dto.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para OdiCredentialType complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="OdiCredentialType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="OdiUser" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OdiPassword" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="WorkRepository" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OdiCredentialType", propOrder = {

})
public class OdiCredentialType {

    @XmlElement(name = "OdiUser")
    protected String odiUser;
    @XmlElement(name = "OdiPassword")
    protected String odiPassword;
    @XmlElement(name = "WorkRepository", required = true)
    protected String workRepository;

    /**
     * Obtiene el valor de la propiedad odiUser.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOdiUser() {
        return odiUser;
    }

    /**
     * Define el valor de la propiedad odiUser.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOdiUser(String value) {
        this.odiUser = value;
    }

    /**
     * Obtiene el valor de la propiedad odiPassword.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOdiPassword() {
        return odiPassword;
    }

    /**
     * Define el valor de la propiedad odiPassword.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOdiPassword(String value) {
        this.odiPassword = value;
    }

    /**
     * Obtiene el valor de la propiedad workRepository.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWorkRepository() {
        return workRepository;
    }

    /**
     * Define el valor de la propiedad workRepository.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWorkRepository(String value) {
        this.workRepository = value;
    }

}
