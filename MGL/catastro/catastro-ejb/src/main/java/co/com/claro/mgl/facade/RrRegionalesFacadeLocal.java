/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.RrRegionales;
import co.com.claro.mgl.jpa.entities.cm.CmtRegionalRr;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Parzifal de León
 */
@Local
public interface RrRegionalesFacadeLocal extends BaseFacadeLocal<RrRegionales> {

    public List<CmtRegionalRr> findByUnibi(String unibi) throws ApplicationException;

    public List<RrRegionales> findByUniAndBi() throws ApplicationException;

    public RrRegionales findById(String id) throws ApplicationException;

    public List<CmtRegionalRr> findRegionales() throws ApplicationException;
    
    public String findNombreRegionalByCodigo(String codigo) throws ApplicationException;
    
    
    public List<CmtRegionalRr> findByCodigo(List<BigDecimal> codigo) throws ApplicationException;
}
