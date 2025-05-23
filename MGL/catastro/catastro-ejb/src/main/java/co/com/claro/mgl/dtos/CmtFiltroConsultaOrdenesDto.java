/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dtos;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author bocanegravm
 */
public class CmtFiltroConsultaOrdenesDto {
    
    private Date fechaIngresoSeleccionada;
    private BigDecimal idOtSelecionada;
    private BigDecimal ccmmRrSelecionada;
    private String tipoOtSelecionada;
    private String dptoSelecionado;
    private String ciudadSelecionada;
    private String tecnoSelecionada;
    private String estIntSelecionada;
    private BigDecimal ccmmMglSelecionada;
    private String regionalSeleccionada;
    private String bloqueada;
    private String userBloqueo;
    private int slaSeleccionada;

    public Date getFechaIngresoSeleccionada() {
        return fechaIngresoSeleccionada;
    }

    public void setFechaIngresoSeleccionada(Date fechaIngresoSeleccionada) {
        this.fechaIngresoSeleccionada = fechaIngresoSeleccionada;
    }

    public BigDecimal getIdOtSelecionada() {
        return idOtSelecionada;
    }

    public void setIdOtSelecionada(BigDecimal idOtSelecionada) {
        this.idOtSelecionada = idOtSelecionada;
    }

    public BigDecimal getCcmmRrSelecionada() {
        return ccmmRrSelecionada;
    }

    public void setCcmmRrSelecionada(BigDecimal ccmmRrSelecionada) {
        this.ccmmRrSelecionada = ccmmRrSelecionada;
    }

    public String getTipoOtSelecionada() {
        return tipoOtSelecionada;
    }

    public void setTipoOtSelecionada(String tipoOtSelecionada) {
        this.tipoOtSelecionada = tipoOtSelecionada;
    }

    public String getDptoSelecionado() {
        return dptoSelecionado;
    }

    public void setDptoSelecionado(String dptoSelecionado) {
        this.dptoSelecionado = dptoSelecionado;
    }

    public String getCiudadSelecionada() {
        return ciudadSelecionada;
    }

    public void setCiudadSelecionada(String ciudadSelecionada) {
        this.ciudadSelecionada = ciudadSelecionada;
    }

    public String getTecnoSelecionada() {
        return tecnoSelecionada;
    }

    public void setTecnoSelecionada(String tecnoSelecionada) {
        this.tecnoSelecionada = tecnoSelecionada;
    }

    public String getEstIntSelecionada() {
        return estIntSelecionada;
    }

    public void setEstIntSelecionada(String estIntSelecionada) {
        this.estIntSelecionada = estIntSelecionada;
    }

    public BigDecimal getCcmmMglSelecionada() {
        return ccmmMglSelecionada;
    }

    public void setCcmmMglSelecionada(BigDecimal ccmmMglSelecionada) {
        this.ccmmMglSelecionada = ccmmMglSelecionada;
    }

    public String getRegionalSeleccionada() {
        return regionalSeleccionada;
    }

    public void setRegionalSeleccionada(String regionalSeleccionada) {
        this.regionalSeleccionada = regionalSeleccionada;
    }
    
    public String getBloqueada() {
        return bloqueada;
    }

    public void setBloqueada(String bloqueada) {
        this.bloqueada = bloqueada;
    }

    public String getUserBloqueo() {
        return userBloqueo;
    }

    public void setUserBloqueo(String userBloqueo) {
        this.userBloqueo = userBloqueo;
    }

    public int getSlaSeleccionada() {
        return slaSeleccionada;
    }

    public void setSlaSeleccionada(int slaSeleccionada) {
        this.slaSeleccionada = slaSeleccionada;
    }

}
