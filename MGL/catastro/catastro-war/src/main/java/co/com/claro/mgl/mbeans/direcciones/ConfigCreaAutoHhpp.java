package co.com.claro.mgl.mbeans.direcciones;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.ConfCreahppAutoFacadeLocal;
import co.com.claro.mgl.facade.GeograficoPoliticoMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.ConfCreahppAuto;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

/**
 * Bean de la pagina de configuracion Automatica de HHPP. Permite manejar la
 * logica de administracion de la configuracion para la creacion automatica de
 * HHPP en Verificacion de casas.
 *
 * @author Johnnatan Ortiz
 * @versión 1.00.000
 */
@ManagedBean(name = "configCreacionAutoHhpp")
@ViewScoped
public class ConfigCreaAutoHhpp {

    private List<GeograficoPoliticoMgl> dptos;
    private List<GeograficoPoliticoMgl> ciudades;
    private List<ConfCreahppAuto> configCiudadesList;
    private ConfCreahppAuto configCreaAutoDet;
    private String dptoSelected;
    private String ciudadSelected;
    private HtmlDataTable confAutoTable;
    private static final Logger LOGGER = LogManager.getLogger(ConfigCreaAutoHhpp.class);
    private SecurityLogin securityLogin;
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpServletResponse response = (HttpServletResponse) facesContext.
            getExternalContext().getResponse();
    @EJB
    private GeograficoPoliticoMglFacadeLocal geograficoPoliticoMglFacadeLocal;
    @EJB
    private ConfCreahppAutoFacadeLocal confCreahppAutoFacadeLocal;

    public ConfigCreaAutoHhpp() {
        try {
            securityLogin = new SecurityLogin(facesContext);
            if (!securityLogin.isLogin()) {
                response.sendRedirect(
                        securityLogin.redireccionarLogin());
            }
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error en ConfigCreaAutoHhpp, Autenticando Login ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ConfigCreaAutoHhpp, Autenticando Login ", e, LOGGER);
        }
    }

    /**
     * Carga la informacion de los filtros. Permite cargar la informacion
     * necesaria de listas para filtros despues del constructor de la pagina.
     *
     * @author Johnnatan Ortiz.
     */
    @PostConstruct
    public void fillData() {
        try {
            //Cargamos los Departamentos del filtro de la pagina
            dptos = geograficoPoliticoMglFacadeLocal.findDptos();
        } catch (ApplicationException e) {
            LOGGER.error("error fillData " + e.getMessage());
        } catch (Exception e) {
            LOGGER.error("error fillData " + e.getMessage());
        }
    }

    /**
     * Carga la informacion de las ciudades. Permite cargar la informacion de
     * las ciudades del departamento seleccionado.
     *
     * @author Johnnatan Ortiz.
     */
    public void listDptoChange() {
        try {
            ciudades = geograficoPoliticoMglFacadeLocal.findCiudades(
                    new BigDecimal(dptoSelected));
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en listDptoChange. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en listDptoChange. ", e, LOGGER);
        }
    }

