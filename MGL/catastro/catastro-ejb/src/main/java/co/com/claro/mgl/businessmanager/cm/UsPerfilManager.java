package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.UsPerfilDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.UsPerfil;
import java.util.List;


/**
 * Administrador de Perfiles <i>US_PERFIL</i>.
 * 
 * @author Camilo Miranda (<i>mirandaca</i>).
 */
public class UsPerfilManager {
    
    /**
     * Busca todo el listado de <i>US_PERFIL</i>.
     * @return Lista de UsPerfil.
     * @throws ApplicationException 
     */
    public List<UsPerfil> findAll() throws ApplicationException {
        UsPerfilDaoImpl usPerfilDaoImpl = new UsPerfilDaoImpl();
        return ( usPerfilDaoImpl.findAll() );
    }
    
    
    /**
     * Realiza la consulta de un Perfil a trav&eacute;s de su c&oacute;digo.
     * 
     * @param codPerfil C&oacute;digo del Perfil a buscar.
     * @return Perfil.
     * @throws ApplicationException 
     */
    public UsPerfil findByCodigo(String codPerfil) throws ApplicationException {
        UsPerfilDaoImpl usPerfilDaoImpl = new UsPerfilDaoImpl();
        return ( usPerfilDaoImpl.findByCodigo(codPerfil) );
    }
}
