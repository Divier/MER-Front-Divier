package co.com.claro.mer.utils.constants;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

/**
 * Clase que contiene las constantes usadas para las sesiones y login
 * @version 1.0, 01/09/2021
 * @author Manuel Hernández
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SessionConstants {
    public static final String PERFIL_SESION = "perfilsesion";
    public static final String PORTAL_LOGIN = "PortalLongin";
    public static final String COOKIE = "cookie";
    public static final String COOKIE_XML = "cookieXml";
    public static final String DELTA_TIEMPO = "deltaTiempo";
    public static final String LISTA_ROLES = "listaRoles";
    public static final String PREFIJO_ROLES_CARGUE_MASIVO = "CGMSV";
    public static final String FORM_MICROSITIO = "MENU";
    public static final String ROL_ACCESO_MICROSITIO = "factibilidadesMgl";
    public static final String ROL_MAPA_MICROSITIO = "factibilidadesMglMapa";

}
