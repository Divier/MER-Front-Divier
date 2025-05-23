/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dtos;

/**
 *
 * @author valbuenayf
 */
public class DireccionSubDireccionDto {

    private String direccion;
    private String subDireccion;
    private Integer direccionSubDireccion;
    private String idTipoDireccion;

    public DireccionSubDireccionDto() {
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getSubDireccion() {
        return subDireccion;
    }

    public void setSubDireccion(String subDireccion) {
        this.subDireccion = subDireccion;
    }

    public Integer getDireccionSubDireccion() {
        return direccionSubDireccion;
    }

    public void setDireccionSubDireccion(Integer direccionSubDireccion) {
        this.direccionSubDireccion = direccionSubDireccion;
    }

    public String getIdTipoDireccion() {
        return idTipoDireccion;
    }

    public void setIdTipoDireccion(String idTipoDireccion) {
        this.idTipoDireccion = idTipoDireccion;
    }
}
