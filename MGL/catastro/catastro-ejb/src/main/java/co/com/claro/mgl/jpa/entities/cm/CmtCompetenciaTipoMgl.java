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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ortizjaf
 */
@Entity
@Table(name = "CMT_COMPETENCIA_TIPO", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmtCompetenciaTipoMgl.findAll", query = "SELECT c FROM CmtCompetenciaTipoMgl c"),
    @NamedQuery(name = "CmtCompetenciaTipoMgl.findAllActiveItemsOrderNameComp", query = "SELECT c FROM CmtCompetenciaTipoMgl c WHERE c.estadoRegistro = 1 ORDER BY c.proveedorCompetencia.nombreBasica ASC"),
    @NamedQuery(name = "CmtCompetenciaTipoMgl.findAllActiveItems", query = "SELECT c FROM CmtCompetenciaTipoMgl c WHERE c.estadoRegistro = 1 "),
    @NamedQuery(name = "CmtCompetenciaTipoMgl.getCountAllActiveItems", query = "SELECT COUNT(1)FROM CmtCompetenciaTipoMgl c WHERE c.estadoRegistro = 1"),
    @NamedQuery(name = "CmtCompetenciaTipoMgl.getMaxCodigoRr", query = "SELECT MAX( SUBSTRING ( CONCAT ('0000',c.codigoRr),-4,4)) FROM CmtCompetenciaTipoMgl c WHERE c.estadoRegistro = 1")})
public class CmtCompetenciaTipoMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "CmtCompetenciaTipoMgl.CmtCompetenciaTipoMglSq",
            sequenceName = "MGL_SCHEME.CMT_COMPETENCA_TIPO_SQ", allocationSize = 1)
    @GeneratedValue(generator = "CmtCompetenciaTipoMgl.CmtCompetenciaTipoMglSq")
    @Column(name = "COMPETENCIA_TIPO_ID", nullable = false)
    private BigDecimal competenciaTipoId;
    
    @Column(name = "CODIGO_RR", nullable = true, length = 4)
    private String codigoRr;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_PROV_COMPETENCIA")
    private CmtBasicaMgl proveedorCompetencia;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_SERV_COMPETENCIA")
    private CmtBasicaMgl servicioCompetencia;
    
    @Column(name = "FECHA_CREACION", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    
    @Column(name = "USUARIO_CREACION", nullable = true, length = 20)
    private String usuarioCreacion;
    
    @Column(name = "PERFIL_CREACION")
    private int perfilCreacion;
    
    @Column(name = "FECHA_EDICION", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaEdicion;
    
    @Column(name = "USUARIO_EDICION", nullable = true, length = 20)
    private String usuarioEdicion;
    
    @Column(name = "PERFIL_EDICION")
    private int perfilEdicion;
    
    @NotNull
    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro;
    
    @Column(name = "DETALLE", nullable = true, length = 200)
    private String detalle;

    /**
     * @return the competenciaTipoId
     */
    public BigDecimal getCompetenciaTipoId() {
        return competenciaTipoId;
    }

    /**
     * @param competenciaTipoId the competenciaTipoId to set
     */
    public void setCompetenciaTipoId(BigDecimal competenciaTipoId) {
        this.competenciaTipoId = competenciaTipoId;
    }

    /**
     * @return the proveedorCompetencia
     */
    public CmtBasicaMgl getProveedorCompetencia() {
        return proveedorCompetencia;
    }

    /**
     * @param proveedorCompetencia the proveedorCompetencia to set
     */
    public void setProveedorCompetencia(CmtBasicaMgl proveedorCompetencia) {
        this.proveedorCompetencia = proveedorCompetencia;
    }

    /**
     * @return the servicioCompetencia
     */
    public CmtBasicaMgl getServicioCompetencia() {
        return servicioCompetencia;
    }

    /**
     * @param servicioCompetencia the servicioCompetencia to set
     */
    public void setServicioCompetencia(CmtBasicaMgl servicioCompetencia) {
        this.servicioCompetencia = servicioCompetencia;
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



    @Override
    public String toString() {
        return ""+servicioCompetencia;
    }
}
