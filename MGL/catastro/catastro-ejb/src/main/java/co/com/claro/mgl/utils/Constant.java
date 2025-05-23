package co.com.claro.mgl.utils;

import java.math.BigDecimal;

/**
 *
 * @author camargomf
 */
public class Constant {

    private Constant() {
        //Impedir instancias invalidas
    }

    /** Esquema de las tablas de la base de datos de MGL. */
    public static final String MGL_DATABASE_SCHEMA = "MGL_SCHEME";
    /** Esquema de las tablas de la base de datos de Gesti&oacute;n Seguridad. */
    public static final String GESTION_DATABASE_SCHEMA = "GESTIONNEW";
    public static final String NODO_NFI_COD_EQ = "NFI";
    public static final String TIPO_VIA_PRINCIPAL_CK = "CALLES_DIR";
    public static final String LETRA_NUMEROS_BIS_CK = "DIR_ALFANUMERICO";
    public static final String CARDINALES_CK = "DIR_CUADRANTE";
    public static final String CRUCE_CK = "CALLES_DIR_GEN";
    public static final String TIPO_CONJUNTO_BM = "DIR_NIVEL_UNO";
    public static final String SUBDIVISION1_BM = "DIR_NIVEL_DOS";
    public static final String SUBDIVISION2_BM = "DIR_NIVEL_TRES";
    public static final String SUBDIVISION3_BM = "DIR_NIVEL_CUATRO";
    public static final String SUBDIVISION4_BM = "DIR_NIVEL_CINCO";
    public static final String TIPO_VIA_PRINCIPAL_IT = "DIR_INTRADUCIBLE";
    public static final String COMPLEMENTO_VIA_PRINCIPAL_IT = "DIR_INTRADUCIBLE2";
    public static final String ZONA_RURAL_IT = "DIR_INTRADUCIBLE3";
    public static final String SUBDIVISION_IT = "DIR_INTRADUCIBLE4";
    public static final String SUBDIVISION1_IT = "DIR_INTRADUCIBLE5";
    public static final String OPCION_IT = "DIR_INTRADUCIBLE6";
    public static final String PLACA_IT = "DIR_PLACA_IT";
    public static final String COMPLEMENTO_IT = "DIR_NIVEL_CINCO";
    public static final String TIPO_APTO_UNI = "DIR_NUM_APTO";
    public static final String TIPO_APTO_BI = "DIR_NIVEL_5_VC";
    public static final String TIPO_APTO_COMP = "DIR_NUM_APTOC";
    public static final BigDecimal BASICA_ESTADO_EDIFICIO_MULTIEDIFICIO = new BigDecimal(50);
    public static final int ID_TIPO_DIRECCION_PRINCIPAL = 2;
    public static final int ID_TIPO_DIRECCION_ALTERNA = 1;
    public static final int ID_TIPO_DIRECCION_SUBEDIFICIO = 3;
    //VARIABLE A CAMBIAR
    public static final String TIPO_COMPLEMENTO = "DIR_SUB_EDIFICIO";
    //solicitudes
    public static final BigDecimal SOLICITUD_BASICA_ESTADO_PENDIENTE = new BigDecimal(80);
    public static final String ERROR_RESULTADO_RR = "E";
    public static final String ERROR_MENSAJE_RESULTADO_RR = "Estado errado";
    public static String TIPO_RED_BIDIRECCIONAL = "B";
    public static String TIPO_RED_UNIDIRECCIONAL = "U";
    public static String TIPO_RED_BIDIRECCIONAL_INSPIRA = "BI";
    public static String TIPO_RED_UNIDIRECCIONAL_INSPIRA = "UNI";
    public static String PLAN_DE_EXPANSION_NACIONAL = "PEN";
    public static final String AGENDAMIENTO_OFSC_ONOFF = "AGENDAMIENTO_OFSC_ONOFF";
    public static final String AGENDAMIENTO_OFSC_VALOR = "1"; 
    

    public static String ACCION_EXITOSA_RR = "INS0000";

