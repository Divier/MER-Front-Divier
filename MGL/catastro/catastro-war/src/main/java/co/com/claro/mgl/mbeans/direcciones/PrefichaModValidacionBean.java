/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.direcciones;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.PreFichaMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.PreFichaMgl;
import co.com.claro.mgl.jpa.entities.PreFichaXlsMgl;
import co.com.telmex.catastro.util.FacesUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;

/**
 *
 * @author User
 */
@ManagedBean(name = "modValidacionPrefichaBean")
@ViewScoped
public class PrefichaModValidacionBean {

    private static final Logger LOGGER = LogManager.getLogger(PrefichaModValidacionBean.class);
    private static final String FASE_MODIFICACION = "MODIFICACION";
    private static final String FASE_VALIDACION = "VALIDACION";
    private HtmlDataTable dataTable = new HtmlDataTable();
    private BigDecimal idPrefichaSelected;
    private List<PreFichaMgl> preFichaModValidacionList;
    private List<PreFichaXlsMgl> edificiosVtXls;
    private List<PreFichaXlsMgl> casasRedExternaXls;
    private List<PreFichaXlsMgl> conjCasasVtXls;
    private List<PreFichaXlsMgl> hhppSNXls;
    private List<PreFichaXlsMgl> IngresoNuevoNXls;
    private List<PreFichaXlsMgl> preFichaXlsMglList;
    private PreFichaMgl prefichaValidar;
    private String saveAvailable = String.valueOf(false);
    private String labelHeaderTableLists = "Información Cargada en la Validación Comercial";
    @EJB
    PreFichaMglFacadeLocal preFichaMglFacadeLocal;

    public static enum CLASIFICACION_NAME {

        EDIFICIOSVT, CASASREDEXTERNA, CONJUNTOCASASVT, HHPPSN, INGRESONUEVO
    }

    public PrefichaModValidacionBean() {
    }

    @PostConstruct
    public void loadList() {
        try {
            preFichaModValidacionList = new ArrayList<PreFichaMgl>();
            //se cargan las Preficha que hayan sido validadas por el area comercial
            List<String> faseList = new ArrayList<String>();
            faseList.add(FASE_VALIDACION);
            preFichaModValidacionList = preFichaMglFacadeLocal.getListPrefichaByFase(faseList,0,0,false);
        } catch (ApplicationException e) {
            LOGGER.error("Error en loadList. " + e.getMessage());
        } catch (Exception e) {
            LOGGER.error("Error en loadList. " + e.getMessage());
        }
    }

    public void cargarDetallePreficha() {
        try {
            prefichaValidar = (PreFichaMgl) dataTable.getRowData();
            LOGGER.error("preficha seleccionada: " + prefichaValidar.getPrfId());
            LOGGER.error("preficha idPrefichaSelected: " + idPrefichaSelected);
            preFichaXlsMglList = preFichaMglFacadeLocal.getListXLSByPrefichaFase(idPrefichaSelected, FASE_VALIDACION);

            edificiosVtXls = new ArrayList<PreFichaXlsMgl>();
            casasRedExternaXls = new ArrayList<PreFichaXlsMgl>();
            conjCasasVtXls = new ArrayList<PreFichaXlsMgl>();
            hhppSNXls = new ArrayList<PreFichaXlsMgl>();
            IngresoNuevoNXls = new ArrayList<PreFichaXlsMgl>();

            for (PreFichaXlsMgl p : preFichaXlsMglList) {
                CLASIFICACION_NAME clasificacion = CLASIFICACION_NAME.valueOf(p.getClasificacion());
                switch (clasificacion) {
                    case EDIFICIOSVT:
                        edificiosVtXls.add(p);
                        break;
                    case CASASREDEXTERNA:
                        casasRedExternaXls.add(p);
                        break;
                    case CONJUNTOCASASVT:
                        conjCasasVtXls.add(p);
                        break;
                    case HHPPSN:
                        hhppSNXls.add(p);
                        break;
                    case INGRESONUEVO:
                        IngresoNuevoNXls.add(p);
                        break;
                }

            }

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en cargarDetallePreficha. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en cargarDetallePreficha. ", e, LOGGER);
        }
    }

