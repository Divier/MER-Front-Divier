package co.com.claro.mgl.facade.cm;

import co.com.claro.mer.dtos.sp.cursors.CmtSolicitudNodoCuadranteDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.BaseFacadeLocal;
import co.com.claro.mgl.jpa.entities.CmtSolicitudNodoCuadrante;
import java.math.BigDecimal;
import java.util.List;

/**
 * Clase EJB Fachada para obtener informacion de procedimiento almacenado CMT_SOL_NODO_CUADRANTE_SP 
 * Permite obtener la informacion devuelta en BD
 *
 * @author Divier Casas
 * @version 1.0
 */
public interface ICmtSolicitudNodoCuadranteFacadeLocal extends BaseFacadeLocal<CmtSolicitudNodoCuadrante> {

    public List<CmtSolicitudNodoCuadranteDto> findByAll(BigDecimal solicitudId, String codigoNodo, String cuadranteId, BigDecimal codDivisional, String legado, String resultadoAsociacion, boolean ordenMayorMenor, int page, int pageSize) throws ApplicationException;
    public void configDisponibilidadGestion(BigDecimal solicitudId, String disponibilidadGestion) throws ApplicationException ;
}

