package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.LogActualizaDetalleMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.LogActualizaDetalle;
import co.com.claro.mgl.jpa.entities.LogActualizaMaster;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 * Facade para consulta del Master Nap <i>TEC_LOG_NAP_MASTER</i>.
 *
 * @author duartey
 */
@Stateless
public class LogActualizaDetalleMglFacade implements LogActualizaDetalleFacadeLocal {

    private String user = "";
    private int perfil = 0;

    @Override
    public List<LogActualizaDetalle> findAll() throws ApplicationException {
        LogActualizaDetalleMglManager logActualizaDetalleMglManager = new LogActualizaDetalleMglManager();
        return logActualizaDetalleMglManager.findAll();
    }
    @Override
    public List<LogActualizaDetalle> findDetalleByIdMaster(BigDecimal idMaster) throws ApplicationException {
        LogActualizaDetalleMglManager logActualizaDetalleMglManager = new LogActualizaDetalleMglManager();
        return logActualizaDetalleMglManager.findDetalleMaster(idMaster);
    }
    @Override
    public List<LogActualizaDetalle> findDetalleByIdMasterAndEstado(String estado, LogActualizaMaster logMaster) throws ApplicationException {
        LogActualizaDetalleMglManager logActualizaDetalleMglManager = new LogActualizaDetalleMglManager();
        return logActualizaDetalleMglManager.findDetalleMasterByEstado(estado, logMaster);
    }

    @Override
    public LogActualizaDetalle create(LogActualizaDetalle t) throws ApplicationException {
        LogActualizaDetalleMglManager logActualizaDetalleMglManager = new LogActualizaDetalleMglManager();
        return logActualizaDetalleMglManager.create(t);
    }

    @Override
    public LogActualizaDetalle update(LogActualizaDetalle t) throws ApplicationException {
        LogActualizaDetalleMglManager logActualizaDetalleMglManager = new LogActualizaDetalleMglManager();
        return logActualizaDetalleMglManager.update(t, user, perfil);
    }

    @Override
    public boolean delete(LogActualizaDetalle t) throws ApplicationException {
        LogActualizaDetalleMglManager logActualizaDetalleMglManager = new LogActualizaDetalleMglManager();
        return logActualizaDetalleMglManager.delete(t);
    }

    @Override
    public LogActualizaDetalle findById(LogActualizaDetalle idNap) throws ApplicationException {
        LogActualizaDetalleMglManager logActualizaDetalleMglManager = new LogActualizaDetalleMglManager();
        return logActualizaDetalleMglManager.findById(idNap);
    }

}
