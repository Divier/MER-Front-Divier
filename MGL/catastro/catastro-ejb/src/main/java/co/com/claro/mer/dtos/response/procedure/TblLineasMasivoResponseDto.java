/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mer.dtos.response.procedure;

import co.com.claro.mer.annotations.StoredProcedureData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Clase DTO para contener la respuesta de ODI. En el se puede ver
 * la informacion de Archivos Consultados usando el LinMasivoId
 *
 * @author Henry Sanchez Arango
 * @version 1.0
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TblLineasMasivoResponseDto {

    @StoredProcedureData(parameterName = "PO_MASIVO_ID", parameterType = String.class)
    private String poMasivoId;

    @StoredProcedureData(parameterName = "PO_ODIUSER", parameterType = String.class)
    private String poOdiUser;

    @StoredProcedureData(parameterName = "PO_ODIPASSWORD", parameterType = String.class)
    private String poOdiPassword;

    @StoredProcedureData(parameterName = "PO_WORKREPOSITORY", parameterType = String.class)
    private String poWorkRepository;

    @StoredProcedureData(parameterName = "PO_LOADPLANNAME", parameterType = String.class)
    private String poLoadPlanName;

    @StoredProcedureData(parameterName = "PO_CONTEXT", parameterType = String.class)
    private String poContext;

    @StoredProcedureData(parameterName = "PO_LOGLEVEL", parameterType = String.class)
    private String poLogLevel;

    @StoredProcedureData(parameterName = "PO_CODIGO", parameterType = BigDecimal.class)
    private BigDecimal poCodigo;

    @StoredProcedureData(parameterName = "PO_RESULTADO", parameterType = String.class)
    private String poResultado;

    @StoredProcedureData(parameterName = "PO_URL", parameterType = String.class)
    private String wsdl;

}
