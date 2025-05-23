/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.ptlus;

import co.com.claro.mgl.businessmanager.ptlus.UsuariosServicesManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.rest.dtos.UsuariosServicesDTO;
import java.math.BigDecimal;
import javax.ejb.Stateless;

/**
 * Fachada para el manejo de Usuarios del servicio.
 *
 * @author Victor Bocanegra
 */
@Stateless
public class UsuarioServicesFacade implements UsuarioServicesFacadeLocal {

    /**
     * Metodo para realizar la consulta de un usuario por identificador 
     * del usuario.
     *
     * @param usuario Identificador del usuario a consultar.
     * @return UsuariosServicesDTO Informaci&oacute;n del Usuario consultado.
     * @throws ApplicationException Excepcion lanzada al realizar la consulta.
     */
    @Override
    public UsuariosServicesDTO consultaInfoUserPorUsuario(String usuario)
            throws ApplicationException {

        UsuariosServicesManager usuariosServicesManager = UsuariosServicesManager.getInstance();
        return usuariosServicesManager.consultaInfoUserPorUsuario(usuario);
    }
    
    
    /**
     * Metodo para realizar la consulta de un usuario por cedula
     *
     * @param cedula del usuario a consultar
     * @return UsuariosServicesDTO
     * @throws ApplicationException Excepcion lanzada al agendar
     */
    @Override
    public UsuariosServicesDTO consultaInfoUserPorCedula(String cedula)
            throws ApplicationException {

        UsuariosServicesManager usuariosServicesManager = UsuariosServicesManager.getInstance();
        return usuariosServicesManager.consultaInfoUserPorCedula(cedula);
    }
    
    
    /**
     * Metodo para realizar la consulta de un usuario por cedula
     *
     * @param cedula del usuario a consultar
     * @return UsuariosServicesDTO
     * @throws ApplicationException Excepcion lanzada al agendar
     */
    @Override
    public UsuariosServicesDTO consultaInfoUserPorCedula(BigDecimal cedula)
            throws ApplicationException {

        UsuariosServicesManager usuariosServicesManager = UsuariosServicesManager.getInstance();
        return usuariosServicesManager.consultaInfoUserPorCedula(cedula);
    }
    
    
    /**
     * Obtiene la informaci&oacute;n del usuario a trav&eacute;s de la c&eacute;dula.
     * Primero realiza la b&uacute;squeda por medio de la c&eacute;dula como par&aacute;metro,
     * de no encontrarse, se realiza la b&uacute;squeda con la c&eacute;dula 
     * configurada para <b>VISOR</b> en los par&aacute;metros de <i>MGL</i>.
     * 
     * @param cedula C&eacute;dula del usuario.
     * @return Informaci&oacute;n del usuario.
     * @throws ApplicationException 
     */
    public UsuariosServicesDTO consultaInfoUserPorCedulaVISOR(String cedula) 
            throws ApplicationException {
        UsuariosServicesManager usuariosServicesManager = UsuariosServicesManager.getInstance();
        return usuariosServicesManager.consultaInfoUserPorCedulaVISOR(cedula);
    }
}
