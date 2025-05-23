package co.com.claro.mgl.mbeans.direcciones;

import co.com.claro.mgl.dtos.CmtFiltroPrefichasDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.PreFichaMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.ExportFichasMgl;
import co.com.claro.mgl.jpa.entities.PreFichaHHPPDetalleMgl;
import co.com.claro.mgl.jpa.entities.PreFichaXlsMgl;
import co.com.claro.mgl.mbeans.util.PrimeFacesUtil;
import co.com.claro.mgl.utils.Constant;
import co.com.telmex.catastro.util.FacesUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;


@ManagedBean(name = "fichaDetalleHistoricoBean")
@ViewScoped
public class FichaDetalleHistoricoBean implements Serializable{
    
    private static final Logger LOGGER = LogManager.getLogger(FichaDetalleHistoricoBean.class);
    private List<PreFichaXlsMgl> edificiosVtHistorico;
    private List<PreFichaXlsMgl> casasRedExternaHistorico;
    private List<PreFichaXlsMgl> conjuntasCasasHistorico;
    private ExportFichasMgl historicoExportSeleccionado;
    private CmtFiltroPrefichasDto filtrosPreficha;
    private String blackListCCM1;
    private String blackListCCM2;
    private String blackListHHPP1;
    private String blackListHHPP2;
    
    @EJB
    private PreFichaMglFacadeLocal preFichaMglFacadeLocal;
    
    @PostConstruct
    public void init(){
        edificiosVtHistorico = new ArrayList<>();
        casasRedExternaHistorico = new ArrayList<>();
        conjuntasCasasHistorico = new ArrayList<>();
        historicoExportSeleccionado = new ExportFichasMgl();
        filtrosPreficha = new CmtFiltroPrefichasDto();
        cargarHistoricoFicha();
    }

    
    public void cargarHistoricoFicha(){
        try {
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            HttpSession session = (HttpSession) context.getSession(false);
            historicoExportSeleccionado = (ExportFichasMgl) session.getAttribute("historicoExportFicha");
            edificiosVtHistorico = preFichaMglFacadeLocal.getListadoPrefichasPorTab(getHistoricoExportSeleccionado().getPrefichaId().getPrfId(), getHistoricoExportSeleccionado().getPrefichaId().getFase(), Constant.EDIFICIOSVT, filtrosPreficha);
            casasRedExternaHistorico = preFichaMglFacadeLocal.getListadoPrefichasPorTab(getHistoricoExportSeleccionado().getPrefichaId().getPrfId(), getHistoricoExportSeleccionado().getPrefichaId().getFase(), Constant.CASAS_RED_EXTERNA, filtrosPreficha);
            conjuntasCasasHistorico = preFichaMglFacadeLocal.getListadoPrefichasPorTab(getHistoricoExportSeleccionado().getPrefichaId().getPrfId(), getHistoricoExportSeleccionado().getPrefichaId().getFase(), Constant.CONJUNTOCASASVT, filtrosPreficha);
            cargarDetallesHHPPRR(edificiosVtHistorico);
            cargarDetallesHHPPRR(casasRedExternaHistorico);
            cargarDetallesHHPPRR(conjuntasCasasHistorico);
            cargarBlackList();
            
            PrimeFacesUtil.update("form_detalle_historico");
        } catch (ApplicationException e) {
             FacesUtil.mostrarMensajeError("Error en cargarHistoricoFicha.", e, LOGGER);
        } catch (Exception e) {
             FacesUtil.mostrarMensajeError("Error en cargarHistoricoFicha.", e, LOGGER);
        }
    }
    
