/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author cardenaslb
 */
@Entity
@Table(name = "MGL_AGENDA_DIRECCION$AUD", schema = "MGL_SCHEME")
@XmlRootElement
public class MglAgendaDireccionAuditoria implements Serializable{
  
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "AUD_AGENDA_ID", nullable = false)
    private BigDecimal agendaIdAud;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AGENDA_ID", nullable = false)
    private MglAgendaDireccion mglAgenda;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_AGENDA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAgenda;

    @Basic(optional = false)
    @Size(min = 1, max = 20)
    @Column(name = "TIME_SLOT")
    private String timeSlot;

    @Basic(optional = false)
    @Size(min = 1, max = 20)
    @Column(name = "TIPO")
    private String tipo;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "OFPS_OT_ID")
    private String ofpsOtId;

    @Basic(optional = false)
    @NotNull
    @Column(name = "OFPS_ID")
    private long ofpsId;

    @Column(name = "FECHA_INICIO_VT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInivioVt;

    @Size(max = 100)
    @Column(name = "NOMBRE_TECNICO")
    private String nombreTecnico;

    @Basic(optional = false)
    @Column(name = "FECHA_FIN_VT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFinVt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_ESTADO_AGENDA")
    private CmtBasicaMgl basicaIdEstadoAgenda;

    @Column(name = "DOC_ADJUNTOS")
    private String docAdjuntos;

    @Column(name = "RAZON_CANCELACION")
    private String razonCancela;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OT_DIRECCION_ID", referencedColumnName = "OT_DIRECCION_ID")
    private OtHhppMgl ordenTrabajo;

    @Column(name = "SEC_MULTI_OFSC")
    private int secMltiOfsc;

    @Column(name = "PERSONA_RECIBE_VT")
    private String personaRecVt;

    @Column(name = "DOC_PER_RECIBE_VT")
    private String docPerRecVt;

    @Column(name = "TEL_PER_RECIBE_VT")
    private String telPerRecVt;

    @Column(name = "PREREQUISITOS_VT")
    private String prerequisitosVT;

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

    @NotNull
    @Column(name = "FECHA_EDICION", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaEdicion;

    @NotNull
    @Column(name = "USUARIO_EDICION", nullable = true, length = 200)
    private String usuarioEdicion;

    @NotNull
    @Column(name = "PERFIL_EDICION")
    private int perfilEdicion;

    @NotNull
    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro;

    @Basic
    @Column(name = "TIPO_RESULTADO")
    private String tipoResultado;

    @Basic
    @Column(name = "RESULTADO")
    private String resultado;

    @Basic
    @Column(name = "EXTERNAL_ID")
    private String idExterno;

    @Column(name = "RAZON_REAGENDA")
    private String razonReagenda;

    @Column(name = "ID_TECNICO")
    private String identificacionTecnico;

    @Column(name = "ID_ALIADO")
    private String identificacionAliado;

    @Column(name = "NOMBRE_ALIADO")
    private String nombreAliado;

    @Column(name = "ID_MENSAJE_ASIGNAR_RECURSO")
    private int idMensajeAsignarRecurso;

    @Column(name = "ID_MENSAJE_INICIAR_VT")
    private int idMensajeIniciarVt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_RAZONES")
    private CmtBasicaMgl basicaIdrazones;

    @Column(name = "ID_MENSAJE_NODONE")
    private int idMensajeNodone;

    @Column(name = "ID_MENSAJE_HARDCLOSE")
    private int idMensajeHardclose;

    @Column(name = "ULTIMA_AGENDA")
    private String ultimaAgenda;

    @Column(name = "FECHA_SUSPENDE_VT", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaSuspendeVt;

    @Column(name = "ID_MENSAJE_SUSPENDE")
    private int idMensajeSuspende;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_ESTADO_VISITA")
    private CmtBasicaMgl estadoVisita;
    
    @Column(name = "EMAIL_PER_RECIBE_VT")
    private String emailPerRecVT;
    
    @Column(name = "OBSERVACIONES_TECNICO")
    private String observacionesTecnico;
    
    @Column(name = "ID_OT_RR", nullable = true, length = 5)
    private String idOtenrr;
    
    @Column(name = "SUB_TIPO_WORKFOCE")
    private String subTipoWorkFoce;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_ESTADO_OT")
    private CmtBasicaMgl basicaIdEstadoOt;
    
    @Column(name = "FECHA_ASIG_TECNICO")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaAsigTecnico;
    
    @Column(name = "FECHA_REAGENDA")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaReagenda;

    public BigDecimal getAgendaIdAud() {
        return agendaIdAud;
    }

    public void setAgendaIdAud(BigDecimal agendaIdAud) {
        this.agendaIdAud = agendaIdAud;
    }

    public MglAgendaDireccion getMglAgenda() {
        return mglAgenda;
    }

    public void setMglAgenda(MglAgendaDireccion mglAgenda) {
        this.mglAgenda = mglAgenda;
    }


    public Date getFechaAgenda() {
        return fechaAgenda;
    }

    public void setFechaAgenda(Date fechaAgenda) {
        this.fechaAgenda = fechaAgenda;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getOfpsOtId() {
        return ofpsOtId;
    }

    public void setOfpsOtId(String ofpsOtId) {
        this.ofpsOtId = ofpsOtId;
    }

    public long getOfpsId() {
        return ofpsId;
    }

    public void setOfpsId(long ofpsId) {
        this.ofpsId = ofpsId;
    }

    public Date getFechaInivioVt() {
        return fechaInivioVt;
    }

    public void setFechaInivioVt(Date fechaInivioVt) {
        this.fechaInivioVt = fechaInivioVt;
    }

    public String getNombreTecnico() {
        return nombreTecnico;
    }

    public void setNombreTecnico(String nombreTecnico) {
        this.nombreTecnico = nombreTecnico;
    }

    public Date getFechaFinVt() {
        return fechaFinVt;
    }

    public void setFechaFinVt(Date fechaFinVt) {
        this.fechaFinVt = fechaFinVt;
    }

    public CmtBasicaMgl getBasicaIdEstadoAgenda() {
        return basicaIdEstadoAgenda;
    }

    public void setBasicaIdEstadoAgenda(CmtBasicaMgl basicaIdEstadoAgenda) {
        this.basicaIdEstadoAgenda = basicaIdEstadoAgenda;
    }

    public String getDocAdjuntos() {
        return docAdjuntos;
    }

    public void setDocAdjuntos(String docAdjuntos) {
        this.docAdjuntos = docAdjuntos;
    }

    public String getRazonCancela() {
        return razonCancela;
    }

    public void setRazonCancela(String razonCancela) {
        this.razonCancela = razonCancela;
    }

    public OtHhppMgl getOrdenTrabajo() {
        return ordenTrabajo;
    }

    public void setOrdenTrabajo(OtHhppMgl ordenTrabajo) {
        this.ordenTrabajo = ordenTrabajo;
    }

    public int getSecMltiOfsc() {
        return secMltiOfsc;
    }

    public void setSecMltiOfsc(int secMltiOfsc) {
        this.secMltiOfsc = secMltiOfsc;
    }

    public String getPersonaRecVt() {
        return personaRecVt;
    }

    public void setPersonaRecVt(String personaRecVt) {
        this.personaRecVt = personaRecVt;
    }

    public String getDocPerRecVt() {
        return docPerRecVt;
    }

    public void setDocPerRecVt(String docPerRecVt) {
        this.docPerRecVt = docPerRecVt;
    }

    public String getTelPerRecVt() {
        return telPerRecVt;
    }

    public void setTelPerRecVt(String telPerRecVt) {
        this.telPerRecVt = telPerRecVt;
    }

    public String getPrerequisitosVT() {
        return prerequisitosVT;
    }

    public void setPrerequisitosVT(String prerequisitosVT) {
        this.prerequisitosVT = prerequisitosVT;
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

    public int getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(int estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    public String getTipoResultado() {
        return tipoResultado;
    }

    public void setTipoResultado(String tipoResultado) {
        this.tipoResultado = tipoResultado;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getIdExterno() {
        return idExterno;
    }

    public void setIdExterno(String idExterno) {
        this.idExterno = idExterno;
    }

    public String getRazonReagenda() {
        return razonReagenda;
    }

    public void setRazonReagenda(String razonReagenda) {
        this.razonReagenda = razonReagenda;
    }

    public String getIdentificacionTecnico() {
        return identificacionTecnico;
    }

    public void setIdentificacionTecnico(String identificacionTecnico) {
        this.identificacionTecnico = identificacionTecnico;
    }

    public String getIdentificacionAliado() {
        return identificacionAliado;
    }

    public void setIdentificacionAliado(String identificacionAliado) {
        this.identificacionAliado = identificacionAliado;
    }

    public String getNombreAliado() {
        return nombreAliado;
    }

    public void setNombreAliado(String nombreAliado) {
        this.nombreAliado = nombreAliado;
    }

    public int getIdMensajeAsignarRecurso() {
        return idMensajeAsignarRecurso;
    }

    public void setIdMensajeAsignarRecurso(int idMensajeAsignarRecurso) {
        this.idMensajeAsignarRecurso = idMensajeAsignarRecurso;
    }

    public int getIdMensajeIniciarVt() {
        return idMensajeIniciarVt;
    }

    public void setIdMensajeIniciarVt(int idMensajeIniciarVt) {
        this.idMensajeIniciarVt = idMensajeIniciarVt;
    }

    public CmtBasicaMgl getBasicaIdrazones() {
        return basicaIdrazones;
    }

    public void setBasicaIdrazones(CmtBasicaMgl basicaIdrazones) {
        this.basicaIdrazones = basicaIdrazones;
    }

    public int getIdMensajeNodone() {
        return idMensajeNodone;
    }

    public void setIdMensajeNodone(int idMensajeNodone) {
        this.idMensajeNodone = idMensajeNodone;
    }

    public int getIdMensajeHardclose() {
        return idMensajeHardclose;
    }

    public void setIdMensajeHardclose(int idMensajeHardclose) {
        this.idMensajeHardclose = idMensajeHardclose;
    }

    public String getUltimaAgenda() {
        return ultimaAgenda;
    }

    public void setUltimaAgenda(String ultimaAgenda) {
        this.ultimaAgenda = ultimaAgenda;
    }

    public Date getFechaSuspendeVt() {
        return fechaSuspendeVt;
    }

    public void setFechaSuspendeVt(Date fechaSuspendeVt) {
        this.fechaSuspendeVt = fechaSuspendeVt;
    }

    public int getIdMensajeSuspende() {
        return idMensajeSuspende;
    }

    public void setIdMensajeSuspende(int idMensajeSuspende) {
        this.idMensajeSuspende = idMensajeSuspende;
    }

    public CmtBasicaMgl getEstadoVisita() {
        return estadoVisita;
    }

    public void setEstadoVisita(CmtBasicaMgl estadoVisita) {
        this.estadoVisita = estadoVisita;
    }

    public String getEmailPerRecVT() {
        return emailPerRecVT;
    }

    public void setEmailPerRecVT(String emailPerRecVT) {
        this.emailPerRecVT = emailPerRecVT;
    }

    public String getObservacionesTecnico() {
        return observacionesTecnico;
    }

    public void setObservacionesTecnico(String observacionesTecnico) {
        this.observacionesTecnico = observacionesTecnico;
    }

    public String getIdOtenrr() {
        return idOtenrr;
    }

    public void setIdOtenrr(String idOtenrr) {
        this.idOtenrr = idOtenrr;
    }

    public String getSubTipoWorkFoce() {
        return subTipoWorkFoce;
    }

    public void setSubTipoWorkFoce(String subTipoWorkFoce) {
        this.subTipoWorkFoce = subTipoWorkFoce;
    }

    public CmtBasicaMgl getBasicaIdEstadoOt() {
        return basicaIdEstadoOt;
    }

    public void setBasicaIdEstadoOt(CmtBasicaMgl basicaIdEstadoOt) {
        this.basicaIdEstadoOt = basicaIdEstadoOt;
    }

    public Date getFechaAsigTecnico() {
        return fechaAsigTecnico;
    }

    public void setFechaAsigTecnico(Date fechaAsigTecnico) {
        this.fechaAsigTecnico = fechaAsigTecnico;
    }

    public Date getFechaReagenda() {
        return fechaReagenda;
    }

    public void setFechaReagenda(Date fechaReagenda) {
        this.fechaReagenda = fechaReagenda;
    }

  
}
