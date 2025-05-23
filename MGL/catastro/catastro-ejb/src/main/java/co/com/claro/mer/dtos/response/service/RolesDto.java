package co.com.claro.mer.dtos.response.service;

import com.google.gson.annotations.SerializedName;
import lombok.*;

/**
 * DTO para la consulta de roles
 * Almacena los datos de toles consultados
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RolesDto {

    @SerializedName("codRol")
    private String codigoRol;

    @SerializedName("descripcionRol")
    private String descripcionRol;

    @SerializedName("accionRol")
    private String accionRol;

    @SerializedName("estadoRol")
    private String estadoRol;
}
