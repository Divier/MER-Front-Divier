/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dtos;

import java.math.BigDecimal;

/**
 *
 * @author bocanegravm
 */
public class FiltroConsultaViabilidadHhppDto {
    
    private BigDecimal idViaSeleccionada;
    private String estadoViaSeleccionada;
    private String estadoNodoSeleccionada;
    private String estadoCcmmSeleccionada;
    private String estadoHhppSeleccionada;
    private String viableSeleccionada;

    public BigDecimal getIdViaSeleccionada() {
        return idViaSeleccionada;
    }

    public void setIdViaSeleccionada(BigDecimal idViaSeleccionada) {
        this.idViaSeleccionada = idViaSeleccionada;
    }

    public String getEstadoViaSeleccionada() {
        return estadoViaSeleccionada;
    }

    public void setEstadoViaSeleccionada(String estadoViaSeleccionada) {
        this.estadoViaSeleccionada = estadoViaSeleccionada;
    }

    public String getEstadoNodoSeleccionada() {
        return estadoNodoSeleccionada;
    }

    public void setEstadoNodoSeleccionada(String estadoNodoSeleccionada) {
        this.estadoNodoSeleccionada = estadoNodoSeleccionada;
    }

    public String getEstadoCcmmSeleccionada() {
        return estadoCcmmSeleccionada;
    }

    public void setEstadoCcmmSeleccionada(String estadoCcmmSeleccionada) {
        this.estadoCcmmSeleccionada = estadoCcmmSeleccionada;
    }

    public String getEstadoHhppSeleccionada() {
        return estadoHhppSeleccionada;
    }

    public void setEstadoHhppSeleccionada(String estadoHhppSeleccionada) {
        this.estadoHhppSeleccionada = estadoHhppSeleccionada;
    }

    public String getViableSeleccionada() {
        return viableSeleccionada;
    }

    public void setViableSeleccionada(String viableSeleccionada) {
        this.viableSeleccionada = viableSeleccionada;
    }
}
