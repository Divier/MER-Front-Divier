/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.DireccionMgl;
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
public class DireccionMglDaoImpl extends GenericDaoImpl<DireccionMgl> {
    
    private static final Logger LOGGER = LogManager.getLogger(DireccionMglDaoImpl.class);

    public List<DireccionMgl> findAll() throws ApplicationException {
        List<DireccionMgl> resultList;
        Query query = entityManager.createNamedQuery("DireccionMgl.findAll");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
        resultList = (List<DireccionMgl>) query.getResultList();
        return resultList;
    }

    public List<DireccionMgl> findByDireccion(String nombreCalle, BigDecimal idgpo) throws ApplicationException {
        List<DireccionMgl> resultList;

        String queryDir = "SELECT d FROM DireccionMgl d WHERE EXISTS("
                + "SELECT u FROM UbicacionMgl u where u.gpoId = :gpoId and d.ubiId = u.ubiId) ";

        if (nombreCalle != null && !nombreCalle.trim().isEmpty()) {
            queryDir += "and  UPPER(d.dirFormatoIgac) like :dirFormatoIgac ";
        }
        queryDir += " ORDER BY d.dirFormatoIgac ASC";

        Query query = entityManager.createQuery(queryDir);

        query.setParameter("gpoId", idgpo.toString());

        if (nombreCalle != null && !nombreCalle.trim().isEmpty()) {
            query.setParameter("dirFormatoIgac", "%" + nombreCalle.toUpperCase() + "%");
        }
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
        resultList = (List<DireccionMgl>) query.getResultList();
        return resultList;

    }
    
    public List<DireccionMgl> findByDirId(BigDecimal dirId) throws ApplicationException {
        List<DireccionMgl> resultList;
        Query query = entityManager.createNamedQuery("DireccionMgl.findByDirId");
        query.setParameter("dirId", dirId);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
        resultList = (List<DireccionMgl>) query.getResultList();
        return resultList;
    }

    public List<DireccionMgl> findByDireccionAlterna(BigDecimal ubicacionId) 
            throws ApplicationException {
        List<DireccionMgl> resultList;
        Query query = entityManager.createQuery("SELECT d FROM DireccionMgl "
                + "d WHERE d.ubicacion.ubiId in(SELECT a.ubicacion.ubiId "
                + "FROM DireccionMgl a WHERE a.ubicacion.ubiId = :ubicacionId)");
        query.setParameter("ubicacionId", ubicacionId);
         
        resultList = (List<DireccionMgl>) query.getResultList();
        return resultList;

    }

    /**
     * Metodo para consultar la tabla Direccion por codigo de Servi informacion
     * o la direccion no estandarizada.
     *
     * @author alejandro.martine.ext@claro.com.co
     *
     * @param dirServiInfo
     * @param direccion
     * @return resultList
     * @throws ApplicationException
     */
    public List<DireccionMgl> findDireccionByServinfoNoStandar(String dirServiInfo, String direccion) throws ApplicationException {
        List<DireccionMgl> resultList;
        String queryDir = "SELECT d FROM DireccionMgl d WHERE d.dirServinformacion = :dirServinformacion"
                + " OR d.dirNostandar = :dirNostandar";

        Query query = entityManager.createQuery(queryDir, DireccionMgl.class);
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");

        if (direccion != null && !direccion.trim().isEmpty()) {
            query.setParameter("dirServinformacion", dirServiInfo);
            query.setParameter("dirNostandar", direccion);
        }

        resultList = (List<DireccionMgl>) query.getResultList();
        return resultList;
    }
      
      /**
     * Actualiza un registro de la tabla mgl_direccion en los campos correspondientes
     * a las coberturas de red y otros valores obtenidos de serviInformacion por el Georeferenciador
     *
     * @param direccionMgl objeto con el id del registro que se desea actualizar.
     * @return true si se actualiza el registro correctamente
     *
     * @author Juan David Hernandez
     * @throws co.com.claro.mgl.error.ApplicationException
     */
       public boolean actualizarCoberturasGeoDireccionMgl(DireccionMgl direccionMgl) throws ApplicationException {
        try {
            entityManager.getTransaction().begin();
            Query q = entityManager.createQuery("UPDATE DireccionMgl c SET c.dirNodouno = :nodoUno, "
                    + "c.dirNododos= :nodoDos, c.dirNodotres= :nodoTres, c.dirNodoDth= :nodoDth,  "
                    + "c.dirNodoFtth= :nodoFtth, c.dirNodoFttx= :nodoFttx, c.dirNodoMovil= :nodoMovil, c.dirNodoWifi= :nodoWifi, "
                    + "c.geoZonaUnifilar =:nodoZonaUnifilar, c.geoZonaGponDiseniado =:nodoZonaGponDiseniado, "
                    + "c.geoZonaMicroOndas =:nodoZonaMicroOndas, c.geoZona3G =:nodoZona3G, "
                    + "c.geoZona4G =:nodoZona4G, c.geoZonaCoberturaCavs =:nodoZonaCoberturaCavs, "
                    + "c.geoZonaCoberturaUltimaMilla =:nodoZonaCoberturaUltimaMilla, "
                    + "c.geoZonaCurrier =:nodoZonaCurrier, c.geoZona5G =:nodoZona5G, "
                    + "c.dirNivelSocioecono =:nivSocioEconomico, c.fechaEdicion =:fechaEdicion  "
                    + "WHERE  c.dirId = :dirId ");     

            q.setParameter("dirId", direccionMgl.getDirId());
            q.setParameter("nodoUno", direccionMgl.getDirNodouno());
            q.setParameter("nodoDos", direccionMgl.getDirNododos());
            q.setParameter("nodoTres", direccionMgl.getDirNodotres());
            q.setParameter("nodoDth", direccionMgl.getDirNodoDth());
            q.setParameter("nodoFtth", direccionMgl.getDirNodoFtth());
            q.setParameter("nodoFttx", direccionMgl.getDirNodoFttx());
            q.setParameter("nodoMovil", direccionMgl.getDirNodoMovil());
            q.setParameter("nodoWifi", direccionMgl.getDirNodoWifi());
            
            q.setParameter("nodoZonaUnifilar", direccionMgl.getGeoZonaUnifilar());
            q.setParameter("nodoZona3G", direccionMgl.getGeoZona3G());
            q.setParameter("nodoZona4G", direccionMgl.getGeoZona4G());
            q.setParameter("nodoZona5G", direccionMgl.getGeoZona5G());
            q.setParameter("nodoZonaCoberturaCavs", direccionMgl.getGeoZonaCoberturaCavs());
            q.setParameter("nodoZonaCoberturaUltimaMilla", direccionMgl.getGeoZonaCoberturaUltimaMilla());
            q.setParameter("nodoZonaCurrier", direccionMgl.getGeoZonaCurrier());
            q.setParameter("nodoZonaGponDiseniado", direccionMgl.getGeoZonaGponDiseniado());
            q.setParameter("nodoZonaMicroOndas", direccionMgl.getGeoZonaMicroOndas());

            q.setParameter("nivSocioEconomico", direccionMgl.getDirNivelSocioecono());            
            q.setParameter("fechaEdicion", new Date());    
          

            q.executeUpdate();
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            String msgError = "Error en " + ClassUtils.getCurrentMethodName(this.getClass()) + " " + e.getMessage();
            LOGGER.error(msgError, e);
            return false;
        }
    }    
}