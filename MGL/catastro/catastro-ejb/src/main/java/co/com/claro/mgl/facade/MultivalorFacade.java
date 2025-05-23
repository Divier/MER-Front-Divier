/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.MultivalorManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.Multivalor;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 * objetivo de la clase. 
 * Clase de tipo implementacion interface bean de session de la tabla multivalor.
 *
 * @author Carlos Leonardo Villamil
 * @versión 1.00.000
 */
@Stateless
public class MultivalorFacade implements MultivalorFacadeLocal {

    MultivalorManager multivalorManager = new MultivalorManager();

    @Override
    public List<Multivalor> findAll() throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Multivalor create(Multivalor t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Multivalor update(Multivalor t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(Multivalor t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Multivalor findById(Multivalor sqlData) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Multivalor> loadPoliticasVeto() throws ApplicationException {
        return multivalorManager.loadPoliticasVeto();
    }

    @Override
    public List<Multivalor> loadTipoRed() throws ApplicationException {
        return multivalorManager.loadTipoRed();
    }

    @Override
    public List<Multivalor> loadTipoVeto() throws ApplicationException {
        return multivalorManager.loadTipoVeto();
    }
    
    /**
     * Descripción del objetivo del método.Busca un codigo de grupo y valor en la tabla multivalor.
     *     
     * @author Carlos Leonardo Villamil
     * @param idGmu Grupo multivalor.
     * @param mulValor key de usuario de la tabla .
     * @return Multivalor Descripcion de la tabla multivalor.
     * @throws co.com.claro.mgl.error.ApplicationException
     */   
    @Override
    public Multivalor findByIdGmuAndmulValor(
                BigDecimal idGmu, String mulValor) 
                throws ApplicationException{
        return multivalorManager.findByIdGmuAndmulValor(idGmu, mulValor);
    }    

    @Override
    public List<Multivalor> loadSocioEconomico(BigDecimal idSocioE) throws ApplicationException {
        return multivalorManager.loadSocioEconomico(idSocioE);
    }

    
}
