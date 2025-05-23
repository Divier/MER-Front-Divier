/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

/**
 *
 * @author Admin
 */
import co.com.claro.mgl.businessmanager.cm.UsuariosManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.Usuarios;
import co.com.claro.mgl.rest.dtos.TokenUsuarioRequestDto;
import co.com.claro.mgl.rest.dtos.TokenUsuarioResponseDto;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Admin
 */
@Stateless
public class UsuariosFacade implements UsuariosFacadeLocal {

    @Override
    public List<Usuarios> findAll() throws ApplicationException {
        UsuariosManager UsuariosMglManager = new UsuariosManager();
        return UsuariosMglManager.findAll();
    }

    @Override
    public Usuarios findUsuarioById(BigDecimal idUsuario) throws ApplicationException {
        UsuariosManager UsuariosMglManager = new UsuariosManager();
        return UsuariosMglManager.findUsuarioById(idUsuario);
    }
    
    @Override
    public String findAreaUsuarioById(BigDecimal idUsuario) throws ApplicationException {
        UsuariosManager UsuariosMglManager = new UsuariosManager();
        return UsuariosMglManager.findAreaUsuarioById(idUsuario);
    }
    
    @Override
    public Usuarios findUsuarioByUsuario(String usuario) throws ApplicationException {
        UsuariosManager UsuariosMglManager = new UsuariosManager();
        return UsuariosMglManager.findUsuarioByUsuario(usuario);
    }

    @Override
    public Usuarios create(Usuarios t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Usuarios update(Usuarios t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(Usuarios t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Usuarios findById(Usuarios sqlData) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TokenUsuarioResponseDto consultarTokenUsuario(TokenUsuarioRequestDto tokenUsuarioRequestDto) {
        UsuariosManager usuariosMglManager = new UsuariosManager();
        return usuariosMglManager.consultarTokenUsuario(tokenUsuarioRequestDto);
    }
   
}
