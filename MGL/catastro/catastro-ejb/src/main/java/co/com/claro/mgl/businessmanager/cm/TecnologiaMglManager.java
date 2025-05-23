package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.TecnologiaMglManagerDaoImpl;
import co.com.claro.mgl.dtos.TecnologiaDTO;
import co.com.claro.mgl.error.ApplicationException;

import java.util.List;

/**
 * Manager encargado de buscar la lista de tecnologías disponibles
 * @author dayver.delahoz@vasslatam.com
 * @version Brief100417
 */
public class TecnologiaMglManager {

    public List<TecnologiaDTO> listarTecnologias() throws ApplicationException {
        TecnologiaMglManagerDaoImpl tecnologiaMglDaoImpl = new TecnologiaMglManagerDaoImpl();
        return tecnologiaMglDaoImpl.consultarTecnologia();
    }
}
