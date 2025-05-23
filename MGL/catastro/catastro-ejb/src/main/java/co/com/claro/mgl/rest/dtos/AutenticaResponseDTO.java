package co.com.claro.mgl.rest.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <b>Nombre: </b> package co.com.claro.mgl.rest.dtos.AutenticaResponseDTO </br>
 * <b>Descripcion: </b> DTO para mapear la repuesta del servicio de autenticacion de portal usuarios </br>
 * <b>Fecha Creación:</b> 27/09/2022 </br>
 * <b>Autor:</b> Manuel Hernández Rivas </br>
 * <b>Fecha de Última Modificación: </b>27/09/2022</br>
 * <b>Modificado por: </b>Manuel Hernández Rivas</br>
 * <b>Brief: </b>87621</br>
 */

@Getter
@Setter
public class AutenticaResponseDTO {
    @JsonProperty("token_session")
    private String token_session;

    @JsonProperty("estado")
    private String estado;

    @JsonProperty("usuario")
    private UsuarioResponseDTO usuario;

    @JsonProperty("fechaExpiracionToken")
    private String fechaExpiracionToken;

    @JsonProperty("nombreAliado")
    private String nombreAliado;

    @JsonProperty("nitAliado")
    private String nitAliado;

    @JsonProperty("correoAliado")
    private String correoAliado;

    @JsonProperty("nombre")
    private String nombre;

    @Override
    public String toString() {
        return "AutenticaResponse{" + "token_session=" + token_session + ", estado=" + estado + ", usuario=" + usuario + ", fechaExpiracionToken=" + fechaExpiracionToken + ", nombreAliado=" + nombreAliado + ", nitAliado=" + nitAliado + ", correoAliado=" + correoAliado + ", nombre=" + nombre + '}';
    }
}
