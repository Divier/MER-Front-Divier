/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dtos;

import java.math.BigDecimal;

/**
 *
 * @author cardenaslb
 */
public class EstadosOtCmDirDto {
    
    private BigDecimal idEstados;
    
    private String nombreEstado;

    public BigDecimal getIdEstados() {
        return idEstados;
    }

    public void setIdEstados(BigDecimal idEstados) {
        this.idEstados = idEstados;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }
    
    
    
}
