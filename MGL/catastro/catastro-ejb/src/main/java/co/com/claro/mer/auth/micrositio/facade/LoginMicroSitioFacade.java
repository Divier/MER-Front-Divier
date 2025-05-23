package co.com.claro.mer.auth.micrositio.facade;

import co.com.claro.mer.auth.micrositio.dao.ILoginMicrositioDao;
import co.com.claro.mgl.error.ApplicationException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.math.BigDecimal;

/**
 * Clase que implementa las operaciones de la fachada de login de micrositio.
 *
 * @author Gildardo Mora
 * @version 1.0, 2025/03/03
 */
@Stateless
public class LoginMicroSitioFacade implements LoginMicroSitioFacadeLocal{

    @EJB
    private ILoginMicrositioDao loginMicrositioDao;

    /**
     * Realiza el registro de la sesión del usuario de micrositio.
     * @param user Usuario.
     * @param profile Perfil del usuario.
     * @return {@link BigDecimal} Id de la sesión registrada del usuario de micrositio.
     * @throws ApplicationException Excepción en caso de error.
     */
    @Override
    public BigDecimal registerUserLogin(String user, String profile) throws ApplicationException {
        return loginMicrositioDao.registerUserLogin(user, profile);
    }

}
