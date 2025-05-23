package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.cm.CentroPobladoMglManager;
import co.com.claro.mgl.dtos.CentroPobladoDTO;
import co.com.claro.mgl.error.ApplicationException;

import javax.ejb.Stateless;
import java.util.List;

/**
 * Clase encargada de buscar la lista de centros poblados disponibles
 * por departamento y ciudad
 * @author dayver.delahoz@vasslatam.com
 * @version Brief100417
 */
@Stateless
public class CentroPobladoMglFacade implements ICentroPobladoMglFacadeLocal {
    @Override
    public List<CentroPobladoDTO> listarCentroPobladoPorDepartamentoYCiudad(String departamento, String ciudad) throws ApplicationException {
        CentroPobladoMglManager centroPobladoMglManager = new CentroPobladoMglManager();
        return centroPobladoMglManager.listarCentroPobladoPorDepartamentoYCiudad(departamento, ciudad);
    }
}
