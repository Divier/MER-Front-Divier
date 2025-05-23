package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoValidacionMgl;

/**
 * Contener los proyectos y tipos de validacion.
 * Contiene los proyectos y tipos de validacion.
 * 
 * @author becerraarmr
 * @version 1.0 revision 17/05/2017.
 */
public class ValidacionBasicaParteDto {
    
    /**
     * Proyecto de viabilidad.
     */
    private CmtBasicaMgl basicaProyecto;

    /**
     * Representa la entidad de basicas.
     */
    private CmtBasicaMgl basica;
    
    /**
     * Tipo de validacion de viabilidad.
     */
    private CmtTipoValidacionMgl tipoValidacion;
    
    /**
     * Viabilidad de la regla.
     */
    private String viabilidad;
    
    /**
     * Mensaje de la regla.
     */
    private String mensaje;

    /**
     * Construr el dto.
     * Constructor para inicializar las variables del dto.
     * 
     * @author becerrararmr.
     * @version 1.0 revision 17/05/2017.
     * @param basicaProyecto Proyecto de viabilidad.
     * @param basica La basica de viabilidad.
     */
    public ValidacionBasicaParteDto(CmtBasicaMgl basicaProyecto, 
            CmtBasicaMgl basica) {
        this.basicaProyecto = basicaProyecto;
        this.basica = basica;
    }
    
    /**
     * Obtener el proyecto de viabilidad.
     * Obtiene el proyecto de viabilidad.
     * 
     * @author becerrararmr.
     * @version 1.0 revision 17/05/2017.
     * @return El proyecto de viabilidad.
     */
    public CmtBasicaMgl getBasicaProyecto() {
        return basicaProyecto;
    }

    /**
     * Cambiar el proyecto de viabilidad.
     * Cambiar el proyecto de viabilidad.
     * 
     * @author becerrararmr.
     * @version 1.0 revision 17/05/2017.
     * @param basicaProyecto El proyecto de viabilidad.
     */
    public void setBasicaProyecto(CmtBasicaMgl basicaProyecto) {
        this.basicaProyecto = basicaProyecto;
    }

    /**
     * Obtener la entidad de basicas.
     * Obtiene la entidad de basicas.
     * 
     * @author becerrararmr.
     * @version 1.0 revision 17/05/2017.
     * @return La entidad de basicas.
     */
    public CmtBasicaMgl getBasica() {
        return basica;
    }

    /**
     * Cambiar la entidad de basicas.
     * Cambia la entidad de basicas.
     * 
     * @author becerrararmr.
     * @version 1.0 revision 17/05/2017.
     * @param basica La entidad de basicas.
     */
    public void setBasica(CmtBasicaMgl basica) {
        this.basica = basica;
    }

    /**
     * Obtener el tipo de validacion de viabilidad.
     * Obtiene el tipo de validacion de viabilidad.
     * 
     * @author becerrararmr.
     * @version 1.0 revision 17/05/2017.
     * @return el tipo de validacion de viabilidad.
     */
    public CmtTipoValidacionMgl getTipoValidacion() {
        return tipoValidacion;
    }

    /**
     * Cambiar el tipo de validacion de viabilidad.
     * Cambia el tipo de validacion de viabilidad.
     * 
     * @author becerrararmr.
     * @version 1.0 revision 17/05/2017.
     * @param tipoValidacion El tipo de validacion de viabilidad.
     */
    public void setTipoValidacion(CmtTipoValidacionMgl tipoValidacion) {
        this.tipoValidacion = tipoValidacion;
    }

    /**
     * Obtener la viabilidad de la regla.
     * Obtiene la viabilidad de la regla.
     * 
     * @author becerrararmr.
     * @version 1.0 revision 17/05/2017.
     * @return La viabilidad de la regla.
     */
    public String getViabilidad() {
        return viabilidad;
    }

    /**
     * Cambiar la viabilidad de la regla.
     * Cambia la viabilidad de la regla.
     * 
     * @author becerrararmr.
     * @version 1.0 revision 17/05/2017.
     * @param viabilidad La viabilidad de la regla.
     */
    public void setViabilidad(String viabilidad) {
        this.viabilidad = viabilidad;
    }

    /**
     * 
     * @return the mensaje
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * Obtener el mensaje de la regla.
     * Obtiene el mensaje de la regla.
     * 
     * @author becerrararmr.
     * @version 1.0 revision 17/05/2017.
     * @param mensaje El mensaje de la regla.
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    
    
    
    
}
