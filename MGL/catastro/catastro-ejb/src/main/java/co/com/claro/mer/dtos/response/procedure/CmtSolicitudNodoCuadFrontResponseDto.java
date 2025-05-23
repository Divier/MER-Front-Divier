package co.com.claro.mer.dtos.response.procedure;

import co.com.claro.mer.annotations.CursorType;
import co.com.claro.mer.annotations.StoredProcedureData;
import co.com.claro.mer.dtos.sp.cursors.CmtSolicitudNodoCuadranteDto;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clase con los campos de salida tras consumir procedimiento almacenado CMT_SOL_NODO_CUAD_FRONT_SP 
 * Permite configurar los campos a recibir
 *
 * @author Divier Casas
 * @version 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CmtSolicitudNodoCuadFrontResponseDto {

    @StoredProcedureData(parameterName = "PO_CODE", parameterType = BigDecimal.class)
    private BigDecimal code;

    @StoredProcedureData(parameterName = "PO_MESSAGE", parameterType = String.class)
    private String message;

    @StoredProcedureData(parameterName = "PO_CURSOR", parameterType = Class.class)
    @CursorType(type = CmtSolicitudNodoCuadranteDto.class)
    private List<CmtSolicitudNodoCuadranteDto> cursor;
}
