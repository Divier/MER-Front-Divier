package co.com.claro.mer.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Nombres de servicios expuestos a MER
 * @author Manuel Hernández Rivas
 * @version 1.0, 2024/09/02
 */
@AllArgsConstructor
@Getter
public enum ServicesEnum {

    CONSULTA_INFO("consultaInfo"),
    CONSULTA_ROLES_USER("consultaRolesPorAplicativoUsuario"),
    CONSULTA_ROLES_PERFIL("consultaRolesPorAplicativoPerfil"),
    AUTENTICA("authentication/autentica")
    ;

    private final String name;
}
