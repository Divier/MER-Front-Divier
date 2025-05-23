
package co.com.claro.wsresourcehomepass.implement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para validateReactivationResourceRequest complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="validateReactivationResourceRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codNode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="division" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="community" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="account" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="userRR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "validateReactivationResourceRequest", propOrder = {
    "codNode",
    "division",
    "community",
    "account",
    "userRR"
})
public class ValidateReactivationResourceRequest {

    @XmlElement(required = true)
    protected String codNode;
    @XmlElement(required = true)
    protected String division;
    @XmlElement(required = true)
    protected String community;
    protected int account;
    @XmlElement(required = true)
    protected String userRR;

    /**
     * Obtiene el valor de la propiedad codNode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodNode() {
        return codNode;
    }

    /**
     * Define el valor de la propiedad codNode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodNode(String value) {
        this.codNode = value;
    }

    /**
     * Obtiene el valor de la propiedad division.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDivision() {
        return division;
    }

    /**
     * Define el valor de la propiedad division.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDivision(String value) {
        this.division = value;
    }

    /**
     * Obtiene el valor de la propiedad community.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCommunity() {
        return community;
    }

    /**
     * Define el valor de la propiedad community.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCommunity(String value) {
        this.community = value;
    }

    /**
     * Obtiene el valor de la propiedad account.
     * 
     */
    public int getAccount() {
        return account;
    }

    /**
     * Define el valor de la propiedad account.
     * 
     */
    public void setAccount(int value) {
        this.account = value;
    }

    /**
     * Obtiene el valor de la propiedad userRR.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserRR() {
        return userRR;
    }

    /**
     * Define el valor de la propiedad userRR.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserRR(String value) {
        this.userRR = value;
    }

}
