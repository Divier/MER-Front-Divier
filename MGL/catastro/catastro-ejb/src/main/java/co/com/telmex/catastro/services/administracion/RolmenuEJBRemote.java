package co.com.telmex.catastro.services.administracion;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.data.Menu;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 * Representa las Subdirecciones agregadas a una dirección
 * implementa Serialización
 *
 * @author 	Nataly Orozco.
 * @version	1.0
 */
@Remote
public interface RolmenuEJBRemote {

    /**
     * 
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    List<Menu> queryMenu() throws ApplicationException;

    /**
     * 
     * @param parteId
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    Menu queryGrupoMenu(BigDecimal parteId) throws ApplicationException;

    /**
     * 
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    List<Menu> queryGruposMenu() throws ApplicationException;
}
