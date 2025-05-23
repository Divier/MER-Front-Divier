package co.com.claro.mgl.facade;

import co.com.claro.cmas400.ejb.request.RequestDataCreaHhppVirtual;
import co.com.claro.cmas400.ejb.request.RequestDataValidaRazonesCreaHhppVt;
import co.com.claro.cmas400.ejb.respons.ResponseDataCreaHhppVirtual;
import co.com.claro.cmas400.ejb.respons.ResponseDataValidaRazonesCreaHhppVt;
import co.com.claro.cmas400.ejb.respons.dto.CodigoRazonSubrazon;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.HhppMgl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Expone las funcionalidades asociadas a traslado de HHPP Bloqueado y
 * creación de HHPP virtual.
 * <p>
 * Permite consultar los servicios asociados a RSResourcesLocationRest,
 * y realizar operaciones de apoyo a procesos de validaciones para proceso de
 * solicitudes de traslado de HHPP Bloqueado, entre otros.
 *
 * @author Gildardo Mora
 * @version 1.0, 2022/05/23
 */
public interface HhppVirtualMglFacadeLocal {
    /**
     * Consulta el tiempo mínimo de la cuenta.
     * <p>
     * Corresponde al valor del acrónimo HHPP_VIRTUAL_TIEMPO_MIN_CUENTA de
     * la tabla parámetros, ejecutando el procedimiento almacenado.
     *
     * @return tiempoMinimoCuenta {@link String}  Valor del tiempo mínimo de cuenta en número de dias.
     */
    String findTiempoMinimoCuenta() throws ApplicationException;

    /**
     * Ejecuta el llamado al servicio rest validateMoveReasonsResource
     * Encargado de validar la dirección y la cuenta del cliente con RR
     *
     * @param requestDataValidaRazonesCreaHhppVt {@link ResponseDataValidaRazonesCreaHhppVt}
     * @return response {@link ResponseDataValidaRazonesCreaHhppVt}
     */
    Optional<ResponseDataValidaRazonesCreaHhppVt> callServiceValidateMoveReasonsResource(
            RequestDataValidaRazonesCreaHhppVt requestDataValidaRazonesCreaHhppVt) throws ApplicationException;

    /**
     * Ejecuta el llamado al servicio rest createVirtualHHPPResource
     * Encargado de realizar la creación de HHPP virtual
     *
     * @param requestDataCreaHhppVirtual {@link RequestDataCreaHhppVirtual} Petición al servicio
     * @return response {@link ResponseDataCreaHhppVirtual} Respuesta del servicio
     * @throws ApplicationException Excepción de la aplicación
     */
    Optional<ResponseDataCreaHhppVirtual> callServiceCreateVirtualHhppResource(
            RequestDataCreaHhppVirtual requestDataCreaHhppVirtual) throws ApplicationException;

    /**
     * Verifica si la lista de razones y sub razones del HHPP
     * se encuentra entre las marcaciones ajustadas para creación de HHPP Virtual
     *
     * @param codigoRazonSubrazonList {@link List<CodigoRazonSubrazon>}
     *                                Lista de razones y sub razones del HHPP.
     * @return Retorna true, si son válidas las marcaciones del HHPP.
     * @throws ApplicationException Excepción de la aplicación
     */
    boolean validateMarcacionesHomePassed(List<CodigoRazonSubrazon> codigoRazonSubrazonList) throws ApplicationException;

    /**
     * Realiza la validación del estado del HHPP de la respuesta del servicio validateMoveReasonsResource
     *
     * @param estadoHhpp {@link String}
     * @return Retorna true si estado de hhpp es valido
     */
    boolean validateEstadoHhppRr(String estadoHhpp) throws ApplicationException;

    /**
     * Se encarga de verificar si los códigos de bloqueo están permitidos para traslados
     *
     * @param responseDataValidaRazonesCreaHhppVt {@link ResponseDataValidaRazonesCreaHhppVt}
     *                                            Respuesta del servicio validateMoveReasonsResource
     * @return Retorna true cuando se cumple con los códigos TRA y BLF
     */
    boolean validateBloqueoHomePassed(ResponseDataValidaRazonesCreaHhppVt responseDataValidaRazonesCreaHhppVt)
            throws ApplicationException;

    /**
     * Validar si la cuenta se encuentra activa
     *
     * @param estadoCuenta {@link String}
     * @return Retorna true si el estado de la cuenta es activo (A)
     */
    boolean validateEstadoCuenta(String estadoCuenta);

    /**
     * Verifica si existe la dirección con base a la respuesta dada
     * por el servicio validateMoveReasonsResource
     *
     * @param codigoRespuestaHhpp {@link String} código de respuesta HHPP del servicio
     * @return Retorna true si existe o no la dirección.
     */
    boolean validateExistenciaDireccion(String codigoRespuestaHhpp);

    /**
     * Verifica que la diferencia en dias, entre la fecha de activación de la cuenta
     * y la fecha actual, sea mayor tiempo mínimo de la cuenta habilitado en MER
     *
     * @param fechaActivacionCuenta {@link String} fecha en formato AAAAMMDD ej: (20220524)
     * @return si es o no vigente la fecha de activación de la cuenta
     * @throws ApplicationException Excepción de la aplicación
     */
    boolean validateVigenciaFechaActivacionCuenta(String fechaActivacionCuenta) throws ApplicationException;

    /**
     * Verifica si el número de cuenta de cliente existe,
     * validando la respuesta del servicio validateMoveReasonsResource
     *
     * @param estadoCuenta {@link String} Mensaje de respuesta de la validación del servicio
     * @return Retorna true si existe la cuenta
     */
    boolean validateExistenciaCuentaClienteTrasladar(String estadoCuenta);

    /**
     * Realiza las validaciones pertinentes para verificar si es viable
     * ejecutar el traslado de HHPP Bloqueado para crear HHPP Virtual
     *
     * @param responseValidateReasonsRs {@link ResponseDataValidaRazonesCreaHhppVt}
     * @param numeroCuenta              {@link String}
     * @return {Map<String,Object>}
     * @throws ApplicationException Excepción de la app
     */
    Map<String, Object> validateTrasladoHhppBloqueado(ResponseDataValidaRazonesCreaHhppVt responseValidateReasonsRs,
            String numeroCuenta) throws ApplicationException;

    /**
     * Genera el request para hacer el llamado al servicio de validateReasonsResource
     *
     * @param hhppTraslado             {@link HhppMgl}
     * @param numCuentaClienteTraslado {@link String}
     * @return {@link RequestDataValidaRazonesCreaHhppVt}
     */
    Optional<RequestDataValidaRazonesCreaHhppVt> generateRequestForServiceValidate(HhppMgl hhppTraslado, String numCuentaClienteTraslado);

    /**
     * Comprueba si el flag que permite mostrar el tipo de solicitud está activo.
     *
     * @return Retorna true, cuando el flag tiene valor "TRUE"
     * @author Gildardo Mora
     */
    boolean isActiveCcmmTrasladoHhppBloqueado();

    /**
     * Comprueba si el flag que permite mostrar el tipo de solitud de "traslado HHPP Bloqueado" en la solicitud
     * de HHPP de barrio abierto está activo.
     *
     * @return {@code boolean} Retorna true, cuando está activo el flag.
     * @author Gildardo Mora
     */
    boolean isActiveHhppTrasladoHhppBloqueado();

}
