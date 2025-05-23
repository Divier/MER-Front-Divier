package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.conexion.ps.ConsultaProcedimientos;
import co.com.claro.mgl.dao.impl.LogActualizaDetalleDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.LogActualizaDetalle;
import co.com.claro.mgl.jpa.entities.LogActualizaMaster;
import java.math.BigDecimal;
import java.util.List;

/**
 * Manager para la consulta del Detalle Nap <i>TEC_LOG_NAP_DETALLE</i>.
 *
 * @author duartey
 */
public class LogActualizaDetalleMglManager {
    
    public List<LogActualizaDetalle> findAll() throws ApplicationException {
        LogActualizaDetalleDaoImpl logActualizaDetalleMglDaoImpl = new LogActualizaDetalleDaoImpl();
        return logActualizaDetalleMglDaoImpl.findAllItems();
    }
    
    public List<LogActualizaDetalle> findDetalleMaster(BigDecimal idMaster) throws ApplicationException {
        LogActualizaDetalleDaoImpl logActualizaDetalleMglDaoImpl = new LogActualizaDetalleDaoImpl();
        return logActualizaDetalleMglDaoImpl.findDetalleMaster(idMaster);
    }
    public List<LogActualizaDetalle> findDetalleMasterByEstado(String estado, LogActualizaMaster logMaster) throws ApplicationException {
        ConsultaProcedimientos consultaProcedimientos = new ConsultaProcedimientos();
        return consultaProcedimientos.prcConsultaLogDetalleBatch(logMaster, estado);
    }

    public LogActualizaDetalle create(LogActualizaDetalle logActualizaMgl) throws ApplicationException {
        LogActualizaDetalleDaoImpl logActualizaDaoImpl = new LogActualizaDetalleDaoImpl();
        return logActualizaDaoImpl.create(logActualizaMgl);
    }

    public LogActualizaDetalle update(LogActualizaDetalle logActualizaDetalleMgl,
            String user, int perfil) throws ApplicationException {
        LogActualizaDetalleDaoImpl logActualizaDetalleDaoImpl = new LogActualizaDetalleDaoImpl();
        return logActualizaDetalleDaoImpl.updateCm(logActualizaDetalleMgl, user, perfil);
    }

    public boolean delete(LogActualizaDetalle logActualizaDetalleMgl) throws ApplicationException {
        LogActualizaDetalleDaoImpl logActualizaDetalleDaoImpl = new LogActualizaDetalleDaoImpl();
        return logActualizaDetalleDaoImpl.delete(logActualizaDetalleMgl);
    }

    public LogActualizaDetalle findById(LogActualizaDetalle logActualizaDetalleMgl) throws ApplicationException {
        LogActualizaDetalleDaoImpl logActualizaDetalleDaoImpl = new LogActualizaDetalleDaoImpl();
        return logActualizaDetalleDaoImpl.find(logActualizaDetalleMgl.getIdNapDetalle());
    }
}
