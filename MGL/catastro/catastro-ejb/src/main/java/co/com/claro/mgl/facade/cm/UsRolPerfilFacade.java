package co.com.claro.mgl.facade.cm;


import co.com.claro.mgl.businessmanager.cm.UsRolPerfilManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.UsRolPerfil;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;


/**
 * Fachada para la administraci&oacute;n de Roles y Perfiles <i>US_ROL_PERFIL</i>.
 * 
 * @author Camilo Miranda (<i>mirandaca</i>).
 */
@Stateless
public class UsRolPerfilFacade implements UsRolPerfilFacadeLocal {

    @Override
    public List<UsRolPerfil> findAll() throws ApplicationException {
        UsRolPerfilManager manager = new UsRolPerfilManager();
        return ( manager.findAll() );
    }
    

    @Override
    public List<UsRolPerfil> findByPerfil(BigDecimal idPerfil) throws ApplicationException {
        UsRolPerfilManager manager = new UsRolPerfilManager();
        return ( manager.findByPerfil(idPerfil) );
    }
    
}
