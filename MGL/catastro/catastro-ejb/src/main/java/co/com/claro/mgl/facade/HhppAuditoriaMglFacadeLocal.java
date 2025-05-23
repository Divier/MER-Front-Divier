package co.com.claro.mgl.facade;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.HhppAuditoriaMgl;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Juan David Hernandez
 */
public interface HhppAuditoriaMglFacadeLocal extends BaseFacadeLocal<HhppAuditoriaMgl> {

    void setUser(String user, int perfil);

      /**
     * Obtiene listado de historico de suscriptores del hhpp
     *
     * @param idHhpp del cual se desea obtener el historico de clientes
     * @param filasPag
     * @return listado suscriptores que ha tenia el hhpp
     * @author Juan David Hernandez Torres
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public List<String> findHistoricoSuscriptor(BigDecimal idHhpp,
            int page, int filasPag) throws ApplicationException;
    
     /**
     * Obtiene conteo de historico de suscriptores del hhpp
     *
     * @param idHhpp del cual se desea obtener el historico de clientes
     * @return valor conteo suscriptores que ha tenido el hhpp
     * @author Juan David Hernandez Torres
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public int countHistoricoSuscriptor(BigDecimal idHhpp) throws ApplicationException;
  

 
}




