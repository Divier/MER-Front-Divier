package co.com.claro.mer.estadoxflujo.facade;

import co.com.claro.mer.estadoxflujo.dao.IEstadoFlujoDao;
import co.com.claro.mgl.error.ApplicationException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.math.BigDecimal;
import java.util.List;

/**
 * Facade para la gestión de los estados de flujo.
 *
 * @author Gildardo Mora
 * @version 1.0, 2025/05/30
 */
@Stateless
public class EstadoFlujoFacade implements EstadoFlujoFacadeLocal {

    @EJB
    private IEstadoFlujoDao estadoFlujoDao;

    /**
     * Obtiene los estados de flujo del usuario.
     *
     * @return Una lista de identificadores de estados de flujo asociados al usuario actual.
     * @throws ApplicationException Si ocurre un error al consultar los estados de flujo.
     * @author Gildardo Mora
     */
    @Override
    public List<BigDecimal> findEstadoFlujoUsuario() throws ApplicationException {
        return estadoFlujoDao.findEstadoFlujoUsuario();
    }

}
