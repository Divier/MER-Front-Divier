package co.com.claro.mgl.jpa.entities.cm;

import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "CMT_COMPANIA$AUD", schema = "MGL_SCHEME")
@XmlRootElement
public class CmtCompaniaAuditoriaMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "COMPANIAAUD_ID", nullable = false)
    private BigDecimal companiaAuditoriaId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPANIA_ID", nullable = false)
    private CmtCompaniaMgl companiaObj;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TIPO_COMPANIA_ID")
    private CmtTipoCompaniaMgl tipoCompaniaObj;
    
    @Column(name = "CODIGO_RR")
    private String codigoRr;
    
    @Column(name = "NOMBRE_COMPANIA")
    private String nombreCompania;
    
    @Column(name = "NIT_COMPANIA")
    private String nitCompania;
    
    @Column(name = "PAGINA_WEB")
    private String paginaWeb;
    
    @Column(name = "DIRECCION")
    private String direccion;
    
    @Column(name = "NOMBRE_CONTACTO")
    private String nombreContacto;
    
    @Column(name = "TELEFONOS")
    private String telefonos;
           
    @Column(name = "EMAIL")
    private String email;
    
    @Column(name = "FECHA_CREACION", nullable = true)
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
    
    @Column(name = "DETALLE", nullable = true, length = 200)
    private String Detalle;
    
    @Column(name = "JUSTIFICACION", nullable = true, length = 200)
    private String justificacion;
    
      
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GPO_ID_DEPARTAMENTO", referencedColumnName = "GEOGRAFICO_POLITICO_ID", nullable = false)
    private GeograficoPoliticoMgl departamento;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GPO_ID_CIUDAD", referencedColumnName = "GEOGRAFICO_POLITICO_ID", nullable = false)
    private GeograficoPoliticoMgl municipio;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GPO_ID_CENTRO_POBLADO", referencedColumnName = "GEOGRAFICO_POLITICO_ID", nullable = false)
    private GeograficoPoliticoMgl centroPoblado;
    
    @Column(name = "BARRIO", nullable = true, length = 50)
    private String barrio;
    
    @Column(name = "TIPO_DOCTO", nullable = false, length = 3)
    private String tipoDocumento;
    
    @Column(name = "ABREVIATURA", nullable = true, length = 10)
    private String abreviatura;
    
    @Column(name = "AUD_ACTION", length = 3)
    private String accionAuditoria;
    
    @Column(name = "AUD_TIMESTAMP")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaAuditoria;
    
    @Column(name = "AUD_USER", length = 30)
    private String usuarioAuditoria;
    
    @Column(name = "SESSION_ID")
    private BigDecimal sesionAuditoria;
    
    @Column(name = "TELEFONO2", nullable = true, length = 20)
    private String telefono2;
    
    @Column(name = "TELEFONO3", nullable = true, length = 20)
    private String telefono3;
    
    @Column(name = "TELEFONO4", nullable = true, length = 20)
    private String telefono4;
    
    @Column(name = "COSTO", nullable = true, length = 15)
    private BigDecimal costo;
    
    @Column(name = "ACTIVADO", nullable = true, length = 1)
    private String activado;    
    
    
    
    public BigDecimal getCompaniaAuditoriaId() {
        return companiaAuditoriaId;
    }

    public void setCompaniaAuditoriaId(BigDecimal companiaAuditoriaId) {
        this.companiaAuditoriaId = companiaAuditoriaId;
    }

    /**
     * @return the companiaId
     */
    public CmtCompaniaMgl getCompaniaObj() {
        return companiaObj;
    }

    public void setCompaniaObj(CmtCompaniaMgl companiaObj) {
        this.companiaObj = companiaObj;
    }

    /**
     * @return the tipoCompaniaObj
     */
    public CmtTipoCompaniaMgl getTipoCompaniaObj() {
        return tipoCompaniaObj;
    }

    /**
     * @param tipoCompaniaObj the tipoCompaniaObj to set
     */
    public void setTipoCompaniaObj(CmtTipoCompaniaMgl tipoCompaniaObj) {
        this.tipoCompaniaObj = tipoCompaniaObj;
    }

    /**
     * @return the codigoRr
     */
    public String getCodigoRr() {
        return codigoRr;
    }

    /**
     * @param codigoRr the codigoRr to set
     */
    public void setCodigoRr(String codigoRr) {
        this.codigoRr = codigoRr;
    }

    /**
     * @return the nombreCompania
     */
    public String getNombreCompania() {
        return nombreCompania;
    }

    /**
     * @param nombreCompania the nombreCompania to set
     */
    public void setNombreCompania(String nombreCompania) {
        this.nombreCompania = nombreCompania;
    }

    /**
     * @return the nitCompania
     */
    public String getNitCompania() {
        return nitCompania;
    }

    /**
     * @param nitCompania the nitCompania to set
     */
    public void setNitCompania(String nitCompania) {
        this.nitCompania = nitCompania;
    }

    /**
     * @return the paginaWeb
     */
    public String getPaginaWeb() {
        return paginaWeb;
    }

    /**
     * @param paginaWeb the paginaWeb to set
     */
    public void setPaginaWeb(String paginaWeb) {
        this.paginaWeb = paginaWeb;
    }

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @return the nombreContacto
     */
    public String getNombreContacto() {
        return nombreContacto;
    }

    /**
     * @param nombreContacto the nombreContacto to set
     */
    public void setNombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    /**
     * @return the telefonos
     */
    public String getTelefonos() {
        return telefonos;
    }

    /**
     * @param telefonos the telefonos to set
     */
    public void setTelefonos(String telefonos) {
        this.telefonos = telefonos;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
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
     * @return the detalle
     */
    public String getDetalle() {
        return Detalle;
    }

    /**
     * @param detalle the detalle to set
     */
    public void setDetalle(String detalle) {
        Detalle = detalle;
    }

    /**
     * @return the justificacion
     */
    public String getJustificacion() {
        return justificacion;
    }

    /**
     * @param justificacion the justificacion to set
     */
    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }

    /**
     * @return the departamento
     */
    public GeograficoPoliticoMgl getDepartamento() {
        return departamento;
    }

    /**
     * @param departamento the departamento to set
     */
    public void setDepartamento(GeograficoPoliticoMgl departamento) {
        this.departamento = departamento;
    }

    /**
     * @return the municipio
     */
    public GeograficoPoliticoMgl getMunicipio() {
        return municipio;
    }

    /**
     * @param municipio the municipio to set
     */
    public void setMunicipio(GeograficoPoliticoMgl municipio) {
        this.municipio = municipio;
    }

    /**
     * @return the centroPoblado
     */
    public GeograficoPoliticoMgl getCentroPoblado() {
        return centroPoblado;
    }

    /**
     * @param centroPoblado the centroPoblado to set
     */
    public void setCentroPoblado(GeograficoPoliticoMgl centroPoblado) {
        this.centroPoblado = centroPoblado;
    }

    public String getBarrio() {
        return barrio;
    }

    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
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
    
    public BigDecimal getCompaniaId(){
        return this.companiaObj.getCompaniaId();
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public String getTelefono3() {
        return telefono3;
    }

    public void setTelefono3(String telefono3) {
        this.telefono3 = telefono3;
    }

    public String getTelefono4() {
        return telefono4;
    }

    public void setTelefono4(String telefono4) {
        this.telefono4 = telefono4;
    }

    public BigDecimal getCosto() {
        return costo;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }

    public String getActivado() {
        return activado;
    }

    public void setActivado(String activado) {
        this.activado = activado;
    }
 
}
