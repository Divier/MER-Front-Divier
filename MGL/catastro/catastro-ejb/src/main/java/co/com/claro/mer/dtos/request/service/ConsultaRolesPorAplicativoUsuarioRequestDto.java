package co.com.claro.mer.dtos.request.service;

import com.google.gson.annotations.SerializedName;
import lombok.*;

/**
 * DTO para la petición de consulta de roles por aplicativo y usuario.
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ConsultaRolesPorAplicativoUsuarioRequestDto {

    @SerializedName("appId")
    private String aplicativo;

    @SerializedName("usuario")
    private String usuario;

}
