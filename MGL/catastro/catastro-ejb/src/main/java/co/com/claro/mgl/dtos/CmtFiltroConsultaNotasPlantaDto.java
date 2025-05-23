/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dtos;

import java.math.BigDecimal;

/**
 *
 * @author bocanegravm
 */
public class CmtFiltroConsultaNotasPlantaDto {

    public BigDecimal noteconfiguracionplantaid;
    public String nota;

    public BigDecimal getNoteconfiguracionplantaid() {
        return noteconfiguracionplantaid;
    }

    public void setNoteconfiguracionplantaid(BigDecimal noteconfiguracionplantaid) {
        this.noteconfiguracionplantaid = noteconfiguracionplantaid;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    
}
