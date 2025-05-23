/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mer.dtos.response.procedure;

import co.com.claro.mer.annotations.CursorType;
import co.com.claro.mer.annotations.StoredProcedureData;
import co.com.claro.mgl.dtos.CruReadCtechDto;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * DTO que representa el response al procedimiento para realizar consulta de CRUD TECHNICAL SITE SAP para CM.
 *
 * @author Johan Gomez
 * @version 1.0, 2024/12/09
 */
@Getter
@Setter
@NoArgsConstructor
@ToString

public class CmtCruCtechResponseDto {
    
    /**
     * Mapea parametros de respuesta del SP.
     */
    
    @StoredProcedureData(parameterName = "PO_CODIGO", parameterType = Integer.class)
    private Integer codigo;

    @StoredProcedureData(parameterName = "PO_RESULTADO", parameterType = String.class)
    private String resultado;
    
    @StoredProcedureData(parameterName = "PO_C_RESPONSE", parameterType = Class.class)
    @CursorType(type = CruReadCtechDto.class)
    private List<CruReadCtechDto> listaReadCtechDto;
    
}
