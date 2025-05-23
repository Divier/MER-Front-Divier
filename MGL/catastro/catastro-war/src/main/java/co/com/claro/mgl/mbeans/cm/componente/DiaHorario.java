/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.cm.componente;


import co.com.claro.mgl.jpa.entities.cm.CmtHorarioRestriccionMgl;
import co.com.claro.mgl.jpa.entities.cm.TipoRestriccion;
import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class DiaHorario implements Serializable {
    
    private final static long serialVersionUID = 7526472295622776150L;
    private CmtHorarioRestriccionMgl horarioRestriccionCm;
    private TipoRestriccion tipoRestriccion; 
    private String razonRestriccion; 
     
    
    public DiaHorario(CmtHorarioRestriccionMgl horarioRestriccionCm, TipoRestriccion tipoRestriccion, String razonRestriccion){
        this.horarioRestriccionCm = horarioRestriccionCm; 
        this.tipoRestriccion = tipoRestriccion; 
        this.razonRestriccion = razonRestriccion; 
    }
    

    public TipoRestriccion getTipoRestriccion() {
        return tipoRestriccion;
    }

    public void setTipoRestriccion(TipoRestriccion tipoRestriccion) {
        this.tipoRestriccion = tipoRestriccion;
    }

    public String getRazonRestriccion() {
        return razonRestriccion;
    }

    public void setRazonRestriccion(String razonRestriccion) {
        this.razonRestriccion = razonRestriccion;
    }
        
    public DiaHorario (){
        this.tipoRestriccion=null;
        this.razonRestriccion="";
    }

    public CmtHorarioRestriccionMgl getHorarioRestriccionCm() {
        return horarioRestriccionCm;
    }

    public void setHorarioRestriccionCm(CmtHorarioRestriccionMgl horarioRestriccionCm) {
        this.horarioRestriccionCm = horarioRestriccionCm;
    }

    
}
