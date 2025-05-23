package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.dtos.CmtGeograficoPoliticoDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author User
 */
public class GeograficoPoliticoDaoImpl extends GenericDaoImpl<GeograficoPoliticoMgl> {

    private static final Logger LOGGER = LogManager.getLogger(GeograficoPoliticoDaoImpl.class);

    public List<GeograficoPoliticoMgl> findAll() throws ApplicationException {
        List<GeograficoPoliticoMgl> resultList;
        Query query = entityManager.createNamedQuery("GeograficoPoliticoMgl.findAll");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");// jpa 2.0
        resultList = (List<GeograficoPoliticoMgl>) query.getResultList();
        getEntityManager().clear();
        return resultList;
    }

    public List<GeograficoPoliticoMgl> findDptos() throws ApplicationException {
        List<GeograficoPoliticoMgl> resultList;
        Query query = entityManager.createQuery(
                "SELECT g FROM GeograficoPoliticoMgl g WHERE g.gpoTipo='DEPARTAMENTO' and g.estadoRegistro = 1 ORDER BY g.gpoNombre ASC");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");// jpa 2.0
        resultList = (List<GeograficoPoliticoMgl>) query.getResultList();
        getEntityManager().clear();
        return resultList;
    }

    public List<GeograficoPoliticoMgl> findPaises() throws ApplicationException {
        List<GeograficoPoliticoMgl> resultList;
        Query query = entityManager.createQuery(
                "SELECT g FROM GeograficoPoliticoMgl g WHERE g.gpoTipo='PAIS' and g.estadoRegistro = 1 ORDER BY g.gpoNombre ASC");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");// jpa 2.0
        resultList = (List<GeograficoPoliticoMgl>) query.getResultList();
        getEntityManager().clear();
        return resultList;
    }

    public List<GeograficoPoliticoMgl> findCiudades(BigDecimal geoGpoId) throws ApplicationException {
        List<GeograficoPoliticoMgl> resultList;
        Query query = entityManager.createQuery(
                "SELECT g FROM GeograficoPoliticoMgl g WHERE g.geoGpoId = :geoGpoId and g.estadoRegistro = 1 ORDER BY g.gpoNombre ASC");
        query.setParameter("geoGpoId", geoGpoId);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");// jpa 2.0
        resultList = (List<GeograficoPoliticoMgl>) query.getResultList();
        getEntityManager().clear();
        return resultList;
    }

    public List<GeograficoPoliticoMgl> findAllCiudades() throws ApplicationException {
        List<GeograficoPoliticoMgl> resultList;
        Query query = entityManager.createQuery(
                "SELECT g FROM GeograficoPoliticoMgl g WHERE g.gpoTipo='CIUDAD' and g.estadoRegistro = 1 ORDER BY g.gpoNombre ASC");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");// jpa 2.0
        resultList = (List<GeograficoPoliticoMgl>) query.getResultList();
        getEntityManager().clear();
        return resultList;
    }

    public List<GeograficoPoliticoMgl> findCentrosPoblados(BigDecimal geoGpoId) throws ApplicationException {
        List<GeograficoPoliticoMgl> resultList;
        Query query = entityManager.createNamedQuery("GeograficoPoliticoMgl.findCentrosPoblados");
        query.setParameter("myGeoGpoId", geoGpoId);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");// jpa 2.0
        resultList = (List<GeograficoPoliticoMgl>) query.getResultList();
        getEntityManager().clear();
        return resultList;
    }

    public List<GeograficoPoliticoMgl> findCentroPoblado(BigDecimal geoGpoId) throws ApplicationException {
        List<GeograficoPoliticoMgl> resultList;
        Query query = entityManager.createQuery(
                "SELECT g FROM GeograficoPoliticoMgl g WHERE g.geoGpoId = :geoGpoId and g.estadoRegistro = 1 ORDER BY g.gpoNombre ASC");
        query.setParameter("geoGpoId", geoGpoId);
        resultList = (List<GeograficoPoliticoMgl>) query.getResultList();
        getEntityManager().clear();
        return resultList;
    }

