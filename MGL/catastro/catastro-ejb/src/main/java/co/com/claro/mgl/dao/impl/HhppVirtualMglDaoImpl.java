package co.com.claro.mgl.dao.impl;

import co.com.claro.cmas400.ejb.request.RequestDataCreaHhppVirtual;
import co.com.claro.cmas400.ejb.request.RequestDataValidaRazonesCreaHhppVt;
import co.com.claro.cmas400.ejb.respons.*;
import co.com.claro.cmas400.ejb.respons.dto.CodigoRazonSubrazon;
import co.com.claro.mer.constants.StoredProcedureNamesConstants;
import co.com.claro.mer.dtos.request.procedure.CrearHhppVirtualRequestDto;
import co.com.claro.mer.dtos.response.procedure.CrearHhppVirtualResponseDto;
import co.com.claro.mer.utils.StoredProcedureUtil;
import co.com.claro.mer.utils.StringToolUtils;
import co.com.claro.mer.utils.enums.DelimitadorEnum;
import co.com.claro.mer.utils.enums.ParametrosMerEnum;
import co.com.claro.mgl.dtos.CmtMarcacionesHhppVtDto;
import co.com.claro.mgl.ejb.wsclient.rest.cm.EnumeratorServiceName;
import co.com.claro.mgl.ejb.wsclient.rest.cm.RestClientGeneric;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.SubDireccionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTecnologiaSubMgl;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.ParametrosMerUtil;
import co.com.claro.mgl.ws.cm.request.RequestCreaSolicitudTrasladoHhppBloqueado;
import com.sun.jersey.api.client.ClientHandlerException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.*;

import static co.com.claro.mer.utils.enums.ParametrosMerEnum.TIEMPO_MIN_CUENTA_HHPP_VT;

/**
 * Se encarga de gestionar las consultas a los servicios rest requeridos para
 * el proceso de creación de hhpp virtual, las consultas hacia las tablas implicadas
 * en MER y las operaciones de negocio intermedias.
 *
 * @author Gildardo Mora
 * @version 1.0, 2022/05/23
 */
public class HhppVirtualMglDaoImpl {
    private static final Logger LOGGER = LogManager.getLogger(HhppVirtualMglDaoImpl.class);
    private static final String RESULTADO_EXITOSO = "resultadoExitoso";
    private static final String MSG_RESULTADO = "msgResultado";

    /**
     * Consulta el tiempo mínimo de la cuenta.
     * <p>
     * Corresponde al valor del acrónimo HHPP_VIRTUAL_TIEMPO_MIN_CUENTA de
     * la tabla parámetros, ejecutando el procedimiento almacenado.
     *
     * @return tiempoMinimoCuenta {@link String}  Valor del tiempo mínimo de cuenta en número de dias.
     */
    public String findTiempoMinimoCuenta() throws ApplicationException {
        try {
            String tiempoMinimoCuenta = ParametrosMerUtil.findValor(TIEMPO_MIN_CUENTA_HHPP_VT.getAcronimo());

            if (tiempoMinimoCuenta.isEmpty()) {
                String msgError = "Error: No se pudo hallar el valor del tiempo mínimo de la cuenta, intente mas tarde..";
                throw new ApplicationException(msgError);
            }

            return tiempoMinimoCuenta;

        } catch (ApplicationException ae) {
            String msgError = "Error: en la ejecución de " + ClassUtils.getCurrentMethodName(this.getClass());
            LOGGER.error(msgError);
            throw new ApplicationException(ae);
        }
    }

