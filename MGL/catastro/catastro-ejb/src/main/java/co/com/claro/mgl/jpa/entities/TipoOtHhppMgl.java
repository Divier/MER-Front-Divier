/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities;

import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
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
import javax.persistence.PrePersist;
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
@Table(name = "MGL_TIPO_OT_DIRECCION", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoOtHhppMgl.findAll", query = "SELECT m FROM TipoOtHhppMgl m where m.estadoRegistro = 1")
    ,
    @NamedQuery(name = "TipoOtHhppMgl.findByIdentificadorInterno", query = "SELECT c FROM TipoOtHhppMgl c WHERE c.identificadorInterno = :identificadorInterno AND c.estadoRegistro = 1")})
public class TipoOtHhppMgl implements Serializable {

    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "TipoOtHhppMgl.TipoOtHhppMglSq", schema = "MGL_SCHEME",
            sequenceName = "MGL_TIPO_OT_DIRECCION_SQ", allocationSize = 1)
    @GeneratedValue(generator = "TipoOtHhppMgl.TipoOtHhppMglSq")
    @Column(name = "TIPO_OT_ID", nullable = false)
    private BigDecimal tipoOtId;
    @NotNull
    @Column(name = "NOMBRE_TIPO_OT")
    private String nombreTipoOt;
    @NotNull
    @Column(name = "AGENDABLE")
    private BigDecimal agendable;

    @NotNull
    @Column(name = "ANS")
    private int ans;
    @NotNull
    @Column(name = "ANS_AVISO")
    private int ansAviso;
    @Column(name = "FECHA_CREACION")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;
    @Column(name = "PERFIL_CREACION")
    private int perfilCreacion;
    @Column(name = "PERFIL_EDICION")
    private int perfilEdicion;
    @Column(name = "FECHA_EDICION")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaEdicion;
    @Column(name = "USUARIO_EDICION")
    private String usuarioEdicion;
    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro;
    @Column(name = "ROL_GESTION")
    private String rolGestion;
    @Column(name = "IDENTIFICADOR_INTERNO_APP")
    private String identificadorInterno;
    @Column(name = "NOMBRE_TECNOLOGIA_OFSC")
    private String nombreTecnologiaOfsc;
    @Column(name = "DIAS_A_MOSTRAR_AGENDA")
    private int diasAMostrarAgenda;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_SUBTIPO_TRABAJO")
    private CmtBasicaMgl subTipoOrdenOFSC;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_TIPO_TRABAJO")
    private CmtBasicaMgl tipoTrabajoOFSC;
    @Column(name = "TIPO_OT_AMPLIACION_TAB")
    private BigDecimal tipoOtAmpliacionTab;
    @Column(name = "AMPLIACION_TAB")
    private BigDecimal ampliacionTab;
    @Column(name = "MANEJA_TECNOLOGIAS")
    private BigDecimal manejaTecnologias;
    @Column(name = "MANEJA_VISITA_DOMICILIARIA")
    private BigDecimal manejaVisitaDomiciliaria;
    @Column(name = "REQUIERE_ONIX")
    private String requiereOnyx;
    
    @Column(name = "IS_MULTIAGENDA")
    private String isMultiagenda;
    
    @JoinColumn(name = "ID_TIPO_TRABAJO_RR")
    @ManyToOne(fetch = FetchType.LAZY)
    private CmtBasicaMgl tipoTrabajoRR;
    
    @JoinColumn(name = "ESTADO_OT_RR_INICIAL")
    @ManyToOne(fetch = FetchType.LAZY)
    private CmtBasicaMgl estadoOtRRInicial;
    
    @JoinColumn(name = "ESTADO_OT_RR_FINAL")
    @ManyToOne(fetch = FetchType.LAZY)
    private CmtBasicaMgl estadoOtRRFinal;

    @Column(name = "IS_TIPO_VISITA_TECNICA")
    private String esTipoVisitaTecnica;
    
    @Column(name = "TIEMPO_AGENDA_OFSC")
    private int tiempoAgendaOfsc;
    

    public int getTiempoAgendaOfsc() {
        return tiempoAgendaOfsc;
    }

    public void setTiempoAgendaOfsc(int tiempoAgendaOfsc) {
        this.tiempoAgendaOfsc = tiempoAgendaOfsc;
    }
    
    public BigDecimal getTipoOtId() {
        return tipoOtId;
    }

    public void setTipoOtId(BigDecimal tipoOtId) {
        this.tipoOtId = tipoOtId;
    }

    public String getNombreTipoOt() {
        return nombreTipoOt;
    }

    public void setNombreTipoOt(String nombreTipoOt) {
        this.nombreTipoOt = nombreTipoOt;
    }

    public BigDecimal getAgendable() {
        return agendable;
    }

    public void setAgendable(BigDecimal agendable) {
        this.agendable = agendable;
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

    public int getPerfilEdicion() {
        return perfilEdicion;
    }

    public void setPerfilEdicion(int perfilEdicion) {
        this.perfilEdicion = perfilEdicion;
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

    public int getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(int estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    public String getRolGestion() {
        return rolGestion;
    }

    public void setRolGestion(String rolGestion) {
        this.rolGestion = rolGestion;
    }

    public String getIdentificadorInterno() {
        return identificadorInterno;
    }

    public void setIdentificadorInterno(String abreviatura) {
        this.identificadorInterno = abreviatura;
    }

    public String getNombreTecnologiaOfsc() {
        return nombreTecnologiaOfsc;
    }

    public void setNombreTecnologiaOfsc(String nombreTecnologiaOfsc) {
        this.nombreTecnologiaOfsc = nombreTecnologiaOfsc;
    }

    public int getDiasAMostrarAgenda() {
        return diasAMostrarAgenda;
    }

    public void setDiasAMostrarAgenda(int diasAMostrarAgenda) {
        this.diasAMostrarAgenda = diasAMostrarAgenda;
    }

    public CmtBasicaMgl getTipoTrabajoOFSC() {
        return tipoTrabajoOFSC;
    }

    public void setTipoTrabajoOFSC(CmtBasicaMgl tipoTrabajoOFSC) {
        this.tipoTrabajoOFSC = tipoTrabajoOFSC;
    }

    public CmtBasicaMgl getSubTipoOrdenOFSC() {
        return subTipoOrdenOFSC;
    }

    public void setSubTipoOrdenOFSC(CmtBasicaMgl subTipoOrdenOFSC) {
        this.subTipoOrdenOFSC = subTipoOrdenOFSC;
    }

    public BigDecimal getTipoOtAmpliacionTab() {
        return tipoOtAmpliacionTab;
    }

    public void setTipoOtAmpliacionTab(BigDecimal tipoOtAmpliacionTab) {
        this.tipoOtAmpliacionTab = tipoOtAmpliacionTab;
    }

    public BigDecimal getAmpliacionTab() {
        return ampliacionTab;
    }

    public void setAmpliacionTab(BigDecimal ampliacionTab) {
        this.ampliacionTab = ampliacionTab;
    }

    public BigDecimal getManejaTecnologias() {
        return manejaTecnologias;
    }

    public void setManejaTecnologias(BigDecimal manejaTecnologias) {
        this.manejaTecnologias = manejaTecnologias;
    }

    public BigDecimal getManejaVisitaDomiciliaria() {
        return manejaVisitaDomiciliaria;
    }

    public void setManejaVisitaDomiciliaria(BigDecimal manejaVisitaDomiciliaria) {
        this.manejaVisitaDomiciliaria = manejaVisitaDomiciliaria;
    }

    public String getRequiereOnyx() {
        return requiereOnyx;
    }

    public void setRequiereOnyx(String requiereOnyx) {
        this.requiereOnyx = requiereOnyx;
    }

    public String getIsMultiagenda() {
        return isMultiagenda;
    }

    public void setIsMultiagenda(String isMultiagenda) {
        this.isMultiagenda = isMultiagenda;
    }

    public CmtBasicaMgl getTipoTrabajoRR() {
        return tipoTrabajoRR;
    }

    public void setTipoTrabajoRR(CmtBasicaMgl tipoTrabajoRR) {
        this.tipoTrabajoRR = tipoTrabajoRR;
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
    
    public String getEsTipoVisitaTecnica() {
        return esTipoVisitaTecnica;
    }
    
    public void setEsTipoVisitaTecnica(String esTipoVisitaTecnica) {
        this.esTipoVisitaTecnica = esTipoVisitaTecnica;
    }
    
    @PrePersist
    public void prePersist() {
        this.estadoRegistro = 1;
        this.identificadorInterno = "@NA";
    }
}
