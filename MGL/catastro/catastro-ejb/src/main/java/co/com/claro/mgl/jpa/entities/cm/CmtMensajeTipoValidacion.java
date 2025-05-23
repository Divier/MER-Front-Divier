package co.com.claro.mgl.jpa.entities.cm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Representar entidad CMT_MESAJES_TIP_VAL Representa la entidad en donde se
 * almacenan los mensajes de validación. Tabla CMT_MESAJES_TIP_VAL.
 *
 * @author Ricardo Cortés Rodríguez.
 * @version 1.0 revision 11/05/2017.
 */
@Entity
@Table(name = "CMT_MESAJES_TIP_VAL", schema = "MGL_SCHEME")
@NamedQueries({
    @NamedQuery(name = "CmtMensajeTipoValidacion.findByValorTipo", query = "SELECT m FROM CmtMensajeTipoValidacion m WHERE m.idValidacion = :validacion AND m.estadoRegistro =1")})
public class CmtMensajeTipoValidacion implements Serializable {

    /**
     * Identificador y llave primaria de la entidad
     */
    private BigDecimal idMensaje;

    /**
     * Identificador del tipo de validación proveniente de la tabla CMT_BASICA
     */
    private CmtBasicaMgl idValidacion;

    /**
     * Mensaje de validación exitosa
     */
    private String mensajeSi;

    /**
     * Mensaje de validación fallida
     */
    private String mensajeNo;

    /**
     * Mensajes de otros procesos exitosos.
     */
    private String mensajeProcesos;

    /**
     * Mensajes de validaciones que no aplican.
     */
    private String mensajeNa;

    /**
     * Estado del mensaje 1 activo 0 inactivo.
     */
    private int estadoRegistro;

    /**
     * Fecha de creación del mensaje
     */
    private Date fechaCreacion;

    /**
     * Usuario de creación del mensaje
     */
    private String usuarioCreacion;

    /**
     * Perfil quien creo el mensaje
     */
    private int perfilCreacion;

    /**
     * Fecha de modificación del mensaje.
     */
    private Date fechaEdicion;

    /**
     * Usuario quién modificó el mensaje.
     */
    private String usuarioEdicion;

    /**
     * Perfil quién modificó el mensaje
     */
    private int perfilEdicion;

    /**
     * Mensajes para validaciones restringidas.
     */
    private String mensajeRestringido;

    public CmtMensajeTipoValidacion() {
    }

    /**
     * Obtener el id del mensaje. Obtiene el id del mensaje.
     *
     * @author Ricardo Cortés Rodríguez
     * @versión 1.0 revisión 11/05/2017.
     * @return El id del mensaje.
     */
    @Id
    @SequenceGenerator(name = "CmtMensajeTipoValidacion.CmtMensajeTipoValidacionSq", schema = "MGL_SCHEME", sequenceName = "CMT_MESAJES_TIP_VAL_SQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CmtMensajeTipoValidacion.CmtMensajeTipoValidacionSq")
    @Column(name = "ID_MENSAJES_TIP_VAL")
    public BigDecimal getIdMensaje() {
        return idMensaje;
    }

    /**
     * Cambiar el id del mensaje Cambia el id del mensaje.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revisión 11/05/2017.
     * @param idMensaje El id del mensaje.
     */
    public void setIdMensaje(BigDecimal idMensaje) {
        this.idMensaje = idMensaje;
    }

    /**
     * Obtener el id del tipo de validación. Obtiene el id del tipo de
     * validación.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @return El id del tipo de validación.
     */
    @JoinColumn(name = "BASICA_ID_VALIDACION")
    @ManyToOne
    public CmtBasicaMgl getIdValidacion() {
        return idValidacion;
    }

    /**
     * Cambiar el id del tipo de validación. Cambio el id del tipo de
     * validación.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @param idValidacion El id del tipo de validación.
     */
    public void setIdValidacion(CmtBasicaMgl idValidacion) {
        this.idValidacion = idValidacion;
    }

    /**
     * Obtener los mensajes de validación exitosa. Obtiene los mensajes de
     * validación exitosa.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @return Los mensajes de validación exitosa.
     */
    @Column(name = "MENSAJE_SI")
    public String getMensajeSi() {
        return mensajeSi;
    }

    /**
     * Cambiar los mensajes de validación exitosa. Cambia los mensajes de
     * validación exitosa.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @param mensajeSi Los mensajes de validación exitosa.
     */
    public void setMensajeSi(String mensajeSi) {
        this.mensajeSi = mensajeSi;
    }

    /**
     * Obtener los mensajes de una validación negativa. Obtiene los mensajes de
     * una validación negativa.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @return Los mensajes de una validación negativa.
     */
    @Column(name = "MENSAJE_NO")
    public String getMensajeNo() {
        return mensajeNo;
    }

    /**
     * Cambiar Los mensajes de una validación negativa. Cambia Los mensajes de
     * una validación negativa.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @param mensajeNo Los mensajes de una validación negativa.
     */
    public void setMensajeNo(String mensajeNo) {
        this.mensajeNo = mensajeNo;
    }

    /**
     * Obtener los mensajes dados por la ejecución del proceso. Obtiene los
     * mensajes dados por la ejecución del proceso.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @return Los mensajes dados por la ejecución del proceso.
     */
    @Column(name = "MENSAJE_PROCESO")
    public String getMensajeProcesos() {
        return mensajeProcesos;
    }