    /**
     * Ejecuta el llamado al servicio rest validateMoveReasonsResource
     * Encargado de validar la dirección y la cuenta del cliente con RR
     *
     * @param request {@link ResponseDataValidaRazonesCreaHhppVt}
     * @param uri                                URI de petición al servicio
     * @return response {@link ResponseDataValidaRazonesCreaHhppVt}
     */
    public ResponseValidateMoveReasonsResource callServiceValidateMoveReasonsResource(
            RequestDataValidaRazonesCreaHhppVt request, String uri) throws ApplicationException {

        String serviceName = EnumeratorServiceName.CM_HHPP_VIRTUAL_REASONS_VALIDATE.getServiceName();
        try {
            if (Objects.isNull(request) || StringUtils.isBlank(request.getCuenta())
                    || StringUtils.isBlank(request.getDivisionalRegion()) || StringUtils.isBlank(request.getComunidad())
                    || StringUtils.isBlank(request.getIdentificadorCalle()) || StringUtils.isBlank(request.getIdentificadorCasa())) {
                String msgError = "Hay valores no válidos en la petición al servicio validateMoveReasonsResource ";
                throw new ApplicationException(msgError);
            }

            if (StringUtils.isBlank(uri)) {
                throw new ApplicationException("No se pudo identificar la url del servicio");
            }

            RestClientGeneric restClientGeneric = new RestClientGeneric(uri, serviceName);
            return restClientGeneric.callWebServiceMethodPost(ResponseValidateMoveReasonsResource.class, request);

        } catch (ApplicationException ae) {
            String msgError = "App Exc: En la ejecución de " + ClassUtils.getCurrentMethodName(this.getClass());
            LOGGER.error(msgError, ae.getMessage());
            throw new ApplicationException(msgError, ae);

        } catch (ClientHandlerException ce) {
            String msgError = "No se pudo establecer conexión con el servicio: validateMoveReasonsResource ";
            LOGGER.error(msgError, ce.getMessage());
            throw new ApplicationException(msgError, ce);

        } catch (Exception e) {
            String msgError = "Exp: " + ClassUtils.getCurrentMethodName(this.getClass());
            LOGGER.error(msgError, e.getMessage());
            throw new ApplicationException(e);
        }
    }

    /**
     * Busca la URI requerida para llamar a los servicios de ReasonsResourceLocation
     *
     * @return Retorna la URI de petición a los servicios de ReasonsResourceLocation
     * @throws ApplicationException excepción de la App
     */
    public String findUriReasonsResourceServices() throws ApplicationException {
        return ParametrosMerUtil.findValor(ParametrosMerEnum.BASE_URI_RS_RESOURCES_LOCATION_REST.getAcronimo());
    }

