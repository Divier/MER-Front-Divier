package co.com.claro.mgl.facade.cm;

import co.com.claro.mer.dtos.sp.cursors.CmtSolicitudNodoCuadranteDto;
import co.com.claro.mgl.businessmanager.cm.CmtSolicitudNodoCuadranteManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.CmtSolicitudNodoCuadrante;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 * Clase EJB Fachada para obtener informacion de procedimiento almacenado CMT_SOL_NODO_CUADRANTE_SP
 * Permite obtener la informacion devuelta en BD
 *
 * @author Divier Casas
 * @version 1.0
 */
@Stateless
public class CmtSolicitudNodoCuadranteFacade implements ICmtSolicitudNodoCuadranteFacadeLocal {

    @Override
    public List<CmtSolicitudNodoCuadranteDto> findByAll(BigDecimal solicitudId, String codigoNodo, String cuadranteId, BigDecimal codDivisional, String legado, String resultadoAsociacion, boolean ordenMayorMenor, int page, int pageSize) throws ApplicationException {

        CmtSolicitudNodoCuadranteManager solNodoCuadManager = new CmtSolicitudNodoCuadranteManager();
        return solNodoCuadManager.findByAll(solicitudId, codigoNodo, cuadranteId, codDivisional, legado, resultadoAsociacion, ordenMayorMenor, page, pageSize);
    }

    @Override
    public List<CmtSolicitudNodoCuadrante> findAll() throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public CmtSolicitudNodoCuadrante create(CmtSolicitudNodoCuadrante t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public CmtSolicitudNodoCuadrante update(CmtSolicitudNodoCuadrante t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean delete(CmtSolicitudNodoCuadrante t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public CmtSolicitudNodoCuadrante findById(CmtSolicitudNodoCuadrante sqlData) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void configDisponibilidadGestion(BigDecimal solicitudId, String disponibilidadGestion) throws ApplicationException {
        
        CmtSolicitudNodoCuadranteManager solNodoCuadManager = new CmtSolicitudNodoCuadranteManager();
        solNodoCuadManager.configDisponibilidadGestion(solicitudId, disponibilidadGestion);
    }
}