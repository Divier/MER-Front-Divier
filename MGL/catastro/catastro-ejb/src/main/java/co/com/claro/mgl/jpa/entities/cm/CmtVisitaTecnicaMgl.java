package co.com.claro.mgl.jpa.entities.cm;

import co.com.claro.mgl.dtos.CmtSubEdificioDto;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entidad mapeo tabla CMT_VISIT_TECNICA
 *
 * alejandro.martinez.ext@claro.com.co
 *
 * @versión 1.00.0000
 */
@Entity
@Table(name = "CMT_VISITA_TECNICA", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmtVisitaTecnicaMgl.findAll", query = "SELECT c FROM CmtVisitaTecnicaMgl c"),
    @NamedQuery(name = "CmtVisitaTecnicaMgl.findVtByOt", query = "SELECT c FROM CmtVisitaTecnicaMgl c WHERE c.otObj.idOt = :otObj")})

public class CmtVisitaTecnicaMgl implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @SequenceGenerator(name = "CmtVisitaTecnicaMgl.CmtVisitaTecnicaSq",
            sequenceName = "MGL_SCHEME.CMT_VISITATECNICA_SQ",
            allocationSize = 1)
    @GeneratedValue(generator = "CmtVisitaTecnicaMgl.CmtVisitaTecnicaSq")
    @Column(name = "VT_ID", nullable = false)
    private BigDecimal idVt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OT_ID")
    private CmtOrdenTrabajoMgl otObj;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_TIPO_VT")
    private CmtBasicaMgl tipoVtObj;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_SEGMENTO")
    private CmtBasicaMgl segmentoObj;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_TIPO_ACTIVIDAD")
    private CmtBasicaMgl tipoActividadObj;

    @Column(name = "DESC_PUNTO_INICIAL_ACOMETIDA", nullable = true, length = 500)
    private String descPtoInilAcometida;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_TIPO_PTO_INIL_ACOMET")
    private CmtBasicaMgl tipoPtoIniAcometObj;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_CANALIZACION_INTERNA")
    private CmtBasicaMgl canalizacionIntObj;

    @Column(name = "PERSONA_INFORMA", nullable = true, length = 200)
    private String personaInforma;

    @Column(name = "DESCRIPCION_ACOMETIDA", nullable = true, length = 2000)
    private String descripcionAcometida;

    @Column(name = "OBSERVACIONES_ACOMETIDA", nullable = true, length = 2000)
    private String observacionesAcometida;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_TIPO_CONF_DISTB")
    private CmtBasicaMgl tipoConfDistribObj;

    @Column(name = "TAPS_EXTERNOS", nullable = true, length = 500)
    private String tapsExternos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_ALIMT_ELECT")
    private CmtBasicaMgl alimentElectObj;

    @Column(name = "VOLTAJE", nullable = true, length = 50)
    private String voltaje;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_TIPO_DISTRIBUCION")
    private CmtBasicaMgl tipoDistribucionObj;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_MANEJO_ASCENSORES")
    private CmtBasicaMgl manejoAscensoresObj;

    @Column(name = "MARCA", nullable = true, length = 200)
    private String marca;

    @Column(name = "TELEFONO", nullable = true, length = 20)
    private String telefono;

    @Column(name = "ACONDICIONAMIENTOS", nullable = true)
    private String acondicionamientos;

    @Column(name = "NOMBRE_ADMINISTRADOR", nullable = true, length = 100)
    private String nombreAdministrador;

    @Column(name = "CELULAR_ADMINISTRADOR", nullable = true, length = 50)
    private String celularAdministrador;

    @Column(name = "TELEFONO_ADMINISTRADOR", nullable = true, length = 50)
    private String telefonoAdministrador;

    @Column(name = "FECHA_AUTORIZA_TRABAJO", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaAutorizaAdministracion;

    @Column(name = "TRANSCRIPTOR_TECNICO", nullable = true, length = 200)
    private String transcriptorTecnico;

    @Column(name = "ALIADO_TECNICO", nullable = true, length = 200)
    private String aliadoTecnico;

    @Column(name = "NOMBRE_TECNICO", nullable = true, length = 200)
    private String nombreTecnico;

    @Column(name = "TELEFONO_TECNICO", nullable = true, length = 200)
    private String telefonoTecnico;

    @Column(name = "MOVIL_TECNICO", nullable = true, length = 100)
    private String movilTecnico;

    @Column(name = "COMPROMISO_INTERNET", nullable = true, length = 1)
    private String compromisoInternet;

    @Column(name = "COMPROMISO_TELEFONIA", nullable = true, length = 1)
    private String compromisoTelefonia;

    @Column(name = "COMPROMISO_TELEVISION", nullable = true, length = 1)
    private String compromisoTelevision;

    @Column(name = "COMPROMISO_HOGARES", nullable = true, length = 5)
    private String compromisoHogares;

    @Column(name = "COMPROMISO_FEC_INI", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date compromisoFecIni;

    @Column(name = "COMPROMISO_FEC_FIN", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date compromisoFecFin;

    @Column(name = "ASESOR_AVANZADA", nullable = true, length = 200)
    private String asesorAvanzada;

    @Column(name = "SUPERVISOR_AVANZADA", nullable = true, length = 200)
    private String supervisorAvanzada;

    @Column(name = "COORDINADOR_COMERCIAL", nullable = true, length = 200)
    private String coordinadorComercial;

    @Column(name = "INTERVENTOR_VT_ACOMETIDA", nullable = true, length = 200)
    private String interventorVtAcometida;

    @Column(name = "COORDINADOR_ACOMETIDA", nullable = true, length = 200)
    private String coordinadorAcometida;

    @Column(name = "CTO_CABLEADO_PRINCIPAL_EDIF", nullable = true)
    private BigDecimal ctoCableadoPrincipalEdif;

    @Column(name = "CTO_MAT_INF_PRECAB", nullable = true)
    private BigDecimal ctoMatInfPrecab;

    @Column(name = "CTO_MATERIALES_RED", nullable = true)
    private BigDecimal ctoMaterialesRed;

    @Column(name = "CTO_MANO_OBRA", nullable = true)
    private BigDecimal ctoManoObra;

    @Column(name = "COSTO_MT_DISENO", nullable = true)
    private BigDecimal costoMaterialesDiseno;

    @Column(name = "COSTO_MO_DISENO", nullable = true)
    private BigDecimal costoManoObraDiseno;

    @Column(name = "CTO_TOTAL_ACOMETIDA", nullable = true)
    private BigDecimal ctoTotalAcometida;

    @Column(name = "NOMBRE_ARCHIVO_PLANOS", nullable = true)
    private String plano;

    @Column(name = "VERSION_VT", nullable = true, length = 30)
    private int versionVt;

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

    @Column(name = "DETALLE", nullable = true, length = 200)
    private String detalle;

    @Column(name = "ES_MULTIEDIFICIO", nullable = true, length = 1)
    private String multiEdificio;

    @Column(name = "EDIFICIO_MANZANA", nullable = true, length = 1)
    private String edificioManzana;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPANIA_ID_ASCENSORES")
    private CmtCompaniaMgl ascensoresObj;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPANIA_ID_ADMINISTRACION")
    private CmtCompaniaMgl administracionObj;

    @Column(name = "P_APELLIDO_ADMON_COMERC", nullable = true, length = 100)
    private String pApellidoAdmonComerc;

    @Column(name = "P_APELLIDO_ASESOR_COMERC", nullable = true, length = 100)
    private String pApellidoAsesorComerc;

    @NotNull
    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro;

    @Column(name = "S_APELLIDO_ADMON_COMERC", nullable = true, length = 100)
    private String sApellidoAdmonComerc;

    @Column(name = "S_APELLIDO_ASESOR_COMERC", nullable = true, length = 100)
    private String sApellidoAsesorComerc;

    @Column(name = "NOMBRE_ADMON_COMERC", nullable = true, length = 100)
    private String nombreAdmonComerc;

    @Column(name = "NOMBRE_ASESOR_COMERC", nullable = true, length = 100)
    private String nombreAsesorComerc;

    @Column(name = "TELEFONO_ADMON_COMERC", nullable = true, length = 20)
    private String telefonoAdmonComerc;

    @Column(name = "TELEFONO_ASESOR_COMERC", nullable = true, length = 20)
    private String telefonoAsesorComerc;

    @Column(name = "OBSERVACIONES_ADMON", nullable = true, length = 2000)
    private String observacionesAdmon;

    @Column(name = "ORIGEN_VT", nullable = true, length = 2000)
    private String origenVt;

    @Column(name = "OTROS", nullable = true, length = 2000)
    private String otros;

    @Column(name = "VALOR_INT_BLQ_TOR", nullable = true, length = 30)
    private String valorIntBlqTor;

    @Column(name = "VALOR_UNIDADES", nullable = true, length = 30)
    private String valorUnidades;

    @Column(name = "TIPO_ESTAB_CONSTRUCTORA", nullable = true, length = 5)
    private String tipoEstabConstructora;

    @Column(name = "UNIDADES_PLANEADAS", nullable = true, length = 5)
    private String uniPlan;

    @Column(name = "ASESOR_CONSTRUCTORA", nullable = true, length = 200)
    private String asesorConstructora;

    @Column(name = "COD_ASESOR_CONSTRUCTORA", nullable = true, length = 50)
    private String codAsesorConst;

    @Column(name = "ESPECIALISTA_COSTRUCTORA", nullable = true, length = 200)
    private String especialistaConstructora;

    @Column(name = "COD_ESPECIALISTA_CONSTRUCTORA", nullable = true, length = 50)
    private String codEspecialistaConst;

    @Column(name = "UNIDAD_APARTAMENTO", nullable = true, length = 1)
    private String unidadApartamento;

    @Column(name = "UNIDAD_CASA", nullable = true, length = 1)
    private String unidadCasa;

    @Column(name = "UNIDAD_OFICINA", nullable = true, length = 1)
    private String unidadOficina;

    @Column(name = "UNIDAD_LOCAL", nullable = true, length = 1)
    private String unidadLocal;

    @Column(name = "PLANTA_ELECTRICA", nullable = true, length = 1)
    private String plantaElectrica;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_ESTRATO")
    private CmtBasicaMgl estratoObj;

    @Column(name = "OTRO_TIPO_DISTR", nullable = true, length = 100)
    private String otroTipoDistribucion;

    @Column(name = "FECHA_ENTREGA_EDIFICIO", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaEntregaEdificio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPANIA_ID_CONSTRUCTORA")
    private CmtCompaniaMgl companiaConstructora;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_ORIGEN_DATOS")
    private CmtBasicaMgl origenDatos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_TIPO_PROYECTO")
    private CmtBasicaMgl tipoProyecto;

    @Column(name = "OBSERVACION_AUTORIZACION", nullable = true, length = 2000)
    private String observacionAutorizacion;

    @OneToMany(mappedBy = "vtObj", fetch = FetchType.LAZY)
    private List<CmtSubEdificiosVt> listCmtSubEdificiosVt;

    @OneToMany(mappedBy = "vtObj", fetch = FetchType.LAZY)
    private List<CmtItemVtMgl> listItemVt;

    @Column(name = "NOMBRE_DOC_VT_DISENO", nullable = true, length = 200)
    private String nombreDocumentoDiseno;

    @Basic
    @Column(name = "META")
    private BigDecimal meta;
    
    @Column(name = "ESTADO_VISITA_TECNICA", nullable = true)
    private BigDecimal estadoVisitaTecnica;
    
    @Column(name = "TIEMPO_RECUPERACION", nullable = false)
    private BigDecimal tiempoRecuperacion;
    
    @Column(name = "PISO_TORRE")
    private String pisoTorre;
    
    @Column(name = "APORTE_FINANCIERO")
    private long aporteFinanciero;
        
    
    //valbuenayf
    @Transient
    private List<CmtSubEdificioDto> listaSubEdificio;


    /**
     * @return the idVt
     */
    public BigDecimal getIdVt() {
        return idVt;
    }

    /**
     * @param idVt the idVt to set
     */
    public void setIdVt(BigDecimal idVt) {
        this.idVt = idVt;
    }

    /**
     * @return the otObj
     */
    public CmtOrdenTrabajoMgl getOtObj() {
        return otObj;
    }

    /**
     * @param otObj the otObj to set
     */
    public void setOtObj(CmtOrdenTrabajoMgl otObj) {
        this.otObj = otObj;
    }

    /**
     * @return the tipoVtObj
     */
    public CmtBasicaMgl getTipoVtObj() {
        return tipoVtObj;
    }

    /**
     * @param tipoVtObj the tipoVtObj to set
     */
    public void setTipoVtObj(CmtBasicaMgl tipoVtObj) {
        this.tipoVtObj = tipoVtObj;
    }

    /**
     * @return the segmentoObj
     */
    public CmtBasicaMgl getSegmentoObj() {
        return segmentoObj;
    }

    /**
     * @param segmentoObj the segmentoObj to set
     */
    public void setSegmentoObj(CmtBasicaMgl segmentoObj) {
        this.segmentoObj = segmentoObj;
    }

    /**
     * @return the tipoActividadObj
     */
    public CmtBasicaMgl getTipoActividadObj() {
        return tipoActividadObj;
    }

    /**
     * @param tipoActividadObj the tipoActividadObj to set
     */
    public void setTipoActividadObj(CmtBasicaMgl tipoActividadObj) {
        this.tipoActividadObj = tipoActividadObj;
    }

    /**
     * @return the descPtoInilAcometida
     */
    public String getDescPtoInilAcometida() {
        return descPtoInilAcometida;
    }

    /**
     * @param descPtoInilAcometida the descPtoInilAcometida to set
     */
    public void setDescPtoInilAcometida(String descPtoInilAcometida) {
        this.descPtoInilAcometida = descPtoInilAcometida;
    }

    /**
     * @return the tipoPtoIniAcometObj
     */
    public CmtBasicaMgl getTipoPtoIniAcometObj() {
        return tipoPtoIniAcometObj;
    }

    /**
     * @param tipoPtoIniAcometObj the tipoPtoIniAcometObj to set
     */
    public void setTipoPtoIniAcometObj(CmtBasicaMgl tipoPtoIniAcometObj) {
        this.tipoPtoIniAcometObj = tipoPtoIniAcometObj;
    }

    /**
     * @return the canalizacionIntObj
     */
    public CmtBasicaMgl getCanalizacionIntObj() {
        return canalizacionIntObj;
    }

    /**
     * @param canalizacionIntObj the canalizacionIntObj to set
     */
    public void setCanalizacionIntObj(CmtBasicaMgl canalizacionIntObj) {
        this.canalizacionIntObj = canalizacionIntObj;
    }

    /**
     * @return the personaInforma
     */
    public String getPersonaInforma() {
        return personaInforma;
    }

    /**
     * @param personaInforma the personaInforma to set
     */
    public void setPersonaInforma(String personaInforma) {
        this.personaInforma = personaInforma;
    }

    /**
     * @return the descripcionAcometida
     */
    public String getDescripcionAcometida() {
        return descripcionAcometida;
    }

    /**
     * @param descripcionAcometida the descripcionAcometida to set
     */
    public void setDescripcionAcometida(String descripcionAcometida) {
        this.descripcionAcometida = descripcionAcometida;
    }

    /**
     * @return the observacionesAcometida
     */
    public String getObservacionesAcometida() {
        return observacionesAcometida;
    }

    /**
     * @param observacionesAcometida the observacionesAcometida to set
     */
    public void setObservacionesAcometida(String observacionesAcometida) {
        this.observacionesAcometida = observacionesAcometida;
    }

    /**
     * @return the tipoConfDistribObj
     */
    public CmtBasicaMgl getTipoConfDistribObj() {
        return tipoConfDistribObj;
    }

    /**
     * @param tipoConfDistribObj the tipoConfDistribObj to set
     */
    public void setTipoConfDistribObj(CmtBasicaMgl tipoConfDistribObj) {
        this.tipoConfDistribObj = tipoConfDistribObj;
    }

    /**
     * @return the tapsExternos
     */
    public String getTapsExternos() {
        return tapsExternos;
    }

    /**
     * @param tapsExternos the tapsExternos to set
     */
    public void setTapsExternos(String tapsExternos) {
        this.tapsExternos = tapsExternos;
    }

    /**
     * @return the alimentElectObj
     */
    public CmtBasicaMgl getAlimentElectObj() {
        return alimentElectObj;
    }

    /**
     * @param alimentElectObj the alimentElectObj to set
     */
    public void setAlimentElectObj(CmtBasicaMgl alimentElectObj) {
        this.alimentElectObj = alimentElectObj;
    }

    /**
     * @return the voltaje
     */
    public String getVoltaje() {
        return voltaje;
    }

    /**
     * @param voltaje the voltaje to set
     */
    public void setVoltaje(String voltaje) {
        this.voltaje = voltaje;
    }

    /**
     * @return the tipoDistribucionObj
     */
    public CmtBasicaMgl getTipoDistribucionObj() {
        return tipoDistribucionObj;
    }

    /**
     * @param tipoDistribucionObj the tipoDistribucionObj to set
     */
    public void setTipoDistribucionObj(CmtBasicaMgl tipoDistribucionObj) {
        this.tipoDistribucionObj = tipoDistribucionObj;
    }

    /**
     * @return the manejoAscensoresObj
     */
    public CmtBasicaMgl getManejoAscensoresObj() {
        return manejoAscensoresObj;
    }

    /**
     * @param manejoAscensoresObj the manejoAscensoresObj to set
     */
    public void setManejoAscensoresObj(CmtBasicaMgl manejoAscensoresObj) {
        this.manejoAscensoresObj = manejoAscensoresObj;
    }

    /**
     * @return the marca
     */
    public String getMarca() {
        return marca;
    }

    /**
     * @param marca the marca to set
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the acondicionamientos
     */
    public String getAcondicionamientos() {
        return acondicionamientos;
    }

    /**
     * @param acondicionamientos the acondicionamientos to set
     */
    public void setAcondicionamientos(String acondicionamientos) {
        this.acondicionamientos = acondicionamientos;
    }

    /**
     * @return the nombreAdministrador
     */
    public String getNombreAdministrador() {
        return nombreAdministrador;
    }

    /**
     * @param nombreAdministrador the nombreAdministrador to set
     */
    public void setNombreAdministrador(String nombreAdministrador) {
        this.nombreAdministrador = nombreAdministrador;
    }

    /**
     * @return the celularAdministrador
     */
    public String getCelularAdministrador() {
        return celularAdministrador;
    }

    /**
     * @param celularAdministrador the celularAdministrador to set
     */
    public void setCelularAdministrador(String celularAdministrador) {
        this.celularAdministrador = celularAdministrador;
    }

    /**
     * @return the telefonoAdministrador
     */
    public String getTelefonoAdministrador() {
        return telefonoAdministrador;
    }

    /**
     * @param telefonoAdministrador the telefonoAdministrador to set
     */
    public void setTelefonoAdministrador(String telefonoAdministrador) {
        this.telefonoAdministrador = telefonoAdministrador;
    }

    /**
     * @return the fechaAutorizaAdministracion
     */
    public Date getFechaAutorizaAdministracion() {
        return fechaAutorizaAdministracion;
    }

    /**
     * @param fechaAutorizaAdministracion the fechaAutorizaAdministracion to set
     */
    public void setFechaAutorizaAdministracion(Date fechaAutorizaAdministracion) {
        this.fechaAutorizaAdministracion = fechaAutorizaAdministracion;
    }

    /**
     * @return the transcriptorTecnico
     */
    public String getTranscriptorTecnico() {
        return transcriptorTecnico;
    }

    /**
     * @param transcriptorTecnico the transcriptorTecnico to set
     */
    public void setTranscriptorTecnico(String transcriptorTecnico) {
        this.transcriptorTecnico = transcriptorTecnico;
    }

    /**
     * @return the aliadoTecnico
     */
    public String getAliadoTecnico() {
        return aliadoTecnico;
    }

    /**
     * @param aliadoTecnico the aliadoTecnico to set
     */
    public void setAliadoTecnico(String aliadoTecnico) {
        this.aliadoTecnico = aliadoTecnico;
    }

    /**
     * @return the nombreTecnico
     */
    public String getNombreTecnico() {
        return nombreTecnico;
    }

    /**
     * @param nombreTecnico the nombreTecnico to set
     */
    public void setNombreTecnico(String nombreTecnico) {
        this.nombreTecnico = nombreTecnico;
    }

    /**
     * @return the telefonoTecnico
     */
    public String getTelefonoTecnico() {
        return telefonoTecnico;
    }

    /**
     * @param telefonoTecnico the telefonoTecnico to set
     */
    public void setTelefonoTecnico(String telefonoTecnico) {
        this.telefonoTecnico = telefonoTecnico;
    }

    /**
     * @return the movilTecnico
     */
    public String getMovilTecnico() {
        return movilTecnico;
    }

    /**
     * @param movilTecnico the movilTecnico to set
     */
    public void setMovilTecnico(String movilTecnico) {
        this.movilTecnico = movilTecnico;
    }

    /**
     * @return the compromisoInternet
     */
    public String getCompromisoInternet() {
        return compromisoInternet;
    }

    /**
     * @param compromisoInternet the compromisoInternet to set
     */
    public void setCompromisoInternet(String compromisoInternet) {
        this.compromisoInternet = compromisoInternet;
    }

    /**
     * @return the compromisoTelefonia
     */
    public String getCompromisoTelefonia() {
        return compromisoTelefonia;
    }

    /**
     * @param compromisoTelefonia the compromisoTelefonia to set
     */
    public void setCompromisoTelefonia(String compromisoTelefonia) {
        this.compromisoTelefonia = compromisoTelefonia;
    }

    /**
     * @return the compromisoTelevision
     */
    public String getCompromisoTelevision() {
        return compromisoTelevision;
    }

    /**
     * @param compromisoTelevision the compromisoTelevision to set
     */
    public void setCompromisoTelevision(String compromisoTelevision) {
        this.compromisoTelevision = compromisoTelevision;
    }

    /**
     * @return the compromisoHogares
     */
    public String getCompromisoHogares() {
        return compromisoHogares;
    }

    /**
     * @param compromisoHogares the compromisoHogares to set
     */
    public void setCompromisoHogares(String compromisoHogares) {
        this.compromisoHogares = compromisoHogares;
    }

    /**
     * @return the compromisoFecIni
     */
    public Date getCompromisoFecIni() {
        return compromisoFecIni;
    }

    /**
     * @param compromisoFecIni the compromisoFecIni to set
     */
    public void setCompromisoFecIni(Date compromisoFecIni) {
        this.compromisoFecIni = compromisoFecIni;
    }

    /**
     * @return the compromisoFecFin
     */
    public Date getCompromisoFecFin() {
        return compromisoFecFin;
    }

    /**
     * @param compromisoFecFin the compromisoFecFin to set
     */
    public void setCompromisoFecFin(Date compromisoFecFin) {
        this.compromisoFecFin = compromisoFecFin;
    }

    /**
     * @return the asesorAvanzada
     */
    public String getAsesorAvanzada() {
        return asesorAvanzada;
    }

    /**
     * @param asesorAvanzada the asesorAvanzada to set
     */
    public void setAsesorAvanzada(String asesorAvanzada) {
        this.asesorAvanzada = asesorAvanzada;
    }

    /**
     * @return the supervisorAvanzada
     */
    public String getSupervisorAvanzada() {
        return supervisorAvanzada;
    }

    /**
     * @param supervisorAvanzada the supervisorAvanzada to set
     */
    public void setSupervisorAvanzada(String supervisorAvanzada) {
        this.supervisorAvanzada = supervisorAvanzada;
    }

    /**
     * @return the coordinadorComercial
     */
    public String getCoordinadorComercial() {
        return coordinadorComercial;
    }

    /**
     * @param coordinadorComercial the coordinadorComercial to set
     */
    public void setCoordinadorComercial(String coordinadorComercial) {
        this.coordinadorComercial = coordinadorComercial;
    }

    /**
     * @return the interventorVtAcometida
     */
    public String getInterventorVtAcometida() {
        return interventorVtAcometida;
    }

    /**
     * @param interventorVtAcometida the interventorVtAcometida to set
     */
    public void setInterventorVtAcometida(String interventorVtAcometida) {
        this.interventorVtAcometida = interventorVtAcometida;
    }

    /**
     * @return the coordinadorAcometida
     */
    public String getCoordinadorAcometida() {
        return coordinadorAcometida;
    }

    /**
     * @param coordinadorAcometida the coordinadorAcometida to set
     */
    public void setCoordinadorAcometida(String coordinadorAcometida) {
        this.coordinadorAcometida = coordinadorAcometida;
    }

    /**
     * @return the ctoCableadoPrincipalEdif
     */
    public BigDecimal getCtoCableadoPrincipalEdif() {
        return ctoCableadoPrincipalEdif;
    }

    /**
     * @param ctoCableadoPrincipalEdif the ctoCableadoPrincipalEdif to set
     */
    public void setCtoCableadoPrincipalEdif(BigDecimal ctoCableadoPrincipalEdif) {
        this.ctoCableadoPrincipalEdif = ctoCableadoPrincipalEdif;
    }

    /**
     * @return the ctoMatInfPrecab
     */
    public BigDecimal getCtoMatInfPrecab() {
        return ctoMatInfPrecab;
    }

    /**
     * @param ctoMatInfPrecab the ctoMatInfPrecab to set
     */
    public void setCtoMatInfPrecab(BigDecimal ctoMatInfPrecab) {
        this.ctoMatInfPrecab = ctoMatInfPrecab;
    }

    /**
     * @return the ctoMaterialesRed
     */
    public BigDecimal getCtoMaterialesRed() {
        return ctoMaterialesRed;
    }

    /**
     * @param ctoMaterialesRed the ctoMaterialesRed to set
     */
    public void setCtoMaterialesRed(BigDecimal ctoMaterialesRed) {
        this.ctoMaterialesRed = ctoMaterialesRed;
    }

    /**
     * @return the ctoManoObra
     */
    public BigDecimal getCtoManoObra() {
        return ctoManoObra;
    }

    /**
     * @param ctoManoObra the ctoManoObra to set
     */
    public void setCtoManoObra(BigDecimal ctoManoObra) {
        this.ctoManoObra = ctoManoObra;
    }

    /**
     * @return the ctoTotalAcometida
     */
    public BigDecimal getCtoTotalAcometida() {
        return ctoTotalAcometida;
    }

    /**
     * @param ctoTotalAcometida the ctoTotalAcometida to set
     */
    public void setCtoTotalAcometida(BigDecimal ctoTotalAcometida) {
        this.ctoTotalAcometida = ctoTotalAcometida;
    }

    /**
     * @return the plano
     */
    public String getPlano() {
        return plano;
    }

    /**
     * @param plano the plano to set
     */
    public void setPlano(String plano) {
        this.plano = plano;
    }

    /**
     * @return the versionVt
     */
    public int getVersionVt() {
        return versionVt;
    }

    /**
     * @param versionVt the versionVt to set
     */
    public void setVersionVt(int versionVt) {
        this.versionVt = versionVt;
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
     * @return the detalle
     */
    public String getDetalle() {
        return detalle;
    }

    /**
     * @param detalle the detalle to set
     */
    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    /**
     * @return the multiEdificio
     */
    public String getMultiEdificio() {
        return multiEdificio;
    }

    /**
     * @param multiEdificio the multiEdificio to set
     */
    public void setMultiEdificio(String multiEdificio) {
        this.multiEdificio = multiEdificio;
    }

    /**
     * @return the edificioManzana
     */
    public String getEdificioManzana() {
        return edificioManzana;
    }

    /**
     * @param edificioManzana the edificioManzana to set
     */
    public void setEdificioManzana(String edificioManzana) {
        this.edificioManzana = edificioManzana;
    }

    /**
     * @return the ascensoresObj
     */
    public CmtCompaniaMgl getAscensoresObj() {
        return ascensoresObj;
    }

    /**
     * @param ascensoresObj the ascensoresObj to set
     */
    public void setAscensoresObj(CmtCompaniaMgl ascensoresObj) {
        this.ascensoresObj = ascensoresObj;
    }

    /**
     * @return the administracionObj
     */
    public CmtCompaniaMgl getAdministracionObj() {
        return administracionObj;
    }

    /**
     * @param administracionObj the administracionObj to set
     */
    public void setAdministracionObj(CmtCompaniaMgl administracionObj) {
        this.administracionObj = administracionObj;
    }

    /**
     * @return the pApellidoAdmonComerc
     */
    public String getPApellidoAdmonComerc() {
        return pApellidoAdmonComerc;
    }

    /**
     * @param pApellidoAdmonComerc the pApellidoAdmonComerc to set
     */
    public void setPApellidoAdmonComerc(String pApellidoAdmonComerc) {
        this.pApellidoAdmonComerc = pApellidoAdmonComerc;
    }

    /**
     * @return the pApellidoAsesorComerc
     */
    public String getPApellidoAsesorComerc() {
        return pApellidoAsesorComerc;
    }

    /**
     * @param pApellidoAsesorComerc the pApellidoAsesorComerc to set
     */
    public void setPApellidoAsesorComerc(String pApellidoAsesorComerc) {
        this.pApellidoAsesorComerc = pApellidoAsesorComerc;
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
     * @return the sApellidoAdmonComerc
     */
    public String getSApellidoAdmonComerc() {
        return sApellidoAdmonComerc;
    }

    /**
     * @param sApellidoAdmonComerc the sApellidoAdmonComerc to set
     */
    public void setSApellidoAdmonComerc(String sApellidoAdmonComerc) {
        this.sApellidoAdmonComerc = sApellidoAdmonComerc;
    }

    /**
     * @return the sApellidoAsesorComerc
     */
    public String getSApellidoAsesorComerc() {
        return sApellidoAsesorComerc;
    }

    /**
     * @param sApellidoAsesorComerc the sApellidoAsesorComerc to set
     */
    public void setSApellidoAsesorComerc(String sApellidoAsesorComerc) {
        this.sApellidoAsesorComerc = sApellidoAsesorComerc;
    }

    /**
     * @return the nombreAdmonComerc
     */
    public String getNombreAdmonComerc() {
        return nombreAdmonComerc;
    }

    /**
     * @param nombreAdmonComerc the nombreAdmonComerc to set
     */
    public void setNombreAdmonComerc(String nombreAdmonComerc) {
        this.nombreAdmonComerc = nombreAdmonComerc;
    }

    /**
     * @return the nombreAsesorComerc
     */
    public String getNombreAsesorComerc() {
        return nombreAsesorComerc;
    }

    /**
     * @param nombreAsesorComerc the nombreAsesorComerc to set
     */
    public void setNombreAsesorComerc(String nombreAsesorComerc) {
        this.nombreAsesorComerc = nombreAsesorComerc;
    }

    /**
     * @return the telefonoAdmonComerc
     */
    public String getTelefonoAdmonComerc() {
        return telefonoAdmonComerc;
    }

    /**
     * @param telefonoAdmonComerc the telefonoAdmonComerc to set
     */
    public void setTelefonoAdmonComerc(String telefonoAdmonComerc) {
        this.telefonoAdmonComerc = telefonoAdmonComerc;
    }

    /**
     * @return the telefonoAsesorComerc
     */
    public String getTelefonoAsesorComerc() {
        return telefonoAsesorComerc;
    }

    /**
     * @param telefonoAsesorComerc the telefonoAsesorComerc to set
     */
    public void setTelefonoAsesorComerc(String telefonoAsesorComerc) {
        this.telefonoAsesorComerc = telefonoAsesorComerc;
    }

    public List<CmtSubEdificiosVt> getListCmtSubEdificiosVt() {
        return listCmtSubEdificiosVt;
    }

    public void setListCmtSubEdificiosVt(List<CmtSubEdificiosVt> listCmtSubEdificiosVt) {
        this.listCmtSubEdificiosVt = listCmtSubEdificiosVt;
    }

    public String getObservacionesAdmon() {
        return observacionesAdmon;
    }

    public void setObservacionesAdmon(String observacionesAdmon) {
        this.observacionesAdmon = observacionesAdmon;
    }

    public String getOrigenVt() {
        return origenVt;
    }

    public String getOtros() {
        return otros;
    }

    public void setOtros(String otros) {
        this.otros = otros;
    }

    public String getValorIntBlqTor() {
        return valorIntBlqTor;
    }

    public void setValorIntBlqTor(String valorIntBlqTor) {
        this.valorIntBlqTor = valorIntBlqTor;
    }

    public String getValorUnidades() {
        return valorUnidades;
    }

    public void setValorUnidades(String valorUnidades) {
        this.valorUnidades = valorUnidades;
    }

    public void setOrigenVt(String origenVt) {
        this.origenVt = origenVt;
    }

    public List<CmtItemVtMgl> getListItemVt() {
        return listItemVt;
    }

    public void setListItemVt(List<CmtItemVtMgl> listItemVt) {
        this.listItemVt = listItemVt;
    }

    public String getUniPlan() {
        return uniPlan;
    }

    public void setUniPlan(String uniPlan) {
        this.uniPlan = uniPlan;
    }

    public String getAsesorConstructora() {
        return asesorConstructora;
    }

    public void setAsesorConstructora(String asesorConstructora) {
        this.asesorConstructora = asesorConstructora;
    }

    public String getCodAsesorConst() {
        return codAsesorConst;
    }

    public void setCodAsesorConst(String codAsesorConst) {
        this.codAsesorConst = codAsesorConst;
    }

    public String getEspecialistaConstructora() {
        return especialistaConstructora;
    }

    public void setEspecialistaConstructora(String especialistaConstructora) {
        this.especialistaConstructora = especialistaConstructora;
    }

    public String getCodEspecialistaConst() {
        return codEspecialistaConst;
    }

    public void setCodEspecialistaConst(String codEspecialistaConst) {
        this.codEspecialistaConst = codEspecialistaConst;
    }

    public String getTipoEstabConstructora() {
        return tipoEstabConstructora;
    }

    public void setTipoEstabConstructora(String tipoEstabConstructora) {
        this.tipoEstabConstructora = tipoEstabConstructora;
    }

    public String getUnidadApartamento() {
        return unidadApartamento;
    }

    public void setUnidadApartamento(String unidadApartamento) {
        this.unidadApartamento = unidadApartamento;
    }

    public String getUnidadCasa() {
        return unidadCasa;
    }

    public void setUnidadCasa(String unidadCasa) {
        this.unidadCasa = unidadCasa;
    }

    public String getUnidadOficina() {
        return unidadOficina;
    }

    public void setUnidadOficina(String unidadOficina) {
        this.unidadOficina = unidadOficina;
    }

    public String getUnidadLocal() {
        return unidadLocal;
    }

    public void setUnidadLocal(String unidadLocal) {
        this.unidadLocal = unidadLocal;
    }

    public String getPlantaElectrica() {
        return plantaElectrica;
    }

    public void setPlantaElectrica(String plantaElectrica) {
        this.plantaElectrica = plantaElectrica;
    }

    public CmtBasicaMgl getEstratoObj() {
        return estratoObj;
    }

    public void setEstratoObj(CmtBasicaMgl estratoObj) {
        this.estratoObj = estratoObj;
    }

    public String getOtroTipoDistribucion() {
        return otroTipoDistribucion;
    }

    public void setOtroTipoDistribucion(String otroTipoDistribucion) {
        this.otroTipoDistribucion = otroTipoDistribucion;
    }

    public Date getFechaEntregaEdificio() {
        return fechaEntregaEdificio;
    }

    public void setFechaEntregaEdificio(Date fechaEntregaEdificio) {
        this.fechaEntregaEdificio = fechaEntregaEdificio;
    }

    public CmtCompaniaMgl getCompaniaConstructora() {
        return companiaConstructora;
    }

    public void setCompaniaConstructora(CmtCompaniaMgl companiaConstructora) {
        this.companiaConstructora = companiaConstructora;
    }

    public CmtBasicaMgl getOrigenDatos() {
        return origenDatos;
    }

    public void setOrigenDatos(CmtBasicaMgl origenDatos) {
        this.origenDatos = origenDatos;
    }

    public CmtBasicaMgl getTipoProyecto() {
        return tipoProyecto;
    }

    public void setTipoProyecto(CmtBasicaMgl tipoProyecto) {
        this.tipoProyecto = tipoProyecto;
    }

    public String getObservacionAutorizacion() {
        return observacionAutorizacion;
    }

    public void setObservacionAutorizacion(String observacionAutorizacion) {
        this.observacionAutorizacion = observacionAutorizacion;
    }

    public BigDecimal getCostoMaterialesDiseno() {
        return costoMaterialesDiseno == null ? new BigDecimal(BigInteger.ZERO) : costoMaterialesDiseno;
    }

    public void setCostoMaterialesDiseno(BigDecimal costoMaterialesDiseno) {
        this.costoMaterialesDiseno = costoMaterialesDiseno;
    }

    public BigDecimal getCostoManoObraDiseno() {
        return costoManoObraDiseno == null ? new BigDecimal(BigInteger.ZERO) : costoManoObraDiseno;
    }

    public void setCostoManoObraDiseno(BigDecimal costoManoObraDiseno) {
        this.costoManoObraDiseno = costoManoObraDiseno;
    }

    public String getNombreDocumentoDiseno() {
        return nombreDocumentoDiseno;
    }

    public void setNombreDocumentoDiseno(String nombreDocumentoDiseno) {
        this.nombreDocumentoDiseno = nombreDocumentoDiseno;
    }

    public BigDecimal getMeta() {
        return meta;
    }

    public void setMeta(BigDecimal meta) {
        this.meta = meta;
    }

    public BigDecimal getEstadoVisitaTecnica() {
        return estadoVisitaTecnica;
    }

    public void setEstadoVisitaTecnica(BigDecimal estadoVisitaTecnica) {
        this.estadoVisitaTecnica = estadoVisitaTecnica;
    }

    public String getPisoTorre() {
        return pisoTorre;
    }

    public void setPisoTorre(String pisoTorre) {
        this.pisoTorre = pisoTorre;
    }

    public BigDecimal getTiempoRecuperacion() {
        return tiempoRecuperacion;
    }

    public void setTiempoRecuperacion(BigDecimal tiempoRecuperacion) {
        this.tiempoRecuperacion = tiempoRecuperacion;
    }

    public List<CmtSubEdificioDto> getListaSubEdificio() {
        return listaSubEdificio;
    }

    public void setListaSubEdificio(List<CmtSubEdificioDto> listaSubEdificio) {
        this.listaSubEdificio = listaSubEdificio;
    }

    public long getAporteFinanciero() {
        return aporteFinanciero;
    }

    public void setAporteFinanciero(long aporteFinanciero) {
        this.aporteFinanciero = aporteFinanciero;
    }
    
}
