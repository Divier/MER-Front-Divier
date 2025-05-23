/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mer.dtos.response.procedure;

import co.com.claro.mer.annotations.CursorType;
import co.com.claro.mer.annotations.StoredProcedureData;
import co.com.claro.mgl.dtos.ConsultaPrefichaXlsNewAllDto;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * DTO que representa el respomse al procedimiento para realizar consulta a tabla TEC_PREFICHA_XLS_NEW.
 *
 * @author Johan Gomez
 * @version 1.0, 2023/09/05
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ConsultaPrefichaXlsNewResponseDto {
    /**
     * Mapea la lista de marcaciones de HHPP virtual encontradas.
     */
    @StoredProcedureData(parameterName = "PO_CODIGO", parameterType = Integer.class)
    private Integer codigo;

    @StoredProcedureData(parameterName = "PO_DESCRIPCION", parameterType = String.class)
    private String descripcion;
    
    @StoredProcedureData(parameterName = "PO_V_RESPONSE", parameterType = Class.class)
    @CursorType(type = ConsultaPrefichaXlsNewAllDto.class)
    private List<ConsultaPrefichaXlsNewAllDto> prefichaXls;
}
