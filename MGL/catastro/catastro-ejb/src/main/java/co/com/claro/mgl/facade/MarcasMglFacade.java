/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.MarcasMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.MarcasMgl;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Admin
 */
@Stateless
public class MarcasMglFacade implements MarcasMglFacadeLocal {

    private final MarcasMglManager marcasMglManager;

    public MarcasMglFacade() {
        this.marcasMglManager = new MarcasMglManager();
    }

    @Override
    public List<MarcasMgl> findAll() throws ApplicationException {
        return marcasMglManager.findAll();
    }

    @Override
    public MarcasMgl create(MarcasMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public MarcasMgl update(MarcasMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(MarcasMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public MarcasMgl findById(MarcasMgl sqlData) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<MarcasMgl> findMarcasMglByHhpp(HhppMgl hhppMgl) throws ApplicationException {
        return marcasMglManager.findMarcasMglByHhpp(hhppMgl);
    }
    
    @Override
    public MarcasMgl findById(BigDecimal idMarcasMgl) throws ApplicationException {
        return marcasMglManager.findById(idMarcasMgl);
    }
    
    public List<MarcasMgl> findByGrupoCodigo(String codigo) throws ApplicationException{
        return marcasMglManager.findByGrupoCodigo(codigo);
    }
    
     @Override
    public List<MarcasMgl> findAllMarcasMgl() throws ApplicationException {
        return marcasMglManager.findAll();
    }
}
