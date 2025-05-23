/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.GeograficoMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.GeograficoMgl;
import co.com.telmex.catastro.data.Geografico;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Admin
 */
@Stateless
public class GeograficoMglFacade implements GeograficoMglFacadeLocal {

    @Override
    public List<GeograficoMgl> findByIdGeograficoPolitico(BigDecimal idGeograficoPolitoco) throws ApplicationException {
        GeograficoMglManager geograficoMglManager = new GeograficoMglManager();
        return geograficoMglManager.findByIdGeograficoPolitico(idGeograficoPolitoco);
    }

    @Override
    public List<GeograficoMgl> findAll() throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public GeograficoMgl create(GeograficoMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public GeograficoMgl update(GeograficoMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(GeograficoMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public GeograficoMgl findById(GeograficoMgl sqlData) throws ApplicationException {
        GeograficoMglManager geograficoMglManager = new GeograficoMglManager();
        return geograficoMglManager.findById(sqlData);
    }

   /**
    * Buscar el barrio del Hhpp
    * 
    * Busca el barrio al cual pertenece este Hhpp.
    * 
    * @author becerraarmr
    * 
     * @param hhpId
     * @return una entidad Geografico
     * @throws ApplicationException  hay algún error de busqueda
     * @see Geografico
     */
  @Override
  public Geografico findBarrioHhpp(BigDecimal hhpId) throws ApplicationException {
    GeograficoMglManager geograficoMglManager = new GeograficoMglManager();
    return geograficoMglManager.findBarrioHhpp(hhpId);
  }
}
