/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgw.agenda.util;

/**
 *
 * @author bocanegravm
 * Objetivo: Clase data Ws
 * Descripcion: Clase data Ws

 *
 */
import co.com.claro.mgw.agenda.user.UserAut;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para Response complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera 
 * que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="Response"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="user" type="{urn:toa:outbound}user_t"/&gt;
 *         &lt;element name="description" 
 *          type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="codigo" 
 *          type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="message_id" 
 *          type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="status" 
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
@XmlType(name = "servicesAgendamientosResponse", propOrder = {
    "user",
    "description",
    "codigo",
    "messageId",
    "status"
})
public class ServicesAgendamientosResponse {

    @XmlElement(required = true)
    protected UserAut user;
    @XmlElement(required = true)
    protected String description;
    @XmlElement(required = true)
    protected String codigo;
    @XmlElement(name = "message_id")
    protected int messageId;
    @XmlElement(required = true)
    protected String status;

    public ServicesAgendamientosResponse()
    {}

    public ServicesAgendamientosResponse
    (
        UserAut user, String description, String codigo,
        int messageId, String status
    )
    {
        this.user = user;
        this.description = description;
        this.codigo = codigo;
        this.messageId = messageId;
        this.status = status;
    }
    
    /**
     * Obtiene el valor de la propiedad user.
     * 
     * @return
     *     possible object is
     *     {@link UserT }
     *     
     */
    public UserAut getUser() {
        return user;
    }

    /**
     * Define el valor de la propiedad user.
     * 
     * @param value
     *     allowed object is
     *     {@link UserT }
     *     
     */
    public void setUser(UserAut value) {
        this.user = value;
    }

    /**
     * Obtiene el valor de la propiedad description.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Define el valor de la propiedad description.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Obtiene el valor de la propiedad codigo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Define el valor de la propiedad codigo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigo(String value) {
        this.codigo = value;
    }

    /**
     * Obtiene el valor de la propiedad messageId.
     * 
     * @return 
     */
    public int getMessageId() {
        return messageId;
    }

    /**
     * Define el valor de la propiedad messageId.
     * 
     * @param value
     */
    public void setMessageId(int value) {
        this.messageId = value;
    }

    /**
     * Obtiene el valor de la propiedad status.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Define el valor de la propiedad status.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

}
