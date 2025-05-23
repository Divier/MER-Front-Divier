/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.telmex.catastro.data;

import co.com.claro.mer.dtos.response.service.UsuarioPortalResponseDto;
import co.com.claro.mgl.rest.dtos.AutenticaResponseDTO;
import co.com.claro.mgl.rest.dtos.UsuarioResponseDTO;
import lombok.*;

/**
 * Gestiona los datos de la sesión del usuario.
 *
 * @author User
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class DatosSesionMgl {

    /**
     * Si el acceso es autorizado
     */
    private boolean accesoAutorizado;
    /**
     * Token de sesión
     */
    private String tokenSession;
    /**
     * Estado de sesión
     */
    private String estadoSesion;
    /**
     * Datos de usuario
     */
    private UsuarioPortalResponseDto usuarioMgl;
    /**
     * Fecha de expiración del token
     */
    private String fechaExpiracionToken;

    public DatosSesionMgl() {
        //crear instancia de clase
    }

    public static final DatosSesionMgl crearDesdeRespuesta (String usuarioLogin, AutenticaResponseDTO response){
        if (response == null) {
            return null;
        }

        UsuarioResponseDTO usuarioJson = response.getUsuario();
        if (usuarioJson == null) {
            return null;
        }
        return DatosSesionMgl.builder()

                .tokenSession(response.getToken_session())
                .estadoSesion(response.getEstado())
                .build();

    }
    
}
