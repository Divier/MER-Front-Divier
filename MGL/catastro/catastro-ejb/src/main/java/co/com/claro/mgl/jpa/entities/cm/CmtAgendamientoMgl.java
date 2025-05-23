package co.com.claro.mgl.jpa.entities.cm;

import co.com.claro.mgl.dtos.CmtOnyxResponseDto;
import co.com.claro.mgl.jpa.entities.NodoMgl;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 * Entidad para mapear la tabla de agendamiento en MGL
 *
 * @author wgavidia
 * @version 23/11/2017
 */
@Entity
@Table(name = "CMT_AGENDA", schema = "MGL_SCHEME")
@NamedQueries({
    @NamedQuery(name = "CmtAgendamientoMgl.findByMaximoSec",
            query = "SELECT MAX(a.secMltiOfsc) FROM CmtAgendamientoMgl a WHERE a.ordenTrabajo.idOt = :idOt")
})
public class CmtAgendamientoMgl implements Serializable {

    @Id
    @Basic
    @Column(name = "AGENDA_ID")
    @SequenceGenerator(name = "CmtAgendamientoMgl.CmtAgendamientoSq",
            sequenceName = "MGL_SCHEME.CMT_AGENDA_SQ", allocationSize = 1)
    @GeneratedValue(generator = "CmtAgendamientoMgl.CmtAgendamientoSq")
    private BigDecimal id;

    @Column(name = "FECHA_AGENDA")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaAgenda;

    @Basic
    @Column(name = "TIPO")
    private String tipo;

    @Basic
    @Column(name = "EXTERNAL_ID")
    private String idExterno;

    @Basic
    @Column(name = "OFPS_ID")
    private BigDecimal WorkForceId;

    @Basic
    @Column(name = "RESULTADO")
    private String resultado;

    @Basic
    @Column(name = "TIPO_RESULTADO")
    private String tipoResultado;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OT_ID", referencedColumnName = "OT_ID")
    private CmtOrdenTrabajoMgl ordenTrabajo;

    @Basic
    @Column(name = "OFPS_OT_ID")
    private String ofpsOtId;

    @Transient
    private String hora;

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
    @Column(name = "ID_OT_RR", nullable = true, length = 5)
    private String idOtenrr;

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
    
