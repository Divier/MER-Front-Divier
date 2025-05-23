/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.facade;

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
public interface PreFichaMglFacadeLocal {

    
    List<PreFichaTxtMgl> savePrefichaTxtList(PreFichaMgl preFichaMgl, List<PreFichaTxtMgl> prefichaTxtList) throws ApplicationException;
    List<PreFichaXlsMgl> savePrefichaXlsList(PreFichaMgl preFichaMgl, List<PreFichaXlsMgl> prefichaXlsList) throws ApplicationException;
    List<PreFichaGeorreferenciaMgl> savePreFichaGeoList(List<PreFichaGeorreferenciaMgl> prefichaGeoList) throws ApplicationException;
    List<PreFichaHHPPDetalleMgl> savePreFichaDetalleHHPPList(List<PreFichaHHPPDetalleMgl> prefichaDetalleHHHPList) throws ApplicationException;
    PreFichaMgl savePreficha(PreFichaMgl preFichaMgl) throws ApplicationException;
    List<PreFichaMgl> getListPrefichaByFase (List<String> faseList,int firstResult,
            int maxResults, boolean paginar) throws ApplicationException;
    List<PreFichaMgl> getListPrefichaByFaseAndDate(List<String> faseList,int firstResult,
            int maxResults, boolean paginar, Date fechaInicial, Date fechaFinal) throws ApplicationException;      
    List<PreFichaMgl> getListPrefichaGeoFase () throws ApplicationException;
    List<PreFichaXlsMgl> getListXLSByPrefichaFase(BigDecimal idPreficha, String fase) throws ApplicationException;
    PreFichaMgl updatePreFicha(PreFichaMgl preFichaMgl) throws ApplicationException;
    boolean deletePreFicha(PreFichaMgl preFichaMgl) throws ApplicationException;
    PreFichaMgl getPreFichaById(BigDecimal idPreficha) throws ApplicationException;
    PreFichaHHPPDetalleMgl getPreFichaDetalleHHPPById(BigDecimal idHHppDetalle) throws ApplicationException;
    List<PreFichaMgl> getListFichaToCreate(int firstResult,
            int maxResults, boolean paginar) throws ApplicationException;
    public List<PreFichaMgl> getListFichaToCreateByDate(int page, int PAGINACION_DIEZ_FILAS, boolean b, Date fechaInicial, Date fechaFinal)  throws ApplicationException;
    List<PreFichaMgl> getListFichaToValidate() throws ApplicationException;
    PreFichaAlertaMgl savePreFichaAlerta(PreFichaAlertaMgl preFichaAlertaMgl) throws ApplicationException;
    void acualizarPrefichaXls(PreFichaGeorreferenciaMgl preFichaGeoActual,PreFichaGeorreferenciaMgl preFichaGeorreferenciaMglNueva,PreFichaXlsMgl xls, boolean isNoProcesados);
    PreFichaGeorreferenciaMgl findPreFichaGeorreferenciaByIdPrefichaXls(BigDecimal id)throws ApplicationException;
    List<PreFichaXlsMgl> getListadoPrefichasPorTab(BigDecimal idPreficha, String fase, String tab ,CmtFiltroPrefichasDto filtros)throws ApplicationException;
    List<PreFichaTxtMgl> getListadoPrefichasTxtPorTab(BigDecimal idPreficha)throws ApplicationException;
    List<PreFichaHHPPDetalleMgl> obtenerDetallesHHPP(PreFichaXlsMgl prefichaXls) throws ApplicationException;
    List<PreFichaXlsMgl> getListadoByFaseAndIdPfAndPestana(BigDecimal idPreficha, String fase, String tab);
    //TEC_PREFICHA_NEW
    PreFichaMglNew savePrefichaNew(PreFichaMglNew preFichaMgl) throws ApplicationException;
    List<PreFichaMglNew> getListPrefichaNewByFase (List<String> faseList,int firstResult,
            int maxResults, boolean paginar) throws ApplicationException;
    List<PreFichaMglNew> getListPrefichaNewByFaseAndDate(List<String> faseList,int firstResult,
            int maxResults, boolean paginar, Date fechaInicial, Date fechaFinal) throws ApplicationException;      
    List<PreFichaMglNew> getListPrefichaNewGeoFase () throws ApplicationException;
    PreFichaMglNew updatePreFichaNew(PreFichaMglNew preFichaMgl) throws ApplicationException;
    boolean deletePreFichaNew(PreFichaMglNew preFichaMgl) throws ApplicationException;
    PreFichaMglNew getPreFichaNewById(BigDecimal idPreficha) throws ApplicationException;
    List<PreFichaMglNew> getListFichaNewToCreate(int firstResult,
            int maxResults, boolean paginar) throws ApplicationException;
    public List<PreFichaMglNew> getListFichaNewToCreateByDate(int page, int PAGINACION_DIEZ_FILAS, boolean b, Date fechaInicial, Date fechaFinal)  throws ApplicationException;
    List<PreFichaMglNew> getListFichaNewToValidate() throws ApplicationException;
    //TEC_CARGUE_PREFICHAS: TXT
    List<PreFichaTxtMglNew> savePrefichaTxtNewList(PreFichaMglNew preFichaMgl, List<PreFichaTxtMglNew> prefichaTxtList) throws ApplicationException;    
    //TEC_PREFICHA_XLS_NEW
    List<PreFichaXlsMglNew> savePrefichaXlsNewList(PreFichaMglNew preFichaMgl, List<PreFichaXlsMglNew> prefichaXlsList) throws ApplicationException;
    List<PreFichaXlsMglNew> getListXLSNewByPrefichaFase(BigDecimal idPreficha, String fase) throws ApplicationException;
    List<PreFichaXlsMglNew> getListadoPrefichasNewPorTab(BigDecimal idPreficha, String fase, String tab ,CmtFiltroPrefichasDto filtros)throws ApplicationException;
    void acualizarPrefichaXlsNew(PreFichaGeorreferenciaMglNew preFichaGeoActual,PreFichaGeorreferenciaMglNew preFichaGeorreferenciaMglNueva,PreFichaXlsMglNew xls, boolean isNoProcesados);    
    List<PreFichaTxtMglNew> getListadoPrefichasTxtNewPorTab(BigDecimal idPreficha)throws ApplicationException;    
    List<PreFichaXlsMglNew> getListadoXlsNewByFaseAndIdPfAndPestana(BigDecimal idPreficha, String fase, String tab);
    //TEC_PREFIHA_GEORREFERENCIA_NEW
    List<PreFichaGeorreferenciaMglNew> savePreFichaGeoNewList(List<PreFichaGeorreferenciaMglNew> prefichaGeoList) throws ApplicationException;
    PreFichaGeorreferenciaMglNew findPreFichaGeorreferenciaNewByIdPrefichaXls(BigDecimal id)throws ApplicationException;
    
