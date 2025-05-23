/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.procesomasivo;

import java.util.Date;

/**
 *
 * @author bocanegravm
 */
public class CmtNotasOtHijaDto implements Comparable<CmtNotasOtHijaDto>{
    
    
    private String usuarioIngresaNota;
    private Date fechaIngresaNota;
    private String descripcionNota;

    public String getUsuarioIngresaNota() {
        return usuarioIngresaNota;
    }

    public void setUsuarioIngresaNota(String usuarioIngresaNota) {
        this.usuarioIngresaNota = usuarioIngresaNota;
    }

    public Date getFechaIngresaNota() {
        return fechaIngresaNota;
    }

    public void setFechaIngresaNota(Date fechaIngresaNota) {
        this.fechaIngresaNota = fechaIngresaNota;
    }

    public String getDescripcionNota() {
        return descripcionNota;
    }

    public void setDescripcionNota(String descripcionNota) {
        this.descripcionNota = descripcionNota;
    }
    
    @Override
    public int compareTo(CmtNotasOtHijaDto o) {
        return o.getFechaIngresaNota().compareTo(fechaIngresaNota);
    }

}
