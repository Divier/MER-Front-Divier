package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.constantes.cm.MensajeTipoValidacion;
import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtMensajeTipoValidacion;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import javax.persistence.Query;

/**
 * Aceder a los datos de mensajes de tipo validación.
 * Realiza todas las operaciones con la base de datos sobre los mensajes de tipo
 * validación.
 *
 * @author Ricardo Cortés Rodríguez.
 * @version 1.0 revision 11/05/2017.
 */
public class CmtMensajeTipoValidacionDaoImpl
        extends GenericDaoImpl<CmtMensajeTipoValidacion> {

    private static final Logger LOGGER = LogManager.getLogger(CmtMensajeTipoValidacionDaoImpl.class);

    /**
     * Realizar la busqueda de mensaje por el valor de la validacion.
     * Realiza la busqueda de mensaje por el valor de la validacion.
     *
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 11/05/2017.
     * @param valorValidacion validacion a buscar.
     * @return mensaje encontrado por la validacion.
     * @throws ApplicationException Lanza las excepciones generadas por la 
     * consulta.
     */
    public CmtMensajeTipoValidacion findByValorTipo(
            CmtBasicaMgl valorValidacion) throws ApplicationException {
        try {
            CmtMensajeTipoValidacion result = null;
            Query query = entityManager.createNamedQuery(
                    "CmtMensajeTipoValidacion.findByValorTipo");
            
            query.setParameter("validacion", valorValidacion);
            

            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<CmtMensajeTipoValidacion> resultList = 
                    (List<CmtMensajeTipoValidacion>) query.getResultList();
            if (resultList != null && !resultList.isEmpty()){
                result =resultList.get(0);
            }
            return result;

        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Realizar la busqueda de registros de mensajes según parametros dados.
     * Realiza la busqueda de los mensajes por los parametros filtrados por el
     * usuario y trae los registros según sea la configuración.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @param parametros Criterio de búsqueda dados y los vaolores para realizar
     * la paginación.
     * @return Lista de mensajes según criterios dados.
     * @throws ApplicationException Si los criterios de búsqueda son vacios.
     */
    public List<CmtMensajeTipoValidacion> findMensajesPorFiltro(Hashtable<String
            , String> parametros) throws ApplicationException {
        Boolean esVariosParametros = false;
        StringBuilder sql = new StringBuilder("SELECT e FROM CmtMensajeTipoValidacion e ");
        sql.append("WHERE e.estadoRegistro = 1 ");

        if (parametros != null && !parametros.isEmpty()) {

            if (parametros.containsKey("tipoValidacion")) {

                sql.append(" AND e.idValidacion.tipoBasicaObj.tipoBasicaId = ");
                sql.append(parametros.get("tipoValidacion"));
                esVariosParametros = true;
            }

            Query consulta = entityManager.createQuery(sql.toString());

            if (parametros.containsKey("registroInicio")) {
                Integer registroInicial = new Integer(parametros.get("registroInicio"));
                consulta.setFirstResult(registroInicial);
                consulta.setMaxResults(new Integer(MensajeTipoValidacion.REGISTROS_POR_PAGINA.getValor()));
            }
            return consulta.getResultList();
        }

        throw new ApplicationException(MensajeTipoValidacion.SIN_CRITERIOS.getValor());
    }

    /**
     * Devolver la cantidad de registros que contiene la busqueda según los
     * criterios dados.
     * Devuelve la cantidad de registros que contiene la busqueda según los
     * criterios dados.
     *
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @param parametros Criterios de búsqueda.
     * @return Cantidad de registros que coinciden con los criterios de
     * búsqueda.
     * @throws ApplicationException Error inesperado en la ejecución de la
     * sentencia.
     */
    public Integer findCantidadRegistrosForFiltro(Hashtable<String, String> parametros)
            throws ApplicationException {
        Boolean esVariosParametros = false;
        StringBuilder sql = new StringBuilder("SELECT COUNT(1)FROM CmtMensajeTipoValidacion e ");
        sql.append("WHERE e.estadoRegistro = 1 ");

        if (parametros.containsKey("tipoValidacion")) {

            sql.append(" AND e.idValidacion.tipoBasicaObj.tipoBasicaId = ");
            sql.append(parametros.get("tipoValidacion"));
            esVariosParametros = true;
        }

        Query consulta = entityManager.createQuery(sql.toString());
        return new Integer(consulta.getSingleResult().toString());
    }
    
    /**
     * Consultar los mensajes los tipos de validación por id proyecto que no 
     * estan en mensajes.
     * Consulta los mensajes los tipos de validación a los cuales  no se les ha 
     * configurado mensajes.
     * 
     * @author Ricardo Cortés Rodríguez.
     * @version 1.0 revision 11/05/2017.
     * @param idTipoBasica Identificadro del tipo de validación a consultar
     * @return La lista de en mensajes de los tipos de validación para configurar 
     * si los hay.
     */
    public List<CmtMensajeTipoValidacion> findMensajesNoConfiguradosPorTipoBasica(BigDecimal idTipoBasica){
        List<CmtMensajeTipoValidacion> mensajes = new ArrayList<CmtMensajeTipoValidacion>();
        StringBuilder sql = new StringBuilder("SELECT b");
        sql.append(" FROM CmtBasicaMgl b");
        sql.append(" WHERE b.tipoBasicaObj.tipoBasicaId = :tipoBasica");
        sql.append(" AND b.estadoRegistro = 1");
        sql.append(" AND b.activado = 'Y'");
        sql.append(" AND NOT EXISTS");
        sql.append(" (SELECT m");
        sql.append(" FROM CmtMensajeTipoValidacion m");
        sql.append(" WHERE m.estadoRegistro = 1");
        sql.append(" AND m.idValidacion = b)");
        Query consulta = entityManager.createQuery(sql.toString());
        consulta.setParameter("tipoBasica", idTipoBasica);
        List<CmtBasicaMgl> basicas = consulta.getResultList();
        
        for (CmtBasicaMgl basica : basicas) {
            CmtMensajeTipoValidacion mensajeTipoValidacion = new CmtMensajeTipoValidacion();
            mensajeTipoValidacion.setIdValidacion(basica);
            mensajes.add(mensajeTipoValidacion);
        }
        return mensajes;
    }
    /**
     * Buscar un valor que representa el mensaje según la viabilidad.
     * Busca un valor que representa el mensaje según la viabilidad.
     * 
     * @author ortizjaf.
     * @version 1.0 revision 11/05/2017.
     * @param basica basica donde se va encontrar la viabilidad.
     * @param viabilidad valor Y/P/N/NA para ver la viabilidad
     * @return el valor del mensaje en caso contrario retorna null
     * @throws ApplicationException Lanaza una excepción en la ejecución del 
     * query.
     */
    public String findMensaje(CmtBasicaMgl basica, String viabilidad)
            throws ApplicationException {
        if(basica==null)return null;
        String sql = "select id_mensajes_tip_val,basica_id_validacion,mensaje_si, "
           + "mensaje_no,mensaje_proceso, mensaje_na "
           + "from cmt_mesajes_tip_val "
           + "where basica_id_validacion="+basica.getBasicaId();
        Query query=entityManager.createNativeQuery(sql,
           CmtMensajeTipoValidacion.class);
        CmtMensajeTipoValidacion valor=null;
        List<CmtMensajeTipoValidacion> list=query.getResultList();
        
        if(!list.isEmpty()){
            valor=list.get(0);
        }
        
        if(viabilidad==null){
            viabilidad="NA";
        }
        
        if(valor!=null ){
            if(viabilidad.equalsIgnoreCase("Y")){
                return valor.getMensajeSi();
            }
            if(viabilidad.equalsIgnoreCase("N")){
                return valor.getMensajeNo();
            }
            if(viabilidad.equalsIgnoreCase("P")){
                return valor.getMensajeProcesos();
            }
            if(viabilidad.equalsIgnoreCase("NA")){
                return valor.getMensajeNa();
            }
            if(viabilidad.equalsIgnoreCase("R")){
                return valor.getMensajeRestringido();
            }
        }
        return null;
    }
}
