package co.com.telmex.catastro.services.util;

import java.math.BigDecimal;

/**
* Clase Constant
*
* @author Ana María Malambo
* @version       1.0
*/
public class Constant {

    private Constant() {
        //Impedir instancias invalidas.
    }

    public static final String HOST_REDIR_VT = "HOTS_PUBLIC_SERVICES";//variable sitidata desde claro
    public static final String PROPERTY_URL_WSRR = "PROPERTY_URL_WSRR";//variable servicio unitApi desde claro
    public static final String BASE_URI_RESTFUL = "BASE_URI_RESTFUL";//varible servicio cuentasMatrices desde claro
    public static final String BASE_URI_REST_AGENDA = "BASE_URI_REST_AGENDA_PRODUCCION";
    public static final String FEATURE_FLAG_LOG_IN_MGL = "FEATURE_FLAG_LOG_IN_MGL";
    public static final String RUTA_LOG_SERVER ="RUTA_LOG_SERVER_PRODUCCION";
    public static final String BASE_URI_WS_GEODIR_SPRING = "BASE_URI_WS_GEODIR_SPRING";//variable servicio WS_GEODIR
    public static final String DIR_BATCH_XML_WSGEO = "DIR_BATCH_XML_WSGEO";//Cantidad de direcciones a procesar por lote
    public static final String MENU_CARGUE_FICHAS_VALIDATION_ENABLED = "MENU_CARGUE_FICHAS_VALIDATION_ENABLED";//flag para mostrar menu de Cargue de Fichas
    //CONSTANTES DE DESARROLLO PARA RR

    /**
     * Flag que determina si se realizar&aacute; o no la validaci&oacute;n del
     * Token en el proceso de autenticaci&oacite;n.
     */
    public static final String LOG_IN_TOKEN_VALIDATION_ENABLED = "LOG_IN_TOKEN_VALIDATION_ENABLED";
    /** URL del servicio de autenticaci&oacute;n a trav&eacute;s del m&oacute;dulo de agendamiento. */
    public static final String URL_SERVICE_LOGIN = "URL_SERVICE_LOGIN";
    public static final String URL_WS_ONYX = "URL_WS_ONYX";
    public static final String RUTA_ALMACENAR_TEMP_ARCHIVO_TCRM = "RUTA_ALMACENAR_TEMP_ARCHIVO_TCRM";
    /**
     * Ruta f&iacute;sica del archivo properties de los par&aacute;metros
     * requeridos para la conexi&oacute;n con AS400.
     */
    public static final String AS400_PROPERTIES_FILE_PATH = "AS400_PROPERTIES_FILE_PATH";

