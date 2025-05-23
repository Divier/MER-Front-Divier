package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtItemMgl;
import java.util.List;

/**
 *
 * @author User
 */
public interface CmtItemMglFacadeLocal{

    public List<CmtItemMgl> findByTipoItem(String tipoItem) throws ApplicationException;

    public CmtItemMgl findId(CmtItemMgl cmtItemMgl) throws ApplicationException;

}