    public String esMultiorigen(BigDecimal gpoId) throws ApplicationException {
        String result;
        Query query = entityManager.createQuery(
                "SELECT g.gpoMultiorigen FROM GeograficoPoliticoMgl g WHERE g.gpoId = :gpoId and g.estadoRegistro = 1 ORDER BY g.gpoNombre ASC");
        query.setParameter("gpoId", gpoId);
        result = (String) query.getSingleResult();
        return result;
    }

    public List<GeograficoPoliticoMgl> findNombre(String nombre) throws ApplicationException {
        List<GeograficoPoliticoMgl> resultList;
        Query query = entityManager.createQuery(
                "SELECT g FROM GeograficoPoliticoMgl g WHERE UPPER(g.gpoNombre) like :gpoNombre and g.estadoRegistro = 1 ORDER BY g.gpoNombre ASC  ");
        query.setParameter("gpoNombre", "%" + nombre.toUpperCase() + "%");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");// jpa 2.0
        resultList = (List<GeograficoPoliticoMgl>) query.getResultList();
        getEntityManager().clear();
        return resultList;
    }

    public List<GeograficoPoliticoMgl> findCiudadesDepartamentos() throws ApplicationException {
        List<GeograficoPoliticoMgl> resultList;
        Query query = entityManager
                .createQuery("SELECT g FROM GeograficoPoliticoMgl g where g.estadoRegistro = 1 ORDER BY g.gpoTipo ASC");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");// jpa 2.0
        resultList = (List<GeograficoPoliticoMgl>) query.getResultList();
        getEntityManager().clear();
        return resultList;
    }

    /**
     * Metodo de consulta de ciudades por codigo DANE
     *
     * @param geoCodigoDane
     * @return
     * @throws ApplicationException
     */
    public GeograficoPoliticoMgl findCityByCodDane(String geoCodigoDane) throws ApplicationException {
        return findGeograficoPoliticoByCodDane("CENTRO POBLADO", geoCodigoDane);
    }

    public GeograficoPoliticoMgl findCityByCodDaneCp(String geoCodigoDane) throws ApplicationException {
        GeograficoPoliticoMgl result;
        Query query = entityManager.createQuery(
                "SELECT g FROM GeograficoPoliticoMgl g WHERE g.geoCodigoDane = :geoCodigoDane AND UPPER(g.gpoTipo) = 'CENTRO POBLADO' and g.estadoRegistro = 1");
        query.setParameter("geoCodigoDane", geoCodigoDane);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");// jpa 2.0
        result = (GeograficoPoliticoMgl) query.getSingleResult();
        getEntityManager().clear();
        return result;
    }

    /**
     * @param geoCodigoDane
     *
     * @return GeograficoPoliticoMgl
     *
     * @throws ApplicationException
     */
    public GeograficoPoliticoMgl findPopulationCenterByCodDane(String geoCodigoDane)
            throws ApplicationException {
        return findGeograficoPoliticoByCodDane("CENTRO POBLADO", geoCodigoDane);
    }

    /**
     * Metodo para consulta de registro geografico_politico por tipo y codigo
     * DANE
     *
     * @param tipoGeo tipo de registro
     * @param codDane codigo DANE
     * @return {@link GeograficoPoliticoMgl} registro encontrado
     * @throws ApplicationException Excepcion relanzada al no encotrar registro
     */
    private GeograficoPoliticoMgl findGeograficoPoliticoByCodDane(String tipoGeo,
            String codDane) throws ApplicationException {
        StringBuilder sentence = new StringBuilder();
        sentence.append("SELECT g FROM GeograficoPoliticoMgl g")
                .append(" WHERE g.gpoId = :gpoId and g.estadoRegistro = 1");
        Query query = entityManager.createQuery(sentence.toString());
        query.setParameter("gpoId", new BigDecimal(codDane));
        return (GeograficoPoliticoMgl) query.getSingleResult();
    }

