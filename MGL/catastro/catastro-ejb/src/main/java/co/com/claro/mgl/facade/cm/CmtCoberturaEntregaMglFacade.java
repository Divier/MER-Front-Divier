
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmtCoberturaEntregaMglManager;
import co.com.claro.mgl.dtos.CmtFiltroCoberturasDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtCoberturaEntregaMgl;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Juan David Hernandez
 */
@Stateless
public class CmtCoberturaEntregaMglFacade implements CmtCoberturaEntregaMglFacadeLocal{
    
    private String user = "";
    private int perfil = 0;

    CmtCoberturaEntregaMglManager managerCoberturas = new CmtCoberturaEntregaMglManager();
    
    
    @Override
    public void setUser(String mUser, int mPerfil) throws ApplicationException {
        if (mUser.equals("") || mPerfil == 0) {
            throw new ApplicationException("El Usuario o perfil No pueden ser nulos");
        }
        user = mUser;
        perfil = mPerfil;
    }


    @Override
    public List<CmtCoberturaEntregaMgl> findAll() throws ApplicationException {
        return managerCoberturas.findAll();
    }

    @Override
    public CmtCoberturaEntregaMgl create(CmtCoberturaEntregaMgl t) throws ApplicationException {
        return managerCoberturas.create(t);
    }

    @Override
    public CmtCoberturaEntregaMgl update(CmtCoberturaEntregaMgl t) throws ApplicationException {
        return managerCoberturas.update(t);
    }

    @Override
    public boolean delete(CmtCoberturaEntregaMgl t) throws ApplicationException {
        t.setEstadoRegistro(Short.valueOf("0"));
        managerCoberturas.update(t);
        return true;
    }

    @Override
    public CmtCoberturaEntregaMgl findById(CmtCoberturaEntregaMgl sqlData) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public List<String> buscarListaCoberturaEntregaIdCentroPoblado(BigDecimal centroPobladoId) throws ApplicationException {
        CmtCoberturaEntregaMglManager CmtCoberturaEntregaMglManager = new CmtCoberturaEntregaMglManager();
        return CmtCoberturaEntregaMglManager.buscarListaCoberturaEntregaIdCentroPoblado(centroPobladoId);
    }

    @Override
    public List<CmtCoberturaEntregaMgl> findCoberturaEntregaListByCentroPobladoId(BigDecimal centroPobladoId) throws ApplicationException {
        CmtCoberturaEntregaMglManager CmtCoberturaEntregaMglManager = new CmtCoberturaEntregaMglManager();
        return CmtCoberturaEntregaMglManager.findCoberturaEntregaListByCentroPobladoId(centroPobladoId);
    }

    @Override
    public CmtFiltroCoberturasDto findCoberturaEntregaListByCentroPoblado(
            HashMap<String, Object> filtro, boolean contar, int firstResult, int maxResults) throws ApplicationException {
        CmtCoberturaEntregaMglManager CmtCoberturaEntregaMglManager = new CmtCoberturaEntregaMglManager();
        return CmtCoberturaEntregaMglManager.findCoberturaEntregaListByCentroPoblado(
                filtro, contar, firstResult, maxResults);
    }

    @Override
    public List<CmtCoberturaEntregaMgl> findCoberturaEntregaListByCentroPobladoIdOperadorId(BigDecimal centroPobladoId, BigDecimal operadorID) {
        CmtCoberturaEntregaMglManager CmtCoberturaEntregaMglManager = new CmtCoberturaEntregaMglManager();
        return CmtCoberturaEntregaMglManager.findCoberturaEntregaListByCentroPobladoIdOperadorId(centroPobladoId, operadorID);
    }
    
    

    
    
}
