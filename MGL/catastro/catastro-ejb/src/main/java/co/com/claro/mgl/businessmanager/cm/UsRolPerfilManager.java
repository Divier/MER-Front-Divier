package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.UsRolPerfilDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.UsRolPerfil;
import java.math.BigDecimal;
import java.util.List;


/**
 * Manager para la administraci&oacute;n de Roles y Perfiles <i>US_ROL_PERFIL</i>.
 * 
 * @author Camilo Miranda (<i>mirandaca</i>).
 */
public class UsRolPerfilManager {
    
    /**
     * Busca todo el listado de ROL_PERFIL.
     * @return Lista de UsRolPerfil.
     * @throws ApplicationException 
     */
    public List<UsRolPerfil> findAll() throws ApplicationException {
        UsRolPerfilDaoImpl usRolPerfilDaoImpl = new UsRolPerfilDaoImpl();
        return ( usRolPerfilDaoImpl.findAll() );
    }
    
    
    /**
     * Busca todos los roles asociados a un perfil.
     * 
     * @param idPerfil
     * @return Lista de UsRolPerfil.
     * @throws ApplicationException 
     */
    public List<UsRolPerfil> findByPerfil (BigDecimal idPerfil) throws ApplicationException {
        UsRolPerfilDaoImpl usRolPerfilDaoImpl = new UsRolPerfilDaoImpl();
        return ( usRolPerfilDaoImpl.findByPerfil(idPerfil) );
    }
}
