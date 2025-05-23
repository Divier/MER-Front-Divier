/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.BaseFacadeLocal;
import co.com.claro.mgl.jpa.entities.cm.Usuarios;
import co.com.claro.mgl.rest.dtos.TokenUsuarioRequestDto;
import co.com.claro.mgl.rest.dtos.TokenUsuarioResponseDto;

import java.math.BigDecimal;

/**
 *
 * @author Admin
 */
public interface UsuariosFacadeLocal extends BaseFacadeLocal<Usuarios> {

    public Usuarios findUsuarioById(BigDecimal idUsuario) throws ApplicationException;

    public String findAreaUsuarioById(BigDecimal idUsuario) throws ApplicationException;
    
    public Usuarios findUsuarioByUsuario(String usuario) throws ApplicationException;

    public TokenUsuarioResponseDto consultarTokenUsuario(TokenUsuarioRequestDto tokenUsuarioRequestDto);
}