    public static enum TYPO_DIR {
        V, CK, BM, IT, VACIO
    }
    //COMPAÑIAS
    public static final BigDecimal TIPO_COMPANIA_ID_ASCENSORES = BigDecimal.ONE;
    public static final BigDecimal TIPO_COMPANIA_ID_CONSTRUCTORAS = new BigDecimal(2);
    public static final BigDecimal TIPO_COMPANIA_ID_ADMINISTRACION = new BigDecimal(3);
    public static final BigDecimal TIPO_COMPANIA_ID_PROOVEDORES = new BigDecimal(5);
    public static final BigDecimal TIPO_COMPANIA_ADMON_NATURAL = new BigDecimal(4);
    //FIN COMPAÑIAS
    public static final String OPTION_BIS = "BIS";
    public static final String VACIO = "VACIO";
    public static final String ABREVIATURA_BASICA_BLK_LIST_TEC = "BT";
    /**
     * CODIGOS TIPO BASICA
     */
    public static final String TIPO_BASICA_OPERA = "@TB_OPERA";
    public static final String TIPO_BASICA_ESTADO_NODO = "@TB_TEN";
    public static final String TIPO_BASICA_ESTADO_CUENTA_MATRIZ = "@TB_ECM";//ok
    public static final String TIPO_BASICA_TIPO_DE_NOTAS = "@TB_TDN";//ok
    public static final String TIPO_BASICA_TIPO_CUENTA_MATRIZ = "@TB_TCM";//ok
    public static final String TIPO_BASICA_TIPO_FLUJO_OT = "@TB_TFO";//no va
    public static final String TIPO_BASICA_ESTADO_INTERNO_OT = "@TB_EIO";//no va
    public static final String TIPO_BASICA_ESTADO_EXTERNO_OT = "@TB_EEO";//no va
    public static final String TIPO_BASICA_TIPO_DE_SEGMENTO = "@TB_TDS";//no va
    public static final String TIPO_BASICA_TIPO_DE_ALIMENTACION_ELECTRICA = "@TB_TAE";//ok
    public static final String TIPO_BASICA_TIPO_DE_CANALIZACION_INTERNA = "@TB_CDI";//no va
    public static final String TIPO_BASICA_TIPO_DE_MANEJO_ASCENSORES = "@TB_TMA";//no va
    public static final String TIPO_BASICA_TIPO_ACTIVIDAD = "@TB_TA_";//no va
    public static final String TIPO_BASICA_CONFIGURACION_DISTRIBUCION = "@TB_CD_";//no va
    public static final String TIPO_BASICA_DISTRIBUCION = "@TB_TD_";//ok
    public static final String TIPO_BASICA_PUNTO_INICIAL_ACOMETIDA = "@TB_PIA";//ok
    public static final String TIPO_BASICA_VISITA_TECNICA = "@TB_VT_";//no va
    public static final String TIPO_BASICA_TIPO_COMPETENCIA = "@TB_TC_";//ok
    public static final String TIPO_BASICA_TIPO_COMPETENCIA_PROVEEDOR = "@TB_TCP";
    public static final String TIPO_BASICA_TIPO_DE_TRABAJO = "@TB_TDT";//ok
    public static final String TIPO_BASICA_TIPO_DE_PROYECTO = "@TB_TDP";//ok
    public static final String TIPO_BASICA_CARGOS = "@TB_C__";//no va
    public static final String TIPO_BASICA_CAUSAS_DE_MANTENIMIENTO = "@TB_CDM";//ok
    public static final String TIPO_BASICA_BLACK_LIST_CM = "@TB_BLC";//oj
    public static final String TIPO_BASICA_ORIGEN_DE_DATOS = "@TB_ORD";//ok
    public static final String TIPO_BASICA_UBICACION_EQUIPOS = "@TB_UDE";//ok
    public static final String TIPO_BASICA_TIPO_MATERIAL = "@TB_TDM";//ok
    public static final String TIPO_BASICA_ESTRATO = "@TB_E__";//ok
    public static final String TIPO_BASICA_ESTADO_INTERNO_GA = "@TB_EIG";//pendiente falta ws
    public static final String TIPO_BASICA_ESTADO_EXTERNO_GA = "@TB_EEG";//no va
    public static final String TIPO_BASICA_TIPO_GA = "@TB_TG_";//no va
    public static final String TIPO_BASICA_TIPO_ACOMETIDA = "@TB_TAA";//ok;
    public static final String TIPO_BASICA_PRODUCTO = "@TB_P__";//ok
    public static final String TIPO_BASICA_TIPO_SUBEDIFICIO = "@TB_TSE";//ok
    public static final String TIPO_BASICA_TIPO_ESTABLESIMIENTO = "@TB_TE_";//ok
    public static final String TIPO_BASICA_TIPO_SOLICITUD = "@TB_TS_";//ok
    public static final String TIPO_BASICA_TECNOLOGIA = "@TB_TTC";//ok
    public static final String TIPO_BASICA_TIPO_ACCION_SOLICITUD = "@TB_STA";//ok
    public static final String TIPO_BASICA_GRUPO_PROYECTO_ID = "@TB_MVGP";
    public static final String TIPO_BASICA_PROYECTO_BASICA_ID = "@TB_MVTP";
    public static final String TIPO_BASICA_CLASE_ORDEN_TRABAJO = "@TB_COT";
    public static final String TIPO_BASICA_ESTADO_RESULTADO = "@TB_ER_";//no va
    public static final String TIPO_BASICA_ATRIBUTOS_CAMBIO_MASIVO = "@TB_ACM";
    public static final String TIPO_BASICA_MODULOS_APLICAR_TIPO_BASICA = "@TB_MATB";
    public static final String TIPO_BASICA_PARAM_INFO_TEC_N1 = "@TB_ITN1";
    public static final String TIPO_BASICA_PARAM_INFO_TEC_N2 = "@TB_ITN2";
    public static final String TIPO_BASICA_PARAM_INFO_TEC_N3 = "@TB_ITN3";
    public static final String TIPO_BASICA_PARAM_INFO_TEC_N4 = "@TB_ITN4";
    public static final String TIPO_BASICA_ESTADO_SOLICITUD = "@TB_TES";
    public static final String TIPO_BASICA_TIPO_GENERAL_OT = "@TB_TGT";
    public static final String TIPO_BASICA_FORMULARIOS_VT = "@TB_FVT";
    public static final String TIPO_BASICA_ORIGEN_SOLICITUD = "@TB_ODS";
    public static final String TIPO_BASICA_TIPO_ACONDICIONAMIENTO = "@TB_TAC";
    public static final String TIPO_BASICA_INVENTARIO_TECNOLOGIA = "@TB_TIT";
    public static final String TIPO_BASICA_CLASE_INVENTARIO_TECNOLOGIA = "@TB_CIT";
    public static final String TIPO_BASICA_FABRICANTES_INVENTARIO_TECNOLOGIA = "@TB_FIT";
    public static final String TIPO_BASICA_TIPO_GESTION_BACKLOG = "@TB_TGB";
    public static final String BTIPO_BASICA_TIPO_RESULTADO_GESTION_BACKLOG = "@TB_RGB";
    public static final String TIPO_BASICA_TIPO_RESULTADO_GESTION_BACKLOG = "@TB_RGB";//-
    public static final String TIPO_BASICA_ACCION_MODIFICAR_CM = "@TB_MDC";
    public static final String TIPO_BASICA_ACCION_RECHAZAR_MODIFICAR_CM = "@TB_RMDC";
    public static final String TIPO_BASICA_ACCION_MODIFICAR_DIRECCION = "@TB_MD__";
    public static final String TIPO_BASICA_ACCION_RECHAZAR_MODIFICAR_DIRECCION = "@TB_RMD_";
    public static final String TIPO_BASICA_ACCION_MODIFICAR_SUBEDIFICIOS = "@TB_MSE_";
    public static final String TIPO_BASICA_ACCION_RECHAZAR_MODIFICAR_SUBEDIFICIOS = "@TB_RMSE";
    public static final String TIPO_BASICA_ACCION_MODIFICAR_COBERTURA = "@TB_MC__";
    public static final String TIPO_BASICA_ACCION_RECHAZAR_MODIFICAR_COBERTURA = "@TB_MRMC";
    public static final String TIPO_BASICA_ACCION_FINALIZAR_SOLICITUD_VISITA_TECNICA = "@TB_FSVT";
    public static final String TIPO_BASICA_ACCION_RECHAZAR_VISITA_TECNICA = "@TB_RSVT";
    public static final String TIPO_BASICA_ACCION_POSPONER_VISITA_TECNICA = "@TB_PVT_";
    public static final String TIPO_BASICA_ACCION_CREAR_CUENTA_MATRIZ = "@TB_CCM_";
    public static final String TIPO_BASICA_ACCION_RECHAZAR_CUENTA_MATRIZ = "@TB_RACM";
    public static final String TIPO_BASICA_ACCION_CREAR_HHPP = "@TB_ACHH";
    public static final String TIPO_BASICA_ACCION_RECHAZAR_HHPP = "@TB_ARHH";
    public static final String TIPO_BASICA_ACCION_ESCALAR_CUENTA_MATRIZ = "@TB_ESCM";
    public static final String TIPO_BASICA_TORRES_SIN_DIRECCION = "@TB_TSD";
    public static final String TIPO_BASICA_CASAS_PROPIA_DIRECCION = "@TB_CPD";
    public static final String TIPO_BASICA_BAT_PROPIA_DIRECCION = "@TB_BPD";
    public static final String TIPO_BASICA_REGIONALES_TECNICAS_WFM = "@TB_RTW_";
    public static final String TIPO_BASICA_ELIMINAR_CM = "@TB_E_CM";
    public static final String TIPO_BASICA_ESCALAR_ACOMETIDAS = "@TB_E_A";
    public static final String TIPO_BASICA_ESCALAR_HHPP = "@TB_E_H";
    public static final String TIPO_BASICA_RECHAZAR_ELIMINACION_CM = "@TB_E_R";
    public static final String TIPO_BASICA_DIVICIONAL_COMERCIAL_TELMEX = "@TB_DVT";
    public static final String TIPO_BASICA_DISTRITOS_COMERCIALES_TELMEX = "@TB_DCT";
    public static final String TIPO_BASICA_ZONA_COMERCIALES_TELMEX = "@TB_ZON";
    public static final String TIPO_BASICA_AREAS_COMERCIALES_TELMEX = "@TB_ARE";
    public static final String TIPO_BASICA_UNIDADES_GESTION_TELMEX = "@TB_UGT";
    public static final String TIPO_BASICA_ESTRATOS_HHPP = "@TB_ESHP";
    public static final String TIPO_BASICA_ESTADOS_NODOS = "@B_ESTN";
    public static final String TIPO_BASICA_ESTADOS_HHPP = "@TB_ETH";
    public static final String TIPO_BASICA_ALIADOS = "@TB_ALR";
    public static final String TIPO_BASICA_RAZONES_NODONE = "@TB_NDONE";
    public static final String TIPO_BASICA_ESTADOS_AGENDA = "@TB_EAG";
    public static final String TIPO_BASICA_SUB_TIPO_ORDEN_OFSC = "@TB_SUO";//ok
    public static final String TIPO_BASICA_NOMBRES_TECNOLOGIAS_OFSC = "@TB_NTO";//ok
    public static final String TIPO_BASICA_PREREQUISITOS_AGENDA = "@TB_PRA";
    public static final String TIPO_BASICA_FLUJO_OT_VISITA_TECNICA = "@B_FOTVT";
    public static final String TIPO_BASICA_FLUJO_OT_VISITA_TECNICA_ANTIGUA = "@B_FOTVTA";
    public static final String TIPO_BASICA_TIPO_VALIDACION_PARAMETRIZADA = "@TB_FVP";
    public static final String TIPO_BASICA_ESTRATO_HHPP = "@TB_ESHP";
    public static final String TIPO_BASICA_ESTADO_VISITA_DOMICILIARIA = "@TB_EVD";
    public static final String TIPO_BASICA_TIPO_TRABAJO_DIRECCIONES = "@TB_TTD";
    public static final String TIPO_BASICA_SUB_TIPO_TRABAJO_DIRECCIONES = "@TB_STD";
    public static final String TIPO_BASICA_ESTADOS_TAREAS_PROGRAMADAS = "@TB_TPR";
    public static final String TIPO_BASICA_ESTADOS_DETALLE_TAREAS_PROGRAMADAS = "@TB_DTP";
    public static final String TIPO_BASICA_TIPO_CERRADURAS_ELECTRONICAS = "@TB_TCE";

    
    /**
     * CODIGOS TABLA BASICA
     */
    public static final String BASICA_TIPO_ESTADO_NODO_CERTIFICADO = "@B_NACT";
    public static final String BASICA_TIPO_ESTADO_NODO_PROCESO_CERTIFICACION = "@B_NIAPC";
    public static final String BASICA_TIPO_ESTADO_NODO_EJECUCION = "@B_NIAE";
    public static final String BASICA_TIPO_ESTADO_NODO_NO_CERTIFICADO = "@B_NIAC";
    public static final String BASICA_TIPO_ESTADO_NODO_FUERA_DE_ZONA = "@B_NIAFZ";
    public static final String BASICA_TIPO_ACTIVIDAD_CONTRUCCION = "@B_TAC";
    public static final String BASICA_ESTADO_SIN_VISITA_TECNICA = "@B_SVT";
    public static final String BASICA_ESTADO_CON_VISITA_TECNICA = "@B_CVT";
    public static final String BASICA_TIPO_TECNOLOGIA_DEFAULT = "@B_TTD";
    public static final String BASICA_ESTADO_SOLICITUD_PENDIENTE = "@B_ESP";
    public static final String BASICA_ESTADO_SOLICITUD_FINALIZADO = "@B_ESF";
    public static final String BASICA_ESTADO_SOLICITUD_PENDIENTE_ESCALADO_ACO = "@B_EPEA";
    public static final String BASICA_ESTADO_SOLICITUD_PENDIENTE_COBERTURA = "@B_EPC";
    public static final String BASICA_ESTADO_SOLICITUD_PENDIENTE_HHPP = "@B_EPH";
    public static final String BASICA_ACCION_RECHAZAR_CUENTA_MATRIZ = "@B_ARC";
    public static final String BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO = "@B_EEM";
    public static final String BASICA_ORIGEN_DE_SOLICTUD_CONSTRUCTORA = "@B_OSC";
    public static final String BASICA_RPT_GEST_POSPONER_VISITA_TECNICA = "@B_GPV";
    public static final String BASICA_GEST_CREAR_VISITA_TECNICA = "@B_GCV";
    public static final String BASICA_ORIGEN_DATOS_BARRIDO_CALLES = "@B_OBC";
    public static final String BASICA_TIPO_SOLICITUD_ACOMETIDA = "@B_TSACOM";
    public static final String BASICA_ORIGEN_DATOS_NODO_NUEVO = "@B_ODN";   
     /**
     *  VALORES TIPO PROYECTO
     */
    public static final String BASICA_TIPO_PROYECTO_ACOMETIDA_NORMAL = "@B_TPA";
    public static final String BASICA_TIPO_PROYECTO_NODO_NUEVO = "@B_TPN";
    public static final String BASICA_TIPO_PROYECTO_CONSTRUCTORARECIDENCIAL = "@B_TPC";
    
    
    public static final String BASICA_TIPO_PRODUCTO_MULTIPLAY = "@B_TPM";
    public static final String BASICA_NOTA_MODIFICACION_CUENTA_MATRIZ = "@B_MNC";
    public static final String BASICA_NOTA_CREACION_CUENTA_MATRIZ = "@B_NCM";
    public static final String BASICA_NOTA_VISITA_TECNICA = "@B_NVT";
    public static final String BASICA_NOTA_HHHPP = "@B_NHP";
    public static final String BASICA_ORIGENDATOS_PYME = "@B_ODP";
    public static final String ESTADO_GENERAL_OT_HHPP = "@TB_EG_OTHHPP";
    public static final String BASICA_CONJUNTO_CASAS_PROPIA_DIRECCION = "@B_CASAS";
    public static final String BASICA_CAMPUS_BATALLON = "@B_CAMP";
    public static final String ESTADO_GENERAL_OT_HHPP_RAZON_ABIERTO = "@TB_RAB";
    public static final String ESTADO_GENERAL_OT_HHPP_RAZON_CERRADO = "@TB_RC";
    public static final String ESTADO_GENERAL_OT_HHPP_RAZON_ANULADO = "@TB_RAN";
    public static final String ESTADO_GENERAL_OT_HHPP_RAZON_CERRADO_CREAR_HHPP = "@TB_RC(@CH)";
    public static final String ESTADO_GENERAL_OT_HHPP_RAZON_ABIERTO_COMPLETO = "@B_EGA(@TB_RAB)";
    public static final String ESTADO_GENERAL_OT_HHPP_RAZON_CERRADO_COMPLETO = "@B_EGCR(@TB_RC)";
    public static final String ESTADO_GENERAL_OT_HHPP_RAZON_ANULADO_COMPLETO = "@B_EGA(@TB_RAN)";
    
