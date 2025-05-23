
package co.com.claro.wsresourcehomepass.implement;

import co.com.claro.wsresourcehomepass.dto.ValidateReactivationResourceResponseDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para ValidateReactivationResourceResponse complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="ValidateReactivationResourceResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ValidateReactivationResourceResponse" type="{http://implement.wsresourcehomepass.claro.com.co/}validateReactivationResourceResponse" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ValidateReactivationResourceResponse", propOrder = {
    "validateReactivationResourceResponse"
})
public class ValidateReactivationResourceResponse {

    @XmlElement(name = "ValidateReactivationResourceResponse")
    protected ValidateReactivationResourceResponseDto validateReactivationResourceResponse;

    /**
     * Obtiene el valor de la propiedad validateReactivationResourceResponse.
     * 
     * @return
     *     possible object is
     *     {@link ValidateReactivationResourceResponseDto }
     *     
     */
    public ValidateReactivationResourceResponseDto getValidateReactivationResourceResponse() {
        return validateReactivationResourceResponse;
    }

    /**
     * Define el valor de la propiedad validateReactivationResourceResponse.
     * 
     * @param value
     *     allowed object is
     *     {@link ValidateReactivationResourceResponseDto }
     *     
     */
    public void setValidateReactivationResourceResponse(ValidateReactivationResourceResponseDto value) {
        this.validateReactivationResourceResponse = value;
    }

}
