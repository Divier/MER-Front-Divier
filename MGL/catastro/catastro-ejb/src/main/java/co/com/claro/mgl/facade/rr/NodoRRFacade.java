/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.rr;

import co.com.claro.mgl.businessmanager.address.rr.NodoRRManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.rr.NodoRR;
import javax.ejb.Stateless;

/**
 *
 * @author User
 */
@Stateless
public class NodoRRFacade implements NodoRRFacadeLocal {

    NodoRRManager manager = new NodoRRManager();

    @Override
    public NodoRR find(String id) throws ApplicationException {
        return manager.findNodo(id);
    }

    @Override
    public boolean isNodoRRCertificado(String codNodo, String user) throws ApplicationException {
        return manager.isNodoRRCertificado(codNodo, user);
    }
    /**
     * Retorna el objeto nodo desde la Tabla RR_NODO en BD repositorio.Recibe como argumento el codigo del nodo
     *
     * @param codNodo codigo alfanumerico del nodo
     * @return objeto co.com.claro.visitasTecnicas.business.NodoRR desde la tabla RR_NODO
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    @Override
    public co.com.claro.visitasTecnicas.business.NodoRR getRRNodoFromDB(String codNodo) throws ApplicationException {
        return manager.getRRNodoFromDB(codNodo);
    }
}