    public void cargarDetallesHHPPRR(List<PreFichaXlsMgl> allPrefichasXls) {
        try {
            if (allPrefichasXls != null && !allPrefichasXls.isEmpty()) {
                for (PreFichaXlsMgl prefichaXls : allPrefichasXls) {
                    List<PreFichaHHPPDetalleMgl> detalles = preFichaMglFacadeLocal.obtenerDetallesHHPP(prefichaXls);
                    if (detalles != null && !detalles.isEmpty()) {
                        List<String> detallesRR = new ArrayList<>();
                        List<String> detallesMGL = new ArrayList<>();
                        for (PreFichaHHPPDetalleMgl detalle : detalles) {
                            detallesRR.add("[" + detalle.getStreetName().trim() + "][" + detalle.getHouseNumber().trim() + "][" + detalle.getApartmentNumber().trim() + "]");
                            if(detalle.getDireccionMGL() != null && !detalle.getDireccionMGL().isEmpty()){
                                detallesMGL.add(detalle.getDireccionMGL());
                            }
                        }
                        prefichaXls.setHhppList(detallesRR);
                        prefichaXls.setDireccionStrList(detallesMGL);
                    }
                }
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en cargarDetallesHHPPRR.", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en cargarDetallesHHPPRR.", e, LOGGER);
        }

    }
    
    public void cargarBlackList(){
        if(historicoExportSeleccionado.getPrefichaId().getMarcas() != null && !historicoExportSeleccionado.getPrefichaId().getMarcas().isEmpty()){
            String[] blacklist = historicoExportSeleccionado.getPrefichaId().getMarcas().split("\\|");
            blackListCCM1 =  blacklist[0];   
            blackListCCM2 =  blacklist[1]; 
            blackListHHPP1 =  blacklist[2]; 
            blackListHHPP2 =  blacklist[3]; 
        }
    }
            
    public List<PreFichaXlsMgl> getEdificiosVtHistorico() {
        return edificiosVtHistorico;
    }

    public void setEdificiosVtHistorico(List<PreFichaXlsMgl> edificiosVtHistorico) {
        this.edificiosVtHistorico = edificiosVtHistorico;
    }

    public List<PreFichaXlsMgl> getCasasRedExternaHistorico() {
        return casasRedExternaHistorico;
    }

    public void setCasasRedExternaHistorico(List<PreFichaXlsMgl> casasRedExternaHistorico) {
        this.casasRedExternaHistorico = casasRedExternaHistorico;
    }

    public List<PreFichaXlsMgl> getConjuntasCasasHistorico() {
        return conjuntasCasasHistorico;
    }

    public void setConjuntasCasasHistorico(List<PreFichaXlsMgl> conjuntasCasasHistorico) {
        this.conjuntasCasasHistorico = conjuntasCasasHistorico;
    }

    public ExportFichasMgl getHistoricoExportSeleccionado() {
        return historicoExportSeleccionado;
    }

    public void setHistoricoExportSeleccionado(ExportFichasMgl historicoExportSeleccionado) {
        this.historicoExportSeleccionado = historicoExportSeleccionado;
    }

    public CmtFiltroPrefichasDto getFiltrosPreficha() {
        return filtrosPreficha;
    }

    public void setFiltrosPreficha(CmtFiltroPrefichasDto filtrosPreficha) {
        this.filtrosPreficha = filtrosPreficha;
    }

    public String getBlackListCCM1() {
        return blackListCCM1;
    }

    public void setBlackListCCM1(String blackListCCM1) {
        this.blackListCCM1 = blackListCCM1;
    }

    public String getBlackListCCM2() {
        return blackListCCM2;
    }

    public void setBlackListCCM2(String blackListCCM2) {
        this.blackListCCM2 = blackListCCM2;
    }

    public String getBlackListHHPP1() {
        return blackListHHPP1;
    }

    public void setBlackListHHPP1(String blackListHHPP1) {
        this.blackListHHPP1 = blackListHHPP1;
    }

    public String getBlackListHHPP2() {
        return blackListHHPP2;
    }

    public void setBlackListHHPP2(String blackListHHPP2) {
        this.blackListHHPP2 = blackListHHPP2;
    }
}
