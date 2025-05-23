/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.DireccionMglFacadeLocal;
import co.com.claro.mgl.facade.GeograficoMglFacadeLocal;
import co.com.claro.mgl.facade.GeograficoPoliticoMglFacadeLocal;
import co.com.claro.mgl.facade.RrCiudadesFacadeLocal;
import co.com.claro.mgl.facade.RrRegionalesFacadeLocal;
import co.com.claro.mgl.facade.UbicacionMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtComponenteDireccionesMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtCuentaMatrizMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtHorarioRestriccionMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.ConfigurationAddressComponent;
import co.com.claro.mgl.jpa.entities.DireccionMgl;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.RrCiudades;
import co.com.claro.mgl.jpa.entities.UbicacionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtHorarioRestriccionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtRegionalRr;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.telmex.catastro.data.AddressRequest;
import co.com.telmex.catastro.data.AddressService;
import co.com.telmex.catastro.data.AddressSuggested;
import co.com.telmex.catastro.services.georeferencia.AddressEJBRemote;
import co.com.telmex.catastro.services.util.Constant;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Parzifal de León
 */
@ManagedBean
@ViewScoped
public class CuentasMatricesBean {

    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpSession session = (HttpSession) facesContext
            .getExternalContext().getSession(false);
    private String usuarioVT = null;
    private String usuarioID = null;
    public String ctaSuscriptor;
    public String serialEquipo;
    private String rrRegional;
    private String rrCiudad;
    public BigDecimal departamento = null;
    public BigDecimal ciudad = null;
    private String cityName = null;
    private String dpto = null;
    private String centroPoblado = null;
    public List<CmtCuentaMatrizMgl> cuentaMatrizList;
    public List<GeograficoPoliticoMgl> departamentoList;
    public List<GeograficoPoliticoMgl> ciudadList;
    public List<String> barrioList = new ArrayList<String>();
    private List<CmtRegionalRr> regionalList;
    private List<RrCiudades> ciudadesList;
    private ArrayList<String> listBarrios = new ArrayList<String>();
    private ArrayList<CmtHorarioRestriccionMgl> horarioRestriccion;
    private Map<String, Object> params = new HashMap<String, Object>();
    private static final Logger LOGGER = LogManager.getLogger(CuentasMatricesBean.class);
    boolean validado = false;
    public CmtCuentaMatrizMgl cuentaMatriz = new CmtCuentaMatrizMgl();
    private DrDireccion drDireccion = new DrDireccion();
    private ConfigurationAddressComponent configurationAddressComponent;
    /**
     * Paginacion Tabla
     */
    private HtmlDataTable dataTable = new HtmlDataTable();
    private String pageActual;
    private String numPagina = "1";
    private List<Integer> paginaList;
    private boolean verHorario = false;
    private HttpServletResponse response = (HttpServletResponse) facesContext
            .getExternalContext().getResponse();
    @EJB
    private RrCiudadesFacadeLocal rrCiudadesFacade; 
    @EJB
    private RrRegionalesFacadeLocal rrRegionalesFacade;
    @EJB
    private CmtComponenteDireccionesMglFacadeLocal cmtComponenteDireccionesMglFacadeLocal;
    @EJB
    private CmtHorarioRestriccionMglFacadeLocal horarioRestriccionFacadeLocal;
    @EJB
    private GeograficoPoliticoMglFacadeLocal geograficoPoliticoMglFacadeLocal;
    @EJB
    private DireccionMglFacadeLocal direccionMglFacadeLocal;
    @EJB
    private UbicacionMglFacadeLocal ubicacionMglFacadeLocal;
    @EJB
    private GeograficoMglFacadeLocal geograficoMglFacadeLocal;
    @EJB
    private CmtCuentaMatrizMglFacadeLocal cuentaMatrizFacade;
    @EJB
    private AddressEJBRemote addressEJBRemote;
    @EJB
    private RrRegionalesFacadeLocal rrRegionalesFacadeLocal;

    @PostConstruct
    public void init() {
        try {
            fillData();
            consultarHorarioRestriccion();
        
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError(CuentasMatricesBean.class.getName() + e.getMessage(), e, LOGGER);
        }
        ApplicationBean applicationBean = (ApplicationBean) JSFUtil
                .getBean(ConstantsCM.APPLICATION_MANAGEDBEAN);
        configurationAddressComponent = applicationBean
                .getConfigurationAddressComponent();
    }

