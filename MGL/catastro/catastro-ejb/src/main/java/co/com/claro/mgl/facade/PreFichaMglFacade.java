/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

import co.com.claro.mgl.businessmanager.address.PreFichaAlertaMglManager;
import co.com.claro.mgl.businessmanager.address.PreFichaGeorreferenciaMglManager;
import co.com.claro.mgl.businessmanager.address.PreFichaGeorreferenciaMglNewManager;
import co.com.claro.mgl.businessmanager.address.PreFichaHHPPDetalleMglManager;
import co.com.claro.mgl.businessmanager.address.PreFichaMglManager;
import co.com.claro.mgl.businessmanager.address.PreFichaMglNewManager;
import co.com.claro.mgl.businessmanager.address.PreFichaTxtMglManager;
import co.com.claro.mgl.businessmanager.address.PreFichaTxtMglNewManager;
import co.com.claro.mgl.businessmanager.address.PreFichaXlsMglManager;
import co.com.claro.mgl.businessmanager.address.PreFichaXlsMglNewManager;
import co.com.claro.mgl.dtos.CmtFiltroPrefichasDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.PreFichaAlertaMgl;
import co.com.claro.mgl.jpa.entities.PreFichaGeorreferenciaMgl;
import co.com.claro.mgl.jpa.entities.PreFichaGeorreferenciaMglNew;
import co.com.claro.mgl.jpa.entities.PreFichaHHPPDetalleMgl;
import co.com.claro.mgl.jpa.entities.PreFichaMgl;
import co.com.claro.mgl.jpa.entities.PreFichaMglNew;
import co.com.claro.mgl.jpa.entities.PreFichaTxtMgl;
import co.com.claro.mgl.jpa.entities.PreFichaTxtMglNew;
import co.com.claro.mgl.jpa.entities.PreFichaXlsMgl;
import co.com.claro.mgl.jpa.entities.PreFichaXlsMglNew;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import co.com.claro.mgl.businessmanager.address.PreFichaHHPPDetalleMglNewManager;
import co.com.claro.mgl.dtos.ConsultaPrefichaXlsNewDto;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.PreFichaHHPPDetalleMglNew;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionDetalladaMgl;
import co.com.claro.visitasTecnicas.entities.DireccionRREntity;
import co.com.telmex.catastro.data.AddressServiceBatchXml;


/**
 *
 * @author User
 */
@Stateless
public class PreFichaMglFacade implements PreFichaMglFacadeLocal{

    @Override
    public PreFichaMgl savePreficha(PreFichaMgl preFichaMgl) throws ApplicationException {
        PreFichaMglManager manager = new PreFichaMglManager();
        return manager.savePreficha(preFichaMgl);
    }

    @Override
    public List<PreFichaTxtMgl> savePrefichaTxtList(PreFichaMgl preFichaMgl, List<PreFichaTxtMgl> prefichaTxtList) throws ApplicationException {
        PreFichaTxtMglManager manager = new PreFichaTxtMglManager();
        return manager.savePreFichaTxtList(preFichaMgl,prefichaTxtList);
    }

    @Override
    public List<PreFichaXlsMgl> savePrefichaXlsList(PreFichaMgl preFichaMgl, List<PreFichaXlsMgl> prefichaXlsList) throws ApplicationException {
        PreFichaXlsMglManager manager = new PreFichaXlsMglManager();
        return manager.savePreFichaXlsList(preFichaMgl,prefichaXlsList);
    }
    
    @Override
    public List<PreFichaGeorreferenciaMgl> savePreFichaGeoList(List<PreFichaGeorreferenciaMgl> prefichaGeoList) throws ApplicationException {
        PreFichaGeorreferenciaMglManager manager = new PreFichaGeorreferenciaMglManager();
        return manager.savePreFichaGeoList(prefichaGeoList);
    }

    @Override
    public List<PreFichaMgl> getListPrefichaByFase(List<String> faseList, int firstResult,
            int maxResults, boolean paginar) throws ApplicationException {
        PreFichaMglManager manager = new PreFichaMglManager();
        return manager.getListPrefichaByFase(faseList, firstResult, maxResults, paginar);
    }
        