    public GeograficoPoliticoMgl findCityByComundidad(String comunidad) throws ApplicationException {
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        entityManager.clear();
        entityManager.flush();
        GeograficoPoliticoMgl result = null;
        TypedQuery<GeograficoPoliticoMgl> query = entityManager.createQuery("SELECT g FROM GeograficoPoliticoMgl g "
                + "WHERE g.geoCodigoDane = :comunidad "
                + "AND  UPPER(g.gpoTipo) = 'CENTRO POBLADO' and g.estadoRegistro = 1",
                GeograficoPoliticoMgl.class);
        query.setParameter("comunidad", comunidad);
        List<GeograficoPoliticoMgl> list = query.getResultList();
        if (!list.isEmpty()) {
            result = list.get(0);
        }
        entityManager.getTransaction().commit();
        return result;
    }

    public List<GeograficoPoliticoMgl> findCiudadesPaginacion(int firstResult, int maxResults,
            BigDecimal geoGpoId) throws ApplicationException {
        List<GeograficoPoliticoMgl> resultList;
        Query query = entityManager.createQuery("SELECT g "
                + "FROM GeograficoPoliticoMgl g WHERE g.geoGpoId= :geoGpoId and g.estadoRegistro = 1 "
                + "ORDER BY g.gpoNombre ASC");
        query.setParameter("geoGpoId", geoGpoId);
        query.setFirstResult(firstResult);
        query.setMaxResults(maxResults);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");// jpa 2.0
        resultList = (List<GeograficoPoliticoMgl>) query.getResultList();
        getEntityManager().clear();
        return resultList;
    }

    public int countCiudades(BigDecimal geoGpoId) throws ApplicationException {
        int resultList;
        Query query = entityManager.createQuery("SELECT Count(1) "
                + "FROM GeograficoPoliticoMgl g WHERE g.geoGpoId= :geoGpoId and g.estadoRegistro = 1 "
                + "ORDER BY g.gpoNombre ASC");
        query.setParameter("geoGpoId", geoGpoId);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");// jpa 2.0
        resultList = query.getSingleResult() == null ? 0
                : ((Long) query.getSingleResult()).intValue();
        getEntityManager().clear();
        return resultList;
    }

    /**
     * valbuenayf Metodo para buscar la centro poblado por el codigo dane
     *
     * @param geoCodigoDane
     * @return
     */
    public GeograficoPoliticoMgl findCentroPobladoCodDane(String geoCodigoDane) {
        try {
            String sql = "SELECT g FROM GeograficoPoliticoMgl g WHERE g.geoCodigoDane = :geoCodigoDane AND UPPER(g.gpoTipo) = :gpoTipo and g.estadoRegistro = 1";
            Query query = entityManager.createQuery(sql);
            query.setParameter("geoCodigoDane", geoCodigoDane.trim());
            query.setParameter("gpoTipo", "CENTRO POBLADO");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");// jpa 2.0
            return (GeograficoPoliticoMgl) query.getSingleResult();
        } catch (EntityNotFoundException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            return null;
        } catch (NoResultException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            return null;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            return null;
        }
    }

    /**
     * valbuenayf Metodo para buscar la ciudad por el codigo dane
     *
     * @param geoCodigoDane
     * @return
     */
    public GeograficoPoliticoMgl findCiudadCodDane(String geoCodigoDane) {
        try {
            String sql = "SELECT g FROM GeograficoPoliticoMgl g WHERE g.geoCodigoDane = :geoCodigoDane AND UPPER(g.gpoTipo) = :gpoTipo and g.estadoRegistro = 1 ";
            Query query = entityManager.createQuery(sql);
            query.setParameter("geoCodigoDane", geoCodigoDane.trim());
            query.setParameter("gpoTipo", "CENTRO POBLADO");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");// jpa 2.0
            return (GeograficoPoliticoMgl) query.getSingleResult();
        } catch (NoResultException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            return null;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            return null;
        }
    }

