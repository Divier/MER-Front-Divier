
package co.com.claro.ejb.mgl.ws.client.updateCaseStatus;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para updateCaseStatusResponse_TYPE complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="updateCaseStatusResponse_TYPE">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="_responseStatus" type="{http://www.ericsson.com/entity/CommonBusiness/BaseTypes/V1/}ResponseStatus"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "updateCaseStatusResponse_TYPE", namespace = "http://www.ericsson.com/esb/message/customer/updateCaseStatusResponse/v1.0", propOrder = {
    "responseStatus"
})
public class UpdateCaseStatusResponseTYPE {

    @XmlElement(name = "_responseStatus", required = true)
    protected ResponseStatus responseStatus;

    /**
     * Obtiene el valor de la propiedad responseStatus.
     * 
     * @return
     *     possible object is
     *     {@link ResponseStatus }
     *     
     */
    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    /**
     * Define el valor de la propiedad responseStatus.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseStatus }
     *     
     */
    public void setResponseStatus(ResponseStatus value) {
        this.responseStatus = value;
    }

}
