/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities.cm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bocanegravm
 */
@Entity
@Table(name = "CMT_BACKLOG", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmtBacklogMgl.findAll", query = "SELECT c FROM CmtBacklogMgl c "
            + "WHERE c.estadoRegistro = :estadoRegistro "),
    @NamedQuery(name = "CmtBacklogMgl.findByIdBacklog",
            query = "SELECT c FROM CmtBacklogMgl c WHERE c.idBacklog = :idBacklog  ")
})
public class CmtBacklogMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "CmtBacklogMgl.CmtBacklogSq",
            sequenceName = "MGL_SCHEME.CMT_BACKLOG_SQ",
            allocationSize = 1)
    @GeneratedValue(generator = "CmtBacklogMgl.CmtBacklogSq")
    @Column(name = "BACKLOG_ID")
    private BigDecimal idBacklog;
    
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_TIPO_GESTION_BL")
    private CmtBasicaMgl tipoGesBac;
    
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_RES_GESTION_BL")
    private CmtBasicaMgl resultadoGesBac;
     
    @NotNull
    @Column(name = "NOTA")
    private String nota;
    
    @NotNull
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;
    
    @NotNull
    @Column(name = "PERFIL_CREACION")
    private int perfilCreacion;
    
    @Column(name = "FECHA_EDICION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEdicion;
    
    @Size(min = 1, max = 20)
    @Column(name = "USUARIO_EDICION")
    private String usuarioEdicion;
    
    @Column(name = "PERFIL_EDICION")
    private int perfilEdicion;
    
    @NotNull
    @Column(name = "ESTADO_REGISTRO", columnDefinition = "default 1")
    private int estadoRegistro;
    
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDEN_TRABAJO_ID", referencedColumnName = "OT_ID", nullable = false)
    private CmtOrdenTrabajoMgl ordenTrabajoMglObj;

    public BigDecimal getIdBacklog() {
        return idBacklog;
    }

    public void setIdBacklog(BigDecimal idBacklog) {
        this.idBacklog = idBacklog;
    }

    public CmtBasicaMgl getTipoGesBac() {
        return tipoGesBac;
    }

    public void setTipoGesBac(CmtBasicaMgl tipoGesBac) {
        this.tipoGesBac = tipoGesBac;
    }

    public CmtBasicaMgl getResultadoGesBac() {
        return resultadoGesBac;
    }

    public void setResultadoGesBac(CmtBasicaMgl resultadoGesBac) {
        this.resultadoGesBac = resultadoGesBac;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
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

    public CmtOrdenTrabajoMgl getOrdenTrabajoMglObj() {
        return ordenTrabajoMglObj;
    }

    public void setOrdenTrabajoMglObj(CmtOrdenTrabajoMgl ordenTrabajoMglObj) {
        this.ordenTrabajoMglObj = ordenTrabajoMglObj;
    }
}
