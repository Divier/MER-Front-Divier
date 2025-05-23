package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.cm.DepartamentoMglManager;
import co.com.claro.mgl.dtos.DepartamentoDTO;
import co.com.claro.mgl.error.ApplicationException;

import javax.ejb.Stateless;
import java.util.List;

/**
 * Clase encargada de buscar la lista de Departamentos disponibles
 * @author dayver.delahoz@vasslatam.com
 * @version Brief100417
 */
@Stateless
public class DepartamentoMglFacade implements IDepartamentoMglFacadeLocal {
    @Override
    public List<DepartamentoDTO> listarDepartamentos() throws ApplicationException {
        DepartamentoMglManager departamentoMglManager = new DepartamentoMglManager();
        return departamentoMglManager.listarDepartamentos();
    }
}
