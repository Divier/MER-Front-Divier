/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.constantes.cm.MensajeTipoValidacion;
import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoValidacionMgl;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

/**
 * clase que implementa operaciones del DAO correpondiente a
 * CMT_TIPO_VALIDACION. implementacion de las posibles operaciones del DAO.
 * 
* @author ortizjaf
 * @versión 1.0
 */
public class CmtTipoValidacionMglDaoImpl extends GenericDaoImpl<CmtTipoValidacionMgl> {
    
    private static final Logger LOGGER = LogManager.getLogger(CmtTipoValidacionMglDaoImpl.class);

    /**
     * Buscar el tipo de validacion. 
     * Comprueba si se encuentra un tipoBasica en la persistencia o no 
     * (true/false).
     *
     * @author jdHernandez.
     * @version 1.0 revision 18/05/2017.
     * @param tipoValidacion Identificador del tipo de validacion.
     * @return True si existe el tipo de validacion de los contrario false.
     * @throws ApplicationException Lanza la excepcion cuando ocurra un error en
     * a ejecucion de la sentencia.
     */
    public boolean existsTipoBasica(CmtTipoBasicaMgl tipoValidacion)
            throws ApplicationException {
        Query query = entityManager.createNamedQuery(
                "CmtTipoValidacionMgl.countByTipoValidacionActive");
        query.setParameter("tipoValidacion", tipoValidacion);
        Integer valor = new Integer(query.getSingleResult().toString());
        return valor > 0;
    }

    /**
     * Obtener la cantidad de paginas del paginador. Obtiene la cantidad de
     * páginas con respecto a los registros y a la constante de cantidad de
     * registros por página.
     *
     * @author jdHernandez.
     * @version 1.0 revision 18/05/2017.
     * @param parametros Contiene los criterios de búsqueda de la aplicación.
     * @return Cantidad de páginas que contendrá la consulta según criterios.
     * @throws ApplicationException Excepción ejecución de sentencia.
     */
    public Integer findCantidadRegistrosForFiltro(
            Hashtable<String, String> parametros)
            throws ApplicationException {
        Boolean esVariosParametros = false;
        StringBuilder sql = new StringBuilder(
                "SELECT COUNT(1)FROM CmtTipoValidacionMgl e ");
        sql.append("WHERE ");
        if (parametros.containsKey("estadoRegistro")) {
            sql.append("e.estadoRegistro = ");
            sql.append(parametros.get("estadoRegistro"));
        }
        Query consulta = entityManager.createQuery(sql.toString());
        return new Integer(consulta.getSingleResult().toString());
    }

    /**
     * Realiza la busqueda de los tipos de validacion por los parametros
     * filtrados por el usuario y trae los registros segun sea la configuracion.
     *
     * @param parametros Criterio de busqueda dados y los vaolores para realizar
     * la paginacion.
     * @throws ApplicationException Si los criterios de busqueda son vacios.
     * @return Lista de tipos de validacion segun criterios dados.
     */
    public List<CmtTipoValidacionMgl> findMensajesPorFiltro(
            Hashtable<String, String> parametros)
            throws ApplicationException {
        Boolean esVariosParametros = false;
        StringBuilder sql = new StringBuilder(
                "SELECT e FROM CmtTipoValidacionMgl e ");
        sql.append("WHERE ");
        if (parametros != null && !parametros.isEmpty()) {
            if (parametros.containsKey("estadoRegistro")) {
                sql.append("e.estadoRegistro = ");
                sql.append(parametros.get("estadoRegistro"));
            }
            sql.append(" ORDER BY e.tipoBasicaObj.nombreTipo ASC ");
            Query consulta = entityManager.createQuery(sql.toString());
            if (parametros.containsKey("registroInicio")) {
                Integer registroInicial = new Integer(
                        parametros.get("registroInicio"));
                consulta.setFirstResult(registroInicial);
                consulta.setMaxResults(new Integer(
                        MensajeTipoValidacion.REGISTROS_POR_PAGINA.getValor()));
            }
            return consulta.getResultList();
        }
        throw new ApplicationException(
                MensajeTipoValidacion.SIN_CRITERIOS.getValor());
    }

    public CmtTipoValidacionMgl findTipoValidacion(CmtBasicaMgl basica) throws ApplicationException {

        String sql1 = "SELECT a FROM CmtTipoValidacionMgl a "
                + "WHERE a.tipoBasicaObj=:tipoBasica AND a.estadoRegistro = 1";

        Query query = entityManager.createQuery(sql1, CmtTipoValidacionMgl.class);
        query.setParameter("tipoBasica", basica.getTipoBasicaObj());

        CmtTipoValidacionMgl aux = null;
        List<CmtTipoValidacionMgl> list = query.getResultList();

        if (!list.isEmpty()) {
            aux = list.get(0);
        }
        return aux;
    }

