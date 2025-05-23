package co.com.claro.mer.blockreport.application.usecases;

import co.com.claro.mer.blockreport.application.ports.IReportParameterPort;
import co.com.claro.mer.blockreport.domain.models.dto.EmailDetailsReportDto;
import co.com.claro.mer.blockreport.domain.ports.ISendEmailOfReportDetails;
import co.com.claro.mer.email.application.constant.EmailConstants;
import co.com.claro.mer.email.utils.EmailMessageUtil;
import co.com.claro.mer.utils.ServerInfoUtil;
import co.com.claro.mgl.error.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.persistence.internal.oxm.conversion.Base64;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static co.com.claro.mer.email.utils.ReadEmailTemplateHtmlUtil.getTemplateContent;

/**
 * Gestiona el envío de correos de detalles de reporte en proceso.
 *
 * @author Gildardo Mora
 * @version 1.0, 2023/10/12
 */
@RequiredArgsConstructor
public class SendEmailOfReportDetails implements ISendEmailOfReportDetails {

    private final IReportParameterPort parameterPort;

    /**
     * Crea el mensaje formateado para el request,
     * requerido para consumir el servicio de envío de correos
     *
     * @param emailDetailsReportDto {@link EmailDetailsReportDto}
     * @return {@link String}
     * @throws ApplicationException Excepción al momento de crear el mensaje
     * @author Gildardo Mora
     */
    @Override
    public String createMessageRequest(EmailDetailsReportDto emailDetailsReportDto) throws ApplicationException {
        try {
            // Asunto del correo
            String emailSubject = "MER - REPORTE EN PROCESO: " + emailDetailsReportDto.getReportName();

            if (StringUtils.isBlank(emailDetailsReportDto.getMessage())) {
                emailDetailsReportDto.setMessage(emailDetailsReportDto.getReportDescription());
            }

            List<String> addressList = parameterPort.findAddressEmail();
            String emailBodyTemplate = getTemplateContent(emailDetailsReportDto.getTemplateHtml());
            Map<String, String> bodyEmailMap = EmailMessageUtil.generateBodyEmailMap(emailDetailsReportDto);
            String emailBody = EmailMessageUtil.reemplazarTexto(emailBodyTemplate, bodyEmailMap);
            // Asigna el archivo adjunto para enviar en el correo.
            Map<String, String> attachments = new HashMap<>();
            String infoReport = generateInfoReport(emailDetailsReportDto);
            byte[] bytesEncoded = Base64.base64Encode(infoReport.getBytes());
            String contenido = new String(bytesEncoded);
            LocalDate fecha = LocalDate.now(ZoneId.of("America/Bogota"));
            String fileName = String.format("MER-infoRpt-%s-%s.txt", emailDetailsReportDto.getReportName(), fecha);
            attachments.put(fileName, contenido);

            return EmailMessageUtil.generateMessage(addressList, attachments, emailBody, emailSubject,
                    EmailConstants.PROFILE_PARAM.split(","));
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Genera los detalles del reporte, para agregar en el archivo adjunto.
     *
     * @param emailDetailsReportDto {@link EmailDetailsReportDto}
     * @return {@link String} Retorna el texto que lleva los detalles del reporte o exportación.
     * @author Gildardo Mora
     */
    private String generateInfoReport(EmailDetailsReportDto emailDetailsReportDto) {
        String nameCluster = "CLUSTER: " + ServerInfoUtil.getClusterName();
        String nameServer = "SERVIDOR: " + ServerInfoUtil.getServerName();
        return "Reporte / Exportación solicitado por: "
                + emailDetailsReportDto.getSessionUser() + " - " + emailDetailsReportDto.getSessionUserName()
                + "\n " + emailDetailsReportDto.getReportDescription()
                + "\n " + nameCluster + " - "
                + "\n " + nameServer;
    }

}
