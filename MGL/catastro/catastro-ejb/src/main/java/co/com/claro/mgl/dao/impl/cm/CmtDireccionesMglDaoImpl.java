package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.DireccionMgl;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;


/**
 *
 * @author User
 */
public class CmtDireccionesMglDaoImpl extends GenericDaoImpl<CmtDireccionMgl> {

    private static final Logger LOGGER = LogManager.getLogger(CmtDireccionesMglDaoImpl.class);

    public List<CmtDireccionMgl> findAll() throws ApplicationException {
        List<CmtDireccionMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtDireccionMgl.findAll");
        resultList = (List<CmtDireccionMgl>) query.getResultList();
        return resultList;
    }

    public CmtDireccionMgl findByIdSolicitud(BigDecimal id) throws ApplicationException {
        try {
            String query = "select * from "+Constant.MGL_DATABASE_SCHEMA+"."+ "CMT_DIRECCION where id_solicitud = " + id + "";
            Query q = entityManager.createNativeQuery(query, CmtDireccionMgl.class);
            List<CmtDireccionMgl> result = (List<CmtDireccionMgl>) q.getResultList();
            return result.get(0);
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(ex.getMessage(), ex);
        }
    }

    public CmtDireccionMgl findByIdSubEdificio(BigDecimal id) throws ApplicationException {
        try {
            String query = "select * from "+Constant.MGL_DATABASE_SCHEMA+"."+ "CMT_DIRECCION where subedificio_id = " + id + "";
            Query q = entityManager.createNativeQuery(query, CmtDireccionMgl.class);
            List<CmtDireccionMgl> result = (List<CmtDireccionMgl>) q.getResultList();
            if (result != null && result.size() > 0) {
                return result.get(0);
            } else {
                return null;
            }
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(ex.getMessage(), ex);
        }
    }

    public CmtDireccionMgl findDirAlt(String idDirCatastro) throws ApplicationException {
        CmtDireccionMgl result;
        String queryDir = "SELECT c FROM CmtDireccionMgl c WHERE c.idDirCatastro = :idDirCatastro";

        Query query = entityManager.createQuery(queryDir, CmtDireccionMgl.class);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");

        query.setParameter("idDirCatastro", idDirCatastro);
        result = (CmtDireccionMgl) query.getSingleResult();
        return result;
    }

