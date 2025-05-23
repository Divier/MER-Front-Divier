package co.com.claro.mgl.mbeans.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.ParametrosMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtSubEdificioMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.utils.Constant;
import co.com.telmex.catastro.util.FacesUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Parzifal de León
 */
@ManagedBean(name = "cuentaMatrizBean")
@SessionScoped
public class CuentaMatrizBean {

    private static final Logger LOGGER = LogManager.getLogger(CuentaMatrizBean.class);
   
 
    @EJB
    private CmtSubEdificioMglFacadeLocal subEdificioMglFacadeLocal;
    @EJB
    private ParametrosMglFacadeLocal parametrosMglFacadeLocal;
   
    private final FacesContext facesContext = FacesContext.getCurrentInstance();
    private final HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    public CmtCuentaMatrizMgl cuentaMatriz = new CmtCuentaMatrizMgl();
    private String selectedTab = "HHPP";
    private CmtSubEdificioMgl selectedCmtSubEdificioMgl = null;
    private String nombreSubedificio;
    private List<CmtSubEdificioMgl> listaSub;
    private boolean activacionUCM;
    
    @PostConstruct
    public void init() {
            }
        
    public String cambiarTab(String sSeleccionado){
        String formTabSeleccionado;
        ConstantsCM.TABS_CM_GENERAL Seleccionado = ConstantsCM.TABS_CM_GENERAL.valueOf(sSeleccionado);
        switch (Seleccionado) {
            case GENERAL:
                formTabSeleccionado = "/view/MGL/CM/tabs/general";
                selectedTab = "GENERAL";
                activacionUCM=activaDesactivaUCM();
                session.setAttribute("activaUCM", activacionUCM);
                break;
            case HHPP:
                formTabSeleccionado = "/view/MGL/CM/tabs/hhpp";
                selectedTab = "HHPP";
                break;
            case PENETRACION:
                formTabSeleccionado = "/view/MGL/CM/tabs/penetracion";
                selectedTab = "PENETRACION";
                break;
            case ORDENES:
                formTabSeleccionado = "/view/MGL/CM/tabs/ot";
                selectedTab = "ORDENES";
                break;
            case NOTAS:
                formTabSeleccionado = "/view/MGL/CM/tabs/notas";
                selectedTab = "NOTAS";
                break;
            case INVENTARIO:
                formTabSeleccionado = "/view/MGL/CM/tabs/inventarios";
                selectedTab = "INVENTARIO";
                break;
            case HORARIO:
                formTabSeleccionado = "/view/MGL/CM/tabs/horario";
                selectedTab = "HORARIO";
                break;
            case INFOTECNICA:
                formTabSeleccionado = "/view/MGL/CM/tabs/infoTecnica";
                selectedTab = "INFOTECNICA";
                break;
            case COMPANIAS:
                formTabSeleccionado = "/view/MGL/CM/tabs/companias/gestion-administracion";
                selectedTab = "COMPANIAS";
                break;
            case BLACKLIST:
                formTabSeleccionado = "/view/MGL/CM/tabs/blackList";
                selectedTab = "BLACKLIST";
                break;
            case CASOS:
                formTabSeleccionado = "/view/MGL/CM/tabs/casos";
                selectedTab = "CASOS";
                break;
            case BITACORA:
                formTabSeleccionado = "/view/MGL/CM/tabs/bitacora";
                selectedTab = "BITACORA";
                break;
            case SEGURIDAD:
                formTabSeleccionado = "/view/MGL/CM/tabs/seguridad";
                selectedTab = "SEGURIDAD";
                break;
            case PROYECTOS:
                formTabSeleccionado = "/view/MGL/CM/tabs/proyectos";
                selectedTab = "PROYECTOS";
                break;
            default:
                formTabSeleccionado = "/view/MGL/CM/tabs/general";
                selectedTab = "GENERAL";
                break;
        }
        return formTabSeleccionado;
    }

    public CmtCuentaMatrizMgl getCuentaMatriz() {
        return cuentaMatriz;
    }

    public void setCuentaMatriz(CmtCuentaMatrizMgl cuentaMatriz) {
        selectedCmtSubEdificioMgl = null;
        this.cuentaMatriz = cuentaMatriz;
    }

    public String getSelectedTab() {
        return selectedTab;
    }

    public void setSelectedTab(String selectedTab) {
        this.selectedTab = selectedTab;
    }

