/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.ParametrosMglManager;
import co.com.claro.mgl.dtos.CmtParamentrosComplejosDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.claro.mgl.utils.ClassUtils;
import com.jlcg.db.exept.ExceptionDB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import javax.ejb.Stateless;

/**
 * Implementa la interfaz de parametros.
 * Contiene acceso a la clase de negocio que administra los parametros.
 * 
 * @author Admin
 * @version 1.0 revision 17/05/2017.
 */
@Stateless
public class ParametrosMglFacade implements ParametrosMglFacadeLocal {

    private static final Logger LOGGER = LogManager.getLogger(ParametrosMglFacade.class);

    /**
     * Contiene la clase que administra los parametros.
     */
    private final ParametrosMglManager parametrosMglManager;

    /**
     * Construtor que inicializa el administrado de parametros.
     * 
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 17/05/2017.
     */
    public ParametrosMglFacade() {
        this.parametrosMglManager = new ParametrosMglManager();
    }

    @Override
    public List<ParametrosMgl> findAll() throws ApplicationException {
        return parametrosMglManager.findAll();
    }

    @Override
    public ParametrosMgl create(ParametrosMgl t) throws ApplicationException {
        try {
            return parametrosMglManager.create(t);
        } catch (ApplicationException | ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
        }
        return null;
    }

    @Override
    public ParametrosMgl update(ParametrosMgl t) throws ApplicationException {
        try {
            return parametrosMglManager.update(t);
        } catch (ApplicationException | ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
        }
        return null;
    }

    @Override
    public boolean delete(ParametrosMgl t) throws ApplicationException {
        try {
            return parametrosMglManager.delete(t);
        } catch (ApplicationException | ExceptionDB e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
        }
        return false;
    }

    @Override
    public ParametrosMgl findById(ParametrosMgl sqlData) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ParametrosMgl> findByAcronimo(String acronimo) throws ApplicationException {
        return parametrosMglManager.findByAcronimo(acronimo);
    }
    
    /**
     * Buscar un parametro por el acrónimo
     * 
     * Solicitar al ParametrosMglManager la busqueda del ParametrosMgl 
     * según el nombre del acrónimo.
     * 
     * @author becerraarmr
     * @param acronimo valor a buscar
     * @return ParametrosMgl correspondiente
     * @throws ApplicationException Lanza la excepcion cuando ocurra un error en 
     * a ejecucion de la sentencia.
     */
    @Override
    public ParametrosMgl findParametroMgl(String acronimo) 
            throws ApplicationException {
        return parametrosMglManager.findParametroMgl(acronimo);
    }

    @Override
    public List<CmtParamentrosComplejosDto> findComplejo(String acronimo) throws ApplicationException {
        return parametrosMglManager.findComplejo(acronimo);
    }
    
    @Override
    public CmtParamentrosComplejosDto findComplejoByKey(String acronimo,
            String keyToFind) throws ApplicationException {
        return parametrosMglManager.findComplejoByKey(acronimo,keyToFind);
    }

    @Override
    public ParametrosMgl findByAcronimoName(String nombreParametro) throws ApplicationException {
        return parametrosMglManager.findByAcronimoName(nombreParametro);
    }
    
    /**
     * Buscar lista de parametros por tipo de parametro
     *
     * @author bocanegravm
     * @param tipoParam tipo parametro a buscar
     * @return List<ParametrosMgl>
     * @throws ApplicationException is hay errores en la busqueda en la base de
     * datos
     */
    @Override
    public List<ParametrosMgl> findByTipoParametro(String tipoParam) throws ApplicationException {
        return parametrosMglManager.findByTipoParametro(tipoParam);
    }
    
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
    @Override
    public List<ParametrosMgl> findByAcronimoAndTipoParam(String acronimo, String tipoParam) throws ApplicationException {
        return parametrosMglManager.findByAcronimoAndTipoParam(acronimo, tipoParam);
    }
}