    /**
     *
     */
    public CuentasMatricesBean() {
        try {
            SecurityLogin securityLogin = new SecurityLogin(facesContext);
             if (!securityLogin.isLogin()) {
                if (!response.isCommitted()) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                }
                return;
            }
            if (usuarioVT == null) {
                usuarioVT = (String) session.getAttribute("usuarioDir");
            } else {

            }
            usuarioID = securityLogin.getIdUser();
            if (usuarioID == null) {
                session.getAttribute("usuarioIDM");
                usuarioID = (String) session.getAttribute("usuarioIDM");
            } else {
                session = (HttpSession) facesContext
                        .getExternalContext().getSession(false);
                session.setAttribute("usuarioIDM", usuarioID);
            }

        } catch (IOException e) {
            FacesUtil.mostrarMensajeError(CuentasMatricesBean.class.getName() + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError(CuentasMatricesBean.class.getName() + e.getMessage(), e, LOGGER);
        }
    }

    public void fillData()  {
        try {
            regionalList = rrRegionalesFacade.findRegionales();
            departamentoList = geograficoPoliticoMglFacadeLocal.findDptos();
            cuentaMatriz.setNumeroCuenta(BigDecimal.ZERO);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError(CuentasMatricesBean.class.getName() + e.getMessage(), e, LOGGER);
        }
    }

    public void consultarCiudades()  {
        try {
            ciudadList = geograficoPoliticoMglFacadeLocal.findCiudades(departamento);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError(CuentasMatricesBean.class.getName() + e.getMessage(), e, LOGGER);
        }
    }

    public String goActualizar(int numTab) {
        try {
            cuentaMatriz = (CmtCuentaMatrizMgl) dataTable.getRowData();
            session.setAttribute("idCuentaMatrizId", cuentaMatriz);

        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
            return "cuentas-matrices";
        }
        return "cuentas-matrices"; 
    }

    public void validarDireccion() {
        try {
            
            String codigoDaneCentroPoblado = "";
            for (GeograficoPoliticoMgl gp : departamentoList) {
                if (gp.getGpoId().equals(departamento)) {
                    dpto = gp.getGpoNombre();
                    break;
                }
            }
            for (GeograficoPoliticoMgl gp : ciudadList) {
                if (gp.getGpoId().equals(ciudad)) {
                    cityName = gp.getGpoNombre();
                    if(gp.getGpoTipo().equalsIgnoreCase("CENTRO POBLADO")){
                    codigoDaneCentroPoblado = gp.getGeoCodigoDane();
                    }
                    break;
                }
            }

            AddressService responseSrv = new AddressService();
            AddressRequest requestSrv = new AddressRequest();

            //Asignar la ciudad
            requestSrv.setCity(cityName);
            //Asignar el departamento
            requestSrv.setState(dpto);
            
            //Asignar el barrio

            requestSrv.setLevel(Constant.TIPO_CONSULTA_COMPLETA);
            
            if(codigoDaneCentroPoblado != null && !codigoDaneCentroPoblado.isEmpty()){
                requestSrv.setCodDaneVt(codigoDaneCentroPoblado);
            }

            responseSrv = addressEJBRemote.queryAddressEnrich(requestSrv);

            barrioList.clear();
            String brStr = responseSrv.getNeighborhood().trim();


            if (brStr != null && !brStr.isEmpty()) {
                barrioList.add(brStr.trim());
            }
            List<AddressSuggested> barrios = responseSrv.getAddressSuggested();
            if (barrios != null && barrios.size() > 0) {
                for (AddressSuggested a : barrios) {
                    if (responseSrv.getAddress().equalsIgnoreCase(a.getAddress())
                            && !a.getNeighborhood().trim().equalsIgnoreCase(brStr)) {
                        barrioList.add(a.getNeighborhood());
                    }
                }
            }

            validado = true;



            if (barrioList.isEmpty()) {
                String msn = "No se encontraron barrios";
                FacesContext.getCurrentInstance()
                        .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return;
            }

            String msn = "Proceso exitoso";
            FacesContext.getCurrentInstance()
                    .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
        } catch (ApplicationException e) {
            String msn = "Proceso falló";
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError(CuentasMatricesBean.class.getName() + e.getMessage(), e, LOGGER);
        }
    }