    /**
     * Buscar los tipos de validacion activos.
     * Busca los tipos de validación activos y con id determinado.
     *
     * @author jdHernandez.
     * @version 1.0 revision 17/05/2017.
     * @param id Identificador del tipo de validación.
     * @return El tipo de validación que se encuentre activo y que corresponda
     * el identificador proporcionado.
     * @throws ApplicationException Lanza las excepciones si no encuentra
     * resultados o cuando los registros buscados son duplicados o un error no
     * conocido para que lo maneje la aplicación.
     */
    public CmtTipoValidacionMgl findByIdActive(BigDecimal id) 
            throws ApplicationException {
        StringBuilder sql = new StringBuilder("SELECT e FROM CmtTipoValidacionMgl e");
        sql.append(" WHERE e.idTipoValidacion = :id");
        sql.append(" AND e.estadoRegistro = 1");

        try {
            Query consulta = entityManager.createQuery(sql.toString());
            consulta.setParameter("id", id);
            return (CmtTipoValidacionMgl) consulta.getSingleResult();
        } catch (NoResultException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + MensajeTipoValidacion.SIN_COINCIDENCIA.getValor()+ "': "
                    + ex.getMessage();
            LOGGER.error(msg);            
        } catch (NonUniqueResultException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + MensajeTipoValidacion.ERROR_DUPLICIDAD.getValor()+ "': "
                    + ex.getMessage();
            LOGGER.error(msg);            
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " 
                    + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(ex);
        }
        return null;
    }
    
    /**
     * Buscar tipo de validación
     * Busca el tipo de validación por el tipo de basica.
     * 
     * @author jdHernandez.
     * @version 1.0 revision 17/05/2017.
     * @param tipoList Tipo de basica.
     * @return La lista de tipos de validacion coincidentes.
     * @throws ApplicationException Lanza la excepcion cuando ocurra un error en 
     * a ejecucion de la sentencia.
     */
    public List<CmtTipoValidacionMgl> findTipoValByTipoBasica(
            List<CmtTipoBasicaMgl> tipoList) throws ApplicationException {
        
        List<CmtTipoValidacionMgl> resultList = null;
        List<BigDecimal> idList = new ArrayList<BigDecimal>();
        
        for (CmtTipoBasicaMgl t : tipoList) {
            idList.add(t.getTipoBasicaId());
        }
        
        String sql = " SELECT tv "
                + "FROM CmtTipoValidacionMgl tv "
                + " WHERE "
                + "tv.tipoBasicaObj.tipoBasicaId IN :tipoList AND "
                + "tv.estadoRegistro =:estado ";

        Query q = entityManager.createQuery(sql);

        q.setParameter("tipoList", idList);
        q.setParameter("estado", 1);
        resultList = (List<CmtTipoValidacionMgl>) q.getResultList();
        return resultList;
    }
    
    /**
     * Busca cuantos tipos de validacion coincidente con el parametro. Retorna
     * conteo de registros en uso.
     *
     * @author jdHernandez.
     * @version 1.0 revision 18/05/2017.
     * @param idTipoValSelected Identificador del tipo de validacion
     * seleccionado.
     * @return True si encuentra registros de los contrario false
     * @throws ApplicationException Lanza la excepcion cuando ocurra un error en
     * a ejecucion de la sentencia.
     */
    public boolean findTipoValidacionEnUso(BigDecimal idTipoValSelected)
            throws ApplicationException {
        Long resultCount = 0L;
        Query q = entityManager.createQuery("Select Count(1) "
                + "From CmtTipoValidacionMgl tv, CmtTipoBasicaMgl tb, CmtBasicaMgl b, CmtValidacionProyectoMgl vp "
                + "Where "
                + "tv.idTipoValidacion =:idTipoValSelected AND "
                + "tv.tipoBasicaObj.tipoBasicaId = tb.tipoBasicaId AND "
                + "tb.tipoBasicaId  = b.tipoBasicaObj.tipoBasicaId AND "
                + "b.basicaId = vp.tipoBasicaValidacionId.basicaId AND "
                + "tv.estadoRegistro =:estado AND "
                + "b.estadoRegistro =:estado AND "
                + "tb.estadoRegistro =:estado AND "
                + "vp.estadoRegistro =:estado");

        q.setParameter("idTipoValSelected", idTipoValSelected);
        q.setParameter("estado", 1);

        resultCount = (Long) q.getSingleResult();

        if (resultCount != null && resultCount > 0) {
            return true;
        }
        return false;
    }
    
    
}
