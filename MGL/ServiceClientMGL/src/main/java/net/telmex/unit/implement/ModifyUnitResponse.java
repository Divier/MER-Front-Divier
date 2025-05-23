
package net.telmex.unit.implement;

import net.telmex.unit.dto.ModifyResponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para modifyUnitResponse complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="modifyUnitResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="responseModUnit" type="{http://unit.telmex.net/}modifyResponse" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "modifyUnitResponse", propOrder = {
    "responseModUnit"
})
public class ModifyUnitResponse {

    protected ModifyResponse responseModUnit;

    /**
     * Obtiene el valor de la propiedad responseModUnit.
     * 
     * @return
     *     possible object is
     *     {@link ModifyResponse }
     *     
     */
    public ModifyResponse getResponseModUnit() {
        return responseModUnit;
    }

    /**
     * Define el valor de la propiedad responseModUnit.
     * 
     * @param value
     *     allowed object is
     *     {@link ModifyResponse }
     *     
     */
    public void setResponseModUnit(ModifyResponse value) {
        this.responseModUnit = value;
    }

}
