/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.error.ApplicationException;

/**
 * Facade Local Base.Contiene los metodos comunes para los facade de CM.
 *
 * @author Johnnatan Ortiz
 * @version 1.0 revision 17/05/2017.
 * @param <T>
 */
public interface BaseCmFacadeLocal <T>{

    /**
     * Asigna usuario y perfil. 
     * Permite asignar el usuario y perfil que realizalas operaciones para 
     * realizar el registro correspondiente sobre BD.
     *
     * @author Johnnatan Ortiz
     * @version 1.0 revision 17/05/2017.
     * @param mUser usuario que realiza la operacion
     * @param mPerfil perfil del usuario que realiza la operacion
     * @throws ApplicationException Lanza una excepcion cuando el perfil usuario 
     * son nulos.
     */
    void setUser(String mUser, int mPerfil) throws ApplicationException;
}