    /**
     * valbuenayf Metodo para buscar el departamento por un id centro poblado
     * (o) idciudad
     *
     * @param idCiuCenPob
     * @return
     */
    public GeograficoPoliticoMgl findDepartamentoIdCiudadCenPob(BigDecimal idCiuCenPob) {
        Query query;
        try {
            String sql = "SELECT g FROM GeograficoPoliticoMgl g WHERE g.gpoId = :idCiuCenPob and g.estadoRegistro = 1 ";
            query = entityManager.createQuery(sql);
            query.setParameter("idCiuCenPob", idCiuCenPob);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");// jpa 2.0
            GeograficoPoliticoMgl result = (GeograficoPoliticoMgl) query.getSingleResult();
            if (result != null) {
                if (result.getGpoTipo().toUpperCase().trim().equals("DEPARTAMENTO")) {
                    return result;
                } else {
                    query = entityManager.createQuery(sql);
                    query.setParameter("idCiuCenPob", result.getGpoId());
                    query.setHint("javax.persistence.cache.storeMode", "REFRESH");// jpa 2.0
                    return (GeograficoPoliticoMgl) query.getSingleResult();
                }
            }
            return result;
        } catch (NoResultException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            return null;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            return null;
        }
    }

    /**
     * valbuenayf Metodo para buscar el pais por el id
     *
     * @param idPais
     * @return
     */
    public GeograficoPoliticoMgl findPaisId(BigDecimal idPais) {
        Query query;
        try {
            String sql = "SELECT g FROM GeograficoPoliticoMgl g WHERE g.gpoId = :idPais AND UPPER(g.gpoTipo) = :gpoTipo and g.estadoRegistro = 1";
            query = entityManager.createQuery(sql);
            query.setParameter("idPais", idPais);
            query.setParameter("gpoTipo", "PAIS");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");// jpa 2.0
            return (GeograficoPoliticoMgl) query.getSingleResult();
        } catch (NoResultException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            return null;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            return null;
        }
    }

    /**
     * Metodo para consultar centro poblado principal por id de ciudad
     *
     * @param idCiudad identificador del registro de ciudad
     * @return {@link GeograficoPoliticoMgl} registro de centro poblado
     * @throws ApplicationException excepcion de registros inexistentes
     */
    public GeograficoPoliticoMgl obtenerCentroPobladoPorCiudad(BigDecimal idCiudad)
            throws ApplicationException {
        try {
            StringBuilder query = new StringBuilder();
            query.append("SELECT cp")
                    .append(" FROM GeograficoPoliticoMgl cp,")
                    .append("   GeograficoPoliticoMgl city")
                    .append(" WHERE city.gpoTipo = 'CIUDAD'")
                    .append("   AND cp.geoGpoId = city.gpoId")
                    .append("   AND cp.geoCodigoDane = city.geoCodigoDane")
                    .append("   AND city.gpoId = :idCiudad and cp.estadoRegistro = 1 and city.estadoRegistro = 1");
            return (GeograficoPoliticoMgl) entityManager.createQuery(query.toString())
                    .setParameter("idCiudad", idCiudad)
                    .getSingleResult();
        } catch (PersistenceException e) {
            LOGGER.error(String.format("No se encontro centro poblado para la ciudad %s", idCiudad), e);
            throw new ApplicationException(String.format("No se encontro centro poblado para la ciudad %s", idCiudad));
        }
    }

    /**
     * Metodo para consultar ciudad por codigo DANEl Autor: Victor Bocanegra
     *
     * @param codigoDane
     * @return {@link GeograficoPoliticoMgl} registro de ciudad
     * @throws ApplicationException excepcion de registros inexistentes
     */
    public GeograficoPoliticoMgl findCityByCodigoDane(String codigoDane) throws ApplicationException {
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        entityManager.clear();
        entityManager.flush();
        GeograficoPoliticoMgl result = null;
        TypedQuery<GeograficoPoliticoMgl> query = entityManager.createQuery("SELECT g FROM GeograficoPoliticoMgl g "
                + "  WHERE g.geoCodigoDane= :geoCodigoDane "
                + "  AND UPPER(g.gpoTipo) = 'CIUDAD' and g.estadoRegistro = 1",
                GeograficoPoliticoMgl.class);
        query.setParameter("geoCodigoDane", codigoDane);
        List<GeograficoPoliticoMgl> list = query.getResultList();
        if (!list.isEmpty()) {
            result = list.get(0);
        }
        entityManager.getTransaction().commit();
        return result;
    }

