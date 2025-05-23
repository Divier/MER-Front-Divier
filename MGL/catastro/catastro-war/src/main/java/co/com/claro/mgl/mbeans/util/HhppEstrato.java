/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.util;

/**
 *
 * @author cardenaslb
 */
public class HhppEstrato {

    private int idSubEdificio;
    private int estratoHhpp;
   
    private int numeroHhpp;
    private int totalHhpp;

    public HhppEstrato() {

    }

    public int getEstratoHhpp() {
        return estratoHhpp;
    }

    public void setEstratoHhpp(int estratoHhpp) {
        this.estratoHhpp = estratoHhpp;
    }



    public int getNumeroHhpp() {
        return numeroHhpp;
    }

    public void setNumeroHhpp(int numeroHhpp) {
        this.numeroHhpp = numeroHhpp;
    }

    public int getIdSubEdificio() {
        return idSubEdificio;
    }

    public void setIdSubEdificio(int idSubEdificio) {
        this.idSubEdificio = idSubEdificio;
    }

    public int getTotalHhpp() {
        return totalHhpp;
    }

    public void setTotalHhpp(int totalHhpp) {
        this.totalHhpp = totalHhpp;
    }

}
