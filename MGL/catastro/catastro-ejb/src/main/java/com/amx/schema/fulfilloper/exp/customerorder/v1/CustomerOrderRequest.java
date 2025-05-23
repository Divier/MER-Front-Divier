
package com.amx.schema.fulfilloper.exp.customerorder.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *       &lt;sequence>
 *         &lt;element name="idOption" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="codDepend" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="relationParameter" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "idOption",
    "codDepend",
    "relationParameter"
})
@XmlRootElement(name = "CustomerOrderRequest")
public class CustomerOrderRequest {

    protected Integer idOption;
    protected String codDepend;
    protected String relationParameter;

    /**
     * Obtiene el valor de la propiedad idOption.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIdOption() {
        return idOption;
    }

    /**
     * Define el valor de la propiedad idOption.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIdOption(Integer value) {
        this.idOption = value;
    }

    /**
     * Obtiene el valor de la propiedad codDepend.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodDepend() {
        return codDepend;
    }

    /**
     * Define el valor de la propiedad codDepend.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodDepend(String value) {
        this.codDepend = value;
    }

    /**
     * Obtiene el valor de la propiedad relationParameter.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRelationParameter() {
        return relationParameter;
    }

    /**
     * Define el valor de la propiedad relationParameter.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRelationParameter(String value) {
        this.relationParameter = value;
    }

}
