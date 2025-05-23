package co.com.claro.mgl.ws.cm.request;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Ingresar datos para una validacion de viabilidadRequestValidacionViabilidad.
 * Un RequestValidacionViabilidad es la encargada de representar la forma como
 * ingresan datos para dar una validación de viabilidad.
 *
 * @author becerrararmr.
 * @version 1.0 revision 17/05/2017.
 */
@XmlRootElement(name = "RequestValidacionViabilidad")
public class RequestValidacionViabilidad {

    /**
     * Muestra el valor de la variable comunidad.
     */
    @XmlElement(name = "comunidad", required = true)
    private String comunidad;
    
    /**
     * Muestra el valor de la variable direccion.
     */
    @XmlElement(name = "direccion", required = true)
    private String direccion;
    /**
     * Muestra el valor de la variable tipoDireccion.
     */
    @XmlElement(name = "tipoDireccion", required = true)
    private String tipoDireccion;

    /**
     * Muestra el valor de la variable calle.
     */
    @XmlElement(name = "calle", required = true)
    private String calle;
    
    /**
     * Muestra el valor de la variable unidad.
     */
    @XmlElement(name = "unidad", required = true)//unidad similar a casa
    private String unidad;
    
    /**
     * Muestra el valor de la variable apartamento.
     */
    @XmlElement(name = "apartamento", required = true)
    private String apartamento;
    
    /**
     * Muestra el valor de la variable segmento.
     */
    @XmlElement(name = "segmento", required = true)
    private String segmento;
    
    /**
     * Muestra el valor de la variable proyecto.
     */
    @XmlElement(name = "proyecto", required = true)
    private String proyecto;
    
    /**
     * Muestra el valor de la variable barrio.
     */
    @XmlElement(name = "barrio", required = false)
    private String barrio;

    /**
     * Constructor por defecto.
     * Constructor por defecto.
     * 
     * @author becerrararmr.
     * @version 1.0 revision 17/05/2017.
     */
    public RequestValidacionViabilidad() {
    }

    /**
     * Constrir el <code>RequestValidacionViabilidad</code>.
     * Construye <code>RequestValidacionViabilidad</code> con los valores de los
     * parámetros ingresados.
     *
     * @author becerrararmr.
     * @version 1.0 revision 17/05/2017.
     * @param comunidad dato String para comunidad.
     * @param calle dato String para calle.
     * @param unidad dato String para unidad.
     * @param apartamento dato string para apartamento.
     * @param segmento dato string para segmento.
     * @param proyecto dato string para proyecto.
     */
    public RequestValidacionViabilidad(String comunidad, String calle,
            String unidad, String apartamento, String segmento, String proyecto) {
        this.comunidad = comunidad;
        this.calle = calle;
        this.unidad = unidad;
        this.apartamento = apartamento;
        this.segmento = segmento;
        this.proyecto = proyecto;
    }

    /**
     * Obtener la comunidad.
     * Muestra el valor de la variable comunidad.
     *
     * @author becerrararmr.
     * @version 1.0 revision 17/05/2017.
     * @return un valor String de la variable comunidad.
     */
    public String getComunidad() {
        return comunidad;
    }

    /**
     * Cambiar la comunidad.
     * Actualiza el valor de la variable comunidad.
     *
     * @author becerrararmr.
     * @version 1.0 revision 17/05/2017.
     * @param comunidad Valor nuevo en String de la comunidad.
     */
    public void setComunidad(String comunidad) {
        this.comunidad = comunidad;
    }

    /**
     * Obtener la calle.
     * Muestra el valor de la variable calle.
     *
     * @author becerrararmr.
     * @version 1.0 revision 17/05/2017.
     * @return dato en String de la variable calle.
     */
    public String getCalle() {
        return calle;
    }

    /**
     * Cambiar la calle.
     * Actualiza el valor de la variable calle.
     *
     * @author becerrararmr.
     * @version 1.0 revision 17/05/2017.
     * @param calle valor nuevo en String para actualizar la vaiable calle.
     */
    public void setCalle(String calle) {
        this.calle = calle;
    }

    /**
     * Obtener la unidad.
     * Muestra el valor de la variable unidad.
     *
     * @author becerrararmr.
     * @version 1.0 revision 17/05/2017.
     * @return el valor en String de la variable unidad.
     */
    public String getUnidad() {
        return unidad;
    }

