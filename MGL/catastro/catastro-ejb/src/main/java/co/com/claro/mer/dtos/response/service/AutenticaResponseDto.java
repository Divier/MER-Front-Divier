package co.com.claro.mer.dtos.response.service;

import com.google.gson.annotations.SerializedName;
import lombok.*;

/**
 * Define la estructura de respuesta de la autenticación de un usuario
 * en el servicio de PortalUsuarios.
 *
 * @author Gildardo Mora
 * @version 1.0, 2024/08/29
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AutenticaResponseDto {

    @SerializedName("token_session")
    private String tokenSession;

    @SerializedName("estado")
    private String estado;

    @SerializedName("usuario")
    private UsuarioPortalResponseDto usuario;

    @SerializedName("fechaExpiracionToken")
    private String fechaExpiracionToken;

    @SerializedName("nombreAliado")
    private String nombreAliado;

    @SerializedName("nitAliado")
    private String nitAliado;

    @SerializedName("correoAliado")
    private String correoAliado;


}