    public void buscar() {
        try {
            construirConsulta();
            List<CmtCuentaMatrizMgl> cmtList = consultarCuentasMatrices();

            cuentaMatrizList = new ArrayList<CmtCuentaMatrizMgl>();
            for (CmtCuentaMatrizMgl cm : cmtList) {
                DireccionMgl direccionMgl = new DireccionMgl();
                direccionMgl = direccionMglFacadeLocal.findById(direccionMgl);

                UbicacionMgl ubicacionMgl = new UbicacionMgl();
                ubicacionMgl = ubicacionMglFacadeLocal.findById(ubicacionMgl);

                cuentaMatrizList.add(cm);
            }
            params = new HashMap<String, Object>();
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError(CuentasMatricesBean.class.getName() + e.getMessage(), e, LOGGER);
        }
    }

    public void consultarHorarioRestriccion()  {
        try {
            List<CmtHorarioRestriccionMgl> alhorarios = horarioRestriccionFacadeLocal
                    .findByCuentaMatrizId(cuentaMatriz.getCuentaMatrizId());

            horarioRestriccion = new ArrayList<CmtHorarioRestriccionMgl>();
            if (!alhorarios.isEmpty() && alhorarios.size() > 0) {
                for (CmtHorarioRestriccionMgl h : alhorarios) {
                    horarioRestriccion.add(h);
                }
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError(CuentasMatricesBean.class.getName() + e.getMessage(), e, LOGGER);
        }
    }

    public void guardarHorarioRestriccion() throws ApplicationException {

    }

    public void ocultarHorarioRestriccion() throws ApplicationException {
        verHorario = false;
    }

    public void verHorarioRestriccion() throws ApplicationException {
        verHorario = true;
    }

    public boolean isVerHorario() {
        return verHorario;
    }

    public void setVerHorario(boolean verHorario) {
        this.verHorario = verHorario;
    }

    public ArrayList<CmtHorarioRestriccionMgl> getHorarioRestriccion() {
        return horarioRestriccion;
    }

    public void setHorarioRestriccion(ArrayList<CmtHorarioRestriccionMgl> horarioRestriccion) {
        this.horarioRestriccion = horarioRestriccion;
    }

    /**
     *
     */
    public void construirConsulta() {


    }

    public List<CmtCuentaMatrizMgl> consultarCuentasMatrices()
            throws ApplicationException {
        return cuentaMatrizFacade.findCmList(params);
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDpto() {
        return dpto;
    }

    public void setDpto(String dpto) {
        this.dpto = dpto;
    }

    public List<CmtCuentaMatrizMgl> getCuentaMatrizList() {
        return cuentaMatrizList;
    }

    public void setCuentaMatrizList(List<CmtCuentaMatrizMgl> cuentaMatrizList) {
        this.cuentaMatrizList = cuentaMatrizList;
    }

    public CmtCuentaMatrizMgl getCuentaMatriz() {
        return cuentaMatriz;
    }

    public void setCuentaMatriz(CmtCuentaMatrizMgl cuentaMatriz) {
        this.cuentaMatriz = cuentaMatriz;
    }

    public List<CmtRegionalRr> getRrRegionalesList() {
        return regionalList;
    }

    public List<GeograficoPoliticoMgl> getDepartamentoList() {
        return departamentoList;
    }

    public void setDepartamentoList(List<GeograficoPoliticoMgl> departamentoList) {
        this.departamentoList = departamentoList;
    }

    public List<GeograficoPoliticoMgl> getCiudadList() {
        return ciudadList;
    }

    public void setCiudadList(List<GeograficoPoliticoMgl> ciudadList) {
        this.ciudadList = ciudadList;
    }

    public List<String> getBarrioList() {
        return barrioList;
    }

    public void setBarrioList(List<String> barrioList) {
        this.barrioList = barrioList;
    }

    public BigDecimal getDepartamento() {
        return departamento;
    }

    public void setDepartamento(BigDecimal departamento) {
        this.departamento = departamento;
    }

    public BigDecimal getCiudad() {
        return ciudad;
    }

    public void setCiudad(BigDecimal ciudad) {
        this.ciudad = ciudad;
    }

    public boolean isValidado() {
        return validado;
    }

    public void setValidado(boolean validado) {
        this.validado = validado;
    }

    //Actions Paging----------------------------------------------------------
    public void pageFirst() {
        dataTable.setFirst(0);
        numPagina = (dataTable.getFirst() / dataTable.getRows()) + 1 + "";
    }

    public void pagePrevious() {
        dataTable.setFirst(dataTable.getFirst() - dataTable.getRows());
        numPagina = (dataTable.getFirst() / dataTable.getRows()) + 1 + "";
    }

    public void pageNext() {
        dataTable.setFirst(dataTable.getFirst() + dataTable.getRows());
        numPagina = (dataTable.getFirst() / dataTable.getRows()) + 1 + "";
    }

    public void pageLast() {
        int count = dataTable.getRowCount();
        int rows = dataTable.getRows();
        HtmlDataTable t = dataTable;
        dataTable.setFirst(count - ((count % rows != 0) ? count % rows : rows));
        numPagina = (dataTable.getFirst() / dataTable.getRows()) + 1 + "";
    }

    public String getPageActual() {
        int count = dataTable.getRowCount();
        int rows = dataTable.getRows();
        int totalPaginas = (count % rows != 0) ? (count / rows) + 1 : count / rows;
        int actual = (dataTable.getFirst() / rows) + 1;

        paginaList = new ArrayList<Integer>();

        for (int i = 1; i <= totalPaginas; i++) {
            paginaList.add(i);
        }

        pageActual = actual + " de " + totalPaginas;

        if (numPagina == null) {
            numPagina = "1";
        }
        return pageActual;
    }

    public void irPagina(ValueChangeEvent event) {
        String value = event.getNewValue().toString();
        dataTable.setFirst((new Integer(value) - 1) * dataTable.getRows());
    }

    public List<Integer> getPaginaList() {
        return paginaList;
    }

    public void setPaginaList(List<Integer> paginaList) {
        this.paginaList = paginaList;
    }

    public HtmlDataTable getDataTable() {
        return dataTable;
    }

    public void setDataTable(HtmlDataTable dataTable) {
        this.dataTable = dataTable;
    }

    public String getNumPagina() {
        return numPagina;
    }

    public void setNumPagina(String numPagina) {
        this.numPagina = numPagina;
    }

    public void setPageActual(String pageActual) {
        this.pageActual = pageActual;
    }

    public String getCtaSuscriptor() {
        return ctaSuscriptor;
    }

    public void setCtaSuscriptor(String ctaSuscriptor) {
        this.ctaSuscriptor = ctaSuscriptor;
    }

    public String getSerialEquipo() {
        return serialEquipo;
    }

    public void setSerialEquipo(String serialEquipo) {
        this.serialEquipo = serialEquipo;
    }

    public String getCentroPoblado() {
        return centroPoblado;
    }

    public void setCentroPoblado(String centroPoblado) {
        this.centroPoblado = centroPoblado;
    }

    public DrDireccion getDrDireccion() {
        return drDireccion;
    }

    public void setDrDireccion(DrDireccion drDireccion) {
        this.drDireccion = drDireccion;
    }

    public ConfigurationAddressComponent getConfigurationAddressComponent() {
        return configurationAddressComponent;
    }

    public void setConfigurationAddressComponent(ConfigurationAddressComponent configurationAddressComponent) {
        this.configurationAddressComponent = configurationAddressComponent;
    }

    public ArrayList<String> getListBarrios() {
        return listBarrios;
    }

    public void setListBarrios(ArrayList<String> listBarrios) {
        this.listBarrios = listBarrios;
    }

    public List<CmtRegionalRr> getRegionalList() {
        return regionalList;
    }

    public void setRegionalList(List<CmtRegionalRr> regionalList) {
        this.regionalList = regionalList;
    }

    public List<RrCiudades> getCiudadesList() {
        return ciudadesList;
    }

    public void setCiudadesList(List<RrCiudades> ciudadesList) {
        this.ciudadesList = ciudadesList;
    }

    public String getRrRegional() {
        return rrRegional;
    }

    public void setRrRegional(String rrRegional) {
        this.rrRegional = rrRegional;
    }

    public String getRrCiudad() {
        return rrCiudad;
    }

    public void setRrCiudad(String rrCiudad) {
        this.rrCiudad = rrCiudad;
    }

    /**
     * Obtener y exponer la lista de comunidades.
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void obtenerListaCiudades() throws ApplicationException {
        ciudadesList = rrCiudadesFacade.findByCodregional(rrRegional);
    }
}
