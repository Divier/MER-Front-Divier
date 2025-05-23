package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.LogActualizaDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.LogActualizaMaster;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Manager para la consulta del Master Nap <i>TEC_LOG_NAP_MASTER</i>.
 *
 * @author duartey
 */
public class LogActualizaMglManager {

    LogActualizaDaoImpl daoImpl = new LogActualizaDaoImpl();

    public List<LogActualizaMaster> findAll() throws ApplicationException {
        LogActualizaDaoImpl logActualizaMglDaoImpl = new LogActualizaDaoImpl();
        return logActualizaMglDaoImpl.findAll();
    }
    public List<LogActualizaMaster> getListLogActualizaByTroncal(String troncal) throws ApplicationException {
        LogActualizaDaoImpl logActualizaMglDaoImpl = new LogActualizaDaoImpl();
        return logActualizaMglDaoImpl.getListLogActualizaByTroncal(troncal);
    }
    public List<LogActualizaMaster> getListLogActualizaGroupByTroncal() throws ApplicationException {
        LogActualizaDaoImpl logActualizaMglDaoImpl = new LogActualizaDaoImpl();
        return logActualizaMglDaoImpl.getListLogActualizaGroupByTroncal();
    }
    public List<LogActualizaMaster> getListLogActualizaByTipoTecnologia(String tipoTecnologia) throws ApplicationException {
        LogActualizaDaoImpl logActualizaMglDaoImpl = new LogActualizaDaoImpl();
        return logActualizaMglDaoImpl.getListLogActualizaByTipoTecnologia(tipoTecnologia);
    }
    public List<LogActualizaMaster> getListLogActualizaGroupByTipoTec() throws ApplicationException {
        LogActualizaDaoImpl logActualizaMglDaoImpl = new LogActualizaDaoImpl();
        return logActualizaMglDaoImpl.getListLogActualizaGroupByTipoTec();
    }
    public List<LogActualizaMaster> getListLogActualizaByLikeNombreArchivo(String nombrearchivo) throws ApplicationException {
        LogActualizaDaoImpl logActualizaMglDaoImpl = new LogActualizaDaoImpl();
        return logActualizaMglDaoImpl.getListLogActualizaByLikeNombreArchivo(nombrearchivo);
    }
    public List<LogActualizaMaster> getListLogActualizaByLikeDepartamento(String departamento) throws ApplicationException {
        LogActualizaDaoImpl logActualizaMglDaoImpl = new LogActualizaDaoImpl();
        return logActualizaMglDaoImpl.getListLogActualizaByDepartamento(departamento);
    }
    public List<LogActualizaMaster> getListLogActualizaGroupByDepartamento() throws ApplicationException {
        LogActualizaDaoImpl logActualizaMglDaoImpl = new LogActualizaDaoImpl();
        return logActualizaMglDaoImpl.getListLogActualizaGroupByDepartamento();
    }
    public List<LogActualizaMaster> getListLogActualizaByCiudad(String ciudad) throws ApplicationException {
        LogActualizaDaoImpl logActualizaMglDaoImpl = new LogActualizaDaoImpl();
        return logActualizaMglDaoImpl.getListLogActualizaByCiudad(ciudad);
    }
    public List<LogActualizaMaster> getListLogActualizaGroupByCiudad() throws ApplicationException {
        LogActualizaDaoImpl logActualizaMglDaoImpl = new LogActualizaDaoImpl();
        return logActualizaMglDaoImpl.getListLogActualizaGroupByCiudad();
    }
    public List<LogActualizaMaster> getListLogActualizaByCentroPoblado(String centroPoblado) throws ApplicationException {
        LogActualizaDaoImpl logActualizaMglDaoImpl = new LogActualizaDaoImpl();
        return logActualizaMglDaoImpl.getListLogActualizaByCentroPoblado(centroPoblado);
    }
    public List<LogActualizaMaster> getListLogActualizaGroupByCentroPoblado() throws ApplicationException {
        LogActualizaDaoImpl logActualizaMglDaoImpl = new LogActualizaDaoImpl();
        return logActualizaMglDaoImpl.getListLogActualizaGroupByCentroPoblado();
    }
    public List<LogActualizaMaster> getListLogActualizaByLikeUsuarioCreacion(String usuario) throws ApplicationException {
        LogActualizaDaoImpl logActualizaMglDaoImpl = new LogActualizaDaoImpl();
        return logActualizaMglDaoImpl.getListLogActualizaByLikeUsuarioCreacion(usuario);
    }

    public LogActualizaMaster create(LogActualizaMaster logActualizaMgl) throws ApplicationException {
        LogActualizaDaoImpl logActualizaDaoImpl = new LogActualizaDaoImpl();
        return logActualizaDaoImpl.create(logActualizaMgl);
    }

    public LogActualizaMaster update(LogActualizaMaster logActualizaMgl,
            String user, int perfil) throws ApplicationException {
        LogActualizaDaoImpl logActualizaDaoImpl = new LogActualizaDaoImpl();
        return logActualizaDaoImpl.updateCm(logActualizaMgl, user, perfil);
    }

    public boolean delete(LogActualizaMaster logActualizaMgl) throws ApplicationException {
        LogActualizaDaoImpl logActualizaDaoImpl = new LogActualizaDaoImpl();
        return logActualizaDaoImpl.delete(logActualizaMgl);
    }

    public LogActualizaMaster findById(LogActualizaMaster logActualizaMgl) throws ApplicationException {
        LogActualizaDaoImpl logActualizaDaoImpl = new LogActualizaDaoImpl();
        return logActualizaDaoImpl.find(logActualizaMgl.getIdNap());
    }

    public List<LogActualizaMaster> getListLogActualizaByFechaRegistro(int paginaSelected,
            int maxResults, boolean paginar, HashMap<String, Object> params, Date fechaInicial, Date fechaFinal) throws ApplicationException {

        int firstResult = 0;
        if (paginaSelected > 1) {
            firstResult = (maxResults * (paginaSelected - 1));
        }
        return daoImpl.getListLogActualizaByFechaRegistro(firstResult, maxResults, paginar, params, fechaInicial, fechaFinal);
    }

}
