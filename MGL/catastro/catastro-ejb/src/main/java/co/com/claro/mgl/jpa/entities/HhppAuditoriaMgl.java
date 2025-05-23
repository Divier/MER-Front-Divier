/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities;

import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTecnologiaSubMgl;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "TEC_TECNOLOGIA_HABILITADA$AUD", schema = "MGL_SCHEME")
@XmlRootElement
public class HhppAuditoriaMgl implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @Column(name = "TECNOLOGIA_HABILITADA_AUD_ID", nullable = false)
    private BigDecimal hhpAuditoriaId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TECNOLOGIA_HABILITADA_ID", nullable = false)
    private HhppMgl hhpIdObj;
    
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
    
    @Column(name = "FECHA_CREACION")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date hhpFechaCreacion;
    
    @Column(name = "USUARIO_CREACION")
    private String hhpUsuarioCreacion;
    
    @Column(name = "FECHA_EDICION")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date hhpFechaModificacion;
    
    @Column(name = "USUARIO_EDICION")
    private String hhpUsuarioModificacion;
    
    @Column(name = "ESTADO_ID", nullable = false)
    private String ehhId;
    
    @Column(name = "AUD_ACTION",length = 3)
    private String accionAuditoria;
    
    @Column(name = "AUD_TIMESTAMP")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaAuditoria;
    
    @Column(name = "AUD_USER",length = 30)
    private String usuarioAuditoria;
    
    @Column(name = "SESSION_ID")
    private BigDecimal sesionAuditoria;
    
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
    
    @Column(name = "SUSCRIPTOR")
    private String suscriptor;
    
    @Column(name = "CFM")
    private BigDecimal cfm;
    
    @Column(name = "MARKER")
    private String marker;
    
    @Basic
    @Column(name = "ESTADO_REGISTRO")
    private Integer estadoRegistro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUBEDIFICIO_ID")
    private CmtSubEdificioMgl hhppSubEdificioObj;
    
    @Column(name = "PERFIL_CREACION")
    private BigDecimal perfilCreacion;
    
    @Column(name = "PERFIL_EDICION")
    private BigDecimal perfilEdicion;
    
   @Column(name = "HHPP_SC_PROCESADO_HHPP")
   private String hhppSCProcesado;
   
   @Column(name = "ORIGEN_FICHA")
    private String hhppOrigenFicha;
   
   @Column(name = "NAP")
    private String nap;
   
   @Column(name = "CUENTA_CLIENTE_TRASLADAR")
    private String cuentaClienteTrasladar;
   
   @Column(name = "ID_DIRECCION_HHPP_VIRTUAL_RR")
    private String IdDireccionHhppVirtualRr;
    
    @Transient
    private Date fechaCreacion;
    @Transient
    private String usuarioCreacion;
    @Transient
    private Date fechaEdicion;
    @Transient
    private String usuarioEdicion;
    @Transient
    private String justificacion;

    public BigDecimal getHhpAuditoriaId() {
        return hhpAuditoriaId;
    }

    public void setHhpAuditoriaId(BigDecimal hhpAuditoriaId) {
        this.hhpAuditoriaId = hhpAuditoriaId;
    }

    public HhppMgl getHhpIdObj() {
        return hhpIdObj;
    }

    public void setHhpIdObj(HhppMgl hhpIdObj) {
        this.hhpIdObj = hhpIdObj;
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

    public Date getHhpFechaCreacion() {
        return hhpFechaCreacion;
    }

    public void setHhpFechaCreacion(Date hhpFechaCreacion) {
        this.hhpFechaCreacion = hhpFechaCreacion;
    }

    public String getHhpUsuarioCreacion() {
        return hhpUsuarioCreacion;
    }

    public void setHhpUsuarioCreacion(String hhpUsuarioCreacion) {
        this.hhpUsuarioCreacion = hhpUsuarioCreacion;
    }

    public Date getHhpFechaModificacion() {
        return hhpFechaModificacion;
    }

    public void setHhpFechaModificacion(Date hhpFechaModificacion) {
        this.hhpFechaModificacion = hhpFechaModificacion;
    }

    public String getHhpUsuarioModificacion() {
        return hhpUsuarioModificacion;
    }

    public void setHhpUsuarioModificacion(String hhpUsuarioModificacion) {
        this.hhpUsuarioModificacion = hhpUsuarioModificacion;
    }

    public String getEhhId() {
        return ehhId;
    }

    public void setEhhId(String ehhId) {
        this.ehhId = ehhId;
    }

    public String getAccionAuditoria() {
        return accionAuditoria;
    }

    public void setAccionAuditoria(String accionAuditoria) {
        this.accionAuditoria = accionAuditoria;
    }

    public Date getFechaAuditoria() {
        return fechaAuditoria;
    }

    public void setFechaAuditoria(Date fechaAuditoria) {
        this.fechaAuditoria = fechaAuditoria;
    }

    public String getUsuarioAuditoria() {
        return usuarioAuditoria;
    }

    public void setUsuarioAuditoria(String usuarioAuditoria) {
        this.usuarioAuditoria = usuarioAuditoria;
    }

    public BigDecimal getSesionAuditoria() {
        return sesionAuditoria;
    }

    public void setSesionAuditoria(BigDecimal sesionAuditoria) {
        this.sesionAuditoria = sesionAuditoria;
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

    public String getHhpEstadoUnit() {
        return HhpEstadoUnit;
    }

    public void setHhpEstadoUnit(String HhpEstadoUnit) {
        this.HhpEstadoUnit = HhpEstadoUnit;
    }

    public String getHhpVendedor() {
        return HhpVendedor;
    }

    public void setHhpVendedor(String HhpVendedor) {
        this.HhpVendedor = HhpVendedor;
    }

    public String getHhpCodigoPostal() {
        return HhpCodigoPostal;
    }

    public void setHhpCodigoPostal(String HhpCodigoPostal) {
        this.HhpCodigoPostal = HhpCodigoPostal;
    }

    public String getHhpTipoAcomet() {
        return HhpTipoAcomet;
    }

    public void setHhpTipoAcomet(String HhpTipoAcomet) {
        this.HhpTipoAcomet = HhpTipoAcomet;
    }

    public String getHhpUltUbicacion() {
        return HhpUltUbicacion;
    }

    public void setHhpUltUbicacion(String HhpUltUbicacion) {
        this.HhpUltUbicacion = HhpUltUbicacion;
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

    public String getHhpEdificio() {
        return HhpEdificio;
    }

    public void setHhpEdificio(String HhpEdificio) {
        this.HhpEdificio = HhpEdificio;
    }

    public String getHhpTipoUnidad() {
        return HhpTipoUnidad;
    }

    public void setHhpTipoUnidad(String HhpTipoUnidad) {
        this.HhpTipoUnidad = HhpTipoUnidad;
    }

    public String getHhpTipoCblAcometida() {
        return HhpTipoCblAcometida;
    }

    public void setHhpTipoCblAcometida(String HhpTipoCblAcometida) {
        this.HhpTipoCblAcometida = HhpTipoCblAcometida;
    }

    public Date getHhpFechaAudit() {
        return HhpFechaAudit;
    }

    public void setHhpFechaAudit(Date HhpFechaAudit) {
        this.HhpFechaAudit = HhpFechaAudit;
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

    public CmtSubEdificioMgl getHhppSubEdificioObj() {
        return hhppSubEdificioObj;
    }

    public void setHhppSubEdificioObj(CmtSubEdificioMgl hhppSubEdificioObj) {
        this.hhppSubEdificioObj = hhppSubEdificioObj;
    }
    
    public BigDecimal getHhpId() {
        return hhpIdObj.getHhpId();
    }

    public Date getFechaCreacion() {
        return hhpFechaCreacion;
    }

    public String getUsuarioCreacion() {
        return hhpUsuarioCreacion;
    }

    public Date getFechaEdicion() {
        return hhpFechaModificacion;
    }

    public String getUsuarioEdicion() {
        return hhpUsuarioModificacion;
    }

    public String getSuscriptor() {
        return suscriptor;
    }

    public void setSuscriptor(String suscriptor) {
        this.suscriptor = suscriptor;
    }

    public String getMarker() {
        return marker;
    }

    public void setMarker(String marker) {
        this.marker = marker;
    }

    public int getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(int estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    public CmtTecnologiaSubMgl getCmtTecnologiaSubId() {
        return cmtTecnologiaSubId;
    }

    public void setCmtTecnologiaSubId(CmtTecnologiaSubMgl cmtTecnologiaSubId) {
        this.cmtTecnologiaSubId = cmtTecnologiaSubId;
    }

    public BigDecimal getCfm() {
        return cfm;
    }

    public void setCfm(BigDecimal cfm) {
        this.cfm = cfm;
    } 
    
    public void setEstadoRegistro(Integer estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    public BigDecimal getPerfilCreacion() {
        return perfilCreacion;
    }

    public void setPerfilCreacion(BigDecimal perfilCreacion) {
        this.perfilCreacion = perfilCreacion;
    }

    public BigDecimal getPerfilEdicion() {
        return perfilEdicion;
    }

    public void setPerfilEdicion(BigDecimal perfilEdicion) {
        this.perfilEdicion = perfilEdicion;
    }
    
    public String getHhppSCProcesado() {
        return hhppSCProcesado;
    }

    public void setHhppSCProcesado(String hhppSCProcesado) {
        this.hhppSCProcesado = hhppSCProcesado;
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

    public String getCuentaClienteTrasladar() {
        return cuentaClienteTrasladar;
    }

    public void setCuentaClienteTrasladar(String cuentaClienteTrasladar) {
        this.cuentaClienteTrasladar = cuentaClienteTrasladar;
    }

    public String getIdDireccionHhppVirtualRr() {
        return IdDireccionHhppVirtualRr;
    }

    public void setIdDireccionHhppVirtualRr(String IdDireccionHhppVirtualRr) {
        this.IdDireccionHhppVirtualRr = IdDireccionHhppVirtualRr;
    }
    
    
}