    /**
     * Cambiar la unidad.
     * Actualiza el valor de la variable unidad.
     *
     * @author becerrararmr.
     * @version 1.0 revision 17/05/2017.
     * @param unidad valor nuevo en String para la variable Unidad
     */
    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    /**
     * Obtener el apartamento.
     * Muestra el valor de la variable apartamento.
     *
     * @author becerrararmr.
     * @version 1.0 revision 17/05/2017.
     * @return el valor de la variable en String.
     */
    public String getApartamento() {
        return apartamento;
    }

    /**
     * Cambiar el apartamento.
     * Actualiza el valor de la varible apartamento
     *
     * @author becerrararmr.
     * @version 1.0 revision 17/05/2017.
     * @param apartamento valor nuevo en String para actualizar la variable
     * apartamento.
     */
    public void setApartamento(String apartamento) {
        this.apartamento = apartamento;
    }

    /**
     * Obtener el segmento.
     * Muestra el valor de la variable segmento.
     *
     * @author becerrararmr.
     * @version 1.0 revision 17/05/2017.
     * @return el valor en String de la variable.
     */
    public String getSegmento() {
        return segmento;
    }

    /**
     * Cambiar el segmento.
     * Actualiza el valor de la variable segmento.
     *
     * @author becerrararmr.
     * @version 1.0 revision 17/05/2017.
     * @param segmento valor nuevo en String para la variable segmento.
     */
    public void setSegmento(String segmento) {
        this.segmento = segmento;
    }

    /**
     * Obtener el proyecto.
     * Muestra el valor de la variable proyecto.
     *
     * @author becerrararmr.
     * @version 1.0 revision 17/05/2017.
     * @return el valor en String de la variable.
     */
    public String getProyecto() {
        return proyecto;
    }

    /**
     * Cambiar el proyecto.
     * Actualiza el valor de la variable proyecto.
     *
     * @author becerrararmr.
     * @version 1.0 revision 17/05/2017.
     * @param proyecto valor nuevo en String para la variable proyecto.
     */
    public void setProyecto(String proyecto) {
        this.proyecto = proyecto;
    }

    /**
     * Obtener el barrio.
     * Muestra el valor de la variable barrio.
     *
     * @author becerrararmr.
     * @version 1.0 revision 17/05/2017.
     * @return el valor en String de la variable.
     */
    public String getBarrio() {
        return barrio;
    }

    /**
     * Cambiar el barrio.
     * Actualiza el valor de la variable barrio.
     *
     * @author becerrararmr.
     * @version 1.0 revision 17/05/2017.
     * @param barrio valor nuevo en String para la variable barrio.
     */
    public void setBarrio(String barrio) {
        this.barrio = barrio;
    }
    
    /**
     * Obtener la Direccion.
     * Muestra el valor de la variable barrio.
     *
     * @author ortizjaf.
     * @version 1.0 revision 01/08/2017.
     * @return el valor en String de la variable.
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Cambiar la Direccion.Actualiza el valor de la variable barrio.
     *
     * @author ortizjaf.
     * @version 1.0 revision 01/08/2017.
     * @param direccion
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    /**
     * Obtener el tipo de Direccion.
     * Muestra el valor de la variable barrio.
     *
     * @author ortizjaf.
     * @version 1.0 revision 01/08/2017.
     * @return el valor en String de la variable.
     */
    public String getTipoDireccion() {
        return tipoDireccion;
    }

    /**
     * Cambiar el tipo de Direccion.Actualiza el valor de la variable barrio.
     *
     * @author ortizjaf.
     * @version 1.0 revision 01/08/2017.
     * @param tipoDireccion
     */
    public void setTipoDireccion(String tipoDireccion) {
        this.tipoDireccion = tipoDireccion;
    }
    

    /**
     * Verificar los valores de los campos.
     * Verifica si los campos tienen valores.
     *
     * @author becerrararmr.
     * @version 1.0 revision 17/05/2017.
     * @return true si hay valores en todos los campos y false en caso
     * contrario.
     */
    public boolean hayCamposConValores() {
        String[] fields = {apartamento, calle, comunidad, segmento, unidad,
            proyecto,direccion,tipoDireccion};

        for (String campo : fields) {
            if (campo == null) {
                return false;
            }
            if (campo.trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }
}