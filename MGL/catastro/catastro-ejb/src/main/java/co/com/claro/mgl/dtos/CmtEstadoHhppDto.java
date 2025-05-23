/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dtos;

import co.com.claro.mgl.jpa.entities.EstadoHhppMgl;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author rodriguezluim
 */
@XmlRootElement(name = "cmtEstadoHhppDto")
public class CmtEstadoHhppDto extends CmtDefaultBasicResquest {

    @XmlElement
    private BigDecimal idHhppRequest;
    @XmlElement
    private BigDecimal idCasoTcrmRequest;
    @XmlElement
    private EstadoHhppMgl estadoHhpp;
    @XmlElement
    private String subscriptor;

    public String getSubscriptor() {
        return subscriptor;
    }

    public void setSubscriptor(String subscriptor) {
        this.subscriptor = subscriptor;
    }
    
    public BigDecimal getIdHhppRequest() {
        return idHhppRequest;
    }

    public void setIdHhppRequest(BigDecimal idHhppRequest) {
        this.idHhppRequest = idHhppRequest;
    }

    public BigDecimal getIdCasoTcrmRequest() {
        return idCasoTcrmRequest;
    }

    public void setIdCasoTcrmRequest(BigDecimal idCasoTcrmRequest) {
        this.idCasoTcrmRequest = idCasoTcrmRequest;
    }

    public EstadoHhppMgl getEstadoHhpp() {
        return estadoHhpp;
    }

    public void setEstadoHhpp(EstadoHhppMgl estadoHhpp) {
        this.estadoHhpp = estadoHhpp;
    }
}
