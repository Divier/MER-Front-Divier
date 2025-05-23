package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.cmas400.ejb.request.RequestDataMarcacionesCreacionHhppVt;
import co.com.claro.cmas400.ejb.respons.ResponseDataMarcacionesHhppVt;
import co.com.claro.cmas400.ejb.respons.ResponseMarcacionesCreacionHhppVt;
import co.com.claro.mer.constants.StoredProcedureNamesConstants;
import co.com.claro.mer.dtos.request.procedure.ActualizaMarcacionHhppVirtualRequestDto;
import co.com.claro.mer.dtos.request.procedure.ConsultaMarcacionHhppVirtualRequestDto;
import co.com.claro.mer.dtos.request.procedure.RegistrarMarcacionHhppVirtualRequestDto;
import co.com.claro.mer.dtos.response.procedure.ActualizaMarcacionHhppVirtualResponseDto;
import co.com.claro.mer.dtos.response.procedure.ConsultaMarcacionHhppVirtualResponseDto;
import co.com.claro.mer.dtos.response.procedure.RegistrarMarcacionHhppVirtualResponseDto;
import co.com.claro.mer.utils.StoredProcedureUtil;
import co.com.claro.mer.utils.enums.ParametrosMerEnum;
import co.com.claro.mgl.dtos.CmtMarcacionesHhppVtDto;
import co.com.claro.mgl.dtos.FiltroConsultaMarcacionesHhppVtDto;
import co.com.claro.mgl.dtos.FiltroMarcacionesHhppVtDto;
import co.com.claro.mgl.ejb.wsclient.rest.cm.EnumeratorServiceName;
import co.com.claro.mgl.ejb.wsclient.rest.cm.RestClientGeneric;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.ParametrosMerUtil;
import co.com.telmex.catastro.services.util.Constant;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.NoResultException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


/**
 * Implementación de lógica asociada al crud y acciones específicas aplicadas.
 * Sobre las marcaciones para creación de HHPP virtuales.
 *
 * @author Gildardo Mora
 * @version 1.2, 2022/06/15 Rev Gildardo Mora
 * @version 1.1, 2021/12/23 Rev Gildardo Mora
 * @since 1.0, 2021/09/29
 */
public class CmtMarcacionesHhppVtMglDaoImpl {

    private static final Logger LOGGER = LogManager.getLogger(CmtMarcacionesHhppVtMglDaoImpl.class);
    private static final String LISTA_DESC_R1 = "listaDescR1";
    private static final String LISTA_DESC_R2 = "listaDescR2";
    private static final String LISTA_APLICA_HHPP ="listaAplicaHhpp";
    private static final String LISTA_ESTADO_MARCACION = "listaEstadoMarcacion";
    private static final String CADENA_DESC_R1 = "cadenaDescR1";
    private static final String CADENA_DESC_R2 = "cadenaDescR2";
    private static final String CADENA_APLICA_HHPP = "cadenaAplicaHhpp";
    private static final String CADENA_ESTADO_MARCACION = "cadenaEstadoMarcacion";
    private static final String MARCACION_CREAR = "marcacionCrear";
    private static final String MARCACION_ELIMINAR = "marcacionEliminar";
    private static final String MARCACION_EXISTENTE = "marcacionExistente";
    private static final String LISTA_CREAR = "listaCrear";
    private static final String LISTA_ACTUALIZAR = "listaActualizar";
    private static final String LISTA_COD_R2 = "listaCodR2";
    private static final String LISTA_COD_R1 = "listaCodR1";
    public static final String LISTA_ESTADO_REG = "listaEstadoReg";
    public static final String LISTA_MARCACION_ID = "listaMarcacionId";
    public static final String CADENA_COD_R_2 = "cadenaCodR2";
    public static final String CADENA_COD_R_1 = "cadenaCodR1";
    public static final String CADENA_MARCACION_ID = "cadenaMarcacionId";
    public static final String CADENA_ESTADO_REG = "cadenaEstadoReg";
    private String lastDateSyncro;

    /**
     * Busca marcaciones para creación de HHPP virtual, según el filtro aplicado.
     *
     * @param filtro      Parámetros para aplicar el filtro de búsqueda
     * @param firstResult Indica el inicio el rango de la lista al aplicar paginación
     * @param maxResults  Indica el fin del rango de la lista al aplicar paginación
     * @return filtroConsultaMarcacionesHhppVtDto
     * @throws ApplicationException Excepción personalizada de la Aplicación
     */
    public FiltroConsultaMarcacionesHhppVtDto findTablasMarcacionesHhppVtSearch(FiltroMarcacionesHhppVtDto filtro,
            int firstResult, int maxResults) throws ApplicationException {

        FiltroConsultaMarcacionesHhppVtDto filtroConsultaMarcacionesHhppVtDto = new FiltroConsultaMarcacionesHhppVtDto();
        List<CmtMarcacionesHhppVtDto> marcacionesHhppVtDatos = consultarMarcacionesHhppSp(filtro);
        filtroConsultaMarcacionesHhppVtDto.setListaTablasMarcacionesHhppVt(
                findMarcacionesHhppVtDatos(firstResult,maxResults,marcacionesHhppVtDatos));
        int numeroRegistros = marcacionesHhppVtDatos.size();
        filtroConsultaMarcacionesHhppVtDto.setNumRegistros(numeroRegistros);
        return filtroConsultaMarcacionesHhppVtDto;
    }

    /**
     * Particiona la lista de marcaciones para apoyar al proceso de paginación
     *
     * @param firstResult            Indica el inicio el rango de la lista al aplicar paginación
     * @param maxResult              Indica el fin del rango de la lista al aplicar paginación
     * @param marcacionesHhppVtDatos {@link List<CmtMarcacionesHhppVtDto>}
     * @return marcacionesList Devuelve la lista de marcaciones encontradas.
     * @throws ApplicationException Excepción de la aplicación
     * @author Gildardo Mora
     */
    public List<CmtMarcacionesHhppVtDto> findMarcacionesHhppVtDatos(
            int firstResult, int maxResult, List<CmtMarcacionesHhppVtDto> marcacionesHhppVtDatos)
            throws ApplicationException {

        if (Objects.isNull(marcacionesHhppVtDatos)) {
            String msgError = "Se presentó un error al consultar los datos para la marcación de HHPP. "
                    + ClassUtils.getCurrentMethodName(this.getClass());
            LOGGER.error(msgError);
            throw new ApplicationException(msgError + "Intente más tarde.");
        }

        if (maxResult > 0) {
            return marcacionesHhppVtDatos.stream().skip(firstResult)
                    .limit(maxResult).collect(Collectors.toList());
        }

        return marcacionesHhppVtDatos;
    }

