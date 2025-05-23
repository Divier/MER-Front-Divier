package co.com.claro.mer.email.utils;

import co.com.claro.mer.email.domain.models.enums.EmailTemplatesHtmlEnum;
import co.com.claro.mgl.error.ApplicationException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Permite procesar la información de las plantillas HTML
 * definidas para envío de correos en MER.
 *
 * @author Gildardo Mora
 * @version 1.0, 2023/10/11
 */
@Log4j2
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReadEmailTemplateHtmlUtil {

    /**
     * Obtiene el contenido de la plantilla HTML indicada.
     *
     * @param emailTemplate {@link EmailTemplatesHtmlEnum}
     * @return {@link String} Retorna el contenido definido en la plantilla.
     * @throws ApplicationException Excepción en caso de error al procesar la plantilla
     * @author Gildardo Mora
     */
    public static String getTemplateContent(EmailTemplatesHtmlEnum emailTemplate) throws ApplicationException {
        if (StringUtils.isBlank(emailTemplate.getTemplateHtml())) {
            throw new ApplicationException("No se recibió nombre de archivo asociado a una plantilla ");
        }

        InputStream propertyStream = ReadEmailTemplateHtmlUtil.class.getClassLoader()
                .getResourceAsStream("templates/html5/" + emailTemplate.getTemplateHtml() + ".html");
        Objects.requireNonNull(propertyStream, "No se obtuvo información de la plantilla" + emailTemplate);
        StringWriter writer = new StringWriter();

        try {
            IOUtils.copy(propertyStream, writer, String.valueOf(StandardCharsets.UTF_8));
        } catch (IOException ex) {
            String msgError = String.format("No se pudo procesar la plantilla HTML %s %s ", emailTemplate, ex.getMessage());
            throw new ApplicationException(msgError, ex);
        }

        return writer.toString();
    }

}
