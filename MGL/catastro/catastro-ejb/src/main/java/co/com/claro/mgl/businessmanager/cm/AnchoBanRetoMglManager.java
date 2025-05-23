
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.AnchoBanRetoMglManagerDaoImpl;
import co.com.claro.mgl.dtos.AnchoBanRetoDTO;
import co.com.claro.mgl.error.ApplicationException;

import java.util.List;

/**
 * Manager encargada de buscar la lista de Ancho de banda reto disponibles
 * @author dayver.delahoz@vasslatam.com
 * @version Brief100417
 */
public class AnchoBanRetoMglManager {

    public List<AnchoBanRetoDTO> listarAnchoBanReto() throws ApplicationException {
        AnchoBanRetoMglManagerDaoImpl anchoBanRetoMglDaoImpl = new AnchoBanRetoMglManagerDaoImpl();
        return anchoBanRetoMglDaoImpl.consultarAnchoBanReto();
    }
}
