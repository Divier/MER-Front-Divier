/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.cm;

import co.com.claro.mgl.constantes.cobertura.Constants;
import co.com.claro.mgl.dtos.FiltroConsultaHomologacionRazonesDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.HomologacionRazonesMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.HomologacionRazonesMgl;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author bocanegravm
 */
@ManagedBean(name = "homologacionRazonesMglBean")
@ViewScoped
public class HomologacionRazonesMglBean implements Serializable {

    @EJB
    private HomologacionRazonesMglFacadeLocal homologacionRazonesMglFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacadeLocal;

    private static final Logger LOGGER = LogManager.getLogger(HomologacionRazonesMglBean.class);
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private String usuarioVT = null;
    private Integer perfilVt;
    private List<HomologacionRazonesMgl> homologacionRazonesMglList;
    private int filasPag15 = Constants.PAGINACION_QUINCE_FILAS;
    private HomologacionRazonesMgl homologacionRazonesMglSelected;
    private HomologacionRazonesMgl homologacionRazonesMgl;
    private boolean mostrarAdminHomologaciones;
    private boolean mostrarListaHomolagaciones;
    private boolean botonCrear;
    private boolean botonMod;
    private SecurityLogin securityLogin;
    private final String ROLOPCNEWHOM = "ROLOPCNEWHOM";
    private final String ROLOPCIDHOM = "ROLOPCIDHOM";
    private final String ROLOPCDELHOM = "ROLOPCDELHOM";
    
    private FiltroConsultaHomologacionRazonesDto filtroConsultaHomologacionesRazonesDto = new FiltroConsultaHomologacionRazonesDto();