    /**
     * Metodo para consultar GeograficoPolito por codigo Autor: aleal
     *
     * @param id
     * @return {@link GeograficoPoliticoMgl} registro de codigoDANE
     * @throws ApplicationException excepcion de registros inexistentes
     */
    public GeograficoPoliticoMgl findGeoPoliticoById(BigDecimal id) throws ApplicationException {
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        entityManager.clear();
        entityManager.flush();
        GeograficoPoliticoMgl result = null;
        TypedQuery<GeograficoPoliticoMgl> query = entityManager.createQuery("SELECT g FROM GeograficoPoliticoMgl g "
                + "  WHERE g.gpoId = :gpoId and g.estadoRegistro = 1",
                GeograficoPoliticoMgl.class);
        query.setParameter("gpoId", id);
        GeograficoPoliticoMgl geoPolitico = query.getSingleResult();
        result = geoPolitico;
        entityManager.getTransaction().commit();
        return result;
    }

    /**
     * Metodo para consultar GeograficoPolito por codigo de Ciudad Autor: aleal
     *
     * @param codigo
     * @return {@link GeograficoPoliticoMgl} registro de codigo Ciudad
     * @throws ApplicationException excepcion de registros inexistentes
     */
    public GeograficoPoliticoMgl findGeoPoliticoByCodigo(String codigo) throws ApplicationException {
        if (!entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().begin();
        }
        entityManager.clear();
        entityManager.flush();
        GeograficoPoliticoMgl result = null;
        TypedQuery<GeograficoPoliticoMgl> query = entityManager.createQuery("SELECT g FROM GeograficoPoliticoMgl g "
                + "  WHERE g.gpoCodigo = :gpoCodigo and g.estadoRegistro = 1",
                GeograficoPoliticoMgl.class);
        query.setParameter("gpoCodigo", codigo);
        GeograficoPoliticoMgl geoPolitico = query.getSingleResult();
        result = geoPolitico;
        entityManager.getTransaction().commit();
        return result;
    }

    public GeograficoPoliticoMgl findCiudadByCentroPoblado(BigDecimal geoGpoId) throws ApplicationException {

        GeograficoPoliticoMgl result = new GeograficoPoliticoMgl();
        Query query = entityManager.createQuery("SELECT g "
                + "FROM GeograficoPoliticoMgl g WHERE g.gpoId= :geoGpoId"
                + " AND g.gpoTipo='CIUDAD' and g.estadoRegistro = 1");
        query.setParameter("geoGpoId", geoGpoId);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");// jpa 2.0
        List<GeograficoPoliticoMgl> resultList = query.getResultList();
        if (!resultList.isEmpty()) {
            result = (GeograficoPoliticoMgl) resultList.get(0);
        }
        getEntityManager().clear();
        return result;

    }

    public GeograficoPoliticoMgl findDepartamentoByCiudad(BigDecimal geoGpoId) throws ApplicationException {

        GeograficoPoliticoMgl result = new GeograficoPoliticoMgl();
        Query query = entityManager.createQuery("SELECT g "
                + "FROM GeograficoPoliticoMgl g WHERE g.gpoId= :geoGpoId"
                + " AND g.gpoTipo='DEPARTAMENTO' and g.estadoRegistro = 1");
        query.setParameter("geoGpoId", geoGpoId);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");// jpa 2.0
        List<GeograficoPoliticoMgl> resultList = query.getResultList();
        if (!resultList.isEmpty()) {
            result = (GeograficoPoliticoMgl) resultList.get(0);
        }
        getEntityManager().clear();
        return result;

    }

