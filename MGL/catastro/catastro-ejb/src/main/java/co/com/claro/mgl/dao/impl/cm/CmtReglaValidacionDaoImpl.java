package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtReglaValidacion;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import javax.persistence.Query;

/**
 * Acceder a los datos de las reglas de validación.
 * Dao Regla Validacion. Contiene la logica de acceso a datos de las reglas de
 * validacion en el repositorio.
 *
 * @author Johnnatan Ortiz.
 * @version 1.0 revisión 16/05/2017.
 */
public class CmtReglaValidacionDaoImpl extends GenericDaoImpl<CmtReglaValidacion> {
    
    private static final Logger LOGGER = LogManager.getLogger(CmtReglaValidacionDaoImpl.class);

    /**
     * Busca las Reglas de validacion asociadas al filtro. 
     * Permite realizar la busqueda de las Reglas de validacion asociadas 
     * paginado en el repositorio.
     *
     * @author Johnnatan Ortiz
     * @version 1.0 revision 16/05/2017.
     * @param proyecto proyecto CmtBasica para el filtro.
     * @param firstResult primer registro para inicar la consulta.
     * @param maxResults numero de registros requeridos en la consulta.
     * @return Reglas de validacion encontradas en el repositorio asociadas al
     * proyecto.
     * @throws ApplicationException Lanza la excepción cuando ocurra un error en 
     * la ejecución de la sentencia.
     */
    public List<CmtReglaValidacion> findByFiltroPaginado(
            int firstResult,
            int maxResults,
            CmtBasicaMgl proyecto)
            throws ApplicationException {
        try {
            List<CmtReglaValidacion> resultList;
            String queryStr = "SELECT r FROM CmtReglaValidacion r WHERE r.estadoRegistro = 1 ";
            if (proyecto != null && proyecto.getBasicaId() != null) {
                queryStr += " AND r.proyecto = :proyecto ";
            }
            Query query = entityManager.createQuery(queryStr);

            query.setFirstResult(firstResult);
            query.setMaxResults(maxResults);

            if (proyecto != null && proyecto.getBasicaId() != null) {
                query.setParameter("proyecto", proyecto);
            }

            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            resultList = (List<CmtReglaValidacion>) query.getResultList();
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
     * Busca las Reglas de validacion asociadas a un proyecto. 
     * Permite realizar la busqueda de las Reglas de validacion asociadas a un 
     * proyecto en el repositorio.
     *
     * @author Johnnatan Ortiz
     * @version 1.0 revision 16/05/2017.
     * @param proyecto proyecto CmtBasica para el filtro.
     * @return Reglas de validacion encontradas en el repositorio asociadas al
     * proyecto.
     * @throws ApplicationException Lanza la excepción cuando ocurra un error en 
     * a ejecución de la sentencia.
     */
    public List<CmtReglaValidacion> findByProyecto(
            CmtBasicaMgl proyecto)
            throws ApplicationException {
        try {
            List<CmtReglaValidacion> resultList;

            Query query = entityManager.createNamedQuery("CmtReglaValidacion.findByProyecto");

            if (proyecto != null && proyecto.getBasicaId() != null) {
                query.setParameter("proyecto", proyecto);
            }

            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            resultList = (List<CmtReglaValidacion>) query.getResultList();
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
     * Cuenta las Reglas de validacion asociadas al filtro. 
     * Permite contar las Reglas de validacion asociadas paginado en el 
     * repositorio.
     *
     * @author Johnnatan Ortiz
     * @version 1.0 revision 16/05/2017.
     * @param proyecto proyecto CmtBasica para el filtro.
     * @return Numero de Reglas de validacion encontradas en el repositorio
     * asociadas al filtro.
     * @throws ApplicationException Lanza la excepción cuando ocurra un error en 
     * a ejecución de la sentencia.
     */
    public int countByFiltroPaginado(
            CmtBasicaMgl proyecto)
            throws ApplicationException {
        try {
            int result = 0;
            String queryStr = "SELECT COUNT(1) FROM CmtReglaValidacion r WHERE r.estadoRegistro = 1 ";
            if (proyecto != null && proyecto.getBasicaId() != null) {
                queryStr += " AND r.proyecto = :proyecto ";
            }
            Query query = entityManager.createQuery(queryStr);

            if (proyecto != null && proyecto.getBasicaId() != null) {
                query.setParameter("proyecto", proyecto);
            }

            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            result = query.getSingleResult() == null
                    ? 0 : ((Long) query.getSingleResult()).intValue();
            return result;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Buscar en la base de datos una regla validacion que cumpla con una id
     * basica.
     * Busca en la base de datos una regla validacion que cumpla con una id
     * basica ademas que esté activa, luego se compara con el parametro
     * validacionRegla y se determina si es igual o no.
     *
     * @author Johnnatan Ortiz
     * @version 1.0 revision 16/05/2017.
     * @param validacionRegla valor String que representa la regla que se quiere
     * validar que cumpla con alguna de las presentes en la base de datos.
     * Ejemplo 1=S|2=N|3=P|4=NA. donde el valor número representa el id del tipo
     * devalidacion.
     *
     * @param basicaProyecto instancia con los valores de basica para ser
     * relacionados con la consulta.
     * 
     * @return true en caso que exista un registro en la base de datos que
     * cumpla las condiciones y false en caso que se presente cualquier otro
     * caso.
     * @throws ApplicationException Lanza la excepción cuando ocurra un error en 
     * a ejecución de la sentencia.
     */
    public boolean isViable(String validacionRegla, CmtBasicaMgl basicaProyecto)
            throws ApplicationException {
        String sql = "select regla_validacion from "+Constant.MGL_DATABASE_SCHEMA+"."+ "cmt_regla_validacion "
                + "where basica_id_proyecto=" + basicaProyecto.getBasicaId() + " "
                + "and estado_registro=1";
        Query query = entityManager.createNativeQuery(sql);
        List<String> reglasEstablecidas = (List<String>) query.getResultList();
        if (validacionRegla != null) {
            if (reglasEstablecidas != null) {
                String[] reglasValidar = validacionRegla.split("\\|");
                for (String reglaEstablecida : reglasEstablecidas) {
                    String[] reglasE = reglaEstablecida.split("\\|");
                    if (reglasE != null && reglasValidar != null) {
                        if (reglasValidar.length == reglasE.length) {
                            Arrays.sort(reglasE);
                            Arrays.sort(reglasValidar);
                            if (Arrays.equals(reglasE, reglasValidar)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Consultar la regla de validación por tipo de validación.
     * Consulta la regla de validación por tipo de validación seleccionada.
     * 
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 16/05/2017.
     * @param idTipoValSelected la regla de validación por tipo de validación 
     * seleccionada.
     * @return True si la regla de validación se encuentra en uso de lo 
     * contrario false.
     * @throws ApplicationException Lanza la excepción cuando ocurra un error en 
     * a ejecución de la sentencia.
     */
    public boolean findReglaValidacionEnUso(BigDecimal idTipoValSelected)
            throws ApplicationException {
        Long resultCount = 0L;
        Query q = entityManager.createQuery(" Select Count(1) "
                + "From CmtReglaValidacion rv "
                + "Where "
                + "rv.reglaValidacion  like '%" + idTipoValSelected.toString() + "%' AND "
                + "rv.estadoRegistro =:estado ");

        q.setParameter("estado", 1);
        resultCount = (Long) q.getSingleResult();

        if (resultCount != null && resultCount > 0) {
            return true;
        }
        return false;
    }
}
