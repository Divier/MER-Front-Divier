package co.com.claro.mer.dtos.response.service;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.util.List;

/**
 * DTO para la respuesta de la consulta de información de usuario.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ConsultaInfoResponseDto {

    @SerializedName("info")
    private List<InformacionUsuarioDto> informacionUsuario;
}
