package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtOpcionesRolMgl;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 * <b>DAO de entidad</b><br />
 * Clase para implementar los acciones de la entidad {@link CmtOpcionesRolMgl}
 *
 * @author wgavidia
 * @version 2017/09/20
 */
public class CmtOpcionesRolMglDaoImpl extends GenericDaoImpl<CmtOpcionesRolMgl> {
    
    private static final Logger LOGGER = LogManager.getLogger(CmtOpcionesRolMglDaoImpl.class);
    
    StringBuilder queryString;

    /**
     * Método para consultar opciones de rol por formulario.
     *
     * @param formulario String, nombre del formulario
     * @return List&lt;{@link CmtOpcionesRolMgl}> listado de opciones por rol
     */
    public List<CmtOpcionesRolMgl> consultarOpcionesRol(String formulario) {
        queryString = new StringBuilder();
        queryString.append("SELECT op")
                .append(" FROM CmtOpcionesRolMgl op")
                .append(" WHERE op.formulario = :formulario");

        TypedQuery<CmtOpcionesRolMgl> query = entityManager.createQuery(queryString.toString(), CmtOpcionesRolMgl.class);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0        
        query.setParameter("formulario", formulario.trim());
        return query.getResultList();
    }

    /**
     * Método para búsqueda de una opción de rol con un
     * formulario y acción específica
     *
     * @param formulario String nombre del formulario al que pertenece el
     * componente
     * @param opcion String nombre de la opci&oacute;n que realizar&aacute; el
     * componente
     * @return {@link CmtOpcionesRolMgl} Objeto de base de datos recuperado en
     * la consulta
     * @throws ApplicationException Excepci&oacute;n generada al no encontrar
     * registros
     */
    public CmtOpcionesRolMgl consultarOpcionRol(String formulario, String opcion)
            throws ApplicationException {
        CmtOpcionesRolMgl cmtOpcionesRolMgl = null;
        try {
            if (formulario != null && opcion != null) {
                queryString = new StringBuilder();
                queryString.append("SELECT op")
                        .append(" FROM CmtOpcionesRolMgl op")
                        .append(" WHERE op.formulario = :formulario")
                        .append("   AND op.nombreOpcion = :opcion");
                TypedQuery<CmtOpcionesRolMgl> query = entityManager.createQuery(queryString.toString(), CmtOpcionesRolMgl.class);
                query.setParameter("formulario", formulario.trim());
                query.setParameter("opcion", opcion.trim());
                query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
                cmtOpcionesRolMgl = query.getSingleResult();
            }
            else {
                LOGGER.error("Error en los parámetros ingresados (formulario: " + formulario + "; opcion: " + opcion + ").");
            }
            return (cmtOpcionesRolMgl);
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Metodo que consulta los roles
     *
     * @return {@link CmtOpcionesRolMgl} Objeto de base de datos recuperado en
     * la consulta
     * @throws ApplicationException Excepci&oacute;n generada al no encontrar
     * registros
     */
    public List<String> consultarRol()
            throws ApplicationException {
        try {
            queryString = new StringBuilder();
            queryString.append("SELECT Distinct op.rol")
                    .append(" FROM CmtOpcionesRolMgl op");
            Query query = entityManager.createQuery(queryString.toString());
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            return (List<String>) query.getResultList();
        } catch (NoResultException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getCause());
        }
    }

}
