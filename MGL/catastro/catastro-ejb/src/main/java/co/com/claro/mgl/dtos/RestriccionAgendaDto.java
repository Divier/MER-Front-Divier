package co.com.claro.mgl.dtos;

import java.io.Serializable;

/**
 * Clase para transportar la informaci&oacute;n de restricciones en agendamiento
 *
 * @author wgavidia
 * @vesion 24/11/2017
 */
public class RestriccionAgendaDto implements Serializable {

    private String code;
    private String type;
    private String description;

    /**
     * Constructor por defecto de la clase
     */
    public RestriccionAgendaDto() {
    }

    /**
     * Constructor con todos los parametros
     *
     * @param code {@link String} c&oacute;digo de la restricci&oacute;n
     * @param type {@link String} tipo de restricci&oacute;n
     * @param description {@link String} descripci&oacute;n de la restricci&oacute;n
     */
    public RestriccionAgendaDto(String code, String type, String description) {
        this.code = code;
        this.type = type;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
