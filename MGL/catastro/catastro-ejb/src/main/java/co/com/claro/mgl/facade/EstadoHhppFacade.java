
package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.EstadoHhppMglManager;
import co.com.claro.mgl.dao.impl.EstadoHhppDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.EstadoHhpp;
import co.com.claro.mgl.jpa.entities.EstadoHhppMgl;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Parzifal de León
 */
@Stateless
public class EstadoHhppFacade implements EstadoHhppFacadeLocal{
    private final EstadoHhppDaoImpl estadoHhppDao;

    public EstadoHhppFacade() {
        this.estadoHhppDao = new EstadoHhppDaoImpl();
    }
    
    @Override
    public List<EstadoHhpp> findAll(){
        return estadoHhppDao.findAll();
    }
    /**
     * Buscar el estado Hhpp
     * 
     * Se solicita la busqueda del estado del hhpp.
     * @param ehh_id
     * @return
     * @throws ApplicationException 
     */
    @Override
    public EstadoHhppMgl findId(String ehh_id) throws ApplicationException{              
      EstadoHhppMglManager manager=new EstadoHhppMglManager();
        return manager.find(ehh_id);
    }
}
