package co.com.claro.mer.dtos.request.service;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Representa la estructura de datos del request de autenticación de un usuario
 *
 * @author Gildardo Mora
 * @version 1.0, 2024/08/29
 */
@Getter
@Setter
@AllArgsConstructor
public class AutenticaRequestDto {

    @SerializedName("usuario")
    private String usuario;

    @SerializedName("password")
    private String password;

    @SerializedName("idApp")
    private String idApp;

}
