package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.dtos.HhppTecnologiaDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTecnologiaSubMgl;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

/**
 *
 * @author rodriguezluim
 */
public class CmtTecnologiaSubMglDaoImpl extends GenericDaoImpl<CmtTecnologiaSubMgl> {

    private static final Logger LOGGER = LogManager.getLogger(CmtTecnologiaSubMglDaoImpl.class);

    /**
     * Buscar una sub tecnologia basandose en un subedificio
     *
     * @author rodriguezluim
     * @param subEdificio Condicional por el cual voy a buscar
     * @param basicaIdTecnologias
     * @return Un elemento de tipo CmtTecnologiaSub
     */
    public CmtTecnologiaSubMgl findBySubEdificioTecnologia(CmtSubEdificioMgl subEdificio,
            CmtBasicaMgl basicaIdTecnologias) {
        CmtTecnologiaSubMgl resultList;

        try {
            Query query = entityManager.createNamedQuery("CmtTecnologiaSubMgl.findBySubEdiAndTecno");
            query.setParameter("subedificioId", subEdificio);
            query.setParameter("basicaIdTecnologias", basicaIdTecnologias);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            resultList = (CmtTecnologiaSubMgl) query.getSingleResult();

        } catch (NoResultException | NonUniqueResultException e) {
            LOGGER.error(e.getMessage());
            resultList = new CmtTecnologiaSubMgl();
         } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return null;
        }
        return resultList;

    }

    public List<CmtSubEdificioMgl> findListaSubEdificioByCM(CmtSubEdificioMgl subEdificio) {
        List<CmtSubEdificioMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtTecnologiaSubMgl.findBySubedificioId");
        query.setParameter("subedificioId", subEdificio);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = query.getResultList();
        return resultList;
    }

    /**
     * Metodo que calcula el numero de HHPP asociados a una tecnologia
     *
     * @param cmtSubEdificioMgl subedificio o cuenta matriz
     * @param findBy
     * @return Dto con informacion del conteo de HHPP
     * @author Lenis Cardenas
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public List<HhppTecnologiaDto> countHhppCM(CmtSubEdificioMgl cmtSubEdificioMgl,
            Constant.FIND_HHPP_BY findBy) throws ApplicationException {
        String queryString;
        queryString = "select  count(s.basicaIdTecnologias), s.basicaIdTecnologias "
                + "from CmtTecnologiaSubMgl s, HhppMgl h  "
                + "where  s.tecnoSubedificioId = h.cmtTecnologiaSubId.tecnoSubedificioId";
        if (cmtSubEdificioMgl == null
                || cmtSubEdificioMgl.getSubEdificioId() == null
                || cmtSubEdificioMgl.getSubEdificioId().equals(BigDecimal.ZERO)) {
            throw new ApplicationException("El filtro Sub Edificio es obligatorio");
        }

        if (Constant.FIND_HHPP_BY.CUENTA_MATRIZ.equals(findBy)) {
            queryString += " and s.subedificioId.subEdificioId  = :. ";
        } else {
            queryString += " and s.subedificioId.subEdificioId  = :subedificioId ";
        }
        queryString += " group by  s.basicaIdTecnologias ";
        Query query = entityManager.createQuery(queryString);

        if (Constant.FIND_HHPP_BY.CUENTA_MATRIZ.equals(findBy)) {
            query.setParameter("cuentaMatrizObj", cmtSubEdificioMgl.getSubEdificioId());
        } else {
            query.setParameter("subedificioId", cmtSubEdificioMgl.getSubEdificioId());
        }
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        List<Object[]> rows = query.getResultList();
        List<HhppTecnologiaDto> listaHhppTecnologiaDto = new ArrayList<HhppTecnologiaDto>();
        for (Object[] row : rows) {
            HhppTecnologiaDto hhppTecnologiaDto = new HhppTecnologiaDto();
            hhppTecnologiaDto.setTotalHhpp(((Long) row[0]).intValue());
            hhppTecnologiaDto.setTecnologiaHhpp((CmtBasicaMgl) row[1]);
            listaHhppTecnologiaDto.add(hhppTecnologiaDto);

        }
        return listaHhppTecnologiaDto;
    }

    /**
     * Metodo que calcula el numero de HHPP asociados a una tecnologia
     *
     * @param cmtTecnologiaSubMgl subedificio o cuenta matriz
     * @param findBy
     * @return Dto con informacion del conteo de HHPP
     * @author Lenis Cardenas
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public HhppTecnologiaDto countHhppTecCM(CmtTecnologiaSubMgl cmtTecnologiaSubMgl,
            Constant.FIND_HHPP_BY findBy) throws ApplicationException {

        String queryString
                = "select count(h.hhpId) "
                + " from HhppMgl h where h.cmtTecnologiaSubId.tecnoSubedificioId in "
                + " (select  t.tecnoSubedificioId from CmtTecnologiaSubMgl t where "
                + " t.subedificioId.subEdificioId in "
                + " (select s.subEdificioId from CmtSubEdificioMgl s where  "
                + " s.cuentaMatrizObj.cuentaMatrizId = :cuentaMatrizid ) "
                + " and h.cmtTecnologiaSubId.basicaIdTecnologias.basicaId = :tecnologia )";

        if (cmtTecnologiaSubMgl.getSubedificioId() == null
                || cmtTecnologiaSubMgl.getSubedificioId() == null) {
            throw new ApplicationException("El filtro Sub Edificio es obligatorio");
        }

        Query query = entityManager.createQuery(queryString);

        if (Constant.FIND_HHPP_BY.CUENTA_MATRIZ.equals(findBy)) {
            query.setParameter("cuentaMatrizid", cmtTecnologiaSubMgl.getSubedificioId().getCmtCuentaMatrizMglObj().getCuentaMatrizId());
            query.setParameter("tecnologia", cmtTecnologiaSubMgl.getBasicaIdTecnologias().getBasicaId());
        }
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        Object conteo = query.getSingleResult();
        List<HhppTecnologiaDto> listaHhppTecnologiaDto = new ArrayList<HhppTecnologiaDto>();
        HhppTecnologiaDto hhppTecnologiaDto = new HhppTecnologiaDto();
        hhppTecnologiaDto.setIdSubEdificio(cmtTecnologiaSubMgl.getSubedificioId());
        hhppTecnologiaDto.setTotalHhpp(((Long) conteo).intValue());
        listaHhppTecnologiaDto.add(hhppTecnologiaDto);
        return hhppTecnologiaDto;
    }

    /**
     * Metodo que calcula el numero de HHPP asociados a una tecnologia
     *
     * @param cmtTecnologiaSubMgl
     * @param findBy
     * @return Dto con informacion del conteo de HHPP
     * @author Lenis Cardenas
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public HhppTecnologiaDto countHhppTecSub(CmtTecnologiaSubMgl cmtTecnologiaSubMgl,
            Constant.FIND_HHPP_BY findBy) throws ApplicationException {
        String queryString = "select  count(s.basicaIdTecnologias), s.basicaIdTecnologias "
                + "from CmtTecnologiaSubMgl s, HhppMgl h  "
                + "where  s.tecnoSubedificioId = h.cmtTecnologiaSubId.tecnoSubedificioId "
                + "and  s.basicaIdTecnologias.basicaId = :tecnologiaId";

        if (cmtTecnologiaSubMgl == null
                || cmtTecnologiaSubMgl.getSubedificioId() == null
                || cmtTecnologiaSubMgl.getSubedificioId().equals(BigDecimal.ZERO)) {
            throw new ApplicationException("El filtro Sub Edificio es obligatorio");
        }

        if (Constant.FIND_HHPP_BY.CUENTA_MATRIZ.equals(findBy)) {
            queryString += " and s.subedificioId.subEdificioId  = :. ";
        } else {
            queryString += " and s.subedificioId.subEdificioId  = :subedificioId ";
        }

        queryString += " group by  s.basicaIdTecnologias ";
        Query query = entityManager.createQuery(queryString);

        if (Constant.FIND_HHPP_BY.CUENTA_MATRIZ.equals(findBy)) {
            query.setParameter("cuentaMatrizObj", cmtTecnologiaSubMgl.getSubedificioId());
        } else {
            query.setParameter("subedificioId", cmtTecnologiaSubMgl.getSubedificioId().getSubEdificioId());
        }
        if (cmtTecnologiaSubMgl.getBasicaIdTecnologias() != null
                || cmtTecnologiaSubMgl.getBasicaIdTecnologias() != null
                || !cmtTecnologiaSubMgl.getBasicaIdTecnologias().equals(BigDecimal.ZERO)) {
            query.setParameter("tecnologiaId", cmtTecnologiaSubMgl.getBasicaIdTecnologias().getBasicaId());
        }
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        List<Object[]> rows = query.getResultList();
        HhppTecnologiaDto hhppTecnologiaDto = new HhppTecnologiaDto();
        for (Object[] row : rows) {
            hhppTecnologiaDto.setIdSubEdificio(cmtTecnologiaSubMgl.getSubedificioId());
            hhppTecnologiaDto.setTotalHhpp(((Long) row[0]).intValue());
            hhppTecnologiaDto.setTecnologiaHhpp((CmtBasicaMgl) row[1]);
        }
        return hhppTecnologiaDto;
    }

    /**
     * Busca las tecnologias asociadas a un subedificio.
     *
     * @author Victor Bocanegra
     * @param cmtSubEdificioMgl SubEdificio
     * @return Tecnologias asociadas a un Subedificio.
     * @throws ApplicationException
     */
    public List<CmtTecnologiaSubMgl> findTecnoSubBySubEdi(CmtSubEdificioMgl cmtSubEdificioMgl) throws ApplicationException {
        try {
            List<CmtTecnologiaSubMgl> resulList;
            Query query = entityManager.createNamedQuery("CmtTecnologiaSubMgl.findBySubedificioId");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            if (cmtSubEdificioMgl != null) {
                query.setParameter("subedificioId", cmtSubEdificioMgl);
            }
            resulList = (List<CmtTecnologiaSubMgl>) query.getResultList();
            return resulList;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Busca las tecnologias asociadas a un subedificio por tecnologia.
     *
     * @author Victor Bocanegra
     * @param cmtSubEdificioMgl
     * @param subEdificioId SubEdificio
     * @param cmtBasicaMgl
     * @param tecnologia Tecnologia de la OT
     * @return Tecnologias asociadas a un Subedificio.
     * @throws ApplicationException
     */
    public List<CmtTecnologiaSubMgl> findTecnoSubBySubEdiTec(CmtSubEdificioMgl cmtSubEdificioMgl,
            CmtBasicaMgl cmtBasicaMgl) throws ApplicationException {
        try {
            List<CmtTecnologiaSubMgl> resultList;
            Query q = entityManager.createNamedQuery("CmtTecnologiaSubMgl.findBySubedificioIdBasicaIdTecnologias");
            q.setParameter("subedificioId", cmtSubEdificioMgl);
            q.setParameter("basicaIdTecnologias", cmtBasicaMgl);
            q.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            resultList = (List<CmtTecnologiaSubMgl>) q.getResultList();
            return resultList;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Metodo que calcula la cantidad de hhpp activos en estado = 'S' por CM
     *
     * @param cmtSubEdificioMgl subedificio o cuenta matriz
     * @param findBy
     *
     * @return Dto con informacion del conteo de HHPP activos
     * @author Lenis Cardenas
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public HhppTecnologiaDto countClientesActivosCM(CmtSubEdificioMgl cmtSubEdificioMgl,
            Constant.FIND_HHPP_BY findBy) throws ApplicationException {

        String queryString = "select count(h.hhpId), sum(h.cfm) "
                + " from HhppMgl h where h.cmtTecnologiaSubId.tecnoSubedificioId in "
                + " (select  t.tecnoSubedificioId from CmtTecnologiaSubMgl t where "
                + " t.subedificioId.subEdificioId in "
                + " (select s.subEdificioId from CmtSubEdificioMgl s where  "
                + "  s.cuentaMatrizObj.cuentaMatrizId = :cuentaMatrizid )) and  h.ehhId.ehhID = 'CS'";

        if (cmtSubEdificioMgl == null
                || cmtSubEdificioMgl.getSubEdificioId() == null
                || cmtSubEdificioMgl.getSubEdificioId().equals(BigDecimal.ZERO)) {
            throw new ApplicationException("El filtro Sub Edificio es obligatorio");
        }

        Query query = entityManager.createQuery(queryString);
        if (Constant.FIND_HHPP_BY.CUENTA_MATRIZ.equals(findBy)) {
            query.setParameter("cuentaMatrizid", cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getCuentaMatrizId());
        }
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        List<Object[]> rows = query.getResultList();
        List<HhppTecnologiaDto> listaHhppTecnologiaDto = new ArrayList<HhppTecnologiaDto>();
        HhppTecnologiaDto hhppTecnologiaDto = new HhppTecnologiaDto();
        for (Object[] row : rows) {
            hhppTecnologiaDto.setIdSubEdificio(cmtSubEdificioMgl);
            hhppTecnologiaDto.setTotalClientesActivos(((Long) row[0]).intValue());
            hhppTecnologiaDto.setRentaMensualCfm((BigDecimal) row[1]);
        }
        return hhppTecnologiaDto;
    }

    /**
     * Metodo que calcula la cantidad de hhpp activos en estado = 'S'
     *
     * @param cmtTecnologiaSubMgl
     * @param findBy
     *
     * @return Dto con informacion del conteo de HHPP activos
     * @author Lenis Cardenas
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public HhppTecnologiaDto countClientesActivos(CmtTecnologiaSubMgl cmtTecnologiaSubMgl,
            Constant.FIND_HHPP_BY findBy) throws ApplicationException {
        String queryString = "select  count(s.basicaIdTecnologias), s.basicaIdTecnologias,sum(h.cfm) "
                + "from CmtTecnologiaSubMgl s, HhppMgl h  "
                + "where  s.tecnoSubedificioId = h.cmtTecnologiaSubId.tecnoSubedificioId "
                + "and  s.basicaIdTecnologias.basicaId = :tecnologiaId "
                + "and h.ehhId.ehhID = 'CS'";

        if (Constant.FIND_HHPP_BY.CUENTA_MATRIZ.equals(findBy)) {
            queryString += " and s.subedificioId.subEdificioId  = :cuentaMatrizObj ";
        } else {
            queryString += " and s.subedificioId.subEdificioId  = :subedificioId ";
        }

        queryString += " group by  s.basicaIdTecnologias,h.cfm  ";
        if (cmtTecnologiaSubMgl == null
                || cmtTecnologiaSubMgl.getSubedificioId() == null
                || cmtTecnologiaSubMgl.getSubedificioId().equals(BigDecimal.ZERO)) {
            throw new ApplicationException("El filtro Sub Edificio es obligatorio");
        }

        Query query = entityManager.createQuery(queryString);
        if (Constant.FIND_HHPP_BY.CUENTA_MATRIZ.equals(findBy)) {
            query.setParameter("cuentaMatrizObj", cmtTecnologiaSubMgl.getSubedificioId());
        } else {
            query.setParameter("subedificioId", cmtTecnologiaSubMgl.getSubedificioId().getSubEdificioId());
        }
        if (cmtTecnologiaSubMgl.getBasicaIdTecnologias() != null
                || cmtTecnologiaSubMgl.getBasicaIdTecnologias() != null
                || !cmtTecnologiaSubMgl.getBasicaIdTecnologias().equals(BigDecimal.ZERO)) {
            query.setParameter("tecnologiaId", cmtTecnologiaSubMgl.getBasicaIdTecnologias().getBasicaId());
        }

        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        List<Object[]> rows = query.getResultList();
        HhppTecnologiaDto hhppTecnologiaDto = new HhppTecnologiaDto();
        for (Object[] row : rows) {
            hhppTecnologiaDto.setIdSubEdificio(cmtTecnologiaSubMgl.getSubedificioId());
            hhppTecnologiaDto.setTotalClientesActivos(((Long) row[0]).intValue());
            hhppTecnologiaDto.setTecnologiaHhpp((CmtBasicaMgl) row[1]);
            hhppTecnologiaDto.setRentaMensualCfm((BigDecimal) row[2] != null ? (BigDecimal) row[2] : BigDecimal.ZERO);
        }
        return hhppTecnologiaDto;
    }

    /**
     * Metodo que obtiene la lista las tecnologias asociadas a HHPP contra la
     * tabla TecnologiaSUb en la cuenta Matriz
     *
     * @param params
     * @param firstResult
     * @param maxResults
     * @param cmtSubEdificioMgl
     * @param findBy
     * @throws co.com.claro.mgl.error.ApplicationException
     * @cmtSubEdificioMgl subedificio o cuenta matriz
     * @return Dto con informacion del conteo de HHPP activos
     * @author Lenis Cardenas
     */
    public List<CmtTecnologiaSubMgl> findListPenetracionTecCM(HashMap<String, Object> params,
            int firstResult, int maxResults, CmtSubEdificioMgl cmtSubEdificioMgl,
            Constant.FIND_HHPP_BY findBy) throws ApplicationException {
        List<CmtTecnologiaSubMgl> listaPenetracionTecnologias;
        String queryString = "select s from CmtTecnologiaSubMgl s  ";
        if (Constant.FIND_HHPP_BY.CUENTA_MATRIZ.equals(findBy)) {
            queryString += " where s.subedificioId.subEdificioId  = :cuentaMatrizObj ";
        } else {
            queryString += " where s.subedificioId.subEdificioId  = :subedificioId ";
        }

        queryString += " and  s.estadoRegistro = 1 ";

        if (cmtSubEdificioMgl == null
                || cmtSubEdificioMgl.getSubEdificioId() == null
                || cmtSubEdificioMgl.getSubEdificioId().equals(BigDecimal.ZERO)) {
            throw new ApplicationException("El filtro Sub Edificio es obligatorio");
        }

        Query query = entityManager.createQuery(queryString);

        if (Constant.FIND_HHPP_BY.CUENTA_MATRIZ.equals(findBy)) {
            query.setParameter("cuentaMatrizObj", cmtSubEdificioMgl.getSubEdificioId());
        } else {
            query.setParameter("subedificioId", cmtSubEdificioMgl.getSubEdificioId());
        }

        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        listaPenetracionTecnologias = (List<CmtTecnologiaSubMgl>) query.getResultList();

        return listaPenetracionTecnologias;
    }

    /**
     * Metodo que obtiene el conteo de la lista de hhpp para paginacion
     *
     * @param params
     * @param cmtSubEdificioMgl
     * @param findBy
     * @throws co.com.claro.mgl.error.ApplicationException
     * @cmtSubEdificioMgl subedificio o cuenta matriz
     * @return Dto con informacion del conteo de HHPP activos
     * @author Lenis Cardenas
     */
    public int countListPenetracionTecSub(HashMap<String, Object> params,
            CmtSubEdificioMgl cmtSubEdificioMgl, Constant.FIND_HHPP_BY findBy) throws ApplicationException {
        String queryString = "select count(s) from CmtTecnologiaSubMgl s  ";
        if (Constant.FIND_HHPP_BY.CUENTA_MATRIZ.equals(findBy)) {
            queryString += " where s.subedificioId.subEdificioId  = :cuentaMatrizObj ";
        } else {
            queryString += " where s.subedificioId.subEdificioId  = :subedificioId ";
        }

        queryString += " and  s.estadoRegistro = 1 ";

        if (cmtSubEdificioMgl == null
                || cmtSubEdificioMgl.getSubEdificioId() == null
                || cmtSubEdificioMgl.getSubEdificioId().equals(BigDecimal.ZERO)) {
            throw new ApplicationException("El filtro Sub Edificio es obligatorio");
        }

        Query query = entityManager.createQuery(queryString);
        if (Constant.FIND_HHPP_BY.CUENTA_MATRIZ.equals(findBy)) {
            query.setParameter("cuentaMatrizObj", cmtSubEdificioMgl.getSubEdificioId());
        } else {
            query.setParameter("subedificioId", cmtSubEdificioMgl.getSubEdificioId());
        }
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        int result = query.getSingleResult() == null ? 0 : ((Long) query.getSingleResult()).intValue();
        return result;
    }

    /**
     * Metodo que obtiene el conteo de la lista de clientes activos para
     * paginacion
     *
     * @param params
     * @param cmtSubEdificioMgl
     * @param findBy
     * @throws co.com.claro.mgl.error.ApplicationException
     * @cmtSubEdificioMgl subedificio o cuenta matriz
     * @return Dto con informacion del conteo de HHPP activos
     * @author Lenis Cardenas
     */
    public int countListPenetracionTecCM(HashMap<String, Object> params,
            CmtSubEdificioMgl cmtSubEdificioMgl, Constant.FIND_HHPP_BY findBy) throws ApplicationException {
        String queryString = "select count(s) from CmtTecnologiaSubMgl s  ";
        if (Constant.FIND_HHPP_BY.CUENTA_MATRIZ.equals(findBy)) {
            queryString += " where s.subedificioId.subEdificioId  = :cuentaMatrizObj ";
        } else {
            queryString += " where s.subedificioId.subEdificioId  = :subedificioId ";
        }

        queryString += " and  s.estadoRegistro = 1 ";

        if (cmtSubEdificioMgl.getCmtCuentaMatrizMglObj() == null
                || cmtSubEdificioMgl.getCmtCuentaMatrizMglObj() == null) {
            throw new ApplicationException("El filtro Sub Edificio es obligatorio");
        }

        Query query = entityManager.createQuery(queryString);
        if (Constant.FIND_HHPP_BY.CUENTA_MATRIZ.equals(findBy)) {
            query.setParameter("cuentaMatrizObj", cmtSubEdificioMgl.getSubEdificioId());
        } else {
            query.setParameter("subedificioId", cmtSubEdificioMgl.getSubEdificioId());
        }
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        Long resultCount = (Long) query.getSingleResult();
        return resultCount == null ? 0 : resultCount.intValue();
    }

    /**
     * valbuenayf metodo para realizar el reporte de cuentamatriz para el cargue
     * masivo
     *
     * @param params
     * @param first
     * @param pageSize
     * @return
     */
    public List<CmtTecnologiaSubMgl> findReporteGeneralDetallado(Map<String, Object> params, int first, int pageSize) {
        List<CmtTecnologiaSubMgl> resultList = null;
        try {
            if (params.get(Constant.CAR_MAS_REPORTE) != null && params.get(Constant.CAR_MAS_REPORTE).equals("0")) {
                resultList = findReporteDetallado(params, first, pageSize);
            } else if (params.get(Constant.CAR_MAS_REPORTE) != null && params.get(Constant.CAR_MAS_REPORTE).equals("1")) {
                resultList = findReporteGeneral(params, first, pageSize);
            }
        } catch (Exception e) {
            LOGGER.error("Error en findReporteGeneralDetallado de CmtTecnologiaSubMglDaoImpl: " + e);
        }
        return resultList;
    }

    /**
     * valbuenayf metodo para realizar el reporte general de cuentamatriz para
     * el cargue masivo
     *
     * @param params
     * @return
     */
    private List<CmtTecnologiaSubMgl> findReporteGeneral(Map<String, Object> params, int first, int pageSize) {
        List<CmtTecnologiaSubMgl> resultList = null;
        try {
            StringBuilder sql = new StringBuilder();

            if (params.get(Constant.CAR_MAS_BLACKLIST_TECNOLOGIA) != null && new BigDecimal(params.get(Constant.CAR_MAS_BLACKLIST_TECNOLOGIA).toString()).compareTo(BigDecimal.ZERO) != 0) {

                sql.append("SELECT c FROM CmtTecnologiaSubMgl c,  CmtBlackListMgl bl "
                        + " WHERE c.basicaIdTecnologias.basicaId = :idTecnologia");
                sql.append(" AND c.subedificioId.subEdificioId  =  bl.subEdificioObj.subEdificioId");
                sql.append(" AND c.subedificioId.cuentaMatrizObj.municipio.gpoId = :idCiudad");
                sql.append(" AND c.estadoRegistro = 1  AND c.subedificioId.estadoRegistro = 1  "
                        + " AND c.subedificioId.cuentaMatrizObj.estadoRegistro = 1");
            } else {
                sql.append("SELECT c FROM CmtTecnologiaSubMgl c"
                        + " WHERE c.basicaIdTecnologias.basicaId = :idTecnologia");
                sql.append(" AND c.subedificioId.cuentaMatrizObj.municipio.gpoId = :idCiudad");
                sql.append(" AND c.estadoRegistro = 1  AND c.subedificioId.estadoRegistro = 1  "
                        + " AND c.subedificioId.cuentaMatrizObj.estadoRegistro = 1");
            }
           

            if (params.get(Constant.CAR_MAS_ID_CENTRO_POBLADO) != null && new BigDecimal(params.get(Constant.CAR_MAS_ID_CENTRO_POBLADO).toString()).compareTo(BigDecimal.ZERO) != 0) {
                sql.append(" AND c.subedificioId.cuentaMatrizObj.centroPoblado.gpoId = :idCentroPoblado");
            }
            if (params.get(Constant.CAR_MAS_CODIGO_NODO) != null) {
                sql.append(" AND UPPER(c.nodoId.nodCodigo) = UPPER(:nodCodigo)");
            }
            /////////
            if (params.get(Constant.CAR_MAS_BLACKLIST_TECNOLOGIA) != null && new BigDecimal(params.get(Constant.CAR_MAS_BLACKLIST_TECNOLOGIA).toString()).compareTo(BigDecimal.ZERO) != 0) {
                sql.append(" AND bl.blackListObj.basicaId = :blacklistTec");
            }

            if (params.get(Constant.CAR_MAS_ID_CCMM_RR) != null && new BigDecimal(params.get(Constant.CAR_MAS_ID_CCMM_RR).toString()).compareTo(BigDecimal.ZERO) != 0) {
                sql.append(" AND c.subedificioId.cuentaMatrizObj.numeroCuenta = :idCcmmRr");
            }

            if (params.get(Constant.CAR_MAS_ID_CCMM_MGL) != null && new BigDecimal(params.get(Constant.CAR_MAS_ID_CCMM_MGL).toString()).compareTo(BigDecimal.ZERO) != 0) {
                sql.append(" AND c.subedificioId.cuentaMatrizObj.cuentaMatrizId = :idCcmmMgl");
            }
            /////////

            Query q = entityManager.createQuery(sql.toString());
            q.setParameter("idTecnologia", params.get(Constant.CAR_MAS_ID_TECNOLOGIA));
            q.setParameter("idCiudad", params.get(Constant.CAR_MAS_ID_CIUDAD));
           
            if (params.get(Constant.CAR_MAS_ID_CENTRO_POBLADO) != null && new BigDecimal(params.get(Constant.CAR_MAS_ID_CENTRO_POBLADO).toString()).compareTo(BigDecimal.ZERO) != 0) {
                q.setParameter("idCentroPoblado", params.get(Constant.CAR_MAS_ID_CENTRO_POBLADO));
            }
            
            if (params.get(Constant.CAR_MAS_CODIGO_NODO) != null) {
                q.setParameter("nodCodigo", params.get(Constant.CAR_MAS_CODIGO_NODO).toString().toUpperCase());
            }
            
           if (params.get(Constant.CAR_MAS_BLACKLIST_TECNOLOGIA) != null && new BigDecimal(params.get(Constant.CAR_MAS_BLACKLIST_TECNOLOGIA).toString()).compareTo(BigDecimal.ZERO) != 0) {
                q.setParameter("blacklistTec", params.get(Constant.CAR_MAS_BLACKLIST_TECNOLOGIA));
            }

            if (params.get(Constant.CAR_MAS_ID_CCMM_RR) != null && new BigDecimal(params.get(Constant.CAR_MAS_ID_CCMM_RR).toString()).compareTo(BigDecimal.ZERO) != 0) {
                q.setParameter("idCcmmRr", params.get(Constant.CAR_MAS_ID_CCMM_RR));
            }

            if (params.get(Constant.CAR_MAS_ID_CCMM_MGL) != null && new BigDecimal(params.get(Constant.CAR_MAS_ID_CCMM_MGL).toString()).compareTo(BigDecimal.ZERO) != 0) {
                q.setParameter("idCcmmMgl", params.get(Constant.CAR_MAS_ID_CCMM_MGL));
            }

            q.setFirstResult(first);
            q.setMaxResults(pageSize);
            resultList = q.getResultList();
        } catch (Exception e) {
            LOGGER.error("Error en findReporteGeneral de CmtTecnologiaSubMglDaoImpl: " + e);
        }
        return resultList;
    }

    /**
     * valbuenayf metodo para realizar el reporte detallado de cuentamatriz para
     * el cargue masivo
     *
     * @param params
     * @return
     */
    private List<CmtTecnologiaSubMgl> findReporteDetallado(Map<String, Object> params, int first, int pageSize) {
        List<CmtTecnologiaSubMgl> resultList = null;
        try {
            StringBuilder sql = new StringBuilder();
            
            if (params.get(Constant.CAR_MAS_BLACKLIST_TECNOLOGIA) != null && new BigDecimal(params.get(Constant.CAR_MAS_BLACKLIST_TECNOLOGIA).toString()).compareTo(BigDecimal.ZERO) != 0) {

                sql.append("SELECT c FROM CmtTecnologiaSubMgl c,  CmtBlackListMgl bl "
                        + " WHERE c.basicaIdTecnologias.basicaId = :idTecnologia");
                sql.append(" AND c.subedificioId.subEdificioId  =  bl.subEdificioObj.subEdificioId");
                sql.append(" AND c.subedificioId.cuentaMatrizObj.municipio.gpoId = :idCiudad");
                sql.append(" AND c.estadoRegistro = 1  AND c.subedificioId.estadoRegistro = 1  "
                        + " AND c.subedificioId.cuentaMatrizObj.estadoRegistro = 1");
            } else {
                sql.append("SELECT c FROM CmtTecnologiaSubMgl c"
                        + " WHERE c.basicaIdTecnologias.basicaId = :idTecnologia");
                sql.append(" AND c.subedificioId.cuentaMatrizObj.municipio.gpoId = :idCiudad");
                sql.append(" AND c.estadoRegistro = 1  AND c.subedificioId.estadoRegistro = 1  "
                        + " AND c.subedificioId.cuentaMatrizObj.estadoRegistro = 1");
            }


            if (params.get(Constant.CAR_MAS_ID_CENTRO_POBLADO) != null && new BigDecimal(params.get(Constant.CAR_MAS_ID_CENTRO_POBLADO).toString()).compareTo(BigDecimal.ZERO) != 0) {
                sql.append(" AND c.subedificioId.cuentaMatrizObj.centroPoblado.gpoId = :idCentroPoblado");
            }
            if (params.get(Constant.CAR_MAS_CODIGO_NODO) != null) {
                sql.append(" AND UPPER(c.nodoId.nodCodigo) = UPPER(:nodCodigo)");
            }

            if (params.get(Constant.CAR_MAS_CUENTA) != null && !params.get(Constant.CAR_MAS_CUENTA).toString().trim().isEmpty()) {
                sql.append(" AND UPPER(c.subedificioId.cuentaMatrizObj.nombreCuenta) like UPPER(:nombreCuenta)");
            }

            if (params.get(Constant.CAR_MAS_COMPANIA) != null && !params.get(Constant.CAR_MAS_COMPANIA).toString().trim().isEmpty()) {
                sql.append(" AND c.subedificioId.companiaAdministracionObj.companiaId = :compania");
            }

            if (params.get(Constant.CAR_MAS_ASCENSORES) != null && !params.get(Constant.CAR_MAS_ASCENSORES).toString().trim().isEmpty()) {
                sql.append(" AND c.subedificioId.companiaAscensorObj.companiaId = :ascensor");
            }

            if (params.get(Constant.CAR_MAS_ADMINISTRADOR) != null && !params.get(Constant.CAR_MAS_ADMINISTRADOR).toString().isEmpty()) {
                sql.append(" AND UPPER(c.subedificioId.administrador) like UPPER(:administrador) ");
            }

            if (params.get(Constant.CAR_MAS_TEL_UNO) != null && !params.get(Constant.CAR_MAS_TEL_UNO).toString().isEmpty()) {
                sql.append(" AND c.subedificioId.telefonoPorteria like :telefonoPorteria ");
            }

            if (params.get(Constant.CAR_MAS_TEL_DOS) != null && !params.get(Constant.CAR_MAS_TEL_DOS).toString().isEmpty()) {
                sql.append(" AND c.subedificioId.telefonoPorteria2 like :telefonoPorteria2 ");
            }

            if (params.get(Constant.CAR_MAS_PROYECTO) != null && !params.get(Constant.CAR_MAS_PROYECTO).toString().isEmpty()) {
                sql.append(" AND c.subedificioId.tipoProyectoObj.basicaId = :proyecto ");
            }

            if (params.get(Constant.CAR_MAS_DATOS) != null && !params.get(Constant.CAR_MAS_DATOS).toString().isEmpty()) {
                sql.append(" AND c.subedificioId.origenDatosObj.basicaId = :datos  ");
            }

            if (params.get(Constant.CAR_MAS_NODO) != null && !params.get(Constant.CAR_MAS_NODO).toString().isEmpty()) {
                sql.append("  AND UPPER(c.nodoId.nodCodigo) = UPPER(:nodId)");
            }

            if (params.get(Constant.CAR_MAS_ESTADO) != null && !params.get(Constant.CAR_MAS_ESTADO).toString().isEmpty()) {
                sql.append(" AND c.basicaIdEstadosTec.basicaId = :estado");
            }

            if (params.get(Constant.CAR_MAS_CONFIGURACION) != null && !params.get(Constant.CAR_MAS_CONFIGURACION).toString().isEmpty()) {
                sql.append(" AND c.tipoConfDistribObj.basicaId = :configuracion");
            }

            if (params.get(Constant.CAR_MAS_ALIMENTACION) != null && !params.get(Constant.CAR_MAS_ALIMENTACION).toString().isEmpty()) {
                sql.append(" AND c.alimentElectObj.basicaId = :alimentacion");
            }

            if (params.get(Constant.CAR_MAS_DSITRIBUCION) != null && !params.get(Constant.CAR_MAS_DSITRIBUCION).toString().isEmpty()) {
                sql.append(" AND c.tipoDistribucionObj.basicaId = :distribucion ");
            }

            if (params.get(Constant.CAR_MAS_CONSTRUCTORA) != null && !params.get(Constant.CAR_MAS_CONSTRUCTORA).toString().isEmpty()) {
                sql.append(" AND c.subedificioId.companiaConstructoraObj.companiaId = :constructora");
            }

            if (params.get(Constant.CAR_MAS_TIPO_CCMM) != null && !params.get(Constant.CAR_MAS_TIPO_CCMM).toString().isEmpty()) {
                sql.append(" AND c.subedificioId.tipoEdificioObj.basicaId = :tipoCcmm");
            }
            
            if (params.get(Constant.CAR_MAS_BLACKLIST_TECNOLOGIA) != null && new BigDecimal(params.get(Constant.CAR_MAS_BLACKLIST_TECNOLOGIA).toString()).compareTo(BigDecimal.ZERO) != 0) {
                sql.append(" AND bl.blackListObj.basicaId = :blacklistTec");
            }

            if (params.get(Constant.CAR_MAS_ID_CCMM_RR) != null && new BigDecimal(params.get(Constant.CAR_MAS_ID_CCMM_RR).toString()).compareTo(BigDecimal.ZERO) != 0) {
                sql.append(" AND c.subedificioId.cuentaMatrizObj.numeroCuenta = :idCcmmRr");
            }

            if (params.get(Constant.CAR_MAS_ID_CCMM_MGL) != null && new BigDecimal(params.get(Constant.CAR_MAS_ID_CCMM_MGL).toString()).compareTo(BigDecimal.ZERO) != 0) {
                sql.append(" AND c.subedificioId.cuentaMatrizObj.cuentaMatrizId = :idCcmmMgl");
            }
            //////
            
            Query q = entityManager.createQuery(sql.toString());
            q.setParameter("idTecnologia", params.get(Constant.CAR_MAS_ID_TECNOLOGIA));
            q.setParameter("idCiudad", params.get(Constant.CAR_MAS_ID_CIUDAD));

            if (params.get(Constant.CAR_MAS_ID_CENTRO_POBLADO) != null && new BigDecimal(params.get(Constant.CAR_MAS_ID_CENTRO_POBLADO).toString()).compareTo(BigDecimal.ZERO) != 0) {
                q.setParameter("idCentroPoblado", params.get(Constant.CAR_MAS_ID_CENTRO_POBLADO));
            }
            if (params.get(Constant.CAR_MAS_CODIGO_NODO) != null) {
                q.setParameter("nodCodigo", params.get(Constant.CAR_MAS_CODIGO_NODO).toString().toUpperCase());
            }

            if (params.get(Constant.CAR_MAS_CUENTA) != null && !params.get(Constant.CAR_MAS_CUENTA).toString().trim().isEmpty()) {
                q.setParameter("nombreCuenta", "%" + params.get(Constant.CAR_MAS_CUENTA).toString().trim().toUpperCase() + "%");
            }

            if (params.get(Constant.CAR_MAS_COMPANIA) != null && !params.get(Constant.CAR_MAS_COMPANIA).toString().trim().isEmpty()) {
                q.setParameter("compania", params.get(Constant.CAR_MAS_COMPANIA));
            }

            if (params.get(Constant.CAR_MAS_ASCENSORES) != null && !params.get(Constant.CAR_MAS_ASCENSORES).toString().trim().isEmpty()) {
                q.setParameter("ascensor", params.get(Constant.CAR_MAS_ASCENSORES));
            }

            if (params.get(Constant.CAR_MAS_ADMINISTRADOR) != null && !params.get(Constant.CAR_MAS_ADMINISTRADOR).toString().trim().isEmpty()) {
                q.setParameter("administrador", "%" + params.get(Constant.CAR_MAS_ADMINISTRADOR).toString().trim().toUpperCase() + "%");
            }

            if (params.get(Constant.CAR_MAS_TEL_UNO) != null && !params.get(Constant.CAR_MAS_TEL_UNO).toString().trim().isEmpty()) {
                q.setParameter("telefonoPorteria", "%" + params.get(Constant.CAR_MAS_TEL_UNO).toString().trim() + "%");
            }

            if (params.get(Constant.CAR_MAS_TEL_DOS) != null && !params.get(Constant.CAR_MAS_TEL_DOS).toString().trim().isEmpty()) {
                q.setParameter("telefonoPorteria2", "%" + params.get(Constant.CAR_MAS_TEL_DOS).toString().trim() + "%");
            }

            if (params.get(Constant.CAR_MAS_PROYECTO) != null && !params.get(Constant.CAR_MAS_PROYECTO).toString().isEmpty()) {
                q.setParameter("proyecto", params.get(Constant.CAR_MAS_PROYECTO));
            }

            if (params.get(Constant.CAR_MAS_DATOS) != null && !params.get(Constant.CAR_MAS_DATOS).toString().isEmpty()) {
                q.setParameter("datos", params.get(Constant.CAR_MAS_DATOS));
            }

            if (params.get(Constant.CAR_MAS_NODO) != null && !params.get(Constant.CAR_MAS_NODO).toString().isEmpty()) {
                q.setParameter("nodId", params.get(Constant.CAR_MAS_NODO).toString().toUpperCase());
            }

            if (params.get(Constant.CAR_MAS_ESTADO) != null && !params.get(Constant.CAR_MAS_ESTADO).toString().isEmpty()) {
                q.setParameter("estado", params.get(Constant.CAR_MAS_ESTADO));
            }

            if (params.get(Constant.CAR_MAS_CONFIGURACION) != null && !params.get(Constant.CAR_MAS_CONFIGURACION).toString().isEmpty()) {
                q.setParameter("configuracion", params.get(Constant.CAR_MAS_CONFIGURACION));
            }

            if (params.get(Constant.CAR_MAS_ALIMENTACION) != null && !params.get(Constant.CAR_MAS_ALIMENTACION).toString().isEmpty()) {
                q.setParameter("alimentacion", params.get(Constant.CAR_MAS_ALIMENTACION));
            }

            if (params.get(Constant.CAR_MAS_DSITRIBUCION) != null && !params.get(Constant.CAR_MAS_DSITRIBUCION).toString().isEmpty()) {
                q.setParameter("distribucion", params.get(Constant.CAR_MAS_DSITRIBUCION));
            }

            ///////////
            if (params.get(Constant.CAR_MAS_CONSTRUCTORA) != null && !params.get(Constant.CAR_MAS_CONSTRUCTORA).toString().isEmpty()) {
                q.setParameter("constructora", params.get(Constant.CAR_MAS_CONSTRUCTORA));
            }

            if (params.get(Constant.CAR_MAS_TIPO_CCMM) != null && !params.get(Constant.CAR_MAS_TIPO_CCMM).toString().isEmpty()) {
                q.setParameter("tipoCcmm", params.get(Constant.CAR_MAS_TIPO_CCMM));
            }

            if (params.get(Constant.CAR_MAS_BLACKLIST_TECNOLOGIA) != null && new BigDecimal(params.get(Constant.CAR_MAS_BLACKLIST_TECNOLOGIA).toString()).compareTo(BigDecimal.ZERO) != 0) {
                q.setParameter("blacklistTec", params.get(Constant.CAR_MAS_BLACKLIST_TECNOLOGIA));
            }

            if (params.get(Constant.CAR_MAS_ID_CCMM_RR) != null && new BigDecimal(params.get(Constant.CAR_MAS_ID_CCMM_RR).toString()).compareTo(BigDecimal.ZERO) != 0) {
                q.setParameter("idCcmmRr", params.get(Constant.CAR_MAS_ID_CCMM_RR));
            }

            if (params.get(Constant.CAR_MAS_ID_CCMM_MGL) != null && new BigDecimal(params.get(Constant.CAR_MAS_ID_CCMM_MGL).toString()).compareTo(BigDecimal.ZERO) != 0) {
                q.setParameter("idCcmmMgl", params.get(Constant.CAR_MAS_ID_CCMM_MGL));
            }

            ////
            q.setFirstResult(first);
            q.setMaxResults(pageSize);
            resultList = q.getResultList();
        } catch (Exception e) {
            LOGGER.error("Error en findReporteDetallado de CmtTecnologiaSubMglDaoImpl: " + e);
        }
        return resultList;
    }

    /**
     * valbuenayf metodo para contar la cantidad de registros del reporte de
     * cuentamatriz para el cargue masivo
     *
     * @param params
     * @return
     */
    public Integer findCountRepGeneralDetallado(Map<String, Object> params) {
        Integer count = 0;
        try {
            if (params.get(Constant.CAR_MAS_REPORTE) != null && params.get(Constant.CAR_MAS_REPORTE).equals("0")) {
                count = findCountDetallado(params);
            } else if (params.get(Constant.CAR_MAS_REPORTE) != null && params.get(Constant.CAR_MAS_REPORTE).equals("1")) {
                count = findCountGeneral(params);
            }
        } catch (Exception e) {
            LOGGER.error("Error en findReporteGeneralDetallado de CmtTecnologiaSubMglDaoImpl: " + e);
        }
        return count;
    }

    /**
     * valbuenayf metodo para contar la cantidad de rgistros del reportde
     * general de cuentamatriz para el cargue masivo
     *
     * @param params
     * @return
     */
    private Integer findCountGeneral(Map<String, Object> params) {
        Integer count = 0;
        try {
            StringBuilder sql = new StringBuilder();
         
            if (params.get(Constant.CAR_MAS_BLACKLIST_TECNOLOGIA) != null &&  new BigDecimal(params.get(Constant.CAR_MAS_BLACKLIST_TECNOLOGIA).toString()).compareTo(BigDecimal.ZERO) != 0) {

                sql.append("SELECT COUNT (c.tecnoSubedificioId) FROM CmtTecnologiaSubMgl c,  CmtBlackListMgl bl "
                        + " WHERE c.basicaIdTecnologias.basicaId = :idTecnologia");
                sql.append(" AND c.subedificioId.subEdificioId  =  bl.subEdificioObj.subEdificioId");
                sql.append(" AND c.subedificioId.cuentaMatrizObj.municipio.gpoId = :idCiudad");
                sql.append(" AND c.estadoRegistro = 1  AND c.subedificioId.estadoRegistro = 1  "
                        + " AND c.subedificioId.cuentaMatrizObj.estadoRegistro = 1");
            } else {
                sql.append("SELECT COUNT (c.tecnoSubedificioId)  FROM CmtTecnologiaSubMgl c"
                        + " WHERE c.basicaIdTecnologias.basicaId = :idTecnologia");
                sql.append(" AND c.subedificioId.cuentaMatrizObj.municipio.gpoId = :idCiudad");
                sql.append(" AND c.estadoRegistro = 1  AND c.subedificioId.estadoRegistro = 1  "
                        + " AND c.subedificioId.cuentaMatrizObj.estadoRegistro = 1");
            }

            if (params.get(Constant.CAR_MAS_ID_CENTRO_POBLADO) != null && new BigDecimal(params.get(Constant.CAR_MAS_ID_CENTRO_POBLADO).toString()).compareTo(BigDecimal.ZERO) != 0) {
                sql.append(" AND c.subedificioId.cuentaMatrizObj.centroPoblado.gpoId = :idCentroPoblado");
            }
            
            if (params.get(Constant.CAR_MAS_CODIGO_NODO) != null) {
                sql.append(" AND UPPER(c.nodoId.nodCodigo) = UPPER(:nodCodigo)");
            }
            
            if (params.get(Constant.CAR_MAS_BLACKLIST_TECNOLOGIA) != null && new BigDecimal(params.get(Constant.CAR_MAS_BLACKLIST_TECNOLOGIA).toString()).compareTo(BigDecimal.ZERO) != 0) {
                sql.append(" AND bl.blackListObj.basicaId = :blacklistTec");
            }

            if (params.get(Constant.CAR_MAS_ID_CCMM_RR) != null && new BigDecimal(params.get(Constant.CAR_MAS_ID_CCMM_RR).toString()).compareTo(BigDecimal.ZERO) != 0) {
                sql.append(" AND c.subedificioId.cuentaMatrizObj.numeroCuenta = :idCcmmRr");
            }

            if (params.get(Constant.CAR_MAS_ID_CCMM_MGL) != null && new BigDecimal(params.get(Constant.CAR_MAS_ID_CCMM_MGL).toString()).compareTo(BigDecimal.ZERO) != 0) {
                sql.append(" AND c.subedificioId.cuentaMatrizObj.cuentaMatrizId = :idCcmmMgl");
            }

            Query q = entityManager.createQuery(sql.toString());
            
            q.setParameter("idTecnologia", params.get(Constant.CAR_MAS_ID_TECNOLOGIA));
            q.setParameter("idCiudad", params.get(Constant.CAR_MAS_ID_CIUDAD));
            
            if (params.get(Constant.CAR_MAS_ID_CENTRO_POBLADO) != null && new BigDecimal(params.get(Constant.CAR_MAS_ID_CENTRO_POBLADO).toString()).compareTo(BigDecimal.ZERO) != 0) {
                q.setParameter("idCentroPoblado", params.get(Constant.CAR_MAS_ID_CENTRO_POBLADO));
            }
            
            if (params.get(Constant.CAR_MAS_CODIGO_NODO) != null) {
                q.setParameter("nodCodigo", params.get(Constant.CAR_MAS_CODIGO_NODO).toString().toUpperCase());
            }
            
            if (params.get(Constant.CAR_MAS_BLACKLIST_TECNOLOGIA) != null && new BigDecimal(params.get(Constant.CAR_MAS_BLACKLIST_TECNOLOGIA).toString()).compareTo(BigDecimal.ZERO) != 0) {
                q.setParameter("blacklistTec", params.get(Constant.CAR_MAS_BLACKLIST_TECNOLOGIA));
            }

            if (params.get(Constant.CAR_MAS_ID_CCMM_RR) != null && new BigDecimal(params.get(Constant.CAR_MAS_ID_CCMM_RR).toString()).compareTo(BigDecimal.ZERO) != 0) {
                q.setParameter("idCcmmRr", params.get(Constant.CAR_MAS_ID_CCMM_RR));
            }

            if (params.get(Constant.CAR_MAS_ID_CCMM_MGL) != null && new BigDecimal(params.get(Constant.CAR_MAS_ID_CCMM_MGL).toString()).compareTo(BigDecimal.ZERO) != 0) {
                q.setParameter("idCcmmMgl", params.get(Constant.CAR_MAS_ID_CCMM_MGL));
            }

            
            Long result = (Long) q.getSingleResult();
            count = result.intValue();
        } catch (NoResultException n) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + n.getMessage();
            LOGGER.error(msg);
            return 0;
        } catch (Exception e) {
            LOGGER.error("Error en findReporteGeneral de CmtTecnologiaSubMglDaoImpl: " + e);
        }
        return count;
    }

    /**
     * valbuenayf metodo para contar la cantidad de rgistros del reporte
     * detallado de cuentamatriz para el cargue masivo
     *
     * @param params
     * @return
     */
    private Integer findCountDetallado(Map<String, Object> params) {
        Integer count = 0;
        try {
            StringBuilder sql = new StringBuilder();
            
            if (params.get(Constant.CAR_MAS_BLACKLIST_TECNOLOGIA) != null && new BigDecimal(params.get(Constant.CAR_MAS_BLACKLIST_TECNOLOGIA).toString()).compareTo(BigDecimal.ZERO) != 0) {

                sql.append("SELECT COUNT (c.tecnoSubedificioId) FROM CmtTecnologiaSubMgl c,  CmtBlackListMgl bl "
                        + " WHERE c.basicaIdTecnologias.basicaId = :idTecnologia");
                sql.append(" AND c.subedificioId.subEdificioId  =  bl.subEdificioObj.subEdificioId");
                sql.append(" AND c.subedificioId.cuentaMatrizObj.municipio.gpoId = :idCiudad");
                sql.append(" AND c.estadoRegistro = 1  AND c.subedificioId.estadoRegistro = 1  "
                        + " AND c.subedificioId.cuentaMatrizObj.estadoRegistro = 1");
            } else {
                sql.append("SELECT COUNT (c.tecnoSubedificioId)  FROM CmtTecnologiaSubMgl c"
                        + " WHERE c.basicaIdTecnologias.basicaId = :idTecnologia");
                sql.append(" AND c.subedificioId.cuentaMatrizObj.municipio.gpoId = :idCiudad");
                sql.append(" AND c.estadoRegistro = 1  AND c.subedificioId.estadoRegistro = 1  "
                        + " AND c.subedificioId.cuentaMatrizObj.estadoRegistro = 1");
            }

            if (params.get(Constant.CAR_MAS_ID_CENTRO_POBLADO) != null && new BigDecimal(params.get(Constant.CAR_MAS_ID_CENTRO_POBLADO).toString()).compareTo(BigDecimal.ZERO) != 0) {
                sql.append(" AND c.subedificioId.cuentaMatrizObj.centroPoblado.gpoId = :idCentroPoblado");
            }
            if (params.get(Constant.CAR_MAS_CODIGO_NODO) != null) {
                sql.append(" AND UPPER(c.nodoId.nodCodigo) = UPPER(:nodCodigo)");
            }

            if (params.get(Constant.CAR_MAS_CUENTA) != null && !params.get(Constant.CAR_MAS_CUENTA).toString().trim().isEmpty()) {
                sql.append(" AND UPPER(c.subedificioId.cuentaMatrizObj.nombreCuenta) like UPPER(:nombreCuenta)");
            }

            if (params.get(Constant.CAR_MAS_COMPANIA) != null && !params.get(Constant.CAR_MAS_COMPANIA).toString().trim().isEmpty()) {
                sql.append(" AND c.subedificioId.companiaAdministracionObj.companiaId = :compania");
            }

            if (params.get(Constant.CAR_MAS_ASCENSORES) != null && !params.get(Constant.CAR_MAS_ASCENSORES).toString().trim().isEmpty()) {
                sql.append(" AND c.subedificioId.companiaAscensorObj.companiaId = :ascensor");
            }

            if (params.get(Constant.CAR_MAS_ADMINISTRADOR) != null && !params.get(Constant.CAR_MAS_ADMINISTRADOR).toString().isEmpty()) {
                sql.append(" AND UPPER(c.subedificioId.administrador) like UPPER(:administrador) ");
            }

            if (params.get(Constant.CAR_MAS_TEL_UNO) != null && !params.get(Constant.CAR_MAS_TEL_UNO).toString().isEmpty()) {
                sql.append(" AND c.subedificioId.telefonoPorteria like :telefonoPorteria ");
            }

            if (params.get(Constant.CAR_MAS_TEL_DOS) != null && !params.get(Constant.CAR_MAS_TEL_DOS).toString().isEmpty()) {
                sql.append(" AND c.subedificioId.telefonoPorteria2 like :telefonoPorteria2 ");
            }

            if (params.get(Constant.CAR_MAS_PROYECTO) != null && !params.get(Constant.CAR_MAS_PROYECTO).toString().isEmpty()) {
                sql.append(" AND c.subedificioId.tipoProyectoObj.basicaId = :proyecto ");
            }

            if (params.get(Constant.CAR_MAS_DATOS) != null && !params.get(Constant.CAR_MAS_DATOS).toString().isEmpty()) {
                sql.append(" AND c.subedificioId.origenDatosObj.basicaId = :datos  ");
            }

            if (params.get(Constant.CAR_MAS_NODO) != null && !params.get(Constant.CAR_MAS_NODO).toString().isEmpty()) {
                sql.append("  AND c.nodoId.nodCodigo = UPPER(:nodId)");
            }

            if (params.get(Constant.CAR_MAS_ESTADO) != null && !params.get(Constant.CAR_MAS_ESTADO).toString().isEmpty()) {
                sql.append(" AND c.basicaIdEstadosTec.basicaId = :estado");
            }

            if (params.get(Constant.CAR_MAS_CONFIGURACION) != null && !params.get(Constant.CAR_MAS_CONFIGURACION).toString().isEmpty()) {
                sql.append(" AND c.tipoConfDistribObj.basicaId = :configuracion");
            }

            if (params.get(Constant.CAR_MAS_ALIMENTACION) != null && !params.get(Constant.CAR_MAS_ALIMENTACION).toString().isEmpty()) {
                sql.append(" AND c.alimentElectObj.basicaId = :alimentacion");
            }

            if (params.get(Constant.CAR_MAS_DSITRIBUCION) != null && !params.get(Constant.CAR_MAS_DSITRIBUCION).toString().isEmpty()) {
                sql.append(" AND c.tipoDistribucionObj.basicaId = :distribucion ");
            }

            //////////
            if (params.get(Constant.CAR_MAS_CONSTRUCTORA) != null && !params.get(Constant.CAR_MAS_CONSTRUCTORA).toString().isEmpty()) {
                sql.append(" AND c.subedificioId.companiaConstructoraObj.companiaId = :constructora");
            }

            if (params.get(Constant.CAR_MAS_TIPO_CCMM) != null && !params.get(Constant.CAR_MAS_TIPO_CCMM).toString().isEmpty()) {
                sql.append(" AND c.subedificioId.tipoEdificioObj.basicaId = :tipoCcmm");
            }

            if (params.get(Constant.CAR_MAS_BLACKLIST_TECNOLOGIA) != null && new BigDecimal(params.get(Constant.CAR_MAS_BLACKLIST_TECNOLOGIA).toString()).compareTo(BigDecimal.ZERO) != 0) {
                sql.append(" AND bl.blackListObj.basicaId = :blacklistTec");
            }

            if (params.get(Constant.CAR_MAS_ID_CCMM_RR) != null && new BigDecimal(params.get(Constant.CAR_MAS_ID_CCMM_RR).toString()).compareTo(BigDecimal.ZERO) != 0) {
                sql.append(" AND c.subedificioId.cuentaMatrizObj.numeroCuenta = :idCcmmRr");
            }

            if (params.get(Constant.CAR_MAS_ID_CCMM_MGL) != null && new BigDecimal(params.get(Constant.CAR_MAS_ID_CCMM_MGL).toString()).compareTo(BigDecimal.ZERO) != 0) {
                sql.append(" AND c.subedificioId.cuentaMatrizObj.cuentaMatrizId = :idCcmmMgl");
            }

            //////
            Query q = entityManager.createQuery(sql.toString());
            q.setParameter("idTecnologia", params.get(Constant.CAR_MAS_ID_TECNOLOGIA));
            q.setParameter("idCiudad", params.get(Constant.CAR_MAS_ID_CIUDAD));

            if (params.get(Constant.CAR_MAS_ID_CENTRO_POBLADO) != null && new BigDecimal(params.get(Constant.CAR_MAS_ID_CENTRO_POBLADO).toString()).compareTo(BigDecimal.ZERO) != 0) {
                q.setParameter("idCentroPoblado", params.get(Constant.CAR_MAS_ID_CENTRO_POBLADO));
            }
            if (params.get(Constant.CAR_MAS_CODIGO_NODO) != null) {
                q.setParameter("nodCodigo", params.get(Constant.CAR_MAS_CODIGO_NODO).toString().toUpperCase());
            }

            if (params.get(Constant.CAR_MAS_CUENTA) != null && !params.get(Constant.CAR_MAS_CUENTA).toString().trim().isEmpty()) {
                q.setParameter("nombreCuenta", "%" + params.get(Constant.CAR_MAS_CUENTA).toString().trim().toUpperCase() + "%");
            }

            if (params.get(Constant.CAR_MAS_COMPANIA) != null && !params.get(Constant.CAR_MAS_COMPANIA).toString().trim().isEmpty()) {
                q.setParameter("compania", params.get(Constant.CAR_MAS_COMPANIA));
            }

            if (params.get(Constant.CAR_MAS_ASCENSORES) != null && !params.get(Constant.CAR_MAS_ASCENSORES).toString().trim().isEmpty()) {
                q.setParameter("ascensor", params.get(Constant.CAR_MAS_ASCENSORES));
            }

            if (params.get(Constant.CAR_MAS_ADMINISTRADOR) != null && !params.get(Constant.CAR_MAS_ADMINISTRADOR).toString().trim().isEmpty()) {
                q.setParameter("administrador", "%" + params.get(Constant.CAR_MAS_ADMINISTRADOR).toString().trim().toUpperCase() + "%");
            }

            if (params.get(Constant.CAR_MAS_TEL_UNO) != null && !params.get(Constant.CAR_MAS_TEL_UNO).toString().trim().isEmpty()) {
                q.setParameter("telefonoPorteria", "%" + params.get(Constant.CAR_MAS_TEL_UNO).toString().trim() + "%");
            }

            if (params.get(Constant.CAR_MAS_TEL_DOS) != null && !params.get(Constant.CAR_MAS_TEL_DOS).toString().trim().isEmpty()) {
                q.setParameter("telefonoPorteria2", "%" + params.get(Constant.CAR_MAS_TEL_DOS).toString().trim() + "%");
            }

            if (params.get(Constant.CAR_MAS_PROYECTO) != null && !params.get(Constant.CAR_MAS_PROYECTO).toString().isEmpty()) {
                q.setParameter("proyecto", params.get(Constant.CAR_MAS_PROYECTO));
            }

            if (params.get(Constant.CAR_MAS_DATOS) != null && !params.get(Constant.CAR_MAS_DATOS).toString().isEmpty()) {
                q.setParameter("datos", params.get(Constant.CAR_MAS_DATOS));
            }

            if (params.get(Constant.CAR_MAS_NODO) != null && !params.get(Constant.CAR_MAS_NODO).toString().isEmpty()) {
                q.setParameter("nodId", params.get(Constant.CAR_MAS_NODO).toString().toUpperCase());
            }

            if (params.get(Constant.CAR_MAS_ESTADO) != null && !params.get(Constant.CAR_MAS_ESTADO).toString().isEmpty()) {
                q.setParameter("estado", params.get(Constant.CAR_MAS_ESTADO));
            }

            if (params.get(Constant.CAR_MAS_CONFIGURACION) != null && !params.get(Constant.CAR_MAS_CONFIGURACION).toString().isEmpty()) {
                q.setParameter("configuracion", params.get(Constant.CAR_MAS_CONFIGURACION));
            }

            if (params.get(Constant.CAR_MAS_ALIMENTACION) != null && !params.get(Constant.CAR_MAS_ALIMENTACION).toString().isEmpty()) {
                q.setParameter("alimentacion", params.get(Constant.CAR_MAS_ALIMENTACION));
            }

            if (params.get(Constant.CAR_MAS_DSITRIBUCION) != null && !params.get(Constant.CAR_MAS_DSITRIBUCION).toString().isEmpty()) {
                q.setParameter("distribucion", params.get(Constant.CAR_MAS_DSITRIBUCION));
            }
            ///////////

            if (params.get(Constant.CAR_MAS_CONSTRUCTORA) != null && !params.get(Constant.CAR_MAS_CONSTRUCTORA).toString().isEmpty()) {
                q.setParameter("constructora", params.get(Constant.CAR_MAS_CONSTRUCTORA));
            }

            if (params.get(Constant.CAR_MAS_TIPO_CCMM) != null && !params.get(Constant.CAR_MAS_TIPO_CCMM).toString().isEmpty()) {
                q.setParameter("tipoCcmm", params.get(Constant.CAR_MAS_TIPO_CCMM));
            }

            if (params.get(Constant.CAR_MAS_BLACKLIST_TECNOLOGIA) != null && new BigDecimal(params.get(Constant.CAR_MAS_BLACKLIST_TECNOLOGIA).toString()).compareTo(BigDecimal.ZERO) != 0) {
                q.setParameter("blacklistTec", params.get(Constant.CAR_MAS_BLACKLIST_TECNOLOGIA));
            }

            if (params.get(Constant.CAR_MAS_ID_CCMM_RR) != null && new BigDecimal(params.get(Constant.CAR_MAS_ID_CCMM_RR).toString()).compareTo(BigDecimal.ZERO) != 0) {
                q.setParameter("idCcmmRr", params.get(Constant.CAR_MAS_ID_CCMM_RR));
            }

            if (params.get(Constant.CAR_MAS_ID_CCMM_MGL) != null && new BigDecimal(params.get(Constant.CAR_MAS_ID_CCMM_MGL).toString()).compareTo(BigDecimal.ZERO) != 0) {
                q.setParameter("idCcmmMgl", params.get(Constant.CAR_MAS_ID_CCMM_MGL));
            }

            ////
            Long result = (Long) q.getSingleResult();
            count = result.intValue();
        } catch (NoResultException n) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + n.getMessage();
            LOGGER.error(msg);
            return 0;
        } catch (Exception e) {
            LOGGER.error("Error en findReporteDetallado de CmtTecnologiaSubMglDaoImpl: " + e);
        }
        return count;
    }

    /**
     * valbuenayf metodo para buscar CmtTecnologiaSubMgl por el id
     *
     * @param tecnoSubedificioId
     * @return
     */
    public CmtTecnologiaSubMgl findIdTecnoSub(BigDecimal tecnoSubedificioId) {
        CmtTecnologiaSubMgl tecnologiaSub = null;
        try {
            Query query = entityManager.createNamedQuery("CmtTecnologiaSubMgl.findByTecnoSubedificioId");
            query.setParameter("tecnoSubedificioId", tecnoSubedificioId);
            tecnologiaSub = (CmtTecnologiaSubMgl) query.getSingleResult();
        } catch (NoResultException n) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + n.getMessage();
            LOGGER.error(msg);
            return null;
        } catch (Exception e) {
            LOGGER.error("Error en findIdTecnoSub de CmtTecnologiaSubMglDaoImpl: " + e);
        }
        return tecnologiaSub;
    }

    /**
     * Busca las tecnologias asociadas a un nodo.
     *
     * @author Victor Bocanegra
     * @param nodo
     * @return Tecnologias asociadas a un Subedificio.
     * @throws ApplicationException
     */
    public List<CmtTecnologiaSubMgl> findTecnoSubByNodo(NodoMgl nodo) throws ApplicationException {
        try {
            List<CmtTecnologiaSubMgl> resulList;
            Query query = entityManager.createNamedQuery("CmtTecnologiaSubMgl.findByNodoId");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            if (nodo != null) {
                query.setParameter("nodoId", nodo);
            }
            resulList = (List<CmtTecnologiaSubMgl>) query.getResultList();
            return resulList;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Metodo que obtiene la lista de las cuentas matrices por tecnologias
     *
     * @param params
     * @throws co.com.claro.mgl.error.ApplicationException
     * @cmtSubEdificioMgl subedificio o cuenta matriz
     * @return Dto con informacion del conteo de HHPP activos
     * @author Lenis Cardenas
     * @modificado Christian Acosta
     * @modificado cardenaslb
     */
    public List<Object[]> findListCMxTecno(HashMap<String, Object> params) throws ApplicationException {

        List<Object[]> listaCMxTecno;

        String queryString =
                "  select "
                + " c.fechaCreacion, s.basicaIdTecnologias.nombreBasica, "
                + " (select  tn.nodCodigo from NodoMgl tn, CmtTecnologiaSubMgl ts ,  CmtSubEdificioMgl se "
                + " where tn.nodId = ts.nodoId.nodId and se.subEdificioId = ts.subedificioId.subEdificioId and "
                + " ts.basicaIdTecnologias.basicaId = s.basicaIdTecnologias.basicaId "
                + " and se.subEdificioId in ( select e.subEdificioId from CmtSubEdificioMgl e where   "
                + " e.subEdificioId = t.subEdificioId )) as REPORTE_NODO, "
                + " t.subEdificioId as SUBEDIFICIO_ID,"
                + " c.cuentaMatrizId as CUENTAMATRIZ_ID,"
                + " c.numeroCuenta as NUMERO_CUENTA, "
                + " c.nombreCuenta as NOMBRE_CUENTA, "
                + " (case when (t.estadoSubEdificioObj.basicaId  = 50) "
                + " then  'MULTIEDIFICIO' else t.nombreSubedificio end) as NOMBRE_EDIFICIO, "
                + " (select d.dirFormatoIgac from  DireccionMgl d, CmtDireccionMgl dir"
                + " where dir.direccionObj.dirId = d.dirId  "
                + " and dir.cuentaMatrizObj.cuentaMatrizId = c.cuentaMatrizId) as DIRECCION, "
                + " (select dir.barrio from CmtDireccionMgl dir, DireccionMgl d "
                + " where c.cuentaMatrizId = dir.cuentaMatrizObj.cuentaMatrizId and dir.direccionObj.dirId = d.dirId ) as BARRIO, "
                + " (select dpto.gpoNombre from GeograficoPoliticoMgl dpto where "
                + " dpto.geoGpoId = c.departamento.gpoId and  dpto.gpoId = c.municipio.gpoId) as Ciudad, "
                + " (select dpto.gpoNombre from GeograficoPoliticoMgl dpto where "
                + " dpto.gpoId = c.centroPoblado.gpoId ) as CentroPoblado, "
                + " (select count(sub.subEdificioId) from CmtSubEdificioMgl sub where "
                + " c.cuentaMatrizId = sub.cuentaMatrizObj.cuentaMatrizId "
                + " and sub.estadoSubEdificioObj.basicaId not in(50) ) as No_Torres, "
                + " (case when (t.estadoSubEdificioObj.basicaId  = 50) "
                + " then  0 else (select sum(e.pisos) from CmtSubEdificioMgl e where "
                + " e.cuentaMatrizObj.cuentaMatrizId = c.cuentaMatrizId and "
                + " e.estadoSubEdificioObj.basicaId  not in (50)) end) as PISOS, "
                + " (select bas.nombreBasica from CmtBasicaMgl bas where bas.basicaId = t.estrato.basicaId) as ESTRATO,"
                + " (select bas.nombreBasica from CmtBasicaMgl bas where bas.basicaId = s.basicaIdTecnologias.basicaId) as Tecnologia_subedificio,"
                + " (select bas.nombreBasica from CmtBasicaMgl bas where bas.basicaId = s.basicaIdEstadosTec.basicaId) as ESTADO_CM,"
               
                + " (case when (t.estadoSubEdificioObj.basicaId  = 50 ) "
             	+ " then (select SUM(count(th.hhpId)) " 
                + " from CmtTecnologiaSubMgl ts, HhppMgl th, CmtBasicaMgl  bas " 
                + " where th.cmtTecnologiaSubId.tecnoSubedificioId = ts.tecnoSubedificioId "
                + " and bas.basicaId = ts.basicaIdTecnologias.basicaId " 
                + " and ts.basicaIdTecnologias.basicaId in ( select ba.basicaId from CmtBasicaMgl  ba" 
                + " where ba.basicaId = ts.basicaIdTecnologias.basicaId ) " 
                + " and th.hhppSubEdificioObj.subEdificioId in (select e.subEdificioId  from CmtSubEdificioMgl e " 
                + " where e.cuentaMatrizObj.cuentaMatrizId = c.cuentaMatrizId ) GROUP BY th.hhpId )  else "
                + " (select SUM(count(th.hhpId)) " 
                + " from CmtTecnologiaSubMgl ts, HhppMgl th, CmtBasicaMgl  bas " 
                + " where th.cmtTecnologiaSubId.tecnoSubedificioId = ts.tecnoSubedificioId "
                + " and bas.basicaId = ts.basicaIdTecnologias.basicaId " 
                + " and ts.basicaIdTecnologias.basicaId in ( select ba.basicaId from CmtBasicaMgl  ba" 
                + " where ba.basicaId = ts.basicaIdTecnologias.basicaId ) " 
                + " and th.hhppSubEdificioObj.subEdificioId = t.subEdificioId GROUP BY th.hhpId) "
                + " end ) as TotalHHPP, "
                + " (case when (t.estadoSubEdificioObj.basicaId  = 50 ) "
             	+ " then (select SUM(count(th.hhpId)) " 
                + " from CmtTecnologiaSubMgl ts, HhppMgl th, CmtBasicaMgl  bas, EstadoHhppMgl eth "
                + " where eth.ehhID = th.ehhId.ehhID and eth.ehhID = 'CS' " 
                + " and th.cmtTecnologiaSubId.tecnoSubedificioId = ts.tecnoSubedificioId "
                + " and bas.basicaId = ts.basicaIdTecnologias.basicaId " 
                + " and ts.basicaIdTecnologias.basicaId in ( select ba.basicaId from CmtBasicaMgl  ba" 
                + " where ba.basicaId = ts.basicaIdTecnologias.basicaId ) " 
                + " and th.hhppSubEdificioObj.subEdificioId in (select e.subEdificioId  from CmtSubEdificioMgl e " 
                + " where e.cuentaMatrizObj.cuentaMatrizId = c.cuentaMatrizId ) GROUP BY th.hhpId )  else "
                + " (select SUM(count(th.hhpId)) " 
                + " from CmtTecnologiaSubMgl ts, HhppMgl th, CmtBasicaMgl  bas, EstadoHhppMgl eth"
                + " where eth.ehhID = th.ehhId.ehhID and eth.ehhID = 'CS' " 
                + " and th.cmtTecnologiaSubId.tecnoSubedificioId = ts.tecnoSubedificioId "
                + " and bas.basicaId = ts.basicaIdTecnologias.basicaId " 
                + " and ts.basicaIdTecnologias.basicaId in ( select ba.basicaId from CmtBasicaMgl  ba" 
                + " where ba.basicaId = ts.basicaIdTecnologias.basicaId ) " 
                + " and th.hhppSubEdificioObj.subEdificioId = t.subEdificioId GROUP BY th.hhpId) "
                + " end ) as Total_Clientes_Activos, "
                + " 'XXX' AS Penetracion, "
                + " 'XXX' as Cumplimiento, "
                + " s.meta, "
                + " s.costoAcometida as COSTO_ACOMETIDA,   "
                + " s.visitaTecnica.idVt as NUMERO_VT , "
                + " s.ordenTrabajo.idOt  as NUMERO_OT_VT,"
                + " s.costoVt as COSTO_VT, "
                + " s.fechaHabilitacion as  FECHA_HABILITACION,"
                + " s.tiempoRecuperacion as TIEMPO_RECUPERACION, "
                + " 0 as Renta_mensual "
                + " from "
                + " CmtSubEdificioMgl t, CmtCuentaMatrizMgl c, CmtTecnologiaSubMgl s "
                + " where c.cuentaMatrizId = t.cuentaMatrizObj.cuentaMatrizId and "
                + " t.subEdificioId = s.subedificioId.subEdificioId and "
                + " t.cuentaMatrizObj.cuentaMatrizId in "
                
               +" (select  c.cuentaMatrizId  "
                + " from  CmtSubEdificioMgl e,  CmtCuentaMatrizMgl c, CmtTecnologiaSubMgl t, CmtBasicaMgl b "
                + " where c.cuentaMatrizId = e.cuentaMatrizObj.cuentaMatrizId and "
                + " e.subEdificioId = t.subedificioId.subEdificioId and "
                + " c.estadoRegistro = 1 and  b.basicaId = t.basicaIdEstadosTec.basicaId and "
                + " b.tipoBasicaObj.tipoBasicaId = 20  AND b.identificadorInternoApp like :identificadorApp) "
                + " and s.basicaIdTecnologias.basicaId  = :tecnologia "
                + " and ( c.fechaCreacion BETWEEN :fechaInicio and  :fechaFinal) ";


        if (params.get(Constant.REPORTE_NODO) != null && !((String) params.get(Constant.REPORTE_NODO)).isEmpty()) {
            queryString += " and  s.nodoId.nodNombre = :nodo ";

        }
        if (params.get(Constant.REPORTE_ESTRATO) != null) {
            queryString += " and  t.estrato.basicaId = :estrato ";

        }

        queryString += " order by t.subEdificioId asc";

        Query queryTorres = entityManager.createQuery(queryString);
        
         queryTorres.setParameter("identificadorApp", "%" + Constant.BASICA_TIPO_TEC_CABLE + "%");
        if (params.get("tecnologia") != null) {
            
           queryTorres.setParameter("tecnologia", (BigDecimal)params.get("tecnologia"));
        }
        if (params.get("fechaInicio") != null) {
           queryTorres.setParameter("fechaInicio",  (Date) params.get("fechaInicio"));
        }
        if (params.get("fechaFinal") != null) {
            queryTorres.setParameter("fechaFinal",   (Date) params.get("fechaFinal"));
        }
        if (((params.get(Constant.REPORTE_NODO) != null && !((String) params.get(Constant.REPORTE_NODO)).isEmpty()))
                && ((params.get(Constant.REPORTE_ESTRATO) == null))) {

            queryTorres.setParameter("nodo", params.get("nodo"));

        } else if ((params.get(Constant.REPORTE_ESTRATO) != null)
                && (((String) params.get(Constant.REPORTE_NODO)).isEmpty())) {

            queryTorres.setParameter("estrato", (BigDecimal) params.get("estrato"));

        } else if (!((String) params.get(Constant.REPORTE_NODO)).isEmpty()
                && (params.get(Constant.REPORTE_ESTRATO)) != null) {

            queryTorres.setParameter("estrato", (BigDecimal) params.get("estrato"));
            queryTorres.setParameter("nodo", (String) params.get("nodo"));
        }

        listaCMxTecno = queryTorres.getResultList();

        return listaCMxTecno;
    }

    /**
     * Total hhpp para reporte penetracion
     *
     * @param idCuentaMatriz
     * @return Dto con informacion del conteo de HHPP
     * @author Lenis Cardenas
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public int countReportHhppTecCM(BigDecimal idCuentaMatriz) throws ApplicationException {
        String queryString = "select count(h.hhpId) "
                + " from CmtTecnologiaSubMgl t, HhppMgl h "
                + " where  t.tecnoSubedificioId = h.cmtTecnologiaSubId.tecnoSubedificioId "
                + " and t.subedificioId.subEdificioId in ( select e.subEdificioId from CmtSubEdificioMgl e "
                + " where e.subEdificioId  in ( SELECT c.subEdificioId FROM CmtSubEdificioMgl c "
                + "WHERE c.cuentaMatrizObj.cuentaMatrizId = :cuentaMatrizObj AND c.estadoRegistro=1))";

        queryString += " group by  h.hhpId, t.tecnoSubedificioId ";
        Query query = entityManager.createQuery(queryString);

        query.setParameter("cuentaMatrizObj", idCuentaMatriz);

        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        LOGGER.error("");

        int rows = query.getResultList().size() - 2;

        return rows;
    }

    /**
     * Cantidad hhpp activos reporte penetracion reporte de penetracion
     *
     * @param cmtCuentaMatrizMgl
     *
     * @return Dto con informacion del conteo de HHPP activos
     * @author Lenis Cardenas
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public int countReportClientesActivos(BigDecimal cmtCuentaMatrizMgl) throws ApplicationException {
        int totalClientes = 0;
        String queryString = "select h.hhpId, count(s.basicaIdTecnologias) "
                + " from CmtTecnologiaSubMgl s, HhppMgl h  "
                + " where  s.tecnoSubedificioId = h.cmtTecnologiaSubId.tecnoSubedificioId and h.ehhId.ehhID = 'CS'";

        if (cmtCuentaMatrizMgl != null) {
            queryString += " and s.subedificioId.subEdificioId  in ( select e.subEdificioId  from  CmtSubEdificioMgl e "
                    + "where e.subEdificioId in ( SELECT c.subEdificioId FROM CmtSubEdificioMgl c "
                    + "WHERE c.cuentaMatrizObj.cuentaMatrizId = :cuentaMatrizObj AND c.estadoRegistro=1) ) ";
        }

        queryString += " group by  h.hhpId, s.basicaIdTecnologias  ";

        Query query = entityManager.createQuery(queryString);
        if (cmtCuentaMatrizMgl != null) {
            query.setParameter("cuentaMatrizObj", cmtCuentaMatrizMgl);
        }
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        int rows = query.getResultList().size();
        return rows;
    }

    /**
     * Metodo que calcula la cantidad de hhpp activos en estado = 'S' por CM
     *
     * @param cmtCuentaMatrizMgl
     * @param findBy
     *
     * @return Dto con informacion del conteo de HHPP activos
     * @author Lenis Cardenas
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public int countClientesActivosByCM(BigDecimal cmtCuentaMatrizMgl,
            Constant.FIND_HHPP_BY findBy) throws ApplicationException {
        int totalClientesActivos = 0;
        String queryString = "select count(t.basicaIdTecnologias) ,t.basicaIdTecnologias,sum(h.cfm) "
                + " from CmtTecnologiaSubMgl t, HhppMgl h "
                + " where  t.tecnoSubedificioId = h.cmtTecnologiaSubId.tecnoSubedificioId  and h.ehhId.ehhID = 'CS'"
                + " and t.subedificioId.subEdificioId in ( select e.subEdificioId from CmtSubEdificioMgl e ";
        if (Constant.FIND_HHPP_BY.CUENTA_MATRIZ.equals(findBy)) {
            queryString += "where  e.cuentaMatrizObj.cuentaMatrizId = :cuentaMatrizObj )";
        }
        queryString += " group by  h.hhpId, h.cfm , h.cmtTecnologiaSubId.basicaIdTecnologias ";
        Query query = entityManager.createQuery(queryString);
        if (Constant.FIND_HHPP_BY.CUENTA_MATRIZ.equals(findBy)) {
            query.setParameter("cuentaMatrizObj", cmtCuentaMatrizMgl);
        }
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        List<Object[]> rows = query.getResultList();
        List<HhppTecnologiaDto> listaHhppTecnologiaDto = new ArrayList<HhppTecnologiaDto>();
        if (!rows.isEmpty()) {
            for (Object[] row : rows) {
                totalClientesActivos = totalClientesActivos + ((Long) row[0]).intValue();
            }
            return totalClientesActivos;
        } else {
            return totalClientesActivos;
        }

    }

    /**
     * Metodo que calcula el numero de HHPP por CM
     *
     * @param cmtCuentaMatrizMgl
     * @param findBy
     * @return Dto con informacion del conteo de HHPP
     * @author Lenis Cardenas
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public int countHhppByCM(BigDecimal cmtCuentaMatrizMgl,
            Constant.FIND_HHPP_BY findBy) throws ApplicationException {
        int totalHHpp = 0;
        String queryString = "select count(t.basicaIdTecnologias) ,t.basicaIdTecnologias "
                + " from CmtTecnologiaSubMgl t, HhppMgl h "
                + " where  t.tecnoSubedificioId = h.cmtTecnologiaSubId.tecnoSubedificioId "
                + " and t.subedificioId.subEdificioId in ( select e.subEdificioId from CmtSubEdificioMgl e ";

        if (Constant.FIND_HHPP_BY.CUENTA_MATRIZ.equals(findBy)) {
            queryString += " where e.cuentaMatrizObj.cuentaMatrizId  = :cuentaMatrizObj )";
        }
        queryString += " group by  t.basicaIdTecnologias ";
        Query query = entityManager.createQuery(queryString);

        if (Constant.FIND_HHPP_BY.CUENTA_MATRIZ.equals(findBy)) {
            query.setParameter("cuentaMatrizObj", cmtCuentaMatrizMgl);
        }
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        List<Object[]> rows = query.getResultList();
        if (!rows.isEmpty()) {
            for (Object[] row : rows) {
                totalHHpp = totalHHpp + ((Long) row[0]).intValue();
            }
            return totalHHpp;
        } else {
            return totalHHpp;
        }

    }

    /**
     * Buscar una sub tecnologia basandose en un subedificio
     *
     * @author rodriguezluim
     * @param subEdificio Condicional por el cual voy a buscar
     * @param tecnologia
     * @return Un elemento de tipo CmtTecnologiaSub
     */
    public CmtTecnologiaSubMgl findBySubEdificioTecnologiaId(CmtSubEdificioMgl subEdificio,
            BigDecimal tecnologia) {
        CmtTecnologiaSubMgl resultList;

        try {
            Query query = entityManager.createNamedQuery("CmtTecnologiaSubMgl.findBySubEdiAndTecnoId");
            query.setParameter("subedificioId", subEdificio);
            query.setParameter("basicaIdTecnologias", tecnologia);
            resultList = (CmtTecnologiaSubMgl) query.getSingleResult();

        } catch (NoResultException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            CmtTecnologiaSubMgl cmtTecnologiaSubMgl = new CmtTecnologiaSubMgl();
            return cmtTecnologiaSubMgl;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            return null;
        }
        return resultList;

    }

    /**
     * Metodo que calcula la media de las metas de una cuenta matriz en estado
     * cable
     *
     * @param cmtCuentaMatrizMgl
     * @param tecnologia
     * @return Dto con informacion del conteo de HHPP
     * @author Lenis Cardenas
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public BigDecimal getMeta(BigDecimal cmtCuentaMatrizMgl, BigDecimal tecnologia) throws ApplicationException {
        try {
            String queryString = "select avg(t.meta) "
                    + " from CmtTecnologiaSubMgl t , CmtSubEdificioMgl s "
                    + " where  t.subedificioId.subEdificioId = s.subEdificioId "
                    + " and  t.basicaIdTecnologias.basicaId = :tecnologia "
                    + " and s.cuentaMatrizObj.cuentaMatrizId = :cuentaMatrizObj "
                    + " and t.basicaIdEstadosTec.basicaId in ( select e.basicaId from CmtBasicaMgl e "
                    + " where e.identificadorInternoApp like :identificadorApp )";

            Query query = entityManager.createQuery(queryString);
            query.setParameter("cuentaMatrizObj", cmtCuentaMatrizMgl);
            query.setParameter("identificadorApp", "%" + Constant.BASICA_TIPO_TEC_CABLE + "%");
            query.setParameter("tecnologia", tecnologia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            Double rows = (Double) query.getSingleResult();
            BigDecimal b = null;
            if (rows != null) {
                b = new BigDecimal(rows, MathContext.DECIMAL64);
            }
            return b;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            return null;
        }

    }

    /**
     * Total hhpp para reporte penetracion
     *
     * @param idCuentaMatriz
     * @return Dto con informacion del conteo de HHPP
     * @author Lenis Cardenas
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public int countHhppTotales(CmtCuentaMatrizMgl idCuentaMatriz) throws ApplicationException {
        String queryString = "select count(t.basicaIdTecnologias) "
                + " from CmtTecnologiaSubMgl t, HhppMgl h "
                + " where  t.tecnoSubedificioId = h.cmtTecnologiaSubId.tecnoSubedificioId "
                + " and t.subedificioId.subEdificioId in ( select e.subEdificioId from CmtSubEdificioMgl e "
                + " where e.subEdificioId  in ( SELECT c.subEdificioId FROM CmtSubEdificioMgl c "
                + "WHERE c.cuentaMatrizObj.cuentaMatrizId = :cuentaMatrizObj AND c.estadoRegistro=1))";

        queryString += " group by  h.hhpId, t.tecnoSubedificioId ";
        Query query = entityManager.createQuery(queryString);

        query.setParameter("cuentaMatrizObj", idCuentaMatriz.getCuentaMatrizId());

        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        LOGGER.error("");

        int rows = query.getResultList().size();

        return rows;
    }

}
