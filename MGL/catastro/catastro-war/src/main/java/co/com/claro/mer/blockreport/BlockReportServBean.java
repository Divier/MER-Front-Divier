package co.com.claro.mer.blockreport;

import co.com.claro.mer.blockreport.domain.models.dto.EmailDetailsReportDto;
import co.com.claro.mer.blockreport.facade.BlockReportFacadeLocal;
import co.com.claro.mer.blockreport.facade.EmailReportFacadeLocal;
import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.util.FacesUtil;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.Map;

import static co.com.claro.mer.utils.constants.BlockReportConstants.IS_ENABLED_BLOCK_REPORT;
import static co.com.claro.mer.utils.constants.BlockReportConstants.MSG_BLOCK_REPORT;

/**
 * Service Bean encargado de gestionar los procesos de bloqueo de generación de reportes
 *
 * @author Gildardo Mora
 * @version 1.0, 2023/09/08
 */
@Named
@RequestScoped
public class BlockReportServBean {

    @EJB
    private BlockReportFacadeLocal blockReportFacadeLocal;
    @EJB
    private EmailReportFacadeLocal emailReportFacadeLocal;

    /**
     * Verifica si está bloqueada la generación de reportes.
     *
     * @return {@code boolean} Retorna {@code true} si la generación de reportes está bloqueada.
     * @throws ApplicationException Excepción personalizada de la app.
     * @author Gildardo Mora
     */
    public boolean isReportGenerationBlock(String nameReport) throws ApplicationException {
        Map<String, Object> reportGeneration = blockReportFacadeLocal.validateReportGeneration(nameReport);
        boolean isReportBlocked = (Boolean) reportGeneration.getOrDefault(IS_ENABLED_BLOCK_REPORT, false);

        if (isReportBlocked) {
            String msgReport = (String) reportGeneration.getOrDefault(MSG_BLOCK_REPORT, "");
            FacesUtil.mostrarMensajeWarn(msgReport);
            return true;
        }

        return false;
    }

    /**
     * Notifica por correo que se está ejecutando un proceso de generación de reportes
     * o de exportación de datos en MER
     *
     * @param detailsReportDto {@link EmailDetailsReportDto}
     * @author Gildardo Mora
     */
    public void notifyReportGeneration(EmailDetailsReportDto detailsReportDto) {
        emailReportFacadeLocal.sendEmailReportDetails(detailsReportDto);
    }

}
