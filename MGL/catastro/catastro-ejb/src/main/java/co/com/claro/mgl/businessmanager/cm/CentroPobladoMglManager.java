package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.CentroPobladoMglManagerDaoImpl;
import co.com.claro.mgl.dtos.CentroPobladoDTO;
import co.com.claro.mgl.error.ApplicationException;

import java.util.List;

/**
 * Manager encargada de buscar la lista de centros poblados disponibles
 * por departamento y ciudad
 * @author dayver.delahoz@vasslatam.com
 * @version Brief100417
 */
public class CentroPobladoMglManager {

    public List<CentroPobladoDTO> listarCentroPobladoPorDepartamentoYCiudad(String departamento, String ciudad) throws ApplicationException {
        CentroPobladoMglManagerDaoImpl centroPobladoMglManagerDaoImpl = new CentroPobladoMglManagerDaoImpl();
        return centroPobladoMglManagerDaoImpl.consultarCentroPobladoPorDepartamentoYCiudad(departamento, ciudad);
    }
}
