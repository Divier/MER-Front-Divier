/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities;

import co.com.claro.mgl.dtos.CmtOnyxResponseDto;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import co.com.claro.mgw.agenda.shareddata.MgwTypeAgendarResponseElement;
import java.io.Serializable;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Orlando Velasquez
 */
@Entity
@Table(name = "MGL_AGENDA_DIRECCION", schema = "MGL_SCHEME")
@NamedQueries({
    @NamedQuery(name = "MglAgendaDireccion.findAll", query = "SELECT m FROM MglAgendaDireccion m")
    , 
    @NamedQuery(name = "MglAgendaDireccion.findByMaximoSec",
            query = "SELECT MAX(m.secMltiOfsc) FROM MglAgendaDireccion m WHERE m.ordenTrabajo.otHhppId = :otHhppId")})
public class MglAgendaDireccion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "AGENDA_ID")
    @SequenceGenerator(name = "MglAgendaDireccion.MglAgendamientoSeq",
            sequenceName = "MGL_SCHEME.MGL_AGENDA_DIRECCION_SEQ", allocationSize = 1)
    @GeneratedValue(generator = "MglAgendaDireccion.MglAgendamientoSeq")
    private Long agendaId;
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
    
    @OneToMany(mappedBy = "mglAgenda", fetch = FetchType.LAZY)
    private List<MglAgendaDireccionAuditoria> listAgendaDireccionAuditoria;
    
    @Column(name = "CONVENIENCIA")
    private String conveniencia;
    
     @Column(name = "CANT_AGENDAS")
    private String cantAgendas;
    
    @Column(name = "FECHA_ASIG_TECNICO")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaAsigTecnico;
    
    @Column(name = "FECHA_REAGENDA")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaReagenda;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NODO_ID", referencedColumnName = "NODO_ID")
    private NodoMgl nodoMgl;
    
    @Column(name = "TECNICO_ANTICIPADO")
    private String tecnicoAnticipado;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUENTAMATRIZ_ID", referencedColumnName = "CUENTAMATRIZ_ID")
    private CmtCuentaMatrizMgl cuentaMatrizMgl;
    
    @Column(name = "HORA_INICIO")
    private String horaInicio;
    
    @Column(name = "AGENDA_INMEDIATA")
    private String agendaInmediata;
    
    @Column(name = "MOTIVOS_REAGENDA")
    private String motivosReagenda;
    
    @Transient
    private String hora;
    
    @Transient
    CmtOnyxResponseDto cmtOnyxResponseDto;
	
    @Transient
    private MgwTypeAgendarResponseElement agendarResponseElement;
    
    @Transient
    private boolean estaEnCapacity;
    
    @Transient
    private String comentarioReasigna;
    
    @Transient
    private String timeSlotAntes;
    
    @Transient
    private String comentarioCancela;
    
    @Transient
    private String nameArchivo;

    @Transient
    private String typeArchivo;

    @Transient
    private String contentArchivo;
    
    @Transient
    private String numeroOrdenInmediata;
    

    public MglAgendaDireccion() {
    }

    public MglAgendaDireccion(Long agendaId) {
        this.agendaId = agendaId;
    }

    /**
     * Constructor empleado para la construcci&oacute;n de un objeto con los
     * datos resultantes del agendamiento
     *
     * @param fecha {@link Date} Fecha de agendamiento
     * @param tipo {@link String} Tipo de respuesta
     * @param idExterno {@link Integer} id que se envia a Work Force
     * @param WorkForceId {@link Integer} id del registro en Work Force
     * @param resultado {@link String} Resutado de la transacci&oacute;n
     * @param tipoResultado {@link String} Tipo de resultado
     * @param ordenTrabajo {@link CmtOrdenTrabajoMgl} orden de trabajo a la que
     * fue asociada la agenda
     * @param ofpsOtId {@link String} Identificador de la ot en WorkForce
     * @param hora {@link String} Hora que se realizar&aaucte; la agenda
     * @param estadoAgeBasicaMgl {@link CmtBasicaMgl} Estado de la agenda
     * @param secuencia {@link int} secuencia multiagenda
     * @param franja {@link String} franja agendada
     * @param personaRecVt {@link String} persona que recibe la Vt
     * @param telPersonaRecVt {@link String} telefono de la persona que recibe
     * la Vt
     * @param prerequisitos {@link String} prerequisitos de la Vt
     * @param emailPerRecVT
     * @param ultimaAgenda
     * @param subTipoWorkFoce
     * @param estadoOt
     * @param responseElement
     * @param nodo
     * @param tecnicoAnti
     * @param ccmmAgru
     * @param horaInicio
     * @param agendaInm
     */
    public MglAgendaDireccion(Date fecha, String tipo, String idExterno,
            Integer WorkForceId, String resultado, String tipoResultado,
            OtHhppMgl ordenTrabajo, String ofpsOtId, String hora,
            CmtBasicaMgl estadoAgeBasicaMgl, int secuencia, String franja,
            String personaRecVt, String telPersonaRecVt, 
            String prerequisitos, String emailPerRecVT,
            String ultimaAgenda, String subTipoWorkFoce, 
            CmtBasicaMgl estadoOt,String identificacionTecnico, 
            String nombreTecnico,String identificacionAliado,
            String nombreAliado,Date fechaAsigTecnico, 
            MgwTypeAgendarResponseElement responseElement, 
            NodoMgl nodo, String tecnicoAnti, 
            CmtCuentaMatrizMgl ccmmAgru, 
            String horaInicio,  String agendaInm) {

        this.fechaAgenda = fecha;
        this.tipo = tipo;
        this.idExterno = idExterno;
        this.ofpsId = WorkForceId;
        this.resultado = resultado;
        this.tipoResultado = tipoResultado;
        this.ordenTrabajo = ordenTrabajo;
        this.ofpsOtId = ofpsOtId;
        this.hora = hora;
        this.basicaIdEstadoAgenda = estadoAgeBasicaMgl;
        this.secMltiOfsc = secuencia;
        this.timeSlot = franja;
        this.personaRecVt = personaRecVt;
        this.telPerRecVt = telPersonaRecVt;
        this.prerequisitosVT = prerequisitos;
        this.emailPerRecVT = emailPerRecVT;
        this.ultimaAgenda = ultimaAgenda;
        this.subTipoWorkFoce = subTipoWorkFoce;
        this.basicaIdEstadoOt = estadoOt;
        this.identificacionTecnico = identificacionTecnico;
        this.nombreTecnico = nombreTecnico;
        this.identificacionAliado = identificacionAliado;
        this.nombreAliado = nombreAliado;
        this.fechaAsigTecnico = fechaAsigTecnico;
        this.agendarResponseElement = responseElement;
        this.nodoMgl = nodo;
        this.tecnicoAnticipado= tecnicoAnti;
        this.cuentaMatrizMgl = ccmmAgru;
        this.horaInicio = horaInicio;
        this.agendaInmediata = agendaInm;
    }

    public MglAgendaDireccion(Long agendaId, Date fechaAgenda, String timeSlot, String tipo, String ofpsOtId, long ofpsId, Date fechaFinVt) {
        this.agendaId = agendaId;
        this.fechaAgenda = fechaAgenda;
        this.timeSlot = timeSlot;
        this.tipo = tipo;
        this.ofpsOtId = ofpsOtId;
        this.ofpsId = ofpsId;
        this.fechaFinVt = fechaFinVt;
    }

    public Long getAgendaId() {
        return agendaId;
    }

    public void setAgendaId(Long agendaId) {
        this.agendaId = agendaId;
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

    public int getSecMltiOfsc() {
        return secMltiOfsc;
    }

    public void setSecMltiOfsc(int secMltiOfsc) {
        this.secMltiOfsc = secMltiOfsc;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (agendaId != null ? agendaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MglAgendaDireccion)) {
            return false;
        }
        MglAgendaDireccion other = (MglAgendaDireccion) object;
        if ((this.agendaId == null && other.agendaId != null) || (this.agendaId != null && !this.agendaId.equals(other.agendaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.claro.mgl.jpa.entities.MglAgendaDireccion[ agendaId=" + agendaId + " ]";
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

    public OtHhppMgl getOrdenTrabajo() {
        return ordenTrabajo;
    }

    public void setOrdenTrabajo(OtHhppMgl ordenTrabajo) {
        this.ordenTrabajo = ordenTrabajo;
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

    public Date getFechaInivioVt() {
        return fechaInivioVt;
    }

    public void setFechaInivioVt(Date fechaInivioVt) {
        this.fechaInivioVt = fechaInivioVt;
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

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
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

    public String getRazonCancela() {
        return razonCancela;
    }

    public void setRazonCancela(String razonCancela) {
        this.razonCancela = razonCancela;
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

    public CmtOnyxResponseDto getCmtOnyxResponseDto() {
        return cmtOnyxResponseDto;
    }

    public void setCmtOnyxResponseDto(CmtOnyxResponseDto cmtOnyxResponseDto) {
        this.cmtOnyxResponseDto = cmtOnyxResponseDto;
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

    public List<MglAgendaDireccionAuditoria> getListAgendaDireccionAuditoria() {
        return listAgendaDireccionAuditoria;
    }

    public void setListAgendaDireccionAuditoria(List<MglAgendaDireccionAuditoria> listAgendaDireccionAuditoria) {
        this.listAgendaDireccionAuditoria = listAgendaDireccionAuditoria;
    }
    
	public MgwTypeAgendarResponseElement getAgendarResponseElement() {
        return agendarResponseElement;
    }

    public void setAgendarResponseElement(MgwTypeAgendarResponseElement agendarResponseElement) {
        this.agendarResponseElement = agendarResponseElement;
    }

    public boolean isEstaEnCapacity() {
        return estaEnCapacity;
    }

    public void setEstaEnCapacity(boolean estaEnCapacity) {
        this.estaEnCapacity = estaEnCapacity;
    }

    public NodoMgl getNodoMgl() {
        return nodoMgl;
    }

    public void setNodoMgl(NodoMgl nodoMgl) {
        this.nodoMgl = nodoMgl;
    }

    public String getComentarioReasigna() {
        return comentarioReasigna;
    }

    public void setComentarioReasigna(String comentarioReasigna) {
        this.comentarioReasigna = comentarioReasigna;
    }

    public String getTecnicoAnticipado() {
        return tecnicoAnticipado;
    }

    public void setTecnicoAnticipado(String tecnicoAnticipado) {
        this.tecnicoAnticipado = tecnicoAnticipado;
    }

    public CmtCuentaMatrizMgl getCuentaMatrizMgl() {
        return cuentaMatrizMgl;
    }

    public void setCuentaMatrizMgl(CmtCuentaMatrizMgl cuentaMatrizMgl) {
        this.cuentaMatrizMgl = cuentaMatrizMgl;
    }

    public String getTimeSlotAntes() {
        return timeSlotAntes;
    }

    public void setTimeSlotAntes(String timeSlotAntes) {
        this.timeSlotAntes = timeSlotAntes;
    }

    public String getComentarioCancela() {
        return comentarioCancela;
    }

    public void setComentarioCancela(String comentarioCancela) {
        this.comentarioCancela = comentarioCancela;
    }

    public String getNameArchivo() {
        return nameArchivo;
    }

    public void setNameArchivo(String nameArchivo) {
        this.nameArchivo = nameArchivo;
    }

    public String getTypeArchivo() {
        return typeArchivo;
    }

    public void setTypeArchivo(String typeArchivo) {
        this.typeArchivo = typeArchivo;
    }

    public String getContentArchivo() {
        return contentArchivo;
    }

    public void setContentArchivo(String contentArchivo) {
        this.contentArchivo = contentArchivo;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getConveniencia() {
        return conveniencia;
    }

    public void setConveniencia(String conveniencia) {
        this.conveniencia = conveniencia;
    }

    public String getCantAgendas() {
        return cantAgendas;
    }

    public void setCantAgendas(String cantAgendas) {
        this.cantAgendas = cantAgendas;
    }

    public String getAgendaInmediata() {
        return agendaInmediata;
    }

    public void setAgendaInmediata(String agendaInmediata) {
        this.agendaInmediata = agendaInmediata;
    }

    public String getNumeroOrdenInmediata() {
        return numeroOrdenInmediata;
    }

    public void setNumeroOrdenInmediata(String numeroOrdenInmediata) {
        this.numeroOrdenInmediata = numeroOrdenInmediata;
    }

    public String getMotivosReagenda() {
        return motivosReagenda;
    }

    public void setMotivosReagenda(String motivosReagenda) {
        this.motivosReagenda = motivosReagenda;
    }
    
}
