package co.com.claro.mer.email.domain.models.dto;

import co.com.claro.mer.email.domain.models.enums.EmailTemplatesHtmlEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO base para procesos de envío de correos
 *
 * @author Gildardo Mora
 * @version 1.0, 2023/10/14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailBaseDto {

    private String module;
    private String sessionUser;
    private String sessionUserName;
    private String message;
    /**
     * Lista de correos de destinatarios
     */
    private List<String> addressList;
    private EmailTemplatesHtmlEnum templateHtml;
}
