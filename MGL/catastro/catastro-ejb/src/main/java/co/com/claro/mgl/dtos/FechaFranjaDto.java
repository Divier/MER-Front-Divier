/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dtos;

import java.util.List;

/**
 *
 * @author bocanegravm
 */
public class FechaFranjaDto {
    
    private String fechaDisponible;
    private List<String> franjasDisponiblesBloques;


    public String getFechaDisponible() {
        return fechaDisponible;
    }

    public void setFechaDisponible(String fechaDisponible) {
        this.fechaDisponible = fechaDisponible;
    }

    public List<String> getFranjasDisponiblesBloques() {
        return franjasDisponiblesBloques;
    }

    public void setFranjasDisponiblesBloques(List<String> franjasDisponiblesBloques) {
        this.franjasDisponiblesBloques = franjasDisponiblesBloques;
    }

}
