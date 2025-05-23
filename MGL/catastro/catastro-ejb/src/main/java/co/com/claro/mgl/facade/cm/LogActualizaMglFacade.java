package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.LogActualizaMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.LogActualizaMaster;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Stateless;

/**
 * Facade para consulta del Master Nap <i>TEC_LOG_NAP_MASTER</i>.
 *
 * @author duartey
 */
@Stateless
public class LogActualizaMglFacade implements LogActualizaFacadeLocal {

    private String user = "";
    private int perfil = 0;

    @Override
    public List<LogActualizaMaster> findAll() throws ApplicationException {
        LogActualizaMglManager logActualizaMglManager = new LogActualizaMglManager();
        return logActualizaMglManager.findAll();
    }
    @Override
    public List<LogActualizaMaster> getListLogActualizaByTroncal(String troncal) throws ApplicationException {
        LogActualizaMglManager logActualizaMglManager = new LogActualizaMglManager();
        return logActualizaMglManager.getListLogActualizaByTroncal(troncal);
    }
    @Override
    public List<LogActualizaMaster> getListLogActualizaGroupByTroncal() throws ApplicationException {
        LogActualizaMglManager logActualizaMglManager = new LogActualizaMglManager();
        return logActualizaMglManager.getListLogActualizaGroupByTroncal();
    }
    @Override
    public List<LogActualizaMaster> getListLogActualizaByTipoTecnologia(String tipoTecnologia) throws ApplicationException {
        LogActualizaMglManager logActualizaMglManager = new LogActualizaMglManager();
        return logActualizaMglManager.getListLogActualizaByTipoTecnologia(tipoTecnologia);
    }
    @Override
    public List<LogActualizaMaster> getListLogActualizaGroupByTipoTec() throws ApplicationException {
        LogActualizaMglManager logActualizaMglManager = new LogActualizaMglManager();
        return logActualizaMglManager.getListLogActualizaGroupByTipoTec();
    }
    @Override
    public List<LogActualizaMaster> getListLogActualizaByLikeNombreArchivo(String nombrearchivo) throws ApplicationException {
        LogActualizaMglManager logActualizaMglManager = new LogActualizaMglManager();
        return logActualizaMglManager.getListLogActualizaByLikeNombreArchivo(nombrearchivo);
    }
    @Override
    public List<LogActualizaMaster> getListLogActualizaByLikeDepartamento(String departamento) throws ApplicationException {
        LogActualizaMglManager logActualizaMglManager = new LogActualizaMglManager();
        return logActualizaMglManager.getListLogActualizaByLikeDepartamento(departamento);
    }
    @Override
    public List<LogActualizaMaster> getListLogActualizaGroupByDepartamento() throws ApplicationException {
        LogActualizaMglManager logActualizaMglManager = new LogActualizaMglManager();
        return logActualizaMglManager.getListLogActualizaGroupByDepartamento();
    }
    @Override
    public List<LogActualizaMaster> getListLogActualizaByCiudad(String ciudad) throws ApplicationException {
        LogActualizaMglManager logActualizaMglManager = new LogActualizaMglManager();
        return logActualizaMglManager.getListLogActualizaByCiudad(ciudad);
    }
    @Override
    public List<LogActualizaMaster> getListLogActualizaGroupByCiudad() throws ApplicationException {
        LogActualizaMglManager logActualizaMglManager = new LogActualizaMglManager();
        return logActualizaMglManager.getListLogActualizaGroupByCiudad();
    }
    @Override
    public List<LogActualizaMaster> getListLogActualizaByCentroPoblado(String centroPoblado) throws ApplicationException {
        LogActualizaMglManager logActualizaMglManager = new LogActualizaMglManager();
        return logActualizaMglManager.getListLogActualizaByCentroPoblado(centroPoblado);
    }
    @Override
    public List<LogActualizaMaster> getListLogActualizaGroupByCentroPoblado() throws ApplicationException {
        LogActualizaMglManager logActualizaMglManager = new LogActualizaMglManager();
        return logActualizaMglManager.getListLogActualizaGroupByCentroPoblado();
    }
    @Override
    public List<LogActualizaMaster> getListLogActualizaByLikeUsuarioCreacion(String usuario) throws ApplicationException {
        LogActualizaMglManager logActualizaMglManager = new LogActualizaMglManager();
        return logActualizaMglManager.getListLogActualizaByLikeUsuarioCreacion(usuario);
    }

    @Override
    public LogActualizaMaster create(LogActualizaMaster t) throws ApplicationException {
        LogActualizaMglManager logActualizaMglManager = new LogActualizaMglManager();
        return logActualizaMglManager.create(t);
    }

    @Override
    public LogActualizaMaster update(LogActualizaMaster t) throws ApplicationException {
        LogActualizaMglManager logActualizaMglManager = new LogActualizaMglManager();
        return logActualizaMglManager.update(t, user, perfil);
    }

    @Override
    public boolean delete(LogActualizaMaster t) throws ApplicationException {
        LogActualizaMglManager logActualizaMglManager = new LogActualizaMglManager();
        return logActualizaMglManager.delete(t);
    }

    @Override
    public LogActualizaMaster findById(LogActualizaMaster idNap) throws ApplicationException {
        LogActualizaMglManager logActualizaMglManager = new LogActualizaMglManager();
        return logActualizaMglManager.findById(idNap);
    }

    @Override
    public List<LogActualizaMaster> getListFichaToCreateByDate(int firstResult, int maxResults, boolean b, HashMap<String, Object> params, Date fechaInicial, Date fechaFinal) throws ApplicationException {
        LogActualizaMglManager logActualizaMglManager = new LogActualizaMglManager();
        return logActualizaMglManager.getListLogActualizaByFechaRegistro(firstResult, maxResults, b, params, fechaInicial, fechaFinal);
    }
}
