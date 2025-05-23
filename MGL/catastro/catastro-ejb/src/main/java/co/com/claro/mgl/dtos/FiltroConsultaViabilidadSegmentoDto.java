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
public class FiltroConsultaViabilidadSegmentoDto {
    
    private BigDecimal idViaSeleccionada;
    private String estadoViaSeleccionada;
    private String segmentoSeleccionado;
    private String estratoSeleccionado;
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

    public String getSegmentoSeleccionado() {
        return segmentoSeleccionado;
    }

    public void setSegmentoSeleccionado(String segmentoSeleccionado) {
        this.segmentoSeleccionado = segmentoSeleccionado;
    }

    public String getEstratoSeleccionado() {
        return estratoSeleccionado;
    }

    public void setEstratoSeleccionado(String estratoSeleccionado) {
        this.estratoSeleccionado = estratoSeleccionado;
    }

    public String getViableSeleccionada() {
        return viableSeleccionada;
    }

    public void setViableSeleccionada(String viableSeleccionada) {
        this.viableSeleccionada = viableSeleccionada;
    }

}
