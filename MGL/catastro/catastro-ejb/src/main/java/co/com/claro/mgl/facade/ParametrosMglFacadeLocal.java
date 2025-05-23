/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.dtos.CmtParamentrosComplejosDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import java.util.List;
import javax.ejb.Local;

/**
 * Dar comunicacion entre la vista y el modelo o objetos de negocio.
 * Interfaz para comunicar la vista con el modelo o objetos de negocio.
 * 
 * @author Admin
 * @version 1.0 revision 17/05/2017.
 * @see BaseFacadeLocal
 */
@Local
public interface ParametrosMglFacadeLocal extends BaseFacadeLocal<ParametrosMgl> {

    /**
     * Buscar un parametro por el acronico.
     * 
     * @author User.
     * @version 1.0 revision 17/05/2017.
     * @param acronimo Criterio de busqueda.
     * @return Lista de parametros que correspondan al acronimo dado.
     * @throws ApplicationException Lanza la excepcion cuando ocurra un error en 
     * a ejecucion de la sentencia.
     */
     List<ParametrosMgl> findByAcronimo(String acronimo) 
            throws ApplicationException;

    /**
     * Busca un parametro complejo por el acronimo. 
     * Permite buscar un parametro complejo y retornar la lista de parametros.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 17/05/2017.
     * @param acronimo palabra clave acronimo para buscar el parametro complejo
     * @return parametro complejo buscado por la key
     * @throws ApplicationException Lanza la excepcion cuando ocurra un error en 
     * a ejecucion de la sentencia.
     */
    List<CmtParamentrosComplejosDto> findComplejo(String acronimo)
            throws ApplicationException;

    /**
     * Busca un parametro complejo por Key. 
     * Permite buscar un parametro
     * complejo y retornar solo la Key buscada.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 17/05/2017.
     * @param acronimo palabra clave acronimo para buscar el parametro complejo
     * @param keyToFind Key buscada dentro del parametro complejo
     * @return parametro complejo buscado por la key
     * @throws ApplicationException Lanza la excepcion cuando ocurra un error en 
     * a ejecucion de la sentencia.
     */
    CmtParamentrosComplejosDto findComplejoByKey(String acronimo, String keyToFind)
            throws ApplicationException;

    /**
     * Buscar un parametro por el acrónimo
     * 
     * Solicitar la busqueda del ParametrosMgl según el nombre del acrónimo.
     * 
     * @author becerraarmr
     * @param acronimo valor a buscar
     * @return ParametrosMgl correspondiente
     * @throws ApplicationException Lanza la excepcion cuando ocurra un error en 
     * a ejecucion de la sentencia.
     */
    ParametrosMgl findParametroMgl(String acronimo) throws ApplicationException;
  
    /**
     * Buscar un parametro por el acrónimo 
     * valbuenayf
     * @param nombreParametro
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    ParametrosMgl findByAcronimoName(String nombreParametro) throws ApplicationException;
    
     /**
     * Buscar lista de parametros por tipo de parametro
     *
     * @author bocanegravm
     * @param tipoParam tipo parametro a buscar
     * @return List<ParametrosMgl>
     * @throws ApplicationException is hay errores en la busqueda en la base de
     * datos
     */
    List<ParametrosMgl> findByTipoParametro(String tipoParam) throws ApplicationException;

    /**
     * Buscar lista de parametros por tipo de parametro y acronimo
     *
     * @author bocanegravm
     * @param acronimo a buscar
     * @param tipoParam tipo parametro a buscar
     * @return List<ParametrosMgl>
     * @throws ApplicationException is hay errores en la busqueda en la base de
     * datos
     */
    List<ParametrosMgl> findByAcronimoAndTipoParam(String acronimo, String tipoParam) throws ApplicationException;
}
