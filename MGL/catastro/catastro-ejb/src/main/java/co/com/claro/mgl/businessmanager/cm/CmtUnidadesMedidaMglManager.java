
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.CmtUnidadesMedidaMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtUnidadesMedidaMgl;
import java.util.List;

/**
 * Contiene la logiga de negocio relacionada con la clase CmtUnidadesMedidaMgl.
 * 
 * @author alejandro.martine.ext@claro.com.co
 * 
 * @versión 1.00.0000
 */
public class CmtUnidadesMedidaMglManager {
    
    public List<CmtUnidadesMedidaMgl> findAll() throws ApplicationException {
        CmtUnidadesMedidaMglDaoImpl daoImpl = new CmtUnidadesMedidaMglDaoImpl();
        return  daoImpl.findAll();
    }    
}
