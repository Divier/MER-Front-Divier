package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.cm.CiudadMglManager;
import co.com.claro.mgl.dtos.CiudadDTO;
import co.com.claro.mgl.error.ApplicationException;

import javax.ejb.Stateless;
import java.util.List;

/**
 * Clase encargada de buscar la lista de ciudades por departamento disponibles
 * @author dayver.delahoz@vasslatam.com
 * @version Brief100417
 */
@Stateless
public class CiudadMglFacade implements ICiudadMglFacadeLocal {
    @Override
    public List<CiudadDTO> listarCiudadesPorDepartamento(String departamento) throws ApplicationException {
        CiudadMglManager ciudadMglManager = new CiudadMglManager();
        return ciudadMglManager.ciudadesPorDepartamento(departamento);
    }
}