    /**
     * Obtiene la configuracion para una ciudad. Permite obtener los datos de
     * configuracion para la creacion automatica de un HHPP en la creacion de la
     * solicitud para una ciudad determinada.
     *
     * @author Johnnatan Ortiz
     */
    public void obtenerConfigCiudad() {
        try {
            GeograficoPoliticoMgl ciudadConf = new GeograficoPoliticoMgl();
            ciudadConf.setGpoId(new BigDecimal(ciudadSelected));
            configCreaAutoDet = confCreahppAutoFacadeLocal.findByIdCity(ciudadConf);
            configCiudadesList = null;

            if (configCreaAutoDet == null) {
                String msn = "No se encontró cofiguración para la ciudad";
                pintarMesaje(msn, FacesMessage.SEVERITY_INFO);
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en obtenerConfigCiudad. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en obtenerConfigCiudad. ", e, LOGGER);
        }
    }

    /**
     * Obtiene la configuracion para una ciudad. Permite obtener los datos de
     * configuracion para la creacion automatica de un HHPP en la creacion de la
     * solicitud para una ciudad determinada.
     *
     * @author Johnnatan Ortiz
     *
     */
    public void verTodo() {
        try {
            configCiudadesList = confCreahppAutoFacadeLocal.findAll();
            configCreaAutoDet = null;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en verTodo. No fue posible realizar la consulta. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en verTodo. No fue posible realizar la consulta. ", e, LOGGER);
        }
    }

    /**
     * Ajusta la aplicacion para Ingresar un nuevo Registro.Permite realizar
 los validaciones necesarias para ingresar un nuevo registro.
     *
     * @author Johnnatan Ortiz
     * @return 
     */
    public String crearRegistro() {
        try {
            if (ciudadSelected == null
                    || ciudadSelected.trim().isEmpty()) {
                String msn = "Por Favor seleccione una ciudad";
                pintarMesaje(msn, FacesMessage.SEVERITY_ERROR);
                return null;
            } else {
                GeograficoPoliticoMgl ciudadConf =
                        geograficoPoliticoMglFacadeLocal.
                        findById(new BigDecimal(ciudadSelected));

                ciudadConf.setGpoId(new BigDecimal(ciudadSelected));
                configCreaAutoDet = confCreahppAutoFacadeLocal.
                        findByIdCity(ciudadConf);
                if (configCreaAutoDet != null) {
                    String msn = "La ciudad selecionada ya cuenta con una "
                            + "configuracion, por favor seleccione otra ciudad";
                    pintarMesaje(msn, FacesMessage.SEVERITY_ERROR);
                    return null;
                } else {
                    configCreaAutoDet = new ConfCreahppAuto();
                    configCreaAutoDet.setGpoId(ciudadConf);
                }
                configCiudadesList = null;
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en crearRegistro. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en crearRegistro. ", e, LOGGER);
        }
        return null;
    }

    /**
     * Actualiza la configuracion para una ciudad. Permite actualizar los datos
     * de configuracion para la creacion automatica de un HHPP en la creacion de
     * la solicitud para una ciudad determinada.
     *
     * @author Johnnatan Ortiz
     */
    public void actualizarDetalle() {
        try {
            configCreaAutoDet = confCreahppAutoFacadeLocal.update(configCreaAutoDet);
            String msn = "La cofiguración fue actualizada correctamente";
            pintarMesaje(msn, FacesMessage.SEVERITY_INFO);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en actualizarDetalle. No fue posible actualizar la configuracion. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en actualizarDetalle. No fue posible actualizar la configuracion. ", e, LOGGER);
        }
    }

    /**
     * Elimina la configuracion para una ciudad. Permite eliminar los datos de
     * configuracion para la creacion automatica de un HHPP en la creacion de la
     * solicitud para una ciudad determinada.
     *
     * @author Johnnatan Ortiz
     */
    public void elmininarDetalle() {
        try {
            boolean result = confCreahppAutoFacadeLocal.delete(configCreaAutoDet);
            String msn;
            if (result) {
                configCreaAutoDet = null;
                msn = "La cofiguración fue eliminada correctamente";
                pintarMesaje(msn, FacesMessage.SEVERITY_INFO);
            } else {
                msn = "La cofiguración No fue eliminada";
                pintarMesaje(msn, FacesMessage.SEVERITY_ERROR);
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en elmininarDetalle. No fue posible eliminar la configuracion. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en elmininarDetalle. No fue posible eliminar la configuracion. ", e, LOGGER);
        }
    }

    /**
     * Crea la configuracion para una ciudad. Permite crear los datos de
     * configuracion para la creacion automatica de un HHPP en la creacion de la
     * solicitud para una ciudad determinada.
     *
     * @author Johnnatan Ortiz
     */
    public void crearDetalle() {
        try {
            configCreaAutoDet = confCreahppAutoFacadeLocal.create(configCreaAutoDet);
            if (configCreaAutoDet != null
                    && configCreaAutoDet.getId() != null) {
                String msn = "La cofiguración fue creada correctamente";
                pintarMesaje(msn, FacesMessage.SEVERITY_INFO);
            } else {
                String msn = "La cofiguración No fue creada";
                pintarMesaje(msn, FacesMessage.SEVERITY_ERROR);
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en crearDetalle. La cofiguración No fue creada. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en crearDetalle. La cofiguración No fue creada. ", e, LOGGER);
        }
    }

    /**
     * Permite pintar un mesaje. Permite pintar un mensaje en la pantalla para
     * notificar al usuario sobre alguna accion realizada.
     *
     * @author Johnnatan Ortiz
     * @param msn mensaje a pintar.
     * @param severity categoria del mensaje.
     */
    private void pintarMesaje(String msn, Severity severity) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, msn, ""));
    }

    /**
     * Get the value of ciudadSelected
     *
     * @return the value of ciudadSelected
     */
    public String getCiudadSelected() {
        return ciudadSelected;
    }

    /**
     * Set the value of ciudadSelected
     *
     * @param ciudadSelected new value of ciudadSelected
     */
    public void setCiudadSelected(String ciudadSelected) {
        this.ciudadSelected = ciudadSelected;
    }

    /**
     * Get the value of dptoSelected
     *
     * @return the value of dptoSelected
     */
    public String getDptoSelected() {
        return dptoSelected;
    }

    /**
     * Set the value of dptoSelected
     *
     * @param dptoSelected new value of dptoSelected
     */
    public void setDptoSelected(String dptoSelected) {
        this.dptoSelected = dptoSelected;
    }

    /**
     * Get the value of ciudades
     *
     * @return the value of ciudades
     */
    public List<GeograficoPoliticoMgl> getCiudades() {
        return ciudades;
    }

    /**
     * Set the value of ciudades
     *
     * @param ciudades new value of ciudades
     */
    public void setCiudades(List<GeograficoPoliticoMgl> ciudades) {
        this.ciudades = ciudades;
    }

    /**
     * Get the value of dptos
     *
     * @return the value of dptos
     */
    public List<GeograficoPoliticoMgl> getDptos() {
        return dptos;
    }

    /**
     * Set the value of dptos
     *
     * @param dptos new value of dptos
     */
    public void setDptos(List<GeograficoPoliticoMgl> dptos) {
        this.dptos = dptos;
    }

    /**
     * Get the value of configCiudadesList
     *
     * @return the value of configCiudadesList
     */
    public List<ConfCreahppAuto> getConfigCiudadesList() {
        return configCiudadesList;
    }

    /**
     * Set the value of configCiudadesList
     *
     * @param configCiudadesList new value of configCiudadesList
     */
    public void setConfigCiudadesList(List<ConfCreahppAuto> configCiudadesList) {
        this.configCiudadesList = configCiudadesList;
    }

    /**
     * Get the value of configCreaAutoDet
     *
     * @return the value of configCreaAutoDet
     */
    public ConfCreahppAuto getConfigCreaAutoDet() {
        return configCreaAutoDet;
    }

    /**
     * Set the value of configCreaAutoDet
     *
     * @param configCreaAutoDet new value of configCreaAutoDet
     */
    public void setConfigCreaAutoDet(ConfCreahppAuto configCreaAutoDet) {
        this.configCreaAutoDet = configCreaAutoDet;
    }

    /**
     * Get the value of confAutoTable
     *
     * @return the value of confAutoTable
     */
    public HtmlDataTable getConfAutoTable() {
        return confAutoTable;
    }

    /**
     * Set the value of confAutoTable
     *
     * @param confAutoTable new value of confAutoTable
     */
    public void setConfAutoTable(HtmlDataTable confAutoTable) {
        this.confAutoTable = confAutoTable;
    }

    // Actions Paging--------------------------------------------------------------------------
    public void pageFirst() {
        confAutoTable.setFirst(0);
    }

    public void pagePrevious() {
        confAutoTable.setFirst(confAutoTable.getFirst() - confAutoTable.getRows());
    }

    public void pageNext() {
        confAutoTable.setFirst(confAutoTable.getFirst() + confAutoTable.getRows());
    }

    public void pageLast() {
        int count = confAutoTable.getRowCount();
        int rows = confAutoTable.getRows();
        confAutoTable.setFirst(count - ((count % rows != 0) ? count % rows : rows));
    }

}
