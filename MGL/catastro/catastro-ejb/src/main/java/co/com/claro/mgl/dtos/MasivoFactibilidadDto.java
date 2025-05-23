/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dtos;

import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;

/**
 *
 * @author bocanegravm
 */
public class MasivoFactibilidadDto {
    
    
    private GeograficoPoliticoMgl centroPoblado;
    private String indicador;
    private String ubicacion;
    private DrDireccion drDireccion;
    private String longitud;
    private String latitud;
    private int radioInclusion;
    private String idTipoDireccion;
    private String barrio;
    private String CpTipoNivel1;
    private String CpTipoNivel5;
    private String CpValorNivel1;
    private String CpValorNivel5;
    private String MzTipoNivel1;
    private String MzTipoNivel2;
    private String MzTipoNivel3;
    private String MzTipoNivel4;
    private String MzTipoNivel5;
    private String MzTipoNivel6;
    private String MzValorNivel1;
    private String MzValorNivel2;
    private String MzValorNivel3;
    private String MzValorNivel4;
    private String MzValorNivel5;
    private String MzValorNivel6;
    private String ItTipoPlaca;
    private String ItValorPlaca;
    private String errorConstruccionDir;
    
    private int linea;

    public GeograficoPoliticoMgl getCentroPoblado() {
        return centroPoblado;
    }

    public void setCentroPoblado(GeograficoPoliticoMgl centroPoblado) {
        this.centroPoblado = centroPoblado;
    }

    public String getIndicador() {
        return indicador;
    }

    public void setIndicador(String indicador) {
        this.indicador = indicador;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public DrDireccion getDrDireccion() {
        return drDireccion;
    }

    public void setDrDireccion(DrDireccion drDireccion) {
        this.drDireccion = drDireccion;
    }
    
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public int getRadioInclusion() {
        return radioInclusion;
    }

    public void setRadioInclusion(int radioInclusion) {
        this.radioInclusion = radioInclusion;
    }

    public String getIdTipoDireccion() {
        return idTipoDireccion;
    }

    public void setIdTipoDireccion(String idTipoDireccion) {
        this.idTipoDireccion = idTipoDireccion;
    }

    public String getCpTipoNivel1() {
        return CpTipoNivel1;
    }

    public void setCpTipoNivel1(String CpTipoNivel1) {
        this.CpTipoNivel1 = CpTipoNivel1;
    }

    public String getCpTipoNivel5() {
        return CpTipoNivel5;
    }

    public void setCpTipoNivel5(String CpTipoNivel5) {
        this.CpTipoNivel5 = CpTipoNivel5;
    }

    public String getCpValorNivel1() {
        return CpValorNivel1;
    }

    public void setCpValorNivel1(String CpValorNivel1) {
        this.CpValorNivel1 = CpValorNivel1;
    }

    public String getCpValorNivel5() {
        return CpValorNivel5;
    }

    public void setCpValorNivel5(String CpValorNivel5) {
        this.CpValorNivel5 = CpValorNivel5;
    }

    public String getMzTipoNivel1() {
        return MzTipoNivel1;
    }

    public void setMzTipoNivel1(String MzTipoNivel1) {
        this.MzTipoNivel1 = MzTipoNivel1;
    }

    public String getMzTipoNivel2() {
        return MzTipoNivel2;
    }

    public void setMzTipoNivel2(String MzTipoNivel2) {
        this.MzTipoNivel2 = MzTipoNivel2;
    }

    public String getMzTipoNivel3() {
        return MzTipoNivel3;
    }

    public void setMzTipoNivel3(String MzTipoNivel3) {
        this.MzTipoNivel3 = MzTipoNivel3;
    }

    public String getMzTipoNivel4() {
        return MzTipoNivel4;
    }

    public void setMzTipoNivel4(String MzTipoNivel4) {
        this.MzTipoNivel4 = MzTipoNivel4;
    }

    public String getMzTipoNivel5() {
        return MzTipoNivel5;
    }

    public void setMzTipoNivel5(String MzTipoNivel5) {
        this.MzTipoNivel5 = MzTipoNivel5;
    }

    public String getMzTipoNivel6() {
        return MzTipoNivel6;
    }

    public void setMzTipoNivel6(String MzTipoNivel6) {
        this.MzTipoNivel6 = MzTipoNivel6;
    }

    public String getMzValorNivel1() {
        return MzValorNivel1;
    }

    public void setMzValorNivel1(String MzValorNivel1) {
        this.MzValorNivel1 = MzValorNivel1;
    }

    public String getMzValorNivel2() {
        return MzValorNivel2;
    }

    public void setMzValorNivel2(String MzValorNivel2) {
        this.MzValorNivel2 = MzValorNivel2;
    }

    public String getMzValorNivel3() {
        return MzValorNivel3;
    }

    public void setMzValorNivel3(String MzValorNivel3) {
        this.MzValorNivel3 = MzValorNivel3;
    }

    public String getMzValorNivel4() {
        return MzValorNivel4;
    }

    public void setMzValorNivel4(String MzValorNivel4) {
        this.MzValorNivel4 = MzValorNivel4;
    }

    public String getMzValorNivel5() {
        return MzValorNivel5;
    }

    public void setMzValorNivel5(String MzValorNivel5) {
        this.MzValorNivel5 = MzValorNivel5;
    }

    public String getMzValorNivel6() {
        return MzValorNivel6;
    }

    public void setMzValorNivel6(String MzValorNivel6) {
        this.MzValorNivel6 = MzValorNivel6;
    }

    public String getItTipoPlaca() {
        return ItTipoPlaca;
    }

    public void setItTipoPlaca(String ItTipoPlaca) {
        this.ItTipoPlaca = ItTipoPlaca;
    }

    public String getItValorPlaca() {
        return ItValorPlaca;
    }

    public void setItValorPlaca(String ItValorPlaca) {
        this.ItValorPlaca = ItValorPlaca;
    }

    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public String getErrorConstruccionDir() {
        return errorConstruccionDir;
    }

    public void setErrorConstruccionDir(String errorConstruccionDir) {
        this.errorConstruccionDir = errorConstruccionDir;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }
}
