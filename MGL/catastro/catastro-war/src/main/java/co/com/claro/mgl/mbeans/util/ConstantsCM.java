package co.com.claro.mgl.mbeans.util;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * Manejo de constantes usadas para procesos de paginacion, validacion de
 * estados y otros atributos fijos.
 *
 * @author Parzifal de León
 */
public final class ConstantsCM {

    public static final int TIPO_COMPANIA_ID_ASCENSORES = 1;
    public static final int TIPO_COMPANIA_ID_CONSTRUCTORAS = 2;
    public static final int TIPO_COMPANIA_ID_ADMINISTRACION = 3;
    public static final int TIPO_COMPANIA_ID_ADMINISTRADORES = 4;
    public static final int HORA_RESTRICCION_ID_CONSTRUCTORAS = 1;
    public static final int HORA_RESTRICCION_ID_ADMINISTRACION = 2;
    public static final String SOLICITUDES_CM_CREACION_CUENTA_MATRIZ = "1";
    public static final String SOLICITUDES_CM_MODIFICACION_CUENTA_MATRIZ = "2";
    public static final String SOLICITUDES_CM_MODIFICACION_HHPP_CUENTA_MATRIZ = "3";
    public static final String APPLICATION_MANAGEDBEAN = "ApplicationBean";
    public static final String REGISTRO_ACTIVO = "Activo";
    public static final String REGISTRO_INACTIVO = "Inactivo";
    public static final String REGISTRO_ACTIVO_VALUE = "Y";
    public static final String REGISTRO_INACTIVO_VALUE = "N";
    public static final int REGISTRO_ENUSO_ID = 1;
    public static final int REGISTRO_ELIMINADO_ID = 0;
    public static final BigDecimal GMU_ID_NIVEL_SOCIO_ECONOMICO = new BigDecimal(17);
    public static final BigDecimal TIPO_COMPANIA_ADMINISTRACION = new BigDecimal(3);
    public static final BigDecimal TIPO_COMPANIA_ADMON_NATURAL = new BigDecimal(4);
    public static final BigDecimal TIPO_COMPANIA_ASCENSORES = BigDecimal.ONE;
    public static final BigDecimal TIPO_COMPANIA_CONSTRUCTORAS = new BigDecimal(2);
    public static final int PAGINACION_QUINCE_FILAS = 15;
    public static final int PAGINACION_TREINTA_FILAS = 30;
    public static final int PAGINACION_DIEZ_FILAS = 10;
    public static final int PAGINACION_OCHO_FILAS = 8;
    public static final int PAGINACION_SEIS_FILAS = 6;
    public static final int PAGINACION_CUATRO_FILAS = 4;
    public static final int PAGINACION_DOS_FILAS = 2;

    /**
     * Constante que maneja la cantidad de filas va tener la tabla de las
     * validaciones por proyecto.
     */
    public static final int PAGINACION_CINCO_FILAS = 5;
    public static final boolean PAGINACION_CONTAR = true;
    public static final boolean PAGINACION_DATOS = false;
    public static final String DIRTECCION_TIPO_INTRADUCIBLE = "IT";

    public enum TABS_CM {
        GENERAL, HHPP, OTS, COMPANIAS, NOTAS, HORARIO, COMPETENCIA,
        ALTERNAS, BLS, INVENTARIOS, CORTESIA, CITIFONIA, MAPA
    }

    public enum TABS_CM_GENERAL {
        GENERAL, HHPP, PENETRACION, ORDENES, NOTAS, INVENTARIO, HORARIO,
        INFOTECNICA, COMPANIAS, BLACKLIST, CASOS, BITACORA, SEGURIDAD, PROYECTOS
    }

    public enum TABS_MOD_CM {
        DATOSCM, DIRECCION, SUBEDIFICIOS, COBERTURA, TRACK, NOTAS, PRINCIPAL, SOLICITUD, HORARIO
    }

    public enum TABS_VT {
        CUENTA_MATRIZ, CONSTRUCTORA, MULTIEDIFICIO, ACOMETIDA, AUTORIZACION, HHPP,
        MATERIALES, MANO_OBRA, COSTOS, PLANO, HORARIO, MTDISENO, MODISENO
    }

    public enum TABS_OT {
        GENERAL, CREAR, FVT, FAC, FGA, TRACK, NOTAS, OTRR, INVENTARIOS_OT,
        AGENDA, CONTACTOS, BITACORA, BACKLOG, AGENDAMIENTO,GENERALOTEJECUCION,NOTASOTEJECUCION,
        OTONYX,HISTORICO_AGENDAS
    }

    public enum TABS_DET_OT {
        GENERAL_OT, AGENDA, NOTAS, INVENTARIO, CONTACTOS, BITACORA
    }

    public enum TABS_DET_OT_PENETRACION {
        TECNOLOGIAS, TECNOLOGIASINST, META, RESUMEN, COSTOS
    }

