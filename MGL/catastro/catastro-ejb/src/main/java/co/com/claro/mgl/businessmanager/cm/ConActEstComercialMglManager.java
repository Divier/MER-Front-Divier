package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.ConActEstComercialMglManagerDaoImpl;
import co.com.claro.mgl.dtos.ArchActEstComercialDto;
import co.com.claro.mgl.error.ApplicationException;

import java.util.List;

/**
 * MglManager encargada de buscar las consultas generadas desde Actualización Estructura Comercial
 * @author dayver.delahoz@vasslatam.com
 * @version Brief100417
 */
public class ConActEstComercialMglManager {

    /**
     * Consulta todos los registros generados desde programar consulta
     * @return
     * @throws ApplicationException
     */
    public List<ArchActEstComercialDto> listarTodosConsulta() throws ApplicationException {
        ConActEstComercialMglManagerDaoImpl comercialMglManagerDao = new ConActEstComercialMglManagerDaoImpl();
        return comercialMglManagerDao.listarTodosConsulta();
    }

    /**
     * Consulta todos los registros generados desde cargue archivos estructura comercial
     * @return
     * @throws ApplicationException
     */
    public List<ArchActEstComercialDto> listarTodosCargue() throws ApplicationException {
        ConActEstComercialMglManagerDaoImpl comercialMglManagerDao = new ConActEstComercialMglManagerDaoImpl();
        return comercialMglManagerDao.listarTodosCargue();
    }

    /**
     * Consulta registro por ID
     * @param id
     * @return
     * @throws ApplicationException
     */
    public ArchActEstComercialDto busarArchivoById(int id) throws ApplicationException {
        ConActEstComercialMglManagerDaoImpl comercialMglManagerDao = new ConActEstComercialMglManagerDaoImpl();
        return comercialMglManagerDao.buscarById(id);
    }
}
