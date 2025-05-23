/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.RRNodosDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.RRNodos;
import java.util.List;

/**
 *
 * @author User
 */
public class RRNodosManager {

    public RRNodos findNodeNFIByCommunity(String codCiudad) throws ApplicationException {
        RRNodosDaoImpl nodosDaoImpl = new RRNodosDaoImpl();
        return nodosDaoImpl.findNodeNFIByCommunity(codCiudad);
    }

    public List<RRNodos> findNodesByCodCiudad(String codCiudad) throws ApplicationException {
        RRNodosDaoImpl nodosDaoImpl = new RRNodosDaoImpl();
        return nodosDaoImpl.findNodesByCodCiudad(codCiudad);

    }
}
