/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.UsuariosDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.Usuarios;
import co.com.claro.mgl.rest.dtos.TokenUsuarioRequestDto;
import co.com.claro.mgl.rest.dtos.TokenUsuarioResponseDto;
import co.com.claro.mgl.utils.CmtUtilidadesAgenda;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Admin
 */
public class UsuariosManager {

    public List<Usuarios> findAll() throws ApplicationException {
        List<Usuarios> resulList;
        UsuariosDaoImpl UsuariosDaoImpl = new UsuariosDaoImpl();
        resulList = UsuariosDaoImpl.findAll();
        return resulList;
    }

    public Usuarios findUsuarioById(BigDecimal idUsuario) throws ApplicationException {
        UsuariosDaoImpl UsuariosDaoImpl = new UsuariosDaoImpl();
        return UsuariosDaoImpl.findUsuarioById(idUsuario);
    }
    
    public Usuarios findUsuarioByUsuario(String usuario) throws ApplicationException {
        UsuariosDaoImpl UsuariosDaoImpl = new UsuariosDaoImpl();
        return UsuariosDaoImpl.findUsuarioSesionSP(usuario);
    }
    
    public String findAreaUsuarioById(BigDecimal idUsuario) throws ApplicationException {
        UsuariosDaoImpl UsuariosDaoImpl = new UsuariosDaoImpl();
        return UsuariosDaoImpl.findAreaUsuarioById(idUsuario);
    }

    public TokenUsuarioResponseDto consultarTokenUsuario(TokenUsuarioRequestDto tokenUsuarioRequestDto) {
        TokenUsuarioResponseDto respuesta = new TokenUsuarioResponseDto();
        if (StringUtils.isEmpty(tokenUsuarioRequestDto.getNombre())) {
            respuesta.setMessage("Error: Debe ingresa el nombre de usuario");
            respuesta.setMessageType("E");
            return respuesta;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String cadena = tokenUsuarioRequestDto.getNombre() + "|" + sdf.format(new Date());
        String cadenaEncriptada = CmtUtilidadesAgenda.Encriptar(cadena);
        respuesta.setToken(cadenaEncriptada);
        respuesta.setMessage("Operacion Exitosa");
        respuesta.setMessageType("I");
        return respuesta;
    }
}