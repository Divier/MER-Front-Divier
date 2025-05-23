/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.MarcasMgl;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Admin
 */
public class MarcasMglDaoImpl extends GenericDaoImpl<MarcasMgl> {
    
    private static final Logger LOGGER = LogManager.getLogger(MarcasMglDaoImpl.class);

    public List<MarcasMgl> findAll() throws ApplicationException {
        List<MarcasMgl> resultList;
        Query query = entityManager.createNamedQuery("MarcasMgl.findAll");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
        resultList = (List<MarcasMgl>) query.getResultList();
        return resultList;
    }

    public List<MarcasMgl> findMarcaHhppMglByCode(String blackLis) throws ApplicationException {
        try {
            List<MarcasMgl> resultList;
            Query query = entityManager.createQuery("SELECT m FROM MarcasMgl m WHERE m.marCodigo = :marCodigo ");
            query.setParameter("marCodigo", blackLis);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            resultList = (List<MarcasMgl>) query.getResultList();
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
     * Buscar las marcas que estan asignadas a un HHPP
     * 
     * @author Luis Alejandro Rodriguez
     * @version 1.0 revision .
     * @param hhppMgl
     * @return
     * @throws ApplicationException 
     */
    public List<MarcasMgl> findMarcasMglByHhpp(HhppMgl hhppMgl) throws ApplicationException {
        try {
            List<MarcasMgl> resultList;
            Query query = entityManager.createQuery("SELECT m FROM MarcasMgl m, "
                    + "MarcasHhppMgl mh WHERE m.marId = mh.marId.marId"
                    + " and mh.hhpp.hhpId =:hhppmgl "
                    + " and mh.estadoRegistro = 1 AND m.estadoRegistro = 1 ORDER BY m.marId desc");
            query.setParameter("hhppmgl", hhppMgl.getHhpId());
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            resultList = (List<MarcasMgl>) query.getResultList();
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
     * Buscar las marcas por codigo
     * 
     * @author Juan David Hernandez     
     * @param codigoMarca
     * @return
     * @throws ApplicationException 
     */
    public MarcasMgl findMarcasMglByCodigo(String codigoMarca) throws ApplicationException {
        try {
            MarcasMgl result;
            Query query = entityManager.createQuery("SELECT m FROM MarcasMgl m WHERE trim(m.marCodigo) = :codigoMarca and m.estadoRegistro = 1 ");
            query.setParameter("codigoMarca", codigoMarca.trim());
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            result = (MarcasMgl) query.getSingleResult();
            return result;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            return null;
        }
    }
    
    /**
     * Buscar las marcas por codigo
     * 
     * @author @cardenaslb 
     * @param idMarcasMgl
     * @return
     * @throws ApplicationException 
     */
    public MarcasMgl findById(BigDecimal idMarcasMgl) throws ApplicationException {
        try {
            MarcasMgl result;
            Query query = entityManager.createQuery("SELECT m FROM MarcasMgl m WHERE m.marId = :idMarcasMgl and m.estadoRegistro = 1 ");
            query.setParameter("idMarcasMgl", idMarcasMgl);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            result = (MarcasMgl) query.getSingleResult();
            return result;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            return null;
        }
    }
    
    public List<MarcasMgl> findByGrupoCodigo(String codigo) throws ApplicationException {
        try {
            List<MarcasMgl> result;
            Query query = entityManager.createQuery("SELECT m FROM MarcasMgl m WHERE m.tmaId.codigo =  :codigoGrupo and m.estadoRegistro = 1 ");
            query.setParameter("codigoGrupo", codigo);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            result = query.getResultList();
            return result;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            return null;
        }
    }
    
    
    
}
