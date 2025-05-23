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
 * Entidad mapeo tabla CMT_ESTADOXFLUJO
 *
 * alejandro.martinez.ext@claro.com.co
 *
 * @versión 1.00.0000
 */
@Entity
@Table(name = "CMT_ESTADOXFLUJO", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmtEstadoxFlujoMgl.findAll", query = "SELECT c FROM CmtEstadoxFlujoMgl c WHERE  c.estadoRegistro = 1"),
    @NamedQuery(name = "CmtEstadoxFlujoMgl.findId", query = "SELECT c FROM CmtEstadoxFlujoMgl c WHERE  c.estadoRegistro = 1 AND c.estadoxFlujoId = :estadoxFlujoId"),
    @NamedQuery(name = "CmtEstadoxFlujoMgl.findByTipoFlujo", query = "SELECT c FROM CmtEstadoxFlujoMgl c WHERE c.tipoFlujoOtObj = :tipoFlujoOt AND c.basicaTecnologia = :tecnologia and c.estadoRegistro = 1"),
    @NamedQuery(name = "CmtEstadoxFlujoMgl.findEstadoIniTipoFlujo", query = "SELECT c FROM CmtEstadoxFlujoMgl c WHERE c.tipoFlujoOtObj = :tipoFlujoOt AND c.esEstadoInicial = :esEstadoInicial  AND c.basicaTecnologia = :tecnologia and c.estadoRegistro = 1"),
    @NamedQuery(name = "CmtEstadoxFlujoMgl.findByTipoFlujoAndEstadoInt", query = "SELECT c FROM CmtEstadoxFlujoMgl c WHERE c.tipoFlujoOtObj = :tipoFlujoOt AND c.estadoInternoObj = :estadoInterno AND c.basicaTecnologia = :tecnologia and c.estadoRegistro = 1"),
    @NamedQuery(name = "CmtEstadoxFlujoMgl.findByInGestionRol", query = "SELECT c FROM CmtEstadoxFlujoMgl c WHERE c.estadoRegistro = 1 AND c.gestionRol IN :gestionRolList and c.estadoRegistro = 1"),
    @NamedQuery(name = "CmtEstadoxFlujoMgl.findByTipoFlujoAndTecnoAndCancelado", query = "SELECT c FROM CmtEstadoxFlujoMgl c WHERE c.tipoFlujoOtObj = :tipoFlujoOt  AND c.basicaTecnologia = :tecnologia AND c.esEstadoInicial = 'C' AND c.estadoRegistro = 1"),
    @NamedQuery(name = "CmtEstadoxFlujoMgl.findByFilter", query = "SELECT c FROM CmtEstadoxFlujoMgl c WHERE UPPER (c.tipoFlujoOtObj.nombreBasica) like :tipoFlujo and UPPER (c.basicaTecnologia.nombreBasica) like :tecnologia and UPPER (c.subTipoOrdenOFSC.nombreBasica) like :subTipoOrden and c.estadoRegistro = 1")
})

public class CmtEstadoxFlujoMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "CmtEstadoxFlujoMgl.CmtEstadoXFlujoSq",
            sequenceName = "MGL_SCHEME.CMT_ESTADOXFLUJO_SQ",
            allocationSize = 1)
    @GeneratedValue(generator = "CmtEstadoxFlujoMgl.CmtEstadoXFlujoSq")
    @Column(name = "ESTADOXFLUJO_ID")
    private BigDecimal estadoxFlujoId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_ESTADOINTERNO")
    private CmtBasicaMgl estadoInternoObj;
    @JoinColumn(name = "BASICA_ID_TIPOFLUJO_OT")
    @ManyToOne(fetch = FetchType.LAZY)
    private CmtBasicaMgl tipoFlujoOtObj;
    @JoinColumn(name = "BASICA_ID_CAMBIA_CM_ESTADO")
    @ManyToOne(fetch = FetchType.LAZY)
    private CmtBasicaMgl cambiaCmEstadoObj;
    @JoinColumn(name = "BASICA_ID_TECNOLOGIA")
    @ManyToOne(fetch = FetchType.LAZY)
    private CmtBasicaMgl basicaTecnologia;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FORMULARIO")
    private CmtBasicaMgl formulario;
    @Column(name = "ES_ESTADO_INICIAL", nullable = true, length = 1)
    private String esEstadoInicial;
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
    @Column(name = "GESTION_ROL", nullable = false, length = 20)
    private String gestionRol;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_SUBTIPO_ORD_OFSC")
    private CmtBasicaMgl subTipoOrdenOFSC;
    
    @Column(name = "DIAS_A_MOSTRAR_AGENDA")
    private int diasAMostrarAgenda;
    
    @JoinColumn(name = "TIPO_OT_RR_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private CmtTipoOtRrMgl tipoOtRr;
    
    @JoinColumn(name = "ESTADO_OT_RR_INICIAL")
    @ManyToOne(fetch = FetchType.LAZY)
    private CmtBasicaMgl estadoOtRRInicial;
    @JoinColumn(name = "ESTADO_OT_RR_FINAL")
    @ManyToOne(fetch = FetchType.LAZY)
    private CmtBasicaMgl estadoOtRRFinal;
    
    @JoinColumn(name = "ID_TIPO_TRABAJO_RR")
    @ManyToOne(fetch = FetchType.LAZY)
    private CmtBasicaMgl tipoTrabajoRR;
    
    @Column(name = "TIEMPO_AGENDA_OFSC")
    private int tiempoAgendaOfsc;

    @JoinColumn(name = "ESTADO_INICIAL_TEC_CCMM")
    @ManyToOne(fetch = FetchType.LAZY)
    private CmtBasicaMgl estadoInicialTecCCMM;

    
    public CmtEstadoxFlujoMgl() {
    }

    /**
     * @return the estadoxFlujoI
     */
    public BigDecimal getEstadoxFlujoId() {
        return estadoxFlujoId;
    }

    /**
     * @param estadoxFlujoId the estadoxFlujoId to set
     */
    public void setEstadoxFlujoId(BigDecimal estadoxFlujoId) {
        this.estadoxFlujoId = estadoxFlujoId;
    }

    /**
     * @return the estadoInternoObj
     */
    public CmtBasicaMgl getEstadoInternoObj() {
        return estadoInternoObj;
    }

    /**
     * @param estadoInternoObj the estadoInternoObj to set
     */
    public void setEstadoInternoObj(CmtBasicaMgl estadoInternoObj) {
        this.estadoInternoObj = estadoInternoObj;
    }

    /**
     * @return the tipoFlujoOtObj
     */
    public CmtBasicaMgl getTipoFlujoOtObj() {
        return tipoFlujoOtObj;
    }

    /**
     * @param tipoFlujoOtObj the tipoFlujoOtObj to set
     */
    public void setTipoFlujoOtObj(CmtBasicaMgl tipoFlujoOtObj) {
        this.tipoFlujoOtObj = tipoFlujoOtObj;
    }

    /**
     * @return the cambiaCmEstadoObj
     */
    public CmtBasicaMgl getCambiaCmEstadoObj() {
        return cambiaCmEstadoObj;
    }

    /**
     * @param cambiaCmEstadoObj the cambiaCmEstadoObj to set
     */
    public void setCambiaCmEstadoObj(CmtBasicaMgl cambiaCmEstadoObj) {
        this.cambiaCmEstadoObj = cambiaCmEstadoObj;
    }

    /**
     * @return the tipoOtRr
     */
    public CmtTipoOtRrMgl getTipoOtRr() {
        return tipoOtRr;
    }

    /**
     * @param tipoOtRr the tipoOtRr to set
     */
    public void setTipoOtRr(CmtTipoOtRrMgl tipoOtRr) {
        this.tipoOtRr = tipoOtRr;
    }

    /**
     * @return the formulario
     */
    public CmtBasicaMgl getFormulario() {
        return formulario;
    }

    /**
     * @param formulario the formulario to set
     */
    public void setFormulario(CmtBasicaMgl formulario) {
        this.formulario = formulario;
    }

    /**
     * @return the esEstadoInicial
     */
    public String getEsEstadoInicial() {
        return esEstadoInicial;
    }

    /**
     * @param esEstadoInicial the estadoInicial to set
     */
    public void setEsEstadoInicial(String esEstadoInicial) {
        this.esEstadoInicial = esEstadoInicial;
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

    /**
     * @return the gestionRol
     */
    public String getGestionRol() {
        return gestionRol;
    }

    /**
     * @param gestionRol the gestionRol to set
     */
    public void setGestionRol(String gestionRol) {
        this.gestionRol = gestionRol;
    }

    public CmtBasicaMgl getBasicaTecnologia() {
        return basicaTecnologia;
    }

    public void setBasicaTecnologia(CmtBasicaMgl basicaTecnologia) {
        this.basicaTecnologia = basicaTecnologia;
    }

    public CmtBasicaMgl getSubTipoOrdenOFSC() {
        return subTipoOrdenOFSC;
    }

    public void setSubTipoOrdenOFSC(CmtBasicaMgl subTipoOrdenOFSC) {
        this.subTipoOrdenOFSC = subTipoOrdenOFSC;
    }

    public int getDiasAMostrarAgenda() {
        return diasAMostrarAgenda;
    }

    public void setDiasAMostrarAgenda(int diasAMostrarAgenda) {
        this.diasAMostrarAgenda = diasAMostrarAgenda;
    }

    public int getTiempoAgendaOfsc() {
        return tiempoAgendaOfsc;
    }

    public void setTiempoAgendaOfsc(int tiempoAgendaOfsc) {
        this.tiempoAgendaOfsc = tiempoAgendaOfsc;
    }

    public CmtBasicaMgl getEstadoOtRRInicial() {
        return estadoOtRRInicial;
    }

    public void setEstadoOtRRInicial(CmtBasicaMgl estadoOtRRInicial) {
        this.estadoOtRRInicial = estadoOtRRInicial;
    }

    public CmtBasicaMgl getEstadoOtRRFinal() {
        return estadoOtRRFinal;
    }

    public void setEstadoOtRRFinal(CmtBasicaMgl estadoOtRRFinal) {
        this.estadoOtRRFinal = estadoOtRRFinal;
    }

    public CmtBasicaMgl getTipoTrabajoRR() {
        return tipoTrabajoRR;
    }

    public void setTipoTrabajoRR(CmtBasicaMgl tipoTrabajoRR) {
        this.tipoTrabajoRR = tipoTrabajoRR;
    }

    public CmtBasicaMgl getEstadoInicialTecCCMM() {
        return estadoInicialTecCCMM;
    }

    public void setEstadoInicialTecCCMM(CmtBasicaMgl estadoInicialTecCCMM) {
        this.estadoInicialTecCCMM = estadoInicialTecCCMM;
    }

}
