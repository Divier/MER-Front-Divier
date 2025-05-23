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
public class CmtGeograficoPoliticoDto {

    private String nombreDepartamento;
    private String nombreCiudad;
    private String nombreCentro;
    private String codigoDane;
    private BigDecimal centroPobladoId;

    public String getNombreDepartamento() {
        return nombreDepartamento;
    }

    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
    }

    public String getNombreCiudad() {
        return nombreCiudad;
    }

    public void setNombreCiudad(String nombreCiudad) {
        this.nombreCiudad = nombreCiudad;
    }

    public String getNombreCentro() {
        return nombreCentro;
    }

    public void setNombreCentro(String nombreCentro) {
        this.nombreCentro = nombreCentro;
    }

    public String getCodigoDane() {
        return codigoDane;
    }

    public void setCodigoDane(String codigoDane) {
        this.codigoDane = codigoDane;
    }

    public BigDecimal getCentroPobladoId() {
        return centroPobladoId;
    }

    public void setCentroPobladoId(BigDecimal centroPobladoId) {
        this.centroPobladoId = centroPobladoId;
    }
    
    

}
