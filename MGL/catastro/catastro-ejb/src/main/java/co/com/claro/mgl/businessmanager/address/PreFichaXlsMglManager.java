/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.businessmanager.address;

import co.com.claro.mgl.dao.impl.PreFichaXlsMglDaoImpl;
import co.com.claro.mgl.dao.impl.storedprocedures.CargueFichasImpl;
import co.com.claro.mgl.dtos.CmtFiltroPrefichasDto;
import co.com.claro.mgl.dtos.ConsultaPrefichaXlsNewDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.PreFichaGeorreferenciaMgl;
import co.com.claro.mgl.jpa.entities.PreFichaMgl;
import co.com.claro.mgl.jpa.entities.PreFichaTxtMgl;
import co.com.claro.mgl.jpa.entities.PreFichaXlsMgl;
import co.com.telmex.catastro.data.AddressServiceBatchXml;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author User
 */
public class PreFichaXlsMglManager {

    public List<PreFichaXlsMgl> savePreFichaXlsList(PreFichaMgl preFichaMgl, List<PreFichaXlsMgl> prefichaXlsList) throws ApplicationException {
       
        for (PreFichaXlsMgl pfx : prefichaXlsList) {
            pfx.setPrfId(preFichaMgl.getPrfId());
            pfx.setIdPfx(BigDecimal.ZERO);
            pfx.setFechaCreacion(preFichaMgl.getFechaCreacion());
            pfx.setFechaEdicion(preFichaMgl.getFechaEdicion());
            pfx.setUsuarioCreacion(preFichaMgl.getUsuarioCreacion());
            pfx.setUsuarioEdicion(preFichaMgl.getUsuarioEdicion());
            pfx.setPerfilCreacion(1);
            pfx.setPerfilEdicion(1);
            pfx.setEstadoRegistro(1);
            
        }
        PreFichaXlsMglDaoImpl daoImpl = new PreFichaXlsMglDaoImpl();
        return daoImpl.create(prefichaXlsList);

    }
    
    public List<PreFichaXlsMgl> getListXLSByPrefichaFase(BigDecimal idPreficha, String fase){
        PreFichaXlsMglDaoImpl daoImpl = new PreFichaXlsMglDaoImpl();        
        return daoImpl.getListXLSByPrefichaFase(idPreficha, fase);
    }
    
    
    public List<PreFichaXlsMgl> getListadoPrefichasPorTab(BigDecimal idPreficha, String fase, String tab, CmtFiltroPrefichasDto filtros){
        PreFichaXlsMglDaoImpl daoImpl = new PreFichaXlsMglDaoImpl();        
        return daoImpl.getListadoPrefichasPorTab(idPreficha, fase , tab , filtros);
    }
     
    public void acualizarPrefichaXls(PreFichaGeorreferenciaMgl preFichaGeoActual, PreFichaGeorreferenciaMgl preFichaGeorreferenciaMglNueva, PreFichaXlsMgl xls , boolean isNoProcesados){
         PreFichaXlsMglDaoImpl daoImpl = new PreFichaXlsMglDaoImpl();
         daoImpl.acualizarPrefichaXls(preFichaGeoActual, preFichaGeorreferenciaMglNueva, xls , isNoProcesados);
        //Actualizar XLS
        //Actualizar Georeferenciacion
    
    }

    public List<PreFichaTxtMgl> getListadoPrefichasTxtPorTab(BigDecimal idPreficha) {
        PreFichaXlsMglDaoImpl daoImpl = new PreFichaXlsMglDaoImpl();
        return daoImpl.getListadoPrefichasTxtPorTab(idPreficha);
    }

    public List<PreFichaXlsMgl> getListadoByFaseAndIdPfAndPestana(BigDecimal idPreficha, String fase, String tab) {
        PreFichaXlsMglDaoImpl daoImpl = new PreFichaXlsMglDaoImpl();
        return daoImpl.getListadoByFaseAndIdPfAndPestana(idPreficha, fase, tab);
    }
    
    public List<ConsultaPrefichaXlsNewDto> getListadoPrefichaXlsNew(BigDecimal idPreficha) throws ApplicationException {
        CargueFichasImpl cargueFichasImpl = new CargueFichasImpl();
        return cargueFichasImpl.tecConsultaPrefichaXlsNew(idPreficha);
    }
    
    public int savePrefichaXlsNewList(List<AddressServiceBatchXml> prefichaXlsNewList, String usuarioVT) throws ApplicationException{
        CargueFichasImpl cargueFichasImpl = new CargueFichasImpl();
        return cargueFichasImpl.tecInsertGeoreferencia(prefichaXlsNewList, usuarioVT);
    }
    
    public int updatePrefichaGeo (BigDecimal idPreficha) throws ApplicationException {
        CargueFichasImpl cargueFichasImpl = new CargueFichasImpl();
        return cargueFichasImpl.updatePrefichaGeo(idPreficha);
    }
}