    /** Path para p&aacute;gina de autenticaci&oacute;n. */
    public static final String BASE_URI_LOG_IN_MGL = "BASE_URI_LOG_IN_MGL";
    /** Path para p&aacute;gina home (main). */
    public static final String BASE_URI_MAIN_LOG_IN = "BASE_URI_MAIN_LOG_IN";
    public static final String URL_SERVICE_PCML = "URL_SERVICE_PCML";//varible sitidata desde claro
    public static final String BASE_URI_REST_GET_ETA = "BASE_URI_REST_GET_ETA";
    public static final String BASE_URI_REST_GET_ETA_ACTUAL = "BASE_URI_REST_GET_ETA_ACTUAL";
    /**
     *
     */
    public static final String ESTADO_PROCESO_RED_RR = "RR";
    /**
     *
     */
    public static final String PROPERTY_SERVICE_WSRR = "PROPERTY_SERVICE_WSRR";
    /**
     *
     */
    public static final String ESTADO_PROCESO_RED_REPOSITORIO = "REPOSITORIO";
    /**
     *
     */
    public static final String ESTADO_PROCESO_RED_TERMINADA = "TERMINADA";
    /**
     *
     */
    public static String JNDI_DATASOURCES = "jndidev";
    /**
     *
     */
    public static final String NIVEL_SOCIO_NO_GEO = "NG";
    /**
     *
     */
    public static final String NIVEL_SOCIO_COMERCIAL = "0";
    /**
     *
     */
    public static final String NIVEL_SOCIO_COMERCIAL_RR = "NR";
    /**
     *
     */
    public static String RR_HHPP_EXISTE = "E";
    /**
     *
     */
    public static final String ESTADO_SPLRED_INICIAL = "N";
    /**
     *
     */
    public static String ESTADO_SPLRED_DETALLEINICIAL = "C";
    /**
     *
     */
    public static String ESTADO_SPLRED_TERMINADO = "T";
    /**
     *
     */
    public static final String ESTADO_SON_CERRADA = "CERRADA";
    /**
     *
     */
    public static final String ESTADO_SON_CREADA = "CREADA";
    /**
     *
     */
    public static final String ESTADO_RED_CREADA = "CREADA";
    /**
     *
     */
    public static String ESTADO_RED_MODIFICACION = "MODIFICACION";
    /**
     *
     */
    public static final String ESTADO_SON_RECHAZADA = "RECHAZADA";
    /**
     *
     */
    public static final String ESTADO_SON_PROCESADA = "PROCESADA";
    /**
     *
     */
    public static final String ESTADO_SON_ARCH_GENERADO = "ARCHIVO GENERADO";
    /**
     *
     */
    public static String ESTADO_SRE_CERRADA = "CERRADA";
    /**
     *
     */
    public static String ESTADO_SRE_CREADA = "CREADA";
    /**
     *
     */
    public static final String ESTADO_SRE_ARCH_GENERADO = "ARCHIVO GENERADO";
    /**
     *
     */
    public static String SON_EXISTE = "EXISTE";
    /**
     *
     */
    public static String SON_NO_EXISTE = "NO EXISTE";
    /**
     *
     */
    public static final String SON_TDIRECCION_NOESTANDARIZABLE = "NO ESTANDARIZABLE";
    /**
     *
     */
    public static final String SON_TDIRECCION_ESTANDARIZABLE = "ESTANDARIZABLE";
    /**
     *
     */
    public static final String TIPO_SON_HHPPUNI = "CREAUNIDIRECCIONAL";//64
    /**
     *
     */
    public static String TIPO_SON_HHPPCTAMATRIZ = " CREACIÓN DE HHPP EN CUENTA MATRIZ";//62
    /**
     *
     */
    public static final String TIPO_SON_VERCASA = "CREABIDIRECCIONAL";//63
    /**
     *
     */
    public static final String TIPO_SON_TODAS = "TODAS";
    /**
     *
     */
    public static final String TIPO_SON_GRID = "MDFGRID";
    /**
     *
     */
    public static final String TIPO_SON_CAMBIODIR = "MDFCAMBIODIR";
    /**
     *
     */
    public static final String TIPO_SON_VERIFICACION = "MDFVERIFICACION";
    /**
     *
     */
    public static final String TIPO_SON_CAMBIOEST = "MDFCAMBIOEST";
    /**
     *
     */
    public static final String TIPO_SON_CAMBIONOD = "MDFCAMBIONOD";
    /**
     *
     */
    public static String TIPO_SON_MODIFICACION = "MODIFICACION";
    /**
     *
     */
    public static final String SON_VERCASA_ESTADOUNI = "N";
    /**
     *
     */
    public static final String SON_HHPPUNI_ESTADOUNI = "E";
    /**
     *
     */
    public static final String BOGOTA = "BOGOTA";
    /**
     *
     */
    public static final String ZBO = "ZBO";
    /**
     *
     */
    public static final String CCB = "CCB";
    /**
     *
     */
    public static final String SPV = "SPV";
    /**
     *
     */
    public static final String ROL_CGV = "CGV";
    /**
     *
     */
    public static final String TIPO_MALLA_ANTIGUA = "ANTIGUA";
    /**
     *
     */
    public static String TIPO_MALLA_NUEVA = "NUEVA";
    /**
     *
     */
    public static final String DIR_NO_EXISTE_MSJ = "NO EXISTE";
    /**
     *
     */
    public static final String DIR_SI_EXISTE_MSJ = "SI EXISTE";
    /**
     *
     */
    public static final String TIPO_DIRECCION_PRINCIPAL = "PRINCIPAL";
    /**
     *
     */
    public static final String UPLOAD_ARCHIVO = "UPLOAD_ARCHIVO";
    /**
     *
     */
    public static final String ROOT_UPLOAD = "ROOT_UPLOAD";
    /**
     *
     */
    public static final String UPLOAD_MASIVOS = "UPLOAD_MASIVOS";
    /**
     *
     */
    public static final String TIPO_DIRECCION_ALIAS = "ALIAS";
    /**
     *
     */
    public static final String TIPO_CIUDAD_MULTIORIGEN = "1";
    /**
     *
     */
    public static final BigDecimal ID_TDI_ALIAS = BigDecimal.ONE;
    /**
     *
     */
    public static final BigDecimal ID_TDI_PRINCIPAL = new BigDecimal("2");
    /**
     *
     */
    public static final BigDecimal ID_TGE_LOCALIDAD = BigDecimal.ONE;
    /**
     *
     */
    public static final BigDecimal ID_TGE_BARRIO = new BigDecimal("2");
    /**
     *
     */
    public static final BigDecimal ID_TGE_MANZANA = new BigDecimal("3");
    /**
     *
     */
    public static String SISTEMA_CREA_APLICATIVO = "APLICATIVO";
    /**
     *
     */
    public static final String PREFIJO_COMPLEMENTO = "AP";
    /**
     *
     */
    public static final String PREFIJO_COMPLEMENTO_APARTAMENTO = " AP ";
    //VARIABLES PARA CREAR ARCHIVO PARA RR DE CREACION DE HHPP
    /**
     *
     */
    public static final String CODIGO_VENDEDOR_RR = "9999";
    /**
     *
     */
    public static final String CABLE_RR = "R6";
    /**
     *
     */
    public static final String CAMPO_ND_RR = "ND";
    /**
     *
     */
    public static final String VACIO_RR = "";
    /**
     *
     */
    public static final String STRING_VACIO = "";
    /**
     *
     */
    public static final String NG_RR = "NG";
    /**
     *
     */
    public static final int LONGITUD_COD_SERVINFORMACION_BARRIO_MANZANA_CASA = 99;
    /**
     *
     */
    public static final String BARRIO_MANSANA_CASA = "1";
    //Separador para los archivos generados para RR
    /**
     *
     */
    public static final String SEPARATOR = ",";
    //Variables para envio de correos
    public static final String DOMINIO = "DOMINIO";
    //Variables para valores por defecto en creacion de solicitudes
    /**
     *
     */
    public static final String ID_THC = "ID_THC";
    //Variable para parametros de Tipo Ubicacion
    /**
     *
     */
    public static final String ID_CASA = "ID_CASA";
    /**
     *
     */
    public static final String ID_EDIFICIO = "ID_EDIFICIO";
    /**
     *
     */
    public static final String CREATE_SOLICITUD = "Solicitud creada con exito";
    /**
     *
     */
    public static final int PORCENTAJE_CONFIABILIDAD_ALTA = 95;
    /**
     *
     */
    public static final String OBLIGATORIO_PAIS = "El país es obligatorio, por favor diligencielo.";
    /**
     *
     */
    public static final String OBLIGATORIO_CIUDAD = "La ciudad es  obligatoria, por favor diligenciela.";
    /**
     *
     */
    public static final String OBLIGATORIO_DIRECCION = "Debe ingresar una dirección para validar.";
    /**
     *
     */
    public static final String OBLIGATORIO_TIPO_CONSULTA = "Se debe seleccionar un tipo de consulta.";
    /**
     *
     */
    public static final String OBLIGATORIO_TIPO_FILTRO = "Se debe seleccionar un tipo de filtro.";
    /**
     *
     */
    public static final String OBLIGATORIO_REGION = "La regional es  obligatoria, por favor diligenciela.";
    /**
     *
     */
    public static final String OBLIGATORIO_DEPARTAMENTO = "El departamento es obligatorio, por favor diligencielo.";
    /**
     *
    */
    public static final String OBLIGATORIO_BARRIO = "El barrio es obligatorio, por favor diligencielo.";
    /**
     *
     */
    public static final String TIPO_CONSULTA_SENCILLA = "S";
    /**
     *
     */
    public static final String TIPO_CONSULTA_COMPLETA = "C";
    /**
     *
     */
    public static final String CODIGO_TIPO_CIUDAD_CALI = "C";
    /**
     *
     */
    public static final String CODIGO_TIPO_CIUDAD_MEDELLIN = "M";
    /**
     *
     */
    public static final String CODIGO_TIPO_CIUDAD_BOGOTA = "B";
    /**
     *
     */
    public static final String COMMON_TRUE = "TRUE";
    /**
     *
     */
    public static final String COMMON_FALSE = "FALSE";
    //Consulta masiva mensajes de error
    /**
     *
     */
    public static final String OBLIGATORIO_SEGMENTO_1 = "El segmento 1 es obligatorio, por favor verifique.";
    /**
     *
     */
    public static final String OBLIGATORIO_SEGMENTO_2 = "El segmento 2 es obligatorio, por favor verifique.";
    /**
     * Mensajes de error Standarizados
     */
    public static final String MESSAGE_INVALID_DIR = "Dirección invalida, por favor verifique";
   /**
     *
     */
    public static String ESTADO_SON_INICIAL;
    /**
     *
     */
    public static final String GEODATA_FUERA_DE_SERVICIO = "Geodata fuera de servicio.";
    /**
     * Nombres de tablas en Base de datos
     */
    public static final String SOLICITUD_NEGOCIO = "SOLICITUD_NEGOCIO";
    /**
     *
     */
    public static String ALIAS_UBICACION = "ALIAS_UBICACION";
    /**
     *
     */
    public static String AREA = "AREA";
    /**
     *
     */
    public static String ATRIBUTO = "ATRIBUTO";
    /**
     *
     */
    public static String CATALOG_ADC = "CATALOG_ADC";
    /**
     *
     */
    public static String CATALOG_DETAIL = "CATALOG_DETAIL";
    /**
     *
     */
    public static String CATALOG_FILTER = "CATALOG_FILTER";
    /**
     *
     */
    public static String CATALOG_RESTRICTION = "CATALOG_RESTRICTION";
    /**
     *
     */
    public static String CATALOG_USER = "CATALOG_USER";
    /**
     *
     */
    public static String DETALLE_SOLICITUD = "DETALLE_SOLICITUD";
    /**
     *
     */
    public static final String DIRECCION_ALTERNA = "DIRECCION_ALTERNA";
    /**
     *
     */
    public static final String DIRECCION = "DIRECCION";
    /**
     *
     */
    public static String APARTAMENTO = "A";
    /**
     *
     */
    public static String CASA = "C";
    /**
     *
     */
    public static String DISTRITO = "DISTRITO";
    /**
     *
     */
    public static String DIVISIONAL = "DIVISIONAL";
    /**
     *
     */
    public static String ESTADO_HHPP = "ESTADO_HHPP";
    /**
     *
     */
    public static String ESTADO_SOLICITUD_NEGOCIO = "ESTADO_SOLICITUD_NEGOCIO";
    /**
     *
     */
    public static String ESTADO_SOLICITUD_RED = "ESTADO_SOLICITUD_RED";
    /**
     *
     */
    public static String ESTADOS_SOLICITUD = "ESTADOS_SOLICITUD";
    /**
     *
     */
    public static String GEOGRAFICO = "GEOGRAFICO";
    /**
     *
     */
    public static String GEOGRAFICO_POLITICO = "GEOGRAFICO_POLITICO";
    /**
     *
     */
    public static String GRUPO_MULTIVALOR = "GRUPO_MULTIVALOR";
    /**
     *
     */
    public static final String HHPP = "HHPP";
    /**
     *
     */
    public static String REGISTRO_MODIFICACION_RED = "CREACION MODIFICACION RED";
    /**
     *
     */
    public static String HISTORICO_UBICACION = "HISTORICO_UBICACION";
    /**
     *
     */
    public static String MARCAS = "MARCAS";
    /**
     *
     */
    public static String MARCAS_HHPP = "MARCAS_HHPP";
    /**
     *
     */
    public static String MENU = "MENU";
    /**
     *
     */
    public static String MULTIVALOR = "MULTIVALOR";
    /**
     *
     */
    public static String NODO = "NODO";
    /**
     *
     */
    public static String RESTRICCION = "RESTRICCION";
    /**
     *
     */
    public static String ROL = "ROL";
    /**
     *
     */
    public static String ROL_MENU = "ROL_MENU";
    /**
     *
     */
    public static final String SOLICITUD_RED = "SOLICITUD_RED";
    /**
     *
     */
    public static final String SUB_DIRECCION = "SUB_DIRECCION";
    /**
     *
     */
    public static String TIPO_DIRECCION = "TIPO_DIRECCION";
    /**
     *
     */
    public static String TIPO_GEOGRAFICO = "TIPO_GEOGRAFICO";
    /**
     *
     */
    public static String TIPO_HHPP = "TIPO_HHPP";
    /**
     *
     */
    public static String TIPO_HHPP_CONEXION = "TIPO_HHPP_CONEXION";
    /**
     *
     */
    public static String TIPO_HHPP_RED = "TIPO_HHPP_RED";
    /**
     *
     */
    public static String TIPO_MARCAS = "TIPO_MARCAS";
    /**
     *
     */
    public static String TIPO_UBICACION = "TIPO_UBICACION";
    /**
     *
     */
    public static String TIPO_VIVIENDA = "TIPO_VIVIENDA";
    /**
     *
     */
    public static String TRANSICION_EDIFICIO = "TRANSICION_EDIFICIO";
    /**
     *
     */
    public static String TRANSICION_HHPP = "TRANSICION_HHPP";
    /**
     *
     */
    public static String UBICACION = "UBICACION";
    /**
     *
     */
    public static String UNIDAD_GESTION = "UNIDAD_GESTION";
    /**
     *
     */
    public static String USUARIO = "USUARIO";
    /**
     *
     */
    public static String USUARIO_ROL = "USUARIO_ROL";
    /**
     *
     */
    public static String VIA = "VIA";
    /**
     *
     */
    public static String ZONA = "ZONA";
    /**
     *
     */
    public static final String NODO_UNIDIRECCIONAL = "U";
    /**
     *
     */
    public static BigDecimal HHPP_UNIDIRECCIONAL = BigDecimal.ONE;
    /**
     *
     */
    public static final BigDecimal HHPP_BIDIRECCIONAL = new BigDecimal("2");

