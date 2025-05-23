package co.com.claro.mgl.dtos;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Dto response basico de ws
 *
 * @author carlos.villa.ext@claro.com.co
 *
 * @versión 1.00.0000
 */
@XmlRootElement(name = "cmtDefaultBasicResquest")
public class CmtDefaultBasicResquest {

    @XmlElement
    private String user;
    @XmlElement
    private String sourceAplication;
    @XmlElement
    private String destinationAplication;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getSourceAplication() {
        return sourceAplication;
    }

    public void setSourceAplication(String sourceAplication) {
        this.sourceAplication = sourceAplication;
    }

    public String getDestinationAplication() {
        return destinationAplication;
    }

    public void setDestinationAplication(String destinationAplication) {
        this.destinationAplication = destinationAplication;
    }
}
