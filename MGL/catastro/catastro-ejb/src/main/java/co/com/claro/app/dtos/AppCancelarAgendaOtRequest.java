/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.app.dtos;

import co.com.claro.mgl.dtos.CmtDefaultBasicResquest;
import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bocanegravm
 */
@XmlRootElement
public class AppCancelarAgendaOtRequest extends CmtDefaultBasicResquest{

    @XmlElement
    private String otLocation;
    @XmlElement
    private BigDecimal idAgenda;
    @XmlElement
    private String codigoRazonCancela;
    @XmlElement
    private String comentarioCancela;
    @XmlElement
    private String ofpsOtId;

    public String getOtLocation() {
        return otLocation;
    }

    public void setOtLocation(String otLocation) {
        this.otLocation = otLocation;
    }

    public BigDecimal getIdAgenda() {
        return idAgenda;
    }

    public void setIdAgenda(BigDecimal idAgenda) {
        this.idAgenda = idAgenda;
    }

    public String getCodigoRazonCancela() {
        return codigoRazonCancela;
    }

    public void setCodigoRazonCancela(String codigoRazonCancela) {
        this.codigoRazonCancela = codigoRazonCancela;
    }

    public String getComentarioCancela() {
        return comentarioCancela;
    }

    public void setComentarioCancela(String comentarioCancela) {
        this.comentarioCancela = comentarioCancela;
    }

    public String getOfpsOtId() {
        return ofpsOtId;
    }

    public void setOfpsOtId(String ofpsOtId) {
        this.ofpsOtId = ofpsOtId;
    }

   
}