    /*
     *
     */
    public static final BigDecimal HHPP_TIPO_CONEXION_CAJA_RELIANCE = new BigDecimal("2");
    /**
     *
     */
    public static final String NODO_BIDIRECCIONAL = "B";
    /**
     *
     */
    public static final String NODO_NFI = "NFI";
    /**
     * FUENTES DE DIRECCION
     */
    public static final String DIR_FUENTE_ANTIGUA = "ANTIGUA";
    /**
     *
     */
    public static final String DIR_FUENTE_NUEVA = "NUEVA";
    /**
     *
     */
    public static final String DIR_ESTADO_INTRADUCIBLE = "C";
    /**
     *
     */
    public static final String DIR_INTRADUCIBLE = "INTRADUCIBLE";
    /**
     * Operaciones con auditoria
     */
    public static final String INSERT = "INSERT";
    /**
     *
     */
    public static final String UPDATE = "UPDATE";
    /*Grupos multivalor**/
    /**
     *
     */
    public static final String GMULTI_AREA = "15";
    /**
     *
     */
    public static final String GMULTI_APTOS = "16";
    /**
     *
     */
    public static final String TIPO_MARCA_BLACK = "1";//        Listas Negras
    /**
     *
     */
    public static String TIPO_MARCA_WHITE = "2";//        Listas Blancas
    /*Tipos de Respuesta a Solicitud de Creación de HHPP*/
    /**
     *
     */
    public static final String SON_TIPO_SOL_HHPPCREADO = "HHPP CREADO";
    /**
     *
     */
    public static final String SON_TIPO_SOL_EXISTENTE = "HHPP YA EXISTE";
    /**
     *
     */
    public static final String SON_TIPO_SOL_MALREALIZADA = "SOLICITUD MAL REALIZADA";
    /* Valor por defecto de la máxima cantidad de egistros a mostrar en pantalla */
    /**
     *
     */
    public static final String CANT_MAXIMA_REGISTROS_DEFAULT_VAUE = "1000";
    /* Constantes para la generación de archivos  */
    /**
     *
     */
    public static final String FILE_RR_HE = "HE";
    /**
     *
     */
    public static final String FILE_RR_CT = "CT";
    /**
     *
     */
    public static final String FILE_RR_SIGLA_ND = "ND";
    /**
     *
     */
    public static final String FILE_RR_VENDEDOR = "9999";
    /**
     *
     */
    public static final String FILE_RR_CTA = "0";
    /**
     *
     */
    public static final String FILE_RR_ADD = "..........";
    /**
     *
     */
    public static final String FILE_RR_VACIO = "";
    /**
     *
     */
    public static final String SIZE_FILE = "SIZE_FILE";
    /**
     *
     */
    public static final String TYPE_SOLICITUD_FICHANODO = "FICHA_NODO";
    /**
     *
     */
    public static String CUADRANTE_NORTE = "NORTE";
    /**
     *
     */
    public static String CUADRANTE_SUR = "SUR";
    /**
     *
     */
    public static final String CUADRANTE_ESTE = "ESTE";
    /**
     *
     */
    public static final String CUADRANTE_OESTE = "OESTE";
    /*
     * 
     */
    public static final String HHPP_STATE_NUNCA = "N";
    /**
     *
     */
    public static final String TIPO_UNIDAD_COMERCIAL = "K";
    /*
     * 
     */

