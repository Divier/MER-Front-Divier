package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.LogActualizaMaster;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Query;

/**
 * Dao para consulta del Master Nap <i>TEC_LOG_NAP_MASTER</i>.
 *
 * @author duartey
 */
public class LogActualizaDaoImpl extends GenericDaoImpl<LogActualizaMaster> {

    private static final Logger LOGGER = LogManager.getLogger(LogActualizaDaoImpl.class);

    public List<LogActualizaMaster> findAll() throws ApplicationException {
        try {

            Query query = entityManager.createNamedQuery("LogActualizaMaster.findAll");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            return query.getResultList();
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    public List<LogActualizaMaster> getListLogActualizaByTroncal(String troncal) throws ApplicationException {
        try {

            Query query = entityManager.createNamedQuery("LogActualizaMaster.findLogToTroncal");
            query.setParameter("troncal", troncal);
            return query.getResultList();
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
    
    public List<LogActualizaMaster> getListLogActualizaGroupByTroncal() throws ApplicationException {
        try {

            Query query = entityManager.createNamedQuery("LogActualizaMaster.findLogGroupByTroncal");
            return query.getResultList();
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
    
    public List<LogActualizaMaster> getListLogActualizaByTipoTecnologia(String tipoTecnologia) throws ApplicationException {
        try {

            Query query = entityManager.createNamedQuery("LogActualizaMaster.findLogToTipoTecnologia");
            query.setParameter("tipoTecnologia", tipoTecnologia);
            return query.getResultList();
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
    
    public List<LogActualizaMaster> getListLogActualizaGroupByTipoTec() throws ApplicationException {
        try {

            Query query = entityManager.createNamedQuery("LogActualizaMaster.findLogGroupByTipoTec");
            return query.getResultList();
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
    
    public List<LogActualizaMaster> getListLogActualizaByLikeNombreArchivo(String nombrearchivo) throws ApplicationException {
        try {

            Query query = entityManager.createNamedQuery("LogActualizaMaster.findLogToLikeNombreArchivo");
            query.setParameter("nombreArchivo", "%" + nombrearchivo.trim() + "%");
            return query.getResultList();
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
    
    public List<LogActualizaMaster> getListLogActualizaByDepartamento(String departamento) throws ApplicationException {
        try {

            Query query = entityManager.createNamedQuery("LogActualizaMaster.findLogToDepartamento");
            query.setParameter("departamento", departamento);
            return query.getResultList();
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
    
    public List<LogActualizaMaster> getListLogActualizaGroupByDepartamento() throws ApplicationException {
        try {

            Query query = entityManager.createNamedQuery("LogActualizaMaster.findLogGroupByDepartamento");
            return query.getResultList();
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
    
    public List<LogActualizaMaster> getListLogActualizaByCiudad(String ciudad) throws ApplicationException {
        try {

            Query query = entityManager.createNamedQuery("LogActualizaMaster.findLogToCiudad");
            query.setParameter("ciudad", ciudad);
            return query.getResultList();
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
    
    public List<LogActualizaMaster> getListLogActualizaGroupByCiudad() throws ApplicationException {
        try {

            Query query = entityManager.createNamedQuery("LogActualizaMaster.findLogGroupByCiudad");
            return query.getResultList();
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }    
    
    public List<LogActualizaMaster> getListLogActualizaByCentroPoblado(String centroPoblado) throws ApplicationException {
        try {

            Query query = entityManager.createNamedQuery("LogActualizaMaster.findLogToCentroPoblado");
            query.setParameter("centroPoblado", centroPoblado);
            return query.getResultList();
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
    
    public List<LogActualizaMaster> getListLogActualizaGroupByCentroPoblado() throws ApplicationException {
        try {

            Query query = entityManager.createNamedQuery("LogActualizaMaster.findLogGroupByCentroPoblado");
            return query.getResultList();
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    } 
    
    public List<LogActualizaMaster> getListLogActualizaByLikeUsuarioCreacion(String usuario) throws ApplicationException {
        try {

            Query query = entityManager.createNamedQuery("LogActualizaMaster.findLogToLikeUsuarioCreacion");
            query.setParameter("usuarioCreacion", "%" + usuario.trim().toUpperCase() + "%");
            return query.getResultList();
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
    
    @SuppressWarnings("unchecked")
    public List<LogActualizaMaster> getListLogActualizaByFechaRegistro(int firstResult,
            int maxResults, boolean paginar, HashMap<String, Object> params, Date fechaInicial, Date fechaFinal) throws ApplicationException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = format.format(new Date());
        List<LogActualizaMaster> resultList;
        String camposFiltro = "";
        Calendar calendar = Calendar.getInstance();
        try {
            if (fechaInicial == null) {
                fechaInicial= format.parse("1000-01-01");

            }
            if (fechaFinal == null) {
                fechaFinal = format.parse("9999-01-01");
            } else {
                calendar.setTime(fechaFinal);
                calendar.add(Calendar.DATE, 1);
                calendar.add(Calendar.SECOND, -1);
                fechaFinal=calendar.getTime();

            }
            if (params.get("troncal") != null) {
                camposFiltro = camposFiltro + " and m.troncal = :troncal";
            }
            if (params.get("tipoTecnologia") != null) {
                camposFiltro = camposFiltro + " and m.tipoTecnologia = :tipoTecnologia";
            }
            if (params.get("departamento") != null) {
                camposFiltro = camposFiltro + " and m.departamento = :departamento";
            }
            if (params.get("ciudad") != null) {
                camposFiltro = camposFiltro+ " and m.ciudad = :ciudad";
            }
            if (params.get("centroPoblado") != null) {
                camposFiltro = camposFiltro + " and m.centroPoblado = :centroPoblado";
            }
        } catch (ParseException ex) {
            LOGGER.error("Error en getListFichaToByFechaRegistro. ", ex);
            throw new ApplicationException(ex);
        }
            Query query = entityManager.createQuery("SELECT m FROM LogActualizaMaster m where "
                + "m.nombreArchivo LIKE :nombreArchivo and m.usuarioCreacion LIKE :usuarioCreacion and "
                + "m.fechaRegistro between :fechaInicial and :fechaFinal"
                + camposFiltro 
                + " ORDER BY m.idNap DESC", LogActualizaMaster.class);
        if (paginar) {
            query.setFirstResult(firstResult);
            query.setMaxResults(maxResults);
        }
        if (params.get("troncal") != null) {
            query.setParameter("troncal", params.get("troncal"));
        }
        if (params.get("tipoTecnologia") != null) {
            query.setParameter("tipoTecnologia", params.get("tipoTecnologia"));
        }
        if (params.get("departamento") != null) {
            query.setParameter("departamento", params.get("departamento"));
        }
        if (params.get("ciudad") != null) {
            query.setParameter("ciudad", params.get("ciudad"));
        }
        if (params.get("centroPoblado") != null) {
            query.setParameter("centroPoblado", params.get("centroPoblado"));
        }
        query.setParameter("nombreArchivo", "%" + params.get("nombreArchivo").toString().trim()+ "%");
        query.setParameter("usuarioCreacion", "%" + params.get("usuarioCreacion").toString().trim().toUpperCase()+ "%");
        query.setParameter("fechaInicial", fechaInicial);
        query.setParameter("fechaFinal", fechaFinal);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
        resultList = (List<LogActualizaMaster>) query.getResultList();
        return resultList;
    }

}
