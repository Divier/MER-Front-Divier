package co.com.telmex.catastro.services.administracion;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.data.Menu;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 * Clase MenuEJBRemote
 *
 * @author 	Jose Luis Caicedo Gonzalez.
 * @version	1.0
 */
@Remote
public interface MenuEJBRemote {

    /**
     * 
     * @param idRol
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    List<Menu> queryMenu(BigDecimal idRol) throws ApplicationException;
}
