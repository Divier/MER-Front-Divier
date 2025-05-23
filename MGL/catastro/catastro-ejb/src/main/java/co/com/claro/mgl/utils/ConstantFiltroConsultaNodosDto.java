/*
 * Clase de constantes para la consulta de nodos.
 * 
 * Esta clase se creó con el objetivo de cumplir con los estándares de desarrollo que indican 
 * que los valores de los campos no deben estar "quemados" en el código, sino definidos como 
 * constantes. De esta manera, se facilita el mantenimiento y la reutilización del código.
 * 
 * La clase define constantes para cada campo de la base de datos, especificando su nombre 
 * en la consulta (FIELD) y el parámetro correspondiente para la consulta (PARAM).
 * 
 * Autor: arevalosam
 */
package co.com.claro.mgl.utils;

/**
 * Clase que define constantes para los nombres de los campos en la base de datos y 
 * sus respectivos parámetros en las consultas sobre nodos.
 * <p>
 * Esta clase no debe ser instanciada, ya que su único propósito es almacenar valores constantes.
 * </p>
 * 
 * @author arevalosam
 * @version 1.0
 */
public class ConstantFiltroConsultaNodosDto {

    /**
     * Constructor privado para evitar la instanciación de la clase.
     */
    private ConstantFiltroConsultaNodosDto() {
    }

    // Definición de constantes para los campos de la base de datos y sus parámetros correspondientes

    /** Código del nodo */
    public static final String FIELD_NOD_CODIGO = "n.nodCodigo";
    public static final String PARAM_NOD_CODIGO = "nodCodigo";

    /** Nombre del nodo */
    public static final String FIELD_NOD_NOMBRE = "n.nodNombre";
    public static final String PARAM_NOD_NOMBRE = "nodNombre";

    /** Fecha de activación del nodo */
    public static final String FIELD_NOD_FECHA_ACTIVACION = "n.nodFechaActivacion";
    public static final String PARAM_NOD_FECHA_ACTIVACION = "nodFechaActivacion";

    /** Identificador del grupo */
    public static final String FIELD_GPO_ID = "n.gpoId";
    public static final String PARAM_GPO_ID = "gpoId";

    /** Identificador de la unidad geográfica */
    public static final String FIELD_UGE_ID = "n.ugeId";
    public static final String PARAM_UGE_ID = "ugeId";

    /** Identificador de la zona */
    public static final String FIELD_ZON_ID = "n.zonId";
    public static final String PARAM_ZON_ID = "zonId";

    /** Identificador del distrito */
    public static final String FIELD_DIS_ID = "n.disId";
    public static final String PARAM_DIS_ID = "disId";

    /** Identificador de la división */
    public static final String FIELD_DIV_ID = "n.divId";
    public static final String PARAM_DIV_ID = "divId";

    /** Identificador del área */
    public static final String FIELD_ARE_ID = "n.areId";
    public static final String PARAM_ARE_ID = "areId";

    /** Headend del nodo */
    public static final String FIELD_NOD_HEADEND = "n.nodHeadEnd";
    public static final String PARAM_NOD_HEADEND = "nodHeadEnd";

    /** Campos adicionales del nodo */
    public static final String FIELD_NOD_CAMPO_ADI1 = "n.nodCampoAdicional1";
    public static final String PARAM_NOD_CAMPO_ADI1 = "nodCampoAdicional1";

    public static final String FIELD_NOD_CAMPO_ADI2 = "n.nodCampoAdicional2";
    public static final String PARAM_NOD_CAMPO_ADI2 = "nodCampoAdicional2";

    public static final String FIELD_NOD_CAMPO_ADI3 = "n.nodCampoAdicional3";
    public static final String PARAM_NOD_CAMPO_ADI3 = "nodCampoAdicional3";

    public static final String FIELD_NOD_CAMPO_ADI4 = "n.nodCampoAdicional4";
    public static final String PARAM_NOD_CAMPO_ADI4 = "nodCampoAdicional4";

    public static final String FIELD_NOD_CAMPO_ADI5 = "n.nodCampoAdicional5";
    public static final String PARAM_NOD_CAMPO_ADI5 = "nodCampoAdicional5";

    /** Tipo de nodo */
    public static final String FIELD_NOD_TIPO = "n.nodTipo.nombreBasica";
    public static final String PARAM_NOD_TIPO = "tipoNodo";

    /** Código de RR asociado */
    public static final String FIELD_COM_ID = "n.comId.codigoRr";
    public static final String PARAM_COM_ID = "codigoRr";

    /** Operación del nodo */
    public static final String FIELD_OPERA = "n.opera";
    public static final String PARAM_OPERA = "opera";

    /** OLT del nodo */
    public static final String FIELD_OLT = "n.olt";
    public static final String PARAM_OLT = "olt";

    /** Nodo OLT asociado */
    public static final String FIELD_OLT_NODO = "n.oltNodo";
    public static final String PARAM_OLT_NODO = "oltNodo";

    /** OT del nodo */
    public static final String FIELD_OT = "n.ot";
    public static final String PARAM_OT = "ot";
}
