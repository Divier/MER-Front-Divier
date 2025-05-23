package co.com.claro.mer.blockreport.application.ports;

import co.com.claro.mgl.error.ApplicationException;

import java.util.List;

/**
 * Define las operaciones de consulta de parámetros específicos asociados al
 * proceso de envío de correos.
 *
 * @author Gildardo Mora
 * @version 1.0, 2023/10/13
 */
public interface IReportParameterPort {


    /**
     * Consulta las direcciones de correo destino que recibirán la información.
     *
     * @return {@link List} Retorna una lista de direcciones de correo electrónico contenidas en el parámetro.
     * @throws ApplicationException Excepción en caso de error al identificar las direcciones de correo electrónico
     * @author Gildardo Mora
     */
    List<String> findAddressEmail() throws ApplicationException;

    /**
     * Consulta la bandera que indica si se envían correos de generación de reportes
     *
     * @return {@code boolean} Retorna true si se encuentra activa
     * la notificación de generación de reportes.
     * @throws ApplicationException Excepción en caso de no encontrar el parámetro
     * @author Gildardo Mora
     */
    boolean isNotifyReportGenerationActive() throws ApplicationException;

}
