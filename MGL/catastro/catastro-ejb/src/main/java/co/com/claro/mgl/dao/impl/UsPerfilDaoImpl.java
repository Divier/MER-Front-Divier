package co.com.claro.mgl.dao.impl;


import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.UsPerfil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;


/**
 * Data Access Object para el manejo de Perfiles <i>US_PERFIL</i>.
 * 
 * @author Camilo Miranda (<i>mirandaca</i>).
 */
public class UsPerfilDaoImpl extends MglGestionGenericDaoImpl<UsPerfil> {
    
    private static final Logger LOGGER = LogManager.getLogger(UsPerfilDaoImpl.class);
    
    
    /**
     * Busca todo el listado de <i>US_PERFIL</i>.
     * @return Lista de UsPerfil.
     * @throws ApplicationException 
     */
    public List<UsPerfil> findAll() throws ApplicationException {
        try {
            List<UsPerfil> resultList;
            Query query = entityManager.createNamedQuery("UsPerfil.findAll");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            resultList = (List<UsPerfil>) query.getResultList();
            return resultList;
        } catch (PersistenceException ex) {
            String msg = "Se produjo un error al momento de consultar todos los perfiles: " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg, ex);
        }
    }
    
    
    /**
     * Realiza la consulta de un Perfil a trav&eacute;s de su c&oacute;digo.
     * 
     * @param codPerfil C&oacute;digo del Perfil a buscar.
     * @return Perfil.
     * @throws ApplicationException 
     */
    public UsPerfil findByCodigo(String codPerfil) throws ApplicationException {
        try {
            UsPerfil result = null;
            try {
                Query query = entityManager.createQuery("SELECT p FROM UsPerfil p"
                        + " WHERE p.codPerfil = :codPerfil");
                query.setParameter("codPerfil", codPerfil);
                query.setHint("javax.persistence.cache.storeMode", "REFRESH");
                result = (UsPerfil) query.getSingleResult();
            } catch (NoResultException ex) {
                LOGGER.error("No se encontraron resultados para la búsqueda del perfil " + codPerfil);
                result = null;
            } catch (NonUniqueResultException ex) {
                String msg = "Se encontró más de un resultado para el perfil " + codPerfil + ".";
                LOGGER.error(msg);
                throw new ApplicationException(msg, ex);
            } catch (Exception ex) {
                String msg = "Error al momento de realizar la consulta del perfil " + codPerfil + ": " + ex.getMessage();
                LOGGER.error(msg);
                throw new ApplicationException(msg, ex);
            }
            return result;
            
        } catch(ApplicationException ex) {
            LOGGER.error("Error realizando la consulta del perfil por código: " + ex.getMessage());
            throw ex;  
        }
    }
}
