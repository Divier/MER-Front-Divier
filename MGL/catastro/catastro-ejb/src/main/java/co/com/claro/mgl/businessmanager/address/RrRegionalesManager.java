/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.RrRegionalesDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.RrRegionales;
import co.com.claro.mgl.jpa.entities.cm.CmtRegionalRr;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Parzifal de León
 */
public class RrRegionalesManager {

    RrRegionalesDaoImpl daoImpl = new RrRegionalesDaoImpl();

    public List<CmtRegionalRr> findByUnibi(String unibi) {
        List<CmtRegionalRr> unibiList;

        unibiList = daoImpl.findByUnibi(unibi);
        return unibiList;
    }

    public List<RrRegionales> findByUniAndBi() {
        List<RrRegionales> unibiList;
        unibiList = daoImpl.findByUniAndBi();
        return unibiList;
    }

    public RrRegionales findById(String id) throws ApplicationException {
        return daoImpl.find(id);
    }

    public List<CmtRegionalRr> findRegionales() {
        List<CmtRegionalRr> unibiList;

        unibiList = daoImpl.findRegionales();
        return unibiList;
    }
    
     public String findNombreRegionalByCodigo(String codigo) throws ApplicationException {
        return daoImpl.findNombreRegionalByCodigo(codigo);
    }
     
      public List<CmtRegionalRr> findByCodigo(List<BigDecimal> codigo) throws ApplicationException {
        return daoImpl.findByCodigo(codigo);
    }
}
