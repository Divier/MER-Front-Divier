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
public class DetalleFactibilidadLogDto {

    private BigDecimal idFactibilidad;
    private Date fechaFactibilidad;
    private Date fechaVencimientoFactibilidad;
    private String tecnologia;
    private String SDS;
    private String cobertura;
    private String claseFactibilidad;
    private String arrendatario;
    private int tiempoInstalaciónUM;
    private String estadoTecnología;

    public BigDecimal getIdFactibilidad() {
        return idFactibilidad;
    }

    public void setIdFactibilidad(BigDecimal idFactibilidad) {
        this.idFactibilidad = idFactibilidad;
    }

    public Date getFechaFactibilidad() {
        return fechaFactibilidad;
    }

    public void setFechaFactibilidad(Date fechaFactibilidad) {
        this.fechaFactibilidad = fechaFactibilidad;
    }

    public Date getFechaVencimientoFactibilidad() {
        return fechaVencimientoFactibilidad;
    }

    public void setFechaVencimientoFactibilidad(Date fechaVencimientoFactibilidad) {
        this.fechaVencimientoFactibilidad = fechaVencimientoFactibilidad;
    }

    public String getTecnologia() {
        return tecnologia;
    }

    public void setTecnologia(String tecnologia) {
        this.tecnologia = tecnologia;
    }

    public String getSDS() {
        return SDS;
    }

    public void setSDS(String SDS) {
        this.SDS = SDS;
    }

    public String getCobertura() {
        return cobertura;
    }

    public void setCobertura(String cobertura) {
        this.cobertura = cobertura;
    }

    public String getClaseFactibilidad() {
        return claseFactibilidad;
    }

    public void setClaseFactibilidad(String claseFactibilidad) {
        this.claseFactibilidad = claseFactibilidad;
    }

    public String getArrendatario() {
        return arrendatario;
    }

    public void setArrendatario(String arrendatario) {
        this.arrendatario = arrendatario;
    }

    public int getTiempoInstalaciónUM() {
        return tiempoInstalaciónUM;
    }

    public void setTiempoInstalaciónUM(int tiempoInstalaciónUM) {
        this.tiempoInstalaciónUM = tiempoInstalaciónUM;
    }

    public String getEstadoTecnología() {
        return estadoTecnología;
    }

    public void setEstadoTecnología(String estadoTecnología) {
        this.estadoTecnología = estadoTecnología;
    }
    
    

}
