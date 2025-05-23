/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities.cm;

import co.com.claro.mgl.jpa.entities.HhppMgl;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bocanegravm
 */
@Entity
@Table(name = "CMT_MOD_CCMM_AUD", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmtModificacionesCcmmAudMgl.findBySolicitud", query =  "SELECT s FROM CmtModificacionesCcmmAudMgl s WHERE s.solicitudCMObj = :solicitudCMObj and  s.estadoRegistro = 1")
})
public class CmtModificacionesCcmmAudMgl implements Serializable {
    
    
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @Column(name = "MOD_CCMM_AUD_ID", nullable = false)
    private BigDecimal modCcmmAudId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SOLICITUD_CM_ID")
    private CmtSolicitudCmMgl solicitudCMObj;
      
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUBEDIFICIO_ID", nullable = false)
    private CmtSubEdificioMgl subEdificioIdObj;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUENTAMATRIZ_ID", nullable = false)
    private CmtCuentaMatrizMgl cuentaMatrizObj;
    
    //CUENTA MATRIZ
    @Basic(optional = false)
    @Column(name = "NOMBRE_SUBEDIFICIO_ANT")
    private String nombreSubedificioAnt;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_TIPO_EDIFICIO_ANT")
    private CmtBasicaMgl tipoEdificioObjAnt;
      
    @Column(name = "TELEFONO_PORTERIA_ANT", nullable = true, length = 200)
    private String telefonoPorteriaAnt;
    
    @Column(name = "TELEFONO_PORTERIA2_ANT", nullable = true, length = 200)
    private String telefonoPorteria2Ant;
    
    @Column(name = "ADMINISTRADOR_ANT", nullable = true, length = 200)
    private String administradorAnt;
  
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPANIA_ID_ADMINISTRACION_ANT") 
    private CmtCompaniaMgl companiaAdministracionObjAnt;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPANIA_ID_ASCENSOR_ANT")
    private CmtCompaniaMgl companiaAscensorObjAnt;
   
    @Basic(optional = false)
    @Column(name = "NOMBRE_SUBEDIFICIO_DES")
    private String nombreSubedificioDes;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_TIPO_EDIFICIO_DES")
    private CmtBasicaMgl tipoEdificioObjDes;
      
    @Column(name = "TELEFONO_PORTERIA_DES", nullable = true, length = 200)
    private String telefonoPorteriaDes;
    
    @Column(name = "TELEFONO_PORTERIA2_DES", nullable = true, length = 200)
    private String telefonoPorteria2Des;
    
    @Column(name = "ADMINISTRADOR_DES", nullable = true, length = 200)
    private String administradorDes;
  
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPANIA_ID_ADMINISTRACION_DES") 
    private CmtCompaniaMgl companiaAdministracionObjDes;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPANIA_ID_ASCENSOR_DES")
    private CmtCompaniaMgl companiaAscensorObjDes;
    
    //SUBEDIFICIO
    
    @Basic(optional = false)
    @Column(name = "FECHA_ENTREGA_EDIFICIO_ANT")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaEntregaEdificioAnt;
    
    @Basic(optional = false)
    @Column(name = "FECHA_ENTREGA_EDIFICIO_DES")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaEntregaEdificioDes;
    
    //DIRECCION
   
    @Basic(optional = false)
    @Column(name = "DIRECCION_CCMM_ANT")
    private String direccionCcmmAnt;
    
    @Basic(optional = false)
    @Column(name = "BARRIO_CCMM_ANT")
    private String barrioCcmmAnt;
            
               
    @Basic(optional = false)
    @Column(name = "DIRECCION_CCMM_DES")
    private String direccionCcmmDes;
    
    @Basic(optional = false)
    @Column(name = "BARRIO_CCMM_DES")
    private String barrioCcmmDes;
    
  
    //HHPP
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TECNOLOGIA_HABILITADA_ID", nullable = false)
    private HhppMgl hhpIdObj;
        
    @Column(name = "APART_ANT")
    private String HhpApartAnt;
    
    @Column(name = "APART_DES")
    private String HhpApartDes;
      
