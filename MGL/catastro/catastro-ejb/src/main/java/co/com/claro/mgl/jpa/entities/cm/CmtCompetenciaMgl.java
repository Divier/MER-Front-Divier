/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities.cm;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entidad CmtCompetenciaMgl. Mapeo de la tabla CMT_COMPETENCA
 *
 * @author Johnnatan Ortiz
 * @versión 1.00.0000
 */
@Entity
@Table(name = "CMT_COMPETENCIA", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmtCompetenciaMgl.findAll", query = "SELECT c FROM CmtCompetenciaMgl c"),
    @NamedQuery(name = "CmtCompetenciaMgl.findBySubEdificio", query = "SELECT c FROM CmtCompetenciaMgl c WHERE c.subEdificioObj = :subEdificio AND c.estadoRegistro = 1"),
    @NamedQuery(name = "CmtCompetenciaMgl.getCountBySubEdificio", query = "SELECT COUNT(1) FROM CmtCompetenciaMgl c WHERE c.subEdificioObj = :subEdificio AND c.estadoRegistro = 1")})
public class CmtCompetenciaMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "CmtCompetenciaMgl.CmtCompetenciaMglSq",
            sequenceName = "MGL_SCHEME.CMT_COMPETENCA_SQ", allocationSize = 1)
    @GeneratedValue(generator = "CmtCompetenciaMgl.CmtCompetenciaMglSq")
    @Column(name = "COMPETENCIA_ID", nullable = false)
    private BigDecimal competenciaId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SUBEDIFICIO_ID")
    private CmtSubEdificioMgl subEdificioObj;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COMPETENCIA_TIPO_ID")
    private CmtCompetenciaTipoMgl competenciaTipo;
    
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
    
    @Column(name = "DETALLE", nullable = true, length = 200)
    private String detalle;
    
    @OneToMany(mappedBy = "competenciaIdObj", fetch = FetchType.LAZY)
    private List<CmtCompetenciaAuditoriaMgl> listAuditoria;

    /**
     * @return the competenciaId
     */
    public BigDecimal getCompetenciaId() {
        return competenciaId;
    }

    /**
     * @param competenciaId the competenciaId to set
     */
    public void setCompetenciaId(BigDecimal competenciaId) {
        this.competenciaId = competenciaId;
    }

    /**
     * @return the subEdificioObj
     */
    public CmtSubEdificioMgl getSubEdificioObj() {
        return subEdificioObj;
    }

    /**
     * @param subEdificioObj the subEdificioObj to set
     */
    public void setSubEdificioObj(CmtSubEdificioMgl subEdificioObj) {
        this.subEdificioObj = subEdificioObj;
    }

    /**
     * @return the competenciaTipo
     */
    public CmtCompetenciaTipoMgl getCompetenciaTipo() {
        return competenciaTipo;
    }

    /**
     * @param competenciaTipo the competenciaTipo to set
     */
    public void setCompetenciaTipo(CmtCompetenciaTipoMgl competenciaTipo) {
        this.competenciaTipo = competenciaTipo;
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
        return detalle;
    }

    /**
     * @param detalle the detalle to set
     */
    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }


    @Override
    public String toString() {
        return "CmtCompetenciaMgl{" + "competenciaId=" + competenciaId + ", subEdificioObj=" + subEdificioObj + ", competenciaTipo=" + competenciaTipo + ", fechaCreacion=" + fechaCreacion + ", usuarioCreacion=" + usuarioCreacion + ", perfilCreacion=" + perfilCreacion + ", fechaEdicion=" + fechaEdicion + ", usuarioEdicion=" + usuarioEdicion + ", perfilEdicion=" + perfilEdicion + ", estadoRegistro=" + estadoRegistro + ", detalle=" + detalle + '}';
    }
    
    

    public List<CmtCompetenciaAuditoriaMgl> getListAuditoria() {
        return listAuditoria;
    }

    public void setListAuditoria(List<CmtCompetenciaAuditoriaMgl> listAuditoria) {
        this.listAuditoria = listAuditoria;
    }
    
}