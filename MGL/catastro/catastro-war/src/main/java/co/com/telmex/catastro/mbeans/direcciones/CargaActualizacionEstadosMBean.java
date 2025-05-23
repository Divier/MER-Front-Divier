package co.com.telmex.catastro.mbeans.direcciones;

import co.com.telmex.catastro.mbeans.comun.BaseMBean;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * Clase CargarActualizacionEstadosMBean
 * Extiende de BaseMBean
 *
 * @author 	Ana María Malambo
 * @version	1.0
 */
@ViewScoped
@ManagedBean(name = "cargaActualizacionEstadosMBean")
public class CargaActualizacionEstadosMBean extends BaseMBean {

    /**
     * 
     */
    protected static final String MSG_ERRROR_LECTURA_ARCHIVO = "ERROR ARCHIVO VACIO.";
    /**
     * 
     */
    protected static final String MSG_ERROR_ESTRUCTURA = "La estructura del archivo no es correcta";
    /**
     * 
     */
    protected static final String MSG_ERRROR_ARCHIVO_TAMANO_MAXIMO = "error_archivo_tamano_maximo";
    /**
     * 
     */
    protected static final String MSG_ERRROR_ARCHIVO_REGISTROS_MAXIMO = "error_archivo_registros_maximo";
    /**
     * 
     */
    protected static final String MSG_ERRROR_ARCHIVO_REGISTROS_VACIO = "el archivo tiene inconsistencias de datos";
}