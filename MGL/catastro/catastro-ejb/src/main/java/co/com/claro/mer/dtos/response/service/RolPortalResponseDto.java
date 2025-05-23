package co.com.claro.mer.dtos.response.service;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Define la estructura de respuesta del atributo rol de la respuesta de autenticación.
 *
 * @author Gildardo Mora
 * @version 1.0, 2024/08/30
 */
@Getter
@Setter
public class RolPortalResponseDto implements Serializable {

    private static final long serialVersionUID = 2231136669220607108L;

    @SerializedName("codRol")
    private String codRol;

    @SerializedName("descripcionRol")
    private String descripcionRol;

    @SerializedName("estadoRol")
    private String estadoRol;

    @SerializedName("idPerfil")
    private String idPerfil;

    @SerializedName("idRolperfil")
    private String idRolPerfil;

}
