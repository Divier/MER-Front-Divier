/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.SubDireccionMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.SubDireccionMgl;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Admin
 */
@Stateless
public class SubDireccionMglFacade implements SubDireccionMglFacadeLocal {
    private final SubDireccionMglManager subDireccionMglManager;
    
    public SubDireccionMglFacade() {
        this.subDireccionMglManager = new SubDireccionMglManager();
    }

    @Override
    public List<SubDireccionMgl> findByIdDireccionMgl(BigDecimal idDireccionMgl) throws ApplicationException {        
        return subDireccionMglManager.findByIdDireccionMgl(idDireccionMgl);
    }

    @Override
    public List<SubDireccionMgl> findAll() throws ApplicationException {
        return subDireccionMglManager.findAll();
    }

    @Override
    public SubDireccionMgl create(SubDireccionMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SubDireccionMgl updateSubDireccionMgl(SubDireccionMgl t) throws ApplicationException {
         return subDireccionMglManager.update(t);
    }

    @Override
    public boolean delete(SubDireccionMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public SubDireccionMgl findById(SubDireccionMgl subDireccion) throws ApplicationException {
        return subDireccionMglManager.findById(subDireccion.getSdiId());
    }

    
    @Override
    public List<SubDireccionMgl> findByDirId(BigDecimal dirId) throws ApplicationException {
        return subDireccionMglManager.findByDirId(dirId);
    }

    @Override
    public SubDireccionMgl update(SubDireccionMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<AuditoriaDto> construirAuditoria(SubDireccionMgl t)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (t != null) {
            return subDireccionMglManager.construirAuditoria(t);
        } else {
            return null;
        }
    }
}
