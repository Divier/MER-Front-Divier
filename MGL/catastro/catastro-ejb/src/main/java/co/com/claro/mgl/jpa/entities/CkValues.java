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
@XmlRootElement(name = "ckValues")
public class CkValues {

    /* Variables */
    @XmlElement
    private List<OpcionIdNombre> tiposDeViaPrinCK = new ArrayList<OpcionIdNombre>();
    @XmlElement 
    private List<OpcionIdNombre> letrasCK = new ArrayList<OpcionIdNombre>();
    @XmlElement 
    private List<OpcionIdNombre> bisCK = new ArrayList<OpcionIdNombre>();
    @XmlElement 
    private List<OpcionIdNombre> cardinalesCK = new ArrayList<OpcionIdNombre>();
    @XmlElement 
    private List<OpcionIdNombre> cruceCK = new ArrayList<OpcionIdNombre>();
    
    /**
     * Constructor de la clase
     */
    public CkValues() {

    }

    /* Getters and Setters */
    public List<OpcionIdNombre> getTiposDeViaPrinCK() {
        return tiposDeViaPrinCK;
    }

    public void setTiposDeViaPrinCK(List<OpcionIdNombre> tiposDeViaPrinCK) {
        this.tiposDeViaPrinCK = tiposDeViaPrinCK;
    }

    public List<OpcionIdNombre> getLetrasCK() {
        return letrasCK;
    }

    public void setLetrasCK(List<OpcionIdNombre> letrasCK) {
        this.letrasCK = letrasCK;
    }

    public List<OpcionIdNombre> getBisCK() {
        return bisCK;
    }

    public void setBisCK(List<OpcionIdNombre> bisCK) {
        this.bisCK = bisCK;
    }

    public List<OpcionIdNombre> getCardinalesCK() {
        return cardinalesCK;
    }

    public void setCardinalesCK(List<OpcionIdNombre> cardinalesCK) {
        this.cardinalesCK = cardinalesCK;
    }

    public List<OpcionIdNombre> getCruceCK() {
        return cruceCK;
    }

    public void setCruceCK(List<OpcionIdNombre> cruseCK) {
        this.cruceCK = cruseCK;
    }

}
