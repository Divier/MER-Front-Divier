/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.direcciones.delegate;

import co.com.claro.direcciones.entities.*;
import co.com.claro.direcciones.facade.NodosBeanFacadeEJBRemote;
import co.com.claro.mgl.error.ApplicationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Clase DelegateNodos
 * @author 	Davide Marangoni
 * @version     version 1.2
 * @date        2013/09/06
 */
public class DelegateNodos {

    private static final Logger LOGGER = LogManager.getLogger(DelegateNodos.class);
    private static String NODOBEANFACADEEJB = "NodosBeanFacadeEJB#co.com.claro.direcciones.facade.NodosBeanFacadeEJBRemote";
    
    
    
    
    public static NodosBeanFacadeEJBRemote getNodoBeanFacadeEjb() throws ApplicationException, NamingException {
        InitialContext ctx = new InitialContext();
        NodosBeanFacadeEJBRemote obj = (NodosBeanFacadeEJBRemote) ctx.lookup(NODOBEANFACADEEJB);
        return obj;
    }

    /**
     * @param tipo
     * @return nodoBI
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public static List<Nodo> queryNodos(String pais, String departamento, String ciudad, String tipo) throws ApplicationException {
        List<Nodo> listaNodos = null;
        try {
            NodosBeanFacadeEJBRemote obj = getNodoBeanFacadeEjb();
            if (obj != null) {
                listaNodos = obj.listaNodosGeograficoPolitico(pais, departamento, ciudad, tipo);
            }
        } catch (NamingException ex) {
            LOGGER.error("Error al consultar los nodos. EX000: " + ex.getMessage(), ex);
        }
        return listaNodos;
    }
}
