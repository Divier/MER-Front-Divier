/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.RRNodos;
import java.util.List;

/**
 *
 * @author GLAFH
 */
public interface RRNodosFacadeLocal {

    public RRNodos findNodeNFIByCommunity(String codCiudad) throws ApplicationException;

    public List<RRNodos> findNodesByCodCiudad(String codCiudad) throws ApplicationException;
}
