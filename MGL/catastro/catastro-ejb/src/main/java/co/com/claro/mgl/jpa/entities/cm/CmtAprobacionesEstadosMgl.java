/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities.cm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bocanegravm
 */
@Entity
@Table(name = "CMT_APROBACIONES_ESTADOS", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmtAprobacionesEstadosMgl.findByOtAndFlujo", 
        query = "SELECT c FROM CmtAprobacionesEstadosMgl c WHERE c.OtObj = :OtObj  "
        + " AND c.detalleFlujoObj = :detalleFlujoObj AND c.estadoRegistro = 1")})

public class CmtAprobacionesEstadosMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "CmtAprobacionesEstadosMgl.CmtAprobacionesEstadosMglSq",
            sequenceName = "MGL_SCHEME.CMT_APROBACIONES_ESTADOS_SQ",
            allocationSize = 1)
    @GeneratedValue(generator
            = "CmtAprobacionesEstadosMgl.CmtAprobacionesEstadosMglSq")
    
    @Column(name = "ID_APROBACIONES_ESTADO")
    private BigDecimal idAprobacionesEstados;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OT_ID")
    private CmtOrdenTrabajoMgl OtObj;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DETALLEFLUJO_ID")
    private CmtDetalleFlujoMgl detalleFlujoObj;

    @Column(name = "USUARIO_APROBACION")
    private String usuarioAprobacion;
    
    @Column(name = "FECHA_APROBACION", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaAprobacion;

    @Column(name = "FECHA_CREACION", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    @Column(name = "USUARIO_CREACION", nullable = true, length = 200)
    private String usuarioCreacion;

    @Column(name = "PERFIL_CREACION")
    private int perfilCreacion;

    @Column(name = "FECHA_EDICION", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaEdicion;

    @Column(name = "USUARIO_EDICION", nullable = true, length = 200)
    private String usuarioEdicion;

    @Column(name = "PERFIL_EDICION")
    private int perfilEdicion;

    @NotNull
    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro;

    public BigDecimal getIdAprobacionesEstados() {
        return idAprobacionesEstados;
    }

    public void setIdAprobacionesEstados(BigDecimal idAprobacionesEstados) {
        this.idAprobacionesEstados = idAprobacionesEstados;
    }

    public CmtOrdenTrabajoMgl getOtObj() {
        return OtObj;
    }

    public void setOtObj(CmtOrdenTrabajoMgl OtObj) {
        this.OtObj = OtObj;
    }

    public CmtDetalleFlujoMgl getDetalleFlujoObj() {
        return detalleFlujoObj;
    }

    public void setDetalleFlujoObj(CmtDetalleFlujoMgl detalleFlujoObj) {
        this.detalleFlujoObj = detalleFlujoObj;
    }

    public String getUsuarioAprobacion() {
        return usuarioAprobacion;
    }

    public void setUsuarioAprobacion(String usuarioAprobacion) {
        this.usuarioAprobacion = usuarioAprobacion;
    }

    public Date getFechaAprobacion() {
        return fechaAprobacion;
    }

    public void setFechaAprobacion(Date fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
    }
  
    /**
     * @return the fechaCreacion
     */
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * @param fechaCreacion the fechaCreacion to set
     */
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * @return the usuarioCreacion
     */
    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    /**
     * @param usuarioCreacion the usuarioCreacion to set
     */
    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    /**
     * @return the perfilCreacion
     */
    public int getPerfilCreacion() {
        return perfilCreacion;
    }

    /**
     * @param perfilCreacion the perfilCreacion to set
     */
    public void setPerfilCreacion(int perfilCreacion) {
        this.perfilCreacion = perfilCreacion;
    }

    /**
     * @return the fechaEdicion
     */
    public Date getFechaEdicion() {
        return fechaEdicion;
    }

    /**
     * @param fechaEdicion the fechaEdicion to set
     */
    public void setFechaEdicion(Date fechaEdicion) {
        this.fechaEdicion = fechaEdicion;
    }

    /**
     * @return the usuarioEdicion
     */
    public String getUsuarioEdicion() {
        return usuarioEdicion;
    }

    /**
     * @param usuarioEdicion the usuarioEdicion to set
     */
    public void setUsuarioEdicion(String usuarioEdicion) {
        this.usuarioEdicion = usuarioEdicion;
    }

    /**
     * @return the perfilEdicion
     */
    public int getPerfilEdicion() {
        return perfilEdicion;
    }

    /**
     * @param perfilEdicion the perfilEdicion to set
     */
    public void setPerfilEdicion(int perfilEdicion) {
        this.perfilEdicion = perfilEdicion;
    }

    /**
     * @return the estadoRegistro
     */
    public int getEstadoRegistro() {
        return estadoRegistro;
    }

    /**
     * @param estadoRegistro the estadoRegistro to set
     */
    public void setEstadoRegistro(int estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

}
