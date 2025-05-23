/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.SubDireccionMgl;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Admin
 */
public class SubDireccionMglDaoImpl extends GenericDaoImpl<SubDireccionMgl> {
    
    private static final Logger LOGGER = LogManager.getLogger(SubDireccionMglDaoImpl.class);

    public List<SubDireccionMgl> findAll() throws ApplicationException {
        List<SubDireccionMgl> resultList;
        Query query = entityManager.createNamedQuery("SubDireccionMgl.findAll");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
        resultList = (List<SubDireccionMgl>) query.getResultList();
        getEntityManager().clear();
        return resultList;
    }

    public List<SubDireccionMgl> findByIdDireccionMgl(BigDecimal dirId) throws ApplicationException {
        List<SubDireccionMgl> resultList;
        Query query = entityManager.createNamedQuery("SubDireccionMgl.findByIdDireccionMgl");
        query.setParameter("dirId", dirId);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
        resultList = (List<SubDireccionMgl>) query.getResultList();
        getEntityManager().clear();
        return resultList;
    }
    
    public List<SubDireccionMgl> findByDirId(BigDecimal dirId) throws ApplicationException {
        List<SubDireccionMgl> resultList;
        Query query = entityManager.createNamedQuery("SubDireccionMgl.findByDirId");
        query.setParameter("dirId", dirId);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
        resultList = (List<SubDireccionMgl>) query.getResultList();
        getEntityManager().clear();
        return resultList;
    }
    
      /**
     * Actualiza un registro de la tabla mgl_direccion en los campos correspondientes
     * a las coberturas de red y otros valores obtenidos de serviInformacion por el Georeferenciador
     *
     * @param subDireccionMgl
     * @param direccionMgl objeto con el id del registro que se desea actualizar.
     * @return true si se actualiza el registro correctamente
     *
     * @author Juan David Hernandez
     * @throws co.com.claro.mgl.error.ApplicationException
     */
       public boolean actualizarCoberturasGeoSubDireccionMgl(SubDireccionMgl subDireccionMgl) throws ApplicationException {
        try {
            entityManager.getTransaction().begin();
            Query q = entityManager.createQuery("UPDATE SubDireccionMgl c SET "                 
                    + "c.sdiNodouno =:nodoUno, c.sdiNododos=:nodoDos, "
                    + "c.sdiNodotres =:nodoTres, c.sdiNodoDth =:nodoDth, "
                    + "c.sdiNodoFtth =:nodoFtth, c.sdiNodoFttx =:nodoFttx, c.sdiNodoMovil =:nodoMovil, "
                    + "c.sdiNodoWifi =:nodoWifi, c.geoZonaUnifilar =:nodoUnifilar, "
                    + "c.geoZonaGponDiseniado =:nodoGponDiseniado, c.geoZonaMicroOndas =:nodoMicroOndas, "
                    + "c.geoZona3G =:nodo3G, c.geoZona4G =:nodo4G, c.geoZonaCoberturaCavs =:nodoCoberturaCavs, "
                    + "c.geoZonaCoberturaUltimaMilla =:nodoCoberturaUltimaMilla, c.geoZonaCurrier =:nodoCurrier, "
                    + "c.geoZona5G =:nodo5G,  "
                    + "c.sdiNivelSocioecono= :nivSocioEconomico, c.fechaEdicion =:fechaEdicion "
                    + "WHERE  c.sdiId = :sdiId ");

            q.setParameter("nivSocioEconomico", subDireccionMgl.getSdiNivelSocioecono()); 
            q.setParameter("sdiId", subDireccionMgl.getSdiId());   
            q.setParameter("nodoUno", subDireccionMgl.getSdiNodouno());
            q.setParameter("nodoDos", subDireccionMgl.getSdiNododos());
            q.setParameter("nodoTres", subDireccionMgl.getSdiNodotres());
            q.setParameter("nodoDth", subDireccionMgl.getSdiNodoDth());
            q.setParameter("nodoFtth", subDireccionMgl.getSdiNodoFtth());
            q.setParameter("nodoFttx", subDireccionMgl.getSdiNodoFttx());
            q.setParameter("nodoMovil", subDireccionMgl.getSdiNodoMovil());
            q.setParameter("nodoWifi", subDireccionMgl.getSdiNodoWifi());
            
            q.setParameter("nodo3G", subDireccionMgl.getGeoZona3G());
            q.setParameter("nodo4G", subDireccionMgl.getGeoZona4G());
            q.setParameter("nodo5G", subDireccionMgl.getGeoZona5G());
            q.setParameter("nodoCoberturaCavs", subDireccionMgl.getGeoZonaCoberturaCavs());
            q.setParameter("nodoCoberturaUltimaMilla", subDireccionMgl.getGeoZonaCoberturaUltimaMilla());
            q.setParameter("nodoCurrier", subDireccionMgl.getGeoZonaCurrier());
            q.setParameter("nodoGponDiseniado", subDireccionMgl.getGeoZonaGponDiseniado());
            q.setParameter("nodoMicroOndas", subDireccionMgl.getGeoZonaMicroOndas());
            q.setParameter("nodoUnifilar", subDireccionMgl.getGeoZonaUnifilar());

            q.setParameter("fechaEdicion", new Date()); 

            q.executeUpdate();
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" 
                  + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                  + e.getMessage();
            LOGGER.error(msg);
            return false;
        }
    }
}
