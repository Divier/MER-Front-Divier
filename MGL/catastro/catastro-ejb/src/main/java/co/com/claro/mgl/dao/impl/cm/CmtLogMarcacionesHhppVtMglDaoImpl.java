package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mer.constants.StoredProcedureNamesConstants;
import co.com.claro.mer.dtos.request.procedure.ConsultarLogMarcacionHhppRequestDto;
import co.com.claro.mer.dtos.request.procedure.RegistrarLogMarcacionHhppVirtualRequestDto;
import co.com.claro.mer.dtos.response.procedure.ConsultarLogMarcacionHhppResponseDto;
import co.com.claro.mer.dtos.response.procedure.RegistrarLogMarcacionHhppVirtualResponseDto;
import co.com.claro.mer.utils.DateToolUtils;
import co.com.claro.mer.utils.StoredProcedureUtil;
import co.com.claro.mgl.dtos.CmtLogMarcacionesHhppVtDto;
import co.com.claro.mgl.dtos.FiltroConsultaLogMarcacionesHhppVtDto;
import co.com.claro.mgl.dtos.FiltroLogMarcacionesHhppVtDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.telmex.catastro.services.util.Constant;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static co.com.claro.mer.constants.StoredProcedureNamesConstants.CMT_INS_LOGS_MARC_HHPP_SP;

/**
 * Implementación de lógica asociada al crud y acciones específicas aplicadas.
 * Sobre los logs de marcaciones para creación de HHPP virtuales.
 *
 * @author Gildardo Mora
 * @version 1.1, 2021/12/23 Rev Gildardo Mora
 * @since 1.0, 2021/09/29
 */
public class CmtLogMarcacionesHhppVtMglDaoImpl {

    private static final Logger LOGGER = LogManager.getLogger(CmtLogMarcacionesHhppVtMglDaoImpl.class);
    /* Nombre de listas para el Map*/
    private static final String LISTA_VALOR_ANTERIOR = "listaValorAnterior";
    private static final String CADENA_MARCACION_ID = "cadenaMarcacionId";
    private static final String CADENA_VALOR_ANTERIOR = "cadenaValorAnterior";
    private static final String CADENA_VALOR_NUEVO = "cadenaValorNuevo";
    private static final String LISTA_MARCACION_ID = "listaMarcacionId";
    private static final String LISTA_VALOR_NUEVO = "listaValorNuevo";

    /**
     * consulta el historial de si aplica o no marcación para creación de HHPP Virtual.
     *
     * @param filtro      Parámetros de filtro para aplicar en la consulta de logs de marcaciones
     * @param firstResult Indica el inicio del rango de la lista de resultados en proceso consulta para paginación
     * @param maxResults  Indica el fin del rango de la lista de resultados en proceso consulta para paginación
     * @return filtroConsultaLogMarcacionesHhppVtDto Retorna la lista de logs marcaciones
     * @throws ApplicationException Excepción personalizada
     */
    public FiltroConsultaLogMarcacionesHhppVtDto findTablasLogMarcacionesHhppVtSearch(
            FiltroLogMarcacionesHhppVtDto filtro, int firstResult, int maxResults) throws ApplicationException {

        FiltroConsultaLogMarcacionesHhppVtDto filtroConsultaLogMarcacionesHhppVtDto = new FiltroConsultaLogMarcacionesHhppVtDto();
        List<CmtLogMarcacionesHhppVtDto> logMarcacionesHhppVtDtoList = consultarLogsMarcacionHhppSp(filtro);

        filtroConsultaLogMarcacionesHhppVtDto.setListaTablasLogMarcacionesHhppVt(
                findLogMarcacionesHhppVtSearchDatos(firstResult, maxResults,logMarcacionesHhppVtDtoList ));
        int numeroRegistros = logMarcacionesHhppVtDtoList.size();
        filtroConsultaLogMarcacionesHhppVtDto.setNumRegistros(numeroRegistros);

        return filtroConsultaLogMarcacionesHhppVtDto;
    }

