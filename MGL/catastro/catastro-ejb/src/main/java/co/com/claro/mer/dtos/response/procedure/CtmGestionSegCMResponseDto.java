/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.com.claro.mer.dtos.response.procedure;


import co.com.claro.mer.annotations.CursorType;
import co.com.claro.mer.annotations.StoredProcedureData;
import co.com.claro.mer.dtos.sp.cursors.CtmGestionSegCMDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO que representa la respuesta del procedimeinto almacenado CMT_GESTI_SEG_CM_AUD_PKG.CMT_GESTI_SEG_CM_AUD_PRC
 *
 * @author Carlos Andres Caicedo
 * @versión 1.00.000
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CtmGestionSegCMResponseDto {

    @StoredProcedureData(parameterName = "PO_CODIGO", parameterType = BigDecimal.class)
    private BigDecimal codigo;

    @StoredProcedureData(parameterName = "PO_DESCRIPCION", parameterType = String.class)
    private String resultado;
    
    @StoredProcedureData(parameterName = "PO_CURSOR", parameterType = Class.class)
    @CursorType(type = CtmGestionSegCMDto.class)
    private List<CtmGestionSegCMDto> cursor;

}
