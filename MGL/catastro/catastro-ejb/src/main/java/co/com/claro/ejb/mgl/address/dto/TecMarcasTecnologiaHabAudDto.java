/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.ejb.mgl.address.dto;

import co.com.claro.mgl.jpa.entities.TecMarcasTecnologiaHabAud;
import java.io.Serializable;

/**
 * @author espinosadiea
 */
public class TecMarcasTecnologiaHabAudDto implements Serializable{
    private TecMarcasTecnologiaHabAud tecmarca;
    private String dataAnterior;
    private String dataNueva;
    private String justificacion;

    public TecMarcasTecnologiaHabAud getTecmarca() {
        return tecmarca;
    }

    public void setTecmarca(TecMarcasTecnologiaHabAud tecmarca) {
        this.tecmarca = tecmarca;
    }

    public String getDataAnterior() {
        return dataAnterior;
    }

    public void setDataAnterior(String dataAnterior) {
        this.dataAnterior = dataAnterior;
    }

    public String getDataNueva() {
        return dataNueva;
    }

    public void setDataNueva(String dataNueva) {
        this.dataNueva = dataNueva;
    }

    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }
    
}
