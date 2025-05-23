/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities.cm;

import co.com.claro.mgl.jpa.entities.Solicitud;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Juan David Hernandez
 */
@Entity
@Table(name = "TEC_TIEMPO_SOLICITUD", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmtTiempoSolicitudMgl.findAll", query = "SELECT c FROM CmtTiempoSolicitudMgl c WHERE c.estadoRegistro=:estado")})
public class CmtTiempoSolicitudMgl implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "CmtTiempoSolicitudMgl.CmtTiempoSolicitudMglSq",
            sequenceName = "MGL_SCHEME.TEC_TIEMPO_SOLICITUD_SQ", allocationSize = 1)
    @GeneratedValue(generator = "CmtTiempoSolicitudMgl.CmtTiempoSolicitudMglSq")
    @Column(name = "TIEMPO_SOLICITUD_ID", nullable = false)
    private BigDecimal tiempoSolicitudId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SOL_TEC_HABILITADA_ID")
    private Solicitud solicitudObj;
    @NotNull
    @Column(name = "TIEMPO_SOLICITUD", length = 20)
    private String tiempoSolicitud;
    @NotNull    
    @Column(name = "TIEMPO_ESPERA",  length = 20)
    private String tiempoEspera;
    @NotNull    
    @Column(name = "TIEMPO_GESTION", length = 20)
    private String tiempoGestion;
    @NotNull    
    @Column(name = "TIEMPO_TOTAL", length = 20)
    private String tiempoTotal;
    @NotNull
    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro;
    @NotNull
    @Column(name = "FECHA_CREACION", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @NotNull
    @Column(name = "USUARIO_CREACION", length = 20)
    private String usuarioCreacion;
    @NotNull
    @Column(name = "PERFIL_CREACION")
    private int perfilCreacion;
    @NotNull    
    @Column(name = "FECHA_EDICION")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaEdicion;
    @NotNull    
    @Column(name = "USUARIO_EDICION" ,length = 20)
    private String usuarioEdicion;
    @NotNull    
    @Column(name = "PERFIL_EDICION")
    private int perfilEdicion;
    @NotNull    
    @Column(name = "ARCHIVO_SOPORTE",  length = 200)
    private String archivoSoporte;

    public BigDecimal getTiempoSolicitudId() {
        return tiempoSolicitudId;
    }

    public void setTiempoSolicitudId(BigDecimal tiempoSolicitudId) {
        this.tiempoSolicitudId = tiempoSolicitudId;
    }

    public Solicitud getSolicitudObj() {
        return solicitudObj;
    }

    public void setSolicitudObj(Solicitud solicitudObj) {
        this.solicitudObj = solicitudObj;
    }

    public String getTiempoSolicitud() {
        return tiempoSolicitud;
    }

    public void setTiempoSolicitud(String tiempoSolicitud) {
        this.tiempoSolicitud = tiempoSolicitud;
    }

    public String getTiempoEspera() {
        return tiempoEspera;
    }

    public void setTiempoEspera(String tiempoEspera) {
        this.tiempoEspera = tiempoEspera;
    }

    public String getTiempoGestion() {
        return tiempoGestion;
    }

    public void setTiempoGestion(String tiempoGestion) {
        this.tiempoGestion = tiempoGestion;
    }

    public String getTiempoTotal() {
        return tiempoTotal;
    }

    public void setTiempoTotal(String tiempoTotal) {
        this.tiempoTotal = tiempoTotal;
    }
   
    public int getEstadoRegistro() {
        return estadoRegistro;
    }

     public void setEstadoRegistro(int estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

     public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public int getPerfilCreacion() {
        return perfilCreacion;
    }

    public void setPerfilCreacion(int perfilCreacion) {
        this.perfilCreacion = perfilCreacion;
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

     public int getPerfilEdicion() {
        return perfilEdicion;
    }

    public void setPerfilEdicion(int perfilEdicion) {
        this.perfilEdicion = perfilEdicion;
    }

  
    @Override
    public CmtTiempoSolicitudMgl clone() throws CloneNotSupportedException {
        return (CmtTiempoSolicitudMgl) super.clone();
    }  

    public String getArchivoSoporte() {
        return archivoSoporte;
    }

    public void setArchivoSoporte(String archivoSoporte) {
        this.archivoSoporte = archivoSoporte;
    }

}
