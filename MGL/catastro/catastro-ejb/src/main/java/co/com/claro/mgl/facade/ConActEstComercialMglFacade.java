package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.cm.ConActEstComercialMglManager;
import co.com.claro.mgl.dtos.ArchActEstComercialDto;
import co.com.claro.mgl.error.ApplicationException;

import javax.ejb.Stateless;
import java.util.List;

/**
 * Clase encargada de buscar las consultas generadas desde Actualización Estructura Comercial
 * @author dayver.delahoz@vasslatam.com
 * @version Brief100417
 */
@Stateless
public class ConActEstComercialMglFacade implements IConActEstComercialMglFacadeLocal {
    @Override
    public List<ArchActEstComercialDto> listarTodosConsulta() throws ApplicationException {
        ConActEstComercialMglManager conActEstComercialMglManager = new ConActEstComercialMglManager();
        return conActEstComercialMglManager.listarTodosConsulta();
    }

    @Override
    public List<ArchActEstComercialDto> listarTodosCargue() throws ApplicationException {
        ConActEstComercialMglManager conActEstComercialMglManager = new ConActEstComercialMglManager();
        return conActEstComercialMglManager.listarTodosCargue();
    }

    @Override
    public ArchActEstComercialDto listarPorIdConsulta(int id_reporte) throws ApplicationException {
        ConActEstComercialMglManager conActEstComercialMglManager = new ConActEstComercialMglManager();
        return conActEstComercialMglManager.busarArchivoById(id_reporte);
    }

}
