/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.NodoMglFacadeLocal;
import co.com.claro.mgl.facade.ParametrosCallesFacadeLocal;
import co.com.claro.mgl.facade.ParametrosMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoBasicaMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.ParametrosCalles;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
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
 * @author cardenaslb
 */
@ManagedBean(name = "nodoBeans")
//@RequestScoped
@ViewScoped
public class NodoBeans implements Serializable {

    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private String message;
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private String usuarioVT = null;
    private int perfilVT = 0;
    private static final Logger LOGGER = LogManager.getLogger(NodoBeans.class);
    private CmtTipoBasicaMgl tipoBasicaTbl;
    private String estiloObligatorio = "<font color='red'>*</font>";
    private List<String> modulosSelected;
    private boolean cargar = true;
    private List<CmtBasicaMgl> modulosTblBasica;
    private String selectedEstado;
    private List<CmtTipoBasicaMgl> tablasTipoBasicasMglList;
    @EJB
    private CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMglFacade;
    @EJB
    private ParametrosMglFacadeLocal parametrosMglFacadeLocal;
    @EJB
    private CmtBasicaMglFacadeLocal cmtBasicaMglFacadeLocal;
    private String tablaTipoBasicaSelected;

    @EJB
    private NodoMglFacadeLocal nodoMglFacade;
    private List modulosRender;
    private NodoMgl nodoMgl;
    private boolean tblTecnologia = true;
    private boolean tipoViviendaActive = false;
    private String subEdificioSelected;
    private boolean tipoViviendaSelected;
    private boolean soporteSelected;
    @EJB
    private ParametrosCallesFacadeLocal parametrosCallesFacadeLocal;
    private List<ParametrosCalles> listTipoNivel5;