    //Tareas Programadas
    public static final String ESTADO_EJECUTANDOSE_TAREAS_PROGRAMADAS = "@TB_TEE";
    public static final String ESTADO_FINALIZADA_TAREAS_PROGRAMADAS = "@TB_TFZ";
    public static final String ESTADO_FINALIZADA_CON_EXITO_DETALLE_TAREAS_PROGRAMADAS = "@TB_FCE";
    public static final String ESTADO_FINALIZADA_CON_ERROR_DETALLE_TAREAS_PROGRAMADAS = "@TB_FER";
    public static final String ESTADO_EJECUTANDOSE_DETALLE_TAREAS_PROGRAMADAS = "@TB_DEJ";
    
    public static final String V_VACIO = "V";
    public static String OPT_PISO_INTERIOR = "PISO + INTERIOR";
    public static String MSG_DIR_EX_NOGEO = "La Dirección existe y requiere validación de cobertura ";
    public static String MSG_DIR_PRINCIPAL_REQUIRED = "Debe Ingresar una Dirección Principal";
    public static String MSG_SOLICITUD_EN_TRAMITE = "La Dirección Principal cuenta con una Solicitud en Tramite";
    public static String ADDRESSEJB = "addressEJB#co.com.telmex.catastro.services.georeferencia.AddressEJBRemote";
    public static String MAIL_FROM = "modulovisitas@claro.com.co";
    public static String OPT_PISO_LOCAL = "PISO + LOCAL";
    public static String OPT_PISO_APTO = "PISO + APTO";
    public static String OPT_CASA_PISO = "CASA + PISO";
    public static String MSG_DIR_NO_VALIDADA = "La Dirección no existe ";
    public static String MSG_DIR_EX_GEO = "La Dirección existe ";
    public static String ID_DIR_REPO_NULL = "0";
    public static final String NO_EXISTE = "false";
    public static final String EXISTE = "true";
    public static String TIEMPO_SLA = "TIEMPO_SLA";
    public static String TIEMPO_AVISO = "TIEMPO_AVISO";
    public static String TIEMPO_SOLICITUD_POR_VENCER = "TIEMPO_SOLICITUD_POR_VENCER";
    public static String TIEMPO_SOLICITUD_VENCIDA = "TIEMPO_SOLICITUD_VENCIDA";
    public static String CIUDADES_ESPECIALES = "CIUDADES_ESPECIALES_DTH";
    public static final String BASICA_DEFAULT_TIPO_EDIFICIO = "DEFAULT_EDIFICIO";
    public static final String HABILITAR_RR = "HABILITAR_RR";
    public static final String BASICA_ESTADO_EXTERNO_CERRADO = "@CER";
    public static final int TIPO_BASICA_ID = 160;
    public static final String PROCESO_CAMBIO_SUBEDIFICIO = "A UnicoEdificio";
    public static final String VISITA_DOMICILIARIA_EXITOSA = "@B_ETE";
    public static final String UNIDADES_CON_DIR = "UNDIR";
    public static final String ESTADO_OT_ABIERTO = "@B_EGA(@TB_RAB)";
    public static final String PREFIJO_SUBEDIFICIO_ENTRADA = "EN ";
    public static final String OPCION_NIVEL_5_LOCAL = "LOCAL";
    public static final String NIVELES_CASAS_CON_DIRECCION = "NIVELES_CASAS_CON_DIRECCION";

    public static final String SOLICITUD_TIPO_TECNO_BASICA = "SOLICITUD_TIPO_TECNO_BASICA"; // "FOG"
    public static final String GESTION_RESULTADO_GESTION = "GESTION_RESULTADO_GESTION"; // "HHPP CREADO"
    public static final String GESTION_OBSERVACION_ENCABEZADO = "GESTION_OBSERVACION_ENCABEZADO"; // "Dirección estandarizada: "
    public static final String GESTION_OBSERVACION_CALLE = "GESTION_OBSERVACION_CALLE"; // "Calle: "
    public static final String GESTION_OBSERVACION_PLACA = "GESTION_OBSERVACION_PLACA"; // "Placa: "
    public static final String GESTION_OBSERVACION_APARTAMENTO = "GESTION_OBSERVACION_APARTAMENTO"; // "Apartamento: "
    public static final String GESTION_OBSERVACION_CENTRO_POBLADO = "GESTION_OBSERVACION_CENTRO_POBLADO"; // "Centro Poblado: "
    public static final String GESTION_OBSERVACION_CIUDAD = "GESTION_OBSERVACION_CIUDAD"; // "Ciudad: "

    public static enum FIND_HHPP_BY {
        SUB_EDIFICIO_SOLO_CONTAR,
        CUENTA_MATRIZ_SOLO_CONTAR,
        SUB_EDIFICIO,
        CUENTA_MATRIZ
    }

    public static final String MSN_PROCESO_EXITOSO = "Proceso Realizado Exitosamente";
    public static final String MSN_NOTA_SIN_SELECCIONAR = "Ud debe seleccionar una Nota";
    public static final String MSN_PROCESO_FALLO = "Ocurrio un error realizando el Proceso";
    public static final String MSN_ERROR_PROCESO = "Ocurrio un error realizando el Proceso";
    public static final String MSN_REQUIRED_MESSAGE = "Valor es necesario";

    /**
     * Mensaje de validación de campos requeridos de tipo validación.
     */
    public static final String MSN_TIPO_VALIDACION_REQUERIDO = "Campo 'Tipo de Validación' es requerido";

    public static enum TYPE_QUERY {
        PER_PAGE,
        ALL
    }

    //Direcciones
    public static String DIRECCIONES_TRADUSIBLE = "true";
    public static String DIRECCIONES_ORIGEN_NO_CAMBIO = "NO CAMBIO";
    public static String DIRECCIONES_ORIGEN_IGUAL = "IGUAL";
    public static String DIRECCIONES_ORIGEN_NUEVA = "NUEVA";
    public static String DIRECCIONES_ORIGEN_ANTIGUA = "ANTIGUA";
    public static String DIRECCIONES_MULTIORIGEN = "1";
    public static String DIRECCIONES_EXISTE = "true";

    /**
     * CODIGOS TABLA CMT_SOLICITUD EN PRODUCCION
     */
    public static final BigDecimal TIPO_SOLICITUD_MODIFICACION_CM = BigDecimal.ONE;
    public static final BigDecimal TIPO_SOLICITUD_VISITA_TECNICA = BigDecimal.TEN;
    public static final BigDecimal TIPO_SOLICITUD_CREACION_CM = new BigDecimal("12");
    public static final BigDecimal TIPO_SOLICITUD_HHPP = new BigDecimal("11");
   
    /**
     * CODIGOS TABLA CMT_TIPO_OT EN PRODUCCION
     */
    public static final BigDecimal TIPO_OT_VISITA_TECNICA = BigDecimal.ONE;
    public static final String WS_ADDRESS_CONSULTA_COMPLETA = "C";
    public static final String ADDRESS_TIPO_CK = "CK";
    public static final String ADDRESS_TIPO_BM = "BM";
    public static final String ADDRESS_TIPO_IT = "IT";

    /**
     * TIPO CP_TIPO_NIVEL_5 PARA DIRECCIONES DETALLADAS
     */
    public static final String CP_TIPO_NIVEL_5_CASA = "CASA";
    public static final String CP_TIPO_NIVEL_5_PISO = "PISO";
    public static final String CP_TIPO_NIVEL_5_APARTAMENTO = "APARTAMENTO";
    public static final String CP_TIPO_NIVEL_5_INTERIOR = "INTERIOR";

    
    //creacion en As400
    public static final BigDecimal DIRECCION_SIN_DIRECCION_PROPIA = new BigDecimal("999999999999999");
    //Nombre cookies Filtros Para pantalla de solicitudes por gestion CM
    public static final String COOKIE_NAME_GEST_SOL_CM_DIVISION = "filtroGestSolCmDivision";
    public static final String COOKIE_NAME_GEST_SOL_CM_COMUNIDAD = "filtroGestSolCmComunidad";
    public static final String REGISTRO_ACTIVO_VALUE = "Y";
    public static final String TIPO_ITEM_MANO_OBRA = "MO";
    public static final String TIPO_ITEM_MATERIAL = "MT";
    public static final String TIPO_ITEM_MANO_OBRA_DISENO = "DO";
    public static final String TIPO_ITEM_MATERIAL_DISENO = "DM";
    public static final String CARPETA_BIDIRECCIONAL = "VERIFICACION_CASAS";
    public static final String CARPETA_UNIDIRECCIONAL = "UNIDIRECCIONAL";
    public static final String CARPETA_CUENTA_MATRIZ = "CUENTA_MATRIZ";
    //Id Pestanas Detalle Cm
    public static final String ID_DETALLE_CM_PESTANA_GENERAL = "detalleCmPtGeneral";
    //GRUPO MULTIVALOR PARA ENVIO DE CORREOS DE CREACION DE SOLICITUD DE CM
    public static final String ID_GRUPO_CORREO_CREA_SOLICITUD_CM = "ID_GRUPO_CORREO_CREA_SOLICITUD_CM";
    public static final String ID_CORREO_CREA_SOLICITUD_CREA_CM = "ID_CORREO_CREA_SOLICITUD_CREA_CM";
    public static final String ID_CORREO_CREA_SOLICITUD_VT = "ID_CORREO_CREA_SOLICITUD_VT";
    public static final String ID_CORREO_CREA_SOLICITUD_MOD_CM = "ID_CORREO_CREA_SOLICITUD_MOD_CM";
    public static final String ID_CORREO_CREA_SOLICITUD_CREA_HHPP_CM = "ID_CORREO_CREA_SOLICITUD_CREA_HHPP_CM";
    public static final String ID_CORREO_CREA_SOLICITUD_ELIMINA_CM = "ID_CORREO_CREA_SOLICITUD_ELIMINA_CM";
    public static final String ID_GRUPO_CORREO_GESTION_SOLICITUD_CM = "ID_GRUPO_CORREO_GESTION_SOLICITUD_CM";
    public static final String ID_CORREO_GESTION_SOLICITUD_CREA_CM = "ID_CORREO_GESTION_SOLICITUD_CREA_CM";
    public static final String ID_CORREO_GESTION_SOLICITUD_VT = "ID_CORREO_GESTION_SOLICITUD_VT";
    public static final String ID_CORREO_GESTION_SOLICITUD_MOD_CM = "ID_CORREO_GESTION_SOLICITUD_MOD_CM";
    public static final String ID_CORREO_GESTION_SOLICITUD_CREA_HHPP_CM = "ID_CORREO_GESTION_SOLICITUD_CREA_HHPP_CM";
    public static final String ID_CORREO_GESTION_SOLICITUD_ELIMINA_CM = "ID_CORREO_GESTION_SOLICITUD_ELIMINA_CM";
    //PARAMETROS DE GRUPO DE TIPO DE SOLICITUDES
    public static final String GRUPO_TIPO_SOLICITUD_CM_PARAM = "GRUPO_TIPO_SOLICITUD_CM_PARAM";
    public static final String ID_TIPO_SOLICITUD_CREA_CM = "ID_TIPO_SOLICITUD_CREA_CM";
    public static final String ID_TIPO_SOLICITUD_VT = "ID_TIPO_SOLICITUD_VT";
    public static final String ID_TIPO_SOLICITUD_MOD_CM = "ID_TIPO_SOLICITUD_MOD_CM";
    public static final String ID_TIPO_SOLICITUD_CREA_HHPP_CM = "ID_TIPO_SOLICITUD_CREA_HHPP_CM";
    public static final String ID_TIPO_SOLICITUD_ELIMINA_CM = "ID_TIPO_SOLICITUD_ELIMINA_CM";
    public static final String ID_TIPO_SOLICITUD_TRASLADO_HHPP_BLOQUEADO_CM = "ID_TIPO_SOLICITUD_TRASLADO_HHPP_BLOQUEADO_CM";

