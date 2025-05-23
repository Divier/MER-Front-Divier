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
public class TipoOtCmDirDto {
    
    private BigDecimal idTipoOt;
    
    private String nombre;
    
    private String codigoBasica;

    public BigDecimal getIdTipoOt() {
        return idTipoOt;
    }

    public void setIdTipoOt(BigDecimal idTipoOt) {
        this.idTipoOt = idTipoOt;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigoBasica() {
        return codigoBasica;
    }

    public void setCodigoBasica(String codigoBasica) {
        this.codigoBasica = codigoBasica;
    }
    
    
    
    
}