    /**
     * Consulta las marcaciones que si aplican para creación de HHPP Virtual
     *
     * @return {List<CmtMarcacionesHhppVtDto>} lista de marcaciones
     * @throws ApplicationException Excepción personalizada {@link ApplicationException}
     */
    public List<CmtMarcacionesHhppVtDto> findAllMarcacionesHhppIsAplica() throws ApplicationException {
        FiltroMarcacionesHhppVtDto filtroMarcacionesHhppVtDto = new FiltroMarcacionesHhppVtDto();
        filtroMarcacionesHhppVtDto.setAplicaHhppVt(true);
        filtroMarcacionesHhppVtDto.setEstadoRegistro(1);
        List<CmtMarcacionesHhppVtDto> marcaciones = consultarMarcacionesHhppSp(filtroMarcacionesHhppVtDto);

        if (Objects.isNull(marcaciones)) {
            String msgError = "Se presentó un error al consultar la lista de marcaciones. ";
            LOGGER.error(msgError);
            throw new ApplicationException(msgError + "Intente de nuevo mas tarde.");
        }
        return marcaciones;
    }

    /**
     * Sincroniza la información de las razones y sub razones desde el servicio
     * hacia la tabla de marcaciones en MER.
     *
     * @param usuario Nombre del usuario actual conectado a la sesión de la aplicación
     * @param perfil  Número del perfil del usuario actual conectado en la sesión.
     * @param filtro  Parámetros de filtro aplicable a la consulta de marcaciones
     * @return {@link Boolean} Retorna true cuando se ejecuta la sincronización de forma exitosa
     * @author Gildardo Mora
     */
    public Boolean sincronizarRazones(FiltroMarcacionesHhppVtDto filtro, String usuario, int perfil) throws ApplicationException {
        List<CmtMarcacionesHhppVtDto> listaRazonesServ; //almacena las razones provenientes del servicio
        filtro.setEstadoRegistro(2);//Para poder determinarlo como null y cargar todos los registros en la ejecución del SP.
        List<CmtMarcacionesHhppVtDto> marcacionesDbList; //almacena las razones (marcaciones) provenientes de la DB
        marcacionesDbList = consultarMarcacionesHhppSp(filtro);

        if (marcacionesDbList == null) {
            //Indica que hubo error en la ejecución del Sp y no se sincronizó
            return false;
        }

        if (marcacionesDbList.isEmpty()) {//Indica que se ejecutó el bien el Sp, pero no hay registros de marcaciones
            lastDateSyncro = "00000000"; // Para ejecutar la consulta masiva de razones y sub razones desde el servicio
            listaRazonesServ = consultarRazonesServicio(); //consulta la lista de razones del servicio

            if (!listaRazonesServ.isEmpty()) {
                // Si existen razones y sub razones desde el servicio, las registra como marcaciones en MER.
                return createMarcacionesHhppVt(listaRazonesServ, usuario, perfil);
            }

            return false;
        }

        /*elimina los caracteres innecesarios para cumplir con el formato solicitado en el servicio */
        lastDateSyncro = lastDateSyncro.replace("-", "");
        listaRazonesServ = consultarRazonesServicio();

        if (!listaRazonesServ.isEmpty()) {
            List<CmtMarcacionesHhppVtDto> listaAux = new ArrayList<>(listaRazonesServ);
            return ejecutarSincronizacion(listaAux, marcacionesDbList, usuario, perfil);
        }

        return false;
    }

    /**
     * Realiza la validación de acciones requeridas sobre las razones y sub razones
     * provenientes del servicio, frente a las marcaciones registradas en MER
     *
     * @param listaRazonesServ  Lista de razones y sub razones obtenidas desde el servicio
     * @param marcacionesDbList Lista de marcaciones en MER
     * @param usuario           Nombre del usuario conectado en la sesión
     * @param perfil            Número de perfil del usuario conectado en la sesión
     * @return {@link Boolean} Retorna true cuando se ejecuta correctamente la sincronización.
     */
    private Boolean ejecutarSincronizacion(List<CmtMarcacionesHhppVtDto> listaRazonesServ,
            List<CmtMarcacionesHhppVtDto> marcacionesDbList, String usuario, int perfil) throws ApplicationException {

        //ordena por fecha la lista de razones que llega del servicio
        listaRazonesServ.sort(Comparator.comparing(CmtMarcacionesHhppVtDto::getFechaSincronizacion)
                .thenComparing(CmtMarcacionesHhppVtDto::getEstadoMarcacion).reversed());

        Map<String, List<CmtMarcacionesHhppVtDto>> listaMarcacionesAcciones = validarAccionPorEstadoMarcacion(listaRazonesServ, marcacionesDbList);

        /*registrar las marcaciones nuevas*/
        if (!listaMarcacionesAcciones.getOrDefault(LISTA_CREAR, new ArrayList<>()).isEmpty()) {
            return createMarcacionesHhppVt(listaMarcacionesAcciones.get(LISTA_CREAR), usuario, perfil);
        }

        /*Actualizar marcaciones que lo requieren*/
        if (!listaMarcacionesAcciones.getOrDefault(LISTA_ACTUALIZAR, new ArrayList<>()).isEmpty()) {
            return updateMarcacionesHhppVt(listaMarcacionesAcciones.get(LISTA_ACTUALIZAR), usuario, perfil, true);
        }

        return false;
    }

