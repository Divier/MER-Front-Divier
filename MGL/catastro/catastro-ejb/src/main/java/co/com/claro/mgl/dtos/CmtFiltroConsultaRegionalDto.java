/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dtos;

import java.math.BigDecimal;

/**
 *
 * @author JPeña
 */
public class CmtFiltroConsultaRegionalDto {
    
    
    public BigDecimal idRegional;
    public String codigoRegional;
    public String nombreRegional;

    public BigDecimal getIdRegional() {
        return idRegional;
    }

    public void setIdRegional(BigDecimal idRegional) {
        this.idRegional = idRegional;
    }

    public String getCodigoRegional() {
        return codigoRegional;
    }

    public void setCodigoRegional(String codigoRegional) {
        this.codigoRegional = codigoRegional;
    }

    public String getNombreRegional() {
        return nombreRegional;
    }

    public void setNombreRegional(String nombreRegional) {
        this.nombreRegional = nombreRegional;
    }
    
    
    
    
}