    //convergente
    public static final String CUENTA_MATRIZ_ESTADO_MULTIEDIFICIO = "0003";
    public static final String PARAMETRO_VALIDACION_OT_HABILITA_CREA_RR = "PRC_RR";
    public static final String PARAMETRO_VALIDACION_OT_HABILITA_FORMULARIO_VT = "FORM_VT";
    public static final String PARAMETRO_VALIDACION_OT_HABILITA_ACTUALIZA_CCMM = "SYN_ACO";
    public static final String PARAMETRO_VALIDACION_OT_HABILITA_FORMULARIO_AC = "FORM_AC";

    //Constantes Visitas Tecnicas tipo de solicitud
    /*Dirección en RR*/
    public static final String RR_DIR_CREA_HHPP = "Creación HHPP";
    public static final String RR_DIR_CREA_HHPP_0 = "0";
    public static final String RR_DIR_CAMBIO_ESTRATO_2 = "2";
    public static final String RR_DIR_ESCALAMIENTO_HHPP = "13";
    public static final String RR_DIR_CAMB_HHPP = "Cambio dirección HHPP";
    public static final String RR_DIR_CAMB_HHPP_1 = "1";
    public static final String RR_DIR_REAC_HHPP = "Reactivación HHPP";
    public static final String RR_DIR_REAC_HHPP_3 = "3";
    public static final String CAMBIO_ESTRATO = "Cambio Estrato";
    public static final String CAMBIO_ESTRATO_2 = "2";
    public static final String TRASLADO_HHPP_BLOQUEADO_5 = "5";
    public static final String RESULTADO_GESTION_CE = "RESULTADO_GESTION_CE";
    public static final String RZ_CAMBIO_DE_DIRECCION_REALIZADO = "CAMBIO DE DIRECCION REALIZADO";
    public static final String ROL_GESTIONAR_VC = "NVTGE5";
    public static final String ROL_GESTIONAR_HHPP_UNI = "NVTGE4";
    public static final String TIPO_SOLICITUD_UNI = "CREACION HHPP UNIDI";
    public static final String TIPO_SOLICITUD_DTH = "DTH";
    public static final String TIPO_SOLICITUD_BI = "VTCASA";
    public static String PROPERTY_SERVER_PATH = "PROPERTY_SERVER_PATH";
    public static String PROPERTY_PATH_PATH = "PROPERTY_PATH_PATH";
    public static final String PROPERTY_ESCALAMIENTO_PATH_PATH = "PROPERTY_ESCALAMIENTO_PATH_PATH";
    public static String PROPERTY_PATH_PATH_VT = "PROPERTY_PATH_PATH_VT";
    public static String PROPERTY_URL_PATH = "PROPERTY_URL_PATH";
    public static final String PARAMETRO_CONFIG_MENU_ROL_VT = "MENU_ROL_VT";
    //Id Menu Vt
    public static final String ID_MENU_VT_GESTION_FROM_SOLICITUD = "menuVtGestionFromSolicitud";
    public static String MSG_TIPO_DIR_TABULADA = "Es necesario ingresar el tipo de dirección en los parametros de la direccion tabulada. [idTipoDireccion: CK, BM o IT]"; 
    public static String MSG_DIR_NO_TABULADA = "La dirección no se pudo tabular, por favor digítela tabulada.";
    public static String MSG_DIR_CODIGO_DANE = "el código dane es obligatorio";
    public static String MSG_DIR_CODIGO_DANE_ERROR = "el codigo dane no es valido, o la ciudad no esta configurada en el sistema";
    public static String MSG_DIR_CREAR_OK = "Se creó la dirección principal, para crear el complemento de la dirección por favor digítela tabulada.";
    public static String MSG_DIR_DET__CREAR_ERROR = "no se pudo crear el registro.";
    public static String MSG_CAMPOS_OBLIGATORIOS_DIR_ERROR = "no se encontraron registros en la busqueda y tampoco fue posible crear la dirección por que no cumple con los campos mínimos requeridos ";
    public static String MSG_DIR_SOLICITUD_OK = "se creó una solicitud para la dirección.";
    public static final String MSG_ERROR_VALIDANDO_PERMISOS_EDICION = "Error validando los permisos de edición.";
    public static final String MSG_ERROR_VALIDANDO_PERMISOS_BORRADO = "Error validando los permisos de eliminación.";
    public static String MSG_DIR_SOLICITUD_ERROR = "no se pudo crear la  solicitud para la dirección, por que no cumple con los campos obligatorios.";
    public static String MSG_DIR_USUARIO_ERROR = "el usuario es obligatorio";
    public static String MSG_DIR_ESTRATO_ERROR = "el estrato de la dirección es obligatorio, no se puede crear la solicitud DTH.";
    public static final BigDecimal NODO_ID_DEFAULT = new BigDecimal("999999999999999");
    public static final BigDecimal BASICA_ID_DEFAULT = new BigDecimal("999999999999999");
    public static final int ACCION_INVENTARIO_OT_ADD = 1;
    public static final int ACCION_INVENTARIO_OT_DEL = 2;
    public static final int ACCION_INVENTARIO_OT_NOA = 3;
    public static String MSG_NODO_OBLIGATORIO = "El nodo debe llenarse en la linea ";
    public static String MSG_NODO_ERROR = "Debe ingresar un codigo de nodo valido para la linea ";
    public static String MSG_NODO_NO_VALIDO = "Debe ingresar un codigo de nodo valido ";
    public static String MSG_TIPO_TECNOLOGIA_OBLIGATORIO = "El tipo de tecnologia es obligatorio";
    public static String MSG_DEPARTAMENTO_OBLIGATORIO = "El campo departamento es obligatorio";
    public static String MSG_CIUDAD_OBLIGATORIO = "El campo ciudad es obligatorio";
    public static String MSG_CENTRO_POBLADO_OBLIGATORIO = "El campo centro poblado es obligatorio";
    public static String CAR_MAS_CUENTA = "cuenta";
    public static String CAR_MAS_COMPANIA = "compania";
    public static String CAR_MAS_ASCENSORES = "ascensores";
    public static String CAR_MAS_CONSTRUCTORA = "constructora";
    public static String CAR_MAS_ADMINISTRADOR = "administrador";
    public static String CAR_MAS_TEL_UNO = "telefonoUno";
    public static String CAR_MAS_TEL_DOS = "telefonoDos";
    public static String CAR_MAS_PROYECTO = "proyecto";
    public static String CAR_MAS_DATOS = "datos";
    public static String CAR_MAS_NODO = "nodo";
    public static String CAR_MAS_ID_CCMM_RR = "idCcmmRr";
    public static String CAR_MAS_ID_CCMM_MGL = "idCcmmMgl";
    public static String CAR_MAS_ESTADO = "estado";
    public static String CAR_MAS_CONFIGURACION = "configuracion";
    public static String CAR_MAS_ALIMENTACION = "alimentacion";
    public static String CAR_MAS_DSITRIBUCION = "distribucion";
    public static String CAR_MAS_TIPO_CCMM = "tipoCcmm";
    public static String CAR_MAS_BLACKLIST_TECNOLOGIA = "blacklistTec";
    public static String CAR_MAS_ID_TECNOLOGIA = "idTecnologia";
    public static String CAR_MAS_ID_CIUDAD = "idCiudad";
    public static String CAR_MAS_ID_CENTRO_POBLADO = "idCentroPoblado";
    public static String CAR_MAS_CODIGO_NODO = "codigoNodo";
    public static String CAR_MAS_REPORTE = "generalDetallado";
    public static String MSN_NOMBRE_CUENTA = "Nombre general de la cuenta matriz es obligatorio.";
    public static String MSN_COMPANIA_ID_ADMINISTRACION = "Compañía administradora es obligatorio.";
    public static String MSN_COMPANIA_ID_ASCENSOR = "Compañía ascensores es obligatorio.";
    public static String MSN_COMPANIA_ID_CONSTRUCTORA = "Compañía constructora es obligatorio.";
    public static String MSN_ADMINISTRADOR = "Administrador de la CCMM es obligatorio.";
    public static String MSN_TELEFONO_PORTERIA = "Teléfono portería de la CCMM es obligatorio.";
    public static String MSN_TELEFONO_PORTERIA2 = "Teléfono portería de la CCMM es obligatorio.";
    public static String MSN_BASICA_ID_TIPO_PROYECTO = "Tipo de proyecto de la CCMM es obligatorio.";
    public static String MSN_BASICA_ID_ORIGEN_DATOS = "Origen de datos de la CCMM es obligatorio.";
    public static String MSN_NODO_ID = "Nodo de la tecnología aplicada en el cambio es obligatorio.";
    public static String MSN_ESTADO_TECNOLOGIA_ID = "Estado de la tecnología Aplicada en el cambio es obligatorio.";
    public static String MSN_BASICA_ID_TIPO_CONF_DISTB = "Detalla el tipo de configuración es obligatorio.";
    public static String MSN_BASICA_ID_ALIMT_ELECT = "Alimentación eléctrica tecnología es obligatorio.";
    public static String MSN_BASICA_ID_TIPO_DISTRIBUCION = "Tipo de distribución tecnologia es obligatorio.";
    public static String MSN_BASICA_ID_TIPO_CCMM = "Tipo CCMM es obligatorio.";
    public static String MSN_ATRIBUTO_HHPP_OBLIGATORIO = "El tipo basica Atributo HHPP es obligatorio.";
    public static String MSN_ATRIBUTO_CCMM_OBLIGATORIO = "El tipo basica Atributo CCMM es obligatorio.";
    public static String TECNOLOGIA = "TIPO_TECNOLOGIA";
    public static String DEPARTAMENTO = "DEPARTAMENTO";
    public static String CIUDAD = "CIUDAD";
    public static String CENTRO_POBLADO = "CENTRO_POBLADO";
    public static String CENTRO_POBLADO_ID = "ID_CENTRO_POBLADO";
    public static String BARRIO = "BARRIO";
    public static String DIRECCION = "DIRECCION_SUBEDIFICIO";
    public static String NOMBRE = "NOMBRE_CUENTA";
    public static String ADMINISTRACION = "COMPANIA_ADMINISTRACION";
    public static String ASCENSOR = "COMPANIA_ASCENSOR";
    public static String CONSTRUCTORA = "COMPANIA_CONSTRUCTORA";
    public static String ADMINISTRADOR = "ADMINISTRADOR";
    public static String TEL_UNO = "TELEFONO_PORTERIA";
    public static String TEL_DOS = "TELEFONO_PORTERIA2";
    public static String PROYECTO = "TIPO_PROYECTO";
    public static String DATOS = "ORIGEN_DATOS";
    public static String NODO = "CODIGO_NODO";
    public static String ESTADO = "ESTADO_TECNOLOGIA";
    public static String CONFIGURACION = "TIPO_CONF_DISTB";
    public static String ALIMENTACION = "ALIMENTACION_ELECTRICA";
    public static String DISTRIBUCION = "TIPO_DISTRIBUCION";
    public static String TIPO_CCMM = "TIPO_CCMM";
    public static String BLACKLIST_TECNOLOGIA = "BLACKLIST_TECNOLOGIA";
    public static String NUEVO = "NUEVO_DATO";
    public static String NOTA = "NOTAS";
    public static String ID_BLACKLIST = "ID_BLACKLIST";
    public static String SHEET_NAME = "MODIFICACION_MASIVA_CM";
    public static final String ORIGEN_CARGA_ARCHIVO_COSTOS = "C";
    public static final String ORIGEN_CARGA_ARCHIVO_FINANCIERA = "F";
    public static final String ORIGEN_CARGA_ARCHIVO_ACOMETIDA = "A";
    public static final String ORIGEN_CARGA_ARCHIVO_CIERRE_OT = "CO";
    public static final String ORIGEN_CARGA_ARCHIVO_DOCUMENTOS_AGENDAMIENTOS = "DA";
    public static String SEPARADOR = "|";
    public static String EXCEL_PASSWORD = "545dffgfhjho5j54d5df5g5fg554asd5srcvbjk";
    public static String TECNO_SUBEDIFICIO_ID = "TECNO_SUBEDIFICIO_ID";
    public static String SUBEDIFICIO_ID = "SUBEDIFICIO_ID";
    public static String NOMBRE_SUBEDIFICIO = "NOMBRE_SUBEDIFICIO";
    public static String DIRECCION_SUBEDIFICIO = "DIRECCION_SUBEDIFICIO";
    public static String TIPO_SUBEDIFICIO = "TIPO_SUBEDIFICIO";
    public static String CUENTAMATRIZ_ID = "CUENTAMATRIZ_ID";
    public static String NUMERO_CUENTA = "NUMERO_CUENTA";
    public static String EXPORT_XLS_MAX = "No se pueden descagar archivos con mas de 2000 registros";
    public static String EXPORT_CSV_MAX = "No se pueden descagar archivos con mas de 40000 registros";
    public static String EXPORT_TXT_MAX = "No se pueden descagar archivos con mas de 40000 registros";
    public static String MSN_ERROR_COMPANIA_ADMIN = " el nombre de la compañia de administracion no existe: ";
    public static String MSN_ERROR_COMPANIA_ASCENSOR = " el nombre de la compañia de ascensor no existe: ";
    public static String MSN_ERROR_COMPANIA_CONSTRUCTORA = " el nombre de la compañia de constructora no existe: ";
    public static String MSN_ERROR_TIPO_PROYECTO = " el nombre tipo de proyecto no existe: ";
    public static String MSN_ERROR_ORIGEN_DATOS = " el nombre origen de datos no existe: ";
    public static String MSN_ERROR_TIPO_TECNOLOGIA = " el nombre de la tecnlogia no existe: ";
    public static String MSN_ERROR_CODIGO_NODO = " el nombre codigo del nodo no existe: ";
    public static String MSN_ERROR_ESTADO_TECNOLOGIA = " el nombre del estado de la tecnlogia no existe: ";
    public static String MSN_ERROR_BLACKLIST_TECNOLOGIA = " el nombre del blacklist  no existe: ";
    public static String MSN_ERROR_TIPO_CCMM = " el nombre del tipo de subedificio no existe: ";
    public static String MSN_ERROR_CONFIGURACION = " el nombre del tipo  de configuracion no existe: ";
    public static String MSN_ERROR_ALIMENTACION_ELECTRICA = " el nombre de la alimentacion electrica no existe: ";
    public static String MSN_ERROR_DISTRIBUCION = " el nombre del tipo de distribucion no existe: ";
    public static String MSN_ERROR_MARCA = " La marca no existe o no corresponde a una marca de Tipo Fraude";
    public static String MSN_DIR_NO_VALIDADA = "El Id Dirección detallada o Dirección no existen";
    public static String MSN_DIR_NO_ENCONTRADA = "La dirección no existe en mer o no es un HHPP";
    public static String MSN_ERROR_CUENTA_MATRIZ = " cuenta matriz: ";
    public static String TIEMPO_REPORTE = "TIEMPO_REPORTE";
    public static String SINCRONIZA_RR = "SINCRONIZA_RR";
    public static String TIPO_VALIDACION_DIR_HHPP = "HHPP";
    public static String TIPO_VALIDACION_DIR_CM = "CM";
    public static String TIPO_VALIDACION_DIR_CM_HHPP = "CM_HHPP";
    public static String REPORTE_TECNOLOGIA = "tecnologia";
    public static String REPORTE_FECHA_INICIAL = "fechaInicial";
    public static String REPORTE_FECHA_FINAL = "fechaFinal";
    public static String REPORTE_NODO = "nodo";
    public static String REPORTE_ESTRATO = "estrato";
    public static String ESTRATO_1 = "@EST_1";
    public static String ESTRATO_2 = "@EST_2";
    public static String ESTRATO_3 = "@EST_3";
    public static String ESTRATO_4 = "@EST_4";
    public static String ESTRATO_5 = "@EST_5";
    public static String ESTRATO_6 = "@EST_6";
    public static String ESTRATO_7 = "@EST_7";
    public static String ESTRATO_NG = "@EST_NG";
    public static String ESTRATO_NR = "@EST_NR";
    

