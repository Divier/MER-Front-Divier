/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.EstadoHhpp;
import co.com.claro.mgl.jpa.entities.EstadoHhppMgl;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Parzifal de León
 */
@Local
public interface EstadoHhppFacadeLocal {
    
    public List<EstadoHhpp> findAll();

  public EstadoHhppMgl findId(String ehh_id) throws ApplicationException;
}
