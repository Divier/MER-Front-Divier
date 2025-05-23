
package co.com.claro.wsresourcehomepass.implement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para ValidateReactivationResource complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="ValidateReactivationResource">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ValidateReactivationResourceRequest" type="{http://implement.wsresourcehomepass.claro.com.co/}validateReactivationResourceRequest" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ValidateReactivationResource", propOrder = {
    "validateReactivationResourceRequest"
})
public class ValidateReactivationResource {

    @XmlElement(name = "ValidateReactivationResourceRequest")
    protected ValidateReactivationResourceRequest validateReactivationResourceRequest;

    /**
     * Obtiene el valor de la propiedad validateReactivationResourceRequest.
     * 
     * @return
     *     possible object is
     *     {@link ValidateReactivationResourceRequest }
     *     
     */
    public ValidateReactivationResourceRequest getValidateReactivationResourceRequest() {
        return validateReactivationResourceRequest;
    }

    /**
     * Define el valor de la propiedad validateReactivationResourceRequest.
     * 
     * @param value
     *     allowed object is
     *     {@link ValidateReactivationResourceRequest }
     *     
     */
    public void setValidateReactivationResourceRequest(ValidateReactivationResourceRequest value) {
        this.validateReactivationResourceRequest = value;
    }

}
