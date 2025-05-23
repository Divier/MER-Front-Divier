/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.cmas400.ejb.request;

import java.io.Serializable;

/**
 *
 * @author aleal
 */
public class MantenimientoBasicoRRMunicipioCentroPobladoDaneRequest implements Serializable{
    
    private String iduser;
    private String cddane;
    private String dspobld;
    private String cddepto;
    private String dsdepto;
    private String cdmpio;
    private String dsmpio;
    private String cdclase;
    private String nummanz;
    private String anocrea;
    private String estvig;

    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    public String getCddane() {
        return cddane;
    }

    public void setCddane(String cddane) {
        this.cddane = cddane;
    }

    public String getDspobld() {
        return dspobld;
    }

    public void setDspobld(String dspobld) {
        this.dspobld = dspobld;
    }

    public String getCddepto() {
        return cddepto;
    }

    public void setCddepto(String cddepto) {
        this.cddepto = cddepto;
    }

    public String getDsdepto() {
        return dsdepto;
    }

    public void setDsdepto(String dsdepto) {
        this.dsdepto = dsdepto;
    }

    public String getCdmpio() {
        return cdmpio;
    }

    public void setCdmpio(String cdmpio) {
        this.cdmpio = cdmpio;
    }

    public String getDsmpio() {
        return dsmpio;
    }

    public void setDsmpio(String dsmpio) {
        this.dsmpio = dsmpio;
    }

    public String getCdclase() {
        return cdclase;
    }

    public void setCdclase(String cdclase) {
        this.cdclase = cdclase;
    }

    public String getNummanz() {
        return nummanz;
    }

    public void setNummanz(String nummanz) {
        this.nummanz = nummanz;
    }

    public String getAnocrea() {
        return anocrea;
    }

    public void setAnocrea(String anocrea) {
        this.anocrea = anocrea;
    }

    public String getEstvig() {
        return estvig;
    }

    public void setEstvig(String estvig) {
        this.estvig = estvig;
    }

    
}
