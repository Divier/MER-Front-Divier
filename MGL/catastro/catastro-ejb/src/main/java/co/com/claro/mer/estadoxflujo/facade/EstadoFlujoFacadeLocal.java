package co.com.claro.mer.estadoxflujo.facade;

import co.com.claro.mgl.error.ApplicationException;

import java.math.BigDecimal;
import java.util.List;

/**
 * Facade para la gestión de los estados de flujo.
 * Esta interfaz define los métodos para interactuar con los estados de flujo
 * @author Gildardo Mora
 * @version 1.0, 2025/05/30
 */
public interface EstadoFlujoFacadeLocal {

    /**
     * Obtiene los estados de flujo del usuario.
     *
     * @return Una lista de identificadores de estados de flujo asociados al usuario actual.
     * @throws ApplicationException Si ocurre un error al consultar los estados de flujo.
     * @author Gildardo Mora
     */
    List<BigDecimal> findEstadoFlujoUsuario() throws ApplicationException;

}
