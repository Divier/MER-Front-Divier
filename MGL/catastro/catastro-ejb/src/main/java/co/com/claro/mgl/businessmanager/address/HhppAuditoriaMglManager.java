/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.HhppAuditoriaMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Juan David Hernandez
 */
public class HhppAuditoriaMglManager {

    HhppAuditoriaMglDaoImpl hhppAuditoriaMglDaoImpl = new HhppAuditoriaMglDaoImpl();
  
     /**
     * Obtiene listado de historico de suscriptores del hhpp
     *
     * @param idHhpp del cual se desea obtener el historico de clientes
     * @param maxResults
     * @return listado suscriptores que ha tenia el hhpp
     * @author Juan David Hernandez Torres
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public List<String> findHistoricoSuscriptor(BigDecimal idHhpp,
            int paginaSelected, int maxResults)throws ApplicationException {
       
        int firstResult = 0;
        if (paginaSelected > 1) {
            firstResult = (maxResults * (paginaSelected - 1));
        }
        return hhppAuditoriaMglDaoImpl.findHistoricoSuscriptor(idHhpp,firstResult,
                maxResults);
    }
    
    
       /**
     * Obtiene conteo de historico de suscriptores del hhpp
     *
     * @param idHhpp del cual se desea obtener el historico de clientes
     * @return valor conteo suscriptores que ha tenido el hhpp
     * @author Juan David Hernandez Torres
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public int countHistoricoSuscriptor(BigDecimal idHhpp)throws ApplicationException {
       
      return hhppAuditoriaMglDaoImpl.countHistoricoSuscriptor(idHhpp);
    }
    
    
    
}