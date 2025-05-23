package co.com.claro.mgl.jpa.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Clase de entidad para la interacción con la tabla TEC_ARCH_ESCALAMIENTOS_HHPP
 * guarda la ruta y el nombre de los archivos para escalamaniento hhpp
 *
 * @author José René Miranda de la O
 */
@Entity
@Table(name = "TEC_ARCH_ESCALAMIENTOS_HHPP", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "TecArchivosEscalamientosHHPP.findUrlsByIdDireccionDetalladaSolicitud",
                query = "SELECT c FROM TecArchivosEscalamientosHHPP c WHERE c.solicitudObj.direccionDetallada.direccionDetalladaId =:direccionDetalladaId"),
        @NamedQuery(name = "TecArchivosEscalamientosHHPP.findUrlsByIdSolicitud",
                query = "SELECT c FROM TecArchivosEscalamientosHHPP c WHERE c.solicitudObj = :solicitudObj"),
        @NamedQuery(name = "TecArchivosEscalamientosHHPP.findUrlsByIdSolicitudAndOrigen",
                query = "SELECT c FROM TecArchivosEscalamientosHHPP c WHERE c.solicitudObj = :solicitudObj AND c.origen = :origen AND (c.tecObservacionSolicitudHHPP = :observacion OR c.tecObservacionSolicitudHHPP IS NULL)")})
public class TecArchivosEscalamientosHHPP implements Serializable {


    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "TecArchivosEscalamientosHHPP.TecArchivosEscalamientosHHPPSeq",
            sequenceName = "MGL_SCHEME.TEC_ARCH_ESCALAMIENTOS_HHPP_SQ", allocationSize = 1)
    @GeneratedValue(generator = "TecArchivosEscalamientosHHPP.TecArchivosEscalamientosHHPPSeq")
    @Column(name = "ID_TEC_ARCH_ESCALAMIENTOS_HHPP", nullable = false)
    private BigDecimal idArchivosEscalamientosHHPP;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SOL_TEC_HABILITADA_ID", referencedColumnName = "SOL_TEC_HABILITADA_ID", nullable = false)
    private Solicitud solicitudObj;
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
    @Column(name = "ORIGEN")
    private String origen;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TEC_OBS_SOL_HHPP", referencedColumnName = "ID_TEC_OBS_SOL_HHPP")
    private TecObservacionSolicitudHHPP tecObservacionSolicitudHHPP;

    /**
     * @return the idArchivosEscalamientosHHPP
     */
    public BigDecimal getIdArchivosEscalamientosHHPP() {
        return idArchivosEscalamientosHHPP;
    }

    /**
     * @param idArchivosEscalamientosHHPP the idArchivosEscalamientosHHPP to set
     */
    public void setIdArchivosEscalamientosHHPP(BigDecimal idArchivosEscalamientosHHPP) {
        this.idArchivosEscalamientosHHPP = idArchivosEscalamientosHHPP;
    }

    /**
     * @return the solicitudObj
     */
    public Solicitud getSolicitudObj() {
        return solicitudObj;
    }

    /**
     * @param solicitudObj the solicitudObj to set
     */
    public void setSolicitudObj(Solicitud solicitudObj) {
        this.solicitudObj = solicitudObj;
    }

    /**
     * @return the urlArchivoSoporte
     */
    public String getUrlArchivoSoporte() {
        return urlArchivoSoporte;
    }

    /**
     * @param urlArchivoSoporte the urlArchivoSoporte to set
     */
    public void setUrlArchivoSoporte(String urlArchivoSoporte) {
        this.urlArchivoSoporte = urlArchivoSoporte;
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
     * @return the nombreArchivo
     */
    public String getNombreArchivo() {
        return nombreArchivo;
    }

    /**
     * @param nombreArchivo the nombreArchivo to set
     */
    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    /**
     * get field @Column(name = "ORIGEN")
     *
     * @return origen @Column(name = "ORIGEN")
     */
    public String getOrigen() {
        return this.origen;
    }

    /**
     * set field @Column(name = "ORIGEN")
     *
     * @param origen @Column(name = "ORIGEN")
     */
    public void setOrigen(String origen) {
        this.origen = origen;
    }

    /**
     * get field @ManyToOne(fetch = FetchType.LAZY)
     *
     * @return tecObservacionSolicitudHHPP @ManyToOne(fetch = FetchType.LAZY)
     * @Column(name = "ID_TEC_OBS_SOL_HHPP")
     * @Column(name = "ID_TEC_OBS_SOL_HHPP")
     */
    public TecObservacionSolicitudHHPP getTecObservacionSolicitudHHPP() {
        return this.tecObservacionSolicitudHHPP;
    }

    /**
     * set field @ManyToOne(fetch = FetchType.LAZY)
     *
     * @param tecObservacionSolicitudHHPP @ManyToOne(fetch = FetchType.LAZY)
     * @Column(name = "ID_TEC_OBS_SOL_HHPP")
     * @Column(name = "ID_TEC_OBS_SOL_HHPP")
     */
    public void setTecObservacionSolicitudHHPP(TecObservacionSolicitudHHPP tecObservacionSolicitudHHPP) {
        this.tecObservacionSolicitudHHPP = tecObservacionSolicitudHHPP;
    }
}