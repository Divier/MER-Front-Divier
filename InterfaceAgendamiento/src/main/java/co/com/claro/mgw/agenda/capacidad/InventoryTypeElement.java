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

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "inventory", propOrder = {
    "type",
    "manufacture",
    "family"
})
public class InventoryTypeElement {

    /**
     *
     */
    public InventoryTypeElement() {
    }

    /**
     *
     * @param type
     * @param manufacture
     * @param family
     */
    public InventoryTypeElement(String type, String manufacture, String family) {
        this.type = type;
        this.manufacture = manufacture;
        this.family = family;
    }
    /**
     *
     */
    @XmlElement(name = "type")
    protected String type;
    /**
     *
     */
    @XmlElement(name = "manufacture")
    protected String manufacture;
    /**
     *
     */
    @XmlElement(name = "family")
    protected String family;

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
    public String getManufacture() {
        return manufacture;
    }

    /**
     *
     * @param manufacture
     */
    public void setManufacture(String manufacture) {
        this.manufacture = manufacture;
    }

    /**
     *
     * @return String
     */
    public String getFamily() {
        return family;
    }

    /**
     *
     * @param family
     */
    public void setFamily(String family) {
        this.family = family;
    }

}