    public enum TABS_HHPP {
        GENERAL, ORDENES, NOTAS, INFOTECNICA, HORARIO, HISTORICOCLIENTES, 
        BITACORA, BITACORADIRECCION, AGENDAMIENTO, CURRIER, MARCAS, ONYX, HISTORICO
    }

    /**
     * Contener constantes de los menús. Constantes de las opciones del menú.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 11/05/2017.
     */
    public enum ITEM_MENU {
        TBBASICA, ESTADOINTERNOGA, RELACIONESTADOCMTIPOGA, COMPADMIN,
        COMPCONSTRUCCION, COMPASCENSORES, GESTIONOT, CREAROTVT, CONSULTACM,
        GESTIONSOLICITUDCM, CREACIONSOLICITUDCREACM, CREACIONSOLICITUDMODCM,
        TBCOMPETENCIA, CREACIONSOLICITUDVT, CREACIONSOLICITUHHPP, GESTIONSOLICITUD,
        VIABILIDADHHPP, VIABILIDADSEGESTRATO, VIABILIDADMENSAJEREGLA,
        VIABILIDADREGLAVALIDACION, VIABILIDADTIPOVALIDACION, VIABILIDADVALIDACIONPROYECTO,
        CARGUEMASIVOMODIFICACION, MASIVOMODIFICACION,
        MTOTABLAS, CRUDTABLA, CRUDTBLBASICA, SLAOT, SLASOL, NODO, ESTXFLUJO,
        REGLAFLUJOOT, ESTADOXTECNOLOGIA, REPORTESOLICITUDES, SOLICITUDELIMINAIONCM,
        ESTADOSOLICITUD,GENERAROTEJECUCION,REGIONAL ,COMUNIDADES,PLANTA, REPORTEPENETRACION,
        REPORTECOMPROMISOCOMERCIAL,PARAMCOBERTURAENTERGAS, GESTIONPARAMETROS, ESTADOINTEXT,ESTADOACTUALOTCM,
        HISTORICOORDENESCM,ESTADOACTUALOTDIR,HISTORICOOTDIR,SUBTIPOSOTCMVTTEC,ARRENDATARIO,SLAEJECUCION,
        HOMOLOGACIONRAZONES,MARCACREAHHPPVIRTR1R2MTOTABLAS, ACTESTCOMER,CARGUEMASIVOS,
        CREARREGLAESTADOS
    }
    
    public enum ITEM_MENU_HHPP {
        SOLICITUDVTCREARSOLICITUD,SOLICITUDVTEDIFICIOCONJUNTO,
        SOLICITUDVTREPLANTEAMIENTO,SOLICITUDVTENCASA,
        SOLICITUDVTCREACIONCM,SOLICITUDVTCREACIONHHPPUNIDI,
        SOLICITUDVTCREACIONHHPPCM,SOLICITUDVTCAMBIOESTRATO,
        SOLICITUDVTMODIFICARELIMINARCM,SOLICITUDVTMODIFICARHHPP,
        SOLICITUDVERIFICARCASA, SOLICITUDVTVIABILIDADINTERNET,
        GESTIONCAMBIOSESTRATO,GESTIONVTEDCO,GESTIONVTREPLANTEAMIENTO,
        GESTIONVTENCAS,GESTIONCRCUMA,GESTIONCRHHPPUNI,GESTIONCRHHPPCUMA,
        GESTIONMODELCUMA,GESTIONMODHHPP,GESTIONVERCASAS,
        GESTIONVIAINTERNET,GESTIONCAMBIOESTRATO,
        ESTADOSOLICITUDVT,ADMINISTRADORVT, REPORTESVT,CIERREMASIVOVT,
        ADMINNODO,ADMINDIRECCIONES,ADMINCIUDADES,OPERACIONESCAMBESTRATOMASIVO,
        OPERACIONESELIMINARMASIVO,SOLICITUDVTGESTIONSOLICITUD,VETONODOS,MODELOHHPP, 
        OPERACIONESCREARREPORTE,OPERACIONESCARGUEMASIVO,OPERACIONESINHABILITARMASIVO,
        GESTIONHHPPOT,CONFIGURACIONHHPPOT, GENERARPREFICHA, VALIDARPREFICHA, CREARPREFICHA,CARGUEFICHA,
        GENERARFICHA, VALIDARFICHA, ACTUALIZARFICHA,HISTORICOEXPORTFICHAS,LOGACTUALIZABATCH,PARAMETROSCORREO,
        MARCACIONFRAUDES,FRAUDESUNUAUNO, FRAUDESUNUAUNODESM,DESCARGUEMARCADODF,DESCARGUEDESMARCADODF, 
        CARGUEMARCADODF, CARGUEDESMARCADODF,MODELOOVERVIEW,MODELOADMPERFIL,MODELOOVERVIEWREPORTE,NODCUAGESSOL 
    }

