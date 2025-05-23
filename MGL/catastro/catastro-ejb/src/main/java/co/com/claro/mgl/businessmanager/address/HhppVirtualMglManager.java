package co.com.claro.mgl.businessmanager.address;

import co.com.claro.cmas400.ejb.request.RequestDataCreaHhppVirtual;
import co.com.claro.cmas400.ejb.request.RequestDataValidaRazonesCreaHhppVt;
import co.com.claro.cmas400.ejb.respons.*;
import co.com.claro.cmas400.ejb.respons.dto.CodigoRazonSubrazon;
import co.com.claro.mer.dtos.response.service.UsuarioPortalResponseDto;
import co.com.claro.mer.utils.DateToolUtils;
import co.com.claro.mer.utils.StringToolUtils;
import co.com.claro.mer.utils.constants.ConstantsSolicitudHhpp;
import co.com.claro.mer.utils.enums.DelimitadorEnum;
import co.com.claro.mer.utils.enums.MessageSeverityEnum;
import co.com.claro.mer.utils.enums.ParametrosMerEnum;
import co.com.claro.mgl.businessmanager.cm.CmtMarcacionesHhppVtMglManager;
import co.com.claro.mgl.dao.impl.HhppVirtualMglDaoImpl;
import co.com.claro.mgl.dtos.CmtMarcacionesHhppVtDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.ParametrosMerUtil;
import co.com.claro.mgl.ws.cm.request.RequestCreaSolicitudTrasladoHhppBloqueado;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.util.StringUtil;

import javax.faces.application.FacesMessage;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Predicate;

/**
 * Manager asociado a procesos de creación de HHPP virtual
 *
 * @author Gildardo Mora
 * @version 1.0, 2022/05/23
 */
public class HhppVirtualMglManager {
    private static final Logger LOGGER = LogManager.getLogger(HhppVirtualMglManager.class);
    private static final String ERROR_MESSAGE = "Ocurrió un error en: ";
    public static final String ESTADO_CUENTA_OK = "A";
    public static final String ESTADO_HHPP_RR_E = "E";
    public static final String ESTADO_HHPP_RR_P = "P";

    /**
     * Consulta el tiempo mínimo de la cuenta.
     * <p>
     * Corresponde al valor del acrónimo HHPP_VIRTUAL_TIEMPO_MIN_CUENTA de
     * la tabla parámetros, ejecutando el procedimiento almacenado.
     *
     * @return {@link String}  Valor del tiempo mínimo de cuenta en número de días.
     * @author Gildardo Mora
     */
    public String findTiempoMinimoCuenta() throws ApplicationException {
        HhppVirtualMglDaoImpl hhppVirtualMglDao = new HhppVirtualMglDaoImpl();
        return hhppVirtualMglDao.findTiempoMinimoCuenta();
    }

    /**
     * Ejecuta el llamado al servicio rest validateMoveReasonsResource
     * Encargado de validar la dirección y la cuenta del cliente con RR
     *
     * @param requestDataValidaRazonesCreaHhppVt {@link ResponseDataValidaRazonesCreaHhppVt}
     * @return response {@link ResponseDataValidaRazonesCreaHhppVt}
     */
    public Optional<ResponseDataValidaRazonesCreaHhppVt> callServiceValidateMoveReasonsResource(
            RequestDataValidaRazonesCreaHhppVt requestDataValidaRazonesCreaHhppVt) throws ApplicationException {
        HhppVirtualMglDaoImpl hhppVirtualMglDao = new HhppVirtualMglDaoImpl();
        String uri = hhppVirtualMglDao.findUriReasonsResourceServices();
        ResponseValidateMoveReasonsResource response = hhppVirtualMglDao.callServiceValidateMoveReasonsResource(requestDataValidaRazonesCreaHhppVt, uri);
        if (Objects.isNull(response)) return Optional.empty();
        return Optional.of(response).map(ResponseValidateMoveReasonsResource::getValidateMoveReasonsResourceResponse);
    }

