/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.DivisionalManager;
import co.com.claro.mgl.dtos.DivisionalDTO;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.Divisional;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author User
 */
@Stateless
public class DivisionalFacade implements IDivisionalFacadeLocal {
    

    @Override
    public List<Divisional> findAll() throws ApplicationException {
        DivisionalManager divisionalManager = new DivisionalManager();
        return divisionalManager.findAll();
    }

    @Override
    public Divisional create(Divisional t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Divisional update(Divisional t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(Divisional t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Divisional findById(Divisional sqlData) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @param departamento
     * @param ciudad
     * @param centroPoblado
     * @return lista de divisionales disponibles
     * @throws ApplicationException
     */
    @Override
    public List<DivisionalDTO> listarDivisionalPorDepartamentoCiudadYCentroPoblado(String departamento, String ciudad, String centroPoblado) throws ApplicationException {
        DivisionalManager divisionalManager = new DivisionalManager();
        return divisionalManager.listarDivisionalPorDepartamentoCiudadYCentroPoblado(departamento, ciudad, centroPoblado);
    }
    
}