    /**
     * Particiona los registros de log de Marcaciones, para apoyar la paginación
     *
     * @param firstResult                 Inicio del rango de la lista de logs a mostrar
     * @param maxResults                  Fin del rango de la lista de datos de logs a mostrar
     * @param logMarcacionesHhppVtDtoList {@link List<CmtLogMarcacionesHhppVtDto>}
     * @return logsMarcacionList Retorna los log de marcaciones encontrados según el filtro aplicado.
     * @throws ApplicationException Excepción personalizada
     * @author Gildardo Mora
     */
    public List<CmtLogMarcacionesHhppVtDto> findLogMarcacionesHhppVtSearchDatos(
             int firstResult, int maxResults, List<CmtLogMarcacionesHhppVtDto> logMarcacionesHhppVtDtoList) throws ApplicationException {

        if (Objects.isNull(logMarcacionesHhppVtDtoList)) {
            String msgError = "Se presentó un error al consultar los registros de log para la marcación de HHPP en "
                    + ClassUtils.getCurrentMethodName(this.getClass()) + " el valor de la lista es nulo.";
            LOGGER.error(msgError);
            throw new ApplicationException(msgError);
        }

        if (maxResults > 0){
            return logMarcacionesHhppVtDtoList.stream().skip(firstResult).limit(maxResults).collect(Collectors.toList());
        }

        return logMarcacionesHhppVtDtoList;
    }

    /**
     * Crear el Log de marcaciones
     *
     * @param logMarcacionesList Lista de log de marcaciones a crear
     * @param usuario            Usuario conectado en la sesión de la aplicación.
     * @param perfil             Número del perfil del usuario conectado en la sesión de la aplicación
     * @return {@link Boolean} Retorna true cuando se realiza el registro de marcaciones.
     */
    public Boolean createLogMarcacionesHhppVt(List<CmtLogMarcacionesHhppVtDto> logMarcacionesList, String usuario, int perfil) throws ApplicationException {

        try {
            List<CmtLogMarcacionesHhppVtDto> logsMarcacionesList = prepareInfoCreateLogMarcaciones(logMarcacionesList, usuario, perfil);
            Map<String, List<String>> listasLogsMarcaciones = new HashMap<>(); //almacena las listas de datos de logs a convertir a cadenas
            Map<String, String> cadenasLogsMarcaciones = new HashMap<>(); //almacena las cadenas con elementos separados por punto y coma generadas
            //Apoyan al proceso de validación de cumplimiento de límite de cadena
            int countSeparadores = 0;
            int countCaracteres;

            /*asignar los valores a las listas de Strings requeridos*/
            for (CmtLogMarcacionesHhppVtDto logMarcacion : logsMarcacionesList) {
                countSeparadores++; // incrementa para suponer el carácter equivalente a cada separador (punto y coma)
                countCaracteres = listasLogsMarcaciones.size() == 0 ? 0 : listasLogsMarcaciones.get(LISTA_VALOR_ANTERIOR).toString().length();

                /* valida si se encuentra dentro del límite de la cadena que se puede crear */
                if ((countCaracteres + logMarcacion.getUsuarioCreacionLog().length()
                        + countSeparadores) < Constant.LIMITE_CARACTERES_ENTRADA_SP) {
                    /* agregar elementos a las listas requeridas */
                    convertLogMarcacionCreate(listasLogsMarcaciones, logMarcacion);
                    continue;
                }
                //si no cumple con el límite de cadena, guarda las marcaciones de la cadena formada, y forma una nueva cadena
                cadenasLogsMarcaciones.putAll(generarCadenasLogMarcaciones(listasLogsMarcaciones));
                registrarLogMarcacionesHhppSp(cadenasLogsMarcaciones, usuario, perfil); // registra los log de marcaciones procesados
                //vaciar listas tras haber registrado las marcaciones procesadas
                cadenasLogsMarcaciones.clear();
                listasLogsMarcaciones.clear();
                //pasar la marcación actual que no fue procesada
                convertLogMarcacionCreate(listasLogsMarcaciones, logMarcacion);
            }

            cadenasLogsMarcaciones.putAll(generarCadenasLogMarcaciones(listasLogsMarcaciones));
            return registrarLogMarcacionesHhppSp(cadenasLogsMarcaciones, usuario, perfil); //registra los logs de marcaciones procesados

        } catch (Exception e) {
            String msgError = "Error al crear Log de Marcaciones en : " + ClassUtils.getCurrentMethodName(this.getClass());
            LOGGER.error(msgError, e);
            throw new ApplicationException(e);
        }
    }

