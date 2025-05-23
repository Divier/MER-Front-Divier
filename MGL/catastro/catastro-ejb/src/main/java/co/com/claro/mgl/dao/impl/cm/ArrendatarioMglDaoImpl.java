/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.dtos.CmtFiltroConsultaArrendatarioDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.ArrendatarioMgl;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author bocanegravm
 */
public class ArrendatarioMglDaoImpl extends GenericDaoImpl<ArrendatarioMgl> {

    private static final Logger LOGGER = LogManager.getLogger(ArrendatarioMglDaoImpl.class);

    /**
     * Autor: victor bocanegra Metodo para consultar todos los arrendatarios
     * activos de la BD
     *
     * @return resulList
     * @throws ApplicationException
     */
    public List<ArrendatarioMgl> findAll() throws ApplicationException {
        Query query = entityManager.createNamedQuery("ArrendatarioMgl.findAll");
        return query.getResultList();
    }

    /**
     * Autor: victor bocanegra Metodo para consultar el 
     * arrendatario por centro poblado
     *
     * @return List<ArrendatarioMgl>
     * @throws ApplicationException
     */
    public List<ArrendatarioMgl> findArrendatariosByCentro
        (GeograficoPoliticoMgl centro) throws ApplicationException {

        try {
            Query query = entityManager.createNamedQuery("ArrendatarioMgl.findArrendatariosByCentro");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            if (centro != null) {
                query.setParameter("centroPoblado", centro);
            }
            return query.getResultList();
        } catch (NoResultException ex) {
            return null;
        }
    }
        
