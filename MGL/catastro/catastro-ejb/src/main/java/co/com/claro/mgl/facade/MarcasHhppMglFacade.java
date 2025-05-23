
package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.MarcasHhppMglManager;
import co.com.claro.mgl.dtos.FiltroMarcasMglDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.MarcasHhppMgl;
import co.com.claro.mgl.jpa.entities.MarcasMgl;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Admin
 */
@Stateless
public class MarcasHhppMglFacade implements MarcasHhppMglFacadeLocal {

    private final MarcasHhppMglManager marcasHhppMglManager;

    public MarcasHhppMglFacade() {
        this.marcasHhppMglManager = new MarcasHhppMglManager();
    }

    @Override
    public List<MarcasHhppMgl> findAll() throws ApplicationException {
        return marcasHhppMglManager.findAll();
    }

    @Override
    public boolean asignarMarcaHhppMgl(HhppMgl hhppMgl, MarcasHhppMgl marcasHhppMgl, String blackLis, String usuario) throws ApplicationException {
        return marcasHhppMglManager.asignarMarcaHhppMgl(hhppMgl, marcasHhppMgl, blackLis, usuario);
    }

    @Override
    public MarcasHhppMgl create(MarcasHhppMgl t) throws ApplicationException {
        return marcasHhppMglManager.create(t);
    }

    @Override
    public MarcasHhppMgl update(MarcasHhppMgl t) throws ApplicationException {
        return marcasHhppMglManager.update(t);
    }

    @Override
    public boolean delete(MarcasHhppMgl t) throws ApplicationException {
        return marcasHhppMglManager.delete(t);
    }

    @Override
    public MarcasHhppMgl findById(MarcasHhppMgl sqlData) throws ApplicationException {
        return marcasHhppMglManager.findById(sqlData);
    }
    
    @Override
     public List<MarcasHhppMgl> findMarcasHhppMglidHhpp(BigDecimal hhppId) throws ApplicationException {
        return marcasHhppMglManager.findMarcasHhppMglidHhpp(hhppId);
    }
     
    @Override
    public List<MarcasHhppMgl> findMarcasHhppMglidHhppSinEstado(List<HhppMgl> hhppId) throws ApplicationException {
        return marcasHhppMglManager.findMarcasHhppMglidHhppSinEstado(hhppId);
    }

    @Override
    public MarcasMgl findMarcasMglByCodigo(String marca) throws ApplicationException{
        return marcasHhppMglManager.findMarcasMglByCodigo(marca);
    }

    @Override
    public List<MarcasHhppMgl> buscarXHhppMarca(BigDecimal homePass, BigDecimal marca) 
            throws ApplicationException {
        return marcasHhppMglManager.buscarXHhppMarca(homePass, marca);
    }
    
    @Override
    public List<MarcasHhppMgl> findResultadosFiltro(FiltroMarcasMglDto filtroMarcasDto) throws ApplicationException {
        return marcasHhppMglManager.findResultadosFiltro(filtroMarcasDto);
    }   
    
}
