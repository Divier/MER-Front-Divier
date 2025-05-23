package co.com.claro.mgl.jpa.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Clase entidad de la tabla CMT_SOLICITUD_NODO_CUADRANTE 
 * Permite mapear los campos de la tabla
 *
 * @author Divier Casas
 * @version 1.0
 */
@Entity
@Table(name = "CMT_SOLICITUD_NODO_CUADRANTE", schema = "MGL_SCHEME")
@XmlRootElement
public class CmtSolicitudNodoCuadrante implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private BigDecimal solicitudId;
    private String codigoNodo;
    private String cuadranteId;
    private String tipoSolicitud;
    private Integer estadoSolicitud;
    private Integer estadoRegistro;
    private Integer resultadoGestion;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    private String usuraioCreacion;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaEdicion;
    private String usuarioEdicion;
    private String disponibilidadGestion;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCierreSol;
    private String email;

    public BigDecimal getSolicitudId() {
        return solicitudId;
    }

    public void setSolicitudId(BigDecimal solicitudId) {
        this.solicitudId = solicitudId;
    }

    public String getCodigoNodo() {
        return codigoNodo;
    }

    public void setCodigoNodo(String codigoNodo) {
        this.codigoNodo = codigoNodo;
    }

    public String getCuadranteId() {
        return cuadranteId;
    }

    public void setCuadranteId(String cuadranteId) {
        this.cuadranteId = cuadranteId;
    }

    public String getTipoSolicitud() {
        return tipoSolicitud;
    }

    public void setTipoSolicitud(String tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    public Integer getEstadoSolicitud() {
        return estadoSolicitud;
    }

    public void setEstadoSolicitud(Integer estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }

    public Integer getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(Integer estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    public Integer getResultadoGestion() {
        return resultadoGestion;
    }

    public void setResultadoGestion(Integer resultadoGestion) {
        this.resultadoGestion = resultadoGestion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getUsuraioCreacion() {
        return usuraioCreacion;
    }

    public void setUsuraioCreacion(String usuraioCreacion) {
        this.usuraioCreacion = usuraioCreacion;
    }

    public Date getFechaEdicion() {
        return fechaEdicion;
    }

    public void setFechaEdicion(Date fechaEdicion) {
        this.fechaEdicion = fechaEdicion;
    }

    public String getUsuarioEdicion() {
        return usuarioEdicion;
    }

    public void setUsuarioEdicion(String usuarioEdicion) {
        this.usuarioEdicion = usuarioEdicion;
    }

    public String getDisponibilidadGestion() {
        return disponibilidadGestion;
    }

    public void setDisponibilidadGestion(String disponibilidadGestion) {
        this.disponibilidadGestion = disponibilidadGestion;
    }

    public Date getFechaCierreSol() {
        return fechaCierreSol;
    }

    public void setFechaCierreSol(Date fechaCierreSol) {
        this.fechaCierreSol = fechaCierreSol;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "CmtSolicitudNodoCuadrante{" + "solicitudId=" + solicitudId + ", codigoNodo=" + codigoNodo + ", cuadranteId=" + cuadranteId + ", tipoSolicitud=" + tipoSolicitud + ", estadoSolicitud=" + estadoSolicitud + ", estadoRegistro=" + estadoRegistro + ", resultadoGestion=" + resultadoGestion + ", fechaCreacion=" + fechaCreacion + ", usuraioCreacion=" + usuraioCreacion + ", fechaEdicion=" + fechaEdicion + ", usuarioEdicion=" + usuarioEdicion + ", disponibilidadGestion=" + disponibilidadGestion + ", fechaCierreSol=" + fechaCierreSol + ", email=" + email + '}';
    }
}