    /**
     * El metodo findAny es empleado en la busqueda de direcciones alternas
     * validando campo a campo la direccion.
     *
     * @author alejandro.martine.ext@claro.com.co
     *
     * @param newAltAddress
     * @return boolean
     * @throws ApplicationException
     */
    public boolean findAny(CmtDireccionMgl newAltAddress) throws ApplicationException {

        List<CmtCuentaMatrizMgl> resultList;

        String queryDir = "SELECT d FROM CmtDireccionMgl d WHERE d.codTipoDir = :idTipoDireccion";

        if (newAltAddress.getCodTipoDir().equals("CK")) {
            if (newAltAddress.getTipoViaPrincipal() != null && !newAltAddress.getTipoViaPrincipal().trim().isEmpty()) {
                queryDir = queryDir.concat(" AND d.tipoViaPrincipal = :tipoViaPrincipal");
            }

            if (newAltAddress.getNumViaPrincipal() != null && !newAltAddress.getNumViaPrincipal().trim().isEmpty()) {
                queryDir = queryDir.concat(" AND d.numViaPrincipal = :numViaPrincipal");
            }

            if (newAltAddress.getLtViaPrincipal() != null && !newAltAddress.getLtViaPrincipal().trim().isEmpty()) {
                queryDir = queryDir.concat(" AND d.ltViaPrincipal = :ltViaPrincipal");
            }

            if (newAltAddress.getNlPostViaP() != null && !newAltAddress.getNlPostViaP().trim().isEmpty()) {
                queryDir = queryDir.concat(" AND d.nlPostViaP = :nlPostViaP");
            }

            if (newAltAddress.getBisViaPrincipal() != null && !newAltAddress.getBisViaPrincipal().trim().isEmpty()) {
                queryDir = queryDir.concat(" AND d.bisViaPrincipal = :bisViaPrincipal");
            }

            if (newAltAddress.getCuadViaPrincipal() != null && !newAltAddress.getCuadViaPrincipal().trim().isEmpty()) {
                queryDir = queryDir.concat(" AND d.cuadViaPrincipal = :cuadViaPrincipal");
            }

            if (newAltAddress.getTipoViaGeneradora() != null && !newAltAddress.getTipoViaGeneradora().trim().isEmpty()) {
                queryDir = queryDir.concat(" AND d.tipoViaGeneradora = :tipoViaGeneradora");
            }

            if (newAltAddress.getNumViaGeneradora() != null && !newAltAddress.getNumViaGeneradora().trim().isEmpty()) {
                queryDir = queryDir.concat(" AND d.numViaGeneradora = :numViaGeneradora");
            }

            if (newAltAddress.getLtViaGeneradora() != null && !newAltAddress.getLtViaGeneradora().trim().isEmpty()) {
                queryDir = queryDir.concat(" AND d.ltViaGeneradora = :ltViaGeneradora");
            }

            if (newAltAddress.getNlPostViaG() != null && !newAltAddress.getNlPostViaG().trim().isEmpty()) {
                queryDir = queryDir.concat(" AND d.nlPostViaG = :nlPostViaG");
            }

            if (newAltAddress.getBisViaGeneradora() != null && !newAltAddress.getBisViaGeneradora().trim().isEmpty()) {
                queryDir = queryDir.concat(" AND d.bisViaGeneradora = :bisViaGeneradora");
            }

            if (newAltAddress.getCuadViaGeneradora() != null && !newAltAddress.getCuadViaGeneradora().trim().isEmpty()) {
                queryDir = queryDir.concat(" AND d.cuadViaGeneradora = :cuadViaGeneradora");
            }

            if (newAltAddress.getPlacaDireccion() != null && !newAltAddress.getPlacaDireccion().trim().isEmpty()) {
                queryDir = queryDir.concat(" AND d.placaDireccion = :placaDireccion");
            }
        }

        if (newAltAddress.getCodTipoDir().equals("BM")) {
            if (newAltAddress.getMzTipoNivel1() != null && !newAltAddress.getMzTipoNivel1().trim().isEmpty()) {
                queryDir += "AND  a.mzTipoNivel1    = :mzTipoNivel1 ";
            }
            if (newAltAddress.getMzTipoNivel2() != null && !newAltAddress.getMzTipoNivel2().trim().isEmpty()) {
                queryDir += "AND  a.mzTipoNivel2    = :mzTipoNivel2 ";
            }
            if (newAltAddress.getMzTipoNivel3() != null && !newAltAddress.getMzTipoNivel3().trim().isEmpty()) {
                queryDir += "AND  a.mzTipoNivel3    = :mzTipoNivel3  ";
            }
            if (newAltAddress.getMzTipoNivel4() != null && !newAltAddress.getMzTipoNivel4().trim().isEmpty()) {
                queryDir += "AND  a.mzTipoNivel4    = :mzTipoNivel4 ";
            }
            if (newAltAddress.getMzTipoNivel5() != null && !newAltAddress.getMzTipoNivel5().trim().isEmpty()) {
                queryDir += "AND  a.mzTipoNivel5    = :mzTipoNivel5 ";
            }
            if (newAltAddress.getMzValorNivel1() != null && !newAltAddress.getMzValorNivel1().trim().isEmpty()) {
                queryDir += "AND  a.mzValorNivel1    = :mzValorNivel1 ";
            }
            if (newAltAddress.getMzValorNivel2() != null && !newAltAddress.getMzValorNivel2().trim().isEmpty()) {
                queryDir += "AND  a.mzValorNivel2    = :mzValorNivel2 ";
            }
            if (newAltAddress.getMzValorNivel3() != null && !newAltAddress.getMzValorNivel3().trim().isEmpty()) {
                queryDir += "AND  a.mzValorNivel3    = :mzValorNivel3 ";
            }
            if (newAltAddress.getMzValorNivel4() != null && !newAltAddress.getMzValorNivel4().trim().isEmpty()) {
                queryDir += "AND  a.mzValorNivel4    = :mzValorNivel4 ";
            }
            if (newAltAddress.getMzValorNivel5() != null && !newAltAddress.getMzValorNivel5().trim().isEmpty()) {
                queryDir += "AND  a.mzValorNivel5    = :mzValorNivel5 ";
            }
        }

        if (newAltAddress.getCodTipoDir().equals("IT")) {
            if (newAltAddress.getCpTipoNivel1() != null && !newAltAddress.getCpTipoNivel1().trim().isEmpty()) {
                queryDir += "AND  a.cpTipoNivel1    = :cpTipoNivel1 ";
            }
            if (newAltAddress.getCpTipoNivel2() != null && !newAltAddress.getCpTipoNivel2().trim().isEmpty()) {
                queryDir += "AND  a.cpTipoNivel2    = :cpTipoNivel2 ";
            }
            if (newAltAddress.getCpTipoNivel3() != null && !newAltAddress.getCpTipoNivel3().trim().isEmpty()) {
                queryDir += "AND  a.cpTipoNivel3    = :cpTipoNivel3 ";
            }
            if (newAltAddress.getCpTipoNivel4() != null && !newAltAddress.getCpTipoNivel4().trim().isEmpty()) {
                queryDir += "AND  a.cpTipoNivel4    = :cpTipoNivel4 ";
            }
            if (newAltAddress.getCpTipoNivel5() != null && !newAltAddress.getCpTipoNivel5().trim().isEmpty()) {
                queryDir += "AND  a.cpTipoNivel5    = :cpTipoNivel5 ";
            }
            if (newAltAddress.getCpTipoNivel6() != null && !newAltAddress.getCpTipoNivel6().trim().isEmpty()) {
                queryDir += "AND  a.cpTipoNivel6    = :cpTipoNivel6 ";
            }
            if (newAltAddress.getCpValorNivel1() != null && !newAltAddress.getCpValorNivel1().trim().isEmpty()) {
                queryDir += "AND  a.cpValorNivel1 = :cpValorNivel1 ";
            }
            if (newAltAddress.getCpValorNivel2() != null && !newAltAddress.getCpValorNivel2().trim().isEmpty()) {
                queryDir += "AND  a.cpValorNivel2 = :cpValorNivel2 ";
            }
            if (newAltAddress.getCpValorNivel3() != null && !newAltAddress.getCpValorNivel3().trim().isEmpty()) {
                queryDir += "AND  a.cpValorNivel3 = :cpValorNivel3 ";
            }
            if (newAltAddress.getCpValorNivel4() != null && !newAltAddress.getCpValorNivel4().trim().isEmpty()) {
                queryDir += "AND  a.cpValorNivel4 = :cpValorNivel4 ";
            }
            if (newAltAddress.getCpValorNivel5() != null && !newAltAddress.getCpValorNivel5().trim().isEmpty()) {
                queryDir += "AND  a.cpValorNivel5 = :cpValorNivel5 ";
            }
            if (newAltAddress.getCpValorNivel6() != null && !newAltAddress.getCpValorNivel6().trim().isEmpty()) {
                queryDir += "AND  a.cpValorNivel6 = :cpValorNivel6 ";
            }
        }

        Query query = entityManager.createQuery(queryDir, CmtDireccionMgl.class);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");

        if (newAltAddress.getCodTipoDir() != null && !newAltAddress.getCodTipoDir().trim().isEmpty()) {
            query.setParameter("idTipoDireccion", newAltAddress.getCodTipoDir());
        }

        if (newAltAddress.getCodTipoDir().equals("CK")) {

            if (newAltAddress.getTipoViaPrincipal() != null && !newAltAddress.getTipoViaPrincipal().trim().isEmpty()) {
                query.setParameter("tipoViaPrincipal", newAltAddress.getTipoViaPrincipal());
            }

            if (newAltAddress.getNumViaPrincipal() != null && !newAltAddress.getNumViaPrincipal().trim().isEmpty()) {
                query.setParameter("numViaPrincipal", newAltAddress.getNumViaPrincipal());
            }

            if (newAltAddress.getLtViaPrincipal() != null && !newAltAddress.getLtViaPrincipal().trim().isEmpty()) {
                query.setParameter("ltViaPrincipal", newAltAddress.getLtViaPrincipal());
            }

            if (newAltAddress.getNlPostViaP() != null && !newAltAddress.getNlPostViaP().trim().isEmpty()) {
                query.setParameter("nlPostViaP", newAltAddress.getNlPostViaP());
            }

            if (newAltAddress.getBisViaPrincipal() != null && !newAltAddress.getBisViaPrincipal().trim().isEmpty()) {
                query.setParameter("bisViaPrincipal", newAltAddress.getBisViaPrincipal());
            }

            if (newAltAddress.getCuadViaPrincipal() != null && !newAltAddress.getCuadViaPrincipal().trim().isEmpty()) {
                query.setParameter("cuadViaPrincipal", newAltAddress.getCuadViaPrincipal());
            }

            if (newAltAddress.getTipoViaGeneradora() != null && !newAltAddress.getTipoViaGeneradora().trim().isEmpty()) {
                query.setParameter("tipoViaGeneradora", newAltAddress.getTipoViaGeneradora());
            }

            if (newAltAddress.getNumViaGeneradora() != null && !newAltAddress.getNumViaGeneradora().trim().isEmpty()) {
                query.setParameter("numViaGeneradora", newAltAddress.getNumViaGeneradora());
            }

            if (newAltAddress.getLtViaGeneradora() != null && !newAltAddress.getLtViaGeneradora().trim().isEmpty()) {
                query.setParameter("ltViaGeneradora", newAltAddress.getLtViaGeneradora());
            }

            if (newAltAddress.getNlPostViaG() != null && !newAltAddress.getNlPostViaG().trim().isEmpty()) {
                query.setParameter("nlPostViaG", newAltAddress.getNlPostViaG());
            }

            if (newAltAddress.getBisViaGeneradora() != null && !newAltAddress.getBisViaGeneradora().trim().isEmpty()) {
                query.setParameter("bisViaGeneradora", newAltAddress.getBisViaGeneradora());
            }

            if (newAltAddress.getCuadViaGeneradora() != null && !newAltAddress.getCuadViaGeneradora().trim().isEmpty()) {
                query.setParameter("cuadViaGeneradora", newAltAddress.getCuadViaGeneradora());
            }

            if (newAltAddress.getPlacaDireccion() != null && !newAltAddress.getPlacaDireccion().trim().isEmpty()) {
                query.setParameter("placaDireccion", newAltAddress.getPlacaDireccion());
            }
        }

        if (newAltAddress.getCodTipoDir().equals("BM")) {
            if (newAltAddress.getMzTipoNivel1() != null && !newAltAddress.getMzTipoNivel1().trim().isEmpty()) {
                query.setParameter("mzTipoNivel1", newAltAddress.getMzTipoNivel1());
            }
            if (newAltAddress.getMzTipoNivel2() != null && !newAltAddress.getMzTipoNivel2().trim().isEmpty()) {
                query.setParameter("mzTipoNivel2", newAltAddress.getMzTipoNivel2());
            }
            if (newAltAddress.getMzTipoNivel3() != null && !newAltAddress.getMzTipoNivel3().trim().isEmpty()) {
                query.setParameter("mzTipoNivel3", newAltAddress.getMzTipoNivel3());
            }
            if (newAltAddress.getMzTipoNivel4() != null && !newAltAddress.getMzTipoNivel4().trim().isEmpty()) {
                query.setParameter("mzTipoNivel4", newAltAddress.getMzTipoNivel4());
            }
            if (newAltAddress.getMzTipoNivel5() != null && !newAltAddress.getMzTipoNivel5().trim().isEmpty()) {
                query.setParameter("mzTipoNivel5", newAltAddress.getMzTipoNivel5());
            }
            if (newAltAddress.getMzValorNivel1() != null && !newAltAddress.getMzValorNivel1().trim().isEmpty()) {
                query.setParameter("mzValorNivel1", newAltAddress.getMzValorNivel1());
            }
            if (newAltAddress.getMzValorNivel2() != null && !newAltAddress.getMzValorNivel2().trim().isEmpty()) {
                query.setParameter("mzValorNivel2", newAltAddress.getMzValorNivel2());
            }
            if (newAltAddress.getMzValorNivel3() != null && !newAltAddress.getMzValorNivel3().trim().isEmpty()) {
                query.setParameter("mzValorNivel3", newAltAddress.getMzValorNivel3());
            }
            if (newAltAddress.getMzValorNivel4() != null && !newAltAddress.getMzValorNivel4().trim().isEmpty()) {
                query.setParameter("mzValorNivel4", newAltAddress.getMzValorNivel4());
            }
            if (newAltAddress.getMzValorNivel5() != null && !newAltAddress.getMzValorNivel5().trim().isEmpty()) {
                query.setParameter("mzValorNivel5", newAltAddress.getMzValorNivel5());
            }
        }

        if (newAltAddress.getCodTipoDir().equals("IT")) {
            if (newAltAddress.getCpTipoNivel1() != null && !newAltAddress.getCpTipoNivel1().trim().isEmpty()) {
                query.setParameter("cpTipoNivel1", newAltAddress.getCpTipoNivel1());
            }
            if (newAltAddress.getCpTipoNivel2() != null && !newAltAddress.getCpTipoNivel2().trim().isEmpty()) {
                query.setParameter("cpTipoNivel2", newAltAddress.getCpTipoNivel2());
            }
            if (newAltAddress.getCpTipoNivel3() != null && !newAltAddress.getCpTipoNivel3().trim().isEmpty()) {
                query.setParameter("cpTipoNivel3", newAltAddress.getCpTipoNivel3());
            }
            if (newAltAddress.getCpTipoNivel4() != null && !newAltAddress.getCpTipoNivel4().trim().isEmpty()) {
                query.setParameter("cpTipoNivel4", newAltAddress.getCpTipoNivel4());
            }
            if (newAltAddress.getCpTipoNivel5() != null && !newAltAddress.getCpTipoNivel5().trim().isEmpty()) {
                query.setParameter("cpTipoNivel5", newAltAddress.getCpTipoNivel5());
            }
            if (newAltAddress.getCpTipoNivel6() != null && !newAltAddress.getCpTipoNivel6().trim().isEmpty()) {
                query.setParameter("cpTipoNivel6", newAltAddress.getCpTipoNivel6());
            }

            if (newAltAddress.getCpValorNivel1() != null && !newAltAddress.getCpValorNivel1().trim().isEmpty()) {
                query.setParameter("cpValorNivel1", newAltAddress.getCpValorNivel1());
            }
            if (newAltAddress.getCpValorNivel2() != null && !newAltAddress.getCpValorNivel2().trim().isEmpty()) {
                query.setParameter("cpValorNivel2", newAltAddress.getCpValorNivel2());
            }
            if (newAltAddress.getCpValorNivel3() != null && !newAltAddress.getCpValorNivel3().trim().isEmpty()) {
                query.setParameter("cpValorNivel3", newAltAddress.getCpValorNivel3());
            }
            if (newAltAddress.getCpValorNivel4() != null && !newAltAddress.getCpValorNivel4().trim().isEmpty()) {
                query.setParameter("cpValorNivel4", newAltAddress.getCpValorNivel4());
            }
            if (newAltAddress.getCpValorNivel5() != null && !newAltAddress.getCpValorNivel5().trim().isEmpty()) {
                query.setParameter("cpValorNivel5", newAltAddress.getCpValorNivel5());
            }
            if (newAltAddress.getCpValorNivel6() != null && !newAltAddress.getCpValorNivel6().trim().isEmpty()) {
                query.setParameter("cpValorNivel6", newAltAddress.getCpValorNivel6());
            }
        }

        resultList = (List<CmtCuentaMatrizMgl>) query.getResultList();
        if (resultList == null || resultList.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public List<CmtDireccionMgl> findDireccionesByCuentaMatriz(CmtCuentaMatrizMgl cuentaMatriz) throws ApplicationException {
        List<CmtDireccionMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtDireccionMgl.findByCuentaMatriz");
        query.setParameter("cuentaMatrizId", cuentaMatriz.getCuentaMatrizId());
        resultList = (List<CmtDireccionMgl>) query.getResultList();
        return resultList;
    }

    /**
     *
     * @param cmtCuentaMatrizMgl
     * @param firstResult
     * @param numberOfResult
     * @param type
     * @return
     * @throws ApplicationException
     */
    public List<CmtDireccionMgl> findByCuentaMatriz(CmtCuentaMatrizMgl cmtCuentaMatrizMgl,
            int firstResult, int numberOfResult, Constant.TYPE_QUERY type) throws ApplicationException {
        List<CmtDireccionMgl> resultList;
        try {
            Query q = entityManager.createQuery("SELECT d FROM CmtDireccionMgl d "
                    + "WHERE  d.cuentaMatrizObj = :cuentaMatrizObj  and d.estadoRegistro=1 AND d.tdiId<>3 "
                    + "ORDER BY d.tdiId desc ");
            if (type == Constant.TYPE_QUERY.PER_PAGE) {
                q.setFirstResult(firstResult);
                q.setMaxResults(numberOfResult);
            }
            q.setParameter("cuentaMatrizObj", cmtCuentaMatrizMgl);
            q.setHint("javax.persistence.cache.storeMode", "REFRESH");
            resultList = (List<CmtDireccionMgl>) q.getResultList();
            return resultList;
        } catch (NoResultException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            return new ArrayList<>();
        }
    }

    public List<CmtDireccionMgl> findBySubEdificio(CmtSubEdificioMgl cmtSubEdificioMgl) throws ApplicationException {
        try {
            List<CmtDireccionMgl> resultList;
            Query query = entityManager.createNamedQuery("CmtDireccionMgl.findBySubEdificio");
            query.setParameter("subEdificio", cmtSubEdificioMgl.getSubEdificioId());
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            resultList = (List<CmtDireccionMgl>) query.getResultList();
            return resultList;
        } catch (NoResultException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            return new ArrayList<>();
        }
    }
    
        public List<CmtDireccionMgl> findByCuentaMatriz(CmtSubEdificioMgl cmtSubEdificioMgl) throws ApplicationException {
        try {
            List<CmtDireccionMgl> resultList;
            Query query = entityManager.createNamedQuery("CmtDireccionMgl.findByCuentaMatriz");
            query.setParameter("cuentaMatrizId", cmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getCuentaMatrizId());
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            resultList = (List<CmtDireccionMgl>) query.getResultList();
            return resultList;
        } catch (NoResultException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            return new ArrayList<>();
        }
    }

    public Long countFindByCm(CmtCuentaMatrizMgl cmtCuentaMatrizMgl)
            throws ApplicationException {
        Query q = entityManager.createQuery("SELECT COUNT(1) FROM CmtDireccionMgl d "
                + "WHERE  d.cuentaMatrizObj = :cuentaMatrizObj  and d.estadoRegistro=1 AND d.tdiId<>3 ");
        q.setParameter("cuentaMatrizObj", cmtCuentaMatrizMgl);
        q.setHint("javax.persistence.cache.storeMode", "REFRESH");
        return (Long) q.getSingleResult();
    }

    public List<CmtCuentaMatrizMgl> findByDrDireccion(DrDireccion drDireccion, String centroPoblado)
            throws ApplicationException {
        List<CmtCuentaMatrizMgl> resultList;
        try {
            String querySql = "SELECT s FROM CmtCuentaMatrizMgl s ";
            querySql += " inner JOIN  s.direccionesList a ";
            querySql += " inner JOIN  s.centroPoblado  c ";
            querySql += "WHERE a.estadoRegistro=1 AND s.estadoRegistro=1 AND c.geoCodigoDane = :comunidad ";

            if (drDireccion.getIdTipoDireccion() != null && !drDireccion.getIdTipoDireccion().trim().isEmpty()) {
                querySql += "AND UPPER(a.codTipoDir) = UPPER(:idTipoDireccion) ";
            }
            if (drDireccion.getBarrio() != null && !drDireccion.getBarrio().trim().isEmpty()) {
                querySql += "AND  UPPER(a.barrio)   = UPPER(:barrio) ";
            }
            
            if (drDireccion.getIdTipoDireccion() != null) {
                if (drDireccion.getIdTipoDireccion().equals("CK")) {
                    if (drDireccion.getTipoViaPrincipal() != null && !drDireccion.getTipoViaPrincipal().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.tipoViaPrincipal)    = UPPER(:tipoViaPrincipal) ";
                    }
                    if (drDireccion.getNumViaPrincipal() != null && !drDireccion.getNumViaPrincipal().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.numViaPrincipal)    = UPPER(:numViaPrincipal) ";
                    }
                    if (drDireccion.getLtViaPrincipal() != null && !drDireccion.getLtViaPrincipal().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.ltViaPrincipal)    = UPPER(:ltViaPrincipal) ";
                    }
                    if (drDireccion.getNlPostViaP() != null && !drDireccion.getNlPostViaP().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.nlPostViaP)    = UPPER(:nlPostViaP) ";
                    }
                    if (drDireccion.getBisViaPrincipal() != null && !drDireccion.getBisViaPrincipal().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.bisViaPrincipal)    = UPPER(:bisViaPrincipal) ";
                    }
                    if (drDireccion.getCuadViaPrincipal() != null && !drDireccion.getCuadViaPrincipal().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.cuadViaPrincipal)    = UPPER(:cuadViaPrincipal)  ";
                    }
                    if (drDireccion.getPlacaDireccion() != null && !drDireccion.getPlacaDireccion().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.placaDireccion)    = UPPER(:placaDireccion) ";
                    }
                    if (drDireccion.getTipoViaGeneradora() != null && !drDireccion.getTipoViaGeneradora().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.tipoViaGeneradora)    = UPPER(:tipoViaGeneradora) ";
                    }
                    if (drDireccion.getNumViaGeneradora() != null && !drDireccion.getNumViaGeneradora().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.numViaGeneradora)    = UPPER(:numViaGeneradora) ";
                    }
                    if (drDireccion.getLtViaGeneradora() != null && !drDireccion.getLtViaGeneradora().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.ltViaGeneradora)    = UPPER(:ltViaGeneradora) ";
                    }
                    if (drDireccion.getNlPostViaG() != null && !drDireccion.getNlPostViaG().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.nlPostViaG)    = UPPER(:nlPostViaG) ";
                    }
                    if (drDireccion.getBisViaGeneradora() != null && !drDireccion.getBisViaGeneradora().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.bisViaGeneradora)    = UPPER(:bisViaGeneradora) ";
                    }
                    if (drDireccion.getCuadViaGeneradora() != null && !drDireccion.getCuadViaGeneradora().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.cuadViaGeneradora)    = UPPER(:cuadViaGeneradora) ";
                    }
                }
                if (drDireccion.getIdTipoDireccion().equals("BM")
                        || drDireccion.getIdTipoDireccion().equals("IT")) {
                    //Direccion Manzana-Casa
                    if (drDireccion.getMzTipoNivel1() != null && !drDireccion.getMzTipoNivel1().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.mzTipoNivel1)    = UPPER(:mzTipoNivel1) ";
                    }
                    if (drDireccion.getMzTipoNivel2() != null && !drDireccion.getMzTipoNivel2().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.mzTipoNivel2)    = UPPER(:mzTipoNivel2) ";
                    }
                    if (drDireccion.getMzTipoNivel3() != null && !drDireccion.getMzTipoNivel3().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.mzTipoNivel3)    = UPPER(:mzTipoNivel3)  ";
                    }
                    if (drDireccion.getMzTipoNivel4() != null && !drDireccion.getMzTipoNivel4().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.mzTipoNivel4)    = UPPER(:mzTipoNivel4) ";
                    }
                    if (drDireccion.getMzTipoNivel5() != null && !drDireccion.getMzTipoNivel5().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.mzTipoNivel5)    = UPPER(:mzTipoNivel5) ";
                    }
                    if (drDireccion.getMzValorNivel1() != null && !drDireccion.getMzValorNivel1().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.mzValorNivel1)    = UPPER(:mzValorNivel1) ";
                    }
                    if (drDireccion.getMzValorNivel2() != null && !drDireccion.getMzValorNivel2().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.mzValorNivel2)    = UPPER(:mzValorNivel2) ";
                    }
                    if (drDireccion.getMzValorNivel3() != null && !drDireccion.getMzValorNivel3().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.mzValorNivel3)    = UPPER(:mzValorNivel3) ";
                    }
                    if (drDireccion.getMzValorNivel4() != null && !drDireccion.getMzValorNivel4().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.mzValorNivel4)    = UPPER(:mzValorNivel4) ";
                    }
                    if (drDireccion.getMzValorNivel5() != null && !drDireccion.getMzValorNivel5().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.mzValorNivel5)    = UPPER(:mzValorNivel5) ";
                    }
                }
                if (drDireccion.getIdTipoDireccion().equals("CK")
                        || drDireccion.getIdTipoDireccion().equals("BM")
                        || drDireccion.getIdTipoDireccion().equals("IT")) {
                    //Complemeto o intraduciple
                    if (drDireccion.getCpTipoNivel1() != null && !drDireccion.getCpTipoNivel1().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.cpTipoNivel1)    = UPPER(:cpTipoNivel1) ";
                    }
                    if (drDireccion.getCpTipoNivel2() != null && !drDireccion.getCpTipoNivel2().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.cpTipoNivel2)    = UPPER(:cpTipoNivel2) ";
                    }
                    if (drDireccion.getCpTipoNivel3() != null && !drDireccion.getCpTipoNivel3().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.cpTipoNivel3)    = UPPER(:cpTipoNivel3) ";
                    }
                    if (drDireccion.getCpTipoNivel4() != null && !drDireccion.getCpTipoNivel4().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.cpTipoNivel4)    = UPPER(:cpTipoNivel4) ";
                    }
                    if (drDireccion.getCpTipoNivel5() != null && !drDireccion.getCpTipoNivel5().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.cpTipoNivel5)    = UPPER(:cpTipoNivel5) ";
                    }
                    if (drDireccion.getCpTipoNivel6() != null && !drDireccion.getCpTipoNivel6().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.cpTipoNivel6)    = UPPER(:cpTipoNivel6) ";
                    }

                    if (drDireccion.getCpValorNivel1() != null && !drDireccion.getCpValorNivel1().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.cpValorNivel1)    = UPPER(:cpValorNivel1) ";
                    }
                    if (drDireccion.getCpValorNivel2() != null && !drDireccion.getCpValorNivel2().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.cpValorNivel2)    = UPPER(:cpValorNivel2) ";
                    }
                    if (drDireccion.getCpValorNivel3() != null && !drDireccion.getCpValorNivel3().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.cpValorNivel3)    = UPPER(:cpValorNivel3) ";
                    }
                    if (drDireccion.getCpValorNivel4() != null && !drDireccion.getCpValorNivel4().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.cpValorNivel4)    = UPPER(:cpValorNivel4) ";
                    }
                    if (drDireccion.getCpValorNivel5() != null && !drDireccion.getCpValorNivel5().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.cpValorNivel5)    = UPPER(:cpValorNivel5) ";
                    }
                    if (drDireccion.getCpValorNivel6() != null && !drDireccion.getCpValorNivel6().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.cpValorNivel6)    = UPPER(:cpValorNivel6) ";
                    }
                }
                if (drDireccion.getIdTipoDireccion().equals("IT")) {
                    if (drDireccion.getMzTipoNivel6() != null && !drDireccion.getMzTipoNivel6().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.mzTipoNivel6)    = UPPER(:mzTipoNivel6) ";
                    }
                    if (drDireccion.getMzValorNivel6() != null && !drDireccion.getMzValorNivel6().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.mzValorNivel6)    = UPPER(:mzValorNivel6)  ";
                    }
                    if (drDireccion.getItTipoPlaca() != null && !drDireccion.getItTipoPlaca().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.itTipoPlaca)    = UPPER(:itTipoPlaca) ";
                    }
                    if (drDireccion.getItValorPlaca() != null && !drDireccion.getItValorPlaca().trim().isEmpty()) {
                        querySql += "AND  UPPER(a.itValorPlaca)    = UPPER(:itValorPlaca) ";
                    }
                }
            }

            Query query = entityManager.createQuery(querySql);

            query.setParameter("comunidad", centroPoblado);

            if (drDireccion.getIdTipoDireccion() != null && !drDireccion.getIdTipoDireccion().trim().isEmpty()) {
                query.setParameter("idTipoDireccion", drDireccion.getIdTipoDireccion());
            }
            if (drDireccion.getBarrio() != null && !drDireccion.getBarrio().trim().isEmpty()) {
                query.setParameter("barrio", drDireccion.getBarrio());
            }
            
            if (drDireccion.getIdTipoDireccion() != null) {
                if (drDireccion.getIdTipoDireccion().equals("CK")) {
                    //Direccion Calle - Carrera
                    if (drDireccion.getTipoViaPrincipal() != null && !drDireccion.getTipoViaPrincipal().trim().isEmpty()) {
                        query.setParameter("tipoViaPrincipal", drDireccion.getTipoViaPrincipal());
                    }
                    if (drDireccion.getNumViaPrincipal() != null && !drDireccion.getNumViaPrincipal().trim().isEmpty()) {
                        query.setParameter("numViaPrincipal", drDireccion.getNumViaPrincipal());
                    }
                    if (drDireccion.getLtViaPrincipal() != null && !drDireccion.getLtViaPrincipal().trim().isEmpty()) {
                        query.setParameter("ltViaPrincipal", drDireccion.getLtViaPrincipal());
                    }
                    if (drDireccion.getNlPostViaP() != null && !drDireccion.getNlPostViaP().trim().isEmpty()) {
                        query.setParameter("nlPostViaP", drDireccion.getNlPostViaP());
                    }
                    if (drDireccion.getBisViaPrincipal() != null && !drDireccion.getBisViaPrincipal().trim().isEmpty()) {
                        query.setParameter("bisViaPrincipal", drDireccion.getBisViaPrincipal());
                    }
                    if (drDireccion.getCuadViaPrincipal() != null && !drDireccion.getCuadViaPrincipal().trim().isEmpty()) {
                        query.setParameter("cuadViaPrincipal", drDireccion.getCuadViaPrincipal());
                    }
                    if (drDireccion.getPlacaDireccion() != null && !drDireccion.getPlacaDireccion().trim().isEmpty()) {
                        query.setParameter("placaDireccion", drDireccion.getPlacaDireccion());
                    }
                    if (drDireccion.getTipoViaGeneradora() != null && !drDireccion.getTipoViaGeneradora().trim().isEmpty()) {
                        query.setParameter("tipoViaGeneradora", drDireccion.getTipoViaGeneradora());
                    }
                    if (drDireccion.getNumViaGeneradora() != null && !drDireccion.getNumViaGeneradora().trim().isEmpty()) {
                        query.setParameter("numViaGeneradora", drDireccion.getNumViaGeneradora());
                    }
                    if (drDireccion.getLtViaGeneradora() != null && !drDireccion.getLtViaGeneradora().trim().isEmpty()) {
                        query.setParameter("ltViaGeneradora", drDireccion.getLtViaGeneradora());
                    }
                    if (drDireccion.getNlPostViaG() != null && !drDireccion.getNlPostViaG().trim().isEmpty()) {
                        query.setParameter("nlPostViaG", drDireccion.getNlPostViaG());
                    }
                    if (drDireccion.getBisViaGeneradora() != null && !drDireccion.getBisViaGeneradora().trim().isEmpty()) {
                        query.setParameter("bisViaGeneradora", drDireccion.getBisViaGeneradora());
                    }
                    if (drDireccion.getCuadViaGeneradora() != null && !drDireccion.getCuadViaGeneradora().trim().isEmpty()) {
                        query.setParameter("cuadViaGeneradora", drDireccion.getCuadViaGeneradora());
                    }
                }
                if (drDireccion.getIdTipoDireccion().equals("BM")
                        || drDireccion.getIdTipoDireccion().equals("IT")) {
                    //Direccion Manzana-Casa
                    if (drDireccion.getMzTipoNivel1() != null && !drDireccion.getMzTipoNivel1().trim().isEmpty()) {
                        query.setParameter("mzTipoNivel1", drDireccion.getMzTipoNivel1());
                    }
                    if (drDireccion.getMzTipoNivel2() != null && !drDireccion.getMzTipoNivel2().trim().isEmpty()) {
                        query.setParameter("mzTipoNivel2", drDireccion.getMzTipoNivel2());
                    }
                    if (drDireccion.getMzTipoNivel3() != null && !drDireccion.getMzTipoNivel3().trim().isEmpty()) {
                        query.setParameter("mzTipoNivel3", drDireccion.getMzTipoNivel3());
                    }
                    if (drDireccion.getMzTipoNivel4() != null && !drDireccion.getMzTipoNivel4().trim().isEmpty()) {
                        query.setParameter("mzTipoNivel4", drDireccion.getMzTipoNivel4());
                    }
                    if (drDireccion.getMzTipoNivel5() != null && !drDireccion.getMzTipoNivel5().trim().isEmpty()) {
                        query.setParameter("mzTipoNivel5", drDireccion.getMzTipoNivel5());
                    }
                    if (drDireccion.getMzValorNivel1() != null && !drDireccion.getMzValorNivel1().trim().isEmpty()) {
                        query.setParameter("mzValorNivel1", drDireccion.getMzValorNivel1());
                    }
                    if (drDireccion.getMzValorNivel2() != null && !drDireccion.getMzValorNivel2().trim().isEmpty()) {
                        query.setParameter("mzValorNivel2", drDireccion.getMzValorNivel2());
                    }
                    if (drDireccion.getMzValorNivel3() != null && !drDireccion.getMzValorNivel3().trim().isEmpty()) {
                        query.setParameter("mzValorNivel3", drDireccion.getMzValorNivel3());
                    }
                    if (drDireccion.getMzValorNivel4() != null && !drDireccion.getMzValorNivel4().trim().isEmpty()) {
                        query.setParameter("mzValorNivel4", drDireccion.getMzValorNivel4());
                    }
                    if (drDireccion.getMzValorNivel5() != null && !drDireccion.getMzValorNivel5().trim().isEmpty()) {
                        query.setParameter("mzValorNivel5", drDireccion.getMzValorNivel5());
                    }
                }
                if (drDireccion.getIdTipoDireccion().equals("CK")
                        || drDireccion.getIdTipoDireccion().equals("BM")
                        || drDireccion.getIdTipoDireccion().equals("IT")) {
                    //Complemeto o intraduciple
                    if (drDireccion.getCpTipoNivel1() != null && !drDireccion.getCpTipoNivel1().trim().isEmpty()) {
                        query.setParameter("cpTipoNivel1", drDireccion.getCpTipoNivel1());
                    }
                    if (drDireccion.getCpTipoNivel2() != null && !drDireccion.getCpTipoNivel2().trim().isEmpty()) {
                        query.setParameter("cpTipoNivel2", drDireccion.getCpTipoNivel2());
                    }
                    if (drDireccion.getCpTipoNivel3() != null && !drDireccion.getCpTipoNivel3().trim().isEmpty()) {
                        query.setParameter("cpTipoNivel3", drDireccion.getCpTipoNivel3());
                    }
                    if (drDireccion.getCpTipoNivel4() != null && !drDireccion.getCpTipoNivel4().trim().isEmpty()) {
                        query.setParameter("cpTipoNivel4", drDireccion.getCpTipoNivel4());
                    }
                    if (drDireccion.getCpTipoNivel5() != null && !drDireccion.getCpTipoNivel5().trim().isEmpty()) {
                        query.setParameter("cpTipoNivel5", drDireccion.getCpTipoNivel5());
                    }
                    if (drDireccion.getCpTipoNivel6() != null && !drDireccion.getCpTipoNivel6().trim().isEmpty()) {
                        query.setParameter("cpTipoNivel6", drDireccion.getCpTipoNivel6());
                    }
                    if (drDireccion.getCpValorNivel1() != null && !drDireccion.getCpValorNivel1().trim().isEmpty()) {
                        query.setParameter("cpValorNivel1", drDireccion.getCpValorNivel1());
                    }
                    if (drDireccion.getCpValorNivel2() != null && !drDireccion.getCpValorNivel2().trim().isEmpty()) {
                        query.setParameter("cpValorNivel2", drDireccion.getCpValorNivel2());
                    }
                    if (drDireccion.getCpValorNivel3() != null && !drDireccion.getCpValorNivel3().trim().isEmpty()) {
                        query.setParameter("cpValorNivel3", drDireccion.getCpValorNivel3());
                    }
                    if (drDireccion.getCpValorNivel4() != null && !drDireccion.getCpValorNivel4().trim().isEmpty()) {
                        query.setParameter("cpValorNivel4", drDireccion.getCpValorNivel4());
                    }
                    if (drDireccion.getCpValorNivel5() != null && !drDireccion.getCpValorNivel5().trim().isEmpty()) {
                        query.setParameter("cpValorNivel5", drDireccion.getCpValorNivel5());
                    }
                    if (drDireccion.getCpValorNivel6() != null && !drDireccion.getCpValorNivel6().trim().isEmpty()) {
                        query.setParameter("cpValorNivel6", drDireccion.getCpValorNivel6());
                    }
                }
                if (drDireccion.getIdTipoDireccion().equals("IT")) {
                    if (drDireccion.getMzTipoNivel6() != null && !drDireccion.getMzTipoNivel6().trim().isEmpty()) {
                        query.setParameter("mzTipoNivel6", drDireccion.getMzTipoNivel6());
                    }
                    if (drDireccion.getMzValorNivel6() != null && !drDireccion.getMzValorNivel6().trim().isEmpty()) {
                        query.setParameter("mzValorNivel6", drDireccion.getMzValorNivel6());
                    }
                    if (drDireccion.getItTipoPlaca() != null && !drDireccion.getItTipoPlaca().trim().isEmpty()) {
                        query.setParameter("itTipoPlaca", drDireccion.getItTipoPlaca());
                    }
                    if (drDireccion.getItValorPlaca() != null && !drDireccion.getItValorPlaca().trim().isEmpty()) {
                        query.setParameter("itValorPlaca", drDireccion.getItValorPlaca());
                    }
                }
            }
            
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            resultList = (List<CmtCuentaMatrizMgl>) query.getResultList();
        } catch (NoResultException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            resultList = new ArrayList<>();
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Se produjo un error al momento de consultar la dirección: " + ex.getMessage(), ex);
        }
        if (resultList == null) {
            resultList = new ArrayList<>();
        }
        return resultList;
    }

    /**
     * M&eacute;todo para encontrar una direcci&oacute;n en base de datos por la direcci&oacute;n digitada
     * 
     * @param direccion {@link CmtDireccionMgl} direcci&oacute;n digitada por el usuario
     * @return {@link List}&lt{@link CmtDireccionMgl}> Direcciones encontradas en base de datos
     * @throws ApplicationException Excepci&oacute;n lanzada por las consultas
     */
    public List<CmtDireccionMgl> findByDireccion(CmtDireccionMgl direccion) throws ApplicationException {
        try {
            StringBuilder sqlQuery = new StringBuilder();
            sqlQuery.append("SELECT d FROM CmtDireccionMgl d WHERE 1 = 1");
            Map<String, Object> params = generarWhereConsulta(direccion, sqlQuery);
            Query query = entityManager.createQuery(sqlQuery.toString());
            for (String key : params.keySet()) {
                query.setParameter(key, params.get(key));
            }
            return query.getResultList();
        } catch (PersistenceException ex){
            String msg = "Error consultando dirección '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(ex.getMessage(), ex);
        }
    }

    /**
     * M&eacute;todo para generar los par&aacute;metros para consulta de direcci&oacute;n
     * @param direccion {@link CmtDireccionMgl} direcci&oacute;n digitada por el usuario
     * @return {@link List}&lt{@link CmtDireccionMgl}> Direcciones encontradas en base de datos
     * @throws ApplicationException Excepci&oacute;n lanzada por las consultas
     */
    private Map<String, Object> generarWhereConsulta(CmtDireccionMgl direccion, StringBuilder query) {
        Map<String, Object> params = new HashMap<>();

        if (ValidacionUtil.validarParametro(direccion.getCodTipoDir())) {
            query.append("  AND d.codTipoDir = :codTipoDir");
            params.put("codTipoDir", direccion.getCodTipoDir());
        }

        if (ValidacionUtil.validarParametro(direccion.getBarrio())) {
            query.append("  AND d.barrio = :barrio");
            params.put("barrio", direccion.getBarrio());
        }

        if (ValidacionUtil.validarParametro(direccion.getTipoViaPrincipal())) {
            query.append("  AND d.tipoViaPrincipal = :tipoViaPrincipal");
            params.put("tipoViaPrincipal", direccion.getTipoViaPrincipal());
        }

        if (ValidacionUtil.validarParametro(direccion.getNumViaPrincipal())) {
            query.append("  AND d.numViaPrincipal = :numViaPrincipal");
            params.put("numViaPrincipal", direccion.getNumViaPrincipal());
        }

        if (ValidacionUtil.validarParametro(direccion.getNumViaPrincipal())) {
            query.append("  AND d.estadoRegistro = :estadoRegistro");
            params.put("estadoRegistro", 1);
        }

        if (ValidacionUtil.validarParametro(direccion.getNlPostViaP())) {
            query.append("  AND d.nlPostViaP = :nlPostViaP");
            params.put("nlPostViaP", direccion.getNlPostViaP());
        }

        if (ValidacionUtil.validarParametro(direccion.getBisViaPrincipal())) {
            query.append("  AND d.bisViaPrincipal = :bisViaPrincipal");
            params.put("bisViaPrincipal", direccion.getBisViaPrincipal());
        }

        if (ValidacionUtil.validarParametro(direccion.getCuadViaPrincipal())) {
            query.append("  AND d.cuadViaPrincipal = :cuadViaPrincipal");
            params.put("cuadViaPrincipal", direccion.getCuadViaPrincipal());
        }

        if (ValidacionUtil.validarParametro(direccion.getTipoViaGeneradora())) {
            query.append("  AND d.tipoViaGeneradora = :tipoViaGeneradora");
            params.put("tipoViaGeneradora", direccion.getTipoViaGeneradora());
        }

        if (ValidacionUtil.validarParametro(direccion.getNumViaGeneradora())) {
            query.append("  AND d.numViaGeneradora = :numViaGeneradora");
            params.put("numViaGeneradora", direccion.getNumViaGeneradora());
        }

        if (ValidacionUtil.validarParametro(direccion.getLtViaGeneradora())) {
            query.append("  AND d.ltViaGeneradora = :ltViaGeneradora");
            params.put("ltViaGeneradora", direccion.getLtViaGeneradora());
        }

        if (ValidacionUtil.validarParametro(direccion.getNlPostViaG())) {
            query.append("  AND d.nlPostViaG = :nlPostViaG");
            params.put("nlPostViaG", direccion.getNlPostViaG());
        }

        if (ValidacionUtil.validarParametro(direccion.getBisViaGeneradora())) {
            query.append("  AND d.bisViaGeneradora = :bisViaGeneradora");
            params.put("bisViaGeneradora", direccion.getBisViaGeneradora());
        }

        if (ValidacionUtil.validarParametro(direccion.getCuadViaGeneradora())) {
            query.append("  AND d.cuadViaGeneradora = :cuadViaGeneradora");
            params.put("cuadViaGeneradora", direccion.getCuadViaGeneradora());
        }

        if (ValidacionUtil.validarParametro(direccion.getPlacaDireccion())) {
            query.append("  AND d.placaDireccion = :placaDireccion");
            params.put("placaDireccion", direccion.getPlacaDireccion());
        }

        if (ValidacionUtil.validarParametro(direccion.getMzTipoNivel1())) {
            query.append("  AND d.mzTipoNivel1 = :mzTipoNivel1");
            params.put("mzTipoNivel1", direccion.getMzTipoNivel1());
        }

        if (ValidacionUtil.validarParametro(direccion.getMzTipoNivel2())) {
            query.append("  AND d.mzTipoNivel2 = :mzTipoNivel2");
            params.put("mzTipoNivel2", direccion.getMzTipoNivel2());
        }

        if (ValidacionUtil.validarParametro(direccion.getMzTipoNivel3())) {
            query.append("  AND d.mzTipoNivel3 = :mzTipoNivel3");
            params.put("mzTipoNivel3", direccion.getMzTipoNivel3());
        }

        if (ValidacionUtil.validarParametro(direccion.getMzTipoNivel4())) {
            query.append("  AND d.mzTipoNivel4 = :mzTipoNivel4");
            params.put("mzTipoNivel4", direccion.getMzTipoNivel4());
        }

        if (ValidacionUtil.validarParametro(direccion.getMzTipoNivel5())) {
            query.append("  AND d.mzTipoNivel5 = :mzTipoNivel5");
            params.put("mzTipoNivel5", direccion.getMzTipoNivel5());
        }

        if (ValidacionUtil.validarParametro(direccion.getMzTipoNivel6())) {
            query.append("  AND d.mzTipoNivel6 = :mzTipoNivel6");
            params.put("mzTipoNivel6", direccion.getMzTipoNivel6());
        }

        if (ValidacionUtil.validarParametro(direccion.getMzValorNivel1())) {
            query.append("  AND d.mzValorNivel1 = :mzValorNivel1");
            params.put("mzValorNivel1", direccion.getMzValorNivel1());
        }

        if (ValidacionUtil.validarParametro(direccion.getMzValorNivel2())) {
            query.append("  AND d.mzValorNivel2 = :mzValorNivel2");
            params.put("mzValorNivel2", direccion.getMzValorNivel2());
        }

        if (ValidacionUtil.validarParametro(direccion.getMzValorNivel3())) {
            query.append("  AND d.mzValorNivel3 = :mzValorNivel3");
            params.put("mzValorNivel3", direccion.getMzValorNivel3());
        }

        if (ValidacionUtil.validarParametro(direccion.getMzValorNivel4())) {
            query.append("  AND d.mzValorNivel4 = :mzValorNivel4");
            params.put("mzValorNivel4", direccion.getMzValorNivel4());
        }

        if (ValidacionUtil.validarParametro(direccion.getMzValorNivel5())) {
            query.append("  AND d.mzValorNivel5 = :mzValorNivel5");
            params.put("mzValorNivel5", direccion.getMzValorNivel5());
        }

        if (ValidacionUtil.validarParametro(direccion.getMzValorNivel6())) {
            query.append("  AND d.mzValorNivel6 = :mzValorNivel6");
            params.put("mzValorNivel6", direccion.getMzValorNivel6());
        }

        return params;
    }
     /**
     * Metodo para consultar las direcciones aletrnas de una cm 
     * Autor: Victor Bocanegra
     * @param cuentaMatriz
     * @return List<CmtDireccionMgl>
     * @throws ApplicationException
     */
     public List<CmtDireccionMgl> findDireccionesByCuentaMatrizAlternas(CmtCuentaMatrizMgl cuentaMatriz)
             throws ApplicationException {
        List<CmtDireccionMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtDireccionMgl.findByCuentaMatrizAlternas");
        query.setParameter("cuentaMatriz", cuentaMatriz);
        resultList = (List<CmtDireccionMgl>) query.getResultList();
        return resultList;
    }

     /**
     * Metodo para consultar las direccion alternas de una cuenta matriz edificio general
     * Autor: Juan David Hernandez
     * @param cuentaMatrizId
     * @param subEdificioId
     * @return List<CmtDireccionMgl>
     * @throws ApplicationException
     */ 
    public List<CmtDireccionMgl> findDireccionAlternaByCmSub(BigDecimal cuentaMatrizId,
            BigDecimal subEdificioId) throws ApplicationException {
        try {
            StringBuilder sqlQuery = new StringBuilder();
            sqlQuery.append("SELECT d FROM CmtDireccionMgl d WHERE d.cuentaMatrizObj.cuentaMatrizId =:cuentaMatrizId");
            sqlQuery.append(" AND d.subEdificioObj.subEdificioId =:subEdificioId ");
            sqlQuery.append(" AND d.estadoRegistro = 1 ");
            
            Query query = entityManager.createQuery(sqlQuery.toString());
            query.setParameter("cuentaMatrizId", cuentaMatrizId);
            query.setParameter("subEdificioId", subEdificioId);

            return query.getResultList();
        } catch (Exception ex){   
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(ex.getMessage(), ex);
        }
    }
    
    
     /**
      * Metodo para consultar la cuenta matriz 
     * Autor: cardenaslb
     * @param idDireccionMgl
     * @return CmtDireccionMgl
     * @throws ApplicationException
     */
    public CmtDireccionMgl findCmtDireccion(BigDecimal idDireccionMgl) throws ApplicationException {
        try {
            StringBuilder sqlQuery = new StringBuilder();
            sqlQuery.append("SELECT d FROM CmtDireccionMgl d WHERE d.direccionObj.dirId =:idDireccionMgl "
                    + "and d.estadoRegistro = 1");
            
            Query query = entityManager.createQuery(sqlQuery.toString());
            query.setParameter("idDireccionMgl", idDireccionMgl);
             List<CmtDireccionMgl> dirrecciones = query.getResultList();
            if (dirrecciones.isEmpty()) {
                return null; //or throw checked exception data not found
            } else {
                return dirrecciones.get(0);
            }
        } catch (Exception ex){    
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(ex.getMessage(), ex);
        }
    }
  
    /**
     * Metodo para consultar una direccion de ccmm por direccionMGL Autor:
     * Victor Bocanegra
     *
     * @param direccionMgl
     * @return List<CmtDireccionMgl>
     * @throws ApplicationException
     */
    @SuppressWarnings("unchecked")
    public List<CmtDireccionMgl> findDireccionByDirMgl(DireccionMgl direccionMgl) throws ApplicationException {
        try {
            StringBuilder sqlQuery = new StringBuilder();
            sqlQuery.append("SELECT d FROM CmtDireccionMgl d WHERE d.direccionObj =:direccionObj");
            sqlQuery.append(" AND d.estadoRegistro = 1 ");

            Query query = entityManager.createQuery(sqlQuery.toString());
            if (direccionMgl != null) {
                query.setParameter("direccionObj", direccionMgl);
            }

            return query.getResultList();
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(ex.getMessage(), ex);
        }
    }
    
     /**
     * Metodo para consultar una direccion de ccmm por id 
     * direccionMGL Autor:
     * Victor Bocanegra
     *
     * @param idDireccionMgl
     * @return CmtDireccionMgl
     * @throws ApplicationException
     */
    @SuppressWarnings("unchecked")
    public CmtDireccionMgl findCmtIdDireccion(BigDecimal idDireccionMgl) throws ApplicationException {
        try {
            StringBuilder sqlQuery = new StringBuilder();
            sqlQuery.append("SELECT d FROM CmtDireccionMgl d  JOIN CmtCuentaMatrizMgl cm"
                    + " ON d.cuentaMatrizObj.cuentaMatrizId = cm.cuentaMatrizId "
                    + " WHERE 1=1  "
                    + " AND  d.direccionObj.dirId =:idDireccionMgl "
                    + " AND d.estadoRegistro = 1  "
                    + " AND cm.estadoRegistro = 1");
            
            Query query = entityManager.createQuery(sqlQuery.toString());
            query.setParameter("idDireccionMgl", idDireccionMgl);
             List<CmtDireccionMgl> dirrecciones = query.getResultList();
            if (dirrecciones.isEmpty()) {
                return null; //or throw checked exception data not found
            } else {
                return dirrecciones.get(0);
            }
        } catch (Exception ex){    
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(ex.getMessage(), ex);
        }
    }
    
    
      public List<CmtDireccionMgl> findDireccionAlternaByDireccionId(BigDecimal direccionId) throws ApplicationException {
        try {
            StringBuilder sqlQuery = new StringBuilder();
            sqlQuery.append("SELECT d FROM CmtDireccionMgl d WHERE d.direccionObj.dirId =:direccionId ");
            sqlQuery.append(" AND d.tdiId = 1 ");
            sqlQuery.append(" AND d.cuentaMatrizObj.estadoRegistro = 1 ");
            sqlQuery.append(" AND d.estadoRegistro = 1 ");
            
            Query query = entityManager.createQuery(sqlQuery.toString());
            query.setParameter("direccionId", direccionId);

            return query.getResultList();
        } catch (NoResultException ex) {           
            return null;
        } catch (Exception ex){   
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(ex.getMessage(), ex);
        }
    }
      
      
    public List<CmtDireccionMgl> findDireccionPrincipalByCuentaMatrizId(BigDecimal cuentaMatrizId) throws ApplicationException {
        try {
            StringBuilder sqlQuery = new StringBuilder();
            sqlQuery.append("SELECT d FROM CmtDireccionMgl d WHERE d.cuentaMatrizObj = :cuentaMatriz and d.tdiId = 2 and d.estadoRegistro=1  ");           
            sqlQuery.append(" AND d.cuentaMatrizObj.estadoRegistro = 1 ");
            sqlQuery.append(" AND d.estadoRegistro = 1 ");

            Query query = entityManager.createQuery(sqlQuery.toString());
            query.setParameter("cuentaMatrizId", cuentaMatrizId);

            return query.getResultList();
        } catch (NoResultException ex) {
            return null;
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(ex.getMessage(), ex);
        }
    }
}