    public static enum TYPO_DIR {

        V, CK, BM, IT
    }
    public static String COLUMN_PF_PLACAUNIDA = "PLACAUNIDA";
    public static String COLUMN_PF_BLOCKNAME = "BLOCKNAME";
    public static String COLUMN_PF_PISOS = "PISOS";
    public static String COLUMN_PF_NOMBRE_CONJ = "NOMBRE_CONJ";
    public static String COLUMN_PF_NUM_CASAS = "NUM_CASAS";
    public static String COLUMN_PF_APTOS = "APTOS";
    public static String COLUMN_PF_OFICINAS = "OFICINAS";
    public static String COLUMN_PF_LOCALES = "LOCALES";
    public static final String FLAG_PROCESHILO_ELIMINARMASIVOHHPP = "FLAG_PROCESHILO_ELIMINARMASIVOHHPP";
    
    /*
     * Ruta donde se almacenar&aacute;n los archivos de cargue en el servidor.
     * (Default).
     */
    public static final String URL_STORE_FILE = "URL_STORE_FILE";
    /*
     * Ruta donde se almacenar&aacute;n los archivos de cargue en el servidor.
     * (Cambio de Estrato).
     */
    public static final String URL_STORE_FILE_CAMBIO_ESTRATO = "UPLOAD_ARCHIVOS_CAMBIO_ESTRATO";
    /**
     * Ruta donde se almacenar&aacute;n los archivos de cargue en el servidor.
     * (Visita T&eacute;cnica).
     */
    public static String URL_STORE_FILE_VT = "FOLDER_FILES_CM_VT";
    /*
     * Cadena para realizar Hash requerido para servidor de cargue de archivos.
     */
    public static final String MULTIPASS_SERVER = "MULTIPAS_SERVER";
    /**
     * URL completa de Multiserver (URL de cargue de archivos).
     */
    public static final String MULTISERVER_URL = "MULTISERVER_URL";
    
