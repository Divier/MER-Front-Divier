package co.com.claro.mer.dtos.response.service;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.util.List;

/**
 * DTO para las respuestas de las operaciones de consultar roles
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ConsultaRolesResponseDto {

    @SerializedName("mensaje")
    private String mensaje;

    @SerializedName("idPerfil")
    private String idPerfil;

    @SerializedName("listaRoles")
    private List<RolesDto> roles;
}