    @Override
    public List<PreFichaMgl> getListPrefichaByFaseAndDate(List<String> faseList, int firstResult, int maxResults, boolean b, Date fechaInicial, Date fechaFinal) throws ApplicationException {
        PreFichaMglManager manager = new PreFichaMglManager();
        return manager.getListPrefichaByFaseAndDate(faseList, firstResult, maxResults, b, fechaInicial, fechaFinal);
    }
    
    @Override
    public List<PreFichaXlsMgl> getListXLSByPrefichaFase(BigDecimal idPreficha, String fase) throws ApplicationException {
        PreFichaXlsMglManager manager = new PreFichaXlsMglManager();
        return manager.getListXLSByPrefichaFase(idPreficha, fase);
    }
    
    @Override
    public List<PreFichaXlsMgl> getListadoPrefichasPorTab(BigDecimal idPreficha, String fase, String tab ,CmtFiltroPrefichasDto filtros)throws ApplicationException{
        PreFichaXlsMglManager manager = new PreFichaXlsMglManager();
        return manager.getListadoPrefichasPorTab(idPreficha, fase, tab ,filtros);
    }
    @Override
    public List<PreFichaTxtMgl> getListadoPrefichasTxtPorTab(BigDecimal idPreficha)throws ApplicationException{
        PreFichaXlsMglManager manager = new PreFichaXlsMglManager();
        return manager.getListadoPrefichasTxtPorTab(idPreficha);
    }

    @Override
    public PreFichaMgl updatePreFicha(PreFichaMgl preFichaMgl) throws ApplicationException {
        PreFichaMglManager manager = new PreFichaMglManager();
        return manager.updatePreficha(preFichaMgl);
    }

    @Override
    public boolean deletePreFicha(PreFichaMgl preFichaMgl) throws ApplicationException {
        PreFichaMglManager manager = new PreFichaMglManager();
        return manager.deletePreficha(preFichaMgl);
    }
    @Override
    public List<PreFichaMgl> getListPrefichaGeoFase() throws ApplicationException {
        PreFichaMglManager manager = new PreFichaMglManager();
        return manager.getListPrefichaGeoFase();
    }

    @Override
    public List<PreFichaHHPPDetalleMgl> savePreFichaDetalleHHPPList(List<PreFichaHHPPDetalleMgl> prefichaDetalleHHHPList) throws ApplicationException {
        PreFichaHHPPDetalleMglManager manager = new PreFichaHHPPDetalleMglManager();
        return manager.savePreFichaDetalleHHPPList(prefichaDetalleHHHPList);
    }

    @Override
    public PreFichaMgl getPreFichaById(BigDecimal idPreficha) throws ApplicationException {
        PreFichaMglManager manager = new PreFichaMglManager();
        return manager.getPreFichaById(idPreficha);
    }

    @Override
    public PreFichaHHPPDetalleMgl getPreFichaDetalleHHPPById(BigDecimal idHHppDetalle) throws ApplicationException {
        PreFichaHHPPDetalleMglManager manager = new PreFichaHHPPDetalleMglManager();
        return manager.getPreFichaDetalleHHPPById(idHHppDetalle);
    }

    @Override
    public List<PreFichaMgl> getListFichaToCreate(int firstResult,
            int maxResults, boolean paginar) throws ApplicationException {
        PreFichaMglManager manager = new PreFichaMglManager();
        return manager.getListFichaToCreate(firstResult, maxResults, paginar);
    }
    
    @Override
    public List<PreFichaMgl> getListFichaToCreateByDate(int firstResult, int maxResults, boolean b, Date fechaInicial, Date fechaFinal) throws ApplicationException {
        PreFichaMglManager manager = new PreFichaMglManager();
        return manager.getListFichaToCreateByDate(firstResult, maxResults, b, fechaInicial, fechaFinal);
    }

    @Override
    public List<PreFichaMgl> getListFichaToValidate() throws ApplicationException {
        PreFichaMglManager manager = new PreFichaMglManager();
        return manager.getListFichaToValidate();
    }
    
    @Override
    public PreFichaAlertaMgl savePreFichaAlerta(PreFichaAlertaMgl preFichaAlertaMgl) throws ApplicationException {
        PreFichaAlertaMglManager manager = new PreFichaAlertaMglManager();
        return manager.savePreFichaAlerta(preFichaAlertaMgl);
    }