    /*
     * Constantes para la creacion de la llamada de servicio para agendemiento automatico
     */
    public static final String SERVICE_CALL_BOOKING_TIME_CODE = "SERVICE_CALL_BOOKING_TIME_CODE";
    /*
     * 
     */
    public static final String SERVICE_CALL_REASON_FOR_SERVICE = "SERVICE_CALL_REASON_FOR_SERVICE";
    /*
     * 
     */
    public static final String SERVICE_CALL_WORK_FORCE_CODE = "SERVICE_CALL_WORK_FORCE_CODE";
    /*
     * 
     */
    public static final String SERVICE_CALL_TRUCK_REQUIRED_FLAG = "SERVICE_CALL_TRUCK_REQUIRED_FLAG";
    /*
     * 
     */
    public static final String SERVICE_CALL_PRIORITY = "SERVICE_CALL_PRIORITY";
    /*
     * 
     */
    public static final String SERVICE_CALL_NY_DEFAULT = "SERVICE_CALL_NY_DEFAULT";
    /*
     * 
     */
    public static final String SERVICE_CALL_BOOKING_SEQ = "SERVICE_CALL_BOOKING_SEQ";
    /*
     * 
     */
    public static final String SERVICE_CALL_REQUEST_TIME = "SERVICE_CALL_REQUEST_TIME";
    /*
     * Constantes para la creacion de la llamada de servicio para agendemiento automatico
     */
    public static final String AGENDA_SERVICE_CALL_IDPROGRAMACION = "AGENDA_SERVICE_CALL_IDPROGRAMACION";
    /*
     * 
     */
    public static final String AGENDA_SERVICE_CALL_IDTIPOTRABAJO = "AGENDA_SERVICE_CALL_IDTIPOTRABAJO";
   /*
     * 
     */
    public static final String ACRONIMO_COMUNIDAD_DTH_LTE = "ACRONIMO_COMUNIDAD_DTH_LTE";
    /***
     * busca el acronimo en la tabla de parametros para distincion de direcciones y subdirecciones
     */
    public static final String IDENTIFICADOR_DIR_SUBDIR = "IDENTIFICADOR_DIR_SUBDIR";
    /***
     * complementos que seran añadidos al coddir devuelto por el geodata que no traduce correctamente
     * los complementos.
     */
    public static final String GEODATA_EXCLUSION_COMPLEMENTO = "GEODATA_EXCLUSION_COMPLEMENTO";
    
