/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dao.impl.cm;

import co.com.claro.mgl.dao.impl.GenericDaoImpl;
import co.com.claro.mgl.jpa.entities.cm.CmtCoberturaEntregaMgl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author valbuenayf
 */
public class CmtCoberturaEntregaMglDaoImpl extends GenericDaoImpl<CmtCoberturaEntregaMgl> {

    private static final Logger LOGGER = LogManager.getLogger(CmtCoberturaEntregaMglDaoImpl.class);

    /**
     * valbuenayf Metodo para buscar la lista de cobertura_entrega de un centro
     * poblado
     *
     * @param idCentroPoblado
     * @return
     */
    public List<String> buscarListaCoberturaEntregaIdCentroPoblado(BigDecimal idCentroPoblado) {

        List<String> resulList;

        try {
            Query query = entityManager.createNamedQuery("CmtCoberturaEntregaMgl.findListaCoberturaEntrega");
            query.setParameter("gpoId", idCentroPoblado);
            resulList = query.getResultList();
        } catch (Exception e) {
            LOGGER.error("Error en buscarListaCoberturaEntrega de CmtCoberturaEntregaMglDaoImpl " + e);
            return null;
        }
        return resulList;
    }    
    
    
        /**
     * Obtiene las coberturas de entrega de los operadores logisticos por centro poblado
     *
     * @author Juan David Hernandez
     * @param idCentroPoblado
     * @return listado de coberturas
     */
    public List<CmtCoberturaEntregaMgl> findCoberturaEntregaListByCentroPobladoId(BigDecimal idCentroPoblado){

        List<CmtCoberturaEntregaMgl> resulList;

        try {
            Query query = entityManager.createNamedQuery("CmtCoberturaEntregaMgl.findCoberturaEntregaListByCentroPobladoId");
            query.setParameter("gpoId", idCentroPoblado);
            resulList = query.getResultList();
        } catch (Exception e) {
            LOGGER.error("Error en buscarListaCoberturaEntrega de CmtCoberturaEntregaMglDaoImpl " + e);
            return null;
        }
        return resulList;
    }
    
        /**
     * Obtiene las coberturas de entrega de los operadores logisticos por centro poblado
     *
     * @author Juan David Hernandez
     * @param maxResults
     * @param firstResult
     * @return listado de coberturas
     */
    public List<CmtCoberturaEntregaMgl> findCoberturaEntregaListByCentroPobladoFlitro(
            HashMap<String, Object> filtro, int firstResult, int maxResults){

        List<CmtCoberturaEntregaMgl> resulList;
        String sQuery="SELECT c FROM CmtCoberturaEntregaMgl c "
                    .concat("WHERE C.geograficoPolitico.gpoId = :gpoId "
                    .concat("AND c.estadoRegistro = 1 "));
        try {
            if(filtro.get("tipoInventario")!=null && !((String)filtro.get("tipoInventario")).isEmpty()){            
               sQuery= sQuery.concat("AND upper(c.basica.nombreBasica) LIKE :tipoInventario ");
            }
            if(filtro.get("operador")!=null && !((String)filtro.get("operador")).isEmpty()){            
                sQuery=sQuery.concat("AND upper(c.operadorBasicaID.nombreBasica) LIKE :operador ");
            }
             Query query = entityManager.createQuery(sQuery);
            query.setFirstResult(firstResult);
            query.setMaxResults(maxResults);

            query.setParameter("gpoId", (BigDecimal)filtro.get("gpoId"));
            if(((String)filtro.get("operador"))!=null && !((String)filtro.get("operador")).isEmpty()){
                query.setParameter("operador","%".concat((String)filtro.get("operador")).concat("%").toUpperCase());
            }            
            if(((String)filtro.get("tipoInventario"))!=null && !((String)filtro.get("tipoInventario")).isEmpty()){
                query.setParameter("tipoInventario","%".concat((String)filtro.get("tipoInventario")).concat("%").toUpperCase());
            }            
            resulList = query.getResultList();
        } catch (Exception e) {
            LOGGER.error("Error en buscarListaCoberturaEntrega de CmtCoberturaEntregaMglDaoImpl " + e);
            return null;
        }
        return resulList;
    }

            /**
     * Obtiene las coberturas de entrega de los operadores logisticos por centro poblado
     *
     * @author Juan David Hernandez
     * @param filtro
     * @return listado de coberturas
     */

    public int findCoberturaEntregaListByCentroPobladoFlitroCount(HashMap<String, Object> filtro){

        int numero=0 ;
        String sQuery="SELECT COUNT(1) FROM CmtCoberturaEntregaMgl c  "
                    .concat("WHERE C.geograficoPolitico.gpoId = :gpoId "
                    .concat("AND c.estadoRegistro = 1 "));
        try {
            if(filtro.get("tipoInventario")!=null && !((String)filtro.get("tipoInventario")).isEmpty()){            
               sQuery= sQuery.concat("AND upper(c.basica.nombreBasica) LIKE :tipoInventario ");
            }
            if(filtro.get("operador")!=null && !((String)filtro.get("operador")).isEmpty()){            
                sQuery=sQuery.concat("AND upper(c.operadorBasicaID.nombreBasica) LIKE :operador ");
            }
             Query query = entityManager.createQuery(sQuery);


            query.setParameter("gpoId", (BigDecimal)filtro.get("gpoId"));
            if(((String)filtro.get("operador"))!=null && !((String)filtro.get("operador")).isEmpty()){
                query.setParameter("operador","%".concat((String)filtro.get("operador")).concat("%").toUpperCase());
            }            
            if(((String)filtro.get("tipoInventario"))!=null && !((String)filtro.get("tipoInventario")).isEmpty()){
                query.setParameter("tipoInventario","%".concat((String)filtro.get("tipoInventario")).concat("%").toUpperCase());
            }            
            numero=query.getSingleResult()==null? 
                    0:((Long) query.getSingleResult()).intValue();
        } catch (Exception e) {
            LOGGER.error("Error en buscarListaCoberturaEntrega de CmtCoberturaEntregaMglDaoImpl " + e);
            return 0;
        }
        return numero;
    }

    public List<CmtCoberturaEntregaMgl> findCoberturaEntregaListByCentroPobladoIdOperadorId(BigDecimal centroPobladoId, BigDecimal operadorID) {
        List<CmtCoberturaEntregaMgl> resulList;

        try {
            Query query = entityManager.createNamedQuery("CmtCoberturaEntregaMgl.findCoberturaEntregaListByCentroPobladoIdOperadorId");
            query.setParameter("gpoId", centroPobladoId);
            query.setParameter("operadorId", operadorID);
            resulList = query.getResultList();
        } catch (Exception e) {
            LOGGER.error("Error en buscarListaCoberturaEntrega de CmtCoberturaEntregaMglDaoImpl " + e);
            return null;
        }
        return resulList;
    }
    
}
