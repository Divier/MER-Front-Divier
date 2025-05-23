/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dtos;

import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;

/**
 *
 * @author Orlando Velasquez
 */
public class GestionRolesOtDireccionesDto {

    private CmtBasicaMgl estadoGeneral;
    private CmtBasicaMgl razon;
    private String rol;

    public GestionRolesOtDireccionesDto() {
    }

    public GestionRolesOtDireccionesDto(CmtBasicaMgl estadoGeneral, CmtBasicaMgl razon, String rol) {
        this.estadoGeneral = estadoGeneral;
        this.razon = razon;
        this.rol = rol;
    }

    public CmtBasicaMgl getEstadoGeneral() {
        return estadoGeneral;
    }

    public void setEstadoGeneral(CmtBasicaMgl estadoGeneral) {
        this.estadoGeneral = estadoGeneral;
    }

    public CmtBasicaMgl getRazon() {
        return razon;
    }

    public void setRazon(CmtBasicaMgl razon) {
        this.razon = razon;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
    
    

}