    /**
     * Service Onyx -- Consulta de OT Onyx
     */
    public static final String SERVICE_WS_ONYX = "SERVICE_WS_ONYX";
    
    /**
     * Id Centro poblado  Bogota
     */
    public static final String BOGOTA_CP_ID = "24926";
    
    /**
     * Nombre tarea programada actualizacion de Hhpp en MGL
     */
    public static final String TAREA_PROGRAMADA_HHPP_UPDATE = "TAREA_PROGRAMADA_HHPP_UPDATE";
    
    /**
     * Path utilizado para conformar la URL del servicio de SitiData.
     */
    public static final String PATH_SITIDATA = "PATH_SITIDATA";
    
    public static final String BASE_URI_TECNICOS_ANTICIPADOS = "BASE_URI_TECNICOS_ANTICIPADOS";
    
    public static final String PAR_WORKTIME = "PAR_WORKTIME";
    
    public static final String PAR_WORKZONE = "PAR_WORKZONE";
    
    public static final String PAR_WORKSKILL = "PAR_WORKSKILL";
    
    public static final String PAR_RESOURCE_PREFERENCE = "PAR_RESOURCE_PREFERENCE";
    
    public static final String BASE_URI_ENVIO_CORREOS = "BASE_URI_ENVIO_CORREOS";
    public static final String NOMBRE_CLIENTE_ONIX = "$NOMBRE_CLIENTE_ONIX,";

    public static final String VARIABLES_AGENDA_TRADICIONAL = NOMBRE_CLIENTE_ONIX
            + "$NIT_CLIENTE_ONIX,$OT_HIJA_ONIX,$DIR_CLIENTE_ONIX,$TIPO_OT,"
            + "$SUB_TIPO_OT,$FECHA_AGENDADA,$FRANJA_AGENDADA,"
            + "$PER_RECIBE_VISITA,$TEL_RECIBE_VISITA";

    public static final String VARIABLES_AGENDA_TRADICIONAL_CONSTRUCCION = "$NOMBRE_EDIFICIO,"
            + "$DIRECCION_EDIFICIO,$CIUDAD_EDIFICIO,$NUM_CUENTA_MGL,$TIPO_OT,"
            + "$SUB_TIPO_OT,$FECHA_AGENDADA,$FRANJA_AGENDADA,"
            + "$PER_RECIBE_VISITA,$TEL_RECIBE_VISITA";
    
    public static final String VARIABLES_AGENDA_ANTICIPADA_INMEDIATA = NOMBRE_CLIENTE_ONIX
            + "$NIT_CLIENTE_ONIX,$OT_HIJA_ONIX,$DIR_CLIENTE_ONIX,$TIPO_OT,"
            + "$SUB_TIPO_OT,$FECHA_AGENDADA,$FRANJA_AGENDADA,"
            + "$PER_RECIBE_VISITA,$TEL_RECIBE_VISITA,$NOMBRE_TECNICO,$ID_TECNICO,$NOMBRE_ALIADO";
    
