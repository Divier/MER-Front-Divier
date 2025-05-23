package co.com.claro.mer.auth.micrositio.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Representa los datos de un usuario, logueado por micrositio.
 *
 * @author Gildardo Mora
 * @version 1.0, 2024/09/13
 */
@Getter
@Setter
public class UserDataDto {

    private String nombreUsuario;
    private String emailUsuario;
    private String idUsuario;
    private Integer idPerfil;
    private boolean accessRol;
    private boolean viewMapRol;
    private String telefono;
    private String identificacion;

}
