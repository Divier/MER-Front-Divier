
package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.ValidacionParametrizadaTipoValidacionMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.ValidacionParametrizadaTipoValidacionMgl;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Juan David Hernandez
 */
@Stateless
public class ValidacionParametrizadaTipoValidacionMglFacade implements ValidacionParametrizadaTipoValidacionMglFacadeLocal{
    
    private String user = "";
    private int perfil = 0;    
    
    @Override
    public void setUser(String mUser, int mPerfil) throws ApplicationException {
        if (mUser.equals("") || mPerfil == 0) {
            throw new ApplicationException("El Usuario o perfil No pueden ser nulos");
        }
        user = mUser;
        perfil = mPerfil;
    }

    @Override
    public List<ValidacionParametrizadaTipoValidacionMgl> findAll() throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ValidacionParametrizadaTipoValidacionMgl create(ValidacionParametrizadaTipoValidacionMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
   public ValidacionParametrizadaTipoValidacionMgl update(ValidacionParametrizadaTipoValidacionMgl t) throws ApplicationException {
      ValidacionParametrizadaTipoValidacionMglManager validacionParametrizadaMglManager = new ValidacionParametrizadaTipoValidacionMglManager();
      return validacionParametrizadaMglManager.update(t);    
    }    
  
    

    @Override
    public boolean delete(ValidacionParametrizadaTipoValidacionMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ValidacionParametrizadaTipoValidacionMgl findById(ValidacionParametrizadaTipoValidacionMgl sqlData) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    /**
     * Obtiene el listado de validaciones por tipo de la base de datos
     *
     * @param tipoValidacion tipo de validacion que se desea obtener.
     * @author Juan David Hernandez
     * @return Listado de validaciones
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    @Override
    public List<ValidacionParametrizadaTipoValidacionMgl> findValidacionParametrizadaByTipo(BigDecimal tipoValidacion)
            throws ApplicationException {
        ValidacionParametrizadaTipoValidacionMglManager validacionParametrizadaMglManager = new ValidacionParametrizadaTipoValidacionMglManager();
        return validacionParametrizadaMglManager.findValidacionParametrizadaByTipo(tipoValidacion);
    }

}
