package co.com.claro.mgl.ws.cm.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Representar ResponseValidacionViabilidadMensaje.
 * Un ResponseValidacionViabilidadMensaje representa los valor obtenidos despues
 * de realizar validaciones.
 * Por Ejemplo.
 * <table>
 * <tr>
 * <th>Tabla</th>
 * <th>Codigo</th>
 * <th>Descripción</th>
 * <th>Viabilidad</th>
 * <th>Mensaje</th>
 * </tr>
 * <tr>
 * <td>TIPOS DE RED</td>
 * <td>BID</td>
 * <td>NODO RED BIDIRECCIONAL</td>
 * <td>Y</td>
 * <td>NODO RED BIDIRECCIONAL válido para digitación de venta</td>
 * </tr>
 * </table>
 *
 * @author becerrararmr.
 * @version 1.0 revision 17/05/2017.
 */
@XmlRootElement(name = "ResponseValidacionViabilidadMensaje")
@XmlAccessorType(XmlAccessType.FIELD)
public class ResponseValidacionViabilidadMensaje {
    @XmlAttribute(name = "Tabla")
    private String tabla;
    @XmlAttribute(name = "codigo")
    private String codigo;
    @XmlElement(name = "descripcion")
    private String descripcion;
    @XmlElement(name = "viabilidad")
    private String viabilidad;
    @XmlElement(name = "mensaje")
    private String mensaje;

    /**
     * Constuir la clase.
     * Construye una instancia con los parametros correspondientes
     * 
     * @author becerrararmr.
     * @version 1.0 revision 17/05/2017.
     * @param tabla Nombre de tipo basica en String
     * @param codigo Codigo de basica
     * @param descripcion Descripcion
     * @param viabilidad valor representado Y/N/P/NA
     * @param mensaje mensajes con el valor de la viabilidad
     */
    public ResponseValidacionViabilidadMensaje(String tabla, String codigo, 
            String descripcion, String viabilidad, String mensaje) {
        this.tabla = tabla;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.viabilidad = viabilidad;
        this.mensaje = mensaje;
    }

    /**
     * Construir la instancia por defecto.
     * Construye una instancia de ResponseValidacionViabilidadMensaje.
     * 
     * @author becerrararmr.
     * @version 1.0 revision 17/05/2017.
     */
    public ResponseValidacionViabilidadMensaje( ) {
    }

    /**
     * Obtener la tabla.
     * Muestra el dato de la variable Tabla.
     *
     * @author becerrararmr.
     * @version 1.0 revision 17/05/2017.
     * @return el valor en String de la variable.
     */
    public String getTabla() {
        return tabla;
    }

    /**
     * Cambiar la tabla.
     * Actualiza el dato de la variable Tabla.
     *
     * @author becerrararmr.
     * @version 1.0 revision 17/05/2017.
     * @param tabla nuevo valor String para actualizar la variable.
     */
    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    /**
     * Obtener el codigo.
     * Muestra el valor de la variable codigo.
     *
     * @author becerrararmr.
     * @version 1.0 revision 17/05/2017.
     * @return el valor String de la variable.
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Cambiar el codigo.
     * Actualiza el valor de la variable codigo.
     *
     * @author becerrararmr.
     * @version 1.0 revision 17/05/2017.
     * @param codigo nuevo valor String para actualizar la variable.
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtener la descripcion.
     * Muestra el valor de la variable descripción
     *
     * @author becerrararmr.
     * @version 1.0 revision 17/05/2017.
     * @return el valor String de la variable.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Cambiar la descripcion.
     * Actualizar el valor de la variable descripcion
     *
     * @author becerrararmr.
     * @version 1.0 revision 17/05/2017.
     * @param descripcion nuevo valor String para actualizar la variable.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtener la viabilidad.
     * Muestra el valor de la variable viabilidad.
     *
     * @author becerrararmr.
     * @version 1.0 revision 17/05/2017.
     * @return el valor String de la variable.
     */
    public String getViabilidad() {
        return viabilidad;
    }

    /**
     * Cambiar la viabilidad.
     * Actualiza el valor de la variable viabilidad.
     *
     * @author becerrararmr.
     * @version 1.0 revision 17/05/2017.
     * @param viabilidad nuevo valor String de la variable viabilidad
     */
    public void setViabilidad(String viabilidad) {
        this.viabilidad = viabilidad;
    }

    /**
     * Obtener el mensaje.
     * Muestra el valor de la variable mensaje.
     *
     * @author becerrararmr.
     * @version 1.0 revision 17/05/2017.
     * @return el valor String de la variable.
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * Cambiar el mensaje.
     * Actualiza el valor de la variable mensaje.
     *
     * @author becerrararmr.
     * @version 1.0 revision 17/05/2017.
     * @param mensaje nuevo valor String de la variable.
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