    /**
     * Ejecuta el llamado al servicio rest createVirtualHHPPResource
     * Encargado de realizar la creación de HHPP virtual
     *
     * @param requestDataCreaHhppVirtual {@link RequestDataCreaHhppVirtual} Petición al servicio
     * @return response {@link ResponseDataCreaHhppVirtual} Respuesta del servicio
     * @throws ApplicationException Excepción de la aplicación
     */
    public Optional<ResponseDataCreaHhppVirtual> callServiceCreateVirtualHhppResource(
            RequestDataCreaHhppVirtual requestDataCreaHhppVirtual) throws ApplicationException {
        HhppVirtualMglDaoImpl hhppVirtualMglDao = new HhppVirtualMglDaoImpl();
        String uri = hhppVirtualMglDao.findUriReasonsResourceServices();
        ResponseCreateVirtualHhppResource response =
                hhppVirtualMglDao.callServiceCreateVirtualHhppResource(requestDataCreaHhppVirtual, uri);
        if (Objects.isNull(response)) return Optional.empty();
        return Optional.of(response).map(ResponseCreateVirtualHhppResource::getCreateVirtualHhppResourceResponse);
    }

    /**
     * Verifica si la lista de razones y sub razones del HHPP
     * se encuentra entre las marcaciones ajustadas para creación de HHPP Virtual
     *
     * @param codigoRazonSubrazonList List {@link CodigoRazonSubrazon} Lista de razones y sub razones del HHPP.
     * @return Retorna true, si son válidas las marcaciones del HHPP.
     * @throws ApplicationException Excepción de la aplicación
     */
    public boolean validateMarcacionesHomePassed(List<CodigoRazonSubrazon> codigoRazonSubrazonList) throws ApplicationException {
        CmtMarcacionesHhppVtMglManager cmtMarcacionesHhppVtMglManager = new CmtMarcacionesHhppVtMglManager();
        List<CmtMarcacionesHhppVtDto> marcacionesHhppVtDtoList = cmtMarcacionesHhppVtMglManager
                .findAllMarcacionesHhppIsAplica();
        HhppVirtualMglDaoImpl hhppVirtualMglDao = new HhppVirtualMglDaoImpl();
        return hhppVirtualMglDao.validateMarcacionesHomePassed(codigoRazonSubrazonList, marcacionesHhppVtDtoList);
    }

    /**
     * Realiza la validación del estado del HHPP de la respuesta del servicio validateMoveReasonsResource
     *
     * @param estadoHhpp {@link String}
     * @return Retorna true si estado de hhpp es valido
     */
    public boolean validateEstadoHhppRr(String estadoHhpp) throws ApplicationException {
        String estadosHhppValidos = ParametrosMerUtil.findValor(ParametrosMerEnum.STATUS_CODES_HHPP_VALIDATE_SERVICE.getAcronimo());
        List<String> estadosHhppList = StringToolUtils.convertStringToList(estadosHhppValidos, DelimitadorEnum.PUNTO_Y_COMA, true);
        estadosHhppList.replaceAll(String::toUpperCase);
        return estadosHhppList.contains(estadoHhpp.toUpperCase());
    }

    /**
     * Se encarga de verificar si los códigos de bloqueo son permitidos para traslados
     *
     * @param responseDataValidaRazonesCreaHhppVt {@link ResponseDataValidaRazonesCreaHhppVt}
     *                                            Respuesta del servicio validateMoveReasonsResource
     * @return Retorna true cuando se cumple con los códigos de bloqueo
     */
    public boolean validateBloqueoHomePassed(ResponseDataValidaRazonesCreaHhppVt responseDataValidaRazonesCreaHhppVt)
            throws ApplicationException {
        HhppVirtualMglDaoImpl hhppVirtualMglDao = new HhppVirtualMglDaoImpl();
        try {
            List<String> codigosBloqueoList = hhppVirtualMglDao.findCodigosBloqueoHabilitadosHhppVirtual();
            return hhppVirtualMglDao.validateBloqueoHomePassed(responseDataValidaRazonesCreaHhppVt, codigosBloqueoList);

        } catch (Exception e) {
            String msgError = "Se produjo un error al validar los códigos de Bloqueo del HHPP en: "
                    + ClassUtils.getCurrentMethodName(this.getClass());
            throw new ApplicationException(msgError, e);
        }
    }

