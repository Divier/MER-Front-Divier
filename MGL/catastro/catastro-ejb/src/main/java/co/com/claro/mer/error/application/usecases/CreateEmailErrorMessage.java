package co.com.claro.mer.error.application.usecases;

import co.com.claro.mer.email.application.constant.EmailConstants;
import co.com.claro.mer.email.utils.EmailMessageUtil;
import co.com.claro.mer.email.utils.ReadEmailTemplateHtmlUtil;
import co.com.claro.mer.error.application.ports.IErrorPort;
import co.com.claro.mer.error.domain.models.dto.EmailErrorDto;
import co.com.claro.mer.error.domain.ports.IErrorDetails;
import co.com.claro.mer.utils.ServerInfoUtil;
import co.com.claro.mgl.error.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.eclipse.persistence.internal.oxm.conversion.Base64;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Permite estandarizar y generar con la estructura requerida,
 * el mensaje con los detalles del error generado.
 *
 * @author Gildardo Mora
 * @version 1.0, 2023/10/25
 */
@RequiredArgsConstructor
public class CreateEmailErrorMessage implements IErrorDetails {

    private final IErrorPort emailParameterPort;

    /**
     * Crea el mensaje con los detalles del error con la estructura requerida.
     *
     * @param emailErrorDto {@link EmailErrorDto}
     * @return {@link String}
     * @throws ApplicationException Excepción en caso de error al crear el mensaje.
     * @author Gildardo Mora
     */
    @Override
    public String createMessageRequest(EmailErrorDto emailErrorDto) throws ApplicationException {
        try {
            String emailSubject = generateEmailSubject(emailErrorDto);
            List<String> addressListEmail = emailParameterPort.findAddressEmail();
            String templateContent = ReadEmailTemplateHtmlUtil.getTemplateContent(emailErrorDto.getTemplateHtml());
            Map<String, String> bodyEmailMap = EmailMessageUtil.generateBodyEmailMap(emailErrorDto);
            bodyEmailMap.put(EmailConstants.UUID_ATRIBUTO, emailErrorDto.getUuid());
            String emailBody = EmailMessageUtil.reemplazarTexto(templateContent, bodyEmailMap);
            Map<String, String> attachments = new HashMap<>();
            byte[] bytesEncoded = Base64.base64Encode(emailErrorDto.getMessage()
                    .replace("<br>", "\n").getBytes());
            String contenido = new String(bytesEncoded);
            attachments.put("MER-ERROR-" + emailErrorDto.getModule() + ".txt", contenido);
            return EmailMessageUtil.generateMessage(addressListEmail, attachments, emailBody,
                    emailSubject, EmailConstants.PROFILE_PARAM.split(","));
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    /**
     * Genera el asunto del correo del error a notificar.
     *
     * @param emailErrorDto {@link EmailErrorDto}
     * @return {@link String} Retorna el asunto para el correo de notificación de error.
     * @author Gildardo Mora
     */
    private String generateEmailSubject(EmailErrorDto emailErrorDto) {
        String nameCluster = ServerInfoUtil.getClusterName();
        String nameServer = ServerInfoUtil.getServerName();
        String nameMachine = ServerInfoUtil.getMachineName();
        Predicate<String> isValid = x -> x != null && !x.contains("NA");
        return "Error MER - " + nameServer + " - "
                + (isValid.test(nameCluster) ? nameCluster + " - " : nameMachine + " - ")
                + emailErrorDto.getModule();
    }

}
