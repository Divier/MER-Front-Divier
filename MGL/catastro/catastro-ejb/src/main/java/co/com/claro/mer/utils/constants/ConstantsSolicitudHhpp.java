package co.com.claro.mer.utils.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Manejo de constantes asociadas a procesos de solicitudes de HHPP
 *
 * @author Gildardo Mora
 * @version 1.0, 2022/10/18
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConstantsSolicitudHhpp {

    public static final String CODIGO_BASICA = "codigoBasica";
    public static final int CM_SOL_TRASLADO_HHPP_BLOQUEADO = 3;
    public static final String ESTADO_SOL = "estadoSol";
    public static final String HHPP_TRASLADO = "hhppTraslado";
    public static final String EXISTE_HHPP = "existeHhpp";
    public static final String MSG_RESULTADO = "msgResultado";
    public static final String RESULTADO_GESTION = "resultadoGestion";
    public static final String RESULTADO_EXITOSO = "resultadoExitoso";
    public static final String MSG_RESULT = "msgResult";
    public static final String NOMBRE_TIPO_TRASLADO_HHPP_BLOQUEADO = "TRASLADO HHPP BLOQUEADO";
    public static final String MSG_ERROR = "msgError";
    public static final String SOL_PENDIENTE = "PENDIENTE";
    public static final String SOL_FINALIZADO = "FINALIZADO";
    public static final String TIPO_VTCECSUS = "VTCECSUS";
    public static final String TIPO_VTCASA = "VTCASA";
    public static final String DEFAULT_TIME = "00:00:00";
    public static final String MSG_TIPO_SOL_NO_VALIDO = "El tipo de solicitud no se encuentra configurado.";
    public static final String ATRIBUTO_TIEMPO_INICIO = "tiempoInicio";
    public static final String DIRECCION_DETA = "direccionDeta";
    public static final String SOLICITUDES_HHPP = "Crear Solicitudes HHPP";
}