    /**
     * Validar si la cuenta se encuentra activa
     *
     * @param estadoCuenta {@link String}
     * @return Retorna true si el estado de la cuenta es activo (A)
     */
    public boolean validateEstadoCuenta(String estadoCuenta) {
        return ESTADO_CUENTA_OK.equals(estadoCuenta);
    }

    /**
     * Verifica si existe la dirección con base a la respuesta dada
     * por el servicio validateMoveReasonsResource
     *
     * @param codigoRespuestaHhpp {@link String} código de respuesta HHPP del servicio
     * @return Retorna true si existe o no la dirección.
     */
    public boolean validateExistenciaDireccion(String codigoRespuestaHhpp) {
        /* Códigos de respuesta:
         * 00 - Dirección válida
         * 01 - Dirección no existe
         * 02 - Existe Precableado
         * */
        return !codigoRespuestaHhpp.equals("01");
    }

    /**
     * Verifica que la diferencia en dias, entre la fecha de activación de la cuenta
     * y la fecha actual, sea mayor tiempo mínimo de la cuenta habilitado en MER
     *
     * @param fechaActivacionCuenta {@link String} fecha en formato AAAAMMDD ej: (20220524)
     * @return si es o no vigente la fecha de activación de la cuenta
     * @throws ApplicationException Excepción de la aplicación
     */
    public boolean validateVigenciaFechaActivacionCuenta(String fechaActivacionCuenta) throws ApplicationException {
        try {
            if (StringUtil.isBlank(fechaActivacionCuenta)) {
                return false;
            }

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDate dateActivacionCuenta = LocalDate.parse(fechaActivacionCuenta, dateTimeFormatter);
            int tiempoMinimo = Integer.parseInt(findTiempoMinimoCuenta());
            LocalDateTime fechaActivacion = DateToolUtils.convertLocalDateToLocalDateTime(dateActivacionCuenta);
            int diasTranscurridos = DateToolUtils.getNumberOfDaysElapsedUntilToday(fechaActivacion);
            return diasTranscurridos > tiempoMinimo;

        } catch (ApplicationException ae) {
            String msgError = "AppExc Error: en la ejecución de " + ClassUtils.getCurrentMethodName(this.getClass());
            LOGGER.error(msgError, ae.getMessage());
            throw new ApplicationException(ae);
        } catch (Exception e) {
            String msgError = "Error: en la ejecución de " + ClassUtils.getCurrentMethodName(this.getClass());
            LOGGER.error(msgError, e.getMessage());
            throw new ApplicationException(e);
        }

    }

    /**
     * Verifica si el número de cuenta de cliente existe,
     * validando la respuesta del servicio validateMoveReasonsResource
     *
     * @param estadoCuenta {@link String} Mensaje de respuesta de la validación del servicio
     * @return Retorna true si existe la cuenta
     */
    public boolean validateExistenciaCuentaClienteTrasladar(String estadoCuenta) {
        return !estadoCuenta.equalsIgnoreCase("Cuenta no Existe");
    }