    @Override
    public PreFichaGeorreferenciaMgl findPreFichaGeorreferenciaByIdPrefichaXls(BigDecimal id) throws ApplicationException {
        PreFichaGeorreferenciaMglManager manager = new PreFichaGeorreferenciaMglManager();
        return manager.findPreFichaGeorreferenciaByIdPrefichaXls(id);
    }

    @Override
    public void acualizarPrefichaXls(PreFichaGeorreferenciaMgl preFichaGeoActual, PreFichaGeorreferenciaMgl preFichaGeorreferenciaMglNueva, PreFichaXlsMgl xls, boolean isNoProcesados) {
        PreFichaXlsMglManager manager = new PreFichaXlsMglManager();
        manager.acualizarPrefichaXls(preFichaGeoActual, preFichaGeorreferenciaMglNueva, xls , isNoProcesados);
    }
    
    @Override
    public List<PreFichaHHPPDetalleMgl> obtenerDetallesHHPP(PreFichaXlsMgl prefichaXls) throws ApplicationException{
        PreFichaHHPPDetalleMglManager manager = new PreFichaHHPPDetalleMglManager();
        return manager.obtenerDetallesHHPP(prefichaXls);
    }
    
    		
    @Override
    public List<PreFichaXlsMgl> getListadoByFaseAndIdPfAndPestana(BigDecimal idPreficha, String fase, String tab) {
        PreFichaXlsMglManager manager = new PreFichaXlsMglManager();
        return manager.getListadoByFaseAndIdPfAndPestana(idPreficha, fase, tab);
    }
    //TEC_PREFICHA_NEW
    @Override
    public PreFichaMglNew savePrefichaNew(PreFichaMglNew preFichaMgl) throws ApplicationException {
        PreFichaMglNewManager manager = new PreFichaMglNewManager();
        return manager.savePrefichaNew(preFichaMgl);
    }    
    @Override
    public List<PreFichaMglNew> getListPrefichaNewByFase(List<String> faseList, int firstResult,
            int maxResults, boolean paginar) throws ApplicationException {
        PreFichaMglNewManager manager = new PreFichaMglNewManager();
        return manager.getListPrefichaNewByFase(faseList, firstResult, maxResults, paginar);
    }        
    @Override
    public List<PreFichaMglNew> getListPrefichaNewByFaseAndDate(List<String> faseList, int firstResult, int maxResults, boolean b, Date fechaInicial, Date fechaFinal) throws ApplicationException {
        PreFichaMglNewManager manager = new PreFichaMglNewManager();
        return manager.getListPrefichaNewByFaseAndDate(faseList, firstResult, maxResults, b, fechaInicial, fechaFinal);
    }    
        @Override
    public List<PreFichaMglNew> getListPrefichaNewGeoFase() throws ApplicationException {
        PreFichaMglNewManager manager = new PreFichaMglNewManager();
        return manager.getListPrefichaNewGeoFase();
    }    
        @Override
    public PreFichaMglNew updatePreFichaNew(PreFichaMglNew preFichaMgl) throws ApplicationException {
        PreFichaMglNewManager manager = new PreFichaMglNewManager();
        return manager.updatePrefichaNew(preFichaMgl);
    }
        @Override
    public boolean deletePreFichaNew(PreFichaMglNew preFichaMgl) throws ApplicationException {
        PreFichaMglNewManager manager = new PreFichaMglNewManager();
        return manager.deletePrefichaNew(preFichaMgl);
    }    
        @Override
    public PreFichaMglNew getPreFichaNewById(BigDecimal idPreficha) throws ApplicationException {
        PreFichaMglNewManager manager = new PreFichaMglNewManager();
        return manager.getPreFichaNewById(idPreficha);
    }
        @Override
    public List<PreFichaMglNew> getListFichaNewToCreate(int firstResult,
            int maxResults, boolean paginar) throws ApplicationException {
        PreFichaMglNewManager manager = new PreFichaMglNewManager();
        return manager.getListFichaNewToCreate(firstResult, maxResults, paginar);
    }
        @Override
    public List<PreFichaMglNew> getListFichaNewToCreateByDate(int firstResult, int maxResults, boolean b, Date fechaInicial, Date fechaFinal) throws ApplicationException {
        PreFichaMglNewManager manager = new PreFichaMglNewManager();
        return manager.getListFichaNewToCreateByDate(firstResult, maxResults, b, fechaInicial, fechaFinal);
    }    
        @Override
    public List<PreFichaMglNew> getListFichaNewToValidate() throws ApplicationException {
        PreFichaMglNewManager manager = new PreFichaMglNewManager();
        return manager.getListFichaNewToValidate();
    }
    //TXT_NEW
    @Override
    public List<PreFichaTxtMglNew> savePrefichaTxtNewList(PreFichaMglNew preFichaMgl, List<PreFichaTxtMglNew> prefichaTxtList) throws ApplicationException {
        PreFichaTxtMglNewManager manager = new PreFichaTxtMglNewManager();
        return manager.savePreFichaTxtNewList(preFichaMgl, prefichaTxtList);
    }
    //XLS_NEW
    @Override
    public List<PreFichaXlsMglNew> savePrefichaXlsNewList(PreFichaMglNew preFichaMgl, List<PreFichaXlsMglNew> prefichaXlsList) throws ApplicationException {
        PreFichaXlsMglNewManager manager = new PreFichaXlsMglNewManager();
        return manager.savePreFichaXlsNewList(preFichaMgl, prefichaXlsList);
    }

