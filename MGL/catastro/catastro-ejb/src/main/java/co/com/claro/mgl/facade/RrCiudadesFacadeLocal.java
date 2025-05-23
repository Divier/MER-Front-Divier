/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.RrCiudades;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Parzifal de León
 */
@Local
public interface RrCiudadesFacadeLocal extends BaseFacadeLocal<RrCiudades>{
    public List<RrCiudades> findByCodregional(String codregional) throws ApplicationException;
    public RrCiudades findById(String id) throws ApplicationException;
    public RrCiudades findNombreCiudadByCodigo(String codigo) throws ApplicationException;
}
