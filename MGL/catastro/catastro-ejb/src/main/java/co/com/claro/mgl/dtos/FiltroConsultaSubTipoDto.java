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
public class FiltroConsultaSubTipoDto {

    private BigDecimal codigoSeleccionado;
    private String descrSeleccionada;
    private String ageSeleccionada;
    private String invSeleccionada;
    private int slaSeleccionada;
    private int slaAvisoSeleccionada;
    private String estadoSeleccionado;
    private String esVtSeleccionado;
    private String acoGenSeleccionado;

    public BigDecimal getCodigoSeleccionado() {
        return codigoSeleccionado;
    }

    public void setCodigoSeleccionado(BigDecimal codigoSeleccionado) {
        this.codigoSeleccionado = codigoSeleccionado;
    }

    public String getDescrSeleccionada() {
        return descrSeleccionada;
    }

    public void setDescrSeleccionada(String descrSeleccionada) {
        this.descrSeleccionada = descrSeleccionada;
    }

    public String getAgeSeleccionada() {
        return ageSeleccionada;
    }

    public void setAgeSeleccionada(String ageSeleccionada) {
        this.ageSeleccionada = ageSeleccionada;
    }

    public String getInvSeleccionada() {
        return invSeleccionada;
    }

    public void setInvSeleccionada(String invSeleccionada) {
        this.invSeleccionada = invSeleccionada;
    }

    public int getSlaSeleccionada() {
        return slaSeleccionada;
    }

    public void setSlaSeleccionada(int slaSeleccionada) {
        this.slaSeleccionada = slaSeleccionada;
    }

    public int getSlaAvisoSeleccionada() {
        return slaAvisoSeleccionada;
    }

    public void setSlaAvisoSeleccionada(int slaAvisoSeleccionada) {
        this.slaAvisoSeleccionada = slaAvisoSeleccionada;
    }

    public String getEstadoSeleccionado() {
        return estadoSeleccionado;
    }

    public void setEstadoSeleccionado(String estadoSeleccionado) {
        this.estadoSeleccionado = estadoSeleccionado;
    }

    public String getEsVtSeleccionado() {
        return esVtSeleccionado;
    }

    public void setEsVtSeleccionado(String esVtSeleccionado) {
        this.esVtSeleccionado = esVtSeleccionado;
    }

    public String getAcoGenSeleccionado() {
        return acoGenSeleccionado;
    }

    public void setAcoGenSeleccionado(String acoGenSeleccionado) {
        this.acoGenSeleccionado = acoGenSeleccionado;
    }

}