    public static final String VARIABLES_REAGENDA = NOMBRE_CLIENTE_ONIX
            + "$NIT_CLIENTE_ONIX,$OT_HIJA_ONIX,$DIR_CLIENTE_ONIX,$TIPO_OT,"
            + "$SUB_TIPO_OT,$FECHA_AGENDADA,$FRANJA_AGENDADA,$FECHA_REAGENDA,$FRANJA_REAGENDA,"
            + "$PER_RECIBE_VISITA,$TEL_RECIBE_VISITA";
    
    public static final String VARIABLES_REAGENDA_CONSTRUCCION = "$NOMBRE_EDIFICIO,"
            + "$DIRECCION_EDIFICIO,$CIUDAD_EDIFICIO,$NUM_CUENTA_MGL,$TIPO_OT,"
            + "$SUB_TIPO_OT,$FECHA_AGENDADA,$FRANJA_AGENDADA,"
            + "$FECHA_REAGENDA,$FRANJA_REAGENDA,$PER_RECIBE_VISITA,$TEL_RECIBE_VISITA";
    
    public static final String VARIABLES_COMPLETADA = NOMBRE_CLIENTE_ONIX
            + "$NIT_CLIENTE_ONIX,$OT_HIJA_ONIX,$DIR_CLIENTE_ONIX,$TIPO_OT,"
            + "$SUB_TIPO_OT,$PER_ATENDIO_VISITA,$FECHA_CIERRE_AGENDA,"
            + "$HORA_INICIO_AGENDA,$HORA_FIN_AGENDA,$ID_TECNICO,"
            + "$NOMBRE_TECNICO,$COMPANIA_CONTRATISTA,$NOTAS_ORDEN,$FIRMA_PERSONA_RECIBIO";
    
    public static final String VARIABLES_COMPLETADA_CONSTRUCCION = "$NOMBRE_EDIFICIO,"
            + "$DIRECCION_EDIFICIO,$CIUDAD_EDIFICIO,$NUM_CUENTA_MGL,$TIPO_OT,"
            + "$SUB_TIPO_OT,$PER_ATENDIO_VISITA,$FECHA_CIERRE_AGENDA,"
            + "$HORA_INICIO_AGENDA,$HORA_FIN_AGENDA,$ID_TECNICO,"
            + "$NOMBRE_TECNICO,$COMPANIA_CONTRATISTA,$NOTAS_ORDEN,$FIRMA_PERSONA_RECIBIO";
    
    public static final String VARIABLES_CANCELADA = NOMBRE_CLIENTE_ONIX
            + "$NIT_CLIENTE_ONIX,$OT_HIJA_ONIX,$DIR_CLIENTE_ONIX,$TIPO_OT,"
            + "$SUB_TIPO_OT,$RAZON_CANCELACION,$FECHA_AGENDA_1,$FRANJA_AGENDA_1,"
            + "$ESTADO_AGENDA_1,$FECHA_AGENDA_2,$FRANJA_AGENDA_2,$ESTADO_AGENDA_2,"
            + "$FECHA_AGENDA_3,$FRANJA_AGENDA_3,$ESTADO_AGENDA_3,"
            + "$FECHA_AGENDA_4,$FRANJA_AGENDA_4,$ESTADO_AGENDA_4,"
            + "$FECHA_AGENDA_5,$FRANJA_AGENDA_5,$ESTADO_AGENDA_5,"
            + "$FECHA_AGENDA_6,$FRANJA_AGENDA_6,$ESTADO_AGENDA_6,"
            + "$FECHA_AGENDA_7,$FRANJA_AGENDA_7,$ESTADO_AGENDA_7,"
            + "$FECHA_AGENDA_8,$FRANJA_AGENDA_8,$ESTADO_AGENDA_8,"
            + "$FECHA_AGENDA_9,$FRANJA_AGENDA_1,$ESTADO_AGENDA_9,"
            + "$FECHA_AGENDA_10,$FRANJA_AGENDA_10,$ESTADO_AGENDA_10,"
            + "$FECHA_AGENDA_11,$FRANJA_AGENDA_11,$ESTADO_AGENDA_11,"
            + "$FECHA_AGENDA_12,$FRANJA_AGENDA_12,$ESTADO_AGENDA_12,"
            + "$FECHA_AGENDA_13,$FRANJA_AGENDA_13,$ESTADO_AGENDA_13,"
            + "$FECHA_AGENDA_14,$FRANJA_AGENDA_14,$ESTADO_AGENDA_14,"
            + "$FECHA_AGENDA_15,$FRANJA_AGENDA_15,$ESTADO_AGENDA_15,"
            + "$FECHA_AGENDA_16,$FRANJA_AGENDA_16,$ESTADO_AGENDA_16,"
            + "$FECHA_AGENDA_17,$FRANJA_AGENDA_17,$ESTADO_AGENDA_17,"
            + "$FECHA_AGENDA_18,$FRANJA_AGENDA_18,$ESTADO_AGENDA_18,"
            + "$FECHA_AGENDA_19,$FRANJA_AGENDA_19,$ESTADO_AGENDA_19,"
            + "$FECHA_AGENDA_20,$FRANJA_AGENDA_20,$ESTADO_AGENDA_20";
    
