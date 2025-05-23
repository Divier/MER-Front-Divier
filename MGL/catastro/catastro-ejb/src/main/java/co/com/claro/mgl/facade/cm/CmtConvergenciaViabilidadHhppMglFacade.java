package co.com.claro.mgl.facade.cm;


import co.com.claro.mgl.businessmanager.cm.CmtConvergenciaViabilidadHhppMglManager;
import co.com.claro.mgl.dtos.FiltroConsultaViabilidadHhppDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtConvergenciaViabilidadHhppMgl;
import java.util.List;
import javax.ejb.Stateless;

/**
 * Facade Viabilidad HHPP Convergencia. Expone la logica de negocio para el manejo de
 * Viabilidad HHPP Convergencia en el repositorio.
 *
 * @author Laura Carolina Muñoz - HITSS
 * @version 1.00.000
 */
@Stateless
public class CmtConvergenciaViabilidadHhppMglFacade implements CmtConvergenciaViabilidadHhppMglFacadeLocal{
    
    @Override
    public List<CmtConvergenciaViabilidadHhppMgl> findAll() throws ApplicationException {
        CmtConvergenciaViabilidadHhppMglManager cmtConvergenciaViabilidadManager = new CmtConvergenciaViabilidadHhppMglManager();
        return cmtConvergenciaViabilidadManager.findAll();
    }
     
    @Override
     public CmtConvergenciaViabilidadHhppMgl create(CmtConvergenciaViabilidadHhppMgl t, String usuario, int perfil) throws ApplicationException {
        CmtConvergenciaViabilidadHhppMglManager cmtConvergenciaViabilidadManager = new CmtConvergenciaViabilidadHhppMglManager();
        return cmtConvergenciaViabilidadManager.create(t, usuario, perfil);
    }

    @Override
    public CmtConvergenciaViabilidadHhppMgl update(CmtConvergenciaViabilidadHhppMgl t, String usuario, int perfil) throws ApplicationException {
        CmtConvergenciaViabilidadHhppMglManager cmtConvergenciaViabilidadManager = new CmtConvergenciaViabilidadHhppMglManager();
        return cmtConvergenciaViabilidadManager.update(t, usuario, perfil);
    }

    @Override
    public CmtConvergenciaViabilidadHhppMgl create(CmtConvergenciaViabilidadHhppMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CmtConvergenciaViabilidadHhppMgl update(CmtConvergenciaViabilidadHhppMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(CmtConvergenciaViabilidadHhppMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CmtConvergenciaViabilidadHhppMgl findById(CmtConvergenciaViabilidadHhppMgl sqlData) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public List<CmtConvergenciaViabilidadHhppMgl> findViabilidadHhppPaginacion(FiltroConsultaViabilidadHhppDto filtro,
            int paginaSelected, int maxResultso) throws ApplicationException{
        CmtConvergenciaViabilidadHhppMglManager cmtConvergenciaViabilidadManager = new CmtConvergenciaViabilidadHhppMglManager();
        return cmtConvergenciaViabilidadManager.findViabilidadHhppPaginacion(filtro,paginaSelected, maxResultso);
    }
            
    
    @Override
    public int getCountFindByAll(FiltroConsultaViabilidadHhppDto filtro) throws ApplicationException{
         CmtConvergenciaViabilidadHhppMglManager cmtConvergenciaViabilidadManager = new CmtConvergenciaViabilidadHhppMglManager();
        return cmtConvergenciaViabilidadManager.getCountFindByAll(filtro);
    }
    
}