    public enum TABS_CM_HHPP {
        CREACION_HHPP, MODIFICACION_HHPP, ELIMINACION_HHPP, GESTION_HHPP, TRASLADO_HHPP_BLOQUEADO
    }

    public static final String MSN_PROCESO_EXITOSO = "Proceso Realizado Exitosamente\n";
    public static final String MSN_ERROR_PROCESO = "Ocurrio un error realizando el Proceso: ";
    public static final String MSN_PROCESO_FALLO = "Ocurrio un error realizando el Proceso: ";
    public static final String MSN_ERROR_PROCESOHHPP = "Ocurrio un error al guardar los HHPP";
    public static final String NOMBRE_FORMULARIO_VT = "FORM_VT";
    public static final String PATH_VIEW_OT = "/view/MGL/CM/ot/";
    public static final String PATH_VIEW_DET_OT = "/view/MGL/CM/ot/";

    /**
     * CODIGOS TIPO BASICA
     */
    public static final String TIPO_SOLICITUD_ELIMINACION_CM = "ELIMINACION_CM";
    public static final String TIPO_SOLICITUD_ELIMINACION_ESCALAMIENTO = "ESCALADO_ACOMETIDAS";
    public static final String TIPO_SOLICITUD_CREACION_CM = "CREACION_CM";
    public static final String TIPO_SOLICITUD_MODIFICACION_CM = "MODIFICACION_CM";
    public static final String TIPO_SOLICITUD_VISITA_TECNICA = "VISITA_TECNICA";
    public static final String TIPO_SOLICITUD_CREACION_HHPP = "CREACION HHPP UNIDI";
    public static final String TIPO_SOLICITUD_MODIFICACION_HHPP = "MODIFICACION_HHPP";
    public static final String TIPO_SOLICITUD_TRASLADO_HHPP_BLOQUEADO ="TRASLADO_HHPP";

    public static final String SOLICITUD_ELIMINACION = "ELIMINACION";
    public static final String SOLICITUD_MODIFICACION_DATOS = "MODIFICACION_DATOS";
    public static final String SOLICITUD_MODIFICACION_COBERTURA = "MODIFICACION_COBERTURA";
    public static final String MODO_INGRESO_SOLICITUD = "SOLICITUD";
    public static final String MODO_INGRESO_GESTION = "GESTION";
    public static final String SOLICITUDHHPPMSGERROR = "Debe llenar el campo nivel 6";
    //validacion Origenes

    //multi server estrato
    public static String PROPERTY_SERVER_PATH = "PROPERTY_SERVER_PATH";
    public static String PROPERTY_PATH_PATH = "PROPERTY_PATH_PATH";
    public static String PROPERTY_URL_PATH = "PROPERTY_URL_PATH";

    //path documentos vt
    public static String PROPERTY_PATH_PATH_VT = "PROPERTY_PATH_PATH_VT";
    //path imagenes CM
    public static String PROPERTY_PATH_PATH_IMA_CM = "PROPERTY_PATH_PATH_IMA_CM";

    //Autor: Victor Bocanegra
    public static final String PROCESO_REPORTE = "procesoReporte";
    public static final String MENSAJE_PROCESO_REPORTE = "mensajeReporte";
    public static final List<String> LISTA_EXTENSIONES = Arrays.asList("jpg","png","jfif","gif","bmp","jpeg","tif");
    //Autor: Victor Bocanegra
    public static final String TIPO_PROCESO_REPORTE = "tipoReporte";
    public static final String WORKBOOK_REPORTE = "workbookReporte";
    public static final String REPORTE_DETALLADO = "DETALLADO";
    public static final String REPORTE_GENERAL = "GENERAL";
    
    public static String RUTA_SAVE_IMAGE_CM = "RUTA_SAVE_IMAGE_CM";
    
    public static final String ERROR_CUENTA_MATRIZ = "Error inicializando el bean de SeguridadCuentaMatrizBean";
    public static final String ERROR_GUARDAR_CUENTA_MATRIZ = "Error al guardar la cuenta matriz";
    
    /*COM_TECHNICALSITESAP_PKG*/
    public static final String SP_CRU_CTECH_CM = "COM_TECHNICALSITESAP_PKG.PRC_CRU_CTECH";
    
    /* Factibilidad de tecnología para proceso de creación de OT
     * author: Gildardo Mora
     * */
    public enum RESULTADO_FACTIBILIDAD_TEC_CM {
        FACTIBILIDAD_NEGATIVA,
        REQUIERE_ESCALAMIENTO,
        ESCALAMIENTO_VIGENTE,
        ESCALAMIENTO_RESULTADO_NEGATIVO,
        NO_EXISTE_NODO_COBERTURA;
    }

}
