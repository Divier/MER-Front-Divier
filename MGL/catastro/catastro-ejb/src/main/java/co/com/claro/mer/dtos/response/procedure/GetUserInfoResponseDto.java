package co.com.claro.mer.dtos.response.procedure;

import co.com.claro.mer.annotations.CursorType;
import co.com.claro.mer.annotations.StoredProcedureData;
import co.com.claro.mer.dtos.sp.cursors.UsuarioMicroSitioDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * DTO que representa la respuesta del procedimeinto almacenado MGL_SCHEME.GET_USER_INFO
 * @version 1.00, 2024-07-31
 * @author Manuel Hernández
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetUserInfoResponseDto {

    @StoredProcedureData(parameterName = "PO_CODIGO", parameterType = int.class)
    private int codigo;

    @StoredProcedureData(parameterName = "PO_RESULTADO", parameterType = String.class)
    private String resultado;

    @StoredProcedureData(parameterName = "PO_CURSOR", parameterType = Class.class)
    @CursorType(type = UsuarioMicroSitioDto.class)
    private List<UsuarioMicroSitioDto> cursor;
}
