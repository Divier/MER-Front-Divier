package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mer.utils.enums.ParametrosMerEnum;
import co.com.claro.mer.utils.funtional.QuerySqlStatement;
import co.com.claro.mer.utils.constants.OrdenTrabajoConstants;
import co.com.claro.mgl.businessmanager.cm.CmtBasicaMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtEstadoIntxExtMglManager;
import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.dtos.CmtFiltroConsultaOrdenesDto;
import co.com.claro.mgl.dtos.ReporteHistoricoOtCmDto;
import co.com.claro.mgl.dtos.ReporteOtCMDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.*;
import co.com.claro.mgl.jpa.entities.cm.*;
import co.com.claro.mgl.utils.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.stream.IntStream;

/**
 * Dao Orden de Trabajo. Contiene la logica de acceso a datos ordenes de trabajo
 * en el repositorio.
 *
 * @author Johnnatan Ortiz
 * @version 1.00.000
 */
public class CmtOrdenTrabajoMglDaoImpl extends GenericDaoImpl<CmtOrdenTrabajoMgl> {

    private static final Logger LOGGER = LogManager.getLogger(CmtOrdenTrabajoMglDaoImpl.class);
    public static final String REFRESH = "REFRESH";
    public static final String SE_PRODUJO_UN_ERROR_AL_MOMENTO_DE_EJECUTAR_EL_METODO = "Se produjo un error al momento de ejecutar el método '";
    public static final String JAVAX_PERSISTENCE_CACHE_STORE_MODE = "javax.persistence.cache.storeMode";
    final int MILISEGUNDOS_DIA = 86400000;
    private final int LIMITE_INPUT_CONSULTA = 1000;


    private static final Predicate<BigDecimal> esValido = bd -> bd != null && !bd.equals(BigDecimal.ZERO);
    private static final Predicate<String> noEstaVacio = org.apache.commons.lang3.StringUtils::isNotBlank;
    private static final Predicate<Collection<?>> tieneElementos = CollectionUtils::isNotEmpty;

