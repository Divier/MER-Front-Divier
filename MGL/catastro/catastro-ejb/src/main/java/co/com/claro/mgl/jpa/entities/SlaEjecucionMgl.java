/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities;

import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bocanegravm
 */
@Entity
@Table(name = "MGL_SLA_EJECUCION", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SlaEjecucionMgl.findAll", query = "SELECT s FROM SlaEjecucionMgl s WHERE s.estadoRegistro=1"),
    @NamedQuery(name = "SlaEjecucionMgl.findByTecnoAndEjecucion", 
            query = "SELECT s FROM SlaEjecucionMgl s WHERE s.estadoRegistro=1  "
                    + "AND s.basicaIdTecnologia = :basicaIdTecnologia AND s.tipoEjecucion = :tipoEjecucion")    
})
public class SlaEjecucionMgl implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "SlaEjecucionMgl.seq_slaEjecucion",
            sequenceName = "MGL_SCHEME.MGL_SLA_EJECUCION_SQ", allocationSize = 1
    )
    
    @GeneratedValue(generator = "SlaEjecucionMgl.seq_slaEjecucion")
    @Column(name = "SLA_EJECUCION_ID", nullable = false)
    private BigDecimal slaEjecucionId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_TECNOLOGIA", nullable = true)
    private CmtBasicaMgl basicaIdTecnologia;
    
    @Column(name = "TIPO_EJECUCION")
    private String tipoEjecucion;
    
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
    
    
    @Column(name = "PERFIL_CREACION")
    private int perfilCreacion;
    
    
    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro;
    
    
    @Column(name = "PERFIL_EDICION")
    private int perfilEdicion;
    
    @OneToMany(mappedBy = "slaEjecucionMgl", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<DetalleSlaEjecucionMgl> lstDetalleSlaEjecucionMgls;
    
    @OneToMany(mappedBy = "slaEjecucionMgl", fetch = FetchType.LAZY)
    private List<SlaEjecucionAuditoriaMgl> listAuditoria;

    public BigDecimal getSlaEjecucionId() {
        return slaEjecucionId;
    }

    public void setSlaEjecucionId(BigDecimal slaEjecucionId) {
        this.slaEjecucionId = slaEjecucionId;
    }

    public CmtBasicaMgl getBasicaIdTecnologia() {
        return basicaIdTecnologia;
    }

    public void setBasicaIdTecnologia(CmtBasicaMgl basicaIdTecnologia) {
        this.basicaIdTecnologia = basicaIdTecnologia;
    }

    public String getTipoEjecucion() {
        return tipoEjecucion;
    }

    public void setTipoEjecucion(String tipoEjecucion) {
        this.tipoEjecucion = tipoEjecucion;
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

    public int getPerfilCreacion() {
        return perfilCreacion;
    }

    public void setPerfilCreacion(int perfilCreacion) {
        this.perfilCreacion = perfilCreacion;
    }

    public int getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(int estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    public int getPerfilEdicion() {
        return perfilEdicion;
    }

    public void setPerfilEdicion(int perfilEdicion) {
        this.perfilEdicion = perfilEdicion;
    }

    public List<SlaEjecucionAuditoriaMgl> getListAuditoria() {
        return listAuditoria;
    }

    public void setListAuditoria(List<SlaEjecucionAuditoriaMgl> listAuditoria) {
        this.listAuditoria = listAuditoria;
    }

    public List<DetalleSlaEjecucionMgl> getLstDetalleSlaEjecucionMgls() {
        return lstDetalleSlaEjecucionMgls;
    }

    public void setLstDetalleSlaEjecucionMgls(List<DetalleSlaEjecucionMgl> lstDetalleSlaEjecucionMgls) {
        this.lstDetalleSlaEjecucionMgls = lstDetalleSlaEjecucionMgls;
    }
}
