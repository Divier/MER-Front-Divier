/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.cmas400.ejb.request;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@XmlRootElement
public class RequestDataManttoInformacionBarrios {
    
    @XmlElement
    private String nombreUsuario = "";
    @XmlElement
    private String codigoBarrioB = "";
    @XmlElement
    private String nombreBarrioB = "";
    @XmlElement
    private String codigoDivisionB = "";
    @XmlElement
    private String codigoComunidadB = "";
    @XmlElement
    private String codigo = "";
    @XmlElement
    private String descripcion = "";
    @XmlElement
    private String codigoDivision = "";
    @XmlElement
    private String nombreDivision = "";
    @XmlElement
    private String codigoComunidad = "";
    @XmlElement
    private String nombreComunidad = "";

    public String getCodigoBarrioB() {
        return codigoBarrioB;
    }

    public void setCodigoBarrioB(String codigoBarrioB) {
        this.codigoBarrioB = codigoBarrioB;
    }

    public String getNombreBarrioB() {
        return nombreBarrioB;
    }

    public void setNombreBarrioB(String nombreBarrioB) {
        this.nombreBarrioB = nombreBarrioB;
    }

    public String getCodigoDivisionB() {
        return codigoDivisionB;
    }

    public void setCodigoDivisionB(String codigoDivisionB) {
        this.codigoDivisionB = codigoDivisionB;
    }

    public String getCodigoComunidadB() {
        return codigoComunidadB;
    }

    public void setCodigoComunidadB(String codigoComunidadB) {
        this.codigoComunidadB = codigoComunidadB;
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

    public String getCodigoDivision() {
        return codigoDivision;
    }

    public void setCodigoDivision(String codigoDivision) {
        this.codigoDivision = codigoDivision;
    }

    public String getNombreDivision() {
        return nombreDivision;
    }

    public void setNombreDivision(String nombreDivision) {
        this.nombreDivision = nombreDivision;
    }

    public String getCodigoComunidad() {
        return codigoComunidad;
    }

    public void setCodigoComunidad(String codigoComunidad) {
        this.codigoComunidad = codigoComunidad;
    }

    public String getNombreComunidad() {
        return nombreComunidad;
    }

    public void setNombreComunidad(String nombreComunidad) {
        this.nombreComunidad = nombreComunidad;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    
}