    public CmtSubEdificioMgl getSelectedCmtSubEdificioMgl() {
        if (selectedCmtSubEdificioMgl != null) {
            return selectedCmtSubEdificioMgl;
        } else {
            if (this.cuentaMatriz != null) {
                List<CmtSubEdificioMgl> listCmtSubEdificioMgl = this.cuentaMatriz.getListCmtSubEdificioMglActivos();
                if (listCmtSubEdificioMgl != null && !listCmtSubEdificioMgl.isEmpty()) {
                    selectedCmtSubEdificioMgl = listCmtSubEdificioMgl.get(0);
                    nombreSubedificio = selectedCmtSubEdificioMgl.getNombreSubedificio();
                    return selectedCmtSubEdificioMgl;
                }
            }
            return null;
        }
    }
    
    public List<CmtSubEdificioMgl> getListaSubedificios() {
         List<CmtSubEdificioMgl> listaSubEdificios = new ArrayList<CmtSubEdificioMgl>();
        try {

            listaSub = subEdificioMglFacadeLocal.findSubEdificioByCuentaMatriz(cuentaMatriz);

            Collections.sort(listaSub, new Comparator<CmtSubEdificioMgl>() {
                @Override
                public int compare(CmtSubEdificioMgl a, CmtSubEdificioMgl b) {
                    return a.getNombreSubedificio().compareTo(b.getNombreSubedificio());
                }
            });
            if (listaSub.size() == 1) {
                listaSubEdificios.add(listaSub.get(0));
            } else {
                for (CmtSubEdificioMgl ed : listaSub) {
                    if (ed.getEstadoRegistro() != 0 && ed.getEstadoSubEdificioObj().getIdentificadorInternoApp() != null 
                            && !ed.getEstadoSubEdificioObj().getIdentificadorInternoApp().
                            equalsIgnoreCase(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO)) {
                        listaSubEdificios.add(ed);
                    } else {
                        listaSubEdificios.add(0, ed);
                    }
                }
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en CuentamatrizBean class ..." + e.getMessage(), e, LOGGER);
        }
        return listaSubEdificios;
    }
    
    public boolean activaDesactivaUCM(){

        String msn;
        try {
            ParametrosMgl parametrosMgl = parametrosMglFacadeLocal.findByAcronimoName(Constant.ACTIVACION_ENVIO_UCM);
            String valor;
            if (parametrosMgl != null) {
                valor = parametrosMgl.getParValor();
                if (!valor.equalsIgnoreCase("1") && !valor.equalsIgnoreCase("0")) {
                    msn = "El valor configurado para el parametro:  "
                            + "" + Constant.ACTIVACION_ENVIO_UCM + " debe ser '1' o '0'  "
                            + "actualmente se encuentra el valor: " + valor + "";
                      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                    activacionUCM = false;
                } else if (valor.equalsIgnoreCase("1")) {
                    activacionUCM = true;
                } else {
                    activacionUCM = false;
                }

            }
        } catch (ApplicationException ex) {
            FacesUtil.mostrarMensajeError("Error al consultar el parametro: " + Constant.ACTIVACION_ENVIO_UCM + "" + ex.getMessage(), ex, LOGGER);
            activacionUCM = false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al consultar el parametro: " + Constant.ACTIVACION_ENVIO_UCM + "" + e.getMessage(), e, LOGGER);
            activacionUCM = false;
        }
        return activacionUCM;
    }


    public void setSelectedCmtSubEdificioMgl(CmtSubEdificioMgl selectedCmtSubEdificioMgl) {
        this.selectedCmtSubEdificioMgl = selectedCmtSubEdificioMgl;
    }

    public String getNombreSubedificio() {
        return nombreSubedificio;
    }

    public void setNombreSubedificio(String nombreSubedificio) {
        this.nombreSubedificio = nombreSubedificio;
    }

    public void initializedComponents() {
        LOGGER.info("Session Bean CM iniciada");
    }

    public CmtSubEdificioMglFacadeLocal getSubEdificioMglFacadeLocal() {
        return subEdificioMglFacadeLocal;
    }

    public void setSubEdificioMglFacadeLocal(CmtSubEdificioMglFacadeLocal subEdificioMglFacadeLocal) {
        this.subEdificioMglFacadeLocal = subEdificioMglFacadeLocal;
    }

    public List<CmtSubEdificioMgl> getListaSub() {
        return listaSub;
    }

    public void setListaSub(List<CmtSubEdificioMgl> listaSub) {
        this.listaSub = listaSub;
    }

}
