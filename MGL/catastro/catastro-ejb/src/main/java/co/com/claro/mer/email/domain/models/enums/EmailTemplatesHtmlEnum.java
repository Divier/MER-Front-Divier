package co.com.claro.mer.email.domain.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enumerado para administrar las plantillas de correo
 *
 * @author Gildardo Mora
 * @version 1.0, 2023/08/16
 */
@Getter
@AllArgsConstructor
public enum EmailTemplatesHtmlEnum {

    ERROR_IN_PRESENTATION("error", "emailError"),
    ERROR_IN_SERVICES("errorInService", "emailErrorOfService"),
    /**
     * Mapea la plantilla de correos de detalles de reportes procesados
     */
    DETAILS_REPORTS("detailsReport", "emailDetailsReport");

    /**
     * Nombre de referencia para la plantilla.
     */
    private final String namePlantilla;
    /**
     * Nombre del archivo (.html) de la plantilla
     */
    private final String templateHtml;
}
