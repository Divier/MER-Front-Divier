package co.com.claro.mer.dtos.request.procedure;

import co.com.claro.mer.annotations.StoredProcedureData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * DTO que representa el request a la ejecución del procedimiento
 * de consulta de marcaciones de HHPP virtual.
 *
 * @author Gildardo Mora
 * @version 1.0, 2023/06/02
 * @see co.com.claro.mgl.dao.impl.cm.CmtMarcacionesHhppVtMglDaoImpl
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ConsultaMarcacionHhppVirtualRequestDto {

    /**
     * Mapea el identificador de la marcación de HHPP virtual.
     */
    @StoredProcedureData(parameterName = "V_MARCACIONES_HHPP_VT_ID", parameterType = BigDecimal.class)
    private BigDecimal marcacionHhppVtId;

    /**
     * Mapea el código de razón (R1) para marcación de HHPP virtual.
     */
    @StoredProcedureData(parameterName = "V_CODIGO_R1", parameterType = String.class)
    private String codigoRazon;

    /**
     * Mapea la descripción de la razón para marcación de HHPP virtual.
     */
    @StoredProcedureData(parameterName = "V_DESCRIPCION_R1", parameterType = String.class)
    private String descripcionRazon;

    /**
     * Mapea el código de sub razón (R2) para marcación de HHPP virtual.
     */
    @StoredProcedureData(parameterName = "V_CODIGO_R2", parameterType = String.class)
    private String codigoSubRazon;

    /**
     * Mapea la descripción de la sub razón para marcación de HHPP virtual.
     */
    @StoredProcedureData(parameterName = "V_DESCRIPCION_R2", parameterType = String.class)
    private String descripcionSubRazon;

    /**
     * Identifica si aplica (1) o no aplica (0), para marcación de HHPP virtual.
     */
    @StoredProcedureData(parameterName = "V_APLICA_HHPP_VT", parameterType = Integer.class)
    private Integer aplicaHhppVirtual;

    /**
     * Mapea el estado (activo = 1, inactivo = 0) del registro
     * de la marcación de HHPP virtual.
     */
    @StoredProcedureData(parameterName = "V_ESTADO_REGISTRO", parameterType = Integer.class)
    private Integer estadoRegistro;
}
