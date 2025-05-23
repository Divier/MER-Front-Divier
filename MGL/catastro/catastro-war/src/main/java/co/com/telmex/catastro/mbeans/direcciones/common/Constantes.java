/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.telmex.catastro.mbeans.direcciones.common;

/**
 *
 * @author user
 */
public final class Constantes {

    public static final String DIR_NODOUNO = "D.DIR_NODOUNO";
    public static final String DIR_NODODOS = "D.DIR_NODODOS";
    public static final String DIR_NODOTRES = "D.DIR_NODOTRES";
    public static final String CIUDAD = "Ciudad";
    public static final String BARRIO = "Barrio";
    public static final String DIRECCION = "Direccion";
    public static final String NIVEL_ECONOMICO = "Nivel Economico";
    public static final String ESTRATO = "Estrato";
    public static final String ACTIVIDAD_ECONOMICA = "Actividad economica";
    public static final String COD_MANZANA = "Codigo de Manzana";
    public static final String DIRECCION_ALTERNA = "Direccion Alterna";
    public static final String EXISTENCIA = "Existencia";
    public static final String CONFIABILIDAD = "Nivel de calidad";
    public static final String NODOUNO = "Nodo Bi";
    public static final String NODODOS = "Nodo UniUno";
    public static final String NODOTRES = "Nodo UniDos";
    public static final String SEPARATOR = ";";
    public static final String ORIGEN = "Fuente";
    public static final String ID = "Id";
    public static final String VACIO = "VACIO";
    public static final String V_VACIO = "V";
    public static final String OPTION_BIS = "BIS";

    public static enum TYPO_DIR {

        V, CK, BM, IT, VACIO
    }
    public static String OPT_PISO_INTERIOR = "PISO + INTERIOR";
    public static String OPT_PISO_LOCAL = "PISO + LOCAL";
    public static String OPT_PISO_APTO = "PISO + APTO";
    public static String OPT_CASA_PISO = "CASA + PISO";
    public static String ID_DIR_REPO_NULL = "0";
    public static String MSG_SOLICITUD_EN_TRAMITE = "La Dirección Principal cuenta con una Solicitud en Tramite";
    public static final String NO_EXISTE = "false";
    public static final String EXISTE = "true";
    public static String MSG_DIR_NO_VALIDADA = "La Dirección no existe ";
    public static String MSG_DIR_EX_GEO = "La Dirección existe ";
    public static String MSG_DIR_EX_NOGEO = "La Dirección existe y requiere validación de cobertura ";
    public static String MSG_DIR_NODO_NO_CERTIFICADO = "El Nodo de la dirección no esta certificado";
    public static String IP_SERV_CORREO = "10.244.140.20";
    public static String MAIL_FROM = "modulovisitas@claro.com.co";
    public static String MSG_ERR_DIR_CATASTRO_NULL = "No fue posible obtener la Dirección del Repositorio";
    public static String MSG_DIR_PRINCIPAL_REQUIRED = "Debe Ingresar una Dirección Principal";
    public static String MSG_DIR_UNIDAD_NO_ENCONTRADA = "No existe un estandar preestablecido para esta dirección, no es posible modificar la dirección";
    public static String MSG_DIR_NO_ESTANDAR_RR = "No fue posible crear el estandar para la dirección ingresada";
    public static String MSG_DIR_UNIDAD_NO_ENCONTRADA_RR = "El HHPP Antiguo no fue encontrado en RR";
    public static String MSG_DIR_UNIDAD_NUEVA_ENCONTRADA_RR = "El HHPP nuevo fue encontrado en RR, por favor ingrese otra direccion";
    public static String MSG_DIR_IGUAL = "Las Direciones Antigua y nueva son iguales";
    public static String MSG_DIR_INCOMPLETA = "Falta informacion en la Direción Antigua ó nueva";
    public static String MSG_DIR_NO_FORMATO_RR = "No fue posible crear el formato RR de la dirección ingresada";
    public static String MSG_ERR_CREAR_SOLICITUD = "La Solicitud no fue creada correctamente. Error en la verificacion de la direccion";
    public static String MSG_SOLICITUD_CREADA_N0 = "La Solicitud No " ;
    public static String MSG_SOLICITUD_CREADA = " de Creación de Cuenta Matriz fue creada con éxito, en estado Pendiente para validación por el área de HHPP";
    public static String MSG_SOLICITUD_MODIFICACION_HHPP_CREADA = "Solicitud creada con éxito, en estado Pendiente para validación por el área de HHPP";
    public static String MSG_NODO_CERTIFICADO = "El nodo esta certificado";
    public static String MENSAJE_NODO_INHABILITADO = "Atención!! nodo no certificado!";
    public static String MSG_DIR_NO_ESTANDAR = "La dirección no se logró estandarizar, por favor verifíquela y cámbiela si esta errada. "
            + " Diríjase al icono \uD83C\uDFE0 para ingresar la dirección tabulada. ";
    public static String MSG_DIR_ESTANDAR = "La direccioón se logro Estandarizar ";
    public static String PARAMETRO_X1 = "PARAMETRO_X1";
    public static String PARAMETRO_X2 = "PARAMETRO_X2";
    public static String PARAMETRO_X3 = "PARAMETRO_X3";
    public static String PARAMETRO_X4 = "PARAMETRO_X4";
    public static String PARAMETRO_X5 = "PARAMETRO_X5";
    public static String PARAMETRO_X6 = "PARAMETRO_X6";
    public static String PARAMETRO_X7 = "PARAMETRO_X7";
    public static String PARAMETRO_X8 = "PARAMETRO_X8";
}
