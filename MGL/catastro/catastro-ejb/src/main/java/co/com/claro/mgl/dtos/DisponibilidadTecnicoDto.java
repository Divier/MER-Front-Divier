/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dtos;

import co.com.claro.ofscCore.findMatchingResources.Resource;
import java.util.List;

/**
 *
 * @author bocanegravm
 */
public class DisponibilidadTecnicoDto {
    
    
    private Resource tecnico;
    private List<FechaFranjaDto> fechaFranjaDtos;
    

    public Resource getTecnico() {
        return tecnico;
    }

    public void setTecnico(Resource tecnico) {
        this.tecnico = tecnico;
    }

    public List<FechaFranjaDto> getFechaFranjaDtos() {
        return fechaFranjaDtos;
    }

    public void setFechaFranjaDtos(List<FechaFranjaDto> fechaFranjaDtos) {
        this.fechaFranjaDtos = fechaFranjaDtos;
    }

}
