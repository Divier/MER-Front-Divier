/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
package co.com.claro.mgl.jpa.entities.cm;

import co.com.claro.mgl.jpa.entities.NodoMgl;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
*
* @author Admin
*/
@Entity
@Table(name = "CMT_SUBEDIFICIO$AUD", schema = "MGL_SCHEME")
@XmlRootElement
public class CmtSubEdificioAuditoriaMgl implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @Column(name = "SUBEDIFICIOAUD_ID", nullable = false)
    private BigDecimal subEdificioAuditoriaId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUBEDIFICIO_ID", nullable = false)
    private CmtSubEdificioMgl subEdificioIdObj;
    
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
    
    @Basic(optional = false)
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
    
    @Column(name = "AUD_ACTION",length = 3)
    private String accionAuditoria;
    
    @Column(name = "AUD_TIMESTAMP")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
     
    private Date fechaAuditoria;
    
    @Column(name = "AUD_USER",length = 30)
    private String usuarioAuditoria;
    
    @Column(name = "SESSION_ID")
    private BigDecimal sesionAuditoria;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_ORIGEN_DATOS")
    private CmtBasicaMgl origenDatosObj;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_ESTADO_ACOMETIDA")
    private CmtBasicaMgl estadoAcometida;
    
    @Column(name = "DIRECCION", nullable = true, length = 200)
    private String direccion;
    
    @Column(name = "CODIGO_RR", nullable = false, length = 20)
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
    
    @Column(name = "DIRECTOR_INGENIERO_OBRA", nullable = true, length = 64)
    private String directorIngenieroObra;
    
    @Column(name = "CAMBIOESTADO", nullable = false, length = 1)
    private String cambioestado;

     
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
     * @param fechaSolicitudConexionCorriente the fechaSolicitudConexionCorriente to set
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
     * @param fechaEntregaConexionCorriente the fechaEntregaConexionCorriente to set
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

    public BigDecimal getSubEdificioAuditoriaId() {
        return subEdificioAuditoriaId;
    }

    public void setSubEdificioAuditoriaId(BigDecimal subEdificioAuditoriaId) {
        this.subEdificioAuditoriaId = subEdificioAuditoriaId;
    }

    public CmtSubEdificioMgl getSubEdificioIdObj() {
        return subEdificioIdObj;
    }

    public void setSubEdificioIdObj(CmtSubEdificioMgl subEdificioIdObj) {
        this.subEdificioIdObj = subEdificioIdObj;
    }

    public CmtCuentaMatrizMgl getCuentaMatrizObj() {
        return cuentaMatrizObj;
    }

    public void setCuentaMatrizObj(CmtCuentaMatrizMgl cuentaMatrizObj) {
        this.cuentaMatrizObj = cuentaMatrizObj;
    }

    public String getNombreSubedificio() {
        return nombreSubedificio;
   }

    public void setNombreSubedificio(String nombreSubedificio) {
        this.nombreSubedificio = nombreSubedificio;
    }

    public CmtBasicaMgl getEstadoSubEdificioObj() {
        return estadoSubEdificioObj;
    }

    public void setEstadoSubEdificioObj(CmtBasicaMgl estadoSubEdificioObj) {
        this.estadoSubEdificioObj = estadoSubEdificioObj;
    }

    public CmtCompaniaMgl getCompaniaConstructoraObj() {
        return companiaConstructoraObj;
    }

    public void setCompaniaConstructoraObj(CmtCompaniaMgl companiaConstructoraObj) {
        this.companiaConstructoraObj = companiaConstructoraObj;
    }

    public CmtBasicaMgl getTipoAcometidaObj() {
        return tipoAcometidaObj;
    }

    public void setTipoAcometidaObj(CmtBasicaMgl tipoAcometidaObj) {
        this.tipoAcometidaObj = tipoAcometidaObj;
    }

    public CmtCompaniaMgl getCompaniaAdministracionObj() {
        return companiaAdministracionObj;
    }

    public void setCompaniaAdministracionObj(CmtCompaniaMgl companiaAdministracionObj) {
        this.companiaAdministracionObj = companiaAdministracionObj;
    }

    public CmtCompaniaMgl getCompaniaAscensorObj() {
        return companiaAscensorObj;
    }

    public void setCompaniaAscensorObj(CmtCompaniaMgl companiaAscensorObj) {
        this.companiaAscensorObj = companiaAscensorObj;
    }

    public NodoMgl getNodoObj() {
        return nodoObj;
    }

    public void setNodoObj(NodoMgl nodoObj) {
       this.nodoObj = nodoObj;
    }

    public Date getFechaEntregaEdificio() {
        return fechaEntregaEdificio;
    }

    public void setFechaEntregaEdificio(Date fechaEntregaEdificio) {
        this.fechaEntregaEdificio = fechaEntregaEdificio;
    }

    public int getTotalUnidadePlaneadas() {
        return totalUnidadePlaneadas;
    }

    public void setTotalUnidadePlaneadas(int totalUnidadePlaneadas) {
        this.totalUnidadePlaneadas = totalUnidadePlaneadas;
    }

    public BigDecimal getMeta() {
        return meta;
    }

    public void setMeta(BigDecimal meta) {
        this.meta = meta;
    }

    public BigDecimal getRenta() {
        return renta;
    }

    public void setRenta(BigDecimal renta) {
        this.renta = renta;
    }

    public BigDecimal getCumplimiento() {
        return cumplimiento;
    }

    public void setCumplimiento(BigDecimal cumplimiento) {
        this.cumplimiento = cumplimiento;
    }

    public int getHogares() {
        return hogares;
    }

    public void setHogares(int hogares) {
        this.hogares = hogares;
    }

    public String getAdministrador() {
        return administrador;
    }

    public void setAdministrador(String administrador) {
        this.administrador = administrador;
    }

    public int getUnidadesVt() {
        return unidadesVt;
    }

    public void setUnidadesVt(int unidadesVt) {
        this.unidadesVt = unidadesVt;
    }

    public int getUnidades() {
        return unidades;
    }

    public void setUnidades(int unidades) {
        this.unidades = unidades;
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

    public int getUnidadesEstimadas() {
        return unidadesEstimadas;
    }

    public void setUnidadesEstimadas(int unidadesEstimadas) {
        this.unidadesEstimadas = unidadesEstimadas;
    }

    public String getTelefonoPorteria() {
        return telefonoPorteria;
    }

    public void setTelefonoPorteria(String telefonoPorteria) {
        this.telefonoPorteria = telefonoPorteria;
    }

    public String getTelefonoPorteria2() {
        return telefonoPorteria2;
    }

    public void setTelefonoPorteria2(String telefonoPorteria2) {
        this.telefonoPorteria2 = telefonoPorteria2;
    }

    public CmtBasicaMgl getTipoEdificioObj() {
        return tipoEdificioObj;
    }

    public void setTipoEdificioObj(CmtBasicaMgl tipoEdificioObj) {
        this.tipoEdificioObj = tipoEdificioObj;
    }

    public CmtBasicaMgl getProductoObj() {
        return productoObj;
    }

    public void setProductoObj(CmtBasicaMgl productoObj) {
        this.productoObj = productoObj;
    }

    public CmtBasicaMgl getTipoProyectoObj() {
        return tipoProyectoObj;
    }

    public void setTipoProyectoObj(CmtBasicaMgl tipoProyectoObj) {
        this.tipoProyectoObj = tipoProyectoObj;
    }

    public String getTipoLoc() {
        return tipoLoc;
    }

    public void setTipoLoc(String tipoLoc) {
        this.tipoLoc = tipoLoc;
    }

    public String getHeadEnd() {
        return headEnd;
    }

    public void setHeadEnd(String headEnd) {
        this.headEnd = headEnd;
    }

    public int getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(int estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    public String getAccionAuditoria() {
        return accionAuditoria;
    }

    public void setAccionAuditoria(String accionAuditoria) {
        this.accionAuditoria = accionAuditoria;
    }

    public Date getFechaAuditoria() {
        return fechaAuditoria;
    }

    public void setFechaAuditoria(Date fechaAuditoria) {
        this.fechaAuditoria = fechaAuditoria;
    }

    public String getUsuarioAuditoria() {
        return usuarioAuditoria;
    }

    public void setUsuarioAuditoria(String usuarioAuditoria) {
        this.usuarioAuditoria = usuarioAuditoria;
    }

    public BigDecimal getSesionAuditoria() {
        return sesionAuditoria;
    }

    public void setSesionAuditoria(BigDecimal sesionAuditoria) {
        this.sesionAuditoria = sesionAuditoria;
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
    
    public BigDecimal getSubEdificioId() {
        return subEdificioIdObj.getSubEdificioId();
    }

    public CmtBasicaMgl getEstrato() {
        return estrato;
    }

    public void setEstrato(CmtBasicaMgl estrato) {
        this.estrato = estrato;
    }

    public CmtBasicaMgl getEstadoAcometida() {
        return estadoAcometida;
    }

    public void setEstadoAcometida(CmtBasicaMgl estadoAcometida) {
        this.estadoAcometida = estadoAcometida;
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

    public String getDirectorIngenieroObra() {
        return directorIngenieroObra;
}

    public void setDirectorIngenieroObra(String directorIngenieroObra) {
        this.directorIngenieroObra = directorIngenieroObra;
    }

    public String getCambioestado() {
        return cambioestado;
    }

    public void setCambioestado(String cambioestado) {
        this.cambioestado = cambioestado;
    }



}
