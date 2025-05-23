/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.telmex.catastro.services.util;

import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;

/**
 *
 * @author Orlando Velasquez
 */
public class ParametrosTareasProgramadas {
    
    private String valor;
    private CmtBasicaMgl estado;
    private String descripcion;
    private String nombreServidor;

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public CmtBasicaMgl getEstado() {
        return estado;
    }

    public void setEstado(CmtBasicaMgl estado) {
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombreServidor() {
        return nombreServidor;
    }

    public void setNombreServidor(String nombreServidor) {
        this.nombreServidor = nombreServidor;
    }  
}
