package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.CiudadMglManagerDaoImpl;
import co.com.claro.mgl.dtos.CiudadDTO;
import co.com.claro.mgl.error.ApplicationException;

import java.util.List;

/**
 * Clase encargada de buscar la lista de ciudades por departamento disponibles
 * @author dayver.delahoz@vasslatam.com
 * @version Brief100417
 */
public class CiudadMglManager {

    public List<CiudadDTO> ciudadesPorDepartamento(String departamento) throws ApplicationException {
        CiudadMglManagerDaoImpl ciudadMglDaoImpl = new CiudadMglManagerDaoImpl();
        return ciudadMglDaoImpl.consultarCiudadesPorDepartamento(departamento);
    }
}
