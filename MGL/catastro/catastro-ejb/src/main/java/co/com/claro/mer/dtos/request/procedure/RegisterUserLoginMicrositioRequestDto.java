package co.com.claro.mer.dtos.request.procedure;

import co.com.claro.mer.annotations.StoredProcedureData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Clase que representa el DTO de request al procedimiento almacenado para registrar la sesión del usuario de micrositio.
 *
 * @author Gildardo Mora
 * @version 1.0, 2025/03/03
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class RegisterUserLoginMicrositioRequestDto {

    @StoredProcedureData(parameterName = "PI_USUARIO ", parameterType = String.class)
    private String user;

    @StoredProcedureData(parameterName = "PI_PERFIL", parameterType = Integer.class)
    private Integer userProfile;

    @StoredProcedureData(parameterName = "PI_SERVIDOR", parameterType = String.class)
    private String serverName;

    @StoredProcedureData(parameterName = "PI_CLUSTER", parameterType = String.class)
    private String clusterName;

}