    /**
     * Verifica las acciones a realizar en función del estado para las marcaciones
     *
     * @param listaRazonesServ  {@link List<CmtMarcacionesHhppVtDto>}
     * @param marcacionesDbList {@link List<CmtMarcacionesHhppVtDto>}
     * @return {@code Map<String, List<CmtMarcacionesHhppVtDto>>}
     */
    private Map<String, List<CmtMarcacionesHhppVtDto>> validarAccionPorEstadoMarcacion(List<CmtMarcacionesHhppVtDto> listaRazonesServ,
            List<CmtMarcacionesHhppVtDto> marcacionesDbList) {

        List<CmtMarcacionesHhppVtDto> listaCreateMarcaciones = new ArrayList<>();
        List<CmtMarcacionesHhppVtDto> listaUpdateMarcaciones = new ArrayList<>();
        Map<String, CmtMarcacionesHhppVtDto> marcacionAccion = new HashMap<>();
        Map<String, List<CmtMarcacionesHhppVtDto>> listasAccion = new HashMap<>();

        for (CmtMarcacionesHhppVtDto razonServ : listaRazonesServ) {
            CmtMarcacionesHhppVtDto marcacionExistente = obtenerMarcacionExistente(razonServ, marcacionesDbList);
            //para apoyar la validación en escenarios en que se crea, edita y elimina
            // una razón y sub razón el mismo dia en RR
            //verifica si la razon ya está agregada en la lista por actualizar
            CmtMarcacionesHhppVtDto marcacionForDelete = getMarcacionesHhppBorrar(razonServ, listaUpdateMarcaciones);
            // verifica si la razon ya esta agregada en la lista por Crear / registrar
            CmtMarcacionesHhppVtDto marcacionForCreate = validarMarcacionExistenteEnLista(razonServ, listaCreateMarcaciones);
            marcacionAccion.put(MARCACION_EXISTENTE, marcacionExistente);
            marcacionAccion.put(MARCACION_ELIMINAR, marcacionForDelete);
            marcacionAccion.put(MARCACION_CREAR, marcacionForCreate);
            procesarListasDeMarcaciones(razonServ, listaCreateMarcaciones, listaUpdateMarcaciones, marcacionAccion);
        }

        listasAccion.put(LISTA_CREAR, listaCreateMarcaciones);
        listasAccion.put(LISTA_ACTUALIZAR, listaUpdateMarcaciones);
        return listasAccion;
    }

    /**
     * Determina si la marcación es existente y retorna sus datos
     *
     * @param razonServ              Razón/ Sub Razón que proviene del servicio
     * @param marcacionesDbList      Lista de marcaciones que esta actualmente en la db
     */
    private  CmtMarcacionesHhppVtDto obtenerMarcacionExistente(CmtMarcacionesHhppVtDto razonServ, List<CmtMarcacionesHhppVtDto> marcacionesDbList) {
        //valida existencia de razón en la lista almacenada en BD MER para
        // completar datos requeridos en proceso de actualización
        CmtMarcacionesHhppVtDto marcacionExistente = validarMarcacionExistenteEnLista(razonServ, marcacionesDbList);

        if (marcacionExistente != null) {
            razonServ.setMarcacionId(marcacionExistente.getMarcacionId());
            razonServ.setAplicaHhppVt(marcacionExistente.isAplicaHhppVt());
        }

        return marcacionExistente;
    }

    /**
     * Realiza las operaciones sobre las marcaciones en proceso de validación
     *
     * @param razonServ              {@link CmtMarcacionesHhppVtDto} Razón/ Sub Razón que proviene del servicio
     * @param listaCreateMarcaciones {@link List<CmtMarcacionesHhppVtDto>} Lista de marcaciones a poblar con las que requieren su creacion
     * @param listaUpdateMarcaciones {@link List<CmtMarcacionesHhppVtDto>} Lista de marcaciones a poblar con las que van a ser actualizadas
     * @param marcacionAccion        {@code Map<String,CmtMarcacionesHhppVtDto>}
     */
    private void procesarListasDeMarcaciones(CmtMarcacionesHhppVtDto razonServ, List<CmtMarcacionesHhppVtDto> listaCreateMarcaciones,
            List<CmtMarcacionesHhppVtDto> listaUpdateMarcaciones, Map<String, CmtMarcacionesHhppVtDto> marcacionAccion) {

        CmtMarcacionesHhppVtDto marcacionExistente = marcacionAccion.getOrDefault(MARCACION_EXISTENTE, null);
        CmtMarcacionesHhppVtDto marcacionForDelete = marcacionAccion.getOrDefault(MARCACION_ELIMINAR, null);
        CmtMarcacionesHhppVtDto marcacionForCreate = marcacionAccion.getOrDefault(MARCACION_CREAR, null);

        // si no hay datos de Razón / sub razón validos no continua la validación
        if (Objects.isNull(razonServ) || StringUtils.isBlank(razonServ.getEstadoMarcacion())) {
            return;
        }

        String[] estadosHabilitados = {"NR", "NS", "MR", "MS", "BR", "BS"};
        String estadoRecibido = razonServ.getEstadoMarcacion();
        boolean esEstadoValido = Arrays.asList(estadosHabilitados).contains(estadoRecibido);

        if (!esEstadoValido) {
            String msgError = "El estado de marcación : " + estadoRecibido + "  recibido no es valido";
            LOGGER.error(msgError);
            return;
        }

        //valida si no existe registro de la marcación, lo agrega a lista de creación y retorna.
        if (Objects.isNull(marcacionExistente)) {
            if (estadoProcesado(estadoRecibido, "BR", "BS")) {
                //deshabilita el registro para que no se cargue al front
                razonServ.setEstadoRegistro(0);
            }

            agregarAlistaCreacion(razonServ, listaCreateMarcaciones, marcacionForCreate);
            return;
        }

        if (estadoProcesado(estadoRecibido, "NR", "NS")) {
            // Si existe el registro de marcación y tiene estado borrado razón / Sub razón, lo habilita.
            validarSiExisteYActivarRegistro(razonServ, listaUpdateMarcaciones, marcacionExistente, marcacionForDelete);
            return;
        }

        if (estadoProcesado(estadoRecibido, "MR", "MS")) {
            //valida que no este pendiente de eliminación
            if (Objects.isNull(marcacionForDelete)) {
                razonServ.setEstadoRegistro(marcacionExistente.getEstadoRegistro() == 0 ? 1 : marcacionExistente.getEstadoRegistro());
                listaUpdateMarcaciones.add(razonServ);
            }
            return;
        }

        if (estadoProcesado(estadoRecibido, "BR", "BS")) {
            //si existe, lo agrega a la lista que se va actualizar
            listaUpdateMarcaciones.add(razonServ);
        }
    }

    /**
     * Verifica si el estado recibido en el proceso corresponde a los tipos de estado que se están validando
     *
     * @param estadoRecibido {@link String}
     * @param estado1        {@link String}
     * @param estado2        {@link String}
     * @return {@code boolean}
     */
    private boolean estadoProcesado(String estadoRecibido, String estado1, String estado2) {
        return estadoRecibido.equals(estado1) || estadoRecibido.equals(estado2);
    }

    /**
     * Se agrega la marcación a la lista pendiente por crear si no se encuentra alli.
     *
     * @param razonServ              {@link CmtMarcacionesHhppVtDto}
     * @param listaCreateMarcaciones {@link List<CmtMarcacionesHhppVtDto>}
     * @param marcacionForCreate     {@link CmtMarcacionesHhppVtDto}
     */
    private void agregarAlistaCreacion(CmtMarcacionesHhppVtDto razonServ, List<CmtMarcacionesHhppVtDto> listaCreateMarcaciones,
            CmtMarcacionesHhppVtDto marcacionForCreate) {
        // Al verificar que no existe la marcación en MER, la agrega a lista de creación
        if (marcacionForCreate == null) {
            listaCreateMarcaciones.add(razonServ);
        }
    }