    /**
     * Ejecuta el llamado al servicio rest createVirtualHHPPResource
     * Encargado de realizar la creación de HHPP virtual
     *
     * @param requestDataCreaHhppVirtual {@link RequestDataCreaHhppVirtual} Petición al servicio
     * @param uri                        URI de petición al servicio
     * @return response {@link ResponseDataCreaHhppVirtual} Respuesta del servicio
     * @throws ApplicationException Excepción de la aplicación
     */
    public ResponseCreateVirtualHhppResource callServiceCreateVirtualHhppResource(
            RequestDataCreaHhppVirtual requestDataCreaHhppVirtual, String uri) throws ApplicationException {

        String serviceName = EnumeratorServiceName.CM_HHPP_VIRTUAL_CREATE.getServiceName();
        try {
            if (Objects.isNull(requestDataCreaHhppVirtual)
                    || StringUtils.isBlank(requestDataCreaHhppVirtual.getCuenta())
                    || requestDataCreaHhppVirtual.getCuenta().equals("0")
                    || StringUtils.isBlank(requestDataCreaHhppVirtual.getCodigoDivision())
                    || StringUtils.isBlank(requestDataCreaHhppVirtual.getCodigoComunidad())
                    || StringUtils.isBlank(requestDataCreaHhppVirtual.getCalle())
                    || StringUtils.isBlank(requestDataCreaHhppVirtual.getApartamentoHhppPrimario())
                    || StringUtils.isBlank(requestDataCreaHhppVirtual.getApartamentoHhppVirtual())
                    || StringUtils.isBlank(requestDataCreaHhppVirtual.getNumeroCasa())
                    || StringUtils.isBlank(requestDataCreaHhppVirtual.getUsuarioPeticion())) {
                String msgError = "Hay valores no válidos en la petición al servicio createVirtualHHPPResource ";
                LOGGER.warn(msgError);
                String infoWarn = "REQUEST callServiceCreateVirtualHhppResource: " + requestDataCreaHhppVirtual;
                LOGGER.warn(infoWarn);
                throw new ApplicationException(msgError);
            }

            if (StringUtils.isBlank(uri)) {
                throw new ApplicationException("No se pudo identificar la url del servicio");
            }
            String requestInfo = "REQUEST callServiceCreateVirtualHhppResource: " + requestDataCreaHhppVirtual;
            LOGGER.info(requestInfo);
            RestClientGeneric restClientGeneric = new RestClientGeneric(uri, serviceName);
            ResponseCreateVirtualHhppResource response = restClientGeneric
                    .callWebServiceMethodPost(ResponseCreateVirtualHhppResource.class, requestDataCreaHhppVirtual);

            Objects.requireNonNull(response, "La respuesta del servicio createVirtualHHPPResource es nula");
            String responseInfo = "RESPONSE callServiceCreateVirtualHhppResource: " + response;
            LOGGER.info(responseInfo);
            String statusCode = Optional.of(response).map(ResponseCreateVirtualHhppResource::getHeaderResponse)
                    .map(HeaderResponseBase::getStatusCode).orElse("");

            String responseCode = Optional.of(response).map(ResponseCreateVirtualHhppResource::getCreateVirtualHhppResourceResponse)
                    .map(ResponseDataCreaHhppVirtual::getCodigoRespuesta).orElse("");

            if (statusCode.startsWith("ER") && !responseCode.equals("00")) {
                String msgErrror = "Error en la creación del HHPP virtual en RR: "
                        + Optional.of(response)
                        .map(ResponseCreateVirtualHhppResource::getCreateVirtualHhppResourceResponse)
                        .map(ResponseDataCreaHhppVirtual::getMensajeRespuesta).orElse("");
                LOGGER.error(msgErrror);
            }
            return response;

        } catch (ApplicationException ae) {
            String msgError = "Ocurrió un error al ejecutar el servicio createVirtualHhppResource: ";
            LOGGER.error(msgError, ae.getMessage());
            throw new ApplicationException(msgError, ae);

        } catch (ClientHandlerException ce) {
            String msgError = "No se pudo establecer conexión con el servicio: createVirtualHhppResource ";
            LOGGER.error(msgError, ce.getMessage());
            throw new ApplicationException(msgError, ce);

        } catch (Exception e) {
            String msgError = "Error en: " + ClassUtils.getCurrentMethodName(this.getClass());
            LOGGER.error(msgError, e.getMessage());
            throw new ApplicationException(msgError, e);
        }
    }

    /**
     * Verifica si la lista de razones y sub razones del HHPP
     * se encuentra entre las marcaciones ajustadas para creación de HHPP Virtual
     *
     * @param codigoRazonSubrazonList  List {@link CodigoRazonSubrazon} Lista de razones y sub razones del HHPP.
     * @param marcacionesHhppVtDtoList {@link List<CmtMarcacionesHhppVtDto>} Lista de marcaciones definidas como
     *                                 "si aplican" para creación de HHPP Virtual
     * @return Retorna true, si son válidas las marcaciones del HHPP.
     * @throws ApplicationException Excepción de la aplicación
     */
    public boolean validateMarcacionesHomePassed(List<CodigoRazonSubrazon> codigoRazonSubrazonList,
            List<CmtMarcacionesHhppVtDto> marcacionesHhppVtDtoList) throws ApplicationException {
        try {
            if (Objects.isNull(codigoRazonSubrazonList)) {
                throw new ApplicationException("La lista de códigos de razón y sub razón no puede ser nula");
            }

            if (codigoRazonSubrazonList.isEmpty()) {
                return true;
            }

            if (CollectionUtils.isEmpty(marcacionesHhppVtDtoList)) {
                return false;
            }

            // Quitar de la lista las parejas de códigos con información faltante
            codigoRazonSubrazonList.removeIf(x ->
                    StringUtils.isBlank(x.getCodigoRazon()) || StringUtils.isBlank(x.getCodigoSubrazon())
                            || x.getCodigoRazon().replace("|", "").trim().isEmpty()
                            || x.getCodigoSubrazon().replace("|", "").trim().isEmpty()
            );

            //validar cada pareja de marcaciones de la cuenta
            // frente a las marcaciones marcadas como "si Aplica"
            for (CodigoRazonSubrazon cod : codigoRazonSubrazonList) {
                cod.setCodigoRazon(cod.getCodigoRazon().replace("|", "").trim());
                cod.setCodigoSubrazon(cod.getCodigoSubrazon().replace("|", "").trim());

                if (!isMarcacionValida(cod, marcacionesHhppVtDtoList)) {
                    return false;
                }
            }

            return true;

        } catch (ApplicationException ae) {
            String msgError = "AppExc: Ocurrió un error en: " + ClassUtils.getCurrentMethodName(this.getClass());
            LOGGER.error(msgError, ae.getMessage());
            throw new ApplicationException(msgError, ae);
        }
    }

