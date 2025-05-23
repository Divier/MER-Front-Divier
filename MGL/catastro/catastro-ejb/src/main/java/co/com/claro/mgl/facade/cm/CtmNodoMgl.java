/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CtmNodesMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.rest.dtos.NodoMglDto;
import co.com.claro.mgl.utils.ClassUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ejb.Stateless;

/**
 *
 * @author User
 */
@Stateless
public class CtmNodoMgl implements ICtmNodoMglFacadeLocal {

    private static final Logger LOGGER = LogManager.getLogger(CtmNodoMgl.class);
    
   private final CtmNodesMglManager ctmNodesMglManager;

    public CtmNodoMgl() {
        ctmNodesMglManager = new CtmNodesMglManager();
    }

    @Override
    public NodoMglDto getNodeDataByCod(String codigo)  {
       try {
           return ctmNodesMglManager.getNodeDataByCod(codigo);
       } catch (ApplicationException ex) {
           String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
       }
       return null;
    }

}
