package co.com.claro.mer.utils.enums;

import static javax.faces.application.FacesMessage.*;

/**
 * Enumerado para apoyar el proceso de generación de mensajes de notificación al usuario.
 *
 * @author Gildardo Mora
 * @version 1.0, 2022/06/21
 */
public enum MessageSeverityEnum {
    INFO(SEVERITY_INFO),
    ERROR(SEVERITY_ERROR),
    WARN(SEVERITY_WARN),
    FATAL(SEVERITY_FATAL);

    private final Severity severity;

    MessageSeverityEnum(Severity severity) {
        this.severity = severity;
    }

    public Severity getSeverity() {
        return severity;
    }
}
