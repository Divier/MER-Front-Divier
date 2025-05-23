/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author camargomf
 */
@XmlRootElement(name = "bmValues")
public class BmValues {

    /* Variables */
    @XmlElement 
    private List<OpcionIdNombre> tipoConjuntoBm = new ArrayList<OpcionIdNombre>();
    @XmlElement  
    private List<OpcionIdNombre> subdivision1Bm = new ArrayList<OpcionIdNombre>();
    @XmlElement  
    private List<OpcionIdNombre> subdivision2Bm = new ArrayList<OpcionIdNombre>();
    @XmlElement  
    private List<OpcionIdNombre> subdivision3Bm = new ArrayList<OpcionIdNombre>();
    @XmlElement  
    private List<OpcionIdNombre> subdivision4Bm = new ArrayList<OpcionIdNombre>();

    /* Getters and Setters */
    public List<OpcionIdNombre> getTipoConjuntoBm() {
        return tipoConjuntoBm;
    }

    public void setTipoConjuntoBm(List<OpcionIdNombre> tipoConjuntoBm) {
        this.tipoConjuntoBm = tipoConjuntoBm;
    }

    public List<OpcionIdNombre> getSubdivision1Bm() {
        return subdivision1Bm;
    }

    public void setSubdivision1Bm(List<OpcionIdNombre> subdivision1Bm) {
        this.subdivision1Bm = subdivision1Bm;
    }

    public List<OpcionIdNombre> getSubdivision2Bm() {
        return subdivision2Bm;
    }

    public void setSubdivision2Bm(List<OpcionIdNombre> subdivision2Bm) {
        this.subdivision2Bm = subdivision2Bm;
    }

    public List<OpcionIdNombre> getSubdivision3Bm() {
        return subdivision3Bm;
    }

    public void setSubdivision3Bm(List<OpcionIdNombre> subdivision3Bm) {
        this.subdivision3Bm = subdivision3Bm;
    }

    public List<OpcionIdNombre> getSubdivision4Bm() {
        return subdivision4Bm;
    }

    public void setSubdivision4Bm(List<OpcionIdNombre> subdivision4Bm) {
        this.subdivision4Bm = subdivision4Bm;
    }

}
