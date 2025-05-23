/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.direcciones;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.AreaDirFacadeLocal;
import co.com.claro.mgl.facade.IDivisionalFacadeLocal;
import co.com.claro.mgl.facade.MultivalorFacadeLocal;
import co.com.claro.mgl.facade.NodoMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.AreaDir;
import co.com.claro.mgl.jpa.entities.Divisional;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.telmex.catastro.services.util.EnvioCorreo;
import co.com.telmex.catastro.util.FacesUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;


/**
 *
 * @author User
 */
@ManagedBean(name = "vetoNodoBean")
@ViewScoped
public class VetoNodoBean {
    
    private static final Logger LOGGER = LogManager.getLogger(VetoNodoBean.class);

    private String numPolitica;
    private String divisional;
    public List<Divisional> listDivisional = null;
    private String area;
    public List<AreaDir> listArea = null;
    @EJB
    MultivalorFacadeLocal multivalorFacadeLocal;
    @EJB
    IDivisionalFacadeLocal divisionalFacadeLocal;
    @EJB
    AreaDirFacadeLocal areaDirFacadeLocal;
    @EJB
    NodoMglFacadeLocal nodoMglFacadeLocal;
    private Date initDate;
    private Date endDate;
    private HtmlDataTable comunidadesTable = new HtmlDataTable();
    private List<GeograficoPoliticoMgl> comunidadesVetoList;
    private String rederUNI;
    private String rederBI;
    private String rederDTH;
    private String idCity;
    private HtmlDataTable nodosTable = new HtmlDataTable();
    private List<NodoMgl> nodoComunidadVetoList;
    private String correoAlerta;
    private boolean selectedAllCities = false;
    private boolean selectedAllNodes = false;
    
    public VetoNodoBean() {
    }

    @PostConstruct
    public void fillData() {
        try {
            //Cargamos las Divisionales
            listDivisional = new ArrayList<Divisional>();
            listDivisional = divisionalFacadeLocal.findAll();

        } catch (ApplicationException e) {
            LOGGER.error("error fillData" + e.getMessage());
        } catch (Exception e) {
            LOGGER.error("error fillData" + e.getMessage());
        }

    }

    public String getNumPolitica() {
        return numPolitica;
    }

    public void setNumPolitica(String numPolitica) {
        this.numPolitica = numPolitica;
    }

    public Date getInitDate() {
        return initDate;
    }

