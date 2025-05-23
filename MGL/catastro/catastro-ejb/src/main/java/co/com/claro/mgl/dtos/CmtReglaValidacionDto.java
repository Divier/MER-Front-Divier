package co.com.claro.mgl.dtos;

import co.com.claro.mgl.jpa.entities.cm.CmtReglaValidacion;
import java.util.List;

/**
 * Dto de la clase CmtReglaValidacion.
 * Contiene los datos de la regla de validación y el dto de tipos de validacion.
 *
 * @author Johnnatan Ortiz
 * @versión 1.0 revision 17/05/2017.
 */
public class CmtReglaValidacionDto {
    
    /**
     * Objeto que representa la entidad de reglas de validacion.
     */
    private CmtReglaValidacion reglaValidacion;
    
    /**
     * Lista dto de tipos de validacion.
     */
    private List<CmtTipoValidacionDto> tipoValidacionList;

    /**
     * Obtener la entidad regla de validacion.
     * Obtiene la entidad regla de validacion.
     * 
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 17/05/2017.
     * @return La entidad regla de validacion.
     */
    public CmtReglaValidacion getReglaValidacion() {
        return reglaValidacion;
    }

    /**
     * Cambiar la entidad regla de validacion.
     * Cambia la entidad regla de validacion.
     * 
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 17/05/2017.
     * @param reglaValidacion La entidad regla de validacion.
     */
    public void setReglaValidacion(CmtReglaValidacion reglaValidacion) {
        this.reglaValidacion = reglaValidacion;
    }

    /**
     * Obtener la lista dto de tipos de validacion.
     * Obtiene la lista dto de tipos de validacion.
     * 
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 17/05/2017.
     * @return La lista dto de tipos de validacion.
     */
    public List<CmtTipoValidacionDto> getTipoValidacionList() {
        return tipoValidacionList;
    }

    /**
     * Cambiar la lista dto de tipos de validacion.
     * Cambia la lista dto de tipos de validacion.
     * 
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 17/05/2017.
     * @param tipoValidacionList La lista dto de tipos de validacion.
     */
    public void setTipoValidacionList(
            List<CmtTipoValidacionDto> tipoValidacionList) {
        this.tipoValidacionList = tipoValidacionList;
    }
}