    /**
     * Realiza las validaciones para proceso de traslado de HHPP
     *
     * @param responseValidateReasonsRs {@link ResponseDataValidaRazonesCreaHhppVt}
     * @param numeroCuenta              {@link String} número de cuenta del suscriptor
     * @return Map<String, Object>
     * @throws ApplicationException Excepción de la app
     */
    public Map<String, Object> validateTrasladoHhppBloqueado(ResponseDataValidaRazonesCreaHhppVt responseValidateReasonsRs,
            String numeroCuenta) throws ApplicationException {
        String msgValidationHhpp;

        if (Objects.isNull(responseValidateReasonsRs)) {
            msgValidationHhpp = "No se obtuvo respuesta del servicio validateMoveReasonsResource. Por favor intente de nuevo mas tarde... ";
            return generateResponseValidation(MessageSeverityEnum.ERROR, msgValidationHhpp, false);
        }

        if (StringUtils.isBlank(Optional.of(responseValidateReasonsRs).map(ResponseDataValidaRazonesCreaHhppVt::getCodigoRespuestaHhpp).orElse(null))) {
            msgValidationHhpp = "El servicio validateMoveReasonsResource no generó un código de respuesta valido. Por favor intente de nuevo mas tarde... ";
            String msgError = String.format(msgValidationHhpp.concat("{%s}"), responseValidateReasonsRs);
            LOGGER.error(msgError);
            return generateResponseValidation(MessageSeverityEnum.ERROR, msgValidationHhpp, false);
        }

        if (!validateExistenciaDireccion(responseValidateReasonsRs.getCodigoRespuestaHhpp())) {
            msgValidationHhpp = "El HHPP no existe, debe solicitar la creación de HHPP";
            return generateResponseValidation(MessageSeverityEnum.WARN, msgValidationHhpp, false);
        }

        if (!validateEstadoHhppRr(responseValidateReasonsRs.getEstadoHhpp())) {
            String estadoHhpp = Optional.of(responseValidateReasonsRs).map(ResponseDataValidaRazonesCreaHhppVt::getEstadoHhpp).orElse("");
            String descEstadoHhpp = Optional.of(responseValidateReasonsRs).map(ResponseDataValidaRazonesCreaHhppVt::getDescripcionEstado).orElse("");
            msgValidationHhpp = "El estado del HHPP <b>" + estadoHhpp + " (" + descEstadoHhpp + ") </b> no es válido. "
                    + "<br> Debe realizar la solicitud de creación de  HHPP normal";
            return generateResponseValidation(MessageSeverityEnum.WARN, msgValidationHhpp, false);
        }

        if (ESTADO_HHPP_RR_E.equals(responseValidateReasonsRs.getEstadoHhpp())
                || ESTADO_HHPP_RR_P.equals(responseValidateReasonsRs.getEstadoHhpp())) {
            msgValidationHhpp = "No requiere HHPP virtual, por favor realizar traslado sobre el HHPP existente.";
            return generateResponseValidation(MessageSeverityEnum.WARN, msgValidationHhpp, false);
        }

        if (!validateExistenciaCuentaClienteTrasladar(responseValidateReasonsRs.getMensajeRespuesta())) {
            msgValidationHhpp = "La cuenta del suscriptor No: <b>" + numeroCuenta + "</b> a trasladar, <b> no existe.</b>";
            return generateResponseValidation(MessageSeverityEnum.WARN, msgValidationHhpp, false);
        }

        if (!validateEstadoCuenta(responseValidateReasonsRs.getEstadoCuenta())) {
            msgValidationHhpp = "El estado de la cuenta: <b>" + numeroCuenta + "</b> a trasladar, <b> no es valido.</b>";
            return generateResponseValidation(MessageSeverityEnum.WARN, msgValidationHhpp, false);
        }

        if (!validateVigenciaFechaActivacionCuenta(responseValidateReasonsRs.getFechaActivacionCuenta())) {
            msgValidationHhpp = "La cuenta <b>" + numeroCuenta + "</b> no cumple con el tiempo mínimo establecido para solicitar traslado.";
            return generateResponseValidation(MessageSeverityEnum.ERROR, msgValidationHhpp, false);
        }

        if (!validateBloqueoHomePassed(responseValidateReasonsRs)) {
            msgValidationHhpp = "No requiere HHPP Virtual, se debe realizar traslado normal.";
            return generateResponseValidation(MessageSeverityEnum.WARN, msgValidationHhpp, false);
        }

        if (!validateMarcacionesHomePassed(responseValidateReasonsRs.getCodigoRazonSubrazon())) {
            msgValidationHhpp = "La cuenta a trasladar, no cumple con las marcaciones de razón "
                    + "y subrazón para la creación de HHPP Virtual. ";
            return generateResponseValidation(MessageSeverityEnum.ERROR, msgValidationHhpp, false);
        }

        msgValidationHhpp = "Dirección validada";
        return generateResponseValidation(MessageSeverityEnum.INFO, msgValidationHhpp, true);
    }

