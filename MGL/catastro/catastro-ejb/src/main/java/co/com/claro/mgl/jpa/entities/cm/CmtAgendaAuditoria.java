/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities.cm;

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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author cardenaslb
 */
@Entity
@Table(name = "CMT_AGENDA$AUD", schema = "MGL_SCHEME")
@XmlRootElement
public class CmtAgendaAuditoria implements Serializable{
    
    @Id
    @Basic(optional = false)
    @Column(name = "AGENDA_AUD_ID", nullable = false)
    private BigDecimal agendaAud_Id;
      
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AGENDA_ID", nullable = false)
    private CmtAgendamientoMgl idAgObj;

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



    @Column(name = "FECHA_ASIG_TECNICO")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaAsigTecnico;
    
    @Column(name = "FECHA_REAGENDA")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaReagenda;

    public BigDecimal getAgendaAud_Id() {
        return agendaAud_Id;
    }

    public void setAgendaAud_Id(BigDecimal agendaAud_Id) {
        this.agendaAud_Id = agendaAud_Id;
    }

    public CmtAgendamientoMgl getIdAgObj() {
        return idAgObj;
    }

    public void setIdAgObj(CmtAgendamientoMgl idAgObj) {
        this.idAgObj = idAgObj;
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
}
