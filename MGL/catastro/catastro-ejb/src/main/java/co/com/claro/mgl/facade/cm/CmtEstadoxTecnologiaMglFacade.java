/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmEstadoxTecnologiaMglManager;
import co.com.claro.mgl.dtos.EstadosTecnologiasDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtEstadoxTecnologiaMgl;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author cardenaslb
 */
@Stateless
public class CmtEstadoxTecnologiaMglFacade extends BaseCmFacade<CmtEstadoxTecnologiaMgl> implements CmtEstadoxTecnologiaMglFacadeLocal {

    public CmtEstadoxTecnologiaMgl create(CmtEstadoxTecnologiaMgl t, String user, int perfil) throws ApplicationException {
        CmEstadoxTecnologiaMglManager cmEstadoxTecnologiaMglManager
                = new CmEstadoxTecnologiaMglManager();
        return cmEstadoxTecnologiaMglManager.create(t, getUser(), getPerfil());
    }

    @Override
    public CmtEstadoxTecnologiaMgl update(CmtEstadoxTecnologiaMgl t) throws ApplicationException {
        CmEstadoxTecnologiaMglManager cmEstadoxTecnologiaMglManager
                = new CmEstadoxTecnologiaMglManager();
        return cmEstadoxTecnologiaMglManager.update(t, getUser(), getPerfil());
    }

    @Override
    public boolean delete(CmtEstadoxTecnologiaMgl t) throws ApplicationException {
        CmEstadoxTecnologiaMglManager cmEstadoxTecnologiaMglManager
                = new CmEstadoxTecnologiaMglManager();
        return cmEstadoxTecnologiaMglManager.delete(t);
    }

    @Override
    public List<CmtEstadoxTecnologiaMgl> findAll() throws ApplicationException {
        CmEstadoxTecnologiaMglManager cmEstadoxTecnologiaMglManager
                = new CmEstadoxTecnologiaMglManager();
        return cmEstadoxTecnologiaMglManager.findAll();

    }

    @Override
    public List<EstadosTecnologiasDto> getListaConf(String estadoCcmm) throws ApplicationException {
        CmEstadoxTecnologiaMglManager cmEstadoxTecnologiaMglManager
                = new CmEstadoxTecnologiaMglManager();
        return cmEstadoxTecnologiaMglManager.getListaConf(getUser(), getPerfil(), estadoCcmm);
    }

    @Override
    public CmtEstadoxTecnologiaMgl findById(CmtEstadoxTecnologiaMgl sqlData) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CmtEstadoxTecnologiaMgl create(CmtEstadoxTecnologiaMgl t) throws ApplicationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean createConf(List<EstadosTecnologiasDto> t, String user, int perfil) throws ApplicationException {
        CmEstadoxTecnologiaMglManager cmEstadoxTecnologiaMglManager
                = new CmEstadoxTecnologiaMglManager();
        return cmEstadoxTecnologiaMglManager.createConfig(t, user, perfil);
    }
    @Override
    public List<CmtEstadoxTecnologiaMgl> findByEstado(CmtBasicaMgl cmtBasicaMgl) throws ApplicationException {
        CmEstadoxTecnologiaMglManager cmEstadoxTecnologiaMglManager
                = new CmEstadoxTecnologiaMglManager();
        return cmEstadoxTecnologiaMglManager.findByEstado(cmtBasicaMgl);
    }

}
