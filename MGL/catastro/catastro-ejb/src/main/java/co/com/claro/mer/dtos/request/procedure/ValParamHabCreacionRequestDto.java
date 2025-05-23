/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mer.dtos.request.procedure;

import co.com.claro.mer.annotations.StoredProcedureData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * DTO que representa el request al procedimiento para realizar consulta sobre el perfil para la creacion.
 *
 * @author Miguel Barrios
 * @version 1.0, 2023/10/20
 */
@Getter
@Setter
@NoArgsConstructor
@ToString

public class ValParamHabCreacionRequestDto {
    @StoredProcedureData(parameterName = "PI_COD_PERFIL", parameterType = String.class)
    private String codPerfil;
}