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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author imartipe
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "capacity_restriction_element", propOrder = {
    "code",
    "type",
    "description"
})
public class CapacityRestrictionElement {

    /**
     *
     */
    public CapacityRestrictionElement() {
    }

    /**
     *
     * @param code
     * @param type
     * @param description
     */
    public CapacityRestrictionElement(String code, String type, String description) {
        this.code = code;
        this.type = type;
        this.description = description;
    }
    /**
     *
     */
    @XmlSchemaType(name = "code")
    protected String code;
    /**
     *
     */
    @XmlElement(name = "type")
    protected String type;
    /**
     *
     */
    @XmlElement(name = "description")
    protected String description;

    /**
     *
     * @return String
     */
    public String getCode() {
        return code;
    }

    /**
     *
     * @param code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     *
     * @return String
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return String
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

}