    /**
     * Verifica si existe la marcación y si requiere habilitar el registro.
     *
     * @param razonServ              {@link CmtMarcacionesHhppVtDto}
     * @param listaUpdateMarcaciones {@link List<CmtMarcacionesHhppVtDto>}
     * @param marcacionExistente     {@link CmtMarcacionesHhppVtDto}
     * @param marcacionForDelete     {@link CmtMarcacionesHhppVtDto}
     */
    private void validarSiExisteYActivarRegistro(CmtMarcacionesHhppVtDto razonServ, List<CmtMarcacionesHhppVtDto> listaUpdateMarcaciones,
            CmtMarcacionesHhppVtDto marcacionExistente, CmtMarcacionesHhppVtDto marcacionForDelete) {

        if (marcacionExistente.getEstadoMarcacion().equals("BS") || marcacionExistente.getEstadoMarcacion().equals("BR")) {
            razonServ.setEstadoRegistro(1);
            // como el registro ya existe, se procede a realizar la actualización
            if (Objects.isNull(marcacionForDelete)) {
                listaUpdateMarcaciones.add(razonServ);
            }
        }
    }

    /**
     * Verifica si hay marcaciones pendientes para eliminar
     *
     * @param razonServ              {@link CmtMarcacionesHhppVtDto}
     * @param listaUpdateMarcaciones {@link List<CmtMarcacionesHhppVtDto>}
     * @return {@link CmtMarcacionesHhppVtDto}
     */
    private CmtMarcacionesHhppVtDto getMarcacionesHhppBorrar(CmtMarcacionesHhppVtDto razonServ, List<CmtMarcacionesHhppVtDto> listaUpdateMarcaciones) {
        return listaUpdateMarcaciones.stream()
                .filter(razonUpdate -> razonUpdate.getCodigoR1().equals(razonServ.getCodigoR1())
                        && razonUpdate.getCodigoR2().equals(razonServ.getCodigoR2())
                        && (razonUpdate.getEstadoMarcacion().equals("BS")
                        || razonUpdate.getEstadoMarcacion().equals("BR")))
                .findFirst().orElse(null);
    }

    /**
     * Busca una marcación específica en la lista que se recibe.
     *
     * @param marcacion     {@link CmtMarcacionesHhppVtDto}
     * @param marcacionList {@link List<CmtMarcacionesHhppVtDto>}
     * @return {@link CmtMarcacionesHhppVtDto}
     */
    private CmtMarcacionesHhppVtDto validarMarcacionExistenteEnLista(CmtMarcacionesHhppVtDto marcacion,
            List<CmtMarcacionesHhppVtDto> marcacionList) {

        return marcacionList.stream()
                .filter(marcacionDeLista -> marcacionDeLista.getCodigoR1().equals(marcacion.getCodigoR1())
                        && marcacionDeLista.getCodigoR2().equals(marcacion.getCodigoR2()))
                .findFirst()
                .orElse(null);
    }

    /**
     * consulta las razones y sub razones que expone el servicio Rest GetMoveReasonsResourceRestClient.
     *
     * @return razonesList
     */
    private List<CmtMarcacionesHhppVtDto> consultarRazonesServicio() throws ApplicationException {

        String url;
        try {
            //obtiene la url de conexión del servicio desde la tabla de parámetros
            url = ParametrosMerUtil.findValor(ParametrosMerEnum.BASE_URI_GET_MOVE_REASONS_RESOURCE.getAcronimo());

        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, ex);
        }

        if (StringUtils.isBlank(url)) {
            return new ArrayList<>();
        }
        List<CmtMarcacionesHhppVtDto> razonesList;

