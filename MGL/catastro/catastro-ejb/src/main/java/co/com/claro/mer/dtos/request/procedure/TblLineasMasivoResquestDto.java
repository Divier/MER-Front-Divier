package co.com.claro.mer.dtos.request.procedure;

import co.com.claro.mer.annotations.StoredProcedureData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * DTO para el procedimiento almacenado TBL_LINEAS_MASIVO
 * <p>
 *     Parametros:
 *     <ul>
 *         <li>PI_ACCION</li>
 *         <li>PI_MASIVO_ID</li>
 *         <li>PI_TIPO</li>
 *         <li>PI_ARC_IN</li>
 *         <li>PI_FILTRO</li>
 *         <li>PI_FILTRO_LEG</li>
 *         <li>PI_TOT_REG</li>
 *         <li>PI_USUARIO</li>
 *         <li>PI_ROL</li>
 *         <li>PI_LIN_MASIVO_ID</li>
 *         <li>PI_ESTADO</li>
 *         <li>PI_OBSERV</li>
 *      </ul>
 * </p>
 * @see co.com.claro.mer.annotations.StoredProcedureData para la anotacion de los parametros.
 * @see co.com.claro.mer.dtos.response.procedure.TblLineasMasivoResponseDto para el DTO de respuesta.
 * @author Manuel Hernández
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TblLineasMasivoResquestDto {

    @StoredProcedureData(parameterName = "PI_ACCION", parameterType = String.class)
    private String accion;

    @StoredProcedureData(parameterName = "PI_MASIVO_ID", parameterType = BigDecimal.class)
    private BigDecimal masivoId;

    @StoredProcedureData(parameterName = "PI_TIPO", parameterType = String.class)
    private String tipo;

    @StoredProcedureData(parameterName = "PI_ARC_IN", parameterType = String.class)
    private String arcIn;

    @StoredProcedureData(parameterName = "PI_FILTRO", parameterType = String.class)
    private String filtro;

    @StoredProcedureData(parameterName = "PI_FILTRO_LEG", parameterType = String.class)
    private  String filtroTraducido;

    @StoredProcedureData(parameterName = "PI_TOT_REG", parameterType = BigDecimal.class)
    private BigDecimal totReg;

    @StoredProcedureData(parameterName = "PI_USUARIO", parameterType = String.class)
    private String usuario;

    @StoredProcedureData(parameterName = "PI_ROL", parameterType = String.class)
    private String rol;

    @StoredProcedureData(parameterName = "PI_LIN_MASIVO_ID", parameterType = BigDecimal.class)
    private BigDecimal linMasivoId;

    @StoredProcedureData(parameterName = "PI_ESTADO", parameterType = String.class)
    private String estado;

    @StoredProcedureData(parameterName = "PI_OBSERV", parameterType = String.class)
    private String observ;
}
