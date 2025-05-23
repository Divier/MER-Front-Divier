/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.VetoNodo;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author User
 */
public interface VetoNodoFacadeLocal extends BaseFacadeLocal<VetoNodo> {
    
    List<VetoNodo> findVetos(String politica, Date initDate, Date endDate, BigDecimal ciudad, String tipoVeto) throws ApplicationException;
    
}