    /**
     * Metodo para consultar geopolitico por nombre y tipo Autor: Victor
     * Bocanegra
     *
     * @param nombre
     * @param tipo
     * @return {@link GeograficoPoliticoMgl}
     * @throws ApplicationException excepcion de registros inexistentes
     */
    public GeograficoPoliticoMgl findGeoPolByNombreAndTipo(String nombre, String tipo)
            throws ApplicationException {

        GeograficoPoliticoMgl result = new GeograficoPoliticoMgl();
        Query query = entityManager.createQuery("SELECT g "
                + "FROM GeograficoPoliticoMgl g WHERE g.gpoNombre= :gpoNombre"
                + " AND g.gpoTipo= :gpoTipo and g.estadoRegistro = 1");
        query.setParameter("gpoNombre", nombre);
        query.setParameter("gpoTipo", tipo);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");// jpa 2.0
        List<GeograficoPoliticoMgl> resultList = query.getResultList();
        if (!resultList.isEmpty()) {
            result = (GeograficoPoliticoMgl) resultList.get(0);
        }
        getEntityManager().clear();
        return result;

    }

    public GeograficoPoliticoMgl findDepartamentoId(BigDecimal geoGpoId) throws ApplicationException {

        GeograficoPoliticoMgl result = new GeograficoPoliticoMgl();
        Query query = entityManager.createQuery("SELECT g "
                + "FROM GeograficoPoliticoMgl g WHERE g.gpoId= :geoGpoId"
                + " AND g.gpoTipo='DEPARTAMENTO' and g.estadoRegistro = 1");
        query.setParameter("geoGpoId", geoGpoId);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");// jpa 2.0
        List<GeograficoPoliticoMgl> resultList = query.getResultList();
        if (!resultList.isEmpty()) {
            result = (GeograficoPoliticoMgl) resultList.get(0);
        }
        getEntityManager().clear();
        return result;

    }

    /**
     * Metodo para consultar los nombres de todos los centro poblados Autor:
     * Victor Bocanegra
     *
     * @return {@link List<String>}
     * @throws ApplicationException excepcion de registros inexistentes
     */
    public List<String> findNamesCentroPoblado() throws ApplicationException {

        List<String> result;
        Query query = entityManager.createQuery("SELECT DISTINCT g.gpoNombre "
                + "FROM GeograficoPoliticoMgl g WHERE g.gpoTipo='CENTRO POBLADO'"
                + " AND g.estadoRegistro = 1 ORDER BY g.gpoNombre");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");// jpa 2.0
        result = query.getResultList();
        getEntityManager().clear();
        return result;

    }

    /**
     * Metodo para consultar centro poblados por nombre Autor: Victor Bocanegra
     *
     * @return {@link List<String>}
     * @throws ApplicationException excepcion de registros inexistentes
     */
    public List<GeograficoPoliticoMgl> findCentroPobladosByNombre(String nombre) throws ApplicationException {
        List<GeograficoPoliticoMgl> resultList;
        Query query = entityManager.createQuery("SELECT g FROM GeograficoPoliticoMgl g "
                + " WHERE UPPER(g.gpoNombre) = :gpoNombre and g.estadoRegistro = 1 "
                + " AND g.gpoTipo='CENTRO POBLADO'"
                + " ORDER BY g.gpoNombre ASC  ");
        query.setParameter("gpoNombre", nombre.toUpperCase());
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");// jpa 2.0
        resultList = (List<GeograficoPoliticoMgl>) query.getResultList();
        getEntityManager().clear();
        return resultList;
    }

    /**
     * Metodo para consultar los nombres de todos los centro poblados
     * 
     * @cardenaslb
     *
     * @return {@link List<String>}
     * @throws ApplicationException excepcion de registros inexistentes
     */
    public List<GeograficoPoliticoMgl> findListCentroPoblado() throws ApplicationException {

        List<GeograficoPoliticoMgl> result;
        Query query = entityManager.createQuery("SELECT g "
                + "FROM GeograficoPoliticoMgl g WHERE g.gpoTipo='CENTRO POBLADO'"
                + " AND g.estadoRegistro = 1 ORDER BY g.gpoNombre");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");// jpa 2.0
        result = query.getResultList();
        getEntityManager().clear();
        return result;

    }