    @Override
    public List<PreFichaXlsMglNew> getListXLSNewByPrefichaFase(BigDecimal idPreficha, String fase) throws ApplicationException {
        PreFichaXlsMglNewManager manager = new PreFichaXlsMglNewManager();
        return manager.getListXLSNewByPrefichaFase(idPreficha, fase);
    }

    @Override
    public List<PreFichaXlsMglNew> getListadoPrefichasNewPorTab(BigDecimal idPreficha, String fase, String tab, CmtFiltroPrefichasDto filtros) throws ApplicationException {
        PreFichaXlsMglNewManager manager = new PreFichaXlsMglNewManager();
        return manager.getListadoPrefichasNewPorTab(idPreficha, fase, tab, filtros);
    }

    @Override
    public void acualizarPrefichaXlsNew(PreFichaGeorreferenciaMglNew preFichaGeoActual, PreFichaGeorreferenciaMglNew preFichaGeorreferenciaMglNueva, PreFichaXlsMglNew xls, boolean isNoProcesados) {
        PreFichaXlsMglNewManager manager = new PreFichaXlsMglNewManager();
        manager.acualizarPrefichaXlsNew(preFichaGeoActual, preFichaGeorreferenciaMglNueva, xls, isNoProcesados);
    }

    @Override
    public List<PreFichaTxtMglNew> getListadoPrefichasTxtNewPorTab(BigDecimal idPreficha) throws ApplicationException {
        PreFichaXlsMglNewManager manager = new PreFichaXlsMglNewManager();
        return manager.getListadoPrefichasTxtNewPorTab(idPreficha);
    }

    @Override
    public List<PreFichaXlsMglNew> getListadoXlsNewByFaseAndIdPfAndPestana(BigDecimal idPreficha, String fase, String tab) {
        PreFichaXlsMglNewManager manager = new PreFichaXlsMglNewManager();
        return manager.getListadoXlsNewByFaseAndIdPfAndPestana(idPreficha, fase, tab);
    }
    //GEO_NEW
    @Override
    public List<PreFichaGeorreferenciaMglNew> savePreFichaGeoNewList(List<PreFichaGeorreferenciaMglNew> prefichaGeoList) throws ApplicationException {
        PreFichaGeorreferenciaMglNewManager manager = new PreFichaGeorreferenciaMglNewManager();
        return manager.savePreFichaGeoNewList(prefichaGeoList);
    }

    @Override
    public PreFichaGeorreferenciaMglNew findPreFichaGeorreferenciaNewByIdPrefichaXls(BigDecimal id) throws ApplicationException {
        PreFichaGeorreferenciaMglNewManager manager = new PreFichaGeorreferenciaMglNewManager();
        return manager.findPreFichaGeorreferenciaNewByIdPrefichaXls(id);
    }
    
