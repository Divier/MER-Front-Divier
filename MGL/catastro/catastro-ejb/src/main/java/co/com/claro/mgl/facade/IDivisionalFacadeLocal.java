/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.dtos.DivisionalDTO;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.Divisional;

import java.util.List;

/**
 *
 * @author Dayver De la hoz
 */
public interface IDivisionalFacadeLocal extends BaseFacadeLocal<Divisional>{
    List<DivisionalDTO> listarDivisionalPorDepartamentoCiudadYCentroPoblado(String departamento, String ciudad, String centroPoblado) throws ApplicationException;
}
