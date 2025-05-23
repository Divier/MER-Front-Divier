/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mer.dtos.response.procedure;

import co.com.claro.mer.annotations.StoredProcedureData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * DTO que representa la respuesta del procedimiento del CRUD a tabla TEC_PREFICHA_NEW.
 * @version 1.0, 2023/09/05
 * @author Johan Gomez
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class CrudTecPrefichaNewResponseDto {
    
    @StoredProcedureData(parameterName = "PO_CODIGO", parameterType = Integer.class)
    private Integer codigo;

    @StoredProcedureData(parameterName = "PO_DESCRIPCION", parameterType = String.class)
    private String descripcion;
}
