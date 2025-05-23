package co.com.claro.mer.dtos.response.procedure;

import co.com.claro.mer.annotations.CursorType;
import co.com.claro.mer.annotations.StoredProcedureData;
import co.com.claro.mer.dtos.sp.cursors.ListaItemsFiltrosDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO para el procedimiento almacenado que retorna la lista de items de los filtros tipo select.
 * @author Manuel Hernández Rivas
 * @version 1.0, 2025/02/27
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ListaFiltrosResponseDto {
    @StoredProcedureData(parameterName = "PO_CODIGO", parameterType = BigDecimal.class)
    private BigDecimal codigo;

    @StoredProcedureData(parameterName = "PO_RESULTADO", parameterType = String.class)
    private String resultado;

    @StoredProcedureData(parameterName = "PO_CURSOR", parameterType = Class.class)
    @CursorType(type = ListaItemsFiltrosDto.class)
    private List<ListaItemsFiltrosDto> cursor;
}
