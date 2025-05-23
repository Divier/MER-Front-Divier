/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.businessmanager.cm.CmtSubTipoOtVtTecMglManager;
import co.com.claro.mgl.dtos.FiltroConsultaSubTipoOtTecDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubtipoOrdenVtTecMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoOtMgl;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author cardenaslb
 */
@Stateless
public class CmtSubtipoOtVtTecFacade implements CmtSubtipoOtVtTecFacadeLocal{
    private String user = "";
    private int perfil = 0;

 



    @Override
    public void setUser(String user, int perfil) throws ApplicationException {
        if (user.isEmpty() || perfil == 0) {
            throw new ApplicationException("El Usuario o perfil Nopueden ser nulos");
        }
        user = user;
        perfil = perfil;
    }

  
    @Override
    public CmtSubtipoOrdenVtTecMgl create(CmtSubtipoOrdenVtTecMgl t, String usuario, int perfilUsu) throws ApplicationException {
        CmtSubTipoOtVtTecMglManager cmtSubTipoOtVtTecMglManager = new CmtSubTipoOtVtTecMglManager();
        return cmtSubTipoOtVtTecMglManager.create(t);
    }

    @Override
    public CmtSubtipoOrdenVtTecMgl update(CmtSubtipoOrdenVtTecMgl t, String usuario, int perfilUsu) throws ApplicationException {
         CmtSubTipoOtVtTecMglManager cmtSubTipoOtVtTecMglManager = new CmtSubTipoOtVtTecMglManager();
        return cmtSubTipoOtVtTecMglManager.update(t);
    }

    @Override
    public FiltroConsultaSubTipoOtTecDto findSubtipoOtTec(HashMap<String, Object> params, boolean contar, int firstResult, int maxResults) throws ApplicationException {
        CmtSubTipoOtVtTecMglManager manager = new CmtSubTipoOtVtTecMglManager();
        return manager.findSubtipoOtTec(params, contar, firstResult, maxResults);
    }

    @Override
    public boolean delete(CmtSubtipoOrdenVtTecMgl t, String usuario, int perfilUsu) throws ApplicationException {
       CmtSubTipoOtVtTecMglManager cmtSubTipoOtVtTecMglManager = new CmtSubTipoOtVtTecMglManager();
        return cmtSubTipoOtVtTecMglManager.delete(t);
    }

    @Override
    public CmtSubtipoOrdenVtTecMgl findById(CmtSubtipoOrdenVtTecMgl id) throws ApplicationException {
       CmtSubTipoOtVtTecMglManager cmtSubTipoOtVtTecMglManager = new CmtSubTipoOtVtTecMglManager();
       return cmtSubTipoOtVtTecMglManager.findById(id);
    }

    @Override
    public List<AuditoriaDto> construirAuditoria(CmtSubtipoOrdenVtTecMgl cmtSubtipoOrdenVtTecMgl) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
            if (cmtSubtipoOrdenVtTecMgl != null) {
             CmtSubTipoOtVtTecMglManager cmtSubTipoOtVtTecMglManager = new CmtSubTipoOtVtTecMglManager();
            return cmtSubTipoOtVtTecMglManager.construirAuditoria(cmtSubtipoOrdenVtTecMgl);
        } else {
            return null;
        }
    }

    @Override
    public List<CmtSubtipoOrdenVtTecMgl> findSubtipoOtxTecno(CmtBasicaMgl basicaTecnologia) throws ApplicationException {
        CmtSubTipoOtVtTecMglManager cmtSubTipoOtVtTecMglManager = new CmtSubTipoOtVtTecMglManager();
        return cmtSubTipoOtVtTecMglManager.findSubtipoOtxTecno(basicaTecnologia);
    }
    
    	 @Override
    public List<CmtSubtipoOrdenVtTecMgl> findConfSubtipoOtxTecno(CmtBasicaMgl basicaTecnologia, CmtTipoOtMgl cmtTipoOtMgl) throws ApplicationException {
        CmtSubTipoOtVtTecMglManager cmtSubTipoOtVtTecMglManager = new CmtSubTipoOtVtTecMglManager();
        return cmtSubTipoOtVtTecMglManager.findConfSubtipoOtxTecno(basicaTecnologia, cmtTipoOtMgl);
    }

    @Override
    public List<CmtSubtipoOrdenVtTecMgl> findAll() throws ApplicationException {
         CmtSubTipoOtVtTecMglManager cmtSubTipoOtVtTecMglManager = new CmtSubTipoOtVtTecMglManager();
         return cmtSubTipoOtVtTecMglManager.findAll();
    }
	
    

}
