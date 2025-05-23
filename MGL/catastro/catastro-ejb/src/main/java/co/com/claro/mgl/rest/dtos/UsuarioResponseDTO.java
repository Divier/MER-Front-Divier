package co.com.claro.mgl.rest.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * <b>Nombre: </b> package co.com.claro.mgl.rest.dtos.UsuarioResponseDTO </br>
 * <b>Descripcion: </b> DTO para mapear información del usuario desde la repuesta
 *                      del servicio de autenticacion de portal usuarios </br>
 * <b>Fecha Creación:</b> 27/09/2022 </br>
 * <b>Autor:</b> Manuel Hernández Rivas </br>
 * <b>Fecha de Última Modificación: </b>27/09/2022</br>
 * <b>Modificado por: </b>Manuel Hernández Rivas</br>
 * <b>Brief: </b>87621</br>
 */

@Getter
@Setter
public class UsuarioResponseDTO {
    @JsonProperty("entityClass")
    private String entityClass;

    @JsonProperty("codPerfil")
    private String codPerfil;

    @JsonProperty("descripcion")
    private String descripcion;

    @JsonProperty("estado")
    private String estado;

    @JsonProperty("idPerfil")
    private Long idPerfil;

    @JsonProperty("idUsuario")
    private Long idUsuario;

    @JsonProperty("listRoles")
    private List<LinkRolesResponseDTO> listRoles;

    @JsonProperty("nombre")
    private String nombre;

    @JsonProperty("usuario")
    private String usuario;

    @Override
    public String toString() {
        return "UsuarioResponse{" + "entityClass=" + entityClass + ", codPerfil=" + codPerfil + ", descripcion=" + descripcion + ", estado=" + estado + ", idPerfil=" + idPerfil + ", idUsuario=" + idUsuario + ", listRoles=" + listRoles + ", nombre=" + nombre + ", usuario=" + usuario + '}';
    }
}
