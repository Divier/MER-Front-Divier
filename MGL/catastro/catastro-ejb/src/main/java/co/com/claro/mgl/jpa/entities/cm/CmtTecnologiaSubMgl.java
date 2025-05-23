package co.com.claro.mgl.jpa.entities.cm;

import co.com.claro.mgl.businessmanager.cm.CmtAvisoProgramadoMglManager;
import co.com.claro.mgl.jpa.entities.HhppMgl;
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
import javax.persistence.PostUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author cardenaslb
 */
@Entity
@XmlRootElement
@Table(name = "CMT_TECNOLOGIA_SUB", catalog = "", schema = "MGL_SCHEME")
@NamedQueries({
    @NamedQuery(name = "CmtTecnologiaSubMgl.findAll", query = "SELECT c FROM CmtTecnologiaSubMgl c"),
    @NamedQuery(name = "CmtTecnologiaSubMgl.findByTecnoSubedificioId", query = "SELECT c FROM CmtTecnologiaSubMgl c WHERE c.tecnoSubedificioId = :tecnoSubedificioId"),
    @NamedQuery(name = "CmtTecnologiaSubMgl.findBySubedificioId", query = "SELECT c FROM CmtTecnologiaSubMgl c WHERE c.subedificioId = :subedificioId and c.estadoRegistro=1"),
    @NamedQuery(name = "CmtTecnologiaSubMgl.findBySubEdiAndTecno", query = "SELECT c FROM CmtTecnologiaSubMgl c WHERE c.subedificioId = :subedificioId and c.basicaIdTecnologias = :basicaIdTecnologias and c.estadoRegistro=1"),
    @NamedQuery(name = "CmtTecnologiaSubMgl.findBySubedificioIdBasicaIdTecnologias", query = "SELECT c FROM CmtTecnologiaSubMgl c WHERE c.subedificioId = :subedificioId  AND c.basicaIdTecnologias = :basicaIdTecnologias AND c.estadoRegistro=1"),
    @NamedQuery(name = "CmtTecnologiaSubMgl.findByBasicaIdTecnologias", query = "SELECT c FROM CmtTecnologiaSubMgl c WHERE c.basicaIdTecnologias = :basicaIdTecnologias AND c.estadoRegistro=1"),
    @NamedQuery(name = "CmtTecnologiaSubMgl.findByNodoId", query = "SELECT c FROM CmtTecnologiaSubMgl c WHERE c.nodoId = :nodoId and c.estadoRegistro=1"),
    @NamedQuery(name = "CmtTecnologiaSubMgl.findByFechaCreacion", query = "SELECT c FROM CmtTecnologiaSubMgl c WHERE c.fechaCreacion = :fechaCreacion and c.estadoRegistro=1"),
    @NamedQuery(name = "CmtTecnologiaSubMgl.findByUsuarioCreacion", query = "SELECT c FROM CmtTecnologiaSubMgl c WHERE c.usuarioCreacion = :usuarioCreacion and c.estadoRegistro=1"),
    @NamedQuery(name = "CmtTecnologiaSubMgl.findByPerfilCreacion", query = "SELECT c FROM CmtTecnologiaSubMgl c WHERE c.perfilCreacion = :perfilCreacion and c.estadoRegistro=1"),
    @NamedQuery(name = "CmtTecnologiaSubMgl.findByFechaEdicion", query = "SELECT c FROM CmtTecnologiaSubMgl c WHERE c.fechaEdicion = :fechaEdicion and c.estadoRegistro=1"),
    @NamedQuery(name = "CmtTecnologiaSubMgl.findByUsuarioEdicion", query = "SELECT c FROM CmtTecnologiaSubMgl c WHERE c.usuarioEdicion = :usuarioEdicion and c.estadoRegistro=1"),
    @NamedQuery(name = "CmtTecnologiaSubMgl.findByPerfilEdicion", query = "SELECT c FROM CmtTecnologiaSubMgl c WHERE c.perfilEdicion = :perfilEdicion and c.estadoRegistro=1"),
    @NamedQuery(name = "CmtTecnologiaSubMgl.findByEstadoRegistro", query = "SELECT c FROM CmtTecnologiaSubMgl c WHERE c.estadoRegistro = :estadoRegistro and c.estadoRegistro=1"),
    @NamedQuery(name = "CmtTecnologiaSubMgl.findBySubEdiAndTecnoId", query = "SELECT c FROM CmtTecnologiaSubMgl c WHERE c.subedificioId = :subedificioId and c.basicaIdTecnologias.basicaId = :basicaIdTecnologias and c.estadoRegistro=1")})
    public class CmtTecnologiaSubMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "CmtTecnologiaSubMgl.CmtTecnologiaSubMglSq",
            sequenceName = "MGL_SCHEME.CMT_TECNOLOGIA_SUB_SQ", allocationSize = 1)
    @GeneratedValue(generator = "CmtTecnologiaSubMgl.CmtTecnologiaSubMglSq")
    @Column(name = "TECNO_SUBEDIFICIO_ID", nullable = false)
    private BigDecimal tecnoSubedificioId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUBEDIFICIO_ID")
    private CmtSubEdificioMgl subedificioId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_TECNOLOGIAS")
    private CmtBasicaMgl basicaIdTecnologias;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ESTADO_TECNOLOGIA_ID")
    private CmtBasicaMgl basicaIdEstadosTec;
    @JoinColumn(name = "NODO_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private NodoMgl nodoId;
    @Basic(optional = false)
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FECHA_CREACION", nullable = false)
    private Date fechaCreacion;
    @NotNull
    @Basic(optional = false)
    @Size(min = 1, max = 20)
    @Column(name = "USUARIO_CREACION", nullable = false, length = 20)
    private String usuarioCreacion;
    @NotNull
    @Basic(optional = false)
    @Column(name = "PERFIL_CREACION", nullable = false)
    private int perfilCreacion;
    @Column(name = "FECHA_EDICION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEdicion;
    @Size(max = 20)
    @Column(name = "USUARIO_EDICION", length = 20)
    private String usuarioEdicion;
    @NotNull
    @Basic(optional = false)
    @Column(name = "PERFIL_EDICION", nullable = false)
    private int perfilEdicion;
    @NotNull
    @Basic(optional = false)
    @Column(name = "ESTADO_REGISTRO", nullable = false)
    private int estadoRegistro;
    @OneToMany(mappedBy = "tecnoSubedificioId", fetch = FetchType.LAZY)
    private List<CmtInventariosTecnologiaMgl> listInventariosTecnologias;
    @OneToMany(mappedBy = "cmtTecnologiaSubId", fetch = FetchType.LAZY)
    private List<HhppMgl> listaHhpp;
    @Basic
    @NotNull
    @Column(name = "COSTO_VT", nullable = false)
    private BigDecimal costoVt;
    @JoinColumn(name = "NUMERO_VT")
    @OneToOne(fetch = FetchType.LAZY)
    private CmtVisitaTecnicaMgl visitaTecnica;
    @JoinColumn(name = "NUMERO_OT_VT")
    @OneToOne(fetch = FetchType.LAZY)
    private CmtOrdenTrabajoMgl ordenTrabajo;
    @Basic
    @NotNull
    @Column(name = "COSTO_ACOMETIDA", nullable = false)
    private BigDecimal costoAcometida;
    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FECHA_HABILITACION", nullable = false)
    private Date fechaHabilitacion;
    @Basic
    @Column(name = "TIEMPO_RECUPERACION", nullable = false)
    private BigDecimal tiempoRecuperacion;
    @Basic
    @Column(name = "CUMPLIMIENTO")
    private BigDecimal cumplimiento;
    @Basic
    @Column(name = "META")
    private BigDecimal meta;
    @Column(name = "VISITA_TECNICA", nullable = false, length = 2)
    private String flagVisitaTecnica;
    @Transient
    private int cantidad;
    @Column(name = "FECHA_VISITA_TECNICA", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaVisitaTecnica;
    @Column(name = "COSTO_VISITA_TECNICA", nullable = true)
    private BigDecimal costoVisitaTecnica;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_TIPO_CONF_DISTB")
    private CmtBasicaMgl tipoConfDistribObj;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_ALIMT_ELECT")
    private CmtBasicaMgl alimentElectObj;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_TIPO_DISTRIBUCION")
    private CmtBasicaMgl tipoDistribucionObj;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_MANEJO_ASCENSORES")
    private CmtBasicaMgl manejoAscensoresObj;
    @Column(name = "VOLTAJE", nullable = true, length = 50)
    private String voltaje;
    @Column(name = "OTRO_TIPO_DISTR", nullable = true, length = 100)
    private String otroTipoDistribucion;
    @Column(name = "TAPS_EXTERNOS", nullable = true, length = 500)
    private String tapsExternos;
    @Column(name = "MARCA", nullable = true, length = 200)
    private String marca;
    @Column(name = "TELEFONO", nullable = true, length = 20)
    private String telefono;
    @Column(name = "ACONDICIONAMIENTO", nullable = true)
    private String acondicionamientos;

    public CmtTecnologiaSubMgl() {
    }

    public BigDecimal getTecnoSubedificioId() {
        return tecnoSubedificioId;
    }

    public void setTecnoSubedificioId(BigDecimal tecnoSubedificioId) {
        this.tecnoSubedificioId = tecnoSubedificioId;
    }

    public CmtSubEdificioMgl getSubedificioId() {
        return subedificioId;
    }

    public void setSubedificioId(CmtSubEdificioMgl subedificioId) {
        this.subedificioId = subedificioId;
    }

    public CmtBasicaMgl getBasicaIdTecnologias() {
        return basicaIdTecnologias;
    }

    public void setBasicaIdTecnologias(CmtBasicaMgl basicaIdTecnologias) {
        this.basicaIdTecnologias = basicaIdTecnologias;
    }

    public NodoMgl getNodoId() {
        return nodoId;
    }

    public void setNodoId(NodoMgl nodoId) {
        this.nodoId = nodoId;
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

    public List<CmtInventariosTecnologiaMgl> getListInventariosTecnologias() {
        return listInventariosTecnologias;
    }

    public void setListInventariosTecnologias(List<CmtInventariosTecnologiaMgl> listInventariosTecnologias) {
        this.listInventariosTecnologias = listInventariosTecnologias;
    }

    public List<HhppMgl> getListaHhpp() {
        return listaHhpp;
    }

    public void setListaHhpp(List<HhppMgl> listaHhpp) {
        this.listaHhpp = listaHhpp;
    }

    public CmtBasicaMgl getBasicaIdEstadosTec() {
        return basicaIdEstadosTec;
    }

    public void setBasicaIdEstadosTec(CmtBasicaMgl basicaIdEstadosTec) {
        this.basicaIdEstadosTec = basicaIdEstadosTec;
    }

    public Date getFechaHabilitacion() {
        return fechaHabilitacion;
    }

    public void setFechaHabilitacion(Date fechaHabilitacion) {
        this.fechaHabilitacion = fechaHabilitacion;
    }

    @PostUpdate
    private void notificarCambioEstado() {
        CmtAvisoProgramadoMglManager capmm = new CmtAvisoProgramadoMglManager();
        capmm.verificarCambio(this);
    }

    public BigDecimal getCostoVt() {
        return costoVt;
    }

    public void setCostoVt(BigDecimal costoVt) {
        this.costoVt = costoVt;
    }

    public CmtVisitaTecnicaMgl getVisitaTecnica() {
        return visitaTecnica;
    }

    public void setVisitaTecnica(CmtVisitaTecnicaMgl visitaTecnica) {
        this.visitaTecnica = visitaTecnica;
    }

    public CmtOrdenTrabajoMgl getOrdenTrabajo() {
        return ordenTrabajo;
    }

    public void setOrdenTrabajo(CmtOrdenTrabajoMgl ordenTrabajo) {
        this.ordenTrabajo = ordenTrabajo;
    }

    public BigDecimal getCostoAcometida() {
        return costoAcometida;
    }

    public void setCostoAcometida(BigDecimal costoAcometida) {
        this.costoAcometida = costoAcometida;
    }

    public BigDecimal getTiempoRecuperacion() {
        return tiempoRecuperacion;
    }

    public void setTiempoRecuperacion(BigDecimal tiempoRecuperacion) {
        this.tiempoRecuperacion = tiempoRecuperacion;
    }

    public BigDecimal getCumplimiento() {
        return cumplimiento;
    }

    public void setCumplimiento(BigDecimal cumplimiento) {
        this.cumplimiento = cumplimiento;
    }

    public BigDecimal getMeta() {
        return meta;
    }

    public void setMeta(BigDecimal meta) {
        this.meta = meta;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getFlagVisitaTecnica() {
        return flagVisitaTecnica;
    }

    public void setFlagVisitaTecnica(String flagVisitaTecnica) {
        this.flagVisitaTecnica = flagVisitaTecnica;
    }

    public Date getFechaVisitaTecnica() {
        return fechaVisitaTecnica;
    }

    public void setFechaVisitaTecnica(Date fechaVisitaTecnica) {
        this.fechaVisitaTecnica = fechaVisitaTecnica;
    }

    public BigDecimal getCostoVisitaTecnica() {
        return costoVisitaTecnica;
    }

    public void setCostoVisitaTecnica(BigDecimal costoVisitaTecnica) {
        this.costoVisitaTecnica = costoVisitaTecnica;
    }

    public CmtBasicaMgl getTipoConfDistribObj() {
        return tipoConfDistribObj;
    }

    public void setTipoConfDistribObj(CmtBasicaMgl tipoConfDistribObj) {
        this.tipoConfDistribObj = tipoConfDistribObj;
    }

    public CmtBasicaMgl getAlimentElectObj() {
        return alimentElectObj;
    }

    public void setAlimentElectObj(CmtBasicaMgl alimentElectObj) {
        this.alimentElectObj = alimentElectObj;
    }

    public CmtBasicaMgl getTipoDistribucionObj() {
        return tipoDistribucionObj;
    }

    public void setTipoDistribucionObj(CmtBasicaMgl tipoDistribucionObj) {
        this.tipoDistribucionObj = tipoDistribucionObj;
    }

    public CmtBasicaMgl getManejoAscensoresObj() {
        return manejoAscensoresObj;
    }

    public void setManejoAscensoresObj(CmtBasicaMgl manejoAscensoresObj) {
        this.manejoAscensoresObj = manejoAscensoresObj;
    }

    public String getVoltaje() {
        return voltaje;
    }

    public void setVoltaje(String voltaje) {
        this.voltaje = voltaje;
    }

    public String getOtroTipoDistribucion() {
        return otroTipoDistribucion;
    }

    public void setOtroTipoDistribucion(String otroTipoDistribucion) {
        this.otroTipoDistribucion = otroTipoDistribucion;
    }

    public String getTapsExternos() {
        return tapsExternos;
    }

    public void setTapsExternos(String tapsExternos) {
        this.tapsExternos = tapsExternos;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getAcondicionamientos() {
        return acondicionamientos;
    }

    public void setAcondicionamientos(String acondicionamientos) {
        this.acondicionamientos = acondicionamientos;
    }

    public int getAddressWithService() {
        int contador = 0;
        if (this.getListaHhpp() != null) {
            for (HhppMgl hhppMgl : this.getListaHhpp()) {
                if (hhppMgl.getEhhId().getEhhID().equals("S")) {
                    contador++;
                }
            }
        }
        return contador;
    }

    
    
    
}