    public void guardarPreficha() {
        try {
            //cambiamos de estado la preficha
            prefichaValidar.setPrfFechaModifica(new Date());
            prefichaValidar.setPrfUsuarioModifica("HITSS");
            prefichaValidar.setFase(FASE_MODIFICACION);
            
            prefichaValidar = preFichaMglFacadeLocal.updatePreFicha(prefichaValidar);
            
            if (preFichaXlsMglList != null && preFichaXlsMglList.size() > 0) {
                preFichaXlsMglList = new ArrayList<PreFichaXlsMgl>();
                if (edificiosVtXls != null && edificiosVtXls.size() > 0) {
                    for (PreFichaXlsMgl p : edificiosVtXls) {
                        p.setFase(FASE_MODIFICACION);
                        p.setIdPfx(p.getId());
                        p.setId(null);
                        preFichaXlsMglList.add(p);
                    }
                }
                if (casasRedExternaXls != null && casasRedExternaXls.size() > 0) {
                    for (PreFichaXlsMgl p : casasRedExternaXls) {
                        p.setFase(FASE_MODIFICACION);
                        p.setIdPfx(p.getId());
                        p.setId(null);
                        preFichaXlsMglList.add(p);
                    }
                }
                if (conjCasasVtXls != null && conjCasasVtXls.size() > 0) {
                    for (PreFichaXlsMgl p : conjCasasVtXls) {
                        p.setFase(FASE_MODIFICACION);
                        p.setIdPfx(p.getId());
                        p.setId(null);
                        preFichaXlsMglList.add(p);
                    }
                }
                if (hhppSNXls != null && hhppSNXls.size() > 0) {
                    for (PreFichaXlsMgl p : hhppSNXls) {
                        p.setFase(FASE_MODIFICACION);
                        p.setIdPfx(p.getId());
                        p.setId(null);
                        preFichaXlsMglList.add(p);
                    }
                }
                if (IngresoNuevoNXls != null && IngresoNuevoNXls.size() > 0) {
                    for (PreFichaXlsMgl p : IngresoNuevoNXls) {
                        p.setFase(FASE_MODIFICACION);
                        p.setIdPfx(p.getId());
                        p.setId(null);
                        preFichaXlsMglList.add(p);
                    }
                }
                
            }
            preFichaXlsMglList = preFichaMglFacadeLocal.savePrefichaXlsList(prefichaValidar, preFichaXlsMglList);
            if (preFichaXlsMglList != null && preFichaXlsMglList.size() > 0 && prefichaValidar != null) {
                idPrefichaSelected = null;
                prefichaValidar = null;
                preFichaXlsMglList = null;
                saveAvailable = String.valueOf(false);
                String msn = "La informacion de la preficha ha sido cargada existosamente";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en guardarPreficha. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en guardarPreficha. ", e, LOGGER);
        }



    }

    public List<PreFichaMgl> getPreFichaModValidacionList() {
        return preFichaModValidacionList;
    }

    public void setPreFichaModValidacionList(List<PreFichaMgl> preFichaModValidacionList) {
        this.preFichaModValidacionList = preFichaModValidacionList;
    }

    public HtmlDataTable getDataTable() {
        return dataTable;
    }

    public void setDataTable(HtmlDataTable dataTable) {
        this.dataTable = dataTable;
    }

    public BigDecimal getIdPrefichaSelected() {
        return idPrefichaSelected;
    }

    public void setIdPrefichaSelected(BigDecimal idPrefichaSelected) {
        this.idPrefichaSelected = idPrefichaSelected;
    }

    public List<PreFichaXlsMgl> getEdificiosVtXls() {
        return edificiosVtXls;
    }

    public void setEdificiosVtXls(List<PreFichaXlsMgl> edificiosVtXls) {
        this.edificiosVtXls = edificiosVtXls;
    }

    public List<PreFichaXlsMgl> getCasasRedExternaXls() {
        return casasRedExternaXls;
    }

    public void setCasasRedExternaXls(List<PreFichaXlsMgl> casasRedExternaXls) {
        this.casasRedExternaXls = casasRedExternaXls;
    }

    public List<PreFichaXlsMgl> getConjCasasVtXls() {
        return conjCasasVtXls;
    }

    public void setConjCasasVtXls(List<PreFichaXlsMgl> conjCasasVtXls) {
        this.conjCasasVtXls = conjCasasVtXls;
    }

    public List<PreFichaXlsMgl> getHhppSNXls() {
        return hhppSNXls;
    }

    public void setHhppSNXls(List<PreFichaXlsMgl> hhppSNXls) {
        this.hhppSNXls = hhppSNXls;
    }

    public List<PreFichaXlsMgl> getIngresoNuevoNXls() {
        return IngresoNuevoNXls;
    }

    public void setIngresoNuevoNXls(List<PreFichaXlsMgl> IngresoNuevoNXls) {
        this.IngresoNuevoNXls = IngresoNuevoNXls;
    }

    public List<PreFichaXlsMgl> getPreFichaXlsMglList() {
        return preFichaXlsMglList;
    }

    public void setPreFichaXlsMglList(List<PreFichaXlsMgl> preFichaXlsMglList) {
        this.preFichaXlsMglList = preFichaXlsMglList;
    }

    public String getSaveAvailable() {
        return saveAvailable;
    }

    public void setSaveAvailable(String saveAvailable) {
        this.saveAvailable = saveAvailable;
    }

    public String getLabelHeaderTableLists() {
        return labelHeaderTableLists;
    }

    public void setLabelHeaderTableLists(String labelHeaderTableLists) {
        this.labelHeaderTableLists = labelHeaderTableLists;
    }
}
