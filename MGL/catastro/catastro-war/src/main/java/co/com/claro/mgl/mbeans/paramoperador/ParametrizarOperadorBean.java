/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.paramoperador;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.GeograficoPoliticoMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtCoberturaEntregaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoBasicaMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCoberturaEntregaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Fabian Castro Managed Bean de la pantalla de parametrizacion de
 * operador logistico
 */
@ManagedBean(name = "parametrizarOperadorBean")
@ViewScoped
public class ParametrizarOperadorBean {

    private SecurityLogin securityLogin;
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private static final Logger LOGGER = LogManager.getLogger(ParametrizarOperadorBean.class);
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private CmtCoberturaEntregaMgl entidadCover;
    private List<CmtBasicaMgl> listaInventario; //Lista de Inventarios
    private List<CmtBasicaMgl> listaOperadores; //Lista de Operadores Logisticos
    private List<CmtCoberturaEntregaMgl> listaCoberturaCentroPoblado; //Lista De Inventarios agregados al Centro Poblado por Operador Logistico 
    private List<CmtCoberturaEntregaMgl> listaCoberturaCentroPobladoBorrar; //Lista De Inventarios Eliminados al Centro Poblado por Operador Logistico 
    private CmtTipoBasicaMgl basicaMGLInventario; //Tipo Basica Tipo Inventarios
    private CmtTipoBasicaMgl basicaMGLOperador; //Tipo Basica Operadores Logisticos
    private List<GeograficoPoliticoMgl> dptoList; //Lista de Departamentos
    private List<GeograficoPoliticoMgl> ciudadList; //Lista de Ciudades 
    private List<GeograficoPoliticoMgl> centrosPobladosList; //Lista de Centros Poblados
    private String dptoStr; // String Departamento Seleccionado
    private String ciudadStr; //String Ciudad Selecionada
    private String centroPobladoStr; //String CentroPoblado 
    private String operadorLogisticoStr; //String CentroPoblado 
    private GeograficoPoliticoMgl centroPobladoObj; //Ciudad Seleccionada
    private CmtBasicaMgl operadorSeleccionado;
    private HashMap<String, Boolean> datos;
    private String usuarioVT;
    private int perfilVT;
    private String cedulaUsuarioVT;
    
    //Opciones agregadas para Rol
    private final String BTNPRVPRM = "BTNPRVPRM";
    
    @EJB
    private CmtCoberturaEntregaMglFacadeLocal coverturaEntregaLocal;
    @EJB
    private CmtBasicaMglFacadeLocal tablaBasicaLocal;
    @EJB
    private GeograficoPoliticoMglFacadeLocal geograficoPoliticoMglFacadeLocal;
    @EJB
    private CmtTipoBasicaMglFacadeLocal tipoBasicaLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacade;
   

    public ParametrizarOperadorBean() {
        seguridad();
    }

