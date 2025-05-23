
package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.DireccionMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.DireccionMgl;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Admin
 */
@Stateless
public class DireccionMglFacade implements DireccionMglFacadeLocal {

    private final DireccionMglManager direccionMglManager;

    public DireccionMglFacade() {
        this.direccionMglManager = new DireccionMglManager();
    }

    @Override
    public List<DireccionMgl> findAll() throws ApplicationException {
        return direccionMglManager.findAll();
    }

    @Override
    public DireccionMgl create(DireccionMgl direccionMgl) throws ApplicationException {
        return direccionMglManager.create(direccionMgl);
    }

    @Override
    public DireccionMgl updateDireccionMgl(DireccionMgl direccionMgl) throws ApplicationException {
        return direccionMglManager.update(direccionMgl);
    }

    @Override
    public boolean delete(DireccionMgl direccionMgl) throws ApplicationException {
        return direccionMglManager.delete(direccionMgl);
    }

    @Override
    public DireccionMgl findById(DireccionMgl direccionMgl) throws ApplicationException {
        return direccionMglManager.findById(direccionMgl);
    }

    @Override
    public List<DireccionMgl> findByDireccion(String direccion, BigDecimal idgpo) throws ApplicationException {
        return direccionMglManager.findByDireccion(direccion, idgpo);
    }
	
    @Override
	public List<DireccionMgl> findByDirId(BigDecimal dirId) throws ApplicationException {
        return direccionMglManager.findByDirId(dirId);
    }

    /**
     * @param ubicacionId
     * @return direccionMglManager
     * @throws ApplicationException
     */
    @Override
    public List<DireccionMgl> findByDireccionAlterna(BigDecimal ubicacionId) throws ApplicationException {
        return direccionMglManager.findByDireccionAlterna(ubicacionId);
    }

    /**
     * @param dirServiInfo
     * @param direccion
     * @return direccionMglManager
     * @throws ApplicationException
     */
    @Override
    public List<DireccionMgl> findDireccionByServinfoNoStandar(String dirServiInfo, String direccion) throws ApplicationException {
        return direccionMglManager.findDireccionByServinfoNoStandar(dirServiInfo, direccion);
    }

    @Override
    public DireccionMgl update(DireccionMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public List<AuditoriaDto> construirAuditoria(DireccionMgl t)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (t != null) {
            return direccionMglManager.construirAuditoria(t);
        } else {
            return null;
        }
    }
}
