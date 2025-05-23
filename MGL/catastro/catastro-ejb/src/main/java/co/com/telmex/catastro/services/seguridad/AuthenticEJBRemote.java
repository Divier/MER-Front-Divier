package co.com.telmex.catastro.services.seguridad;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.data.Rol;
import co.com.telmex.catastro.data.Usuario;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Remote;

/**
 *Interfase AuditoriaEJBRemote
 *
 * @author 	José Luis Caicedo
 * @version	1.0
 */
@Remote
public interface AuthenticEJBRemote {

    /**
     * 
     * @param login
     * @param pwd
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public Usuario processAuthentic(String login, String pwd) throws ApplicationException;

    /**
     * 
     * @param usuId
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public List<Rol> queryListRol(BigDecimal usuId) throws ApplicationException;

    /**
     * 
     * @param idRol
     * @return
     * @throws co.com.claro.mgl.error.ApplicationException
     * @throws Exception
     */
    public boolean validateRolMenu(String idRol) throws ApplicationException;
}
