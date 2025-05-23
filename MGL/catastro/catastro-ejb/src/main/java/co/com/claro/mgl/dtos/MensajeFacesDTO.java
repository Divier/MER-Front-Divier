package co.com.claro.mgl.dtos;

import javax.faces.application.FacesMessage;


/**
 * Data Transfer Object para el manejo de Mensajes Faces.
 * 
 * @author Camilo Miranda (<i>mirandaca</i>)
 */
public class MensajeFacesDTO {
    
    /** Texto del Mensaje. */
    private String message;
    /** Severidad del Mensaje. */
    private FacesMessage.Severity severity;
    /** T&iacute;tulo o resumen del Mensaje. */
    private String summary;
    
    
    
    /**
     * Constructor.
     * @param message Mensaje a mostrar.
     * @param severity Severidad del mensaje.
     */
    public MensajeFacesDTO(String message, FacesMessage.Severity severity) {
        this.message = message;
        this.severity = severity;
        
        if (severity != null) {
            this.summary = severity.toString();
        }
    }
    
    
    /**
     * Constructor.
     * @param summary T&iacute;tulo o resumen del Mensaje.
     * @param message Mensaje a mostrar.
     * @param severity Severidad del mensaje.
     */
    public MensajeFacesDTO(String summary, String message, FacesMessage.Severity severity) {
        this.summary = summary;
        this.message = message;
        this.severity = severity;
    }
    
    
    /**
     * Obtiene el nombre de la severidad, de acuerdo al tipo.
     * @return Severity name.
     */
    public String getSeverityName() {
        String severityName = null;
        
        if (this.severity != null) {
            if (severity.equals(FacesMessage.SEVERITY_INFO)) {
                severityName = "info";
            } else if (severity.equals(FacesMessage.SEVERITY_WARN)) {
                severityName = "warn";
            } else if (severity.equals(FacesMessage.SEVERITY_ERROR)) {
                severityName = "error";
            } else if (severity.equals(FacesMessage.SEVERITY_FATAL)) {
                severityName = "fatal";
            }
        }
        
        return (severityName);
    }
    
    
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public FacesMessage.Severity getSeverity() {
        return severity;
    }

    public void setSeverity(FacesMessage.Severity severity) {
        this.severity = severity;
    }
    
}
