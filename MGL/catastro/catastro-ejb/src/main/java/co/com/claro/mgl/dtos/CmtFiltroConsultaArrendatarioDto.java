/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dtos;

import java.math.BigDecimal;

/**
 *
 * @author corredorje
 */
public class CmtFiltroConsultaArrendatarioDto {
       
    private BigDecimal idArrendaSeleccionado;;
    private String previsitaSeleccionada;
    private String slaPrevisitaSeleccionada;
    private String slaPermisoSeleccionada;
    private String dptoSelecionado;
    private String ciudadSelecionada;
    private String centroPobladoSelecionado;
    private String nombreArrendaSelecionado;
    private String cuadranteSeleccionado;

    public BigDecimal getIdArrendaSeleccionado() {
        return idArrendaSeleccionado;
    }

    public void setIdArrendaSeleccionado(BigDecimal idArrendaSeleccionado) {
        this.idArrendaSeleccionado = idArrendaSeleccionado;
    }        

    public String getPrevisitaSeleccionada() {
        return previsitaSeleccionada;
    }

    public void setPrevisitaSeleccionada(String previsitaSeleccionada) {
        this.previsitaSeleccionada = previsitaSeleccionada;
    }

    public String getSlaPrevisitaSeleccionada() {
        return slaPrevisitaSeleccionada;
    }

    public void setSlaPrevisitaSeleccionada(String slaPrevisitaSeleccionada) {
        this.slaPrevisitaSeleccionada = slaPrevisitaSeleccionada;
    }

    public String getSlaPermisoSeleccionada() {
        return slaPermisoSeleccionada;
    }

    public void setSlaPermisoSeleccionada(String slaPermisoSeleccionada) {
        this.slaPermisoSeleccionada = slaPermisoSeleccionada;
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

    public String getCentroPobladoSelecionado() {
        return centroPobladoSelecionado;
    }

    public void setCentroPobladoSelecionado(String centroPobladoSelecionado) {
        this.centroPobladoSelecionado = centroPobladoSelecionado;
    }

    public String getNombreArrendaSelecionado() {
        return nombreArrendaSelecionado;
    }

    public void setNombreArrendaSelecionado(String nombreArrendaSelecionado) {
        this.nombreArrendaSelecionado = nombreArrendaSelecionado;
    }

    public String getCuadranteSeleccionado() {
        return cuadranteSeleccionado;
    }

    public void setCuadranteSeleccionado(String cuadranteSeleccionado) {
        this.cuadranteSeleccionado = cuadranteSeleccionado;
    }     
}
