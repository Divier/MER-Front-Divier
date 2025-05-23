/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

import co.com.claro.mgl.dtos.FiltroConsultaSubTipoOtTecDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubtipoOrdenVtTecMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoOtMgl;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author cardenaslb
 */
@Local
public interface CmtSubtipoOtVtTecFacadeLocal {
    
     public void setUser(String user, int perfil) throws ApplicationException;

    public CmtSubtipoOrdenVtTecMgl create(CmtSubtipoOrdenVtTecMgl t, String usuario, int perfilUsu) throws ApplicationException;

    public CmtSubtipoOrdenVtTecMgl update(CmtSubtipoOrdenVtTecMgl t, String usuario, int perfilUsu) throws ApplicationException;

    public boolean delete(CmtSubtipoOrdenVtTecMgl t, String usuario, int perfilUsu) throws ApplicationException;

    public FiltroConsultaSubTipoOtTecDto findSubtipoOtTec(HashMap<String, Object> params,
            boolean contar, int firstResult, int maxResults) throws ApplicationException;
    
     public CmtSubtipoOrdenVtTecMgl findById(CmtSubtipoOrdenVtTecMgl id) throws ApplicationException;
     
     public List<AuditoriaDto> construirAuditoria(CmtSubtipoOrdenVtTecMgl cmtSubtipoOrdenVtTecMgl)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException;
     
      public List<CmtSubtipoOrdenVtTecMgl> findSubtipoOtxTecno(CmtBasicaMgl basicaTecnologia) throws ApplicationException;
      
      public List<CmtSubtipoOrdenVtTecMgl> findConfSubtipoOtxTecno(CmtBasicaMgl basicaTecnologia, CmtTipoOtMgl cmtTipoOtMgl) throws ApplicationException;
      
       public List<CmtSubtipoOrdenVtTecMgl> findAll() throws ApplicationException;
}
