/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.cmas400.ejb.request;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author camargomf
 */
@XmlRootElement
public class RequestDataManttoEstadoEdificio {

    @XmlElement
    private String nombreUsuario = "";
    @XmlElement
    private String codigo = "";
    @XmlElement
    private String descripcion = "";
    @XmlElement
    private String estado = "";
    @XmlElement
    private String validarEstado = "";
    @XmlElement
    private String excepcionRadiografia = "";

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getValidarEstado() {
        return validarEstado;
    }

    public void setValidarEstado(String validarEstado) {
        this.validarEstado = validarEstado;
    }

    public String getExcepcionRadiografia() {
        return excepcionRadiografia;
    }

    public void setExcepcionRadiografia(String excepcionRadiografia) {
        this.excepcionRadiografia = excepcionRadiografia;
    }
}
