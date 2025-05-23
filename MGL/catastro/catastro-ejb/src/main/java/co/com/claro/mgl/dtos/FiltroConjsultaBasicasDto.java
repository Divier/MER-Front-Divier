/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dtos;

import java.math.BigDecimal;

/**
 *
 * @author ADMIN
 */
public class FiltroConjsultaBasicasDto {
   private BigDecimal idTipoBasica=null;
   private String codigo="";
   private String nombre="";
   private String abreviatura="";
   private String decripcion="";
   private String estado="";

    public BigDecimal getIdTipoBasica() {
        return idTipoBasica;
    }

    public void setIdTipoBasica(BigDecimal idTipoBasica) {
        this.idTipoBasica = idTipoBasica;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public String getDecripcion() {
        return decripcion;
    }

    public void setDecripcion(String decripcion) {
        this.decripcion = decripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
   
   
}