    @PostConstruct
    public void init() {
        try {
            setDatos(new HashMap<String, Boolean>());
            setEntidadCover(new CmtCoberturaEntregaMgl());

            getEntidadCover().setBasica(new CmtBasicaMgl());
            setBasicaMGLInventario(getTipoBasicaLocal().findByNombreTipoBasica("TIPO INVENTARIO"));
            setBasicaMGLOperador(getTipoBasicaLocal().findByNombreTipoBasica("OPERADORES LOGISTICOS"));
            loadList();
            listaCoberturaCentroPoblado = new ArrayList<CmtCoberturaEntregaMgl>();
            setListaCoberturaCentroPobladoBorrar(new ArrayList<CmtCoberturaEntregaMgl>());

        } catch (ApplicationException e) {
            LOGGER.error("Error en init. " + e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Error en init. " + e.getMessage(), e);
        }
    }

    public void addItemListCoberturaCentroPob(CmtBasicaMgl item) {
        if (item == null || centroPobladoObj == null | operadorSeleccionado == null) {
            return;
        }
        CmtCoberturaEntregaMgl coberturaCentro = new CmtCoberturaEntregaMgl();
        coberturaCentro.setBasica(item);
        coberturaCentro.setGeograficoPolitico(centroPobladoObj);
        coberturaCentro.setOperadorBasicaID(operadorSeleccionado);
        boolean add = true;
        for (CmtCoberturaEntregaMgl itemCobertura : listaCoberturaCentroPoblado) {
            if (itemCobertura.getGeograficoPolitico().getGpoId().equals(centroPobladoObj.getGpoId())
                    && itemCobertura.getOperadorBasicaID().getBasicaId().equals(operadorSeleccionado.getBasicaId())
                    && itemCobertura.getBasica().getNombreBasica().equals(item.getNombreBasica())) {
                add = false;
                listaCoberturaCentroPoblado.remove(itemCobertura);
                getListaCoberturaCentroPobladoBorrar().add(itemCobertura);
                break;
            }
        }
        if (add) {
            listaCoberturaCentroPoblado.add(coberturaCentro);
            for (CmtCoberturaEntregaMgl itemCobertura : getListaCoberturaCentroPobladoBorrar()) {
                if (itemCobertura.getGeograficoPolitico().equals(centroPobladoObj)
                        && itemCobertura.getOperadorBasicaID().equals(operadorSeleccionado)
                        && itemCobertura.getBasica().equals(item)) {
                    getListaCoberturaCentroPobladoBorrar().remove(itemCobertura);
                    break;
                }
            }
        }
    }

    public void loadList() {
        try {
            setDptoList(getGeograficoPoliticoMglFacadeLocal().findDptos());
            loadCities();
            recargarListas();
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en loadList. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en loadList. ", e, LOGGER);
        }
    }

    public void loadCities() throws ApplicationException {
        try {
            if(getDptoStr()== null){
                setDptoStr("0");
            }
            if (getDptoStr().equalsIgnoreCase("0") || getDptoStr().isEmpty()) {
                setCiudadList(null);
            } else {
                setCiudadStr("0");
                setCiudadList(getGeograficoPoliticoMglFacadeLocal().findCiudades(new BigDecimal(getDptoStr())));

            }
            recargarListas();
            setCentroPobladoStr("");
            setCentrosPobladosList(new ArrayList<GeograficoPoliticoMgl>());

        } catch (ApplicationException e) {
            LOGGER.error("Error en loadCities. " +e.getMessage(), e); 
            throw e;
        } catch (Exception e) {
            LOGGER.error("Error en loadCities. " +e.getMessage(), e); 
            throw e;
        }
    }

    public void loadOperadores() {
        try {
            if (getCentroPobladoStr().equalsIgnoreCase("0") || getCentroPobladoStr().isEmpty()) {
                setListaOperadores(null);
            } else {
                setOperadorLogisticoStr("0");
                setListaOperadores(getTablaBasicaLocal().findByTipoBasica(getBasicaMGLOperador()));
            }
            recargarListas();

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en loadOperadores. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en loadOperadores. ", e, LOGGER);
        }
    }

    public void loadCenters() {
        try {
            if (getCiudadStr().equalsIgnoreCase("0")) {
                setCiudadStr(null);
                setCentroPobladoStr("0");
                setCentrosPobladosList(new ArrayList<GeograficoPoliticoMgl>());
            } else {
                setCentrosPobladosList(new ArrayList<GeograficoPoliticoMgl>());
                setCentroPobladoStr("");
                setCentrosPobladosList(getGeograficoPoliticoMglFacadeLocal().findCentrosPoblados(new BigDecimal(getCiudadStr())));
            }
            recargarListas();
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en loadCenters. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en loadCenters. ", e, LOGGER);
        }
    }

    public void loadCoberturas() throws ApplicationException {
        try {
            setCentroPobladoObj(getGeograficoPoliticoMglFacadeLocal().findById(new BigDecimal(getCentroPobladoStr())));
            if (centroPobladoObj != null) {
                setOperadorLogisticoStr("0");
                setListaCoberturaCentroPoblado(getCoberturaEntregaLocal().findCoberturaEntregaListByCentroPobladoIdOperadorId(centroPobladoObj.getGpoId(),operadorSeleccionado.getBasicaId()));
            }
        } catch (ApplicationException e) {
            LOGGER.error("Error en loadCoberturas. " +e.getMessage(), e);  
            throw e;
        } catch (Exception e) {
            LOGGER.error("Error en loadCoberturas. " +e.getMessage(), e);
            throw new ApplicationException("Error en loadCoberturas. ",e);
        }
    }

    public void cambioOperador() {
        try {
            operadorSeleccionado = getTablaBasicaLocal().findById(new BigDecimal(operadorLogisticoStr));
            LOGGER.error("Cambio Operador  " + operadorSeleccionado.getNombreBasica());
            loadCoberturas();
            setListaInventario(getTablaBasicaLocal().findByTipoBasica(getBasicaMGLInventario()));
            asignarChecks();
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en cambioOperador. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en cambioOperador. ", e, LOGGER);
        }
    }

    public void nuevoRegistro() {
        setEntidadCover(new CmtCoberturaEntregaMgl());
        getEntidadCover().setBasica(new CmtBasicaMgl());
        getEntidadCover().setGeograficoPolitico(new GeograficoPoliticoMgl());
        setListaCoberturaCentroPoblado(new ArrayList<CmtCoberturaEntregaMgl>());
        setListaCoberturaCentroPobladoBorrar(new ArrayList<CmtCoberturaEntregaMgl>());
        setDptoStr("");
        setListaOperadores(new ArrayList<CmtBasicaMgl>());
        setCiudadList(new ArrayList<GeograficoPoliticoMgl>());
        setCentrosPobladosList(new ArrayList<GeograficoPoliticoMgl>());
        loadList();
        setListaInventario(new ArrayList<CmtBasicaMgl>());


    }

    public void guardarCoberturaEntrega() {
        try {
            for (CmtCoberturaEntregaMgl item : listaCoberturaCentroPoblado) {
                if (item != null) {
                    if (item.getCoberturaEntregaId() != null && item.getCoberturaEntregaId().intValue() != 0) {
                        continue;
                    }
                    configurarObjeto(item);
                    getCoberturaEntregaLocal().create(item);
                }
            }
            for (CmtCoberturaEntregaMgl item : getListaCoberturaCentroPobladoBorrar()) {
                getCoberturaEntregaLocal().delete(item);
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cobertura Actualizada "));

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en guardarCoberturaEntrega. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en guardarCoberturaEntrega. ", e, LOGGER);
        }
    }

    public CmtCoberturaEntregaMgl getEntidadCover() {
        return entidadCover;
    }

    public void setEntidadCover(CmtCoberturaEntregaMgl entidadCover) {
        this.entidadCover = entidadCover;
    }

    public List<CmtBasicaMgl> getListaInventario() {
        return listaInventario;
    }

    public void setListaInventario(List<CmtBasicaMgl> listaCoberturas) {
        this.listaInventario = listaCoberturas;
    }

    public FacesContext getFacesContext() {
        return facesContext;
    }

    public void setFacesContext(FacesContext facesContext) {
        this.facesContext = facesContext;
    }

    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public CmtCoberturaEntregaMglFacadeLocal getCoberturaEntregaLocal() {
        return coverturaEntregaLocal;
    }

    public void setCoberturaEntregaLocal(CmtCoberturaEntregaMglFacadeLocal coverturaEntregaLocal) {
        this.coverturaEntregaLocal = coverturaEntregaLocal;
    }

    public CmtBasicaMglFacadeLocal getTablaBasicaLocal() {
        return tablaBasicaLocal;
    }

    public void setTablaBasicaLocal(CmtBasicaMglFacadeLocal tablaBasicaLocal) {
        this.tablaBasicaLocal = tablaBasicaLocal;
    }

    public GeograficoPoliticoMglFacadeLocal getGeograficoPoliticoMglFacadeLocal() {
        return geograficoPoliticoMglFacadeLocal;
    }

    public void setGeograficoPoliticoMglFacadeLocal(GeograficoPoliticoMglFacadeLocal geograficoPoliticoMglFacadeLocal) {
        this.geograficoPoliticoMglFacadeLocal = geograficoPoliticoMglFacadeLocal;
    }

    public CmtTipoBasicaMglFacadeLocal getTipoBasicaLocal() {
        return tipoBasicaLocal;
    }

    public void setTipoBasicaLocal(CmtTipoBasicaMglFacadeLocal tipoBasicaLocal) {
        this.tipoBasicaLocal = tipoBasicaLocal;
    }

    public List<CmtCoberturaEntregaMgl> getListaCoberturaCentroPoblado() {
        return listaCoberturaCentroPoblado;
    }

    public void setListaCoberturaCentroPoblado(List<CmtCoberturaEntregaMgl> listaCoberturaCentroPoblado) {
        this.listaCoberturaCentroPoblado = listaCoberturaCentroPoblado;
    }

    public CmtTipoBasicaMgl getBasicaMGLInventario() {
        return basicaMGLInventario;
    }

    public void setBasicaMGLInventario(CmtTipoBasicaMgl basicaMGL) {
        this.basicaMGLInventario = basicaMGL;
    }

    public List<GeograficoPoliticoMgl> getDptoList() {
        return dptoList;
    }

    public void setDptoList(List<GeograficoPoliticoMgl> dptoList) {
        this.dptoList = dptoList;
    }

    public String getDptoStr() {
        return dptoStr;
    }

    public void setDptoStr(String dptoStr) {
        this.dptoStr = dptoStr;
    }

    public List<GeograficoPoliticoMgl> getCiudadList() {
        return ciudadList;
    }

    public void setCiudadList(List<GeograficoPoliticoMgl> ciudadList) {
        this.ciudadList = ciudadList;
    }

    public String getCentroPobladoStr() {
        return centroPobladoStr;
    }

    public void setCentroPobladoStr(String centroPobladoStr) {
        this.centroPobladoStr = centroPobladoStr;
    }

    public String getCiudadStr() {
        return ciudadStr;
    }

    public void setCiudadStr(String ciudadStr) {
        this.ciudadStr = ciudadStr;
    }

    public List<GeograficoPoliticoMgl> getCentrosPobladosList() {
        return centrosPobladosList;
    }

    public void setCentrosPobladosList(List<GeograficoPoliticoMgl> centrosPobladosList) {
        this.centrosPobladosList = centrosPobladosList;
    }

    public GeograficoPoliticoMgl getCentroPobladoObj() {
        return centroPobladoObj;
    }

    public void setCentroPobladoObj(GeograficoPoliticoMgl centroPobladoObj) {
        this.centroPobladoObj = centroPobladoObj;
    }

    public CmtTipoBasicaMgl getBasicaMGLOperador() {
        return basicaMGLOperador;
    }

    public void setBasicaMGLOperador(CmtTipoBasicaMgl basicaMGLOperador) {
        this.basicaMGLOperador = basicaMGLOperador;
    }

    public List<CmtBasicaMgl> getListaOperadores() {
        return listaOperadores;
    }

    public void setListaOperadores(List<CmtBasicaMgl> listaOperadores) {
        this.listaOperadores = listaOperadores;
    }

    public CmtBasicaMgl getOperadorSeleccionado() {
        return operadorSeleccionado;
    }

    public void setOperadorSeleccionado(CmtBasicaMgl operadorSeleccionado) {
        this.operadorSeleccionado = operadorSeleccionado;
    }

    public String getOperadorLogisticoStr() {
        return operadorLogisticoStr;
    }

    public void setOperadorLogisticoStr(String operadorLogisticoStr) {
        this.operadorLogisticoStr = operadorLogisticoStr;
    }

    private void configurarObjeto(CmtCoberturaEntregaMgl item) {
        item.setEstadoRegistro(new Short("1"));
        item.setFechaCreacion(new Date());
        item.setUsuarioCreacion(usuarioVT);
        item.setCoberturaEntregaId(null);
    }

    public void seguridad() {
        try {
            securityLogin = new SecurityLogin(facesContext);
             if (!securityLogin.isLogin()) {
                if (!response.isCommitted()) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                }
                return;
            }
            usuarioVT = securityLogin.getLoginUser();
            perfilVT = securityLogin.getPerfilUsuario();
            cedulaUsuarioVT = securityLogin.getIdUser();

        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error en seguridad. ", e, LOGGER);
        }catch(Exception e){
            FacesUtil.mostrarMensajeError("Error en seguridad. ", e, LOGGER);
        }
    }

    private void asignarChecks() {
        for (CmtBasicaMgl cmtBasicaMgl : listaInventario) {
            for (CmtCoberturaEntregaMgl cover : listaCoberturaCentroPoblado) {
                if (cover.getBasica().getNombreBasica().equals(cmtBasicaMgl.getNombreBasica())) {
                    getDatos().put(cmtBasicaMgl.getAbreviatura(), Boolean.TRUE);
                }
            }
        }
        for (CmtBasicaMgl cmtBasicaMgl : listaInventario) {
            if (!datos.keySet().contains(cmtBasicaMgl.getAbreviatura())) {
                getDatos().put(cmtBasicaMgl.getAbreviatura(), Boolean.FALSE);
            }
        }
    }

    public HashMap<String, Boolean> getDatos() {
        return datos;
    }

    public void setDatos(HashMap<String, Boolean> datos) {
        this.datos = datos;
    }

    private void recargarListas() {
        listaCoberturaCentroPoblado = new ArrayList<CmtCoberturaEntregaMgl>();
        setListaCoberturaCentroPobladoBorrar(new ArrayList<CmtCoberturaEntregaMgl>());
        listaInventario = new ArrayList<CmtBasicaMgl>();
        datos = new HashMap<String, Boolean>();
    }
    
    // Validar Opciones por Rol    
    public boolean validarOpcionNuevo() {
        return validarEdicionRol(BTNPRVPRM);
    }
    
    //funcion General
      private boolean validarEdicionRol(String formulario) {
        try {
            boolean tieneRolPermitido = ValidacionUtil.validarVisualizacion(formulario, ValidacionUtil.OPC_EDICION, cmtOpcionesRolMglFacade, securityLogin);
            return tieneRolPermitido;
        }catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION + e.getMessage(), e, LOGGER);
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al ParametrizarOperadorBean. " + e.getMessage(), e, LOGGER);
        }
        return false;
    }

    public List<CmtCoberturaEntregaMgl> getListaCoberturaCentroPobladoBorrar() {
        return listaCoberturaCentroPobladoBorrar;
    }

    public void setListaCoberturaCentroPobladoBorrar(List<CmtCoberturaEntregaMgl> listaCoberturaCentroPobladoBorrar) {
        this.listaCoberturaCentroPobladoBorrar = listaCoberturaCentroPobladoBorrar;
    }
}
