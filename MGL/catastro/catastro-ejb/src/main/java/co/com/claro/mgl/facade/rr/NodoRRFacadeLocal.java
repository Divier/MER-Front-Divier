/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.rr;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.rr.NodoRR;

/**
 *
 * @author User
 */
public interface NodoRRFacadeLocal {

    NodoRR find(String id) throws ApplicationException;

    boolean isNodoRRCertificado(String codNodo, String user) throws ApplicationException;

    /**
     * Retorna el objeto nodo desde la Tabla RR_NODO en BD repositorio.Recibe
        como argumento el codigo del nodo
     *
     * @param codNodo codigo alfanumerico del nodo
     * @return objeto co.com.claro.visitasTecnicas.business.NodoRR desde la
     * tabla RR_NODO
     * @throws ApplicationException
     */
    co.com.claro.visitasTecnicas.business.NodoRR getRRNodoFromDB(String codNodo) throws ApplicationException;
}