    /**
     * Genera el Map de respuesta para la validación
     *
     * @param typeMsgValidation {@link FacesMessage.Severity} Tipo de severidad del mensaje
     * @param msgValidationHhpp {@link String} Mensaje generado en la validación
     * @param isValid           {boolean} Indica si fue o no aprobada la validación
     * @return { Map<String,Object>} Map con la respuesta
     */
    private Map<String, Object> generateResponseValidation(MessageSeverityEnum typeMsgValidation, String msgValidationHhpp, boolean isValid) {

        Map<String, Object> validacionMap = new HashMap<>();
        validacionMap.put("esValido", isValid);
        validacionMap.put("tipoMsg", typeMsgValidation);
        validacionMap.put("msg", msgValidationHhpp);
        return validacionMap;
    }

    /**
     * Asigna datos al request, necesario para llamado
     * a servicio validateMoveReasonsResource.
     *
     * @param hhppTraslado {@link HhppMgl}
     * @return {@link RequestDataValidaRazonesCreaHhppVt}
     */
    public Optional<RequestDataValidaRazonesCreaHhppVt> generateRequestForServiceValidate(HhppMgl hhppTraslado, String numCuentaClienteTraslado) {

        if (Objects.isNull(hhppTraslado)) {
            return Optional.empty();
        }

        RequestDataValidaRazonesCreaHhppVt requestValidateRazones = new RequestDataValidaRazonesCreaHhppVt();
        requestValidateRazones.setCuenta(numCuentaClienteTraslado);
        requestValidateRazones.setIdentificadorCalle(hhppTraslado.getHhpCalle() != null ? hhppTraslado.getHhpCalle() : null);
        requestValidateRazones.setIdentificadorCasa(hhppTraslado.getHhpPlaca() != null ? hhppTraslado.getHhpPlaca() : null);
        requestValidateRazones.setIdentificadorApartamento(hhppTraslado.getHhpApart() != null ? hhppTraslado.getHhpApart() : null);
        requestValidateRazones.setDivisionalRegion(hhppTraslado.getHhpDivision() != null ? hhppTraslado.getHhpDivision() : null);
        requestValidateRazones.setComunidad(hhppTraslado.getHhpComunidad() != null ? hhppTraslado.getHhpComunidad() : null);
        return Optional.of(requestValidateRazones);
    }

    /**
     * Genera el request de petición al servicio createHhppVirtual
     *
     * @param hhppMgl {@link HhppMgl}
     * @param hhppMap {@code Map<String, String>}
     * @return {@link RequestDataCreaHhppVirtual}
     */
    public RequestDataCreaHhppVirtual generateRequestForServiceCreateHhppVirtual(HhppMgl hhppMgl, Map<String, String> hhppMap) {
        RequestDataCreaHhppVirtual requestDataCreaHhppVirtual = new RequestDataCreaHhppVirtual();
        requestDataCreaHhppVirtual.setCuenta(hhppMap.getOrDefault("numCuentaCliente", ""));
        requestDataCreaHhppVirtual.setCodigoDivision(hhppMgl.getHhpDivision());
        requestDataCreaHhppVirtual.setCodigoComunidad(hhppMgl.getHhpComunidad());
        requestDataCreaHhppVirtual.setCalle(hhppMgl.getHhpCalle());
        requestDataCreaHhppVirtual.setNumeroCasa(hhppMgl.getHhpPlaca());
        requestDataCreaHhppVirtual.setApartamentoHhppPrimario(hhppMgl.getHhpApart());
        requestDataCreaHhppVirtual.setApartamentoHhppVirtual(hhppMgl.getHhpApart());
        requestDataCreaHhppVirtual.setUsuarioPeticion(hhppMap.getOrDefault("usuario", ""));
        return requestDataCreaHhppVirtual;
    }

