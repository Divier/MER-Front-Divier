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
* Entidad mapeo tabla CMT_TIPO_VALIDACION.
* Ofrece acceso a atributos encapsulados de la entidad.
* 
* @author ortizjaf.
* @versión 1.0 revision 16/05/2017.
* @see Serializable.
*/

@Entity
@Table(name = "CMT_VALIDACION_PROYECTO", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmtValidacionProyectoMgl.findAll", query = "SELECT c FROM CmtValidacionProyectoMgl c"),
    @NamedQuery(name = "CmtValidacionProyectoMgl.findByProyectoAndValidacion", query = "SELECT c FROM CmtValidacionProyectoMgl c WHERE c.tipoBasicaProyectoId = :proyecto AND c.tipoBasicaValidacionId = :validacion AND c.estadoRegistro = 1")})
public class CmtValidacionProyectoMgl implements Serializable{
    
    /**
     * Constante de serial de la clase.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Identificador de validacion proyecto.
     */
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "CmtValidacionProyectoMgl.CmtValidacionProyectoSq",
            sequenceName = "MGL_SCHEME.CMT_VALIDACION_PROYECTO_SQ",
            allocationSize = 1)
    @GeneratedValue(generator =
            "CmtValidacionProyectoMgl.CmtValidacionProyectoSq")
    @Column(name = "VALIDACION_PROYECTO_ID")
    private BigDecimal idValidacionProyecto;
    
    /**
     * Identificador de tipo basica.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_PROYECTO")
    private CmtBasicaMgl tipoBasicaProyectoId;
    
    /**
     * Identificador de tipo de validacion.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASICA_ID_VALIDACION")
    private CmtBasicaMgl tipoBasicaValidacionId;
    
    /**
     * Valor de viabilidad.
     */
    @Column(name = "VIABILIDAD", nullable = true, length = 2)
    private String viabilidad; 
    
    /**
     * Fecha de creacion temporal de los registros.
     */
    @Column(name = "FECHA_CREACION", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    
    /**
     * Usuario que creo el registro.
     */
    @Column(name = "USUARIO_CREACION", nullable = true, length = 200)
    private String usuarioCreacion;
    
    /**
     * Perfil que creo el registro.
     */
    @Column(name = "PERFIL_CREACION")
    private int perfilCreacion;
    
    /**
     * Fecha de edicion del registro.
     */
    @Column(name = "FECHA_EDICION", nullable = true)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaEdicion;
    
    /**
     * Usuario de edicion del registro.
     */
    @Column(name = "USUARIO_EDICION", nullable = true, length = 200)
    private String usuarioEdicion;
    
    /**
     * Perfil de edicion del registro.
     */
    @Column(name = "PERFIL_EDICION")
    private int perfilEdicion;
    
    /**
     * Estado de registro 1 activo 0 inactivo.
     */
    @NotNull
    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro;

    /**
     * Obtener el identificador de validacion proyecto.
     * Obtiene el identificador de validacion proyecto.
     * 
     * @author Jhoimer Rios.
     * @version 1.0 revision 16/05/2017.
     * @return El identificador de validacion proyecto.
     */
    public BigDecimal getIdValidacionProyecto() {
        return idValidacionProyecto;
    }

    /**
     * Cambiar el identificador de validacion proyecto.
     * Cambia el identificador de validacion proyecto.
     * 
     * @author Jhoimer Rios.
     * @version 1.0 revision 16/05/2017.
     * @param idValidacionProyecto El identificador de validacion proyecto.
     */
    public void setIdValidacionProyecto(BigDecimal idValidacionProyecto) {
        this.idValidacionProyecto = idValidacionProyecto;
    }

    /**
     * Obtener el identificador de tipo basica.
     * Obtiene el identificador de tipo basica.
     * 
     * @author Jhoimer Rios.
     * @version 1.0 revision 16/05/2017.
     * @return El identificador de tipo basica.
     */
    public CmtBasicaMgl getTipoBasicaProyectoId() {
        return tipoBasicaProyectoId;
    }

    /**
     * Cambiar el identificador de tipo basica.
     * Cambia el identificador de tipo basica.
     * 
     * @author Jhoimer Rios.
     * @version 1.0 revision 16/05/2017.
     * @param tipoBasicaProyectoId El identificador de tipo basica.
     */
    public void setTipoBasicaProyectoId(CmtBasicaMgl tipoBasicaProyectoId) {
        this.tipoBasicaProyectoId = tipoBasicaProyectoId;
    }

    /**
     * Obtener el identificador de tipo de validacion.
     * Obtiene el identificador de tipo de validacion.
     * 
     * @author Jhoimer Rios.
     * @version 1.0 revision 16/05/2017.
     * @return El identificador de tipo de validacion.
     */
    public CmtBasicaMgl getTipoBasicaValidacionId() {
        return tipoBasicaValidacionId;
    }

    /**
     * Cambiar el identificador de tipo de validacion.
     * Cambia el identificador de tipo de validacion.
     * 
     * @author Jhoimer Rios.
     * @version 1.0 revision 16/05/2017.
     * @param tipoBasicaValidacionId El identificador de tipo de validacion.
     */
    public void setTipoBasicaValidacionId(CmtBasicaMgl tipoBasicaValidacionId) {
        this.tipoBasicaValidacionId = tipoBasicaValidacionId;
    }

    /**
     * Obtener el valor de viabilidad.
     * Obtiene el valor de viabilidad.
     * 
     * @author Jhoimer Rios.
     * @version 1.0 revision 16/05/2017.
     * @return El valor de viabilidad.
     */
    public String getViabilidad() {
        return viabilidad;
    }

    /**
     * Cambiar el valor de viabilidad.
     * Cambia el valor de viabilidad.
     * 
     * @author Jhoimer Rios.
     * @version 1.0 revision 16/05/2017.
     * @param viabilidad El valor de viabilidad.
     */
    public void setViabilidad(String viabilidad) {
        this.viabilidad = viabilidad;
    }

    /**
     * Obtener la fecha de creacion temporal de los registros.
     * Obtiene la fecha de creacion temporal de los registros.
     * 
     * @author Jhoimer Rios.
     * @version 1.0 revision 16/05/2017.
     * @return La fecha de creacion temporal de los registros.
     */
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * Cambiar la fecha de creacion temporal de los registros.
     * Cambia la fecha de creacion temporal de los registros.
     * 
     * @author Jhoimer Rios.
     * @version 1.0 revision 16/05/2017.
     * @param fechaCreacion La fecha de creacion temporal de los registros.
     */
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * Obtener el usuario que creo el registro.
     * Obtiene el usuario que creo el registro.
     * 
     * @author Jhoimer Rios.
     * @version 1.0 revision 16/05/2017.
     * @return El usuario que creo el registro.
     */
    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    /**
     * Cambiar el usuario que creo el registro.
     * Cambia el usuario que creo el registro.
     * 
     * @author Jhoimer Rios.
     * @version 1.0 revision 16/05/2017.
     * @param usuarioCreacion El usuario que creo el registro.
     */
    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    /**
     * Obtener el perfil que creo el registro.
     * Obtiene el perfil que creo el registro.
     * 
     * @author Jhoimer Rios.
     * @version 1.0 revision 16/05/2017.
     * @return El perfil que creo el registro.
     */
    public int getPerfilCreacion() {
        return perfilCreacion;
    }

    /**
     * Cambiar el perfil que creo el registro.
     * Cambia el perfil que creo el registro.
     * 
     * @author Jhoimer Rios.
     * @version 1.0 revision 16/05/2017.
     * @param perfilCreacion El perfil que creo el registro.
     */
    public void setPerfilCreacion(int perfilCreacion) {
        this.perfilCreacion = perfilCreacion;
    }

    /**
     * Obtener la fecha de edicion del registro.
     * Obtiene la fecha de edicion del registro.
     * 
     * @author Jhoimer Rios.
     * @version 1.0 revision 16/05/2017.
     * @return La fecha de edicion del registro.
     */
    public Date getFechaEdicion() {
        return fechaEdicion;
    }

    /**
     * Cambiar la fecha de edicion del registro.
     * Cambia la fecha de edicion del registro.
     * 
     * @author Jhoimer Rios.
     * @version 1.0 revision 16/05/2017.
     * @param fechaEdicion La fecha de edicion del registro.
     */
    public void setFechaEdicion(Date fechaEdicion) {
        this.fechaEdicion = fechaEdicion;
    }

    /**
     * Obtener el usuario de edicion del registro.
     * Obtiene el usuario de edicion del registro.
     * 
     * @author Jhoimer Rios.
     * @version 1.0 revision 16/05/2017.
     * @return El usuario de edicion del registro.
     */
    public String getUsuarioEdicion() {
        return usuarioEdicion;
    }

    /**
     * Cambiar el usuario de edicion del registro.
     * Cambia el usuario de edicion del registro.
     * 
     * @author Jhoimer Rios.
     * @version 1.0 revision 16/05/2017.
     * @param usuarioEdicion El usuario de edicion del registro.
     */
    public void setUsuarioEdicion(String usuarioEdicion) {
        this.usuarioEdicion = usuarioEdicion;
    }

    /**
     * Obtener el perfil de edicion del registro.
     * Obtiene el perfil de edicion del registro.
     * 
     * @author Jhoimer Rios.
     * @version 1.0 revision 16/05/2017.
     * @return El perfil de edicion del registro.
     */
    public int getPerfilEdicion() {
        return perfilEdicion;
    }

    /**
     * Cambiar el perfil de edicion del registro.
     * Cambia el perfil de edicion del registro.
     * 
     * @author Jhoimer Rios.
     * @version 1.0 revision 16/05/2017.
     * @param perfilEdicion El perfil de edicion del registro.
     */
    public void setPerfilEdicion(int perfilEdicion) {
        this.perfilEdicion = perfilEdicion;
    }

    /**
     * Obtener el estado de registro 1 activo 0 inactivo.
     * Obtiene el estado de registro 1 activo 0 inactivo.
     * 
     * @author Jhoimer Rios.
     * @version 1.0 revision 16/05/2017.
     * @return El estado de registro 1 activo 0 inactivo.
     */
    public int getEstadoRegistro() {
        return estadoRegistro;
    }

    /**
     * Cambiar el estado de registro 1 activo 0 inactivo.
     * Cambia el estado de registro 1 activo 0 inactivo.
     * 
     * @author Jhoimer Rios.
     * @version 1.0 revision 16/05/2017.
     * @param estadoRegistro El estado de registro 1 activo 0 inactivo.
     */
    public void setEstadoRegistro(int estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

}
