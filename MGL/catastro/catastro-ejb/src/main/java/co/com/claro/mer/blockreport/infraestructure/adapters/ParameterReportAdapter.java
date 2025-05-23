package co.com.claro.mer.blockreport.infraestructure.adapters;

import co.com.claro.mer.blockreport.application.ports.IReportParameterPort;
import co.com.claro.mer.utils.StringToolUtils;
import co.com.claro.mer.utils.enums.DelimitadorEnum;
import co.com.claro.mer.utils.enums.ParametrosMerEnum;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.ParametrosMerUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Gestiona la consulta de parámetros asociados al bloqueo de generación de reportes
 *
 * @author Gildardo Mora
 * @version 1.0, 2023/10/17
 */
public class ParameterReportAdapter implements IReportParameterPort {

    /**
     * Consulta las direcciones de correo destino que recibirán la información.
     *
     * @return {@link List} Retorna una lista de direcciones de correo electrónico contenidas en el parámetro.
     * @throws ApplicationException Excepción en caso de error al identificar las direcciones de correo electrónico
     * @author Gildardo Mora
     */
    @Override
    public List<String> findAddressEmail() throws ApplicationException {
        String correosBloqueoReportes = ParametrosMerUtil.findValor(ParametrosMerEnum.DESTINATARIOS_CORREO_BLOQUEO_GEN_REPORTES);
        return StringToolUtils.convertStringToList(correosBloqueoReportes, DelimitadorEnum.PUNTO_Y_COMA, true);
    }

    /**
     * Consulta la bandera que indica si se envían correos de generación de reportes
     *
     * @return {@code boolean} Retorna true si se encuentra activa
     * la notificación de generación de reportes.
     * @throws ApplicationException Excepción en caso de no encontrar el parámetro
     * @author Gildardo Mora
     */
    @Override
    public boolean isNotifyReportGenerationActive() throws ApplicationException {
        String flagNotify = ParametrosMerUtil.findValor(ParametrosMerEnum.FLAG_NOTIFICAR_GENERACION_REPORTES);
        return StringUtils.isNotBlank(flagNotify) && flagNotify.trim().equals("1");
    }

}
