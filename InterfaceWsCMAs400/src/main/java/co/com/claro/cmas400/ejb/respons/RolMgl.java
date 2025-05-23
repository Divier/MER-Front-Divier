/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.cmas400.ejb.respons;

import java.math.BigInteger;
import java.util.Date;

/**
 *
 * @author User
 */
public class RolMgl {
    
    private String codRol;
    private String descripcionRol;
    private String estadoRol;
    private String idPerfil;
    private String idRolperfil;
    private Date fechaCambio;
    private BigInteger usuModifico;
             

    public RolMgl() {
    }

    public String getCodRol() {
        return codRol;
    }

    public void setCodRol(String codRol) {
        this.codRol = codRol;
    }

    public String getDescripcionRol() {
        return descripcionRol;
    }

    public void setDescripcionRol(String descripcionRol) {
        this.descripcionRol = descripcionRol;
    }

    public String getEstadoRol() {
        return estadoRol;
    }

    public void setEstadoRol(String estadoRol) {
        this.estadoRol = estadoRol;
    }

    public String getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(String idPerfil) {
        this.idPerfil = idPerfil;
    }

    public String getIdRolperfil() {
        return idRolperfil;
    }

    public void setIdRolperfil(String idRolperfil) {
        this.idRolperfil = idRolperfil;
    }

    /**
     * @return the fechaCambio
     */
    public Date getFechaCambio() {
        return fechaCambio;
    }

    /**
     * @param fechaCambio the fechaCambio to set
     */
    public void setFechaCambio(Date fechaCambio) {
        this.fechaCambio = fechaCambio;
    }

    /**
     * @return the usuModifico
     */
    public BigInteger getUsuModifico() {
        return usuModifico;
    }

    /**
     * @param usuModifico the usuModifico to set
     */
    public void setUsuModifico(BigInteger usuModifico) {
        this.usuModifico = usuModifico;
    }
    
    
}