    public void setInitDate(Date initDate) {
        this.initDate = initDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDivisional() {
        return divisional;
    }

    public void setDivisional(String divisional) {
        this.divisional = divisional;
    }

    public List<Divisional> getListDivisional() {
        return listDivisional;
    }

    public void setListDivisional(List<Divisional> listDivisional) {
        this.listDivisional = listDivisional;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public List<AreaDir> getListArea() {
        return listArea;
    }

    public void setListArea(List<AreaDir> listArea) {
        this.listArea = listArea;
    }

    public HtmlDataTable getComunidadesTable() {
        return comunidadesTable;
    }

    public void setComunidadesTable(HtmlDataTable comunidadesTable) {
        this.comunidadesTable = comunidadesTable;
    }

    public List<GeograficoPoliticoMgl> getComunidadesVetoList() {
        return comunidadesVetoList;
    }

    public void setComunidadesVetoList(List<GeograficoPoliticoMgl> comunidadesVetoList) {
        this.comunidadesVetoList = comunidadesVetoList;
    }

    public String getRederUNI() {
        return rederUNI;
    }

    public void setRederUNI(String rederUNI) {
        this.rederUNI = rederUNI;
    }

    public String getRederBI() {
        return rederBI;
    }

    public void setRederBI(String rederBI) {
        this.rederBI = rederBI;
    }

    public String getRederDTH() {
        return rederDTH;
    }

    public void setRederDTH(String rederDTH) {
        this.rederDTH = rederDTH;
    }

    public String getIdCity() {
        return idCity;
    }

    public void setIdCity(String idCity) {
        this.idCity = idCity;
    }

    public HtmlDataTable getNodosTable() {
        return nodosTable;
    }

    public void setNodosTable(HtmlDataTable nodosTable) {
        this.nodosTable = nodosTable;
    }

    public List<NodoMgl> getNodoComunidadVetoList() {
        return nodoComunidadVetoList;
    }

    public void setNodoComunidadVetoList(List<NodoMgl> nodoComunidadVetoList) {
        this.nodoComunidadVetoList = nodoComunidadVetoList;
    }

    public String getCorreoAlerta() {
        return correoAlerta;
    }

    public void setCorreoAlerta(String correoAlerta) {
        this.correoAlerta = correoAlerta;
    }
    
    public String mostrarComunidades() {
        try {
            BigDecimal areaId = new BigDecimal(area);
            BigDecimal divisionalId = new BigDecimal(divisional);
            comunidadesVetoList = new ArrayList<GeograficoPoliticoMgl>();
            comunidadesVetoList = nodoMglFacadeLocal.getCitiesNodoByDivArea(divisionalId, areaId);

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en mostrarComunidades. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en mostrarComunidades. ", e, LOGGER);
        }
        return null;
    }

    public String vetarComunidad() {
        try {
            List<GeograficoPoliticoMgl> gpList = new ArrayList<GeograficoPoliticoMgl>();
            for (GeograficoPoliticoMgl gp : comunidadesVetoList) {
                if (gp.isSelected()) {
                    gpList.add(gp);
                }
            }
            if (gpList.size() > 0) {
                
                List<NodoMgl> nodoVetoList = nodoMglFacadeLocal.findNodosByCitytipos(gpList, 
                                                                            new BigDecimal(divisional), 
                                                                            new BigDecimal(area));
                if (nodoVetoList != null && !nodoVetoList.isEmpty() ){
                    nodoMglFacadeLocal.vetoNodoByCitiesDivArea(gpList,
                        new BigDecimal(divisional), new BigDecimal(area),
                        initDate, endDate,
                        numPolitica, correoAlerta);
                    enviaCorreo();
                }else{
                    //No existen nodos con el filtro seleccionado
                    return null;
                }
               
            } else {
                //No se seleccionaron ciudades
                return null;
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en vetarComunidad: " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en vetarComunidad: " + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public String vetarNodos() {
        try {
            List<NodoMgl> listProcesar = new ArrayList<NodoMgl>();

            for (NodoMgl nd : nodoComunidadVetoList) {
                if (nd.isSelected()) {
                    listProcesar.add(nd);
                }
            }

            if (!listProcesar.isEmpty()) {
                nodoMglFacadeLocal.vetoNodosByCityDivAre(listProcesar,
                        new BigDecimal(divisional), new BigDecimal(area),
                        initDate, endDate, numPolitica, correoAlerta);
            }
            enviaCorreo();
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en vetarNodos. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en vetarNodos. ", e, LOGGER);
        }
        return null;
    }

    public String mostarNodosComunidad() {
        try {
            GeograficoPoliticoMgl cityNodo = (GeograficoPoliticoMgl) comunidadesTable.getRowData();
            List<String> tipoNodoList = new ArrayList<String>();
            if (!cityNodo.isSelectedUNI() && !cityNodo.isSelectedBI() && !cityNodo.isSelectedDTH()) {
                tipoNodoList.add("UNI");
                tipoNodoList.add("BI");
                tipoNodoList.add("DTH");
            } else {
                if (cityNodo.isSelectedUNI()) {
                    tipoNodoList.add("UNI");
                }
                if (cityNodo.isSelectedBI()) {
                    tipoNodoList.add("BI");
                }
                if (cityNodo.isSelectedDTH()) {
                    tipoNodoList.add("DTH");
                }
            }
            List<BigDecimal> comunidades = new ArrayList<BigDecimal>();
            comunidades.add(cityNodo.getGpoId());
            nodoComunidadVetoList = new ArrayList<NodoMgl>();
            nodoComunidadVetoList = nodoMglFacadeLocal.findNodosByComDivArea(comunidades,
                    new BigDecimal(divisional), new BigDecimal(area), tipoNodoList);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en mostarNodosComunidad. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en mostarNodosComunidad. ", e, LOGGER);
        }
        return null;
    }

    public void listDivisionalChange() {
        try {
            LOGGER.error("divisional:" + divisional);
            listArea = areaDirFacadeLocal.findByIdDivisional(new BigDecimal(divisional));
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en listDivisionalChange. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en listDivisionalChange. ", e, LOGGER);
        }

    }

    public void listAreaChange() {
        try {
            LOGGER.error("divisional:" + divisional);
            LOGGER.error("area:" + area);
        } catch (RuntimeException e) {
            FacesUtil.mostrarMensajeError("Error en listAreaChange. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en listAreaChange. ", e, LOGGER);
        }

    }

    private void enviaCorreo() throws ApplicationException {
        EnvioCorreo envioCorreo = new EnvioCorreo();
        envioCorreo.envio(correoAlerta, "Veto nodo", "se veta nodo");
    }

    public void endDateValidator(FacesContext facesContext, UIComponent uiComponent, Object value) {

        LOGGER.error("Validador Fecha: --endDate:-- " + value.toString() + " --initDate:--" + initDate.toString());
    }

    public void selectAllCities() {        
        selectedAllCities = !selectedAllCities;
        for (GeograficoPoliticoMgl gp : comunidadesVetoList) {
            gp.setSelected(selectedAllCities);
        }
    }
    public void selectAllNodes() {        
        selectedAllNodes = !selectedAllNodes;
        for (NodoMgl nd : nodoComunidadVetoList) {
            nd.setSelected(selectedAllNodes);
        }
    }
}
