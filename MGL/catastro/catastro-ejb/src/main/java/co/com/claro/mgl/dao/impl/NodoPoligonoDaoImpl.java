package co.com.claro.mgl.dao.impl;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.NodoPoligono;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.PersistenceException;
import javax.persistence.Query;


/**
 * Data Access Object para el manejo de la entidad NodoPoligono.<br>
 * Tabla: <i>TEC_NODO_POLIGONO</i>.
 * 
 * @author Camilo Miranda (<i>mirandaca</i>).
 */
public class NodoPoligonoDaoImpl extends GenericDaoImpl<NodoPoligono> {

    private static final Logger LOGGER = LogManager.getLogger(NodoPoligonoDaoImpl.class);
    
    /**
     * Obtiene el listado de los Nodo Pol&iacute;gono asociados al Nodo V&eacute;rtice especificado.
     * @param idNodoVertice Identificador del Nodo V&eacute;rtice.
     * @return Listado de Nodos que conforman el Pol&iacute;gono.
     * @throws ApplicationException 
     */
    public List<NodoPoligono> findByNodoVertice(BigDecimal idNodoVertice) 
            throws ApplicationException {
        
        List <NodoPoligono> result;
        try {
            Query query = entityManager.createNamedQuery("NodoPoligono.findByNodoVertice");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");//jpa 2.0
            query.setParameter("nodId", idNodoVertice);
            result = query.getResultList();
            getEntityManager().clear();
            return result;
        } catch (PersistenceException e) {
            String msg = "Se produjo un error al momento de consultar los nodos "
                    + "del polígono en '" 
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': "
                    + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(e.getMessage(), e);
        }
    }
    
    /**
     * Obtiene Poligonos de nodos por coordenadas de la direccion
     *
     * @param coordenadasDto contiene el codigo dane con el que se desea filtrar
     * @author Victor Bocanegra
     * @return List<NodoPoligono>
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public List<NodoPoligono> findNodosByCoordenadasDir(double latitudRestada,
            double longitudRestada, double latitudAumentada, double longitudAumentada)
            throws ApplicationException {
        List<NodoPoligono> nodosPoligonosLst;
        try {
            Query query = entityManager.createQuery("SELECT n FROM NodoPoligono n  "
                    + " WHERE n.y BETWEEN :latitudRestada AND :latitudAumentada "
                    + "AND n.x BETWEEN :longitudRestada AND :longitudAumentada ");

            query.setParameter("latitudRestada", latitudRestada);
            query.setParameter("longitudRestada", longitudRestada);
            query.setParameter("latitudAumentada", latitudAumentada);
            query.setParameter("longitudAumentada", longitudAumentada);

            nodosPoligonosLst = query.getResultList();
            getEntityManager().clear();
            return nodosPoligonosLst;

        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error buscando nodos poligonos por coordenadas"
                    + " debido a: " + ex.getMessage());
        }
    }
    
    /**
     * Obtiene Poligonos de nodos por coordenadas de la direccion-Tecnologia y
     * centro poblado
     *
     * @param latitudRestada
     * @param longitudRestada
     * @param latitudAumentada
     * @param longitudAumentada
     * @param tecnologia
     * @param centro
     * @author Victor Bocanegra
     * @return List<NodoPoligono>
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public List<NodoPoligono> findNodosByCoordenadasDirTecnoAndCentro(double latitudRestada,
            double longitudRestada, double latitudAumentada,
            double longitudAumentada, CmtBasicaMgl tecnologia, GeograficoPoliticoMgl centro)
            throws ApplicationException {
        List<NodoPoligono> nodosPoligonosLst;
        try {
            Query query = entityManager.createQuery("SELECT n FROM NodoPoligono n "
                    + " WHERE n.y BETWEEN :latitudRestada AND :latitudAumentada "
                    + " AND n.x BETWEEN :longitudRestada AND :longitudAumentada  "
                    + " AND n.nodoVertice.gpoId = :gpoId  "
                    + " AND n.nodoVertice.nodTipo = :nodTipo "
                    + " AND n.nodoVertice.estadoRegistro = 1");

            query.setParameter("latitudRestada", latitudRestada);
            query.setParameter("longitudRestada", longitudRestada);
            query.setParameter("latitudAumentada", latitudAumentada);
            query.setParameter("longitudAumentada", longitudAumentada);
            query.setParameter("gpoId", centro.getGpoId());
            query.setParameter("nodTipo", tecnologia);

            nodosPoligonosLst = query.getResultList();
            getEntityManager().clear();
            return nodosPoligonosLst;

        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error buscando nodos poligonos por coordenadas"
                    + " debido a: " + ex.getMessage());
        }
    }
    
    /**
     * Obtiene Poligonos de nodos por coordenadas de la direccion-Tecnologia y
     * centro poblado diferentes del nodo referencia
     *
     * @param latitudRestada
     * @param longitudRestada
     * @param latitudAumentada
     * @param longitudAumentada
     * @param tecnologia
     * @param centro
     * @param nodoRef
     * @author Victor Bocanegra
     * @return List<NodoPoligono>
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public List<NodoPoligono> findNodosByCoordenadasDirTecnCentroAndDifNodo(double latitudRestada,
            double longitudRestada, double latitudAumentada,
            double longitudAumentada, CmtBasicaMgl tecnologia, GeograficoPoliticoMgl centro,
            NodoMgl nodoRef)
            throws ApplicationException {
        List<NodoPoligono> nodosPoligonosLst;
        try {
            Query query = entityManager.createQuery("SELECT n FROM NodoPoligono n "
                    + " WHERE n.y BETWEEN :latitudRestada AND :latitudAumentada "
                    + " AND n.x BETWEEN :longitudRestada AND :longitudAumentada  "
                    + " AND n.nodoVertice.gpoId = :gpoId  "
                    + " AND n.nodoVertice.nodTipo = :nodTipo "
                    + " AND n.nodoVertice.estadoRegistro = 1 "
                    + " AND n.nodoVertice.nodId != :nodId "
                    + " AND n.nodoVertice.limites != :limites");

            query.setParameter("latitudRestada", latitudRestada);
            query.setParameter("longitudRestada", longitudRestada);
            query.setParameter("latitudAumentada", latitudAumentada);
            query.setParameter("longitudAumentada", longitudAumentada);
            query.setParameter("gpoId", centro.getGpoId());
            query.setParameter("nodTipo", tecnologia);
            query.setParameter("nodId", nodoRef.getNodId());
            query.setParameter("limites", nodoRef.getLimites());

            nodosPoligonosLst = query.getResultList();
            getEntityManager().clear();
            return nodosPoligonosLst;

        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException("Error buscando nodos poligonos por coordenadas"
                    + " debido a: " + ex.getMessage());
        }
    }

}
