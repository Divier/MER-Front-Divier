package co.com.claro.mgl.jpa.entities.cm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "CMT_TIPO_SOLICITUD", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmtTipoSolicitudMgl.findAll", query = "SELECT c FROM CmtTipoSolicitudMgl c "),
    @NamedQuery(name = "CmtTipoSolicitudMgl.findByTipoApplication", 
        query = "SELECT c FROM CmtTipoSolicitudMgl c WHERE c.aplicacion = :aplicacion AND c.estadoRegistro = 1")
})

public class CmtTipoSolicitudMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "CmtTipoSolicitudMgl.cmtTipoSolicitudMglSq",
            sequenceName = "MGL_SCHEME.MGL_TIPO_SOLICITUD_SQ", allocationSize = 1)
    @GeneratedValue(generator = "CmtTipoSolicitudMgl.cmtTipoSolicitudMglSq")
    @Column(name = "TIPO_SOLICITUD_ID", nullable = false)
    private BigDecimal tipoSolicitudId;
    @Column(name = "NOMBRE_TIPO")
    private String nombreTipo;
    @Column(name = "CODIGO_TIPO")
    private String codigoTipo;
    @Column(name = "FECHA_CREACION", nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaCreacion;
    @Column(name = "USUARIO_CREACION", nullable = true, length = 200)
    private String usuarioCreacion;
    @Column(name = "PERFIL_CREACION")
    private int perfilCreacion;
    @Column(name = "FECHA_EDICION", nullable = true)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaEdicion;
    @Column(name = "USUARIO_EDICION", nullable = true, length = 200)
    private String usuarioEdicion;
    @Column(name = "PERFIL_EDICION")
    private int perfilEdicion;
    @NotNull
    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro;
    @NotNull
    @Column(name = "ANS")
    private int ans;
    @NotNull
    @Column(name = "ANS_AVISO")
    private int ansAviso;
    @Column(name = "CREACION_ROL", nullable = false, length = 20)
    private String creacionRol;
    @Column(name = "GESTION_ROL", nullable = false, length = 20)
    private String gestionRol;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tipoSolicitudObj")
    private List<CmtSolicitudCmMgl> solicitudesList;
    @Column(name = "APLICACION")
    private String aplicacion;
    @Column(name = "ABREVIATURA")    
    private String abreviatura;   
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TIPO_SOLICITUD_BASICA_ID", referencedColumnName = "BASICA_ID")
    private CmtBasicaMgl tipoSolicitudBasicaId;

    /**
     * @return the tipoSolicitudId
     */
    public BigDecimal getTipoSolicitudId() {
        return tipoSolicitudId;
    }

    /**
     * @param tipoSolicitudId the tipoSolicitudId to set
     */
    public void setTipoSolicitudId(BigDecimal tipoSolicitudId) {
        this.tipoSolicitudId = tipoSolicitudId;
    }

    /**
     * @return the nombreTipo
     */
    public String getNombreTipo() {
        return nombreTipo;
    }

    /**
     * @param nombreTipo the nombreTipo to set
     */
    public void setNombreTipo(String nombreTipo) {
        this.nombreTipo = nombreTipo;
    }

    /**
     * @return the codigoTipo
     */
    public String getCodigoTipo() {
        return codigoTipo;
    }

    /**
     * @param codigoTipo the codigoTipo to set
     */
    public void setCodigoTipo(String codigoTipo) {
        this.codigoTipo = codigoTipo;
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

    public List<CmtSolicitudCmMgl> getSolicitudesList() {
        return solicitudesList;
    }

    public void setSolicitudesList(List<CmtSolicitudCmMgl> solicitudesList) {
        this.solicitudesList = solicitudesList;
    }

    public int getAns() {
        return ans;
    }

    public void setAns(int ans) {
        this.ans = ans;
    }

    public int getAnsAviso() {
        return ansAviso;
    }

    public void setAnsAviso(int ansAviso) {
        this.ansAviso = ansAviso;
    }  

    public String getCreacionRol() {
        return creacionRol;
    }

    public void setCreacionRol(String creacionRol) {
        this.creacionRol = creacionRol;
    }

    public String getGestionRol() {
        return gestionRol;
    }

    public void setGestionRol(String gestionRol) {
        this.gestionRol = gestionRol;
    }

    public String getAplicacion() {
        return aplicacion;
    }

    public void setAplicacion(String aplicacion) {
        this.aplicacion = aplicacion;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public CmtBasicaMgl getTipoSolicitudBasicaId() {
        return tipoSolicitudBasicaId;
    }

    public void setTipoSolicitudBasicaId(CmtBasicaMgl tipoSolicitudBasicaId) {
        this.tipoSolicitudBasicaId = tipoSolicitudBasicaId;
    }

}
