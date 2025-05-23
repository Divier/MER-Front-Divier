package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.DepartamentoMglManagerDaoImpl;
import co.com.claro.mgl.dtos.DepartamentoDTO;
import co.com.claro.mgl.error.ApplicationException;

import java.util.List;

/**
 * Manager encargada de buscar la lista de Departamentos disponibles
 * @author dayver.delahoz@vasslatam.com
 * @version Brief100417
 */
public class DepartamentoMglManager {

    public List<DepartamentoDTO> listarDepartamentos() throws ApplicationException {
        DepartamentoMglManagerDaoImpl departamentoMglDaoImpl = new DepartamentoMglManagerDaoImpl();
        return departamentoMglDaoImpl.consultarDepartamentos();
    }
}
