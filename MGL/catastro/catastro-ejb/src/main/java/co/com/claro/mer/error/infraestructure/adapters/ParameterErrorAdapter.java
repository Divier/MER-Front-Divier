package co.com.claro.mer.error.infraestructure.adapters;

import co.com.claro.mer.error.application.ports.IErrorPort;
import co.com.claro.mer.utils.StringToolUtils;
import co.com.claro.mer.utils.enums.DelimitadorEnum;
import co.com.claro.mer.utils.enums.ParametrosMerEnum;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.ParametrosMerUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Permite consultar los parámetros requeridos para el proceso de notificación
 * de errores en MER
 *
 * @author Gildardo Mora
 * @version 1.0, 2023/10/25
 */
public class ParameterErrorAdapter implements IErrorPort {

    /**
     * Consulta las direcciones de correo destino que recibirán la información del error ocurrido.
     *
     * @return {@link List} Retorna una lista de direcciones de correo electrónico contenidas en el parámetro.
     * @throws ApplicationException Excepción en caso de error al identificar las direcciones de correo electrónico
     * @author Gildardo Mora
     */
    @Override
    public List<String> findAddressEmail() throws ApplicationException {
        String destinatarios = ParametrosMerUtil.findValor(ParametrosMerEnum.DESTINATARIOS_NOTIFICAR_ERROR_MER);
        return StringToolUtils.convertStringToList(destinatarios, DelimitadorEnum.PUNTO_Y_COMA, true);
    }

    /**
     * Consulta la bandera que indica si se envían correos de notificación de errores
     *
     * @return {@code boolean} Retorna true si se encuentra activa a notificación de errores.
     * @author Gildardo Mora
     */
    @Override
    public boolean isNotifyErrorActive() throws ApplicationException {
        String flagNotify = ParametrosMerUtil.findValor(ParametrosMerEnum.FLAG_NOTIFICAR_ERRORES_MER);
        return StringUtils.isNotBlank(flagNotify) && flagNotify.trim().equals("1");
    }

}
