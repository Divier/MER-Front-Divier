/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities.cm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author rodriguezluim
 */
@Entity
@Table(name = "CMT_AVISOS_PROGRAMADO", schema = "MGL_SCHEME")
@XmlRootElement
public class CmtAvisosProgramadosMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(
            name = "CmtAvisosProgramadosMgl.CmtAvisosProgramadosMglSq",
            sequenceName = "MGL_SCHEME.CMT_AVISOS_PROGRAMADO_SQ", allocationSize = 1)
    @GeneratedValue(generator = "CmtAvisosProgramadosMgl.CmtAvisosProgramadosMglSq")


    @Column(name = "AVISO_ID", nullable = false)
    private BigDecimal avisoId;
    
    @Column(name = "TCRM_CASE", nullable = false)
    private String casoTcrmId;
    
    @Column(name = "TECNOLOGIA_HABILITADA_ID")
    private BigDecimal hhppId;
    
    @Column(name = "TECNO_SUBEDIFICIO_ID")
    private BigDecimal tecnologiaSubedificioId;
    
    @Column(name = "SOLICITUD_ID")
    private BigDecimal solicitudId;
    
    @Column(name = "TECNOLOGIA", nullable = false, length = 20)
    private String tecnologia;
    
    @Column(name = "FECHA_CREACION", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    
    @NotNull
    @Column(name = "USUARIO_CREACION", nullable = false, length = 20)
    private String usuarioCreacion;
    
    @NotNull
    @Column(name = "PERFIL_CREACION", nullable = false)
    private int perfilCreacion;
    
    @Column(name = "FECHA_MODIFICACION")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaEdicion;
    
    @Column(name = "USUARIO_MODIFICACION")
    private String usuarioEdicion;
    
    
    @Column(name = "PERFIL_MODIFICACION")
    private int perfilEdicion;
    
  
    @Column(name = "ESTADO_REGISTRO", nullable = false)
    private int estadoRegistro;
    
    @Column(name = "ORDEN_TRABAJO_ID")
    private BigDecimal otHhppId;

    public BigDecimal getSolicitudId() {
        return solicitudId;
    }

    public void setSolicitudId(BigDecimal solicitudId) {
        this.solicitudId = solicitudId;
    }

    public BigDecimal getAvisoId() {
        return avisoId;
    }

    public void setAvisoId(BigDecimal avisoId) {
        this.avisoId = avisoId;
    }

    public String getCasoTcrmId() {
        return casoTcrmId;
    }

    public void setCasoTcrmId(String casoTcrmId) {
        this.casoTcrmId = casoTcrmId;
    }

    public BigDecimal getHhppId() {
        return hhppId;
    }

    public void setHhppId(BigDecimal hhppId) {
        this.hhppId = hhppId;
    }

    public BigDecimal getTecnologiaSubedificioId() {
        return tecnologiaSubedificioId;
    }

    public void setTecnologiaSubedificioId(BigDecimal tecnologiaSubedificioId) {
        this.tecnologiaSubedificioId = tecnologiaSubedificioId;
    }

    public String getTecnologia() {
        return tecnologia;
    }

    public void setTecnologia(String tecnologia) {
        this.tecnologia = tecnologia;
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

    public BigDecimal getOtHhppId() {
        return otHhppId;
    }

    public void setOtHhppId(BigDecimal otHhppId) {
        this.otHhppId = otHhppId;
    }
    
}
