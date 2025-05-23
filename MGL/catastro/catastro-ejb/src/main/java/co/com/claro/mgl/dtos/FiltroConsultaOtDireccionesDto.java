/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dtos;

import co.com.claro.mgl.jpa.entities.TipoOtHhppMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtRegionalRr;
import java.util.Date;

/**
 *
 * @author Orlando Velasquez
 */
public class FiltroConsultaOtDireccionesDto {

    private String id;
    private TipoOtHhppMgl tipo;
    private String nombreContacto;
    private String telefonoContacto;
    private String correoContacto;
    private Date fechaCreacion;
    private CmtBasicaMgl estado;
    private CmtRegionalRr regional;
    private int sla;

    public FiltroConsultaOtDireccionesDto() {
    }

    public FiltroConsultaOtDireccionesDto(String id, TipoOtHhppMgl tipo, 
            String nombreContacto, String telefonoContacto, 
            String correoContacto, Date fechaCreacion, CmtBasicaMgl estado) {
        this.id = id;
        this.tipo = tipo;
        this.nombreContacto = nombreContacto;
        this.telefonoContacto = telefonoContacto;
        this.correoContacto = correoContacto;
        this.fechaCreacion = fechaCreacion;
        this.estado = estado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TipoOtHhppMgl getTipo() {
        return tipo;
    }

    public void setTipo(TipoOtHhppMgl tipo) {
        this.tipo = tipo;
    }

    public String getNombreContacto() {
        return nombreContacto;
    }

    public void setNombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public String getCorreoContacto() {
        return correoContacto;
    }

    public void setCorreoContacto(String correoContacto) {
        this.correoContacto = correoContacto;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public CmtBasicaMgl getEstado() {
        return estado;
    }

    public void setEstado(CmtBasicaMgl estado) {
        this.estado = estado;
    }

    public CmtRegionalRr getRegional() {
        return regional;
    }

    public void setRegional(CmtRegionalRr regional) {
        this.regional = regional;
    }

    public int getSla() {
        return sla;
    }

    public void setSla(int sla) {
        this.sla = sla;
    }
}
