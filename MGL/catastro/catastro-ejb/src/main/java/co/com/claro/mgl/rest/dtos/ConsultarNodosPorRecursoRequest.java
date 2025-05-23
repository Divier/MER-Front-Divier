package co.com.claro.mgl.rest.dtos;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * dto del reuest del metodo ConsultarNodosPorRecursoRequest. 
 * Capura los parametro de entrada.
 *
 * @author Hitts - Leidy Montero
 * @versión V1.0
 */
@XmlRootElement(name = "consultarNodosPorRecursoRequest")
public class ConsultarNodosPorRecursoRequest  {

    @XmlElement(name = "type")
    private String type;
    @XmlElement(name = "value")
    private String value;
    @XmlElement(name = "technology")
    private String technology;
    @XmlElement(name = "stateTechnology")
    private String stateTechnology;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public String getStateTechnology() {
        return stateTechnology;
    }

    public void setStateTechnology(String stateTechnology) {
        this.stateTechnology = stateTechnology;
    }
}