package co.com.claro.mer.dtos.request.procedure;

import co.com.claro.mer.annotations.StoredProcedureData;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clase con los campos de entrada para consumir procedimiento almacenado CMT_SOL_NODO_CUAD_FRONT_SP 
 * Permite configurar los campos a enviar
 *
 * @author Divier Casas
 * @version 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CmtSolicitudNodoCuadFrontRequestDto {
    
    @StoredProcedureData(parameterName = "PI_SOLICITUD_ID", parameterType = BigDecimal.class)
    private BigDecimal solicitudId;

    @StoredProcedureData(parameterName = "PI_CUADRANTE_ID", parameterType = String.class)
    private String cuadranteId;
    
    @StoredProcedureData(parameterName = "PI_COD_DIVISIONAL", parameterType = BigDecimal.class)
    private BigDecimal codDivisional;
    
    @StoredProcedureData(parameterName = "PI_COD_DEPTO", parameterType = BigDecimal.class)
    private BigDecimal codDepto;
    
    @StoredProcedureData(parameterName = "PI_COD_CIUDAD", parameterType = BigDecimal.class)
    private BigDecimal codCiudad;

    @StoredProcedureData(parameterName = "PI_COD_CENTRO_POBLADO", parameterType = BigDecimal.class)
    private BigDecimal codCentroPoblado;
    
    @StoredProcedureData(parameterName = "PI_CODIGO_NODO", parameterType = String.class)
    private String codigoNodo;
    
    @StoredProcedureData(parameterName = "PI_COD_ESTADO", parameterType = Integer.class)
    private Integer codEstado;
    
    @StoredProcedureData(parameterName = "PI_LEGADO", parameterType = String.class)
    private String legado;
    
    @StoredProcedureData(parameterName = "PI_RESULTADO_ASOCIACION", parameterType = String.class)
    private String resultadoAsociacion;
}