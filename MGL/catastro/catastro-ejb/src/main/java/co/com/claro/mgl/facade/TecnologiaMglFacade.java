package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.cm.TecnologiaMglManager;
import co.com.claro.mgl.dtos.TecnologiaDTO;
import co.com.claro.mgl.error.ApplicationException;

import javax.ejb.Stateless;
import java.util.List;

/**
 * Clase encargada de buscar la lista de tecnologías disponibles
 * @author dayver.delahoz@vasslatam.com
 * @version Brief100417
 */
@Stateless
public class TecnologiaMglFacade implements ITecnologiaMglFacadeLocal {
    @Override
    public List<TecnologiaDTO> listarTecnologias() throws ApplicationException {
        TecnologiaMglManager tecnologiaMglManager = new TecnologiaMglManager();
        return tecnologiaMglManager.listarTecnologias();
    }
}