    public static final String VARIABLES_CANCELADA_CONSTRUCCION = "$NOMBRE_EDIFICIO,"
            + "$DIRECCION_EDIFICIO,$CIUDAD_EDIFICIO,$NUM_CUENTA_MGL,$TIPO_OT,"
            + "$SUB_TIPO_OT,$RAZON_CANCELACION,$FECHA_AGENDA_1,$FRANJA_AGENDA_1,"
            + "$ESTADO_AGENDA_1,$FECHA_AGENDA_2,$FRANJA_AGENDA_2,$ESTADO_AGENDA_2,"
            + "$FECHA_AGENDA_3,$FRANJA_AGENDA_3,$ESTADO_AGENDA_3,"
            + "$FECHA_AGENDA_4,$FRANJA_AGENDA_4,$ESTADO_AGENDA_4,"
            + "$FECHA_AGENDA_5,$FRANJA_AGENDA_5,$ESTADO_AGENDA_5,"
            + "$FECHA_AGENDA_6,$FRANJA_AGENDA_6,$ESTADO_AGENDA_6,"
            + "$FECHA_AGENDA_7,$FRANJA_AGENDA_7,$ESTADO_AGENDA_7,"
            + "$FECHA_AGENDA_8,$FRANJA_AGENDA_8,$ESTADO_AGENDA_8,"
            + "$FECHA_AGENDA_9,$FRANJA_AGENDA_1,$ESTADO_AGENDA_9,"
            + "$FECHA_AGENDA_10,$FRANJA_AGENDA_10,$ESTADO_AGENDA_10,"
            + "$FECHA_AGENDA_11,$FRANJA_AGENDA_11,$ESTADO_AGENDA_11,"
            + "$FECHA_AGENDA_12,$FRANJA_AGENDA_12,$ESTADO_AGENDA_12,"
            + "$FECHA_AGENDA_13,$FRANJA_AGENDA_13,$ESTADO_AGENDA_13,"
            + "$FECHA_AGENDA_14,$FRANJA_AGENDA_14,$ESTADO_AGENDA_14,"
            + "$FECHA_AGENDA_15,$FRANJA_AGENDA_15,$ESTADO_AGENDA_15,"
            + "$FECHA_AGENDA_16,$FRANJA_AGENDA_16,$ESTADO_AGENDA_16,"
            + "$FECHA_AGENDA_17,$FRANJA_AGENDA_17,$ESTADO_AGENDA_17,"
            + "$FECHA_AGENDA_18,$FRANJA_AGENDA_18,$ESTADO_AGENDA_18,"
            + "$FECHA_AGENDA_19,$FRANJA_AGENDA_19,$ESTADO_AGENDA_19,"
            + "$FECHA_AGENDA_20,$FRANJA_AGENDA_20,$ESTADO_AGENDA_20";
    
    public static final String PERFIL_ENVIO_CORREOS = "PERFIL_ENVIO_CORREOS";
    
    public static final String VARIABLES_VISIBLE_TABLAS= "$AGENDA_1_VISIBLE,"
            + "$AGENDA_2_VISIBLE,$AGENDA_3_VISIBLE,$AGENDA_4_VISIBLE,"
            + "$AGENDA_5_VISIBLE,$AGENDA_6_VISIBLE,$AGENDA_7_VISIBLE,"
            + "$AGENDA_8_VISIBLE,$AGENDA_9_VISIBLE,$AGENDA_10_VISIBLE,"
            + "$AGENDA_11_VISIBLE,$AGENDA_12_VISIBLE,$AGENDA_13_VISIBLE,"
            + "$AGENDA_14_VISIBLE,$AGENDA_15_VISIBLE,$AGENDA_16_VISIBLE,"
            + "$AGENDA_17_VISIBLE,$AGENDA_18_VISIBLE,$AGENDA_19_VISIBLE,"
            + "$AGENDA_20_VISIBLE";
     
     public static final String BASE_URI_REST_GET_ZONAS = "BASE_URI_REST_GET_ZONAS";
     
     public static final String TIME_OUT_CONSUMO_SERVICES = "TIME_OUT_CONSUMO_SERVICES";

    /* Constante para apoyo en consumo de Stored Procedure
    para delimitar el máximo de carácteres que permite */
    public static final int LIMITE_CARACTERES_ENTRADA_SP = 10000;//tope maximo ya establecido
    public static final String SIN_VT_FLOW = "SIN_VT_FLOW";

}
