/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dtos;

import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author cardenaslb
 */
public class EstadosTecnologiasDto {

    private BigDecimal idEstadosxTecnologia;

    private CmtBasicaMgl estadosCm;

    private List<CmtBasicaMgl> innerlistTec;

    public CmtBasicaMgl getEstadosCm() {
        return estadosCm;
    }

    public void setEstadosCm(CmtBasicaMgl estadosCm) {
        this.estadosCm = estadosCm;
    }

    public List<CmtBasicaMgl> getInnerlistTec() {
        return innerlistTec;
    }

    public void setInnerlistTec(List<CmtBasicaMgl> innerlistTec) {
        this.innerlistTec = innerlistTec;
    }

    public BigDecimal getIdEstadosxTecnologia() {
        return idEstadosxTecnologia;
    }

    public void setIdEstadosxTecnologia(BigDecimal idEstadosxTecnologia) {
        this.idEstadosxTecnologia = idEstadosxTecnologia;
    }

}
