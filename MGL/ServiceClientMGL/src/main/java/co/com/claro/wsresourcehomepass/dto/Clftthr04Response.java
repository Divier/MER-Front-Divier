
package co.com.claro.wsresourcehomepass.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para clftthr04Response complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="clftthr04Response">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="descrNodo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codRed" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrRed" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="estadoRed" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codeRr" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descreRr" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "clftthr04Response", propOrder = {
    "descrNodo",
    "codRed",
    "descrRed",
    "estadoRed",
    "codeRr",
    "descreRr"
})
public class Clftthr04Response {

    @XmlElement(required = true)
    protected String descrNodo;
    @XmlElement(required = true)
    protected String codRed;
    @XmlElement(required = true)
    protected String descrRed;
    @XmlElement(required = true)
    protected String estadoRed;
    @XmlElement(required = true)
    protected String codeRr;
    @XmlElement(required = true)
    protected String descreRr;

    /**
     * Obtiene el valor de la propiedad descrNodo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrNodo() {
        return descrNodo;
    }

    /**
     * Define el valor de la propiedad descrNodo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrNodo(String value) {
        this.descrNodo = value;
    }

    /**
     * Obtiene el valor de la propiedad codRed.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodRed() {
        return codRed;
    }

    /**
     * Define el valor de la propiedad codRed.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodRed(String value) {
        this.codRed = value;
    }

    /**
     * Obtiene el valor de la propiedad descrRed.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrRed() {
        return descrRed;
    }

    /**
     * Define el valor de la propiedad descrRed.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrRed(String value) {
        this.descrRed = value;
    }

    /**
     * Obtiene el valor de la propiedad estadoRed.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEstadoRed() {
        return estadoRed;
    }

    /**
     * Define el valor de la propiedad estadoRed.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEstadoRed(String value) {
        this.estadoRed = value;
    }

    /**
     * Obtiene el valor de la propiedad codeRr.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodeRr() {
        return codeRr;
    }

    /**
     * Define el valor de la propiedad codeRr.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodeRr(String value) {
        this.codeRr = value;
    }

    /**
     * Obtiene el valor de la propiedad descreRr.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescreRr() {
        return descreRr;
    }

    /**
     * Define el valor de la propiedad descreRr.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescreRr(String value) {
        this.descreRr = value;
    }

}
