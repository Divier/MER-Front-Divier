package co.com.claro.mer.blockreport.facade;

import co.com.claro.mer.parametro.facade.ParametroFacadeLocal;
import co.com.claro.mer.utils.SesionUtil;
import co.com.claro.mer.utils.StringToolUtils;
import co.com.claro.mer.utils.enums.DelimitadorEnum;
import co.com.claro.mgl.error.ApplicationException;
import org.apache.commons.lang3.StringUtils;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static co.com.claro.mer.utils.constants.BlockReportConstants.IS_ENABLED_BLOCK_REPORT;
import static co.com.claro.mer.utils.constants.BlockReportConstants.MSG_BLOCK_REPORT;
import static co.com.claro.mer.utils.enums.ParametrosMerEnum.*;

/**
 * Implementación de los casos de uso asociados al proceso de bloqueo de generación de reportes
 * y de exportación de datos en MER.
 *
 * @version 1.0, 2023/09/07
 * @author Gildardo Mora
 */
@Stateless
public class BlockReportFacade implements BlockReportFacadeLocal {

    @EJB
    private ParametroFacadeLocal parametroFacadeLocal;

    /**
     * Realiza la validación para determinar si está permitido generar un reporte
     * o exportar datos de un proceso específico.
     *
     * @param nameReport {@link String} Nombre del reporte o proceso de exportación
     *                                 que se quiere validar.
     * @return {@code Map<String, Object>} Retorna los datos de la validación realizada.
     * @author Gildardo Mora
     */
    @Override
    public Map<String, Object> validateReportGeneration(String nameReport) throws ApplicationException {
        Map<String, Object> result = new HashMap<>();
        String flagBlockReports = parametroFacadeLocal.findParameterValueByAcronymInCache(FLAG_BLOCK_REPORT_GENERATION);
        boolean isReportsBlocked = StringUtils.isNotBlank(flagBlockReports) && flagBlockReports.trim().equals("1");
        result.put(IS_ENABLED_BLOCK_REPORT, isReportsBlocked && isNotAnAuthorizedUser());

        if ((boolean) result.get(IS_ENABLED_BLOCK_REPORT)) {
            String message = obtainMessageBlockReport();
            String msgBlockReprt = "<br> La funcionalidad de " + nameReport + " <br> se encuentra inhabilitada... "
                    + " <br><br> Por favor comuníquese con el area de soporte.."
                    + " <br><br>" + message;
            result.put(MSG_BLOCK_REPORT, msgBlockReprt);
            return result;
        }

        return result;
    }

    /**
     * Verifica si el usuario en sesión no está autorizado para generar reportes.
     *
     * @return {@code boolean} Retorna true si el usuario no etá autorizado
     * para generar reportes durante el bloqueo asignado en la generación de reportes.
     *
     * @throws ApplicationException Excepción personalizada de la aplicación
     * @author Gildardo Mora
     */
    private boolean isNotAnAuthorizedUser() throws ApplicationException {
        String usuarioLogueado = SesionUtil.getUsuarioLogueado();
        if (StringUtils.isBlank(usuarioLogueado)) return false;

        String usersAuthorized = parametroFacadeLocal.findParameterValueByAcronymInCache(
                USERS_AUTHORIZED_TO_GENERATE_REPORTS);

        if (StringUtils.isBlank(usersAuthorized)) return false;

        List<String> usersAuthorizedList = StringToolUtils.convertStringToList(
                usersAuthorized, DelimitadorEnum.PUNTO_Y_COMA, true);
        return usersAuthorizedList.stream()
                .noneMatch(usuario -> usuario.trim().equalsIgnoreCase(usuarioLogueado.trim()));
    }

    /**
     * Obtiene el mensaje complementario que va a mostrarse en el popup cuando
     * se encuentra activo el bloqueo de generación de reportes.
     *
     * @return {@link String}
     * @throws ApplicationException Excepción generada al momento de consultar
     * el mensaje complementario del bloqueo de reportes.
     * @author Gildardo Mora
     */
    private String obtainMessageBlockReport() throws ApplicationException {
        return parametroFacadeLocal.findParameterValueByAcronymInCache(MESSAGE_POPUP_BLOCK_REPORT);
    }

}
