/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;
import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.dtos.CmtFiltroConsultaBacklogsDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtBacklogMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author bocanegravm
 */
public class CmtBacklogMglDaoImpl extends GenericDaoImpl<CmtBacklogMgl> {


    private static final Logger LOGGER = LogManager.getLogger(CmtBacklogMglDaoImpl.class.getName());
    
    /**
     * Busca lista de Backlogs en el repositorio
     *
     * @author Victor Bocanegra
     * @return List<CmtBacklogMgl> encontrada en el repositorio
     * @throws ApplicationException
     */
    public List<CmtBacklogMgl> findAll()
            throws ApplicationException {
        try {
            List<CmtBacklogMgl> resultList;
            Query query = entityManager.createNamedQuery("CmtBacklogMgl.findAll");
            query.setParameter("estadoRegistro", 1);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            resultList = (List<CmtBacklogMgl>) query.getResultList();
            return resultList;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * Busca un Backlog de OT en el repositorio.
     *
     * @author Victor Bocanegra
     * @param idBacklog
     * @return un Backlog de OT.
     * @throws ApplicationException
     */
    public CmtBacklogMgl findByIdBacklog(BigDecimal idBacklog) throws ApplicationException {
        try {
            CmtBacklogMgl resulList;
            Query query = entityManager.createNamedQuery("CmtBacklogMgl.findByIdBacklog");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            if (idBacklog != null) {
                query.setParameter("idBacklog", idBacklog);
            }
            resulList = (CmtBacklogMgl) query.getSingleResult();
            return resulList;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }

    /**
     * *Victor Bocanegra Metodo para buscar los backlogs por los filtros de la
     * tabla
     *
     * @param paginaSelected
     * @param maxResults
     * @param consulta
     * @param cmtOrdenTrabajoMgl
     * @return List<CmtBacklogMgl>
     * @throws ApplicationException
     */
    public List<CmtBacklogMgl> findByFiltro(int paginaSelected,
            int maxResults, CmtFiltroConsultaBacklogsDto consulta, 
            CmtOrdenTrabajoMgl cmtOrdenTrabajoMgl)
            throws ApplicationException {

        List<CmtBacklogMgl> resultList;


        
        if (cmtOrdenTrabajoMgl == null
                || cmtOrdenTrabajoMgl.getIdOt() == null
                || cmtOrdenTrabajoMgl.getIdOt().equals(BigDecimal.ZERO)) {
            throw new ApplicationException("El filtro Orden de trabajo es obligatorio");
        }
        
        String queryTipo = "SELECT n FROM CmtBacklogMgl n WHERE n.estadoRegistro = 1  "
                        + " AND n.ordenTrabajoMglObj = :idOt  ";


        if (consulta.getIdBacklog() != null) {
            queryTipo += "AND  n.idBacklog =  :idBacklog ";
        }

        if (consulta.getTipoGestionBacklog() != null && !consulta.getTipoGestionBacklog().isEmpty()) {
            queryTipo += "AND UPPER(n.tipoGesBac.nombreBasica) LIKE :nombreTipoGestion";
        }
        if (consulta.getResGestionBacklog() != null && !consulta.getResGestionBacklog().isEmpty()) {
            queryTipo += "AND UPPER(n.resultadoGesBac.nombreBasica) LIKE :nombreResGestion ";
        }
        if (consulta.getFechaGestion() != null && !consulta.getFechaGestion().isEmpty()) {
            queryTipo += "AND UPPER(n.fechaGestionBac) LIKE :fechaGestionBac ";
        }
        if (consulta.getUsuarioGestion() != null && !consulta.getUsuarioGestion().isEmpty()) {
            queryTipo += "AND UPPER(n.usuarioCreacion) LIKE :usuarioCreacion ";
        }
        if (consulta.getNotaBacklog() != null && !consulta.getNotaBacklog().isEmpty()) {
            queryTipo += "AND UPPER(n.nota) LIKE :nota ";
        }

        Query query = entityManager.createQuery(queryTipo);
        
        query.setParameter("idOt", cmtOrdenTrabajoMgl);

        if (consulta.getIdBacklog() != null) {
            query.setParameter("idBacklog", consulta.getIdBacklog());
        }

        if (consulta.getTipoGestionBacklog() != null && !consulta.getTipoGestionBacklog().isEmpty()) {
            query.setParameter("nombreTipoGestion", "%" + consulta.getTipoGestionBacklog().toUpperCase() + "%");
        }
        if (consulta.getResGestionBacklog() != null && !consulta.getResGestionBacklog().isEmpty()) {
            query.setParameter("nombreResGestion", "%" + consulta.getResGestionBacklog().toUpperCase() + "%");
        }
        if (consulta.getFechaGestion() != null && !consulta.getFechaGestion().isEmpty()) {
            query.setParameter("fechaGestionBac", "%" + consulta.getFechaGestion().toUpperCase() + "%");
        }
        if (consulta.getUsuarioGestion() != null && !consulta.getUsuarioGestion().isEmpty()) {
            query.setParameter("usuarioCreacion", "%" + consulta.getUsuarioGestion().toUpperCase() + "%");
        }
        if (consulta.getNotaBacklog() != null && !consulta.getNotaBacklog().isEmpty()) {
            query.setParameter("nota", "%" + consulta.getNotaBacklog().toUpperCase() + "%");
        }
        query.setFirstResult(paginaSelected);
        query.setMaxResults(maxResults);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultList = (List<CmtBacklogMgl>) query.getResultList();
        
            Collections.sort(resultList, new Comparator<CmtBacklogMgl>() {

                    @Override
                    public int compare(CmtBacklogMgl p1, CmtBacklogMgl p2) {
                       return new Integer((p1.getIdBacklog().intValueExact())).compareTo(new Integer(p2.getIdBacklog().intValueExact()));
                    }

                });

        return resultList;
    }

    /**
     * *Victor Bocanegra Metodo para contar los backlogs por los filtros de la
     * tabla
     *
     * @param consulta
     * @param cmtOrdenTrabajoMgl
     * @return Long
     * @throws ApplicationException
     */
    public Long countByBacklogFiltro(CmtFiltroConsultaBacklogsDto consulta, 
            CmtOrdenTrabajoMgl cmtOrdenTrabajoMgl)
            throws ApplicationException {

        Long resultCount = 0L;
        String queryTipo;
        
        if (cmtOrdenTrabajoMgl == null
                || cmtOrdenTrabajoMgl.getIdOt() == null
                || cmtOrdenTrabajoMgl.getIdOt().equals(BigDecimal.ZERO)) {
            throw new ApplicationException("El filtro Orden de trabajo es obligatorio");
        }
        
        queryTipo = "SELECT COUNT(1) FROM CmtBacklogMgl n WHERE n.estadoRegistro = 1  "
                  + "AND n.ordenTrabajoMglObj = :idOt ";

        if (consulta.getIdBacklog() != null) {
            queryTipo += "AND  n.idBacklog =  :idBacklog ";
        }

        if (consulta.getTipoGestionBacklog() != null && !consulta.getTipoGestionBacklog().isEmpty()) {
            queryTipo += "AND UPPER(n.tipoGesBac.nombreBasica) LIKE :nombreTipoGestion";
        }
        if (consulta.getResGestionBacklog() != null && !consulta.getResGestionBacklog().isEmpty()) {
            queryTipo += "AND UPPER(n.resultadoGesBac.nombreBasica) LIKE :nombreResGestion ";
        }
        if (consulta.getFechaGestion() != null && !consulta.getFechaGestion().isEmpty()) {
            queryTipo += "AND UPPER(n.fechaGestionBac) LIKE :fechaGestionBac ";
        }
        if (consulta.getUsuarioGestion() != null && !consulta.getUsuarioGestion().isEmpty()) {
            queryTipo += "AND UPPER(n.usuarioCreacion) LIKE :usuarioCreacion ";
        }
        if (consulta.getNotaBacklog() != null && !consulta.getNotaBacklog().isEmpty()) {
            queryTipo += "AND UPPER(n.nota) LIKE :nota ";
        }

        Query query = entityManager.createQuery(queryTipo);

        query.setParameter("idOt", cmtOrdenTrabajoMgl);
        
        if (consulta.getIdBacklog() != null) {
            query.setParameter("idBacklog", consulta.getIdBacklog());
        }

        if (consulta.getTipoGestionBacklog() != null && !consulta.getTipoGestionBacklog().isEmpty()) {
            query.setParameter("nombreTipoGestion", "%" + consulta.getTipoGestionBacklog().toUpperCase() + "%");
        }
        if (consulta.getResGestionBacklog() != null && !consulta.getResGestionBacklog().isEmpty()) {
            query.setParameter("nombreResGestion", "%" + consulta.getResGestionBacklog().toUpperCase() + "%");
        }
        if (consulta.getFechaGestion() != null && !consulta.getFechaGestion().isEmpty()) {
            query.setParameter("fechaGestionBac", "%" + consulta.getFechaGestion().toUpperCase() + "%");
        }
        if (consulta.getUsuarioGestion() != null && !consulta.getUsuarioGestion().isEmpty()) {
            query.setParameter("usuarioCreacion", "%" + consulta.getUsuarioGestion().toUpperCase() + "%");
        }
        if (consulta.getNotaBacklog() != null && !consulta.getNotaBacklog().isEmpty()) {
            query.setParameter("nota", "%" + consulta.getNotaBacklog().toUpperCase() + "%");
        }

        query.setHint("javax.persistence.cache.storeMode", "REFRESH");
        resultCount = (Long) query.getSingleResult();
        return resultCount;
    }
}
