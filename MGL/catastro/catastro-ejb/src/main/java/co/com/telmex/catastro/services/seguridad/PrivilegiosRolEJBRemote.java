package co.com.telmex.catastro.services.seguridad;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.data.Privilegios;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 * Interfase PrivilegiosRolEJBRemote
 *
 * @author 	Ana María Malambo
 * @version	1.0
 */
@Remote
public interface PrivilegiosRolEJBRemote {

    /**
     * 
     * @param rol
     * @param funcionalidad
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public List<Privilegios> queryPrivilegiosRol(BigDecimal rol, String funcionalidad) throws ApplicationException;
}
