package co.com.claro.mgl.rest.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <b>Nombre: </b> package co.com.claro.mgl.rest.dtos.AutenticaRequestDTO </br>
 * <b>Descripcion: </b> DTO para mapear la peticion del servicio de autenticacion de portal usuarios </br>
 * <b>Fecha Creación:</b> 27/09/2022 </br>
 * <b>Autor:</b> Manuel Hernández Rivas </br>
 * <b>Fecha de Última Modificación: </b>27/09/2022</br>
 * <b>Modificado por: </b>Manuel Hernández Rivas</br>
 * <b>Brief: </b>87621</br>
 */
@Getter
@Setter
public class AutenticaRequestDTO {
    @JsonProperty("usuario")
    private String usuario;

    @JsonProperty("password")
    private String password;

    @JsonProperty("idApp")
    private String idApp;

    @Override
    public String toString() {
        return "AutenticaRequest{" + "usuario=" + usuario + ", password=" + password + ", idApp=" + idApp + '}';
    }
}
