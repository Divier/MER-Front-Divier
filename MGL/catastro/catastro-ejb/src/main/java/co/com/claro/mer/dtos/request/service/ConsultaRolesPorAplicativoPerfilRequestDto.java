package co.com.claro.mer.dtos.request.service;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para la petición de consulta de roles por aplicativo y perfil.
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ConsultaRolesPorAplicativoPerfilRequestDto {

    @SerializedName("appId")
    private String aplicativo;

    @SerializedName("perfil_id")
    private String perfil;

}