    /**
     * Variables para verificar el estado de flujo este en un estado que no sea
     * cancelado
     *
     * @author rodriguezluim
     */
    public static final String ESTADO_FLUJO_CANCELADO = "C";
    public static final String ESTADO_FLUJO_FINALIZADO = "F";

    public static final String COMPLEMENTOS_TIPO_BASICA = "infoAdi";
    public static final String EQUIVALENTE_RR = "@estadosRr";//ok

    public static final String BASICA_TORRE = "@B_TORRE";
    public static final String BASICA_ACOMETIDA_CABLE = "@BACOCAB";
    public static final String BASICA_TIPO_TEC_CABLE = "@CABLE";
    public static final String BASICA_EST_HHPP_TIPO_POTENCIAL = "@FREE";
    public static final String BASICA_EST_HHPP_TIPO_PRECABLEADO = "@PREWIRE";
    public static final String BASICA_EST_HHPP_TIPO_SUSPENDIDO = "@SUSP";
    public static final String BASICA_EST_HHPP_TIPO_DESCONECTADO = "@DESC";
    public static final String BASICA_EST_HHPP_TIPO_INSERVICE = "@INSERV";
    public static final String BASICA_EST_HHPP_TIPO_DES_FISICA = "@DESFI";
    public static final String BASICA_ORIGEN_ELIMINAR_CM = "@B_O_EL_CM";
    public static final String BASICA_ELIMINAR_CM = "@B_EL_CM";
    public static final String BASICA_ELIMINAR_CM_RECHAZO = "@B_EL_CM_R";
    public static final String BASICA_ELIMINAR_CM_ACEPTA = "@B_EL_CM_A";
    public static final String BASICA_ELIMINAR_CM_HHPP = "@B_E_CM_H";
    public static final String BASICA_ESCALAR_HHPP_SERVICIO = "@B_E_H_S";
    public static final String BASICA_ESCALAR_EQUIPOS_ASOCIADOS = "@B_E_E_A";
    public static final String BASICA_ESCALAR_HHPP_SERVICIO_DESASOCIADOS = "@B_E_H_SD";
    public static final String BASICA_ESCALAR_EQUIPOS_DESASOCIADOS = "@B_E_E_D";
    public static final String BASICA_RECHAZAR_HHPP_SERVICIO = "@B_E_R_H";
    public static final String BASICA_RECHAZAR_EQUIPOS_ASOCIADOS = "@B_E_R_A";

    public static final BigDecimal ESTRATO_DIRECCION_NR = BigDecimal.ZERO;
    public static final BigDecimal ESTRATO_DIRECCION_NG = new BigDecimal("-1");
    public static final String ERROR = "Error";
    public static final BigDecimal NUMERO_CM_MGL = BigDecimal.ZERO;
    //Constate para la url del servidor donde esta la cola de JMS TCRM
    public static final String URL_TCRM_JMS = "URL_TCRM_JMS";
    public static final String CAMBIO_ESTRATO_ACEPTADO = "ACEPTADO";
    public static final String CAMBIO_ESTRATO_RECHAZADO = "RECHAZADO";
    public static final String ESTADO_SOL_VERIFICADO = "VERIFICADO";
    public static final String CAMBIO_DE_ESTRATO_REALIZADO = "CAMBIO DE ESTRATO REALIZADO";
    public static final String ESTADO_SOL_FINALIZADO = "FINALIZADO";
    public static final String CREAR_HHPP_VERIFICACION_AGENDADA = "VERIFICACION AGENDADA";
    public static final String CREAR_HHPP = "HHPP CREADO";
    public static final String CREADO_AUTOMATICAMENTE = "CREADO AUTOMATICAMENTE";
    public static final String HHPP_NO_VIABLE = "HHPP NO VIABLE";

    public static String PROPERTY_URL_WS_MAXIMO = "PROPERTY_URL_WS_MAXIMO";

    public static final String TIPO_RES_RESTRICCION = "RESTRICCION";
    public static final String TIPO_RES_DISPONIBLE = "DISPONIBLE";
    public static final String TIPO_RES_NO_DISPONIBLE = "NO_DISPONIBLE";
    public static final String NO_MARCADO = "NO_MARCADO";