    @Override
    public List<ConsultaPrefichaXlsNewDto> getListadoPrefichaXlsNew(BigDecimal idPreficha) throws ApplicationException {
        PreFichaXlsMglManager manager = new PreFichaXlsMglManager();
        return manager.getListadoPrefichaXlsNew(idPreficha);
    }
    
    
    @Override
    public int savePrefichaXlsNewService(List<AddressServiceBatchXml> prefichaXlsNewList, String usuarioVT) throws ApplicationException {
        PreFichaXlsMglManager manager = new PreFichaXlsMglManager();
        return manager.savePrefichaXlsNewList(prefichaXlsNewList, usuarioVT);
    }
    
    @Override
    public int updatePrefichaGeo (BigDecimal idPreficha) throws ApplicationException {
        PreFichaXlsMglManager manager = new PreFichaXlsMglManager();
        return manager.updatePrefichaGeo(idPreficha);
    }
    
    //TEC_HAB_DET_NEW
    @Override
    public List<PreFichaHHPPDetalleMglNew> savePreFichaDetalleHHPPNewList(List<PreFichaHHPPDetalleMglNew> prefichaDetalleHHHPList) throws ApplicationException {
        PreFichaHHPPDetalleMglNewManager manager = new PreFichaHHPPDetalleMglNewManager();
        return manager.savePreFichaDetalleHHPPNewList(prefichaDetalleHHHPList);
    }

    @Override
    public PreFichaHHPPDetalleMglNew getPreFichaDetalleHHPPNewById(BigDecimal idHHppDetalle) throws ApplicationException {
        PreFichaHHPPDetalleMglNewManager manager = new PreFichaHHPPDetalleMglNewManager();
        return manager.getPreFichaDetalleHHPPNewById(idHHppDetalle);
    }

    @Override
    public List<PreFichaHHPPDetalleMglNew> obtenerDetallesHHPPNew(PreFichaXlsMglNew prefichaXls) throws ApplicationException {
        PreFichaHHPPDetalleMglNewManager manager = new PreFichaHHPPDetalleMglNewManager();
        return manager.obtenerDetallesHHPPNew(prefichaXls);
    }
    
    /**
     * Metodo para realizar el cambio de Apto
     *
     * @author Miguel Barrios
     * @param p Objeto prefichaXlsNew
     * @param hhpp HomePass Mgl    
     * @param usuarioVt Usuario que realiza la operacion
     * @param perfilVt Perfil del usuario que realiza la operacion
     * @return Indica si la operacion fue exitosa
     * @throws co.com.claro.mgl.error.ApplicationException
    */
    @Override
    public boolean cambioAptoDireccionFichas(PreFichaXlsMglNew p, HhppMgl hhpp, String usuarioVt,int perfilVt) throws ApplicationException {
        PreFichaXlsMglNewManager manager = new PreFichaXlsMglNewManager();
        return manager.cambioAptoDireccionFichas(p,hhpp,usuarioVt, perfilVt);
    }
    
    /**
     * Metodo para propagar transaccion creacion HHPP
     *
     * @author Miguel Barrios
     * @param direccionDetallada Direccion creada en Mgl
     * @param drDireccionFor Instancia de Drdireccion
     * @param usuario Usuario que ejecuta la transaccion
     * @param carpeta Carpeta de la solicitud
     * @param nvlSocioEconomico Estrato de la direccion
     * @param nodoMglHhpp Nodo de transaccion
     * @param amp Amp de la ficha
     * @return Direcion creada en MER-RR
     * @throws co.com.claro.mgl.error.ApplicationException
    */
    @Override
    public DireccionRREntity crearHHPPFichas(CmtDireccionDetalladaMgl direccionDetallada,DrDireccion drDireccionFor, String usuario, String carpeta, String nvlSocioEconomico, NodoMgl nodoMglHhpp, String amp) throws ApplicationException {
        PreFichaXlsMglNewManager manager = new PreFichaXlsMglNewManager();
        return manager.crearHHPPFichas(direccionDetallada,drDireccionFor,usuario,carpeta,nvlSocioEconomico,nodoMglHhpp, amp);
    }
}