        /* llamado al servicio rest */
        try {
            razonesList = callServiceGetMoveReasonsResource(url, lastDateSyncro);
            return razonesList;

        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new ApplicationException(e);
        }

    }

    /**
     * Realiza el llamado al servicio rest getMoveReasonsResource
     *
     * @param url   Direccion de conexión del servicio Rest
     * @param fecha Fecha de la última sincronización presentada en MER
     * @return Lista de razones y sub razones (marcaciones)
     */
    private List<CmtMarcacionesHhppVtDto> callServiceGetMoveReasonsResource(String url, String fecha) throws ApplicationException {
        try {
            String serviceName = EnumeratorServiceName.CM_HHPP_VIRTUAL_REASONS_QUERY.getServiceName();
            url = url.replace(serviceName,"");
            RestClientGeneric restClientGeneric = new RestClientGeneric(url,serviceName) ;
            RequestDataMarcacionesCreacionHhppVt request = new RequestDataMarcacionesCreacionHhppVt(fecha);
            ResponseMarcacionesCreacionHhppVt response = restClientGeneric
                    .callWebServiceMethodPost(ResponseMarcacionesCreacionHhppVt.class, request);

            return convertRazonesToMarcaciones(response.getGetMoveReasonsResourceResponse()
                    .getMarcacionesHhppVtList());

        } catch (Exception e) {
            String msgError = "Ocurrió un error en el llamado al servicio rest getMoveReasonsResource en: "
                    + ClassUtils.getCurrentMethodName(this.getClass()) + " : " + e.getMessage();
            LOGGER.error(msgError);
            throw new ApplicationException(msgError, e);
        }
    }

    /**
     * Ajusta la lista plana (respuesta del servicio) en lista de marcaciones
     * requerida para el resto del proceso de sincronización
     *
     * @param razonesList Lista originada desde del cliente del servicio
     * @return marcacionesList Lista convertida en marcaciones para uso en el proceso
     */
    private List<CmtMarcacionesHhppVtDto> convertRazonesToMarcaciones(List<ResponseDataMarcacionesHhppVt> razonesList) {

        List<CmtMarcacionesHhppVtDto> marcacionesList = new ArrayList<>();
        //quitar del arreglo aquellas que tienen error en la data
        razonesList = razonesList.stream()
                .filter(marcacion -> marcacion.getFecha() != null
                        && !marcacion.getFecha().isEmpty()
                        && !marcacion.getFecha().equals("0")
                        && marcacion.getCodigoRazonR1() != null && !marcacion.getCodigoRazonR1().isEmpty()
                        && marcacion.getCodigoRazonR2() != null && !marcacion.getDescripcionR1().isEmpty())
                .collect(Collectors.toList());

        razonesList.forEach(razon -> {
            CmtMarcacionesHhppVtDto marcacion = new CmtMarcacionesHhppVtDto();
            marcacion.setCodigoR1(razon.getCodigoRazonR1());
            marcacion.setDescripcionR1(razon.getDescripcionR1());
            marcacion.setCodigoR2(razon.getCodigoRazonR2());
            marcacion.setDescripcionR2(razon.getDescripcionR2());
            marcacion.setEstadoMarcacion(razon.getEstado());
            String fecha = razon.getFecha();
            fecha = fecha.substring(0, 4) + "-"
                    + fecha.substring(4, 6) + "-"
                    + fecha.substring(6, 8);
            marcacion.setFechaSincronizacion(LocalDateTime.parse(fecha));
            marcacionesList.add(marcacion);
        });

        return marcacionesList;
    }

    /**
     * Registra las marcaciones nuevas resultado de la sincronización con el
     * servicio rest, para enviarlas al procedimiento almacenado.
     *
     * @param marcacionesList Lista de marcaciones que van a ser registradas
     * @param usuario         Nombre de usuario actual conectado a la sesión de la aplicación
     * @param perfil          Número del perfil de usuario actual conectado a la sesión de la aplicación.
     * @return ""
     */
    public Boolean createMarcacionesHhppVt(List<CmtMarcacionesHhppVtDto> marcacionesList, String usuario, int perfil) {
        try {
            List<CmtMarcacionesHhppVtDto> listMarcaciones = prepareInfoCreateMarcaciones(marcacionesList, usuario, perfil);
            Map<String, List<String>> listasMarcaciones = new HashMap<>(); //almacena las listas de datos a convertir a cadenas
            Map<String, String> cadenasMarcaciones = new HashMap<>(); // almacena las cadenas de datos separados por punto y coma
            /* Apoya al proceso de validación de cumplir con límite de cadena*/
            int countSeparadores = 0;
            int countCaracteres;
            /* asigna los valores a las listas de Strings requeridas */
            for (CmtMarcacionesHhppVtDto marc : listMarcaciones) {
                countSeparadores++; // supone el equivalente a cada separador (punto y coma)
                countCaracteres = listasMarcaciones.size() == 0 ? 0 : Math.max(
                        listasMarcaciones.get(LISTA_DESC_R1).toString().length(),
                        listasMarcaciones.get(LISTA_DESC_R2).toString().length());

                /* valida si se encuentra dentro del límite de la cadena que se puede crear */
                if ((countCaracteres + marc.getDescripcionR1().length()
                        + countSeparadores) < Constant.LIMITE_CARACTERES_ENTRADA_SP) {
                    /* agrega los elementos a las listas*/
                    convertMarcacionCreate(listasMarcaciones, marc);

                } else {
                    //si no cumple con el límite de cadena, guarda las marcaciones de la cadena formada, y forma una nueva cadena
                    cadenasMarcaciones.putAll(generarCadenasCreateMarcaciones(listasMarcaciones));
                    registrarMarcacionesHhppSp(cadenasMarcaciones, usuario, perfil);//registra las marcaciones procesadas
                    //vaciar listas tras haber registrado las marcaciones procesadas
                    cadenasMarcaciones.clear();
                    listasMarcaciones.clear();
                    //pasar la marcación actual que no fue procesada
                    convertMarcacionCreate(listasMarcaciones, marc);
                }
            }

            cadenasMarcaciones.putAll(generarCadenasCreateMarcaciones(listasMarcaciones));
            registrarMarcacionesHhppSp(cadenasMarcaciones, usuario, perfil); //registra marcaciones procesadas
            return true;

        } catch (Exception e) {
            String msgError = "Error al crear Marcaciones: " + ClassUtils.getCurrentMethodName(this.getClass());
            LOGGER.error(msgError, e);
            return false;
        }
    }

    /**
     * Se encarga de asignar las marcaciones a listas del map
     *
     * @param listasMarcaciones Mapa en el que procesará las listas de elementos de marcaciones
     * @param marcacion         Marcación que será procesada en las listas de los elementos
     */
    private void convertMarcacionCreate(Map<String, List<String>> listasMarcaciones, CmtMarcacionesHhppVtDto marcacion) {
        /* agrega los elementos a las listas*/
        listasMarcaciones.computeIfAbsent(LISTA_COD_R1, k -> new ArrayList<>()).add(marcacion.getCodigoR1());
        listasMarcaciones.computeIfAbsent(LISTA_DESC_R1, k -> new ArrayList<>()).add(marcacion.getDescripcionR1());
        listasMarcaciones.computeIfAbsent(LISTA_COD_R2, k -> new ArrayList<>()).add(marcacion.getCodigoR2());
        listasMarcaciones.computeIfAbsent(LISTA_DESC_R2, k -> new ArrayList<>()).add(marcacion.getDescripcionR2());
        listasMarcaciones.computeIfAbsent(LISTA_APLICA_HHPP, k -> new ArrayList<>()).add("0"); //0 es No aplica
        listasMarcaciones.computeIfAbsent(LISTA_ESTADO_MARCACION, k -> new ArrayList<>()).add(marcacion.getEstadoMarcacion());
    }

    /**
     * Prepara la lista de marcaciones para creación de HHPP virtual, con datos iniciales requeridos para su registro
     *
     * @param marcacionesList Lista de marcaciones para agregar información inicial requerida
     * @param usuario         Nombre del usuario actual conectado en la sesión de la aplicación
     * @param perfil          Número del perfil del usuario actual conectado en la sesión de la aplicación
     * @return listMarcaciones Devuelve la lista de las marcaciones con la información inicial requerida
     */
    private List<CmtMarcacionesHhppVtDto> prepareInfoCreateMarcaciones(List<CmtMarcacionesHhppVtDto> marcacionesList,
            String usuario, int perfil) {
        /* Se agrega información requerida a la lista de marcaciones */
        marcacionesList.forEach(marcacion -> {
            marcacion.setEstadoRegistro(1);// Estado 1 es activo
            marcacion.setFechaCreacion(LocalDateTime.now());
            marcacion.setUsuarioCreacion(usuario);
            marcacion.setPerfilCreacion(perfil);
        });
        return marcacionesList;
    }

    /**
     * Se encarga de transformar las listas recibidas de las marcaciones, en cadenas separadas por punto y coma
     * para que pueda ser procesada por el procedimiento almacenado
     *
     * @param cadenaMarcaciones Mapa con las listas de marcaciones
     * @return cadenasGeneradas Retorna el mapa con las cadenas generadas
     */
    private Map<String, String> generarCadenasCreateMarcaciones(Map<String, List<String>> cadenaMarcaciones) {
        Map<String, String> cadenasGeneradas = new HashMap<>();
        cadenasGeneradas.put(CADENA_COD_R_1, String.join(";", cadenaMarcaciones.get(LISTA_COD_R1)));// cadena string con separador punto y coma.
        cadenasGeneradas.put(CADENA_DESC_R1, String.join(";", cadenaMarcaciones.get(LISTA_DESC_R1)));
        cadenasGeneradas.put(CADENA_COD_R_2, String.join(";", cadenaMarcaciones.get(LISTA_COD_R2)));
        cadenasGeneradas.put(CADENA_DESC_R2, String.join(";", cadenaMarcaciones.get(LISTA_DESC_R2)));
        cadenasGeneradas.put(CADENA_APLICA_HHPP, String.join(";", cadenaMarcaciones.get(LISTA_APLICA_HHPP)));
        cadenasGeneradas.put(CADENA_ESTADO_MARCACION, String.join(";", cadenaMarcaciones.get(LISTA_ESTADO_MARCACION)));
        return cadenasGeneradas;
    }

    /**
     * Ejecuta la actualización de registros de marcaciones para creación de
     * HHPP virtual, a través del procedimiento almacenado.
     *
     * @param marcacionesList Lista de marcaciones que van a ser actualizadas
     * @param usuario         Nombre de usuario actual conectado en la sesión de la aplicación
     * @param perfil          Número de perfil del usuario actual conectado en la sesión
     * @return {@link Boolean} Retorna true si se ejecuta la actualización de marcaciones
     */
    public Boolean updateMarcacionesHhppVt(List<CmtMarcacionesHhppVtDto> marcacionesList,
            String usuario, int perfil, boolean sincronizar) throws ApplicationException {

        try {
            List<CmtMarcacionesHhppVtDto> listMarcaciones = prepareInfoUpdateMarcaciones(marcacionesList, usuario, perfil);
            Map<String, List<String>> listasMarcaciones = new HashMap<>(); //almacena las listas de datos a convertir a cadenas
            Map<String, String> cadenasMarcaciones = new HashMap<>(); // almacena las cadenas de datos separados por punto y coma
            /* Apoya al proceso de validación de cumplir con límite de cadena*/
            int countSeparadores = 0;
            int countCaracteres;

            /* asigna los valores a las listas de Strings requeridas */
            for (CmtMarcacionesHhppVtDto marc : listMarcaciones) {
                countSeparadores++; // incrementa para suponer el carácter equivalente a cada separador (punto y coma)
                countCaracteres = listasMarcaciones.size() == 0 ? 0 : Math.max(
                        listasMarcaciones.get(LISTA_DESC_R1).toString().length(),
                        listasMarcaciones.get(LISTA_DESC_R2).toString().length());

                /* valida si se encuentra dentro del límite de la cadena que se puede crear */
                if ((countCaracteres + marc.getDescripcionR1().length()
                        + countSeparadores) < Constant.LIMITE_CARACTERES_ENTRADA_SP) {
                    /* agrega los elementos a las listas*/
                    convertMarcacionUpdate(listasMarcaciones, marc);
                } else {
                    //si no cumple con el límite de cadena, guarda las marcaciones de la cadena formada, y forma una nueva cadena
                    cadenasMarcaciones.putAll(generarCadenasCreateMarcaciones(listasMarcaciones));
                    actualizarMarcacionesHhppSp(cadenasMarcaciones, usuario, perfil, sincronizar);//registra las marcaciones procesadas
                    //vaciar listas tras haber registrado las marcaciones procesadas
                    cadenasMarcaciones.clear();
                    listasMarcaciones.clear();
                    //pasar la marcación actual que no fue procesada
                    convertMarcacionUpdate(listasMarcaciones, marc);
                }
            }

            cadenasMarcaciones.putAll(generarCadenasUpdateMarcaciones(listasMarcaciones));//
            actualizarMarcacionesHhppSp(cadenasMarcaciones, usuario, perfil, sincronizar); //registra marcaciones procesadas
            return true;

        } catch (Exception e) {
            String msgError = "Error al crear Marcaciones: " + ClassUtils.getCurrentMethodName(this.getClass());
            LOGGER.error(msgError, e.getMessage());
            throw new ApplicationException(msgError, e);
        }
    }

    /**
     * @param listasMarcaciones Mapa que contendrá las listas de marcaciones
     * @param marcacion         Marcación que será procesada en el map
     */
    private void convertMarcacionUpdate(Map<String, List<String>> listasMarcaciones, CmtMarcacionesHhppVtDto marcacion) {
        /* agrega los elementos a las listas*/
        listasMarcaciones.computeIfAbsent(LISTA_MARCACION_ID, k -> new ArrayList<>())
                .add(String.valueOf(marcacion.getMarcacionId()));
        listasMarcaciones.computeIfAbsent(LISTA_DESC_R1, k -> new ArrayList<>())
                .add(marcacion.getDescripcionR1());
        listasMarcaciones.computeIfAbsent(LISTA_DESC_R2, k -> new ArrayList<>())
                .add(marcacion.getDescripcionR2());
        listasMarcaciones.computeIfAbsent(LISTA_APLICA_HHPP, k -> new ArrayList<>())
                .add(marcacion.isAplicaHhppVt() ? "1" : "0");
        listasMarcaciones.computeIfAbsent(LISTA_ESTADO_REG, k -> new ArrayList<>())
                .add(String.valueOf(marcacion.getEstadoRegistro()));
        listasMarcaciones.computeIfAbsent(LISTA_ESTADO_MARCACION, k -> new ArrayList<>())
                .add(String.valueOf(marcacion.getEstadoMarcacion()));
    }

    /**
     * Se encarga de transformar las listas recibidas de las marcaciones, en cadenas separadas por punto y coma
     * para que pueda ser procesada por el procedimiento almacenado de actualización de marcaciones
     *
     * @param cadenaMarcaciones Mapa con las listas de marcaciones
     * @return cadenasGeneradas Retorna el mapa con las cadenas generadas
     */
    private Map<String, String> generarCadenasUpdateMarcaciones(Map<String, List<String>> cadenaMarcaciones) {
        Map<String, String> cadenasGeneradas = new HashMap<>();
        cadenasGeneradas.put(CADENA_MARCACION_ID, String.join(";", cadenaMarcaciones.get(LISTA_MARCACION_ID)));
        cadenasGeneradas.put(CADENA_DESC_R1, String.join(";", cadenaMarcaciones.get(LISTA_DESC_R1)));
        cadenasGeneradas.put(CADENA_DESC_R2, String.join(";", cadenaMarcaciones.get(LISTA_DESC_R2)));
        cadenasGeneradas.put(CADENA_APLICA_HHPP, String.join(";", cadenaMarcaciones.get(LISTA_APLICA_HHPP)));
        cadenasGeneradas.put(CADENA_ESTADO_REG, String.join(";", cadenaMarcaciones.get(LISTA_ESTADO_REG)));
        cadenasGeneradas.put(CADENA_ESTADO_MARCACION, String.join(";", cadenaMarcaciones.get(LISTA_ESTADO_MARCACION)));
        return cadenasGeneradas;
    }

    /**
     * * Prepara los registros de marcaciones por actualizar con la información inicial requerida
     *
     * @param marcacionesList Lista de marcaciones para actualizar, a convertir en cadena
     * @param usuario         Nombre de usuario actual conectado en la sesión de la aplicación
     * @param perfil          Número de perfil del usuario actual conectado en la sesión
     * @return listMarcaciones Retorna la lista de marcaciones con información inicial requerida para actualizar
     */
    private List<CmtMarcacionesHhppVtDto> prepareInfoUpdateMarcaciones(
            List<CmtMarcacionesHhppVtDto> marcacionesList, String usuario, int perfil) {

        /* Se agrega información requerida a la lista de marcaciones */
        marcacionesList.forEach(marcacion -> {
            marcacion.setFechaEdicion(LocalDateTime.now());
            marcacion.setUsuarioEdicion(usuario);
            marcacion.setPerfilEdicion(perfil);
        });
        return marcacionesList;
    }

    /**
     * Consulta el tiempo mínimo del servicio, que corresponde al valor del
     * acrónimo HHPP_VIRTUAL_TIEMPO_MIN_CUENTA de la tabla parámetros, ejecutando el procedimiento almacenado.
     *
     * @return tiempoMinimoServicio Valor del tiempo mínimo en número de dias.
     */
    public String findTiempoMinimoServicio() throws ApplicationException {
        String tiempoMinimoServicio;
        try {
            tiempoMinimoServicio = ParametrosMerUtil.findValor(ParametrosMerEnum.TIEMPO_MIN_CUENTA_HHPP_VT.getAcronimo());
            return tiempoMinimoServicio;

        } catch (Exception ex) {
            String msgError = "Error al consultar el tiempo mínimo del servicio "
                    + ClassUtils.getCurrentMethodName(this.getClass());
            LOGGER.error(msgError, ex);
            throw new ApplicationException("Error al validar el tiempo minimo de servicio");
        }
    }
