/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.DivisionalDaoImpl;
import co.com.claro.mgl.dtos.DivisionalDTO;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.Divisional;
import java.util.List;

/**
 *
 * @author User
 */
public class DivisionalManager {
    
    public List<Divisional> findAll() throws ApplicationException{
        
        List<Divisional> resulList;
        DivisionalDaoImpl divisionalDaoImpl = new DivisionalDaoImpl();
        
        resulList = divisionalDaoImpl.findAll();        
        
       return resulList;
    }

    /**
     * @param departamento
     * @param ciudad
     * @param centroPoblado
     * @return lista de divisionales disponibles
     * @throws ApplicationException
     */
    public List<DivisionalDTO> listarDivisionalPorDepartamentoCiudadYCentroPoblado(String departamento, String ciudad, String centroPoblado) throws ApplicationException {
        DivisionalDaoImpl divisionalDaoImpl = new DivisionalDaoImpl();
        return divisionalDaoImpl.listarDivisionalPorDepartamentoCiudadYCentroPoblado(departamento, ciudad, centroPoblado);
    }
}
