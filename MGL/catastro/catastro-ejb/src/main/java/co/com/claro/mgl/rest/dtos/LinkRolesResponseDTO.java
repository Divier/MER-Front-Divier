package co.com.claro.mgl.rest.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <b>Nombre: </b> package co.com.claro.mgl.rest.dtos.LinkRolesResponseDTO </br>
 * <b>Descripcion: </b> DTO para mapear roles desde la repuesta
 *                      del servicio de autenticacion de portal usuarios </br>
 * <b>Fecha Creación:</b> 27/09/2022 </br>
 * <b>Autor:</b> Manuel Hernández Rivas </br>
 * <b>Fecha de Última Modificación: </b>27/09/2022</br>
 * <b>Modificado por: </b>Manuel Hernández Rivas</br>
 * <b>Brief: </b>87621</br>
 */
@Getter
@Setter
public class LinkRolesResponseDTO {
    @JsonProperty("entityClass")
    private String entityClass;

    @JsonProperty("codRol")
    private String codRol;

    @JsonProperty("descripcionRol")
    private String descripcionRol;

    @JsonProperty("estadoRol")
    private String estadoRol;

    @JsonProperty("idPerfil")
    private Long idPerfil;

    @JsonProperty("idRolPerfil")
    private String idRolPerfil;

    @JsonProperty("fechaCambio")
    private String fechaCambio;

    @JsonProperty("usuModifico")
    private String usuModifico;

    @Override
    public String toString() {
        return "LinkRolesResponse{" + "entityClass=" + entityClass + ", codRol=" + codRol + ", descripcionRol=" + descripcionRol + ", estadoRol=" + estadoRol + ", idPerfil=" + idPerfil + ", idRolPerfil=" + idRolPerfil + ", fechaCambio=" + fechaCambio + ", usuModifico=" + usuModifico + '}';
    }
}
