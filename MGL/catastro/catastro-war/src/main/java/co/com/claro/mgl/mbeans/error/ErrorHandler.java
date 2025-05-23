/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.error;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.util.Map;

/**
 * Managed Bean encargado del Manejo de Excepciones en p&aacute;ginas de Error.
 *
 * @author Camilo Miranda (<i>mirandaca</i>).
 */
@ManagedBean
@RequestScoped
public class ErrorHandler {
    
    /** N&uacute;mero m&aacute;ximo de caracteres que podr&aacute; 
     * contener los mensajes en la pantalla de error. */
    private final int MAX_MESSAGE_LENGHT = 300;
    
    
    /**
     * Obtiene el valor de la propiedad especificada del mapa del contexto Faces.
     * 
     * @param mapPropertyName Nombre de la propiedad que se desea obtener.
     * @return Map property value.
     */
    private String geRequestMapProperty(String mapPropertyName) {
        String val = null;
        Map<String, Object> facesMap = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
        if (facesMap != null && !facesMap.isEmpty()) {
            Object o = facesMap.get(mapPropertyName);
            if (o != null) {
                val = o.toString();
            }
        }
        
        if (mapPropertyName.equals("javax.servlet.error.message")) {
            if (val != null) {
                // Eliminar las etiquetas HTML.
                String strRegEx = "<[^>]*>";
                val = val.replaceAll(strRegEx, "");
            }
        }
        
        if (val != null && val.length() > MAX_MESSAGE_LENGHT) {
            if (mapPropertyName.equals("javax.servlet.error.message")) {
                if (val.contains("NullPointerException")) {
                    val = "java.lang.NullPointerException";
                } else {
                    // recortar el texto, para una mejor visualizacion.
                    val = val.substring(0, MAX_MESSAGE_LENGHT) + "...";
                }
            } else {
                // recortar el texto, para una mejor visualizacion.
                val = val.substring(0, MAX_MESSAGE_LENGHT) + "...";
            }
        }
        
        return val;
    }
    
    
    /**
     * Obtiene el c&oacute;digo de Error.
     * 
     * @return Status Code (javax.servlet.error.status_code).
     */
    public String getStatusCode() {
        return ( this.geRequestMapProperty("javax.servlet.error.status_code") );
    }
    
    
    /**
     * Obtiene el mensaje de la Excepci&oacute;n / Error.
     * 
     * @return Message (javax.servlet.error.message).
     */
    public String getMessage() {
        return ( this.geRequestMapProperty("javax.servlet.error.message") );
    }
    
    
    /**
     * Obtiene el Tipo de Excepci&oacute;n.
     * 
     * @return Exception Type (javax.servlet.error.exception_type).
     */
    public String getExceptionType() {
        return ( this.geRequestMapProperty("javax.servlet.error.exception_type") );
    }
    
    
    /**
     * Obtiene el nombre de la Excepci&ocute;n.
     * 
     * @return Exception (javax.servlet.error.exception).
     */
    public String getException() {
        return ( this.geRequestMapProperty("javax.servlet.error.exception") );
    }
    
    
    /**
     * Obtiene el Request URL.
     * @return Request URL (javax.servlet.error.request_uri).
     */
    public String getRequestURI() {
        return ( this.geRequestMapProperty("javax.servlet.error.request_uri") );
    }
    
    
    /**
     * Obtiene el nombre del Servlet.
     * 
     * @return Servlet Name (javax.servlet.error.servlet_name).
     */
    public String getServletName() {
        return ( this.geRequestMapProperty("javax.servlet.error.servlet_name") );
    }

}
