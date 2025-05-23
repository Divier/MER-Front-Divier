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
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entidad mapeo tabla CMT_TIPO_VALIDACION. 
 * Ofrece acceso a atributos encapsulados de la entidad.
 * 
* @author ortizjaf
 * @versión 1.0 revision 16/05/2017.
 * @see Serializable
 */
@Entity
@Table(name = "CMT_TIPO_VALIDACION", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CmtTipoValidacionMgl.findAll", query = "SELECT c FROM CmtTipoValidacionMgl c"),
    @NamedQuery(name = "CmtTipoValidacionMgl.countByTipoValidacionActive", query = "SELECT COUNT(1) FROM CmtTipoValidacionMgl c WHERE c.tipoBasicaObj = :tipoValidacion AND c.estadoRegistro = 1")})
public class CmtTipoValidacionMgl implements Serializable {

    /**
     * Constante de serial de la clase.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Identificador de tipo de validacion.
     */
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "CmtTipoValidacionMgl.CmtTipoValidacionSq",
            sequenceName = "MGL_SCHEME.CMT_TIPO_VALIDACION_SQ",
            allocationSize = 1)
    @GeneratedValue(generator
            = "CmtTipoValidacionMgl.CmtTipoValidacionSq")
    @Column(name = "TIPO_VALIDACION_ID")
    private BigDecimal idTipoValidacion;

    /**
     * Identificador tipo basica.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TIPO_BASICA_ID")
    private CmtTipoBasicaMgl tipoBasicaObj;
    
    /**
     * Nombre cualitativo de la clase.
     */
    @Column(name = "CLASE", nullable = true, length = 20)
    private String clase;
    
    /**
     * Nombre del metodo.
     */
    @Column(name = "METODO", nullable = true, length = 20)
    private String metodo;

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
     * Constructor por defecto.
     * Constructor por defecto.
     * 
     * @author Jhoimer Rios.
     * @version 1.0 revision 16/05/2017.
     */
    public CmtTipoValidacionMgl() {
    }

    /**
     * Obtener el identificador de tipo de validacion.
     * Obtiene el identificador de tipo de validacion.
     * 
     * @author Jhoimer Rios.
     * @version 1.0 revision 16/05/2017.
     * @return El identificador de tipo de validacion.
     */
    public BigDecimal getIdTipoValidacion() {
        return idTipoValidacion;
    }

    /**
     * Cambiar el identificador de tipo de validacion.
     * Cambia el identificador de tipo de validacion.
     * 
     * @author Jhoimer Rios.
     * @version 1.0 revision 16/05/2017.
     * @param idTipoValidacion El identificador de tipo de validacion.
     */
    public void setIdTipoValidacion(BigDecimal idTipoValidacion) {
        this.idTipoValidacion = idTipoValidacion;
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
     * @return el perfil que creo el registro.
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
     * @param estadoRegistro 
     */
    public void setEstadoRegistro(int estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    /**
     * Obtener el nombre cualitativo de la clase.
     * Obtiene el nombre cualitativo de la clase.
     * 
     * @author Jhoimer Rios.
     * @version 1.0 revision 16/05/2017.
     * @return El nombre cualitativo de la clase.
     */
    public String getClase() {
        return clase;
    }

    /**
     * Cambiar el nombre cualitativo de la clase.
     * Cambia el nombre cualitativo de la clase.
     * 
     * @author Jhoimer Rios.
     * @version 1.0 revision 16/05/2017.
     * @param clase El nombre cualitativo de la clase.
     */
    public void setClase(String clase) {
        this.clase = clase;
    }

    /**
     * Obtener el nombre del metodo.
     * Obtiene el nombre del metodo.
     * 
     * @author Jhoimer Rios.
     * @version 1.0 revision 16/05/2017.
     * @return El nombre del metodo.
     */
    public String getMetodo() {
        return metodo;
    }

    /**
     * Cambiar el nombre del metodo.
     * Cambia el nombre del metodo.
     * 
     * @author Jhoimer Rios.
     * @version 1.0 revision 16/05/2017.
     * @param metodo El nombre del metodo.
     */
    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    /**
     * Obtener el identificador tipo basica.
     * Obtiene el identificador tipo basica.
     * 
     * @author Jhoimer Rios.
     * @version 1.0 revision 16/05/2017.
     * @return el identificador tipo basica.
     */
    public CmtTipoBasicaMgl getTipoBasicaObj() {
        return tipoBasicaObj;
    }

    /**
     * Cambiar el identificador tipo basica.
     * Cambia el identificador tipo basica.
     * 
     * @author Jhoimer Rios.
     * @version 1.0 revision 16/05/2017.
     * @param tipoBasicaObj El identificador tipo basica.
     */
    public void setTipoBasicaObj(CmtTipoBasicaMgl tipoBasicaObj) {
        this.tipoBasicaObj = tipoBasicaObj;
    }
    
    
  /**
     * Diligenciar los atributos.
     * Diligencia los atributos clase, metodo y estadoRegistro con valores por 
     * defecto antes de persistirlos.
     * 
     * @author Jhoimer Rios.
     * @version 1.0 revision 16/05/2017.
     */
    @PrePersist
    public void prePersist() {
        this.clase = "N/A";
        this.metodo = "N/A";
        this.estadoRegistro = 1;

    }
    
}
