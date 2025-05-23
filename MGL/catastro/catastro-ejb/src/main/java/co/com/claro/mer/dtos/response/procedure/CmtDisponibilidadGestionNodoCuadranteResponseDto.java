/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mer.dtos.response.procedure;

import co.com.claro.mer.annotations.StoredProcedureData;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clase con los campos de salida tras consumir procedimiento almacenado UPDATE_NODO_CUAD_DISPONIBILIDAD_GESTION_SP 
 * Permite configurar los campos a recibir
 *
 * @author Divier Casas
 * @version 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CmtDisponibilidadGestionNodoCuadranteResponseDto {
    
    @StoredProcedureData(parameterName = "PO_CODE", parameterType = BigDecimal.class)
    private BigDecimal code;

    @StoredProcedureData(parameterName = "PO_MESSAGE", parameterType = String.class)
    private String message;
}
