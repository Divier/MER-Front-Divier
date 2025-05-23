package co.com.claro.mer.dtos.request.service;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para la petición de consulta de información de usuario.
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ConsultaInfoRequestDto {

    @SerializedName("usuario")
    private String usuario;

    @SerializedName("cedula")
    private String cedula;

}