    /**
     * Realiza la creación en BD del HHPP virtual.
     *
     * @param request     {@link RequestCreaSolicitudTrasladoHhppBloqueado}
     * @param hhppVirtual {@link HhppMgl} HhPP virtual a registrar
     * @throws ApplicationException Excepción personalizada de la App
     * @author Gildardo Mora
     */
    public Map<String, Object> createHhppVirtual(RequestCreaSolicitudTrasladoHhppBloqueado request, HhppMgl hhppVirtual) throws ApplicationException {

        Map<String, Object> result = new HashMap<>();
        Map<String, String> hhppMap = new HashMap<>();

        try {
            hhppMap.put("usuario", request.getUsuarioVt());
            hhppMap.put("numCuentaCliente", request.getNumeroCuentaClienteTrasladar());
            /* Ejecuta el servicio encargado de crear el HHPP virtual en RR */
            RequestDataCreaHhppVirtual requestDataCreaHhppVirtual = generateRequestForServiceCreateHhppVirtual(hhppVirtual, hhppMap);
            Map<String, Object> hhppVirtualRr = createHhppVirtualRr(requestDataCreaHhppVirtual);
            boolean isHhppVirtualRrCreated = (boolean) hhppVirtualRr.getOrDefault(ConstantsSolicitudHhpp.RESULTADO_EXITOSO, false);
            result.put(ConstantsSolicitudHhpp.MSG_RESULT, result.getOrDefault(ConstantsSolicitudHhpp.MSG_RESULT, "")
                    + (String) hhppVirtualRr.getOrDefault(ConstantsSolicitudHhpp.MSG_RESULT, ""));

            if (!isHhppVirtualRrCreated) {
                result.put(ConstantsSolicitudHhpp.RESULTADO_EXITOSO, false);
                return result;
            }

            ResponseDataCreaHhppVirtual response = (ResponseDataCreaHhppVirtual) hhppVirtualRr.getOrDefault("response", null);
            Objects.requireNonNull(response, "La respuesta del servicio de creación de HHPP virtual fue nula. ");
            String idDireccionRR = Optional.of(response).map(ResponseDataCreaHhppVirtual::getIdDireccion).orElse("0");
            request.setIdDireccionUnidadVirtualRR(idDireccionRR);
            /* Ejecuta creación de HHPP Virtual en MER */
            Map<String, Object> hhppVirtualMer = createHhppVirtualMer(hhppVirtual, request);
            result.put(ConstantsSolicitudHhpp.RESULTADO_EXITOSO, hhppVirtualMer.getOrDefault(ConstantsSolicitudHhpp.RESULTADO_EXITOSO, false));
            result.put(ConstantsSolicitudHhpp.MSG_RESULT, result.getOrDefault(ConstantsSolicitudHhpp.MSG_RESULT, "")
                    + (String) hhppVirtualMer.getOrDefault(ConstantsSolicitudHhpp.MSG_RESULT, ""));
            return result;

        } catch (ApplicationException ae) {
            String msgError = "Error en la creación de HHPP Virtual " + ae.getMessage();
            LOGGER.error(msgError);
            result.put(ConstantsSolicitudHhpp.RESULTADO_EXITOSO, false);
            result.put(ConstantsSolicitudHhpp.MSG_RESULT, msgError);
            return result;

        } catch (Exception e) {
            String msgError = "Error registrando el HHPP virtual. " + e.getMessage();
            LOGGER.error(msgError, e.getMessage());
            result.put(ConstantsSolicitudHhpp.RESULTADO_EXITOSO, false);
            result.put(ConstantsSolicitudHhpp.MSG_RESULT, msgError);
            return result;
        }
    }

    /**
     * Comprueba la respuesta del servicio createHHPPVirtual para asignar el mensaje de error
     *
     * @param responseDataCreaHhppVirtual {@link ResponseDataCreaHhppVirtual}
     * @return {@code Map<String, String>}
     * @author Gildardo Mora
     */
    private Map<String, String> validateResposeCreateHhppVirtual(ResponseDataCreaHhppVirtual responseDataCreaHhppVirtual) {
        Map<String, String> result = new HashMap<>();
        String responseCode = Optional.of(responseDataCreaHhppVirtual).map(ResponseDataCreaHhppVirtual::getCodigoRespuesta).orElse("");
        String msgResponse = Optional.of(responseDataCreaHhppVirtual).map(ResponseDataCreaHhppVirtual::getMensajeRespuesta).orElse("");
        Predicate<String> noEmpty = StringUtils::isNotBlank;

        if (noEmpty.test(responseCode) || noEmpty.test(msgResponse)) {
            result.put(ConstantsSolicitudHhpp.MSG_ERROR, "Respuesta RR : COD RESP: " + responseCode + " MSG RESP: " + msgResponse);
            return result;
        }

        result.put(ConstantsSolicitudHhpp.MSG_ERROR, "no se pudo ejecutar el servicio de");
        return result;
    }

