/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.com.claro.mer.dtos.request.procedure;

import co.com.claro.mer.annotations.StoredProcedureData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * DTO que representa el request de entrada del procedimiento
 * CMT_GESTION_SEG_CM_PKG.CMT_CON_GESTI_SEG_CM_PRC
 *
 * @author Carlos Andres Caicedo
 * @versión 1.00.000
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CtmGestionSegCMConRequestDto {

    @StoredProcedureData(parameterName = "PI_CUENTAMATRIZ_ID", parameterType = BigDecimal.class)
    private BigDecimal cuentaMatriz;
}
