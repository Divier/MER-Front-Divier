package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmtItemMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtItemMgl;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author alejandro.martine.ext@claro.com.co
 */
@Stateless
public class CmtItemMglFacade implements CmtItemMglFacadeLocal{

    @Override
    public CmtItemMgl findId (CmtItemMgl cmtItemMgl) throws ApplicationException{
        CmtItemMglManager manager = new CmtItemMglManager();
        return manager.findId(cmtItemMgl);
    }
    /**
     *
     * @param tipoItem
     * @return
     * @throws ApplicationException
     */
    @Override
    public List<CmtItemMgl> findByTipoItem(String tipoItem) throws ApplicationException {
        CmtItemMglManager manager = new CmtItemMglManager();
        return manager.findByTipoItem(tipoItem);
    }
}