    @Column(name = "FECHA_INICIO_VT", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaInivioVt;
        
    @Column(name = "FECHA_FIN_VT", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaFinVt;
    
    @Column(name = "FECHA_SUSPENDE_VT", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaSuspendeVt;
    
    @Column(name = "ID_TECNICO")
    private String identificacionTecnico;
    
    @Column(name = "NOMBRE_TECNICO")
    private String nombreTecnico;
    
    @Column(name = "ID_ALIADO")
    private String identificacionAliado;
    
    @Column(name = "ID_MENSAJE_ASIGNAR_RECURSO")
    private int idMensajeAsignarRecurso;
    
    @Column(name = "ID_MENSAJE_INICIAR_VT")
    private int idMensajeIniciarVt;
    
    @Column(name = "ID_MENSAJE_NODONE")
    private int idMensajeNodone;
    
    @Column(name = "ID_MENSAJE_HARDCLOSE")
    private int idMensajeHardclose;
    
    @Column(name = "ID_MENSAJE_SUSPENDE")
    private int idMensajeSuspende;
    
    @Column(name = "SEC_MULTI_OFSC")
    private int secMltiOfsc;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_RAZONES")
    private CmtBasicaMgl basicaIdrazones;
    
    @Column(name = "NOMBRE_ALIADO")
    private String nombreAliado;
    
     
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_ESTADO_AGENDA")
    private CmtBasicaMgl basicaIdEstadoAgenda;
    
    
    @Column(name = "TIME_SLOT")
    private String timeSlot;
    
    
    @Column(name = "RAZON_REAGENDA")
    private String razonReagenda;
    
    
    @Column(name = "RAZON_CANCELACION")
    private String razonCancela;
    
    
    @Column(name = "DOC_ADJUNTOS")
    private String docAdjuntos;
    
    
    @Column(name = "ULTIMA_AGENDA")
    private String ultimaAgenda;
    
    
    @Column(name = "SUB_TIPO_WORKFOCE")
    private String subTipoWorkFoce;
    
    @Column(name = "OBSERVACIONES_TECNICO")
    private String observacionesTecnico;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_ESTADO_OT")
    private CmtBasicaMgl basicaIdEstadoOt;
    
    @Transient
    CmtOnyxResponseDto cmtOnyxResponseDto;
    
    @Column(name = "FECHA_ASIG_TECNICO")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaAsigTecnico;
    @Column(name = "FECHA_REAGENDA")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaReagenda;

    @OneToMany(mappedBy = "idAgObj", fetch = FetchType.LAZY)
    private List<CmtAgendaAuditoria> listAuditoria;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NODO_ID", referencedColumnName = "NODO_ID")
    private NodoMgl nodoMgl;
    
    @Column(name = "TECNICO_ANTICIPADO")
    private String tecnicoAnticipado;
    
    @Column(name = "HORA_INICIO")
    private String horaInicio;

    @Column(name = "CONVENIENCIA")
    private String conveniencia;

    @Column(name = "CANT_AGENDAS")
    private String cantAgendas;

    @Column(name = "AGENDA_INMEDIATA")
    private String agendaInmediata;
    
    @Column(name = "MOTIVOS_REAGENDA")
    private String motivosReagenda;
    
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
    
    /**
     * Constructor de la calse
     */
    public CmtAgendamientoMgl() {
    }

    /**
     * Constructor empleado para la construcci&oacute;n de un objeto
     * con los datos resultantes del agendamiento
     * 
     * @param fecha {@link Date} Fecha de agendamiento
     * @param tipo {@link String} Tipo de respuesta
     * @param idExterno {@link Integer} id que se envia a Work Force
     * @param WorkForceId {@link Integer} id del registro en Work Force
     * @param resultado {@link String} Resutado de la transacci&oacute;n
     * @param tipoResultado {@link String} Tipo de resultado
     * @param ordenTrabajo {@link CmtOrdenTrabajoMgl} orden de trabajo a la que fue asociada la agenda 
     * @param ofpsOtId {@link String} Identificador de la ot en WorkForce
     * @param hora {@link String} Hora que se realizar&aaucte; la agenda
     * @param estadoAgeBasicaMgl {@link CmtBasicaMgl} Estado de la agenda
     * @param secuencia {@link int} secuencia multiagenda
     * @param franja {@link String} franja agendada
     * @param ultimaAgenda {@link String} Ultima agenda
     * @param subTipoOrdenW {@link String} Sub tipo de trabajo en OFSC
     * @param estadoOt
     * @param idTecnico
     * @param nombreTecnico
     * @param idAliado
     * @param nombreAliado
     * @param fechaAsignaTecnico
     * @param nodoMgl
     * @param tecnicoAnti
     * @param horaInicio
     * @param agendaInm
     */
    public CmtAgendamientoMgl(Date fecha, String tipo, String idExterno,
            Integer WorkForceId, String resultado, String tipoResultado,
            CmtOrdenTrabajoMgl ordenTrabajo, String ofpsOtId, String hora, 
            CmtBasicaMgl estadoAgeBasicaMgl, int secuencia, String franja,
            String ultimaAgenda, String subTipoOrdenW, CmtBasicaMgl estadoOt,
            String idTecnico, String nombreTecnico, String idAliado,
            String nombreAliado, Date fechaAsignaTecnico,
            NodoMgl nodoMgl, String tecnicoAnti, 
            String horaInicio, String agendaInm) {
        
        this.fechaAgenda = fecha;
        this.tipo = tipo;
        this.idExterno = idExterno;
        this.WorkForceId = BigDecimal.valueOf(WorkForceId);
        this.resultado = resultado;
        this.tipoResultado = tipoResultado;
        this.ordenTrabajo = ordenTrabajo;
        this.ofpsOtId = ofpsOtId;
        this.hora = hora;
        this.basicaIdEstadoAgenda=estadoAgeBasicaMgl;
        this.secMltiOfsc = secuencia;
        this.timeSlot = franja;
        this.ultimaAgenda = ultimaAgenda;
        this.subTipoWorkFoce = subTipoOrdenW;
        this.basicaIdEstadoOt = estadoOt;
        this.identificacionTecnico = idTecnico;
        this.nombreTecnico = nombreTecnico;
        this.identificacionAliado = idAliado;
        this.nombreAliado = nombreAliado;
        this.fechaAsigTecnico = fechaAsignaTecnico;
        this.nodoMgl = nodoMgl;
        this.tecnicoAnticipado = tecnicoAnti;
        this.horaInicio = horaInicio;
        this.agendaInmediata = agendaInm;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public Date getFechaAgenda() {
        return fechaAgenda;
    }

    public void setFechaAgenda(Date fechaAgenda) {
        this.fechaAgenda = fechaAgenda;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getIdExterno() {
        return idExterno;
    }

    public void setIdExterno(String idExterno) {
        this.idExterno = idExterno;
    }

    public BigDecimal getWorkForceId() {
        return WorkForceId;
    }

    public void setWorkForceId(BigDecimal WorkForceId) {
        this.WorkForceId = WorkForceId;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getTipoResultado() {
        return tipoResultado;
    }

    public void setTipoResultado(String tipoResultado) {
        this.tipoResultado = tipoResultado;
    }

    public CmtOrdenTrabajoMgl getOrdenTrabajo() {
        return ordenTrabajo;
    }

    public void setOrdenTrabajo(CmtOrdenTrabajoMgl ordenTrabajo) {
        this.ordenTrabajo = ordenTrabajo;
    }

    public String getOfpsOtId() {
        return ofpsOtId;
    }

    public void setOfpsOtId(String ofpsOtId) {
        this.ofpsOtId = ofpsOtId;
    }
    
    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
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

    public Date getFechaInivioVt() {
        return fechaInivioVt;
    }

    public void setFechaInivioVt(Date fechaInivioVt) {
        this.fechaInivioVt = fechaInivioVt;
    }

    public Date getFechaFinVt() {
        return fechaFinVt;
    }

    public void setFechaFinVt(Date fechaFinVt) {
        this.fechaFinVt = fechaFinVt;
    }

    public String getIdentificacionTecnico() {
        return identificacionTecnico;
    }

    public void setIdentificacionTecnico(String identificacionTecnico) {
        this.identificacionTecnico = identificacionTecnico;
    }

    public String getNombreTecnico() {
        return nombreTecnico;
    }

    public void setNombreTecnico(String nombreTecnico) {
        this.nombreTecnico = nombreTecnico;
    }

    public String getIdentificacionAliado() {
        return identificacionAliado;
    }

    public void setIdentificacionAliado(String identificacionAliado) {
        this.identificacionAliado = identificacionAliado;
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

    public CmtBasicaMgl getBasicaIdrazones() {
        return basicaIdrazones;
    }

    public void setBasicaIdrazones(CmtBasicaMgl basicaIdrazones) {
        this.basicaIdrazones = basicaIdrazones;
    }  

    public int getSecMltiOfsc() {
        return secMltiOfsc;
    }

    public void setSecMltiOfsc(int secMltiOfsc) {
        this.secMltiOfsc = secMltiOfsc;
    }

    public String getNombreAliado() {
        return nombreAliado;
    }

    public void setNombreAliado(String nombreAliado) {
        this.nombreAliado = nombreAliado;
    }

    public CmtBasicaMgl getBasicaIdEstadoAgenda() {
        return basicaIdEstadoAgenda;
    }

    public void setBasicaIdEstadoAgenda(CmtBasicaMgl basicaIdEstadoAgenda) {
        this.basicaIdEstadoAgenda = basicaIdEstadoAgenda;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public String getRazonReagenda() {
        return razonReagenda;
    }

    public void setRazonReagenda(String razonReagenda) {
        this.razonReagenda = razonReagenda;
    }

    public String getRazonCancela() {
        return razonCancela;
    }

    public void setRazonCancela(String razonCancela) {
        this.razonCancela = razonCancela;
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

    public String getDocAdjuntos() {
        return docAdjuntos;
    }

    public void setDocAdjuntos(String docAdjuntos) {
        this.docAdjuntos = docAdjuntos;
    }

    public String getUltimaAgenda() {
        return ultimaAgenda;
    }

    public void setUltimaAgenda(String ultimaAgenda) {
        this.ultimaAgenda = ultimaAgenda;
    }

    public String getSubTipoWorkFoce() {
        return subTipoWorkFoce;
    }

    public void setSubTipoWorkFoce(String subTipoWorkFoce) {
        this.subTipoWorkFoce = subTipoWorkFoce;
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

    public String getObservacionesTecnico() {
        return observacionesTecnico;
    }

    public void setObservacionesTecnico(String observacionesTecnico) {
        this.observacionesTecnico = observacionesTecnico;
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

    public List<CmtAgendaAuditoria> getListAuditoria() {
        return listAuditoria;
    }

    public void setListAuditoria(List<CmtAgendaAuditoria> listAuditoria) {
        this.listAuditoria = listAuditoria;
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

    public String getMotivosReagenda() {
        return motivosReagenda;
    }

    public void setMotivosReagenda(String motivosReagenda) {
        this.motivosReagenda = motivosReagenda;
    }
 
}
