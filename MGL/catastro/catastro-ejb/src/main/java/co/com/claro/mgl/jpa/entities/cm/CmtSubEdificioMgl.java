
package co.com.claro.mgl.jpa.entities.cm;

import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "CMT_SUBEDIFICIO", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "CmtSubEdificioMgl.findAll", query = "SELECT c FROM CmtSubEdificioMgl c "),
        @NamedQuery(name = "CmtSubEdificioMgl.findBySubEdificioId", query = "SELECT c FROM CmtSubEdificioMgl c "),
        @NamedQuery(name = "CmtSubEdificioMgl.findByCuentaMatrizAndEstado", query = "SELECT c FROM CmtSubEdificioMgl c WHERE c.cuentaMatrizObj = :cuentaMatriz AND c.estadoSubEdificioObj = :estadoSubEdificio and c.estadoRegistro = 1"),
        @NamedQuery(name = "CmtSubEdificioMgl.findByCuentaMatriz", query = "SELECT c FROM CmtSubEdificioMgl c WHERE c.cuentaMatrizObj = :cuentaMatriz AND c.estadoRegistro=1"),
        @NamedQuery(name = "CmtSubEdificioMgl.updateSubEdificioNumPisos", query = "UPDATE CmtSubEdificioMgl c SET c.pisos = :numPisos WHERE c.subEdificioId = :cmtSubEdificioId"),
        @NamedQuery(name = "CmtSubEdificioMgl.countSubEdificiosCuentaMatriz", query = "SELECT COUNT(c) FROM CmtSubEdificioMgl c WHERE c.cuentaMatrizObj.cuentaMatrizId = :idCuentaMatriz and c.estadoRegistro = 1"),
        @NamedQuery(name = "CmtSubEdificioMgl.countSubEdificiosCuentaMatrizNombre", query = "SELECT c FROM CmtSubEdificioMgl c WHERE c.nombreSubedificio = :nombreCuentaMatriz and c.estadoRegistro = 1"),
        @NamedQuery(name = "CmtSubEdificioMgl.findByNodo", query = "SELECT c FROM CmtSubEdificioMgl c WHERE c.nodoObj = :nodoObj AND c.estadoRegistro=1"),
        @NamedQuery(name = "CmtSubEdificioMgl.findByCuentaMatrizId", query = "SELECT c FROM CmtSubEdificioMgl c WHERE c.cuentaMatrizObj.cuentaMatrizId = :cuentaMatriz AND c.estadoRegistro=1"),
        @NamedQuery(name = "CmtSubEdificioMgl.findBySubEdificioUniMulti", query = "SELECT c FROM CmtSubEdificioMgl c WHERE c.cuentaMatrizObj = :cuentaMatriz AND (c.estadoSubEdificioObj = :estadoUni or c.estadoSubEdificioObj = :estadoMulti) and c.estadoRegistro = 1")
})
public class CmtSubEdificioMgl implements Serializable, Cloneable, Comparable<CmtSubEdificioMgl> {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "CmtSubEdificioMgl.CmtSubEdificioMglSq",
            sequenceName = "MGL_SCHEME.CMT_SUBEDIFICIO_SQ", allocationSize = 1)
    @GeneratedValue(generator = "CmtSubEdificioMgl.CmtSubEdificioMglSq")
    @Column(name = "SUBEDIFICIO_ID", nullable = false)
    private BigDecimal subEdificioId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUENTAMATRIZ_ID", nullable = false)
    private CmtCuentaMatrizMgl cuentaMatrizObj;
    @Basic(optional = false)
    @Column(name = "NOMBRE_SUBEDIFICIO")
    private String nombreSubedificio;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_ESTADO_SUBEDIFICIO")
    private CmtBasicaMgl estadoSubEdificioObj;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPANIA_ID_CONSTRUCTORA")
    private CmtCompaniaMgl companiaConstructoraObj;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_TIPO_ACOMETIDA")
    private CmtBasicaMgl tipoAcometidaObj;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPANIA_ID_ADMINISTRACION")
    private CmtCompaniaMgl companiaAdministracionObj;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPANIA_ID_ASCENSOR")
    private CmtCompaniaMgl companiaAscensorObj;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NODO_ID")
    private NodoMgl nodoObj;
    @Basic(optional = false)
    @Column(name = "FECHA_ENTREGA_EDIFICIO")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaEntregaEdificio;
    @Basic(optional = false)
    @Column(name = "TOTAL_UNIDADES_PLANEADAS")
    private int totalUnidadePlaneadas;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_ESTRATO")
    private CmtBasicaMgl estrato;
    @Basic(optional = false)
    @Column(name = "META")
    private BigDecimal meta;
    @Basic(optional = false)
    @Column(name = "RENTA")
    private BigDecimal renta;
    @Basic(optional = false)
    @Column(name = "CUMPLIMIENTO")
    private BigDecimal cumplimiento;
    @Basic(optional = false)
    @Column(name = "HOGARES")
    private int hogares;
    @Column(name = "PISOS")
    private int pisos;
    @Column(name = "ADMINISTRADOR", nullable = true, length = 200)
    private String administrador;
    @Basic(optional = false)
    @Column(name = "UNIDADES_VT")
    private int unidadesVt;
    @Basic(optional = false)
    @Column(name = "UNIDADES")
    private int unidades;
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
    @Basic(optional = false)
    @Column(name = "UNIDADES_ESTIMADAS")
    private int unidadesEstimadas;
    @Column(name = "TELEFONO_PORTERIA", nullable = true, length = 200)
    private String telefonoPorteria;
    @Column(name = "TELEFONO_PORTERIA2", nullable = true, length = 200)
    private String telefonoPorteria2;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_TIPO_EDIFICIO")
    private CmtBasicaMgl tipoEdificioObj;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_PRODUCTO")
    private CmtBasicaMgl productoObj;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_TIPO_PROYECTO")
    private CmtBasicaMgl tipoProyectoObj;
    @Column(name = "TIPO_LOC", nullable = true, length = 2)
    private String tipoLoc;
    @Column(name = "HEAD_END", nullable = true, length = 5)
    private String headEnd;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro;
    @Column(name = "DIRECTOR_INGENIERO_OBRA", nullable = true, length = 64)
    private String directorIngenieroObra;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_ORIGEN_DATOS")
    private CmtBasicaMgl origenDatosObj;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_ESTADO_ACOMETIDA")
    private CmtBasicaMgl estadoAcometida;
    @Column(name = "DIRECCION", nullable = true, length = 200)
    private String direccion;
    @Column(name = "CODIGO_RR", nullable = true, length = 20)
    private String codigoRr;
    @Column(name = "VISITA_TECNICA", nullable = false, length = 2)
    private String visitaTecnica;
    @Column(name = "FECHA_VISITA_TECNICA", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaVisitaTecnica;
    @Column(name = "COSTO_VISITA_TECNICA", nullable = true)
    private BigDecimal costoVisitaTecnica;
    @Column(name = "REDISENO", nullable = false, length = 2)
    private String reDiseno;
    @Column(name = "FECHA_REDISENO", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaReDiseno;
    @Column(name = "FECHA_RESPUESTA_REDISENO", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaRespuestaReDiseno;
    @Column(name = "CIERRE", nullable = false, length = 2)
    private String cierre;
    @Column(name = "FECHA_INI_EJECUCION", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaInicioEjecuion;
    @Column(name = "FECHA_FIN_EJECUCION", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaFinEjecuion;
    @Column(name = "COSTO_EJECUCION", nullable = true)
    private BigDecimal costoEjecucion;
    @Column(name = "PLANOS", nullable = false, length = 2)
    private String planos;
    @Column(name = "FECHA_SOLICITUD_PLANOS", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaSolicitudPlanos;
    @Column(name = "FECHA_ENTREGA_PLANOS", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaEntregaPlanos;
    @Column(name = "CONNECT_CORRIENTE", nullable = false, length = 2)
    private String conexionCorriente;
    @Column(name = "FECHA_SOL_CONNECT_CORRIENTE", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaSolicitudConexionCorriente;
    @Column(name = "FECHA_ENTREG_CONNECT_CORRIENTE", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaEntregaConexionCorriente;
    @Column(name = "FECHA_RECIBIDO_CIERRE", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaRecibidoCierre;
    @Column(name = "DIRECCION_ANTIGUA", nullable = true, length = 200)
    private String direccionAntigua;

    @OneToMany(mappedBy = "hhppSubEdificioObj", fetch = FetchType.LAZY)
    private List<HhppMgl> listHhpp;
    @OneToMany(mappedBy = "subEdificioObj", fetch = FetchType.LAZY)
    private List<CmtEstablecimientoCmMgl> listEstablesimientos;
    @OneToMany(mappedBy = "subEdificioObj", fetch = FetchType.LAZY)
    private List<CmtCompetenciaMgl> competenciaList;
    @OneToMany(mappedBy = "subEdificioIdObj", fetch = FetchType.LAZY)
    private List<CmtSubEdificioAuditoriaMgl> listAuditoria;
    @OneToMany(mappedBy = "subEdificioObj", fetch = FetchType.LAZY)
    private List<CmtNotasMgl> notasList;
    @OneToMany(mappedBy = "subEdificioObj", fetch = FetchType.LAZY)
    private List<CmtSolicitudSubEdificioMgl> cmtSolSubEdiList;
    @OneToMany(mappedBy = "subEdificioObj", fetch = FetchType.LAZY)
    private List<CmtBlackListMgl> listCmtBlackListMgl;
    @OneToMany(mappedBy = "subEdificioObj",
            fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CmtDireccionMgl> listDireccionesMgl;
    @OneToMany(mappedBy = "subedificioId", fetch = FetchType.LAZY)
    private List<CmtTecnologiaSubMgl> listTecnologiasSub;
    @Column(name = "CAMBIOESTADO", nullable = false, length = 1)
    private String cambioestado;
    @Transient
    private String nombreGeneral;
    @Transient
    private String nombreTorre;
    @Transient
    private BigDecimal estadoSubNuevo;
    @Transient
    private String nombreEntSubedificio;
    @Transient
    private String calleEntSubedificio;
    @Transient
    private String uniadEntSubedificio;
    @Transient
    private boolean corregimientoAplicado;

    public CmtSubEdificioMgl mapearCamposCmtSubEdificioMglMod(CmtSolicitudSubEdificioMgl modSe) {

        this.setNombreSubedificio(modSe.getNombreSubedificio() == null ? this.getNombreSubedificio() : modSe.getNombreSubedificio());
        this.setTipoEdificioObj(modSe.getTipoEdificioObj() == null ? this.getTipoEdificioObj() : modSe.getTipoEdificioObj());
        this.setTelefonoPorteria(modSe.getTelefonoPorteria() == null ? this.getTelefonoPorteria() : modSe.getTelefonoPorteria());
        this.setTelefonoPorteria2(modSe.getTelefonoPorteria2() == null ? this.getTelefonoPorteria2() : modSe.getTelefonoPorteria2());
        this.setAdministrador(modSe.getAdministrador() == null ? this.getAdministrador() : modSe.getAdministrador());
        this.setCompaniaAdministracionObj(modSe.getCompaniaAdministracionObj() == null ? this.getCompaniaAdministracionObj() : modSe.getCompaniaAdministracionObj());
        this.setCompaniaAscensorObj(modSe.getCompaniaAscensorObj() == null ? this.getCompaniaAscensorObj() : modSe.getCompaniaAscensorObj());
        this.setFechaEntregaEdificio(modSe.getFechaEntregaEdificio() == null ? this.getFechaEntregaEdificio() : modSe.getFechaEntregaEdificio());

        return this;
    }

    /**
     * @return the subEdificioId
     */
    public BigDecimal getSubEdificioId() {
        return subEdificioId;
    }

    /**
     * @param subEdificioId the subEdificioId to set
     */
    public void setSubEdificioId(BigDecimal subEdificioId) {
        this.subEdificioId = subEdificioId;
    }

    /**
     * @return the cmtCuentaMatrizMglObj
     */
    public CmtCuentaMatrizMgl getCmtCuentaMatrizMglObj() {
        return cuentaMatrizObj;
    }

    /**
     * @param cmtCuentaMatrizMglObj the cmtCuentaMatrizMglObj to set
     */
    public void setCmtCuentaMatrizMglObj(CmtCuentaMatrizMgl cmtCuentaMatrizMglObj) {
        this.cuentaMatrizObj = cmtCuentaMatrizMglObj;
    }

    /**
     * @return the nombreSubedificio
     */
    public String getNombreSubedificio() {
        return nombreSubedificio;

    }

    /**
     * @param nombreSubedificio the nombreSubedificio to set
     */
    public void setNombreSubedificio(String nombreSubedificio) {
        this.nombreSubedificio = nombreSubedificio;
    }

    /**
     * @return the estadoSubEdificioObj
     */
    public CmtBasicaMgl getEstadoSubEdificioObj() {
        return estadoSubEdificioObj;
    }

    /**
     * @param estadoSubEdificioObj the estadoSubEdificioObj to set
     */
    public void setEstadoSubEdificioObj(CmtBasicaMgl estadoSubEdificioObj) {
        this.estadoSubEdificioObj = estadoSubEdificioObj;
    }

    /**
     * @return the companiaConstructoraObj
     */
    public CmtCompaniaMgl getCompaniaConstructoraObj() {
        return companiaConstructoraObj;
    }

    /**
     * @param companiaConstructoraObj the companiaConstructoraObj to set
     */
    public void setCompaniaConstructoraObj(CmtCompaniaMgl companiaConstructoraObj) {
        this.companiaConstructoraObj = companiaConstructoraObj;
    }

    /**
     * @return the tipoAcometidaObj
     */
    public CmtBasicaMgl getTipoAcometidaObj() {
        return tipoAcometidaObj;
    }

    /**
     * @param tipoAcometidaObj the tipoAcometidaObj to set
     */
    public void setTipoAcometidaObj(CmtBasicaMgl tipoAcometidaObj) {
        this.tipoAcometidaObj = tipoAcometidaObj;
    }

    /**
     * @return the companiaAdministracionObj
     */
    public CmtCompaniaMgl getCompaniaAdministracionObj() {
        return companiaAdministracionObj;
    }

    /**
     * @param companiaAdministracionObj the companiaAdministracionObj to set
     */
    public void setCompaniaAdministracionObj(CmtCompaniaMgl companiaAdministracionObj) {
        this.companiaAdministracionObj = companiaAdministracionObj;
    }

    /**
     * @return the companiaAscensorObj
     */
    public CmtCompaniaMgl getCompaniaAscensorObj() {
        return companiaAscensorObj;
    }

    /**
     * @param companiaAscensorObj the companiaAscensorObj to set
     */
    public void setCompaniaAscensorObj(CmtCompaniaMgl companiaAscensorObj) {
        this.companiaAscensorObj = companiaAscensorObj;
    }

    /**
     * @return the nodoObj
     */
    public NodoMgl getNodoObj() {
        return nodoObj;
    }

    /**
     * @param nodoObj the nodoObj to set
     */
    public void setNodoObj(NodoMgl nodoObj) {
        this.nodoObj = nodoObj;
    }

    /**
     * @return the fechaEntregaEdificio
     */
    public Date getFechaEntregaEdificio() {
        return fechaEntregaEdificio;
    }

    /**
     * @param fechaEntregaEdificio the fechaEntregaEdificio to set
     */
    public void setFechaEntregaEdificio(Date fechaEntregaEdificio) {
        this.fechaEntregaEdificio = fechaEntregaEdificio;
    }

    /**
     * @return the totalUnidadePlaneadas
     */
    public int getTotalUnidadePlaneadas() {
        return totalUnidadePlaneadas;
    }

    /**
     * z
     *
     * @param totalUnidadePlaneadas the totalUnidadePlaneadas to set
     */
    public void setTotalUnidadePlaneadas(int totalUnidadePlaneadas) {
        this.totalUnidadePlaneadas = totalUnidadePlaneadas;
    }

    /**
     * @return the estrato
     */
    public CmtBasicaMgl getEstrato() {
        return estrato;
    }

    /**
     * @param estrato the estrato to set
     */
    public void setEstrato(CmtBasicaMgl estrato) {
        this.estrato = estrato;
    }

    /**
     * @return the meta
     */
    public BigDecimal getMeta() {
        return meta;
    }

    /**
     * @param meta the meta to set
     */
    public void setMeta(BigDecimal meta) {
        this.meta = meta;
    }

    /**
     * @return the renta
     */
    public BigDecimal getRenta() {
        return renta;
    }

    /**
     * @param renta the renta to set
     */
    public void setRenta(BigDecimal renta) {
        this.renta = renta;
    }

    /**
     * @return the cumplimiento
     */
    public BigDecimal getCumplimiento() {
        return cumplimiento;
    }

    /**
     * @param cumplimiento the cumplimiento to set
     */
    public void setCumplimiento(BigDecimal cumplimiento) {
        this.cumplimiento = cumplimiento;
    }

    /**
     * @return the hogares
     */
    public int getHogares() {
        return hogares;
    }

    /**
     * @param hogares the hogares to set
     */
    public void setHogares(int hogares) {
        this.hogares = hogares;
    }

    /**
     * @return the administrador
     */
    public String getAdministrador() {
        return administrador;
    }

    /**
     * @param administrador the administrador to set
     */
    public void setAdministrador(String administrador) {
        this.administrador = administrador;
    }

    /**
     * @return the unidadesVt
     */
    public int getUnidadesVt() {
        return unidadesVt;
    }

    /**
     * @param unidadesVt the unidadesVt to set
     */
    public void setUnidadesVt(int unidadesVt) {
        this.unidadesVt = unidadesVt;
    }

    /**
     * @return the unidades
     */
    public int getUnidades() {
        return unidades;
    }

    /**
     * @param unidades the unidades to set
     */
    public void setUnidades(int unidades) {
        this.unidades = unidades;
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
     * @return the unidadesEstimadas
     */
    public int getUnidadesEstimadas() {
        return unidadesEstimadas;
    }

    /**
     * @param unidadesEstimadas the unidadesEstimadas to set
     */
    public void setUnidadesEstimadas(int unidadesEstimadas) {
        this.unidadesEstimadas = unidadesEstimadas;
    }

    /**
     * @return the telefonoPorteria
     */
    public String getTelefonoPorteria() {
        return telefonoPorteria;
    }

    /**
     * @param telefonoPorteria the telefonoPorteria to set
     */
    public void setTelefonoPorteria(String telefonoPorteria) {
        this.telefonoPorteria = telefonoPorteria;
    }

    /**
     * @return the telefonoPorteria2
     */
    public String getTelefonoPorteria2() {
        return telefonoPorteria2;
    }

    /**
     * @param telefonoPorteria2 the telefonoPorteria2 to set
     */
    public void setTelefonoPorteria2(String telefonoPorteria2) {
        this.telefonoPorteria2 = telefonoPorteria2;
    }

    /**
     * @return the tipoEdificioObj
     */
    public CmtBasicaMgl getTipoEdificioObj() {
        return tipoEdificioObj;
    }

    /**
     * @param tipoEdificioObj the tipoEdificioObj to set
     */
    public void setTipoEdificioObj(CmtBasicaMgl tipoEdificioObj) {
        this.tipoEdificioObj = tipoEdificioObj;
    }

    /**
     * @return the productoObj
     */
    public CmtBasicaMgl getProductoObj() {
        return productoObj;
    }

    /**
     * @param productoObj the productoObj to set
     */
    public void setProductoObj(CmtBasicaMgl productoObj) {
        this.productoObj = productoObj;
    }

    /**
     * @return the tipoProyectoObj
     */
    public CmtBasicaMgl getTipoProyectoObj() {
        return tipoProyectoObj;
    }

    /**
     * @param tipoProyectoObj the tipoProyectoObj to set
     */
    public void setTipoProyectoObj(CmtBasicaMgl tipoProyectoObj) {
        this.tipoProyectoObj = tipoProyectoObj;
    }

    /**
     * @return the tipoLoc
     */
    public String getTipoLoc() {
        return tipoLoc;
    }

    /**
     * @param tipoLoc the tipoLoc to set
     */
    public void setTipoLoc(String tipoLoc) {
        this.tipoLoc = tipoLoc;
    }

    /**
     * @return the headEnd
     */
    public String getHeadEnd() {
        return headEnd;
    }

    /**
     * @param headEnd the headEnd to set
     */
    public void setHeadEnd(String headEnd) {
        this.headEnd = headEnd;
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
     * @return the codigoRr
     */
    public String getCodigoRr() {
        return codigoRr;
    }

    /**
     * @param codigoRr the codigoRr to set
     */
    public void setCodigoRr(String codigoRr) {
        this.codigoRr = codigoRr;
    }

    /**
     * @return the visitaTecnica
     */
    public String getVisitaTecnica() {
        return visitaTecnica;
    }

    /**
     * @param visitaTecnica the visitaTecnica to set
     */
    public void setVisitaTecnica(String visitaTecnica) {
        this.visitaTecnica = visitaTecnica;
    }

    /**
     * @return the fechaVisitaTecnica
     */
    public Date getFechaVisitaTecnica() {
        return fechaVisitaTecnica;
    }

    /**
     * @param fechaVisitaTecnica the fechaVisitaTecnica to set
     */
    public void setFechaVisitaTecnica(Date fechaVisitaTecnica) {
        this.fechaVisitaTecnica = fechaVisitaTecnica;
    }

    /**
     * @return the costoVisitaTecnica
     */
    public BigDecimal getCostoVisitaTecnica() {
        return costoVisitaTecnica;
    }

    /**
     * @param costoVisitaTecnica the costoVisitaTecnica to set
     */
    public void setCostoVisitaTecnica(BigDecimal costoVisitaTecnica) {
        this.costoVisitaTecnica = costoVisitaTecnica;
    }

    /**
     * @return the reDiseno
     */
    public String getReDiseno() {
        return reDiseno;
    }

    /**
     * @param reDiseno the reDiseno to set
     */
    public void setReDiseno(String reDiseno) {
        this.reDiseno = reDiseno;
    }

    /**
     * @return the fechaReDiseno
     */
    public Date getFechaReDiseno() {
        return fechaReDiseno;
    }

    /**
     * @param fechaReDiseno the fechaReDiseno to set
     */
    public void setFechaReDiseno(Date fechaReDiseno) {
        this.fechaReDiseno = fechaReDiseno;
    }

    /**
     * @return the fechaRespuestaReDiseno
     */
    public Date getFechaRespuestaReDiseno() {
        return fechaRespuestaReDiseno;
    }

    /**
     * @param fechaRespuestaReDiseno the fechaRespuestaReDiseno to set
     */
    public void setFechaRespuestaReDiseno(Date fechaRespuestaReDiseno) {
        this.fechaRespuestaReDiseno = fechaRespuestaReDiseno;
    }

    /**
     * @return the cierre
     */
    public String getCierre() {
        return cierre;
    }

    /**
     * @param cierre the cierre to set
     */
    public void setCierre(String cierre) {
        this.cierre = cierre;
    }

    /**
     * @return the fechaInicioEjecuion
     */
    public Date getFechaInicioEjecuion() {
        return fechaInicioEjecuion;
    }

    /**
     * @param fechaInicioEjecuion the fechaInicioEjecuion to set
     */
    public void setFechaInicioEjecuion(Date fechaInicioEjecuion) {
        this.fechaInicioEjecuion = fechaInicioEjecuion;
    }

    /**
     * @return the fechaFinEjecuion
     */
    public Date getFechaFinEjecuion() {
        return fechaFinEjecuion;
    }

    /**
     * @param fechaFinEjecuion the fechaFinEjecuion to set
     */
    public void setFechaFinEjecuion(Date fechaFinEjecuion) {
        this.fechaFinEjecuion = fechaFinEjecuion;
    }

    /**
     * @return the costoEjecucion
     */
    public BigDecimal getCostoEjecucion() {
        return costoEjecucion;
    }

    /**
     * @param costoEjecucion the costoEjecucion to set
     */
    public void setCostoEjecucion(BigDecimal costoEjecucion) {
        this.costoEjecucion = costoEjecucion;
    }

    /**
     * @return the planos
     */
    public String getPlanos() {
        return planos;
    }

    /**
     * @param planos the planos to set
     */
    public void setPlanos(String planos) {
        this.planos = planos;
    }

    /**
     * @return the fechaSolicitudPlanos
     */
    public Date getFechaSolicitudPlanos() {
        return fechaSolicitudPlanos;
    }

    /**
     * @param fechaSolicitudPlanos the fechaSolicitudPlanos to set
     */
    public void setFechaSolicitudPlanos(Date fechaSolicitudPlanos) {
        this.fechaSolicitudPlanos = fechaSolicitudPlanos;
    }

    /**
     * @return the fechaEntregaPlanos
     */
    public Date getFechaEntregaPlanos() {
        return fechaEntregaPlanos;
    }

    /**
     * @param fechaEntregaPlanos the fechaEntregaPlanos to set
     */
    public void setFechaEntregaPlanos(Date fechaEntregaPlanos) {
        this.fechaEntregaPlanos = fechaEntregaPlanos;
    }

    /**
     * @return the conexionCorriente
     */
    public String getConexionCorriente() {
        return conexionCorriente;
    }

    /**
     * @param conexionCorriente the conexionCorriente to set
     */
    public void setConexionCorriente(String conexionCorriente) {
        this.conexionCorriente = conexionCorriente;
    }

    /**
     * @return the fechaSolicitudConexionCorriente
     */
    public Date getFechaSolicitudConexionCorriente() {
        return fechaSolicitudConexionCorriente;
    }

    /**
     * @param fechaSolicitudConexionCorriente the
     * fechaSolicitudConexionCorriente to set
     */
    public void setFechaSolicitudConexionCorriente(Date fechaSolicitudConexionCorriente) {
        this.fechaSolicitudConexionCorriente = fechaSolicitudConexionCorriente;
    }

    /**
     * @return the fechaEntregaConexionCorriente
     */
    public Date getFechaEntregaConexionCorriente() {
        return fechaEntregaConexionCorriente;
    }

    /**
     * @param fechaEntregaConexionCorriente the fechaEntregaConexionCorriente to
     * set
     */
    public void setFechaEntregaConexionCorriente(Date fechaEntregaConexionCorriente) {
        this.fechaEntregaConexionCorriente = fechaEntregaConexionCorriente;
    }

    /**
     * @return the fechaRecibidoCierre
     */
    public Date getFechaRecibidoCierre() {
        return fechaRecibidoCierre;
    }

    /**
     * @param fechaRecibidoCierre the fechaRecibidoCierre to set
     */
    public void setFechaRecibidoCierre(Date fechaRecibidoCierre) {
        this.fechaRecibidoCierre = fechaRecibidoCierre;
    }

    public CmtCuentaMatrizMgl getCuentaMatrizObj() {
        return cuentaMatrizObj;
    }

    public void setCuentaMatrizObj(CmtCuentaMatrizMgl cuentaMatrizObj) {
        this.cuentaMatrizObj = cuentaMatrizObj;
    }

    public CmtBasicaMgl getOrigenDatosObj() {
        return origenDatosObj;
    }

    public void setOrigenDatosObj(CmtBasicaMgl origenDatosObj) {
        this.origenDatosObj = origenDatosObj;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<HhppMgl> getListHhpp() {
        return listHhpp;
    }

    public void setListHhpp(List<HhppMgl> listHhpp) {
        this.listHhpp = listHhpp;
    }

    public List<CmtEstablecimientoCmMgl> getListEstablesimientos() {
        return listEstablesimientos;
    }

    public void setListEstablesimientos(List<CmtEstablecimientoCmMgl> listEstablesimientos) {
        this.listEstablesimientos = listEstablesimientos;
    }

    public List<CmtCompetenciaMgl> getCompetenciaList() {
        return competenciaList;
    }

    public void setCompetenciaList(List<CmtCompetenciaMgl> competenciaList) {
        this.competenciaList = competenciaList;
    }

    @Override
    public String toString() {
        return "[" + subEdificioId + "]" + nombreSubedificio;
    }

    public List<CmtNotasMgl> getNotasList() {
        return notasList;
    }

    public void setNotasList(List<CmtNotasMgl> notasList) {
        this.notasList = notasList;
    }

    public List<CmtSubEdificioAuditoriaMgl> getListAuditoria() {
        return listAuditoria;
    }

    public void setListAuditoria(List<CmtSubEdificioAuditoriaMgl> listAuditoria) {
        this.listAuditoria = listAuditoria;
    }

    public List<CmtSolicitudSubEdificioMgl> getCmtSolSubEdiList() {
        return cmtSolSubEdiList;
    }

    public void setCmtSolSubEdiList(List<CmtSolicitudSubEdificioMgl> cmtSolSubEdiList) {
        this.cmtSolSubEdiList = cmtSolSubEdiList;
    }

    public CmtBasicaMgl getEstadoAcometida() {
        return estadoAcometida;
    }

    public void setEstadoAcometida(CmtBasicaMgl estadoAcometida) {
        this.estadoAcometida = estadoAcometida;
    }

    public List<CmtBlackListMgl> getListCmtBlackListMgl() {
        return listCmtBlackListMgl;
    }

    public void setListCmtBlackListMgl(List<CmtBlackListMgl> listCmtBlackListMgl) {
        this.listCmtBlackListMgl = listCmtBlackListMgl;
    }

    public List<CmtDireccionMgl> getListDireccionesMgl() {
        return listDireccionesMgl;
    }

    public void setListDireccionesMgl(List<CmtDireccionMgl> listDireccionesMgl) {
        this.listDireccionesMgl = listDireccionesMgl;
    }

    public String getDireccionAntigua() {
        return direccionAntigua;
    }

    public void setDireccionAntigua(String direccionAntigua) {
        this.direccionAntigua = direccionAntigua;
    }

    public int getPisos() {
        return pisos;
    }

    public void setPisos(int pisos) {
        this.pisos = pisos;
    }

    public List<CmtTecnologiaSubMgl> getListTecnologiasSub() {
        return listTecnologiasSub;
    }

    public void setListTecnologiasSub(List<CmtTecnologiaSubMgl> listTecnologiasSub) {
        this.listTecnologiasSub = listTecnologiasSub;
    }

    public String getNombreGeneral() {
        nombreGeneral = "GENERAL";
        return nombreGeneral;
    }

    public void setNombreGeneral(String nombreGeneral) {
        nombreGeneral = "GENERAL";
        this.nombreGeneral = nombreGeneral;
    }

    public String getNombreTorre() {
        nombreTorre = "Torre 1";
        return nombreTorre;
    }

    public void setNombreTorre(String nombreTorre) {
        nombreTorre = "Torre 1";
        this.nombreTorre = nombreTorre;
    }

    public BigDecimal getEstadoSubNuevo() {
        return estadoSubNuevo;
    }

    public void setEstadoSubNuevo(BigDecimal estadoSubNuevo) {
        this.estadoSubNuevo = estadoSubNuevo;
    }

    public String getDirectorIngenieroObra() {
        return directorIngenieroObra;
    }

    public void setDirectorIngenieroObra(String directorIngenieroObra) {
        this.directorIngenieroObra = directorIngenieroObra;
    }

    public String getNombreEntSubedificio() {
        return nombreEntSubedificio;
    }

    public void setNombreEntSubedificio(String nombreEntSubedificio) {
        this.nombreEntSubedificio = nombreEntSubedificio;
    }

    public String getCalleEntSubedificio() {
        return calleEntSubedificio;
    }

    public void setCalleEntSubedificio(String calleEntSubedificio) {
        this.calleEntSubedificio = calleEntSubedificio;
    }

    public String getUniadEntSubedificio() {
        return uniadEntSubedificio;
    }

    public void setUniadEntSubedificio(String uniadEntSubedificio) {
        this.uniadEntSubedificio = uniadEntSubedificio;
    }

    public String getCambioestado() {
        return cambioestado;
    }

    public void setCambioestado(String cambioestado) {
        this.cambioestado = cambioestado;
    }


    /**
     * Compara 2 instancias del objeto.
     *
     * @param o Objeto a comparar.
     * @return Realiza la comparaci&oacute;n por ID de subedificio de manera
     * ascendente.
     */
    @Override
    public int compareTo(CmtSubEdificioMgl o) {
        return ( this.nombreSubedificio.compareTo(o.nombreSubedificio) );
    }


    /**
     * Obtiene la direcci&oacute;n del subedificio.
     *
     * @return Direcci&oacute;n.
     */
    @Transient
    public String getDireccionSubEdificio() {
        String direccionSub = "";

        if (this.getListDireccionesMgl() != null && !this.getListDireccionesMgl().isEmpty()) {
            CmtDireccionMgl cmtDireccionMgl = this.getListDireccionesMgl().get(0);
            direccionSub = cmtDireccionMgl.getCalleRr()+" " +cmtDireccionMgl.getUnidadRr();
        }

        return (direccionSub);
    }

    public boolean isCorregimientoAplicado() {
        return corregimientoAplicado;
    }

    public void setCorregimientoAplicado(boolean corregimientoAplicado) {
        this.corregimientoAplicado = corregimientoAplicado;
    }

    @Override
    public CmtSubEdificioMgl clone() throws CloneNotSupportedException {
        return (CmtSubEdificioMgl) super.clone();
    }

}
