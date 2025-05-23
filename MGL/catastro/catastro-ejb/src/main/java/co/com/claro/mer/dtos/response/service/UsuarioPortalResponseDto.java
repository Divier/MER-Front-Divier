package co.com.claro.mer.dtos.response.service;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Define la estructura de respuesta del atributo usuario de la repuesta de autenticación.
 *
 * @author Gildardo Mora
 * @version 1.0, 2024/08/30
 */
@Getter
@Setter
public class UsuarioPortalResponseDto implements Serializable {

    private static final long serialVersionUID = -2329198920730023673L;

    @SerializedName("codPerfil")
    private String codPerfil;

    @SerializedName("descripcion")
    private String descripcion;

    @SerializedName("estado")
    private String estado;

    @SerializedName("idPerfil")
    private String idPerfil;

    @SerializedName("idUsuario")
    private String idUsuario;

    @SerializedName("listRoles")
    private List<RolPortalResponseDto> listRoles;

    @SerializedName("nombre")
    private String nombre;

    @SerializedName("usuario")
    private String usuario;

    @SerializedName("email")
    private String email;

    @SerializedName("usuarioRR")
    private String usuarioRr;

}
