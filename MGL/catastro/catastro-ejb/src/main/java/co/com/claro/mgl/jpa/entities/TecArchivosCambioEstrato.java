/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities;

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
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bocanegravm
 */
@Entity
@Table(name = "TEC_ARCHIVOS_CAMBIO_ESTRATO", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TecArchivosCambioEstrato.findUrlsByIdDireccionDetalladaSolicitud",
        query = "SELECT c FROM TecArchivosCambioEstrato c WHERE c.solicitudObj.direccionDetallada.direccionDetalladaId =:direccionDetalladaId"),
    @NamedQuery(name = "TecArchivosCambioEstrato.findUrlsByIdSolicitud", 
        query = "SELECT c FROM TecArchivosCambioEstrato c WHERE c.solicitudObj = :solicitudObj")})

public class TecArchivosCambioEstrato implements Serializable {
    
    
     private static final long serialVersionUID = 1L;
     
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "TecArchivosCambioEstrato.TecArchivosCambioEstratoSeq",
            sequenceName = "MGL_SCHEME.TEC_ARCHIVOS_CAMBIO_ESTRATO_SQ", allocationSize = 1)
    @GeneratedValue(generator = "TecArchivosCambioEstrato.TecArchivosCambioEstratoSeq")
    @Column(name = "ID_TEC_ARCHIVOS_CAMBIO_ESTRATO", nullable = false)
    private BigDecimal idArchivoscambioEstrato; 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SOL_TEC_HABILITADA_ID", referencedColumnName = "SOL_TEC_HABILITADA_ID", nullable = false)
    private  Solicitud solicitudObj;
    @Size(min = 1, max = 500)
    @Column(name = "URL_ARCHIVO_SOPORTE")
    private String urlArchivoSoporte;
    @NotNull
    @Column(name = "ESTADO_REGISTRO", columnDefinition = "default 1")
    private int estadoRegistro;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PERFIL_CREACION")
    private int perfilCreacion;
    @Column(name = "FECHA_EDICION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEdicion;
    @Size(min = 1, max = 20)
    @Column(name = "USUARIO_EDICION")
    private String usuarioEdicion;
    @Basic(optional = false)
    @Column(name = "PERFIL_EDICION")
    private int perfilEdicion;
    @Column(name = "NOMBRE_ARCHIVO")
    private String nombreArchivo;

    public BigDecimal getIdArchivoscambioEstrato() {
        return idArchivoscambioEstrato;
    }

    public void setIdArchivoscambioEstrato(BigDecimal idArchivoscambioEstrato) {
        this.idArchivoscambioEstrato = idArchivoscambioEstrato;
    }

    public Solicitud getSolicitudObj() {
        return solicitudObj;
    }

    public void setSolicitudObj(Solicitud solicitudObj) {
        this.solicitudObj = solicitudObj;
    }

    public String getUrlArchivoSoporte() {
        return urlArchivoSoporte;
    }

    public void setUrlArchivoSoporte(String urlArchivoSoporte) {
        this.urlArchivoSoporte = urlArchivoSoporte;
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

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

}