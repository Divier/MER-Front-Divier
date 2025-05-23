package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.dao.impl.cm.CmtBasicaMglDaoImpl;
import co.com.claro.mgl.dtos.FiltroBusquedaDirecccionDto;
import co.com.claro.mgl.dtos.FiltroConsultaHhppDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.DireccionMgl;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.HhppAuditoriaMgl;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.OtHhppMgl;
import co.com.claro.mgl.jpa.entities.SubDireccionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionDetalladaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionSolicitudMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.visitasTecnicas.business.DireccionRRManager;
import co.com.claro.visitasTecnicas.entities.HhppResponseRR;
import co.com.telmex.catastro.data.Hhpp;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Admin
 */
public class HhppMglDaoImpl extends GenericDaoImpl<HhppMgl> {

    private static final Logger LOGGER = LogManager.getLogger(HhppMglDaoImpl.class);

    public List<HhppMgl> findAll() throws ApplicationException {
        try {

            Query query = entityManager.createNamedQuery("HhppMgl.findAll");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");// jpa 2.0
            return query.getResultList();
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    public List<HhppMgl> findHhppByInNodo(List<NodoMgl> nodoHhpp) throws ApplicationException {
        try {
            Query query = entityManager
                    .createQuery("SELECT h FROM HhppMgl h WHERE h.nodId.nodId in :nodId and h.estadoRegistro = 1");
            List<BigDecimal> listIdNodo = new ArrayList<BigDecimal>();
            if (nodoHhpp != null && nodoHhpp.size() > 0) {
                for (NodoMgl n : nodoHhpp) {
                    listIdNodo.add(n.getNodId());
                }
            }
            query.setParameter("nodId", listIdNodo);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");// jpa 2.0
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.error("Error al momento de consultar el HHPP. EX000: " + e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    public HhppMgl findHhppByRRFieldsInRepository(Hhpp hhpp) {
        try {
            TypedQuery<HhppMgl> query = entityManager.createQuery(
                    "SELECT g FROM HhppMgl g WHERE UPPER(g.HhpCalle) = :calleRR AND UPPER(g.HhpPlaca) = :placaRR AND UPPER(g.HhpApart) = :apartRR "
                            + " AND UPPER(g.HhpComunidad) = :comunidadRR AND UPPER(g.HhpDivision) = :divisionRR ",
                    HhppMgl.class);
            query.setParameter("calleRR", hhpp.getCalleRR().toUpperCase());
            query.setParameter("placaRR", hhpp.getUnidadRR().toUpperCase());
            query.setParameter("apartRR", hhpp.getAptoRR().toUpperCase());
            query.setParameter("comunidadRR", hhpp.getComunidadRR().toUpperCase());
            query.setParameter("divisionRR", hhpp.getDivisionRR().toUpperCase());
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");// jpa 2.0
            return query.getSingleResult();
        } catch (NoResultException e) {
            LOGGER.error("No fue encontrado ningún HHPP: " + e.getMessage());
            return null;
        } catch (Exception e) {
            LOGGER.error("Error al momento de consultar el HHPP. EX000: " + e.getMessage(), e);
            return null;
        }
    }

    /**
     * Busca un HHPP en repositorio. Permite buscar un HHPP en el repositorio
     * por los campos de la direccion de la unidad RR
     *
     * @param hhpp Objeto HHPP con los parametros de la direccion
     *
     * @return HhppMgl si la unidad Existe en el repositorio
     *
     * @author Johnnatan Ortiz
     */
    public HhppMgl findHhppByRRFieldsInRepository(HhppMgl hhpp) {
        try {
            TypedQuery<HhppMgl> query = entityManager.createNamedQuery("HhppMgl.findByRRFields",
                    HhppMgl.class);
            query.setParameter("calleRR", hhpp.getHhpCalle().toUpperCase());
            query.setParameter("placaRR", hhpp.getHhpPlaca().toUpperCase());
            query.setParameter("apartRR", hhpp.getHhpApart().toUpperCase());
            query.setParameter("comunidadRR", hhpp.getHhpComunidad().toUpperCase());
            query.setParameter("divisionRR", hhpp.getHhpDivision().toUpperCase());
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");// jpa 2.0
            return query.getSingleResult();
        } catch (NoResultException e) {
            LOGGER.error("No fue encontrado ningún HHPP: " + e.getMessage());
            return null;
        } catch (Exception e) {
            LOGGER.error("Error al momento de consultar el HHPP. EX000: " + e.getMessage(), e);
            return null;
        }
    }

    public HhppMgl findById(BigDecimal idHhpp) {
        HhppMgl result = null;
        TypedQuery<HhppMgl> query = entityManager.createQuery("SELECT g FROM HhppMgl g WHERE g.hhpId = :idHhpp ",
                HhppMgl.class);
        query.setParameter("idHhpp", idHhpp);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");// jpa 2.0
        try {
            result = query.getSingleResult();
        } catch (NoResultException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            result = null;
        }

        return result;
    }

    public List<HhppMgl> findHhppBySelect(BigDecimal geo, BigDecimal blackLis,
            String tipoUnidad, String estadoUnidad, String calle, String placa,
            String apartamento, BigDecimal hhppId, NodoMgl nodo, Date fechaCreacion,
            String comunidad, String division) throws ApplicationException {
        try {
            String consulta = "SELECT h FROM HhppMgl h WHERE  ";

            if (calle != null) {
                consulta += "  h.HhpCalle = :HhpCalle ";
            }
            if (placa != null) {
                if (calle != null) {
                    consulta += "  AND ";
                }
                consulta += "  h.HhpPlaca = :HhpPlaca ";
            }

            if (apartamento != null) {
                if (calle != null || placa != null) {
                    consulta += "  AND ";
                }
                consulta += "  h.HhpApart = :HhpApart ";
            }

            if (fechaCreacion != null) {
                if (calle != null || placa != null || apartamento != null) {
                    consulta += "  AND ";
                }
                consulta += " ( h.hhpFechaCreacion BETWEEN :hhpFechaCreacionI and  :hhpFechaCreacionF )  ";
            }

            if (hhppId != null) {
                if (calle != null || placa != null || apartamento != null || fechaCreacion != null) {
                    consulta += "  AND ";
                }
                consulta += "  h.hhpId = :hhpId ";
            }

            if (nodo != null) {
                if (calle != null || placa != null || apartamento != null || fechaCreacion != null || hhppId != null) {
                    consulta += "  AND ";
                }
                consulta += "  h.nodId.nodId = :nodId ";
            }

            if (comunidad != null) {
                if (calle != null || placa != null || apartamento != null || fechaCreacion != null || hhppId != null
                        || nodo != null) {
                    consulta += "  AND ";
                }
                consulta += "  h.HhpComunidad = :HhpComunidad ";
            }

            if (division != null) {
                if (calle != null || placa != null || apartamento != null || fechaCreacion != null || hhppId != null
                        || nodo != null || comunidad != null) {
                    consulta += "  AND ";
                }
                consulta += "  h.HhpDivision = :HhpDivision ";
            }

            Query query = entityManager.createQuery(consulta);

            if (calle != null) {
                query.setParameter("HhpCalle", calle);
            }
            if (placa != null) {
                query.setParameter("HhpPlaca", placa);
            }

            if (apartamento != null) {
                query.setParameter("HhpApart", apartamento);
            }

            if (fechaCreacion != null) {
                query.setParameter("hhpFechaCreacionI", fechaCreacion);
                query.setParameter("hhpFechaCreacionF", new Date(fechaCreacion.getTime() + (1000 * 60 * 60 * 24)));

            }

            if (hhppId != null) {
                query.setParameter("hhpId", hhppId);

            }

            if (nodo != null) {
                query.setParameter("nodId", nodo.getNodId());

            }

            if (comunidad != null) {
                query.setParameter("HhpComunidad", comunidad);
            }

            if (division != null) {
                query.setParameter("HhpDivision", division);
            }
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");// jpa 2.0
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.error("Error al momento de consultar el HHPP. EX000: " + e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    public List<HhppMgl> findHhppByInHhppListId(List<BigDecimal> hhppList) {
        try {
            Query query = entityManager.createQuery("SELECT h FROM HhppMgl h WHERE h.hhpId in :hhpIdList");
            query.setParameter("hhpIdList", hhppList);
            return (List<HhppMgl>) query.getResultList();
        } catch (Exception e) {
            LOGGER.error("Error al momento de consultar el HHPP. EX000: " + e.getMessage(), e);
            return new ArrayList<HhppMgl>();
        }
    }

    public List<HhppMgl> findHhppByGeoId(BigDecimal gpoId) {
        try {
            Query query = entityManager.createQuery("SELECT h FROM HhppMgl h WHERE h.nodId.gpoId = :nodGpoId ");
            query.setParameter("nodGpoId", gpoId);
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.error("Error al momento de consultar el HHPP. EX000: " + e.getMessage(), e);
            return new ArrayList<HhppMgl>();
        }
    }

    public List<HhppMgl> findHhppByIdRR(String HhpIdrR) {
        try {
            Query query = entityManager.createQuery("SELECT h FROM HhppMgl h WHERE h.HhpIdrR = :HhpIdrR ");
            query.setParameter("HhpIdrR", Integer.valueOf(HhpIdrR).toString());// hay que quitar los ceros a la
                                                                               // izquierda
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.error("Error al momento de consultar el HHPP. EX000: " + e.getMessage(), e);
            return new ArrayList<HhppMgl>();
        }
    }

    /**
     * Permite busqueda en reposotorio por id RR
     *
     * @param HhpIdrR
     * @param idHhppRr
     *
     * @return HhppMgl
     */
    public HhppMgl findHhppByIdRROne(String HhpIdrR) {
        try {
            Query query = entityManager.createQuery("SELECT h FROM HhppMgl h WHERE h.HhpIdrR = :HhpIdrR ");
            query.setParameter("HhpIdrR", HhpIdrR);
            return (HhppMgl) query.getSingleResult();
        } catch (NoResultException e) {
            LOGGER.error("No fue encontrado ningún HHPP: " + e.getMessage());
            return null;
        } catch (Exception e) {
            LOGGER.error("Error al momento de consultar el HHPP. EX000: " + e.getMessage(), e);
            return null;
        }
    }

    public List<HhppMgl> findHhppBySubEdificioId(BigDecimal HhppSubEdificioId) {
        try {
            Query query = entityManager
                    .createQuery(
                            "SELECT h FROM HhppMgl h WHERE h.hhppSubEdificioObj.subEdificioId = :HhppSubEdificioId ");
            query.setParameter("HhppSubEdificioId", HhppSubEdificioId);
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.error("Error al momento de consultar el HHPP. EX000: " + e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    public Long countBySubCm(CmtSubEdificioMgl cmtSubEdificioMgl,
            FiltroConsultaHhppDto filtroConsultaHhppDto) throws ApplicationException {
        String queryString = "SELECT COUNT(1) FROM HhppMgl h WHERE h.hhppSubEdificioObj= :hhppSubEdificioObj ";

        if (cmtSubEdificioMgl == null
                || cmtSubEdificioMgl.getSubEdificioId() == null
                || cmtSubEdificioMgl.getSubEdificioId().equals(BigDecimal.ZERO)) {
            throw new ApplicationException("El filtro Sub Edificio es obligatorio");
        }
        if (filtroConsultaHhppDto.getDireccion() != null
                && !filtroConsultaHhppDto.getDireccion().isEmpty()) {
            queryString += " AND  h.SubDireccionObj.sdiFormatoIgac LIKE :direccion ";
        }
        if (filtroConsultaHhppDto.getNodo() != null
                && !filtroConsultaHhppDto.getNodo().isEmpty()) {
            queryString += " AND  h.nodId.nodCodigo LIKE :nodo ";
        }
        if (filtroConsultaHhppDto.getEstado() != null
                && !filtroConsultaHhppDto.getEstado().isEmpty()) {
            queryString += " AND  h.ehhId = :estado ";
        }
        if (filtroConsultaHhppDto.getEstrato() != null
                && !filtroConsultaHhppDto.getEstrato().isEmpty()) {
            queryString += " AND  h.SubDireccionObj.sdiEstrato = :estrato ";
        }
        if (filtroConsultaHhppDto.getApartamento() != null
                && !filtroConsultaHhppDto.getApartamento().isEmpty()) {
            queryString += " AND  h.HhpApart LIKE :apartamento ";
        }

        Query query = entityManager.createQuery(queryString);

        query.setParameter("hhppSubEdificioObj", cmtSubEdificioMgl);
        if (filtroConsultaHhppDto.getDireccion() != null && !filtroConsultaHhppDto.getDireccion().isEmpty()) {
            query.setParameter("direccion", "%" + filtroConsultaHhppDto.getDireccion() + "%");
        }
        if (filtroConsultaHhppDto.getNodo() != null
                && !filtroConsultaHhppDto.getNodo().isEmpty()) {
            query.setParameter("nodo", filtroConsultaHhppDto.getNodo() + "%");
        }
        if (filtroConsultaHhppDto.getEstado() != null
                && !filtroConsultaHhppDto.getEstado().isEmpty()) {
            query.setParameter("estado", filtroConsultaHhppDto.getEstado());
        }
        if (filtroConsultaHhppDto.getEstrato() != null
                && !filtroConsultaHhppDto.getEstrato().isEmpty()) {
            query.setParameter("estrato", new BigDecimal(filtroConsultaHhppDto.getEstrato()));
        }
        if (filtroConsultaHhppDto.getApartamento() != null
                && !filtroConsultaHhppDto.getApartamento().isEmpty()) {
            query.setParameter("apartamento", filtroConsultaHhppDto.getApartamento() + "%");
        }
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        return (Long) query.getSingleResult();
    }

    public List<HhppMgl> findPaginacionSub(int firstResult, int maxResults,
            CmtSubEdificioMgl cmtSubEdificioMgl, FiltroConsultaHhppDto filtroConsultaHhppDto)
            throws ApplicationException {
        String queryString = "SELECT h FROM HhppMgl h WHERE h.hhppSubEdificioObj= :hhppSubEdificioObj ";

        if (cmtSubEdificioMgl == null
                || cmtSubEdificioMgl.getSubEdificioId() == null
                || cmtSubEdificioMgl.getSubEdificioId().equals(BigDecimal.ZERO)) {
            throw new ApplicationException("El filtro Sub Edificio es obligatorio");
        }
        if (filtroConsultaHhppDto.getDireccion() != null
                && !filtroConsultaHhppDto.getDireccion().isEmpty()) {
            queryString += " AND  h.SubDireccionObj.sdiFormatoIgac LIKE :direccion ";
        }
        if (filtroConsultaHhppDto.getNodo() != null
                && !filtroConsultaHhppDto.getNodo().isEmpty()) {
            queryString += " AND  h.nodId.nodCodigo LIKE :nodo ";
        }
        if (filtroConsultaHhppDto.getEstado() != null
                && !filtroConsultaHhppDto.getEstado().isEmpty()) {
            queryString += " AND  h.ehhId = :estado ";
        }
        if (filtroConsultaHhppDto.getEstrato() != null
                && !filtroConsultaHhppDto.getEstrato().isEmpty()) {
            queryString += " AND  h.SubDireccionObj.sdiEstrato = :estrato ";
        }
        if (filtroConsultaHhppDto.getApartamento() != null
                && !filtroConsultaHhppDto.getApartamento().isEmpty()) {
            queryString += " AND  h.HhpApart LIKE :apartamento ";
        }

        queryString += " ORDER BY h.hhppSubEdificioObj.subEdificioId asc ";

        Query query = entityManager.createQuery(queryString);

        query.setParameter("hhppSubEdificioObj", cmtSubEdificioMgl);
        if (filtroConsultaHhppDto.getDireccion() != null
                && !filtroConsultaHhppDto.getDireccion().isEmpty()) {
            query.setParameter("direccion", "%" + filtroConsultaHhppDto.getDireccion() + "%");
        }
        if (filtroConsultaHhppDto.getNodo() != null
                && !filtroConsultaHhppDto.getNodo().isEmpty()) {
            query.setParameter("nodo", filtroConsultaHhppDto.getNodo() + "%");
        }
        if (filtroConsultaHhppDto.getEstado() != null
                && !filtroConsultaHhppDto.getEstado().isEmpty()) {
            query.setParameter("estado", filtroConsultaHhppDto.getEstado());
        }
        if (filtroConsultaHhppDto.getEstrato() != null
                && !filtroConsultaHhppDto.getEstrato().isEmpty()) {
            query.setParameter("estrato", new BigDecimal(filtroConsultaHhppDto.getEstrato()));
        }
        if (filtroConsultaHhppDto.getApartamento() != null
                && !filtroConsultaHhppDto.getApartamento().isEmpty()) {
            query.setParameter("apartamento", filtroConsultaHhppDto.getApartamento() + "%");
        }

        query.setFirstResult(firstResult);
        query.setMaxResults(maxResults);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        return query.getResultList();
    }

    public Long countByCm(CmtCuentaMatrizMgl cmtCuentaMatrizMgl, FiltroConsultaHhppDto filtroConsultaHhppDto)
            throws ApplicationException {
        String queryString = "SELECT COUNT(1) FROM HhppMgl h WHERE h.hhppSubEdificioObj.cuentaMatrizObj = :cuentaMatrizObj ";

        if (cmtCuentaMatrizMgl == null
                || cmtCuentaMatrizMgl.getCuentaMatrizId() == null
                || cmtCuentaMatrizMgl.getCuentaMatrizId().equals(BigDecimal.ZERO)) {
            throw new ApplicationException("El filtro cuenta matriz es obligatorio");
        }
        if (filtroConsultaHhppDto.getDireccion() != null
                && !filtroConsultaHhppDto.getDireccion().isEmpty()) {
            queryString += " AND  h.SubDireccionObj.sdiFormatoIgac LIKE :direccion ";
        }
        if (filtroConsultaHhppDto.getNodo() != null
                && !filtroConsultaHhppDto.getNodo().isEmpty()) {
            queryString += " AND  h.nodId.nodCodigo LIKE :nodo ";
        }
        if (filtroConsultaHhppDto.getEstado() != null
                && !filtroConsultaHhppDto.getEstado().isEmpty()) {
            queryString += " AND  h.ehhId = :estado ";
        }
        if (filtroConsultaHhppDto.getEstrato() != null
                && !filtroConsultaHhppDto.getEstrato().isEmpty()) {
            queryString += " AND  h.SubDireccionObj.sdiEstrato = :estrato ";
        }
        if (filtroConsultaHhppDto.getApartamento() != null
                && !filtroConsultaHhppDto.getApartamento().isEmpty()) {
            queryString += " AND  h.HhpApart LIKE :apartamento ";
        }

        Query query = entityManager.createQuery(queryString);

        query.setParameter("cuentaMatrizObj", cmtCuentaMatrizMgl);
        if (filtroConsultaHhppDto.getDireccion() != null
                && !filtroConsultaHhppDto.getDireccion().isEmpty()) {
            query.setParameter("direccion", "%" + filtroConsultaHhppDto.getDireccion() + "%");
        }
        if (filtroConsultaHhppDto.getNodo() != null
                && !filtroConsultaHhppDto.getNodo().isEmpty()) {
            query.setParameter("nodo", filtroConsultaHhppDto.getNodo() + "%");
        }
        if (filtroConsultaHhppDto.getEstado() != null
                && !filtroConsultaHhppDto.getEstado().isEmpty()) {
            query.setParameter("estado", filtroConsultaHhppDto.getEstado());
        }
        if (filtroConsultaHhppDto.getEstrato() != null
                && !filtroConsultaHhppDto.getEstrato().isEmpty()) {
            query.setParameter("estrato", new BigDecimal(filtroConsultaHhppDto.getEstrato()));
        }
        if (filtroConsultaHhppDto.getApartamento() != null
                && !filtroConsultaHhppDto.getApartamento().isEmpty()) {
            query.setParameter("apartamento", filtroConsultaHhppDto.getApartamento() + "%");
        }

        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        return (Long) query.getSingleResult();
    }

    public List<HhppMgl> findPaginacion(int firstResult, int maxResults, CmtCuentaMatrizMgl cmtCuentaMatrizMgl,
            FiltroConsultaHhppDto filtroConsultaHhppDto) throws ApplicationException {
        String queryString = "SELECT h FROM HhppMgl h WHERE h.hhppSubEdificioObj.cuentaMatrizObj = :cuentaMatrizObj ";

        if (cmtCuentaMatrizMgl == null
                || cmtCuentaMatrizMgl.getCuentaMatrizId() == null
                || cmtCuentaMatrizMgl.getCuentaMatrizId().equals(BigDecimal.ZERO)) {
            throw new ApplicationException("El filtro cuenta matriz es obligatorio");
        }
        if (filtroConsultaHhppDto.getDireccion() != null
                && !filtroConsultaHhppDto.getDireccion().isEmpty()) {
            queryString += " AND  h.SubDireccionObj.sdiFormatoIgac LIKE :direccion ";
        }
        if (filtroConsultaHhppDto.getNodo() != null
                && !filtroConsultaHhppDto.getNodo().isEmpty()) {
            queryString += " AND  h.nodId.nodCodigo LIKE :nodo ";
        }
        if (filtroConsultaHhppDto.getEstado() != null
                && !filtroConsultaHhppDto.getEstado().isEmpty()) {
            queryString += " AND  h.ehhId = :estado ";
        }
        if (filtroConsultaHhppDto.getEstrato() != null
                && !filtroConsultaHhppDto.getEstrato().isEmpty()) {
            queryString += " AND  h.SubDireccionObj.sdiEstrato = :estrato ";
        }
        if (filtroConsultaHhppDto.getApartamento() != null
                && !filtroConsultaHhppDto.getApartamento().isEmpty()) {
            queryString += " AND  h.HhpApart LIKE :apartamento ";
        }

        Query query = entityManager.createQuery(queryString);

        query.setParameter("cuentaMatrizObj", cmtCuentaMatrizMgl);
        if (filtroConsultaHhppDto.getDireccion() != null
                && !filtroConsultaHhppDto.getDireccion().isEmpty()) {
            query.setParameter("direccion", "%" + filtroConsultaHhppDto.getDireccion() + "%");
        }
        if (filtroConsultaHhppDto.getNodo() != null
                && !filtroConsultaHhppDto.getNodo().isEmpty()) {
            query.setParameter("nodo", filtroConsultaHhppDto.getNodo() + "%");
        }
        if (filtroConsultaHhppDto.getEstado() != null
                && !filtroConsultaHhppDto.getEstado().isEmpty()) {
            query.setParameter("estado", filtroConsultaHhppDto.getEstado());
        }
        if (filtroConsultaHhppDto.getEstrato() != null
                && !filtroConsultaHhppDto.getEstrato().isEmpty()) {
            query.setParameter("estrato", new BigDecimal(filtroConsultaHhppDto.getEstrato()));
        }
        if (filtroConsultaHhppDto.getApartamento() != null
                && !filtroConsultaHhppDto.getApartamento().isEmpty()) {
            query.setParameter("apartamento", filtroConsultaHhppDto.getApartamento() + "%");
        }
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResults);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        return (List<HhppMgl>) query.getResultList();
    }

    public HhppResponseRR getHhPpRr(String division, String comunidad,
            String aptoNum, String numeroCasa, String nombreCalle) {
        DireccionRRManager direccionRR = new DireccionRRManager(true);
        return direccionRR.getHhppRr(division, comunidad, aptoNum, numeroCasa, nombreCalle);
    }

    public List<HhppMgl> getHhppByCoordinates(String longitude,
            String latitude, int deviationMtr, int unitsNumber, BigDecimal idGeo) {
        Query query = entityManager.createNativeQuery(
                "SELECT "
                        + // " hhpp.HHP_ID," +
                        "  hhpp.DIR_ID,"
                        + "  hhpp.NOD_ID,"
                        + "  hhpp.THH_ID,"
                        + "  hhpp.SDI_ID,"
                        + "  hhpp.THC_ID,"
                        + "  hhpp.THR_ID,"
                        + "  HHPP.HHP_FECHA_CREACION,\n"
                        + "  hhpp.HHP_USUARIO_CREACION,\n"
                        + "  hhpp.HHP_FECHA_MODIFICACION,\n"
                        + "  hhpp.HHP_USUARIO_MODIFICACION,\n"
                        + "  hhpp.EHH_ID,\n"
                        + "  hhpp.HHP_ID_RR,\n"
                        + "  hhpp.HHP_CALLE,\n"
                        + "  hhpp.HHP_PLACA,\n"
                        + "  hhpp.HHP_APART,\n"
                        + "  hhpp.HHP_COMUNIDAD,\n"
                        + "  hhpp.HHP_DIVISION,\n"
                        + "  hhpp.HHP_ESTADO_UNIT,\n"
                        + "  hhpp.HHP_VENDEDOR,\n"
                        + "  hhpp.HHP_CODIGO_POSTAL\n"
                        + " FROM UBICACION_GEO ubi\n"
                        + " INNER JOIN DIRECCION dir ON dir.UBI_ID = ubi.UBI_ID\n"
                        + " INNER JOIN HHPP hhpp ON hhpp.DIR_ID  = dir.DIR_ID\n"
                        + " WHERE ? between ubi.UBI_LATITUD_INI AND ubi.UBI_LATITUD_FIN\n"
                        + " AND ? between  ubi.UBI_LONGITUD_INI AND ubi.UBI_LONGITUD_FIN\n"
                        + " AND ((6371 * acos( case when (cos((0.0174532925) * ? )\n"
                        + "                * cos((0.0174532925) * ubi.UBI_LATITUD)\n"
                        + "                * cos(((0.0174532925) * ubi.UBI_LONGITUD)  - ((0.0174532925) * ?) )\n"
                        + "                + sin((0.0174532925) * ?)\n"
                        + "                * sin((0.0174532925) * ubi.UBI_LATITUD))>1 then 0.99999 else  "
                        + "                (cos((0.0174532925) * ? )\n"
                        + "                * cos((0.0174532925) * ubi.UBI_LATITUD)\n"
                        + "                * cos(((0.0174532925) * ubi.UBI_LONGITUD)  - ((0.0174532925) * ?) )\n"
                        + "                + sin((0.0174532925) * ?)\n"
                        + "                * sin((0.0174532925) * ubi.UBI_LATITUD)) end \n"
                        + "                )) < ?) and ubi.GPO_ID=?");

        query.setParameter(1, Double.parseDouble(latitude));
        query.setParameter(2, Double.parseDouble(longitude));
        query.setParameter(3, Double.parseDouble(latitude));
        query.setParameter(4, Double.parseDouble(longitude));
        query.setParameter(5, Double.parseDouble(latitude));
        query.setParameter(6, Double.parseDouble(latitude));
        query.setParameter(7, Double.parseDouble(longitude));
        query.setParameter(8, Double.parseDouble(latitude));
        query.setParameter(9, deviationMtr);
        query.setParameter(10, idGeo);
        query.setMaxResults(unitsNumber);
        return query.getResultList();
    }

    public List<HhppAuditoriaMgl> findHhppAuditoriaByIdHhppList(HhppMgl hhpp) {
        try {
            Query query = entityManager.createQuery(
                    "Select hhpp From HhppAuditoriaMgl hhpp where hhpp.hhpIdObj=:hhpp order By hhpp.hhpAuditoriaId DESC");
            query.setParameter("hhpp", hhpp);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.error("Error al momento de consultar el HHPP. EX000: " + e.getMessage(), e);
            return null;
        }
    }

    /**
     * valbuenayf Metodo para buscar los hhpp de un id direccion
     *
     * @param idDireccion
     *
     * @return
     */
    public List<HhppMgl> findHhppDireccion(BigDecimal idDireccion) {
        try {
            Query query = entityManager.createNamedQuery("HhppMgl.findHhppDireccion");
            query.setParameter("dirId", idDireccion);
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.error("Error al momento de consultar el HHPP. EX000: " + e.getMessage(), e);
            return null;
        }
    }

    /**
     * valbuenayf Metodo para buscar los hhpp de un id direccion
     *
     * @param idSubDireccion
     *
     * @return
     */
    public List<HhppMgl> findHhppSubDireccion(BigDecimal idSubDireccion) {
        try {
            Query query = entityManager.createNamedQuery("HhppMgl.findHhppSubDireccion");
            query.setParameter("sdiId", idSubDireccion);
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.error("Error al momento de consultar el HHPP. EX000: " + e.getMessage(), e);
            return null;
        }
    }
    
        /**
     * valbuenayf Metodo para buscar los hhpp de un id direccion
     * @param HhpCalle
     * @param HhpPlaca
     * @param HhpComunidad
     * @return
     */
    public List<HhppMgl> findHhppNap(String HhpCalle, String HhpPlaca,String HhpComunidad) {
        try {
             Query query = entityManager.createQuery(
                    "SELECT h FROM HhppMgl h WHERE h.HhpCalle = :calleRR AND h.HhpPlaca LIKE :placaRR AND h.HhpComunidad = :HhpComunidad AND h.estadoRegistro = 1 AND h.nap IS NOT NULL");
            query.setParameter("calleRR", HhpCalle);
            query.setParameter("placaRR", HhpPlaca);
            query.setParameter("HhpComunidad", HhpComunidad);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.error("Error al momento de consultar el HHPP. EX000: " + e.getMessage(), e);
            return null;
        }
    }

    public List<BigDecimal> findListHhppSubPaginacion(FiltroBusquedaDirecccionDto filtroBusquedaDirecccionDto,
            int firstResult,
            int maxResults, CmtSubEdificioMgl cmtSubEdificioMgl)
            throws ApplicationException {

        String queryString1 = "select d.direccion_detallada_id from " + Constant.MGL_DATABASE_SCHEMA + "."
                + "mgl_direccion_detallada d where d.direccion_id "
                + "in ( "
                + "SELECT h.direccion_id FROM  " + Constant.MGL_DATABASE_SCHEMA + "." + "tec_tecnologia_habilitada h, "
                + Constant.MGL_DATABASE_SCHEMA + "." + "cmt_tecnologia_sub t "
                + "where  h.sub_direccion_id IS NULL "
                + "and t.tecno_subedificio_id = h.tecno_subedificio_id AND t.subedificio_id in "
                + "(select e.subedificio_id from " + Constant.MGL_DATABASE_SCHEMA + "."
                + "cmt_subedificio e where e.subedificio_id = " + cmtSubEdificioMgl.getSubEdificioId() + ")) "
                + "and d.sub_direccion_id is null "
                + "union "
                + "select  d.direccion_detallada_id from " + Constant.MGL_DATABASE_SCHEMA + "."
                + "mgl_direccion_detallada d where d.sub_direccion_id "
                + "in ("
                + "SELECT c.sub_direccion_id FROM  " + Constant.MGL_DATABASE_SCHEMA + "." + "mgl_sub_direccion c, "
                + "" + Constant.MGL_DATABASE_SCHEMA + "." + "tec_tecnologia_habilitada h, "
                + Constant.MGL_DATABASE_SCHEMA + "."
                + "cmt_tecnologia_sub t where  h.sub_direccion_id = c.sub_direccion_id and "
                + "t.tecno_subedificio_id = h.tecno_subedificio_id AND h.subedificio_id in (select e.subedificio_id from "
                + Constant.MGL_DATABASE_SCHEMA + "." + "cmt_subedificio e where "
                + "e.subedificio_id = " + cmtSubEdificioMgl.getSubEdificioId() + ")) ";

        if (cmtSubEdificioMgl == null
                || cmtSubEdificioMgl.getSubEdificioId() == null
                || cmtSubEdificioMgl.getSubEdificioId().equals(BigDecimal.ZERO)) {
            throw new ApplicationException("El filtro Sub Edificio es obligatorio");
        }

        if (filtroBusquedaDirecccionDto.getDireccion() != null
                && !filtroBusquedaDirecccionDto.getDireccion().isEmpty()) {
            queryString1 += " AND  d.direccionTexto LIKE :direccion ";
        }

        Query query1 = entityManager.createNativeQuery(queryString1);
        query1.setFirstResult(firstResult);
        query1.setMaxResults(maxResults);
        if (filtroBusquedaDirecccionDto.getDireccion() != null
                && !filtroBusquedaDirecccionDto.getDireccion().isEmpty()) {
            query1.setParameter("direccion", "%" + filtroBusquedaDirecccionDto.getDireccion().toUpperCase() + "%");
        }

        List<BigDecimal> listahhpp1 = (List<BigDecimal>) query1.getResultList();
        Collections.sort(listahhpp1, new Comparator<BigDecimal>() {
            @Override
            public int compare(BigDecimal o1, BigDecimal o2) {
                if (o1 == null) {
                    return (o2 == null) ? 0 : -1;
                }
                if (o2 == null) {
                    return 1;
                }
                return o1.compareTo(o2);
            }
        });

        return listahhpp1;
    }

    public List<BigDecimal> findListHhppCMPaginacion(FiltroBusquedaDirecccionDto filtroBusquedaDirecccionDto,
            int firstResult, int maxResults, CmtSubEdificioMgl cmtSubEdificioMgl) throws ApplicationException {
        if (cmtSubEdificioMgl == null
                || cmtSubEdificioMgl.getCmtCuentaMatrizMglObj() == null
                || cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getCuentaMatrizId().equals(BigDecimal.ZERO)) {
            throw new ApplicationException("El filtro Sub Edificio es obligatorio");

        }
        String queryString1 = "select d.direccion_detallada_id from " + Constant.MGL_DATABASE_SCHEMA + "."
                + "mgl_direccion_detallada d where d.direccion_id "
                + "in ( "
                + "SELECT h.direccion_id FROM  " + Constant.MGL_DATABASE_SCHEMA + "." + "tec_tecnologia_habilitada h, "
                + Constant.MGL_DATABASE_SCHEMA + "." + "cmt_tecnologia_sub t "
                + "where  h.sub_direccion_id IS NULL "
                + "and t.tecno_subedificio_id = h.tecno_subedificio_id AND t.subedificio_id in "
                + "(select e.subedificio_id from " + Constant.MGL_DATABASE_SCHEMA + "."
                + "cmt_subedificio e where e.cuentamatriz_id = "
                + cmtSubEdificioMgl.getCuentaMatrizObj().getCuentaMatrizId() + ")) "
                + "and d.sub_direccion_id is null "
                + "union "
                + "select  d.direccion_detallada_id from " + Constant.MGL_DATABASE_SCHEMA + "."
                + "mgl_direccion_detallada d where d.sub_direccion_id "
                + "in ("
                + "SELECT c.sub_direccion_id FROM  " + Constant.MGL_DATABASE_SCHEMA + "." + "mgl_sub_direccion c, "
                + "" + Constant.MGL_DATABASE_SCHEMA + "." + "tec_tecnologia_habilitada h, "
                + Constant.MGL_DATABASE_SCHEMA + "."
                + "cmt_tecnologia_sub t where  h.sub_direccion_id = c.sub_direccion_id and \n"
                + "t.tecno_subedificio_id = h.tecno_subedificio_id AND h.subedificio_id in (select e.subedificio_id from "
                + Constant.MGL_DATABASE_SCHEMA + "." + "cmt_subedificio e where \n"
                + "e.cuentamatriz_id = " + cmtSubEdificioMgl.getCuentaMatrizObj().getCuentaMatrizId() + ")) ";

        if (filtroBusquedaDirecccionDto.getDireccion() != null
                && !filtroBusquedaDirecccionDto.getDireccion().isEmpty()) {
            queryString1 += " AND  d.direccionTexto LIKE :direccion ";
        }

        Query query1 = entityManager.createNativeQuery(queryString1);
        if (filtroBusquedaDirecccionDto.getDireccion() != null
                && !filtroBusquedaDirecccionDto.getDireccion().isEmpty()) {
            query1.setParameter("direccion", "%" + filtroBusquedaDirecccionDto.getDireccion().toUpperCase() + "%");
        }
        List<BigDecimal> listahhpp1 = query1.getResultList();
        return listahhpp1;

    }

    public int countListHhppSubEdif(FiltroBusquedaDirecccionDto filtroBusquedaDirecccionDto,
            CmtSubEdificioMgl cmtSubEdificioMgl)
            throws ApplicationException {

        if (cmtSubEdificioMgl == null
                || cmtSubEdificioMgl.getSubEdificioId() == null
                || cmtSubEdificioMgl.getSubEdificioId().equals(BigDecimal.ZERO)) {
            throw new ApplicationException("El filtro Sub Edificio es obligatorio");
        }

        String queryString1 = "select  d from CmtDireccionDetalladaMgl d where d.subDireccion.sdiId in "
                + "(SELECT c.sdiId FROM  SubDireccionMgl c, "
                + "HhppMgl h, CmtTecnologiaSubMgl t where  h.SubDireccionObj.sdiId = c.sdiId and "
                + "t.tecnoSubedificioId = h.cmtTecnologiaSubId.tecnoSubedificioId AND h.hhppSubEdificioObj.subEdificioId in "
                + "(select e.subEdificioId from CmtSubEdificioMgl e where "
                + "e.subEdificioId = " + cmtSubEdificioMgl.getSubEdificioId() + "))";

        String queryString2 = "select d from CmtDireccionDetalladaMgl d where d.direccion.dirId in ( "
                + "SELECT h.direccionObj.dirId FROM  HhppMgl h, CmtTecnologiaSubMgl t "
                + "where  h.SubDireccionObj is null "
                + "AND t.tecnoSubedificioId = h.cmtTecnologiaSubId.tecnoSubedificioId "
                + "AND h.hhppSubEdificioObj.subEdificioId in "
                + "(select e.subEdificioId from CmtSubEdificioMgl e where "
                + "e.subEdificioId = " + cmtSubEdificioMgl.getSubEdificioId() + ")) and d.subDireccion is null";

        if (filtroBusquedaDirecccionDto != null && filtroBusquedaDirecccionDto.getDireccion() != null
                && !filtroBusquedaDirecccionDto.getDireccion().equalsIgnoreCase("")) {
            queryString1 += " AND  d.direccionTexto LIKE :direccion ";
        }
        if (filtroBusquedaDirecccionDto != null && filtroBusquedaDirecccionDto.getDireccion() != null
                && !filtroBusquedaDirecccionDto.getDireccion().equalsIgnoreCase("")) {
            queryString2 += " AND  d.direccionTexto LIKE :direccion ";
        }

        Query query1 = entityManager.createQuery(queryString1);
        Query query2 = entityManager.createQuery(queryString2);
        if (filtroBusquedaDirecccionDto != null && filtroBusquedaDirecccionDto.getDireccion() != null
                && !filtroBusquedaDirecccionDto.getDireccion().equalsIgnoreCase("")) {
            query1.setParameter("direccion", "%" + filtroBusquedaDirecccionDto.getDireccion().toUpperCase() + "%");
        }
        if (filtroBusquedaDirecccionDto != null && filtroBusquedaDirecccionDto.getDireccion() != null
                && !filtroBusquedaDirecccionDto.getDireccion().equalsIgnoreCase("")) {
            query2.setParameter("direccion", "%" + filtroBusquedaDirecccionDto.getDireccion().toUpperCase() + "%");
        }
        LinkedHashSet<CmtDireccionDetalladaMgl> result = new LinkedHashSet<>();
        List<CmtDireccionDetalladaMgl> listahhpp1 = (List<CmtDireccionDetalladaMgl>) query1.getResultList();
        List<CmtDireccionDetalladaMgl> listahhpp2 = (List<CmtDireccionDetalladaMgl>) query2.getResultList();
        result.addAll(listahhpp1);
        result.addAll(listahhpp2);
        List<CmtDireccionDetalladaMgl> list = new ArrayList<>(result);
        return list.isEmpty() ? 0 : list.size();
    }

    public int countListHhppCM(FiltroBusquedaDirecccionDto filtroBusquedaDirecccionDto,
            CmtSubEdificioMgl cmtSubEdificioMgl)
            throws ApplicationException {

        if (cmtSubEdificioMgl == null
                || cmtSubEdificioMgl.getSubEdificioId() == null
                || cmtSubEdificioMgl.getSubEdificioId().equals(BigDecimal.ZERO)) {
            throw new ApplicationException("El filtro Sub Edificio es obligatorio");
        }
        String queryString1 = "select d from CmtDireccionDetalladaMgl d where d.subDireccion.sdiId in "
                + "(SELECT c.sdiId FROM  SubDireccionMgl c, "
                + "HhppMgl h, CmtTecnologiaSubMgl t where  h.SubDireccionObj.sdiId = c.sdiId and "
                + "t.tecnoSubedificioId = h.cmtTecnologiaSubId.tecnoSubedificioId AND h.hhppSubEdificioObj.subEdificioId in "
                + "(select e.subEdificioId from CmtSubEdificioMgl e where "
                + "e.cuentaMatrizObj.cuentaMatrizId = "
                + cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getCuentaMatrizId() + ")) ";

        String queryString2 = "select d from CmtDireccionDetalladaMgl d where d.direccion.dirId in ( "
                + "SELECT h.direccionObj.dirId FROM  HhppMgl h, CmtTecnologiaSubMgl t "
                + "where  h.SubDireccionObj is null "
                + "AND t.tecnoSubedificioId = h.cmtTecnologiaSubId.tecnoSubedificioId "
                + "AND h.hhppSubEdificioObj.subEdificioId in "
                + "(select e.subEdificioId from CmtSubEdificioMgl e where "
                + "e.cuentaMatrizObj.cuentaMatrizId = "
                + cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getCuentaMatrizId() + ")) and d.subDireccion is null";

        if (filtroBusquedaDirecccionDto != null && filtroBusquedaDirecccionDto.getDireccion() != null
                && !filtroBusquedaDirecccionDto.getDireccion().equalsIgnoreCase("")) {
            queryString1 += " AND  d.direccionTexto LIKE :direccion ";
        }
        if (filtroBusquedaDirecccionDto != null && filtroBusquedaDirecccionDto.getDireccion() != null
                && !filtroBusquedaDirecccionDto.getDireccion().equalsIgnoreCase("")) {
            queryString2 += " AND  d.direccionTexto LIKE :direccion ";
        }

        Query query1 = entityManager.createQuery(queryString1);
        Query query2 = entityManager.createQuery(queryString2);
        LinkedHashSet<CmtDireccionDetalladaMgl> result = new LinkedHashSet<CmtDireccionDetalladaMgl>();
        if (filtroBusquedaDirecccionDto != null && filtroBusquedaDirecccionDto.getDireccion() != null
                && !filtroBusquedaDirecccionDto.getDireccion().equalsIgnoreCase("")) {
            query1.setParameter("direccion", "%" + filtroBusquedaDirecccionDto.getDireccion().toUpperCase() + "%");
        }
        if (filtroBusquedaDirecccionDto != null && filtroBusquedaDirecccionDto.getDireccion() != null
                && !filtroBusquedaDirecccionDto.getDireccion().equalsIgnoreCase("")) {
            query2.setParameter("direccion", "%" + filtroBusquedaDirecccionDto.getDireccion().toUpperCase() + "%");
        }
        List<CmtDireccionDetalladaMgl> listahhpp1 = (List<CmtDireccionDetalladaMgl>) query1.getResultList();
        List<CmtDireccionDetalladaMgl> listahhpp2 = (List<CmtDireccionDetalladaMgl>) query2.getResultList();
        result.addAll(listahhpp1);
        result.addAll(listahhpp2);
        List<CmtDireccionDetalladaMgl> list = new ArrayList<CmtDireccionDetalladaMgl>(result);
        return list.isEmpty() ? 0 : list.size();

    }

    public List<SubDireccionMgl> findListHhppSub(CmtSubEdificioMgl cmtSubEdificioMgl)
            throws ApplicationException {
        String queryString = "select s from SubDireccionMgl s where s.sdiId in ("
                + "select h.SubDireccionObj.sdiId from HhppMgl h , CmtTecnologiaSubMgl t where  h.cmtTecnologiaSubId.tecnoSubedificioId =  t.tecnoSubedificioId "
                + "and h.SubDireccionObj.sdiId  IS NOT NULL  and  "
                + "h.hhppSubEdificioObj.subEdificioId in (select e.subEdificioId from CmtSubEdificioMgl e "
                + "where e.subEdificioId  = :hhppSubEdificioObj ))";

        if (cmtSubEdificioMgl == null
                || cmtSubEdificioMgl.getSubEdificioId() == null
                || cmtSubEdificioMgl.getSubEdificioId().equals(BigDecimal.ZERO)) {
            throw new ApplicationException("El filtro Sub Edificio es obligatorio");
        }

        queryString += " ORDER BY  s.sdiFormatoIgac asc ";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("hhppSubEdificioObj", cmtSubEdificioMgl.getSubEdificioId());
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        return (List<SubDireccionMgl>) query.getResultList();
    }

    public List<SubDireccionMgl> findListHhppCM(CmtSubEdificioMgl cmtSubEdificioMgl)
            throws ApplicationException {

        String queryString = "select s from SubDireccionMgl s where s.sdiId in ("
                + "select m.SubDireccionObj.sdiId from HhppMgl m , CmtTecnologiaSubMgl t where  m.cmtTecnologiaSubId.tecnoSubedificioId =  t.tecnoSubedificioId "
                + "and m.SubDireccionObj.sdiId  IS NOT NULL  and  "
                + "m.hhppSubEdificioObj.subEdificioId in ( select e.subEdificioId from CmtSubEdificioMgl e "
                + "where e.cuentaMatrizObj  = :cuentaMatrizObj)) ";

        if (cmtSubEdificioMgl == null
                || cmtSubEdificioMgl.getCmtCuentaMatrizMglObj() == null
                || cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getCuentaMatrizId().equals(BigDecimal.ZERO)) {
            throw new ApplicationException("El filtro Sub Edificio es obligatorio");
        }

        queryString += " ORDER BY s.sdiFormatoIgac asc ";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("cuentaMatrizObj", cmtSubEdificioMgl.getCmtCuentaMatrizMglObj());
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        return (List<SubDireccionMgl>) query.getResultList();
    }

    /**
     * Consulta de hhpp existentes por cuenta matriz
     *
     * @param cuentaMatrizMgl {@link CmtCuentaMatrizMgl} cuenta matriz a
     *                        consultar
     *
     * @return {@link Integer} Cantidad de registros encontrados
     */
    public Integer cantidadHhppCuentaMatriz(CmtCuentaMatrizMgl cuentaMatrizMgl) {
        try {
            StringBuilder consulta = new StringBuilder();
            consulta.append("SELECT COUNT(DISTINCT HHPP.hhpId) ")
                    .append("FROM HhppMgl HHPP ")
                    .append("  JOIN HHPP.hhppSubEdificioObj SUB ")
                    .append("WHERE SUB.cuentaMatrizObj.cuentaMatrizId = :cuentaMatrizId");
            return ((Long) entityManager
                    .createQuery(consulta.toString())
                    .setParameter("cuentaMatrizId", cuentaMatrizMgl.getCuentaMatrizId())
                    .getSingleResult()).intValue();
        } catch (PersistenceException e) {
            LOGGER.error("Error al momento de consultar la cantidad de HHPP. EX000: " + e.getMessage(), e);
            return 0;
        }
    }

    /**
     * Consulta de n&uacute;mero de HHPP activos
     *
     * @param cuentaMatrizMgl {@link CmtCuentaMatrizMgl} Cuenta matriz a
     *                        verificar
     *
     * @return {@link Integer} Cantidad de registros activos encontrados
     *
     * @throws ApplicationException Error lanzado al realizar consultas
     */
    public Integer cantidadHhppEnServicio(CmtCuentaMatrizMgl cuentaMatrizMgl) throws ApplicationException {
        try {
            StringBuilder consulta = new StringBuilder();
            consulta.append("SELECT COUNT(DISTINCT h.hhpId)")
                    .append(" FROM HhppMgl h")
                    .append("   JOIN h.hhppSubEdificioObj sub")
                    .append(" WHERE sub.cuentaMatrizObj.cuentaMatrizId = :idCuentaMatriz")
                    .append("   AND h.HhpEstadoUnit IN ('SU', 'CS', 'DF')"); // 'D','S','M'
            return ((Long) entityManager
                    .createQuery(consulta.toString())
                    .setParameter("idCuentaMatriz", cuentaMatrizMgl.getCuentaMatrizId())
                    .setHint("javax.persistence.cache.storeMode", "REFRESH")
                    .getSingleResult()).intValue();
        } catch (PersistenceException e) {
            LOGGER.error("Error consultando registros de hhpp.", e);
            throw new ApplicationException(e);
        }
    }

    public List<HhppMgl> obtenerHhppCM(CmtCuentaMatrizMgl cuentaMatriz) throws ApplicationException {
        try {
            StringBuilder consulta = new StringBuilder();
            consulta.append("SELECT h")
                    .append(" FROM HhppMgl h")
                    .append("   JOIN h.hhppSubEdificioObj sub")
                    .append(" WHERE sub.cuentaMatrizObj.cuentaMatrizId = :idCuentaMatriz");
            return entityManager.createQuery(consulta.toString())
                    .setParameter("idCuentaMatriz", cuentaMatriz.getCuentaMatrizId())
                    .setHint("javax.persistence.cache.storeMode", "REFRESH")
                    .getResultList();
        } catch (PersistenceException e) {
            LOGGER.error("Error consultando los hhpp por CM", e);
            throw new ApplicationException(e);
        }
    }

    @Override
    public boolean deleteCm(HhppMgl hhpp, String usuario, int perfil) throws ApplicationException {
        try {
            if (usuario.equals("") || perfil == 0) {
                throw new ApplicationException("El Usuario o perfil Nopueden ser nulos");
            }
            hhpp.setFechaEdicion(new Date());
            hhpp.setUsuarioEdicion(usuario);
            hhpp.setEstadoRegistro(0);
            entityManager.getTransaction().begin();
            this.entityManager.merge(hhpp);
            entityManager.getTransaction().commit();
            return true;
        } catch (ApplicationException e) {
            LOGGER.error(e.getMessage());
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    public List<HhppMgl> findHhppByDireccion(String direccion, BigDecimal gpo,
            String suscriptor, int firstResult, int maxResults) {
        try {
            List<HhppMgl> resultList;
            StringBuilder sql = new StringBuilder();

            sql.append("SELECT h FROM HhppMgl h ");
            sql.append("WHERE ");
            if (direccion != null && !direccion.isEmpty()
                    && suscriptor != null && !suscriptor.isEmpty()) {
                sql.append("h.direccionObj.dirFormatoIgac like '%"
                        + "TV 28 J 70 68 AP 101 PI 1 EL DIAMANTE" + "%' AND ");
                sql.append(" h.direccionObj.ubicacion.ubiId =:gpo AND ");
                sql.append(" h.suscriptor =:suscriptor");
            } else {
                if (direccion != null && !direccion.isEmpty()) {
                    sql.append("h.direccionObj.dirFormatoIgac like '%"
                            + "TV 28 J 70 68 AP 101 PI 1 EL DIAMANTE" + "%' AND ");
                    sql.append(" h.direccionObj.ubicacion.ubiId =:gpo ");
                } else {
                    if (suscriptor != null && !suscriptor.isEmpty()) {
                        sql.append(" h.suscriptor =:suscriptor");
                    }
                }
            }
            Query query = entityManager.createQuery(sql.toString());
            query.setFirstResult(firstResult);
            query.setMaxResults(maxResults);

            if (direccion != null && !direccion.isEmpty()) {
                query.setParameter("gpo", gpo);
            }
            if (suscriptor != null && !suscriptor.isEmpty()) {
                query.setParameter("suscriptor", suscriptor);
            }

            resultList = (List<HhppMgl>) query.getResultList();
            return resultList;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            return null;
        }
    }

    public int countHhppByDireccion(String direccion, BigDecimal gpo,
            String suscriptor) {
        try {

            StringBuilder sql = new StringBuilder();

            sql.append("SELECT count(1) FROM HhppMgl h ");
            sql.append("WHERE ");
            if (direccion != null && !direccion.isEmpty()
                    && suscriptor != null && !suscriptor.isEmpty()) {
                sql.append("h.direccionObj.dirFormatoIgac like '%"
                        + "TV 28 J 70 68 AP 101 PI 1 EL DIAMANTE" + "%' AND ");
                sql.append(" h.direccionObj.ubicacion.ubiId =:gpo AND ");
                sql.append(" h.suscriptor =:suscriptor");
            } else {
                if (direccion != null && !direccion.isEmpty()) {
                    sql.append("h.direccionObj.dirFormatoIgac like '%"
                            + "TV 28 J 70 68 AP 101 PI 1 EL DIAMANTE" + "%' AND ");
                    sql.append(" h.direccionObj.ubicacion.ubiId =:gpo ");
                } else {
                    if (suscriptor != null && !suscriptor.isEmpty()) {
                        sql.append(" h.suscriptor =:suscriptor");
                    }
                }
            }
            Query query = entityManager.createQuery(sql.toString());

            if (direccion != null && !direccion.isEmpty()) {
                query.setParameter("gpo", gpo);
            }
            if (suscriptor != null && !suscriptor.isEmpty()) {
                query.setParameter("suscriptor", suscriptor);
            }

            int result = query.getSingleResult() == null ? 0
                    : ((Long) query.getSingleResult()).intValue();
            return result;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            return 0;
        }
    }

    public List<HhppMgl> findByNodoMgl(NodoMgl nodoMgl) throws ApplicationException {
        try {
            if (!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().begin();
            }
            entityManager.clear();
            entityManager.flush();
            String sql = "SELECT s FROM HhppMgl s WHERE s.nodId=:nodoId";
            TypedQuery<HhppMgl> query = entityManager.createQuery(sql, HhppMgl.class);
            query.setParameter("nodoId", nodoMgl);
            LOGGER.error("nodo " + nodoMgl.getNodId());
            List<HhppMgl> lista = query.getResultList();
            entityManager.getTransaction().commit();
            return lista;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }

    }

    /**
     * Cuenta la cantidad de hhpp.
     *
     * @param gpoIdCiudad        - id de la ciudad
     * @param gpoIdCentroPoblado - id del centro poblado
     * @param idNodo             - id del nodo
     * @param idTipoTecnologia   - id del tipo de tecnología
     * @param atributo           - atributo
     * @param valorAtributo      - valor del atributo
     * @param fechaInicial       - fecha de inicio de la creación
     * @param fechaFin           - fecha final de la creación
     * @param idHhpp             - id del Hhpp
     * @return un entero con el valor encontrado
     * @throws ApplicationException el error manifestado al momento de
     *                              consultar.
     */
    public int countHhpp(BigDecimal gpoIdCiudad, BigDecimal gpoIdCentroPoblado,
            BigDecimal idNodo, BigDecimal idTipoTecnologia,
            String atributo, String valorAtributo, Date fechaInicial, Date fechaFin,
            BigDecimal idHhpp, BigDecimal etiqueta)
            throws ApplicationException {

        try {

            if (!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().begin();
            }
            entityManager.clear();
            entityManager.flush();

            String sqlHhpp = sqlQuery(gpoIdCiudad, gpoIdCentroPoblado,
                    idNodo, idTipoTecnologia, atributo, valorAtributo,
                    fechaInicial, fechaFin, true, idHhpp, etiqueta);

            Query query = entityManager.createNativeQuery(sqlHhpp);

            int cant = ((Number) query.getSingleResult()).intValue();
            entityManager.getTransaction().commit();
            return cant;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Creación de la consulta para busqueda de hhpp.
     *
     * @author becerraarmr
     * @param gpoIdCiudad        - id de la ciudad
     * @param gpoIdCentroPoblado - id del centro poblado
     * @param idNodo             - id del nodo
     * @param idTipoTecnologia   - id del tipo de tecnología
     * @param atributo           - atributo
     * @param valorAtributo      - valor del atributo
     * @param fechaInicial       - fecha de inicio de la creación
     * @param fechaFin           - fecha final de la creación
     * @param esConteo           - establece si el sql para buscar es de conteo.
     * @return un String con el sql representativo.
     */
    private String sqlQuery(
            BigDecimal gpoIdCiudad,
            BigDecimal gpoIdCentroPoblado,
            BigDecimal idNodo,
            BigDecimal idTipoTecnologia,
            String atributo,
            String valorAtributo,
            Date fechaInicial,
            Date fechaFin,
            boolean esConteo,
            BigDecimal idHhpp,
            BigDecimal etiqueta) {

        CmtBasicaMglDaoImpl cmtBasicaMglDaoImpl = new CmtBasicaMglDaoImpl();

        CmtBasicaMgl basicaEstado = cmtBasicaMglDaoImpl.findBasicaCodigo(Constant.CODIGO_BASICA_ESTADO,
                Constant.NOMBRE_TIPO_ATRIBUTOS_HHPP);
        CmtBasicaMgl basicaEstrato = cmtBasicaMglDaoImpl.findBasicaCodigo(Constant.CODIGO_BASICA_ESTRATO,
                Constant.NOMBRE_TIPO_ATRIBUTOS_HHPP);
        CmtBasicaMgl basicaCobertura = cmtBasicaMglDaoImpl.findBasicaCodigo(Constant.CODIGO_BASICA_COBERTURA,
                Constant.NOMBRE_TIPO_ATRIBUTOS_HHPP);
        CmtBasicaMgl basicaBariio = cmtBasicaMglDaoImpl.findBasicaCodigo(Constant.CODIGO_BASICA_BARRIO,
                Constant.NOMBRE_TIPO_ATRIBUTOS_HHPP);
        CmtBasicaMgl basicaDireccion = cmtBasicaMglDaoImpl.findBasicaCodigo(Constant.CODIGO_BASICA_DIRECCION,
                Constant.NOMBRE_TIPO_ATRIBUTOS_HHPP);
        CmtBasicaMgl basicaTipoVivienda = cmtBasicaMglDaoImpl.findBasicaCodigo(Constant.CODIGO_BASICA_TIPO_VIVIENDA,
                Constant.NOMBRE_TIPO_ATRIBUTOS_HHPP);

        String sqlHhpp = "SELECT h.* ";

        if (esConteo) {
            sqlHhpp = "SELECT count(h.tecnologia_habilitada_id) ";
        }

        sqlHhpp += " FROM ((" + Constant.MGL_DATABASE_SCHEMA + "." + "tec_tecnologia_habilitada h INNER JOIN "
                + Constant.MGL_DATABASE_SCHEMA + "." + "tec_nodo n ON h.nodo_id = n.nodo_id) "
                + " INNER JOIN " + Constant.MGL_DATABASE_SCHEMA + "."
                + "mgl_geografico_politico g ON g.geografico_politico_id = n.geografico_politico_id) ";

        if (etiqueta != null) {
            sqlHhpp += " INNER JOIN " + Constant.MGL_DATABASE_SCHEMA + "."
                    + "tec_marcas_tecnologia_hab m ON h.tecnologia_habilitada_id = m.tecnologia_habilitada_id  ";
        }

        sqlHhpp += " WHERE h.estado_registro = 1 AND n.tipo='" + idTipoTecnologia + "'";
        if (basicaDireccion != null && basicaDireccion.getNombreBasica() != null
                && basicaDireccion.getNombreBasica().equalsIgnoreCase(atributo)) {
            sqlHhpp += " AND h.subedificio_id is null ";
        }

        if (gpoIdCentroPoblado != null) {
            sqlHhpp += " AND g.geografico_politico_id='" + gpoIdCentroPoblado + "'";
        } else if (gpoIdCiudad != null) {
            sqlHhpp += " AND g.geografico_politico_id IN (SELECT gp1.geografico_politico_id "
                    + " FROM " + Constant.MGL_DATABASE_SCHEMA + "." + "mgl_geografico_politico gp1 "
                    + " WHERE gp1.geografico_geo_politico_id='" + gpoIdCiudad + "')";
        }
        if (idNodo != null) {
            sqlHhpp += " AND n.nodo_id='" + idNodo + "'";
        }
        if (idHhpp != null) {
            sqlHhpp += " AND h.tecnologia_habilitada_id='" + idHhpp + "'";
        }
        if (etiqueta != null) {
            sqlHhpp += " AND m.marcas_id='" + etiqueta + "'";
        }
        if (idHhpp == null) {
            valorAtributo = valorAtributo != null ? valorAtributo.toUpperCase() : "";
            if (!valorAtributo.isEmpty()) {
                if (basicaEstado != null && basicaEstado.getNombreBasica() != null
                        && basicaEstado.getNombreBasica().equalsIgnoreCase(atributo)) {
                    sqlHhpp += " AND h.estado_unit='" + valorAtributo + "'";
                } else if (basicaEstrato != null && basicaEstrato.getNombreBasica() != null
                        && basicaEstrato.getNombreBasica().equalsIgnoreCase(atributo)) {
                    Integer valor;
                    String sqlEstrato;
                    try {
                        valor = Integer.valueOf(valorAtributo);
                        sqlEstrato = "and ((CASE WHEN (h.SUB_DIRECCION_ID IS NULL)"
                                + " THEN (select estrato from " + Constant.MGL_DATABASE_SCHEMA + "."
                                + "mgl_direccion d where d.direccion_id = h.direccion_id)"
                                + " ELSE (select estrato from " + Constant.MGL_DATABASE_SCHEMA + "."
                                + "mgl_sub_direccion d where d.SUB_DIRECCION_ID = "
                                + "h.SUB_DIRECCION_ID) END) =" + valorAtributo + ")";

                    } catch (NumberFormatException e) {
                        sqlEstrato = "";
                    }
                    sqlHhpp += sqlEstrato;
                } else if (basicaCobertura != null && basicaCobertura.getNombreBasica() != null
                        && basicaCobertura.getNombreBasica().equalsIgnoreCase(atributo)) {

                } else if (basicaDireccion != null && basicaDireccion.getNombreBasica() != null
                        && basicaDireccion.getNombreBasica().equalsIgnoreCase(atributo)) {
                    valorAtributo = "%" + valorAtributo + "%";
                    sqlHhpp += " AND h.direccion_id in (select d.direccion_id from " + Constant.MGL_DATABASE_SCHEMA
                            + "." + "mgl_direccion d "
                            + " where d.formato_igac like '" + valorAtributo + "')";
                } else if (basicaBariio != null && basicaBariio.getNombreBasica() != null
                        && basicaBariio.getNombreBasica().equalsIgnoreCase(atributo)) {
                    valorAtributo = valorAtributo + "%";
                    sqlHhpp += " AND h.direccion_id in(select d1.direccion_id from ((" + Constant.MGL_DATABASE_SCHEMA
                            + "." + "mgl_direccion d1 "
                            + " inner join " + Constant.MGL_DATABASE_SCHEMA + "."
                            + "mgl_ubicacion u1 on d1.ubicacion_id = u1.ubicacion_id) "
                            + " inner join " + Constant.MGL_DATABASE_SCHEMA + "."
                            + "mgl_geografico g1 on g1.geografico_id = u1.geografico_politico_id) "
                            + " where g1.tipo_geografico_id = 2 and g1.nombre like '" + valorAtributo + "')";
                } else if (basicaTipoVivienda != null && basicaTipoVivienda.getNombreBasica() != null
                        && basicaTipoVivienda.getNombreBasica().equalsIgnoreCase(atributo)) {
                    String sqlTipoV = " AND h.TIPO_TECNOLOGIA_HAB_ID = '" + valorAtributo + "' ";

                    sqlHhpp += sqlTipoV;
                }
            }
        }

        // Se valida si son la misma fecha la fecha inicial y la fecha final
        if ((fechaInicial != null && fechaFin != null) && (fechaInicial.compareTo(fechaFin)) == 0) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String stringFechaInicial = dateFormat.format(fechaInicial);
            sqlHhpp += " And trunc(h.FECHA_CREACION) = to_date('" + stringFechaInicial + "','DD/MM/YYYY')";
        } else {
            // Se completa la busqueda por fecha de inicio
            if (fechaInicial != null) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String stringFechaInicial = dateFormat.format(fechaInicial);
                sqlHhpp += " And h.FECHA_CREACION >= to_date('" + stringFechaInicial + "','DD/MM/YYYY')";
            }
            // Se completa la busqueda con fecha final
            if (fechaFin != null) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String stringFechaFin = dateFormat.format(fechaFin);
                sqlHhpp += " And h.FECHA_CREACION <= to_date('" + stringFechaFin + "','DD/MM/YYYY')";
            }
        }

        return sqlHhpp;
    }

    /**
     * Creación de la consulta para busqueda de hhpp.
     *
     * @author becerraarmr
     * @param gpoIdCiudad        - id de la ciudad
     * @param gpoIdCentroPoblado - id del centro poblado
     * @param idNodo             - id del nodo
     * @param idTipoTecnologia   - id del tipo de tecnología
     * @param atributo           - atributo
     * @param valorAtributo      - valor del atributo
     * @param fechaInicial       - fecha de inicio de la creación
     * @param fechaFin           - fecha final de la creación
     * @param esConteo           - establece si el sql para buscar es de conteo.
     * @return un String con el sql representativo.
     */
    private String busquedaHhppByAtributoIdCcmm(BigDecimal idCcmmMgl, BigDecimal idCcmmRr,
            String atributo, String valorAtributo, BigDecimal idNodo, BigDecimal etiqueta) {

        CmtBasicaMglDaoImpl cmtBasicaMglDaoImpl = new CmtBasicaMglDaoImpl();
        CmtBasicaMgl basicaEstrato = cmtBasicaMglDaoImpl.findBasicaCodigo(Constant.CODIGO_BASICA_ESTRATO,
                Constant.NOMBRE_TIPO_ATRIBUTOS_HHPP);
        CmtBasicaMgl basicaCobertura = cmtBasicaMglDaoImpl.findBasicaCodigo(Constant.CODIGO_BASICA_COBERTURA,
                Constant.NOMBRE_TIPO_ATRIBUTOS_HHPP);
        CmtBasicaMgl basicaTipoVivienda = cmtBasicaMglDaoImpl.findBasicaCodigo(Constant.CODIGO_BASICA_TIPO_VIVIENDA,
                Constant.NOMBRE_TIPO_ATRIBUTOS_HHPP);

        String sqlHhpp = "SELECT h ";

        sqlHhpp += " FROM HhppMgl h, CmtSubEdificioMgl sub ";

        if (etiqueta != null && !etiqueta.equals(BigDecimal.ZERO)) {
            sqlHhpp += " , MarcasHhppMgl m ";
        }

        sqlHhpp += " WHERE h.hhppSubEdificioObj.subEdificioId = sub.subEdificioId ";

        if (idCcmmMgl != null && !idCcmmMgl.equals(BigDecimal.ZERO)) {
            sqlHhpp += " AND sub.cuentaMatrizObj.cuentaMatrizId =:idCcmmMgl ";
        } else {
            if (idCcmmRr != null && !idCcmmRr.equals(BigDecimal.ZERO)) {
                sqlHhpp += " AND sub.cuentaMatrizObj.numeroCuenta =:idCcmmRr ";
            }
        }

        if (etiqueta != null && !etiqueta.equals(BigDecimal.ZERO)) {
            sqlHhpp += "AND h.hhpId = m.hhpp.hhpId AND m.marId.marId =:etiqueta AND m.estadoRegistro =:estado ";
        }

        if (basicaCobertura != null && basicaCobertura.getNombreBasica() != null
                && basicaCobertura.getNombreBasica().equalsIgnoreCase(atributo)) {
            sqlHhpp += " AND h.nodId.nodId =:idNodo ";
        }

        // Filtro por el atributo seleccionado en pantalla
        valorAtributo = valorAtributo != null ? valorAtributo.toUpperCase() : "";
        if (!valorAtributo.isEmpty()) {

            if (basicaTipoVivienda != null && basicaTipoVivienda.getNombreBasica() != null
                    && basicaTipoVivienda.getNombreBasica().equalsIgnoreCase(atributo)) {
                sqlHhpp += " AND h.thhId =:valorAtributo ";
            }

            if (basicaEstrato != null && basicaEstrato.getNombreBasica() != null
                    && basicaEstrato.getNombreBasica().equalsIgnoreCase(atributo)) {
                sqlHhpp += " and (( CASE WHEN (h.SubDireccionObj IS NULL) "
                        + " THEN (select d.dirEstrato from DireccionMgl d where d.dirId = h.direccionObj.dirId) "
                        + " ELSE (select subdir.sdiEstrato from SubDireccionMgl subdir where subdir.sdiId = h.SubDireccionObj.sdiId) END) =:valorAtributo )";
            }
        }

        sqlHhpp += " AND h.estadoRegistro =:estado ";

        return sqlHhpp;
    }

    /**
     * Contar hhpp por dirección Se cuenta hhppp según los parámetros teniendo
     * cuenta que mínimo debe ingresar idTipoTecnologia, gpoIdCiudad y
     * drDireccion
     *
     * @author becerraarmr
     * @param gpoIdCiudad        BigDecimal GeograficoPoliticoMgl del id de la
     *                           ciudad
     * @param gpoIdCentroPoblado BigDecimal GeograficoPoliticoMgl del centro
     *                           poblado
     * @param idNodo             BigDecimal del NodoMgl del nodo
     * @param idTipoTecnologia   BigDecimal del id CmtBasicaMgl
     * @param drDireccion        DrDireccion con la data de la dirección
     * @param fechaInicio        - fecha inicio de inhabilitar
     * @param fechaFin           - fecha final de inhabilitar.
     * @param idHhpp
     * @param etiqueta
     *
     * @return un listado de HhppMgl
     *
     * @throws ApplicationException si hay un error en la busqueda
     *
     */
    public int countHhppByDireccion(BigDecimal gpoIdCiudad, BigDecimal gpoIdCentroPoblado,
            BigDecimal idNodo, BigDecimal idTipoTecnologia, DrDireccion drDireccion,
            Date fechaInicio, Date fechaFin, BigDecimal idHhpp, BigDecimal etiqueta)
            throws ApplicationException {
        if (idTipoTecnologia == null || drDireccion == null) {
            return 0;
        }
        try {
            if (!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().begin();
            }
            entityManager.clear();
            entityManager.flush();
            Query query = queryHhppByDireccion(gpoIdCiudad, gpoIdCentroPoblado, idNodo, idTipoTecnologia, drDireccion,
                    fechaInicio, fechaFin, true, idHhpp, etiqueta);
            int cant = ((Number) query.getSingleResult()).intValue();
            entityManager.getTransaction().commit();
            return cant;
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    private String queryDireccion(DrDireccion drDireccion, boolean subDireccion) {
        StringBuilder sqlBuilder = new StringBuilder();
        String colDireccion = " DISTINCT dd1.direccion.dirId ";
        if (subDireccion) {
            colDireccion = " DISTINCT dd1.subDireccion.sdiId ";
        }
        sqlBuilder.append(" SELECT ");
        sqlBuilder.append(colDireccion);
        sqlBuilder.append(" FROM CmtDireccionDetalladaMgl dd1 ");
        sqlBuilder.append(" WHERE  dd1.idTipoDireccion =:idTipoDireccion AND dd1.estadoRegistro = 1 ");
        if (drDireccion.getBarrio() != null && !drDireccion.getBarrio().trim().isEmpty()) {
            sqlBuilder.append(" AND dd1.barrio LIKE :barrio");
        }
        if (drDireccion.getTipoViaPrincipal() != null && !drDireccion.getTipoViaPrincipal().trim().isEmpty()) {
            sqlBuilder.append(" AND dd1.tipoViaPrincipal =:tipoViaPrincipal");
        }
        if (drDireccion.getNumViaPrincipal() != null && !drDireccion.getNumViaPrincipal().trim().isEmpty()) {
            sqlBuilder.append(" AND dd1.numViaPrincipal =:numViaPrincipal");
        }
        if (drDireccion.getLtViaPrincipal() != null && !drDireccion.getLtViaPrincipal().trim().isEmpty()) {
            sqlBuilder.append(" AND dd1.ltViaPrincipal =:ltViaPrincipal");
        }
        if (drDireccion.getNlPostViaP() != null && !drDireccion.getNlPostViaP().trim().isEmpty()) {
            sqlBuilder.append(" AND dd1.nlPostViaP =:nlPostViaP");
        }
        if (drDireccion.getBisViaPrincipal() != null && !drDireccion.getBisViaPrincipal().trim().isEmpty()) {
            sqlBuilder.append(" AND dd1.bisViaPrincipal =:bisViaPrincipal");
        }
        if (drDireccion.getCuadViaPrincipal() != null && !drDireccion.getCuadViaPrincipal().trim().isEmpty()) {
            sqlBuilder.append(" AND dd1.cuadViaPrincipal =:cuadViaPrincipal");
        }
        if (drDireccion.getTipoViaGeneradora() != null && !drDireccion.getTipoViaGeneradora().trim().isEmpty()) {
            sqlBuilder.append(" AND dd1.tipoViaGeneradora =:tipoViaGeneradora");
        }
        if (drDireccion.getNumViaGeneradora() != null && !drDireccion.getNumViaGeneradora().trim().isEmpty()) {
            sqlBuilder.append(" AND dd1.numViaGeneradora =:numViaGeneradora");
        }
        if (drDireccion.getLtViaGeneradora() != null && !drDireccion.getLtViaGeneradora().trim().isEmpty()) {
            sqlBuilder.append(" AND dd1.ltViaGeneradora =:ltViaGeneradora");
        }
        if (drDireccion.getNlPostViaG() != null && !drDireccion.getNlPostViaG().trim().isEmpty()) {
            sqlBuilder.append(" AND dd1.nlPostViaG =:nlPostViaG");
        }
        if (drDireccion.getBisViaGeneradora() != null && !drDireccion.getBisViaGeneradora().trim().isEmpty()) {
            sqlBuilder.append(" AND dd1.bisViaGeneradora =:bisViaGeneradora");
        }
        if (drDireccion.getCuadViaGeneradora() != null && !drDireccion.getCuadViaGeneradora().trim().isEmpty()) {
            sqlBuilder.append(" AND dd1.cuadViaGeneradora =:cuadViaGeneradora");
        }
        if (drDireccion.getPlacaDireccion() != null && !drDireccion.getPlacaDireccion().trim().isEmpty()) {
            sqlBuilder.append(" AND dd1.placaDireccion =:placaDireccion");
        }
        if (drDireccion.getMzTipoNivel1() != null && !drDireccion.getMzTipoNivel1().trim().isEmpty()) {
            sqlBuilder.append(" AND  dd1.mzTipoNivel1 =:mzTipoNivel1");
        }
        if (drDireccion.getMzTipoNivel2() != null && !drDireccion.getMzTipoNivel2().trim().isEmpty()) {
            sqlBuilder.append(" AND  dd1.mzTipoNivel2 =:mzTipoNivel2");
        }
        if (drDireccion.getMzTipoNivel3() != null && !drDireccion.getMzTipoNivel3().trim().isEmpty()) {
            sqlBuilder.append(" AND  dd1.mzTipoNivel3 =:mzTipoNivel3");
        }
        if (drDireccion.getMzTipoNivel4() != null && !drDireccion.getMzTipoNivel4().trim().isEmpty()) {
            sqlBuilder.append(" AND  dd1.mzTipoNivel4 =:mzTipoNivel4");
        }
        if (drDireccion.getMzTipoNivel5() != null && !drDireccion.getMzTipoNivel5().trim().isEmpty()) {
            sqlBuilder.append(" AND  dd1.mzTipoNivel5 =:mzTipoNivel5");
        }
        if (drDireccion.getMzValorNivel1() != null && !drDireccion.getMzValorNivel1().trim().isEmpty()) {
            sqlBuilder.append(" AND  dd1.mzValorNivel1 =:mzValorNivel1");
        }
        if (drDireccion.getMzValorNivel2() != null && !drDireccion.getMzValorNivel2().trim().isEmpty()) {
            sqlBuilder.append(" AND  dd1.mzValorNivel2 =:mzValorNivel2");
        }
        if (drDireccion.getMzValorNivel3() != null && !drDireccion.getMzValorNivel3().trim().isEmpty()) {
            sqlBuilder.append(" AND  dd1.mzValorNivel3 =:mzValorNivel3");
        }
        if (drDireccion.getMzValorNivel4() != null && !drDireccion.getMzValorNivel4().trim().isEmpty()) {
            sqlBuilder.append(" AND  dd1.mzValorNivel4 =:mzValorNivel4");
        }
        if (drDireccion.getMzValorNivel5() != null && !drDireccion.getMzValorNivel5().trim().isEmpty()) {
            sqlBuilder.append(" AND  dd1.mzValorNivel5 =:mzValorNivel5");
        }
        if (drDireccion.getCpTipoNivel1() != null && !drDireccion.getCpTipoNivel1().trim().isEmpty()) {
            sqlBuilder.append(" AND  dd1.cpTipoNivel1 =:cpTipoNivel1");
        }
        if (drDireccion.getCpTipoNivel2() != null && !drDireccion.getCpTipoNivel2().trim().isEmpty()) {
            sqlBuilder.append(" AND  dd1.cpTipoNivel2 =:cpTipoNivel2");
        }
        if (drDireccion.getCpTipoNivel3() != null && !drDireccion.getCpTipoNivel3().trim().isEmpty()) {
            sqlBuilder.append(" AND  dd1.cpTipoNivel3 =:cpTipoNivel3");
        }
        if (drDireccion.getCpTipoNivel4() != null && !drDireccion.getCpTipoNivel4().trim().isEmpty()) {
            sqlBuilder.append(" AND  dd1.cpTipoNivel4 =:cpTipoNivel4");
        }
        if (drDireccion.getCpTipoNivel5() != null && !drDireccion.getCpTipoNivel5().trim().isEmpty()) {
            sqlBuilder.append(" AND  dd1.cpTipoNivel5 =:cpTipoNivel5");
        }
        if (drDireccion.getCpTipoNivel6() != null && !drDireccion.getCpTipoNivel6().trim().isEmpty()) {
            sqlBuilder.append(" AND  dd1.cpTipoNivel6 =:cpTipoNivel6");
        }
        if (drDireccion.getCpValorNivel1() != null && !drDireccion.getCpValorNivel1().trim().isEmpty()) {
            sqlBuilder.append(" AND  dd1.cpValorNivel1 =:cpValorNivel1");
        }
        if (drDireccion.getCpValorNivel2() != null && !drDireccion.getCpValorNivel2().trim().isEmpty()) {
            sqlBuilder.append(" AND  dd1.cpValorNivel2 =:cpValorNivel2");
        }
        if (drDireccion.getCpValorNivel3() != null && !drDireccion.getCpValorNivel3().trim().isEmpty()) {
            sqlBuilder.append(" AND  dd1.cpValorNivel3 =:cpValorNivel3");
        }
        if (drDireccion.getCpValorNivel4() != null && !drDireccion.getCpValorNivel4().trim().isEmpty()) {
            sqlBuilder.append(" AND  dd1.cpValorNivel4 =:cpValorNivel4");
        }
        if (drDireccion.getCpValorNivel5() != null && !drDireccion.getCpValorNivel5().trim().isEmpty()) {
            sqlBuilder.append(" AND  dd1.cpValorNivel5 =:cpValorNivel5");
        }
        if (drDireccion.getCpValorNivel6() != null && !drDireccion.getCpValorNivel6().trim().isEmpty()) {
            sqlBuilder.append(" AND  dd1.cpValorNivel6 =:cpValorNivel6");
        }
        if (drDireccion.getItTipoPlaca() != null && !drDireccion.getItTipoPlaca().trim().isEmpty()) {
            sqlBuilder.append(" AND  dd1.itTipoPlaca =:itTipoPlaca");
        }
        if (drDireccion.getItValorPlaca() != null && !drDireccion.getItValorPlaca().trim().isEmpty()) {
            sqlBuilder.append(" AND  dd1.itValorPlaca =:itValorPlaca");
        }
        String sql = sqlBuilder.toString();
        if (subDireccion) {
            sql = sqlBuilder.toString().replace("dd1", "dd2");
        }

        Query query = entityManager.createQuery(sql);

        query = querySetParameter(drDireccion, query);

        List<BigDecimal> list = query.getResultList();

        if (list.isEmpty()) {
            return "0";
        }

        String valor = "";
        for (int i = 0; i < list.size();) {
            valor += list.get(i);
            i++;
            if (i < list.size()) {
                valor += ",";
            }
        }

        return valor;
    }

    private Query querySetParameter(DrDireccion drDireccion, Query query) {

        if (drDireccion.getBarrio() != null && !drDireccion.getBarrio().trim().isEmpty()) {
            query.setParameter("barrio", drDireccion.getBarrio().toUpperCase());
        }
        if (drDireccion.getIdTipoDireccion() != null && !drDireccion.getIdTipoDireccion().trim().isEmpty()) {
            query.setParameter("idTipoDireccion", drDireccion.getIdTipoDireccion().toUpperCase());
        }
        if (drDireccion.getTipoViaPrincipal() != null && !drDireccion.getTipoViaPrincipal().trim().isEmpty()) {
            query.setParameter("tipoViaPrincipal", drDireccion.getTipoViaPrincipal().toUpperCase());
        }
        if (drDireccion.getNumViaPrincipal() != null && !drDireccion.getNumViaPrincipal().trim().isEmpty()) {
            query.setParameter("numViaPrincipal", drDireccion.getNumViaPrincipal().toUpperCase());
        }
        if (drDireccion.getLtViaPrincipal() != null && !drDireccion.getLtViaPrincipal().trim().isEmpty()) {
            query.setParameter("ltViaPrincipal", drDireccion.getLtViaPrincipal().toUpperCase());
        }
        if (drDireccion.getNlPostViaP() != null && !drDireccion.getNlPostViaP().trim().isEmpty()) {
            query.setParameter("nlPostViaP", drDireccion.getNlPostViaP().toUpperCase());
        }
        if (drDireccion.getBisViaPrincipal() != null && !drDireccion.getBisViaPrincipal().trim().isEmpty()) {
            query.setParameter("bisViaPrincipal", drDireccion.getBisViaPrincipal().toUpperCase());
        }
        if (drDireccion.getCuadViaPrincipal() != null && !drDireccion.getCuadViaPrincipal().trim().isEmpty()) {
            query.setParameter("cuadViaPrincipal", drDireccion.getCuadViaPrincipal().toUpperCase());
        }
        if (drDireccion.getTipoViaGeneradora() != null && !drDireccion.getTipoViaGeneradora().trim().isEmpty()) {
            query.setParameter("tipoViaGeneradora", drDireccion.getTipoViaGeneradora().toUpperCase());
        }
        if (drDireccion.getNumViaGeneradora() != null && !drDireccion.getNumViaGeneradora().trim().isEmpty()) {
            query.setParameter("numViaGeneradora", drDireccion.getNumViaGeneradora().toUpperCase());
        }
        if (drDireccion.getLtViaGeneradora() != null && !drDireccion.getLtViaGeneradora().trim().isEmpty()) {
            query.setParameter("ltViaGeneradora", drDireccion.getLtViaGeneradora().toUpperCase());
        }
        if (drDireccion.getNlPostViaG() != null && !drDireccion.getNlPostViaG().trim().isEmpty()) {
            query.setParameter("nlPostViaG", drDireccion.getNlPostViaG().toUpperCase());
        }
        if (drDireccion.getBisViaGeneradora() != null && !drDireccion.getBisViaGeneradora().trim().isEmpty()) {
            query.setParameter("bisViaGeneradora", drDireccion.getBisViaGeneradora().toUpperCase());
        }
        if (drDireccion.getCuadViaGeneradora() != null && !drDireccion.getCuadViaGeneradora().trim().isEmpty()) {
            query.setParameter("cuadViaGeneradora", drDireccion.getCuadViaGeneradora().toUpperCase());
        }
        if (drDireccion.getPlacaDireccion() != null && !drDireccion.getPlacaDireccion().trim().isEmpty()) {
            query.setParameter("placaDireccion", drDireccion.getPlacaDireccion().toUpperCase());
        }
        if (drDireccion.getMzTipoNivel1() != null && !drDireccion.getMzTipoNivel1().trim().isEmpty()) {
            query.setParameter("mzTipoNivel1", drDireccion.getMzTipoNivel1().toUpperCase());
        }
        if (drDireccion.getMzTipoNivel2() != null && !drDireccion.getMzTipoNivel2().trim().isEmpty()) {
            query.setParameter("mzTipoNivel2", drDireccion.getMzTipoNivel2().toUpperCase());
        }
        if (drDireccion.getMzTipoNivel3() != null && !drDireccion.getMzTipoNivel3().trim().isEmpty()) {
            query.setParameter("mzTipoNivel3", drDireccion.getMzTipoNivel3().toUpperCase());
        }
        if (drDireccion.getMzTipoNivel4() != null && !drDireccion.getMzTipoNivel4().trim().isEmpty()) {
            query.setParameter("mzTipoNivel4", drDireccion.getMzTipoNivel4().toUpperCase());
        }
        if (drDireccion.getMzTipoNivel5() != null && !drDireccion.getMzTipoNivel5().trim().isEmpty()) {
            query.setParameter("mzTipoNivel5", drDireccion.getMzTipoNivel5().toUpperCase());
        }
        if (drDireccion.getMzValorNivel1() != null && !drDireccion.getMzValorNivel1().trim().isEmpty()) {
            query.setParameter("mzValorNivel1", drDireccion.getMzValorNivel1().toUpperCase());
        }
        if (drDireccion.getMzValorNivel2() != null && !drDireccion.getMzValorNivel2().trim().isEmpty()) {
            query.setParameter("mzValorNivel2", drDireccion.getMzValorNivel2().toUpperCase());
        }
        if (drDireccion.getMzValorNivel3() != null && !drDireccion.getMzValorNivel3().trim().isEmpty()) {
            query.setParameter("mzValorNivel3", drDireccion.getMzValorNivel3().toUpperCase());
        }
        if (drDireccion.getMzValorNivel4() != null && !drDireccion.getMzValorNivel4().trim().isEmpty()) {
            query.setParameter("mzValorNivel4", drDireccion.getMzValorNivel4().toUpperCase());
        }
        if (drDireccion.getMzValorNivel5() != null && !drDireccion.getMzValorNivel5().trim().isEmpty()) {
            query.setParameter("mzValorNivel5", drDireccion.getMzValorNivel5().toUpperCase());
        }
        if (drDireccion.getCpTipoNivel1() != null && !drDireccion.getCpTipoNivel1().trim().isEmpty()) {
            query.setParameter("cpTipoNivel1", drDireccion.getCpTipoNivel1().toUpperCase());
        }
        if (drDireccion.getCpTipoNivel2() != null && !drDireccion.getCpTipoNivel2().trim().isEmpty()) {
            query.setParameter("cpTipoNivel2", drDireccion.getCpTipoNivel2().toUpperCase());
        }
        if (drDireccion.getCpTipoNivel3() != null && !drDireccion.getCpTipoNivel3().trim().isEmpty()) {
            query.setParameter("cpTipoNivel3", drDireccion.getCpTipoNivel3().toUpperCase());
        }
        if (drDireccion.getCpTipoNivel4() != null && !drDireccion.getCpTipoNivel4().trim().isEmpty()) {
            query.setParameter("cpTipoNivel4", drDireccion.getCpTipoNivel4().toUpperCase());
        }
        if (drDireccion.getCpTipoNivel5() != null && !drDireccion.getCpTipoNivel5().trim().isEmpty()) {
            query.setParameter("cpTipoNivel5", drDireccion.getCpTipoNivel5().toUpperCase());
        }
        if (drDireccion.getCpTipoNivel6() != null && !drDireccion.getCpTipoNivel6().trim().isEmpty()) {
            query.setParameter("cpTipoNivel6", drDireccion.getCpTipoNivel6().toUpperCase());
        }
        if (drDireccion.getCpValorNivel1() != null && !drDireccion.getCpValorNivel1().trim().isEmpty()) {
            query.setParameter("cpValorNivel1", drDireccion.getCpValorNivel1().toUpperCase());
        }
        if (drDireccion.getCpValorNivel2() != null && !drDireccion.getCpValorNivel2().trim().isEmpty()) {
            query.setParameter("cpValorNivel2", drDireccion.getCpValorNivel2().toUpperCase());
        }
        if (drDireccion.getCpValorNivel3() != null && !drDireccion.getCpValorNivel3().trim().isEmpty()) {
            query.setParameter("cpValorNivel3", drDireccion.getCpValorNivel3().toUpperCase());
        }
        if (drDireccion.getCpValorNivel4() != null && !drDireccion.getCpValorNivel4().trim().isEmpty()) {
            query.setParameter("cpValorNivel4", drDireccion.getCpValorNivel4().toUpperCase());
        }
        if (drDireccion.getCpValorNivel5() != null && !drDireccion.getCpValorNivel5().trim().isEmpty()) {
            query.setParameter("cpValorNivel5", drDireccion.getCpValorNivel5().toUpperCase());
        }
        if (drDireccion.getCpValorNivel6() != null && !drDireccion.getCpValorNivel6().trim().isEmpty()) {
            query.setParameter("cpValorNivel6", drDireccion.getCpValorNivel6().toUpperCase());
        }
        if (drDireccion.getItTipoPlaca() != null && !drDireccion.getItTipoPlaca().trim().isEmpty()) {
            query.setParameter("itTipoPlaca", drDireccion.getItTipoPlaca().toUpperCase());
        }
        if (drDireccion.getItValorPlaca() != null && !drDireccion.getItValorPlaca().trim().isEmpty()) {
            query.setParameter("itValorPlaca", drDireccion.getItValorPlaca().toUpperCase());
        }

        return query;
    }

    /**
     * Preparar la consulta. Prepara la consulta para contar o para consultar
     * los hhpp según los parámetros
     *
     * @author becerraarmr
     * @param gpoIdCiudad        ciudad
     * @param gpoIdCentroPoblado centro poblado
     * @param idNodo             nodo
     * @param idTipoTecnologia   basica tipo tecnologia
     * @param drDireccion        drDireccion
     * @param count              boolean si cuenta o no
     * @return Query con el valor calculado.
     * @throws ApplicationException si hay error en la construcción.
     */
    private Query queryHhppByDireccion(
            BigDecimal gpoIdCiudad,
            BigDecimal gpoIdCentroPoblado,
            BigDecimal idNodo,
            BigDecimal idTipoTecnologia,
            DrDireccion drDireccion,
            Date fechaInicio,
            Date fechaFin,
            boolean count,
            BigDecimal idHhpp, BigDecimal etiqueta)
            throws ApplicationException {
        if (idTipoTecnologia == null) {
            return null;
        }

        StringBuilder sqlBuilder = new StringBuilder();

        String accion;

        if (etiqueta != null && count) {
            accion = "SELECT COUNT(h) FROM HhppMgl h  "
                    + "INNER JOIN  MarcasHhppMgl m ";
        } else if (count) {
            accion = "SELECT COUNT(h) FROM HhppMgl h ";
        } else if (etiqueta != null) {
            accion = "SELECT DISTINCT h FROM HhppMgl h  "
                    + "INNER JOIN  MarcasHhppMgl m ";
        } else {
            accion = "SELECT DISTINCT h FROM HhppMgl h ";
        }

        sqlBuilder.append(accion);
        if (gpoIdCentroPoblado != null) {// Contar hhpp por centro poblado
            sqlBuilder.append("WHERE h.estadoRegistro = 1 AND h.nodId.nodTipo.basicaId=:idTipoTecnologia "
                    + "AND h.nodId.gpoId=:idCentroPoblado  AND h.hhppSubEdificioObj is null");
        } else {// Contar hhpp por ciudad
            sqlBuilder.append("WHERE h.estadoRegistro = 1 AND h.nodId.nodTipo.basicaId=:idTipoTecnologia "
                    + "AND h.nodId.gpoId IN (SELECT gp.gpoId FROM GeograficoPoliticoMgl gp "
                    + "WHERE gp.geoGpoId=:gpoIdCiudad) ");
        }
        if (idNodo != null) {// Contar hhpp por ciudad o centro poblado y nodo
            sqlBuilder.append(" AND h.nodId.nodId=:idNodo ");
        }

        if (idHhpp != null) {
            sqlBuilder.append(" AND h.hhpId = :hhpId ");
        }
        if (etiqueta != null) {
            sqlBuilder.append(" AND m.marId.marId = :idEtiqueta ");
        }

        if (drDireccion != null) {
            String direccion = queryDireccion(drDireccion, false);
            sqlBuilder.append(" AND h.direccionObj.dirId IN(");
            sqlBuilder.append(direccion);
            sqlBuilder.append(")");
            String subDireccion = queryDireccion(drDireccion, true);
            if (subDireccion != "0") {
                sqlBuilder.append(" AND h.SubDireccionObj.sdiId IN(");
                sqlBuilder.append(subDireccion);
                sqlBuilder.append(")");
            }
        }

        if (fechaInicio != null || fechaFin != null) {
            if (fechaInicio.compareTo(fechaFin) == 0) {
                sqlBuilder.append(" AND h.hhpFechaCreacion =:fechaInicioInhabilitar");
            } else {
                if (fechaInicio != null) {
                    sqlBuilder.append(" AND h.hhpFechaCreacion>=:fechaInicioInhabilitar");
                }
                if (fechaFin != null) {
                    sqlBuilder.append(" AND h.hhpFechaCreacion<=:fechaFinInhabilitar");
                }
            }
        }

        Query query = entityManager.createQuery(sqlBuilder.toString());

        query.setParameter("idTipoTecnologia", idTipoTecnologia);

        if (gpoIdCentroPoblado != null) {// Contar hhpp por centro poblado
            query.setParameter("idCentroPoblado", gpoIdCentroPoblado);
        } else {// Contar hhpp por ciudad
            query.setParameter("gpoIdCiudad", gpoIdCiudad);
        }
        if (idNodo != null) {// Contar hhpp por ciudad o centro poblado y nodo
            query.setParameter("idNodo", idNodo);
        }
        if (fechaInicio != null) {// Fecha inicial de busqueda
            query.setParameter("fechaInicioInhabilitar", fechaInicio);
        }
        if (fechaFin != null) {// Fecha final de busqueda
            query.setParameter("fechaFinInhabilitar", fechaFin);
        }
        if (idHhpp != null) {
            query.setParameter("hhpId", idHhpp);
        }
        if (etiqueta != null) {// id de la marca
            query.setParameter("idEtiqueta", etiqueta);
        }

        return query;
    }

    /**
     * Setear los parámetros de DrDirección Se setean cada uno de los parámetros
     * de direccionTabulada si no es nulo y no está vacia.
     *
     * @author becerraarmr
     * @param direccionTabulada DrDireccion con la data de dirección.
     * @param query             query que se va a setear
     *
     * @return query con los parametros seteados
     */
    private Query setParameter(DrDireccion direccionTabulada, Query query,
            String consulta) {
        if (query == null || direccionTabulada == null) {
            return query;
        }
        String alias = "";
        if ("direccion".equalsIgnoreCase(consulta)) {
            alias = "dir";
        } else if ("subdireccion".equalsIgnoreCase(consulta)) {
            alias = "sdir";
        }

        if (direccionTabulada.getIdTipoDireccion() != null
                && !direccionTabulada.getIdTipoDireccion().trim().isEmpty()) {
            query.setParameter(alias + "idTipoDireccion", direccionTabulada.getIdTipoDireccion());
        }
        if (direccionTabulada.getTipoViaPrincipal() != null
                && !direccionTabulada.getTipoViaPrincipal().trim().isEmpty()) {
            query.setParameter(alias + "tipoViaPrincipal", direccionTabulada.getTipoViaPrincipal());
        }
        if (direccionTabulada.getNumViaPrincipal() != null
                && !direccionTabulada.getNumViaPrincipal().trim().isEmpty()) {
            query.setParameter(alias + "numViaPrincipal", direccionTabulada.getNumViaPrincipal());
        }
        if (direccionTabulada.getLtViaPrincipal() != null && !direccionTabulada.getLtViaPrincipal().trim().isEmpty()) {
            query.setParameter(alias + "ltViaPrincipal", direccionTabulada.getLtViaPrincipal());
        }
        if (direccionTabulada.getNlPostViaP() != null && !direccionTabulada.getNlPostViaP().trim().isEmpty()) {
            query.setParameter(alias + "nlPostViaP", direccionTabulada.getNlPostViaP());
        }
        if (direccionTabulada.getBisViaPrincipal() != null
                && !direccionTabulada.getBisViaPrincipal().trim().isEmpty()) {
            query.setParameter(alias + "bisViaPrincipal", direccionTabulada.getBisViaPrincipal());
        }
        if (direccionTabulada.getCuadViaPrincipal() != null
                && !direccionTabulada.getCuadViaPrincipal().trim().isEmpty()) {
            query.setParameter(alias + "cuadViaPrincipal", direccionTabulada.getCuadViaPrincipal());
        }
        if (direccionTabulada.getTipoViaGeneradora() != null
                && !direccionTabulada.getTipoViaGeneradora().trim().isEmpty()) {
            query.setParameter(alias + "tipoViaGeneradora", direccionTabulada.getTipoViaGeneradora());
        }
        if (direccionTabulada.getNumViaGeneradora() != null
                && !direccionTabulada.getNumViaGeneradora().trim().isEmpty()) {
            query.setParameter(alias + "numViaGeneradora", direccionTabulada.getNumViaGeneradora());
        }
        if (direccionTabulada.getLtViaGeneradora() != null
                && !direccionTabulada.getLtViaGeneradora().trim().isEmpty()) {
            query.setParameter(alias + "ltViaGeneradora", direccionTabulada.getLtViaGeneradora());
        }
        if (direccionTabulada.getNlPostViaG() != null && !direccionTabulada.getNlPostViaG().trim().isEmpty()) {
            query.setParameter(alias + "nlPostViaG", direccionTabulada.getNlPostViaG());
        }
        if (direccionTabulada.getBisViaGeneradora() != null
                && !direccionTabulada.getBisViaGeneradora().trim().isEmpty()) {
            query.setParameter(alias + "bisViaGeneradora", direccionTabulada.getBisViaGeneradora());
        }
        if (direccionTabulada.getCuadViaGeneradora() != null
                && !direccionTabulada.getCuadViaGeneradora().trim().isEmpty()) {
            query.setParameter(alias + "cuadViaGeneradora", direccionTabulada.getCuadViaGeneradora());
        }
        if (direccionTabulada.getPlacaDireccion() != null && !direccionTabulada.getPlacaDireccion().trim().isEmpty()) {
            query.setParameter(alias + "placaDireccion", direccionTabulada.getPlacaDireccion());
        }
        if (direccionTabulada.getMzTipoNivel1() != null && !direccionTabulada.getMzTipoNivel1().trim().isEmpty()) {
            query.setParameter(alias + "mzTipoNivel1", direccionTabulada.getMzTipoNivel1());
        }
        if (direccionTabulada.getMzTipoNivel2() != null && !direccionTabulada.getMzTipoNivel2().trim().isEmpty()) {
            query.setParameter(alias + "mzTipoNivel2", direccionTabulada.getMzTipoNivel2());
        }
        if (direccionTabulada.getMzTipoNivel3() != null && !direccionTabulada.getMzTipoNivel3().trim().isEmpty()) {
            query.setParameter(alias + "mzTipoNivel3", direccionTabulada.getMzTipoNivel3());
        }
        if (direccionTabulada.getMzTipoNivel4() != null && !direccionTabulada.getMzTipoNivel4().trim().isEmpty()) {
            query.setParameter(alias + "mzTipoNivel4", direccionTabulada.getMzTipoNivel4());
        }
        if (direccionTabulada.getMzTipoNivel5() != null && !direccionTabulada.getMzTipoNivel5().trim().isEmpty()) {
            query.setParameter(alias + "mzTipoNivel5", direccionTabulada.getMzTipoNivel5());
        }
        if (direccionTabulada.getMzValorNivel1() != null && !direccionTabulada.getMzValorNivel1().trim().isEmpty()) {
            query.setParameter(alias + "mzValorNivel1", direccionTabulada.getMzValorNivel1());
        }
        if (direccionTabulada.getMzValorNivel2() != null && !direccionTabulada.getMzValorNivel2().trim().isEmpty()) {
            query.setParameter(alias + "mzValorNivel2", direccionTabulada.getMzValorNivel2());
        }
        if (direccionTabulada.getMzValorNivel3() != null && !direccionTabulada.getMzValorNivel3().trim().isEmpty()) {
            query.setParameter(alias + "mzValorNivel3", direccionTabulada.getMzValorNivel3());
        }
        if (direccionTabulada.getMzValorNivel4() != null && !direccionTabulada.getMzValorNivel4().trim().isEmpty()) {
            query.setParameter(alias + "mzValorNivel4", direccionTabulada.getMzValorNivel4());
        }
        if (direccionTabulada.getMzValorNivel5() != null && !direccionTabulada.getMzValorNivel5().trim().isEmpty()) {
            query.setParameter(alias + "mzValorNivel5", direccionTabulada.getMzValorNivel5());
        }
        if (direccionTabulada.getCpTipoNivel1() != null && !direccionTabulada.getCpTipoNivel1().trim().isEmpty()) {
            query.setParameter(alias + "cpTipoNivel1", direccionTabulada.getCpTipoNivel1());
        }
        if (direccionTabulada.getCpTipoNivel2() != null && !direccionTabulada.getCpTipoNivel2().trim().isEmpty()) {
            query.setParameter(alias + "cpTipoNivel2", direccionTabulada.getCpTipoNivel2());
        }
        if (direccionTabulada.getCpTipoNivel3() != null && !direccionTabulada.getCpTipoNivel3().trim().isEmpty()) {
            query.setParameter(alias + "cpTipoNivel3", direccionTabulada.getCpTipoNivel3());
        }
        if (direccionTabulada.getCpTipoNivel4() != null && !direccionTabulada.getCpTipoNivel4().trim().isEmpty()) {
            query.setParameter(alias + "cpTipoNivel4", direccionTabulada.getCpTipoNivel4());
        }
        if (direccionTabulada.getCpTipoNivel5() != null && !direccionTabulada.getCpTipoNivel5().trim().isEmpty()) {
            query.setParameter(alias + "cpTipoNivel5", direccionTabulada.getCpTipoNivel5());
        }
        if (direccionTabulada.getCpTipoNivel6() != null && !direccionTabulada.getCpTipoNivel6().trim().isEmpty()) {
            query.setParameter(alias + "cpTipoNivel6", direccionTabulada.getCpTipoNivel6());
        }
        if (direccionTabulada.getCpValorNivel1() != null && !direccionTabulada.getCpValorNivel1().trim().isEmpty()) {
            query.setParameter(alias + "cpValorNivel1", direccionTabulada.getCpValorNivel1());
        }
        if (direccionTabulada.getCpValorNivel2() != null && !direccionTabulada.getCpValorNivel2().trim().isEmpty()) {
            query.setParameter(alias + "cpValorNivel2", direccionTabulada.getCpValorNivel2());
        }
        if (direccionTabulada.getCpValorNivel3() != null && !direccionTabulada.getCpValorNivel3().trim().isEmpty()) {
            query.setParameter(alias + "cpValorNivel3", direccionTabulada.getCpValorNivel3());
        }
        if (direccionTabulada.getCpValorNivel4() != null && !direccionTabulada.getCpValorNivel4().trim().isEmpty()) {
            query.setParameter(alias + "cpValorNivel4", direccionTabulada.getCpValorNivel4());
        }
        if (direccionTabulada.getCpValorNivel5() != null && !direccionTabulada.getCpValorNivel5().trim().isEmpty()) {
            query.setParameter(alias + "cpValorNivel5", direccionTabulada.getCpValorNivel5());
        }
        if (direccionTabulada.getCpValorNivel6() != null && !direccionTabulada.getCpValorNivel6().trim().isEmpty()) {
            query.setParameter(alias + "cpValorNivel6", direccionTabulada.getCpValorNivel6());
        }
        return query;
    }

    /**
     * Crear la consulta para dirección tabulada Se crea la consulta en
     * StringBuilder teniendo en cuenta si es para contar o para consultar.
     *
     * @author becerraarmr
     * @param direccionTabulada DrDireccion con la data
     * @param consulta          boolean si es true consulta en false cuenta
     *
     * @return StringBuilder con la consulta
     */
    private StringBuilder sqlDireccionTabulada(DrDireccion direccionTabulada,
            String consulta, BigDecimal gpoIdCentroPoblado) {
        StringBuilder sql = new StringBuilder();
        String alias = "dir";
        String sqlString = " SELECT DISTINCT " + alias + ".direccion.dirId FROM CmtDireccionDetalladaMgl " + alias
                + " ";
        if ("subdireccion".equalsIgnoreCase(consulta)) {
            alias = "sdir";
            sqlString = " SELECT DISTINCT " + alias + ".subDireccion.sdiId FROM CmtDireccionDetalladaMgl " + alias
                    + " ";
        }

        sqlString += " WHERE " + alias + ".idTipoDireccion =:" + alias + "idTipoDireccion "
                + " AND " + alias + ".estadoRegistro = 1 ";

        if (direccionTabulada.getTipoViaPrincipal() != null
                && !direccionTabulada.getTipoViaPrincipal().trim().isEmpty()) {
            sqlString += " AND " + alias + ".tipoViaPrincipal =:" + alias + "tipoViaPrincipal";
        }
        if (direccionTabulada.getNumViaPrincipal() != null
                && !direccionTabulada.getNumViaPrincipal().trim().isEmpty()) {
            sqlString += " AND " + alias + ".numViaPrincipal =:" + alias + "numViaPrincipal";
        }
        if (direccionTabulada.getLtViaPrincipal() != null && !direccionTabulada.getLtViaPrincipal().trim().isEmpty()) {
            sqlString += " AND " + alias + ".ltViaPrincipal =:" + alias + "ltViaPrincipal";
        }
        if (direccionTabulada.getNlPostViaP() != null && !direccionTabulada.getNlPostViaP().trim().isEmpty()) {
            sqlString += " AND " + alias + ".nlPostViaP =:" + alias + "nlPostViaP";
        }
        if (direccionTabulada.getBisViaPrincipal() != null
                && !direccionTabulada.getBisViaPrincipal().trim().isEmpty()) {
            sqlString += " AND " + alias + ".bisViaPrincipal =:" + alias + "bisViaPrincipal";
        }
        if (direccionTabulada.getCuadViaPrincipal() != null
                && !direccionTabulada.getCuadViaPrincipal().trim().isEmpty()) {
            sqlString += " AND " + alias + ".cuadViaPrincipal =:" + alias + "cuadViaPrincipal";
        }
        if (direccionTabulada.getTipoViaGeneradora() != null
                && !direccionTabulada.getTipoViaGeneradora().trim().isEmpty()) {
            sqlString += " AND " + alias + ".tipoViaGeneradora =:" + alias + "tipoViaGeneradora";
        }
        if (direccionTabulada.getNumViaGeneradora() != null
                && !direccionTabulada.getNumViaGeneradora().trim().isEmpty()) {
            sqlString += " AND " + alias + ".numViaGeneradora =:" + alias + "numViaGeneradora";
        }
        if (direccionTabulada.getLtViaGeneradora() != null
                && !direccionTabulada.getLtViaGeneradora().trim().isEmpty()) {
            sqlString += " AND " + alias + ".ltViaGeneradora =:" + alias + "ltViaGeneradora";
        }
        if (direccionTabulada.getNlPostViaG() != null && !direccionTabulada.getNlPostViaG().trim().isEmpty()) {
            sqlString += " AND " + alias + ".nlPostViaG =:" + alias + "nlPostViaG";
        }
        if (direccionTabulada.getBisViaGeneradora() != null
                && !direccionTabulada.getBisViaGeneradora().trim().isEmpty()) {
            sqlString += " AND " + alias + ".bisViaGeneradora =:" + alias + "bisViaGeneradora";
        }
        if (direccionTabulada.getCuadViaGeneradora() != null
                && !direccionTabulada.getCuadViaGeneradora().trim().isEmpty()) {
            sqlString += " AND " + alias + ".cuadViaGeneradora =:" + alias + "cuadViaGeneradora";
        }
        if (direccionTabulada.getPlacaDireccion() != null && !direccionTabulada.getPlacaDireccion().trim().isEmpty()) {
            sqlString += " AND " + alias + ".placaDireccion =:" + alias + "placaDireccion";
        }
        if (direccionTabulada.getMzTipoNivel1() != null && !direccionTabulada.getMzTipoNivel1().trim().isEmpty()) {
            sqlString += " AND  " + alias + ".mzTipoNivel1 =:" + alias + "mzTipoNivel1";
        }
        if (direccionTabulada.getMzTipoNivel2() != null && !direccionTabulada.getMzTipoNivel2().trim().isEmpty()) {
            sqlString += " AND  " + alias + ".mzTipoNivel2 =:" + alias + "mzTipoNivel2";
        }
        if (direccionTabulada.getMzTipoNivel3() != null && !direccionTabulada.getMzTipoNivel3().trim().isEmpty()) {
            sqlString += " AND  " + alias + ".mzTipoNivel3 =:" + alias + "mzTipoNivel3";
        }
        if (direccionTabulada.getMzTipoNivel4() != null && !direccionTabulada.getMzTipoNivel4().trim().isEmpty()) {
            sqlString += " AND  " + alias + ".mzTipoNivel4 =:" + alias + "mzTipoNivel4";
        }
        if (direccionTabulada.getMzTipoNivel5() != null && !direccionTabulada.getMzTipoNivel5().trim().isEmpty()) {
            sqlString += " AND  " + alias + ".mzTipoNivel5 =:" + alias + "mzTipoNivel5";
        }
        if (direccionTabulada.getMzValorNivel1() != null && !direccionTabulada.getMzValorNivel1().trim().isEmpty()) {
            sqlString += " AND  " + alias + ".mzValorNivel1 =:" + alias + "mzValorNivel1";
        }
        if (direccionTabulada.getMzValorNivel2() != null && !direccionTabulada.getMzValorNivel2().trim().isEmpty()) {
            sqlString += " AND  " + alias + ".mzValorNivel2 =:" + alias + "mzValorNivel2";
        }
        if (direccionTabulada.getMzValorNivel3() != null && !direccionTabulada.getMzValorNivel3().trim().isEmpty()) {
            sqlString += " AND  " + alias + ".mzValorNivel3 =:" + alias + "mzValorNivel3";
        }
        if (direccionTabulada.getMzValorNivel4() != null && !direccionTabulada.getMzValorNivel4().trim().isEmpty()) {
            sqlString += " AND  " + alias + ".mzValorNivel4 =:" + alias + "mzValorNivel4";
        }
        if (direccionTabulada.getMzValorNivel5() != null && !direccionTabulada.getMzValorNivel5().trim().isEmpty()) {
            sqlString += " AND  " + alias + ".mzValorNivel5 =:" + alias + "mzValorNivel5";
        }
        if (direccionTabulada.getCpTipoNivel1() != null && !direccionTabulada.getCpTipoNivel1().trim().isEmpty()) {
            sqlString += " AND  " + alias + ".cpTipoNivel1 =:" + alias + "cpTipoNivel1";
        }
        if (direccionTabulada.getCpTipoNivel2() != null && !direccionTabulada.getCpTipoNivel2().trim().isEmpty()) {
            sqlString += " AND  " + alias + ".cpTipoNivel2 =:" + alias + "cpTipoNivel2";
        }
        if (direccionTabulada.getCpTipoNivel3() != null && !direccionTabulada.getCpTipoNivel3().trim().isEmpty()) {
            sqlString += " AND  " + alias + ".cpTipoNivel3 =:" + alias + "cpTipoNivel3";
        }
        if (direccionTabulada.getCpTipoNivel4() != null && !direccionTabulada.getCpTipoNivel4().trim().isEmpty()) {
            sqlString += " AND  " + alias + ".cpTipoNivel4 =:" + alias + "cpTipoNivel4";
        }
        if (direccionTabulada.getCpTipoNivel5() != null && !direccionTabulada.getCpTipoNivel5().trim().isEmpty()) {
            sqlString += " AND  " + alias + ".cpTipoNivel5 =:" + alias + "cpTipoNivel5";
        }
        if (direccionTabulada.getCpTipoNivel6() != null && !direccionTabulada.getCpTipoNivel6().trim().isEmpty()) {
            sqlString += " AND  " + alias + ".cpTipoNivel6 =:" + alias + "cpTipoNivel6";
        }
        if (direccionTabulada.getCpValorNivel1() != null && !direccionTabulada.getCpValorNivel1().trim().isEmpty()) {
            sqlString += " AND  " + alias + ".cpValorNivel1 =:" + alias + "cpValorNivel1";
        }
        if (direccionTabulada.getCpValorNivel2() != null && !direccionTabulada.getCpValorNivel2().trim().isEmpty()) {
            sqlString += " AND  " + alias + ".cpValorNivel2 =:" + alias + "cpValorNivel2";
        }
        if (direccionTabulada.getCpValorNivel3() != null && !direccionTabulada.getCpValorNivel3().trim().isEmpty()) {
            sqlString += " AND  " + alias + ".cpValorNivel3 =:" + alias + "cpValorNivel3";
        }
        if (direccionTabulada.getCpValorNivel4() != null && !direccionTabulada.getCpValorNivel4().trim().isEmpty()) {
            sqlString += " AND  " + alias + ".cpValorNivel4 =:" + alias + "cpValorNivel4";
        }
        if (direccionTabulada.getCpValorNivel5() != null && !direccionTabulada.getCpValorNivel5().trim().isEmpty()) {
            sqlString += " AND  " + alias + ".cpValorNivel5 =:" + alias + "cpValorNivel5";
        }
        if (direccionTabulada.getCpValorNivel6() != null && !direccionTabulada.getCpValorNivel6().trim().isEmpty()) {
            sqlString += " AND  " + alias + ".cpValorNivel6 =:" + alias + "cpValorNivel6";
        }
        return sql.append(sqlString);
    }

    /**
     * Busca una lista de hhpp
     *
     * @author becerraarmr
     * @param gpoIdCiudad        - id de la ciudad
     * @param gpoIdCentroPoblado - id del centro poblado
     * @param idNodo             - id del nodo
     * @param idTipoTecnologia   - id del tipo de tecnología
     * @param atributo           - atributo
     * @param valorAtributo      - valor del atributo
     * @param fechaInicial       - fecha de inicio de la creación
     * @param fechaFin           - fecha final de la creación * @param range
     * @param range              - el rango de busqueda.
     * @param idHhpp             id del hhpp
     * @param etiqueta
     * @param idCcmmMgl
     * @param idCcmmRr
     * @param isConteo
     * @return una Lista con objetos HhppMgl
     * @throws ApplicationException un error al momento de realizar la busqueda
     */
    public List<HhppMgl> findHhpp(BigDecimal gpoIdCiudad, BigDecimal gpoIdCentroPoblado,
            BigDecimal idNodo, BigDecimal idTipoTecnologia, String atributo,
            String valorAtributo, Date fechaInicial, Date fechaFin,
            int[] range, BigDecimal idHhpp, BigDecimal etiqueta, BigDecimal idCcmmMgl,
            BigDecimal idCcmmRr) throws ApplicationException {

        try {
            if (!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().begin();
            }
            entityManager.clear();
            entityManager.flush();
            String sqlHhpp = "";
            Query query;
            // JDHT Consulta cuando se ingresa id ccmm Mgl o rr en pantalla
            if ((idCcmmMgl != null && !idCcmmMgl.equals(BigDecimal.ZERO))
                    || (idCcmmRr != null && !idCcmmRr.equals(BigDecimal.ZERO))) {

                sqlHhpp = busquedaHhppByAtributoIdCcmm(idCcmmMgl, idCcmmRr, atributo, valorAtributo, idNodo, etiqueta);

                query = entityManager.createQuery(sqlHhpp);

                if (idCcmmMgl != null && !idCcmmMgl.equals(BigDecimal.ZERO)) {
                    query.setParameter("idCcmmMgl", idCcmmMgl);
                }
                if (idCcmmRr != null && !idCcmmRr.equals(BigDecimal.ZERO)) {
                    query.setParameter("idCcmmRr", idCcmmRr);
                }
                if (valorAtributo != null && !valorAtributo.isEmpty()) {
                    query.setParameter("valorAtributo", valorAtributo);
                }
                if (idNodo != null && !idNodo.equals(BigDecimal.ZERO)) {
                    query.setParameter("idNodo", idNodo);
                }
                if (etiqueta != null && !etiqueta.equals(BigDecimal.ZERO)) {
                    query.setParameter("etiqueta", etiqueta);
                }

                query.setParameter("estado", 1);

            } else {

                /* Consulta normal */
                sqlHhpp = this.sqlQuery(gpoIdCiudad, gpoIdCentroPoblado,
                        idNodo, idTipoTecnologia, atributo, valorAtributo,
                        fechaInicial, fechaFin, false, idHhpp, etiqueta);

                query = entityManager.createNativeQuery(sqlHhpp, HhppMgl.class);

            }

            if (range != null) {
                query.setMaxResults(range[1] - range[0] + 1);
                query.setFirstResult(range[0]);
            }
            List<HhppMgl> lista = query.getResultList();

            entityManager.getTransaction().commit();
            return lista;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Autor: Victor Bocanegra Consulta todos los hhpp por direccion detallada
     *
     * @param cmtDireccionSolicitudMgl
     * @param consultaUnidades
     * @return List<HhppMgl> Cantidad de registros activos encontrados
     * @throws ApplicationException Error lanzado al realizar consultas
     */
    public List<HhppMgl> findHhppByDirDetalladaCK(CmtDireccionSolicitudMgl cmtDireccionSolicitudMgl,
            boolean consultaUnidades) throws ApplicationException {
        try {

            String sqlQuery = "SELECT h FROM HhppMgl h  "
                    + " INNER JOIN  h.direccionObj d  "
                    + " INNER JOIN  d.direccionDetallada dta  "
                    + " INNER JOIN  d.ubicacion u  "
                    + " WHERE  dta.estadoRegistro = 1 "
                    + " AND u.gpoIdObj.gpoId = :gpoIdObj";

            if (cmtDireccionSolicitudMgl.getCodTipoDir() != null
                    && !cmtDireccionSolicitudMgl.getCodTipoDir().isEmpty()) {
                sqlQuery += " AND  dta.idTipoDireccion = :idTipoDireccion ";
            }

            if (cmtDireccionSolicitudMgl.getBarrio() != null
                    && !cmtDireccionSolicitudMgl.getBarrio().trim().isEmpty()) {
                sqlQuery += " AND  dta.barrio = :barrio";
            }

            if (cmtDireccionSolicitudMgl.getTipoViaPrincipal() != null
                    && !cmtDireccionSolicitudMgl.getTipoViaPrincipal().isEmpty()) {
                sqlQuery += " AND  dta.tipoViaPrincipal = :tipoViaPrincipal ";
            } else {
                sqlQuery += " AND  dta.tipoViaPrincipal is null ";
            }
            if (cmtDireccionSolicitudMgl.getNumViaPrincipal() != null
                    && !cmtDireccionSolicitudMgl.getNumViaPrincipal().isEmpty()) {
                sqlQuery += " AND  dta.numViaPrincipal = :numViaPrincipal ";
            } else {
                sqlQuery += " AND  dta.numViaPrincipal is null ";
            }
            if (cmtDireccionSolicitudMgl.getLtViaPrincipal() != null
                    && !cmtDireccionSolicitudMgl.getLtViaPrincipal().isEmpty()) {
                sqlQuery += " AND  dta.ltViaPrincipal = :ltViaPrincipal ";
            } else {
                sqlQuery += " AND  dta.ltViaPrincipal  is null ";
            }
            if (cmtDireccionSolicitudMgl.getNlPostViaP() != null
                    && !cmtDireccionSolicitudMgl.getNlPostViaP().isEmpty()) {
                sqlQuery += " AND  dta.nlPostViaP = :nlPostViaP ";
            } else {
                sqlQuery += " AND  dta.nlPostViaP is null ";
            }
            if (cmtDireccionSolicitudMgl.getBisViaPrincipal() != null
                    && !cmtDireccionSolicitudMgl.getBisViaPrincipal().isEmpty()) {
                sqlQuery += " AND  dta.bisViaPrincipal = :bisViaPrincipal ";
            } else {
                sqlQuery += " AND  dta.bisViaPrincipal is null ";
            }
            if (cmtDireccionSolicitudMgl.getCuadViaPrincipal() != null
                    && !cmtDireccionSolicitudMgl.getCuadViaPrincipal().isEmpty()) {
                sqlQuery += " AND  dta.cuadViaPrincipal = :cuadViaPrincipal ";
            } else {
                sqlQuery += " AND  dta.cuadViaPrincipal is null ";
            }
            if (cmtDireccionSolicitudMgl.getTipoViaGeneradora() != null
                    && !cmtDireccionSolicitudMgl.getTipoViaGeneradora().isEmpty()) {
                sqlQuery += " AND  dta.tipoViaGeneradora = :tipoViaGeneradora ";
            } else {
                sqlQuery += " AND  dta.tipoViaGeneradora is null ";
            }
            if (cmtDireccionSolicitudMgl.getNumViaGeneradora() != null
                    && !cmtDireccionSolicitudMgl.getNumViaGeneradora().isEmpty()) {
                sqlQuery += " AND  dta.numViaGeneradora = :numViaGeneradora ";
            } else {
                sqlQuery += " AND  dta.numViaGeneradora is null ";
            }
            if (cmtDireccionSolicitudMgl.getLtViaGeneradora() != null
                    && !cmtDireccionSolicitudMgl.getLtViaGeneradora().isEmpty()) {
                sqlQuery += " AND  dta.ltViaGeneradora = :ltViaGeneradora ";
            } else {
                sqlQuery += " AND  dta.ltViaGeneradora is null ";
            }
            if (cmtDireccionSolicitudMgl.getNlPostViaG() != null
                    && !cmtDireccionSolicitudMgl.getNlPostViaG().isEmpty()) {
                sqlQuery += " AND  dta.nlPostViaG = :nlPostViaG ";
            } else {
                sqlQuery += " AND  dta.nlPostViaG is null ";
            }
            if (cmtDireccionSolicitudMgl.getLetra3G() != null
                    && !cmtDireccionSolicitudMgl.getLetra3G().isEmpty()) {
                sqlQuery += " AND  dta.letra3G = :letra3G ";
            } else {
                sqlQuery += " AND  dta.letra3G is null ";
            }
            if (cmtDireccionSolicitudMgl.getBisViaGeneradora() != null
                    && !cmtDireccionSolicitudMgl.getBisViaGeneradora().isEmpty()) {
                sqlQuery += " AND  dta.bisViaGeneradora = :bisViaGeneradora ";
            } else {
                sqlQuery += " AND  dta.bisViaGeneradora is null ";
            }
            if (cmtDireccionSolicitudMgl.getCuadViaGeneradora() != null
                    && !cmtDireccionSolicitudMgl.getCuadViaGeneradora().isEmpty()) {
                sqlQuery += " AND  dta.cuadViaGeneradora = :cuadViaGeneradora ";
            } else {
                sqlQuery += " AND  dta.cuadViaGeneradora is null ";
            }
            if (consultaUnidades) {
                if (cmtDireccionSolicitudMgl.getPlacaDireccion() != null
                        && !cmtDireccionSolicitudMgl.getPlacaDireccion().isEmpty()) {
                    sqlQuery += " AND  dta.placaDireccion = :placaDireccion ";
                } else {
                    sqlQuery += " AND  dta.placaDireccion is null ";
                }
            }

            Query query = entityManager.createQuery(sqlQuery);
            query.setMaxResults(0);
            query.setFirstResult(100);

            query.setParameter("gpoIdObj",
                    cmtDireccionSolicitudMgl.getSolicitudCMObj().getCentroPobladoGpo().getGpoId());

            if (cmtDireccionSolicitudMgl.getCodTipoDir() != null
                    && !cmtDireccionSolicitudMgl.getCodTipoDir().isEmpty()) {
                query.setParameter("idTipoDireccion", cmtDireccionSolicitudMgl.getCodTipoDir().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getBarrio() != null
                    && !cmtDireccionSolicitudMgl.getBarrio().trim().isEmpty()) {
                query.setParameter("barrio",
                        cmtDireccionSolicitudMgl.getBarrio().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getTipoViaPrincipal() != null
                    && !cmtDireccionSolicitudMgl.getTipoViaPrincipal().isEmpty()) {
                query.setParameter("tipoViaPrincipal", cmtDireccionSolicitudMgl.getTipoViaPrincipal().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getNumViaPrincipal() != null
                    && !cmtDireccionSolicitudMgl.getNumViaPrincipal().isEmpty()) {
                query.setParameter("numViaPrincipal", cmtDireccionSolicitudMgl.getNumViaPrincipal().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getLtViaPrincipal() != null
                    && !cmtDireccionSolicitudMgl.getLtViaPrincipal().isEmpty()) {
                query.setParameter("ltViaPrincipal", cmtDireccionSolicitudMgl.getLtViaPrincipal().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getNlPostViaP() != null
                    && !cmtDireccionSolicitudMgl.getNlPostViaP().isEmpty()) {
                query.setParameter("nlPostViaP", cmtDireccionSolicitudMgl.getNlPostViaP().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getBisViaPrincipal() != null
                    && !cmtDireccionSolicitudMgl.getBisViaPrincipal().isEmpty()) {
                query.setParameter("bisViaPrincipal", cmtDireccionSolicitudMgl.getBisViaPrincipal().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getCuadViaPrincipal() != null
                    && !cmtDireccionSolicitudMgl.getCuadViaPrincipal().isEmpty()) {
                query.setParameter("cuadViaPrincipal", cmtDireccionSolicitudMgl.getCuadViaPrincipal().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getTipoViaGeneradora() != null
                    && !cmtDireccionSolicitudMgl.getTipoViaGeneradora().isEmpty()) {
                query.setParameter("tipoViaGeneradora", cmtDireccionSolicitudMgl.getTipoViaGeneradora().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getNumViaGeneradora() != null
                    && !cmtDireccionSolicitudMgl.getNumViaGeneradora().isEmpty()) {
                query.setParameter("numViaGeneradora", cmtDireccionSolicitudMgl.getNumViaGeneradora().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getLtViaGeneradora() != null
                    && !cmtDireccionSolicitudMgl.getLtViaGeneradora().isEmpty()) {
                query.setParameter("ltViaGeneradora", cmtDireccionSolicitudMgl.getLtViaGeneradora().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getNlPostViaG() != null
                    && !cmtDireccionSolicitudMgl.getNlPostViaG().isEmpty()) {
                query.setParameter("nlPostViaG", cmtDireccionSolicitudMgl.getNlPostViaG().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getLetra3G() != null
                    && !cmtDireccionSolicitudMgl.getLetra3G().isEmpty()) {
                query.setParameter("letra3G", cmtDireccionSolicitudMgl.getLetra3G().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getBisViaGeneradora() != null
                    && !cmtDireccionSolicitudMgl.getBisViaGeneradora().isEmpty()) {
                query.setParameter("bisViaGeneradora", cmtDireccionSolicitudMgl.getBisViaGeneradora().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getCuadViaGeneradora() != null
                    && !cmtDireccionSolicitudMgl.getCuadViaGeneradora().isEmpty()) {
                query.setParameter("cuadViaGeneradora", cmtDireccionSolicitudMgl.getCuadViaGeneradora().toUpperCase());
            }
            if (consultaUnidades) {
                if (cmtDireccionSolicitudMgl.getPlacaDireccion() != null
                        && !cmtDireccionSolicitudMgl.getPlacaDireccion().isEmpty()) {
                    query.setParameter("placaDireccion", cmtDireccionSolicitudMgl.getPlacaDireccion().toUpperCase());
                }
            }

            List<HhppMgl> result = query.getResultList();
            entityManager.clear();
            return result;
        } catch (Exception e) {
            LOGGER.error("Error al momento de consultar el HHPP. EX000: " + e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    /**
     * Autor: Victor Bocanegra Consulta todos los hhpp por direccion detallada
     * BM(Barrio-Manzana)
     *
     * @param cmtDireccionSolicitudMgl
     * @param consultaUnidades
     * @return List<HhppMgl> Cantidad de registros activos encontrados
     * @throws ApplicationException Error lanzado al realizar consultas
     */
    public List<HhppMgl> findHhppByDirDetalladaBM(CmtDireccionSolicitudMgl cmtDireccionSolicitudMgl,
            boolean consultaUnidades) throws ApplicationException {
        try {

            String sqlQuery = "SELECT h FROM HhppMgl h  "
                    + " INNER JOIN  h.direccionObj d  "
                    + " INNER JOIN  d.direccionDetallada dta  "
                    + " INNER JOIN  d.ubicacion u  "
                    + " WHERE  dta.estadoRegistro = 1 "
                    + " AND u.gpoIdObj.gpoId = :gpoIdObj";

            if (cmtDireccionSolicitudMgl.getCodTipoDir() != null
                    && !cmtDireccionSolicitudMgl.getCodTipoDir().isEmpty()) {
                sqlQuery += " AND  dta.idTipoDireccion = :idTipoDireccion ";
            }

            if (cmtDireccionSolicitudMgl.getBarrio() != null
                    && !cmtDireccionSolicitudMgl.getBarrio().trim().isEmpty()) {
                sqlQuery += " AND  dta.barrio = :barrio";
            }

            if (cmtDireccionSolicitudMgl.getMzTipoNivel1() != null
                    && !cmtDireccionSolicitudMgl.getMzTipoNivel1().isEmpty()) {
                sqlQuery += " AND  dta.mzTipoNivel1 = :mzTipoNivel1 ";
            } else {
                sqlQuery += " AND  dta.mzTipoNivel1 is null ";
            }
            if (cmtDireccionSolicitudMgl.getMzValorNivel1() != null
                    && !cmtDireccionSolicitudMgl.getMzValorNivel1().isEmpty()) {
                sqlQuery += " AND  dta.mzValorNivel1 = :mzValorNivel1 ";
            } else {
                sqlQuery += " AND  dta.mzValorNivel1 is null ";
            }
            if (cmtDireccionSolicitudMgl.getMzTipoNivel2() != null
                    && !cmtDireccionSolicitudMgl.getMzTipoNivel2().isEmpty()) {
                sqlQuery += " AND  dta.mzTipoNivel2 = :mzTipoNivel2 ";
            } else {
                sqlQuery += " AND  dta.mzTipoNivel2 is null ";
            }
            if (cmtDireccionSolicitudMgl.getMzValorNivel2() != null
                    && !cmtDireccionSolicitudMgl.getMzValorNivel2().isEmpty()) {
                sqlQuery += " AND  dta.mzValorNivel2 = :mzValorNivel2 ";
            } else {
                sqlQuery += " AND  dta.mzValorNivel2 is null ";
            }
            if (cmtDireccionSolicitudMgl.getMzTipoNivel3() != null
                    && !cmtDireccionSolicitudMgl.getMzTipoNivel3().isEmpty()) {
                sqlQuery += " AND  dta.mzTipoNivel3 = :mzTipoNivel3 ";
            } else {
                sqlQuery += " AND  dta.mzTipoNivel3 is null ";
            }
            if (cmtDireccionSolicitudMgl.getMzValorNivel3() != null
                    && !cmtDireccionSolicitudMgl.getMzValorNivel3().isEmpty()) {
                sqlQuery += " AND  dta.mzValorNivel3 = :mzValorNivel3 ";
            } else {
                sqlQuery += " AND  dta.mzValorNivel3 is null ";
            }
            if (cmtDireccionSolicitudMgl.getMzTipoNivel4() != null
                    && !cmtDireccionSolicitudMgl.getMzTipoNivel4().isEmpty()) {
                sqlQuery += " AND  dta.mzTipoNivel4 = :mzTipoNivel4 ";
            } else {
                sqlQuery += " AND  dta.mzTipoNivel4 is null ";
            }
            if (cmtDireccionSolicitudMgl.getMzValorNivel4() != null
                    && !cmtDireccionSolicitudMgl.getMzValorNivel4().isEmpty()) {
                sqlQuery += " AND  dta.mzValorNivel4 = :mzValorNivel4 ";
            } else {
                sqlQuery += " AND  dta.mzValorNivel4 is null ";
            }
            if (consultaUnidades) {
                if (cmtDireccionSolicitudMgl.getMzTipoNivel5() != null
                        && !cmtDireccionSolicitudMgl.getMzTipoNivel5().trim().isEmpty()) {
                    if (cmtDireccionSolicitudMgl.getMzTipoNivel5().trim().equalsIgnoreCase("CASA")
                            || cmtDireccionSolicitudMgl.getMzTipoNivel5().trim().equalsIgnoreCase("LOTE")) {
                        sqlQuery += " AND  (dta.mzTipoNivel5 =:casa OR dta.mzTipoNivel5 =:lote) ";
                    } else {
                        sqlQuery += " AND  dta.mzTipoNivel5 = :mzTipoNivel5";
                    }

                } else {
                    sqlQuery += " AND  dta.mzTipoNivel5 is null ";
                }
                if (cmtDireccionSolicitudMgl.getMzValorNivel5() != null
                        && !cmtDireccionSolicitudMgl.getMzValorNivel5().isEmpty()) {
                    sqlQuery += " AND  dta.mzValorNivel5 = :mzValorNivel5 ";
                }
            }

            Query query = entityManager.createQuery(sqlQuery);
            query.setMaxResults(0);
            query.setFirstResult(100);

            query.setParameter("gpoIdObj",
                    cmtDireccionSolicitudMgl.getSolicitudCMObj().getCentroPobladoGpo().getGpoId());

            if (cmtDireccionSolicitudMgl.getCodTipoDir() != null
                    && !cmtDireccionSolicitudMgl.getCodTipoDir().isEmpty()) {
                query.setParameter("idTipoDireccion", cmtDireccionSolicitudMgl.getCodTipoDir().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getBarrio() != null
                    && !cmtDireccionSolicitudMgl.getBarrio().trim().isEmpty()) {
                query.setParameter("barrio",
                        cmtDireccionSolicitudMgl.getBarrio().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getMzTipoNivel1() != null
                    && !cmtDireccionSolicitudMgl.getMzTipoNivel1().isEmpty()) {
                query.setParameter("mzTipoNivel1", cmtDireccionSolicitudMgl.getMzTipoNivel1().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getMzValorNivel1() != null
                    && !cmtDireccionSolicitudMgl.getMzValorNivel1().isEmpty()) {
                query.setParameter("mzValorNivel1", cmtDireccionSolicitudMgl.getMzValorNivel1().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getMzTipoNivel2() != null
                    && !cmtDireccionSolicitudMgl.getMzTipoNivel2().isEmpty()) {
                query.setParameter("mzTipoNivel2", cmtDireccionSolicitudMgl.getMzTipoNivel2().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getMzValorNivel2() != null
                    && !cmtDireccionSolicitudMgl.getMzValorNivel2().isEmpty()) {
                query.setParameter("mzValorNivel2", cmtDireccionSolicitudMgl.getMzValorNivel2().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getMzTipoNivel3() != null
                    && !cmtDireccionSolicitudMgl.getMzTipoNivel3().isEmpty()) {
                query.setParameter("mzTipoNivel3", cmtDireccionSolicitudMgl.getMzTipoNivel3().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getMzValorNivel3() != null
                    && !cmtDireccionSolicitudMgl.getMzValorNivel3().isEmpty()) {
                query.setParameter("mzValorNivel3", cmtDireccionSolicitudMgl.getMzValorNivel3().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getMzTipoNivel4() != null
                    && !cmtDireccionSolicitudMgl.getMzTipoNivel4().isEmpty()) {
                query.setParameter("mzTipoNivel4", cmtDireccionSolicitudMgl.getMzTipoNivel4().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getMzValorNivel4() != null
                    && !cmtDireccionSolicitudMgl.getMzValorNivel4().isEmpty()) {
                query.setParameter("mzValorNivel4", cmtDireccionSolicitudMgl.getMzValorNivel4().toUpperCase());
            }
            if (consultaUnidades) {
                if (cmtDireccionSolicitudMgl.getMzTipoNivel5() != null
                        && !cmtDireccionSolicitudMgl.getMzTipoNivel5().trim().isEmpty()) {

                    if (cmtDireccionSolicitudMgl.getMzTipoNivel5().trim().equalsIgnoreCase("CASA")
                            || cmtDireccionSolicitudMgl.getMzTipoNivel5().trim().equalsIgnoreCase("LOTE")) {
                        query.setParameter("casa", "CASA");
                        query.setParameter("lote", "LOTE");
                    } else {
                        query.setParameter("mzTipoNivel5",
                                cmtDireccionSolicitudMgl.getMzTipoNivel5().toUpperCase());
                    }
                }
                if (cmtDireccionSolicitudMgl.getMzValorNivel5() != null
                        && !cmtDireccionSolicitudMgl.getMzValorNivel5().isEmpty()) {
                    query.setParameter("mzValorNivel5", cmtDireccionSolicitudMgl.getMzValorNivel5().toUpperCase());
                }

            }
            entityManager.clear();
            return query.getResultList();

        } catch (Exception e) {
            LOGGER.error("Error al momento de consultar el HHPP. EX000: " + e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    /**
     * Autor: Victor Bocanegra Consulta todos los hhpp por direccion detallada
     * IT(intraducible)
     *
     * @param cmtDireccionSolicitudMgl
     * @param consultaUnidades
     * @return List<HhppMgl> Cantidad de registros activos encontrados
     * @throws ApplicationException Error lanzado al realizar consultas
     */
    public List<HhppMgl> findHhppByDirDetalladaIT(CmtDireccionSolicitudMgl cmtDireccionSolicitudMgl,
            boolean consultaUnidades) throws ApplicationException {
        try {

            String sqlQuery = "SELECT h FROM HhppMgl h  "
                    + " INNER JOIN  h.direccionObj d  "
                    + " INNER JOIN  d.direccionDetallada dta  "
                    + "INNER JOIN  d.ubicacion u  "
                    + " WHERE  dta.estadoRegistro = 1 "
                    + " AND u.gpoIdObj.gpoId = :gpoIdObj";

            if (cmtDireccionSolicitudMgl.getCodTipoDir() != null
                    && !cmtDireccionSolicitudMgl.getCodTipoDir().isEmpty()) {
                sqlQuery += " AND  dta.idTipoDireccion = :idTipoDireccion ";
            }
            if (cmtDireccionSolicitudMgl.getMzTipoNivel1() != null
                    && !cmtDireccionSolicitudMgl.getMzTipoNivel1().isEmpty()) {
                sqlQuery += " AND  dta.mzTipoNivel1 = :mzTipoNivel1 ";
            } else {
                sqlQuery += " AND  dta.mzTipoNivel1 is null ";
            }
            if (cmtDireccionSolicitudMgl.getMzValorNivel1() != null
                    && !cmtDireccionSolicitudMgl.getMzValorNivel1().isEmpty()) {
                sqlQuery += " AND  dta.mzValorNivel1 = :mzValorNivel1 ";
            } else {
                sqlQuery += " AND  dta.mzValorNivel1 is null ";
            }
            if (cmtDireccionSolicitudMgl.getMzTipoNivel2() != null
                    && !cmtDireccionSolicitudMgl.getMzTipoNivel2().isEmpty()) {
                sqlQuery += " AND  dta.mzTipoNivel2 = :mzTipoNivel2 ";
            } else {
                sqlQuery += " AND  dta.mzTipoNivel2 is null ";
            }
            if (cmtDireccionSolicitudMgl.getMzValorNivel2() != null
                    && !cmtDireccionSolicitudMgl.getMzValorNivel2().isEmpty()) {
                sqlQuery += " AND  dta.mzValorNivel2 = :mzValorNivel2 ";
            } else {
                sqlQuery += " AND  dta.mzValorNivel2 is null ";
            }
            if (cmtDireccionSolicitudMgl.getMzTipoNivel3() != null
                    && !cmtDireccionSolicitudMgl.getMzTipoNivel3().isEmpty()) {
                sqlQuery += " AND  dta.mzTipoNivel3 = :mzTipoNivel3 ";
            } else {
                sqlQuery += " AND  dta.mzTipoNivel3 is null ";
            }
            if (cmtDireccionSolicitudMgl.getMzValorNivel3() != null
                    && !cmtDireccionSolicitudMgl.getMzValorNivel3().isEmpty()) {
                sqlQuery += " AND  dta.mzValorNivel3 = :mzValorNivel3 ";
            } else {
                sqlQuery += " AND  dta.mzValorNivel3 is null ";
            }
            if (cmtDireccionSolicitudMgl.getMzTipoNivel4() != null
                    && !cmtDireccionSolicitudMgl.getMzTipoNivel4().isEmpty()) {
                sqlQuery += " AND  dta.mzTipoNivel4 = :mzTipoNivel4 ";
            } else {
                sqlQuery += " AND  dta.mzTipoNivel4 is null ";
            }
            if (cmtDireccionSolicitudMgl.getMzValorNivel4() != null
                    && !cmtDireccionSolicitudMgl.getMzValorNivel4().isEmpty()) {
                sqlQuery += " AND  dta.mzValorNivel4 = :mzValorNivel4 ";
            } else {
                sqlQuery += " AND  dta.mzValorNivel4 is null ";
            }
            if (cmtDireccionSolicitudMgl.getMzTipoNivel5() != null
                    && !cmtDireccionSolicitudMgl.getMzTipoNivel5().trim().isEmpty()) {
                if (cmtDireccionSolicitudMgl.getMzTipoNivel5().trim().equalsIgnoreCase("CASA")
                        || cmtDireccionSolicitudMgl.getMzTipoNivel5().trim().equalsIgnoreCase("LOTE")) {
                    sqlQuery += " AND  (dta.mzTipoNivel5 =:casa OR dta.mzTipoNivel5 =:lote) ";
                } else {
                    sqlQuery += " AND  dta.mzTipoNivel5 = :mzTipoNivel5";
                }

            } else {
                sqlQuery += " AND  dta.mzTipoNivel5 is null ";
            }
            if (cmtDireccionSolicitudMgl.getMzValorNivel5() != null
                    && !cmtDireccionSolicitudMgl.getMzValorNivel5().isEmpty()) {
                sqlQuery += " AND  dta.mzValorNivel5 = :mzValorNivel5 ";
            }
            if (cmtDireccionSolicitudMgl.getMzTipoNivel6() != null
                    && !cmtDireccionSolicitudMgl.getMzTipoNivel6().isEmpty()) {
                sqlQuery += " AND  dta.mzTipoNivel6 = :mzTipoNivel6 ";
            } else {
                sqlQuery += " AND  dta.mzTipoNivel6 is null ";
            }
            if (cmtDireccionSolicitudMgl.getMzValorNivel6() != null
                    && !cmtDireccionSolicitudMgl.getMzValorNivel6().isEmpty()) {
                sqlQuery += " AND  dta.mzValorNivel6 = :mzValorNivel6 ";
            } else {
                sqlQuery += " AND  dta.mzValorNivel6 is null ";
            }
            if (cmtDireccionSolicitudMgl.getItTipoPlaca() != null
                    && !cmtDireccionSolicitudMgl.getItTipoPlaca().isEmpty()) {
                sqlQuery += " AND  dta.itTipoPlaca = :itTipoPlaca ";
            }

            if (consultaUnidades) {
                if (cmtDireccionSolicitudMgl.getItValorPlaca() != null
                        && !cmtDireccionSolicitudMgl.getItValorPlaca().isEmpty()) {
                    sqlQuery += " AND  dta.itValorPlaca = :itValorPlaca ";
                }
            }
            Query query = entityManager.createQuery(sqlQuery);
            query.setMaxResults(0);
            query.setFirstResult(100);

            if (cmtDireccionSolicitudMgl.getSolicitudCMObj() != null
                    && cmtDireccionSolicitudMgl.getSolicitudCMObj().getCentroPobladoGpo() != null) {
                query.setParameter("gpoIdObj",
                        cmtDireccionSolicitudMgl.getSolicitudCMObj().getCentroPobladoGpo().getGpoId());
            } else {
                LOGGER.error("No se encontró el centro poblado para la solicitud de la dirección "
                        + cmtDireccionSolicitudMgl.getDireccionSolId() + ".");
                query.setParameter("gpoIdObj", "");
            }

            if (cmtDireccionSolicitudMgl.getCodTipoDir() != null
                    && !cmtDireccionSolicitudMgl.getCodTipoDir().isEmpty()) {
                query.setParameter("idTipoDireccion", cmtDireccionSolicitudMgl.getCodTipoDir().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getMzTipoNivel1() != null
                    && !cmtDireccionSolicitudMgl.getMzTipoNivel1().isEmpty()) {
                query.setParameter("mzTipoNivel1", cmtDireccionSolicitudMgl.getMzTipoNivel1().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getMzValorNivel1() != null
                    && !cmtDireccionSolicitudMgl.getMzValorNivel1().isEmpty()) {
                query.setParameter("mzValorNivel1", cmtDireccionSolicitudMgl.getMzValorNivel1().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getMzTipoNivel2() != null
                    && !cmtDireccionSolicitudMgl.getMzTipoNivel2().isEmpty()) {
                query.setParameter("mzTipoNivel2", cmtDireccionSolicitudMgl.getMzTipoNivel2().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getMzValorNivel2() != null
                    && !cmtDireccionSolicitudMgl.getMzValorNivel2().isEmpty()) {
                query.setParameter("mzValorNivel2", cmtDireccionSolicitudMgl.getMzValorNivel2().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getMzTipoNivel3() != null
                    && !cmtDireccionSolicitudMgl.getMzTipoNivel3().isEmpty()) {
                query.setParameter("mzTipoNivel3", cmtDireccionSolicitudMgl.getMzTipoNivel3().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getMzValorNivel3() != null
                    && !cmtDireccionSolicitudMgl.getMzValorNivel3().isEmpty()) {
                query.setParameter("mzValorNivel3", cmtDireccionSolicitudMgl.getMzValorNivel3().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getMzTipoNivel4() != null
                    && !cmtDireccionSolicitudMgl.getMzTipoNivel4().isEmpty()) {
                query.setParameter("mzTipoNivel4", cmtDireccionSolicitudMgl.getMzTipoNivel4().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getMzValorNivel4() != null
                    && !cmtDireccionSolicitudMgl.getMzValorNivel4().isEmpty()) {
                query.setParameter("mzValorNivel4", cmtDireccionSolicitudMgl.getMzValorNivel4().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getMzTipoNivel5() != null
                    && !cmtDireccionSolicitudMgl.getMzTipoNivel5().trim().isEmpty()) {

                if (cmtDireccionSolicitudMgl.getMzTipoNivel5().trim().equalsIgnoreCase("CASA")
                        || cmtDireccionSolicitudMgl.getMzTipoNivel5().trim().equalsIgnoreCase("LOTE")) {
                    query.setParameter("casa", "CASA");
                    query.setParameter("lote", "LOTE");
                } else {
                    query.setParameter("mzTipoNivel5",
                            cmtDireccionSolicitudMgl.getMzTipoNivel5().toUpperCase());
                }
            }
            if (cmtDireccionSolicitudMgl.getMzValorNivel5() != null
                    && !cmtDireccionSolicitudMgl.getMzValorNivel5().isEmpty()) {
                query.setParameter("mzValorNivel5", cmtDireccionSolicitudMgl.getMzValorNivel5().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getMzTipoNivel6() != null
                    && !cmtDireccionSolicitudMgl.getMzTipoNivel6().isEmpty()) {
                query.setParameter("mzTipoNivel6", cmtDireccionSolicitudMgl.getMzTipoNivel6().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getMzValorNivel6() != null
                    && !cmtDireccionSolicitudMgl.getMzValorNivel6().isEmpty()) {
                query.setParameter("mzValorNivel6", cmtDireccionSolicitudMgl.getMzValorNivel6().toUpperCase());
            }
            if (cmtDireccionSolicitudMgl.getItTipoPlaca() != null
                    && !cmtDireccionSolicitudMgl.getItTipoPlaca().isEmpty()) {
                query.setParameter("itTipoPlaca", cmtDireccionSolicitudMgl.getItTipoPlaca().toUpperCase());
            }
            if (consultaUnidades) {
                if (cmtDireccionSolicitudMgl.getItValorPlaca() != null
                        && !cmtDireccionSolicitudMgl.getItValorPlaca().isEmpty()) {
                    query.setParameter("itValorPlaca", cmtDireccionSolicitudMgl.getItValorPlaca().toUpperCase());
                }
            }
            entityManager.clear();
            return query.getResultList();

        } catch (Exception e) {
            LOGGER.error("Error al momento de consultar el HHPP. EX000: " + e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    /**
     * Buscar hhpp por dirección Se busca hhppp según los parámetros teniendo
     * cuenta que mínimo debe ingresar idTipoTecnologia, gpoIdCiudad y
     * drDireccion
     *
     * @author becerraarmr
     * @param gpoIdCiudad        BigDecimal GeograficoPoliticoMgl del id de la
     *                           ciudad
     * @param gpoIdCentroPoblado BigDecimal GeograficoPoliticoMgl del centro
     *                           poblado
     * @param idNodo             BigDecimal del NodoMgl del nodo
     * @param idTipoTecnologia   BigDecimal del id CmtBasicaMgl
     * @param drDireccion        DrDireccion con la data de la dirección
     * @param fechaInicio        - Fecha de inicio para inhabilitar
     * @param fechaFinal         - Fecha final para inhabilitar.
     * @param range              rango de busqueda
     * @param idHhpp             id del hhpp
     *
     * @return un listado de HhppMgl
     *
     * @throws ApplicationException si hay un error en la busqueda
     */
    public List<HhppMgl> findHhppDireccion(BigDecimal gpoIdCiudad, BigDecimal gpoIdCentroPoblado,
            BigDecimal idNodo, BigDecimal idTipoTecnologia, DrDireccion drDireccion,
            Date fechaInicio, Date fechaFinal,
            int[] range, BigDecimal idHhpp, BigDecimal etiqueta) throws ApplicationException {
        List<HhppMgl> lista = new ArrayList<>();
        if (idTipoTecnologia == null || gpoIdCiudad == null || drDireccion == null) {
            return lista;
        }
        try {
            if (!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().begin();
            }
            entityManager.clear();
            entityManager.flush();
            Query query = queryHhppByDireccion(gpoIdCiudad, gpoIdCentroPoblado, idNodo, idTipoTecnologia, drDireccion,
                    fechaInicio, fechaFinal, false, idHhpp, etiqueta);
            if (range != null) {
                query.setMaxResults(range[1] - range[0] + 1);
                query.setFirstResult(range[0]);
            }
            lista = query.getResultList();
            entityManager.getTransaction().commit();
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
        }
        return lista;
    }

    /**
     * Busca un HHPP en repositorio por Id de Dirección.
     *
     * @param dirId
     * @return HhppMgl si la unidad Existe en el repositorio
     * @author Juan David Hernandez
     */
    public List<HhppMgl> findHhppByDirId(BigDecimal dirId) {
        try {
            TypedQuery<HhppMgl> query = entityManager.createQuery("SELECT g FROM HhppMgl g WHERE "
                    + "g.direccionObj.dirId =:dirId AND g.estadoRegistro = 1 ",
                    HhppMgl.class);
            query.setParameter("dirId", dirId);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");// jpa 2.0
            entityManager.clear();
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.error("Error al momento de consultar el HHPP. EX000: " + e.getMessage(), e);
            return null;
        }
    }

    /**
     * Busca un HHPP en repositorio por Id de Dirección.
     *
     * @param subDirId id del objeto subdirección por el que se desea buscar
     * @return HhppMgl si la unidad Existe en el repositorio
     * @author Juan David Hernandez
     */
    public List<HhppMgl> findHhppBySubDirId(BigDecimal subDirId) {
        try {
            TypedQuery<HhppMgl> query = entityManager.createQuery("SELECT g FROM HhppMgl g WHERE "
                    + "g.SubDireccionObj.sdiId =:subDirId AND g.estadoRegistro = 1 ",
                    HhppMgl.class);
            query.setParameter("subDirId", subDirId);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");// jpa 2.0
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.error("Error al momento de consultar el HHPP. EX000: " + e.getMessage(), e);
            return null;
        }
    }

    /**
     * Obtiene listado de Hhpp por Suscriptor
     *
     * @param suscriptor por el cual se desea filtrar hhpp
     * @return listado de hhpp
     * @author Juan David Hernandez Torres
     */
    public List<HhppMgl> findHhppBySuscriptor(String suscriptor) {
        try {
            TypedQuery<HhppMgl> query = entityManager.createQuery("SELECT g FROM HhppMgl g WHERE "
                    + "g.suscriptor =:suscriptor AND g.estadoRegistro = 1 ",
                    HhppMgl.class);
            query.setParameter("suscriptor", suscriptor);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");// jpa 2.0
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.error("Error al momento de consultar el HHPP. EX000: " + e.getMessage(), e);
            return null;
        }
    }

    /**
     * Busca un HHPP en repositorio por Id de Dirección y subdireccion.
     *
     * @param dirId
     * @param subDirId id del objeto subdireccion por el que se desea buscar
     * @return HhppMgl si la unidad Existe en el repositorio
     * @author Juan David Hernandez
     */
    public List<HhppMgl> findHhppByDirIdSubDirId(BigDecimal dirId, BigDecimal subDirId) {
        try {

            String sqlQuery = "SELECT g FROM HhppMgl g WHERE "
                    + "g.direccionObj.dirId =:dirId  AND g.estadoRegistro = 1 ";
            if (subDirId != null) {
                sqlQuery += "AND g.SubDireccionObj.sdiId =:subDirId ";
            } else {
                sqlQuery += "AND g.SubDireccionObj IS NULL ";
            }

            TypedQuery<HhppMgl> query = entityManager.createQuery(sqlQuery,
                    HhppMgl.class);
            query.setParameter("dirId", dirId);
            if (subDirId != null) {
                query.setParameter("subDirId", subDirId);
            }
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");// jpa 2.0
            entityManager.clear();
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.error("Error al momento de consultar el HHPP. EX000: " + e.getMessage(), e);
            return null;
        }
    }

    /**
     * Cambia el estado del registro de un HHPP en repositoriol para dar a esto
     * como una reactivación.
     *
     * @param hhppId  id del hhpp que se desea reactivar
     * @param usuario que realiza la reactivación del hhpp
     * @param perfil  que realiza la reactivación del hhpp
     *
     *                return true si se actualiza correctamente el registro.
     *
     * @author Juan David Hernandez
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public boolean reactivacionHhppMgl(BigDecimal hhppId, String usuario, int perfil) throws ApplicationException {
        try {
            entityManager.getTransaction().begin();
            Query q = entityManager.createQuery("UPDATE HhppMgl c SET c.estadoRegistro = :estadoRegistro, "
                    + "c.hhpUsuarioModificacion= :usuario, c.perfilEdicion= :perfilEdicion, "
                    + "c.hhpFechaModificacion =:fechaEdicion, c.ehhId =:estadoHhpp "
                    + "WHERE  c.hhpId = :hhppId ");

            q.setParameter("hhppId", hhppId);
            q.setParameter("fechaEdicion", new Date());
            q.setParameter("usuario", usuario);
            q.setParameter("perfilEdicion", perfil);
            q.setParameter("estadoRegistro", 1);
            q.setParameter("estadoHhpp", "PO");

            q.executeUpdate();
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            return false;
        }
    }

    public List<HhppMgl> findBy_SubEdifi_TecSubEdifi(BigDecimal idSubEdifi, BigDecimal idTecSubEdifi) {
        List<HhppMgl> hpmgl = null;
        Query consulta = entityManager.createQuery("SELECT h FROM HhppMgl h  "
                + " WHERE h.hhppSubEdificioObj.subEdificioId = :idSubEdifi "
                + " AND h.cmtTecnologiaSubId.tecnoSubedificioId = :idTecSubEdifi ");
        consulta.setParameter("idSubEdifi", idSubEdifi);
        consulta.setParameter("idTecSubEdifi", idTecSubEdifi);
        hpmgl = consulta.getResultList();
        return hpmgl;

    }

    public HhppMgl findHhppByDirAndSubDir(OtHhppMgl otHhpp) {
        List<HhppMgl> hpmgl = null;
        Query consulta = entityManager.createQuery("SELECT h FROM HhppMgl h "
                + "WHERE h.direccionObj= :direccion AND "
                + "h.SubDireccionObj= :subDireccion");
        consulta.setParameter("direccion", otHhpp.getDireccionId());
        consulta.setParameter("subDireccion", otHhpp.getSubDireccionId());
        hpmgl = (List<HhppMgl>) consulta.getResultList();

        if (!consulta.getResultList().isEmpty()) {
            return hpmgl.get(0);
        }

        return null;

    }

    /**
     * Metodo para buscar los hhpp apartir de una direccion y una subdireccion
     *
     * @author Orlando Velasquez
     * @param dir
     * @param subDir
     * @return
     */
    public List<HhppMgl> findByDirAndSubDir(DireccionMgl dir, SubDireccionMgl subDir) {
        List<HhppMgl> hpmgl;

        String sqlQuery = "SELECT h FROM HhppMgl h "
                + "WHERE h.estadoRegistro = 1 ";

        if (dir != null) {
            sqlQuery += "AND h.direccionObj= :direccion ";
        }
        if (subDir != null) {
            sqlQuery += "AND h.SubDireccionObj= :subDireccion ";
        } else {
            sqlQuery += " AND  h.SubDireccionObj is null ";
        }
        Query consulta = entityManager.createQuery(sqlQuery);
        consulta.setParameter("direccion", dir);
        if (subDir != null) {
            consulta.setParameter("subDireccion", subDir);
        }
        consulta.setHint("javax.persistence.cache.storeMode", "REFRESH");// jpa 2.0
        hpmgl = (List<HhppMgl>) consulta.getResultList();
        entityManager.clear();
        return hpmgl;

    }

    /**
     * valbuenayf Metodo para buscar los hhpp de un id direccion
     *
     * @param idDireccion
     *
     * @return
     */
    public List<HhppMgl> findHhppsByDireccion(BigDecimal idDireccion) {
        Query query = entityManager.createNamedQuery("HhppMgl.findHhppsByDireccion");
        query.setParameter("dirId", idDireccion);
        entityManager.clear();
        return query.getResultList();

    }

    public List<HhppMgl> findListHhppCM(FiltroBusquedaDirecccionDto filtroBusquedaDirecccionDto,
            int firstResult, int maxResults, CmtSubEdificioMgl cmtSubEdificioMgl) throws ApplicationException {
        if (cmtSubEdificioMgl == null
                || cmtSubEdificioMgl.getCmtCuentaMatrizMglObj() == null
                || cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getCuentaMatrizId().equals(BigDecimal.ZERO)) {
            throw new ApplicationException("El filtro Sub Edificio es obligatorio");

        }

        String queryString1 = "SELECT h FROM  HhppMgl h,CmtTecnologiaSubMgl t "
                + "where  "
                + "t.tecnoSubedificioId = h.cmtTecnologiaSubId.tecnoSubedificioId  and t.subedificioId.subEdificioId in "
                + "(select e.subEdificioId from CmtSubEdificioMgl e where e.cuentaMatrizObj.cuentaMatrizId  = "
                + cmtSubEdificioMgl.getCuentaMatrizObj().getCuentaMatrizId() + ") ";

        if (filtroBusquedaDirecccionDto.getDireccion() != null
                && !filtroBusquedaDirecccionDto.getDireccion().isEmpty()) {
            queryString1 += " AND  (h.direccionObj.dirFormatoIgac LIKE :direccion OR h.SubDireccionObj.sdiFormatoIgac LIKE :direccion    )";
        }
        Query query1 = entityManager.createQuery(queryString1);
        if (filtroBusquedaDirecccionDto.getDireccion() != null
                && !filtroBusquedaDirecccionDto.getDireccion().isEmpty()) {
            query1.setParameter("direccion", "%" + filtroBusquedaDirecccionDto.getDireccion().toUpperCase() + "%");
        }

        LinkedHashSet<HhppMgl> result = new LinkedHashSet<>();
        List<HhppMgl> listahhpp1 = (List<HhppMgl>) query1.getResultList();
        result.addAll(listahhpp1);
        ArrayList<HhppMgl> listaHpp = new ArrayList<>(result);
        entityManager.clear();
        return listaHpp;

    }

    public List<HhppMgl> findListHhppSub(FiltroBusquedaDirecccionDto filtroBusquedaDirecccionDto,
            int firstResult, int maxResults, CmtSubEdificioMgl cmtSubEdificioMgl) throws ApplicationException {
        if (cmtSubEdificioMgl == null
                || cmtSubEdificioMgl.getCmtCuentaMatrizMglObj() == null
                || cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getCuentaMatrizId().equals(BigDecimal.ZERO)) {
            throw new ApplicationException("El filtro Sub Edificio es obligatorio");

        }

        String queryString1 = "SELECT h FROM  HhppMgl h,CmtTecnologiaSubMgl t "
                + "where  "
                + "t.tecnoSubedificioId = h.cmtTecnologiaSubId.tecnoSubedificioId  and t.subedificioId.subEdificioId in "
                + "(select e.subEdificioId from CmtSubEdificioMgl e where e.subEdificioId  = "
                + cmtSubEdificioMgl.getSubEdificioId() + ") ";

        if (filtroBusquedaDirecccionDto.getDireccion() != null
                && !filtroBusquedaDirecccionDto.getDireccion().isEmpty()) {
            queryString1 += " AND  (h.SubDireccionObj.sdiFormatoIgac LIKE :direccion  OR  h.direccionObj.dirFormatoIgac LIKE :direccion)";
        }

        Query query1 = entityManager.createQuery(queryString1);
        if (filtroBusquedaDirecccionDto.getDireccion() != null
                && !filtroBusquedaDirecccionDto.getDireccion().isEmpty()) {
            query1.setParameter("direccion", "%" + filtroBusquedaDirecccionDto.getDireccion().toUpperCase() + "%");
        }

        LinkedHashSet<HhppMgl> result = new LinkedHashSet<>();
        List<HhppMgl> listahhpp1 = (List<HhppMgl>) query1.getResultList();
        result.addAll(listahhpp1);
        ArrayList<HhppMgl> listaHpp = new ArrayList<>(result);
        entityManager.clear();
        return listaHpp;

    }

    /**
     * Metodo para buscar los hhpp por id del nodo
     *
     * @author Juan David Hernandez
     * @param nodoId
     * @return
     */
    public List<HhppMgl> findHhppByNodoId(BigDecimal nodoId) throws ApplicationException {
        try {
            Query query = entityManager
                    .createQuery("SELECT h FROM HhppMgl h WHERE h.nodId.nodId = :nodId and h.estadoRegistro = 1");

            query.setParameter("nodId", nodoId);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");// jpa 2.0
            entityManager.clear();
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.error("Error al momento de consultar el HHPP. EX000: " + e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    /**
     * Realiza la b&uacute;squeda de HHPP activos a trav&eacute;s de su Nodo,
     * Direcci&oacute;n y SubDirecci&oacute;n.
     *
     * @param idNodo         Identificador del Nodo.
     * @param idDireccion    Identificador de la Direcci&oacute;n.
     * @param idSubDireccion Identificador de la SubDirecci&oacute;n.
     * @return Listado de HHPP coincidente con el Nodo, Direcci&oacute;n y
     *         SubDirecci&oacute;n.
     * @throws ApplicationException
     */
    public List<HhppMgl> findHhppByNodoDireccionYSubDireccion(BigDecimal idNodo, BigDecimal idDireccion,
            BigDecimal idSubDireccion)
            throws ApplicationException {

        List<HhppMgl> resultado = null;

        try {
            Query query = entityManager.createNamedQuery("HhppMgl.findHhppByNodoDireccionYSubDireccion");
            query.setParameter("nodId", idNodo);
            query.setParameter("dirId", idDireccion);
            query.setParameter("sdiId", idSubDireccion);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");// jpa 2.0
            resultado = query.getResultList();
        } catch (IllegalArgumentException | PersistenceException e) {
            String msg = "Se produjo un error al momento de consultar el HHPP en el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }

        return (resultado);
    }

    /**
     * Realiza la b&uacute;squeda de HHPP activos unica direccion y subdireccion
     * no repetidos por varias tecnologias .
     *
     * @param cuentaMatriz
     * @return Listado de HHPP
     * @throws ApplicationException
     */
    public List<HhppMgl> obtenerHhppCMUnicaDirAndSub(CmtCuentaMatrizMgl cuentaMatriz) throws ApplicationException {
        try {
            StringBuilder consulta = new StringBuilder();
            consulta.append("SELECT distinct h.direccionObj.dirId,  h.SubDireccionObj.sdiId")
                    .append(" FROM HhppMgl h")
                    .append("   JOIN h.hhppSubEdificioObj sub")
                    .append(" WHERE sub.cuentaMatrizObj.cuentaMatrizId = :idCuentaMatriz");
            return entityManager.createQuery(consulta.toString())
                    .setParameter("idCuentaMatriz", cuentaMatriz.getCuentaMatrizId())
                    .setHint("javax.persistence.cache.storeMode", "REFRESH")
                    .getResultList();
        } catch (PersistenceException e) {
            LOGGER.error("Error consultando los hhpp por CM", e);
            throw new ApplicationException(e);
        }
    }

    /**
     * Realiza la b&uacute;squeda de HHPP activos unica direccion y subdireccion
     * no repetidos por varias tecnologias por id subedificio .
     *
     * @param HhppSubEdificioId
     * @return Listado de HHPP
     * @throws ApplicationException
     */
    public List<HhppMgl> findHhppBySubEdificioIdUnicaDirAndSub(BigDecimal HhppSubEdificioId) {
        try {
            Query query = entityManager
                    .createQuery("SELECT distinct h.direccionObj.dirId,  h.SubDireccionObj.sdiId"
                            + " FROM HhppMgl h WHERE h.hhppSubEdificioObj.subEdificioId = :HhppSubEdificioId ");
            query.setParameter("HhppSubEdificioId", HhppSubEdificioId);
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.error("Error al momento de consultar el HHPP. EX000: " + e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    /**
     * Realiza la b&uacute;squeda de HHPP activos a trav&eacute;s de su Nodo,
     * Direcci&oacute;n y SubDirecci&oacute;n. Autor: Victor Bocanegra
     *
     * @param idNodo         Identificador del Nodo.
     * @param idDireccion    Identificador de la Direcci&oacute;n.
     * @param idSubDireccion Identificador de la SubDirecci&oacute;n.
     * @return Listado de HHPP coincidente con el Nodo, Direcci&oacute;n y
     *         SubDirecci&oacute;n.
     * @throws ApplicationException
     */
    public List<HhppMgl> findHhppByNodoDireccionAndSubDireccion(BigDecimal idNodo, BigDecimal idDireccion,
            BigDecimal idSubDireccion) throws ApplicationException {

        List<HhppMgl> hpmgl;

        try {
            String sqlQuery = "SELECT h FROM HhppMgl h "
                    + "WHERE h.estadoRegistro = 1 ";

            if (idNodo != null) {
                sqlQuery += "AND h.nodId.nodId = :nodId ";
            }
            if (idDireccion != null) {
                sqlQuery += "AND h.direccionObj.dirId = :dirId ";
            }
            if (idSubDireccion != null) {
                sqlQuery += " AND h.SubDireccionObj.sdiId = :sdiId ";
            } else {
                sqlQuery += " AND  h.SubDireccionObj IS NULL ";
            }
            Query consulta = entityManager.createQuery(sqlQuery);
            consulta.setParameter("nodId", idNodo);
            consulta.setParameter("dirId", idDireccion);
            if (idSubDireccion != null) {
                consulta.setParameter("sdiId", idSubDireccion);
            }

            consulta.setHint("javax.persistence.cache.storeMode", "REFRESH");// jpa 2.0
            hpmgl = (List<HhppMgl>) consulta.getResultList();
        } catch (IllegalArgumentException | PersistenceException e) {
            String msg = "Se produjo un error al momento de consultar el HHPP en el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }

        return hpmgl;

    }

    /**
     * Realiza la b&uacute;squeda de HHPP activos a trav&eacute;s de la
     * tecnologia del Nodo, Direcci&oacute;n y SubDirecci&oacute;n. Autor:
     * Victor Bocanegra
     *
     * @param tecnologia     Identificador de la tecnologia.
     * @param idDireccion    Identificador de la Direcci&oacute;n.
     * @param idSubDireccion Identificador de la SubDirecci&oacute;n.
     * @return Listado de HHPP coincidente con la Tecnologia, Direcci&oacute;n y
     *         SubDirecci&oacute;n.
     * @throws ApplicationException
     */
    public List<HhppMgl> findHhppByTecnoAndDireccionAndSubDireccion(BigDecimal tecnologia, BigDecimal idDireccion,
            BigDecimal idSubDireccion)
            throws ApplicationException {

        List<HhppMgl> hpmgl;

        try {
            String sqlQuery = "SELECT h FROM HhppMgl h "
                    + "WHERE h.estadoRegistro = 1 ";

            if (tecnologia != null) {
                sqlQuery += "AND h.nodId.nodTipo.basicaId = :nodTipo ";
            }
            if (idDireccion != null) {
                sqlQuery += "AND h.direccionObj.dirId = :dirId ";
            }
            if (idSubDireccion != null) {
                sqlQuery += " AND h.SubDireccionObj.sdiId = :sdiId ";
            } else {
                sqlQuery += " AND  h.SubDireccionObj IS NULL ";
            }
            Query consulta = entityManager.createQuery(sqlQuery);
            consulta.setParameter("nodTipo", tecnologia);
            consulta.setParameter("dirId", idDireccion);
            if (idSubDireccion != null) {
                consulta.setParameter("sdiId", idSubDireccion);
            }

            consulta.setHint("javax.persistence.cache.storeMode", "REFRESH");// jpa 2.0
            hpmgl = (List<HhppMgl>) consulta.getResultList();
        } catch (NoResultException e) {
            LOGGER.error("No fue encontrado ningún HHPP: " + e.getMessage());
            return null;
        } catch (Exception e) {
            LOGGER.error("Error al momento de consultar el HHPP. EX000: " + e.getMessage(), e);
            return null;
        }
        return hpmgl;
    }
}
