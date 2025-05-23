/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dtos;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author rodriguezluim
 */
@XmlRootElement(name = "cmtSolicitudDto")
public class CmtSolicitudDto extends CmtDefaultBasicResquest {

    @XmlElement
    private BigDecimal idSolicitudRequest;
    @XmlElement
    private String idTcrmRequest;
    @XmlElement
    private String tipoSolicitud;

    public BigDecimal getIdSolicitudRequest() {
        return idSolicitudRequest;
    }

    public void setIdSolicitudRequest(BigDecimal idSolicitudRequest) {
        this.idSolicitudRequest = idSolicitudRequest;
    }

    public String getIdTcrmRequest() {
        return idTcrmRequest;
    }

    public void setIdTcrmRequest(String idTcrmRequest) {
        this.idTcrmRequest = idTcrmRequest;
    }

    public String getTipoSolicitud() {
        return tipoSolicitud;
    }

    public void setTipoSolicitud(String tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }
    
    
}
