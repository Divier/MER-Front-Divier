/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.direcciones.facade;

import co.com.claro.direcciones.business.NegocioNodo;
import co.com.claro.direcciones.entities.Nodo;
import co.com.claro.mgl.error.ApplicationException;
import java.util.List;
import javax.ejb.Remote;
import javax.ejb.Stateless;

/**
 * Clase nodosBeanFacade
 * @author 	Davide Marangoni
 * @version     version 1.2
 * @date        2013/09/06
 */
@Stateless(name = "NodosBeanFacadeEJB", mappedName = "NodosBeanFacadeEJB", description = "Nodos")
@Remote({NodosBeanFacadeEJBRemote.class})
public class NodosBeanFacadeEJB implements NodosBeanFacadeEJBRemote {

@Override
public List<Nodo> listaNodosGeograficoPolitico(String pais, String departamento, String ciudad, String tipo) throws ApplicationException{
        NegocioNodo negocioNodo=new NegocioNodo();
        return negocioNodo.consultaNodoGeopolitico(pais, departamento, ciudad, tipo);
    }
}
