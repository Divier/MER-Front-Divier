package co.com.claro.mer.constants;

/**
 * Clase para almacenar y leer los nombres de los procedimientos consumidos en la aplicacion.
 *
 * @author Manuel Hernández Rivas
 * @version 1.0, 03/03/2022
 */

public final class StoredProcedureNamesConstants {

    private StoredProcedureNamesConstants() {
        //Impedir instancias invalidas
    }

    public static final String CON_TBL_MASIVOS_SP = "CARGUES_MASIVOS_PKG.CON_TBL_MASIVOS_SP";
    public static final String CON_TBL_LINEAS_MASIVO_SP = "cargues_masivos_pkg.CON_TBL_LINEAS_MASIVO_SP";
    public static final String TBL_LINEAS_MASIVO_SP = "CARGUES_MASIVOS_PKG.TBL_LINEAS_MASIVO_SP";
    public static final String CON_TBL_MASIVOS_NOMBRES_SP="cargues_masivos_pkg.CON_TBL_MASIVOS_NOMBRES_SP";
    public static final String CON_FILTROS_SP="CARGUES_MASIVOS_PKG.CON_FILTROS_SP";
    public static final String LISTA_FILTROS_SP="CARGUES_MASIVOS_PKG.DATA_INPUT_SELECT_SP";
    /* PROCESO DE CREACIÓN HHPP VIRTUAL */
    public static final String SP_CREATE_HHPP_VIRTUAL = "PKG_HHPP_VIRTUAL.CREATE_HHPP_VIRTUAL_SP";
    public static final String CMT_INS_LOGS_MARC_HHPP_SP = "CMT_INS_LOGS_MARC_HHPP_SP";
    public static final String CMT_CONS_LOGS_MARC_HHPP_SP = "CMT_CONS_LOGS_MARC_HHPP_SP";
    public static final String CMT_CONS_MARCACIONES_HHPP_SP = "CMT_CONS_MARCACIONES_HHPP_SP";
    public static final String CMT_ACT_MARCACIONES_HHPP_SP = "CMT_ACT_MARCACIONES_HHPP_SP";
    public static final String CMT_INS_MARCACIONES_HHPP_SP = "CMT_INS_MARCACIONES_HHPP_SP";
    public static final String SP_VAL_PARAM_HAB_CREACION = "VAL_PARAM_HAB_CREACION_SP";
    /* PROCESO CREACION FICHAS*/
    public static final String TEC_CON_PREFICHA_PRC="PKG_CRUD_TEC_PREFICHA_NEW.TEC_CON_PREFICHA_PRC";   
    
    public static final String SP_TEC_CONSULTA_PREFICHA = "PKG_UTILITIES_FICHAS.TEC_CONSULTA_PREFICHA";
    public static final String SP_TEC_INSERT_GEOREFERENCIA_PRC = "PKG_UTILITIES_FICHAS.TEC_INSERT_GEOREFERENCIA_PRC";
    public static final String SP_TEC_UPDATE_PREFICHA_PRC = "PKG_CRUD_TEC_PREFICHA_NEW.TEC_REGISTRA_PREFICHA_PRC";
    public static final String SP_TEC_CONSULTA_TOKEN_DIR = "TEC_CONSULTA_TOKEN_DIR";
    
    
    public static final String CMT_CON_TIP_CERR_ELEC_PRC = "CMT_TIP_CERRAD_ELECT_PKG.CMT_CON_TIP_CERR_ELEC_PRC";
    public static final String CMT_GESTI_SEG_CM_AUD_PRC = "CMT_GESTI_SEG_CM_AUD_PKG.CMT_GESTI_SEG_CM_AUD_PRC";
    public static final String CMT_CON_GESTI_SEG_CM_PRC="CMT_GESTION_SEG_CM_PKG.CMT_CON_GESTI_SEG_CM_PRC";
    public static final String CMT_INS_GESTI_SEG_CM_PRC="CMT_GESTION_SEG_CM_PKG.CMT_INS_GESTI_SEG_CM_PRC";
    
