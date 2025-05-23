package co.com.claro.mer.dtos.sp.cursors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * DTO para el usuario de micrositio
 * @author Manuel Hernández
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioMicroSitioDto {

    private BigDecimal idUsuario;
    private String identificacion;
    private String telefono;
    private String usuario;
    private String nombre;
    private BigDecimal idPerfil;
    private String email;
}