/*
***************************************************************************************************
 Ejecución de procedimientos almacenados
***************************************************************************************************
 */

    /**
     * Consulta las marcaciones para creación de HHPP virtual usando un procedimiento almacenado.
     *
     * @param filtro, parámetro con el cual se van a filtrar los datos a cargar de las marcaciones.
     * @return listaMarcacionesResult
     * @author Gildardo Mora
     */
    public List<CmtMarcacionesHhppVtDto> consultarMarcacionesHhppSp(FiltroMarcacionesHhppVtDto filtro) throws ApplicationException {
        try {
            ConsultaMarcacionHhppVirtualRequestDto consultaMarcacionHhppVirtualRequestDto = new ConsultaMarcacionHhppVirtualRequestDto();
            consultaMarcacionHhppVirtualRequestDto.setMarcacionHhppVtId(filtro.getMarcacionId());
            consultaMarcacionHhppVirtualRequestDto.setCodigoRazon(filtro.getCodigoR1());
            consultaMarcacionHhppVirtualRequestDto.setDescripcionRazon(filtro.getDescripcionR1());
            consultaMarcacionHhppVirtualRequestDto.setCodigoSubRazon(filtro.getCodigoR2());
            consultaMarcacionHhppVirtualRequestDto.setDescripcionSubRazon(filtro.getDescripcionR2());
            /* Valida el valor de AplicaHhpp para convertir de boolean al int que requiere el SP*/
            if (filtro.getAplicaHhppVt() != null) {
                consultaMarcacionHhppVirtualRequestDto.setAplicaHhppVirtual(Boolean.TRUE.equals(filtro.getAplicaHhppVt()) ? 1 : 0);
            } else {
                consultaMarcacionHhppVirtualRequestDto.setAplicaHhppVirtual(null);
            }

            consultaMarcacionHhppVirtualRequestDto.setEstadoRegistro(filtro.getEstadoRegistro());
            StoredProcedureUtil procedureUtil = new StoredProcedureUtil(StoredProcedureNamesConstants.CMT_CONS_MARCACIONES_HHPP_SP);
            procedureUtil.addRequestData(consultaMarcacionHhppVirtualRequestDto);
            ConsultaMarcacionHhppVirtualResponseDto response = procedureUtil.executeStoredProcedure(ConsultaMarcacionHhppVirtualResponseDto.class);

            List<CmtMarcacionesHhppVtDto> marcacionesBdList = response.getMarcaciones().parallelStream().map(marcacion -> {
                CmtMarcacionesHhppVtDto hhppVtDto = new CmtMarcacionesHhppVtDto();
                hhppVtDto.setMarcacionId(marcacion.getMarcacionId());
                hhppVtDto.setCodigoR1(marcacion.getCodigoR1());
                hhppVtDto.setDescripcionR1(marcacion.getDescripcionR1());
                hhppVtDto.setCodigoR2(marcacion.getCodigoR2());
                hhppVtDto.setDescripcionR2(marcacion.getDescripcionR2());
                hhppVtDto.setAplicaHhppVt(marcacion.getAplicaHhppVt() == 1);
                hhppVtDto.setFechaCreacion(marcacion.getFechaCreacion());
                hhppVtDto.setUsuarioCreacion(marcacion.getUsuarioCreacion());
                hhppVtDto.setPerfilCreacion(marcacion.getPerfilCreacion());
                hhppVtDto.setFechaEdicion(marcacion.getFechaEdicion());
                hhppVtDto.setUsuarioEdicion(marcacion.getUsuarioEdicion());
                hhppVtDto.setPerfilEdicion(marcacion.getPerfilEdicion());
                hhppVtDto.setEstadoRegistro(marcacion.getEstadoRegistro());
                hhppVtDto.setEstadoMarcacion(marcacion.getEstadoMarcacion());
                hhppVtDto.setFechaSincronizacion(marcacion.getFechaSincronizacion());
                return hhppVtDto;
            }).collect(Collectors.toList());

            /*captura la fecha más reciente de sincronización con RR */
            lastDateSyncro = Objects.requireNonNull(marcacionesBdList.stream()
                    .map(CmtMarcacionesHhppVtDto::getFechaSincronizacion)
                    .map(LocalDateTime::toLocalDate)
                    .max(LocalDate::compareTo).orElse(null)).toString();

            return marcacionesBdList;
        } catch (NoResultException ex) {
            String msgError = "No se obtuvo resultados en el Procedimiento almacenado CMT_CONS_MARCACIONES_HHPP_SP "
                    + ClassUtils.getCurrentMethodName(this.getClass());
            LOGGER.error(msgError, ex.getMessage());
            throw new ApplicationException("No hay resultados", ex);

        } catch (Exception e) {
            String msgError = "Error en el Procedimiento almacenado CMT_CONS_MARCACIONES_HHPP_SP "
                    + ClassUtils.getCurrentMethodName(this.getClass());
            LOGGER.error(msgError, e.getMessage());
            throw new ApplicationException(msgError, e);
        }
    }

    /**
     * Almacena los nuevos registros de marcaciones usando el procedimiento
     * almacenado.
     *
     * @param marcaciones Mapa de cadenas (separador punto y coma) de lista de marcaciones que se van a registrar
     * @param usuario     Nombre de usuario actual conectado en la sesión de la aplicación
     * @param perfil      Número de perfil del usuario actual conectado en la sesión
     * @author Gildardo Mora
     */
    private void registrarMarcacionesHhppSp(Map<String, String> marcaciones, String usuario, int perfil) {
        try {
            //Asignación de datos requeridos al request del procedimiento.
            RegistrarMarcacionHhppVirtualRequestDto requestDto = new RegistrarMarcacionHhppVirtualRequestDto();
            requestDto.setCodigoR1(marcaciones.get(CADENA_COD_R_1));
            requestDto.setDescripcionR1(marcaciones.get(CADENA_DESC_R1));
            requestDto.setCodigoR2(marcaciones.get(CADENA_COD_R_2));
            requestDto.setDescripcionR2(marcaciones.get(CADENA_DESC_R2));
            requestDto.setAplicaHhppVirtual( marcaciones.get(CADENA_APLICA_HHPP));
            requestDto.setEstadoMarcacion(marcaciones.get(CADENA_ESTADO_MARCACION));
            requestDto.setFechaSincronizacion(LocalDateTime.now());
            requestDto.setUsuarioCreacion(usuario);
            requestDto.setPerfilCreacion(perfil);
            // Ejecución del procedimiento
            StoredProcedureUtil procedureUtil = new StoredProcedureUtil(StoredProcedureNamesConstants.CMT_INS_MARCACIONES_HHPP_SP);
            procedureUtil.addRequestData(requestDto);
            RegistrarMarcacionHhppVirtualResponseDto responseDto = procedureUtil.executeStoredProcedure(RegistrarMarcacionHhppVirtualResponseDto.class);

            if (!responseDto.getResultado().equals("PROCESO CORRECTO")) {
                String msgError = "Error en Procedimiento almacenado CMT_INS_MARCACIONES_HHPP_SP " + responseDto.getResultado();
                LOGGER.error(msgError);
                throw new ApplicationException(msgError);
            }

        } catch (ApplicationException ex) {
            String msgError = "Error en ejecución de Procedimiento almacenado CMT_INS_MARCACIONES_HHPP_SP";
            LOGGER.error(msgError, ex);

        } catch (Exception e) {
            String msgError = "Error en el Procedimiento almacenado CMT_INS_MARCACIONES_HHPP_SP";
            LOGGER.error(msgError, e);
        }
    }

    /**
     * Realiza la actualización de los registros de marcaciones para creacion de
     * HHPP virtual, a través del procedimiento almacenado.
     *
     * @param marcaciones Lista de marcaciones que se van a actualizar
     * @param usuario     Nombre de usuario actual conectado en la sesión de la aplicación
     * @param perfil      Número de perfil del usuario actual conectado en la sesión
     * @param sincronizar {@code boolean} Indica si se trata de una sincronización de marcaciones con RR
     * @throws ApplicationException Excepción personalizada de la App
     * @author Gildardo Mora
     */
    private void actualizarMarcacionesHhppSp(Map<String, String> marcaciones, String usuario, int perfil, boolean sincronizar)
            throws ApplicationException {
        try {
            ActualizaMarcacionHhppVirtualRequestDto requestDto = new ActualizaMarcacionHhppVirtualRequestDto();
            requestDto.setMarcacionHhppVtId(marcaciones.get(CADENA_MARCACION_ID));
            requestDto.setUsuarioEdicion(usuario);
            requestDto.setPerfilEdicion(perfil);
            requestDto.setEstadoRegistro(marcaciones.get(CADENA_ESTADO_REG));
            requestDto.setAplicaHhppVirtual(marcaciones.get(CADENA_APLICA_HHPP));

            if (sincronizar) { //Cuando se trata de una sincronización con RR
                requestDto.setFechaSincronizacion(LocalDateTime.now());
                requestDto.setEstadoMarcacion(marcaciones.get(CADENA_ESTADO_MARCACION));
                requestDto.setDescripcionR1(marcaciones.get(CADENA_DESC_R1));
                requestDto.setDescripcionR2(marcaciones.get(CADENA_DESC_R2));
            } else {
                //Cuando se hace una actualización de las marcaciones desde el front
                requestDto.setFechaSincronizacion(null);
                requestDto.setEstadoMarcacion(null);
                requestDto.setDescripcionR1(null);
                requestDto.setDescripcionR2(null);
            }

            StoredProcedureUtil procedureUtil = new StoredProcedureUtil(StoredProcedureNamesConstants.CMT_ACT_MARCACIONES_HHPP_SP);
            procedureUtil.addRequestData(requestDto);
            ActualizaMarcacionHhppVirtualResponseDto responseDto = procedureUtil.executeStoredProcedure(ActualizaMarcacionHhppVirtualResponseDto.class);

            if (!responseDto.getResultado().equals("PROCESO CORRECTO")) {
                String msgError = "Ocurrió un error en: " + ClassUtils.getCurrentMethodName(this.getClass()) + " : " + responseDto.getResultado();
                LOGGER.error(msgError);
                throw new ApplicationException("App Exc: " + responseDto.getResultado());
            }

        } catch (ApplicationException ex) {
            String msgError = "Ocurrió un error: " + ex.getMessage();
            LOGGER.error(msgError);
            throw new ApplicationException(msgError, ex);

        } catch (Exception e) {
            String msgError = "Ocurrió error al actualizar en : "  + ClassUtils.getCurrentMethodName(this.getClass());
            LOGGER.error(msgError, e.getMessage());
            throw new ApplicationException(msgError, e);
        }
    }
}
