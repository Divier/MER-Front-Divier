package co.com.claro.mgl.jpa.entities;

import co.com.claro.mgl.businessmanager.cm.CmtAvisoProgramadoMglManager;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTecnologiaSubMgl;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;
import net.telmex.pcml.service.OrdenTrabajoVO;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "TEC_TECNOLOGIA_HABILITADA", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HhppMgl.findAll", query = "SELECT s FROM HhppMgl s"),
    @NamedQuery(name = "HhppMgl.findByRRFields", query = "SELECT g FROM HhppMgl g WHERE UPPER(g.HhpCalle) = :calleRR AND UPPER(g.HhpPlaca) = :placaRR AND UPPER(g.HhpApart) = :apartRR AND UPPER(g.HhpComunidad) = :comunidadRR AND UPPER(g.HhpDivision) = :divisionRR AND g.estadoRegistro = 1"),
    @NamedQuery(name = "HhppMgl.findHhppDireccion", query = "SELECT s FROM HhppMgl s WHERE s.direccionObj.dirId = :dirId and s.SubDireccionObj is null"),
    @NamedQuery(name = "HhppMgl.findHhppSubDireccion", query = "SELECT s FROM HhppMgl s WHERE s.SubDireccionObj.sdiId = :sdiId "),
    @NamedQuery(name = "HhppMgl.findHhppsByDireccion", query = "SELECT s FROM HhppMgl s WHERE s.direccionObj.dirId = :dirId AND s.estadoRegistro = 1"),
    @NamedQuery(name = "HhppMgl.findHhppByNodoDireccionYSubDireccion", query = "SELECT h FROM HhppMgl h WHERE h.nodId.nodId = :nodId  AND  h.direccionObj.dirId = :dirId and h.SubDireccionObj.sdiId = :sdiId  AND  h.estadoRegistro = 1")
})
public class HhppMgl implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "HhppMgl.TEC_TEC_TECNOLOGIA_HAB_SQ",
            sequenceName = "MGL_SCHEME.TEC_TEC_TECNOLOGIA_HAB_SQ", allocationSize = 1)
    @GeneratedValue(generator = "HhppMgl.TEC_TEC_TECNOLOGIA_HAB_SQ")
    @Column(name = "TECNOLOGIA_HABILITADA_ID", nullable = false)
    private BigDecimal hhpId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DIRECCION_ID", nullable = false)
    private DireccionMgl direccionObj;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUB_DIRECCION_ID", nullable = false)
    private SubDireccionMgl SubDireccionObj;
    @JoinColumn(name = "TECNO_SUBEDIFICIO_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private CmtTecnologiaSubMgl cmtTecnologiaSubId;
    @ManyToOne
    @JoinColumn(name = "NODO_ID", referencedColumnName = "NODO_ID", nullable = false)
    private NodoMgl nodId;
    @Column(name = "TIPO_TECNOLOGIA_HAB_ID", nullable = false)
    private String thhId;
    @Column(name = "TIPO_CONEXION_TEC_HABI_ID", nullable = false)
    private BigDecimal thcId;
    @Column(name = "TIPO_RED_TEC_HABI_ID", nullable = false)
    private BigDecimal thrId;
    @Column(name = "SUSCRIPTOR", nullable = false)
    private String suscriptor;
    @Column(name = "FECHA_CREACION")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;
    @Column(name = "FECHA_EDICION")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaEdicion;
    @Column(name = "USUARIO_EDICION")
    private String usuarioEdicion;
    @ManyToOne
    @JoinColumn(name = "ESTADO_ID", referencedColumnName = "ESTADO_TEC_HABILITADA_ID", nullable = false)
    private EstadoHhppMgl ehhId;
    @Column(name = "TECNOLOGIA_HABILITADA_ID_RR")
    private String HhpIdrR;
    @Column(name = "CALLE")
    private String HhpCalle;
    @Column(name = "PLACA")
    private String HhpPlaca;
    @Column(name = "APART")
    private String HhpApart;
    @Column(name = "COMUNIDAD")
    private String HhpComunidad;
    @Column(name = "DIVISION")
    private String HhpDivision;
    @Column(name = "ESTADO_UNIT")
    private String HhpEstadoUnit;
    @Column(name = "VENDEDOR")
    private String HhpVendedor;
    @Column(name = "CODIGO_POSTAL")
    private String HhpCodigoPostal;
    @Transient
    private boolean selected = false;
    @Transient
    private String resultado;
    @Transient
    private String descripcion;
    @Column(name = "TIPO_ACOMET")
    private String HhpTipoAcomet;
    @Column(name = "ULT_UBICACION")
    private String HhpUltUbicacion;
    @Column(name = "HEAD_END")
    private String HhpHeadEnd;
    @Column(name = "TIPO")
    private String HhpTipo;
    @Column(name = "EDIFICIO")
    private String HhpEdificio;
    @Column(name = "TIPO_UNIDAD")
    private String HhpTipoUnidad;
    @Column(name = "TIPO_CBL_ACOMETIDA")
    private String HhpTipoCblAcometida;
    @Column(name = "FECHA_AUDIT")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date HhpFechaAudit;
    @Column(name = "NOTA_ADD1")
    private String HhpNotasAdd1;
    @Column(name = "NOTA_ADD2")
    private String HhpNotasAdd2;
    @Column(name = "NOTA_ADD3")
    private String HhpNotasAdd3;
    @Column(name = "NOTA_ADD4")
    private String HhpNotasAdd4;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUBEDIFICIO_ID", referencedColumnName = "SUBEDIFICIO_ID",
            nullable = true)
    private CmtSubEdificioMgl hhppSubEdificioObj;
    @OneToMany(mappedBy = "hhpp", fetch = FetchType.LAZY)
    private List<MarcasHhppMgl> listMarcasHhpp;
    @Column(name = "CFM")
    private BigDecimal cfm;
    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro;
    @Column(name = "PERFIL_CREACION")
    private int perfilCreacion;
    @Column(name = "PERFIL_EDICION")
    private int perfilEdicion;
    @Column(name = "MARKER", nullable = false)
    private String marker;
    @Column(name = "HHPP_SC_PROCESADO_HHPP")
    private String hhppSCProcesado;
    @Column(name = "ORIGEN_FICHA")
    private String hhppOrigenFicha;

    @Getter
    @Setter
    @Column(name = "CUENTA_CLIENTE_TRASLADAR")
    private String cuentaClienteTrasladar;
    @Getter
    @Setter
    @Column(name = "ID_DIRECCION_HHPP_VIRTUAL_RR")
    private String idDireccionHhppVirtualRr;

    @Column(name = "NAP", nullable = true, length = 8)
    private String nap;
    @Transient
    private String cuenta;
    @Transient
    private String otId;
    @OneToMany(mappedBy = "hhpIdObj", fetch = FetchType.LAZY)
    private List<HhppAuditoriaMgl> listAuditoria;
    @Transient
    OrdenTrabajoVO ordenTrabajoVO;
    @Transient
    private String nombreCuenta;
    @Transient
    private String departamento;
    @Transient
    private String justificacion;
    @Transient
    private String barrioHhpp;
    @Transient
    private String ciudad;
    @Transient
    private String direccionDetalladaHhpp;
    @Transient
    private String estratoActual;
    @Transient
    private String estadoOt;
    @Transient
    private boolean hhppExistente;
    @Transient
    private HhppMgl hhppMgl;
    @Transient
    private MarcasHhppMgl marcasHhppMgl;
    @Transient
    private MarcasMgl etiquetaHhpp;
    @Transient
    private boolean isNodoCertificado;
    @Transient
    private boolean isDetalleHhppFact;

    public BigDecimal getHhpId() {
        return hhpId;
    }

    public void setHhpId(BigDecimal hhpId) {
        this.hhpId = hhpId;
    }

    
    /* Se agrego un flujo de control para determinar si el campo "direccionObj" no es nulo */
    public BigDecimal getDirId() {
        if (this.direccionObj == null) {
            direccionObj = new DireccionMgl();
        }
        return direccionObj.getDirId();
    }

    public void setDirId(BigDecimal dirId) {
        direccionObj = new DireccionMgl();
        direccionObj.setDirId(dirId);
    }

    public NodoMgl getNodId() {
        return nodId;
    }

    public void setNodId(NodoMgl nodId) {
        this.nodId = nodId;
    }

    public String getThhId() {
        return thhId;
    }

    public void setThhId(String thhId) {
        this.thhId = thhId;
    }

    public BigDecimal getSdiId() {
        if (SubDireccionObj != null) {
            return SubDireccionObj.getSdiId();
        } else {
            return null;
        }
    }

    public void setSdiId(BigDecimal sdiId) {
        this.SubDireccionObj = new SubDireccionMgl();
        this.SubDireccionObj.setSdiId(sdiId);
    }

    public BigDecimal getThcId() {
        return thcId;
    }

    public void setThcId(BigDecimal thcId) {
        this.thcId = thcId;
    }

    public BigDecimal getThrId() {
        return thrId;
    }

    public void setThrId(BigDecimal thrId) {
        this.thrId = thrId;
    }

    public EstadoHhppMgl getEhhId() {
        return ehhId;
    }

    public void setEhhId(EstadoHhppMgl ehhId) {
        this.ehhId = ehhId;
    }

    public String getHhpIdrR() {
        return HhpIdrR;
    }

    public void setHhpIdrR(String HhpIdrR) {
        this.HhpIdrR = HhpIdrR;
    }

    public String getHhpCalle() {
        return HhpCalle;
    }

    public void setHhpCalle(String HhpCalle) {
        this.HhpCalle = HhpCalle;
    }

    public String getHhpPlaca() {
        return HhpPlaca;
    }

    public void setHhpPlaca(String HhpPlaca) {
        this.HhpPlaca = HhpPlaca;
    }

    public String getHhpApart() {
        return HhpApart;
    }

    public void setHhpApart(String HhpApart) {
        this.HhpApart = HhpApart;
    }

    public String getHhpComunidad() {
        return HhpComunidad;
    }

    public void setHhpComunidad(String HhpComunidad) {
        this.HhpComunidad = HhpComunidad;
    }

    public String getHhpDivision() {
        return HhpDivision;
    }

    public void setHhpDivision(String HhpDivision) {
        this.HhpDivision = HhpDivision;
    }

    public String getHhpCodigoPostal() {
        return HhpCodigoPostal;
    }

    public void setHhpCodigoPostal(String HhpCodigoPostal) {
        this.HhpCodigoPostal = HhpCodigoPostal;
    }

    public String getHhpEdificio() {
        return HhpEdificio;
    }

    public void setHhpEdificio(String HhpEdificio) {
        this.HhpEdificio = HhpEdificio;
    }

    public String getHhpEstadoUnit() {
        return HhpEstadoUnit;
    }

    public void setHhpEstadoUnit(String HhpEstadoUnit) {
        this.HhpEstadoUnit = HhpEstadoUnit;
    }

    public Date getHhpFechaAudit() {
        return HhpFechaAudit;
    }

    public void setHhpFechaAudit(Date HhpFechaAudit) {
        this.HhpFechaAudit = HhpFechaAudit;
    }

    public String getHhpHeadEnd() {
        return HhpHeadEnd;
    }

    public void setHhpHeadEnd(String HhpHeadEnd) {
        this.HhpHeadEnd = HhpHeadEnd;
    }

    public String getHhpTipo() {
        return HhpTipo;
    }

    public void setHhpTipo(String HhpTipo) {
        this.HhpTipo = HhpTipo;
    }

    public String getHhpTipoAcomet() {
        return HhpTipoAcomet;
    }

    public void setHhpTipoAcomet(String HhpTipoAcomet) {
        this.HhpTipoAcomet = HhpTipoAcomet;
    }

    public String getHhpTipoCblAcometida() {
        return HhpTipoCblAcometida;
    }

    public void setHhpTipoCblAcometida(String HhpTipoCblAcometida) {
        this.HhpTipoCblAcometida = HhpTipoCblAcometida;
    }

    public String getHhpTipoUnidad() {
        return HhpTipoUnidad;
    }

    public void setHhpTipoUnidad(String HhpTipoUnidad) {
        this.HhpTipoUnidad = HhpTipoUnidad;
    }

    public String getHhpUltUbicacion() {
        return HhpUltUbicacion;
    }

    public void setHhpUltUbicacion(String HhpUltUbicacion) {
        this.HhpUltUbicacion = HhpUltUbicacion;
    }

    public String getHhpVendedor() {
        return HhpVendedor;
    }

    public void setHhpVendedor(String HhpVendedor) {
        this.HhpVendedor = HhpVendedor;
    }

    public String getHhpNotasAdd1() {
        return HhpNotasAdd1;
    }

    public void setHhpNotasAdd1(String HhpNotasAdd1) {
        this.HhpNotasAdd1 = HhpNotasAdd1;
    }

    public String getHhpNotasAdd2() {
        return HhpNotasAdd2;
    }

    public void setHhpNotasAdd2(String HhpNotasAdd2) {
        this.HhpNotasAdd2 = HhpNotasAdd2;
    }

    public String getHhpNotasAdd3() {
        return HhpNotasAdd3;
    }

    public void setHhpNotasAdd3(String HhpNotasAdd3) {
        this.HhpNotasAdd3 = HhpNotasAdd3;
    }

    public String getHhpNotasAdd4() {
        return HhpNotasAdd4;
    }

    public void setHhpNotasAdd4(String HhpNotasAdd4) {
        this.HhpNotasAdd4 = HhpNotasAdd4;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getOtId() {
        return otId;
    }

    public void setOtId(String otId) {
        this.otId = otId;
    }

    public CmtSubEdificioMgl getHhppSubEdificioObj() {
        return hhppSubEdificioObj;
    }

    public void setHhppSubEdificioObj(CmtSubEdificioMgl hhppSubEdificioObj) {
        this.hhppSubEdificioObj = hhppSubEdificioObj;
    }

    public DireccionMgl getDireccionObj() {
        return direccionObj;
    }

    public void setDireccionObj(DireccionMgl direccionObj) {
        this.direccionObj = direccionObj;
    }

    public SubDireccionMgl getSubDireccionObj() {
        return SubDireccionObj;
    }

    public void setSubDireccionObj(SubDireccionMgl SubDireccionObj) {
        this.SubDireccionObj = SubDireccionObj;
    }

    public OrdenTrabajoVO getOrdenTrabajoVO() {
        return ordenTrabajoVO;
    }

    public void setOrdenTrabajoVO(OrdenTrabajoVO ordenTrabajoVO) {
        this.ordenTrabajoVO = ordenTrabajoVO;
    }

    public List<HhppAuditoriaMgl> getListAuditoria() {
        return listAuditoria;
    }

    public void setListAuditoria(List<HhppAuditoriaMgl> listAuditoria) {
        this.listAuditoria = listAuditoria;
    }

    public void getHhpApart(String ValidadorDireccionHHPP) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getSuscriptor() {
        return suscriptor;
    }

    public void setSuscriptor(String suscriptor) {
        this.suscriptor = suscriptor;
    }

    public CmtTecnologiaSubMgl getCmtTecnologiaSubId() {
        return cmtTecnologiaSubId;
    }

    public void setCmtTecnologiaSubId(CmtTecnologiaSubMgl cmtTecnologiaSubId) {
        this.cmtTecnologiaSubId = cmtTecnologiaSubId;
    }

    public List<MarcasHhppMgl> getListMarcasHhpp() {
        return listMarcasHhpp;
    }

    public void setListMarcasHhpp(List<MarcasHhppMgl> listMarcasHhpp) {
        this.listMarcasHhpp = listMarcasHhpp;
    }

    public BigDecimal getCfm() {
        return cfm;
    }

    public void setCfm(BigDecimal cfm) {
        this.cfm = cfm;
    }

    public int getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(int estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getNombreCuenta() {
        return nombreCuenta;
    }

    public void setNombreCuenta(String nombreCuenta) {
        this.nombreCuenta = nombreCuenta;
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

    public String getMarker() {
        return marker;
    }

    public void setMarker(String marker) {
        this.marker = marker;
    }

    public String getBarrioHhpp() {
        return barrioHhpp;
    }

    public void setBarrioHhpp(String barrioHhpp) {
        this.barrioHhpp = barrioHhpp;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    @PostUpdate
    private void notificarCambioEstado() {
        CmtAvisoProgramadoMglManager capmm = new CmtAvisoProgramadoMglManager();
        capmm.verificarCambio(this);
    }

    public String getHhppSCProcesado() {
        return hhppSCProcesado;
    }

    public void setHhppSCProcesado(String hhppSCProcesado) {
        this.hhppSCProcesado = hhppSCProcesado;
    }

    public String getDireccionDetalladaHhpp() {
        return direccionDetalladaHhpp;
    }

    public void setDireccionDetalladaHhpp(String direccionDetalladaHhpp) {
        this.direccionDetalladaHhpp = direccionDetalladaHhpp;
    }

    public String getEstratoActual() {
        return estratoActual;
    }

    public void setEstratoActual(String estratoActual) {
        this.estratoActual = estratoActual;
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

    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }

    public String getHhppOrigenFicha() {
        return hhppOrigenFicha;
    }

    public void setHhppOrigenFicha(String hhppOrigenFicha) {
        this.hhppOrigenFicha = hhppOrigenFicha;
    }

    public String getNap() {
        return nap;
    }

    public void setNap(String nap) {
        this.nap = nap;
    }

    public String getEstadoOt() {
        return estadoOt;
    }

    public void setEstadoOt(String estadoOt) {
        this.estadoOt = estadoOt;
    }

    public boolean isHhppExistente() {
        return hhppExistente;
    }

    public void setHhppExistente(boolean hhppExistente) {
        this.hhppExistente = hhppExistente;
    }

    public HhppMgl getHhppMgl() {
        return hhppMgl;
    }

    public void setHhppMgl(HhppMgl hhppMgl) {
        this.hhppMgl = hhppMgl;
    }

    public MarcasHhppMgl getMarcasHhppMgl() {
        return marcasHhppMgl;
    }

    public void setMarcasHhppMgl(MarcasHhppMgl marcasHhppMgl) {
        this.marcasHhppMgl = marcasHhppMgl;
    }

    public MarcasMgl getEtiquetaHhpp() {
        return etiquetaHhpp;
    }

    public void setEtiquetaHhpp(MarcasMgl etiquetaHhpp) {
        this.etiquetaHhpp = etiquetaHhpp;
    }

    public boolean isIsNodoCertificado() {
        return isNodoCertificado;
    }

    public void setIsNodoCertificado(boolean isNodoCertificado) {
        this.isNodoCertificado = isNodoCertificado;
    }

    public boolean isDetalleHhppFact() {
        return isDetalleHhppFact;
    }

    public void setDetalleHhppFact(boolean isDetalleHhppFact) {
        this.isDetalleHhppFact = isDetalleHhppFact;
    }

    @Override
    public HhppMgl clone() throws CloneNotSupportedException {
        return (HhppMgl) super.clone();
    }

}
