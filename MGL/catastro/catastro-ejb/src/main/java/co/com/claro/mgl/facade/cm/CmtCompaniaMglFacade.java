/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade.cm;

/**
 *
 * @author Admin
 */
import co.com.claro.cmas400.ejb.request.RequestDataConstructorasMgl;
import co.com.claro.cmas400.ejb.respons.ResponseConstructorasList;
import co.com.claro.mgl.businessmanager.cm.CmtCompaniaMglManager;
import co.com.claro.mgl.dtos.FiltroConsultaCompaniasDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCompaniaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoCompaniaMgl;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Admin
 */
@Stateless
public class CmtCompaniaMglFacade implements CmtCompaniaMglFacadeLocal {

    private String user = "";
    private int perfil = 0;
    
    @Override
    public List<CmtCompaniaMgl> findAll() throws ApplicationException {
        CmtCompaniaMglManager cmtCompaniaMglMglManager = new CmtCompaniaMglManager();
        return cmtCompaniaMglMglManager.findAll();
    }
    @Override
    public CmtCompaniaMgl create(CmtCompaniaMgl t) throws ApplicationException {
        CmtCompaniaMglManager cmtCompaniaMglMglManager = new CmtCompaniaMglManager();
        return cmtCompaniaMglMglManager.create(t, user, perfil);
    }
    @Override
    public CmtCompaniaMgl update(CmtCompaniaMgl t) throws ApplicationException {
        CmtCompaniaMglManager cmtCompaniaMglMglManager = new CmtCompaniaMglManager();
        return cmtCompaniaMglMglManager.update(t, user, perfil);
    }
    @Override
    public boolean delete(CmtCompaniaMgl t) throws ApplicationException {
        CmtCompaniaMglManager cmtCompaniaMglMglManager = new CmtCompaniaMglManager();
        return cmtCompaniaMglMglManager.delete(t, user, perfil);
    }
    @Override
    public CmtCompaniaMgl findById(BigDecimal id) throws ApplicationException {
        CmtCompaniaMglManager cmtCompaniaMglMglManager = new CmtCompaniaMglManager();
        return cmtCompaniaMglMglManager.findById(id);
    }
    @Override
    public CmtCompaniaMgl findById(CmtCompaniaMgl sqlData) throws ApplicationException {
        CmtCompaniaMglManager cmtCompaniaMglMglManager = new CmtCompaniaMglManager();
        return cmtCompaniaMglMglManager.findById(sqlData);
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
    public List<CmtCompaniaMgl> findByTipoCompania(CmtTipoCompaniaMgl tipoCompania) throws ApplicationException {
        CmtCompaniaMglManager cmtCompaniaMglMglManager = new CmtCompaniaMglManager();
        return cmtCompaniaMglMglManager.findByTipoCompania(tipoCompania);
    }
    @Override
    public List<AuditoriaDto> construirAuditoria(CmtCompaniaMgl cmtCompaniaMgl) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (cmtCompaniaMgl != null) {
            CmtCompaniaMglManager cmtCompaniaMglMglManager = new CmtCompaniaMglManager();
            return cmtCompaniaMglMglManager.construirAuditoria(cmtCompaniaMgl);
        } else {
            return null;
        }
    }
    @Override
    public List<CmtCompaniaMgl> findByMunicipioByTipeCompany(GeograficoPoliticoMgl municipio,
            CmtTipoCompaniaMgl tipoCompania) throws ApplicationException {
        CmtCompaniaMglManager cmtCompaniaMglMglManager = new CmtCompaniaMglManager();
        return cmtCompaniaMglMglManager.findByMunicipioByTipeCompany(municipio,
                tipoCompania);
    }
    @Override
    public List<CmtCompaniaMgl> findByfiltro(FiltroConsultaCompaniasDto filtro, boolean ordenarPorCodigo) 
            throws ApplicationException {
        CmtCompaniaMglManager cmtCompaniaMglMglManager = new CmtCompaniaMglManager();
        return cmtCompaniaMglMglManager.findByfiltro(filtro,ordenarPorCodigo);
    }
    
    /**
     * *Victor Bocanegra Metodo para conseguir las compañias por los filtros de
     * la tabla y paginado
     *
     * @param paginaSelected
     * @param maxResults
     * @param filtro
     * @param ordenarPorCodigo
     * @return List<CmtCompaniaMgl>
     * @throws ApplicationException
     */
    @Override
    public List<CmtCompaniaMgl> findByfiltroAndPaginado(int paginaSelected,
            int maxResults, FiltroConsultaCompaniasDto filtro, boolean ordenarPorCodigo)
            throws ApplicationException {
        CmtCompaniaMglManager cmtCompaniaMglMglManager = new CmtCompaniaMglManager();
        return cmtCompaniaMglMglManager.findByfiltroAndPaginado(paginaSelected, maxResults,filtro,ordenarPorCodigo);
    }
   
    /**
     * *Victor Bocanegra Metodo para contar las compañias por los filtros de la
     * tabla
     *
     * @param filtro
     * @param ordenarPorCodigo
     * @return Long
     * @throws ApplicationException
     */
    @Override
    public Long countByCompaFiltro(FiltroConsultaCompaniasDto filtro, boolean ordenarPorCodigo)
            throws ApplicationException {
        CmtCompaniaMglManager cmtCompaniaMglMglManager = new CmtCompaniaMglManager();
        return cmtCompaniaMglMglManager.countByCompaFiltro(filtro, ordenarPorCodigo);
    }
    
    
    @Override
    public String buscarUltimoCodigoNumerico(CmtTipoCompaniaMgl tipoCompaniaMgl) throws ApplicationException {
        CmtCompaniaMglManager cmtCompaniaMglMglManager = new CmtCompaniaMglManager();
        return cmtCompaniaMglMglManager.buscarUltimoCodigoNumerico(tipoCompaniaMgl);
    }
    /**
     * Autor: Victor Bocanegra Metodo encargado de consultar los registros de
     * constructoras en la tabla CMT_COMPANIAS.
     *
     * @param request
     * @return ResponseConstructorasList Objeto utilizado para capturar los
     * resultados de la ejecucion del PCML
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    @Override
    public ResponseConstructorasList constructorasQueryMgl(
            RequestDataConstructorasMgl request) throws ApplicationException {
        CmtCompaniaMglManager cmtCompaniaMglMglManager = new CmtCompaniaMglManager();
        return cmtCompaniaMglMglManager.constructorasQueryMgl(request);
    }
}
