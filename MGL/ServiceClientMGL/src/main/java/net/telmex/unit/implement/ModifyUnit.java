
package net.telmex.unit.implement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para modifyUnit complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="modifyUnit">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="requestModUnit" type="{http://unit.telmex.net/}modifyUnitRequest" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "modifyUnit", propOrder = {
    "requestModUnit"
})
public class ModifyUnit {

    protected ModifyUnitRequest requestModUnit;

    /**
     * Obtiene el valor de la propiedad requestModUnit.
     * 
     * @return
     *     possible object is
     *     {@link ModifyUnitRequest }
     *     
     */
    public ModifyUnitRequest getRequestModUnit() {
        return requestModUnit;
    }

    /**
     * Define el valor de la propiedad requestModUnit.
     * 
     * @param value
     *     allowed object is
     *     {@link ModifyUnitRequest }
     *     
     */
    public void setRequestModUnit(ModifyUnitRequest value) {
        this.requestModUnit = value;
    }

}
