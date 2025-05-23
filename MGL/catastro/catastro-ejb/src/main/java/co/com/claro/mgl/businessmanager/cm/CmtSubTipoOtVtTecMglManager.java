/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.cm;

import co.com.claro.mgl.dao.impl.cm.CmtSubTipoOtVtTecMglDaoImpl;
import co.com.claro.mgl.dtos.FiltroConsultaSubTipoOtTecDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubtipoOrdenVtTecAudMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubtipoOrdenVtTecMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoOtMgl;
import co.com.claro.mgl.utils.UtilsCMAuditoria;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author cardenaslb
 */
public class CmtSubTipoOtVtTecMglManager {
    
       public CmtSubtipoOrdenVtTecMgl create(CmtSubtipoOrdenVtTecMgl cmtSubtipoOrdenVtTecMgl) throws ApplicationException {
        CmtSubTipoOtVtTecMglDaoImpl dao = new CmtSubTipoOtVtTecMglDaoImpl();
        return dao.create(cmtSubtipoOrdenVtTecMgl);
    }
       
    public CmtSubtipoOrdenVtTecMgl update(CmtSubtipoOrdenVtTecMgl cmtSubtipoOrdenVtTecMgl) throws ApplicationException {
        CmtSubTipoOtVtTecMglDaoImpl dao = new CmtSubTipoOtVtTecMglDaoImpl();
        return dao.update(cmtSubtipoOrdenVtTecMgl);
    }
    
    public boolean delete(CmtSubtipoOrdenVtTecMgl cmtSubtipoOrdenVtTecMgl) throws ApplicationException {
        CmtSubTipoOtVtTecMglDaoImpl dao = new CmtSubTipoOtVtTecMglDaoImpl();
        return dao.delete(cmtSubtipoOrdenVtTecMgl);
    }
       public CmtSubtipoOrdenVtTecMgl findById(CmtSubtipoOrdenVtTecMgl cmtSubtipoOrdenVtTecMgl) throws ApplicationException {
        CmtSubTipoOtVtTecMglDaoImpl dao = new CmtSubTipoOtVtTecMglDaoImpl();
        return dao.find(cmtSubtipoOrdenVtTecMgl.getIdSubtipoOtVtTec());
    }
       
    public FiltroConsultaSubTipoOtTecDto findSubtipoOtTec(HashMap<String, Object> params, boolean contar, int firstResult, int maxResults) throws ApplicationException {
        CmtSubTipoOtVtTecMglDaoImpl dao = new CmtSubTipoOtVtTecMglDaoImpl();

        if (params != null && (params.get("idSubtipoOtVtTec") != null)) {
            params.put("idSubtipoOtVtTec", params.get("idSubtipoOtVtTec"));
            params.put("tipoFlujoOtObj", params.get("tipoFlujoOtObj"));
            params.put("basicaTecnologia", params.get("basicaTecnologia"));

        }
        return dao.findSubtipoOtTec(params, contar, firstResult, maxResults);
    }
    
       public CmtSubtipoOrdenVtTecMgl findSubTipoxTecnologia(CmtBasicaMgl basicaTecnologia) throws ApplicationException {
        CmtSubTipoOtVtTecMglDaoImpl dao = new CmtSubTipoOtVtTecMglDaoImpl();
        return dao.findSubTipoxTecnologia(basicaTecnologia);
    }
       
     public List<AuditoriaDto> construirAuditoria(CmtSubtipoOrdenVtTecMgl cmtSubtipoOrdenVtTecMgl)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        UtilsCMAuditoria<CmtSubtipoOrdenVtTecMgl, CmtSubtipoOrdenVtTecAudMgl> utilsCMAuditoria = new UtilsCMAuditoria<CmtSubtipoOrdenVtTecMgl, CmtSubtipoOrdenVtTecAudMgl>();
        List<AuditoriaDto> listAuditoriaDto = utilsCMAuditoria.construirAuditoria(cmtSubtipoOrdenVtTecMgl);
        return listAuditoriaDto;

    }
     
       public List<CmtSubtipoOrdenVtTecMgl> findSubtipoOtxTecno(CmtBasicaMgl basicaTecnologia) throws ApplicationException {
        CmtSubTipoOtVtTecMglDaoImpl dao = new CmtSubTipoOtVtTecMglDaoImpl();
        return dao.findSubtipoOtxTecno(basicaTecnologia);

    }
       
       	
    public List<CmtSubtipoOrdenVtTecMgl> findConfSubtipoOtxTecno(CmtBasicaMgl basicaTecnologia, CmtTipoOtMgl cmtTipoOtMgl) throws ApplicationException {
        CmtSubTipoOtVtTecMglDaoImpl dao = new CmtSubTipoOtVtTecMglDaoImpl();
        return dao.findConfSubtipoOtxTecno(basicaTecnologia,cmtTipoOtMgl);

    }
    	
    public List<CmtSubtipoOrdenVtTecMgl> findAll() throws ApplicationException {
        CmtSubTipoOtVtTecMglDaoImpl dao = new CmtSubTipoOtVtTecMglDaoImpl();
        return dao.findAllItems();

    }
}
