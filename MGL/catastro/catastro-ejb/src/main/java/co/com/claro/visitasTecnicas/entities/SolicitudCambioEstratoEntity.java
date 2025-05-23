package co.com.claro.visitasTecnicas.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author user
 */
public class SolicitudCambioEstratoEntity implements Serializable {
    
    private BigDecimal id;
    private String usuarioCreacion;
    private BigDecimal idUsuario;        
    private String nombreDocumento; 
    private Date fechaIngreso;          
    private String fechaIngresoStr;          
    private BigDecimal idSolicitud;
    private String estado;

    public SolicitudCambioEstratoEntity() {
    }

    public SolicitudCambioEstratoEntity(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    
    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public BigDecimal getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(BigDecimal idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreDocumento() {
        return nombreDocumento;
    }

    public void setNombreDocumento(String nombreDocumento) {
        this.nombreDocumento = nombreDocumento;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getFechaIngresoStr() {
        return fechaIngresoStr;
    }

    public void setFechaIngresoStr(String fechaIngresoStr) {
        this.fechaIngresoStr = fechaIngresoStr;
    }

    public BigDecimal getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(BigDecimal idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
}
