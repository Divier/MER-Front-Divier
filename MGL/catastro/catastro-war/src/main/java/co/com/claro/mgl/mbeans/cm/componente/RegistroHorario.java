/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.cm.componente;

import java.io.Serializable; 

/**
 *
 * @author Admin
 */
public class RegistroHorario implements Serializable{
    
    private static final long serialVersionUID = 7526472295622776146L;
    
    private String hora; 
    private DiaHorario lunes;
    private DiaHorario martes;
    private DiaHorario miercoles;
    private DiaHorario jueves;
    private DiaHorario viernes;
    private DiaHorario sabado;
    private DiaHorario domingo;

    public RegistroHorario(){
    }
    
    public String  getHoras() {
        return hora;
    }

    public void setHoras(String hora) {
        this.hora = hora;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public DiaHorario getLunes() {
        return lunes;
    }

    public void setLunes(DiaHorario lunes) {
        this.lunes = lunes;
    }

    public DiaHorario getMartes() {
        return martes;
    }

    public void setMartes(DiaHorario martes) {
        this.martes = martes;
    }

    public DiaHorario getMiercoles() {
        return miercoles;
    }

    public void setMiercoles(DiaHorario miercoles) {
        this.miercoles = miercoles;
    }

    public DiaHorario getJueves() {
        return jueves;
    }

    public void setJueves(DiaHorario jueves) {
        this.jueves = jueves;
    }

    public DiaHorario getViernes() {
        return viernes;
    }

    public void setViernes(DiaHorario viernes) {
        this.viernes = viernes;
    }

    public DiaHorario getSabado() {
        return sabado;
    }

    public void setSabado(DiaHorario sabado) {
        this.sabado = sabado;
    }

    public DiaHorario getDomingo() {
        return domingo;
    }

    public void setDomingo(DiaHorario domingo) {
        this.domingo = domingo;
    }
    
    
}