    public NodoBeans() {
        try {
            try {
                SecurityLogin securityLogin = new SecurityLogin(facesContext);
                if (!securityLogin.isLogin()) {
                    if (!response.isCommitted()) {
                        response.sendRedirect(securityLogin.redireccionarLogin());
                    }
                    return;
                }
                usuarioVT = securityLogin.getLoginUser();
                perfilVT = securityLogin.getPerfilUsuario();
                if (usuarioVT == null) {
                    session.getAttribute("usuarioM");
                    usuarioVT = (String) session.getAttribute("usuarioM");
                } else {
                    session = (HttpSession) facesContext.getExternalContext().getSession(false);
                    session.setAttribute("usuarioM", usuarioVT);
                }

            } catch (IOException e) {
                FacesUtil.mostrarMensajeError("Se generea error en NodoBeans class ..." + e.getMessage(), e, LOGGER);
            } catch (Exception e) {
                FacesUtil.mostrarMensajeError("Se generea error en NodoBeans class ..." + e.getMessage(), e, LOGGER);
            }

        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg, e);
        }

    }

    @PostConstruct
    private void fillSqlList() {
        try {
            CmtTipoBasicaMgl tipoBasicaTipoProyecto;
            tipoBasicaTipoProyecto = cmtTipoBasicaMglFacade.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_MODULOS_APLICAR_TIPO_BASICA);
            modulosTblBasica = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasicaTipoProyecto);
            tablasTipoBasicasMglList = new ArrayList<CmtTipoBasicaMgl>();
            tablasTipoBasicasMglList = cmtTipoBasicaMglFacade.findAll();
            buscarTipoVivienda();
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error en NodoBeans class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en NodoBeans class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public void buscarNodo() {
        try {
      

            change();
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error en NodoBeans class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en NodoBeans class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public void change() throws ApplicationException {
        if (tipoBasicaTbl.getComplemento().contains("tblTec")) {
            cargar = false;
            tblTecnologia = true;
        }

    }

    public void buscarTipoVivienda() {
        try {
            listTipoNivel5 = parametrosCallesFacadeLocal.findByTipo("DIR_NUM_APTO");
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error en NodoBeans class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en NodoBeans class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public void mostrarTipoVivienda() {
        tipoViviendaSelected = true;
    }

    public void crearTipoTablasBasicasMgl() {
        try {
            if (nodoMgl.getAreId() == null || nodoMgl.getComId() == null || nodoMgl.getDisId() == null || nodoMgl.getDivId() == null
                    || nodoMgl.getGpoId() == null || nodoMgl.getNodFechaActivacion() == null || nodoMgl.getNodNombre() == null || nodoMgl.getZonId() == null || nodoMgl.getEstado() == null) {
                String msn = "Todos los campos son obligatorios";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return;
            }

            NodoMgl nodo = nodoMglFacade.create(nodoMgl);
            if (nodo != null) {
                String msn = "Proceso exitoso";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
            }

        } catch (ApplicationException e) {
            String msn = "Proceso falló: ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
            session.setAttribute("message", message);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en NodoBeans class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public CmtTipoBasicaMgl getTipoBasicaTbl() {
        return tipoBasicaTbl;
    }

    public void setTipoBasicaTbl(CmtTipoBasicaMgl tipoBasicaTbl) {
        this.tipoBasicaTbl = tipoBasicaTbl;
    }

    public String getEstiloObligatorio() {
        return estiloObligatorio;
    }

    public void setEstiloObligatorio(String estiloObligatorio) {
        this.estiloObligatorio = estiloObligatorio;
    }

    public List<String> getModulosSelected() {
        return modulosSelected;
    }

    public void setModulosSelected(List<String> modulosSelected) {
        this.modulosSelected = modulosSelected;
    }

    public boolean isCargar() {
        return cargar;
    }

    public void setCargar(boolean cargar) {
        this.cargar = cargar;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<CmtBasicaMgl> getModulosTblBasica() {
        return modulosTblBasica;
    }

    public void setModulosTblBasica(List<CmtBasicaMgl> modulosTblBasica) {
        this.modulosTblBasica = modulosTblBasica;
    }

    public String getSelectedEstado() {
        return selectedEstado;
    }

    public void setSelectedEstado(String selectedEstado) {
        this.selectedEstado = selectedEstado;
    }

    public List<CmtTipoBasicaMgl> getTablasTipoBasicasMglList() {
        return tablasTipoBasicasMglList;
    }

    public void setTablasTipoBasicasMglList(List<CmtTipoBasicaMgl> tablasTipoBasicasMglList) {
        this.tablasTipoBasicasMglList = tablasTipoBasicasMglList;
    }

    public String getTablaTipoBasicaSelected() {
        return tablaTipoBasicaSelected;
    }

    public void setTablaTipoBasicaSelected(String tablaTipoBasicaSelected) {
        this.tablaTipoBasicaSelected = tablaTipoBasicaSelected;
    }

    public List getModulosRender() {
        return modulosRender;
    }

    public void setModulosRender(List modulosRender) {
        this.modulosRender = modulosRender;
    }

    public NodoMgl getNodoMgl() {
        return nodoMgl;
    }

    public void setNodoMgl(NodoMgl nodoMgl) {
        this.nodoMgl = nodoMgl;
    }

    public boolean isTblTecnologia() {
        return tblTecnologia;
    }

    public void setTblTecnologia(boolean tblTecnologia) {
        this.tblTecnologia = tblTecnologia;
    }

    public String getSubEdificioSelected() {
        return subEdificioSelected;
    }

    public void setSubEdificioSelected(String subEdificioSelected) {
        this.subEdificioSelected = subEdificioSelected;
    }

    public boolean getTipoViviendaSelected() {
        return tipoViviendaSelected;
    }

    public void setTipoViviendaSelected(boolean tipoViviendaSelected) {
        this.tipoViviendaSelected = tipoViviendaSelected;
    }

    public List<ParametrosCalles> getListTipoNivel5() {
        return listTipoNivel5;
    }

    public void setListTipoNivel5(List<ParametrosCalles> listTipoNivel5) {
        this.listTipoNivel5 = listTipoNivel5;
    }

    public boolean isTipoViviendaActive() {
        return tipoViviendaActive;
    }

    public void setTipoViviendaActive(boolean tipoViviendaActive) {
        this.tipoViviendaActive = tipoViviendaActive;
    }

    public boolean isSoporteSelected() {
        return soporteSelected;
    }

    public void setSoporteSelected(boolean soporteSelected) {
        this.soporteSelected = soporteSelected;
    }

}
