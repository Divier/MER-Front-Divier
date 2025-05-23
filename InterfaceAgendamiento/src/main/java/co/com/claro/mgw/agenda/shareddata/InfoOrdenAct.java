/**
 *
 * Objetivo: Clase data Ws Descripcion: Clase data Ws
 *
 * @author imartipe-everis
 *
 */
package co.com.claro.mgw.agenda.shareddata;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author imartipe
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "infoOrderAct", propOrder = {
    "name",
    "value"
})
public class InfoOrdenAct {

    /**
     *
     */
    public InfoOrdenAct() {
    }

    /**
     *
     * @param name
     * @param value
     */
    public InfoOrdenAct(String name, String value) {
        this.name = name;
        this.value = value;
    }
    /**
     *
     */
    @XmlElement(name = "name", required = true)
    private String name;
    /**
     *
     */
    @XmlElement(name = "value", required = true)
    private String value;

    /**
     *
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return String
     */
    public String getValue() {
        return value;
    }

    /**
     *
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }

}
