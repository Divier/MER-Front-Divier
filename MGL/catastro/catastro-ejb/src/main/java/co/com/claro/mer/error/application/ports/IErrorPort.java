package co.com.claro.mer.error.application.ports;

import co.com.claro.mgl.error.ApplicationException;

import java.util.List;

/**
 * Define las operaciones que requiere obtener externamente
 * para el proceso de notificación de errores ocurridos en MER.
 *
 * @author Gildardo Mora
 * @version 1.0, 2023/10/25
 */
public interface IErrorPort {

    /**
     * Consulta las direcciones de correo destino que recibirán la información del error ocurrido.
     *
     * @return {@link List} Retorna una lista de direcciones de correo electrónico contenidas en el parámetro.
     * @throws ApplicationException Excepción en caso de error al identificar las direcciones de correo electrónico
     * @author Gildardo Mora
     */
    List<String> findAddressEmail() throws ApplicationException;

    /**
     * Consulta la bandera que indica si se envían correos de notificación de errores
     *
     * @return {@code boolean} Retorna true si se encuentra activa a notificación de errores.
     * @author Gildardo Mora
     */
    boolean isNotifyErrorActive() throws ApplicationException;

}