    /**
     * Busca las Ordenes de Trabajo asociadas a una CM. Permite realizar la
     * busqueda de las Ordenes de Trabajo asociadas a una Cuenta Matriz en el
     * repositorio por el ID de la Cuenta Matriz.
     *
     * @author Johnnatan Ortiz
     * @param cuentaMatrizMgl ID de la Cuenta Matriz
     * @return Ordenes de Trabajo encontrada en el repositorio asociadas a una
     * Cuenta Matriz
     * @throws ApplicationException
     */
    public List<CmtOrdenTrabajoMgl> findByIdCm(CmtCuentaMatrizMgl cuentaMatrizMgl) throws ApplicationException {
        try {
            Query query = entityManager.createNamedQuery("CmtOrdenTrabajoMgl.findByIdCm");
            query.setParameter("cmObj", cuentaMatrizMgl);
            query.setHint(JAVAX_PERSISTENCE_CACHE_STORE_MODE, REFRESH);
            List<CmtOrdenTrabajoMgl> result = query.getResultList();
            getEntityManager().clear();
            return result;
        } catch (Exception e) {
            String msg = SE_PRODUJO_UN_ERROR_AL_MOMENTO_DE_EJECUTAR_EL_METODO + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Busca las Ordenes de Trabajo por estados X flujo.Permite realizar la
     * busqueda de las Ordenes de Trabajo por medio de los estado X flujo,
     * comunidad, division y tipo.
     *
     * @author Johnnatan Ortiz
     * @param firstResult pagina de la busqueda
     * @param maxResults maximo numero de resultados
     * @param estadosxflujoList lista de estado X flujo
     * @param filtro filtro para la consulta
     * @return Ordenes de Trabajo encontrada en el repositorio
     * @throws ApplicationException
     */
    public List<CmtOrdenTrabajoMgl> findByFiltroParaGestionPaginacion(int firstResult,
            int maxResults, List<BigDecimal> estadosxflujoList,
            CmtFiltroConsultaOrdenesDto filtro) throws ApplicationException {

        try {
            String flagEstadosXflujo = ParametrosMerUtil.findValor(ParametrosMerEnum.FLAG_USAR_ESTADOS_FLUJO_OT);
            boolean usarEstadosXflujo = org.apache.commons.lang3.StringUtils.isNotBlank(flagEstadosXflujo) && flagEstadosXflujo.trim().equals("1");

            StringBuilder queryStr = new StringBuilder()
                    .append(OrdenTrabajoConstants.SELECT_DISTINCT_O_FROM_CMT_ORDEN_TRABAJO_MGL_O)
                    .append(OrdenTrabajoConstants.CMT_ESTADOX_FLUJO_MGL_EF_CMT_CUENTA_MATRIZ_MGL_CM)
                    .append(OrdenTrabajoConstants.WHERE_T_ID_TIPO_OT_O_TIPO_OT_OBJ_ID_TIPO_OT)
                    .append(OrdenTrabajoConstants.AND_EF_TIPO_FLUJO_OT_OBJ_BASICA_ID_T_TIPO_FLUJO_OT_BASICA_ID)
                    .append(OrdenTrabajoConstants.AND_EF_BASICA_TECNOLOGIA_BASICA_ID_O_BASICA_ID_TECNOLOGIA_BASICA_ID)
                    .append(OrdenTrabajoConstants.AND_O_ESTADO_INTERNO_OBJ_BASICA_ID_EF_ESTADO_INTERNO_OBJ_BASICA_ID)
                    .append(OrdenTrabajoConstants.MATRIZ_ID_O_CM_OBJ_CUENTA_MATRIZ_ID)
                    .append(OrdenTrabajoConstants.AND_O_ESTADO_REGISTRO_ESTADO_REGISTRO)
                    .append(" AND ef.estadoRegistro = :estadoxF ")
                    .append(crearQuery(estadosxflujoList, filtro, usarEstadosXflujo));

            TypedQuery<CmtOrdenTrabajoMgl> query =  entityManager.createQuery(queryStr.toString(), CmtOrdenTrabajoMgl.class);
            query.setFirstResult(firstResult);
            query.setMaxResults(maxResults);

            crearParametros(estadosxflujoList, filtro, query, usarEstadosXflujo);

            query.setHint(JAVAX_PERSISTENCE_CACHE_STORE_MODE, REFRESH);

            return query.getResultList();
        } catch (Exception e) {
            String msg = SE_PRODUJO_UN_ERROR_AL_MOMENTO_DE_EJECUTAR_EL_METODO + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }finally {
            getEntityManager().clear();
        }
    }

    private void crearParametros(List<BigDecimal> estadosxflujoList, CmtFiltroConsultaOrdenesDto filtro, Query query, boolean usarEstadosXflujo) {
        query.setParameter(OrdenTrabajoConstants.ESTADO_REGISTRO_PARAMETER, 1);
        query.setParameter("estadoxF", 1);

        // Función para establecer parámetros de texto con formato LIKE
        BiConsumer<String, String> setParamTexto = (nombre, valor) -> {
            if (org.apache.commons.lang3.StringUtils.isNotBlank(valor)) {
                query.setParameter(nombre, "%" + valor.trim().toUpperCase() + "%");
            }
        };

        Optional.ofNullable(filtro.getFechaIngresoSeleccionada())
                .ifPresent(fecha -> {
                    query.setParameter(OrdenTrabajoConstants.FECHA_CREACION_INICIAL_PARAMETER, fecha);
                    query.setParameter(OrdenTrabajoConstants.FECHA_CREACION_FINAL_PARAMETER,
                            new Date(fecha.getTime() + MILISEGUNDOS_DIA - 1));
                });

        Optional.ofNullable(filtro.getIdOtSelecionada())
                .filter(esValido)
                .ifPresent(idOt -> query.setParameter("idOt", idOt));

        Optional.ofNullable(filtro.getCcmmRrSelecionada())
                .filter(esValido)
                .ifPresent(numeroCuenta -> query.setParameter(OrdenTrabajoConstants.NUMERO_CUENTA_PARAMETER, numeroCuenta));

        setParamTexto.accept(OrdenTrabajoConstants.DESC_TIPO_OT_PARAMETER, filtro.getTipoOtSelecionada());
        setParamTexto.accept(OrdenTrabajoConstants.DEPARTAMENTO_PARAMETER, filtro.getDptoSelecionado());
        setParamTexto.accept(OrdenTrabajoConstants.MUNICIPIO_PARAMETER, filtro.getCiudadSelecionada());
        setParamTexto.accept(OrdenTrabajoConstants.TECNOLOGIA_PARAMETER, filtro.getTecnoSelecionada());
        setParamTexto.accept(OrdenTrabajoConstants.ESTADO_PARAMETER, filtro.getEstIntSelecionada());

        Optional.ofNullable(filtro.getCcmmMglSelecionada())
                .filter(esValido)
                .ifPresent(cuentaMatrizId -> query.setParameter("cuentaMatrizId", cuentaMatrizId));

        setParamTexto.accept("divicion", filtro.getRegionalSeleccionada());

        setParamTexto.accept("disponibilidadGestion", filtro.getUserBloqueo());

        // Parámetro SLA
        Optional.of(filtro.getSlaSeleccionada())
                .filter(sla -> sla > 0)
                .ifPresent(sla -> query.setParameter("ans", sla));

        if (CollectionUtils.isNotEmpty(estadosxflujoList) && usarEstadosXflujo) {
            // Se asignan los parámetros de los estadosxflujo en bloques
            // para evitar problemas de longitud de consulta usando la cláusula IN
            for (int i = 0; i < estadosxflujoList.size(); i += LIMITE_INPUT_CONSULTA) {
                int finBloque = Math.min(i + LIMITE_INPUT_CONSULTA, estadosxflujoList.size());
                String paramName =  OrdenTrabajoConstants.ESTADOSXFLUJO_LIST_PARAMETER + (i > 0 ? i / LIMITE_INPUT_CONSULTA : "");
                query.setParameter(paramName, estadosxflujoList.subList(i, finBloque));
            }
        }
    }

    private StringBuilder crearQuery(List<BigDecimal> estadosxflujoList, CmtFiltroConsultaOrdenesDto filtro, boolean usarEstadosXflujo) {
        StringBuilder queryStr = new StringBuilder();
        QuerySqlStatement agregarCondicion = QuerySqlStatement.addStatement();

        agregarCondicion.accept(Objects.nonNull(filtro.getFechaIngresoSeleccionada()),
                "AND o.fechaCreacion BETWEEN :fechaCreacionInicial AND :fechaCreacionFinal ",
                queryStr);

        agregarCondicion.accept( esValido.test(filtro.getIdOtSelecionada()), OrdenTrabajoConstants.AND_O_ID_OT_ID_OT,
                queryStr);

        agregarCondicion.accept(esValido.test(filtro.getCcmmRrSelecionada()),
                OrdenTrabajoConstants.AND_CM_NUMERO_CUENTA_NUMERO_CUENTA,
                queryStr);

        agregarCondicion.accept(noEstaVacio.test(filtro.getTipoOtSelecionada()),
                OrdenTrabajoConstants.AND_UPPER_O_TIPO_OT_OBJ_DESC_TIPO_OT_LIKE_DESC_TIPO_OT,
                queryStr);

        agregarCondicion.accept(noEstaVacio.test(filtro.getDptoSelecionado()),
                OrdenTrabajoConstants.AND_UPPER_CM_DEPARTAMENTO_GPO_NOMBRE_LIKE_DEPARTAMENTO,
                queryStr);

        agregarCondicion.accept(noEstaVacio.test(filtro.getCiudadSelecionada()),
                OrdenTrabajoConstants.AND_UPPER_CM_MUNICIPIO_GPO_NOMBRE_LIKE_MUNICIPIO,
                queryStr);

        agregarCondicion.accept(noEstaVacio.test(filtro.getTecnoSelecionada()),
                OrdenTrabajoConstants.AND_UPPER_O_BASICA_ID_TECNOLOGIA_NOMBRE_BASICA_LIKE_TECNOLOGIA,
                queryStr);

        agregarCondicion.accept(noEstaVacio.test(filtro.getEstIntSelecionada()),
                OrdenTrabajoConstants.AND_UPPER_O_ESTADO_INTERNO_OBJ_NOMBRE_BASICA_LIKE_ESTADO,
                queryStr);

        agregarCondicion.accept(esValido.test(filtro.getCcmmMglSelecionada()),
                " AND cm.cuentaMatrizId = :cuentaMatrizId ",
                queryStr);

        agregarCondicion.accept(noEstaVacio.test(filtro.getRegionalSeleccionada()),
                " AND UPPER(o.cmObj.division) LIKE :divicion",
                queryStr);

        agregarCondicion.accept(noEstaVacio.test(filtro.getUserBloqueo()),
                " AND UPPER(o.disponibilidadGestion) LIKE :disponibilidadGestion",
                queryStr);

        agregarCondicion.accept(filtro.getSlaSeleccionada() > 0,
                OrdenTrabajoConstants.AND_O_TIPO_OT_OBJ_ANS_ANS,
                queryStr);

        if (tieneElementos.test(estadosxflujoList) && usarEstadosXflujo) {
            // Se asignan los parámetros de los estadosxflujo en bloques para evitar problemas de longitud de consulta usando la cláusula IN
            // Se usa un rango para dividir la lista en bloques y se asignan los parámetros correspondientes a cada bloque
            // Se usa AND y OR para combinar los bloques
            queryStr.append(" AND (");
            IntStream.range(0, (int) Math.ceil((double) estadosxflujoList.size() / LIMITE_INPUT_CONSULTA))
                    .forEach(idx -> {
                        if (idx > 0) {
                            queryStr.append(" OR ");
                        }
                        queryStr.append("ef.estadoxFlujoId IN :estadosxflujoList").append(idx == 0 ? "" : idx);
                    });

            queryStr.append(") ");
        }

        queryStr.append(" ORDER BY o.fechaCreacion DESC ");

        return queryStr;
    }

    /**
     * Cuenta las Ordenes de Trabajo por estados X flujo.Permite realizar el
     * conteo de las Ordenes de Trabajo por medio de los estado X flujo,
     * comunidad, division y tipo.
     *
     * @author Johnnatan Ortiz
     * @param estadosxflujoList lista de estado X flujo
     * @param filtro para la consulta
     * @return Ordenes de Trabajo encontrada en el repositorio
     * @throws ApplicationException
     */
    public int getCountByFiltroParaGestion(List<BigDecimal> estadosxflujoList,
            CmtFiltroConsultaOrdenesDto filtro) throws ApplicationException {

        try {
            String flagEstadosXflujo = ParametrosMerUtil.findValor(ParametrosMerEnum.FLAG_USAR_ESTADOS_FLUJO_OT);
            boolean usarEstadosXflujo = noEstaVacio.test(flagEstadosXflujo) && flagEstadosXflujo.trim().equals("1");

            StringBuilder queryStr = new StringBuilder()
                    .append("SELECT COUNT(Distinct o.idOt) FROM CmtOrdenTrabajoMgl o,")
                    .append(" CmtTipoOtMgl t, CmtEstadoxFlujoMgl ef, CmtCuentaMatrizMgl cm")
                    .append(OrdenTrabajoConstants.WHERE_T_ID_TIPO_OT_O_TIPO_OT_OBJ_ID_TIPO_OT)
                    .append(OrdenTrabajoConstants.AND_EF_TIPO_FLUJO_OT_OBJ_BASICA_ID_T_TIPO_FLUJO_OT_BASICA_ID)
                    .append(OrdenTrabajoConstants.AND_EF_BASICA_TECNOLOGIA_BASICA_ID_O_BASICA_ID_TECNOLOGIA_BASICA_ID)
                    .append(OrdenTrabajoConstants.AND_O_ESTADO_INTERNO_OBJ_BASICA_ID_EF_ESTADO_INTERNO_OBJ_BASICA_ID)
                    .append(OrdenTrabajoConstants.MATRIZ_ID_O_CM_OBJ_CUENTA_MATRIZ_ID)
                    .append(OrdenTrabajoConstants.AND_O_ESTADO_REGISTRO_ESTADO_REGISTRO)
                    .append( " AND ef.estadoRegistro = :estadoxF ")
                    .append(crearQuery(estadosxflujoList, filtro, usarEstadosXflujo));

            TypedQuery<Long> query = entityManager.createQuery(queryStr.toString(), Long.class);

            crearParametros(estadosxflujoList, filtro, query, usarEstadosXflujo);

            Long result = query.getSingleResult();
            return result != null ? result.intValue() : 0;

        } catch (Exception e) {
            String msg = SE_PRODUJO_UN_ERROR_AL_MOMENTO_DE_EJECUTAR_EL_METODO + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        } finally {
            getEntityManager().clear();
        }
    }

    /**
     * Busca las Ordenes de Trabajo por tipo. Permite realizar la busqueda de
     * las Ordenes de Trabajo de un mismo tipo.
     *
     * @author Johnnatan Ortiz
     * @param tipoOtMgl Tipo de Orde de trabajo
     * @return Ordenes de Trabajo encontrada en el repositorio asociadas a una
     * Cuenta Matriz
     * @throws ApplicationException
     */
    public List<CmtOrdenTrabajoMgl> findByTipoOt(CmtTipoOtMgl tipoOtMgl) throws ApplicationException {
        try {
            Query query = entityManager.createNamedQuery("CmtOrdenTrabajoMgl.findByTipoOt");
            query.setParameter("tipoOtObj", tipoOtMgl);
            query.setHint(JAVAX_PERSISTENCE_CACHE_STORE_MODE, REFRESH);
            List<CmtOrdenTrabajoMgl> result = query.getResultList();
            getEntityManager().clear();
            return result;
        } catch (Exception e) {
            String msg = SE_PRODUJO_UN_ERROR_AL_MOMENTO_DE_EJECUTAR_EL_METODO + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    public List<CmtOrdenTrabajoMgl> findPaginacion(CmtCuentaMatrizMgl cmtCuentaMatrizMgl) throws ApplicationException {
        try {
            Query q = entityManager.createQuery("SELECT o FROM CmtOrdenTrabajoMgl o "
                    + " WHERE  o.cmObj = :cmObj ORDER BY o.idOt DESC ");
            q.setParameter("cmObj", cmtCuentaMatrizMgl);
            q.setHint(JAVAX_PERSISTENCE_CACHE_STORE_MODE, REFRESH);
            List<CmtOrdenTrabajoMgl> result = q.getResultList();
            getEntityManager().clear();
            return result;
        } catch (Exception e) {
            String msg = SE_PRODUJO_UN_ERROR_AL_MOMENTO_DE_EJECUTAR_EL_METODO + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Busca las Ordenes de Trabajo aosciadas a las subedificios
     *
     * @author cardenaslb
     * @param cmtSubEdificioMgl
     * @return Ordenes de Trabajo encontrada en el repositorio asociadas a una
     * Cuenta Matriz
     * @throws ApplicationException
     */
    public List<CmtOrdenTrabajoMgl> findPaginacionSub(CmtSubEdificioMgl cmtSubEdificioMgl) throws ApplicationException {
        try {
            String query = "select o from CmtOrdenTrabajoMgl o where o.idOt in ( select t.otObj.idOt from CmtVisitaTecnicaMgl t "
                    + " where t.idVt in (select e.vtObj.idVt from CmtSubEdificiosVt e "
                    + " where e.subEdificioObj.subEdificioId = :subEdificioObj ))";
            Query q = entityManager.createQuery(query);
            q.setParameter("subEdificioObj", cmtSubEdificioMgl.getSubEdificioId());
            q.setHint(JAVAX_PERSISTENCE_CACHE_STORE_MODE, REFRESH);
            List<CmtOrdenTrabajoMgl> result = q.getResultList();
            getEntityManager().clear();
            return result;
        } catch (Exception e) {
            String msg = SE_PRODUJO_UN_ERROR_AL_MOMENTO_DE_EJECUTAR_EL_METODO + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    public Long countByCm(CmtCuentaMatrizMgl cmtCuentaMatrizMgl)
            throws ApplicationException {
        try {
            Query q = entityManager.createQuery("SELECT COUNT(1) FROM CmtOrdenTrabajoMgl o "
                    + "WHERE  o.cmObj = :cmObj ");
            q.setParameter("cmObj", cmtCuentaMatrizMgl);
            q.setHint(JAVAX_PERSISTENCE_CACHE_STORE_MODE, REFRESH);
            return (Long) q.getSingleResult();
        } catch (Exception e) {
            String msg = SE_PRODUJO_UN_ERROR_AL_MOMENTO_DE_EJECUTAR_EL_METODO + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Cuenta las Ordenes de Trabajo aosciadas a las subedificios
     *
     * @author cardenaslb
     * @param cmtSubEdificioMgl
     * @return Ordenes de Trabajo encontrada en el repositorio asociadas a una
     * Cuenta Matriz
     * @throws ApplicationException
     */
    public Long countBySub(CmtSubEdificioMgl cmtSubEdificioMgl)
            throws ApplicationException {
        try {
            String query = "select count(1) from CmtOrdenTrabajoMgl o where o.idOt in "
                    + "( select t.otObj.idOt from CmtVisitaTecnicaMgl t "
                    + " where t.idVt in (select e.vtObj.idVt from CmtSubEdificiosVt e "
                    + " where e.subEdificioObj.subEdificioId = :subEdificioObj ))";
            Query q = entityManager.createQuery(query);
            q.setParameter("subEdificioObj", cmtSubEdificioMgl.getSubEdificioId());
            q.setHint(JAVAX_PERSISTENCE_CACHE_STORE_MODE, REFRESH);
            return (Long) q.getSingleResult();
        } catch (Exception e) {
            String msg = SE_PRODUJO_UN_ERROR_AL_MOMENTO_DE_EJECUTAR_EL_METODO + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Busqueda de todas las Ordenes de trabajo que sean de una cuenta matriza y
     * sean de la misma tecnologia
     *
     * @author Rodriguezluim
     * @param CuentaMatriz valor de la cuenta matriz para la busqueda
     * @param basicaTecnologia valor de la basica de tecnologia para la busqueda
     * @return Retorna una lista de Ordenes de trabajo
     * @throws ApplicationException
     */
    public List<CmtOrdenTrabajoMgl> findByCcmmAndTecnologia(CmtCuentaMatrizMgl CuentaMatriz,
            CmtBasicaMgl basicaTecnologia) throws ApplicationException {
        try {
            Query q = entityManager.createQuery("SELECT c FROM CmtOrdenTrabajoMgl c WHERE c.cmObj = :cuentaMatriz and c.basicaIdTecnologia = :basicaTecnologia");
            q.setParameter("cuentaMatriz", CuentaMatriz);
            q.setParameter("basicaTecnologia", basicaTecnologia);
            q.setHint(JAVAX_PERSISTENCE_CACHE_STORE_MODE, REFRESH);
            List<CmtOrdenTrabajoMgl> result = q.getResultList();
            getEntityManager().clear();
            return result;
        } catch (Exception e) {
            String msg = SE_PRODUJO_UN_ERROR_AL_MOMENTO_DE_EJECUTAR_EL_METODO + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * M&eacute;todo para consultar ordenes de trabajo abiertas
     *
     * @param cuentaMatriz {@link CmtCuentaMatrizMgl} CM a la que esta asociada
     * la OT
     * @return {@link List}&lt{@link CmtOrdenTrabajoMgl}> Ordenes de trabajo
     * encontradas
     * @throws ApplicationException Excepci&oacute;n lanzada por la consulta
     */
    @SuppressWarnings("unchecked")
    public List<CmtOrdenTrabajoMgl> ordenesTrabajoActivas(CmtCuentaMatrizMgl cuentaMatriz) throws ApplicationException {

        List<CmtOrdenTrabajoMgl> ordenesCcmm;
        List<CmtOrdenTrabajoMgl> ordenesActivas = new ArrayList<>();

        if (cuentaMatriz == null) {
            throw new ApplicationException("No se envio una cuenta matriz para la consulta");
        }

        Query q = entityManager.createQuery("SELECT c FROM CmtOrdenTrabajoMgl c  "
                + "WHERE c.cmObj = :cuentaMatriz  AND  c.estadoRegistro = :estado");

        q.setParameter("cuentaMatriz", cuentaMatriz);
        q.setParameter(OrdenTrabajoConstants.ESTADO_PARAMETER, 1);
        q.setHint(JAVAX_PERSISTENCE_CACHE_STORE_MODE, REFRESH);
        ordenesCcmm = q.getResultList();

        if (ordenesCcmm != null && ordenesCcmm.size() > 0) {

            CmtBasicaMglManager basicaMglManager = new CmtBasicaMglManager();
            CmtBasicaMgl basicaEstExteAbi = basicaMglManager.findByCodigoInternoApp("@ABR");
            CmtEstadoIntxExtMgl estadoInternoExterno;
            CmtEstadoIntxExtMglManager estadoIntxExtMglManager = new CmtEstadoIntxExtMglManager();

            if (basicaEstExteAbi != null && basicaEstExteAbi.getBasicaId() != null) {
                for (CmtOrdenTrabajoMgl ordenes : ordenesCcmm) {
                    estadoInternoExterno = estadoIntxExtMglManager.
                            findByEstadoInterno(ordenes.getEstadoInternoObj());
                    if (estadoInternoExterno != null
                            && estadoInternoExterno.getIdEstadoExt().getBasicaId().
                                    compareTo(basicaEstExteAbi.getBasicaId()) == 0) {
                        ordenesActivas.add(ordenes);
                    }
                }

            }

        }

        getEntityManager().clear();
        return ordenesActivas;

    }

    /**
     * Metodo para consultar ordenes de trabajo de vt cerradas Autor: Victor
     * Bocanegra
     *
     * @param cuentaMatriz {@link CmtCuentaMatrizMgl} CM a la que esta asociada
     * la OT
     * @param tipoOtMgl
     * @param estadoInternoOt estado interno finalizado
     * @return {@link List}&lt{@link CmtOrdenTrabajoMgl}> Ordenes de trabajo
     * encontradas
     * @throws ApplicationException Excepci&oacute;n lanzada por la consulta
     */
    public List<CmtOrdenTrabajoMgl> ordenesTrabajoVtCerradas(CmtCuentaMatrizMgl cuentaMatriz,
            CmtTipoOtMgl tipoOtMgl, CmtBasicaMgl estadoInternoOt) throws ApplicationException {
        StringBuilder consulta = new StringBuilder();
        consulta.append("SELECT ot FROM CmtOrdenTrabajoMgl ot")
                .append(" WHERE ot.estadoRegistro = :estado")
                .append(" AND ot.cmObj.cuentaMatrizId = :idCuentaMatriz")
                .append(" AND ot.tipoOtObj.idTipoOt = :idTipoOt")
                .append(" AND ot.estadoInternoObj.basicaId = :basicaId");

        return entityManager.createQuery(consulta.toString())
                .setParameter(OrdenTrabajoConstants.ESTADO_PARAMETER, 1)
                .setParameter("idCuentaMatriz", cuentaMatriz.getCuentaMatrizId())
                .setParameter("idTipoOt", tipoOtMgl.getIdTipoOt())
                .setParameter("basicaId", estadoInternoOt.getBasicaId())
                .setHint(JAVAX_PERSISTENCE_CACHE_STORE_MODE, REFRESH)
                .getResultList();
    }

    /**
     * Busca las Ordenes de Trabajo asociadas a un tipo de trabajo.
     *
     * @author Victor Bocanegra
     * @param tipoOtMgl tipo de trabajo
     * @return Ordenes de Trabajo encontrada en el repositorio asociadas a un
     * tipo de trabajo
     * @throws ApplicationException
     */
    public List<CmtOrdenTrabajoMgl> findByTipoTrabajo(CmtTipoOtMgl tipoOtMgl) throws ApplicationException {
        try {
            Query query = entityManager.createNamedQuery("CmtOrdenTrabajoMgl.findByTipoTrabajo");
            query.setParameter("tipoOtObj", tipoOtMgl);
            query.setHint(JAVAX_PERSISTENCE_CACHE_STORE_MODE, REFRESH);
            List<CmtOrdenTrabajoMgl> result = query.getResultList();
            getEntityManager().clear();
            return result;
        } catch (Exception e) {
            String msg = SE_PRODUJO_UN_ERROR_AL_MOMENTO_DE_EJECUTAR_EL_METODO + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Busca una orden de trabajo especifica
     *
     * @author Victor Bocanegra
     * @param idOt de la orden a consultar
     * @param estadosxflujoList lista de estado X flujo
     * @return CmtOrdenTrabajoMgl encontrada en el repositorio
     * @throws ApplicationException
     */
    public CmtOrdenTrabajoMgl findByIdOtAndEstXFlu(BigDecimal idOt, List<BigDecimal> estadosxflujoList)
            throws ApplicationException {
        try {
            StringBuilder queryStr = new StringBuilder()
                    .append(OrdenTrabajoConstants.SELECT_DISTINCT_O_FROM_CMT_ORDEN_TRABAJO_MGL_O
                    + OrdenTrabajoConstants.CMT_ESTADOX_FLUJO_MGL_EF_CMT_CUENTA_MATRIZ_MGL_CM
                    + " WHERE t.idTipoOt = o.tipoOtObj.idTipoOt  "
                    + " AND ef.tipoFlujoOtObj.basicaId = t.tipoFlujoOt.basicaId  "
                    + " AND ef.basicaTecnologia.basicaId =o.basicaIdTecnologia.basicaId  "
                    + " AND o.estadoInternoObj.basicaId = ef.estadoInternoObj.basicaId  "
                    + " AND cm.cuentaMatrizId = o.cmObj.cuentaMatrizId  "
                    + OrdenTrabajoConstants.AND_O_ESTADO_REGISTRO_ESTADO_REGISTRO);

            if (idOt != null && !idOt.equals(BigDecimal.ZERO)) {
                queryStr.append(OrdenTrabajoConstants.AND_O_ID_OT_ID_OT);
            }
            if (estadosxflujoList != null && !estadosxflujoList.isEmpty()) {
                queryStr.append(OrdenTrabajoConstants.AND_EF_ESTADOX_FLUJO_ID_IN_ESTADOSXFLUJO_LIST);
            }

            Query query = entityManager.createQuery(queryStr.toString());
            query.setParameter(OrdenTrabajoConstants.ESTADO_REGISTRO_PARAMETER, 1);

            if (idOt != null && !idOt.equals(BigDecimal.ZERO)) {
                query.setParameter("idOt", idOt);
            }
            if (estadosxflujoList != null && !estadosxflujoList.isEmpty()) {
                query.setParameter(OrdenTrabajoConstants.ESTADOSXFLUJO_LIST_PARAMETER, estadosxflujoList);
            }

            query.setHint(JAVAX_PERSISTENCE_CACHE_STORE_MODE, REFRESH);
            return (CmtOrdenTrabajoMgl) query.getSingleResult();
        } catch (NoResultException e) {
            String msg = SE_PRODUJO_UN_ERROR_AL_MOMENTO_DE_EJECUTAR_EL_METODO + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            return null;
        } catch (Exception e) {
            String msg = SE_PRODUJO_UN_ERROR_AL_MOMENTO_DE_EJECUTAR_EL_METODO + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }

    }

    /**
     * Busca las Ordenes de Trabajo por estados X flujo.Permite realizar la
     * busqueda de las Ordenes de Trabajo por medio de los estado X flujo,
     * comunidad, division y tipo.
     *
     * @author Victor Bocanegra
     * @param estadosxflujoList lista de estado X flujo
     * @param filtro para la consulta
     * @return Ordenes de Trabajo encontrada en el repositorio
     * @throws ApplicationException
     */
    public List<CmtOrdenTrabajoMgl> findByFiltroParaGestionExportarOt(
            List<BigDecimal> estadosxflujoList, CmtFiltroConsultaOrdenesDto filtro) throws ApplicationException {
        try {
            String queryStr = OrdenTrabajoConstants.SELECT_DISTINCT_O_FROM_CMT_ORDEN_TRABAJO_MGL_O
                    + OrdenTrabajoConstants.CMT_ESTADOX_FLUJO_MGL_EF_CMT_CUENTA_MATRIZ_MGL_CM
                    + OrdenTrabajoConstants.WHERE_T_ID_TIPO_OT_O_TIPO_OT_OBJ_ID_TIPO_OT
                    + OrdenTrabajoConstants.AND_EF_TIPO_FLUJO_OT_OBJ_BASICA_ID_T_TIPO_FLUJO_OT_BASICA_ID
                    + OrdenTrabajoConstants.AND_EF_BASICA_TECNOLOGIA_BASICA_ID_O_BASICA_ID_TECNOLOGIA_BASICA_ID
                    + OrdenTrabajoConstants.AND_O_ESTADO_INTERNO_OBJ_BASICA_ID_EF_ESTADO_INTERNO_OBJ_BASICA_ID
                    + OrdenTrabajoConstants.MATRIZ_ID_O_CM_OBJ_CUENTA_MATRIZ_ID
                    + OrdenTrabajoConstants.AND_O_ESTADO_REGISTRO_ESTADO_REGISTRO;

            if (filtro.getFechaIngresoSeleccionada() != null) {
                queryStr += "AND o.fechaCreacion BETWEEN :fechaCreacionInicial AND :fechaCreacionFinal ";
            }

            if (filtro.getIdOtSelecionada() != null
                    && !filtro.getIdOtSelecionada().equals(BigDecimal.ZERO)) {
                queryStr += OrdenTrabajoConstants.AND_O_ID_OT_ID_OT;
            }

            if (filtro.getCcmmRrSelecionada() != null
                    && !filtro.getCcmmRrSelecionada().equals(BigDecimal.ZERO)) {
                queryStr += OrdenTrabajoConstants.AND_CM_NUMERO_CUENTA_NUMERO_CUENTA;
            }

            if (filtro.getTipoOtSelecionada() != null && !filtro.getTipoOtSelecionada().isEmpty()) {
                queryStr += OrdenTrabajoConstants.AND_UPPER_O_TIPO_OT_OBJ_DESC_TIPO_OT_LIKE_DESC_TIPO_OT;
            }

            if (filtro.getDptoSelecionado() != null && !filtro.getDptoSelecionado().isEmpty()) {
                queryStr += OrdenTrabajoConstants.AND_UPPER_CM_DEPARTAMENTO_GPO_NOMBRE_LIKE_DEPARTAMENTO;
            }

            if (filtro.getCiudadSelecionada() != null && !filtro.getCiudadSelecionada().isEmpty()) {
                queryStr += OrdenTrabajoConstants.AND_UPPER_CM_MUNICIPIO_GPO_NOMBRE_LIKE_MUNICIPIO;
            }

            if (filtro.getTecnoSelecionada() != null && !filtro.getTecnoSelecionada().isEmpty()) {
                queryStr += OrdenTrabajoConstants.AND_UPPER_O_BASICA_ID_TECNOLOGIA_NOMBRE_BASICA_LIKE_TECNOLOGIA;
            }

            if (filtro.getEstIntSelecionada() != null && !filtro.getEstIntSelecionada().isEmpty()) {
                queryStr += OrdenTrabajoConstants.AND_UPPER_O_ESTADO_INTERNO_OBJ_NOMBRE_BASICA_LIKE_ESTADO;
            }

            if (filtro.getCcmmMglSelecionada() != null
                    && !filtro.getCcmmMglSelecionada().equals(BigDecimal.ZERO)) {
                queryStr += " AND cm.cuentaMatrizId = :cuentaMatrizId ";
            }

            if (filtro.getRegionalSeleccionada() != null && !filtro.getRegionalSeleccionada().isEmpty()) {
                queryStr += " AND UPPER(o.cmObj.division) LIKE :divicion";
            }

            if (filtro.getUserBloqueo() != null && !filtro.getUserBloqueo().isEmpty()) {
                queryStr += " AND UPPER(o.disponibilidadGestion) LIKE :disponibilidadGestion";
            }

            if (estadosxflujoList != null && !estadosxflujoList.isEmpty()) {
                queryStr += OrdenTrabajoConstants.AND_EF_ESTADOX_FLUJO_ID_IN_ESTADOSXFLUJO_LIST;
            }
            queryStr += OrdenTrabajoConstants.ORDER_BY_O_FECHA_CREACION_ASC;

            Query query = entityManager.createQuery(queryStr);
            query.setParameter(OrdenTrabajoConstants.ESTADO_REGISTRO_PARAMETER, 1);

            if (filtro.getFechaIngresoSeleccionada() != null) {
                query.setParameter(OrdenTrabajoConstants.FECHA_CREACION_INICIAL_PARAMETER, filtro.getFechaIngresoSeleccionada());

                long fechaEnMilisegundos = filtro.getFechaIngresoSeleccionada().getTime() + MILISEGUNDOS_DIA - 1;
                query.setParameter(OrdenTrabajoConstants.FECHA_CREACION_FINAL_PARAMETER, new Date(fechaEnMilisegundos));
            }

            if (filtro.getIdOtSelecionada() != null
                    && !filtro.getIdOtSelecionada().equals(BigDecimal.ZERO)) {
                query.setParameter("idOt", filtro.getIdOtSelecionada());
            }

            if (filtro.getCcmmRrSelecionada() != null
                    && !filtro.getCcmmRrSelecionada().equals(BigDecimal.ZERO)) {
                query.setParameter(OrdenTrabajoConstants.NUMERO_CUENTA_PARAMETER, filtro.getCcmmRrSelecionada());
            }

            if (filtro.getTipoOtSelecionada() != null && !filtro.getTipoOtSelecionada().isEmpty()) {
                query.setParameter(OrdenTrabajoConstants.DESC_TIPO_OT_PARAMETER, "%" + filtro.getTipoOtSelecionada().trim().toUpperCase() + "%");
            }

            if (filtro.getDptoSelecionado() != null && !filtro.getDptoSelecionado().isEmpty()) {
                query.setParameter(OrdenTrabajoConstants.DEPARTAMENTO_PARAMETER, "%" + filtro.getDptoSelecionado().trim().toUpperCase() + "%");
            }

            if (filtro.getCiudadSelecionada() != null && !filtro.getCiudadSelecionada().isEmpty()) {
                query.setParameter(OrdenTrabajoConstants.MUNICIPIO_PARAMETER, "%" + filtro.getCiudadSelecionada().trim().toUpperCase() + "%");
            }

            if (filtro.getTecnoSelecionada() != null && !filtro.getTecnoSelecionada().isEmpty()) {
                query.setParameter(OrdenTrabajoConstants.TECNOLOGIA_PARAMETER, "%" + filtro.getTecnoSelecionada().trim().toUpperCase() + "%");
            }

            if (filtro.getEstIntSelecionada() != null && !filtro.getEstIntSelecionada().isEmpty()) {
                query.setParameter(OrdenTrabajoConstants.ESTADO_PARAMETER, "%" + filtro.getEstIntSelecionada().trim().toUpperCase() + "%");
            }

            if (filtro.getCcmmMglSelecionada() != null
                    && !filtro.getCcmmMglSelecionada().equals(BigDecimal.ZERO)) {
                query.setParameter("cuentaMatrizId", filtro.getCcmmMglSelecionada());
            }

            if (filtro.getRegionalSeleccionada() != null && !filtro.getRegionalSeleccionada().isEmpty()) {
                query.setParameter("divicion", "%" + filtro.getRegionalSeleccionada().trim().toUpperCase() + "%");
            }

            if (filtro.getUserBloqueo() != null && !filtro.getUserBloqueo().isEmpty()) {
                query.setParameter("disponibilidadGestion", "%" + filtro.getUserBloqueo().trim().toUpperCase() + "%");
            }
            if (estadosxflujoList != null && !estadosxflujoList.isEmpty()) {
                query.setParameter(OrdenTrabajoConstants.ESTADOSXFLUJO_LIST_PARAMETER, estadosxflujoList);
            }

            query.setHint(JAVAX_PERSISTENCE_CACHE_STORE_MODE, REFRESH);
            getEntityManager().clear();
            return query.getResultList();
        } catch (Exception e) {
            String msg = SE_PRODUJO_UN_ERROR_AL_MOMENTO_DE_EJECUTAR_EL_METODO + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    public int countByFiltroParaGestionExportarOt(
            List<BigDecimal> estadosxflujoList, BigDecimal gpoDepartamento,
            BigDecimal gpoCiudad, BigDecimal idTipoOt, BigDecimal estadoInternoBasicaId,
            BigDecimal tecnologia, String codigoReg) throws ApplicationException {
        try {
            String queryStr = "SELECT Count (1) FROM CmtOrdenTrabajoMgl o,"
                    + OrdenTrabajoConstants.CMT_ESTADOX_FLUJO_MGL_EF_CMT_CUENTA_MATRIZ_MGL_CM
                    + OrdenTrabajoConstants.WHERE_T_ID_TIPO_OT_O_TIPO_OT_OBJ_ID_TIPO_OT
                    + OrdenTrabajoConstants.AND_EF_TIPO_FLUJO_OT_OBJ_BASICA_ID_T_TIPO_FLUJO_OT_BASICA_ID
                    + OrdenTrabajoConstants.AND_EF_BASICA_TECNOLOGIA_BASICA_ID_O_BASICA_ID_TECNOLOGIA_BASICA_ID
                    + OrdenTrabajoConstants.AND_O_ESTADO_INTERNO_OBJ_BASICA_ID_EF_ESTADO_INTERNO_OBJ_BASICA_ID
                    + OrdenTrabajoConstants.MATRIZ_ID_O_CM_OBJ_CUENTA_MATRIZ_ID
                    + OrdenTrabajoConstants.AND_O_ESTADO_REGISTRO_ESTADO_REGISTRO;

            if (codigoReg != null && !codigoReg.isEmpty()) {
                queryStr += " AND o.cmObj.division = :codigoReg ";
            }
            if (gpoDepartamento != null && !gpoDepartamento.equals(BigDecimal.ZERO)) {
                queryStr += " AND cm.departamento.gpoId = :gpoDepartamento ";
            }
            if (gpoCiudad != null && !gpoCiudad.equals(BigDecimal.ZERO)) {
                queryStr += " AND cm.municipio.gpoId = :gpoCiudad ";
            }
            if (idTipoOt != null && !idTipoOt.equals(BigDecimal.ZERO)) {
                queryStr += " AND o.tipoOtObj.idTipoOt = :tipoOt ";
            }
            if (estadoInternoBasicaId != null && !estadoInternoBasicaId.equals(BigDecimal.ZERO)) {
                queryStr += " AND o.estadoInternoObj.basicaId = :estadoInterno ";
            }
            if (tecnologia != null && !tecnologia.equals(BigDecimal.ZERO)) {
                queryStr += " AND o.basicaIdTecnologia.basicaId = :basicaIdTecnologia ";
            }
            if (estadosxflujoList != null && !estadosxflujoList.isEmpty()) {
                queryStr += OrdenTrabajoConstants.AND_EF_ESTADOX_FLUJO_ID_IN_ESTADOSXFLUJO_LIST;
            }
            queryStr += OrdenTrabajoConstants.ORDER_BY_O_FECHA_CREACION_ASC;

            Query query = entityManager.createQuery(queryStr);
            query.setParameter(OrdenTrabajoConstants.ESTADO_REGISTRO_PARAMETER, 1);
            if (codigoReg != null && !codigoReg.isEmpty()) {
                query.setParameter("codigoReg", codigoReg);
            }
            if (gpoDepartamento != null && !gpoDepartamento.equals(BigDecimal.ZERO)) {
                query.setParameter("gpoDepartamento", gpoDepartamento);
            }
            if (gpoCiudad != null && !gpoCiudad.equals(BigDecimal.ZERO)) {
                query.setParameter("gpoCiudad", gpoCiudad);
            }
            if (idTipoOt != null && !idTipoOt.equals(BigDecimal.ZERO)) {
                query.setParameter("tipoOt", idTipoOt);
            }
            if (estadosxflujoList != null && !estadosxflujoList.isEmpty()) {
                query.setParameter(OrdenTrabajoConstants.ESTADOSXFLUJO_LIST_PARAMETER, estadosxflujoList);
            }
            if (tecnologia != null && !tecnologia.equals(BigDecimal.ZERO)) {
                query.setParameter("basicaIdTecnologia", tecnologia);
            }
            if (estadoInternoBasicaId != null && !estadoInternoBasicaId.equals(BigDecimal.ZERO)) {
                query.setParameter("estadoInterno", estadoInternoBasicaId);
            }
            query.setHint(JAVAX_PERSISTENCE_CACHE_STORE_MODE, REFRESH);

            Long result;
            result = (Long) query.getSingleResult();

            if (result != null) {
                return result.intValue();
            } else {
                return 0;
            }
        } catch (Exception e) {
            String msg = SE_PRODUJO_UN_ERROR_AL_MOMENTO_DE_EJECUTAR_EL_METODO + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }

    }

    /**
     * Busca las Ordenes de Trabajo por estados X flujo.Permite realizar la
     * busqueda de las Ordenes de Trabajo que generan OTs de acometidas por
     * medio de los estado X flujo, comunidad, division y tipo.
     *
     * @author Victor Bocanegra
     * @param estadosxflujoList lista de estado X flujo
     * @param filtro para la consulta
     * @param tiposOtgeneranAco tipos de Ot que generan acometidas
     * @param estIntCierreCom estados internos de cierre comercial
     * @return Ordenes de Trabajo encontrada en el repositorio
     * @throws ApplicationException
     */
    public List<CmtOrdenTrabajoMgl> findByOtCloseForGenerarAcometida(List<BigDecimal> estadosxflujoList,
            CmtFiltroConsultaOrdenesDto filtro, List<BigDecimal> tiposOtgeneranAco,
            List<BigDecimal> estIntCierreCom, int firstResult, int maxResults) throws ApplicationException {

        List<CmtOrdenTrabajoMgl> result = new ArrayList();
        List<BigDecimal> estadosxflujoListFull = estadosxflujoList;
        int numeroEstados = estadosxflujoListFull.size();
        int numeroBloques = (int) Math.ceil(Double.parseDouble(String.valueOf(numeroEstados)) / (LIMITE_INPUT_CONSULTA));
        int inicioBloque = 0;
        int finBloque = numeroEstados < LIMITE_INPUT_CONSULTA ? numeroEstados : LIMITE_INPUT_CONSULTA;

        for (int i = 1; i <= numeroBloques; i++) {
            estadosxflujoList = estadosxflujoListFull.subList(inicioBloque, finBloque);

            try {

                String queryStr = OrdenTrabajoConstants.SELECT_DISTINCT_O_FROM_CMT_ORDEN_TRABAJO_MGL_O
                        + OrdenTrabajoConstants.CMT_TIPO_OT_MGL_T_CMT_ESTADOX_FLUJO_MGL_EF_CMT_CUENTA_MATRIZ_MGL_CM
                        + OrdenTrabajoConstants.CMT_VISITA_TECNICA_MGL_VT_CMT_SUB_EDIFICIOS_VT_SUB_VT
                        + OrdenTrabajoConstants.WHERE_T_ID_TIPO_OT_O_TIPO_OT_OBJ_ID_TIPO_OT
                        + OrdenTrabajoConstants.AND_EF_TIPO_FLUJO_OT_OBJ_BASICA_ID_T_TIPO_FLUJO_OT_BASICA_ID
                        + OrdenTrabajoConstants.AND_O_ESTADO_INTERNO_OBJ_BASICA_ID_EF_ESTADO_INTERNO_OBJ_BASICA_ID
                        + OrdenTrabajoConstants.AND_VT_OT_OBJ_ID_OT_O_ID_OT
                        + OrdenTrabajoConstants.AND_SUB_VT_VT_OBJ_ID_VT_VT_ID_VT
                        + OrdenTrabajoConstants.MATRIZ_ID_O_CM_OBJ_CUENTA_MATRIZ_ID
                        + OrdenTrabajoConstants.AND_O_ESTADO_REGISTRO_ESTADO_REGISTRO
                        + OrdenTrabajoConstants.AND_VT_ESTADO_VISITA_TECNICA_1
                        + OrdenTrabajoConstants.AND_SUB_VT_OT_ACOMETIDA_ID_IS_NULL
                        + OrdenTrabajoConstants.AND_SUB_VT_ESTADO_REGISTRO_1;

                if (filtro.getFechaIngresoSeleccionada() != null) {
                    queryStr += " AND o.fechaCreacion BETWEEN :fechaCreacionInicial AND :fechaCreacionFinal ";
                }

                if (filtro.getIdOtSelecionada() != null
                        && !filtro.getIdOtSelecionada().equals(BigDecimal.ZERO)) {
                    queryStr += OrdenTrabajoConstants.AND_O_ID_OT_ID_OT;
                }

                if (filtro.getCcmmRrSelecionada() != null
                        && !filtro.getCcmmRrSelecionada().equals(BigDecimal.ZERO)) {
                    queryStr += OrdenTrabajoConstants.AND_CM_NUMERO_CUENTA_NUMERO_CUENTA;
                }

                if (filtro.getTipoOtSelecionada() != null && !filtro.getTipoOtSelecionada().isEmpty()) {
                    queryStr += OrdenTrabajoConstants.AND_UPPER_O_TIPO_OT_OBJ_DESC_TIPO_OT_LIKE_DESC_TIPO_OT;
                } else {
                    if (tiposOtgeneranAco != null && !tiposOtgeneranAco.isEmpty()) {
                        queryStr += " AND o.tipoOtObj.idTipoOt IN :tipoOtGenAcoList ";
                    }
                }

                if (filtro.getDptoSelecionado() != null && !filtro.getDptoSelecionado().isEmpty()) {
                    queryStr += OrdenTrabajoConstants.AND_UPPER_CM_DEPARTAMENTO_GPO_NOMBRE_LIKE_DEPARTAMENTO;
                }

                if (filtro.getCiudadSelecionada() != null && !filtro.getCiudadSelecionada().isEmpty()) {
                    queryStr += OrdenTrabajoConstants.AND_UPPER_CM_MUNICIPIO_GPO_NOMBRE_LIKE_MUNICIPIO;
                }

                if (filtro.getTecnoSelecionada() != null && !filtro.getTecnoSelecionada().isEmpty()) {
                    queryStr += OrdenTrabajoConstants.AND_UPPER_O_BASICA_ID_TECNOLOGIA_NOMBRE_BASICA_LIKE_TECNOLOGIA;
                }

                if (filtro.getEstIntSelecionada() != null && !filtro.getEstIntSelecionada().isEmpty()) {
                    queryStr += OrdenTrabajoConstants.AND_UPPER_O_ESTADO_INTERNO_OBJ_NOMBRE_BASICA_LIKE_ESTADO;
                } else {
                    if (estIntCierreCom != null && !estIntCierreCom.isEmpty()) {
                        queryStr += " AND o.estadoInternoObj.basicaId IN :estadosInternoCierreCom ";
                    }
                }

                if (filtro.getSlaSeleccionada() > 0) {
                    queryStr += OrdenTrabajoConstants.AND_O_TIPO_OT_OBJ_ANS_ANS;
                }

                if (estadosxflujoList != null && !estadosxflujoList.isEmpty()) {
                    queryStr += OrdenTrabajoConstants.AND_EF_ESTADOX_FLUJO_ID_IN_ESTADOSXFLUJO_LIST;
                }

                queryStr += OrdenTrabajoConstants.ORDER_BY_O_FECHA_CREACION_ASC;

                Query query = entityManager.createQuery(queryStr);
                query.setFirstResult(firstResult);
                query.setMaxResults(maxResults);
                query.setParameter(OrdenTrabajoConstants.ESTADO_REGISTRO_PARAMETER, 1);

                if (filtro.getFechaIngresoSeleccionada() != null) {
                    query.setParameter(OrdenTrabajoConstants.FECHA_CREACION_INICIAL_PARAMETER, filtro.getFechaIngresoSeleccionada());

                    long fechaEnMilisegundos = filtro.getFechaIngresoSeleccionada().getTime() + MILISEGUNDOS_DIA - 1;
                    query.setParameter(OrdenTrabajoConstants.FECHA_CREACION_FINAL_PARAMETER, new Date(fechaEnMilisegundos));
                }

                if (filtro.getIdOtSelecionada() != null
                        && !filtro.getIdOtSelecionada().equals(BigDecimal.ZERO)) {
                    query.setParameter("idOt", filtro.getIdOtSelecionada());
                }

                if (filtro.getCcmmRrSelecionada() != null
                        && !filtro.getCcmmRrSelecionada().equals(BigDecimal.ZERO)) {
                    query.setParameter(OrdenTrabajoConstants.NUMERO_CUENTA_PARAMETER, filtro.getCcmmRrSelecionada());
                }

                if (filtro.getTipoOtSelecionada() != null && !filtro.getTipoOtSelecionada().isEmpty()) {
                    query.setParameter(OrdenTrabajoConstants.DESC_TIPO_OT_PARAMETER, "%" + filtro.getTipoOtSelecionada().trim().toUpperCase() + "%");
                } else {
                    if (tiposOtgeneranAco != null && !tiposOtgeneranAco.isEmpty()) {
                        query.setParameter("tipoOtGenAcoList", tiposOtgeneranAco);
                    }
                }

                if (filtro.getDptoSelecionado() != null && !filtro.getDptoSelecionado().isEmpty()) {
                    query.setParameter(OrdenTrabajoConstants.DEPARTAMENTO_PARAMETER, "%" + filtro.getDptoSelecionado().trim().toUpperCase() + "%");
                }

                if (filtro.getCiudadSelecionada() != null && !filtro.getCiudadSelecionada().isEmpty()) {
                    query.setParameter(OrdenTrabajoConstants.MUNICIPIO_PARAMETER, "%" + filtro.getCiudadSelecionada().trim().toUpperCase() + "%");
                }

                if (filtro.getTecnoSelecionada() != null && !filtro.getTecnoSelecionada().isEmpty()) {
                    query.setParameter(OrdenTrabajoConstants.TECNOLOGIA_PARAMETER, "%" + filtro.getTecnoSelecionada().trim().toUpperCase() + "%");
                }

                if (filtro.getEstIntSelecionada() != null && !filtro.getEstIntSelecionada().isEmpty()) {
                    query.setParameter(OrdenTrabajoConstants.ESTADO_PARAMETER, "%" + filtro.getEstIntSelecionada().trim().toUpperCase() + "%");
                } else {
                    if (estIntCierreCom != null && !estIntCierreCom.isEmpty()) {
                        query.setParameter("estadosInternoCierreCom", estIntCierreCom);
                    }
                }

                if (filtro.getSlaSeleccionada() > 0) {
                    query.setParameter("ans", filtro.getSlaSeleccionada());
                }

                if (estadosxflujoList != null && !estadosxflujoList.isEmpty()) {
                    query.setParameter(OrdenTrabajoConstants.ESTADOSXFLUJO_LIST_PARAMETER, estadosxflujoList);
                }

                query.setHint(JAVAX_PERSISTENCE_CACHE_STORE_MODE, REFRESH);
                for (int j = 0; j < query.getResultList().size(); j++) {
                    if (result.isEmpty()) {
                        result.add((CmtOrdenTrabajoMgl) query.getResultList().get(j));
                    } else {
                        boolean isRegistrada = this.isRegistradoOrdenTrabajoMgl(query.getResultList().get(j), result);
                        if (!isRegistrada) {
                            result.add((CmtOrdenTrabajoMgl) query.getResultList().get(j));
                        }
                    }
                }
                getEntityManager().clear();
            } catch (Exception e) {
                String msg = SE_PRODUJO_UN_ERROR_AL_MOMENTO_DE_EJECUTAR_EL_METODO + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
                LOGGER.error(msg);
                throw new ApplicationException(e.getMessage(), e);
            }
        }
        return result;
    }

    private boolean isRegistradoOrdenTrabajoMgl(Object objeto, List<CmtOrdenTrabajoMgl> lstOrdenTrabajo) {
        if (!(objeto instanceof CmtOrdenTrabajoMgl)) {
            return false;
        }
        CmtOrdenTrabajoMgl orderTrabajo = (CmtOrdenTrabajoMgl) objeto;
        boolean isRegistrada = false;
        try {
            int cantidad = lstOrdenTrabajo.size();
            int iterador = 0;
            while ((iterador != cantidad) && (!isRegistrada)) {
                if (lstOrdenTrabajo.get(iterador).getIdOt().equals(orderTrabajo.getIdOt())) {
                    isRegistrada = true;
                }
                iterador++;
            }
        } catch (Exception e) {
            String msg = SE_PRODUJO_UN_ERROR_AL_MOMENTO_DE_EJECUTAR_EL_METODO + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
        }
        return isRegistrada;
    }

    /**
     * Busca las Ordenes de Trabajo por estados X flujo.Permite realizar la
     * busqueda de las Ordenes de Trabajo que generan OTs de acometidas por
     * medio de los estado X flujo, comunidad, division y tipo.
     *
     * @author Victor Bocanegra
     * @param estadosxflujoList lista de estado X flujo
     * @param filtro para la consulta
     * @param tiposOtgeneranAco tipos de Ot que generan acometidas
     * @param estIntCierreCom estados internos de cierre comercial
     * @return Ordenes de Trabajo encontrada en el repositorio
     * @throws ApplicationException
     */
    public int getCountOtCloseForGenerarAcometida(List<BigDecimal> estadosxflujoList,
            CmtFiltroConsultaOrdenesDto filtro, List<BigDecimal> tiposOtgeneranAco,
            List<BigDecimal> estIntCierreCom) throws ApplicationException {

        List<BigDecimal> estadosxflujoListFull = estadosxflujoList;
        int numeroEstados = estadosxflujoListFull.size();
        int numeroBloques = (int) Math.ceil(Double.parseDouble(String.valueOf(numeroEstados)) / (LIMITE_INPUT_CONSULTA));
        int inicioBloque = 0;
        int finBloque = numeroEstados < LIMITE_INPUT_CONSULTA ? numeroEstados : LIMITE_INPUT_CONSULTA;
        int result = 0;

        for (int i = 1; i <= numeroBloques; i++) {
            estadosxflujoList = estadosxflujoListFull.subList(inicioBloque, finBloque);

            try {
                String queryStr = "SELECT COUNT(DISTINCT o)  FROM CmtOrdenTrabajoMgl o,"
                        + OrdenTrabajoConstants.CMT_TIPO_OT_MGL_T_CMT_ESTADOX_FLUJO_MGL_EF_CMT_CUENTA_MATRIZ_MGL_CM
                        + OrdenTrabajoConstants.CMT_VISITA_TECNICA_MGL_VT_CMT_SUB_EDIFICIOS_VT_SUB_VT
                        + OrdenTrabajoConstants.WHERE_T_ID_TIPO_OT_O_TIPO_OT_OBJ_ID_TIPO_OT
                        + OrdenTrabajoConstants.AND_EF_TIPO_FLUJO_OT_OBJ_BASICA_ID_T_TIPO_FLUJO_OT_BASICA_ID
                        + OrdenTrabajoConstants.AND_O_ESTADO_INTERNO_OBJ_BASICA_ID_EF_ESTADO_INTERNO_OBJ_BASICA_ID
                        + OrdenTrabajoConstants.AND_VT_OT_OBJ_ID_OT_O_ID_OT
                        + OrdenTrabajoConstants.AND_SUB_VT_VT_OBJ_ID_VT_VT_ID_VT
                        + OrdenTrabajoConstants.MATRIZ_ID_O_CM_OBJ_CUENTA_MATRIZ_ID
                        + OrdenTrabajoConstants.AND_O_ESTADO_REGISTRO_ESTADO_REGISTRO
                        + OrdenTrabajoConstants.AND_VT_ESTADO_VISITA_TECNICA_1
                        + OrdenTrabajoConstants.AND_SUB_VT_OT_ACOMETIDA_ID_IS_NULL
                        + OrdenTrabajoConstants.AND_SUB_VT_ESTADO_REGISTRO_1;

                if (filtro.getFechaIngresoSeleccionada() != null) {
                    queryStr += " AND o.fechaCreacion BETWEEN :fechaCreacionInicial AND :fechaCreacionFinal ";
                }

                if (filtro.getIdOtSelecionada() != null
                        && !filtro.getIdOtSelecionada().equals(BigDecimal.ZERO)) {
                    queryStr += OrdenTrabajoConstants.AND_O_ID_OT_ID_OT;
                }

                if (filtro.getCcmmRrSelecionada() != null
                        && !filtro.getCcmmRrSelecionada().equals(BigDecimal.ZERO)) {
                    queryStr += OrdenTrabajoConstants.AND_CM_NUMERO_CUENTA_NUMERO_CUENTA;
                }

                if (filtro.getTipoOtSelecionada() != null && !filtro.getTipoOtSelecionada().isEmpty()) {
                    queryStr += OrdenTrabajoConstants.AND_UPPER_O_TIPO_OT_OBJ_DESC_TIPO_OT_LIKE_DESC_TIPO_OT;
                } else {
                    if (tiposOtgeneranAco != null && !tiposOtgeneranAco.isEmpty()) {
                        queryStr += " AND o.tipoOtObj.idTipoOt IN :tipoOtGenAcoList ";
                    }
                }

                if (filtro.getDptoSelecionado() != null && !filtro.getDptoSelecionado().isEmpty()) {
                    queryStr += OrdenTrabajoConstants.AND_UPPER_CM_DEPARTAMENTO_GPO_NOMBRE_LIKE_DEPARTAMENTO;
                }

                if (filtro.getCiudadSelecionada() != null && !filtro.getCiudadSelecionada().isEmpty()) {
                    queryStr += OrdenTrabajoConstants.AND_UPPER_CM_MUNICIPIO_GPO_NOMBRE_LIKE_MUNICIPIO;
                }

                if (filtro.getTecnoSelecionada() != null && !filtro.getTecnoSelecionada().isEmpty()) {
                    queryStr += OrdenTrabajoConstants.AND_UPPER_O_BASICA_ID_TECNOLOGIA_NOMBRE_BASICA_LIKE_TECNOLOGIA;
                }

                if (filtro.getEstIntSelecionada() != null && !filtro.getEstIntSelecionada().isEmpty()) {
                    queryStr += OrdenTrabajoConstants.AND_UPPER_O_ESTADO_INTERNO_OBJ_NOMBRE_BASICA_LIKE_ESTADO;
                } else {
                    if (estIntCierreCom != null && !estIntCierreCom.isEmpty()) {
                        queryStr += " AND o.estadoInternoObj.basicaId IN :estadosInternoCierreCom ";
                    }
                }

                if (filtro.getSlaSeleccionada() > 0) {
                    queryStr += OrdenTrabajoConstants.AND_O_TIPO_OT_OBJ_ANS_ANS;
                }

                if (estadosxflujoList != null && !estadosxflujoList.isEmpty()) {
                    queryStr += OrdenTrabajoConstants.AND_EF_ESTADOX_FLUJO_ID_IN_ESTADOSXFLUJO_LIST;
                }

                queryStr += OrdenTrabajoConstants.ORDER_BY_O_FECHA_CREACION_ASC;

                Query query = entityManager.createQuery(queryStr);
                query.setParameter(OrdenTrabajoConstants.ESTADO_REGISTRO_PARAMETER, 1);

                if (filtro.getFechaIngresoSeleccionada() != null) {
                    query.setParameter(OrdenTrabajoConstants.FECHA_CREACION_INICIAL_PARAMETER, filtro.getFechaIngresoSeleccionada());

                    long fechaEnMilisegundos = filtro.getFechaIngresoSeleccionada().getTime() + MILISEGUNDOS_DIA - 1;
                    query.setParameter(OrdenTrabajoConstants.FECHA_CREACION_FINAL_PARAMETER, new Date(fechaEnMilisegundos));
                }

                if (filtro.getIdOtSelecionada() != null
                        && !filtro.getIdOtSelecionada().equals(BigDecimal.ZERO)) {
                    query.setParameter("idOt", filtro.getIdOtSelecionada());
                }

                if (filtro.getCcmmRrSelecionada() != null
                        && !filtro.getCcmmRrSelecionada().equals(BigDecimal.ZERO)) {
                    query.setParameter(OrdenTrabajoConstants.NUMERO_CUENTA_PARAMETER, filtro.getCcmmRrSelecionada());
                }

                if (filtro.getTipoOtSelecionada() != null && !filtro.getTipoOtSelecionada().isEmpty()) {
                    query.setParameter(OrdenTrabajoConstants.DESC_TIPO_OT_PARAMETER, "%" + filtro.getTipoOtSelecionada().trim().toUpperCase() + "%");
                } else {
                    if (tiposOtgeneranAco != null && !tiposOtgeneranAco.isEmpty()) {
                        query.setParameter("tipoOtGenAcoList", tiposOtgeneranAco);
                    }
                }

                if (filtro.getDptoSelecionado() != null && !filtro.getDptoSelecionado().isEmpty()) {
                    query.setParameter(OrdenTrabajoConstants.DEPARTAMENTO_PARAMETER, "%" + filtro.getDptoSelecionado().trim().toUpperCase() + "%");
                }

                if (filtro.getCiudadSelecionada() != null && !filtro.getCiudadSelecionada().isEmpty()) {
                    query.setParameter(OrdenTrabajoConstants.MUNICIPIO_PARAMETER, "%" + filtro.getCiudadSelecionada().trim().toUpperCase() + "%");
                }

                if (filtro.getTecnoSelecionada() != null && !filtro.getTecnoSelecionada().isEmpty()) {
                    query.setParameter(OrdenTrabajoConstants.TECNOLOGIA_PARAMETER, "%" + filtro.getTecnoSelecionada().trim().toUpperCase() + "%");
                }

                if (filtro.getEstIntSelecionada() != null && !filtro.getEstIntSelecionada().isEmpty()) {
                    query.setParameter(OrdenTrabajoConstants.ESTADO_PARAMETER, "%" + filtro.getEstIntSelecionada().trim().toUpperCase() + "%");
                } else {
                    if (estIntCierreCom != null && !estIntCierreCom.isEmpty()) {
                        query.setParameter("estadosInternoCierreCom", estIntCierreCom);
                    }
                }

                if (filtro.getSlaSeleccionada() > 0) {
                    query.setParameter("ans", filtro.getSlaSeleccionada());
                }

                if (estadosxflujoList != null && !estadosxflujoList.isEmpty()) {
                    query.setParameter(OrdenTrabajoConstants.ESTADOSXFLUJO_LIST_PARAMETER, estadosxflujoList);
                }

                query.setHint(JAVAX_PERSISTENCE_CACHE_STORE_MODE, REFRESH);
                result += query.getSingleResult() == null ? 0 : ((Long) query.getSingleResult()).intValue();

            } catch (Exception e) {
                String msg = SE_PRODUJO_UN_ERROR_AL_MOMENTO_DE_EJECUTAR_EL_METODO + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
                LOGGER.error(msg);
                throw new ApplicationException(e.getMessage(), e);
            }
        }
        return result;
    }

    /**
     * Busca una Orden de Trabajo por estados X flujo.
     *
     * @author Victor Bocanegra
     * @param estadosxflujoList lista de estado X flujo
     * @param idOt id de la Ot a buscar
     * @param tiposOtgeneranAco tipos de Ot que generan acometidas
     * @param estIntCierreCom estados internos de cierre comercial
     * @param conPermisos consulta con roles
     * @return CmtOrdenTrabajoMgl encontrada en el repositorio
     * @throws ApplicationException
     */
    public CmtOrdenTrabajoMgl findIdOtCloseForGenerarAcometidaAndPermisos(List<BigDecimal> estadosxflujoList,
            List<BigDecimal> tiposOtgeneranAco, List<BigDecimal> estIntCierreCom,
            BigDecimal idOt, boolean conPermisos)
            throws ApplicationException {
        try {

            String queryStr = OrdenTrabajoConstants.SELECT_DISTINCT_O_FROM_CMT_ORDEN_TRABAJO_MGL_O
                    + OrdenTrabajoConstants.CMT_TIPO_OT_MGL_T_CMT_ESTADOX_FLUJO_MGL_EF_CMT_CUENTA_MATRIZ_MGL_CM
                    + OrdenTrabajoConstants.CMT_VISITA_TECNICA_MGL_VT_CMT_SUB_EDIFICIOS_VT_SUB_VT
                    + OrdenTrabajoConstants.WHERE_T_ID_TIPO_OT_O_TIPO_OT_OBJ_ID_TIPO_OT
                    + OrdenTrabajoConstants.AND_EF_TIPO_FLUJO_OT_OBJ_BASICA_ID_T_TIPO_FLUJO_OT_BASICA_ID
                    + OrdenTrabajoConstants.AND_O_ESTADO_INTERNO_OBJ_BASICA_ID_EF_ESTADO_INTERNO_OBJ_BASICA_ID
                    + OrdenTrabajoConstants.AND_VT_OT_OBJ_ID_OT_O_ID_OT
                    + OrdenTrabajoConstants.AND_SUB_VT_VT_OBJ_ID_VT_VT_ID_VT
                    + OrdenTrabajoConstants.MATRIZ_ID_O_CM_OBJ_CUENTA_MATRIZ_ID
                    + OrdenTrabajoConstants.AND_O_ESTADO_REGISTRO_ESTADO_REGISTRO
                    + OrdenTrabajoConstants.AND_VT_ESTADO_VISITA_TECNICA_1
                    + OrdenTrabajoConstants.AND_SUB_VT_OT_ACOMETIDA_ID_IS_NULL
                    + OrdenTrabajoConstants.AND_SUB_VT_ESTADO_REGISTRO_1;

            if (idOt != null) {
                queryStr += OrdenTrabajoConstants.AND_O_ID_OT_ID_OT;
            }
            if (tiposOtgeneranAco != null && !tiposOtgeneranAco.isEmpty()) {
                queryStr += " AND o.tipoOtObj.idTipoOt IN :tipoOtGenAcoList ";
            }
            if (estIntCierreCom != null && !estIntCierreCom.isEmpty()) {
                queryStr += " AND o.estadoInternoObj.basicaId IN :estadosInternoCierreCom ";
            }
            if (conPermisos) {
                if (estadosxflujoList != null && !estadosxflujoList.isEmpty()) {
                    queryStr += OrdenTrabajoConstants.AND_EF_ESTADOX_FLUJO_ID_IN_ESTADOSXFLUJO_LIST;
                }
            }

            queryStr += OrdenTrabajoConstants.ORDER_BY_O_FECHA_CREACION_ASC;

            Query query = entityManager.createQuery(queryStr);

            query.setParameter(OrdenTrabajoConstants.ESTADO_REGISTRO_PARAMETER, 1);

            if (idOt != null) {
                query.setParameter("idOt", idOt);
            }

            if (tiposOtgeneranAco != null && !tiposOtgeneranAco.isEmpty()) {
                query.setParameter("tipoOtGenAcoList", tiposOtgeneranAco);
            }

            if (conPermisos) {
                if (estadosxflujoList != null && !estadosxflujoList.isEmpty()) {
                    query.setParameter(OrdenTrabajoConstants.ESTADOSXFLUJO_LIST_PARAMETER, estadosxflujoList);
                }
            }

            if (estIntCierreCom != null && !estIntCierreCom.isEmpty()) {
                query.setParameter("estadosInternoCierreCom", estIntCierreCom);
            }

            query.setHint(JAVAX_PERSISTENCE_CACHE_STORE_MODE, REFRESH);

            return (CmtOrdenTrabajoMgl) query.getSingleResult();
        } catch (NoResultException e) {
            String msg = SE_PRODUJO_UN_ERROR_AL_MOMENTO_DE_EJECUTAR_EL_METODO + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            return null;
        } catch (Exception e) {
            String msg = SE_PRODUJO_UN_ERROR_AL_MOMENTO_DE_EJECUTAR_EL_METODO + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }

    }

    public int getCountReporteEstadoActualOtCM(HashMap<String, Object> params,
            boolean contar, int firstResult, int maxResults, String usuario) throws ApplicationException {
        try {
            StringBuilder sql = new StringBuilder();
            StringBuilder sql2 = new StringBuilder();
            List<Object[]> listaOrdenesAgendas;
            List<Object[]> listaOrdenesAgendasDir;
            int total = 0;

            //CCMM
            sql.append("SELECT o, a,oy "
                    + "FROM CmtOrdenTrabajoMgl o "
                    + "left join o.listAgendas a  "
                    + "left join o.listOnyx oy "
                    + "WHERE "
                    + " (a.ordenTrabajo is null or a.ordenTrabajo is not null)"
                    + "and ( oy.ot_Id_Cm is null or oy.ot_Id_Cm is not null )"
                    + "and (o.estadoRegistro is null or o.estadoRegistro = 1 ) "
                    + "and (oy.estadoRegistro is null or oy.estadoRegistro = 1 )");
            //DIRECCIONES  
            sql2.append("SELECT o,a,oy, t "
                    + "FROM OtHhppMgl o "
                    + "left join o.listAgendaDireccion a "
                    + "left join o.listOnyx oy "
                    + "left join o.tecnologiaBasicaList t "
                    + "WHERE "
                    + "(a.ordenTrabajo is null or a.ordenTrabajo is not null) "
                    + "and ( oy.ot_Direccion_Id is null or  oy.ot_Direccion_Id is not null ) "
                    + "and ( t.otHhppId is null or t.otHhppId is not null ) "
                    + "and (o.estadoRegistro is null or o.estadoRegistro = 1 ) "
                    + "and (oy.estadoRegistro is null or oy.estadoRegistro = 1 )");

            generarSelectReporteOtCm(sql, params, true);
            Query q = entityManager.createQuery(sql.toString());
            agregarParametros(q, params, true);
            q.setFirstResult(firstResult);
            q.setMaxResults(maxResults);
            listaOrdenesAgendas = (List<Object[]>) q.getResultList();
            //DIRECCIONES  
            generarSelectReporteOtDIR(sql2, params, true);
            Query q2 = entityManager.createQuery(sql2.toString());
            agregarParametrosDIR(q2, params, true);
            q2.setFirstResult(firstResult);
            q2.setMaxResults(maxResults);
            listaOrdenesAgendasDir = (List<Object[]>) q2.getResultList();

            if (listaOrdenesAgendasDir != null && !listaOrdenesAgendasDir.isEmpty()
                    && listaOrdenesAgendas != null && !listaOrdenesAgendas.isEmpty()) {
                total = listaOrdenesAgendasDir.size() + listaOrdenesAgendas.size();
            } else {
                if (listaOrdenesAgendas != null && !listaOrdenesAgendas.isEmpty()) {
                    total = listaOrdenesAgendas.size();
                } else {
                    if (listaOrdenesAgendasDir != null && !listaOrdenesAgendasDir.isEmpty()) {
                        total = listaOrdenesAgendasDir.size();
                    }
                }

            }
            return total;

        } catch (NoResultException nre) {
            LOGGER.error("No se encontraron resultados para la consulta: ", nre);
            throw new ApplicationException("No se encontraron resultados para "
                    + "la consulta.");
        } catch (Exception e) {
            String msg = SE_PRODUJO_UN_ERROR_AL_MOMENTO_DE_EJECUTAR_EL_METODO + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    public List<ReporteOtCMDto> getReporteEstadoActualOtCM(HashMap<String, Object> params,
            boolean contar, int firstResult, int maxResults, String usuario, List<CmtBasicaMgl> listaBasicaDir,
            List<CmtBasicaMgl> listaBasicaCm,
            List<CmtRegionalRr> listacmtRegionalMgl,
            List<CmtComunidadRr> listacmtComunidadRr,
            List<CmtEstadoIntxExtMgl> listaEstadosIntExt) throws ApplicationException {
        try {
            StringBuilder sql = new StringBuilder();
            StringBuilder sql2 = new StringBuilder();
            List<ReporteOtCMDto> listaReporteOtCMDto = new ArrayList<>();
            List<Object[]> listaOrdenesAgendas;
            List<Object[]> listaOrdenesAgendasDir;
            int total = 0;
            // CCMM
            sql.append("SELECT o, a,oy "
                    + "FROM CmtOrdenTrabajoMgl o "
                    + "left join o.listAgendas a  "
                    + "left join o.listOnyx oy "
                    + "WHERE "
                    + " (a.ordenTrabajo is null or a.ordenTrabajo is not null)"
                    + "and ( oy.ot_Id_Cm is null or oy.ot_Id_Cm is not null ) "
                    + "and (o.estadoRegistro is null or o.estadoRegistro = 1 ) "
                    + "and (oy.estadoRegistro is null or oy.estadoRegistro = 1 )");
            //DIRECCIONES  
            sql2.append("SELECT o,a,oy, t "
                    + "FROM OtHhppMgl o "
                    + "left join o.listAgendaDireccion a "
                    + "left join o.listOnyx oy "
                    + "left join o.tecnologiaBasicaList t "
                    + "WHERE "
                    + "(a.ordenTrabajo is null or a.ordenTrabajo is not null) "
                    + "and ( oy.ot_Direccion_Id is null or  oy.ot_Direccion_Id is not null ) "
                    + "and ( t.otHhppId is null or t.otHhppId is not null ) "
                    + "and (o.estadoRegistro is null or o.estadoRegistro = 1 ) "
                    + "and (oy.estadoRegistro is null or oy.estadoRegistro = 1 )");

            generarSelectReporteOtCm(sql, params, true);
            Query q = entityManager.createQuery(sql.toString());
            agregarParametros(q, params, true);
            q.setFirstResult(firstResult);
            q.setMaxResults(maxResults);
            listaOrdenesAgendas = (List<Object[]>) q.getResultList();
            // DIRECCIONES  
            generarSelectReporteOtDIR(sql2, params, true);
            Query q2 = entityManager.createQuery(sql2.toString());
            agregarParametrosDIR(q2, params, true);
            q2.setFirstResult(firstResult);
            q2.setMaxResults(maxResults);
            listaOrdenesAgendasDir = (List<Object[]>) q2.getResultList();

            cargarListaOrdenesCM(listaOrdenesAgendas, listaBasicaCm, listaReporteOtCMDto, listaEstadosIntExt, listacmtRegionalMgl, listacmtComunidadRr);
            cargarListaOrdenesDir(listaOrdenesAgendasDir, listaBasicaDir, listaReporteOtCMDto);

            return listaReporteOtCMDto;

        } catch (NoResultException nre) {
            LOGGER.error("No se encontraron resultados para la consulta: ", nre);
            throw new ApplicationException("No se encontraron resultados para "
                    + "la consulta.");
        } catch (ApplicationException e) {
            String msg = SE_PRODUJO_UN_ERROR_AL_MOMENTO_DE_EJECUTAR_EL_METODO + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    public void cargarListaOrdenesCM(List<Object[]> listaOrdenesCm, List<CmtBasicaMgl> listaBasica,
            List<ReporteOtCMDto> listaReporteOtCMDto, List<CmtEstadoIntxExtMgl> listaEstadosIntExt,
            List<CmtRegionalRr> listacmtRegionalMgl, List<CmtComunidadRr> listacmtComunidadRr) throws ApplicationException {
        //CUENTAS MATRICES
        if (listaOrdenesCm != null && !listaOrdenesCm.isEmpty()) {

            for (Object[] orden : listaOrdenesCm) {

                ReporteOtCMDto reporteOtCMDto = new ReporteOtCMDto();
                //ordenes
                reporteOtCMDto.setOt_Id_Cm(((CmtOrdenTrabajoMgl) orden[0]).getIdOt().toString());
                reporteOtCMDto.setTipo_OT_MER(((CmtOrdenTrabajoMgl) orden[0]).getTipoOtObj() != null
                        ? ((CmtOrdenTrabajoMgl) orden[0]).getTipoOtObj().getDescTipoOt() : "");
                reporteOtCMDto.setSub_tipo_OT_MER(((CmtOrdenTrabajoMgl) orden[0]).getBasicaIdTipoTrabajo() != null
                        ? ((CmtOrdenTrabajoMgl) orden[0]).getBasicaIdTipoTrabajo().getNombreBasica() : "");
                reporteOtCMDto.setEstado_interno_OT_MER(((CmtOrdenTrabajoMgl) orden[0]).getEstadoInternoObj() != null
                        ? ((CmtOrdenTrabajoMgl) orden[0]).getEstadoInternoObj().getNombreBasica() : "");
                // estado interno externo
                if (listaEstadosIntExt != null && ((CmtOrdenTrabajoMgl) orden[0]).getEstadoInternoObj() != null) {
                    for (CmtEstadoIntxExtMgl estado : listaEstadosIntExt) {
                        if (estado.getIdEstadoInt() != null && estado.getIdEstadoInt().getBasicaId() != null && estado.getIdEstadoExt() != null
                                && ((CmtOrdenTrabajoMgl) orden[0]).getEstadoInternoObj() != null && ((CmtOrdenTrabajoMgl) orden[0]).getEstadoInternoObj().getBasicaId() != null) {
                            if (estado.getIdEstadoInt().getBasicaId().compareTo(((CmtOrdenTrabajoMgl) orden[0]).getEstadoInternoObj().getBasicaId()) == 0) {
                                reporteOtCMDto.setEstadoOt(estado.getIdEstadoExt().getNombreBasica());
                            }
                        }
                    }
                }
                SimpleDateFormat fecha_creacion_OT_MER = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss", Locale.ENGLISH);
                String fechaCreacion;
                if (((CmtOrdenTrabajoMgl) orden[0]) != null && ((CmtOrdenTrabajoMgl) orden[0]).getFechaCreacion() != null) {
                    fechaCreacion = fecha_creacion_OT_MER.format(((CmtOrdenTrabajoMgl) orden[0]).getFechaCreacion());
                    reporteOtCMDto.setFecha_creacion_OT_MER(fechaCreacion);
                }

                reporteOtCMDto.setSegmento_OT_MER(((CmtOrdenTrabajoMgl) orden[0]).getSegmento() != null
                        ? ((CmtOrdenTrabajoMgl) orden[0]).getSegmento().getNombreBasica() : "");
                reporteOtCMDto.setTecnologia_OT_MGL(((CmtOrdenTrabajoMgl) orden[0]).getBasicaIdTecnologia() != null
                        ? ((CmtOrdenTrabajoMgl) orden[0]).getBasicaIdTecnologia().getNombreBasica() : "");
                reporteOtCMDto.setCmObj(((CmtOrdenTrabajoMgl) orden[0]).getCmObj() != null
                        ? ((CmtOrdenTrabajoMgl) orden[0]).getCmObj().getCuentaId().substring(7) : "");
                if (((CmtOrdenTrabajoMgl) orden[0]).getCmObj() != null && ((CmtOrdenTrabajoMgl) orden[0]).getCmObj().getDireccionPrincipal() != null
                        && ((CmtOrdenTrabajoMgl) orden[0]).getCmObj().getDireccionPrincipal().getDireccionObj() != null) {
                    reporteOtCMDto.setDireccionMER(((CmtOrdenTrabajoMgl) orden[0]).getCmObj().getDireccionPrincipal().getDireccionObj().getDirFormatoIgac());
                }
                reporteOtCMDto.setNombreCMMER(((CmtOrdenTrabajoMgl) orden[0]).getCmObj() != null
                        ? ((CmtOrdenTrabajoMgl) orden[0]).getCmObj().getNombreCuenta() : "");
                reporteOtCMDto.setCodigoCMR(((CmtOrdenTrabajoMgl) orden[0]).getCmObj() != null
                        ? ((CmtOrdenTrabajoMgl) orden[0]).getCmObj().getNumeroCuenta().toString() : "");
                reporteOtCMDto.setUsuario_Creacion_OT_MER(((CmtOrdenTrabajoMgl) orden[0]).getUsuarioCreacionId() != null
                        ? ((CmtOrdenTrabajoMgl) orden[0]).getUsuarioCreacionId().toString() : "");
                reporteOtCMDto.setOnyx_Ot_Hija_Cm(((CmtOrdenTrabajoMgl) orden[0]).getOnyxOtHija() != null
                        ? ((CmtOrdenTrabajoMgl) orden[0]).getOnyxOtHija().toString() : "");
                String complejidadDescripcion;
                if (((CmtOrdenTrabajoMgl) orden[0]).getComplejidadServicio() != null && ((CmtOrdenTrabajoMgl) orden[0]).getComplejidadServicio().equals("A")) {
                    complejidadDescripcion = "ALTA";
                } else if (((CmtOrdenTrabajoMgl) orden[0]).getComplejidadServicio() != null && ((CmtOrdenTrabajoMgl) orden[0]).getComplejidadServicio().equals("M")) {
                    complejidadDescripcion = "MEDIA";
                } else if (((CmtOrdenTrabajoMgl) orden[0]).getComplejidadServicio() != null && ((CmtOrdenTrabajoMgl) orden[0]).getComplejidadServicio().equals("B")) {
                    complejidadDescripcion = "BAJA";
                } else {
                    complejidadDescripcion = "";
                }
                reporteOtCMDto.setComplejidadServicio(complejidadDescripcion);
                if (((CmtOrdenTrabajoMgl) orden[0]).getCmObj() != null && ((CmtOrdenTrabajoMgl) orden[0]).getCmObj().getDepartamento() != null) {
                    reporteOtCMDto.setDepartamento(((CmtOrdenTrabajoMgl) orden[0]).getCmObj().getDepartamento().getGpoNombre());
                }
                reporteOtCMDto.setUsuarioModOt(((CmtOrdenTrabajoMgl) orden[0]).getUsuarioEdicion());
                // campos nuevos
                reporteOtCMDto.setPersonaAtiendeSitio(((CmtOrdenTrabajoMgl) orden[0]).getPersonaRecVt());
                reporteOtCMDto.setTelefonoAtiendeSitio(((CmtOrdenTrabajoMgl) orden[0]).getTelPerRecVt());
                // regional
                if (listacmtRegionalMgl != null && !listacmtRegionalMgl.isEmpty()) {
                    for (CmtRegionalRr regional : listacmtRegionalMgl) {
                        if (regional.getCodigoRr() != null && ((CmtOrdenTrabajoMgl) orden[0]).getCmObj() != null
                                && ((CmtOrdenTrabajoMgl) orden[0]).getCmObj().getDivision() != null
                                && regional.getNombreRegional() != null) {
                            if (regional.getCodigoRr().equals(((CmtOrdenTrabajoMgl) orden[0]).getCmObj().getDivision())) {
                                reporteOtCMDto.setRegional(regional.getNombreRegional() + " " + "("
                                        + ((CmtOrdenTrabajoMgl) orden[0]).getCmObj().getDivision() + ")");
                            }
                        }
                    }
                }
                // ciudad
                if (listacmtComunidadRr != null && !listacmtComunidadRr.isEmpty()) {
                    for (CmtComunidadRr com : listacmtComunidadRr) {
                        if (com.getCodigoRr() != null && ((CmtOrdenTrabajoMgl) orden[0]).getCmObj() != null
                                && ((CmtOrdenTrabajoMgl) orden[0]).getCmObj().getComunidad() != null
                                && com.getNombreComunidad() != null) {
                            if (com.getCodigoRr().equals(((CmtOrdenTrabajoMgl) orden[0]).getCmObj().getComunidad())) {
                                reporteOtCMDto.setCiudad(com.getNombreComunidad() + " " + "("
                                        + ((CmtOrdenTrabajoMgl) orden[0]).getCmObj().getComunidad() + ")");
                            }
                        }

                    }
                }

                //agendamiento
                if (((CmtAgendamientoMgl) orden[1]) != null) {
                    reporteOtCMDto.setOrden_RR(((CmtAgendamientoMgl) orden[1]) != null
                            ? ((CmtAgendamientoMgl) orden[1]).getIdOtenrr() : "");
                    //sub tipo ordenes ofsc
                    if (listaBasica != null && listaBasica.isEmpty()) {
                        for (CmtBasicaMgl cmtBasicaMgl : listaBasica) {
                            if (((CmtAgendamientoMgl) orden[1]).getSubTipoWorkFoce() != null && cmtBasicaMgl.getCodigoBasica() != null) {
                                if (((CmtAgendamientoMgl) orden[1]).getSubTipoWorkFoce().equals(cmtBasicaMgl.getCodigoBasica())) {
                                    reporteOtCMDto.setSubTipo_Orden_OFSC(((CmtAgendamientoMgl) orden[1]).getSubTipoWorkFoce().trim());
                                    break;
                                }
                            }
                        }
                    }

                    SimpleDateFormat fechaAgendaFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    String fechaAgenda;
                    if (((CmtAgendamientoMgl) orden[1]) != null && ((CmtAgendamientoMgl) orden[1]).getFechaAgenda() != null) {
                        fechaAgenda = fechaAgendaFormat.format(((CmtAgendamientoMgl) orden[1]).getFechaAgenda());
                        reporteOtCMDto.setFecha_agenda_OFSC(fechaAgenda);
                    }

                    reporteOtCMDto.setUsuario_creacion_agenda_OFSC((((CmtAgendamientoMgl) orden[1]) != null
                            ? ((CmtAgendamientoMgl) orden[1]).getUsuarioCreacion() : ""));
                    String timeS = ((CmtAgendamientoMgl) orden[1]) != null ? ((CmtAgendamientoMgl) orden[1]).getTimeSlot() : "";
                    if (timeS != null && !timeS.equals("")) {
                        if (timeS.contains("Durante")) {
                            reporteOtCMDto.setTime_slot_OFSC(timeS);
                        } else {
                            timeS = timeS.replace("-", "_");
                            reporteOtCMDto.setTime_slot_OFSC(timeS);
                        }
                    }
                    reporteOtCMDto.setAppt_number_OFSC(((CmtAgendamientoMgl) orden[1]) != null
                            ? ((CmtAgendamientoMgl) orden[1]).getOfpsOtId() : "");
                    reporteOtCMDto.setEstado_agenda_OFSC(((CmtAgendamientoMgl) orden[1]) != null
                            ? ((CmtAgendamientoMgl) orden[1]).getBasicaIdEstadoAgenda().getNombreBasica() : "");
                    SimpleDateFormat fecha_inicia_agenda = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
                    String fechaInivioVt;
                    if (((CmtAgendamientoMgl) orden[1]) != null && ((CmtAgendamientoMgl) orden[1]).getFechaInivioVt() != null) {
                        fechaInivioVt = fecha_inicia_agenda.format(((CmtAgendamientoMgl) orden[1]).getFechaInivioVt());
                        reporteOtCMDto.setFecha_inicia_agenda_OFSC(fechaInivioVt);
                    }

                    SimpleDateFormat fecha_fin_agenda = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
                    String fechaFinVt;
                    if (((CmtAgendamientoMgl) orden[1]) != null && ((CmtAgendamientoMgl) orden[1]).getFechaFinVt() != null) {
                        fechaFinVt = fecha_fin_agenda.format(((CmtAgendamientoMgl) orden[1]).getFechaFinVt());
                        reporteOtCMDto.setFecha_fin_agenda_OFSC(fechaFinVt);
                    }

                    reporteOtCMDto.setId_aliado_OFSC((((CmtAgendamientoMgl) orden[1]) != null
                            ? ((CmtAgendamientoMgl) orden[1]).getIdentificacionAliado() : ""));
                    reporteOtCMDto.setNombre_aliado_OFSC(((CmtAgendamientoMgl) orden[1]) != null
                            ? ((CmtAgendamientoMgl) orden[1]).getNombreAliado() : "");
                    reporteOtCMDto.setId_tecnico_aliado_OFSC((((CmtAgendamientoMgl) orden[1]) != null
                            ? ((CmtAgendamientoMgl) orden[1]).getIdentificacionTecnico() : ""));
                    reporteOtCMDto.setNombre_tecnico_aliado_OFSC((((CmtAgendamientoMgl) orden[1]) != null
                            ? ((CmtAgendamientoMgl) orden[1]).getNombreTecnico() : ""));
                    reporteOtCMDto.setUltima_agenda_multiagenda((((CmtAgendamientoMgl) orden[1]) != null
                            ? ((CmtAgendamientoMgl) orden[1]).getUltimaAgenda() : ""));
                    // observaciones 
                    String obs = (((CmtAgendamientoMgl) orden[1]) != null
                            ? ((CmtAgendamientoMgl) orden[1]).getObservacionesTecnico() : "");
                    if (obs != null && !obs.equals("")) {
                        obs = StringUtils.caracteresEspeciales(obs);
                        reporteOtCMDto.setObservaciones_tecnico_OFSC(obs);
                    }

                    SimpleDateFormat fechaAsigTecnico = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
                    String fechaTecnico;
                    if (((CmtAgendamientoMgl) orden[1]) != null && ((CmtAgendamientoMgl) orden[1]).getFechaAsigTecnico() != null) {
                        fechaTecnico = fechaAsigTecnico.format(((CmtAgendamientoMgl) orden[1]).getFechaAsigTecnico());
                        reporteOtCMDto.setFechaAsigTecnico(fechaTecnico);
                    }

                    reporteOtCMDto.setUsuarioModAgenda(((CmtAgendamientoMgl) orden[1]).getUsuarioEdicion());
                    // indicador de cumplimiento
                    String cumplimiento = "No se cumplio";
                    if (((OnyxOtCmDir) orden[2]) != null && ((OnyxOtCmDir) orden[2]).getFecha_Compromiso_Ot_Padre_Onyx() != null
                            && ((CmtAgendamientoMgl) orden[1]) != null && ((CmtAgendamientoMgl) orden[1]).getFechaAgenda() != null) {
                        if (((CmtAgendamientoMgl) orden[1]).getUltimaAgenda() != null && ((CmtAgendamientoMgl) orden[1]).getUltimaAgenda().equals("Y")) {
                            if (((CmtAgendamientoMgl) orden[1]).getFechaAgenda().before(((OnyxOtCmDir) orden[2]).getFecha_Compromiso_Ot_Padre_Onyx())) {
                                cumplimiento = "Si se cumplió";
                            }
                        }
                        reporteOtCMDto.setIndicadorCumplimiento(cumplimiento);
                    }
                    //resultado agenda
                    reporteOtCMDto.setResultadoOrden(((CmtAgendamientoMgl) orden[1]).getBasicaIdrazones() != null
                            ? ((CmtAgendamientoMgl) orden[1]).getBasicaIdrazones().getNombreBasica() : "");

                    //cantidad reagenda 
                    if (((CmtAgendamientoMgl) orden[1]).getCantAgendas() != null) {
                        reporteOtCMDto.setCantReagenda(((CmtAgendamientoMgl) orden[1]).getCantAgendas());
                    }
                    //Motivos 
                    if (((CmtAgendamientoMgl) orden[1]).getMotivosReagenda() != null) {
                        reporteOtCMDto.setMotivosReagenda(((CmtAgendamientoMgl) orden[1]).getMotivosReagenda());
                    }

                    //tiempo ejecucion
                    if (((CmtAgendamientoMgl) orden[1]).getFechaInivioVt() != null
                            && ((CmtAgendamientoMgl) orden[1]).getFechaFinVt() != null) {
                        String tiempoEjecucion = DateUtils.getHoraMinEntreFechasConFormato(
                                ((CmtAgendamientoMgl) orden[1]).getFechaInivioVt(), ((CmtAgendamientoMgl) orden[1]).getFechaFinVt());
                        reporteOtCMDto.setTiempoEjecucion(tiempoEjecucion);
                    }
                    //conveniencia
                    if (((CmtAgendamientoMgl) orden[1]).getConveniencia() != null) {
                        reporteOtCMDto.setConveniencia(((CmtAgendamientoMgl) orden[1]).getConveniencia());
                    }

                }
                // Onyx
                if (((OnyxOtCmDir) orden[2]) != null) {
                    reporteOtCMDto.setNit_Cliente_Onyx((((OnyxOtCmDir) orden[2]) != null
                            ? ((OnyxOtCmDir) orden[2]).getNit_Cliente_Onyx() : ""));
                    // nombreCiente
                    String nombreCiente = (((OnyxOtCmDir) orden[2]) != null
                            ? ((OnyxOtCmDir) orden[2]).getNombre_Cliente_Onyx() : "");
                    if (nombreCiente != null && !nombreCiente.equals("")) {
                        nombreCiente = StringUtils.caracteresEspeciales(nombreCiente);
                        reporteOtCMDto.setNombre_Cliente_Onyx(nombreCiente);
                    }

                    // nombreOtHija 
                    String nombreOtHija = (((OnyxOtCmDir) orden[2]) != null
                            ? ((OnyxOtCmDir) orden[2]).getNombre_Ot_Hija_Onyx() : "");
                    if (nombreOtHija != null && !nombreOtHija.equals("")) {
                        nombreOtHija = StringUtils.caracteresEspeciales(nombreOtHija);
                        reporteOtCMDto.setNombre_Ot_Hija_Onyx(nombreOtHija);
                    }
                    reporteOtCMDto.setOnyx_Ot_Hija_Cm((((OnyxOtCmDir) orden[2]) != null
                            ? ((OnyxOtCmDir) orden[2]).getOnyx_Ot_Hija_Cm() : ""));
                    reporteOtCMDto.setOnyx_Ot_Padre_Cm(((OnyxOtCmDir) orden[2]) != null
                            ? ((OnyxOtCmDir) orden[2]).getOnyx_Ot_Padre_Cm() : "");
                    SimpleDateFormat creacion_Ot_Hija_Onyx = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    String fecha_Creacion_Ot_Hija_Onyx;
                    if (((OnyxOtCmDir) orden[2]) != null && ((OnyxOtCmDir) orden[2]).getFecha_Creacion_Ot_Hija_Onyx() != null) {
                        fecha_Creacion_Ot_Hija_Onyx = creacion_Ot_Hija_Onyx.format(((OnyxOtCmDir) orden[2]).getFecha_Creacion_Ot_Hija_Onyx());
                        reporteOtCMDto.setFecha_Creacion_Ot_Hija_Onyx(fecha_Creacion_Ot_Hija_Onyx);
                    }

                    reporteOtCMDto.setDescripcion_Onyx((((OnyxOtCmDir) orden[2]) != null
                            ? ((OnyxOtCmDir) orden[2]).getDescripcion_Onyx() : ""));
                    reporteOtCMDto.setSegmento_Onyx((((OnyxOtCmDir) orden[2]) != null
                            ? ((OnyxOtCmDir) orden[2]).getSegmento_Onyx() : ""));
                    reporteOtCMDto.setTipo_Servicio_Onyx(((OnyxOtCmDir) orden[2]) != null
                            ? (((OnyxOtCmDir) orden[2]).getTipo_Servicio_Onyx()) : "");
                    reporteOtCMDto.setServicios_Onyx((((OnyxOtCmDir) orden[2]) != null
                            ? ((OnyxOtCmDir) orden[2]).getServicios_Onyx() : ""));
                    reporteOtCMDto.setRecurrente_Mensual_Onyx((((OnyxOtCmDir) orden[2]) != null
                            ? ((OnyxOtCmDir) orden[2]).getRecurrente_Mensual_Onyx() : ""));
                    reporteOtCMDto.setCodigo_Servicio_Onyx((((OnyxOtCmDir) orden[2]) != null
                            ? ((OnyxOtCmDir) orden[2]).getCodigo_Servicio_Onyx() : ""));
                    reporteOtCMDto.setVendedor_Onyx((((OnyxOtCmDir) orden[2]) != null
                            ? ((OnyxOtCmDir) orden[2]).getVendedor_Onyx() : ""));
                    // Telefono 
                    String telefonoVend = (((OnyxOtCmDir) orden[2]) != null
                            ? ((OnyxOtCmDir) orden[2]).getTelefono_Vendedor_Onyx() : "");
                    if (telefonoVend != null && !telefonoVend.equals("")) {
                        telefonoVend = StringUtils.caracteresEspeciales(telefonoVend);
                        reporteOtCMDto.setTelefono_Vendedor_Onyx(telefonoVend);
                    }
                    reporteOtCMDto.setEstado_Ot_Hija_Onyx_Cm((((OnyxOtCmDir) orden[2]) != null
                            ? ((OnyxOtCmDir) orden[2]).getEstado_Ot_Hija_Onyx_Cm() : ""));
                    reporteOtCMDto.setEstado_Ot_Padre_Onyx_Cm((((OnyxOtCmDir) orden[2]) != null
                            ? ((OnyxOtCmDir) orden[2]).getEstado_Ot_Padre_Onyx_Cm() : ""));
                    SimpleDateFormat compromiso_Ot_Padre_Onyx = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
                    String fecha_Compromiso_Ot_Padre_Onyx;
                    if (((OnyxOtCmDir) orden[2]) != null && ((OnyxOtCmDir) orden[2]).getFecha_Compromiso_Ot_Padre_Onyx() != null) {
                        fecha_Compromiso_Ot_Padre_Onyx = compromiso_Ot_Padre_Onyx.format(((OnyxOtCmDir) orden[2]).getFecha_Compromiso_Ot_Padre_Onyx());
                        reporteOtCMDto.setFecha_Compromiso_Ot_Padre_Onyx(fecha_Compromiso_Ot_Padre_Onyx);
                    }

                    reporteOtCMDto.setOt_Hija_Resolucion_1_Onyx((((OnyxOtCmDir) orden[2]) != null
                            ? ((OnyxOtCmDir) orden[2]).getOt_Hija_Resolucion_1_Onyx() : ""));
                    reporteOtCMDto.setOt_Hija_Resolucion_2_Onyx((((OnyxOtCmDir) orden[2]) != null
                            ? ((OnyxOtCmDir) orden[2]).getOt_Hija_Resolucion_2_Onyx() : ""));
                    reporteOtCMDto.setOt_Hija_Resolucion_3_Onyx((((OnyxOtCmDir) orden[2]) != null
                            ? ((OnyxOtCmDir) orden[2]).getOt_Hija_Resolucion_3_Onyx() : ""));
                    reporteOtCMDto.setOt_Hija_Resolucion_4_Onyx((((OnyxOtCmDir) orden[2]) != null
                            ? ((OnyxOtCmDir) orden[2]).getOt_Hija_Resolucion_4_Onyx() : ""));
                    reporteOtCMDto.setOt_Padre_Resolucion_1_Onyx((((OnyxOtCmDir) orden[2]) != null
                            ? ((OnyxOtCmDir) orden[2]).getOt_Padre_Resolucion_1_Onyx() : ""));
                    reporteOtCMDto.setOt_Padre_Resolucion_2_Onyx((((OnyxOtCmDir) orden[2]) != null
                            ? ((OnyxOtCmDir) orden[2]).getOt_Padre_Resolucion_2_Onyx() : ""));
                    reporteOtCMDto.setOt_Padre_Resolucion_3_Onyx((((OnyxOtCmDir) orden[2]) != null
                            ? ((OnyxOtCmDir) orden[2]).getOt_Padre_Resolucion_3_Onyx() : ""));
                    reporteOtCMDto.setOt_Padre_Resolucion_4_Onyx((((OnyxOtCmDir) orden[2]) != null
                            ? ((OnyxOtCmDir) orden[2]).getOt_Padre_Resolucion_4_Onyx() : ""));
                    SimpleDateFormat fecha_Creacion_Onyx = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss", Locale.ENGLISH);
                    String fecha_Creacion_Ot_Onyx;
                    if (((OnyxOtCmDir) orden[2]) != null && ((OnyxOtCmDir) orden[2]).getFechaCreacion() != null) {
                        fecha_Creacion_Ot_Onyx = fecha_Creacion_Onyx.format(((OnyxOtCmDir) orden[2]).getFechaCreacion());
                        reporteOtCMDto.setFechaCreacionOtOnyx(fecha_Creacion_Ot_Onyx);
                    }

                    SimpleDateFormat fecha_Padre_Onyx = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.ENGLISH);
                    String fecha_Creacion_Ot_Padre_Onyx;
                    if (((OnyxOtCmDir) orden[2]) != null && ((OnyxOtCmDir) orden[2]).getFecha_Creacion_Ot_Padre_Onyx() != null) {
                        fecha_Creacion_Ot_Padre_Onyx = fecha_Padre_Onyx.format(((OnyxOtCmDir) orden[2]).getFecha_Creacion_Ot_Padre_Onyx());
                        reporteOtCMDto.setFecha_Creacion_Ot_Padre_Onyx(fecha_Creacion_Ot_Padre_Onyx);
                    }

                    reporteOtCMDto.setContacto_Tecnico_Ot_Padre_Onyx((((OnyxOtCmDir) orden[2]) != null
                            ? ((OnyxOtCmDir) orden[2]).getContacto_Tecnico_Ot_Padre_Onyx() : ""));
                    // Telefono 
                    String telefono = (((OnyxOtCmDir) orden[2]) != null
                            ? ((OnyxOtCmDir) orden[2]).getTelefono_Tecnico_Ot_Padre_Onyx() : "");
                    if (telefono != null && !telefono.equals("")) {
                        telefono = StringUtils.caracteresEspeciales(telefono);
                        reporteOtCMDto.setTelefono_Tecnico_Ot_Padre_Onyx(telefono);
                    }

                    //CAMPOS NUEVOS ONYX
                    reporteOtCMDto.setCodigoProyecto((((OnyxOtCmDir) orden[2]) != null
                            ? ((OnyxOtCmDir) orden[2]).getCodigoProyecto() : ""));
                    // direccion Onyx
                    String direccion = ((OnyxOtCmDir) orden[2]).getDireccion_Onyx();
                    if (direccion != null && !direccion.equals("")) {
                        direccion = StringUtils.caracteresEspeciales(direccion);
                        reporteOtCMDto.setDireccionFact(direccion);
                    }

                    reporteOtCMDto.setContactoTecnicoOTP((((OnyxOtCmDir) orden[2]) != null
                            ? ((OnyxOtCmDir) orden[2]).getContacto_Tecnico_Ot_Padre_Onyx() : ""));
                    reporteOtCMDto.setEmailCTec((((OnyxOtCmDir) orden[2]) != null
                            ? ((OnyxOtCmDir) orden[2]).getEmailTec() : ""));

                    //tiempo de programacion
                    if (((CmtAgendamientoMgl) orden[1]) != null && ((CmtAgendamientoMgl) orden[1]).getFechaAgenda() != null
                            && ((OnyxOtCmDir) orden[2]).getFecha_Creacion_Ot_Hija_Onyx() != null) {
                        String fecha = DateUtils.getTiempoEntreFechasConFormato(((OnyxOtCmDir) orden[2]).getFecha_Creacion_Ot_Hija_Onyx(), ((CmtAgendamientoMgl) orden[1]).getFechaAgenda());
                        reporteOtCMDto.setTiempoProgramacion(fecha);

                    }
                    //tiempo de atencion
                    if (((OnyxOtCmDir) orden[2]) != null && ((OnyxOtCmDir) orden[2]).getFecha_Creacion_Ot_Hija_Onyx() != null
                            && ((CmtAgendamientoMgl) orden[1]) != null && ((CmtAgendamientoMgl) orden[1]).getFechaAgenda() != null) {
                        String timeSlot = ((CmtAgendamientoMgl) orden[1]).getTimeSlot() == null ? ""
                                : ((CmtAgendamientoMgl) orden[1]).getTimeSlot().contains("Durante") ? ""
                                : ((CmtAgendamientoMgl) orden[1]).getTimeSlot();
                        if (timeSlot != null && !timeSlot.equals("")) {
                            if (timeSlot.contains("Durante")) {
                                timeSlot = "00";
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(((CmtAgendamientoMgl) orden[1]).getFechaAgenda()); //tuFechaBase es un Date;
                                calendar.add(Calendar.HOUR, Integer.parseInt(timeSlot)); //minutosASumar es int.
                                String horasTiempoAtiende = DateUtils.getTiempoEntreFechasTimeSlot(((CmtAgendamientoMgl) orden[1]).getFechaAgenda(), ((OnyxOtCmDir) orden[2]).getFecha_Creacion_Ot_Hija_Onyx(), timeSlot);
                                reporteOtCMDto.setTiempoAtencion(horasTiempoAtiende);
                            } else {
                                timeSlot = ((CmtAgendamientoMgl) orden[1]).getTimeSlot().substring(0, 2);
                                String horasTiempoAtiende = DateUtils.getTiempoEntreFechasTimeSlot(((CmtAgendamientoMgl) orden[1]).getFechaAgenda(), ((OnyxOtCmDir) orden[2]).getFecha_Creacion_Ot_Hija_Onyx(), timeSlot);
                                reporteOtCMDto.setTiempoAtencion(horasTiempoAtiende);
                            }
                        }
                    }

                    //antiguedad de la agenda
                    if (((OnyxOtCmDir) orden[2]).getFecha_Creacion_Ot_Padre_Onyx() != null) {
                        String antiguedad = DateUtils.getTiempoEntreFechasConFormato(((OnyxOtCmDir) orden[2]).getFecha_Creacion_Ot_Padre_Onyx(), new Date());
                        reporteOtCMDto.setAntiguedadOrden(antiguedad);
                    }

                    //aliado Implementacion 
                    if (((OnyxOtCmDir) orden[2]).getaImplement() != null) {
                        reporteOtCMDto.setaImplement(((OnyxOtCmDir) orden[2]).getaImplement());
                    }
                    //tipo solucion
                    if (((OnyxOtCmDir) orden[2]).getTipo_Servicio_Onyx() != null) {
                        reporteOtCMDto.setTipo_Servicio_Onyx(((OnyxOtCmDir) orden[2]).getTipo_Servicio_Onyx());
                    }
                    //ciudad de fact
                    if (((OnyxOtCmDir) orden[2]).getCiudadFact() != null) {
                        reporteOtCMDto.setCiudadFacturacion(((OnyxOtCmDir) orden[2]).getCiudadFact());
                    }

                }
                listaReporteOtCMDto.add(reporteOtCMDto);

            }
        }

    }

    public void cargarListaOrdenesDir(List<Object[]> listaOrdenesDir, List<CmtBasicaMgl> listaBasicaDir, List<ReporteOtCMDto> listaReporteOtCMDto) throws ApplicationException {

        //DIRECCIONES
        if (listaOrdenesDir != null && !listaOrdenesDir.isEmpty()) {
            for (Object[] orden : listaOrdenesDir) {

                ReporteOtCMDto reporteOtCMDto = new ReporteOtCMDto();
                if (((OtHhppMgl) orden[0]) != null) {

                    reporteOtCMDto.setOt_Id_Cm(((OtHhppMgl) orden[0]).getOtHhppId() != null
                            ? ((OtHhppMgl) orden[0]).getOtHhppId().toString() : "");
                    reporteOtCMDto.setTipo_OT_MER(((OtHhppMgl) orden[0]).getTipoOtHhppId() != null
                            ? ((OtHhppMgl) orden[0]).getTipoOtHhppId().getNombreTipoOt() : "");
                    reporteOtCMDto.setSub_tipo_OT_MER(((OtHhppMgl) orden[0]).getTipoOtHhppId() != null
                            && ((OtHhppMgl) orden[0]).getTipoOtHhppId().getTipoTrabajoOFSC() != null
                            ? ((OtHhppMgl) orden[0]).getTipoOtHhppId().getTipoTrabajoOFSC().getNombreBasica() : "");
                    reporteOtCMDto.setSubTipo_Orden_OFSC(((OtHhppMgl) orden[0]).getTipoOtHhppId() != null
                            && ((OtHhppMgl) orden[0]).getTipoOtHhppId().getSubTipoOrdenOFSC() != null
                            ? ((OtHhppMgl) orden[0]).getTipoOtHhppId().getSubTipoOrdenOFSC().getNombreBasica() : "");
                    reporteOtCMDto.setEstadoOt(((OtHhppMgl) orden[0]).getEstadoGeneral() != null
                            ? ((OtHhppMgl) orden[0]).getEstadoGeneral().getNombreBasica() : "");
                    reporteOtCMDto.setEstado_interno_OT_MER(((OtHhppMgl) orden[0]).getEstadoInternoInicial() == null ? ""
                            : ((OtHhppMgl) orden[0]).getEstadoInternoInicial().getNombreBasica());
                    SimpleDateFormat fecha_creacion_OT_MER = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
                    String getFechaCreacionMer;
                    if (((OtHhppMgl) orden[0]) != null && ((OtHhppMgl) orden[0]).getFechaCreacionOt() != null) {
                        getFechaCreacionMer = fecha_creacion_OT_MER.format(((OtHhppMgl) orden[0]).getFechaCreacionOt());
                        reporteOtCMDto.setFecha_creacion_OT_MER(getFechaCreacionMer);
                    }

                    String listaTecno = "";
                    List<OtHhppTecnologiaMgl> listaTecnologias;
                    listaTecnologias = ((OtHhppMgl) orden[0]).getTecnologiaBasicaList();
                    if (listaTecnologias != null && !listaTecnologias.isEmpty()) {
                        for (OtHhppTecnologiaMgl otHhppTecnologiaMgl : listaTecnologias) {
                            if (otHhppTecnologiaMgl.getTecnoglogiaBasicaId() != null) {
                                listaTecno += otHhppTecnologiaMgl.getTecnoglogiaBasicaId().getNombreBasica() + ",";
                            }
                        }
                        if (!listaTecno.isEmpty()) {
                            listaTecno = listaTecno.substring(0, listaTecno.length() - 1);
                        }
                        reporteOtCMDto.setTecnologia_OT_MGL(listaTecno);
                    }

                    if (((OtHhppMgl) orden[0]).getSubDireccionId() != null) {
                        reporteOtCMDto.setDireccionMER(((OtHhppMgl) orden[0]).getSubDireccionId().getSdiFormatoIgac());
                    } else {
                        if (((OtHhppMgl) orden[0]).getDireccionId() != null) {
                            reporteOtCMDto.setDireccionMER(((OtHhppMgl) orden[0]).getDireccionId().getDirFormatoIgac());
                        }
                    }

                    reporteOtCMDto.setUsuario_Creacion_OT_MER((((OtHhppMgl) orden[0]).getUsuarioCreacion() != null
                            ? ((OtHhppMgl) orden[0]).getUsuarioCreacion() : ""));
                    reporteOtCMDto.setOnyx_Ot_Hija_Cm(((OtHhppMgl) orden[0]).getOnyxOtHija() != null
                            ? ((OtHhppMgl) orden[0]).getOnyxOtHija().toString() : "");
                    String complejidadDescripcion;
                    if (((OtHhppMgl) orden[0]).getComplejidadServicio() != null && ((OtHhppMgl) orden[0]).getComplejidadServicio().equals("A")) {
                        complejidadDescripcion = "ALTA";
                    } else if (((OtHhppMgl) orden[0]).getComplejidadServicio() != null && ((OtHhppMgl) orden[0]).getComplejidadServicio().equals("M")) {
                        complejidadDescripcion = "MEDIA";
                    } else if (((OtHhppMgl) orden[0]).getComplejidadServicio() != null && ((OtHhppMgl) orden[0]).getComplejidadServicio().equals("B")) {
                        complejidadDescripcion = "BAJA";
                    } else {
                        complejidadDescripcion = "";
                    }

                    reporteOtCMDto.setComplejidadServicio(complejidadDescripcion);
                    reporteOtCMDto.setUsuarioModOt(((OtHhppMgl) orden[0]).getUsuarioEdicion());
                    reporteOtCMDto.setSegmento_OT_MER(((OtHhppMgl) orden[0]).getSegmento() != null
                            ? ((OtHhppMgl) orden[0]).getSegmento().getNombreBasica() : "");

                }

                //agendamiento
                if (((MglAgendaDireccion) orden[1]) != null) {

                    reporteOtCMDto.setOrden_RR(((MglAgendaDireccion) orden[1]).getIdOtenrr() != null
                            ? ((MglAgendaDireccion) orden[1]).getIdOtenrr() : "");
                    //sub tipo ordenes ofsc
                    if (listaBasicaDir != null && !listaBasicaDir.isEmpty()) {
                        for (CmtBasicaMgl cmtBasicaMgl : listaBasicaDir) {
                            if (cmtBasicaMgl.getCodigoBasica() != null && ((MglAgendaDireccion) orden[1]).getSubTipoWorkFoce() != null) {
                                if (((MglAgendaDireccion) orden[1]).getSubTipoWorkFoce().equals(cmtBasicaMgl.getCodigoBasica())) {
                                    reporteOtCMDto.setSubTipo_Orden_OFSC(((MglAgendaDireccion) orden[1]).getSubTipoWorkFoce().trim());
                                    break;
                                }
                            }

                        }
                    }

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
                    String fechaCreacion;
                    if (((MglAgendaDireccion) orden[1]) != null && ((MglAgendaDireccion) orden[1]).getFechaCreacion() != null) {
                        fechaCreacion = dateFormat.format(((MglAgendaDireccion) orden[1]).getFechaAgenda());
                        reporteOtCMDto.setFecha_agenda_OFSC(fechaCreacion);
                    }

                    reporteOtCMDto.setUsuario_creacion_agenda_OFSC((((MglAgendaDireccion) orden[1]).getUsuarioCreacion() != null
                            ? ((MglAgendaDireccion) orden[1]).getUsuarioCreacion() : ""));
                    String timeS = ((MglAgendaDireccion) orden[1]) != null ? ((MglAgendaDireccion) orden[1]).getTimeSlot() : "";
                    if (timeS != null && !timeS.equals("")) {
                        if (timeS.contains("Durante")) {
                            reporteOtCMDto.setTime_slot_OFSC(timeS);
                        } else {
                            timeS = timeS.replace("-", "_");
                            reporteOtCMDto.setTime_slot_OFSC(timeS);
                        }
                    }
                    reporteOtCMDto.setEstado_agenda_OFSC(((MglAgendaDireccion) orden[1]).getBasicaIdEstadoAgenda() != null
                            ? ((MglAgendaDireccion) orden[1]).getBasicaIdEstadoAgenda().getNombreBasica() : "");
                    SimpleDateFormat fecha_inicia_agenda = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
                    String fechaInivioVt;
                    if (((MglAgendaDireccion) orden[1]) != null && ((MglAgendaDireccion) orden[1]).getFechaInivioVt() != null) {
                        fechaInivioVt = fecha_inicia_agenda.format(((MglAgendaDireccion) orden[1]).getFechaInivioVt());
                        reporteOtCMDto.setFecha_inicia_agenda_OFSC(fechaInivioVt);
                    }

                    SimpleDateFormat fecha_fin_agenda = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
                    String fechaFinVt;
                    if (((MglAgendaDireccion) orden[1]) != null && ((MglAgendaDireccion) orden[1]).getFechaFinVt() != null) {
                        fechaFinVt = fecha_fin_agenda.format(((MglAgendaDireccion) orden[1]).getFechaFinVt());
                        reporteOtCMDto.setFecha_fin_agenda_OFSC(fechaFinVt);
                    }

                    reporteOtCMDto.setId_aliado_OFSC((((MglAgendaDireccion) orden[1]).getIdentificacionAliado() != null
                            ? ((MglAgendaDireccion) orden[1]).getIdentificacionAliado() : ""));
                    reporteOtCMDto.setNombre_aliado_OFSC((((MglAgendaDireccion) orden[1]) != null
                            ? ((MglAgendaDireccion) orden[1]).getNombreAliado() : ""));

                    reporteOtCMDto.setId_tecnico_aliado_OFSC((((MglAgendaDireccion) orden[1]).getIdentificacionTecnico() != null
                            ? ((MglAgendaDireccion) orden[1]).getIdentificacionTecnico() : ""));
                    reporteOtCMDto.setNombre_tecnico_aliado_OFSC((((MglAgendaDireccion) orden[1]).getNombreTecnico() != null
                            ? ((MglAgendaDireccion) orden[1]).getNombreTecnico() : ""));
                    reporteOtCMDto.setUltima_agenda_multiagenda((((MglAgendaDireccion) orden[1]).getUltimaAgenda() != null
                            ? ((MglAgendaDireccion) orden[1]).getUltimaAgenda() : ""));
                    // observaciones 
                    String obs = ((MglAgendaDireccion) orden[1]).getObservacionesTecnico();
                    if (obs != null && !obs.equals("")) {
                        obs = StringUtils.caracteresEspeciales(obs);
                        reporteOtCMDto.setObservaciones_tecnico_OFSC(obs);
                    }

                    reporteOtCMDto.setAppt_number_OFSC(((MglAgendaDireccion) orden[1]) != null
                            ? ((MglAgendaDireccion) orden[1]).getOfpsOtId() : "");

                    reporteOtCMDto.setUsuarioModAgenda(((MglAgendaDireccion) orden[1]).getUsuarioEdicion());

                    //CAMPOS NUEVOS
                    reporteOtCMDto.setPersonaAtiendeSitio(((MglAgendaDireccion) orden[1]).getPersonaRecVt());
                    reporteOtCMDto.setTelefonoAtiendeSitio(((MglAgendaDireccion) orden[1]).getTelPerRecVt());
                    //tiempo ejecucion
                    if (((MglAgendaDireccion) orden[1]).getFechaInivioVt() != null && ((MglAgendaDireccion) orden[1]).getFechaFinVt() != null) {
                        String tiempoEjecucion = DateUtils.getHoraMinEntreFechasConFormato(((MglAgendaDireccion) orden[1]).getFechaInivioVt(), ((MglAgendaDireccion) orden[1]).getFechaFinVt());
                        reporteOtCMDto.setTiempoEjecucion(tiempoEjecucion);
                    }
                    //resultado agenda
                    reporteOtCMDto.setResultadoOrden((((MglAgendaDireccion) orden[1]) != null
                            && ((MglAgendaDireccion) orden[1]).getBasicaIdrazones() != null
                            ? ((MglAgendaDireccion) orden[1]).getBasicaIdrazones().getNombreBasica() : ""));

                    //cantidad reagenda 
                    if (((MglAgendaDireccion) orden[1]).getCantAgendas() != null) {
                        reporteOtCMDto.setCantReagenda(((MglAgendaDireccion) orden[1]).getCantAgendas());
                    }
                    //Motivos  
                    if (((MglAgendaDireccion) orden[1]).getMotivosReagenda() != null) {
                        reporteOtCMDto.setMotivosReagenda(((MglAgendaDireccion) orden[1]).getMotivosReagenda());
                    }

                    //tiempo ejecucion
                    if (((MglAgendaDireccion) orden[1]).getFechaInivioVt() != null && ((MglAgendaDireccion) orden[1]).getFechaFinVt() != null) {
                        String tiempoEjecucion = DateUtils.getHoraMinEntreFechasConFormato(((MglAgendaDireccion) orden[1]).getFechaInivioVt(), ((MglAgendaDireccion) orden[1]).getFechaFinVt());
                        reporteOtCMDto.setTiempoEjecucion(tiempoEjecucion);
                    }
                }
                // onyx
                if (((OnyxOtCmDir) orden[2]) != null) {

                    reporteOtCMDto.setNit_Cliente_Onyx((((OnyxOtCmDir) orden[2]).getNit_Cliente_Onyx() != null
                            ? ((OnyxOtCmDir) orden[2]).getNit_Cliente_Onyx() : ""));
                    // nombreCiente
                    String nombreCienteOnyx = ((OnyxOtCmDir) orden[2]).getNombre_Cliente_Onyx() != null
                            ? ((OnyxOtCmDir) orden[2]).getNombre_Cliente_Onyx() : "";
                    if (nombreCienteOnyx != null && !nombreCienteOnyx.equals("")) {
                        nombreCienteOnyx = StringUtils.caracteresEspeciales(nombreCienteOnyx);
                    }
                    reporteOtCMDto.setNombre_Cliente_Onyx(nombreCienteOnyx);
                    // nombreOtHija 
                    String nombreOtHija = ((OnyxOtCmDir) orden[2]).getNombre_Ot_Hija_Onyx();
                    if (nombreOtHija != null && !nombreOtHija.equals("")) {
                        nombreOtHija = StringUtils.caracteresEspeciales(nombreOtHija);
                    }
                    reporteOtCMDto.setNombre_Ot_Hija_Onyx(nombreOtHija);
                    //ot hija direcciones
                    reporteOtCMDto.setOnyx_Ot_Padre_Cm(((OnyxOtCmDir) orden[2]).getOnyx_Ot_Hija_Dir() != null
                            ? ((OnyxOtCmDir) orden[2]).getOnyx_Ot_Hija_Dir() : "");
                    reporteOtCMDto.setOnyx_Ot_Hija_Cm(((OnyxOtCmDir) orden[2]).getOnyx_Ot_Padre_Dir() != null
                            ? ((OnyxOtCmDir) orden[2]).getOnyx_Ot_Padre_Dir() : "");
                    SimpleDateFormat creacion_Ot_Hija_Onyx = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    String fecha_Creacion_Ot_Hija_Onyx;
                    if (((OnyxOtCmDir) orden[2]) != null && ((OnyxOtCmDir) orden[2]).getFecha_Creacion_Ot_Hija_Onyx() != null) {
                        fecha_Creacion_Ot_Hija_Onyx = creacion_Ot_Hija_Onyx.format(((OnyxOtCmDir) orden[2]).getFecha_Creacion_Ot_Hija_Onyx());
                        reporteOtCMDto.setFecha_Creacion_Ot_Hija_Onyx(fecha_Creacion_Ot_Hija_Onyx);
                    }

                    // fecha creacion onyx
                    String fecha_Creacion_Ot_Onyx;
                    if (((OnyxOtCmDir) orden[2]) != null && ((OnyxOtCmDir) orden[2]).getFechaCreacion() != null) {
                        fecha_Creacion_Ot_Onyx = creacion_Ot_Hija_Onyx.format(((OnyxOtCmDir) orden[2]).getFechaCreacion());
                        reporteOtCMDto.setFechaCreacionOtOnyx(fecha_Creacion_Ot_Onyx);
                    }

                    reporteOtCMDto.setDescripcion_Onyx(((OnyxOtCmDir) orden[2]).getDescripcion_Onyx() != null
                            ? ((OnyxOtCmDir) orden[2]).getDescripcion_Onyx() : "");
                    reporteOtCMDto.setSegmento_Onyx((((OnyxOtCmDir) orden[2]).getSegmento_Onyx() != null
                            ? ((OnyxOtCmDir) orden[2]).getSegmento_Onyx() : ""));
                    reporteOtCMDto.setTipo_Servicio_Onyx(((OnyxOtCmDir) orden[2]).getTipo_Servicio_Onyx() != null
                            ? ((OnyxOtCmDir) orden[2]).getTipo_Servicio_Onyx() : "");
                    reporteOtCMDto.setServicios_Onyx(((OnyxOtCmDir) orden[2]).getServicios_Onyx() != null
                            ? ((OnyxOtCmDir) orden[2]).getServicios_Onyx() : "");
                    reporteOtCMDto.setRecurrente_Mensual_Onyx(((OnyxOtCmDir) orden[2]).getRecurrente_Mensual_Onyx() != null
                            ? ((OnyxOtCmDir) orden[2]).getRecurrente_Mensual_Onyx() : "");
                    reporteOtCMDto.setCodigo_Servicio_Onyx(((OnyxOtCmDir) orden[2]).getCodigo_Servicio_Onyx() != null
                            ? ((OnyxOtCmDir) orden[2]).getCodigo_Servicio_Onyx() : "");
                    reporteOtCMDto.setVendedor_Onyx(((OnyxOtCmDir) orden[2]).getVendedor_Onyx() != null
                            ? ((OnyxOtCmDir) orden[2]).getVendedor_Onyx() : "");
                    // Telefono 
                    String telefonoVend = ((OnyxOtCmDir) orden[2]).getTelefono_Vendedor_Onyx() != null
                            ? ((OnyxOtCmDir) orden[2]).getTelefono_Vendedor_Onyx() : "";
                    if (telefonoVend != null && !telefonoVend.equals("")) {
                        telefonoVend = StringUtils.caracteresEspeciales(telefonoVend);
                        reporteOtCMDto.setTelefono_Vendedor_Onyx(telefonoVend);
                    }

                    reporteOtCMDto.setEstado_Ot_Hija_Onyx_Cm((((OnyxOtCmDir) orden[2]) != null
                            ? ((OnyxOtCmDir) orden[2]).getEstado_Ot_Hija_Onyx_Dir() : ""));
                    reporteOtCMDto.setEstado_Ot_Padre_Onyx_Cm(((OnyxOtCmDir) orden[2]) != null
                            ? ((OnyxOtCmDir) orden[2]).getEstado_Ot_Padre_Onyx_Dir() : "");
                    SimpleDateFormat compromiso_Ot_Padre_Onyx = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    String fecha_Compromiso_Ot_Padre_Onyx;
                    if (((OnyxOtCmDir) orden[2]) != null && ((OnyxOtCmDir) orden[2]).getFecha_Compromiso_Ot_Padre_Onyx() != null) {
                        fecha_Compromiso_Ot_Padre_Onyx = compromiso_Ot_Padre_Onyx.format(((OnyxOtCmDir) orden[2]).getFecha_Compromiso_Ot_Padre_Onyx());
                        reporteOtCMDto.setFecha_Compromiso_Ot_Padre_Onyx(fecha_Compromiso_Ot_Padre_Onyx);
                    }

                    reporteOtCMDto.setOt_Hija_Resolucion_1_Onyx(((OnyxOtCmDir) orden[2]) != null
                            ? ((OnyxOtCmDir) orden[2]).getOt_Hija_Resolucion_1_Onyx() : "");
                    reporteOtCMDto.setOt_Hija_Resolucion_2_Onyx(((OnyxOtCmDir) orden[2]) != null
                            ? ((OnyxOtCmDir) orden[2]).getOt_Hija_Resolucion_2_Onyx() : "");
                    reporteOtCMDto.setOt_Hija_Resolucion_3_Onyx(((OnyxOtCmDir) orden[2]) != null
                            ? ((OnyxOtCmDir) orden[2]).getOt_Hija_Resolucion_3_Onyx() : "");
                    reporteOtCMDto.setOt_Hija_Resolucion_4_Onyx(((OnyxOtCmDir) orden[2]) != null
                            ? ((OnyxOtCmDir) orden[2]).getOt_Hija_Resolucion_4_Onyx() : "");
                    reporteOtCMDto.setOt_Padre_Resolucion_1_Onyx(((OnyxOtCmDir) orden[2]) != null
                            ? ((OnyxOtCmDir) orden[2]).getOt_Padre_Resolucion_1_Onyx() : "");
                    reporteOtCMDto.setOt_Padre_Resolucion_2_Onyx(((OnyxOtCmDir) orden[2]) != null
                            ? ((OnyxOtCmDir) orden[2]).getOt_Padre_Resolucion_2_Onyx() : "");
                    reporteOtCMDto.setOt_Padre_Resolucion_3_Onyx(((OnyxOtCmDir) orden[2]) != null
                            ? ((OnyxOtCmDir) orden[2]).getOt_Padre_Resolucion_3_Onyx() : "");
                    reporteOtCMDto.setOt_Padre_Resolucion_4_Onyx(((OnyxOtCmDir) orden[2]) != null
                            ? ((OnyxOtCmDir) orden[2]).getOt_Padre_Resolucion_4_Onyx() : "");

                    SimpleDateFormat fecha_Padre_Onyx = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    String fecha_Creacion_Ot_Padre_Onyx;
                    if (((OnyxOtCmDir) orden[2]) != null && ((OnyxOtCmDir) orden[2]).getFecha_Creacion_Ot_Padre_Onyx() != null) {
                        fecha_Creacion_Ot_Padre_Onyx = fecha_Padre_Onyx.format(((OnyxOtCmDir) orden[2]).getFecha_Creacion_Ot_Padre_Onyx());
                        reporteOtCMDto.setFecha_Creacion_Ot_Padre_Onyx(fecha_Creacion_Ot_Padre_Onyx);
                    }

                    reporteOtCMDto.setContacto_Tecnico_Ot_Padre_Onyx(((OnyxOtCmDir) orden[2]).getContacto_Tecnico_Ot_Padre_Onyx() != null
                            ? ((OnyxOtCmDir) orden[2]).getContacto_Tecnico_Ot_Padre_Onyx() : "");

                    // Telefono 
                    String telefono = ((OnyxOtCmDir) orden[2]).getTelefono_Tecnico_Ot_Padre_Onyx() != null
                            ? ((OnyxOtCmDir) orden[2]).getTelefono_Tecnico_Ot_Padre_Onyx() : "";
                    if (telefono != null && !telefono.equals("")) {
                        telefono = StringUtils.caracteresEspeciales(telefono);
                        reporteOtCMDto.setTelefono_Tecnico_Ot_Padre_Onyx(telefono);
                    }

                    //CAMPOS NUEVOS OT DIRECCION
                    reporteOtCMDto.setCodigoProyecto((((OnyxOtCmDir) orden[2]) != null
                            ? ((OnyxOtCmDir) orden[2]).getCodigoProyecto() : ""));
                    // direccion Onyx
                    String direccion = ((OnyxOtCmDir) orden[2]).getDireccion_Onyx() != null
                            ? ((OnyxOtCmDir) orden[2]).getDireccion_Onyx() : "";
                    if (direccion != null && !direccion.equals("")) {
                        direccion = StringUtils.caracteresEspeciales(direccion);
                        reporteOtCMDto.setDireccionFact(direccion);
                    }

                    reporteOtCMDto.setContactoTecnicoOTP((((OnyxOtCmDir) orden[2]) != null
                            ? ((OnyxOtCmDir) orden[2]).getContacto_Tecnico_Ot_Padre_Onyx() : ""));
                    reporteOtCMDto.setEmailCTec((((OnyxOtCmDir) orden[2]) != null ? ((OnyxOtCmDir) orden[2]).getEmailTec() : ""));

                    // indicador de cumplimiento
                    String cumplimiento = "No se cumplio";
                    if (((OnyxOtCmDir) orden[2]) != null && ((OnyxOtCmDir) orden[2]).getFecha_Compromiso_Ot_Padre_Onyx() != null
                            && ((MglAgendaDireccion) orden[1]) != null && ((MglAgendaDireccion) orden[1]).getFechaAgenda() != null) {
                        if (((MglAgendaDireccion) orden[1]).getUltimaAgenda() != null && ((MglAgendaDireccion) orden[1]).getUltimaAgenda().equals("Y")) {
                            if (((MglAgendaDireccion) orden[1]).getFechaAgenda().before(((OnyxOtCmDir) orden[2]).getFecha_Compromiso_Ot_Padre_Onyx())) {
                                cumplimiento = "Si se cumplió";
                            }
                        }
                        reporteOtCMDto.setIndicadorCumplimiento(cumplimiento);
                    }
                    //tiempo de programacion
                    if (((MglAgendaDireccion) orden[1]) != null && ((MglAgendaDireccion) orden[1]).getFechaAgenda() != null
                            && ((OnyxOtCmDir) orden[2]).getFecha_Creacion_Ot_Hija_Onyx() != null) {
                        String fecha = DateUtils.getTiempoEntreFechasConFormato(((MglAgendaDireccion) orden[1]).getFechaAgenda(), ((OnyxOtCmDir) orden[2]).getFecha_Creacion_Ot_Hija_Onyx());
                        reporteOtCMDto.setTiempoProgramacion(fecha);

                    }
                    //tiempo de atencion
                    if (((OnyxOtCmDir) orden[2]) != null && ((OnyxOtCmDir) orden[2]).getFecha_Creacion_Ot_Hija_Onyx() != null
                            && ((MglAgendaDireccion) orden[1]) != null && ((MglAgendaDireccion) orden[1]).getFechaAgenda() != null) {
                        String timeSlot = ((MglAgendaDireccion) orden[1]).getTimeSlot() == null ? ""
                                : ((MglAgendaDireccion) orden[1]).getTimeSlot().contains("Durante") ? ""
                                : ((MglAgendaDireccion) orden[1]).getTimeSlot();
                        if (timeSlot != null && !timeSlot.equals("")) {
                            if (timeSlot.contains("Durante")) {
                                timeSlot = "00";
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(((MglAgendaDireccion) orden[1]).getFechaAgenda()); //tuFechaBase es un Date;
                                calendar.add(Calendar.HOUR, Integer.parseInt(timeSlot)); //minutosASumar es int.
                                String horasTiempoAtiende = DateUtils.getTiempoEntreFechasTimeSlot(((MglAgendaDireccion) orden[1]).getFechaAgenda(), ((OnyxOtCmDir) orden[2]).getFecha_Creacion_Ot_Hija_Onyx(), timeSlot);
                                reporteOtCMDto.setTiempoAtencion(horasTiempoAtiende);
                            } else {
                                timeSlot = ((MglAgendaDireccion) orden[1]).getTimeSlot().substring(0, 2);
                                String horasTiempoAtiende = DateUtils.getTiempoEntreFechasTimeSlot(((MglAgendaDireccion) orden[1]).getFechaAgenda(), ((OnyxOtCmDir) orden[2]).getFecha_Creacion_Ot_Hija_Onyx(), timeSlot);
                                reporteOtCMDto.setTiempoAtencion(horasTiempoAtiende);
                            }
                        }

                    }
                    //antiguedad de la agenda
                    if (((OnyxOtCmDir) orden[2]).getFecha_Creacion_Ot_Padre_Onyx() != null) {
                        String antiguedad = DateUtils.getTiempoEntreFechasConFormato(((OnyxOtCmDir) orden[2]).getFecha_Creacion_Ot_Padre_Onyx(), new Date());
                        reporteOtCMDto.setAntiguedadOrden(antiguedad);
                    }

                    //aliado Implementacion 
                    if (((OnyxOtCmDir) orden[2]).getaImplement() != null) {
                        reporteOtCMDto.setaImplement(((OnyxOtCmDir) orden[2]).getaImplement());
                    }
                    //tipo solucion
                    if (((OnyxOtCmDir) orden[2]).getTipo_Servicio_Onyx() != null) {
                        reporteOtCMDto.setTipo_Servicio_Onyx(((OnyxOtCmDir) orden[2]).getTipo_Servicio_Onyx());
                    }

                }
                // OtHhppTecnologiaMgl
                if (((OtHhppTecnologiaMgl) orden[3]) != null) {
                    // CAMPOS NUEVOS
                    if (((OtHhppTecnologiaMgl) orden[3]).getNodo() != null && ((OtHhppTecnologiaMgl) orden[3]).getNodo().getComId() != null
                            && ((OtHhppTecnologiaMgl) orden[3]).getNodo().getComId().getRegionalRr() != null
                            && ((OtHhppTecnologiaMgl) orden[3]).getNodo().getComId().getRegionalRr().getNombreRegional() != null
                            && ((OtHhppTecnologiaMgl) orden[3]).getNodo().getComId().getRegionalRr().getCodigoRr() != null) {
                        reporteOtCMDto.setRegional(((OtHhppTecnologiaMgl) orden[3]).getNodo().getComId().getRegionalRr().getNombreRegional()
                                + " " + "(" + ((OtHhppTecnologiaMgl) orden[3]).getNodo().getComId().getRegionalRr().getCodigoRr() + ")");
                    }
                    if (((OtHhppTecnologiaMgl) orden[3]).getNodo() != null && ((OtHhppTecnologiaMgl) orden[3]).getNodo().getComId() != null
                            && ((OtHhppTecnologiaMgl) orden[3]).getNodo().getComId().getNombreCortoRegional() != null
                            && ((OtHhppTecnologiaMgl) orden[3]).getNodo().getComId().getCodigoRr() != null) {
                        reporteOtCMDto.setCiudad(((OtHhppTecnologiaMgl) orden[3]).getNodo().getComId().getNombreCortoRegional()
                                + " " + "(" + ((OtHhppTecnologiaMgl) orden[3]).getNodo().getComId().getCodigoRr() + ")");
                    }

                    if (((OtHhppTecnologiaMgl) orden[3]).getNodo() != null && ((OtHhppTecnologiaMgl) orden[3]).getNodo().getComId() != null
                            && ((OtHhppTecnologiaMgl) orden[3]).getNodo().getComId().getCiudad() != null
                            && ((OtHhppTecnologiaMgl) orden[3]).getNodo().getComId().getCiudad().getGpoNombre() != null) {
                        reporteOtCMDto.setDepartamento(((OtHhppTecnologiaMgl) orden[3]).getNodo().getComId().getCiudad().getGpoNombre());
                    }

                }
                listaReporteOtCMDto.add(reporteOtCMDto);

            }
        }

    }

    public List<ReporteHistoricoOtCmDto> getReporteHistoricoOtCM(HashMap<String, Object> params,
            boolean contar, int firstResult, int maxResults, String usuario) throws ApplicationException {
        List<ReporteHistoricoOtCmDto> listReporteHistoricoOtCmDto = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        List<Object[]> listaOrdenesHistoricoAgendas;
        CmtTipoBasicaMglDaoImpl cmtTipoBasicaMglDaoImpl = new CmtTipoBasicaMglDaoImpl();
        CmtBasicaMglDaoImpl cmtBasicaMglDaoImpl = new CmtBasicaMglDaoImpl();
        CmtTipoBasicaMgl subtipoOden = cmtTipoBasicaMglDaoImpl.findByCodigoInternoApp(
                Constant.TIPO_BASICA_SUB_TIPO_ORDEN_OFSC);
        List<CmtBasicaMgl> listaBasica = cmtBasicaMglDaoImpl.findByTipoBasica(subtipoOden);

        sql.append("SELECT distinct o , ot_aud, a ,ag_aud, oy ,onyx_aud  "
                + "FROM CmtOrdenTrabajoMgl o  "
                + "left join o.listAgendas a "
                + "left join o.listOnyx oy "
                + "left join o.listAuditoria ot_aud "
                + "left join a.listAuditoria  ag_aud "
                + "left join oy.listAuditoria onyx_aud "
                + "WHERE (o.estadoRegistro is null or o.estadoRegistro = 1 )"
                + "and (oy.estadoRegistro is null or oy.estadoRegistro = 1 )");

        generarSelectReporteHistoricoOtCm(sql, params, true);
        Query q = entityManager.createQuery(sql.toString());
        agregarParametrosHistorico(q, params, true);
        q.setFirstResult(firstResult);
        q.setMaxResults(maxResults);
        listaOrdenesHistoricoAgendas = (List<Object[]>) q.getResultList();

        for (Object[] orden : listaOrdenesHistoricoAgendas) {
            ReporteHistoricoOtCmDto reporteHistoricoOtCmDto = new ReporteHistoricoOtCmDto();
            // ordenes de cm
            reporteHistoricoOtCmDto.setOt_Id_Cm(((CmtOrdenTrabajoMgl) orden[0]).getIdOt() != null ? ((CmtOrdenTrabajoMgl) orden[0]).getIdOt().toString() : "");
            reporteHistoricoOtCmDto.setTipo_OT_MER(((CmtOrdenTrabajoMgl) orden[0]).getTipoOtObj() != null ? ((CmtOrdenTrabajoMgl) orden[0]).getTipoOtObj().getDescTipoOt() : "");
            reporteHistoricoOtCmDto.setSub_tipo_OT_MER(((CmtOrdenTrabajoMgl) orden[0]).getBasicaIdTipoTrabajo() != null ? ((CmtOrdenTrabajoMgl) orden[0]).getBasicaIdTipoTrabajo().getNombreBasica() : "");
            reporteHistoricoOtCmDto.setEstado_interno_OT_MER(((CmtOrdenTrabajoMgl) orden[0]).getEstadoInternoObj() != null ? ((CmtOrdenTrabajoMgl) orden[0]).getEstadoInternoObj().getNombreBasica() : "");

            SimpleDateFormat fecha_creacion_OT_MER = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            String fechaCreacion;
            if (((CmtOrdenTrabajoMgl) orden[0]) != null && ((CmtOrdenTrabajoMgl) orden[0]).getFechaCreacion() != null) {
                fechaCreacion = fecha_creacion_OT_MER.format(((CmtOrdenTrabajoMgl) orden[0]).getFechaCreacion());
            } else {
                fechaCreacion = "";
            }

            SimpleDateFormat fecha_modificacion_OT_MER = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            String fechaModificacion;
            if (((CmtOrdenTrabajoAuditoriaMgl) orden[1]) != null && ((CmtOrdenTrabajoAuditoriaMgl) orden[1]).getFechaEdicion() != null) {
                fechaModificacion = fecha_modificacion_OT_MER.format(((CmtOrdenTrabajoAuditoriaMgl) orden[1]).getFechaEdicion());
            } else {
                fechaModificacion = "";
            }

            reporteHistoricoOtCmDto.setFecha_creacion_OT_MER(fechaCreacion);
            reporteHistoricoOtCmDto.setFechaModificacionOtMer(fechaModificacion);

            reporteHistoricoOtCmDto.setSegmento_OT_MER(((CmtOrdenTrabajoMgl) orden[0]).getSegmento().getNombreBasica() != null ? ((CmtOrdenTrabajoMgl) orden[0]).getSegmento().getNombreBasica() : "");
            reporteHistoricoOtCmDto.setTecnologia_OT_MGL(((CmtOrdenTrabajoMgl) orden[0]).getBasicaIdTecnologia() == null ? "" : ((CmtOrdenTrabajoMgl) orden[0]).getBasicaIdTecnologia().getNombreBasica());
            reporteHistoricoOtCmDto.setCmObj(((CmtOrdenTrabajoMgl) orden[0]).getCmObj().getCuentaId() != null ? ((CmtOrdenTrabajoMgl) orden[0]).getCmObj().getCuentaId().substring(7) : "");
            reporteHistoricoOtCmDto.setDireccionMer(((CmtOrdenTrabajoMgl) orden[0]).getCmObj().getDireccionPrincipal() == null ? "" : ((CmtOrdenTrabajoMgl) orden[0]).getCmObj().getDireccionPrincipal().getDireccionObj().getDirFormatoIgac());
            reporteHistoricoOtCmDto.setNombreCM(((CmtOrdenTrabajoMgl) orden[0]).getCmObj().getNombreCuenta() != null ? ((CmtOrdenTrabajoMgl) orden[0]).getCmObj().getNombreCuenta() : "");
            reporteHistoricoOtCmDto.setCodigoCMR(((CmtOrdenTrabajoMgl) orden[0]).getCmObj().getNumeroCuenta() != null ? ((CmtOrdenTrabajoMgl) orden[0]).getCmObj().getNumeroCuenta().toString() : "");
            reporteHistoricoOtCmDto.setUsuario_Creacion_OT_MER(((CmtOrdenTrabajoMgl) orden[0]).getUsuarioCreacion() == null ? "" : ((CmtOrdenTrabajoMgl) orden[0]).getUsuarioCreacion().toString());
            reporteHistoricoOtCmDto.setOnyx_Ot_Hija_Cm(((CmtOrdenTrabajoMgl) orden[0]).getOnyxOtHija() == null ? "" : ((CmtOrdenTrabajoMgl) orden[0]).getOnyxOtHija().toString());
            String complejidadDescripcion;
            if (((CmtOrdenTrabajoMgl) orden[0]).getComplejidadServicio() != null && ((CmtOrdenTrabajoMgl) orden[0]).getComplejidadServicio().equals("A")) {
                complejidadDescripcion = "ALTA";
            } else if (((CmtOrdenTrabajoMgl) orden[0]).getComplejidadServicio() != null && ((CmtOrdenTrabajoMgl) orden[0]).getComplejidadServicio().equals("M")) {
                complejidadDescripcion = "MEDIA";
            } else if (((CmtOrdenTrabajoMgl) orden[0]).getComplejidadServicio() != null && ((CmtOrdenTrabajoMgl) orden[0]).getComplejidadServicio().equals("B")) {
                complejidadDescripcion = "BAJA";
            } else {
                complejidadDescripcion = "";
            }
            reporteHistoricoOtCmDto.setComplejidadServicio(complejidadDescripcion);

            String departamento = "";
            if (((CmtOrdenTrabajoMgl) orden[0]).getCmObj() != null && ((CmtOrdenTrabajoMgl) orden[0]).getCmObj().getDepartamento() != null) {
                ((CmtOrdenTrabajoMgl) orden[0]).getCmObj().getDepartamento().getGpoNombre();
                departamento = (((CmtOrdenTrabajoMgl) orden[0]).getCmObj().getDepartamento().getGpoNombre());
            }
            reporteHistoricoOtCmDto.setDepartamento(departamento);
            reporteHistoricoOtCmDto.setUsuarioModOt(((CmtOrdenTrabajoMgl) orden[0]).getUsuarioEdicion());

            // ot auditoria
            if (((CmtOrdenTrabajoAuditoriaMgl) orden[1]) != null) {
                reporteHistoricoOtCmDto.setSub_tipo_OT_MER_Aud(((CmtOrdenTrabajoAuditoriaMgl) orden[1]).getBasicaIdTipoTrabajo() == null ? "" : ((CmtOrdenTrabajoAuditoriaMgl) orden[1]).getBasicaIdTipoTrabajo().getNombreBasica());
                reporteHistoricoOtCmDto.setSegmento_OT_MER_Aud(((CmtOrdenTrabajoAuditoriaMgl) orden[1]).getSegmento().getNombreBasica());
                reporteHistoricoOtCmDto.setOnyx_Ot_Hija_Cm_Aud(((CmtOrdenTrabajoAuditoriaMgl) orden[1]).getOnyxOtHija() == null ? "" : ((CmtOrdenTrabajoAuditoriaMgl) orden[1]).getOnyxOtHija().toString());
                SimpleDateFormat fecha_creacion_OT_MER_Aud = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                String fechaCreacion_Mer_Aud = "";
                if (((CmtOrdenTrabajoAuditoriaMgl) orden[1]) != null && ((CmtOrdenTrabajoAuditoriaMgl) orden[1]).getFechaCreacion() != null) {
                    fechaCreacion_Mer_Aud = fecha_creacion_OT_MER_Aud.format(((CmtOrdenTrabajoAuditoriaMgl) orden[1]).getFechaCreacion());
                }
                reporteHistoricoOtCmDto.setFecha_Creacion_OT_MER_Aud(fechaCreacion_Mer_Aud);
                reporteHistoricoOtCmDto.setEstado_interno_OT_MER_Aud(((CmtOrdenTrabajoAuditoriaMgl) orden[1]) == null ? "" : ((CmtOrdenTrabajoAuditoriaMgl) orden[1]).getEstadoInternoObj().getNombreBasica());
                String complejidadDescripcionAud;
                if (((CmtOrdenTrabajoAuditoriaMgl) orden[1]).getComplejidadServicio() != null && ((CmtOrdenTrabajoAuditoriaMgl) orden[1]).getComplejidadServicio().equals("A")) {
                    complejidadDescripcionAud = "ALTA";
                } else if (((CmtOrdenTrabajoAuditoriaMgl) orden[1]).getComplejidadServicio() != null && ((CmtOrdenTrabajoAuditoriaMgl) orden[1]).getComplejidadServicio().equals("M")) {
                    complejidadDescripcionAud = "MEDIA";
                } else if (((CmtOrdenTrabajoAuditoriaMgl) orden[1]).getComplejidadServicio() != null && ((CmtOrdenTrabajoAuditoriaMgl) orden[1]).getComplejidadServicio().equals("B")) {
                    complejidadDescripcionAud = "BAJA";
                } else {
                    complejidadDescripcionAud = "";
                }
                reporteHistoricoOtCmDto.setComplejidadServicioAud(complejidadDescripcionAud);
                reporteHistoricoOtCmDto.setUsuarioModOtAud(((CmtOrdenTrabajoAuditoriaMgl) orden[1]).getUsuarioEdicion() != null ? ((CmtOrdenTrabajoAuditoriaMgl) orden[1]).getUsuarioEdicion() : "");
            }
            // agendamiento
            if (((CmtAgendamientoMgl) orden[2]) != null) {
                reporteHistoricoOtCmDto.setOrden_RR(((CmtAgendamientoMgl) orden[2]).getIdOtenrr() == null ? "" : ((CmtAgendamientoMgl) orden[2]).getIdOtenrr());
                //sub tipo ordenes ofsc
                for (CmtBasicaMgl cmtBasicaMgl : listaBasica) {
                    if (((CmtAgendamientoMgl) orden[2]).getSubTipoWorkFoce() != null && ((CmtAgendamientoMgl) orden[2]).getSubTipoWorkFoce().equals(cmtBasicaMgl.getCodigoBasica())) {
                        reporteHistoricoOtCmDto.setSubTipo_Orden_OFSC(((CmtAgendamientoMgl) orden[2]).getSubTipoWorkFoce().trim());
                        break;
                    }
                }

                reporteHistoricoOtCmDto.setUsuario_creacion_agenda_OFSC((((CmtAgendamientoMgl) orden[2]).getUsuarioCreacion() != null ? ((CmtAgendamientoMgl) orden[2]).getUsuarioCreacion() : ""));
                String timeS = "";
                if (((CmtAgendamientoMgl) orden[2]).getTimeSlot() != null) {
                    timeS = ((CmtAgendamientoMgl) orden[2]).getTimeSlot().equals("") ? "" : ((CmtAgendamientoMgl) orden[2]).getTimeSlot();
                }
                if (timeS.equals("")) {
                    reporteHistoricoOtCmDto.setTime_slot_OFSC(timeS);
                } else {
                    timeS = timeS.replace("-", "_");
                    reporteHistoricoOtCmDto.setTime_slot_OFSC(timeS);
                }
                reporteHistoricoOtCmDto.setAppt_number_OFSC(((CmtAgendamientoMgl) orden[2]).getOfpsOtId() == null ? "" : ((CmtAgendamientoMgl) orden[2]).getOfpsOtId());
                reporteHistoricoOtCmDto.setEstado_agenda_OFSC(((CmtAgendamientoMgl) orden[2]).getBasicaIdEstadoAgenda().getNombreBasica() == null ? "" : ((CmtAgendamientoMgl) orden[2]).getBasicaIdEstadoAgenda().getNombreBasica());

                SimpleDateFormat fecha_inicia_agenda = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                String fechaInivioVt = "";
                if (((CmtAgendamientoMgl) orden[2]) != null && ((CmtAgendamientoMgl) orden[2]).getFechaInivioVt() != null) {
                    fechaInivioVt = fecha_inicia_agenda.format(((CmtAgendamientoMgl) orden[2]).getFechaInivioVt());
                }
                reporteHistoricoOtCmDto.setFecha_inicia_agenda_OFSC(fechaInivioVt);

                SimpleDateFormat format_fecha_fin_agenda = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                String fechaFinVt = "";
                if (((CmtAgendamientoMgl) orden[2]) != null && ((CmtAgendamientoMgl) orden[2]).getFechaFinVt() != null) {
                    fechaFinVt = format_fecha_fin_agenda.format(((CmtAgendamientoMgl) orden[2]).getFechaFinVt());
                }
                reporteHistoricoOtCmDto.setFecha_fin_agenda_OFSC(fechaFinVt);

                reporteHistoricoOtCmDto.setId_aliado_OFSC((((CmtAgendamientoMgl) orden[2]).getIdentificacionAliado() == null ? ""
                        : ((CmtAgendamientoMgl) orden[2]).getIdentificacionAliado()));
                reporteHistoricoOtCmDto.setNombre_aliado_OFSC(((CmtAgendamientoMgl) orden[2]).getNombreAliado() == null ? ""
                        : ((CmtAgendamientoMgl) orden[2]).getNombreAliado());
                reporteHistoricoOtCmDto.setId_tecnico_aliado_OFSC((((CmtAgendamientoMgl) orden[2]).getIdentificacionTecnico() == null ? ""
                        : ((CmtAgendamientoMgl) orden[2]).getIdentificacionTecnico()));
                reporteHistoricoOtCmDto.setNombre_tecnico_aliado_OFSC((((CmtAgendamientoMgl) orden[2]).getNombreTecnico() == null ? ""
                        : ((CmtAgendamientoMgl) orden[2]).getNombreTecnico()));
                reporteHistoricoOtCmDto.setUltima_agenda_multiagenda((((CmtAgendamientoMgl) orden[2]).getUltimaAgenda() == null ? ""
                        : ((CmtAgendamientoMgl) orden[2]).getUltimaAgenda()));
                reporteHistoricoOtCmDto.setObservaciones_tecnico_OFSC((((CmtAgendamientoMgl) orden[2]).getObservacionesTecnico() == null ? ""
                        : ((CmtAgendamientoMgl) orden[2]).getObservacionesTecnico()));

                SimpleDateFormat fecha_agenda = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                String fechaAgenda = "";
                if (((CmtAgendamientoMgl) orden[2]) != null && ((CmtAgendamientoMgl) orden[2]).getFechaAgenda() != null) {
                    fechaAgenda = fecha_agenda.format(((CmtAgendamientoMgl) orden[2]).getFechaAgenda());
                }
                reporteHistoricoOtCmDto.setFecha_agenda_OFSC(fechaAgenda);
                reporteHistoricoOtCmDto.setUsuarioModAgenda(((CmtAgendamientoMgl) orden[2]).getUsuarioEdicion() != null ? ((CmtAgendamientoMgl) orden[2]).getUsuarioEdicion() : "");

            }
            if (((CmtAgendaAuditoria) orden[3]) != null) {
                CmtBasicaMgl basicaSubTipoOrdenes = null;
                if (((CmtAgendaAuditoria) orden[3]).getSubTipoWorkFoce() != null) {
                    basicaSubTipoOrdenes = cmtBasicaMglDaoImpl.findBasica(((CmtAgendaAuditoria) orden[3]).getSubTipoWorkFoce(), subtipoOden.getIdentificadorInternoApp());
                }
                reporteHistoricoOtCmDto.setSubTipo_Orden_OFSC_Aud(basicaSubTipoOrdenes == null ? "" : basicaSubTipoOrdenes.getDescripcion().trim());
                reporteHistoricoOtCmDto.setEstado_agenda_OFSC_Aud(((CmtAgendaAuditoria) orden[3]).getBasicaIdEstadoAgenda().getNombreBasica() == null ? ""
                        : ((CmtAgendaAuditoria) orden[3]).getBasicaIdEstadoAgenda().getNombreBasica());
                reporteHistoricoOtCmDto.setId_aliado_OFSC_Aud((((CmtAgendaAuditoria) orden[3]).getIdentificacionAliado() == null ? ""
                        : ((CmtAgendaAuditoria) orden[3]).getIdentificacionAliado()));
                reporteHistoricoOtCmDto.setNombre_aliado_OFSC_Aud(((CmtAgendaAuditoria) orden[3]).getNombreAliado() == null ? ""
                        : ((CmtAgendaAuditoria) orden[3]).getNombreAliado());
                reporteHistoricoOtCmDto.setId_tecnico_aliado_OFSC_Aud((((CmtAgendaAuditoria) orden[3]).getIdentificacionTecnico() == null ? ""
                        : ((CmtAgendaAuditoria) orden[3]).getIdentificacionTecnico()));
                reporteHistoricoOtCmDto.setNombre_tecnico_aliado_OFSC_Aud((((CmtAgendaAuditoria) orden[3]).getNombreTecnico() == null ? ""
                        : ((CmtAgendaAuditoria) orden[3]).getNombreTecnico()));
                reporteHistoricoOtCmDto.setUsuarioModAgendaAud(((CmtAgendaAuditoria) orden[3]).getUsuarioEdicion());
            }
            // datos onyx
            if (((OnyxOtCmDir) orden[4]) != null) {
                reporteHistoricoOtCmDto.setNit_Cliente_Onyx((((OnyxOtCmDir) orden[4]).getNit_Cliente_Onyx() == null ? ""
                        : ((OnyxOtCmDir) orden[4]).getNit_Cliente_Onyx()));
                reporteHistoricoOtCmDto.setNombre_Cliente_Onyx((((OnyxOtCmDir) orden[4]).getNombre_Cliente_Onyx() == null ? ""
                        : ((OnyxOtCmDir) orden[4]).getNombre_Cliente_Onyx()));
                reporteHistoricoOtCmDto.setOnyx_Ot_Hija_Cm((((OnyxOtCmDir) orden[4]).getOnyx_Ot_Hija_Cm() == null ? ""
                        : ((OnyxOtCmDir) orden[4]).getOnyx_Ot_Hija_Cm()));
                SimpleDateFormat creacion_Ot_Hija_Onyx = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                String fecha_Creacion_Ot_Hija_Onyx = "";
                if (((OnyxOtCmDir) orden[4]) != null && (((OnyxOtCmDir) orden[4]).getFecha_Creacion_Ot_Hija_Onyx() != null)) {
                    fecha_Creacion_Ot_Hija_Onyx = creacion_Ot_Hija_Onyx.format(((OnyxOtCmDir) orden[4]).getFecha_Creacion_Ot_Hija_Onyx());
                }
                reporteHistoricoOtCmDto.setFecha_Creacion_Ot_Hija_Onyx(fecha_Creacion_Ot_Hija_Onyx);
                reporteHistoricoOtCmDto.setDireccion_Onyx((((OnyxOtCmDir) orden[4]).getDireccion_Onyx() == null ? ""
                        : ((OnyxOtCmDir) orden[4]).getDireccion_Onyx()));
                reporteHistoricoOtCmDto.setDescripcion_Onyx((((OnyxOtCmDir) orden[4]).getDescripcion_Onyx() == null ? ""
                        : ((OnyxOtCmDir) orden[4]).getDescripcion_Onyx()));
                reporteHistoricoOtCmDto.setSegmento_Onyx((((OnyxOtCmDir) orden[4]).getSegmento_Onyx() == null ? ""
                        : ((OnyxOtCmDir) orden[4]).getSegmento_Onyx()));
                reporteHistoricoOtCmDto.setTipo_Servicio_Onyx((((OnyxOtCmDir) orden[4]).getTipo_Servicio_Onyx() == null ? ""
                        : ((OnyxOtCmDir) orden[4]).getTipo_Servicio_Onyx()));
                reporteHistoricoOtCmDto.setServicios_Onyx((((OnyxOtCmDir) orden[4]).getServicios_Onyx() == null ? ""
                        : ((OnyxOtCmDir) orden[4]).getServicios_Onyx()));
                reporteHistoricoOtCmDto.setRecurrente_Mensual_Onyx((((OnyxOtCmDir) orden[4]).getRecurrente_Mensual_Onyx() == null ? ""
                        : ((OnyxOtCmDir) orden[4]).getRecurrente_Mensual_Onyx()));
                reporteHistoricoOtCmDto.setCodigo_Servicio_Onyx((((OnyxOtCmDir) orden[4]).getCodigo_Servicio_Onyx() == null ? ""
                        : ((OnyxOtCmDir) orden[4]).getCodigo_Servicio_Onyx()));
                reporteHistoricoOtCmDto.setVendedor_Onyx((((OnyxOtCmDir) orden[4]).getVendedor_Onyx() == null ? ""
                        : ((OnyxOtCmDir) orden[4]).getVendedor_Onyx()));
                reporteHistoricoOtCmDto.setTelefono_Vendedor_Onyx((((OnyxOtCmDir) orden[4]).getTelefono_Vendedor_Onyx() == null ? ""
                        : ((OnyxOtCmDir) orden[4]).getTelefono_Vendedor_Onyx()));
                reporteHistoricoOtCmDto.setEstado_Ot_Hija_Onyx_Cm((((OnyxOtCmDir) orden[4]).getEstado_Ot_Hija_Onyx_Cm() == null ? ""
                        : ((OnyxOtCmDir) orden[4]).getEstado_Ot_Hija_Onyx_Cm()));
                reporteHistoricoOtCmDto.setEstado_Ot_Padre_Onyx_Cm((((OnyxOtCmDir) orden[4]).getEstado_Ot_Padre_Onyx_Cm() == null ? ""
                        : ((OnyxOtCmDir) orden[4]).getEstado_Ot_Padre_Onyx_Cm()));
                SimpleDateFormat compromiso_Ot_Padre_Onyx = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                String fecha_Compromiso_Ot_Padre_Onyx = "";
                if (((OnyxOtCmDir) orden[4]) != null && (((OnyxOtCmDir) orden[4]).getFecha_Creacion_Ot_Hija_Onyx() != null)) {
                    fecha_Compromiso_Ot_Padre_Onyx = compromiso_Ot_Padre_Onyx.format(((OnyxOtCmDir) orden[4]).getFecha_Creacion_Ot_Hija_Onyx());
                }
                reporteHistoricoOtCmDto.setFecha_Compromiso_Ot_Padre_Onyx(fecha_Compromiso_Ot_Padre_Onyx);
                reporteHistoricoOtCmDto.setOt_Hija_Resolucion_1_Onyx((((OnyxOtCmDir) orden[4]).getOt_Hija_Resolucion_1_Onyx() == null ? ""
                        : ((OnyxOtCmDir) orden[4]).getOt_Hija_Resolucion_1_Onyx()));
                reporteHistoricoOtCmDto.setOt_Hija_Resolucion_2_Onyx((((OnyxOtCmDir) orden[4]).getOt_Hija_Resolucion_2_Onyx() == null ? ""
                        : ((OnyxOtCmDir) orden[4]).getOt_Hija_Resolucion_1_Onyx()));
                reporteHistoricoOtCmDto.setOt_Hija_Resolucion_3_Onyx((((OnyxOtCmDir) orden[4]).getOt_Hija_Resolucion_3_Onyx() == null ? ""
                        : ((OnyxOtCmDir) orden[4]).getOt_Hija_Resolucion_2_Onyx()));
                reporteHistoricoOtCmDto.setOt_Hija_Resolucion_4_Onyx((((OnyxOtCmDir) orden[4]).getOt_Hija_Resolucion_4_Onyx() == null ? ""
                        : ((OnyxOtCmDir) orden[4]).getOt_Hija_Resolucion_3_Onyx()));
                reporteHistoricoOtCmDto.setOt_Padre_Resolucion_1_Onyx((((OnyxOtCmDir) orden[4]).getOt_Padre_Resolucion_1_Onyx() == null ? ""
                        : ((OnyxOtCmDir) orden[4]).getOt_Padre_Resolucion_1_Onyx()));
                reporteHistoricoOtCmDto.setOt_Padre_Resolucion_2_Onyx((((OnyxOtCmDir) orden[4]).getOt_Padre_Resolucion_2_Onyx() == null ? ""
                        : ((OnyxOtCmDir) orden[4]).getOt_Padre_Resolucion_2_Onyx()));
                reporteHistoricoOtCmDto.setOt_Padre_Resolucion_3_Onyx((((OnyxOtCmDir) orden[4]).getOt_Padre_Resolucion_3_Onyx() == null ? ""
                        : ((OnyxOtCmDir) orden[4]).getOt_Padre_Resolucion_3_Onyx()));
                reporteHistoricoOtCmDto.setOt_Padre_Resolucion_4_Onyx((((OnyxOtCmDir) orden[4]).getOt_Padre_Resolucion_4_Onyx() == null ? ""
                        : ((OnyxOtCmDir) orden[4]).getOt_Padre_Resolucion_4_Onyx()));

                reporteHistoricoOtCmDto.setOnyx_Ot_Padre_Cm((((OnyxOtCmDir) orden[4]).getOnyx_Ot_Padre_Cm() == null ? "" : ((OnyxOtCmDir) orden[4]).getOnyx_Ot_Padre_Cm()));
                SimpleDateFormat fecha_Padre_Onyx = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                String fecha_Creacion_Ot_Padre_Onyx = "";
                if (((OnyxOtCmDir) orden[4]) != null && (((OnyxOtCmDir) orden[4]).getFecha_Creacion_Ot_Padre_Onyx() != null)) {
                    fecha_Creacion_Ot_Padre_Onyx = fecha_Padre_Onyx.format(((OnyxOtCmDir) orden[4]).getFecha_Creacion_Ot_Padre_Onyx());
                }
                reporteHistoricoOtCmDto.setFecha_Creacion_Ot_Padre_Onyx(fecha_Creacion_Ot_Padre_Onyx);
                reporteHistoricoOtCmDto.setContacto_Tecnico_Ot_Padre_Onyx((((OnyxOtCmDir) orden[4]).getContacto_Tecnico_Ot_Padre_Onyx() == null ? ""
                        : ((OnyxOtCmDir) orden[4]).getContacto_Tecnico_Ot_Padre_Onyx()));
                reporteHistoricoOtCmDto.setTelefono_Tecnico_Ot_Padre_Onyx((((OnyxOtCmDir) orden[4]).getTelefono_Tecnico_Ot_Padre_Onyx() == null ? ""
                        : ((OnyxOtCmDir) orden[4]).getTelefono_Tecnico_Ot_Padre_Onyx()));
            }
            //auditoria
            if (((OnyxOtCmDirAuditoria) orden[5]) != null) {

                reporteHistoricoOtCmDto.setNit_Cliente_Onyx_Aud((((OnyxOtCmDirAuditoria) orden[5]) == null ? ""
                        : ((OnyxOtCmDirAuditoria) orden[5]).getNit_Cliente_Onyx()));
                reporteHistoricoOtCmDto.setNombre_Cliente_Onyx_Aud((((OnyxOtCmDirAuditoria) orden[5]) == null ? ""
                        : ((OnyxOtCmDirAuditoria) orden[5]).getNombre_Cliente_Onyx()));
                reporteHistoricoOtCmDto.setOnyx_Ot_Hija_Cm_Aud((((OnyxOtCmDirAuditoria) orden[5]) == null ? ""
                        : ((OnyxOtCmDirAuditoria) orden[5]).getOnyx_Ot_Hija_Cm()));
                SimpleDateFormat format_Fecha_Creacion_Ot_Hija_Onyx = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

                String fecha_Creacion_Ot_Hija_Onyx_Aud = "";
                if (((OnyxOtCmDirAuditoria) orden[5]) != null && (((OnyxOtCmDirAuditoria) orden[5]).getFecha_Creacion_Ot_Hija_Onyx() != null)) {
                    fecha_Creacion_Ot_Hija_Onyx_Aud = format_Fecha_Creacion_Ot_Hija_Onyx.format(((OnyxOtCmDirAuditoria) orden[5]).getFecha_Creacion_Ot_Hija_Onyx());
                }
                reporteHistoricoOtCmDto.setFecha_Creacion_Ot_Hija_Onyx(fecha_Creacion_Ot_Hija_Onyx_Aud);
                reporteHistoricoOtCmDto.setDireccion_Onyx_Aud((((OnyxOtCmDirAuditoria) orden[5]).getDireccion_Onyx() == null ? ""
                        : ((OnyxOtCmDirAuditoria) orden[5]).getDireccion_Onyx()));
                reporteHistoricoOtCmDto.setDescripcion_Onyx_Aud((((OnyxOtCmDirAuditoria) orden[5]).getDescripcion_Onyx() == null ? ""
                        : ((OnyxOtCmDirAuditoria) orden[5]).getDescripcion_Onyx()));
                reporteHistoricoOtCmDto.setSegmento_Onyx_Aud((((OnyxOtCmDirAuditoria) orden[5]).getSegmento_Onyx() == null ? ""
                        : ((OnyxOtCmDirAuditoria) orden[5]).getSegmento_Onyx()));
                reporteHistoricoOtCmDto.setTipo_Servicio_Onyx_Aud((((OnyxOtCmDirAuditoria) orden[5]).getTipo_Servicio_Onyx() == null ? ""
                        : ((OnyxOtCmDirAuditoria) orden[5]).getTipo_Servicio_Onyx()));
                reporteHistoricoOtCmDto.setServicios_Onyx_Aud((((OnyxOtCmDirAuditoria) orden[5]).getServicios_Onyx() == null ? ""
                        : ((OnyxOtCmDirAuditoria) orden[5]).getServicios_Onyx()));
                reporteHistoricoOtCmDto.setRecurrente_Mensual_Onyx_Aud((((OnyxOtCmDirAuditoria) orden[5]).getRecurrente_Mensual_Onyx() == null ? ""
                        : ((OnyxOtCmDirAuditoria) orden[5]).getRecurrente_Mensual_Onyx()));
                reporteHistoricoOtCmDto.setCodigo_Servicio_Onyx_Aud((((OnyxOtCmDirAuditoria) orden[5]).getCodigo_Servicio_Onyx() == null ? ""
                        : ((OnyxOtCmDirAuditoria) orden[5]).getCodigo_Servicio_Onyx()));
                reporteHistoricoOtCmDto.setVendedor_Onyx_Aud((((OnyxOtCmDirAuditoria) orden[5]).getVendedor_Onyx() == null ? ""
                        : ((OnyxOtCmDirAuditoria) orden[5]).getVendedor_Onyx()));
                reporteHistoricoOtCmDto.setTelefono_Vendedor_Onyx_Aud((((OnyxOtCmDirAuditoria) orden[5]).getTelefono_Vendedor_Onyx() == null ? ""
                        : ((OnyxOtCmDirAuditoria) orden[5]).getTelefono_Vendedor_Onyx()));
                reporteHistoricoOtCmDto.setEstado_Ot_Hija_Onyx_Cm_Aud((((OnyxOtCmDirAuditoria) orden[5]).getEstado_Ot_Hija_Onyx_Cm() == null ? ""
                        : ((OnyxOtCmDirAuditoria) orden[5]).getEstado_Ot_Hija_Onyx_Cm()));
                reporteHistoricoOtCmDto.setEstado_Ot_Padre_Onyx_Cm_Aud((((OnyxOtCmDirAuditoria) orden[5]).getEstado_Ot_Padre_Onyx_Cm() == null ? ""
                        : ((OnyxOtCmDirAuditoria) orden[5]).getEstado_Ot_Padre_Onyx_Cm()));
                SimpleDateFormat compromiso_Ot_Padre_Onyx_Aud = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

                String fecha_Compromiso_Ot_Padre_Onyx_Aud = "";
                if (((OnyxOtCmDirAuditoria) orden[5]) != null && (((OnyxOtCmDirAuditoria) orden[5]).getFecha_Compromiso_Ot_Padre_Onyx() != null)) {
                    fecha_Compromiso_Ot_Padre_Onyx_Aud = compromiso_Ot_Padre_Onyx_Aud.format(((OnyxOtCmDirAuditoria) orden[5]).getFecha_Compromiso_Ot_Padre_Onyx());
                }
                reporteHistoricoOtCmDto.setFecha_Compromiso_Ot_Padre_Onyx_Aud(fecha_Compromiso_Ot_Padre_Onyx_Aud);
                reporteHistoricoOtCmDto.setOt_Hija_Resolucion_1_Onyx_Aud((((OnyxOtCmDirAuditoria) orden[5]).getOt_Hija_Resolucion_1_Onyx() == null ? ""
                        : ((OnyxOtCmDirAuditoria) orden[5]).getOt_Hija_Resolucion_1_Onyx()));
                reporteHistoricoOtCmDto.setOt_Hija_Resolucion_2_Onyx_Aud((((OnyxOtCmDirAuditoria) orden[5]).getOt_Hija_Resolucion_2_Onyx() == null ? ""
                        : ((OnyxOtCmDirAuditoria) orden[5]).getOt_Hija_Resolucion_1_Onyx()));
                reporteHistoricoOtCmDto.setOt_Hija_Resolucion_3_Onyx_Aud((((OnyxOtCmDirAuditoria) orden[5]).getOt_Hija_Resolucion_3_Onyx() == null ? ""
                        : ((OnyxOtCmDirAuditoria) orden[5]).getOt_Hija_Resolucion_2_Onyx()));
                reporteHistoricoOtCmDto.setOt_Hija_Resolucion_4_Onyx_Aud((((OnyxOtCmDirAuditoria) orden[5]).getOt_Hija_Resolucion_4_Onyx() == null ? ""
                        : ((OnyxOtCmDirAuditoria) orden[5]).getOt_Hija_Resolucion_3_Onyx()));
                reporteHistoricoOtCmDto.setOt_Padre_Resolucion_1_Onyx_Aud((((OnyxOtCmDirAuditoria) orden[5]).getOt_Padre_Resolucion_1_Onyx() == null ? ""
                        : ((OnyxOtCmDirAuditoria) orden[5]).getOt_Padre_Resolucion_1_Onyx()));
                reporteHistoricoOtCmDto.setOt_Padre_Resolucion_2_Onyx_Aud((((OnyxOtCmDirAuditoria) orden[5]).getOt_Padre_Resolucion_2_Onyx() == null ? ""
                        : ((OnyxOtCmDirAuditoria) orden[5]).getOt_Padre_Resolucion_2_Onyx()));
                reporteHistoricoOtCmDto.setOt_Padre_Resolucion_3_Onyx_Aud((((OnyxOtCmDirAuditoria) orden[5]).getOt_Padre_Resolucion_3_Onyx() == null ? ""
                        : ((OnyxOtCmDirAuditoria) orden[5]).getOt_Padre_Resolucion_3_Onyx()));
                reporteHistoricoOtCmDto.setOt_Padre_Resolucion_4_Onyx_Aud((((OnyxOtCmDirAuditoria) orden[5]).getOt_Padre_Resolucion_4_Onyx() == null ? ""
                        : ((OnyxOtCmDirAuditoria) orden[5]).getOt_Padre_Resolucion_4_Onyx()));
                reporteHistoricoOtCmDto.setOnyx_Ot_Padre_Cm_Aud((((OnyxOtCmDirAuditoria) orden[5]).getOnyx_Ot_Padre_Cm() == null ? ""
                        : ((OnyxOtCmDirAuditoria) orden[5]).getOnyx_Ot_Padre_Cm()));
                SimpleDateFormat fecha_Padre_Onyx_aud = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

                String fecha_Creacion_OT_Padre_Onyx_Aud = "";
                if (((OnyxOtCmDirAuditoria) orden[5]) != null && (((OnyxOtCmDirAuditoria) orden[5]).getFecha_Creacion_Ot_Padre_Onyx() != null)) {
                    fecha_Creacion_OT_Padre_Onyx_Aud = fecha_Padre_Onyx_aud.format(((OnyxOtCmDirAuditoria) orden[5]).getFecha_Creacion_Ot_Padre_Onyx());
                }
                reporteHistoricoOtCmDto.setFecha_Creacion_OT_Padre_Onyx_Aud(fecha_Creacion_OT_Padre_Onyx_Aud);
                reporteHistoricoOtCmDto.setContacto_Tecnico_Ot_Padre_Onyx_Aud((((OnyxOtCmDirAuditoria) orden[5]).getContacto_Tecnico_Ot_Padre_Onyx() == null ? ""
                        : ((OnyxOtCmDirAuditoria) orden[5]).getContacto_Tecnico_Ot_Padre_Onyx()));
                reporteHistoricoOtCmDto.setTelefono_Tecnico_Ot_Padre_Onyx_Aud((((OnyxOtCmDirAuditoria) orden[5]).getTelefono_Tecnico_Ot_Padre_Onyx() == null ? ""
                        : ((OnyxOtCmDirAuditoria) orden[5]).getTelefono_Tecnico_Ot_Padre_Onyx()));
            }
            listReporteHistoricoOtCmDto.add(reporteHistoricoOtCmDto);

        }
        getEntityManager().clear();
        return listReporteHistoricoOtCmDto;
    }

    /**
     * M&eacute;todo para generar el reporte de ordenes de trabajo de CM
     *
     * @param sql {@link StringBuilder} objeto para generar el String de la
     * consulta.
     * @param params Mapa {@link HashMap}&lt;{@link String}, {@link Object}>
     * par&aacute;metros para realizar la consulta.
     *
     */
    private void generarSelectReporteOtCm(StringBuilder sql, HashMap<String, Object> params, boolean esConteo) {
        List<BigDecimal> subTipoOrdenList = (List<BigDecimal>) params.get("subTipoOrden");
        List<BigDecimal> tipoOrdenList = (List<BigDecimal>) params.get("tipoOrden");
        List<String> subOrdenList = (List<String>) params.get("subTipoOrdenOfscSelected");
        List<BigDecimal> tipoOrdenListOFSC = (List<BigDecimal>) params.get("tipoOrdenOfscSelected");
        List<String> regionalList = (List<String>) params.get("listRegionalSelected");
        List<String> ciudadList = (List<String>) params.get("listRrCiudadesSelected");
        List<String> listSegmento = (List<String>) params.get("listSegmentoSelected");
        String listaSolucion = (String) params.get("tipoSolucionSelected");
        List<String> listEstadosOtCmDirSelected = (List<String>) params.get("listEstadosOtCmDirSelected");
        String codigoProyecto = (String) params.get("codProyecto");
        List<BigDecimal> listEstadosSelected = (List<BigDecimal>) params.get("listEstadosSelected");

        // fecha de Creacion orden OT ,
        if (params.get("fechaInicioOt") != null && (params.get("fechaFinOt") != null)) {
            SimpleDateFormat diaInicial = new SimpleDateFormat("dd/MM/yyyy");
            String diaIni = diaInicial.format(params.get("fechaInicioOt"));
            SimpleDateFormat diaFin = new SimpleDateFormat("dd/MM/yyyy");
            String diaFinal = diaFin.format(params.get("fechaFinOt"));
            if (diaIni != null && diaFinal != null) {
                if (((Date) params.get("fechaInicioOt")).before((Date) params.get("fechaFinOt"))) {
//                    sql.append("AND func('trunc', o.fechaCreacion) BETWEEN :fechaInicioOt and  :fechaFinOt");
                    sql.append(" AND o.fechaCreacion BETWEEN :fechaInicioOt and  :fechaFinOt");
                } else {
                    sql.append(" AND  func('trunc', o.fechaCreacion) = :fechaInicioOt");
                }
            }

        }

        // fecha creacion ot hija onyx FECHA_CREACION_OT_HIJA_ONYX
        if (params.get("fechaInicioOtHijaOnyx") != null && !params.get("fechaFinOtHijaOnyx").toString().isEmpty()) {
            SimpleDateFormat diaInicial = new SimpleDateFormat("dd/MM/yyyy");
            String diaIni = diaInicial.format(params.get("fechaInicioOtHijaOnyx"));
            SimpleDateFormat diaFin = new SimpleDateFormat("dd/MM/yyyy");
            String diaFinal = diaFin.format(params.get("fechaFinOtHijaOnyx"));
            if (diaIni != null && diaFinal != null) {
                if (((Date) params.get("fechaInicioOtHijaOnyx")).before((Date) params.get("fechaFinOtHijaOnyx"))) {
                    sql.append(" AND func('trunc', oy.fecha_Creacion_Ot_Hija_Onyx) BETWEEN :fechaInicioOtHijaOnyx and  :fechaFinOtHijaOnyx");
                } else {
                    sql.append(" AND func('trunc', oy.fecha_Creacion_Ot_Hija_Onyx) = :fechaInicioOtHijaOnyx");
                }
            }

        }

        // fecha creacion onyx
        if (params.get("fechaCreacionIniOnyx") != null && !params.get("fechaCreacionFinOnyx").toString().isEmpty()) {
            SimpleDateFormat diaInicial = new SimpleDateFormat("dd/MM/yyyy");
            String diaIni = diaInicial.format(params.get("fechaCreacionIniOnyx"));
            SimpleDateFormat diaFin = new SimpleDateFormat("dd/MM/yyyy");
            String diaFinal = diaFin.format(params.get("fechaCreacionFinOnyx"));
            if (diaIni != null && diaFinal != null) {
                if (((Date) params.get("fechaCreacionIniOnyx")).before((Date) params.get("fechaCreacionFinOnyx"))) {
                    sql.append(" AND func('trunc', oy.fechaCreacion) BETWEEN :fechaCreacionIniOnyx and  :fechaCreacionFinOnyx");
                } else {
                    sql.append(" AND func('trunc', oy.fechaCreacion) = :fechaCreacionIniOnyx");
                }
            }

        }

        // fecha de agendamiento OFSC FECHA_AGENDA
        if (params.get("fechaInicioAgendOFSC") != null && !params.get("fechaFinAgendOFSC").toString().isEmpty()) {
            SimpleDateFormat diaInicial = new SimpleDateFormat("dd/MM/yyyy");
            String diaIni = diaInicial.format(params.get("fechaInicioAgendOFSC"));
            SimpleDateFormat diaFin = new SimpleDateFormat("dd/MM/yyyy");
            String diaFinal = diaFin.format(params.get("fechaFinAgendOFSC"));
            if (diaIni != null && diaFinal != null) {
                if (((Date) params.get("fechaInicioAgendOFSC")).before((Date) params.get("fechaFinAgendOFSC"))) {
                    sql.append(" AND func('trunc', a.fechaAgenda) BETWEEN :fechaInicioAgendOFSC and  :fechaFinAgendOFSC");
                } else {
                    sql.append(" AND func('trunc', a.fechaAgenda) = :fechaInicioAgendOFSC");
                }
            }

        }

        //fecha asignacion del tecnico FECHA_INICIO_VT
        if (params.get("fechaInicioAsigTecnico") != null && !params.get("fechaFinAsigTecnico").toString().isEmpty()) {
            SimpleDateFormat diaInicial = new SimpleDateFormat("dd/MM/yyyy");
            String diaIni = diaInicial.format(params.get("fechaInicioAsigTecnico"));
            SimpleDateFormat diaFin = new SimpleDateFormat("dd/MM/yyyy");
            String diaFinal = diaFin.format(params.get("fechaFinAsigTecnico"));
            if (diaIni != null && diaFinal != null) {
                if (((Date) params.get("fechaInicioAsigTecnico")).before((Date) params.get("fechaFinAsigTecnico"))) {
                    sql.append(" AND func('trunc', a.fechaAsigTecnico) BETWEEN :fechaInicioAsigTecnico and  :fechaFinAsigTecnico");
                } else {
                    sql.append(" AND func('trunc', a.fechaAsigTecnico) = :fechaInicioAsigTecnico");
                }
            }

        }
        //fecha cierre de agenda FECHA_FIN_VT
        if (params.get("fechaInicioCierreAgenda") != null && !params.get("fechaFinCierreAgenda").toString().isEmpty()) {
            SimpleDateFormat diaInicial = new SimpleDateFormat("dd/MM/yyyy");
            String diaIni = diaInicial.format(params.get("fechaInicioCierreAgenda"));
            SimpleDateFormat diaFin = new SimpleDateFormat("dd/MM/yyyy");
            String diaFinal = diaFin.format(params.get("fechaFinCierreAgenda"));
            if (diaIni != null && diaFinal != null) {
                if (((Date) params.get("fechaInicioCierreAgenda")).before((Date) params.get("fechaFinCierreAgenda"))) {
                    sql.append(" AND func('trunc', a.fechaFinVt) BETWEEN :fechaInicioCierreAgenda and  :fechaFinCierreAgenda");
                } else {
                    sql.append(" AND func('trunc', a.fechaFinVt) = :fechaInicioCierreAgenda");
                }
            }
        }
        //fecha cancelacion de agenda, estado cancelada id=22998 

        if (params.get("fechaInicioCancAgenda") != null && !params.get("fechaFinCancAgenda").toString().isEmpty()) {
            SimpleDateFormat diaInicial = new SimpleDateFormat("dd/MM/yyyy");
            String diaIni = diaInicial.format(params.get("fechaInicioCancAgenda"));
            SimpleDateFormat diaFin = new SimpleDateFormat("dd/MM/yyyy");
            String diaFinal = diaFin.format(params.get("fechaFinCancAgenda"));
            if (diaIni != null && diaFinal != null) {
                if (((Date) params.get("fechaInicioCancAgenda")).before((Date) params.get("fechaFinCancAgenda"))) {
                    sql.append("AND func('trunc', a.fechaEdicion) BETWEEN :fechaInicioCancAgenda and  :fechaFinCancAgenda");
                } else {
                    sql.append(" AND func('trunc', a.fechaEdicion) = :fechaInicioCancAgenda");
                }
            }
            CmtBasicaMglDaoImpl cmtBasicaMglDaoImpl = new CmtBasicaMglDaoImpl();
            CmtBasicaMgl agendaCancelada = null;
            try {
                agendaCancelada = cmtBasicaMglDaoImpl.findByCodigoInternoApp(Constant.BASICA_EST_AGENDA_CANCELADA);
            } catch (ApplicationException ex) {
                String msgError = "Error en  " + ClassUtils.getCurrentMethodName(this.getClass()) + ": " + ex.getMessage();
                LOGGER.error(msgError, ex);
            }
            if (agendaCancelada != null) {
                sql.append(" AND  a.basicaIdEstadoAgenda.basicaId  = ").append(agendaCancelada.getBasicaId());
            }

        }
        //fecha de reagendamiento ???? 

        if ((params.get("fechaInicioReagenda") != null && !params.get("fechaFinReagenda").toString().isEmpty())) {
            SimpleDateFormat diaInicial = new SimpleDateFormat("dd/MM/yyyy");
            String diaIni = diaInicial.format(params.get("fechaInicioReagenda"));
            SimpleDateFormat diaFin = new SimpleDateFormat("dd/MM/yyyy");
            String diaFinal = diaFin.format(params.get("fechaFinReagenda"));
            if (diaIni != null && diaFinal != null) {
                if (((Date) params.get("fechaInicioReagenda")).before((Date) params.get("fechaFinReagenda"))) {
                    sql.append(" AND func('trunc',  a.fechaReagenda) BETWEEN :fechaInicioReagenda and  :fechaFinReagenda");
                } else {
                    sql.append(" AND func('trunc', a.fechaReagenda) = :fechaInicioReagenda");
                }
            }
            //'22959'
            CmtBasicaMglDaoImpl cmtBasicaMglDaoImpl = new CmtBasicaMglDaoImpl();
            CmtBasicaMgl agendaReagendada = null;
            try {
                agendaReagendada = cmtBasicaMglDaoImpl.findByCodigoInternoApp(Constant.BASICA_EST_AGENDA_REAGENDADA);
            } catch (ApplicationException ex) {
                String msgError = "Error en  " + ClassUtils.getCurrentMethodName(this.getClass()) + ": " + ex.getMessage();
                LOGGER.error(msgError, ex);
            }
            if (agendaReagendada != null) {
                sql.append(" AND  a.basicaIdEstadoAgenda.basicaId  = ").append(agendaReagendada.getBasicaId()).append(" AND  a.estadoRegistro = 1");
            }

        }
        //fecha de suspencion
        if ((params.get("fechaInicioSuspension") != null && !params.get("fechaFinSuspension").toString().isEmpty())) {
            SimpleDateFormat diaInicial = new SimpleDateFormat("dd/MM/yyyy");
            String diaIni = diaInicial.format(params.get("fechaInicioSuspension"));
            SimpleDateFormat diaFin = new SimpleDateFormat("dd/MM/yyyy");
            String diaFinal = diaFin.format(params.get("fechaFinSuspension"));
            if (diaIni != null && diaFinal != null) {
                if (((Date) params.get("fechaInicioSuspension")).before((Date) params.get("fechaFinSuspension"))) {
                    sql.append(" AND func('trunc',  a.fechaSuspendeVt) BETWEEN :fechaInicioSuspension and  :fechaFinSuspension");
                } else {
                    sql.append(" AND func('trunc', a.fechaSuspendeVt) = :fechaInicioSuspension");
                }
            }

        }

        // numero de OT
        if (params.get("otIni") != null && (params.get("otFin") != null)) {
            sql.append(" and o.idOt BETWEEN :otIni and  :otFin ");
        }
        if (subTipoOrdenList != null && !subTipoOrdenList.isEmpty()) {
            sql.append("  AND  O.basicaIdTipoTrabajo.basicaId  IN :subTipoOrdenList  ");
        }
        if (tipoOrdenList != null && !tipoOrdenList.isEmpty()) {
            sql.append("  AND  O.tipoOtObj.idTipoOt  IN :tipoOrdenList  ");
        }
        if (subOrdenList != null && !subOrdenList.isEmpty()) {
            sql.append("  AND  a.subTipoWorkFoce  IN :subOrdenList  ");
        }
        if (tipoOrdenListOFSC != null && !tipoOrdenListOFSC.isEmpty()) {
            sql.append("  AND  O.basicaIdTipoTrabajo.basicaId  IN :tipoOrdenListOFSC  ");
        }
        // estado interno
        if (listEstadosOtCmDirSelected != null && !listEstadosOtCmDirSelected.isEmpty()) {
            sql.append("  AND  O.estadoInternoObj.basicaId  IN :listEstadosOtCmDirSelected ");
        }
        // estado Prueba
        if (listEstadosSelected != null && !listEstadosSelected.isEmpty()) {
            sql.append("  AND  O.estadoInternoObj.basicaId  IN :listEstadosSelected ");
        }

        // regional MER
        if (regionalList != null && !regionalList.isEmpty()) {
            sql.append("  AND  o.cmObj.division  IN :regionalList  ");
        }
        // ciudad MER
        if (ciudadList != null && !ciudadList.isEmpty()) {
            sql.append("  AND  o.cmObj.comunidad  IN :ciudadList  ");
        }
        // segmento 
        if (listSegmento != null && !listSegmento.isEmpty()) {
            sql.append("  AND oy.segmento_Onyx  IN :listSegmento ");
        }

        // codigo de proyecto
        if (codigoProyecto != null && !codigoProyecto.equals("")) {
            sql.append("  AND  oy.codigoProyecto  = :codProyecto");
        }
        // Nit Cliente
        if (params.get("nitCliente") != null && !params.get("nitCliente").toString().isEmpty()) {
            sql.append("  AND oy.nit_Cliente_Onyx  like :nitCliente");

        }
        // numero Ot Padre
        if (params.get("numOtOnyxPadre") != null && !params.get("numOtOnyxPadre").toString().isEmpty()) {
            sql.append("  AND  (oy.Onyx_Ot_Padre_Cm  = :numOtOnyxPadre or oy.Onyx_Ot_Padre_Dir  = :numOtOnyxPadre )");

        }
        // numero Ot Hija
        if (params.get("numeroOtOnyxHija") != null && !params.get("numeroOtOnyxHija").toString().isEmpty()) {

            sql.append("  AND  (oy.Onyx_Ot_Hija_Dir  = :numeroOtOnyxHija or oy.Onyx_Ot_Hija_Cm  = :numeroOtOnyxHija )");

        }
        // nombre del cliente
        if (params.get("nombreCliente") != null && !params.get("nombreCliente").toString().isEmpty()) {

            sql.append("  AND  UPPER(oy.nombre_Cliente_Onyx)  like UPPER(:nombreCliente)");

        }

        // Tipo de solucion
        if (listaSolucion != null && !listaSolucion.equals("")) {
            sql.append("  AND  oy.tipo_Servicio_Onyx   = :listaSolucion ");

        }

    }

    /**
     * M&eacute;todo para generar el reporte de ordenes de trabajo de CM
     *
     * @param sql {@link StringBuilder} objeto para generar el String de la
     * consulta.
     * @param params Mapa {@link HashMap}&lt;{@link String}, {@link Object}>
     * par&aacute;metros para realizar la consulta.
     *
     */
    private void generarSelectReporteHistoricoOtCm(StringBuilder sql, HashMap<String, Object> params, boolean esConteo) {
        List<BigDecimal> tipoOrdenList = (List<BigDecimal>) params.get("tipoOrden");
        List<BigDecimal> subTipoOrdenList = (List<BigDecimal>) params.get("subTipoOrden");
        List<String> subOrdenList = (List<String>) params.get("subTipoOrdenOfscSelected");
        List<BigDecimal> tipoOrdenListOFSC = (List<BigDecimal>) params.get("tipoOrdenOfscSelected");
        List<BigDecimal> estadosList = (List<BigDecimal>) params.get("estadoInternolist");

        // fecha de Creacion orden OT ,
        if (params.get("fechaInicioOt") != null && (params.get("fechaFinOt") != null)) {
            SimpleDateFormat diaInicial = new SimpleDateFormat("dd/MM/yyyy");
            String diaIni = diaInicial.format(params.get("fechaInicioOt"));
            SimpleDateFormat diaFin = new SimpleDateFormat("dd/MM/yyyy");
            String diaFinal = diaFin.format(params.get("fechaFinOt"));
            if (diaIni != null && diaFinal != null) {
                if (((Date) params.get("fechaInicioOt")).before((Date) params.get("fechaFinOt"))) {
//                    sql.append("AND func('trunc', o.fechaCreacion) BETWEEN :fechaInicioOt and  :fechaFinOt");
                    sql.append(" AND o.fechaCreacion BETWEEN :fechaInicioOt and  :fechaFinOt");
                } else {
                    sql.append(" AND  func('trunc', o.fechaCreacion) = :fechaInicioOt");
                }
            }

        }

        // fecha creacion ot hija onyx FECHA_CREACION_OT_HIJA_ONYX
        if (params.get("fechaInicioOtOnyx") != null && !params.get("fechaFinOtOnyx").toString().isEmpty()) {
            SimpleDateFormat diaInicial = new SimpleDateFormat("dd/MM/yyyy");
            String diaIni = diaInicial.format(params.get("fechaInicioOtOnyx"));
            SimpleDateFormat diaFin = new SimpleDateFormat("dd/MM/yyyy");
            String diaFinal = diaFin.format(params.get("fechaFinOtOnyx"));
            if (diaIni != null && diaFinal != null) {
                if (((Date) params.get("fechaInicioOtOnyx")).before((Date) params.get("fechaFinOtOnyx"))) {
                    sql.append(" AND func('trunc', oy.fecha_Creacion_Ot_Hija_Onyx) BETWEEN :fechaInicioOtOnyx and  :fechaFinOtOnyx");
                } else {
                    sql.append(" AND func('trunc', oy.fecha_Creacion_Ot_Hija_Onyx) = :fechaInicioOtOnyx");
                }
            }

        }
        // fecha de agendamiento OFSC FECHA_AGENDA
        if (params.get("fechaInicioAgendOFSC") != null && !params.get("fechaFinAgendOFSC").toString().isEmpty()) {
            SimpleDateFormat diaInicial = new SimpleDateFormat("dd/MM/yyyy");
            String diaIni = diaInicial.format(params.get("fechaInicioAgendOFSC"));
            SimpleDateFormat diaFin = new SimpleDateFormat("dd/MM/yyyy");
            String diaFinal = diaFin.format(params.get("fechaFinAgendOFSC"));
            if (diaIni != null && diaFinal != null) {
                if (((Date) params.get("fechaInicioAgendOFSC")).before((Date) params.get("fechaFinAgendOFSC"))) {
                    sql.append(" AND func('trunc', a.fechaAgenda) BETWEEN :fechaInicioAgendOFSC and  :fechaFinAgendOFSC");
                } else {
                    sql.append(" AND func('trunc', a.fechaAgenda) = :fechaInicioAgendOFSC");
                }
            }

        }
        //fecha asignacion del tecnico FECHA_INICIO_VT
        if (params.get("fechaInicioAsigTecnico") != null && !params.get("fechaFinAsigTecnico").toString().isEmpty()) {
            SimpleDateFormat diaInicial = new SimpleDateFormat("dd/MM/yyyy");
            String diaIni = diaInicial.format(params.get("fechaInicioAsigTecnico"));
            SimpleDateFormat diaFin = new SimpleDateFormat("dd/MM/yyyy");
            String diaFinal = diaFin.format(params.get("fechaFinAsigTecnico"));
            if (diaIni != null && diaFinal != null) {
                if (((Date) params.get("fechaInicioAsigTecnico")).before((Date) params.get("fechaFinAsigTecnico"))) {
                    sql.append(" AND func('trunc', a.fechaAsigTecnico) BETWEEN :fechaInicioAsigTecnico and  :fechaFinAsigTecnico");
                } else {
                    sql.append(" AND func('trunc', a.fechaAsigTecnico) = :fechaInicioAsigTecnico");
                }
            }

        }

        //fecha cierre de agenda FECHA_FIN_VT
        if (params.get("fechaInicioCierreAgenda") != null && !params.get("fechaFinCierreAgenda").toString().isEmpty()) {
            SimpleDateFormat diaInicial = new SimpleDateFormat("dd/MM/yyyy");
            String diaIni = diaInicial.format(params.get("fechaInicioCierreAgenda"));
            SimpleDateFormat diaFin = new SimpleDateFormat("dd/MM/yyyy");
            String diaFinal = diaFin.format(params.get("fechaFinCierreAgenda"));
            if (diaIni != null && diaFinal != null) {
                if (((Date) params.get("fechaInicioCierreAgenda")).before((Date) params.get("fechaFinCierreAgenda"))) {
                    sql.append(" AND func('trunc', a.fechaFinVt) BETWEEN :fechaInicioCierreAgenda and  :fechaFinCierreAgenda");
                } else {
                    sql.append(" AND func('trunc', a.fechaFinVt) = :fechaInicioCierreAgenda");
                }
            }
        }
        //fecha cancelacion de agenda, estado cancelada id=22998 
        if (params.get("fechaInicioCancAgenda") != null && !params.get("fechaFinCancAgenda").toString().isEmpty()) {
            SimpleDateFormat diaInicial = new SimpleDateFormat("dd/MM/yyyy");
            String diaIni = diaInicial.format(params.get("fechaInicioCancAgenda"));
            SimpleDateFormat diaFin = new SimpleDateFormat("dd/MM/yyyy");
            String diaFinal = diaFin.format(params.get("fechaFinCancAgenda"));
            if (diaIni != null && diaFinal != null) {
                if (((Date) params.get("fechaInicioCancAgenda")).before((Date) params.get("fechaFinCancAgenda"))) {
                    sql.append(" AND func('trunc', a.fechaEdicion) BETWEEN :fechaInicioCancAgenda and  :fechaFinCancAgenda");
                } else {
                    sql.append(" AND func('trunc', a.fechaEdicion) = :fechaInicioCancAgenda");
                }
            }
            sql.append(" AND  a.basicaIdEstadoAgenda.basicaId  = '22998'   AND  a.estadoRegistro = 1");

        }
        //fecha de reagendamiento ???? 
        if ((params.get("fechaInicioReagenda") != null && !params.get("fechaFinReagenda").toString().isEmpty())) {
            SimpleDateFormat diaInicial = new SimpleDateFormat("dd/MM/yyyy");
            String diaIni = diaInicial.format(params.get("fechaInicioReagenda"));
            SimpleDateFormat diaFin = new SimpleDateFormat("dd/MM/yyyy");
            String diaFinal = diaFin.format(params.get("fechaFinReagenda"));
            if (diaIni != null && diaFinal != null) {
                if (((Date) params.get("fechaInicioReagenda")).before((Date) params.get("fechaFinReagenda"))) {
                    sql.append(" AND func('trunc',  a.fechaReagenda) BETWEEN :fechaInicioReagenda and  :fechaFinReagenda");
                } else {
                    sql.append(" AND func('trunc', a.fechaReagenda) = :fechaInicioReagenda");
                }
            }

        }
        //fecha de suspencion
        if ((params.get("fechaInicioSuspension") != null && !params.get("fechaFinSuspension").toString().isEmpty())) {
            SimpleDateFormat diaInicial = new SimpleDateFormat("dd/MM/yyyy");
            String diaIni = diaInicial.format(params.get("fechaInicioSuspension"));
            SimpleDateFormat diaFin = new SimpleDateFormat("dd/MM/yyyy");
            String diaFinal = diaFin.format(params.get("fechaFinSuspension"));
            if (diaIni != null && diaFinal != null) {
                if (((Date) params.get("fechaInicioSuspension")).before((Date) params.get("fechaFinSuspension"))) {
                    sql.append(" AND func('trunc',  a.fechaSuspendeVt) BETWEEN :fechaInicioSuspension and  :fechaFinSuspension");
                } else {
                    sql.append(" AND func('trunc', a.fechaSuspendeVt) = :fechaInicioSuspension");
                }
            }

        }

        // numero de OT
        if (params.get("otIni") != null && (params.get("otFin") != null)) {
            sql.append(" and o.idOt BETWEEN :otIni and  :otFin ");
        }
        if (subTipoOrdenList != null && !subTipoOrdenList.isEmpty()) {
            sql.append("  AND  o.basicaIdTipoTrabajo.basicaId  IN :subTipoOrdenList  ");
        }
        if (tipoOrdenList != null && !tipoOrdenList.isEmpty()) {
            sql.append("  AND  o.tipoOtObj.idTipoOt  IN :tipoOrdenList  ");
        }
        if (subOrdenList != null && !subOrdenList.isEmpty()) {
            sql.append("  AND  a.subTipoWorkFoce  IN :subOrdenList  ");
        }
        if (tipoOrdenListOFSC != null && !tipoOrdenListOFSC.isEmpty()) {
            sql.append("  AND  o.basicaIdTipoTrabajo.basicaId  IN :tipoOrdenListOFSC  ");
        }
        if (estadosList != null && !estadosList.isEmpty()) {
            sql.append("  AND  o.estadoInternoObj.basicaId  IN :estadosList ");
        }

    }

    /**
     * M&eacute;todo para adicionar los par&aacute;metros de b&uacute;squeda en
     * en el reporte de ordenes de Cm
     *
     * @param q {@link Query} objeto de jpa para realizar la consulta.
     * @param params Mapa {@link HashMap}&lt;{@link String}, {@link Object}>
     * par&aacute;metros para realizar la consulta.
     */
    private void agregarParametros(Query q, HashMap<String, Object> params, boolean esConteo) {
        List<BigDecimal> subTipoOrdenList = (List<BigDecimal>) params.get("subTipoOrden");
        List<BigDecimal> tipoOrdenList = (List<BigDecimal>) params.get("tipoOrden");
        List<String> subOrdenList = (List<String>) params.get("subTipoOrdenOfscSelected");
        List<BigDecimal> tipoOrdenListOFSC = (List<BigDecimal>) params.get("tipoOrdenOfscSelected");
        List<BigDecimal> listEstadosSelected = (List<BigDecimal>) params.get("listEstadosSelected");
        List<String> regionalList = (List<String>) params.get("listRegionalSelected");
        List<String> ciudadList = (List<String>) params.get("listRrCiudadesSelected");
        String valor = (String) params.get("valor");
        String codigoProyecto = (String) params.get("codProyecto");
        String nitCliente = (String) params.get("nitCliente");
        String numOtOnyxPadre = (String) params.get("numOtOnyxPadre");
        String numeroOtOnyxHija = (String) params.get("numeroOtOnyxHija");
        String nombreCliente = (String) params.get("nombreCliente");
        List<String> listSegmento = (List<String>) params.get("listSegmentoSelected");
        List<String> listEstadosOtCmDirSelected = (List<String>) params.get("listEstadosOtCmDirSelected");
        String listaSolucion = (String) params.get("tipoSolucionSelected");

        // fecha de Creacion orden OT ,
        if (params.get("fechaInicioOt") != null && params.get("fechaFinOt") != null) {
            if (((Date) params.get("fechaInicioOt")).before((Date) params.get("fechaFinOt"))) {
                q.setParameter("fechaInicioOt", (Date) params.get("fechaInicioOt"));
                q.setParameter("fechaFinOt", (Date) params.get("fechaFinOt"));
            } else {
                q.setParameter("fechaInicioOt", (Date) params.get("fechaInicioOt"));
            }

        }
        // fecha creacion ot hija onyx FECHA_CREACION_OT_HIJA_ONYX
        if (params.get("fechaInicioOtHijaOnyx") != null && !params.get("fechaFinOtHijaOnyx").toString().isEmpty()) {
            if (((Date) params.get("fechaInicioOtHijaOnyx")).before((Date) params.get("fechaFinOtHijaOnyx"))) {
                q.setParameter("fechaInicioOtHijaOnyx", (Date) params.get("fechaInicioOtHijaOnyx"));
                q.setParameter("fechaFinOtHijaOnyx", (Date) params.get("fechaFinOtHijaOnyx"));
            } else {
                q.setParameter("fechaInicioOtHijaOnyx", (Date) params.get("fechaInicioOtHijaOnyx"));
            }

        }
        // fecha creacion 
        if (params.get("fechaCreacionIniOnyx") != null && !params.get("fechaCreacionFinOnyx").toString().isEmpty()) {
            if (((Date) params.get("fechaCreacionIniOnyx")).before((Date) params.get("fechaCreacionFinOnyx"))) {
                q.setParameter("fechaCreacionIniOnyx", (Date) params.get("fechaCreacionIniOnyx"));
                q.setParameter("fechaCreacionFinOnyx", (Date) params.get("fechaCreacionFinOnyx"));
            } else {
                q.setParameter("fechaCreacionIniOnyx", (Date) params.get("fechaCreacionIniOnyx"));
            }

        }
        // fecha de agendamiento OFSC FECHA_AGENDA
        if (params.get("fechaInicioAgendOFSC") != null && !params.get("fechaFinAgendOFSC").toString().isEmpty()) {
            if (((Date) params.get("fechaInicioAgendOFSC")).before((Date) params.get("fechaFinAgendOFSC"))) {
                q.setParameter("fechaInicioAgendOFSC", (Date) params.get("fechaInicioAgendOFSC"));
                q.setParameter("fechaFinAgendOFSC", (Date) params.get("fechaFinAgendOFSC"));
            } else {
                q.setParameter("fechaInicioAgendOFSC", (Date) params.get("fechaInicioAgendOFSC"));
            }

        }

        //fecha asignacion del tecnico FECHA_INICIO_VT
        if (params.get("fechaInicioAsigTecnico") != null && !params.get("fechaFinAsigTecnico").toString().isEmpty()) {
            if (((Date) params.get("fechaInicioAsigTecnico")).before((Date) params.get("fechaFinAsigTecnico"))) {
                q.setParameter("fechaInicioAsigTecnico", (Date) params.get("fechaInicioAsigTecnico"));
                q.setParameter("fechaFinAsigTecnico", (Date) params.get("fechaFinAsigTecnico"));
            } else {
                q.setParameter("fechaInicioAsigTecnico", (Date) params.get("fechaInicioAsigTecnico"));
            }

        }
        //fecha cierre de agenda FECHA_FIN_VT
        if (params.get("fechaInicioCierreAgenda") != null && !params.get("fechaFinCierreAgenda").toString().isEmpty()) {
            if (((Date) params.get("fechaInicioCierreAgenda")).before((Date) params.get("fechaFinCierreAgenda"))) {
                q.setParameter("fechaInicioCierreAgenda", (Date) params.get("fechaInicioCierreAgenda"));
                q.setParameter("fechaFinCierreAgenda", (Date) params.get("fechaFinCierreAgenda"));
            } else {
                q.setParameter("fechaInicioCierreAgenda", (Date) params.get("fechaInicioCierreAgenda"));
            }
        }

        //fecha cancelacion de agenda, estado cancelada id=22998 
        if (params.get("fechaInicioCancAgenda") != null && !params.get("fechaFinCancAgenda").toString().isEmpty()) {

            if (((Date) params.get("fechaInicioCancAgenda")).before((Date) params.get("fechaFinCancAgenda"))) {
                q.setParameter("fechaInicioCancAgenda", (Date) params.get("fechaInicioCancAgenda"));
                q.setParameter("fechaFinCancAgenda", (Date) params.get("fechaFinCancAgenda"));
            } else {
                q.setParameter("fechaInicioCancAgenda", (Date) params.get("fechaInicioCancAgenda"));
            }

        }
        //fecha de reagendamiento ???? 
        if ((params.get("fechaInicioReagenda") != null && !params.get("fechaFinReagenda").toString().isEmpty())) {
            if (((Date) params.get("fechaInicioReagenda")).before((Date) params.get("fechaFinReagenda"))) {
                q.setParameter("fechaInicioReagenda", (Date) params.get("fechaInicioReagenda"));
                q.setParameter("fechaFinReagenda", (Date) params.get("fechaFinReagenda"));
            } else {
                q.setParameter("fechaInicioReagenda", (Date) params.get("fechaFinReagenda"));
            }

        }

        //fecha de suspencion
        if ((params.get("fechaInicioSuspension") != null && !params.get("fechaFinSuspension").toString().isEmpty())) {

            if (((Date) params.get("fechaInicioSuspension")).before((Date) params.get("fechaFinSuspension"))) {
                q.setParameter("fechaInicioSuspension", (Date) params.get("fechaInicioSuspension"));
                q.setParameter("fechaFinSuspension", (Date) params.get("fechaFinSuspension"));
            } else {
                q.setParameter("fechaInicioReagenda", (Date) params.get("fechaInicioSuspension"));
            }
        }

        if (tipoOrdenList != null && !tipoOrdenList.isEmpty()) {
            q.setParameter("tipoOrdenList", (tipoOrdenList.isEmpty()) ? null : tipoOrdenList);
        }
        if (subOrdenList != null && !subOrdenList.isEmpty()) {
            q.setParameter("subOrdenList", (subOrdenList.isEmpty()) ? null : subOrdenList);
        }
        if (subTipoOrdenList != null && !subTipoOrdenList.isEmpty()) {
            q.setParameter("subTipoOrdenList", (subTipoOrdenList.isEmpty()) ? null : subTipoOrdenList);
        }
        // estados inyternos
        if (listEstadosOtCmDirSelected != null && !listEstadosOtCmDirSelected.isEmpty()) {
            q.setParameter("listEstadosOtCmDirSelected", (listEstadosOtCmDirSelected.isEmpty()) ? null : listEstadosOtCmDirSelected);
        }
        if (tipoOrdenListOFSC != null && !tipoOrdenListOFSC.isEmpty()) {
            q.setParameter("tipoOrdenListOFSC", (tipoOrdenListOFSC.isEmpty()) ? null : tipoOrdenListOFSC);
        }
        // estados
        if (listEstadosSelected != null && !listEstadosSelected.isEmpty()) {
            q.setParameter("listEstadosSelected", (listEstadosSelected.isEmpty()) ? null : listEstadosSelected);
        }

        if ((params.get("otIni") != null)) {
            q.setParameter("otIni",
                    (null == params.get("otIni")) ? null
                    : params.get("otIni"));
        }
        if ((params.get("otFin") != null)) {
            q.setParameter("otFin",
                    (null == params.get("otFin")) ? null
                    : params.get("otFin"));
        }

        // regional MER
        if (regionalList != null && !regionalList.isEmpty()) {
            q.setParameter("regionalList", (regionalList.isEmpty()) ? null : regionalList);
        }
        // ciudad MER
        if (ciudadList != null && !ciudadList.isEmpty()) {
            q.setParameter("ciudadList", (ciudadList.isEmpty()) ? null : ciudadList);
        }
        // Segmento
        if (listSegmento != null && !listSegmento.isEmpty()) {
            q.setParameter("listSegmento", (listSegmento.isEmpty()) ? null : listSegmento);
        }

        // codigo de proyecto
        if (codigoProyecto != null && !codigoProyecto.equals("")) {
            q.setParameter("codProyecto", valor.equals("") ? "" : valor);

        }
        // Nit Cliente
        if (nitCliente != null && !nitCliente.isEmpty()) {
            q.setParameter("nitCliente", valor.equals("") ? "" : valor);

        }
        // numero Ot Padre
        if (numOtOnyxPadre != null && !numOtOnyxPadre.isEmpty()) {
            q.setParameter("numOtOnyxPadre", valor.equals("") ? "" : valor);
        }
        // numero Ot Hija
        if (numeroOtOnyxHija != null && !numeroOtOnyxHija.isEmpty()) {
            q.setParameter("numeroOtOnyxHija", (valor.equals("")) ? "" : valor);
        }
        // nombre del cliente
        if (nombreCliente != null && !nombreCliente.isEmpty()) {
            q.setParameter("nombreCliente", (valor.equals("")) ? "" : "%" + valor.toUpperCase() + "%");
        }
        // tipo solucion
        if (listaSolucion != null && !listaSolucion.isEmpty()) {
            q.setParameter("listaSolucion", (listaSolucion.isEmpty()) ? null : listaSolucion);
        }

    }

    /**
     * M&eacute;todo para adicionar los par&aacute;metros de b&uacute;squeda en
     * en el reporte de ordenes de Cm
     *
     * @param q {@link Query} objeto de jpa para realizar la consulta.
     * @param params Mapa {@link HashMap}&lt;{@link String}, {@link Object}>
     * par&aacute;metros para realizar la consulta.
     */
    private void agregarParametrosHistorico(Query q, HashMap<String, Object> params, boolean esConteo) {
        List<BigDecimal> subTipoOrdenList = (List<BigDecimal>) params.get("subTipoOrden");
        List<BigDecimal> tipoOrdenList = (List<BigDecimal>) params.get("tipoOrden");
        List<String> subOrdenList = (List<String>) params.get("subTipoOrdenOfscSelected");
        List<BigDecimal> tipoOrdenListOFSC = (List<BigDecimal>) params.get("tipoOrdenOfscSelected");
        List<BigDecimal> estadosList = (List<BigDecimal>) params.get("estadoInternolist");
        // fecha de Creacion orden OT ,
        if (params.get("fechaInicioOt") != null && params.get("fechaFinOt") != null) {
            if (((Date) params.get("fechaInicioOt")).before((Date) params.get("fechaFinOt"))) {
                q.setParameter("fechaInicioOt", (Date) params.get("fechaInicioOt"));
                q.setParameter("fechaFinOt", (Date) params.get("fechaFinOt"));
            } else {
                q.setParameter("fechaInicioOt", (Date) params.get("fechaInicioOt"));
            }

        }

        // fecha creacion ot hija onyx FECHA_CREACION_OT_HIJA_ONYX
        if (params.get("fechaInicioOtOnyx") != null && !params.get("fechaFinOtOnyx").toString().isEmpty()) {
            if (((Date) params.get("fechaInicioOtOnyx")).before((Date) params.get("fechaFinOtOnyx"))) {
                q.setParameter("fechaInicioOtOnyx", (Date) params.get("fechaInicioOtOnyx"));
                q.setParameter("fechaFinOtOnyx", (Date) params.get("fechaFinOtOnyx"));
            } else {
                q.setParameter("fechaInicioOtOnyx", (Date) params.get("fechaInicioOtOnyx"));
            }

        }

        // fecha de agendamiento OFSC FECHA_AGENDA
        if (params.get("fechaInicioAgendOFSC") != null && !params.get("fechaFinAgendOFSC").toString().isEmpty()) {
            if (((Date) params.get("fechaInicioAgendOFSC")).before((Date) params.get("fechaFinAgendOFSC"))) {
                q.setParameter("fechaInicioAgendOFSC", (Date) params.get("fechaInicioAgendOFSC"));
                q.setParameter("fechaFinAgendOFSC", (Date) params.get("fechaFinAgendOFSC"));
            } else {
                q.setParameter("fechaInicioAgendOFSC", (Date) params.get("fechaInicioAgendOFSC"));
            }

        }

        //fecha asignacion del tecnico FECHA_INICIO_VT
        if (params.get("fechaInicioAsigTecnico") != null && !params.get("fechaFinAsigTecnico").toString().isEmpty()) {
            if (((Date) params.get("fechaInicioAsigTecnico")).before((Date) params.get("fechaFinAsigTecnico"))) {
                q.setParameter("fechaInicioAsigTecnico", (Date) params.get("fechaInicioAsigTecnico"));
                q.setParameter("fechaFinAsigTecnico", (Date) params.get("fechaFinAsigTecnico"));
            } else {
                q.setParameter("fechaInicioAsigTecnico", (Date) params.get("fechaInicioAsigTecnico"));
            }

        }

        //fecha cierre de agenda FECHA_FIN_VT
        if (params.get("fechaInicioCierreAgenda") != null && !params.get("fechaFinCierreAgenda").toString().isEmpty()) {
            if (((Date) params.get("fechaInicioCierreAgenda")).before((Date) params.get("fechaFinCierreAgenda"))) {
                q.setParameter("fechaInicioCierreAgenda", (Date) params.get("fechaInicioCierreAgenda"));
                q.setParameter("fechaFinCierreAgenda", (Date) params.get("fechaFinCierreAgenda"));
            } else {
                q.setParameter("fechaInicioCierreAgenda", (Date) params.get("fechaInicioCierreAgenda"));
            }
        }

        //fecha cancelacion de agenda, estado cancelada id=22998 
        if (params.get("fechaInicioCancAgenda") != null && !params.get("fechaFinCancAgenda").toString().isEmpty()) {

            if (((Date) params.get("fechaInicioCancAgenda")).before((Date) params.get("fechaFinCancAgenda"))) {
                q.setParameter("fechaInicioCancAgenda", (Date) params.get("fechaInicioCancAgenda"));
                q.setParameter("fechaFinCancAgenda", (Date) params.get("fechaFinCancAgenda"));
            } else {
                q.setParameter("fechaInicioCancAgenda", (Date) params.get("fechaInicioCancAgenda"));
            }

        }

        //fecha de reagendamiento ???? 
        if ((params.get("fechaInicioReagenda") != null && !params.get("fechaFinReagenda").toString().isEmpty())) {
            if (((Date) params.get("fechaInicioReagenda")).before((Date) params.get("fechaFinReagenda"))) {
                q.setParameter("fechaInicioReagenda", (Date) params.get("fechaInicioReagenda"));
                q.setParameter("fechaFinReagenda", (Date) params.get("fechaFinReagenda"));
            } else {
                q.setParameter("fechaInicioReagenda", (Date) params.get("fechaFinReagenda"));
            }

        }
        //fecha de suspencion
        if ((params.get("fechaInicioSuspension") != null && !params.get("fechaFinSuspension").toString().isEmpty())) {

            if (((Date) params.get("fechaInicioSuspension")).before((Date) params.get("fechaFinSuspension"))) {
                q.setParameter("fechaInicioSuspension", (Date) params.get("fechaInicioSuspension"));
                q.setParameter("fechaFinSuspension", (Date) params.get("fechaFinSuspension"));
            } else {
                q.setParameter("fechaInicioReagenda", (Date) params.get("fechaInicioSuspension"));
            }
        }

        if (tipoOrdenList != null && !tipoOrdenList.isEmpty()) {
            q.setParameter("tipoOrdenList", (tipoOrdenList.isEmpty()) ? null : tipoOrdenList);
        }
        if (subOrdenList != null && !subOrdenList.isEmpty()) {
            q.setParameter("subOrdenList", (subOrdenList.isEmpty()) ? null : subOrdenList);
        }
        if (subTipoOrdenList != null && !subTipoOrdenList.isEmpty()) {
            q.setParameter("subTipoOrdenList", (tipoOrdenList.isEmpty()) ? null : subTipoOrdenList);
        }
        if (estadosList != null && !estadosList.isEmpty()) {
            q.setParameter("estadosList", (estadosList.isEmpty()) ? null : estadosList);
        }
        if (tipoOrdenListOFSC != null && !tipoOrdenListOFSC.isEmpty()) {
            q.setParameter("tipoOrdenListOFSC", (tipoOrdenListOFSC.isEmpty()) ? null : tipoOrdenListOFSC);
        }
        if ((params.get("otIni") != null)) {
            q.setParameter("otIni",
                    (null == params.get("otIni")) ? null
                    : params.get("otIni"));
        }
        if ((params.get("otFin") != null)) {
            q.setParameter("otFin",
                    (null == params.get("otFin")) ? null
                    : params.get("otFin"));
        }
    }

    /**
     * Busqueda el ultimo estado de la orden en auditoria con estado razonado
     *
     * @author victor bocanegra
     * @param ot valor de la cuenta matriz para la busqueda
     * @param estadoIntActual valor de la basica de tecnologia para la busqueda
     * @return CmtOrdenTrabajoAuditoriaMgl
     */
    public CmtOrdenTrabajoAuditoriaMgl findUltimoEstadoOtRazonada(CmtOrdenTrabajoMgl ot,
            BigDecimal estadoIntActual) throws ApplicationException {
        try {
            Query q = entityManager.createQuery("SELECT aud FROM CmtOrdenTrabajoAuditoriaMgl aud "
                    + " WHERE aud.otIdObj = :otIdObj "
                    + " AND   aud.otAuditoriaId = (SELECT max(aud2.otAuditoriaId) "
                    + " FROM  CmtOrdenTrabajoAuditoriaMgl  aud2  "
                    + " WHERE aud2.estadoInternoObj.basicaId <> :basicaId  "
                    + " AND   aud2.otIdObj = :otIdObj)");

            q.setParameter("otIdObj", ot);
            q.setParameter("basicaId", estadoIntActual);
            q.setHint(JAVAX_PERSISTENCE_CACHE_STORE_MODE, REFRESH);

            return (CmtOrdenTrabajoAuditoriaMgl) q.getSingleResult();
        } catch (Exception e) {
            String msg = SE_PRODUJO_UN_ERROR_AL_MOMENTO_DE_EJECUTAR_EL_METODO + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            return null;
        }
    }

    public void eliminarOTByCM(BigDecimal idCuentaMatriz) throws ApplicationException {
        try {
            entityManager.getTransaction().begin();
            Query q = entityManager.createQuery("UPDATE CmtOrdenTrabajoMgl ot "
                    + "SET ot.estadoRegistro = 0, ot.fechaEdicion =:fechaEdicion WHERE ot.cmObj.cuentaMatrizId =:idCuentaMatriz");

            q.setParameter("idCuentaMatriz", idCuentaMatriz);
            q.setParameter("fechaEdicion", new Date());

            q.executeUpdate();
            entityManager.getTransaction().commit();

        } catch (Exception e) {
            String msg = SE_PRODUJO_UN_ERROR_AL_MOMENTO_DE_EJECUTAR_EL_METODO + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
        }
    }

    public int getCountReporteHistoricoOtCM(HashMap<String, Object> params,
            boolean contar, int firstResult, int maxResults, String usuario) throws ApplicationException {

        StringBuilder sql = new StringBuilder();

        sql.append("SELECT distinct o, a,oy  "
                + "FROM CmtOrdenTrabajoMgl o  "
                + "left join o.listAgendas a "
                + "left join o.listOnyx oy "
                + "left join o.listAuditoria ot_aud "
                + "left join a.listAuditoria  ag_aud "
                + "left join oy.listAuditoria onyx_aud "
                + "WHERE (o.estadoRegistro is null or o.estadoRegistro = 1 )"
                + "and (oy.estadoRegistro is null or oy.estadoRegistro = 1 )");

        generarSelectReporteHistoricoOtCm(sql, params, true);
        Query q = entityManager.createQuery(sql.toString());
        agregarParametrosHistorico(q, params, true);
        q.setFirstResult(firstResult);
        q.setMaxResults(maxResults);
        int resultCount;
        resultCount = q.getSingleResult() == null ? 0 : ((Long) q.getSingleResult()).intValue();
        getEntityManager().clear();
        return resultCount;

    }

    /**
     * Busqueda de todas las Ordenes de trabajo por id Enlace y tecnologia
     * GPON/FOU
     *
     * @author Victor Bocanegra
     * @param idEnlace valor id del enlace para la busqueda
     * @param tecnologias lista de tecnologia para la busqueda
     * @return Retorna una lista de Ordenes de trabajo
     * @throws ApplicationException
     */
    public List<CmtOrdenTrabajoMgl> findByIdEnlaceAndTecnologia(String idEnlace,
            List<BigDecimal> tecnologias)
            throws ApplicationException {
        try {
            Query q = entityManager.createQuery("SELECT c FROM CmtOrdenTrabajoMgl c "
                    + " WHERE c.enlaceId = :enlaceId "
                    + " AND c.basicaIdTecnologia.basicaId IN :tecnologias AND c.estadoRegistro = 1"
                    + " ORDER BY c.fechaCreacion DESC");

            q.setParameter("enlaceId", idEnlace);
            q.setParameter("tecnologias", tecnologias);
            q.setHint(JAVAX_PERSISTENCE_CACHE_STORE_MODE, REFRESH);
            return q.getResultList();
        } catch (NoResultException e) {
            String msg = SE_PRODUJO_UN_ERROR_AL_MOMENTO_DE_EJECUTAR_EL_METODO + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            return null;
        } catch (Exception e) {
            String msg = SE_PRODUJO_UN_ERROR_AL_MOMENTO_DE_EJECUTAR_EL_METODO + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            return null;
        }
    }

    /**
     * M&eacute;todo para generar el reporte de ordenes de trabajo de CM
     *
     * @param sql {@link StringBuilder} objeto para generar el String de la
     * consulta.
     * @param params Mapa {@link HashMap}&lt;{@link String}, {@link Object}>
     * par&aacute;metros para realizar la consulta.
     *
     */
    private void generarSelectReporteOtDIR(StringBuilder sql, HashMap<String, Object> params, boolean esConteo) {
        List<BigDecimal> tipoOrdenList = (List<BigDecimal>) params.get("tipoOrden");
        List<BigDecimal> subtipoOrdenList = (List<BigDecimal>) params.get("subTipoOrden");
        List<String> subOrdenList = (List<String>) params.get("subTipoOrdenOfscSelected");
        List<BigDecimal> tipoOrdenListOSFC = (List<BigDecimal>) params.get("tipoOrdenOfscSelected");
        List<BigDecimal> listEstadosSelected = (List<BigDecimal>) params.get("listEstadosSelected");
        List<String> regionalList = (List<String>) params.get("listRegionalSelected");
        List<String> ciudadList = (List<String>) params.get("listRrCiudadesSelected");
        List<String> listSegmento = (List<String>) params.get("listSegmentoSelected");
        List<String> listEstadosOtCmDirSelected = (List<String>) params.get("listEstadosOtCmDirSelected");
        String listaSolucion = (String) params.get("tipoSolucionSelected");
        String codigoProyecto = (String) params.get("codProyecto");

        // fecha de Creacion orden OT ,
        if (params.get("fechaInicioOt") != null && (params.get("fechaFinOt") != null)) {
            SimpleDateFormat diaInicial = new SimpleDateFormat("dd/MM/yyyy");
            String diaIni = diaInicial.format(params.get("fechaInicioOt"));
            SimpleDateFormat diaFin = new SimpleDateFormat("dd/MM/yyyy");
            String diaFinal = diaFin.format(params.get("fechaFinOt"));
            if (diaIni != null && diaFinal != null) {
                if (((Date) params.get("fechaInicioOt")).before((Date) params.get("fechaFinOt"))) {
//                    sql.append("AND func('trunc', o.fechaCreacion) BETWEEN :fechaInicioOt and  :fechaFinOt");
                    sql.append(" AND o.fechaCreacion BETWEEN :fechaInicioOt and  :fechaFinOt");
                } else {
                    sql.append(" AND  func('trunc', o.fechaCreacion) = :fechaInicioOt");
                }
            }

        }

        // fecha creacion ot hija onyx FECHA_CREACION_OT_HIJA_ONYX
        if (params.get("fechaInicioOtHijaOnyx") != null && !params.get("fechaFinOtHijaOnyx").toString().isEmpty()) {
            SimpleDateFormat diaInicial = new SimpleDateFormat("dd/MM/yyyy");
            String diaIni = diaInicial.format(params.get("fechaFinOtHijaOnyx"));
            SimpleDateFormat diaFin = new SimpleDateFormat("dd/MM/yyyy");
            String diaFinal = diaFin.format(params.get("fechaFinOtHijaOnyx"));
            if (diaIni != null && diaFinal != null) {
                if (((Date) params.get("fechaInicioOtHijaOnyx")).before((Date) params.get("fechaFinOtHijaOnyx"))) {
                    sql.append(" AND func('trunc', oy.fecha_Creacion_Ot_Hija_Onyx) BETWEEN :fechaInicioOtHijaOnyx and  :fechaFinOtHijaOnyx");
                } else {
                    sql.append(" AND func('trunc', oy.fecha_Creacion_Ot_Hija_Onyx) = :fechaInicioOtHijaOnyx");
                }
            }

        }

        // fecha creacion onyx
        if (params.get("fechaCreacionIniOnyx") != null && !params.get("fechaCreacionFinOnyx").toString().isEmpty()) {
            SimpleDateFormat diaInicial = new SimpleDateFormat("dd/MM/yyyy");
            String diaIni = diaInicial.format(params.get("fechaCreacionIniOnyx"));
            SimpleDateFormat diaFin = new SimpleDateFormat("dd/MM/yyyy");
            String diaFinal = diaFin.format(params.get("fechaCreacionFinOnyx"));
            if (diaIni != null && diaFinal != null) {
                if (((Date) params.get("fechaCreacionIniOnyx")).before((Date) params.get("fechaCreacionFinOnyx"))) {
                    sql.append(" AND func('trunc', oy.fechaCreacion) BETWEEN :fechaCreacionIniOnyx and  :fechaCreacionFinOnyx");
                } else {
                    sql.append(" AND func('trunc', oy.fechaCreacion) = :fechaCreacionIniOnyx");
                }
            }

        }
        // fecha de agendamiento OFSC FECHA_AGENDA
        if (params.get("fechaInicioAgendOFSC") != null && !params.get("fechaFinAgendOFSC").toString().isEmpty()) {
            SimpleDateFormat diaInicial = new SimpleDateFormat("dd/MM/yyyy");
            String diaIni = diaInicial.format(params.get("fechaInicioAgendOFSC"));
            SimpleDateFormat diaFin = new SimpleDateFormat("dd/MM/yyyy");
            String diaFinal = diaFin.format(params.get("fechaFinAgendOFSC"));
            if (diaIni != null && diaFinal != null) {
                if (((Date) params.get("fechaInicioAgendOFSC")).before((Date) params.get("fechaFinAgendOFSC"))) {
                    sql.append(" AND func('trunc', a.fechaAgenda) BETWEEN :fechaInicioAgendOFSC and  :fechaFinAgendOFSC");
                } else {
                    sql.append(" AND func('trunc', a.fechaAgenda) = :fechaInicioAgendOFSC");
                }
            }

        }
        //fecha asignacion del tecnico FECHA_INICIO_VT
        if (params.get("fechaInicioAsigTecnico") != null && !params.get("fechaFinAsigTecnico").toString().isEmpty()) {
            SimpleDateFormat diaInicial = new SimpleDateFormat("dd/MM/yyyy");
            String diaIni = diaInicial.format(params.get("fechaInicioAsigTecnico"));
            SimpleDateFormat diaFin = new SimpleDateFormat("dd/MM/yyyy");
            String diaFinal = diaFin.format(params.get("fechaFinAsigTecnico"));
            if (diaIni != null && diaFinal != null) {
                if (((Date) params.get("fechaInicioAsigTecnico")).before((Date) params.get("fechaFinAsigTecnico"))) {
                    sql.append(" AND func('trunc', a.fechaAsigTecnico) BETWEEN :fechaInicioAsigTecnico and  :fechaFinAsigTecnico");
                } else {
                    sql.append(" AND func('trunc', a.fechaAsigTecnico) = :fechaInicioAsigTecnico");
                }
            }

        }
        //fecha cierre de agenda FECHA_FIN_VT
        if (params.get("fechaInicioCierreAgenda") != null && !params.get("fechaFinCierreAgenda").toString().isEmpty()) {
            SimpleDateFormat diaInicial = new SimpleDateFormat("dd/MM/yyyy");
            String diaIni = diaInicial.format(params.get("fechaInicioCierreAgenda"));
            SimpleDateFormat diaFin = new SimpleDateFormat("dd/MM/yyyy");
            String diaFinal = diaFin.format(params.get("fechaFinCierreAgenda"));
            if (diaIni != null && diaFinal != null) {
                if (((Date) params.get("fechaInicioCierreAgenda")).before((Date) params.get("fechaFinCierreAgenda"))) {
                    sql.append(" AND func('trunc', a.fechaFinVt) BETWEEN :fechaInicioCierreAgenda and  :fechaFinCierreAgenda");
                } else {
                    sql.append(" AND func('trunc', a.fechaFinVt) = :fechaInicioCierreAgenda");
                }
            }
        }
        //fecha cancelacion de agenda, estado cancelada id=22998 
        if (params.get("fechaInicioCancAgenda") != null && !params.get("fechaFinCancAgenda").toString().isEmpty()) {
            SimpleDateFormat diaInicial = new SimpleDateFormat("dd/MM/yyyy");
            String diaIni = diaInicial.format(params.get("fechaInicioCancAgenda"));
            SimpleDateFormat diaFin = new SimpleDateFormat("dd/MM/yyyy");
            String diaFinal = diaFin.format(params.get("fechaFinCancAgenda"));
            if (diaIni != null && diaFinal != null) {
                if (((Date) params.get("fechaInicioCancAgenda")).before((Date) params.get("fechaFinCancAgenda"))) {
                    sql.append(" AND func('trunc', a.fechaEdicion) BETWEEN :fechaInicioCancAgenda and  :fechaFinCancAgenda");
                } else {
                    sql.append(" AND func('trunc', a.fechaEdicion) = :fechaInicioCancAgenda");
                }
            }
            CmtBasicaMglDaoImpl cmtBasicaMglDaoImpl = new CmtBasicaMglDaoImpl();
            CmtBasicaMgl agendaCancelada = null;
            try {
                agendaCancelada = cmtBasicaMglDaoImpl.findByCodigoInternoApp(Constant.BASICA_EST_AGENDA_CANCELADA);
            } catch (ApplicationException ex) {
                String msgError = "Error en " + ClassUtils.getCurrentMethodName(this.getClass()) + ": " + ex.getMessage();
                LOGGER.error(msgError, ex);
            }
            if (agendaCancelada != null) {
                sql.append(" AND  a.basicaIdEstadoAgenda.basicaId  = ").append(agendaCancelada.getBasicaId()).append(" AND  a.estadoRegistro = 1");
            }

        }
        //fecha de reagendamiento ???? 

        if ((params.get("fechaInicioReagenda") != null && !params.get("fechaFinReagenda").toString().isEmpty())) {
            SimpleDateFormat diaInicial = new SimpleDateFormat("dd/MM/yyyy ");
            String diaIni = diaInicial.format(params.get("fechaInicioReagenda"));
            SimpleDateFormat diaFin = new SimpleDateFormat("dd/MM/yyyy");
            String diaFinal = diaFin.format(params.get("fechaFinReagenda"));
            if (diaIni != null && diaFinal != null) {
                if (((Date) params.get("fechaInicioReagenda")).before((Date) params.get("fechaFinReagenda"))) {
                    sql.append(" AND func('trunc',  a.fechaReagenda) BETWEEN :fechaInicioReagenda and  :fechaFinReagenda");
                } else {
                    sql.append(" AND func('trunc', a.fechaReagenda) = :fechaInicioReagenda");
                }
            }

            CmtBasicaMglDaoImpl cmtBasicaMglDaoImpl = new CmtBasicaMglDaoImpl();
            CmtBasicaMgl agendaReagendada = null;
            try {
                agendaReagendada = cmtBasicaMglDaoImpl.findByCodigoInternoApp(Constant.BASICA_EST_AGENDA_REAGENDADA);
            } catch (ApplicationException ex) {
                String msgError = "Error en " + ClassUtils.getCurrentMethodName(this.getClass()) + ": " + ex.getMessage();
                LOGGER.error(msgError, ex);
            }
            if (agendaReagendada != null) {
                sql.append(" AND  a.basicaIdEstadoAgenda.basicaId  = ").append(agendaReagendada.getBasicaId()).append(" AND  a.estadoRegistro = 1");
            }

        }
        //fecha de suspencion
        if ((params.get("fechaInicioSuspension") != null && !params.get("fechaFinSuspension").toString().isEmpty())) {
            SimpleDateFormat diaInicial = new SimpleDateFormat("dd/MM/yyyy");
            String diaIni = diaInicial.format(params.get("fechaInicioSuspension"));
            SimpleDateFormat diaFin = new SimpleDateFormat("dd/MM/yyyy");
            String diaFinal = diaFin.format(params.get("fechaFinSuspension"));
            if (diaIni != null && diaFinal != null) {
                if (((Date) params.get("fechaInicioSuspension")).before((Date) params.get("fechaFinSuspension"))) {
                    sql.append(" AND func('trunc',  a.fechaSuspendeVt) BETWEEN :fechaInicioSuspension and  :fechaFinSuspension");
                } else {
                    sql.append(" AND func('trunc', a.fechaSuspendeVt) = :fechaInicioSuspension");
                }
            }

        }

        // numero de OT
        if (params.get("otIni") != null && (params.get("otFin") != null)) {
            sql.append(" and o.otHhppId BETWEEN :otIni and  :otFin ");
        }
        if (tipoOrdenList != null && !tipoOrdenList.isEmpty()) {
            sql.append("  AND  o.tipoOtHhppId.tipoOtId  IN :tipoOrdenList  ");
        }
        if (subtipoOrdenList != null && !subtipoOrdenList.isEmpty()) {
            sql.append("  AND  O.tipoOtHhppId.tipoTrabajoOFSC.basicaId IN :subtipoOrdenList");
        }
        if (subOrdenList != null && !subOrdenList.isEmpty()) {
            sql.append("  AND  a.subTipoWorkFoce  IN :subOrdenList  ");
        }
        if (tipoOrdenListOSFC != null && !tipoOrdenListOSFC.isEmpty()) {
            sql.append("  AND  O.tipoOtHhppId.tipoTrabajoOFSC.basicaId  IN :tipoOrdenListOSFC  ");
        }
        // estado interno
        if (listEstadosOtCmDirSelected != null && !listEstadosOtCmDirSelected.isEmpty()) {
            sql.append("  AND  O.estadoInternoInicial.basicaId  IN :listEstadosOtCmDirSelected ");
        }
        // estados
        if (listEstadosSelected != null && !listEstadosSelected.isEmpty()) {
            sql.append("  AND  O.estadoGeneral.basicaId  IN :listEstadosSelected ");
        }
        //CAMPOS NUEVOS 
        // regional MER 
        if (regionalList != null && !regionalList.isEmpty()) {
            sql.append("  AND  t.nodo.comId.regionalRr.codigoRr  IN :regionalList  ");
        }
        // ciudad MER .nodo.comId.codigoRr
        if (ciudadList != null && !ciudadList.isEmpty()) {
            sql.append("  AND  t.nodo.comId.codigoRr  IN :ciudadList  ");
        }
        // segmento 
        if (listSegmento != null && !listSegmento.isEmpty()) {
            sql.append("  AND oy.segmento_Onyx  IN :listSegmento ");

        }

        // codigo de proyecto
        if (codigoProyecto != null && !codigoProyecto.equals("")) {
            sql.append("  AND  oy.codigoProyecto  = :codigoProyecto");

        }
        // Nit Cliente
        if (params.get("nitCliente") != null && !params.get("nitCliente").toString().isEmpty()) {
            sql.append("  AND oy.nit_Cliente_Onyx  like :nitCliente");

        }
        // numero Ot Padre
        if (params.get("numOtOnyxPadre") != null && !params.get("numOtOnyxPadre").toString().isEmpty()) {
            sql.append("  AND  (oy.Onyx_Ot_Padre_Cm  = :numOtOnyxPadre or oy.Onyx_Ot_Padre_Dir  = :numOtOnyxPadre )");

        }
        // numero Ot Hija
        if (params.get("numeroOtOnyxHija") != null && !params.get("numeroOtOnyxHija").toString().isEmpty()) {
            sql.append("  AND  (oy.Onyx_Ot_Hija_Dir  = :numeroOtOnyxHija or oy.Onyx_Ot_Hija_Cm  = :numeroOtOnyxHija )");

        }
        // nombre del cliente
        if (params.get("nombreCliente") != null && !params.get("nombreCliente").toString().isEmpty()) {
            sql.append("  AND  UPPER(oy.nombre_Cliente_Onyx)  like UPPER(:nombreCliente)");

        }

        // Tipo de solucion
        if (listaSolucion != null && !listaSolucion.isEmpty()) {
            sql.append("  AND  oy.tipo_Servicio_Onyx   = :listaSolucion ");

        }

    }

    /**
     * M&eacute;todo para adicionar los par&aacute;metros de b&uacute;squeda en
     * en el reporte de ordenes de Cm
     *
     * @param q {@link Query} objeto de jpa para realizar la consulta.
     * @param params Mapa {@link HashMap}&lt;{@link String}, {@link Object}>
     * par&aacute;metros para realizar la consulta.
     */
    private void agregarParametrosDIR(Query q, HashMap<String, Object> params, boolean esConteo) {
        List<BigDecimal> tipoOrdenList = (List<BigDecimal>) params.get("tipoOrden");
        List<BigDecimal> subtipoOrdenList = (List<BigDecimal>) params.get("subTipoOrden");
        List<String> subOrdenList = (List<String>) params.get("subTipoOrdenOfscSelected");
        List<BigDecimal> tipoOrdenListOSFC = (List<BigDecimal>) params.get("tipoOrdenOfscSelected");
        List<BigDecimal> listEstadosSelected = (List<BigDecimal>) params.get("listEstadosSelected");
        List<String> regionalList = (List<String>) params.get("listRegionalSelected");
        List<String> ciudadList = (List<String>) params.get("listRrCiudadesSelected");
        String valor = (String) params.get("valor");
        String codigoProyecto = (String) params.get("codProyecto");
        String nitCliente = (String) params.get("nitCliente");
        String numOtOnyxPadre = (String) params.get("numOtOnyxPadre");
        String numeroOtOnyxHija = (String) params.get("numeroOtOnyxHija");
        String nombreCliente = (String) params.get("nombreCliente");
        List<String> listSegmento = (List<String>) params.get("listSegmentoSelected");
        List<String> listEstadosOtCmDirSelected = (List<String>) params.get("listEstadosOtCmDirSelected");
        String listaSolucion = (String) params.get("tipoSolucionSelected");

        // fecha de Creacion orden OT ,
        if (params.get("fechaInicioOt") != null && params.get("fechaFinOt") != null) {
            if (((Date) params.get("fechaInicioOt")).before((Date) params.get("fechaFinOt"))) {
                q.setParameter("fechaInicioOt", (Date) params.get("fechaInicioOt"));
                q.setParameter("fechaFinOt", (Date) params.get("fechaFinOt"));
            } else {
                q.setParameter("fechaInicioOt", (Date) params.get("fechaInicioOt"));
            }

        }
        // fecha creacion ot hija onyx FECHA_CREACION_OT_HIJA_ONYX
        if (params.get("fechaInicioOtHijaOnyx") != null && !params.get("fechaFinOtHijaOnyx").toString().isEmpty()) {
            if (((Date) params.get("fechaInicioOtHijaOnyx")).before((Date) params.get("fechaFinOtHijaOnyx"))) {
                q.setParameter("fechaInicioOtHijaOnyx", (Date) params.get("fechaInicioOtHijaOnyx"));
                q.setParameter("fechaFinOtHijaOnyx", (Date) params.get("fechaFinOtHijaOnyx"));
            } else {
                q.setParameter("fechaInicioOtHijaOnyx", (Date) params.get("fechaInicioOtHijaOnyx"));
            }

        }
        // fecha creacion 
        if (params.get("fechaCreacionIniOnyx") != null && !params.get("fechaCreacionFinOnyx").toString().isEmpty()) {
            if (((Date) params.get("fechaCreacionIniOnyx")).before((Date) params.get("fechaCreacionFinOnyx"))) {
                q.setParameter("fechaCreacionIniOnyx", (Date) params.get("fechaCreacionIniOnyx"));
                q.setParameter("fechaCreacionFinOnyx", (Date) params.get("fechaCreacionFinOnyx"));
            } else {
                q.setParameter("fechaCreacionIniOnyx", (Date) params.get("fechaCreacionIniOnyx"));
            }

        }

        // fecha de agendamiento OFSC FECHA_AGENDA
        if (params.get("fechaInicioAgendOFSC") != null && !params.get("fechaFinAgendOFSC").toString().isEmpty()) {
            if (((Date) params.get("fechaInicioAgendOFSC")).before((Date) params.get("fechaFinAgendOFSC"))) {
                q.setParameter("fechaInicioAgendOFSC", (Date) params.get("fechaInicioAgendOFSC"));
                q.setParameter("fechaFinAgendOFSC", (Date) params.get("fechaFinAgendOFSC"));
            } else {
                q.setParameter("fechaInicioAgendOFSC", (Date) params.get("fechaInicioAgendOFSC"));
            }

        }

        //fecha asignacion del tecnico FECHA_INICIO_VT
        if (params.get("fechaInicioAsigTecnico") != null && !params.get("fechaFinAsigTecnico").toString().isEmpty()) {
            if (((Date) params.get("fechaInicioAsigTecnico")).before((Date) params.get("fechaFinAsigTecnico"))) {
                q.setParameter("fechaInicioAsigTecnico", (Date) params.get("fechaInicioAsigTecnico"));
                q.setParameter("fechaFinAsigTecnico", (Date) params.get("fechaFinAsigTecnico"));
            } else {
                q.setParameter("fechaInicioAsigTecnico", (Date) params.get("fechaInicioAsigTecnico"));
            }

        }
        //fecha cierre de agenda FECHA_FIN_VT
        if (params.get("fechaInicioCierreAgenda") != null && !params.get("fechaFinCierreAgenda").toString().isEmpty()) {
            if (((Date) params.get("fechaInicioCierreAgenda")).before((Date) params.get("fechaFinCierreAgenda"))) {
                q.setParameter("fechaInicioCierreAgenda", (Date) params.get("fechaInicioCierreAgenda"));
                q.setParameter("fechaFinCierreAgenda", (Date) params.get("fechaFinCierreAgenda"));
            } else {
                q.setParameter("fechaInicioCierreAgenda", (Date) params.get("fechaInicioCierreAgenda"));
            }
        }
        //fecha cancelacion de agenda, estado cancelada id=22998 
        if (params.get("fechaInicioCancAgenda") != null && !params.get("fechaFinCancAgenda").toString().isEmpty()) {

            if (((Date) params.get("fechaInicioCancAgenda")).before((Date) params.get("fechaFinCancAgenda"))) {
                q.setParameter("fechaInicioCancAgenda", (Date) params.get("fechaInicioCancAgenda"));
                q.setParameter("fechaFinCancAgenda", (Date) params.get("fechaFinCancAgenda"));
            } else {
                q.setParameter("fechaInicioCancAgenda", (Date) params.get("fechaInicioCancAgenda"));
            }

        }
        //fecha de reagendamiento ???? 
        if ((params.get("fechaInicioReagenda") != null && !params.get("fechaFinReagenda").toString().isEmpty())) {
            if (((Date) params.get("fechaInicioReagenda")).before((Date) params.get("fechaFinReagenda"))) {
                q.setParameter("fechaInicioReagenda", (Date) params.get("fechaInicioReagenda"));
                q.setParameter("fechaFinReagenda", (Date) params.get("fechaFinReagenda"));
            } else {
                q.setParameter("fechaInicioReagenda", (Date) params.get("fechaFinReagenda"));
            }

        }
        //fecha de suspencion
        if ((params.get("fechaInicioSuspension") != null && !params.get("fechaFinSuspension").toString().isEmpty())) {

            if (((Date) params.get("fechaInicioSuspension")).before((Date) params.get("fechaFinSuspension"))) {
                q.setParameter("fechaInicioSuspension", (Date) params.get("fechaInicioSuspension"));
                q.setParameter("fechaFinSuspension", (Date) params.get("fechaFinSuspension"));
            } else {
                q.setParameter("fechaInicioReagenda", (Date) params.get("fechaInicioSuspension"));
            }
        }

        if (tipoOrdenList != null && !tipoOrdenList.isEmpty()) {
            q.setParameter("tipoOrdenList", (tipoOrdenList.isEmpty()) ? null : tipoOrdenList);
        }
        if (subOrdenList != null && !subOrdenList.isEmpty()) {
            q.setParameter("subOrdenList", (subOrdenList.isEmpty()) ? null : subOrdenList);
        }
        if (subtipoOrdenList != null && !subtipoOrdenList.isEmpty()) {
            q.setParameter("subtipoOrdenList", (subtipoOrdenList.isEmpty()) ? null : subtipoOrdenList);
        }
        if (listEstadosOtCmDirSelected != null && !listEstadosOtCmDirSelected.isEmpty()) {
            q.setParameter("listEstadosOtCmDirSelected", (listEstadosOtCmDirSelected.isEmpty()) ? null : listEstadosOtCmDirSelected);
        }
        if (tipoOrdenListOSFC != null && !tipoOrdenListOSFC.isEmpty()) {
            q.setParameter("tipoOrdenListOSFC", (tipoOrdenListOSFC.isEmpty()) ? null : tipoOrdenListOSFC);
        }
        if (listEstadosSelected != null && !listEstadosSelected.isEmpty()) {
            q.setParameter("listEstadosSelected", (listEstadosSelected.isEmpty()) ? null : listEstadosSelected);
        }

        if ((params.get("otIni") != null)) {
            q.setParameter("otIni",
                    (null == params.get("otIni")) ? null
                    : params.get("otIni"));
        }
        if ((params.get("otFin") != null)) {
            q.setParameter("otFin",
                    (null == params.get("otFin")) ? null
                    : params.get("otFin"));
        }

        // regional MER
        if (regionalList != null && !regionalList.isEmpty()) {
            q.setParameter("regionalList", (regionalList.isEmpty()) ? null : regionalList);
        }
        // ciudad MER
        if (ciudadList != null && !ciudadList.isEmpty()) {
            q.setParameter("ciudadList", (ciudadList.isEmpty()) ? null : ciudadList);
        }
        // Segmento
        if (listSegmento != null && !listSegmento.isEmpty()) {
            q.setParameter("listSegmento", (listSegmento.isEmpty()) ? null : listSegmento);
        }
        // Tipo Solucion
        if (listaSolucion != null && !listaSolucion.equals("")) {
            q.setParameter("listaSolucion", listaSolucion.equals("") ? "" : listaSolucion);
        }

        // codigo de proyecto
        if (codigoProyecto != null && !codigoProyecto.isEmpty()) {
            q.setParameter("codigoProyecto", valor.equals("") ? "" : valor);

        }
        // Nit Cliente
        if (nitCliente != null && !nitCliente.isEmpty()) {
            q.setParameter("nitCliente", valor.equals("") ? "" : valor);

        }
        // numero Ot Padre
        if (numOtOnyxPadre != null && !numOtOnyxPadre.isEmpty()) {
            q.setParameter("numOtOnyxPadre", valor.equals("") ? "" : valor);
        }
        // numero Ot Hija
        if (numeroOtOnyxHija != null && !numeroOtOnyxHija.isEmpty()) {
            q.setParameter("numeroOtOnyxHija", (valor.equals("")) ? "" : valor);
        }
        // nombre del cliente
        if (nombreCliente != null && !nombreCliente.isEmpty()) {
            q.setParameter("nombreCliente", (valor.equals("")) ? "" : "%" + valor.toUpperCase() + "%");
        }

    }

    /**
     * Busca una Orden de Trabajo por estados X flujo.
     *
     * @author Victor Bocanegra
     * @param estadosxflujoList lista de estado X flujo
     * @return Ordenes de Trabajo encontrada en el repositorio
     * @throws ApplicationException
     */
    public CmtOrdenTrabajoMgl findByEstadosXFlujoAndIdOt(List<BigDecimal> estadosxflujoList,
            BigDecimal idOt) throws ApplicationException {
        try {
            String queryStr = "SELECT  o  FROM CmtOrdenTrabajoMgl o,"
                    + OrdenTrabajoConstants.CMT_ESTADOX_FLUJO_MGL_EF_CMT_CUENTA_MATRIZ_MGL_CM
                    + OrdenTrabajoConstants.WHERE_T_ID_TIPO_OT_O_TIPO_OT_OBJ_ID_TIPO_OT
                    + OrdenTrabajoConstants.AND_EF_TIPO_FLUJO_OT_OBJ_BASICA_ID_T_TIPO_FLUJO_OT_BASICA_ID
                    + OrdenTrabajoConstants.AND_EF_BASICA_TECNOLOGIA_BASICA_ID_O_BASICA_ID_TECNOLOGIA_BASICA_ID
                    + OrdenTrabajoConstants.AND_O_ESTADO_INTERNO_OBJ_BASICA_ID_EF_ESTADO_INTERNO_OBJ_BASICA_ID
                    + OrdenTrabajoConstants.MATRIZ_ID_O_CM_OBJ_CUENTA_MATRIZ_ID
                    + OrdenTrabajoConstants.AND_O_ESTADO_REGISTRO_ESTADO_REGISTRO
                    + OrdenTrabajoConstants.AND_O_ID_OT_ID_OT;

            if (estadosxflujoList != null && !estadosxflujoList.isEmpty()) {
                queryStr += OrdenTrabajoConstants.AND_EF_ESTADOX_FLUJO_ID_IN_ESTADOSXFLUJO_LIST;
            }

            Query query = entityManager.createQuery(queryStr);
            query.setParameter("idOt", idOt);
            query.setParameter(OrdenTrabajoConstants.ESTADO_REGISTRO_PARAMETER, 1);

            if (estadosxflujoList != null && !estadosxflujoList.isEmpty()) {
                query.setParameter(OrdenTrabajoConstants.ESTADOSXFLUJO_LIST_PARAMETER, estadosxflujoList);
            }

            query.setHint(JAVAX_PERSISTENCE_CACHE_STORE_MODE, REFRESH);
            return (CmtOrdenTrabajoMgl) query.getSingleResult();
        } catch (NoResultException e) {
            String msg = SE_PRODUJO_UN_ERROR_AL_MOMENTO_DE_EJECUTAR_EL_METODO + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            return null;
        } catch (Exception e) {
            String msg = SE_PRODUJO_UN_ERROR_AL_MOMENTO_DE_EJECUTAR_EL_METODO + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }

    }

    public List<CmtOrdenTrabajoMgl> findOtAcometidaSubEdificio(CmtSubEdificioMgl cmtSubEdificioMgl) throws ApplicationException {
        try {
            String query = "select o from CmtOrdenTrabajoMgl o, CmtSubEdificiosVt e where o.idOt = e.otAcometidaId AND "
                    + "e.subEdificioObj.subEdificioId =:subEdificioId AND o.estadoRegistro = 1 AND e.estadoRegistro = 1 ";
            Query q = entityManager.createQuery(query);
            q.setParameter("subEdificioId", cmtSubEdificioMgl.getSubEdificioId());
            q.setHint(JAVAX_PERSISTENCE_CACHE_STORE_MODE, REFRESH);
            List<CmtOrdenTrabajoMgl> result = q.getResultList();
            getEntityManager().clear();
            return result;
        } catch (Exception e) {
            String msg = SE_PRODUJO_UN_ERROR_AL_MOMENTO_DE_EJECUTAR_EL_METODO + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

}