    /**
     * Registra el HHPP Virtual en la BD MER
     *
     * @param hhppVirtual {@link HhppMgl}
     * @param request     {@link RequestCreaSolicitudTrasladoHhppBloqueado}
     * @return {@code Map<String, Object>}
     * @author Gildardo Mora
     */
    private Map<String, Object> createHhppVirtualMer(HhppMgl hhppVirtual, RequestCreaSolicitudTrasladoHhppBloqueado request) throws ApplicationException {
        try {
            Map<String, Object> result = new HashMap<>();
            HhppVirtualMglDaoImpl hhppVirtualMglDao = new HhppVirtualMglDaoImpl();
            Map<String, Object> hhppVirtualSp = hhppVirtualMglDao.createHhppVirtualSp(hhppVirtual, request);
            boolean resultadoExitoso = (boolean) hhppVirtualSp.getOrDefault(ConstantsSolicitudHhpp.RESULTADO_EXITOSO, false);

            if (!resultadoExitoso) {
                result.put(ConstantsSolicitudHhpp.RESULTADO_EXITOSO, hhppVirtualSp.getOrDefault(ConstantsSolicitudHhpp.RESULTADO_EXITOSO, false));
                result.put(ConstantsSolicitudHhpp.MSG_RESULT, hhppVirtualSp.getOrDefault("msgResultado", ""));
                return result;
            }

            result.put(ConstantsSolicitudHhpp.RESULTADO_EXITOSO, hhppVirtualSp.getOrDefault(ConstantsSolicitudHhpp.RESULTADO_EXITOSO, true));
            return result;

        } catch (ApplicationException e) {
            String msgError = ERROR_MESSAGE + ClassUtils.getCurrentMethodName();
            LOGGER.error(msgError);
            throw new ApplicationException(e);
        }
    }

    /**
     * Crea el HHPP virtual y la OT de traslado en RR
     *
     * @param requestDataCreaHhppVirtual {@link RequestDataCreaHhppVirtual}
     * @return {@code Map<String, Object>}
     * @author Gildardo Mora
     */
    Map<String, Object> createHhppVirtualRr(RequestDataCreaHhppVirtual requestDataCreaHhppVirtual) {
        Map<String, Object> result = new HashMap<>();
        try {
            Optional<ResponseDataCreaHhppVirtual> responseDataCreaHhppVirtual;
            responseDataCreaHhppVirtual = callServiceCreateVirtualHhppResource(requestDataCreaHhppVirtual);

            if (!responseDataCreaHhppVirtual.isPresent()) {
                result.put(ConstantsSolicitudHhpp.RESULTADO_EXITOSO, false);
                result.put(ConstantsSolicitudHhpp.MSG_RESULT, "No hubo respuesta valida del servicio");
                return result;
            }

            String responseCode = responseDataCreaHhppVirtual.map(ResponseDataCreaHhppVirtual::getCodigoRespuesta).orElse("");

            if (!responseCode.equals("00")) {
                Map<String, String> resultValidateResp = validateResposeCreateHhppVirtual(responseDataCreaHhppVirtual.get());
                result.put(ConstantsSolicitudHhpp.RESULTADO_EXITOSO, false);
                result.put(ConstantsSolicitudHhpp.MSG_RESULT, resultValidateResp.getOrDefault(ConstantsSolicitudHhpp.MSG_ERROR, ""));
                return result;
            }

            String msgResponse = responseDataCreaHhppVirtual.map(ResponseDataCreaHhppVirtual::getMensajeRespuesta).orElse("");

            if (msgResponse.contains("OT NO fue creada")) {
                result.put(ConstantsSolicitudHhpp.MSG_RESULT, responseDataCreaHhppVirtual.get().getMensajeRespuesta());
            } else {
                String otCreada = responseDataCreaHhppVirtual.map(ResponseDataCreaHhppVirtual::getOTCreada).orElse("");
                result.put(ConstantsSolicitudHhpp.MSG_RESULT, result.getOrDefault(ConstantsSolicitudHhpp.MSG_RESULT, "")
                        + " OT TRASLADO CREADA: " + otCreada);
            }

            result.put(ConstantsSolicitudHhpp.RESULTADO_EXITOSO, true);
            result.put("response", responseDataCreaHhppVirtual.get());
            return result;

        } catch (ApplicationException ae) {
            LOGGER.error(ae.getMessage(), ae);
            result.put(ConstantsSolicitudHhpp.RESULTADO_EXITOSO, false);
            result.put(ConstantsSolicitudHhpp.MSG_RESULT, ae.getMessage());
            return result;
        } catch (Exception e) {
            LOGGER.error(e);
            result.put(ConstantsSolicitudHhpp.RESULTADO_EXITOSO, false);
            result.put(ConstantsSolicitudHhpp.MSG_RESULT, e.getMessage());
            return result;
        }
    }