    /**
     * Creates a new instance of HomologacionRazonesMglBean
     */
    public HomologacionRazonesMglBean() {
        try {
            securityLogin = new SecurityLogin(facesContext);
            if (!securityLogin.isLogin()) {
                if (!response.isCommitted()) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                }
                return;
            }
            usuarioVT = securityLogin.getLoginUser();
            perfilVt = securityLogin.getPerfilUsuario();

        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error en HomologacionRazonesMglBean:" + e.getMessage() + " ", e, LOGGER);

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en HomologacionRazonesMglBean: " + e.getMessage() + " ", e, LOGGER);
        }
    }

    @PostConstruct
    public void init() {
        try {
            homologacionRazonesMglList = homologacionRazonesMglFacadeLocal.findAll();
            mostrarListaHomolagaciones = true;
        } catch (ApplicationException ex) {
            FacesUtil.mostrarMensajeError("Error en HomologacionRazonesMglBean: " + ex.getMessage() + " ", ex, LOGGER);
        }
    }

    public void irHomologacionDetalle(HomologacionRazonesMgl homologacionRazonesMglSel) throws ApplicationException {

        if (homologacionRazonesMglSel != null) {
            homologacionRazonesMgl = homologacionRazonesMglSel;
            mostrarAdminHomologaciones = true;
            mostrarListaHomolagaciones = false;
            botonCrear = false;
            botonMod = true;
        }

    }

    public void eliminarHomologacionList(HomologacionRazonesMgl homologacionRazonesMglElimina) throws ApplicationException {

        boolean elimina = homologacionRazonesMglFacadeLocal.delete(homologacionRazonesMglElimina, usuarioVT, perfilVt);        
        if (elimina) {
            filtroConsultaHomologacionesRazonesDto = new FiltroConsultaHomologacionRazonesDto();            
            String msnError = "Registro eliminado satisfatoriamente";
            info(msnError);
            homologacionRazonesMglList = homologacionRazonesMglFacadeLocal.findAll();
        } else {
            String msnError = "Ocurrió un error  eliminando el registro";
            error(msnError);
        }
    }

    public void irToCrearHomologacion() throws ApplicationException {

        mostrarListaHomolagaciones = false;
        homologacionRazonesMgl = new HomologacionRazonesMgl();
        mostrarAdminHomologaciones = true;

        botonCrear = true;
        botonMod = false;
    }

    public void crearHomologacion() throws ApplicationException {

        if (homologacionRazonesMgl.getCodRazonOfscMer() == null
                || homologacionRazonesMgl.getCodRazonOfscMer().equalsIgnoreCase("")) {
            String msnError = "Debe ingresar un código o razón OFSC-MER "
                    + " por favor. ";
            warn(msnError);
        } else if (homologacionRazonesMgl.getDescripcionOfscMer()== null
                || homologacionRazonesMgl.getDescripcionOfscMer().equalsIgnoreCase("")) {
            String msnError = "Debe ingresar una descripción del código o resolución de OFSC-MER"
                    + " por favor. ";
            warn(msnError);
        } else if (homologacionRazonesMgl.getCodResOnix() == null
                || homologacionRazonesMgl.getCodResOnix().equalsIgnoreCase("")) {
            String msnError = "Debe ingresar un código o resolución ONYX "
                    + " por favor. ";
            warn(msnError);
        } else if (!isNumeric(homologacionRazonesMgl.getCodResOnix())) {
            String msnError = "Debe ingresar un código o resolución ONYX numérico";
            warn(msnError);
        } else if (homologacionRazonesMgl.getCodResOnix().length() > 8) {
            String msnError = "El código ONYX no debe ser mayor a 8 caracteres";
            warn(msnError);
        } else if (homologacionRazonesMgl.getDescripcionOnyx()== null
                || homologacionRazonesMgl.getDescripcionOnyx().equalsIgnoreCase("")) {
            String msnError = "Debe ingresar una descripción del código o resolución de ONYX"
                    + " por favor. ";
            warn(msnError);
        } else {
            //Busco que no haya una homologacion con el mismo codigo OFSC
            HomologacionRazonesMgl homologacionRazonesMglOfsc = homologacionRazonesMglFacadeLocal.
                    findHomologacionByCodigoOFSC(homologacionRazonesMgl.getCodRazonOfscMer());

            if (homologacionRazonesMglOfsc != null
                    && homologacionRazonesMglOfsc.getHomologacionRazonesId() != null) {
                String msnError = "Ya existe una homologación con el código OFSC: "
                        + "" + homologacionRazonesMgl.getCodRazonOfscMer() + "";
                warn(msnError);
            } else {
                //Busco que no haya una homologacion con el mismo codigo OFSC
                HomologacionRazonesMgl homologacionRazonesMglOnix = homologacionRazonesMglFacadeLocal.
                        findHomologacionByCodigoONIX(homologacionRazonesMgl.getCodResOnix());
                if (homologacionRazonesMglOnix != null
                        && homologacionRazonesMglOnix.getHomologacionRazonesId() != null) {
                    String msnError = "Ya existe una homologación con el código ONYX: "
                            + "" + homologacionRazonesMgl.getCodResOnix()+ "";
                    warn(msnError);
                } else {
                    homologacionRazonesMgl = homologacionRazonesMglFacadeLocal.create(homologacionRazonesMgl, usuarioVT, perfilVt);

                    if (homologacionRazonesMgl.getHomologacionRazonesId() != null) {
                        String msnError = "Homologación creada satisfactoriamente";
                        info(msnError);
                        homologacionRazonesMglList = homologacionRazonesMglFacadeLocal.findAll();
                        mostrarAdminHomologaciones = false;

                        mostrarListaHomolagaciones = true;
                    } else {
                        String msnError = "Ocurrió un error creando el Homologación";
                        error(msnError);
                    }
                }
            }
        }
    }

    public void modificarHomologacion() throws ApplicationException {

        if (homologacionRazonesMgl.getCodRazonOfscMer() == null
                || homologacionRazonesMgl.getCodRazonOfscMer().equalsIgnoreCase("")) {
            String msnError = "Debe ingresar un código o razón OFSC-MER "
                    + " por favor. ";
            warn(msnError);
        } else if (homologacionRazonesMgl.getDescripcionOfscMer()== null
                || homologacionRazonesMgl.getDescripcionOfscMer().equalsIgnoreCase("")) {
            String msnError = "Debe ingresar una descripción del código o resolución de OFSC-MER"
                    + " por favor. ";
            warn(msnError);
        } else if (homologacionRazonesMgl.getCodResOnix() == null
                || homologacionRazonesMgl.getCodResOnix().equalsIgnoreCase("")) {
            String msnError = "Debe ingresar un código o resolución ONYX "
                    + " por favor. ";
            warn(msnError);
        } else if (!isNumeric(homologacionRazonesMgl.getCodResOnix())) {
            String msnError = "Debe ingresar un código o resolución ONYX numérico";
            warn(msnError);
        } else if (homologacionRazonesMgl.getCodResOnix().length() > 8) {
            String msnError = "El código ONYX no debe ser mayor a 8 caracteres";
            warn(msnError);
        } else if (homologacionRazonesMgl.getDescripcionOnyx()== null
                || homologacionRazonesMgl.getDescripcionOnyx().equalsIgnoreCase("")) {
            String msnError = "Debe ingresar una descripción del código o resolución de ONYX"
                    + " por favor. ";
            warn(msnError);
        } else {
            //Busco que no haya una homologacion con el mismo codigo OFSC
            HomologacionRazonesMgl homologacionRazonesMglOfsc = homologacionRazonesMglFacadeLocal.
                    findHomologacionByCodigoOFSCAndId(homologacionRazonesMgl.getCodRazonOfscMer(),
                            homologacionRazonesMgl.getHomologacionRazonesId());

            if (homologacionRazonesMglOfsc != null
                    && homologacionRazonesMglOfsc.getHomologacionRazonesId() != null) {
                String msnError = "Ya existe una homologación con el código OFSC: "
                        + "" + homologacionRazonesMgl.getCodRazonOfscMer() + "";
                warn(msnError);
            } else {
                //Busco que no haya una homologacion con el mismo codigo OFSC
                HomologacionRazonesMgl homologacionRazonesMglOnix = homologacionRazonesMglFacadeLocal.
                        findHomologacionByCodigoONIXAndId(homologacionRazonesMgl.getCodResOnix(),
                                homologacionRazonesMgl.getHomologacionRazonesId());
                if (homologacionRazonesMglOnix != null
                        && homologacionRazonesMglOnix.getHomologacionRazonesId() != null) {
                    String msnError = "Ya existe una homologación con el código ONYX: "
                            + "" + homologacionRazonesMgl.getCodResOnix() + "";
                    warn(msnError);
                } else {
                    
                    homologacionRazonesMgl = homologacionRazonesMglFacadeLocal.update(homologacionRazonesMgl, usuarioVT, perfilVt);

                    String msnError = "Homologación modificada satisfactoriamente";
                    info(msnError);
                    homologacionRazonesMglList = homologacionRazonesMglFacadeLocal.findAll();
                    mostrarAdminHomologaciones = false;
                    mostrarListaHomolagaciones = true;
                }
            }

        }
    }

    public void volverPanelListHomologaciones() {

        mostrarAdminHomologaciones = false;
        mostrarListaHomolagaciones = true;
    }

    /**
     * <b>Mensaje de Advertencia</b>.
     *
     * M&eacute;todo que muestra el mensaje de advertencia, que llega como
     * par&aacute;metro, en la vista.
     *
     * @param mensaje Mensaje a mostrar en pantalla.
     */
    private void warn(String mensaje) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN, mensaje, ""));
    }

    /**
     * <b>Mensaje de Informaci&oacute;n</b>.
     *
     * metodo que muestra el mensaje de informacion, que llega como parametro,
     * en la vista
     *
     * @author Victor Bocanegra
     * @param mensaje
     */
    public void info(String mensaje) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje, ""));
    }

    /**
     * <b>Mensaje de Error</b>.
     *
     * M&eacute;todo que muestra el mensaje de error, que llega como
     * par&aacute;metro, en la vista.
     *
     * @param mensaje Mensaje a mostrar en pantalla.
     */
    private void error(String mensaje) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje, ""));
    }
    
    public boolean isNumeric(String cadena) {
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe) {
            LOGGER.error("Error en isNumeric. " + nfe.getMessage(), nfe);
            return false;
        } catch (Exception nfe) {
            LOGGER.error("Error en isNumeric. " + nfe.getMessage(), nfe);
            return false;
        }
    }
    
    public void filtrarInfo() throws ApplicationException {
        try {
            homologacionRazonesMglList = homologacionRazonesMglFacadeLocal.findHomologacionesByFiltro(filtroConsultaHomologacionesRazonesDto);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(Constant.MSN_PROCESO_FALLO + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en  ArrendatarioMglBean " + e.getMessage(), e, LOGGER);
        }
    }
    
    public boolean validarNuevoRol() {
        return validarEdicion(ROLOPCNEWHOM);
    }
    
    public boolean validarIdRol() {
        return validarEdicion(ROLOPCIDHOM);
    }
    
    public boolean validarEliminarRol() {
        return validarEdicion(ROLOPCDELHOM);
    }

    private boolean validarEdicion(String formulario) {
        try {
            boolean tieneRolPermitido = ValidacionUtil.validarVisualizacion(formulario, ValidacionUtil.OPC_EDICION, 
                    cmtOpcionesRolMglFacadeLocal, securityLogin);
            return tieneRolPermitido;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION + e.getMessage(), e, LOGGER);
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al OrdenTrabajoGestionarBean. " + e.getMessage(), e, LOGGER);
        }
        return false;
    }

    public int getFilasPag15() {
        return filasPag15;
    }

    public void setFilasPag15(int filasPag15) {
        this.filasPag15 = filasPag15;
    }

    public boolean isMostrarAdminHomologaciones() {
        return mostrarAdminHomologaciones;
    }

    public void setMostrarAdminHomologaciones(boolean mostrarAdminHomologaciones) {
        this.mostrarAdminHomologaciones = mostrarAdminHomologaciones;
    }

    public boolean isMostrarListaHomolagaciones() {
        return mostrarListaHomolagaciones;
    }

    public void setMostrarListaHomolagaciones(boolean mostrarListaHomolagaciones) {
        this.mostrarListaHomolagaciones = mostrarListaHomolagaciones;
    }

    public boolean isBotonCrear() {
        return botonCrear;
    }

    public void setBotonCrear(boolean botonCrear) {
        this.botonCrear = botonCrear;
    }

    public boolean isBotonMod() {
        return botonMod;
    }

    public void setBotonMod(boolean botonMod) {
        this.botonMod = botonMod;
    }

    public List<HomologacionRazonesMgl> getHomologacionRazonesMglList() {
        return homologacionRazonesMglList;
    }

    public void setHomologacionRazonesMglList(List<HomologacionRazonesMgl> homologacionRazonesMglList) {
        this.homologacionRazonesMglList = homologacionRazonesMglList;
    }

    public HomologacionRazonesMgl getHomologacionRazonesMglSelected() {
        return homologacionRazonesMglSelected;
    }

    public void setHomologacionRazonesMglSelected(HomologacionRazonesMgl homologacionRazonesMglSelected) {
        this.homologacionRazonesMglSelected = homologacionRazonesMglSelected;
    }

    public HomologacionRazonesMgl getHomologacionRazonesMgl() {
        return homologacionRazonesMgl;
    }

    public void setHomologacionRazonesMgl(HomologacionRazonesMgl homologacionRazonesMgl) {
        this.homologacionRazonesMgl = homologacionRazonesMgl;
    }

    public FiltroConsultaHomologacionRazonesDto getFiltroConsultaHomologacionesRazonesDto() {
        return filtroConsultaHomologacionesRazonesDto;
    }

    public void setFiltroConsultaHomologacionesRazonesDto(FiltroConsultaHomologacionRazonesDto filtroConsultaHomologacionesRazonesDto) {
        this.filtroConsultaHomologacionesRazonesDto = filtroConsultaHomologacionesRazonesDto;
    }  
    
}
