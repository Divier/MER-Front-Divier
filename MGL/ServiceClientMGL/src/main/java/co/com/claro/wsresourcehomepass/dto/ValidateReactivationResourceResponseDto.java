
package co.com.claro.wsresourcehomepass.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para validateReactivationResourceResponse complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="validateReactivationResourceResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="curNode" type="{http://implement.wsresourcehomepass.claro.com.co/}clftthr04Response"/>
 *         &lt;element name="curDirAndStatusUnit" type="{http://implement.wsresourcehomepass.claro.com.co/}curDirAndStatusUnit"/>
 *         &lt;element name="curFraudMarking" type="{http://implement.wsresourcehomepass.claro.com.co/}curFraudMarking"/>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "validateReactivationResourceResponse", propOrder = {
    "curNode",
    "curDirAndStatusUnit",
    "curFraudMarking",
    "code",
    "description"
})
public class ValidateReactivationResourceResponseDto {

    @XmlElement(required = true)
    protected Clftthr04Response curNode;
    @XmlElement(required = true)
    protected CurDirAndStatusUnit curDirAndStatusUnit;
    @XmlElement(required = true)
    protected CurFraudMarking curFraudMarking;
    @XmlElement(required = true)
    protected String code;
    @XmlElement(required = true)
    protected String description;

    /**
     * Obtiene el valor de la propiedad curNode.
     * 
     * @return
     *     possible object is
     *     {@link Clftthr04Response }
     *     
     */
    public Clftthr04Response getCurNode() {
        return curNode;
    }

    /**
     * Define el valor de la propiedad curNode.
     * 
     * @param value
     *     allowed object is
     *     {@link Clftthr04Response }
     *     
     */
    public void setCurNode(Clftthr04Response value) {
        this.curNode = value;
    }

    /**
     * Obtiene el valor de la propiedad curDirAndStatusUnit.
     * 
     * @return
     *     possible object is
     *     {@link CurDirAndStatusUnit }
     *     
     */
    public CurDirAndStatusUnit getCurDirAndStatusUnit() {
        return curDirAndStatusUnit;
    }

    /**
     * Define el valor de la propiedad curDirAndStatusUnit.
     * 
     * @param value
     *     allowed object is
     *     {@link CurDirAndStatusUnit }
     *     
     */
    public void setCurDirAndStatusUnit(CurDirAndStatusUnit value) {
        this.curDirAndStatusUnit = value;
    }

    /**
     * Obtiene el valor de la propiedad curFraudMarking.
     * 
     * @return
     *     possible object is
     *     {@link CurFraudMarking }
     *     
     */
    public CurFraudMarking getCurFraudMarking() {
        return curFraudMarking;
    }

    /**
     * Define el valor de la propiedad curFraudMarking.
     * 
     * @param value
     *     allowed object is
     *     {@link CurFraudMarking }
     *     
     */
    public void setCurFraudMarking(CurFraudMarking value) {
        this.curFraudMarking = value;
    }

    /**
     * Obtiene el valor de la propiedad code.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCode() {
        return code;
    }

    /**
     * Define el valor de la propiedad code.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCode(String value) {
        this.code = value;
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

}
