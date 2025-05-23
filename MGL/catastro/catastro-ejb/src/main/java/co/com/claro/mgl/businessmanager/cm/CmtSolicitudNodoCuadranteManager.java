package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mer.dtos.sp.cursors.CmtSolicitudNodoCuadranteDto;
import co.com.claro.mgl.dao.impl.CmtSolicitudNodoCuadranteMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import java.math.BigDecimal;
import java.util.List;

/**
 * Clase manejadora para obtener informacion de DAO a procedimiento almacenado
 * CMT_SOL_NODO_CUADRANTE_SP Permite obtener la informacion devuelta en BD
 *
 * @author Divier Casas
 * @version 1.0
 */
public class CmtSolicitudNodoCuadranteManager {

    public List<CmtSolicitudNodoCuadranteDto> findByAll(BigDecimal solicitudId, String codigoNodo, String cuadranteId, BigDecimal codDivisional, String legado, String resultadoAsociacion, boolean ordenMayorMenor, int page, int pageSize) throws ApplicationException {

        CmtSolicitudNodoCuadranteMglDaoImpl daoImpl = new CmtSolicitudNodoCuadranteMglDaoImpl();
        return daoImpl.findByAll(solicitudId, codigoNodo, cuadranteId, codDivisional, legado, resultadoAsociacion, ordenMayorMenor, page, pageSize);
    }
    
    public void configDisponibilidadGestion(BigDecimal solicitudId, String disponibilidadGestion) throws ApplicationException {
        
        CmtSolicitudNodoCuadranteMglDaoImpl daoImpl = new CmtSolicitudNodoCuadranteMglDaoImpl();
        daoImpl.configDisponibilidadGestion(solicitudId, disponibilidadGestion);
    }
}