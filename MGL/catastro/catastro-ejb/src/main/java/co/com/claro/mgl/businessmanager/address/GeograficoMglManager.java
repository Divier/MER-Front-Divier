/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.GeograficoMglDaoImpl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.GeograficoMgl;
import co.com.telmex.catastro.data.Geografico;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Admin
 */
public class GeograficoMglManager {
       public List<GeograficoMgl> findByIdGeograficoPolitico(BigDecimal gpoId) throws ApplicationException{
        
        List<GeograficoMgl> resultList;        
        GeograficoMglDaoImpl geograficoMglDaoImpl = new GeograficoMglDaoImpl();        
        resultList = geograficoMglDaoImpl.findByIdGeograficoPolitico(gpoId);
        
        return resultList;
    }

    public GeograficoMgl findById(GeograficoMgl geograficoMgl) throws ApplicationException {
        GeograficoMgl result;
        GeograficoMglDaoImpl geograficoMglDaoImpl = new GeograficoMglDaoImpl();
        result = geograficoMglDaoImpl.find(geograficoMgl.getGeoId());
        return result;
    }

    /**
     * Buscar el barrio
     * 
     * Solicita a la base de datos el barrio que corresponde con el hhpp.
     * 
     * @author becerraarmr
     * 
     * @param hhpId id del Hhpp
     * @return el valor del barrrio en la entidad Geografico
     * @throws ApplicationException 
     */
  public Geografico findBarrioHhpp(BigDecimal hhpId)throws ApplicationException {
    GeograficoMglDaoImpl geograficoMglDaoImpl = new GeograficoMglDaoImpl();
    return geograficoMglDaoImpl.findBarrioHhpp(hhpId);
  }
}
