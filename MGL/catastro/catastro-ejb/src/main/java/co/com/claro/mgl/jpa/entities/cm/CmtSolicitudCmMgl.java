/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities.cm;

import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.PreFichaXlsMgl;
import co.com.claro.mgl.utils.Constant;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
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
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "CMT_SOLICITUD_CM", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmtSolicitudCmMgl.findAll", query = "SELECT c FROM CmtSolicitudCmMgl c "),
    @NamedQuery(name = "CmtSolicitudCmMgl.findByFiltro", query = "SELECT s FROM CmtSolicitudCmMgl s WHERE s.estadoRegistro = :estadoRegistro AND s.tipoSolicitudObj.tipoSolicitudId IN :tipoSolicitudList AND s.estadoSolicitudObj.basicaId IN :estadoSolicitudList AND s.division = :division AND s.comunidad = :comunidad AND s.tipoSegmento = :tipoSegmento "),
    @NamedQuery(name = "CmtSolicitudCmMgl.findByCuentaMatriz", query = "SELECT s FROM CmtSolicitudCmMgl s WHERE s.cuentaMatrizObj = :cuentaMatriz"),
    @NamedQuery(name = "CmtSolicitudCmMgl.getCountByCuentaMatriz", query = "SELECT COUNT(1) FROM CmtSolicitudCmMgl s WHERE s.cuentaMatrizObj = :cuentaMatriz"),
    @NamedQuery(name = "CmtSolicitudCmMgl.getCountSolicitudCreateDay", query = "SELECT COUNT(1) FROM CmtSolicitudCmMgl s WHERE ((FUNC('TRUNC', s.fechaCreacion)) = (FUNC('TRUNC', CURRENT_DATE))) AND s.tipoSolicitudObj.tipoSolicitudId IN :tipoSolicitudList "),
    @NamedQuery(name = "CmtSolicitudCmMgl.getCountSolicitudGestionadaDay", query = "SELECT COUNT(1) FROM CmtSolicitudCmMgl s WHERE ((FUNC('TRUNC', s.fechaGestion)) = (FUNC('TRUNC', CURRENT_DATE))) AND s.tipoSolicitudObj.tipoSolicitudId IN :tipoSolicitudList "),
    @NamedQuery(name = "CmtSolicitudCmMgl.findByIdCmTipoSol", query =  "SELECT s FROM CmtSolicitudCmMgl s WHERE s.cuentaMatrizObj.numeroCuenta = :cuentaMatriz and s.origenSolicitud.basicaId = :origenSolicitud and s.estadoSolicitudObj.basicaId = :estadoSol"),
    @NamedQuery(name = "CmtSolicitudCmMgl.findByCmTipoSol", query =  "SELECT s FROM CmtSolicitudCmMgl s WHERE s.cuentaMatrizObj.cuentaMatrizId = :cuentaMatriz and s.tipoSolicitudObj = :tipoSolicitudObj and s.estadoSolicitudObj.basicaId = :estadoSol")
})
public class CmtSolicitudCmMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "CmtSolicitudCmMgl.CmtSolicitudCmMglSeq",
            sequenceName = "MGL_SCHEME.CMT_SOLICITUD_CM_SQ", allocationSize = 1)
    @GeneratedValue(generator = "CmtSolicitudCmMgl.CmtSolicitudCmMglSeq")
    @Column(name = "SOLICITUD_CM_ID", nullable = false)
    private BigDecimal solicitudCmId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUENTAMATRIZ_ID")
    private CmtCuentaMatrizMgl cuentaMatrizObj;
    @Column(name = "DIVISION")
    private String division;
    @Column(name = "COMUNIDAD")
    private String comunidad;

    @Column(name = "ES_MULTI_EDIFICIO")
    private String esMuiltiEdificio;
    @Column(name = "NUMERO_INTENTOS_LLAMADA")
    private BigDecimal numeroIntentosLlamada;
    @Column(name = "CANTIDAD_TORRES")
    private BigDecimal cantidadTorres;
    @Column(name = "TOTAL_UNIDADES")
    private BigDecimal totalUnidades;
    @Column(name = "CORREO_COPIA_SOLICITUD")
    private String correoCopiaSolicitud;
    @Column(name = "ASESOR")
    private String asesor;
    @Column(name = "TELEFONO_ASESOR")
    private String telefonoAsesor;
    @Column(name = "CORREO_ASESOR")
    private String correoAsesor;
    @Column(name = "RESULTADO_GESTION")
    private String resultadoGestion;
    @Column(name = "RESPUESTA_ACTUAL")
    private String respuestaActual;
    @Column(name = "FECHA_CREACION")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "FECHA_GESTION")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaGestion;
    @Column(name = "FECHA_INI_CREACION")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaInicioCreacion;
    @Column(name = "FECHA_INI_GESTION")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaInicioGestion;
    @Column(name = "FECHA_INI_GESTION_HHPP")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaInicioGestionHhpp;
    @Column(name = "FECHA_INI_GESTION_COBERTURA")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaInicioGestionCobertura;
    @Column(name = "FECHA_GESTION_HHPP")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaGestionHhpp;
    @Column(name = "FECHA_GESTION_COBERTURA")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaGestionCobertura;
    @Column(name = "NOMBRE_ARCHIVO")
    private String nombreArchivo;
    @Column(name = "TEMP_SOLOLICTUD")
    private String tempSolicitud;
    @Column(name = "TEMP_GESTION")
    private String tempGestion;
    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;
    @Column(name = "PERFIL_CREACION")
    private int perfilCreacion;
    @Column(name = "FECHA_EDICION")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaEdicion;
    @Column(name = "USUARIO_EDICION")
    private String usuarioEdicion;
    @Column(name = "PERFIL_EDICION")
    private int perfilEdicion;
    @Column(name = "DETALLE")
    private String detalle;
    @Column(name = "JUSTIFICACION")
    private String justificacion;
    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro;
    @Column(name = "MOD_SUBEDIFICIOS")
    private short modSubedificios;
    @Column(name = "MOD_DIRECCION")
    private short modDireccion;
    @Column(name = "MOD_DATOS_CM")
    private short modDatosCm;
    @Column(name = "MOD_COBERTURA")
    private short modCobertura;

    @Column(name = "CUENTA_CLIENTE_TRASLADAR")
    @Getter
    @Setter
    private String numeroClienteTrasladar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TIPO_SOLICITUD_ID", nullable = false)
    private CmtTipoSolicitudMgl tipoSolicitudObj;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_ESTADO")
    private CmtBasicaMgl estadoSolicitudObj;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_ACONDICIONAMIENTO", nullable = true)
    private CmtBasicaMgl tipoAcondicionamiento;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_SEGMENTO", nullable = true)
    private CmtBasicaMgl tipoSegmento;
    @Column(name = "NOMBRE_ADMINISTRADOR", nullable = true)
    private String nombreAdministrador;
    @Column(name = "CORREO_ADMINISTRADOR", nullable = true)
    private String correoAdministrador;
    @Column(name = "CELULAR_ADMINISTRADOR", nullable = true)
    private String celularAdministrador;
    @Column(name = "TEL_FIJO_ADMINISTRADOR", nullable = true)
    private String telFijoAdministrador;
    @OneToMany(mappedBy = "solicitudCMObj", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<CmtSolicitudSubEdificioMgl> listCmtSolicitudSubEdificioMgl;
    @OneToMany(mappedBy = "solicitudCMObj", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<CmtDireccionSolicitudMgl> listCmtDireccionesMgl;
    @OneToMany(mappedBy = "solicitudCm", fetch = FetchType.LAZY)
    @OrderBy("notasId ASC")
    private List<CmtNotasSolicitudMgl> notasSolicitudList;
    @OneToOne(mappedBy = "solicitud", fetch = FetchType.LAZY)
    private CmtOrdenTrabajoMgl ordenTrabajo;
    @Column(name = "CORREO_COPIA_GESTION")
    private String correoCopiaGestion;
    @Column(name = "USUARIO_GESTION_ID", nullable = true)
    private BigDecimal usuarioGestionId;
    @OneToMany(mappedBy = "cmtSolicitudCmMglObj", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<CmtSolicitudHhppMgl> listCmtSolicitudHhppMgl;
    @Column(name = "USUARIO_GESTION_COBERTURA_ID", nullable = true)
    private BigDecimal usuarioGestionCoberturaId;
    @Column(name = "USUARIO_SOLICITUD_ID", nullable = true)
    private BigDecimal usuarioSolicitudId;
    @OneToMany(mappedBy = "solicitudCm", fetch = FetchType.LAZY)
    private List<CmtHorarioRestriccionMgl> horarioSolicitudList;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_RESULT_GESTION", nullable = true)
    private CmtBasicaMgl resultGestion;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_RESULT_GESTION_MDCM", nullable = true)
    private CmtBasicaMgl resultGestionModDatosCm;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_RESULT_GESTION_MDIR", nullable = true)
    private CmtBasicaMgl resultGestionModDir;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_RESULT_GESTION_MSE", nullable = true)
    private CmtBasicaMgl resultGestionModSubEdi;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_RESULT_GESTION_MCO", nullable = true)
    private CmtBasicaMgl resultGestionModCobertura;
    @Transient
    private boolean solicitudModificacion;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GEOGRAFICO_PO_ID_DEPARTAMENTO", nullable = true)
    private GeograficoPoliticoMgl departamentoGpo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GEOGRAFICO_PO_ID_MUNICIPIO", nullable = true)
    private GeograficoPoliticoMgl ciudadGpo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GEOGRAFICO_PO_ID_POBLADO", nullable = true)
    private GeograficoPoliticoMgl centroPobladoGpo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ORIGEN_ID")
    private CmtBasicaMgl origenSolicitud;
    @Column(name = "AREA_SOLICITUD")
    private String areaSolictud;
    @Column(name = "DISPONIBILIDAD_GESTION", nullable = false, length = 2)
    private String disponibilidadGestion;
    @Column(name = "UNIDAD", nullable = true, length = 2)
    private String unidad;
    @Column(name = "NOMBRE_CONTACTO", nullable = true, length = 200)
    private String nombreContacto;
    @Column(name = "TEL_CONTACTO", nullable = true, length = 20)
    private String telefonoContacto;
    @OneToMany(mappedBy = "solicitudObj", fetch = FetchType.LAZY)
    @OrderBy("unidadPreviaId ASC")
    private List<CmtUnidadesPreviasMgl> listUnidadesPreviasCm;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_TECNOLOGIA", nullable = true)
    private CmtBasicaMgl basicaIdTecnologia;
    //Autor: victor bocanegra
    //Nuevo campo fecha programacion VT
    @Column(name = "FECHA_PROGRAMACION_VT")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaProgramcionVt;
    @Transient
    private String tiempoCreacionSolicitud;
    @Transient
    private boolean casaaEdificio;
     @Transient
    private String tipoVivienda;
    
    @Transient
    private PreFichaXlsMgl preficha;
    
      @Transient
    private String estadoTecnoRR;
    
    public BigDecimal getSolicitudCmId() {
        return solicitudCmId;
    }

    public void setSolicitudCmId(BigDecimal solicitudCmId) {
        this.solicitudCmId = solicitudCmId;
    }



    public BigDecimal getNumeroIntentosLlamada() {
        return numeroIntentosLlamada;
    }

    public void setNumeroIntentosLlamada(BigDecimal numeroIntentosLlamada) {
        this.numeroIntentosLlamada = numeroIntentosLlamada;
    }

    public BigDecimal getCantidadTorres() {
        return cantidadTorres;
    }

    public void setCantidadTorres(BigDecimal cantidadTorres) {
        this.cantidadTorres = cantidadTorres;
    }

    public BigDecimal getTotalUnidades() {
        return totalUnidades;
    }

    public void setTotalUnidades(BigDecimal totalUnidades) {
        this.totalUnidades = totalUnidades;
    }

    public String getAsesor() {
        return asesor;
    }

    public void setAsesor(String asesor) {
        this.asesor = asesor;
    }

    public String getTelefonoAsesor() {
        return telefonoAsesor;
    }

    public void setTelefonoAsesor(String telefonoAsesor) {
        this.telefonoAsesor = telefonoAsesor;
    }

    public String getCorreoAsesor() {
        return correoAsesor;
    }

    public void setCorreoAsesor(String correoAsesor) {
        this.correoAsesor = correoAsesor;
    }

    public String getResultadoGestion() {
        return resultadoGestion;
    }

    public void setResultadoGestion(String resultadoGestion) {
        this.resultadoGestion = resultadoGestion;
    }

    public String getRespuestaActual() {
        return respuestaActual;
    }

    public void setRespuestaActual(String respuestaActual) {
        this.respuestaActual = respuestaActual;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getTempGestion() {
        return tempGestion;
    }

    public void setTempGestion(String tempGestion) {
        this.tempGestion = tempGestion;
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

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }

    public int getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(int estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    public CmtTipoSolicitudMgl getTipoSolicitudObj() {
        return tipoSolicitudObj;
    }

    public void setTipoSolicitudObj(CmtTipoSolicitudMgl tipoSolicitudObj) {
        this.tipoSolicitudObj = tipoSolicitudObj;
    }

    public String getEsMuiltiEdificio() {
        return esMuiltiEdificio;
    }

    public void setEsMuiltiEdificio(String esMuiltiEdificio) {
        this.esMuiltiEdificio = esMuiltiEdificio;
    }

    public String getTempSolicitud() {
        return tempSolicitud;
    }

    public void setTempSolicitud(String tempSolicitud) {
        this.tempSolicitud = tempSolicitud;
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

    public CmtCuentaMatrizMgl getCuentaMatrizObj() {
        return cuentaMatrizObj;
    }

    public void setCuentaMatrizObj(CmtCuentaMatrizMgl cuentaMatrizObj) {
        this.cuentaMatrizObj = cuentaMatrizObj;
    }

    public List<CmtSolicitudSubEdificioMgl> getListCmtSolicitudSubEdificioMgl() {
        return listCmtSolicitudSubEdificioMgl;
    }

    public void setListCmtSolicitudSubEdificioMgl(List<CmtSolicitudSubEdificioMgl> listCmtSolicitudSubEdificioMgl) {
        this.listCmtSolicitudSubEdificioMgl = listCmtSolicitudSubEdificioMgl;
    }

    public boolean isSolicitudModificacion() {
        return solicitudModificacion;
    }

    public void setSolicitudModificacion(boolean solicitudModificacion) {
        this.solicitudModificacion = solicitudModificacion;
    }

    public CmtBasicaMgl getEstadoSolicitudObj() {
        return estadoSolicitudObj;
    }

    public void setEstadoSolicitudObj(CmtBasicaMgl estadoSolicitudObj) {
        this.estadoSolicitudObj = estadoSolicitudObj;
    }

    public short getModSubedificios() {
        return modSubedificios;
    }

    public void setModSubedificios(short modSubedificios) {
        this.modSubedificios = modSubedificios;
    }

    public short getModDireccion() {
        return modDireccion;
    }

    public void setModDireccion(short modDireccion) {
        this.modDireccion = modDireccion;
    }

    public short getModDatosCm() {
        return modDatosCm;
    }

    public void setModDatosCm(short modDatosCm) {
        this.modDatosCm = modDatosCm;
    }

    public short getModCobertura() {
        return modCobertura;
    }

    public void setModCobertura(short modCobertura) {
        this.modCobertura = modCobertura;
    }

    public CmtBasicaMgl getTipoAcondicionamiento() {
        return tipoAcondicionamiento;
    }

    public void setTipoAcondicionamiento(CmtBasicaMgl tipoAcondicionamiento) {
        this.tipoAcondicionamiento = tipoAcondicionamiento;
    }

    public CmtBasicaMgl getTipoSegmento() {
        return tipoSegmento;
    }

    public void setTipoSegmento(CmtBasicaMgl tipoSegmento) {
        this.tipoSegmento = tipoSegmento;
    }

    public String getNombreAdministrador() {
        return nombreAdministrador;
    }

    public void setNombreAdministrador(String nombreAdministrador) {
        this.nombreAdministrador = nombreAdministrador;
    }

    public String getCorreoAdministrador() {
        return correoAdministrador;
    }

    public void setCorreoAdministrador(String correoAdministrador) {
        this.correoAdministrador = correoAdministrador;
    }

    public String getCelularAdministrador() {
        return celularAdministrador;
    }

    public void setCelularAdministrador(String celularAdministrador) {
        this.celularAdministrador = celularAdministrador;
    }

    public String getTelFijoAdministrador() {
        return telFijoAdministrador;
    }

    public void setTelFijoAdministrador(String telFijoAdministrador) {
        this.telFijoAdministrador = telFijoAdministrador;
    }


    public String getCorreoCopiaSolicitud() {
        return correoCopiaSolicitud;
    }

    public void setCorreoCopiaSolicitud(String correoCopiaSolicitud) {
        this.correoCopiaSolicitud = correoCopiaSolicitud;
    }

    public String getCorreoCopiaGestion() {
        return correoCopiaGestion;
    }

    public void setCorreoCopiaGestion(String correoCopiaGestion) {
        this.correoCopiaGestion = correoCopiaGestion;
    }

    public CmtBasicaMgl getResultGestion() {
        return resultGestion;
    }

    public void setResultGestion(CmtBasicaMgl resultGestion) {
        this.resultGestion = resultGestion;
    }

    public CmtBasicaMgl getResultGestionModDatosCm() {
        return resultGestionModDatosCm;
    }

    public void setResultGestionModDatosCm(CmtBasicaMgl resultGestionModDatosCm) {
        this.resultGestionModDatosCm = resultGestionModDatosCm;
    }

    public CmtBasicaMgl getResultGestionModDir() {
        return resultGestionModDir;
    }

    public void setResultGestionModDir(CmtBasicaMgl resultGestionModDir) {
        this.resultGestionModDir = resultGestionModDir;
    }

    public CmtBasicaMgl getResultGestionModSubEdi() {
        return resultGestionModSubEdi;
    }

    public void setResultGestionModSubEdi(CmtBasicaMgl resultGestionModSubEdi) {
        this.resultGestionModSubEdi = resultGestionModSubEdi;
    }

    public CmtBasicaMgl getResultGestionModCobertura() {
        return resultGestionModCobertura;
    }

    public void setResultGestionModCobertura(CmtBasicaMgl resultGestionModCobertura) {
        this.resultGestionModCobertura = resultGestionModCobertura;
    }

    public BigDecimal getUsuarioGestionId() {
        return usuarioGestionId;
    }

    public void setUsuarioGestionId(BigDecimal usuarioGestionId) {
        this.usuarioGestionId = usuarioGestionId;
    }

    public BigDecimal getUsuarioGestionCoberturaId() {
        return usuarioGestionCoberturaId;
    }

    public void setUsuarioGestionCoberturaId(BigDecimal usuarioGestionCoberturaId) {
        this.usuarioGestionCoberturaId = usuarioGestionCoberturaId;
    }

    public BigDecimal getUsuarioSolicitudId() {
        return usuarioSolicitudId;
    }

    public void setUsuarioSolicitudId(BigDecimal usuarioSolicitudId) {
        this.usuarioSolicitudId = usuarioSolicitudId;
    }


    public Date getFechaGestion() {
        return fechaGestion;
    }

    public void setFechaGestion(Date fechaGestion) {
        this.fechaGestion = fechaGestion;
    }

    public Date getFechaInicioCreacion() {
        return fechaInicioCreacion;
    }

    public void setFechaInicioCreacion(Date fechaInicioCreacion) {
        this.fechaInicioCreacion = fechaInicioCreacion;
    }

    public Date getFechaInicioGestion() {
        return fechaInicioGestion;
    }

    public void setFechaInicioGestion(Date fechaInicioGestion) {
        this.fechaInicioGestion = fechaInicioGestion;
    }

    public Date getFechaInicioGestionHhpp() {
        return fechaInicioGestionHhpp;
    }

    public void setFechaInicioGestionHhpp(Date fechaInicioGestionHhpp) {
        this.fechaInicioGestionHhpp = fechaInicioGestionHhpp;
    }

    public Date getFechaInicioGestionCobertura() {
        return fechaInicioGestionCobertura;
    }

    public void setFechaInicioGestionCobertura(Date fechaInicioGestionCobertura) {
        this.fechaInicioGestionCobertura = fechaInicioGestionCobertura;
    }

    public Date getFechaGestionHhpp() {
        return fechaGestionHhpp;
    }

    public void setFechaGestionHhpp(Date fechaGestionHhpp) {
        this.fechaGestionHhpp = fechaGestionHhpp;
    }

    public Date getFechaGestionCobertura() {
        return fechaGestionCobertura;
    }

    public void setFechaGestionCobertura(Date fechaGestionCobertura) {
        this.fechaGestionCobertura = fechaGestionCobertura;
    }

    public List<CmtHorarioRestriccionMgl> getHorarioSolicitudList() {
        return horarioSolicitudList;
    }

    public void setHorarioSolicitudList(List<CmtHorarioRestriccionMgl> horarioSolicitudList) {
        this.horarioSolicitudList = horarioSolicitudList;
    }

    public String getTiempoCreacionSolicitud() {
        tiempoCreacionSolicitud = getTiempo(fechaInicioCreacion, fechaCreacion);
        return tiempoCreacionSolicitud;
    }

    public void setTiempoCreacionSolicitud(String tiempoCreacionSolicitud) {
        this.tiempoCreacionSolicitud = tiempoCreacionSolicitud;
    }

    //tiempo de espera

    public String getAnsSolicitud() {
        String result = getTiempo(fechaCreacion, fechaInicioGestion);
        return result;
    }

    public String getTiempoTotalSolicitud() {
        String result = getTiempo(fechaCreacion, fechaGestion);
        return result;
    }

    public String getTiempoTotalGestionSolicitud() {
        String result = getTiempo(fechaInicioGestion, fechaGestion);
        return result;
    }

    public String getTiempoGestionHHPPSolicitud() {
        String result = getTiempo(fechaInicioGestionHhpp, fechaGestionHhpp);
        return result;
    }

    public String getTiempoGestionCoberturaSolicitud() {
        String result = getTiempo(fechaInicioGestionCobertura, fechaGestionCobertura);
        return result;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getComunidad() {
        return comunidad;
    }

    public void setComunidad(String comunidad) {
        this.comunidad = comunidad;
    }

    public String getTiempo(Date dateInicio, Date dateFin) {
        String result = "00:00:00";
        if (dateInicio != null) {
            Date fechaG = new Date();
            if (dateFin != null) {
                fechaG = dateFin;
            }
            long diffDate = fechaG.getTime() - dateInicio.getTime();
            //Diferencia de las Fechas en Segundos
            long diffSeconds = Math.abs(diffDate / 1000);
            //Diferencia de las Fechas en Minutos
            long diffMinutes = Math.abs(diffDate / (60 * 1000));
            long seconds = diffSeconds % 60;
            long minutes = diffMinutes % 60;
            long hours = Math.abs(diffDate / (60 * 60 * 1000));

            String hoursStr = (hours < 10 ? "0" + String.valueOf(hours) : String.valueOf(hours));
            String minutesStr = (minutes < 10 ? "0" + String.valueOf(minutes) : String.valueOf(minutes));
            String secondsStr = (seconds < 10 ? "0" + String.valueOf(seconds) : String.valueOf(seconds));

            result = hoursStr + ":" + minutesStr + ":" + secondsStr;
        }
        return result;
    }

    public String getColorAlerta() {
        String colorResult = "blue";
        if (fechaCreacion != null) {
            long diffDate = (new Date().getTime()) - (fechaCreacion.getTime());
            //Diferencia de las Fechas en Minutos
            long diffMinutes = Math.abs(diffDate / (60 * 1000));
            long hours = Math.abs(diffDate / (60 * 60 * 1000));

            if ((int) diffMinutes >= tipoSolicitudObj.getAns()) {
                colorResult = "red";
            } else if ((int) diffMinutes < tipoSolicitudObj.getAns()
                    && (int) diffMinutes >= tipoSolicitudObj.getAnsAviso()) {
                colorResult = "yellow";
            } else if ((int) diffMinutes < tipoSolicitudObj.getAnsAviso()) {
                colorResult = "green";
            }
        }
        return colorResult;
    }
    
     public Date fechaFinalizacion(Date fechaInicio, String tempo) {

        Date fechaFin;
        Calendar calendar = Calendar.getInstance();
        if (fechaInicio != null) {

            calendar.setTime(fechaInicio); //tuFechaBase es un Date;
            int hora = 0;
            int minutos = 0;
            int segundos = 0;
            if (tempo != null && !tempo.isEmpty()) {
                String[] tiempoSol = tempo.split(":");
                hora = Integer.parseInt(tiempoSol[0]);
                minutos = Integer.parseInt(tiempoSol[1]);
                segundos = Integer.parseInt(tiempoSol[2]);
            }

            calendar.add(Calendar.HOUR, hora); //horasASumar es int.
            calendar.add(Calendar.MINUTE, minutos); //minutosASumar es int.
            calendar.add(Calendar.SECOND, segundos); //segundosASumar es int.

            //lo que más quieras sumar
            fechaFin = calendar.getTime();
        } else {

            fechaFin = null;
        }



        return fechaFin;
    }

    public boolean isTipoSolicitudModificacionCm() {
        boolean result = (tipoSolicitudObj != null
                && tipoSolicitudObj.getTipoSolicitudId() != null
                && tipoSolicitudObj.getAbreviatura().equalsIgnoreCase(Constant.TYPE_SOLICITUD_MODIFICACION_CM));
        return result;
    }

    public boolean isTipoSolicitudVt() {
        boolean result = (tipoSolicitudObj != null
                && tipoSolicitudObj.getTipoSolicitudId() != null
                && tipoSolicitudObj.getAbreviatura().equalsIgnoreCase(Constant.TYPE_SOLICITUD_VISITA_TECNICA));
        return result;
    }
    
     public boolean isTipoSolicitudModificacionCreacionHhpp() {
        boolean result = (tipoSolicitudObj != null
                && tipoSolicitudObj.getTipoSolicitudId() != null
                && tipoSolicitudObj.getAbreviatura().equalsIgnoreCase(Constant.TYPE_SOLICITUD_MODIFICACION_HHPP)
                ||tipoSolicitudObj.getAbreviatura().equalsIgnoreCase(Constant.TYPE_SOLICITUD_CREACION_HHPP));
        return result;
    }

    public List<CmtNotasSolicitudMgl> getNotasSolicitudList() {
        return notasSolicitudList;
    }

    public void setNotasSolicitudList(List<CmtNotasSolicitudMgl> notasSolicitudList) {
        this.notasSolicitudList = notasSolicitudList;
    }

    public List<CmtDireccionSolicitudMgl> getListCmtDireccionesMgl() {
        return listCmtDireccionesMgl;
    }

    public void setListCmtDireccionesMgl(List<CmtDireccionSolicitudMgl> listCmtDireccionesMgl) {
        this.listCmtDireccionesMgl = listCmtDireccionesMgl;
    }

    public List<CmtSolicitudHhppMgl> getListCmtSolicitudHhppMgl() {
        return listCmtSolicitudHhppMgl;
    }
    
    @PrePersist
    public void prePersist() {
        if (this.detalle == null || this.detalle.trim().isEmpty()) {
            this.detalle = "CREACION";
        }
        this.estadoRegistro = 1;
        this.disponibilidadGestion = "1";
    }
    public List<CmtSolicitudHhppMgl> getHhppToChangeList() {
        List<CmtSolicitudHhppMgl> tmpList = null;
        if (listCmtSolicitudHhppMgl != null && !listCmtSolicitudHhppMgl.isEmpty()){
            tmpList = new ArrayList<CmtSolicitudHhppMgl>();
            for(CmtSolicitudHhppMgl c : listCmtSolicitudHhppMgl){
                if (c.getEstadoRegistro() ==1){
                    tmpList.add(c);
                }
            }
        }
        return tmpList;
        
    }

    public void setListCmtSolicitudHhppMgl(List<CmtSolicitudHhppMgl> listCmtSolicitudHhppMgl) {
        this.listCmtSolicitudHhppMgl = listCmtSolicitudHhppMgl;
    }

    public List<CmtSolicitudHhppMgl> getModHhppCreateBySubEdificio(CmtSubEdificioMgl cmtSubEdificioMgl) {
        List<CmtSolicitudHhppMgl> cmtSolicitudHhppMgls = new ArrayList<CmtSolicitudHhppMgl>();
        if (listCmtSolicitudHhppMgl != null && !listCmtSolicitudHhppMgl.isEmpty()) {
            for (CmtSolicitudHhppMgl cshm : listCmtSolicitudHhppMgl) {
                if (cshm.getEstadoRegistro() >= 1 && (cshm.getCmtSubEdificioMglObj().getSubEdificioId().compareTo(cmtSubEdificioMgl.getSubEdificioId())) == 0) {
                    cmtSolicitudHhppMgls.add(cshm);
                }
            }
        }
        return cmtSolicitudHhppMgls;
    }

    public List<CmtSolicitudHhppMgl> getModHhppDeleteBySubEdificio(
            List<CmtSolicitudHhppMgl> listDeleteCmtSolicitudHhppMgl, 
            CmtSubEdificioMgl cmtSubEdificioMgl, 
            CmtSolicitudHhppMgl cmtSolicitudHhppMgl) {
        if (listDeleteCmtSolicitudHhppMgl != null && !listDeleteCmtSolicitudHhppMgl.isEmpty()) {
            if (listDeleteCmtSolicitudHhppMgl.contains(cmtSolicitudHhppMgl)) {
                listDeleteCmtSolicitudHhppMgl.remove(cmtSolicitudHhppMgl);
            }
        }
        return listDeleteCmtSolicitudHhppMgl;
    }

    public GeograficoPoliticoMgl getCiudadGpo() {
        return ciudadGpo;
    }

    public void setCiudadGpo(GeograficoPoliticoMgl ciudadGpo) {
        this.ciudadGpo = ciudadGpo;
    }

    public GeograficoPoliticoMgl getCentroPobladoGpo() {
        return centroPobladoGpo;
    }

    public void setCentroPobladoGpo(GeograficoPoliticoMgl centroPobladoGpo) {
        this.centroPobladoGpo = centroPobladoGpo;
    }

    public GeograficoPoliticoMgl getDepartamentoGpo() {
        return departamentoGpo;
    }

    public void setDepartamentoGpo(GeograficoPoliticoMgl departamentoGpo) {
        this.departamentoGpo = departamentoGpo;
    }

    public CmtBasicaMgl getOrigenSolicitud() {
        return origenSolicitud;
    }

    public void setOrigenSolicitud(CmtBasicaMgl origenSolicitud) {
        this.origenSolicitud = origenSolicitud;
    }

    public String getAreaSolictud() {
        return areaSolictud;
    }

    public void setAreaSolictud(String areaSolictud) {
        this.areaSolictud = areaSolictud;
    }

    public String getDisponibilidadGestion() {
        return disponibilidadGestion;
    }

    public void setDisponibilidadGestion(String disponibilidadGestion) {
        this.disponibilidadGestion = disponibilidadGestion;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getNombreContacto() {
        return nombreContacto;
    }

    public void setNombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public List<CmtUnidadesPreviasMgl> getListUnidadesPreviasCm() {
        return listUnidadesPreviasCm;
    }

    public void setListUnidadesPreviasCm(List<CmtUnidadesPreviasMgl> listUnidadesPreviasCm) {
        this.listUnidadesPreviasCm = listUnidadesPreviasCm;
    }

    public CmtOrdenTrabajoMgl getOrdenTrabajo() {
        return ordenTrabajo;
    }

    public void setOrdenTrabajo(CmtOrdenTrabajoMgl ordenTrabajo) {
        this.ordenTrabajo = ordenTrabajo;
    }

    public CmtNotasSolicitudMgl getLastNota() {
        CmtNotasSolicitudMgl lastNota = null;
        if (notasSolicitudList != null && !notasSolicitudList.isEmpty()) {
            lastNota = notasSolicitudList.get(0);
            for (CmtNotasSolicitudMgl n : notasSolicitudList) {
                if (n.getNotasId().compareTo(lastNota.getNotasId()) == 1) {
                    lastNota = n;
                }
            }
        }
        return lastNota;
    }

    public CmtNotasSolicitudMgl getFirstNota() {
        CmtNotasSolicitudMgl lastNota = null;
        if (notasSolicitudList != null && !notasSolicitudList.isEmpty()) {
            lastNota = notasSolicitudList.get(0);
            for (CmtNotasSolicitudMgl n : notasSolicitudList) {
                if (n.getNotasId().compareTo(lastNota.getNotasId()) == -1) {
                    lastNota = n;
                }
            }
        }
        return lastNota;
    }

    public CmtBasicaMgl getBasicaIdTecnologia() {
        return basicaIdTecnologia;
    }

    public void setBasicaIdTecnologia(CmtBasicaMgl basicaIdTecnologia) {
        this.basicaIdTecnologia = basicaIdTecnologia;
    }

    public Date getFechaProgramcionVt() {
        return fechaProgramcionVt;
    }

    public void setFechaProgramcionVt(Date fechaProgramcionVt) {
        this.fechaProgramcionVt = fechaProgramcionVt;
    }

    public boolean isCasaaEdificio() {
        return casaaEdificio;
    }

    public void setCasaaEdificio(boolean casaaEdificio) {
        this.casaaEdificio = casaaEdificio;
    }

    public String getTipoVivienda() {
        return tipoVivienda;
    }

    public void setTipoVivienda(String tipoVivienda) {
        this.tipoVivienda = tipoVivienda;
    }

    public PreFichaXlsMgl getPreficha() {
        return preficha;
    }

    public void setPreficha(PreFichaXlsMgl preficha) {
        this.preficha = preficha;
    }

    public String getEstadoTecnoRR() {
        return estadoTecnoRR;
    }

    public void setEstadoTecnoRR(String estadoTecnoRR) {
        this.estadoTecnoRR = estadoTecnoRR;
    }



    }
