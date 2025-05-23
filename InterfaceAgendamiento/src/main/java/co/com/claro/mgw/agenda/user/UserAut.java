/**
 *
 * Objetivo: Clase data Ws
 * Descripcion: Clase data Ws
 *
 * @author victor bocanegra-HITSS
 *
 */
package co.com.claro.mgw.agenda.user;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para userAut complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera 
 * que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="userAut"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="now"
 *          type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="login" 
 *          type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="auth_string" 
 *          type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="company" 
 *          type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "userAut", propOrder = {
    "now",
    "login",
    "authString",
    "company"
})
public class UserAut {

    @XmlElement(required = true)
    protected String now;
    @XmlElement(required = true)
    protected String login;
    @XmlElement(name = "auth_string", required = true)
    protected String authString;
    @XmlElement(required = true)
    protected String company;

    public UserAut()
    {}

    public UserAut(String now, String login, String authString, String company)
    {
        this.now = now;
        this.login = login;
        this.authString = authString;
        this.company = company;
    }
    
    /**
     * Obtiene el valor de la propiedad now.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNow() {
        return now;
    }

    /**
     * Define el valor de la propiedad now.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNow(String value) {
        this.now = value;
    }

    /**
     * Obtiene el valor de la propiedad login.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLogin() {
        return login;
    }

    /**
     * Define el valor de la propiedad login.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLogin(String value) {
        this.login = value;
    }

    /**
     * Obtiene el valor de la propiedad authString.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthString() {
        return authString;
    }

    /**
     * Define el valor de la propiedad authString.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthString(String value) {
        this.authString = value;
    }

    /**
     * Obtiene el valor de la propiedad company.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompany() {
        return company;
    }

    /**
     * Define el valor de la propiedad company.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompany(String value) {
        this.company = value;
    }

}
