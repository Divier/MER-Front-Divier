package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.BaseFacadeLocal;
import co.com.claro.mgl.jpa.entities.LogActualizaDetalle;
import co.com.claro.mgl.jpa.entities.LogActualizaMaster;
import java.math.BigDecimal;
import java.util.List;

/**
 * Facade para consulta del Detalle Nap <i>TEC_LOG_NAP_DETALLE</i>.
 *
 * @author duartey
 */
public interface LogActualizaDetalleFacadeLocal extends BaseFacadeLocal<LogActualizaDetalle> {
    
    List<LogActualizaDetalle> findDetalleByIdMaster(BigDecimal idMaster) throws ApplicationException;
    
    List<LogActualizaDetalle> findDetalleByIdMasterAndEstado(String estado, LogActualizaMaster logMaster) throws ApplicationException;
}