    /*COM_TECHNICALSITESAP_PKG*/
    public static final String SP_CRU_CTECH_CM = "COM_TECHNICALSITESAP_PKG.PRC_CRU_CTECH";
    /**
     * Procedimiento para realizar consulta de parámetros de MER.
     */
    public static final String MGL_CONSULTA_PARAMETRO_MER_SP = "MGL_SCHEME.MGL_CONSULTA_PARAMETRO_MER_SP";
    public static final String MG_CONSULTA_USUARIOS = "MG_USUARIOS_PKG.SET_USER_SP";
    /**
     * Procedimiento para crear HomePassed.
     */
    public static final String CREATE_HHPP_SP = "HHPP_PKG.CREATE_HHPP_SP";
    /* --- Procedimientos para realizar operaciones con parámetros de MER. --- */
    public static final String PARAM_CONSULTA_VALOR_X_ACRONIMO_SP = "MGL_PARAMETROS_PKG.VLR_ACRNMO_SP";
    public static final String PARAM_CONSULTA_TODO_SP = "MGL_PARAMETROS_PKG.CNSLTA_ALL_SP";
    public static final String PARAM_CONSULTA_X_TPO_PARAMETRO_SP = "MGL_PARAMETROS_PKG.TPO_PRMTRO_SP";
    public static final String PARAM_CONSULTA_X_ACRONIMO_SP = "MGL_PARAMETROS_PKG.POR_ACRNMO_SP";
    public static final String PARAM_CONSULTA_X_LIKE_ACRNMO_SP = "MGL_PARAMETROS_PKG.LIKE_ACRNMO_SP";
    public static final String PARAM_CONSULTA_X_ACRONIMO_X_PARAMETRO_SP = "MGL_PARAMETROS_PKG.ACRO_PRAM_SP";
    public static final String PARAM_INSERTA_REGISTRO_SP = "MGL_PARAMETROS_PKG.NEW_RGSTRO_SP";
    public static final String PARAM_ACTUALIZA_REGISTRO_SP = "MGL_PARAMETROS_PKG.UPDTE_SP";
    public static final String PARAM_ELIMINA_REGISTRO_SP = "MGL_PARAMETROS_PKG.ELMNA_SP";
    //consulta de usuarios activos en micrositio
    public static final String GET_USER_INFO_SP = "MICROSITIO_USER_PKG.GET_USER_INFO";
    /**
     * SP para registrar el histórico de la sesión del usuario.
     */
    public static final String INSERT_SESSION = "SESION_PKG.INSERT_SESSION";
    /**
     * SP para registrar el histórico de la sesión del usuario en micrositio.
     */
    public static final String INSERT_MICROSITIO_SESSION = "SESSION_MICROSITIO_PKG.INSERT_SESSION";
    /**
     * Procedimiento para consultar la tabla CMT_SOLIC_NODO_CUADRANTE.
     */
    public static final String CMT_SOL_NODO_CUADRANTE_SP = "PKG_CRUD_NODO_CUAD.CMT_SOL_NODO_CUADRANTE_SP";
    
    /**
     * Procedimiento para actualizar disponibilidad de gestion en la tabla CMT_SOLIC_NODO_CUADRANTE.
     */
    public static final String CMT_UDP_NOD_CUAD_DISP_GS_SP = "PKG_CRUD_NODO_CUAD.CMT_UDP_NOD_CUAD_DISP_GS_SP";
    /**
     * Procedimiento para consultar la tabla CMT_SOLIC_NODO_CUADRANTE desde FRONT de MER.
     */
    public static final String CMT_SOL_NODO_CUAD_FRONT_SP = "PKG_CRUD_NODO_CUAD.CMT_SOL_NODO_CUAD_FRONT_SP";
}
