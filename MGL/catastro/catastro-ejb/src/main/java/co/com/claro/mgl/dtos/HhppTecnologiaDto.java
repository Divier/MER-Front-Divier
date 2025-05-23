/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dtos;

import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import java.math.BigDecimal;

/**
 *
 * @author cardenaslb
 */
public class HhppTecnologiaDto {
    
    private BigDecimal cmtCuentaMatrizMgl;
    private CmtSubEdificioMgl idSubEdificio;
    private CmtBasicaMgl tecnologiaHhpp;
    private int totalClientesActivos;
    private int totalHhpp;
    private BigDecimal rentaMensualCfm;
    private BigDecimal costoVt;
    private BigDecimal costoAcometida;

    public CmtSubEdificioMgl getIdSubEdificio() {
        return idSubEdificio;
    }

    public void setIdSubEdificio(CmtSubEdificioMgl idSubEdificio) {
        this.idSubEdificio = idSubEdificio;
    }

    public CmtBasicaMgl getTecnologiaHhpp() {
        return tecnologiaHhpp;
    }

    public void setTecnologiaHhpp(CmtBasicaMgl tecnologiaHhpp) {
        this.tecnologiaHhpp = tecnologiaHhpp;
    }

    public int getTotalClientesActivos() {
        return totalClientesActivos;
    }

    public void setTotalClientesActivos(int totalClientesActivos) {
        this.totalClientesActivos = totalClientesActivos;
    }

    public int getTotalHhpp() {
        return totalHhpp;
    }

    public void setTotalHhpp(int totalHhpp) {
        this.totalHhpp = totalHhpp;
    }

    public BigDecimal getRentaMensualCfm() {
        return rentaMensualCfm;
    }

    public void setRentaMensualCfm(BigDecimal rentaMensualCfm) {
        this.rentaMensualCfm = rentaMensualCfm;
    }

    public BigDecimal getCostoVt() {
        return costoVt;
    }

    public void setCostoVt(BigDecimal costoVt) {
        this.costoVt = costoVt;
    }

    public BigDecimal getCostoAcometida() {
        return costoAcometida;
    }

    public void setCostoAcometida(BigDecimal costoAcometida) {
        this.costoAcometida = costoAcometida;
    }

    public BigDecimal getCmtCuentaMatrizMgl() {
        return cmtCuentaMatrizMgl;
    }

    public void setCmtCuentaMatrizMgl(BigDecimal cmtCuentaMatrizMgl) {
        this.cmtCuentaMatrizMgl = cmtCuentaMatrizMgl;
    }


    
    

}
