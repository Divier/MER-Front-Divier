
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.CmtItemMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtItemMgl;
import java.util.List;

/**
 * Contiene la logiga de negocio relacionada con la clase CmtItemMgl.
 * 
 * @author alejandro.martine.ext@claro.com.co
 * 
 * @versión 1.00.0000
 */
public class CmtItemMglManager { 
    
    public CmtItemMgl create (CmtItemMgl cmtItemMgl) throws ApplicationException{
        CmtItemMglDaoImpl daoImpl = new CmtItemMglDaoImpl();
        return daoImpl.create(cmtItemMgl);
    }
    
    public CmtItemMgl findId (CmtItemMgl cmtItemMgl) throws ApplicationException{
        CmtItemMglDaoImpl daoImpl = new CmtItemMglDaoImpl();
        return daoImpl.find(cmtItemMgl.getIdItem());
    }
    
    public CmtItemMgl update (CmtItemMgl cmtItemMgl) throws ApplicationException{
        CmtItemMglDaoImpl daoImpl = new CmtItemMglDaoImpl();
        return daoImpl.update(cmtItemMgl);
    }
    
    public boolean delete (CmtItemMgl cmtItemMgl) throws ApplicationException{
        CmtItemMglDaoImpl daoImpl = new CmtItemMglDaoImpl();
        return daoImpl.delete(cmtItemMgl);
    }
    
    /**
     * findByTipoItem lista los items de acuerdo a su tipo, mano de obra o materiales.
     *
     * alejandro.martinez.ext@claro.com.co
     *
     * @param tipoItem
     * @return
     * @throws ApplicationException
     */
    public List<CmtItemMgl> findByTipoItem(String tipoItem) throws ApplicationException {
        CmtItemMglDaoImpl daoImpl = new CmtItemMglDaoImpl();
        return daoImpl.findByTipoItem(tipoItem);
    }
}
