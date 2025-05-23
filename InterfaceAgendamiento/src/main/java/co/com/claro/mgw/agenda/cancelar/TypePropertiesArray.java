/**
 * 
 * Objetivo: Clase data Ws
 * Descripcion: Clase data Ws
 *
 * @author imartipe-everis
 * 
 */
package co.com.claro.mgw.agenda.cancelar;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Clase Java para TypePropertiesArray complex type.
 *
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera
 * que haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="TypePropertiesArray"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="property" type="{http://www.amx.com.co/SIEC/ETAdir
 * ect/WS_SIEC_CancelarOrdenMGWInbound}TypePropertyElement" maxOccurs="unbounded
 * " minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TypePropertiesArray", propOrder = {
    "property"
})
public class TypePropertiesArray {

    /**
     *
     */
    public TypePropertiesArray() {
    }

    /**
     *
     * @param property
     */
    public TypePropertiesArray(List<TypePropertyElement> property) {
        this.property = property;
    }
    /**
     *
     */
    protected List<TypePropertyElement> property;

    /**
     * Gets the value of the property property.
     *
     * <p>
     * This accessor method returns a reference to the live list, not a
     * snapshot. Therefore any modification you make to the returned list will
     * be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the property property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProperty().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TypePropertyElement }
     *
     *
     * @return
     */
    public List<TypePropertyElement> getProperty() {
        if (property == null) {
            property = new ArrayList();
        }

        return this.property;
    }

    /**
     *
     * @param c
     */
    public void add(TypePropertyElement c) {
        if (property == null) {
            property = new ArrayList();
            property.add(c);
        } else {
            property.add(c);
        }
    }

}
