/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.UbicacionMgl;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author Admin
 */
public class UbicacionMglDaoImpl extends GenericDaoImpl<UbicacionMgl> {

    public List<UbicacionMgl> findAll() throws ApplicationException {
        List<UbicacionMgl> resultList;
        Query query = entityManager.createNamedQuery("UbicacionMgl.findAll");
        query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
        resultList = (List<UbicacionMgl>) query.getResultList();
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
       public boolean actualizarCoordenadasGeoDireccionMgl(UbicacionMgl ubicacion) throws ApplicationException {
        try {
            entityManager.getTransaction().begin();
            Query q = entityManager.createQuery("UPDATE UbicacionMgl c SET c.ubiLatitud = :ubiLatitud, "
                    + "c.ubiLongitud = :ubiLongitud, c.ubiLatitudNum =:ubiLatitudNum, "
                    + " c.ubiLongitudNum =:ubiLongitudNum, c.ubiFechaModificacion =:fechaEdicion  "
                    + "WHERE  c.ubiId = :ubiId ");     

            q.setParameter("ubiLatitud", ubicacion.getUbiLatitud());
            q.setParameter("ubiLongitud", ubicacion.getUbiLongitud());   
            q.setParameter("ubiLatitudNum", ubicacion.getUbiLatitudNum());
            q.setParameter("ubiLongitudNum", ubicacion.getUbiLongitudNum());  
            q.setParameter("fechaEdicion", new Date());   
            q.setParameter("ubiId", ubicacion.getUbiId());

            q.executeUpdate();
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {        
            return false;
        }
    }
    
   /**
     * Metodo para consultar una ubicacion por centro poblado- longitud and latitud
     * @param centro centro oblado de la direccion.
     * @param longitud  de la direccion.
     * @param latitud  de la direccion.
     * @return UbicacionMgl
     *
     * @author Victor Bocanegra
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public UbicacionMgl findByCentroAndLongAndLat(BigDecimal centro, String longitud,
            String latitud) throws ApplicationException {

        try {
            List<UbicacionMgl> resulList;
            UbicacionMgl result = null;

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT s FROM UbicacionMgl s "
                    + " WHERE s.gpoIdObj.gpoId = :gpoId "
                    + " AND s.ubiLongitud = :ubiLongitud "
                    + " AND s.ubiLatitud = :ubiLatitud");

            Query query = entityManager.createQuery(sql.toString());
            query.setParameter("gpoId", centro);
            query.setParameter("ubiLongitud", longitud);
            query.setParameter("ubiLatitud", latitud);
            query.setMaxResults(5);

            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            resulList = query.getResultList();
            if (resulList != null && !resulList.isEmpty()) {
                result = resulList.get(0);
            }
            return result;
        } catch (NoResultException ex) {
            return null;
        } catch (Exception ex) {
            return null;
        }

    }
}
