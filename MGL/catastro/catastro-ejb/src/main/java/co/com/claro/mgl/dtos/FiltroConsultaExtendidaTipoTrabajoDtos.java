/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dtos;

import java.util.Date;

/**
 *
 * @author bocanegravm
 */
public class FiltroConsultaExtendidaTipoTrabajoDtos {
    
    
    private String codigoLocationSeleccionado;
    private String comunidaSeleccionada;
    private Date fechaCreacionSeleccionada;
    private String tecnicoAntSeleccionada;
    private String codigoComunidad;
    private String agendaInmediataSeleccionada;

    public String getCodigoLocationSeleccionado() {
        return codigoLocationSeleccionado;
    }

    public void setCodigoLocationSeleccionado(String codigoLocationSeleccionado) {
        this.codigoLocationSeleccionado = codigoLocationSeleccionado;
    }

    public String getComunidaSeleccionada() {
        return comunidaSeleccionada;
    }

    public void setComunidaSeleccionada(String comunidaSeleccionada) {
        this.comunidaSeleccionada = comunidaSeleccionada;
    }

    public Date getFechaCreacionSeleccionada() {
        return fechaCreacionSeleccionada;
    }

    public void setFechaCreacionSeleccionada(Date fechaCreacionSeleccionada) {
        this.fechaCreacionSeleccionada = fechaCreacionSeleccionada;
    }

    public String getTecnicoAntSeleccionada() {
        return tecnicoAntSeleccionada;
    }

    public void setTecnicoAntSeleccionada(String tecnicoAntSeleccionada) {
        this.tecnicoAntSeleccionada = tecnicoAntSeleccionada;
    }

    public String getCodigoComunidad() {
        return codigoComunidad;
    }

    public void setCodigoComunidad(String codigoComunidad) {
        this.codigoComunidad = codigoComunidad;
    }

    public String getAgendaInmediataSeleccionada() {
        return agendaInmediataSeleccionada;
    }

    public void setAgendaInmediataSeleccionada(String agendaInmediataSeleccionada) {
        this.agendaInmediataSeleccionada = agendaInmediataSeleccionada;
    }
        
    
}
