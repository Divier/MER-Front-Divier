
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmtTipoBasicaExtMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaExtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author cardenaslb
 */
@Stateless
public class CmtTipoBasicaExtMglFacade implements CmtTipoBasicaExtMglFacadeLocal{
       /**
     * Función almacena informacion de registros de extension o adicionales
     *
     * @author cardenaslb
     * @param t entidad basica extendida
     * @return 
     * @throws co.com.claro.mgl.error.ApplicationException 
     */
    @Override
    public List<CmtTipoBasicaExtMgl> getCamposAdic(CmtTipoBasicaMgl t) throws ApplicationException {
       List<CmtTipoBasicaExtMgl> listaTipoBasicaExt;
           CmtTipoBasicaExtMglManager cmtTipoBasicaExtMglManager = new CmtTipoBasicaExtMglManager();
           listaTipoBasicaExt = cmtTipoBasicaExtMglManager.findCamposByTipoBasica(t);
           return listaTipoBasicaExt;
    }

    @Override
    public List<CmtTipoBasicaExtMgl> findAll() throws ApplicationException {
         List<CmtTipoBasicaExtMgl> listaTipoBasicaExt;
           CmtTipoBasicaExtMglManager cmtTipoBasicaExtMglManager = new CmtTipoBasicaExtMglManager();
           listaTipoBasicaExt = cmtTipoBasicaExtMglManager.findAll();
           return listaTipoBasicaExt;
    }

    @Override
    public CmtTipoBasicaExtMgl create(CmtTipoBasicaExtMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CmtTipoBasicaExtMgl update(CmtTipoBasicaExtMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(CmtTipoBasicaExtMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CmtTipoBasicaExtMgl findById(CmtTipoBasicaExtMgl sqlData) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