    /**
     * Se encarga de verificar si los códigos de bloqueo son permitidos para traslados
     *
     * @param responseDataValidaRazonesCreaHhppVt {@link ResponseDataValidaRazonesCreaHhppVt}
     *                                            Respuesta del servicio validateMoveReasonsResource
     * @param codigosBloqueoList                  {@link List<String>} Lista de códigos de Bloqueo habilitados en parámetro de BD
     * @return Retorna true cuando se cumple con los códigos de bloqueo
     */
    public boolean validateBloqueoHomePassed(ResponseDataValidaRazonesCreaHhppVt responseDataValidaRazonesCreaHhppVt,
            List<String> codigosBloqueoList) throws ApplicationException {

        if (Objects.isNull(responseDataValidaRazonesCreaHhppVt) || Objects.isNull(codigosBloqueoList)) {
            throw new ApplicationException("Se recibió el valor nulo al momento de validar los códigos de bloqueo.");
        }

        List<String> bloqueos = Arrays.asList(responseDataValidaRazonesCreaHhppVt.getCodigoBloqueo1(),
                responseDataValidaRazonesCreaHhppVt.getCodigoBloqueo2(),
                responseDataValidaRazonesCreaHhppVt.getCodigoBloqueo3(),
                responseDataValidaRazonesCreaHhppVt.getCodigoBloqueo4());

        return bloqueos.stream()
                .filter(StringUtils::isNotBlank)
                .map(bloqueo -> bloqueo.trim().toUpperCase())
                .allMatch(codigosBloqueoList::contains);
    }

    /**
     * Consulta los códigos de bloqueo habilitados para el proceso de creación
     * de HHPP virtual en el parámetro asignado en BD.
     *
     * @return {@link List<String>}
     * @throws ApplicationException Excepción de la App.
     * @author Gildardo Mora
     */
    public List<String> findCodigosBloqueoHabilitadosHhppVirtual() throws ApplicationException {
        String codigosBloqueo = ParametrosMerUtil.findValor(ParametrosMerEnum.LOCK_CODES_TO_CREATE_VIRTUAL_HHPP.getAcronimo());
        List<String> codigosBloqueoList = StringToolUtils
                .convertStringToList(codigosBloqueo, DelimitadorEnum.PUNTO_Y_COMA, true);
        codigosBloqueoList.replaceAll(String::toUpperCase);
        return codigosBloqueoList;
    }

    /* -------------- Procesos de validaciones internos -------------------- */

    /**
     * Verifica si el conjunto de razón y sub razón es válido
     *
     * @param codigoRazonSubrazon      {@link CodigoRazonSubrazon}
     * @param marcacionesHhppVtDtoList {@link CmtMarcacionesHhppVtDto}
     * @return Retorna true si el código de razón-SubRazón se encuentra en
     * la lista de marcaciones, que están marcadas como "si aplica" en MER
     */
    private boolean isMarcacionValida(CodigoRazonSubrazon codigoRazonSubrazon, List<CmtMarcacionesHhppVtDto> marcacionesHhppVtDtoList) {
        return marcacionesHhppVtDtoList.stream()
                .anyMatch(marcacion ->
                        marcacion.getCodigoR1().equals(codigoRazonSubrazon.getCodigoRazon())
                                && marcacion.getCodigoR2().equals(codigoRazonSubrazon.getCodigoSubrazon()));
    }

