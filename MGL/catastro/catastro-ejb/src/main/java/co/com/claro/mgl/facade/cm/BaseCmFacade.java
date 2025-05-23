/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.error.ApplicationException;

/**
 * Facade Base CM.Contiene los metodos comunes para los facade de CM.
 *
 * @author Johnnatan Ortiz
 * @version 1.00.000
 * @param <T>
 */
public class BaseCmFacade <T> implements BaseCmFacadeLocal<T>{
    
    /**
     * Contiene el usuario de sesion.
     */
    private String user = "";
    
    /**
     * Contiene el perfil del usuario.
     */
    private int perfil = 0;
    
    @Override
    public void setUser(String mUser, int mPerfil) throws ApplicationException {
        if (mUser.equals("") || mPerfil == 0) {
            throw new ApplicationException(
                    "El Usuario o perfil No pueden ser nulos");
        }
        user = mUser;
        perfil = mPerfil;
    }

    /**
     * Obtener el usuario de la sesion.
     * Obtiene el usuario de la sesion.
     * 
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 17/05/2017.
     * @return El usuario de la sesion.
     */
    public String getUser() {
        return user;
    }

    /**
     * Cambiar el usuario de la sesion.
     * Cambia el usuario de la sesion.
     * 
     * @author Johnnatan Ortiz.
     * @version 1.0 revision 17/05/2017.
     * @return El usuario de la sesion.
     */
    public int getPerfil() {
        return perfil;
    }
}
