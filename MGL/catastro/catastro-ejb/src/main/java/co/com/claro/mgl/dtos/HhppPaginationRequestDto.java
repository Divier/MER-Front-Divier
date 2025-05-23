/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dtos;

/**
 *
 * @author Orlando Velasquez
 */
public class HhppPaginationRequestDto {

    private String fechaInicio;
    private String horaInicio;
    private String fechaFin;
    private String horaFin;
    private String cantidadRegistrosSolicitados;

    public HhppPaginationRequestDto() {
    }
    
    public HhppPaginationRequestDto(String fechaInicio, String horaInicio, String fechaFin, String horaFin, String cantidadRegistrosSolicitados) {
        this.fechaInicio = fechaInicio;
        this.horaInicio = horaInicio;
        this.fechaFin = fechaFin;
        this.horaFin = horaFin;
        this.cantidadRegistrosSolicitados = cantidadRegistrosSolicitados;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public String getCantidadRegistrosSolicitados() {
        return cantidadRegistrosSolicitados;
    }

    public void setCantidadRegistrosSolicitados(String idUltimoRegistro) {
        this.cantidadRegistrosSolicitados = idUltimoRegistro;
    }

}