    public static final String LETRA_ORIGEN_WFM_MGL = "LETRA_ORIGEN_WFC_MGL";
    public static final String LETRA_TIPO_ORDEN_MGW_CM = "LETRA_TIPO_ORDEN_MGW_CM";
    public static final String LETRA_TIPO_ORDEN_MGW_SEG = "LETRA_TIPO_ORDEN_MGW_SEG";
    public static final String CERRADURA_ELECT_S = "S";
    public static final String CERRADURA_ELECT_N = "N";
    public static final String XA_TIPO_ORDEN_MGW = "XA_TipoOrdenMGW";
    public static final String LETRA_TIPO_ORDEN_MGW_HHPP = "LETRA_TIPO_ORDEN_MGW_HHPP";
    public static final String LETRA_TIPO_ORDEN_MGW_HHPP_MULTI = "LETRA_TIPO_ORDEN_MGW_HHPP_MULTI";
    public static final String PROPIEDAD_ORIGEN_ORDEN = "PROPIEDAD_ORIGEN_ORDEN";
    public static final String BASICA_EST_AGENDA_AGENDADA = "@B_EAA";
    public static final String BASICA_EST_AGENDA_NODONE = "@B_EAN";
    public static final String BASICA_EST_AGENDA_CERRADA = "@B_EAC";
    public static final String BASICA_EST_AGENDA_SUSPENDIDA = "@B_EAS";
    public static final String BASICA_EST_AGENDA_REAGENDADA = "@B_EAR";
    public static final String BASICA_EST_AGENDA_CANCELADA = "@B_EACAN";
    public static String PROPERTY_UCM_TIPO_DOCUMENTAL = "PROPERTY_UCM_TIPO_DOCUMENTAL";
    public static String PROPERTY_UCM_TIPO_EMPRESA = "PROPERTY_UCM_TIPO_EMPRESA";
    public static String BASICA_EST_INT_CIERRECOM = "@B_CCOK";
    public static String BASICA_EST_INT_CIERRECOMVOK = "@B_CCOMK";
    public static String BASICA_EST_INT_PENDOCCIECOM = "@B_PDCC";
    public static String BASICA_EST_INT_APROB_FIN_PEN_GESTION = "@B_AFPG";
    public static String BASICA_EST_INT_PENCIECOM = "@B_PCC";
    public static String BASICA_EST_INT_CANCELADO = "@BCAN";
    public static String DETALLE_FLUJO_APRO_FINAN = "@APFIN";
    public static String FUNCIONALIDAD_DIRECCIONES = "DIRECCIONES";
    public static String RZ_REACTIVACION_HHPP_NO_REALIZADO = "REACTIVACION HHPP NO REALIZADO";
    public static String RESULTADO_GESTION_HHPP_UNI = "RESULTADO_GESTION_HHPP_UNI";
    public static String RESULTADO_GESTION_VC = "RESULTADO_GESTION_VC";
    public static String RZ_HHPP_CREADO = "HHPP CREADO";
    public static String RZ_REACTIVACION_HHPP_REALIZADO = "REACTIVACION HHPP REALIZADO";
    public static String RZ_CAMBIO_DE_ESTRATO_REALIZADO = "CAMBIO DE ESTRATO REALIZADO";
    public static String RZ_VERIFICACION_AGENDADA = "VERIFICACION AGENDADA";
    public static String RZ_HHPP_CREADOS = "HHPP CREADOS";
    public static String RZ_CAMBIO_DE_DIRECCION_NOREALIZADO = "CAMBIO DE DIRECCION NO REALIZADO";
    public static String TIPO_RPT_NO_VALIDA = "TIPO DE RESPUESTA NO VALIDA";
    public static String RZ_REGIONAL_SIN_CUPOS_DE_VERIFICACION = "REGIONAL SIN CUPOS DE VERIFICACION";
    public static String RZ_GESTION_HHPP_FORMA_MANUAL = "GESTION DE HHPP DE FORMA MANUAL";

    public static String ABR_TIPO_OT_VERIFICACION = "@OT_VR";
    public static String ABR_TIPO_OT_AMPLIACION_TAB = "@OT_AT";
    public static final String ESTADO_AGENDA_AGENDADA = "@B_EAA";
    public static final String ESTADO_AGENDA_NODONE = "@B_EAN";
    public static final String ESTADO_AGENDA_CERRADA = "@B_EAC";
    public static final String ESTADO_AGENDA_SUSPENDIDA = "@B_EAS";
    public static final String ESTADO_AGENDA_REAGENDADA = "@B_EAR";
    public static final String ESTADO_AGENDA_CANCELADA = "@B_EACAN";

    
    public static final String VISOR_MOVILIDAD = "VISORMOVILIDAD";
    
    //TIPOVETO
    public static String VETO_TIPO_VENTA = "1";
    public static String VETO_TIPO_CREACION_HHPP = "2";
    public static String VETO_TIPO_CREACION_HHPP_VENTA = "3";

    //Busqueda en MGL_BASICA por nombre para constructura
    public static String NOMBRE_BASICA_CONSTRUCTORA = "CONSTRUC";
   
    //Estados HHPP
    public static String ESTADO_HHPP_POTENCIAL = "@B_POT";
    public static String ESTADO_HHPP_PRE_CABLEADO = "@B_PCR";
    public static String ESTADO_HHPP_CON_SERVICIO = "@B_COS";
    public static String ESTADO_HHPP_SUSPENDIDO = "@B_SUS";
    public static String ESTADO_HHPP_DESCONEXION_FISICA = "@B_DEF";
    public static String ESTADO_HHPP_DESCONECTADO = "@B_DES";
    public static String ESTADO_HHPP_NO_EXISTIRA = "@B_NOE";

    public static String CONSULTA_OT_WITH_ROLES = "CONSULTA_OT_WITH_ROLES";
    
    //Nombre validacion viabilidad
    public static final String BASICA_TIPO_VALIDACION_PARAMETRIZADA_CREAR_SOLICITUD = "@TB_FVP_CS";
    public static final String BASICA_TIPO_VALIDACION_PARAMETRIZADA_ELIMINAR_CM = "@TB_FVP_EC";
    
    //Tecnologias
    public static final String DTH_NOMBRE = "DTH";
    public static final String FIBRA_FTTTH_NOMBRE = "Fibra FTTH";
    public static final String FIBRA_OP_GPON_NOMBRE = "Fibra Optica GPON";
    public static final String FIBRA_OP_UNI_NOMBRE = "Fibra Optica Unifilar";
    public static final String HFC_BID_NOMBRE = "HFC Bidireccional";
    public static final String HFC_UNI_NOMBRE = "HFC Unidireccional";
    public static final String LTE_INTERNET_NOMBRE = "LTE";
    public static final String BTS_MOVIL_NOMBRE = "Movil";
    //Tecnologias identificadores app
    public static final String HFC_UNI = "@B_UNI";
    public static final String HFC_BID = "@B_BI";
    public static final String DTH = "@B_DTH";
    public static final String FIBRA_FTTTH = "@B_FTH";
    public static final String FIBRA_FTTX = "@B_FTX";
    public static final String FIBRA_OP_GPON = "@B_FOG";
    public static final String BTS_MOVIL = "@B_MOV";
    public static final String FIBRA_OP_UNI = "@B_FOU";
    public static final String LTE_INTERNET = "@B_LTE";
    public static final String RED_FO = "@B_FOR"; 
    public static final String INTERNET_SOCIAL_MINTIC = "@B_ISM";
    
       //Estados cm
    public static final String ESTADO_DTH = "@EST_DTH";
    public static final String ESTADO_FIBRA_FTTTH = "@EST_FTTH";
    public static final String ESTADO_FIBRA_OP_GPON = "@EST_GPON";
    public static final String ESTADO_FIBRA_OP_UNI = "@EST_FO";
    public static final String ESTADO_HFC_BID = "@EST_BI";
    public static final String ESTADO_HFC_UNI = "@EST_UNI";
    public static final String ESTADO_LTE_INTERNET = "@EST_LTE";
    public static final String ESTADO_BTS_MOVIL = "@EST_MOVIL";
    
    public static final String BASICA_RAZON_CERRAR = "@B_CAGE";
    public static final String RAZON_CANCELAR = "C06";
    public static final String COMENTARIO_CANCELAR = "Se cancela agenda por cierre de ultima agenda";
    public static final String RR_HABILITADO = "1";
    public static final String RR_INHABILITADO = "0";
    
    public static final String USARIO_TCRM_SFTP = "USARIO_TCRM_SFTP";
    public static final String HOST_TCRM_SFTP = "HOST_TCRM_SFTP";
    public static final String PASSWORD_TCRM_SFTP = "PASSWORD_TCRM_SFTP";
    public static final String PUERTO_TCRM_SFTP = "PUERTO_TCRM_SFTP";
    public static final String RUTA_CARGAR_ARCHIVO_TCRM = "RUTA_CARGAR_ARCHIVO_TCRM";
    public static final String RUTA_BAJAR_ARCHIVO_TCRM = "RUTA_BAJAR_ARCHIVO_TCRM";
    public static final String TIEMPO_SEG_CONSULTE_TCRM = "TIEMPO_SEG_CONSULTE_TCRM";
    public static final String TIEMPO_MIN_CONSULTE_TCRM = "TIEMPO_MIN_CONSULTE_TCRM";
    public static final String TIEMPO_HOR_CONSULTE_TCRM = "TIEMPO_HOR_CONSULTE_TCRM";
    public static final String TIEMPO_DAY_CONSULTE_TCRM = "TIEMPO_DAY_CONSULTE_TCRM";
    public static final String TIEMPO_MONTH_CONSULTE_TCRM = "TIEMPO_MONTH_CONSULTE_TCRM";
    public static final String NO_GEOREFENCIADO = "NG";
    public static final String NO_RESIDENCIAL = "NR";
    public static final String BASICA_ATRIBUTO_CCMM_DIRECCION = "@B_ATD";
    public static final String TIPOS_DOCUMENTO_SOPORTE = "DOCUMENTO_SOPORTE";
    public static final String TIPO_SOL_MOD_CM = "MODIFICACION_CM";
    public static final String NO_TIENE_TRABAJOS = "Edificio no tiene trabajos asociados";
    public static String MSN_CODIGO_NODO = "Codigo del Nodo es obligatorio.";  
    public static final String MSG_REPORTE_EXITOSO = "Se ha terminado de procesar el archivo.";
    public static final String MSG_REPORTE_NOMBRE_ARCHIVO = "El archivo que intenta cargar, ya ha sido cargado";
    public static final String MARCA_HHPP_SIN_VERIFICACION = "HSV";
    public static final String MSG_REPORTE_NOCTURNO = "Procesar en horario nocturno";
    
    //TEC_TIPO_MARCAS
    public static final String TEC_TIPO_MARCA_FRAUDE = "FD";
    