    /**
     * Se encarga de asignar los logs de marcaciones a listas del map
     *
     * @param listasLogsMarcaciones Map en el que se procesará las listas de elementos de logs marcacion
     * @param log                   Log de marcación que será procesada en las listas de los elementos
     */
    private void convertLogMarcacionCreate(Map<String, List<String>> listasLogsMarcaciones, CmtLogMarcacionesHhppVtDto log) {
        listasLogsMarcaciones.computeIfAbsent(LISTA_MARCACION_ID, k -> new ArrayList<>()).add(String.valueOf(log.getIdMarcacion()));
        listasLogsMarcaciones.computeIfAbsent(LISTA_VALOR_ANTERIOR, k -> new ArrayList<>()).add(log.getValorAntLogMarcaHHPP());
        listasLogsMarcaciones.computeIfAbsent(LISTA_VALOR_NUEVO, k -> new ArrayList<>()).add(log.getValorNuevoLogMarcaHHPP());
    }

    /**
     * Preparar datos de la lista de log de marcaciones agregando la información inicial requerida como usuario, perfil etc.
     *
     * @param logMarcacionesList Lista de log marcaciones para agregarle datos iniciales requeridos.
     * @param usuario            Nombre de usuario conectado en la sesión
     * @param perfil             Número de perfil del usuario conectado en la sesión
     * @return marcacionesCadena Devuelve la lista de marcaciones con los datos iniciales requeridos
     */
    private List<CmtLogMarcacionesHhppVtDto> prepareInfoCreateLogMarcaciones(List<CmtLogMarcacionesHhppVtDto> logMarcacionesList,
            String usuario, int perfil) {

        /* Se agrega información requerida a la lista de marcaciones */
        logMarcacionesList.forEach(logMarcacion -> {
            logMarcacion.setUsuarioCreacionLog(usuario);
            logMarcacion.setPerfilCreacion(perfil);
            logMarcacion.setFechaCreacionLogMarcaHHPP(LocalDateTime.now());
            logMarcacion.setEstadoRegistro(1);
        });
        return logMarcacionesList;
    }

    /**
     * Se encarga de transformar las listas recibidas de log marcaciones, en cadenas separadas por punto y coma
     * para que pueda ser procesada por el procedimiento almacenado
     *
     * @param cadenaLogMarcaciones Mapa con las listas de marcaciones
     * @return cadenasGeneradas Retorna el mapa con las cadenas generadas
     */
    private Map<String, String> generarCadenasLogMarcaciones(Map<String, List<String>> cadenaLogMarcaciones) {
        Map<String, String> cadenasGeneradas = new HashMap<>();
        cadenasGeneradas.put(CADENA_MARCACION_ID, String.join(";", cadenaLogMarcaciones.get(LISTA_MARCACION_ID)));// cadena string con separador punto y coma.
        cadenasGeneradas.put(CADENA_VALOR_ANTERIOR, String.join(";", cadenaLogMarcaciones.get(LISTA_VALOR_ANTERIOR)));
        cadenasGeneradas.put(CADENA_VALOR_NUEVO, String.join(";", cadenaLogMarcaciones.get(LISTA_VALOR_NUEVO)));
        return cadenasGeneradas;
    }

/*
***************************************************************************************************
 Ejecución de procedimientos almacenados
***************************************************************************************************
 */