    /**
     * Autor: victor bocanegra Metodo para consultar un arrendatario por
     * centro-ciudad y depto
     *
     * @param centro
     * @param ciudad
     * @param dpto
     * @return ArrendatarioMgl
     * @throws ApplicationException
     */
    public ArrendatarioMgl findArrendatariosByCentroAndCityAndDpto(GeograficoPoliticoMgl centro, GeograficoPoliticoMgl ciudad,
            GeograficoPoliticoMgl dpto) throws ApplicationException {

        try {
            Query query = entityManager.createNamedQuery("ArrendatarioMgl.findArrendatariosByCentroAndCityAndDpto");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            if (centro != null) {
                query.setParameter("centroPoblado", centro);
            }
            if (ciudad != null) {
                query.setParameter("ciudad", ciudad);
            }
            if (dpto != null) {
                query.setParameter("departamento", dpto);
            }
            return (ArrendatarioMgl) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
           
    /**
     * Autor: victor bocanegra Metodo para consultar un arrendatario por centro-
     * nombre de arrendatario y cuadrante
     *
     * @param centro
     * @param nombreArrenda
     * @param cuadrante
     * @return ArrendatarioMgl
     * @throws ApplicationException
     */
    public ArrendatarioMgl findByCentroAndNombreArrendaAndCuadrante
        (GeograficoPoliticoMgl centro, String nombreArrenda,
            String cuadrante) throws ApplicationException {

        try {
            Query query = entityManager.createNamedQuery("ArrendatarioMgl.findByCentroAndNombreArrendaAndCuadrante");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");          
            if (cuadrante != null
                    && !cuadrante.isEmpty()) {
                query.setParameter("cuadrante", cuadrante);
            }
            return (ArrendatarioMgl) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
    
    /**
     * Autor Jeniffer Corredor
     * Método para consultar los Arrendatarios según filtros de consulta
     * 
     * @param filtro
     * @return
     * @throws ApplicationException 
     */    
    public List<ArrendatarioMgl> findArrendatariosByFiltro(CmtFiltroConsultaArrendatarioDto filtro)
            throws ApplicationException {
        try {
            String queryStr = "SELECT a FROM ArrendatarioMgl a WHERE a.estadoRegistro = :estadoRegistro ";             
                    
            if (filtro.getIdArrendaSeleccionado() != null && 
                    !filtro.getIdArrendaSeleccionado().equals(BigDecimal.ZERO)) {
                queryStr += " AND a.arrendatarioId = :arrendatarioId ";
            }
            
            if (filtro.getPrevisitaSeleccionada() != null && !filtro.getPrevisitaSeleccionada().isEmpty()) {
               queryStr += " AND UPPER(a.previsita) LIKE :previsita ";
            }
            
            if (filtro.getSlaPrevisitaSeleccionada() != null && !filtro.getSlaPrevisitaSeleccionada().isEmpty()) {
               queryStr += " AND a.slaPrevisita = :slaPrevisita ";
            }
            
            if (filtro.getSlaPermisoSeleccionada() != null && !filtro.getSlaPermisoSeleccionada().isEmpty()) {
               queryStr += " AND a.slaPermisos = :slaPermisos ";
            }
            
            if (filtro.getDptoSelecionado() != null && !filtro.getDptoSelecionado().isEmpty()) {
               queryStr += " AND UPPER(a.departamento.gpoNombre) LIKE :departamento ";
            }
            
            if (filtro.getCiudadSelecionada() != null && !filtro.getCiudadSelecionada().isEmpty()) {
               queryStr += " AND UPPER(a.ciudad.gpoNombre) LIKE :ciudad ";
            } 
            
            if (filtro.getCentroPobladoSelecionado() != null && !filtro.getCentroPobladoSelecionado().isEmpty()) {
               queryStr += " AND UPPER(a.centroPoblado.gpoNombre) LIKE :centroPoblado ";
            }
            
            if (filtro.getNombreArrendaSelecionado() != null && !filtro.getNombreArrendaSelecionado().isEmpty()) {
               queryStr += " AND UPPER(a.nombreArrendatario) LIKE :nombreArrendatario ";
            }
            
            if (filtro.getCuadranteSeleccionado() != null && !filtro.getCuadranteSeleccionado().isEmpty()) {
               queryStr += " AND UPPER(a.cuadrante) LIKE :cuadrante ";
            }
            
            queryStr += " ORDER BY a.arrendatarioId DESC ";

            Query query = entityManager.createQuery(queryStr);            
            query.setParameter("estadoRegistro", 1);
            
            if (filtro.getIdArrendaSeleccionado() != null
                    && !filtro.getIdArrendaSeleccionado().equals(BigDecimal.ZERO)) {
                query.setParameter("arrendatarioId", filtro.getIdArrendaSeleccionado());
            }
                        
            if (filtro.getPrevisitaSeleccionada() != null && !filtro.getPrevisitaSeleccionada().isEmpty()) {
                query.setParameter("previsita", "%"+filtro.getPrevisitaSeleccionada().trim().toUpperCase()+"%");
            }
            
            if (filtro.getSlaPrevisitaSeleccionada() != null && !filtro.getSlaPrevisitaSeleccionada().isEmpty()) {
                query.setParameter("slaPrevisita", filtro.getSlaPrevisitaSeleccionada().trim());
            }
            
            if (filtro.getSlaPermisoSeleccionada() != null && !filtro.getSlaPermisoSeleccionada().isEmpty()) {
                query.setParameter("slaPermisos", filtro.getSlaPermisoSeleccionada().trim());
            } 
            
            if (filtro.getDptoSelecionado() != null && !filtro.getDptoSelecionado().isEmpty()) {
                query.setParameter("departamento", "%"+filtro.getDptoSelecionado().trim().toUpperCase()+"%");
            }
            
            if (filtro.getCiudadSelecionada() != null && !filtro.getCiudadSelecionada().isEmpty()) {
                query.setParameter("ciudad", "%"+filtro.getCiudadSelecionada().trim().toUpperCase()+"%");
            }
            
            if (filtro.getCentroPobladoSelecionado() != null && !filtro.getCentroPobladoSelecionado().isEmpty()) {
                query.setParameter("centroPoblado", "%"+filtro.getCentroPobladoSelecionado().trim().toUpperCase()+"%");
            }
            
            if (filtro.getNombreArrendaSelecionado() != null && !filtro.getNombreArrendaSelecionado().isEmpty()) {
                query.setParameter("nombreArrendatario", "%"+filtro.getNombreArrendaSelecionado().trim().toUpperCase()+"%");
            }
            
            if (filtro.getCuadranteSeleccionado() != null && !filtro.getCuadranteSeleccionado().isEmpty()) {
                query.setParameter("cuadrante", "%"+filtro.getCuadranteSeleccionado().trim().toUpperCase()+"%");
            }
            
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<ArrendatarioMgl> result = query.getResultList();
            getEntityManager().clear();
            return result;
                       
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }        
    }
}
