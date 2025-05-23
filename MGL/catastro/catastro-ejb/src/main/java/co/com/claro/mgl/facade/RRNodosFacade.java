/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.RRNodosManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.RRNodos;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author User
 */
@Stateless
public class RRNodosFacade implements RRNodosFacadeLocal {

    @Override
    public RRNodos findNodeNFIByCommunity(String codCiudad) throws ApplicationException {
        RRNodosManager nodosManager = new RRNodosManager();
        return nodosManager.findNodeNFIByCommunity(codCiudad);
    }

    @Override
    public List<RRNodos> findNodesByCodCiudad(String codCiudad) throws ApplicationException {
        RRNodosManager nodosManager = new RRNodosManager();
        return nodosManager.findNodesByCodCiudad(codCiudad);
    }
}