    /**
     * Autor: Victor Bocanegra
     * Metodo para consultar Lista centro poblados Autor: Victor Bocanegra
     *
     * @return {@link List<CmtGeograficoPoliticoDto>}
     * @throws ApplicationException excepcion de registros inexistentes
     */
    public List<CmtGeograficoPoliticoDto> findAllCentroPoblados() throws ApplicationException {

        List<CmtGeograficoPoliticoDto> resultList;

        Query query = entityManager.createQuery("SELECT dp.gpoNombre, mu.gpoNombre, cp.gpoNombre,"
                + " cp.geoCodigoDane, cp.gpoId "
                + " FROM GeograficoPoliticoMgl cp "
                + " JOIN  GeograficoPoliticoMgl mu ON cp.geoGpoId = mu.gpoId  "
                + " JOIN GeograficoPoliticoMgl dp  ON mu.geoGpoId = dp.gpoId "
                + " WHERE cp.estadoRegistro = 1 "
                + " AND cp.gpoTipo='CENTRO POBLADO'"
                + " ORDER BY cp.gpoNombre ASC  ");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");// jpa 2.0

        List<Object[]> rows = query.getResultList();
        resultList = new ArrayList<>();
        if (rows != null && !rows.isEmpty()) {
            for (Object[] row : rows) {
                CmtGeograficoPoliticoDto cmtGeograficoPoliticoDto = new CmtGeograficoPoliticoDto();
                cmtGeograficoPoliticoDto.setNombreDepartamento((row[0]) != null ? (row[0]).toString() : "");
                cmtGeograficoPoliticoDto.setNombreCiudad((row[1]) != null ? (row[1]).toString() : "");
                cmtGeograficoPoliticoDto.setNombreCentro((row[2]) != null ? (row[2]).toString() : "");
                cmtGeograficoPoliticoDto.setCodigoDane((row[3]) != null ? (row[3]).toString() : "");
                cmtGeograficoPoliticoDto.setCentroPobladoId((BigDecimal) (row[4]));
                resultList.add(cmtGeograficoPoliticoDto);
            }
        }
        getEntityManager().clear();
        return resultList;
    }

    public List<CmtGeograficoPoliticoDto> findCentroPobladosById(List<String> listaIdCentroPoblado) {
        List<CmtGeograficoPoliticoDto> resultList;

        StringBuilder queryJpql = new StringBuilder("");
        queryJpql.append("SELECT dp.gpoNombre, mu.gpoNombre, cp.gpoNombre, ");
        queryJpql.append("cp.geoCodigoDane, cp.gpoId ");
        queryJpql.append("FROM GeograficoPoliticoMgl cp ");
        queryJpql.append("JOIN  GeograficoPoliticoMgl mu ON cp.geoGpoId = mu.gpoId ");
        queryJpql.append("JOIN GeograficoPoliticoMgl dp  ON mu.geoGpoId = dp.gpoId ");
        queryJpql.append("WHERE cp.estadoRegistro = 1 ");
        queryJpql.append("AND cp.gpoTipo='CENTRO POBLADO' ");
        queryJpql.append("AND cp.gpoId IN :listacentroPoblado ");
        queryJpql.append("ORDER BY cp.gpoNombre ASC ");

        Query query = entityManager.createQuery(queryJpql.toString());
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");// jpa 2.0
        query.setParameter("listacentroPoblado", listaIdCentroPoblado);

        List<Object[]> resultadosGeografico = query.getResultList();
        resultList = new ArrayList<>();

        if (resultadosGeografico != null && (!resultadosGeografico.isEmpty())) {
            for (Object[] item : resultadosGeografico) {
                CmtGeograficoPoliticoDto cmtGeograficoPoliticoDto = new CmtGeograficoPoliticoDto();
                cmtGeograficoPoliticoDto.setNombreDepartamento((item[0]) != null ? (item[0]).toString() : "");
                cmtGeograficoPoliticoDto.setNombreCiudad((item[1]) != null ? (item[1]).toString() : "");
                cmtGeograficoPoliticoDto.setNombreCentro((item[2]) != null ? (item[2]).toString() : "");
                cmtGeograficoPoliticoDto.setCodigoDane((item[3]) != null ? (item[3]).toString() : "");
                cmtGeograficoPoliticoDto.setCentroPobladoId((BigDecimal) (item[4]));
                resultList.add(cmtGeograficoPoliticoDto);
            }
        }
        getEntityManager().clear();
        return resultList;
    }
}