    List<ConsultaPrefichaXlsNewDto> getListadoPrefichaXlsNew (BigDecimal idPreficha) throws ApplicationException;
    int savePrefichaXlsNewService (List<AddressServiceBatchXml> addressService, String usuarioVT) throws ApplicationException;
    int updatePrefichaGeo (BigDecimal idPreficha) throws ApplicationException;
    
    //TEC_PREF_TEC_HAB_NEW
    List<PreFichaHHPPDetalleMglNew> savePreFichaDetalleHHPPNewList(List<PreFichaHHPPDetalleMglNew> prefichaDetalleHHHPList) throws ApplicationException;
    PreFichaHHPPDetalleMglNew getPreFichaDetalleHHPPNewById(BigDecimal idHHppDetalle) throws ApplicationException;
    List<PreFichaHHPPDetalleMglNew> obtenerDetallesHHPPNew(PreFichaXlsMglNew prefichaXls) throws ApplicationException;       
    /**
     * Metodo para realizar el cambio de Apto
     *
     * @author Miguel Barrios
     * @param p Objeto prefichaXls
     * @param hhpp HomePass Mgl     
     * @param usuarioVt Usuario que realiza la operacion
     * @param perfilVt Perfil del usuario que realiza la operacion
     * @return Indica si la operacion fue exitosa
     * @throws co.com.claro.mgl.error.ApplicationException
    */
    boolean cambioAptoDireccionFichas(PreFichaXlsMglNew p, HhppMgl hhpp, String usuarioVt, int perfilVt)throws ApplicationException;
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
    DireccionRREntity crearHHPPFichas(CmtDireccionDetalladaMgl direccionDetallada,DrDireccion drDireccionFor, String usuario, String carpeta, String nvlSocioEconomico, NodoMgl nodoMglHhpp, String amp) throws ApplicationException;
}
