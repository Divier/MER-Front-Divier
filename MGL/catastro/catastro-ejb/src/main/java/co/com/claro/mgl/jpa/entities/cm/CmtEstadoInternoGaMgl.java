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
 * @author Hitss
 */
@Entity
@Table(name = "CMT_ESTADO_INTERNO_GA", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmtEstadoInternoGaMgl.findAll", query = "SELECT c FROM CmtEstadoInternoGaMgl c WHERE c.estadoRegistro=1"),
    @NamedQuery(name = "CmtEstadoInternoGaMgl.findByCodigo", query = "SELECT c FROM CmtEstadoInternoGaMgl c WHERE c.codigo= :codigo AND c.estadoRegistro=1")
})
public class CmtEstadoInternoGaMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "CmtEstadoInternoGaMgl.CmtEstadoInternoGaMglSq",
            sequenceName = "MGL_SCHEME.CMT_ESTADO_INTERNO_GA_SQ", allocationSize = 1)
    @GeneratedValue(generator = "CmtEstadoInternoGaMgl.CmtEstadoInternoGaMglSq")
    @Column(name = "ESTADO_INTERNO_GA_ID", nullable = false)
    private BigDecimal estadoInternoGaid;
    @Column(name = "CODIGO", nullable = false, length = 10)
    private String codigo;
    @Column(name = "ABREVIATURA", nullable = true, length = 20)
    private String abreviatura;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ESTADO_INI_CM_ID", referencedColumnName = "BASICA_ID", nullable = false)
    private CmtBasicaMgl estadoInicialCM;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_TIPO_GA_ID", referencedColumnName = "BASICA_ID", nullable = false)
    private CmtBasicaMgl tipoGaObj;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ESTADO_EXT_ANT_GA_ID", referencedColumnName = "BASICA_ID", nullable = false)
    private CmtBasicaMgl estadoExternoAnteriorGA;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ESTADO_INTERNO_GA_ID", referencedColumnName = "BASICA_ID", nullable = false)
    private CmtBasicaMgl estadoInternoGA;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ESTADO_EXT_POST_GA_ID", referencedColumnName = "BASICA_ID", nullable = false)
    private CmtBasicaMgl estadoExternoPosteriorGA;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ESTADO_FIN_CM_ID", referencedColumnName = "BASICA_ID", nullable = false)
    private CmtBasicaMgl estadoFinalCM;
    @Column(name = "DESCRIPCION", nullable = true, length = 500)
    private String descripcion;
    @Column(name = "DOCUMENTACION", nullable = true, length = 500)
    private String documentacion;
    @Column(name = "ACTIVADO", nullable = false, length = 1)
    private String estado;
    @Column(name = "JUSTIFICACION", nullable = true, length = 500)
    private String justificacion;
    @Column(name = "DETALLE", nullable = false, length = 20)
    private String detalleOperacion;
    @Basic(optional = false)
    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro;
    @NotNull
    @Column(name = "FECHA_CREACION", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @NotNull
    @Column(name = "USUARIO_CREACION", nullable = true, length = 200)
    private String usuarioCreacion;
    @NotNull
    @Column(name = "PERFIL_CREACION")
    private int perfilCreacion;
    @Column(name = "FECHA_EDICION", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaEdicion;
    @Column(name = "USUARIO_EDICION", nullable = true, length = 200)
    private String usuarioEdicion;
    @Column(name = "PERFIL_EDICION")
    private int perfilEdicion;

    @OneToMany(mappedBy = "estadoInternoGaObj", fetch = FetchType.LAZY)
    private List<CmtEstadoInternoGaAuditoriaMgl> listAuditoria;

    public BigDecimal getEstadoInternoGaid() {
        return estadoInternoGaid;
    }

    public void setEstadoInternoGaid(BigDecimal estadoInternoGaid) {
        this.estadoInternoGaid = estadoInternoGaid;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public CmtBasicaMgl getEstadoInicialCM() {
        return estadoInicialCM;
    }

    public void setEstadoInicialCM(CmtBasicaMgl estadoInicialCM) {
        this.estadoInicialCM = estadoInicialCM;
    }

    public CmtBasicaMgl getEstadoExternoAnteriorGA() {
        return estadoExternoAnteriorGA;
    }

    public void setEstadoExternoAnteriorGA(CmtBasicaMgl estadoExternoAnteriorGA) {
        this.estadoExternoAnteriorGA = estadoExternoAnteriorGA;
    }

    public CmtBasicaMgl getEstadoInternoGA() {
        return estadoInternoGA;
    }

    public void setEstadoInternoGA(CmtBasicaMgl estadoInternoGA) {
        this.estadoInternoGA = estadoInternoGA;
    }

    public CmtBasicaMgl getEstadoExternoPosteriorGA() {
        return estadoExternoPosteriorGA;
    }

    public void setEstadoExternoPosteriorGA(CmtBasicaMgl estadoExternoPosteriorGA) {
        this.estadoExternoPosteriorGA = estadoExternoPosteriorGA;
    }

    public CmtBasicaMgl getEstadoFinalCM() {
        return estadoFinalCM;
    }

    public void setEstadoFinalCM(CmtBasicaMgl estadoFinalCM) {
        this.estadoFinalCM = estadoFinalCM;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDocumentacion() {
        return documentacion;
    }

    public void setDocumentacion(String documentacion) {
        this.documentacion = documentacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }

    public String getDetalleOperacion() {
        return detalleOperacion;
    }

    public void setDetalleOperacion(String detalleOperacion) {
        this.detalleOperacion = detalleOperacion;
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

    public CmtBasicaMgl getTipoGaObj() {
        return tipoGaObj;
    }

    public void setTipoGaObj(CmtBasicaMgl tipoGaObj) {
        this.tipoGaObj = tipoGaObj;
    }

    public List<CmtEstadoInternoGaAuditoriaMgl> getListAuditoria() {
        return listAuditoria;
    }

    public void setListAuditoria(List<CmtEstadoInternoGaAuditoriaMgl> listAuditoria) {
        this.listAuditoria = listAuditoria;
    }
    
}
