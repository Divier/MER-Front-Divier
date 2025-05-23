/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities.cm;

import co.com.claro.mgl.jpa.entities.Solicitud;
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


@Entity
@Table(name = "MGL_SOLICITUD_TIPO_SOLICITUD", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmtSolicitudTipoSolicitudMgl.findAll", query = "SELECT n FROM CmtSolicitudTipoSolicitudMgl n where n.estadoRegistro =:estado"),
    @NamedQuery(name = "CmtSolicitudTipoSolicitudMgl.findByIdSolicitud", query = "SELECT n FROM CmtSolicitudTipoSolicitudMgl n where n.solicitudObj.idSolicitud =:idSolicitud AND n.estadoRegistro=:estado")})
public class CmtSolicitudTipoSolicitudMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(name = "CmtSolicitudTipoSolicitudMgl.CmtSolicitudTipoSolMglSq",
            sequenceName = "MGL_SCHEME.MGL_SOLICITUD_TIPO_SOL_SQ", allocationSize = 1)
    @GeneratedValue(generator = "CmtSolicitudTipoSolicitudMgl.CmtSolicitudTipoSolMglSq")
    @Column(name = "SOLICITUD_TIPO_SOLICITUD_ID", nullable = false)
    private BigDecimal solicitudTipoSolicitudId;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SOL_TEC_HABILITADA_ID", referencedColumnName = "SOL_TEC_HABILITADA_ID", nullable = false)
    private Solicitud solicitudObj;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TIPO_SOLICITUD_ID", referencedColumnName = "TIPO_SOLICITUD_ID", nullable = false)
    private CmtTipoSolicitudMgl tipoSolicitudObj;    
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
    @Column(name = "PERFIL_EDICION",  nullable = true)
    private int perfilEdicion;    
    @NotNull
    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro;        

    public BigDecimal getSolicitudTipoSolicitudId() {
        return solicitudTipoSolicitudId;
    }

    public void setSolicitudTipoSolicitudId(BigDecimal solicitudTipoSolicitudId) {
        this.solicitudTipoSolicitudId = solicitudTipoSolicitudId;
    }

    public Solicitud getSolicitudObj() {
        return solicitudObj;
    }

    public void setSolicitudObj(Solicitud solicitudObj) {
        this.solicitudObj = solicitudObj;
    }

    public CmtTipoSolicitudMgl getTipoSolicitudObj() {
        return tipoSolicitudObj;
    }

    public void setTipoSolicitudObj(CmtTipoSolicitudMgl tipoSolicitudObj) {
        this.tipoSolicitudObj = tipoSolicitudObj;
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

}
