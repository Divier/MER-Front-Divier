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
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entidad mapeo tabla CMT_REGLA_VALIDACION.
 * Representa la entidad CMT_REGLA_VALIDACION.
 *
 * @author Johnnatan Ortiz
 * @version 1.0 revision 17/05/2017.
 */
@Entity
@Table(name = "CMT_REGLA_VALIDACION", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmtReglaValidacion.findAll", query = "SELECT r FROM CmtReglaValidacion r"),
    @NamedQuery(name = "CmtReglaValidacion.findByProyecto", query = "SELECT r FROM CmtReglaValidacion r WHERE r.proyecto = :proyecto AND r.estadoRegistro = 1 ")})
public class CmtReglaValidacion implements Serializable{
    
    /**
     * Constante que contiene la serie de la clase.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Identificador de la regla de validacion.
     */
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "CmtReglaValidacion.CmtReglaValidacionSeq",
            sequenceName = "MGL_SCHEME.CMT_REGLA_VALIDACION_SQ", allocationSize = 1)
    @GeneratedValue(generator = "CmtReglaValidacion.CmtReglaValidacionSeq")
    @Column(name = "REGLA_VALIDACION_ID", nullable = false)
    private BigDecimal reglaValidacionId;    
    
    /**
     * Identificador de proyecto.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_PROYECTO")
    private CmtBasicaMgl proyecto;
    
    /**
     * Regla de validacion.
     */
    @Basic(optional = false)
    @Column(name = "REGLA_VALIDACION", length = 200)
    private String reglaValidacion;
    
    /**
     * Estado de la regla de validacion.
     */
    @Basic(optional = false)
    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro;
    
    /**
     * Fecha de creación de la regla de validacion.
     */
    @Column(name = "FECHA_CREACION", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    
    /**
     * Usuario de creacion de la regla de validacion.
     */
    @Column(name = "USUARIO_CREACION", nullable = false, length = 20)
    private String usuarioCreacion;
    
    /**
     * Perfil del usuario de creacion.
     */
    @Column(name = "PERFIL_CREACION", nullable = false)
    private int perfilCreacion;
    
    /**
     * Fecha de edicion de la regla de validacion.
     */
    @Column(name = "FECHA_EDICION", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaEdicion;
    
    /**
     * Usuario de edicion de la regla de validacion.
     */
    @Column(name = "USUARIO_EDICION", nullable = false, length = 20)
    private String usuarioEdicion;
    
    /**
     * Perfil del usuario de edicion.
     */
    @Column(name = "PERFIL_EDICION", nullable = false)
    private int perfilEdicion;

    /**
     * Obtener el identificador de la regla de validacion.
     * Obtiene el identificador de la regla de validacion.
     * 
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 17/05/2017.
     * @return El identificador de la regla de validacion.
     */
    public BigDecimal getReglaValidacionId() {
        return reglaValidacionId;
    }

    /**
     * Cambiar el identificador de la regla de validacion.
     * Cambia el identificador de la regla de validacion.
     * 
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 17/05/2017.
     * @param reglaValidacionId El identificador de la regla de validacion.
     */
    public void setReglaValidacionId(BigDecimal reglaValidacionId) {
        this.reglaValidacionId = reglaValidacionId;
    }

    /**
     * Obtener el identificador de proyecto.
     * Obtiene el identificador de proyecto.
     * 
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 17/05/2017.
     * @return El identificador de proyecto.
     */
    public CmtBasicaMgl getProyecto() {
        return proyecto;
    }

    /**
     * Cambiar el identificador de proyecto.
     * Cambia el identificador de proyecto.
     * 
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 17/05/2017.
     * @param proyecto El identificador de proyecto.
     */
    public void setProyecto(CmtBasicaMgl proyecto) {
        this.proyecto = proyecto;
    }

    /**
     * Obtener la regla de validacion.
     * Obtiene la regla de validacion.
     * 
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 17/05/2017.
     * @return La regla de validacion.
     */
    public String getReglaValidacion() {
        return reglaValidacion;
    }

    /**
     * Cambiar la regla de validacion.
     * Cambia la regla de validacion.
     * 
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 17/05/2017.
     * @param reglaValidacion La regla de validacion.
     */
    public void setReglaValidacion(String reglaValidacion) {
        this.reglaValidacion = reglaValidacion;
    }

    /**
     * Obtener el estado de la regla de validacion.
     * Obtiene el estado de la regla de validacion.
     * 
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 17/05/2017.
     * @return El estado de la regla de validacion.
     */
    public int getEstadoRegistro() {
        return estadoRegistro;
    }

    /**
     * Cambiar el estado de la regla de validacion.
     * Cambia el estado de la regla de validacion.
     * 
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 17/05/2017.
     * @param estadoRegistro El estado de la regla de validacion.
     */
    public void setEstadoRegistro(int estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    /**
     * Obtener la fecha de creación de la regla de validacion.
     * Obtiene la fecha de creación de la regla de validacion.
     * 
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 17/05/2017.
     * @return La fecha de creación de la regla de validacion.
     */
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * Cambiar la fecha de creación de la regla de validacion.
     * Cambia la fecha de creación de la regla de validacion.
     * 
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 17/05/2017.
     * @param fechaCreacion La fecha de creación de la regla de validacion.
     */
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * Obtener el usuario de creacion de la regla de validacion.
     * Obtiene el usuario de creacion de la regla de validacion.
     * 
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 17/05/2017.
     * @return El usuario de creacion de la regla de validacion.
     */
    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    /**
     * Cambiar el usuario de creacion de la regla de validacion.
     * Cambia el usuario de creacion de la regla de validacion.
     * 
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 17/05/2017.
     * @param usuarioCreacion El usuario de creacion de la regla de validacion.
     */
    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    /**
     * Obtener el perfil del usuario de creacion.
     * Obtiene el perfil del usuario de creacion.
     * 
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 17/05/2017.
     * @return El perfil del usuario de creacion.
     */
    public int getPerfilCreacion() {
        return perfilCreacion;
    }

    /**
     * Cambiar el perfil del usuario de creacion.
     * Cambia el perfil del usuario de creacion.
     * 
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 17/05/2017.
     * @param perfilCreacion El perfil del usuario de creacion.
     */
    public void setPerfilCreacion(int perfilCreacion) {
        this.perfilCreacion = perfilCreacion;
    }

    /**
     * Obtener la fecha de edicion de la regla de validacion.
     * Obtiene la fecha de edicion de la regla de validacion.
     * 
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 17/05/2017.
     * @return La fecha de edicion de la regla de validacion.
     */
    public Date getFechaEdicion() {
        return fechaEdicion;
    }

    /**
     * Cambiar la fecha de edicion de la regla de validacion.
     * Cambia la fecha de edicion de la regla de validacion.
     * 
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 17/05/2017.
     * @param fechaEdicion La fecha de edicion de la regla de validacion.
     */
    public void setFechaEdicion(Date fechaEdicion) {
        this.fechaEdicion = fechaEdicion;
    }

    /**
     * Obtener el usuario de edicion de la regla de validacion.
     * Obtener el usuario de edicion de la regla de validacion.
     * 
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 17/05/2017.
     * @return El usuario de edicion de la regla de validacion.
     */
    public String getUsuarioEdicion() {
        return usuarioEdicion;
    }

    /**
     * Cambiar el usuario de edicion de la regla de validacion.
     * Cambia el usuario de edicion de la regla de validacion.
     * 
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 17/05/2017.
     * @param usuarioEdicion El usuario de edicion de la regla de validacion.
     */
    public void setUsuarioEdicion(String usuarioEdicion) {
        this.usuarioEdicion = usuarioEdicion;
    }

    /**
     * Obtener el perfil del usuario de edicion.
     * Obtiene el perfil del usuario de edicion.
     * 
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 17/05/2017.
     * @return El perfil del usuario de edicion.
     */
    public int getPerfilEdicion() {
        return perfilEdicion;
    }

    /**
     * Cambiar el perfil del usuario de edicion.
     * Cambia el perfil del usuario de edicion.
     * 
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 17/05/2017.
     * @param perfilEdicion El perfil del usuario de edicion.
     */
    public void setPerfilEdicion(int perfilEdicion) {
        this.perfilEdicion = perfilEdicion;
    }

}
