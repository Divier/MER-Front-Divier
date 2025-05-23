/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.rest.dtos.NodoMglDto;
import javax.ejb.Local;

/**
 *
 * @author User
 */
@Local
public interface ICtmNodoMglFacadeLocal {

    public NodoMglDto getNodeDataByCod(String codigo);

}
