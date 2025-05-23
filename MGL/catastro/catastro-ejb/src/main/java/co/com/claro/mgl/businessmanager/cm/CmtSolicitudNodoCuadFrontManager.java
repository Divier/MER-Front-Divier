package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mer.dtos.sp.cursors.CmtSolicitudNodoCuadranteDto;
import co.com.claro.mgl.dao.impl.CmtSolicitudNodoCuadFrontMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import java.util.List;
import java.util.Map;

/**
 * Clase manejadora para obtener informacion de DAO a procedimiento almacenado
 * CMT_SOL_NODO_CUAD_FRONT_SP Permite obtener la informacion devuelta en BD
 *
 * @author Divier Casas
 * @version 1.0
 */
public class CmtSolicitudNodoCuadFrontManager {

    public List<CmtSolicitudNodoCuadranteDto> findByFilters(Map<String, Object> mFilters, boolean ordenMayorMenor, int page, int pageSize) throws ApplicationException {

        CmtSolicitudNodoCuadFrontMglDaoImpl daoImpl = new CmtSolicitudNodoCuadFrontMglDaoImpl();
        return daoImpl.findByFilters(mFilters, ordenMayorMenor, page, pageSize);
    }
    
    public List<String> findAllNodes() throws ApplicationException {

        CmtSolicitudNodoCuadFrontMglDaoImpl daoImpl = new CmtSolicitudNodoCuadFrontMglDaoImpl();
        return daoImpl.findAllNodes();
    }
}
