package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmtConvergenciaViabilidadEstratoMglManager;
import co.com.claro.mgl.dtos.FiltroConsultaViabilidadSegmentoDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtConvergenciaViabilidadEstratoMgl;
import java.util.List;
import javax.ejb.Stateless;

/**
 * Facade Viabilidad Segmento Estratos Convergencia. Expone la logica de negocio 
 * para el manejo de Viabilidad Segmento Estratos Convergencia en el repositorio.
 *
 * @author Laura Carolina Muñoz - HITSS
 * @version 1.00.000
 */
@Stateless
public class CmtConvergenciaViabilidadEstratoMglFacade implements CmtConvergenciaViabilidadEstratoMglFacadeLocal {
    @Override
    public List<CmtConvergenciaViabilidadEstratoMgl> findAll() throws ApplicationException {
        CmtConvergenciaViabilidadEstratoMglManager cmtConverViabilidadEstratoManager = new CmtConvergenciaViabilidadEstratoMglManager();
        return cmtConverViabilidadEstratoManager.findAll();
    }
     
    @Override
     public CmtConvergenciaViabilidadEstratoMgl create(CmtConvergenciaViabilidadEstratoMgl t, String usuario, int perfil) throws ApplicationException {
        CmtConvergenciaViabilidadEstratoMglManager cmtConverViabilidadEstratoManager = new CmtConvergenciaViabilidadEstratoMglManager();
        return cmtConverViabilidadEstratoManager.create(t, usuario, perfil);
    }

    @Override
    public CmtConvergenciaViabilidadEstratoMgl update(CmtConvergenciaViabilidadEstratoMgl t, String usuario, int perfil) throws ApplicationException {
        CmtConvergenciaViabilidadEstratoMglManager cmtConverViabilidadEstratoManager = new CmtConvergenciaViabilidadEstratoMglManager();
        return cmtConverViabilidadEstratoManager.update(t, usuario, perfil);
    }

    @Override
    public CmtConvergenciaViabilidadEstratoMgl create(CmtConvergenciaViabilidadEstratoMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CmtConvergenciaViabilidadEstratoMgl update(CmtConvergenciaViabilidadEstratoMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(CmtConvergenciaViabilidadEstratoMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CmtConvergenciaViabilidadEstratoMgl findById(CmtConvergenciaViabilidadEstratoMgl sqlData) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public List<CmtConvergenciaViabilidadEstratoMgl> findViabilidadSegEstratoPaginacion(FiltroConsultaViabilidadSegmentoDto filtro, 
            int paginaSelected, int maxResultso) throws ApplicationException{
        CmtConvergenciaViabilidadEstratoMglManager cmtConverViabilidadEstratoManager = new CmtConvergenciaViabilidadEstratoMglManager();
        return cmtConverViabilidadEstratoManager.findViabilidadSegEstratoPaginacion(filtro, paginaSelected, maxResultso);
    }
    

    @Override
    public int getCountFindByAll(FiltroConsultaViabilidadSegmentoDto filtro) throws ApplicationException{
        CmtConvergenciaViabilidadEstratoMglManager cmtConverViabilidadEstratoManager = new CmtConvergenciaViabilidadEstratoMglManager();
        return cmtConverViabilidadEstratoManager.getCountFindByAll(filtro);
    }
    
}
