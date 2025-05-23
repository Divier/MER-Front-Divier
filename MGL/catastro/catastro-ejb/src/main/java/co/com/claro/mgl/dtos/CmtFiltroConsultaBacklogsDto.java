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
public class CmtFiltroConsultaBacklogsDto {
    
    
   private BigDecimal idBacklog=null;
   private String tipoGestionBacklog="";
   private String resGestionBacklog="";
   private String fechaGestion="";
   private String usuarioGestion="";
   private String notaBacklog="";

    public BigDecimal getIdBacklog() {
        return idBacklog;
    }

    public void setIdBacklog(BigDecimal idBacklog) {
        this.idBacklog = idBacklog;
    }

    public String getTipoGestionBacklog() {
        return tipoGestionBacklog;
    }

    public void setTipoGestionBacklog(String tipoGestionBacklog) {
        this.tipoGestionBacklog = tipoGestionBacklog;
    }

    public String getResGestionBacklog() {
        return resGestionBacklog;
    }

    public void setResGestionBacklog(String resGestionBacklog) {
        this.resGestionBacklog = resGestionBacklog;
    }

    public String getFechaGestion() {
        return fechaGestion;
    }

    public void setFechaGestion(String fechaGestion) {
        this.fechaGestion = fechaGestion;
    }

    public String getUsuarioGestion() {
        return usuarioGestion;
    }

    public void setUsuarioGestion(String usuarioGestion) {
        this.usuarioGestion = usuarioGestion;
    }

    public String getNotaBacklog() {
        return notaBacklog;
    }

    public void setNotaBacklog(String notaBacklog) {
        this.notaBacklog = notaBacklog;
    }
   
   
   
    
}
