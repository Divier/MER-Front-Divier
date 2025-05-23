/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.ValidacionParametrizadaTipoValidacionMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Juan David Hernandez
 */
public interface ValidacionParametrizadaTipoValidacionMglFacadeLocal extends BaseFacadeLocal<ValidacionParametrizadaTipoValidacionMgl>{
    
    public void setUser(String mUser, int mPerfil) throws ApplicationException;

    /**
     * Obtiene el listado de validaciones por tipo de la base de datos
     *
     * @param tipoValidacion tipo de validacion que se desea obtener.
     * @author Juan David Hernandez
     * @return Listado de validaciones
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public List<ValidacionParametrizadaTipoValidacionMgl> findValidacionParametrizadaByTipo
            (BigDecimal tipoValidacion) throws ApplicationException;
    
    @Override
     public ValidacionParametrizadaTipoValidacionMgl update
             (ValidacionParametrizadaTipoValidacionMgl t) throws ApplicationException;
    
}