    /**
     * Cambiar los mensajes dados por la ejecución del proceso. Cambia los
     * mensajes dados por la ejecución del proceso.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @param mensajeProcesos Los mensajes dados por la ejecución del proceso.
     */
    public void setMensajeProcesos(String mensajeProcesos) {
        this.mensajeProcesos = mensajeProcesos;
    }

    /**
     * Obtener los mensajes que no aplican para la validación. Obtiene los
     * mensajes que no aplican para la validación.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @return Los mensajes que no aplican para la validación.
     */
    @Column(name = "MENSAJE_NA")
    public String getMensajeNa() {
        return mensajeNa;
    }

    /**
     * Cambiar los mensajes que no aplican para la validación Cambia los
     * mensajes que no aplican para la validación
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @param mensajeNa Los mensajes que no aplican para la validación
     */
    public void setMensajeNa(String mensajeNa) {
        this.mensajeNa = mensajeNa;
    }

    /**
     * Obtener el estado de los mensajes de tipos de validación. Obtiene el
     * estado de los mensajes de tipos de validación.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @return El estado de los mensajes de tipos de validación.
     */
    @Column(name = "ESTADO_REGISTRO")
    public int getEstadoRegistro() {
        return estadoRegistro;
    }

    /**
     * Cambiar el estado de los mensajes de tipos de validación. Cambia el
     * estado de los mensajes de tipos de validación.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @param estado El estado de los mensajes de tipos de validación.
     */
    public void setEstadoRegistro(int estado) {
        this.estadoRegistro = estado;
    }

    /**
     * Obtener la fecha de creación del registro de mensajes. Obtiene la fecha
     * de creación del registro de mensajes.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @return La fecha de creación del registro de mensajes.
     */
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.DATE)
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * Cambiar la fecha de creación del registro de mensajes. Cambia la fecha de
     * creación del registro de mensajes.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @param fechaCreacion La fecha de creación del registro de mensajes.
     */
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * Obtener el usuario que creo el registro. Obtiene el usuario que creo el
     * registro.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @return El usuario que creo el registro.
     */
    @Column(name = "USUARIO_CREACION")
    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    /**
     * Cambiar el usuario que creo el registro. Cambia el usuario que creo el
     * registro.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @param usuarioCreacion El usuario que creo el registro.
     */
    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    /**
     * obtiene el perfil de creación del registro. obtiene el perfil de creación
     * del registro.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @return El perfil de creación del registro
     */
    @Column(name = "PERFIL_CREACION")
    public int getPerfilCreacion() {
        return perfilCreacion;
    }

    /**
     * Cambiar el perfil de creación del registro Cambia el perfil de creación
     * del registro
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @param perfilCreacion El perfil de creación del registro
     */
    public void setPerfilCreacion(int perfilCreacion) {
        this.perfilCreacion = perfilCreacion;
    }

    /**
     * Obtener la fecha de edicion del registro. Obtiene la fecha de edicion del
     * registro.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @return La fecha de edicion del registro.
     */
    @Column(name = "FECHA_EDICION")
    @Temporal(TemporalType.DATE)
    public Date getFechaEdicion() {
        return fechaEdicion;
    }

    /**
     * Cambiar la fecha de edicion del registro. Cambia la fecha de edicion del
     * registro.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @param fechaEdicion La fecha de edicion del registro.
     */
    public void setFechaEdicion(Date fechaEdicion) {
        this.fechaEdicion = fechaEdicion;
    }

    /**
     * Obtener el usuario de edicion. Obtiene el usuario de edicion.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @return El usuario de edicion.
     */
    @Column(name = "USUARIO_EDICION")
    public String getUsuarioEdicion() {
        return usuarioEdicion;
    }

    /**
     * Cambiar el usuario de edicion. Cambia el usuario de edicion.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @param usuarioEdicion El usuario de edicion.
     */
    public void setUsuarioEdicion(String usuarioEdicion) {
        this.usuarioEdicion = usuarioEdicion;
    }

    /**
     * Obtener el perfil con el cual se realizo la edición. Obtiene el perfil
     * con el cual se realizo la edición.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @return El perfil con el cual se realizo la edición.
     */
    @Column(name = "PERFIL_EDICION")
    public int getPerfilEdicion() {
        return perfilEdicion;
    }

    /**
     * Cambiar el perfil con el cual se realizo la edición. Cambia el perfil con
     * el cual se realizo la edición.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @param perfilEdicion El perfil con el cual se realizo la edición.
     */
    public void setPerfilEdicion(int perfilEdicion) {
        this.perfilEdicion = perfilEdicion;
    }

    /**
     * Obtener el mensaje restringido. Obtiene el mensaje restringido.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @return el mensaje restringido.
     */
    @Column(name = "MENSAJE_RESTRINGIDO")
    public String getMensajeRestringido() {
        return mensajeRestringido;
    }

    /**
     * Cambiar el mensaje restringido. Cambia el mensaje restringido.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @param mensajeRestringido el mensaje restringido.
     */
    public void setMensajeRestringido(String mensajeRestringido) {
        this.mensajeRestringido = mensajeRestringido;
    }

}
