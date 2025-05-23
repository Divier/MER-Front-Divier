/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.dtos.CmtFiltroConsultaComunidadDto;
import co.com.claro.mgl.dtos.CmtFiltroConsultaComunidadesRrDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtComunidadRr;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

/**
 *
 * @author valbuenayf
 */
public class CmtComunidadRrDaoImpl extends GenericDaoImpl<CmtComunidadRr> {

    private static final Logger LOGGER = LogManager.getLogger(CmtComunidadRrDaoImpl.class);

    /**
     * valbuenayf Metodo para la comunidad y su regional
     *
     * @param idCiudad
     * @param codigoTecnologia
     * @return
     */
    public CmtComunidadRr findComunidadRegional(BigDecimal idCiudad, String codigoTecnologia) {
        CmtComunidadRr comunidadRr;
        try {
            Query query = entityManager.createNamedQuery("CmtComunidadRr.findByComunidadRegional");
            query.setParameter("idCiudad", idCiudad);
            query.setParameter("codigoTecnologia", codigoTecnologia);
            comunidadRr = (CmtComunidadRr) query.getSingleResult();
            getEntityManager().clear();
        } catch (NonUniqueResultException e) {
            LOGGER.error("Error findComunidadRegional de CmtComunidadRrDaoImpl hay mas de un registro " + e.getMessage());
            return null;
        } catch (NoResultException e) {
            LOGGER.error("Error findComunidadRegional de CmtComunidadRrDaoImpl no hay registros " + e.getMessage());
            return null;
        } catch (Exception e) {
            LOGGER.error("Error findComunidadRegional de CmtComunidadRrDaoImpl " + e.getMessage());
            return null;
        }
        return comunidadRr;
    }

    /**
     * Victor Bocanegra Metodo para trar la comunidad por el codigo RR
     *
     * @param codigoRR
     * @return CmtComunidadRr
     */
    public CmtComunidadRr findComunidadByCodigo(String codigoRR) {
        CmtComunidadRr comunidadRr;
        try {
            Query query = entityManager.createNamedQuery("CmtComunidadRr.findComunidadByCodigo");
            query.setParameter("codigoRr", codigoRR);
            comunidadRr = (CmtComunidadRr) query.getSingleResult();
            getEntityManager().clear();
        } catch (NonUniqueResultException e) {
            LOGGER.error("Error findComunidadRegional de CmtComunidadRrDaoImpl hay mas de un registro " + e.getMessage());
            return null;
        } catch (NoResultException e) {
            LOGGER.error("Error findComunidadRegional de CmtComunidadRrDaoImpl no hay registros " + e.getMessage());
            return null;
        } catch (Exception e) {
            LOGGER.error("Error findComunidadRegional de CmtComunidadRrDaoImpl " + e.getMessage());
            return null;
        }
        return comunidadRr;
    }

    /**
     * Victor Bocanegra Metodo para traer todas las comunidades
     *
     * @return List<CmtComunidadRr>
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public List<CmtComunidadRr> findAll() throws ApplicationException {

        List<CmtComunidadRr> lstResult;
        try {
            Query query = entityManager.createNamedQuery("CmtComunidadRr.findAll");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            lstResult = (List<CmtComunidadRr>) query.getResultList();
            getEntityManager().clear();
            return lstResult;
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(ex.getMessage());
        }
    }

    /**
     * *Victor Bocanegra Metodo para contar las comunidades por los filtros de
     * la tabla
     *
     * @param consulta
     * @return Long
     * @throws ApplicationException
     */
    public Long countByComunidadFiltro(CmtFiltroConsultaComunidadesRrDto consulta)
            throws ApplicationException {

        Long resultCount = 0L;
        String queryTipo;

        queryTipo = "SELECT COUNT(1) FROM CmtComunidadRr n WHERE n.estadoRegistro = 1 ";

        if (consulta.getNombreComunidad() != null && !consulta.getNombreComunidad().isEmpty()) {
            queryTipo += "AND UPPER(n.nombreComunidad) LIKE :nombreComunidad";
        }
        if (consulta.getCodigoComunidad() != null && !consulta.getCodigoComunidad().isEmpty()) {
            queryTipo += "AND UPPER(n.codigoRr) LIKE :codigoRr ";
        }

        Query query = entityManager.createQuery(queryTipo);

        if (consulta.getNombreComunidad() != null && !consulta.getNombreComunidad().isEmpty()) {
            query.setParameter("nombreComunidad", "%" + consulta.getNombreComunidad().toUpperCase() + "%");
        }
        if (consulta.getCodigoComunidad() != null && !consulta.getCodigoComunidad().isEmpty()) {
            query.setParameter("codigoRr", "%" + consulta.getCodigoComunidad().toUpperCase() + "%");
        }

        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultCount = (Long) query.getSingleResult();
        return resultCount;
    }

