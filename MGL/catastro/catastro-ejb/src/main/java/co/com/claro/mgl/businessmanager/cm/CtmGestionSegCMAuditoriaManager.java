package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mer.dtos.sp.cursors.CtmGestionSegCMAuditoriaDto;
import co.com.claro.mgl.dao.impl.cm.CtmGestionSegCMAuditoriaMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CtmGestionSegCMAuditoria;

import java.math.BigDecimal;
import java.util.List;

/**
 * Manager de la pagina de seguridad. Permite manejar la
 * conexion a los datos de la entidad
 *
 * @author Carlos Andres Caicedo
 * @versión 1.00.000
 */

public class CtmGestionSegCMAuditoriaManager {

    public List<CtmGestionSegCMAuditoriaDto> findAllAuditParentAccount(BigDecimal cuentaMatriz) throws ApplicationException {
        CtmGestionSegCMAuditoriaMglDaoImpl daoImpl = new CtmGestionSegCMAuditoriaMglDaoImpl();
        return daoImpl.findAllAuditParentAccount(cuentaMatriz);
    }
}
