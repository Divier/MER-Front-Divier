package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

/**
 *
 * @author User
 */
public class CmtSubEdificioMglDaoImpl extends GenericDaoImpl<CmtSubEdificioMgl> {

    private static final Logger LOGGER = LogManager.getLogger(CmtSubEdificioMglDaoImpl.class);

    public List<CmtSubEdificioMgl> findAll() throws ApplicationException {
        List<CmtSubEdificioMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtSubEdificioMgl.findAll");
        resultList = (List<CmtSubEdificioMgl>) query.getResultList();
        return resultList;
    }

    public List<CmtSubEdificioMgl> findSubEdificioByCuentaMatriz(CmtCuentaMatrizMgl cuentaMatriz) throws ApplicationException {
        List<CmtSubEdificioMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtSubEdificioMgl.findByCuentaMatriz");
        query.setParameter("cuentaMatriz", cuentaMatriz);
        resultList = (List<CmtSubEdificioMgl>) query.getResultList();
        return resultList;
    }

    /**
     * Busca los SubEdificios asociados a una CM y en un estado especifico.
     * Permite realizar la busqueda de los Subedificios asociados a una Cuenta
     * Matriz y que se encuentren en un estado especifico en el repositorio.
     *
     * @author Johnnatan Ortiz
     * @param cuentaMatriz Cuenta Matriz
     * @param estado Estado de los SubEdificios
     * @return SubEdificios asociados a una Cuenta Matriz que se encuentran en
     * el estado especificado
     * @throws ApplicationException
     */
    public List<CmtSubEdificioMgl> findSubEdifByCmAndEstado(
            CmtCuentaMatrizMgl cuentaMatriz,
            CmtBasicaMgl estado) throws ApplicationException {
        try {
            List<CmtSubEdificioMgl> resultList;
            Query query = entityManager.createNamedQuery("CmtSubEdificioMgl.findByCuentaMatrizAndEstado");
            query.setParameter("cuentaMatriz", cuentaMatriz);
            query.setParameter("estadoSubEdificio", estado);
            resultList = (List<CmtSubEdificioMgl>) query.getResultList();
            return resultList;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    public boolean updateSubEdificioNumPisos(
            BigDecimal cmtSubEdificioMglId,
            int numPisos) throws ApplicationException {
        try {

            Query query = entityManager.createNamedQuery("CmtSubEdificioMgl.updateSubEdificioNumPisos");
            query.setParameter("cmtSubEdificioId", cmtSubEdificioMglId);
            query.setParameter("numPisos", numPisos);
            entityManager.getTransaction().begin();
            query.executeUpdate();
            entityManager.getTransaction().commit();
            return true;

        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Buscar un sub edificio general de una cuenta matriz especifica
     *
     * @author rodriguezluim
     * @param cuentaMatriz Condicional para buscar el subedificio
     * @param estadoSubEdificio
     * @return Un obnbjeto de tipo CmtSubEdificioMgl
     * @throws ApplicationException
     */
    public CmtSubEdificioMgl findSubEdificioGeneralByCuentaMatriz(CmtCuentaMatrizMgl cuentaMatriz,
            CmtBasicaMgl estadoSubEdificio) throws ApplicationException {
        try {
            Query query
                    = entityManager.createQuery("SELECT c FROM CmtSubEdificioMgl c WHERE c.cuentaMatrizObj.cuentaMatrizId = :idCuentaMatriz AND c.estadoRegistro = 1");
            List<CmtSubEdificioMgl> subedificio = query
                    .setParameter("idCuentaMatriz", cuentaMatriz.getCuentaMatrizId())
                    .getResultList();
            CmtSubEdificioMgl registro;
            if (subedificio.isEmpty()) {
                return null;
            }
            if (subedificio.size() == 1) {
                registro = subedificio.get(0);
            } else {
                query = entityManager.createNamedQuery("CmtSubEdificioMgl.findByCuentaMatrizAndEstado");
                query.setParameter("cuentaMatriz", cuentaMatriz);
                query.setParameter("estadoSubEdificio", estadoSubEdificio);
                registro = (CmtSubEdificioMgl) query.getSingleResult();
            }
            return registro;
        } catch (NonUniqueResultException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Hay mas un subedificio asociado a la cuenta matriz como General " + cuentaMatriz.getCuentaMatrizId());
        } 
    }

    public Long countSubEdificiosCuentaMatriz(BigDecimal id_cuenta_matriz) throws ApplicationException {
        if (id_cuenta_matriz != null) {
            try {
                Query query = entityManager.createNamedQuery("CmtSubEdificioMgl.countSubEdificiosCuentaMatriz");
                query.setParameter("idCuentaMatriz", id_cuenta_matriz);
                return (Long) query.getSingleResult();
            } catch (NoResultException ex) {
                LOGGER.error("Error al realizar el conteo de subedificios. EX000: " + ex.getMessage(), ex);
                return 0L;
            } 
        } else {
            LOGGER.error("Error en CmtSubEdificioMglDaoImpl:countSubEdificiosCuentaMatriz: Cuenta Matriz nula.");
            return 0L;
        }
    }

    /**
     * Busca lista de sub edificios por nodo especifico
     *
     * @author bocanegra vm
     * @param nodo
     * @return List<CmtSubEdificioMgl>
     * @throws ApplicationException
     */
    public List<CmtSubEdificioMgl> findSubEdificioByNodo(NodoMgl nodo) throws ApplicationException {
        List<CmtSubEdificioMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtSubEdificioMgl.findByNodo");
        query.setParameter("nodoObj", nodo);
        resultList = (List<CmtSubEdificioMgl>) query.getResultList();
        return resultList;
    }

    /**
     * Buscar un sub edificio general de una cuenta matriz especifica
     *
     * @author rodriguezluim
     * @param cuentaMatriz Condicional para buscar el subedificio
     * @return Un obnbjeto de tipo CmtSubEdificioMgl
     * @throws ApplicationException
     */
    public CmtSubEdificioMgl findSubEdificioGeneralByCuentaMatriz(BigDecimal cuentaMatriz) throws ApplicationException {
        try {
            Query query
                    = entityManager.createQuery("SELECT c FROM CmtSubEdificioMgl c WHERE c.cuentaMatrizObj.cuentaMatrizId = :idCuentaMatriz AND c.estadoRegistro = 1");
            List<CmtSubEdificioMgl> subedificio = query
                    .setParameter("idCuentaMatriz", cuentaMatriz)
                    .getResultList();
            CmtSubEdificioMgl registro = null;
            if (subedificio.size() > 1) {
                for (CmtSubEdificioMgl cmtSubEdificioMgl : subedificio) {
                    CmtBasicaMglDaoImpl cmtBasicaMglDaoImpl = new CmtBasicaMglDaoImpl();
                    if (cmtSubEdificioMgl.getEstadoSubEdificioObj().getNombreBasica().equals(cmtBasicaMglDaoImpl.findByCodigoInternoApp(
                            Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO).getNombreBasica())) {
                        registro = cmtSubEdificioMgl;
                    }
                }

            }
            if (subedificio.size() == 1) {
                registro = subedificio.get(0);
            }
            return registro;
        } catch (NonUniqueResultException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Hay mas un subedificio asociado a la cuenta matriz como General " + cuentaMatriz);
        } catch (ApplicationException ex) {
            LOGGER.error("Error al consultar el subedificio. EX000: " + ex.getMessage(), ex);
            throw new ApplicationException("Error generico de aplicacion al buscar "
                    + "subedificio general para la cuenta matriz " + cuentaMatriz
                    + " ver Log del sistema para mayor informacion. ");
        }
    }

    /**
     * Buscar un sub edificio general de una cuenta matriz especifica
     *
     * @author acostacg
     * @param cuentaMatriz Condicional para buscar el subedificio
     * @param subEdificioID Identificador del Subedificio
     * @return Un obnbjeto de tipo CmtSubEdificioMgl
     * @throws ApplicationException
     */
    public CmtSubEdificioMgl findSubEdificioGeneralByCuentaMatrizAndSubEdificioID(BigDecimal cuentaMatriz, BigDecimal subEdificioID) throws ApplicationException {
        try {
            Query query
                    = entityManager.createQuery("SELECT c FROM CmtSubEdificioMgl c "
                            + "WHERE c.cuentaMatrizObj.cuentaMatrizId = :idCuentaMatriz "
                            + "AND c.subEdificioId = :subEdificioID "
                            + "AND c.estadoRegistro = 1");
            List<CmtSubEdificioMgl> subedificio = query
                    .setParameter("idCuentaMatriz", cuentaMatriz)
                    .setParameter("subEdificioID", subEdificioID)
                    .getResultList();
            CmtSubEdificioMgl registro = null;
            if (subedificio.size() > 1) {
                for (CmtSubEdificioMgl cmtSubEdificioMgl : subedificio) {
                    CmtBasicaMglDaoImpl cmtBasicaMglDaoImpl = new CmtBasicaMglDaoImpl();
                    if (cmtSubEdificioMgl.getEstadoSubEdificioObj().getNombreBasica().equals(cmtBasicaMglDaoImpl.findByCodigoInternoApp(
                            Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO).getNombreBasica())) {
                        registro = cmtSubEdificioMgl;
                    }
                }

            }
            if (subedificio.size() == 1) {
                registro = subedificio.get(0);
            }
            return registro;
        } catch (NonUniqueResultException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Hay mas un subedificio asociado a la cuenta matriz como General " + cuentaMatriz);
        } catch (ApplicationException ex) {
            throw new ApplicationException("Error al buscar "
                    + "subedificio general para la cuenta matriz " + cuentaMatriz
                    + ": " + ex.getMessage(), ex);
        }
    }

    public CmtSubEdificioMgl countSubEdificiosCuentaMatriz(String nombreCuentaMatriz) throws ApplicationException {
        try {
            Query query = entityManager.createNamedQuery("CmtSubEdificioMgl.countSubEdificiosCuentaMatrizNombre");
            query.setParameter("nombreCuentaMatriz", nombreCuentaMatriz);
            return (CmtSubEdificioMgl) query.getSingleResult();
        } catch (NoResultException ex) {
            LOGGER.error("Error al realizar el conteo de subedificios. EX000: " + ex.getMessage(), ex);
            return null;
        } 
    }

    /**
     * Buscar un sub edificio general de una cuenta matriz especifica
     *
     * @author Bocanegra vm
     * @param cuentaMatriz Condicional para buscar el subedificio
     * @param estadoSubEdificio
     * @return Un obnbjeto de tipo CmtSubEdificioMgl
     * @throws ApplicationException
     */
    public CmtSubEdificioMgl findSubEdificioGeneralByCuentaMatrizEliminado(CmtCuentaMatrizMgl cuentaMatriz,
            CmtBasicaMgl estadoSubEdificio) throws ApplicationException {
        CmtSubEdificioMgl registro = null;

        try {
            Query query
                    = entityManager.createQuery("SELECT c FROM CmtSubEdificioMgl c  "
                            + " WHERE c.cuentaMatrizObj = :cuentaMatrizObj  "
                            + " AND c.estadoSubEdificioObj = :estadoSubEdificioObj");

            query.setParameter("cuentaMatrizObj", cuentaMatriz);
            query.setParameter("estadoSubEdificioObj", estadoSubEdificio);
            registro = (CmtSubEdificioMgl) query.getSingleResult();

            return registro;
        } catch (NonUniqueResultException ex) {
            LOGGER.error("NonUniqueResultException:CmtSubEdificioMglDaoImpl:findSubEdificioGeneralByCuentaMatrizEliminado "
                    + "MGL: " + cuentaMatriz.getCuentaMatrizId() + ":" + ex.getMessage());
        } catch (Exception e) {
            LOGGER.error("Excepcion:CmtSubEdificioMglDaoImpl:findSubEdificioGeneralByCuentaMatrizEliminado "
                    + "MGL: " + cuentaMatriz.getCuentaMatrizId() + ":" + e.getMessage());
        }

        return registro;
    }

    public List<CmtSubEdificioMgl> findSubEdificioByCuentaMatrizId(BigDecimal cuentaMatriz) throws ApplicationException {
        List<CmtSubEdificioMgl> resultList;
        Query query = entityManager.createNamedQuery("CmtSubEdificioMgl.findByCuentaMatrizId");
        query.setParameter("cuentaMatriz", cuentaMatriz);
        resultList = (List<CmtSubEdificioMgl>) query.getResultList();
        return resultList;
    }
    
     public void updateCompania(CmtSubEdificioMgl cmtSubEdificioMgl, String usuario, int perfil) throws ApplicationException {
        try {

             entityManager.getTransaction().begin();
            Query query = entityManager.createQuery("UPDATE CmtSubEdificioMgl c SET c.administrador =:administrador, c.fechaEdicion= :fechaEdicion, "
                    + "c.usuarioEdicion = :usuarioEdicion, c.perfilEdicion= :perfilEdicion, "
                    + "c.companiaAdministracionObj =:companiaId  WHERE  c.subEdificioId = :subEdificioId "); 
            query.setParameter("subEdificioId", cmtSubEdificioMgl.getSubEdificioId());
            query.setParameter("administrador", cmtSubEdificioMgl.getAdministrador());
            query.setParameter("companiaId", cmtSubEdificioMgl.getCompaniaAdministracionObj());
            query.setParameter("fechaEdicion", new Date());
            query.setParameter("usuarioEdicion", usuario);
            query.setParameter("perfilEdicion", perfil);
            
            query.executeUpdate();
            entityManager.getTransaction().commit();

        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
    
 
}