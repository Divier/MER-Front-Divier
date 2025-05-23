/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.Multivalor;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;


/**
 * DAO Multivalor. Clase que reprecenta el acceso a datos de la tabla
 * multivalor.
 *
 * @author Carlos Leonardo Villamil
 * @versión 1.00.000
 */
public class MultivalorDaoImpl extends GenericDaoImpl<Multivalor> {
    
    private static final Logger LOGGER = LogManager.getLogger(MultivalorDaoImpl.class);

    public List<Multivalor> findByIdGmu(BigDecimal idGmu) 
            throws ApplicationException {
        List<Multivalor> resultList;
        TypedQuery<Multivalor> query = entityManager.createNamedQuery("Multivalor.findByIdGmu",
                Multivalor.class);
        query.setParameter("gmuId", idGmu);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
        resultList = query.getResultList();
        return resultList;
    }

    /**
     * Descripción del objetivo del método.Busca un codigo de rupo y valor en
 la tabla multivalor.
     *
     * @author Carlos Leonardo Villamil
     * @param idGmu Grupo multivalor.
     * @param mulValor key de usuario de la tabla .
     * @return Multivalor Descripcion de la tabla multivalor.
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public Multivalor findByIdGmuAndmulValor(BigDecimal idGmu, String mulValor)
            throws ApplicationException {
        try {
            TypedQuery<Multivalor> query = entityManager.createNamedQuery(
                    "Multivalor.findByIdGmuAndmulValor", Multivalor.class);
            query.setParameter("gmuId", idGmu);
            query.setParameter("mulValor", mulValor);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            return query.getSingleResult();
        } catch (NoResultException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            Multivalor varMultivalor = new Multivalor();
            varMultivalor.setMulDescripcion("");
            return varMultivalor;
        }
    }
}
