/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

/**
 *
 * @author Admin
 */
import co.com.claro.mgl.businessmanager.cm.CmtNotasMglManager;
import co.com.claro.mgl.dtos.FiltroNotasDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.cm.CmtNotasMgl;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Admin
 */
@Stateless
public class CmtNotasMglFacade implements CmtNotasMglFacadeLocal {

    private String user = "";
    private int perfil = 0;

    @Override
    public List<CmtNotasMgl> findAll() throws ApplicationException {
        CmtNotasMglManager cmtNotasMglMglManager = new CmtNotasMglManager();
        return cmtNotasMglMglManager.findAll();
    }

    @Override
    public List<CmtNotasMgl> findTipoNotasId(BigDecimal tipoNotasId) throws ApplicationException {
        CmtNotasMglManager cmtNotasMglMglManager = new CmtNotasMglManager();
        return cmtNotasMglMglManager.findTipoNotasId(tipoNotasId);
    }

    @Override
    public List<CmtNotasMgl> findNotasBySubEdificioId(BigDecimal subEdificioId, BigDecimal tipoNota) throws ApplicationException {
        CmtNotasMglManager cmtNotasMglMglManager = new CmtNotasMglManager();
        return cmtNotasMglMglManager.findNotasBySubEdificioId(subEdificioId, tipoNota);
    }

    @Override
    public CmtNotasMgl create(CmtNotasMgl notaMgl) throws ApplicationException {
        CmtNotasMglManager cmtNotasMglMglManager = new CmtNotasMglManager();
        return cmtNotasMglMglManager.create(notaMgl, user, perfil);
    }

    @Override
    public CmtNotasMgl update(CmtNotasMgl t) throws ApplicationException {
        CmtNotasMglManager cmtNotasMglMglManager = new CmtNotasMglManager();
        return cmtNotasMglMglManager.update(t);
    }

    @Override
    public boolean delete(CmtNotasMgl t) throws ApplicationException {
        CmtNotasMglManager cmtNotasMglMglManager = new CmtNotasMglManager();
        return cmtNotasMglMglManager.delete(t);
    }

    @Override
    public CmtNotasMgl findById(BigDecimal id) throws ApplicationException {
        CmtNotasMglManager cmtNotasMglMglManager = new CmtNotasMglManager();
        return cmtNotasMglMglManager.findById(id);
    }

    @Override
    public CmtNotasMgl findById(CmtNotasMgl sqlData) throws ApplicationException {
        CmtNotasMglManager cmtNotasMglMglManager = new CmtNotasMglManager();
        return cmtNotasMglMglManager.findById(sqlData);
    }

    @Override
    public void setUser(String mUser, int mPerfil) throws ApplicationException {
        if (mUser.equals("") || mPerfil == 0) {
            throw new ApplicationException("El Usuario o perfil Nopueden ser nulos");
        }
        user = mUser;
        perfil = mPerfil;
    }

    @Override
    public List<AuditoriaDto> construirAuditoria(CmtNotasMgl cmtNotasMgl)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (cmtNotasMgl != null) {
            CmtNotasMglManager cmtNotasMglManager = new CmtNotasMglManager();
            return cmtNotasMglManager.construirAuditoria(cmtNotasMgl);
        } else {
            return null;
        }
    }

    @Override
    public FiltroNotasDto findNotas(HashMap<String, Object> params, BigDecimal subEdificio, boolean contar, int firstResult, int maxResults) throws ApplicationException {
        CmtNotasMglManager cmtNotasMglManager = new CmtNotasMglManager();

        return cmtNotasMglManager.getNotasSearch(params,subEdificio, contar, firstResult, maxResults);
    }

    @Override
    public CmtNotasMgl create(CmtNotasMgl cmtNotasMgl, String usuario, int perfil) throws ApplicationException {
        CmtNotasMglManager cmtNotasMglManager = new CmtNotasMglManager(); 
        return cmtNotasMglManager.create(cmtNotasMgl, usuario, perfil);
    }
}