    /**
     * Realiza el registro del HHPP Virtual
     *
     * @param hhppMgl {@link HhppMgl} HHPP Virtual a registrar
     * @param request Datos de la petición al servicio
     * @return {Map<String,Object>}
     * @throws ApplicationException Excepción personalizada de la app
     * @author Gildardo Mora
     */
    public Map<String, Object> createHhppVirtualSp(HhppMgl hhppMgl, RequestCreaSolicitudTrasladoHhppBloqueado request) throws ApplicationException {
        Map<String, Object> createResult = new HashMap<>();

        if (Objects.isNull(hhppMgl)) {
            String msg = "No se pudo ejecutar la creación de HHPP virtual, el valor recibido es nulo. "
                    + ClassUtils.getCurrentMethodName(this.getClass());
            LOGGER.error(msg);
            createResult.put(RESULTADO_EXITOSO, false);
            createResult.put(MSG_RESULTADO, msg);
            return createResult;
        }

        try {
            /* Se requiere adaptar mejor el proceso para la fase 3 */
            //Asignar datos de entrada requeridos para el procedimiento
            CrearHhppVirtualRequestDto requestDto = assignParametersToCreateHhppVirtual(hhppMgl, request);
            //ejecución de procedimiento de creación de HHPP virtual.
            StoredProcedureUtil procedureUtil = new StoredProcedureUtil(StoredProcedureNamesConstants.SP_CREATE_HHPP_VIRTUAL);
            procedureUtil.addRequestData(requestDto);
            CrearHhppVirtualResponseDto responseDto = procedureUtil.executeStoredProcedure(CrearHhppVirtualResponseDto.class);
            String msgRespuesta = responseDto.getRespuesta();
            int codigoRespuesta = responseDto.getCodigo() != null ? responseDto.getCodigo() : 1;

            if (codigoRespuesta == 1) {
                String msg = "No se pudo ejecutar la creación de HHPP virtual " + msgRespuesta;
                LOGGER.error(msg);
                createResult.put(RESULTADO_EXITOSO, false);
                createResult.put(MSG_RESULTADO, msgRespuesta);
                return createResult;
            }

            createResult.put(RESULTADO_EXITOSO, true);
            return createResult;

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Asigna los valores requeridos a los parámetros de entrada
     * del SP de creación de HHPP Virtual
     *
     * @param hhppMgl {@link HhppMgl} HHPP Virtual a registrar
     * @param request {@link RequestCreaSolicitudTrasladoHhppBloqueado} datos de la petición al servicio
     * @author Gildardo Mora
     */
    private CrearHhppVirtualRequestDto assignParametersToCreateHhppVirtual(HhppMgl hhppMgl, RequestCreaSolicitudTrasladoHhppBloqueado request) {
        CrearHhppVirtualRequestDto requestDto = new CrearHhppVirtualRequestDto();
        requestDto.setDireccionId(hhppMgl.getDirId());
        BigDecimal nodoId = Optional.ofNullable(hhppMgl.getNodId())
                .map(NodoMgl::getNodId).orElse(null);
        requestDto.setNodoId(nodoId);
        requestDto.setTipoTecnologiaId(hhppMgl.getThhId());
        BigDecimal sdiId = Optional.ofNullable(hhppMgl.getSubDireccionObj())
                .map(SubDireccionMgl::getSdiId).orElse(null);
        requestDto.setSubDireccionId(sdiId);
        requestDto.setTipoConexionTecId(hhppMgl.getThcId());
        requestDto.setTipoRedTecnologiaId(hhppMgl.getThrId());
        requestDto.setUsuarioCreacion(request.getUsuarioVt());
        requestDto.setUsuarioEdicion(null);//Al ser creado el registro no tiene usuario de edición.
        requestDto.setEstadoId("PO"); // El estado de HHPP PO (Potencial)
        requestDto.setTecnogiaHabilitadaIdRr(hhppMgl.getHhpIdrR());
        requestDto.setCalle(hhppMgl.getHhpCalle());
        requestDto.setPlaca(hhppMgl.getHhpPlaca());
        requestDto.setApart(hhppMgl.getHhpApart() != null ? hhppMgl.getHhpApart() + "V" : null);
        requestDto.setComunidad(hhppMgl.getHhpComunidad());
        requestDto.setDivision(hhppMgl.getHhpDivision());
        requestDto.setEstadoUnit("PO"); // Potencial
        requestDto.setVendedor(hhppMgl.getHhpVendedor());
        requestDto.setCodigoPostal(hhppMgl.getHhpCodigoPostal());
        requestDto.setTipoAcometida(hhppMgl.getHhpTipoAcomet());
        requestDto.setUltUbicacion(hhppMgl.getHhpUltUbicacion());
        requestDto.setHeadEnd(hhppMgl.getHhpHeadEnd());
        requestDto.setTipo(hhppMgl.getHhpTipo());
        requestDto.setEdificio(hhppMgl.getHhpEdificio());
        requestDto.setTipoUnidad(hhppMgl.getHhpTipoUnidad());
        requestDto.setTipoCblAcometida(hhppMgl.getHhpTipoCblAcometida());
        requestDto.setNotaAdd1(hhppMgl.getHhpNotasAdd1());
        requestDto.setNotaAdd2(hhppMgl.getHhpNotasAdd2());
        requestDto.setNotaAdd3(hhppMgl.getHhpNotasAdd3());
        requestDto.setNotaAdd4(hhppMgl.getHhpNotasAdd4());
        BigDecimal subEdificioId = Optional.ofNullable(hhppMgl.getHhppSubEdificioObj())
                .map(CmtSubEdificioMgl::getSubEdificioId).orElse(null);
        requestDto.setSubEdificioId(subEdificioId);
        requestDto.setEstadoRegistro(1);//Se deja activo el registro creado.
        requestDto.setCfm(hhppMgl.getCfm());
        BigDecimal tecnoSubedificioId = Optional.ofNullable(hhppMgl.getCmtTecnologiaSubId())
                .map(CmtTecnologiaSubMgl::getTecnoSubedificioId).orElse(null);
        requestDto.setTecnoSubEdificioId(tecnoSubedificioId);
        requestDto.setSuscriptor(hhppMgl.getSuscriptor());
        requestDto.setMarker(hhppMgl.getMarker());
        requestDto.setPerfilCreacion(request.getPerfilVt() != null ? request.getPerfilVt() : null);
        requestDto.setPerfilEdicion(null);//Al ser creado el registro no tiene perfil de edición.
        requestDto.setHhppScProcesado(hhppMgl.getHhppSCProcesado());
        requestDto.setOrigenFicha(hhppMgl.getHhppOrigenFicha());
        requestDto.setCuentaClienteTrasladar(request.getNumeroCuentaClienteTrasladar());
        requestDto.setNap(hhppMgl.getNap() != null ? hhppMgl.getNap() : null);
        requestDto.setIdDirHhppVirtualRr(request.getIdDireccionUnidadVirtualRR());
        return requestDto;
    }

    /**
     * Comprueba si el flag que permite mostrar el tipo de solicitud está activo.
     *
     * @param isCcmm {@code boolean} Determina si se está validando para el proceso de traslado de HHPP bloqueado de
     *                              Cuenta matriz, por el contrario, se hace desde solicitud de HHPP.
     * @return Retorna true, cuando el flag tiene valor "TRUE"
     * @author Gildardo Mora
     */
    public boolean isActiveTrasladoHhppBloqueado(boolean isCcmm) {
        try {
            ParametrosMerEnum parametro = isCcmm ? ParametrosMerEnum.FLAG_CCMM_HABILITAR_TRASLADO_HHPP_BLOQUEADO : ParametrosMerEnum.FLAG_HHPP_HABILITAR_TRASLADO_HHPP_BLOQUEADO;
            String flagTraslado = ParametrosMerUtil.findValor(parametro.getAcronimo());
            return flagTraslado.trim().equalsIgnoreCase("TRUE");
        } catch (ApplicationException e) {
            LOGGER.error("No se pudo consultar el flag de TRASLADO HHPP BLOQUEADO", e);
            return false;
        }
    }
}
