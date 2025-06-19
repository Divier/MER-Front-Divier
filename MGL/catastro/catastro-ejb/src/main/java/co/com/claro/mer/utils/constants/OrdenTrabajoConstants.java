package co.com.claro.mer.utils.constants;

import lombok.AllArgsConstructor;

/**
 * Constantes utilizadas en las consultas de Orden de Trabajo.
 *
 * @author Gildardo Mora
 * @version 1.0, 2025/05/29
 */
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class OrdenTrabajoConstants {


    public static final String ESTADO_REGISTRO_PARAMETER = "estadoRegistro";
    public static final String FECHA_CREACION_INICIAL_PARAMETER = "fechaCreacionInicial";
    public static final String FECHA_CREACION_FINAL_PARAMETER = "fechaCreacionFinal";
    public static final String NUMERO_CUENTA_PARAMETER = "numeroCuenta";
    public static final String DESC_TIPO_OT_PARAMETER = "descTipoOt";
    public static final String DEPARTAMENTO_PARAMETER = "departamento";
    public static final String MUNICIPIO_PARAMETER = "municipio";
    public static final String TECNOLOGIA_PARAMETER = "tecnologia";
    public static final String ESTADO_PARAMETER = "estado";
    public static final String ESTADOSXFLUJO_LIST_PARAMETER = "estadosxflujoList";
    public static final String AND_SUB_VT_VT_OBJ_ID_VT_VT_ID_VT = " AND subVt.vtObj.idVt = vt.idVt ";
    public static final String AND_O_TIPO_OT_OBJ_ANS_ANS = " AND o.tipoOtObj.ans = :ans ";
    public static final String WHERE_T_ID_TIPO_OT_O_TIPO_OT_OBJ_ID_TIPO_OT = " WHERE t.idTipoOt = o.tipoOtObj.idTipoOt ";
    public static final String MATRIZ_ID_O_CM_OBJ_CUENTA_MATRIZ_ID = " AND cm.cuentaMatrizId = o.cmObj.cuentaMatrizId ";
    public static final String AND_SUB_VT_OT_ACOMETIDA_ID_IS_NULL = " AND subVt.otAcometidaId  IS NULL ";
    public static final String SELECT_DISTINCT_O_FROM_CMT_ORDEN_TRABAJO_MGL_O = "SELECT Distinct o  FROM CmtOrdenTrabajoMgl o,";
    public static final String AND_UPPER_CM_DEPARTAMENTO_GPO_NOMBRE_LIKE_DEPARTAMENTO = " AND UPPER(cm.departamento.gpoNombre) LIKE :departamento ";
    public static final String AND_VT_OT_OBJ_ID_OT_O_ID_OT = " AND vt.otObj.idOt = o.idOt ";
    public static final String AND_SUB_VT_ESTADO_REGISTRO_1 = " AND subVt.estadoRegistro = 1";
    public static final String ORDER_BY_O_FECHA_CREACION_ASC = " ORDER BY o.fechaCreacion ASC ";
    public static final String AND_EF_TIPO_FLUJO_OT_OBJ_BASICA_ID_T_TIPO_FLUJO_OT_BASICA_ID = " AND ef.tipoFlujoOtObj.basicaId = t.tipoFlujoOt.basicaId ";
    public static final String AND_CM_NUMERO_CUENTA_NUMERO_CUENTA = " AND cm.numeroCuenta = :numeroCuenta ";
    public static final String AND_O_ID_OT_ID_OT = " AND o.idOt = :idOt ";
    public static final String AND_UPPER_O_BASICA_ID_TECNOLOGIA_NOMBRE_BASICA_LIKE_TECNOLOGIA = " AND UPPER(o.basicaIdTecnologia.nombreBasica) LIKE :tecnologia";
    public static final String AND_O_ESTADO_REGISTRO_ESTADO_REGISTRO = " AND o.estadoRegistro = :estadoRegistro ";
    public static final String AND_VT_ESTADO_VISITA_TECNICA_1 = " AND vt.estadoVisitaTecnica = 1 ";
    public static final String AND_UPPER_CM_MUNICIPIO_GPO_NOMBRE_LIKE_MUNICIPIO = " AND UPPER(cm.municipio.gpoNombre) LIKE :municipio";
    public static final String AND_UPPER_O_ESTADO_INTERNO_OBJ_NOMBRE_BASICA_LIKE_ESTADO = " AND UPPER(o.estadoInternoObj.nombreBasica) LIKE :estado";
    public static final String AND_UPPER_O_TIPO_OT_OBJ_DESC_TIPO_OT_LIKE_DESC_TIPO_OT = " AND UPPER(o.tipoOtObj.descTipoOt) LIKE :descTipoOt ";
    public static final String CMT_VISITA_TECNICA_MGL_VT_CMT_SUB_EDIFICIOS_VT_SUB_VT = " CmtVisitaTecnicaMgl vt, CmtSubEdificiosVt subVt ";
    public static final String AND_O_ESTADO_INTERNO_OBJ_BASICA_ID_EF_ESTADO_INTERNO_OBJ_BASICA_ID = " AND o.estadoInternoObj.basicaId = ef.estadoInternoObj.basicaId ";
    public static final String CMT_TIPO_OT_MGL_T_CMT_ESTADOX_FLUJO_MGL_EF_CMT_CUENTA_MATRIZ_MGL_CM = " CmtTipoOtMgl t, CmtEstadoxFlujoMgl ef, CmtCuentaMatrizMgl cm, ";
    public static final String CMT_ESTADOX_FLUJO_MGL_EF_CMT_CUENTA_MATRIZ_MGL_CM = " CmtTipoOtMgl t, CmtEstadoxFlujoMgl ef, CmtCuentaMatrizMgl cm ";
    public static final String AND_EF_BASICA_TECNOLOGIA_BASICA_ID_O_BASICA_ID_TECNOLOGIA_BASICA_ID = " AND ef.basicaTecnologia.basicaId =o.basicaIdTecnologia.basicaId ";
    public static final String AND_EF_ESTADOX_FLUJO_ID_IN_ESTADOSXFLUJO_LIST = " AND ef.estadoxFlujoId IN :estadosxflujoList ";

}