    /**
     * Consulta el log de la marcación para creación de hhpp virtual, a través del SP.
     *
     * @param filtro Términos usados como filtro para la búsqueda de logs de marcaciones
     * @return logsMarcacionBdList, lista de datos de log de marcaciones encontrados en BD.
     * @author Gildardo Mora
     */
    public List<CmtLogMarcacionesHhppVtDto> consultarLogsMarcacionHhppSp(FiltroLogMarcacionesHhppVtDto filtro) throws ApplicationException {
        try {
            //Asignación de datos al request del procedimiento.
            ConsultarLogMarcacionHhppRequestDto requestDto = new ConsultarLogMarcacionHhppRequestDto();
            requestDto.setLogMarcacionHhppVtId(filtro.getIdLogMarcaHHPP());
            requestDto.setMarcacionId(filtro.getIdMarcacion());
            requestDto.setFechaCreacion(Objects.nonNull(filtro.getFechaCreacionLogMarcaHHPP()) ?
                    DateToolUtils.convertDateToLocalDateTime(filtro.getFechaCreacionLogMarcaHHPP()) : null);
            requestDto.setValorAnteriorAplica(filtro.getValorAntLogMarcaHHPP());
            requestDto.setValorNuevoAplica(filtro.getValorNuevoLogMarcaHHPP());
            requestDto.setEstadoRegistro("1"); //Busca los registros activos
            requestDto.setUsuarioCreacion(filtro.getUsuarioCreacionLogMarcaHHPP());
            //Ejecución del procedimiento
            StoredProcedureUtil procedureUtil = new StoredProcedureUtil(StoredProcedureNamesConstants.CMT_CONS_LOGS_MARC_HHPP_SP);
            procedureUtil.addRequestData(requestDto);
            ConsultarLogMarcacionHhppResponseDto responseDto = procedureUtil.executeStoredProcedure(ConsultarLogMarcacionHhppResponseDto.class);
            return responseDto.getLogMarcacionesList();

        } catch (ApplicationException ex) {
            String msgError = "Error no obtuvo resultados en el Procedimiento almacenado CMT_CONS_LOGS_MARC_HHPP_SP" + ex.getMessage();
            LOGGER.error(msgError);
            throw ex;

        } catch (Exception e) {
            String msgError = "Error en " + ClassUtils.getCurrentMethodName(this.getClass());
            LOGGER.error(msgError, e);
            throw new ApplicationException(msgError, e);
        }
    }

    /**
     * Registra el log de las marcaciones para creaci&oacute;n de HHPP virtual, a trav&eacute;s del SP
     *
     * @param logMarcaciones Mapa de cadenas de log marcaciones a registrar
     * @param usuario        Nombre de usuario conectado en la sesión
     * @param perfil         Número del perfil del usuario conectado en la sesión
     * @return {@link Boolean} Retorna true si se realizó el registro de la marcación.
     */
    private Boolean registrarLogMarcacionesHhppSp(Map<String, String> logMarcaciones, String usuario, int perfil) throws ApplicationException {
        try {
            //Asignación de datos al request del procedimiento.
            RegistrarLogMarcacionHhppVirtualRequestDto requestDto = new RegistrarLogMarcacionHhppVirtualRequestDto();
            requestDto.setIdMarcacion(logMarcaciones.get(CADENA_MARCACION_ID));
            requestDto.setValorAnteriorAplica(logMarcaciones.get(CADENA_VALOR_ANTERIOR));
            requestDto.setValorNuevoAplica(logMarcaciones.get(CADENA_VALOR_NUEVO));
            requestDto.setUsuarioCreacion(usuario);
            requestDto.setPerfilCreacion(perfil);
            //Ejecución del procedimiento
            StoredProcedureUtil procedureUtil = new StoredProcedureUtil(CMT_INS_LOGS_MARC_HHPP_SP);
            procedureUtil.addRequestData(requestDto);
            RegistrarLogMarcacionHhppVirtualResponseDto responseDto = procedureUtil.executeStoredProcedure(RegistrarLogMarcacionHhppVirtualResponseDto.class);

            if (StringUtils.isBlank(responseDto.getResultado())) {
                return false;
            }

            if (!responseDto.getResultado().equals("PROCESO CORRECTO")) {
                String msgError = "Error en: Procedimiento " + CMT_INS_LOGS_MARC_HHPP_SP + " " + responseDto.getResultado();
                LOGGER.error(msgError);
                throw new ApplicationException("App Exc: " + responseDto.getResultado());
            }

            return true;

        } catch (ApplicationException ae) {
            String msgError = "Error en la ejecución de: " + ClassUtils.getCurrentMethodName(this.getClass()) + ae.getMessage();
            LOGGER.error(msgError);
            throw ae;

        } catch (Exception e) {
            String msgError = "Ocurrió un error: " + e.getMessage() + " : " + ClassUtils.getCurrentMethodName(this.getClass());
            LOGGER.error(msgError);
            throw new ApplicationException(msgError, e);
        }
    }

}
