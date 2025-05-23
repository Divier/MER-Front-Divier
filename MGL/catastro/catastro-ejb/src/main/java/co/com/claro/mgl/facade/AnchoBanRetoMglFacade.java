package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.cm.AnchoBanRetoMglManager;
import co.com.claro.mgl.dtos.AnchoBanRetoDTO;
import co.com.claro.mgl.error.ApplicationException;

import javax.ejb.Stateless;
import java.util.List;

/**
 * Clase encargada de buscar la lista de Ancho de banda reto disponibles
 * @author dayver.delahoz@vasslatam.com
 * @version Brief100417
 */
@Stateless
public class AnchoBanRetoMglFacade implements IAnchoBanRetoMglFacadeLocal {
    @Override
    public List<AnchoBanRetoDTO> listarAnchoBanReto() throws ApplicationException {
        AnchoBanRetoMglManager anchoBanRetoMglManager = new AnchoBanRetoMglManager();
        return anchoBanRetoMglManager.listarAnchoBanReto();
    }
}
