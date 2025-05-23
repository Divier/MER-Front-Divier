package co.com.claro.mgl.jpa.entities.cm;

import co.com.claro.mgl.dtos.CmtSubEdificioDto;
import co.com.claro.mgl.jpa.entities.OnyxOtCmDir;
import co.com.claro.mgl.jpa.entities.OnyxOtCmDirAuditoria;
import co.com.claro.mgl.utils.CmtUtilidadesCM;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
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
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entidad mapeo tabla CMT_ORDENTRABAJO
 *
 * alejandro.martinez.ext@claro.com.co
 * @versión 1.00.0000
 */
@Entity
@Table(name = "CMT_ORDENTRABAJO", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmtOrdenTrabajoMgl.findAll", query = "SELECT c FROM CmtOrdenTrabajoMgl c"),
    @NamedQuery(name = "CmtOrdenTrabajoMgl.findByIdCm", query = "SELECT c FROM CmtOrdenTrabajoMgl c WHERE c.cmObj = :cmObj"),
    @NamedQuery(name = "CmtOrdenTrabajoMgl.findByTipoTrabajo", query = "SELECT c FROM CmtOrdenTrabajoMgl c WHERE c.tipoOtObj = :tipoOtObj"),
    @NamedQuery(name = "CmtOrdenTrabajoMgl.findByTipoOt", query = "SELECT c FROM CmtOrdenTrabajoMgl c WHERE c.tipoOtObj = :tipoOtObj"),
    @NamedQuery(name = "CmtOrdenTrabajoMgl.findOtToGestion", query = "SELECT o FROM CmtOrdenTrabajoMgl o, CmtTipoOtMgl t, CmtEstadoxFlujoMgl ef, CmtCuentaMatrizMgl cm WHERE t.idTipoOt = o.tipoOtObj.idTipoOt AND ef.tipoFlujoOtObj.basicaId = t.tipoFlujoOt.basicaId AND o.estadoInternoObj.basicaId = ef.estadoInternoObj.basicaId AND cm.cuentaMatrizId = o.cmObj.cuentaMatrizId AND o.estadoRegistro = 1 AND cm.comunidad = :comunidad AND cm.division= :division AND o.tipoOtObj = :tipoOt AND ef.estadoxFlujoId IN :estadosxflujoList ORDER BY o.fechaCreacion ASC")
})
public class CmtOrdenTrabajoMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "CmtOrdenTrabajoMgl.CmtOrdenTrabajoSq",
            sequenceName = "MGL_SCHEME.CMT_ORDENTRABAJO_SQ",
            allocationSize = 1)
    @GeneratedValue(generator = "CmtOrdenTrabajoMgl.CmtOrdenTrabajoSq")
    @Column(name = "OT_ID")
    private BigDecimal idOt;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUENTAMATRIZ_ID")
    private CmtCuentaMatrizMgl cmObj;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_SEGMENTO")
    private CmtBasicaMgl segmento;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_TIPO_TRABAJO")
    private CmtBasicaMgl basicaIdTipoTrabajo;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TIPO_OT_ID", referencedColumnName = "TIPO_OT_ID", nullable = false)
    private CmtTipoOtMgl tipoOtObj;
    @Column(name = "FECHA_CIERRE_OT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCierreOt;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_ESTADOINTERNO", referencedColumnName = "BASICA_ID", nullable = false)
    private CmtBasicaMgl estadoInternoObj;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PERFIL_CREACION")
    private int perfilCreacion;
    @Column(name = "FECHA_EDICION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEdicion;
    @Size(min = 1, max = 20)
    @Column(name = "USUARIO_EDICION")
    private String usuarioEdicion;
    @Basic(optional = false)
    @Column(name = "PERFIL_EDICION")
    private int perfilEdicion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "DETALLE", columnDefinition = "default 'EDICION'")
    private String detalle;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTADO_REGISTRO", columnDefinition = "default 1")
    private int estadoRegistro;
    @Column(name = "FECHA_PROGRAMACION_OT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaProgramacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "OBSERVACION")
    private String observacion;
    @Column(name = "ID_USUARIO_CREACION", nullable = false)
    private BigDecimal usuarioCreacionId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SOLICITUD_CM_ID", nullable = true)
    private CmtSolicitudCmMgl solicitud;
    @OneToMany(mappedBy = "ordenTrabajoObj", fetch = FetchType.LAZY)
    @OrderBy("notaOtId ASC")
    private List<CmtNotaOtMgl> notasList;
    @OneToMany(mappedBy = "otObj", fetch = FetchType.LAZY)
    @OrderBy("idVt ASC")
    private List<CmtVisitaTecnicaMgl> visitaTecnicaMglList;
    @OneToMany(mappedBy = "OtObj", fetch = FetchType.LAZY)
    private List<CmtOrdenTrabajoRrMgl> ordenTrabajoRrList;
    @OneToMany(mappedBy = "otIdObj", fetch = FetchType.LAZY)
    private List<CmtOrdenTrabajoAuditoriaMgl> listAuditoria;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_TECNOLOGIA", nullable = true)
    private CmtBasicaMgl basicaIdTecnologia;
    @JoinColumn(name = "BASICA_ID_CLASE_ORDEN", referencedColumnName = "BASICA_ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private CmtBasicaMgl claseOrdenTrabajo;
    @OneToMany(mappedBy = "cmtOrdenTrabajoMglObj", fetch = FetchType.LAZY)
    private List<CmtOrdenTrabajoInventarioMgl> InventarioList;
    @Column(name = "ORDEN_TRABAJO_REFERENCIA", nullable = true)
    private BigDecimal ordenTrabajoReferencia;   
    @OneToMany(mappedBy = "ot_Id_Cm", fetch = FetchType.LAZY)
    private List<OnyxOtCmDir> listOnyx;
    @OneToMany(mappedBy = "ordenTrabajo", fetch = FetchType.LAZY)
    private List<CmtAgendamientoMgl> listAgendas;
    @OneToMany(mappedBy = "ordenTrabajo", fetch = FetchType.LAZY)
    private List<CmtAgendaAuditoria> listAgendaAuditoria;
    @OneToMany(mappedBy = "ot_Id_Cm", fetch = FetchType.LAZY)
    private List<OnyxOtCmDirAuditoria> listOnyxAuditoria;
    @Column(name = "ESTADOEXTINTOT", nullable = true)
    private BigDecimal estadoGeneralOt;
    
    
    @Column(name = "ONYX_OT_HIJA", nullable = true)
    private BigDecimal onyxOtHija;
    
    @Column(name = "PERSONA_RECIBE_VT")
    private String personaRecVt;
       
    
    @Column(name = "DOC_PER_RECIBE_VT")
    private String  docPerRecVt;
      
    
    @Column(name = "TEL_PER_RECIBA_VT")
    private String telPerRecVt;
    
    
    @Column(name = "PREREQUISITOS_VT")
    private String prerequisitosVT;
    
    
    @Column(name = "COMPLEJIDAD_SERVICIO")
    private String complejidadServicio;
    
    
    @Column(name = "EMAIL_PER_RECIBE_VT")
    private String emailPerRecVT;
    
    
    @Column(name = "FECHA_HABILITACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHabilitacion;
    
    
    @Column(name = "DISPONIBILIDAD_GESTION")
    private String disponibilidadGestion;
    
    
    @Column(name = "ENLACE_ID")
    private String enlaceId;
    
    
    @Transient
    private List<CmtSubEdificioDto> listaSubEdificio;
    
    @Transient
    private String ans;
    @Transient
    private String mensajeTecnologiaRR;

    public CmtOrdenTrabajoMgl() {
    }

    /**
     * @return the idOt
     */
    public BigDecimal getIdOt() {
        return idOt;
    }

    /**
     * @param idOt the idOt to set
     */
    public void setIdOt(BigDecimal idOt) {
        this.idOt = idOt;
    }

    /**
     * @return the cmObj
     */
    public CmtCuentaMatrizMgl getCmObj() {
        return cmObj;
    }

    /**
     * @param cmObj the cmObj to set
     */
    public void setCmObj(CmtCuentaMatrizMgl cmObj) {
        this.cmObj = cmObj;
    }

    /**
     * @return the segmento
     */
    public CmtBasicaMgl getSegmento() {
        return segmento;
    }

    /**
     * @param segmento the segmento to set
     */
    public void setSegmento(CmtBasicaMgl segmento) {
        this.segmento = segmento;
    }

    /**
     * @return the tipoOtObj
     */
    public CmtTipoOtMgl getTipoOtObj() {
        return tipoOtObj;
    }

    /**
     * @param tipoOtObj the tipoOtObj to set
     */
    public void setTipoOtObj(CmtTipoOtMgl tipoOtObj) {
        this.tipoOtObj = tipoOtObj;
    }

    /**
     * @return the fechaCierreOt
     */
    public Date getFechaCierreOt() {
        return fechaCierreOt;
    }

    /**
     * @param fechaCierreOt the fechaCierreOt to set
     */
    public void setFechaCierreOt(Date fechaCierreOt) {
        this.fechaCierreOt = fechaCierreOt;
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
     * @return the fechaProgramacion
     */
    public Date getFechaProgramacion() {
        return fechaProgramacion;
    }

    /**
     * @param fechaProgramacion the fechaEdicion to set
     */
    public void setFechaProgramacion(Date fechaProgramacion) {
        this.fechaProgramacion = fechaProgramacion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public List<CmtNotaOtMgl> getNotasList() {
        return notasList;
    }

    public void setNotasList(List<CmtNotaOtMgl> notasList) {
        this.notasList = notasList;
    }

    public List<CmtVisitaTecnicaMgl> getVisitaTecnicaMglList() {
        return visitaTecnicaMglList;
    }

    public void setVisitaTecnicaMglList(List<CmtVisitaTecnicaMgl> visitaTecnicaMglList) {
        this.visitaTecnicaMglList = visitaTecnicaMglList;
    }

    public BigDecimal getUsuarioCreacionId() {
        return usuarioCreacionId;
    }

    public void setUsuarioCreacionId(BigDecimal usuarioCreacionId) {
        this.usuarioCreacionId = usuarioCreacionId;
    }


    public CmtSolicitudCmMgl getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(CmtSolicitudCmMgl solicitud) {
        this.solicitud = solicitud;
    }

    public String getAnsOt() {
        String result = "";
        if (fechaCreacion != null) {
            long diffDate = (new Date().getTime()) - (fechaCreacion.getTime());
            //Diferencia de las Fechas en Minutos
            long diffMinutes = Math.abs(diffDate / (60 * 1000));
            long minutes = diffMinutes % 60;
            long hours = Math.abs(diffDate / (60 * 60 * 1000));

            String hoursStr = (hours < 10 ? "0" + String.valueOf(hours) : String.valueOf(hours));
            String minutesStr = (minutes < 10 ? "0" + String.valueOf(minutes) : String.valueOf(minutes));

            result = hoursStr + ":" + minutesStr;
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

            if ((int) hours >= tipoOtObj.getAns()) {
                colorResult = "red";
            } else if ((int) hours < tipoOtObj.getAns()
                    && (int) hours >= tipoOtObj.getAnsAviso()) {
                colorResult = "yellow";
            } else if ((int) hours < tipoOtObj.getAnsAviso()) {
                colorResult = "green";
            }
        }

        return colorResult;
    }

    @PrePersist
    public void prePersist() {
        if (this.detalle == null || this.detalle.trim().isEmpty()) {
            this.detalle = "CREACION";
        }
        this.estadoRegistro = 1;
    }
 
    public List<CmtOrdenTrabajoAuditoriaMgl> getListAuditoria() {
        return listAuditoria;
    }

    public void setListAuditoria(List<CmtOrdenTrabajoAuditoriaMgl> listAuditoria) {
        this.listAuditoria = listAuditoria;
    }

    public List<CmtOrdenTrabajoRrMgl> getOrdenTrabajoRrList() {
        return ordenTrabajoRrList;
    }

    public void setOrdenTrabajoRrList(List<CmtOrdenTrabajoRrMgl> ordenTrabajoRrList) {
        this.ordenTrabajoRrList = ordenTrabajoRrList;
    }

    public CmtNotaOtMgl getLastNota() {
        CmtNotaOtMgl lastNota = null;
        if (notasList != null && !notasList.isEmpty()) {
            lastNota = notasList.get(0);
            for (CmtNotaOtMgl n : notasList) {
                if (n.getNotaOtId().compareTo(lastNota.getNotaOtId()) == 1) {
                    lastNota = n;
                }
            }
        }
        return lastNota;
    }

    public CmtBasicaMgl getBasicaIdTipoTrabajo() {
        return basicaIdTipoTrabajo;
    }

    public void setBasicaIdTipoTrabajo(CmtBasicaMgl basicaIdTipoTrabajo) {
        this.basicaIdTipoTrabajo = basicaIdTipoTrabajo;
    }

    public CmtBasicaMgl getBasicaIdTecnologia() {
        return basicaIdTecnologia;
    }

    public void setBasicaIdTecnologia(CmtBasicaMgl basicaIdTecnologia) {
        this.basicaIdTecnologia = basicaIdTecnologia;
    }

    public List<CmtSubEdificioDto> getListaSubEdificio() {
        return listaSubEdificio;
    }

    public void setListaSubEdificio(List<CmtSubEdificioDto> listaSubEdificio) {
        this.listaSubEdificio = listaSubEdificio;
    }

    public CmtBasicaMgl getClaseOrdenTrabajo() {
        return claseOrdenTrabajo;
    }

    public void setClaseOrdenTrabajo(CmtBasicaMgl claseOrdenTrabajo) {
        this.claseOrdenTrabajo = claseOrdenTrabajo;
    }
    
    public String getAns() {
        if (this.getTipoOtObj() != null && this.getTipoOtObj().getIdTipoOt() != null) {
            Long aux = CmtUtilidadesCM.calcularDiasHoy(this.fechaCreacion);
            if (aux != null && aux < this.getTipoOtObj().getAns()) {
                ans = "En cumplimiento";
            } else {
                ans = "Fuera de cumplimiento";
            }
        }
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    public List<CmtOrdenTrabajoInventarioMgl> getInventarioList() {
        return InventarioList;
    }

    public void setInventarioList(List<CmtOrdenTrabajoInventarioMgl> InventarioList) {
        this.InventarioList = InventarioList;
    }

    public BigDecimal getOrdenTrabajoReferencia() {
        return ordenTrabajoReferencia;
    }

    public void setOrdenTrabajoReferencia(BigDecimal ordenTrabajoReferencia) {
        this.ordenTrabajoReferencia = ordenTrabajoReferencia;
    }

    public BigDecimal getOnyxOtHija() {
        return onyxOtHija;
    }

    public void setOnyxOtHija(BigDecimal onyxOtHija) {
        this.onyxOtHija = onyxOtHija;
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

    public String getComplejidadServicio() {
        return complejidadServicio;
    }

    public void setComplejidadServicio(String complejidadServicio) {
        this.complejidadServicio = complejidadServicio;
    }

    public String getEmailPerRecVT() {
        return emailPerRecVT;
    }

    public void setEmailPerRecVT(String emailPerRecVT) {
        this.emailPerRecVT = emailPerRecVT;
    }
    
    
     /**
     * @return Lista de registros activos logicamente.
     */
    public List<CmtOrdenTrabajoInventarioMgl> getInventarioListActivos() {
        List<CmtOrdenTrabajoInventarioMgl> resultadoList=new ArrayList<CmtOrdenTrabajoInventarioMgl>();
        for(CmtOrdenTrabajoInventarioMgl cmtOrdenTrabajoInventarioMgl :InventarioList){
            if(cmtOrdenTrabajoInventarioMgl.getEstadoRegistro()==1){
                resultadoList.add(cmtOrdenTrabajoInventarioMgl);
            }
        }
        return resultadoList;
    }

    
     /**
     * Retorna la visita recnica activa
     *
     * @return CmtVisitaTecnicaMgl visita tecnica activa
     * @
     */
    public CmtVisitaTecnicaMgl getVtActivaFromOt() {
        if (this.getVisitaTecnicaMglList() != null) {
            for (CmtVisitaTecnicaMgl cmtVisitaTecnicaMgl : this.getVisitaTecnicaMglList()) {
                if (cmtVisitaTecnicaMgl.getEstadoVisitaTecnica().compareTo(BigDecimal.ONE) == 0) {
                    return cmtVisitaTecnicaMgl;
                }
            }
            return null;
        } else {
            return null;
        }
    }

    public Date getFechaHabilitacion() {
        return fechaHabilitacion;
    }

    public void setFechaHabilitacion(Date fechaHabilitacion) {
        this.fechaHabilitacion = fechaHabilitacion;
    }

    public List<OnyxOtCmDir> getListOnyx() {
        return listOnyx;
    }

    public void setListOnyx(List<OnyxOtCmDir> listOnyx) {
        this.listOnyx = listOnyx;
    }

    public List<CmtAgendamientoMgl> getListAgendas() {
        return listAgendas;
    }

    public void setListAgendas(List<CmtAgendamientoMgl> listAgendas) {
        this.listAgendas = listAgendas;
    }

    public List<CmtAgendaAuditoria> getListAgendaAuditoria() {
        return listAgendaAuditoria;
    }

    public void setListAgendaAuditoria(List<CmtAgendaAuditoria> listAgendaAuditoria) {
        this.listAgendaAuditoria = listAgendaAuditoria;
    }

    public List<OnyxOtCmDirAuditoria> getListOnyxAuditoria() {
        return listOnyxAuditoria;
    }

    public void setListOnyxAuditoria(List<OnyxOtCmDirAuditoria> listOnyxAuditoria) {
        this.listOnyxAuditoria = listOnyxAuditoria;
    }

    public String getMensajeTecnologiaRR() {
        return mensajeTecnologiaRR;
    }

    public void setMensajeTecnologiaRR(String mensajeTecnologiaRR) {
        this.mensajeTecnologiaRR = mensajeTecnologiaRR;
    }

    public String getDisponibilidadGestion() {
        return disponibilidadGestion;
    }

    public void setDisponibilidadGestion(String disponibilidadGestion) {
        this.disponibilidadGestion = disponibilidadGestion;
    }

    public BigDecimal getEstadoGeneralOt() {
        return estadoGeneralOt;
    }

    public void setEstadoGeneralOt(BigDecimal estadoGeneralOt) {
        this.estadoGeneralOt = estadoGeneralOt;
    }

    public String getEnlaceId() {
        return enlaceId;
    }

    public void setEnlaceId(String enlaceId) {
        this.enlaceId = enlaceId;
    }

}
