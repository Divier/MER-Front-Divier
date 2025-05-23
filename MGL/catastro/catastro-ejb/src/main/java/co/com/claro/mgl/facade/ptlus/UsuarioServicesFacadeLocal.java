/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.ptlus;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.rest.dtos.UsuariosServicesDTO;
import java.math.BigDecimal;


/**
 *
 * @author bocanegravm
 */
public interface UsuarioServicesFacadeLocal {

    /**
     * Metodo para realizar la consulta de un usuario por identificador 
     * del usuario.
     *
     * @param usuario Identificador del usuario a consultar.
     * @return UsuariosServicesDTO Informaci&oacute;n del Usuario consultado.
     * @throws ApplicationException Excepcion lanzada al realizar la consulta.
     */
    public UsuariosServicesDTO consultaInfoUserPorUsuario(String usuario) 
            throws ApplicationException;
    
    
    /**
     * Metodo para realizar la consulta de un usuario por cedula
     *
     * @param cedula del usuario a consultar
     * @return UsuariosServicesDTO
     * @throws ApplicationException Excepcion lanzada al agendar
     */
    public UsuariosServicesDTO consultaInfoUserPorCedula(String cedula)
            throws ApplicationException;
    
    
    /**
     * M&eacute;todo para realizar la consulta de un usuario por cedula
     *
     * @param cedula del usuario a consultar
     * @return UsuariosServicesDTO
     * @throws ApplicationException Excepcion lanzada al agendar
     */
    public UsuariosServicesDTO consultaInfoUserPorCedula(BigDecimal cedula)
            throws ApplicationException;
}
