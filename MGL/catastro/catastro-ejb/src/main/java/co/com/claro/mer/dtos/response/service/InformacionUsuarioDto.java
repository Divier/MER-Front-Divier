package co.com.claro.mer.dtos.response.service;

import com.google.gson.annotations.SerializedName;
import lombok.*;

/**
 * DTO para la respuesta de la consulta de información de usuario.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class InformacionUsuarioDto {

    @SerializedName("cedula")
    private String cedula;

    @SerializedName("nombre")
    private String nombre;

    @SerializedName("telefono")
    private String telefono;

    @SerializedName("email")
    private String email;

    @SerializedName("direccion")
    private String direccion;

    @SerializedName("usuario")
    private String usuario;

    @SerializedName("codPerfil")
    private String codigoPerfil;

    @SerializedName("descripcionPerfil")
    private String descripcionPerfil;

    @SerializedName("idArea")
    private String idArea;

    @SerializedName("descripcionArea")
    private String descripcionArea;

}
