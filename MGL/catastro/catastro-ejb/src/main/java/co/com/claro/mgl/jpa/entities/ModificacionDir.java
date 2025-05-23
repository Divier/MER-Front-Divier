/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author User
 */
@Entity
@Table(name = "TEC_MODIFICACION_TECHABILITADA", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ModificacionDir.findAll", query = "SELECT m FROM ModificacionDir m"),
    @NamedQuery(name = "ModificacionDir.findHhppConflicto", query = "SELECT m FROM ModificacionDir m where m.solicitud = :solicitud AND m.conflictApto = 1"),
    @NamedQuery(name = "ModificacionDir.findByIdSolicitud", query = "SELECT m FROM ModificacionDir m where m.solicitud = :solicitud AND m.conflictApto = 0")})
public class ModificacionDir implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "ModificacionDir.ModificacionDirSeq",
            sequenceName = "MGL_SCHEME.TEC_MODIF_TECHABILITADA_SQ", allocationSize = 1)
    @GeneratedValue(generator = "ModificacionDir.ModificacionDirSeq")
    @Column(name = "MOD_TEC_HABILITADA_ID", nullable = false)
    private BigDecimal id;
    @Column(name = "SOLICITUD_ID", nullable = false)
    private BigDecimal solicitud;
    @Column(name = "OLD_APTO_TEC_HABILITADA")
    private String oldApto;
    @Column(name = "NEW_APTO_TEC_HABILITADA")
    private String newApto;
    @Column(name = "CONFLICT_APTO_TEC_HABILITADA", nullable = true)
    private String conflictApto;   
    @Column(name = "CP_TIPO_NIVEL_5", nullable = true)
    private String cpTipoNivel5 ;
    @Column(name = "CP_TIPO_NIVEL_6", nullable = true)
    private String cpTipoNivel6 ;
    @Column(name = "CP_VALOR_NIVEL_5", nullable = true)
    private String cpValorNivel5;
    @Column(name = "CP_VALOR_NIVEL_6", nullable = true)
    private String cpValorNivel6;
    @Column(name = "TECNOLOGIA_HABILITADA_ID", nullable = true)
    private BigDecimal tecnologiaHabilitadaId;   
    @Column(name = "TEC_HABILITADA_ID_NEW", nullable = true)
    private BigDecimal tecHabilitadaIdNuevaDireccion;     
    @Column(name = "FECHA_CREACION", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)  
    private Date fechaCreacion;
    @Column(name = "USUARIO_CREACION", nullable = false)
    private String usuarioCreacion;
    @Column(name = "PERFIL_CREACION", nullable = false)
    private int perfilCreacion;
    @Column(name = "FECHA_EDICION")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaEdicion;
    @Column(name = "USUARIO_EDICION")
    private String usuarioEdicion;
    @Column(name = "PERFIL_EDICION")
    private int perfilEdicion;
    @Column(name = "ESTADO_REGISTRO", nullable = false)
    private int estadoRegistro;
    
    @Transient
    private boolean selected = false;
    
    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(BigDecimal solicitud) {
        this.solicitud = solicitud;
    }

    public String getOldApto() {
        return oldApto;
    }

    public void setOldApto(String oldApto) {
        this.oldApto = oldApto;
    }

    public String getNewApto() {
        return newApto;
    }

    public void setNewApto(String newApto) {
        this.newApto = newApto;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getConflictApto() {
        return conflictApto;
    }

    public void setConflictApto(String conflictApto) {
        this.conflictApto = conflictApto;
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

    public String getCpTipoNivel5() {
        return cpTipoNivel5;
    }

    public void setCpTipoNivel5(String cpTipoNivel5) {
        this.cpTipoNivel5 = cpTipoNivel5;
    }

    public String getCpTipoNivel6() {
        return cpTipoNivel6;
    }

    public void setCpTipoNivel6(String cpTipoNivel6) {
        this.cpTipoNivel6 = cpTipoNivel6;
    }

    public String getCpValorNivel5() {
        return cpValorNivel5;
    }

    public void setCpValorNivel5(String cpValorNivel5) {
        this.cpValorNivel5 = cpValorNivel5;
    }

    public String getCpValorNivel6() {
        return cpValorNivel6;
    }

    public void setCpValorNivel6(String cpValorNivel6) {
        this.cpValorNivel6 = cpValorNivel6;
    }

    public BigDecimal getTecnologiaHabilitadaId() {
        return tecnologiaHabilitadaId;
    }

    public void setTecnologiaHabilitadaId(BigDecimal tecnologiaHabilitadaId) {
        this.tecnologiaHabilitadaId = tecnologiaHabilitadaId;
    } 
    
    public BigDecimal getTecHabilitadaIdNuevaDireccion() {
        return tecHabilitadaIdNuevaDireccion;
    }
    
    public void setTecHabilitadaIdNuevaDireccion(BigDecimal tecHabilitadaIdNuevaDireccion) {
        this.tecHabilitadaIdNuevaDireccion = tecHabilitadaIdNuevaDireccion;
    }
  
}