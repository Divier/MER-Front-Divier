/**
 * 
 * Objetivo: Clase data Ws
 * Descripcion: Clase data Ws
 *
 * @author imartipe-everis
 * 
 */
package co.com.claro.mgw.agenda.shareddata;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para user_element complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera
 * que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="user_element"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="now" type="{http://www.w3.org/2001/XMLSchema}
 *          string"/&gt;
 *         &lt;element name="login" type="{http://www.w3.org/2001/XMLSchema}
 *          string"/&gt;
 *         &lt;element name="company" type="{http://www.w3.org/2001/XMLSchema}
 *          string"/&gt;
 *         &lt;element name="auth_string"
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
@XmlType
(name = "user_element", propOrder =
{
    "now",
    "login",
    "company",
    "auth_string"
})
public class MgwUserElement
{

    /**
     * 
     */
    public MgwUserElement()
    {}

    /**
     * 
     * @param now
     * @param login
     * @param company
     * @param auth_string 
     */
    public MgwUserElement
    (String now, String login, String company, String auth_string)
    {
        this.now = now;
        this.login = login;
        this.company = company;
        this.auth_string = auth_string;
    }

    /**
     *
     */
    @XmlElement(required = true)
    protected String now;

    /**
     *
     */
    @XmlElement(required = true)
    protected String login;

    /**
     *
     */
    @XmlElement(required = true)
    protected String company;

    /**
     *
     */
    @XmlElement(name = "auth_string", required = true)
    protected String auth_string;

    /**
     * Obtiene el valor de la propiedad now.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNow()
    {
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
    public void setNow(String value)
    {
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
    public String getLogin()
    {
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
    public void setLogin(String value)
    {
        this.login = value;
    }

    /**
     * Obtiene el valor de la propiedad company.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompany()
    {
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
    public void setCompany(String value)
    {
        this.company = value;
    }

    /**
     * Obtiene el valor de la propiedad auth_string.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuth_string()
    {
        return auth_string;
    }

    /**
     * Define el valor de la propiedad auth_string.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuth_string(String value)
    {
        this.auth_string = value;
    }
}
