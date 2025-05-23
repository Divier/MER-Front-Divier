package co.com.claro.mer.dtos.request.procedure;

import co.com.claro.mer.annotations.StoredProcedureData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO que representa el request de ejecución
 * del procedimiento de consulta de log de marcación HHPP virtual.
 *
 * @author Gildardo Mora
 * @version 1.0, 2023/06/02
 * @see co.com.claro.mgl.dao.impl.cm.CmtLogMarcacionesHhppVtMglDaoImpl
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ConsultarLogMarcacionHhppRequestDto {

    /**
     * Mapea el identificador del log de marcación de HHPP virtual.
     */
    @StoredProcedureData(parameterName = "V_LOG_MARCACIONES_HHPP_VT_ID", parameterType = BigDecimal.class)
    private BigDecimal logMarcacionHhppVtId;

    /**
     * Mapea el identificador de la marcación de HHPP virtual.
     */
    @StoredProcedureData(parameterName = "V_ID_MARCACION", parameterType = BigDecimal.class)
    private BigDecimal marcacionId;

    /**
     * Mapea la fecha de creación del log de marcación de HHPP virtual.
     */
    @StoredProcedureData(parameterName = "V_FECHA_CREACION", parameterType = LocalDateTime.class)
    private LocalDateTime fechaCreacion;

    /**
     * Mapea el valor anterior del log de marcación de HHPP virtual.
     */
    @StoredProcedureData(parameterName = "V_VALOR_ANTERIOR_APLICA", parameterType = String.class)
    private String valorAnteriorAplica;

    /**
     * Mapea el valor nuevo del log de marcación de HHPP virtual.
     */
    @StoredProcedureData(parameterName = "V_VALOR_NUEVO_APLICA", parameterType = String.class)
    private String valorNuevoAplica;

    /**
     * Mapea el estado del registro del log de marcación de HHPP virtual.
     */
    @StoredProcedureData(parameterName = "V_ESTADO_REGISTRO", parameterType = String.class)
    private String estadoRegistro;

    /**
     * Mapea el usuario que creó el registro del log de marcación de HHpp virtual.
     */
    @StoredProcedureData(parameterName = "V_USUARIO_CREACION", parameterType = BigDecimal.class)
    private String usuarioCreacion;

}