    @Column(name = "FECHA_CREACION", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    
    @Column(name = "USUARIO_CREACION", nullable = true, length = 200)
    private String usuarioCreacion;
    
    @Column(name = "PERFIL_CREACION")
    private int perfilCreacion;
    
    @Column(name = "FECHA_EDICION", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaEdicion;
    
    @Column(name = "USUARIO_EDICION", nullable = true, length = 200)
    private String usuarioEdicion;
    
    @Column(name = "PERFIL_EDICION")
    private int perfilEdicion;
    
    @NotNull
    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro;

    public BigDecimal getModCcmmAudId() {
        return modCcmmAudId;
    }

    public void setModCcmmAudId(BigDecimal modCcmmAudId) {
        this.modCcmmAudId = modCcmmAudId;
    }

    public CmtSolicitudCmMgl getSolicitudCMObj() {
        return solicitudCMObj;
    }

    public void setSolicitudCMObj(CmtSolicitudCmMgl solicitudCMObj) {
        this.solicitudCMObj = solicitudCMObj;
    }

    public CmtSubEdificioMgl getSubEdificioIdObj() {
        return subEdificioIdObj;
    }

    public void setSubEdificioIdObj(CmtSubEdificioMgl subEdificioIdObj) {
        this.subEdificioIdObj = subEdificioIdObj;
    }

    public CmtCuentaMatrizMgl getCuentaMatrizObj() {
        return cuentaMatrizObj;
    }

    public void setCuentaMatrizObj(CmtCuentaMatrizMgl cuentaMatrizObj) {
        this.cuentaMatrizObj = cuentaMatrizObj;
    }

    public String getNombreSubedificioAnt() {
        return nombreSubedificioAnt;
    }

    public void setNombreSubedificioAnt(String nombreSubedificioAnt) {
        this.nombreSubedificioAnt = nombreSubedificioAnt;
    }

    public CmtBasicaMgl getTipoEdificioObjAnt() {
        return tipoEdificioObjAnt;
    }

    public void setTipoEdificioObjAnt(CmtBasicaMgl tipoEdificioObjAnt) {
        this.tipoEdificioObjAnt = tipoEdificioObjAnt;
    }

    public String getTelefonoPorteriaAnt() {
        return telefonoPorteriaAnt;
    }

    public void setTelefonoPorteriaAnt(String telefonoPorteriaAnt) {
        this.telefonoPorteriaAnt = telefonoPorteriaAnt;
    }

    public String getTelefonoPorteria2Ant() {
        return telefonoPorteria2Ant;
    }

    public void setTelefonoPorteria2Ant(String telefonoPorteria2Ant) {
        this.telefonoPorteria2Ant = telefonoPorteria2Ant;
    }

    public String getAdministradorAnt() {
        return administradorAnt;
    }

    public void setAdministradorAnt(String administradorAnt) {
        this.administradorAnt = administradorAnt;
    }

    public CmtCompaniaMgl getCompaniaAdministracionObjAnt() {
        return companiaAdministracionObjAnt;
    }

    public void setCompaniaAdministracionObjAnt(CmtCompaniaMgl companiaAdministracionObjAnt) {
        this.companiaAdministracionObjAnt = companiaAdministracionObjAnt;
    }

    public CmtCompaniaMgl getCompaniaAscensorObjAnt() {
        return companiaAscensorObjAnt;
    }

    public void setCompaniaAscensorObjAnt(CmtCompaniaMgl companiaAscensorObjAnt) {
        this.companiaAscensorObjAnt = companiaAscensorObjAnt;
    }

    public String getNombreSubedificioDes() {
        return nombreSubedificioDes;
    }

    public void setNombreSubedificioDes(String nombreSubedificioDes) {
        this.nombreSubedificioDes = nombreSubedificioDes;
    }

    public CmtBasicaMgl getTipoEdificioObjDes() {
        return tipoEdificioObjDes;
    }

    public void setTipoEdificioObjDes(CmtBasicaMgl tipoEdificioObjDes) {
        this.tipoEdificioObjDes = tipoEdificioObjDes;
    }

    public String getTelefonoPorteriaDes() {
        return telefonoPorteriaDes;
    }

    public void setTelefonoPorteriaDes(String telefonoPorteriaDes) {
        this.telefonoPorteriaDes = telefonoPorteriaDes;
    }

    public String getTelefonoPorteria2Des() {
        return telefonoPorteria2Des;
    }

    public void setTelefonoPorteria2Des(String telefonoPorteria2Des) {
        this.telefonoPorteria2Des = telefonoPorteria2Des;
    }

    public String getAdministradorDes() {
        return administradorDes;
    }

    public void setAdministradorDes(String administradorDes) {
        this.administradorDes = administradorDes;
    }

    public CmtCompaniaMgl getCompaniaAdministracionObjDes() {
        return companiaAdministracionObjDes;
    }

    public void setCompaniaAdministracionObjDes(CmtCompaniaMgl companiaAdministracionObjDes) {
        this.companiaAdministracionObjDes = companiaAdministracionObjDes;
    }

    public CmtCompaniaMgl getCompaniaAscensorObjDes() {
        return companiaAscensorObjDes;
    }

    public void setCompaniaAscensorObjDes(CmtCompaniaMgl companiaAscensorObjDes) {
        this.companiaAscensorObjDes = companiaAscensorObjDes;
    }

    public Date getFechaEntregaEdificioAnt() {
        return fechaEntregaEdificioAnt;
    }

    public void setFechaEntregaEdificioAnt(Date fechaEntregaEdificioAnt) {
        this.fechaEntregaEdificioAnt = fechaEntregaEdificioAnt;
    }

    public Date getFechaEntregaEdificioDes() {
        return fechaEntregaEdificioDes;
    }

    public void setFechaEntregaEdificioDes(Date fechaEntregaEdificioDes) {
        this.fechaEntregaEdificioDes = fechaEntregaEdificioDes;
    }

    public String getDireccionCcmmAnt() {
        return direccionCcmmAnt;
    }

    public void setDireccionCcmmAnt(String direccionCcmmAnt) {
        this.direccionCcmmAnt = direccionCcmmAnt;
    }

    public String getBarrioCcmmAnt() {
        return barrioCcmmAnt;
    }

    public void setBarrioCcmmAnt(String barrioCcmmAnt) {
        this.barrioCcmmAnt = barrioCcmmAnt;
    }

    public String getDireccionCcmmDes() {
        return direccionCcmmDes;
    }

    public void setDireccionCcmmDes(String direccionCcmmDes) {
        this.direccionCcmmDes = direccionCcmmDes;
    }

    public String getBarrioCcmmDes() {
        return barrioCcmmDes;
    }

    public void setBarrioCcmmDes(String barrioCcmmDes) {
        this.barrioCcmmDes = barrioCcmmDes;
    }

    public HhppMgl getHhpIdObj() {
        return hhpIdObj;
    }

    public void setHhpIdObj(HhppMgl hhpIdObj) {
        this.hhpIdObj = hhpIdObj;
    }

    public String getHhpApartAnt() {
        return HhpApartAnt;
    }

    public void setHhpApartAnt(String HhpApartAnt) {
        this.HhpApartAnt = HhpApartAnt;
    }

    public String getHhpApartDes() {
        return HhpApartDes;
    }

    public void setHhpApartDes(String HhpApartDes) {
        this.HhpApartDes = HhpApartDes;
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
    
}
