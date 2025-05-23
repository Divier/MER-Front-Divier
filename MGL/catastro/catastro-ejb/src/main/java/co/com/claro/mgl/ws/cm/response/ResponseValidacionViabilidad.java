package co.com.claro.mgl.ws.cm.response;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Mostrar olos datos de la validacion viabilidad.
 * Permite mostrar la Validación de la viabilidad de tal forma que puede mostrar
 * el resultado, los mensajes y si se presenta errores lo muestra con su 
 * respectivo mensaje.
 * 
 * @author becerrararmr.
 * @version 1.0 revision 17/05/2017.
 */
@XmlRootElement(name = "ResponseValidacionViabilidad")
@XmlAccessorType(XmlAccessType.FIELD)
public class ResponseValidacionViabilidad {
    
    /**
     * Muestra si el resultado es válido o no.
     */
    @XmlElement(name = "ResultadoValidacion")
    private boolean resultadoValidacion;
    
    /**
     * Muestra el listado de ResponseValidacionViabilidadMensaje.
     */
    @XmlElementWrapper(name="Mensajes")
    @XmlElement(name="Mensaje")
    private List<ResponseValidacionViabilidadMensaje> mensajes=null;
    
    /**
     * Muestra el valor de la variable codRespuesta.
     */
    @XmlElement(name = "codRespuesta")
    private String codRespuesta;
    
    /**
     * Muestra el valor de la variable mensajeRespuesta.
     */
    @XmlElement(name = "mensajeRespuesta")
    private String mensajeRespuesta;
    
        /**
     * Muestra el valor de la variable mensajeRespuesta.
     */
    @XmlElement(name = "nombreProyecto")
    private String nombreProyecto;
    

    /**
     * Construye una instancia de ResponseValidacionViabilidad.
     * Construye una instancia de ResponseValidacionViabilidad.
     * 
     * @author becerrararmr.
     * @version 1.0 revision 17/05/2017.
     */
    public ResponseValidacionViabilidad() {
    }
    /**
     * Adhiere una instancia ResponseValidacionViabilidadMensaje.
     * Adhiere una instancia ResponseValidacionViabilidadMensaje al listado
     * creado.
     * 
     * @author becerrararmr.
     * @version 1.0 revision 17/05/2017.
     * @param tabla Valor que representa el nombre de Tipo Basica
     * @param codigo Valor del código de 
     * @param descripcion Descripcion de la basica
     * @param viabilidad Validación Y/P/N/NA
     * @param mensaje valor que describe la validación
     * @return 
     */
    public boolean addMensaje(String tabla, String codigo, 
            String descripcion, String viabilidad, String mensaje){
        if(mensajes==null){
            mensajes=new ArrayList<ResponseValidacionViabilidadMensaje>();            
        }
        return mensajes.add(new ResponseValidacionViabilidadMensaje(tabla, codigo, 
                descripcion, viabilidad, mensaje));
    }

    /**
     * Obtener el resultado de la validacion.
     * Muestra si el resultado es válido o no.
     * 
     * @author becerrararmr.
     * @version 1.0 revision 17/05/2017.
     * @return el valor boolean de la variable.
     */
    public boolean isResultadoValidacion() {
        return resultadoValidacion;
    }

    /**
     * Cambiar el resultado de la validacion.
     * Actualiza el valor de la variable.
     * 
     * @author becerrararmr.
     * @version 1.0 revision 17/05/2017.
     * @param resultadoValidacion valor nuevo para actualizar el valor de la 
     *  variable.
     */
    public void setResultadoValidacion(boolean resultadoValidacion) {
        this.resultadoValidacion = resultadoValidacion;
    }

    /**
     * Obtener los mensajes.
     * Muestra el listado de ResponseValidacionViabilidadMensaje. 
     * 
     * @author becerrararmr.
     * @version 1.0 revision 17/05/2017.
     * @return un List con las instancias que contiene.
     */
    public List<ResponseValidacionViabilidadMensaje> getMensajes() {
        return mensajes;
    }

    /**
     * Cambiar los mensajes.
     * Actualiza el listado de mensajes de la validación de viabilidad.
     * 
     * @author becerrararmr.
     * @version 1.0 revision 17/05/2017.
     * @param mensajes listado nuevo para actualizar la variable mensajes.
     */
    public void setMensajes(List<ResponseValidacionViabilidadMensaje> mensajes) {
        this.mensajes = mensajes;
    }

    /**
     * Obtener el codigo de respuesta.
     * Muestra el valor de la variable codRespuesta.
     * 
     * @author becerrararmr.
     * @version 1.0 revision 17/05/2017.
     * @return valor String de la variable codRespuesta.
     */
    public String getCodRespuesta() {
        return codRespuesta;
    }

    /**
     * Cambiar el codigo de respuesta.
     * Actualiza el valor de la variable codRespuesta.
     * 
     * @author becerrararmr.
     * @version 1.0 revision 17/05/2017.
     * @param codRespuesta valor String para actualizar la variable.
     */
    public void setCodRespuesta(String codRespuesta) {
        this.codRespuesta = codRespuesta;
    }

    /**
     * Obtener el mensaje de respuesta.
     * Muestra el valor de la variable mensajeRespuesta.
     * 
     * @author becerrararmr.
     * @version 1.0 revision 17/05/2017.
     * @return el valor String de la variable
     */
    public String getMensajeRespuesta() {
        return mensajeRespuesta;
    }

    /**
     * Cambiar el mensaje de respuesta.
     * Actualiza el valor de la variable mensajeRespuesta.
     * 
     * @author becerrararmr.
     * @version 1.0 revision 17/05/2017.
     * @param mensajeRespuesta valor String para actualizar la variable.
     */
    public void setMensajeRespuesta(String mensajeRespuesta) {
        this.mensajeRespuesta = mensajeRespuesta;
    }    
    
    public String getNombreProyecto() {
        return nombreProyecto;
    }

    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }
    
    
    
}