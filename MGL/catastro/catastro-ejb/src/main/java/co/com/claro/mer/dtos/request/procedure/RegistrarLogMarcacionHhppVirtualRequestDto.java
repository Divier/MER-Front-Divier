package co.com.claro.mer.dtos.request.procedure;

import co.com.claro.mer.annotations.StoredProcedureData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * DTO que representa el request de petición del procedimiento que
 * consulta de registro de log de marcación de HHPP virtual.
 *
 * @author Gildardo Mora
 * @version 1.0, 2023/06/02
 * @see co.com.claro.mgl.dao.impl.cm.CmtLogMarcacionesHhppVtMglDaoImpl
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class RegistrarLogMarcacionHhppVirtualRequestDto {

    /**
     * Identificadores de la marcación.
     */
    @StoredProcedureData(parameterName = "V_ID_MARCACION", parameterType = String.class)
    private String idMarcacion;

    /**
     * Valores anteriores de la marcación.
     */
    @StoredProcedureData(parameterName = "V_VALOR_ANTERIOR_APLICA", parameterType = String.class)
    private String valorAnteriorAplica;

    /**
     * Valores nuevos de la marcación.
     */
    @StoredProcedureData(parameterName = "V_VALOR_NUEVO_APLICA", parameterType = String.class)
    private String valorNuevoAplica;

    /**
     * Usuario que creó la marcación.
     */
    @StoredProcedureData(parameterName = "V_USUARIO_CREACION", parameterType = String.class)
    private String usuarioCreacion;

    /**
     * Perfil que creó la marcación.
     */
    @StoredProcedureData(parameterName = "V_PERFIL_CREACION", parameterType = Integer.class)
    private Integer perfilCreacion;

}
