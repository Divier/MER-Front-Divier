/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dtos;

import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionDetalladaMgl;

/**
 *
 * @author espinosadiea
 */
public class HhppByNodoDto {
    private NodoMgl nodoConsulta;
    private HhppMgl homepassConsulta;
    private GeograficoPoliticoMgl geoPolCPobConsulta;
    private GeograficoPoliticoMgl geoPolCiuConsulta;
    private GeograficoPoliticoMgl geoPolDeparConsulta;
    private CmtDireccionDetalladaMgl dirDetallaConsulta;

    public NodoMgl getNodoConsulta() {
        return nodoConsulta;
    }

    public void setNodoConsulta(NodoMgl nodoConsulta) {
        this.nodoConsulta = nodoConsulta;
    }

    public HhppMgl getHomepassConsulta() {
        return homepassConsulta;
    }

    public void setHomepassConsulta(HhppMgl homepassConsulta) {
        this.homepassConsulta = homepassConsulta;
    }

    public GeograficoPoliticoMgl getGeoPolCPobConsulta() {
        return geoPolCPobConsulta;
    }

    public void setGeoPolCPobConsulta(GeograficoPoliticoMgl geoPolCPobConsulta) {
        this.geoPolCPobConsulta = geoPolCPobConsulta;
    }

    public GeograficoPoliticoMgl getGeoPolCiuConsulta() {
        return geoPolCiuConsulta;
    }

    public void setGeoPolCiuConsulta(GeograficoPoliticoMgl geoPolCiuConsulta) {
        this.geoPolCiuConsulta = geoPolCiuConsulta;
    }

    public GeograficoPoliticoMgl getGeoPolDeparConsulta() {
        return geoPolDeparConsulta;
    }

    public void setGeoPolDeparConsulta(GeograficoPoliticoMgl geoPolDeparConsulta) {
        this.geoPolDeparConsulta = geoPolDeparConsulta;
    }
    
    
    
    public CmtDireccionDetalladaMgl getDirDetallaConsulta() {
        return dirDetallaConsulta;
    }

    public void setDirDetallaConsulta(CmtDireccionDetalladaMgl dirDetallaConsulta) {
        this.dirDetallaConsulta = dirDetallaConsulta;
    }
    
    
}
