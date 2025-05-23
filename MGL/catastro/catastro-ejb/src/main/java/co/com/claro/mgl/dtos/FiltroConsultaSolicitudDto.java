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
public class FiltroConsultaSolicitudDto {
    
    private BigDecimal codigoSeleccionado;
    private String nombreSeleccionado;
    private String estadoSeleccionado;
    private String creaRolSeleccionado;
    private String gestionRolSeleccionado;    
    private int slaSeleccionado;
    private int slaAvisoSeleccionado;

    public BigDecimal getCodigoSeleccionado() {
        return codigoSeleccionado;
    }

    public void setCodigoSeleccionado(BigDecimal codigoSeleccionado) {
        this.codigoSeleccionado = codigoSeleccionado;
    }

    public String getNombreSeleccionado() {
        return nombreSeleccionado;
    }

    public void setNombreSeleccionado(String nombreSeleccionado) {
        this.nombreSeleccionado = nombreSeleccionado;
    }

    public String getEstadoSeleccionado() {
        return estadoSeleccionado;
    }

    public void setEstadoSeleccionado(String estadoSeleccionado) {
        this.estadoSeleccionado = estadoSeleccionado;
    }

    public String getCreaRolSeleccionado() {
        return creaRolSeleccionado;
    }

    public void setCreaRolSeleccionado(String creaRolSeleccionado) {
        this.creaRolSeleccionado = creaRolSeleccionado;
    }

    public String getGestionRolSeleccionado() {
        return gestionRolSeleccionado;
    }

    public void setGestionRolSeleccionado(String gestionRolSeleccionado) {
        this.gestionRolSeleccionado = gestionRolSeleccionado;
    }

    public int getSlaSeleccionado() {
        return slaSeleccionado;
    }

    public void setSlaSeleccionado(int slaSeleccionado) {
        this.slaSeleccionado = slaSeleccionado;
    }

    public int getSlaAvisoSeleccionado() {
        return slaAvisoSeleccionado;
    }

    public void setSlaAvisoSeleccionado(int slaAvisoSeleccionado) {
        this.slaAvisoSeleccionado = slaAvisoSeleccionado;
    }
    
}
