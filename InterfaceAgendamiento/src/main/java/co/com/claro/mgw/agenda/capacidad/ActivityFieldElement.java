/**
 * 
 * Objetivo: Clase data Ws
 * Descripcion: Clase data Ws
 *
 * @author imartipe-everis
 * 
 */
package co.com.claro.mgw.agenda.capacidad;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Clase Java para activity_field_element complex type.
 *
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera
 * que haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="activity_field_element"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}
 * string"/&gt;
 *         &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}
 * string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "activity_field_element", propOrder = {
    "name",
    "value"
})
public class ActivityFieldElement {

    /**
     *
     */
    public ActivityFieldElement() {
    }

    /**
     *
     * @param name
     * @param value
     */
    public ActivityFieldElement(String name, String value) {
        this.name = name;
        this.value = value;
    }
    /**
     *
     */
    @XmlElement(required = true)
    protected String name;
    /**
     *
     */
    @XmlElement(required = true)
    protected String value;

    /**
     * Obtiene el valor de la propiedad name.
     *
     * @return possible object is {@link String }
     *
     */
    public String getName() {
        return name;
    }

    /**
     * Define el valor de la propiedad name.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Obtiene el valor de la propiedad value.
     *
     * @return possible object is {@link String }
     *
     */
    public String getValue() {
        return value;
    }

    /**
     * Define el valor de la propiedad value.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setValue(String value) {
        this.value = value;
    }

}