    /**
     * *Victor Bocanegra Metodo para buscar las comunidades por los filtros de
     * la tabla
     *
     * @param paginaSelected
     * @param maxResults
     * @param consulta
     * @return List<CmtComunidadRr>
     * @throws ApplicationException
     */
    public List<CmtComunidadRr> findComunidadByFiltro(int paginaSelected,
            int maxResults, CmtFiltroConsultaComunidadesRrDto consulta)
            throws ApplicationException {

        List<CmtComunidadRr> resultList;


        String queryTipo = "SELECT n FROM CmtComunidadRr n WHERE n.estadoRegistro = 1 ";

        if (consulta.getNombreComunidad() != null && !consulta.getNombreComunidad().isEmpty()) {
            queryTipo += "AND UPPER(n.nombreComunidad) LIKE :nombreComunidad";
        }
        if (consulta.getCodigoComunidad() != null && !consulta.getCodigoComunidad().isEmpty()) {
            queryTipo += "AND UPPER(n.codigoRr) LIKE :codigoRr ";
        }

        Query query = entityManager.createQuery(queryTipo);

        if (consulta.getNombreComunidad() != null && !consulta.getNombreComunidad().isEmpty()) {
            query.setParameter("nombreComunidad", "%" + consulta.getNombreComunidad().toUpperCase() + "%");
        }
        if (consulta.getCodigoComunidad() != null && !consulta.getCodigoComunidad().isEmpty()) {
            query.setParameter("codigoRr", "%" + consulta.getCodigoComunidad().toUpperCase() + "%");
        }
        query.setFirstResult(paginaSelected);
        query.setMaxResults(maxResults);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<CmtComunidadRr>) query.getResultList();
        getEntityManager().clear();
        return resultList;
    }

    /**
     * *Metodo para contar las comunidades por los filtros de la
     * tabla
     *
     * @param paginaSelected
     * @param maxResults
     * @param consulta
     * @return Long
     * @throws ApplicationException
     */
    public List<CmtComunidadRr> findByFiltro(int paginaSelected,
            int maxResults, CmtFiltroConsultaComunidadDto consulta) throws ApplicationException {
        List<CmtComunidadRr> resultList;

        String queryTipo = "SELECT c FROM CmtComunidadRr c  WHERE  c.estadoRegistro = 1  ";

        if (consulta.getCodigoRr() != null && !consulta.getCodigoRr().isEmpty()) {
            queryTipo += "AND  UPPER(c.codigoRr) LIKE  :codigoRr ";
        }
        if (consulta.getNombreComunidad() != null && !consulta.getNombreComunidad().isEmpty()) {
            queryTipo += "AND  UPPER(c.nombreComunidad) LIKE  :nombreComunidad ";
        }
        if (consulta.getNombreCortoRegional()!= null && !consulta.getNombreCortoRegional().isEmpty()) {
            queryTipo += "AND  UPPER(c.nombreCortoRegional) LIKE  :nombreCortoRegional ";
        }
        if (consulta.getTecnologia()!= null && !consulta.getTecnologia().isEmpty()) {
            queryTipo += "AND  UPPER(c.tecnologia.abreviatura) LIKE  :tecnologia ";
        }
        if (consulta.getRegionalRr()!= null && !consulta.getRegionalRr().isEmpty()) {
            queryTipo += "AND  UPPER(c.regionalRr.nombreRegional) LIKE  :regionalRr ";
        }
        if (consulta.getCodigoPostal()!= null && !consulta.getCodigoPostal().isEmpty()) {
            queryTipo += "AND  UPPER(c.codigoPostal) LIKE  :codigoPostal ";
        }
        if (consulta.getUbicacion()!= null && !consulta.getUbicacion().isEmpty()) {            
            queryTipo += "AND  UPPER(c.ubicacion) LIKE  :ubicacion ";
        }

        Query query = entityManager.createQuery(queryTipo);

        if (consulta.getCodigoRr() != null && !consulta.getCodigoRr().isEmpty()) {
            query.setParameter("codigoRr",  "%" + consulta.getCodigoRr() +"%");
        }
        if (consulta.getNombreComunidad() != null && !consulta.getNombreComunidad().isEmpty()) {
            query.setParameter("nombreComunidad",  "%" + consulta.getNombreComunidad() +"%");
        }
        if (consulta.getNombreCortoRegional() != null && !consulta.getNombreCortoRegional().isEmpty()) {
            query.setParameter("nombreCortoRegional",  "%" + consulta.getNombreCortoRegional() +"%");
        }
        if (consulta.getTecnologia() != null && !consulta.getTecnologia().isEmpty()) {
            query.setParameter("tecnologia",  "%" + consulta.getTecnologia() +"%");
        }
        if (consulta.getRegionalRr() != null && !consulta.getRegionalRr().isEmpty()) {
            query.setParameter("regionalRr",  "%" + consulta.getRegionalRr() +"%");
        }
        if (consulta.getCodigoPostal() != null && !consulta.getCodigoPostal().isEmpty()) {
            query.setParameter("codigoPostal",  "%" + consulta.getCodigoPostal() +"%");
        }
        if (consulta.getUbicacion() != null && !consulta.getUbicacion().isEmpty()) {
            int ubicacion = 0;
            if (consulta.getUbicacion().equals("Rural")) {
                ubicacion = 1;
            }else if(consulta.getUbicacion().equals("Urbano")){
                ubicacion =2;
            }
            query.setParameter("ubicacion",  "%" + ubicacion +"%");
        }
        
        query.setFirstResult(paginaSelected);
        query.setMaxResults(maxResults);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<CmtComunidadRr>) query.getResultList();
        getEntityManager().clear();
        return resultList;
    }

    /**
     * Metodo para contar las comunidades por los filtros de la
     * tabla
     *
     * @param consulta
     * @return Long
     * @throws ApplicationException
     */
   public Long countByComFiltro(CmtFiltroConsultaComunidadDto consulta)
            throws ApplicationException {

        Long resultCount = 0L;
        String queryTipo = " SELECT COUNT(1)  FROM CmtComunidadRr c  WHERE  c.estadoRegistro = 1  ";

        if (consulta.getCodigoRr() != null && !consulta.getCodigoRr().isEmpty()) {
            queryTipo += "AND  c.codigoRr =  :codigoRr ";
        }
        if (consulta.getNombreComunidad() != null && !consulta.getNombreComunidad().isEmpty()) {
            queryTipo += "AND  c.nombreComunidad =  :nombreComunidad ";
        }
        if (consulta.getNombreCortoRegional()!= null && !consulta.getNombreCortoRegional().isEmpty()) {
            queryTipo += "AND  c.nombreCortoRegional =  :nombreCortoRegional ";
        }
        if (consulta.getTecnologia()!= null && !consulta.getTecnologia().isEmpty()) {
            queryTipo += "AND  c.tecnologia.abreviatura =  :tecnologia ";
        }
        if (consulta.getRegionalRr()!= null && !consulta.getRegionalRr().isEmpty()) {
            queryTipo += "AND  c.regionalRr.nombreRegional =  :regionalRr ";
        }
        if (consulta.getCodigoPostal()!= null && !consulta.getCodigoPostal().isEmpty()) {
            queryTipo += "AND  c.codigoPostal =  :codigoPostal ";
        }
        if (consulta.getUbicacion()!= null && !consulta.getUbicacion().isEmpty()) {
            queryTipo += "AND  c.ubicacion =  :ubicacion ";
        }

        Query query = entityManager.createQuery(queryTipo);

        if (consulta.getCodigoRr() != null && !consulta.getCodigoRr().isEmpty()) {
            query.setParameter("codigoRr", consulta.getCodigoRr());
        }
        if (consulta.getNombreComunidad() != null && !consulta.getNombreComunidad().isEmpty()) {
            query.setParameter("nombreComunidad", consulta.getNombreComunidad());
        }
        if (consulta.getNombreCortoRegional() != null && !consulta.getNombreCortoRegional().isEmpty()) {
            query.setParameter("nombreCortoRegional", consulta.getNombreCortoRegional());
        }
        if (consulta.getTecnologia() != null && !consulta.getTecnologia().isEmpty()) {
            query.setParameter("tecnologia", consulta.getTecnologia());
        }
        if (consulta.getRegionalRr() != null && !consulta.getRegionalRr().isEmpty()) {
            query.setParameter("regionalRr", consulta.getRegionalRr());
        }
        if (consulta.getCodigoPostal() != null && !consulta.getCodigoPostal().isEmpty()) {
            query.setParameter("codigoPostal", consulta.getCodigoPostal());
        }
        if (consulta.getUbicacion() != null && !consulta.getUbicacion().isEmpty()) {
            BigInteger ubicacion = new BigInteger("0");
            if(consulta.getUbicacion().equalsIgnoreCase("Rural")){
                ubicacion = new BigInteger("1");
            }else if(consulta.getUbicacion().equals("Urbano")){
                ubicacion = new BigInteger("2");
            }
            query.setParameter("ubicacion", ubicacion);
        }

        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultCount = (Long) query.getSingleResult();
        return resultCount;
    }
   
     /**
     * *Victor Bocanegra Metodo para buscar las comunidades de una region
     *
     * @param idRegional
     * @return List<CmtComunidadRr>
     * @throws ApplicationException
     */
    public List<CmtComunidadRr> findByIdRegional(BigDecimal idRegional)
            throws ApplicationException {

        List<CmtComunidadRr> resultList;

        String queryTipo = "SELECT n FROM CmtComunidadRr n WHERE n.estadoRegistro = 1 "
                + "AND n.regionalRr.regionalRrId = :regionalRrId and n.ciudad is not null ORDER BY n.nombreComunidad ";

        Query query = entityManager.createQuery(queryTipo);

        if (idRegional != null) {
            query.setParameter("regionalRrId", idRegional);
        }

        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<CmtComunidadRr>) query.getResultList();
        getEntityManager().clear();
        return resultList;
    }
    
     public List<CmtComunidadRr> findByListRegional(List<BigDecimal> idRegional) throws ApplicationException {
               List<CmtComunidadRr> resultList;

        String queryTipo = "SELECT n FROM CmtComunidadRr n WHERE n.estadoRegistro = 1 "
                + "AND n.regionalRr.regionalRrId  IN :listRegionales and n.ciudad is not null ORDER BY n.nombreComunidad ";

        Query query = entityManager.createQuery(queryTipo);

        if (idRegional != null) {
            query.setParameter("listRegionales", idRegional);
        }

        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<CmtComunidadRr>) query.getResultList();

        return resultList;
         
     }

    public List<CmtComunidadRr> findByListComunidad(List<BigDecimal> idComunidad) throws ApplicationException {
        List<CmtComunidadRr> resultList;

        String queryTipo = "SELECT n FROM CmtComunidadRr n WHERE n.estadoRegistro = 1 "
                + "AND n.comunidadRrId  IN :listComunidades and n.ciudad is not null ORDER BY n.nombreComunidad ";

        Query query = entityManager.createQuery(queryTipo);

        if (idComunidad != null) {
            query.setParameter("listComunidades", idComunidad);
        }

        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<CmtComunidadRr>) query.getResultList();

        return resultList;

    }
}
