package co.com.claro.mer.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum para gestionar los acrónimos de la tabla de parámetros de MER
 * MGL_PARAMETROS
 *
 * @author Gildardo Mora
 * @version 1.0, 2022/10/14
 */
@Getter
@AllArgsConstructor
public enum ParametrosMerEnum {

    /* Acrónimo de parámetro encargado de contener los códigos de tecnologías a las que se le impide
     * la creación de solicitud de creación de HHPP de barrio abierto con numeración en complemento casa  */
    TECNOLOGIAS_BLOQUEO_SOLICITUD_HHPP("TECNOLOGIAS_BLOQUEO_SOLICITUD_HHPP"), //Acrónimo del parámetro en BD del tiempo mínimo servicio en
    // el proceso de marcaciones para creación de HHPP Virtual.
    TIEMPO_MIN_CUENTA_HHPP_VT("HHPP_VIRTUAL_TIEMPO_MIN_CUENTA"),
    /* BASE URI de servicios rest para procesos de creación de HHPP Virtual*/
    //Acrónimo del parámetro URI de servicio getMoveReasonsResource
    BASE_URI_GET_MOVE_REASONS_RESOURCE("BASE_URI_REST_GET_MOVE_REASONS_RESOURCE"),
    //Acrónimo del parámetro URI base de los servicios de rsResourcesLocation
    BASE_URI_RS_RESOURCES_LOCATION_REST("BASE_URI_RS_RESOURCES_LOCATION_REST"),
    //Acrónimo del parámetro que contiene los codigos de bloqueo válidos para crear HHPP Virtual
    LOCK_CODES_TO_CREATE_VIRTUAL_HHPP("LOCK_CODES_TO_CREATE_VIRTUAL_HHPP"),
    //Acrónimo del parámetro que contiene los estados de HHPP válidos para Traslado de HHPP bloqueado
    STATUS_CODES_HHPP_VALIDATE_SERVICE("STATUS_CODES_HHPP_VALIDATE_SERVICE"),
    //Acrónimo para definir el identificador de respuesta de CCMM
    ACCION_SOLICITUD_HHPP_VIRTUAL_CCMM("ACCION_SOLICITUD_HHPP_VIRTUAL_CCMM"),
    RESPUESTA_ACCION_SOLICITUD_HHPP_VIRTUAL_CCMM("RESPUESTA_ACCION_SOLICITUD_HHPP_VIRTUAL_CCMM"),
    //Acrónimo de flag que permite activar / desactivar proceso de traslado HHPP Bloqueado
    FLAG_CCMM_HABILITAR_TRASLADO_HHPP_BLOQUEADO("FLAG_CCMM_HABILITAR_TRASLADO_HHPP_BLOQUEADO"),
    FLAG_HHPP_HABILITAR_TRASLADO_HHPP_BLOQUEADO("FLAG_HHPP_HABILITAR_TRASLADO_HHPP_BLOQUEADO"),
    /* ------- Parámetros para envío de correos ----------- */
    MAIL_SMTPSERVER("MAIL_SMTPSERVER"),
    MAIL_FROM("MAIL_FROM"),
    MAIL_AUTH_FLAG("MAIL_AUTH_FLAG"),
    MAIL_TLS_FLAG("MAIL_TLS_FLAG"),
    MAIL_FROM_PASSWORD("MAIL_FROM_PASSWORD"),
    MAIL_SMTPPORT("MAIL_SMTPPORT"),
    /* ------ Parámetros de SITIDATA ------ */
    IP_WS_GEO("IP_WS_GEO"),
    /* ----- Parámetros asociados a roles en la aplicación ------ */
    FLAG_VALIDACION_ROLES("FLAG_VALIDACION_ROLES"),
    /* ----- Capa de servicios de MER -----*/
    BASE_URI_RESTFULL_BASICA("BASE_URI_RESTFULL_BASICA"),
    /* ---- Portal Usuarios-----*/
    BASE_URI_AUTENTICACION("BASE_URI_AUTH_PORTAL_USUARIOS"),
    ENDPOINT_AUTENTICACION("ENDPOINT_AUTENTICACION"),
    BASE_URI_REST_USUARIOS("BASE_URI_REST_USUARIOS"),
    /* ----- Configuración de procesado de parametros de MER ----- */
    /**
     * Flag para activar (1) / desactivar (0), el manejo de parámetros de MER
     * en memoria, durante la sesión del usuario.
     */
    FLAG_STORE_PARAMETERS_IN_CACHE("FLAG_STORE_PARAMETERS_IN_CACHE"),
    /* ----- Parámetros asociados a bloqueo de generación de reportes ----- */
    /**
     * Flag para BLOQUEAR (1) / DESBLOQUEAR (0), la generación de reportes en
     * la aplicación.
     */
    FLAG_BLOCK_REPORT_GENERATION("FLAG_BLOCK_REPORT_GENERATION"),
    /**
     * Usuarios autorizados para generar reportes, mientras está activo el bloqueo.
     */
    USERS_AUTHORIZED_TO_GENERATE_REPORTS("USERS_AUTHORIZED_TO_GENERATE_REPORTS"),
    /**
     * Mensaje complementario que se mostrará en el popup de bloqueo de generación de reportes.
     */
    MESSAGE_POPUP_BLOCK_REPORT("MESSAGE_POPUP_BLOCK_REPORT"),
    DESTINATARIOS_CORREO_BLOQUEO_GEN_REPORTES("DESTINATARIOS_CORREO_BLOQUEO_GEN_REPORTES"),
    FLAG_NOTIFICAR_GENERACION_REPORTES("FLAG_NOTIFICAR_GENERACION_REPORTES"),
    /* ---- Parámetros asociados al servicio de correos de torre de control ----- */
    URL_EMAIL_SERVICE_TORRE_CONTROL("URL_EMAIL_SERVICE_TORRE_CONTROL"),
    PASS_EMAIL_SERVICE_TORRE_CONTROL("PASS_EMAIL_SERVICE_TORRE_CONTROL"),
    USER_EMAIL_SERVICE_TORRE_CONTROL("USER_EMAIL_SERVICE_TORRE_CONTROL"),
    SYSTEM_EMAIL_SERVICE_TORRE_CONTROL("SYSTEM_EMAIL_SERVICE_TORRE_CONTROL"),

    /* ---- Parámetros para proceso de notificación de errores de MER --- */
    DESTINATARIOS_NOTIFICAR_ERROR_MER("DESTINATARIOS_NOTIFICAR_ERROR_MER"),
    FLAG_NOTIFICAR_ERRORES_MER("FLAG_NOTIFICAR_ERRORES_MER"),
     /* -- valores de asociación de identificadores de app para info técnica CCMM --- */
    IDENTIFICADORES_INFO_TECNICA_CCMM("IDENTIFICADORES_INFO_TECNICA_CCMM"),
    /* -- LOGIN --*/
    BASE_URI_MAIN_LOG_IN("BASE_URI_MAIN_LOG_IN"),
    BASE_URI_LOG_IN_MGL("BASE_URI_LOG_IN_MGL"),
    FEATURE_FLAG_LOG_IN_MGL("FEATURE_FLAG_LOG_IN_MGL"),
    APP_NAME("APP_NAME_LOGIN_PU"),
    MICROSITIO_APP_NAME("MICROSITIO_APP_NAME")
    ;

    /* --------------------------------------------- */
    private final String acronimo;
}
