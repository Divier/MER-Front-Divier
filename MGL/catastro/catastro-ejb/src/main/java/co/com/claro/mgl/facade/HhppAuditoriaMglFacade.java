package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.HhppAuditoriaMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.HhppAuditoriaMgl;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;


/**
 *
 * @author Admin
 */
@Stateless
public class HhppAuditoriaMglFacade implements HhppAuditoriaMglFacadeLocal {

    private final HhppAuditoriaMglManager hhppAuditoriaMglManager;
    private String user;
    private Integer perfil;

    public HhppAuditoriaMglFacade() {
        this.hhppAuditoriaMglManager = new HhppAuditoriaMglManager();
        this.user = "";
        this.perfil = 0;
    }

    @Override
    public void setUser(String user, int perfil) {
        this.user = user;
        this.perfil = perfil;
    }

    @Override
    public List<HhppAuditoriaMgl> findAll() throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HhppAuditoriaMgl create(HhppAuditoriaMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HhppAuditoriaMgl update(HhppAuditoriaMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(HhppAuditoriaMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HhppAuditoriaMgl findById(HhppAuditoriaMgl sqlData) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    

     /**
     * Obtiene listado de historico de suscriptores del hhpp
     *
     * @param idHhpp del cual se desea obtener el historico de clientes
     * @param filasPag
     * @return listado suscriptores que ha tenia el hhpp
     * @author Juan David Hernandez Torres
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    @Override
    public List<String> findHistoricoSuscriptor(BigDecimal idHhpp, 
    int page, int filasPag) throws ApplicationException {
       return hhppAuditoriaMglManager.findHistoricoSuscriptor(idHhpp, page, filasPag);
    }
    
       /**
     * Obtiene conteo de historico de suscriptores del hhpp
     *
     * @param idHhpp del cual se desea obtener el historico de clientes
     * @return valor conteo suscriptores que ha tenido el hhpp
     * @author Juan David Hernandez Torres
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    @Override
    public int countHistoricoSuscriptor(BigDecimal idHhpp) throws ApplicationException {
       return hhppAuditoriaMglManager.countHistoricoSuscriptor(idHhpp);
    }


    
}
