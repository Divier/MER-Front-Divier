package co.com.claro.mgl.dtos;

import co.com.claro.mgl.jpa.entities.cm.CmtTipoValidacionMgl;

/**
 * Accesar datos de la entidad CmtTipoValidacionMgl.
 * Dto de la clase CmtTipoValidacionMgl.
 *
 * @author Johnnatan Ortiz
 * @version 1.0 revision 17/05/2017.
 */
public class CmtTipoValidacionDto {
    
    /**
     * Representa la entidad de tipo de validacion.
     */
    private CmtTipoValidacionMgl tipoValidacion;
    
    /**
     * Valor de la validacion.
     */
    private String valueValidacion;

    /**
     * Obtener el tipo de validacion.
     * Obtiene el tipo de validacion.
     * 
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 17/05/2017.
     * @return El tipo de validacion.
     */
    public CmtTipoValidacionMgl getTipoValidacion() {
        return tipoValidacion;
    }

    /**
     * Obtener el tipo de validacion.
     * Obtiene el tipo de validacion.
     * 
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 17/05/2017.
     * @param tipoValidacion El tipo de validacion.
     */
    public void setTipoValidacion(CmtTipoValidacionMgl tipoValidacion) {
        this.tipoValidacion = tipoValidacion;
    }

    /**
     * Obtener el valor de validacion.
     * Obtiene el valor de validacion.
     * 
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 17/05/2017.
     * @return El valor de validacion.
     */
    public String getValueValidacion() {
        return valueValidacion;
    }

    /**
     * Cambiar el valor de validacion.
     * Cambia el valor de validacion.
     * 
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 17/05/2017.
     * @param valueValidacion El valor de validacion.
     */
    public void setValueValidacion(String valueValidacion) {
        this.valueValidacion = valueValidacion;
    }
}
