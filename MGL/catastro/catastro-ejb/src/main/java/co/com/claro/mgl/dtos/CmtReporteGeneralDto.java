/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dtos;

/**
 *
 * @author cardenaslb
 */
public class CmtReporteGeneralDto {
    
     private String idSol;
    private int cantSol;
    private String estadoSol;
    private String fechaCreacionSol;
    private String regional ;
     private String descripcion ;

    public int getCantSol() {
        return cantSol;
    }

    public void setCantSol(int cantSol) {
        this.cantSol = cantSol;
    }

    public String getEstadoSol() {
        return estadoSol;
    }

    public void setEstadoSol(String estadoSol) {
        this.estadoSol = estadoSol;
    }

    public String getFechaCreacionSol() {
        return fechaCreacionSol;
    }

    public void setFechaCreacionSol(String fechaCreacionSol) {
        this.fechaCreacionSol = fechaCreacionSol;
    }

    public String getRegional() {
        return regional;
    }

    public void setRegional(String regional) {
        this.regional = regional;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIdSol() {
        return idSol;
    }

    public void setIdSol(String idSol) {
        this.idSol = idSol;
    }

   
    
    
    
}