    //codigos basiscas para  atributos hhpp
     public static final String NOMBRE_TIPO_ATRIBUTOS_HHPP = "ATRIBUTOS HHPP";
     public static final String CODIGO_BASICA_DIRECCION = "541";
     public static final String CODIGO_BASICA_ESTADO = "542";
     public static final String CODIGO_BASICA_ESTRATO = "543";
     public static final String CODIGO_BASICA_BARRIO = "544";
     public static final String CODIGO_BASICA_COBERTURA = "545";
     public static final String CODIGO_BASICA_TIPO_VIVIENDA = "546";
     public static final String CODIGO_BASICA_ETIQUETA = "547";
     public static String BASICA_EST_INT_CANCELADO_VT_ANT = "@B_CANVA";
     public static String BASICA_EST_INT_CERRADO = "@BCER";
     public static String BASICA_EST_INT_FINALIZADO = "@B_FIN";
     public static String TIPO_COMPANIA_CONSTRUCTORA = "CONSTRUCTORAS";
     public static String RUTA_SAVE_DOC_CAMBIO_ESTRATO="RUTA_SAVE_DOC_CAMBIO_ESTRATO";
     public static String PROCESO_MASIVO_HHPP = "MASIVO_HHPP";
     public static final String OPCION_NIVEL_5_CASAS = "CASA";
     public static final String OPCION_NIVEL_OTROS = "OTROS";
     public static final String TYPE_SOLICITUD_ELIMINACION_CM = "ELIMINACION_CM";
     public static final String TYPE_SOLICITUD_ELIMINACION_ESCALAMIENTO = "ESCALADO_ACOMETIDAS";
     public static final String TYPE_SOLICITUD_CREACION_CM = "CREACION_CM";
     public static final String TYPE_SOLICITUD_MODIFICACION_CM = "MODIFICACION_CM";
     public static final String TYPE_SOLICITUD_VISITA_TECNICA = "VISITA_TECNICA";
     public static final String TYPE_SOLICITUD_CREACION_HHPP = "CREACION HHPP UNIDI";
     public static final String TYPE_SOLICITUD_MODIFICACION_HHPP = "MODIFICACION_HHPP";

     //Distancia cada 2 km para coordenadas de una direccion (0,00000534 cada 2 metros)
     public static final double DISTANCIA_1_METRO_COORDENADA = 0.000010127;
     
     //RAZONES CREACION HHPP
     public static final String RESULTADO_HHPP_CREADO = "HHPP CREADO";
     public static final String RESULTADO_VERIFICACION_AGENDA = "VERIFICACION AGENDADA";

     //DIAS A MOSTRAR AGENDA WORKFORCE
     public static final int DIAS_A_MOSTRAR_AGENDA = 9;
     
     //Parametros Corregimientos
     public static final String CORREGIMIENTO_FICHAS = "CORREGIMIENTO_FICHAS";
     
     //Nombre de tareas programadas
     public static final String TAREAS_PROGRAMADAS_HHPP_UPDATE = "HHPP_UPDATE";	
     
    //Identificador del dato para la configuracion de la tarea
     public static final String CONF_TASK_HHPP_UPDATE = "CONF_TASK_HHPP_UPDATE";	
     
    //Identificador del dato para obtener el flag de la tarea de update de HHPP
     public static final String FLAG_TAREA_PROGRAMADA_HHPP_UPDATE = "TAREA_PROGRAMADA_HHPP_UPDATE";	

     //Parametros para primer ejecucion de tarea programada Hhpp update
     public static final String FECHA_INICIO_TAREA_PROGRAMADA_HHPP_UPDATE = "FECHA_INICIO_TAREA_PROGRAMADA_HHPP_UPDATE";	
     public static final String HORA_INICIO_TAREA_PROGRAMADA_HHPP_UPDATE = "HORA_INICIO_TAREA_PROGRAMADA_HHPP_UPDATE";	
     public static final String FECHA_FIN_TAREA_PROGRAMADA_HHPP_UPDATE = "FECHA_FIN_TAREA_PROGRAMADA_HHPP_UPDATE";	
     public static final String HORA_FIN_TAREA_PROGRAMADA_HHPP_UPDATE = "HORA_FIN_TAREA_PROGRAMADA_HHPP_UPDATE";	
     public static final String CANTIDAD_REGISTROS_SOLICITADOS_POR_PAGINA_HHPP_UPDATE = "CANTIDAD_REGISTROS_SOLICITADOS_POR_PAGINA_HHPP_UPDATE";	     
     
      // acronimo parametros para expresiones regulares fichas nodo
       public static final String EXP_REG_VALIDA_PLACA_BM_FICHAS_NODO= "EXP_REG_VALIDA_PLACA_BM_FICHAS_NODO"; 
       public static final String EXP_REG_VALIDA_PLACA_CK_FICHAS_NODO= "EXP_REG_VALIDA_PLACA_CK_FICHAS_NODO"; 
       public static final String EXP_REG_VALIDA_PLACA_IT_FICHAS_NODO= "EXP_REG_VALIDA_PLACA_IT_FICHAS_NODO"; 
      
       public static final String EXP_REG_VALIDA_DIR_BM_FICHAS_NODO= "EXP_REG_VALIDA_DIR_BM_FICHAS_NODO"; 
       public static final String EXP_REG_VALIDA_DIR_CK_FICHAS_NODO= "EXP_REG_VALIDA_DIR_CK_FICHAS_NODO"; 
       public static final String EXP_REG_VALIDA_DIR_IT_FICHAS_NODO= "EXP_REG_VALIDA_DIR_IT_FICHAS_NODO"; 
       
       
       //ACRONIMOS PARA TIPOS DE DIRECCIONES
       public static final String ACRONIMO_BM= "BM"; 
       public static final String ACRONIMO_IT= "IT"; 
       public static final String ACRONIMO_CK= "CK"; 
       
       //CLASIFICACIONES PREFICHA FICHAS NODOS
       public static final String HHPPSN = "HHPPSN";
       public static final String CASAS_RED_EXTERNA = "CASASREDEXTERNA";
       public static final String EDIFICIOSVT = "EDIFICIOSVT";
       public static final String CONJUNTOCASASVT = "CONJUNTOCASASVT";
       public static final String EDIFICIOSVT_NO_PROCESADOS = "EDIFICIOSVT_NO_PROCESADOS";
       public static final String CASAS_RED_EXTERNA_NO_PROCESADOS = "CASASREDEXTERNA_NO_PROCESADOS";
       public static final String CONJUNTOCASASVT_NO_PROCESADOS = "CONJUNTOCASASVT_NO_PROCESADOS";
       
       //TIPO DE APLICACION EN TIPO DE SOLICITUD ROLES
      public static final String TIPO_APLICACION_SOLICITUD_HHPP = "VT";
      public static final String TIPO_APLICACION_SOLICITUD_CCMM = "CM";
       
       public static String BASICA_EST_INT_CABLE = "@BACOCAB";
       
       //Usuario por default para la aplicación VISOR que se encuentra en ParametrosMGL
       public static String USUARIO_VISOR_DEFAULT = "USUARIO_VISOR_DEFAULT";
       
       
       //paginacion 
      public static final int PAGINACION_OCHO_FILAS = 8;
    
    
    /**
     * N&uacute;mero m&aacute;ximo de caracteres que soporta RR para el nombre
     * de usuario.
     */
    public final static int MAX_RR_USER_ID_LENGTH = 10;
    
    public final static String ACTIVAR_CREACION_OT_RR= "ACTIVAR_CREACION_OT_RR";
    
    public final static String ACTIVACION_ENVIO_UCM= "ACTIVACION_ENVIO_UCM";
    
    
    public final static String MODIFICACION_CM = "MODIFICACION_CM";
    
    public final static String DIFERENTE_MODIFICACION_CM = "DIFERENTE_MODIFICACION_CM";
    
    public static String FECHA_CREACION = "FECHA_CREACION";
    
    public static String FECHA_ULT_MOD = "FECHA_ULTIMA_MODIFICACION";
    
    public static final String BASICA_TIPO_ATRIBUTOS_HHPP = "@TB_ATHH";
    
    public static final String SPLIT_CADENA_NOTAS_OT_HIJA = "NOTA: Insertado Por:";
   
    public static final String DIVISION_DIR_CK = "DIVISION_DIR_CK";
    
    public final static String ACTIVAR_CONSULTA_PORTAL_US= "ACTIVAR_CONSULTA_PORTAL_US";
    	
	 public static String SEPARADOR_ARCHIVO_CVS = ";";
    public static String ID_DIRECCION_DETALLADA_DF = "ID_DIRECCION_DETALLADA";
    public static String DEPARTAMENTO_DF = "DEPARTAMENTO";
    public static String MUNICIPIO_DF = "MUNICIPIO";
    public static String CENTRO_POBLADO_DF = "CENTRO POBLADO";
    public static String DIRECCION_DF = "CENTRO DIRECCION_DF";
    public static String NODO_DF = "NODO";
    public static String MARCAS_DF = "MARCAS";
    public static String TIPO_MARCA_FRAUDES = "2";
   
    // Filtro fechas reporte ordenes de cm
    public static String FECHA_CREACION_OT = "Creacion OT";
    public static String FECHA_CREACION_OT_HIJA_ONYX = "Creacion ONYX Hija";
    public static String FECHA_AGENDACMIENTO_OFSC = "Agendamiento OFSC";
    public static String FECHA_ASIGNACION_TECNICO_OFSC = "Asignacion tecnico OFSC";
    public static String FECHA_CIERRE_AGENDA_OFSC = "Cierre agenda OFSC";
    public static String FECHA_CANCELACION_AGENDA_OFSC = "Cancelacion OFSC";
    public static String FECHA_REAGENDAMIENTO_OFSC = "Reagendamiento OFSC";
    public static String FECHA_SUSPENSION_OFSC = "Suspension OFSC";
     public static String FECHA_CREACION_ONYX = "Creacion ONYX";
    
    public static final String RUTA_SERVLET_MAP = "RUTA_SERVLET_MAP";        
    public static final String RUTA_MAPA_GOOGLE = "RUTA_MAPA_GOOGLE";
    
    public static final String CLAVE_ENCRIPTAR = "Claro.*2019#123";

    //Estados iniciales 
    public static final String EST_INI_HFC_UNI = "@B_SVT";
    public static final String EST_INI_HFC_BID = "@B_SVT";
    public static final String EST_INI_DTH = "@EST_DTH";
    public static final String EST_INI_FIBRA_FTTTH = "@EI_FTTH";
    public static final String EST_INI_FIBRA_OP_GPON = "@EI_FO";
    
