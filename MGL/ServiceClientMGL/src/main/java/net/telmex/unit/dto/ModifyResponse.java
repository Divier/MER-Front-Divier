
package net.telmex.unit.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para modifyResponse complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="modifyResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://unit.telmex.net/}basicMessage">
 *       &lt;sequence>
 *         &lt;element name="AKYN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="STRN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="messageText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="messageType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "modifyResponse", propOrder = {
    "akyn",
    "strn",
    "messageText",
    "messageType"
})
public class ModifyResponse
    extends BasicMessage
{

    @XmlElement(name = "AKYN")
    protected String akyn;
    @XmlElement(name = "STRN")
    protected String strn;
    protected String messageText;
    protected String messageType;

    /**
     * Obtiene el valor de la propiedad akyn.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAKYN() {
        return akyn;
    }

    /**
     * Define el valor de la propiedad akyn.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAKYN(String value) {
        this.akyn = value;
    }

    /**
     * Obtiene el valor de la propiedad strn.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSTRN() {
        return strn;
    }

    /**
     * Define el valor de la propiedad strn.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSTRN(String value) {
        this.strn = value;
    }

    /**
     * Obtiene el valor de la propiedad messageText.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessageText() {
        return messageText;
    }

    /**
     * Define el valor de la propiedad messageText.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessageText(String value) {
        this.messageText = value;
    }

    /**
     * Obtiene el valor de la propiedad messageType.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessageType() {
        return messageType;
    }

    /**
     * Define el valor de la propiedad messageType.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessageType(String value) {
        this.messageType = value;
    }

}
