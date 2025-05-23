package co.com.telmex.catastro.services.comun;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.data.Restriccion;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 * Interfase RestrictionEJBRemote
 *
 * @author 	Ana María Malambo.
 * @version	1.0
 */
@Remote
public interface RestrictionEJBRemote {

    /**
     * 
     * @param rolId
     * @param nameTable
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public List<Restriccion> queryRestriction(BigDecimal rolId, String nameTable) throws ApplicationException;
}
