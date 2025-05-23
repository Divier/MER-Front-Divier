package co.com.claro.mgl.facade.cm;

import co.com.claro.mer.dtos.sp.cursors.CmtSolicitudNodoCuadranteDto;
import co.com.claro.mgl.businessmanager.cm.CmtSolicitudNodoCuadFrontManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.CmtSolicitudNodoCuadrante;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;

/**
 * Clase EJB Fachada para obtener informacion de procedimiento almacenado CMT_SOL_NODO_CUAD_FRONT_SP 
 * Permite obtener la informacion devuelta en BD
 *
 * @author Divier Casas
 * @version 1.0
 */
@Stateless
public class CmtSolicitudNodoCuadFrontFacade implements ICmtSolicitudNodoCuadFrontFacadeLocal {

    @Override
    public List<CmtSolicitudNodoCuadranteDto> findByFilters(Map<String, Object> mFilters, boolean ordenMayorMenor, int page, int pageSize) throws ApplicationException {

        CmtSolicitudNodoCuadFrontManager solNodoCuadManager = new CmtSolicitudNodoCuadFrontManager();
        return solNodoCuadManager.findByFilters(mFilters, ordenMayorMenor, page, pageSize);
    }

    @Override
    public List<String> findAllNodes() throws ApplicationException {
        
        CmtSolicitudNodoCuadFrontManager solNodoCuadManager = new CmtSolicitudNodoCuadFrontManager();
        return solNodoCuadManager.findAllNodes();
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
}

