/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mer.dtos.request.procedure;

import co.com.claro.mer.annotations.StoredProcedureData;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * DTO que representa el request al procedimiento para realizar consulta a tabla TEC_PREFICHA_XLS_NEW.
 *
 * @author Johan Gomez
 * @version 1.0, 2023/09/05
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ConsultaPrefichaXlsNewRequestDto {
    @StoredProcedureData(parameterName = "PI_ID", parameterType = BigDecimal.class)
    private BigDecimal prefichaXlsId;
}
