/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.direcciones.facade;


import co.com.claro.direcciones.entities.Nodo;
import co.com.claro.mgl.error.ApplicationException;
import java.util.List;
import javax.ejb.Remote;

/**
 * Clase nodosBeanFacadeLocal
 * @author 	Davide Marangoni
 * @version     version 1.2
 * @date        2013/09/06
 */
@Remote
public interface NodosBeanFacadeEJBRemote {
    public List<Nodo> listaNodosGeograficoPolitico(String pais, String departamento, String ciudad, String tipo)throws ApplicationException;
    }