    /**
     * Realiza el registro del HHPP virtual para la cuenta Matriz
     * llamado al proceso de creación de HHPP Virtual
     *
     * @param hhppMgl {@link HhppMgl} Datos del HHPP virtual a crear
     * @param usuario {@link UsuarioPortalResponseDto} Datos de usuario en sesión
     * @return Retorna true si se ejecutó correctamente el registro de HHPP Virtual
     */
    public boolean crearHhppVirtualCM(HhppMgl hhppMgl, UsuarioPortalResponseDto usuario) throws ApplicationException {
        try {
            HhppVirtualMglDaoImpl hhppVirtualMglDao = new HhppVirtualMglDaoImpl();
            RequestCreaSolicitudTrasladoHhppBloqueado request = new RequestCreaSolicitudTrasladoHhppBloqueado();
            request.setUsuarioVt(usuario.getUsuario());
            request.setPerfilVt(Integer.valueOf(usuario.getCodPerfil()));

            Map<String, Object> hhppVirtualSpCM = hhppVirtualMglDao.createHhppVirtualSp(hhppMgl, request);
            boolean resultadoExitoso = (boolean) hhppVirtualSpCM.getOrDefault(ConstantsSolicitudHhpp.RESULTADO_EXITOSO, false);

            if (!resultadoExitoso) {
                throw new ApplicationException(ERROR_MESSAGE + ClassUtils.getCurrentMethodName(this.getClass()));
            }

            return true;

        } catch (ApplicationException e) {
            String msgError = "No se pudo realizar  el registro del HHPP virtual";
            LOGGER.error(msgError, e.getMessage());
            throw new ApplicationException(ERROR_MESSAGE + ClassUtils.getCurrentMethodName(this.getClass()));
        }
    }

    /**
     * Comprueba si el flag que permite mostrar el tipo de solicitud está activo.
     *
     * @return Retorna true, cuando el flag tiene valor "TRUE"
     * @author Gildardo Mora
     */
    public boolean isActiveCcmmTrasladoHhppBloqueado() {
        HhppVirtualMglDaoImpl hhppVirtualMglDao = new HhppVirtualMglDaoImpl();
        return hhppVirtualMglDao.isActiveTrasladoHhppBloqueado(true);
    }

    /**
     * Comprueba si el flag que permite mostrar el tipo de solitud de "traslado HHPP Bloqueado" en la solicitud
     * de HHPP de barrio abierto está activo.
     *
     * @return {@code boolean} Retorna true, cuando está activo el flag.
     * @author Gildardo Mora
     */
    public boolean isActiveHhppTrasladoHhppBloqueado() {
        HhppVirtualMglDaoImpl hhppVirtualMglDao = new HhppVirtualMglDaoImpl();
        return hhppVirtualMglDao.isActiveTrasladoHhppBloqueado(false);
    }
}
