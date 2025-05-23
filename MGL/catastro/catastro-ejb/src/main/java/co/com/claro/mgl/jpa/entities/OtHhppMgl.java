/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities;

import co.com.claro.mgl.businessmanager.cm.CmtAvisoProgramadoMglManager;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtRegionalRr;
import co.com.claro.mgl.utils.CmtUtilidadesCM;
import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.OrderBy;
import javax.persistence.PostUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Juan David Hernandez
 */
@Entity
@Table(name = "MGL_OT_DIRECCION", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OtHhppMgl.findAll", query = "SELECT m FROM OtHhppMgl m where m.estadoRegistro = 1")
    ,
    @NamedQuery(name = "OtHhppMgl.findOrdenTrabajoByIdSolicitud", query = "SELECT o FROM OtHhppMgl o WHERE o.solicitudId.idSolicitud = :idSolicitud")})
public class OtHhppMgl implements Serializable {
    
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "OtHhppMgl.OtHhppMglSq",
            sequenceName = "MGL_SCHEME.MGL_OT_DIRECCION_SQ", allocationSize = 1)
    @GeneratedValue(generator = "OtHhppMgl.OtHhppMglSq")
    @Column(name = "OT_DIRECCION_ID", nullable = false)
    private BigDecimal otHhppId;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TIPO_OT_DIRECCION_ID", referencedColumnName = "TIPO_OT_ID", nullable = false)
    private TipoOtHhppMgl tipoOtHhppId;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ESTADO_GENERAL", referencedColumnName = "BASICA_ID", nullable = false)
    private CmtBasicaMgl estadoGeneral;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ESTADO_INTERNO_INICIAL", referencedColumnName = "BASICA_ID", nullable = false)
    private CmtBasicaMgl estadoInternoInicial;
    
    @NotNull
    @Column(name = "NOMBRE_CONTACTO")
    private String nombreContacto;
    @NotNull
    @Column(name = "TELEFONO_CONTACTO")
    private String telefonoContacto;
    @NotNull
    @Column(name = "CORREO_CONTACTO")
    private String correoContacto;
    @Column(name = "FECHA_CREACION_OT")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCreacionOt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DIRECCION_ID", referencedColumnName = "DIRECCION_ID")
    private DireccionMgl direccionId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUB_DIRECCION_ID", referencedColumnName = "SUB_DIRECCION_ID")
    private SubDireccionMgl subDireccionId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEC_SOL_TEC_HABILITADA_ID", referencedColumnName = "SOL_TEC_HABILITADA_ID")
    private Solicitud solicitudId;
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
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_TIPO_TRABAJO_HHPP")
    private CmtBasicaMgl basicaIdTipoTrabajo;
    @OneToMany(mappedBy = "otHhppId", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<OtHhppTecnologiaMgl> tecnologiaBasicaList;
    @Column(name = "ONYX_OT_HIJA", nullable = true)
    private BigDecimal onyxOtHija;
    @Column(name = "COMPLEJIDAD_SERVICIO")
    private String complejidadServicio;
    @Column(name = "DISPONIBILIDAD_GESTION")
    private String disponibilidadGestion;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_SEGMENTO")
    private CmtBasicaMgl segmento;
    @Column(name = "ENLACE_ID")
    private String enlaceId;

    @Transient
    private String colorAlerta;
    @Transient
    private String tecnologia;
    @Transient
    private BigDecimal tipoTecnologiaId;
    @Transient
    private CmtRegionalRr regionalRr;
    @Transient
    private String nodosSeleccionado;

    @Transient
    private String ans;
    @OneToMany(mappedBy = "ot_Direccion_Id", fetch = FetchType.LAZY)
    private List<OnyxOtCmDir> listOnyx;
    @OneToMany(mappedBy = "ordenTrabajo", fetch = FetchType.LAZY)
    private List<MglAgendaDireccion> listAgendaDireccion;
    @OneToMany(mappedBy = "otHhppMgl", fetch = FetchType.LAZY)
    private List<OtHhppMglAuditoria> listOtHhppMglAuditoria;
    @OneToMany(mappedBy = "ordenTrabajo", fetch = FetchType.LAZY)
    private List<MglAgendaDireccionAuditoria> listAgendaDireccionAuditoria;
    @OneToMany(mappedBy = "ot_Direccion_Id", fetch = FetchType.LAZY)
    private List<OnyxOtCmDirAuditoria> listOnyxOtCmDirAuditoria;
    @OneToMany(mappedBy = "otHhppId", fetch = FetchType.LAZY)
    @OrderBy("notasId ASC")
    private List<CmtNotasOtHhppMgl> notasList;
    
    
    public BigDecimal getOtHhppId() {
        return otHhppId;
    }

    public void setOtHhppId(BigDecimal otHhppId) {
        this.otHhppId = otHhppId;
    }

    public TipoOtHhppMgl getTipoOtHhppId() {
        return tipoOtHhppId;
    }

    public void setTipoOtHhppId(TipoOtHhppMgl tipoOtHhppId) {
        this.tipoOtHhppId = tipoOtHhppId;
    }

    public CmtBasicaMgl getEstadoGeneral() {
        return estadoGeneral;
    }

    public void setEstadoGeneral(CmtBasicaMgl estadoGeneral) {
        this.estadoGeneral = estadoGeneral;
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

    public String getCorreoContacto() {
        return correoContacto;
    }

    public void setCorreoContacto(String correoContacto) {
        this.correoContacto = correoContacto;
    }

    public Date getFechaCreacionOt() {
        return fechaCreacionOt;
    }

    public void setFechaCreacionOt(Date fechaCreacionOt) {
        this.fechaCreacionOt = fechaCreacionOt;
    }

    public DireccionMgl getDireccionId() {
        return direccionId;
    }

    public void setDireccionId(DireccionMgl direccionId) {
        this.direccionId = direccionId;
    }

    public SubDireccionMgl getSubDireccionId() {
        return subDireccionId;
    }

    public void setSubDireccionId(SubDireccionMgl subDireccionId) {
        this.subDireccionId = subDireccionId;
    }

    public Solicitud getSolicitudId() {
        return solicitudId;
    }

    public void setSolicitudId(Solicitud solicitudId) {
        this.solicitudId = solicitudId;
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

    public List<OtHhppTecnologiaMgl> getTecnologiaBasicaList() {
        return tecnologiaBasicaList;
    }

    public void setTecnologiaBasicaList(List<OtHhppTecnologiaMgl> tecnologiaBasicaList) {
        this.tecnologiaBasicaList = tecnologiaBasicaList;
    }

    public String getColorAlerta() {
        return colorAlerta;
    }

    public void setColorAlerta(String colorAlerta) {
        this.colorAlerta = colorAlerta;
    }

    public String getTecnologia() {
        return tecnologia;
    }

    public void setTecnologia(String tecnologia) {
        this.tecnologia = tecnologia;
    }

    @PostUpdate
    private void notificarCambioEstado() {
        CmtAvisoProgramadoMglManager capmm = new CmtAvisoProgramadoMglManager();
        capmm.verificarCambio(this);
    }

    public CmtBasicaMgl getBasicaIdTipoTrabajo() {
        return basicaIdTipoTrabajo;
    }

    public void setBasicaIdTipoTrabajo(CmtBasicaMgl basicaIdTipoTrabajo) {
        this.basicaIdTipoTrabajo = basicaIdTipoTrabajo;
    }

    public CmtBasicaMgl getEstadoInternoInicial() {
        return estadoInternoInicial;
    }

    public void setEstadoInternoInicial(CmtBasicaMgl estadoInternoInicial) {
        this.estadoInternoInicial = estadoInternoInicial;
    }
        
    public String getAns() {
        if (this.getTipoOtHhppId() != null && this.getTipoOtHhppId().getTipoOtId() != null) {
            Long aux = CmtUtilidadesCM.calcularDiasHoy(this.fechaCreacion);
            if (aux != null && aux < this.getTipoOtHhppId().getAns()) {
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

    public BigDecimal getTipoTecnologiaId() {
        return tipoTecnologiaId;
    }

    public void setTipoTecnologiaId(BigDecimal tipoTecnologiaId) {
        this.tipoTecnologiaId = tipoTecnologiaId;
    }

    public BigDecimal getOnyxOtHija() {
        return onyxOtHija;
    }

    public void setOnyxOtHija(BigDecimal onyxOtHija) {
        this.onyxOtHija = onyxOtHija;
    }

    public String getComplejidadServicio() {
        return complejidadServicio;
    }

    public void setComplejidadServicio(String complejidadServicio) {
        this.complejidadServicio = complejidadServicio;
    }

    public List<OnyxOtCmDir> getListOnyx() {
        return listOnyx;
    }

    public void setListOnyx(List<OnyxOtCmDir> listOnyx) {
        this.listOnyx = listOnyx;
    }

    public List<MglAgendaDireccion> getListAgendaDireccion() {
        return listAgendaDireccion;
    }

    public void setListAgendaDireccion(List<MglAgendaDireccion> listAgendaDireccion) {
        this.listAgendaDireccion = listAgendaDireccion;
    }

    public List<OtHhppMglAuditoria> getListOtHhppMglAuditoria() {
        return listOtHhppMglAuditoria;
    }

    public void setListOtHhppMglAuditoria(List<OtHhppMglAuditoria> listOtHhppMglAuditoria) {
        this.listOtHhppMglAuditoria = listOtHhppMglAuditoria;
    }

    public List<MglAgendaDireccionAuditoria> getListAgendaDireccionAuditoria() {
        return listAgendaDireccionAuditoria;
    }

    public void setListAgendaDireccionAuditoria(List<MglAgendaDireccionAuditoria> listAgendaDireccionAuditoria) {
        this.listAgendaDireccionAuditoria = listAgendaDireccionAuditoria;
    }

    public List<OnyxOtCmDirAuditoria> getListOnyxOtCmDirAuditoria() {
        return listOnyxOtCmDirAuditoria;
    }

    public void setListOnyxOtCmDirAuditoria(List<OnyxOtCmDirAuditoria> listOnyxOtCmDirAuditoria) {
        this.listOnyxOtCmDirAuditoria = listOnyxOtCmDirAuditoria;
    }

    public CmtRegionalRr getRegionalRr() {
        return regionalRr;
    }
    
    public void setRegionalRr(CmtRegionalRr regionalRr) {
        this.regionalRr = regionalRr;
    }

    public String getNodosSeleccionado() {
        return nodosSeleccionado;
    }

    public void setNodosSeleccionado(String nodosSeleccionado) {
        this.nodosSeleccionado = nodosSeleccionado;
    }

    public String getDisponibilidadGestion() {
        return disponibilidadGestion;
    }

    public void setDisponibilidadGestion(String disponibilidadGestion) {
        this.disponibilidadGestion = disponibilidadGestion;
    }
    
    public CmtBasicaMgl getSegmento() {
        return segmento;
    }

    public void setSegmento(CmtBasicaMgl segmento) {
        this.segmento = segmento;
    }

    public String getEnlaceId() {
        return enlaceId;
    }

    public void setEnlaceId(String enlaceId) {
        this.enlaceId = enlaceId;
    }

    public List<CmtNotasOtHhppMgl> getNotasList() {
        return notasList;
    }

    public void setNotasList(List<CmtNotasOtHhppMgl> notasList) {
        this.notasList = notasList;
    }
}
