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
public class SegmentoDto {
    private BigDecimal idSegmento;
    private String nombreSegmento;

    public BigDecimal getIdSegmento() {
        return idSegmento;
    }

    public void setIdSegmento(BigDecimal idSegmento) {
        this.idSegmento = idSegmento;
    }

    public String getNombreSegmento() {
        return nombreSegmento;
    }

    public void setNombreSegmento(String nombreSegmento) {
        this.nombreSegmento = nombreSegmento;
    }
    
    
    
    
}