    public static final String RESULTADO_GESTION_VAL_COBER = "RESULTADO_GESTION_VAL_COBER";
    
    
    //Solicitud Validacion de cobertura
    public static final String VALIDACION_COBERTURA = "73";
    public static final String TIPO_SOLICITUD_VALIDACION_COBERTURA = "12";
    public static final String SOL_VALIDACION_COBERTURA = "Validacion de Cobertura";
    public static final String VALIDACION_COBERTURA_12 = "12";
    public static String VALIDACION_COBERTURA_MENSUAL = "VALIDACION_COBERTURA_MENSUAL";
    
    public static String REPORTE_EXCEL_REG_MAX = "REPORTE_EXCEL_REG_MAX";
    public static String REPORTE_CVS_TXT_REG_MAX = "REPORTE_CVS_TXT_REG_MAX";
    public static String REPORTE_EXCEL_HIST_REG_MAX = "REPORTE_EXCEL_HIST_REG_MAX";
    public static String REPORTE_CVS_TXT_HIST_REG_MAX = "REPORTE_CVS_TXT_HIST_REG_MAX";
    public static final String BASICA_TIPO_EDIFICIO_AGRUPADOR_DIRECCIONES_BARRIO = "@B_ADIRB";
    public static final String DURANTE_EL_DIA = "Durante el Dia";
    
    public static String USER_AUTENTICACION_GESTOR_DOCUMENTAL = "USER_AUTENTICACION_GESTOR_DOCUMENTAL";
    public static String PASS_AUTENTICACION_GESTOR_DOCUMENTAL = "PASS_AUTENTICACION_GESTOR_DOCUMENTAL";
    public static String PROPERTY_URL_WS_UCM_UPLOAD_CCMM = "PROPERTY_URL_WS_UCM_UPLOAD_CCMM";
    public static String PROPERTY_URL_WS_UCM_UPLOAD_ORDENES = "PROPERTY_URL_WS_UCM_UPLOAD_ORDENES";
    public static String PROPERTY_URL_WS_UCM_SEARCH_CCMM = "PROPERTY_URL_WS_UCM_SEARCH_CCMM";
    public static String PROPERTY_URL_WS_UCM_SEARCH_ORDENES = "PROPERTY_URL_WS_UCM_SEARCH_ORDENES";
    public static String GESTOR_DOCUMENTAL_LOGIN_URL = "GESTOR_DOCUMENTAL_LOGIN_URL";
    
    public static final String TIPO_DOCUMENTAL_CUENTA_MATRIZ = "@B_TPDCM";
    public static final String TIPO_DOCUMENTAL_DISEÑO_RED = "@B_TPDDR";
    public static final String TIPO_DOCUMENTAL_SOPORTE_POM = "@B_TPDSPOM";
    public static final String TIPO_DOCUMENTAL_CIERRE_CUENTA_MATRIZ = "@B_TPDECM";
    public static final String TIPO_DOCUMENTAL_ENTREGADA_CLIENTE = "@B_TPDEEC";
    public static final String TIPO_DOCUMENTAL_NODONE_CLIENTE = "@B_TPDENC";
    public static final String TIPO_DOCUMENTAL_NODONE_CUENTA_MATRIZ = "@B_TPDENCCM";
    public static final String TIPO_DOCUMENTAL_VISITA_TECNICA = "@B_TPDEVST";
    public static String ACTIVAR_SAVE_REQUEST_RESPONSES = "ACTIVAR_SAVE_REQUEST_RESPONSES";
    public static String BASE_URI_ATENCION_INMEDIATA = "BASE_URI_ATENCION_INMEDIATA";
    public static String KEY_EAF_ORACLE = "KEY_EAF_ORACLE";
    public static String PROPERTY_URL_WS_NOTIFICA_UPDATE_CLOSING_ONIX = "PROPERTY_URL_WS_NOTIFICA_UPDATE_CLOSING_ONIX";
    public static String PROPERTY_URL_WS_RS_CUSORDE_FEASIB_PROFILING = "PROPERTY_URL_WS_RS_CUSORDE_FEASIB_PROFILING";
    public static final String NIVEL_UNO_NAP = "@B_ITN1NAP";
    public static final String NIVEL_UNO_NODO = "@B_ITN1NO";
    public static final String NIVEL_UNO_IDZ = "@B_ITN1IDZ";
    public static final String HOSTPOT = "@B_HOST";
    public static final String BASICA_TIPO_NOTA_AGENDA = "@B_NOTAGE"; 
    public static String REPORTE_REG_MAX_CONSULTA = "REPORTE_REG_MAX_CONSULTA";
    public static String REPORTE_HIST_REG_MAX = "REPORTE_HIST_REG_MAX";
    public static String REPORTE_HIST_REG_MAX_CONSULTA  = "REPORTE_HIST_REG_MAX_CONSULTA";
    public static String REFRESH_MAX = "REFRESH_MAX";
    public static String REFRESH_MIN = "REFRESH_MIN";
    public static String TECNOLOGIA_MANEJA_NODOS = "TECNOLOGIA_MANEJA_NODOS";
    public static String TIPO_OT_MTO_CCMM = "TIPO_OT_MTO_CCMM";
    public static String TIPO_OT_MTO_DIR = "TIPO_OT_MTO_DIR";
    public static String TIPO_OT_TRAS_CCMM = "TIPO_OT_TRAS_CCMM";
    public static String TIPO_OT_TRAS_DIR = "TIPO_OT_TRAS_DIR";
    public static String TIPO_OT_TRAS_INT_CCMM = "TIPO_OT_TRAS_INT_CCMM";
    public static String TIPO_OT_TRAS_INT_DIR = "TIPO_OT_TRAS_INT_DIR";
    
    public static String TAMAÑO_NAME_USERS = "TAMAÑO_NAME_USERS";
    //Identificador del dato para obtener el flag de la tarea de update de HHPP
    public static final String FLAG_TAREA_PROGRAMADA_BAJAR_TCRM = "TAREA_PROGRAMADA_BAJAR_TCRM";
    public static final String WADL_SERVICES_AGENDAMIENTOS = "WADL_SERVICES_AGENDAMIENTOS";
    public static final String WSDL_OT_HIJA_ONIX = "WSDL_OT_HIJA_ONIX";
    public static String CANAL_AGENDA_INMEDIATA = "CANAL_AGENDA_INMEDIATA";
    public static String TIME_OUT_AGENDA_INMEDIATA_IFRAME = "TIME_OUT_AGENDA_INMEDIATA_IFRAME";
    public static String TIME_OUT_SERVICIOS_AGENDAMIENTO = "TIME_OUT_SERVICIOS_AGENDAMIENTO";
    //CONSTANTE DEL TIPO DE MARCA - TIPO FRAUDE
    public static final String TIPO_MARCA_FRAUDE_HHPP = "2";
    //CONSTANTES DE IDENTIFICADOS DE BASICA
    public static final String BASICA_FOESTADO_AGENDADO = "@B_FOAGEN";
    public static final String BASICA_FOESTADO_PENDIENTE = "@B_FOPENDGES";
    //CARGUES MASIVOS
    public static final String ORIGEN_MASIVOS = "ORIGEN_CARGUES_MASIVOS";
        
    public static final BigDecimal BASICA_PRODUCTO_FTTH = new BigDecimal("27693");
    public static final String CODIGO_BASICA_HFC_RED_EXTERNA = "12";
    public static final String CODIGO_BASICA_FTTH_RED_EXTERNA = "1094"; 
    public static final String NOMBRE_TIPO_ESTADOS_CCMM = "ESTADOS CUENTA MATRIZ";   
    public static final String PORTAL_LOGIN = "PortalLongin";
    public static final String USER_RR = "userRr";
    public static final String USER_SESION = "usersesion";

    public static final String NODO_TIPO_RED_TECNOLOGIA_FTTH = "2314";
    public static final String NODO_TIPO_RED_TECNOLOGIA_BI = "2310";
    public static final String NODO_CERTIFICADO = "NODO CERTIFICADO";
    public static final String NO_CERTIFICADO = "NODO NO CERTIFICADO";
    public static final BigDecimal NODO_TIPO_CERTIFICADO = new BigDecimal("23111");
    
    public static final String TEC_PARAM_ULTIMA_MILLA_FTTH = "FTTH";
    
    public static final String PO_CODIGO = "PO_CODIGO";    
    public static final String PO_DESCRIPCION  = "PO_DESCRIPCION";
    public static final String PO_CURSOR  = "PO_CURSOR";
    public static final String PROCESO_EXITOSO = "PROCESO_EXITOSO";
    public static final String PI_CUENTAMATRIZ_ID="PI_CUENTAMATRIZ_ID";
    
        //PROPIEDADES ENVIADAS A OFSC
    public static final String MAN_NO_OT_PADRE = "MAN_NO_OT_PADRE";
    public static final String MAN_NO_OT_HIJA = "MAN_NO_OT_HIJA";
    
    //PROPIEDADES VALORES HOMOLOGADOS TIPOS ORDENES MANTENIMIENTO PARA HHPP Y CCMM
    public static final String SUB_TIPO_OT_MTO_HHPP = "SUB_TIPO_OT_MTO_HHPP";
    public static final String SUB_TIPO_OT_MTO_CCMM = "SUB_TIPO_OT_MTO_CCMM";
    
    public static final String TEC_FTTH_CALCULATE_NODE = "FTTH";
    public static final String TEC_BI_CALCULATE_NODE = "BI";
    public static final String TEC_HFC_CALCULATE_NODE = "HFC";
    public static final String FAC_NEGATIVA = "NEGATIVA";
    public static final String RESP_CODE_NODE_APROX_OK = "OK0001";
    public static final String RESP_CODE_NODE_APROX_NO_DATA = "OK0002";
    public static final String INFO_NODE_APROX_NO_DATA = "NO HAY COBERTURA";
    public static final String RESP_CODE_NODE_APROX_ERR1 = "ERROR0001";
    public static final String RESP_CODE_NODE_APROX_ERR2 = "ERROR0002";
    public static final String RESP_CODE_NODE_APROX_ERR3 = "ERROR0003";
    public static final String RESP_CODE_NODE_APROX_ERR4 = "ERROR0004";
    public static final String INFO_NODE_APROX_GEN_ERROR = "Error en sitídata";
    public static final String RESP_CODE_NODE_APROX_ERR5 = "ERROR0005";
    
    // feature 348327 Constante de homologacion piso cajero 
    public static final String OPT_PISO_CAJERO = "PISO + CAJERO";
    
    //PROPIEDADES VALORES PARA LA ACTUALIZACION DE NODOS EN MANTENIMIENTO TABLAS DE TIPO_TECNOLOGIA
    public static final String DESACTIVAR_OPCION_OFSC = "DESACTIVAR_OPCION_OFSC";
    public static final String DESACTIVAR_OPCION_MG = "DESACTIVAR_OPCION_MG";
    public static final String DESACTIVAR_OPCION_SGO = "DESACTIVAR_OPCION_SGO";
}
