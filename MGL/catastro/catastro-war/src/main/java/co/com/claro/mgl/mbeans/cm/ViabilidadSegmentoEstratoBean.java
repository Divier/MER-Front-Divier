package co.com.claro.mgl.mbeans.cm;

import co.com.claro.mgl.dtos.FiltroConsultaViabilidadSegmentoDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtConvergenciaViabilidadEstratoMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.cm.CmtConvergenciaViabilidadEstratoMgl;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;

@ManagedBean(name = "viabilidadSegmentoEstratoBean")
@ViewScoped
public class ViabilidadSegmentoEstratoBean {

    @EJB
    private CmtConvergenciaViabilidadEstratoMglFacadeLocal cmtConvergenciaViabilidadEstratoMglFacadeLocal;
    @EJB
    private CmtBasicaMglFacadeLocal cmtBasicaMglFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacadeLocal;

    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private static final Logger LOGGER = LogManager.getLogger(ViabilidadSegmentoEstratoBean.class);
    private final String FORMULARIO = "VIABILIDADSEGMENTOESTRATO";

    private String usuarioVT = null;
    private int perfilVT = 0;
    private boolean renderDetalle;
    private List<CmtConvergenciaViabilidadEstratoMgl> listaViabilidadEstratos;
    private CmtConvergenciaViabilidadEstratoMgl viabilidadSelected;
    private int actual;
    private String numPagina = "1";
    private List<Integer> paginaList;
    private String pageActual;
    public List<SelectItem> listaSegmentos;
    public List<SelectItem> listaEstratos;
    public List<SelectItem> listaOpcionesViabilidad;
    public List<SelectItem> listaEstadosRegistro;
    private SecurityLogin securityLogin;
    private FiltroConsultaViabilidadSegmentoDto filtroConsultaViabilidadSegmentoDto = new FiltroConsultaViabilidadSegmentoDto();
    private final String ROLOPCIDVIASEG = "ROLOPCIDVIASEG";
    private final String ROLOPCCREATEVIASEG = "ROLOPCCREATEVIASEG";

    public ViabilidadSegmentoEstratoBean() {

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
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error al ViabilidadSegmentoEstratoBean. " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al ViabilidadSegmentoEstratoBean. " + e.getMessage(), e, LOGGER);
        }
    }

    @PostConstruct
    public void init() {

        renderDetalle = false;
        cargarListaViabilidad();
        cargarListaSegmentos();
        cargarListaEstratos();
        cargarListaEstadosRegistro();
        listaOpcionesViabilidad();

    }

    private void cargarListaViabilidad() {
        try {
            listaViabilidadEstratos = cmtConvergenciaViabilidadEstratoMglFacadeLocal.findAll();
            listInfoByPage(1);
        } catch (ApplicationException e) {
           FacesUtil.mostrarMensajeError("Error en ViabilidadSegmentoEstratoBean: cargarListaViabilidad(). " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ViabilidadSegmentoEstratoBean: cargarListaViabilidad(). " + e.getMessage(), e, LOGGER);
        }
    }

    private void cargarListaSegmentos() {
        listaSegmentos = new ArrayList<>();
        SelectItem item = new SelectItem();
        item.setValue("PY");
        item.setLabel("Pyme");
        listaSegmentos.add(item);

        item = new SelectItem();
        item.setValue("SH");
        item.setLabel("Soho");
        listaSegmentos.add(item);

        item = new SelectItem();
        item.setValue("RE");
        item.setLabel("Residencial");
        listaSegmentos.add(item);
    }

    private void cargarListaEstratos() {
        listaEstratos = new ArrayList<>();
        SelectItem item = new SelectItem();
        item.setValue("1");
        item.setLabel("1");
        listaEstratos.add(item);

        item = new SelectItem();
        item.setValue("2");
        item.setLabel("2");
        listaEstratos.add(item);

        item = new SelectItem();
        item.setValue("3");
        item.setLabel("3");
        listaEstratos.add(item);

        item = new SelectItem();
        item.setValue("4");
        item.setLabel("4");
        listaEstratos.add(item);

        item = new SelectItem();
        item.setValue("5");
        item.setLabel("5");
        listaEstratos.add(item);

        item = new SelectItem();
        item.setValue("6");
        item.setLabel("6");
        listaEstratos.add(item);

        item = new SelectItem();
        item.setValue("NG");
        item.setLabel("NG");
        listaEstratos.add(item);

        item = new SelectItem();
        item.setValue("NR");
        item.setLabel("NR");
        listaEstratos.add(item);
    }

    private void cargarListaEstadosRegistro() {
        listaEstadosRegistro = new ArrayList<>();
        SelectItem item = new SelectItem();
        item.setValue(0);
        item.setLabel("Inactivo");
        listaEstadosRegistro.add(item);

        item = new SelectItem();
        item.setValue(1);
        item.setLabel("Activo");
        listaEstadosRegistro.add(item);
    }

    private void listaOpcionesViabilidad() {
        listaOpcionesViabilidad = new ArrayList<>();
        SelectItem item = new SelectItem();
        item.setValue("SI");
        item.setLabel("SI");
        listaOpcionesViabilidad.add(item);

        item = new SelectItem();
        item.setValue("NO");
        item.setLabel("NO");
        listaOpcionesViabilidad.add(item);
    }

    public String buscarValorLista(List<SelectItem> lista, String valor) {
        if (lista != null && !lista.isEmpty()) {
            for (SelectItem item : lista) {
                if (item.getValue().toString().equals(valor)) {
                    return item.getLabel();
                }
            }
        }

        return "";
    }

    private String listInfoByPage(int page) {
        try {
            listaViabilidadEstratos =
                    cmtConvergenciaViabilidadEstratoMglFacadeLocal.findViabilidadSegEstratoPaginacion(filtroConsultaViabilidadSegmentoDto,page, ConstantsCM.PAGINACION_QUINCE_FILAS);
            actual = page;
            getPageActual();
        } catch (ApplicationException e) {
            String msn = Constant.MSN_PROCESO_FALLO + " " + e.getMessage();
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ViabilidadSegmentoEstratoBean: listInfoByPage(). " + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public void pageFirst() {
        try {
            listInfoByPage(1);
        } catch (Exception ex) {
            LOGGER.error("Error al ir a la primera página. EX000 " + ex.getMessage(), ex);
        }
    }

    public void pagePrevious() {
        try {
            int totalPaginas = getTotalPaginas();
            int nuevaPageActual = actual - 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            if (nuevaPageActual <= 0) {
                nuevaPageActual = 1;
            }
            listInfoByPage(nuevaPageActual);
        } catch (Exception ex) {
            LOGGER.error("Error al ir a la página anterior. EX000 " + ex.getMessage(), ex);
        }
    }

    public void irPagina() {
        try {
            int totalPaginas = getTotalPaginas();
            int nuevaPageActual = Integer.parseInt(numPagina);
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            listInfoByPage(nuevaPageActual);
        } catch (NumberFormatException e) {
            FacesUtil.mostrarMensajeError("Error en ViabilidadSegmentoEstratoBean: irPagina(). " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ViabilidadSegmentoEstratoBean: irPagina(). " + e.getMessage(), e, LOGGER);
        }
    }

    public void pageNext() {
        try {
            int totalPaginas = getTotalPaginas();
            int nuevaPageActual = actual + 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            listInfoByPage(nuevaPageActual);
        } catch (Exception ex) {
            LOGGER.error("Error al ir a la siguiente página. EX000 " + ex.getMessage(), ex);
        }
    }

    public void pageLast() {
        try {
            int totalPaginas = getTotalPaginas();
            listInfoByPage(totalPaginas);
        } catch (Exception ex) {
            LOGGER.error("Error al ir a la última página. EX000 " + ex.getMessage(), ex);
        }
    }

    public int getTotalPaginas() {
        try {
            int pageSol = cmtConvergenciaViabilidadEstratoMglFacadeLocal.getCountFindByAll(filtroConsultaViabilidadSegmentoDto);
            int totalPaginas = (int) ((pageSol % ConstantsCM.PAGINACION_QUINCE_FILAS != 0) ? (pageSol / ConstantsCM.PAGINACION_QUINCE_FILAS) + 1 : pageSol / ConstantsCM.PAGINACION_QUINCE_FILAS);
            return totalPaginas;
        } catch (ApplicationException e) {
             FacesUtil.mostrarMensajeError("Error en ViabilidadSegmentoEstratoBean: getTotalPaginas(). " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ViabilidadSegmentoEstratoBean: getTotalPaginas(). " + e.getMessage(), e, LOGGER);
        }
        return 1;
    }

    public String getPageActual() {
        try {
            paginaList = new ArrayList<>();
            int totalPaginas = getTotalPaginas();
            for (int i = 1; i <= totalPaginas; i++) {
                paginaList.add(i);
            }
            pageActual = String.valueOf(actual) + " de "
                    + String.valueOf(totalPaginas);

            if (numPagina == null) {
                numPagina = "1";
            }
            numPagina = String.valueOf(actual);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ViabilidadSegmentoEstratoBean: getPageActual(). " + e.getMessage(), e, LOGGER);
        }
        return pageActual;
    }

    public String irCrearViabilidad() {
        try {
            viabilidadSelected = new CmtConvergenciaViabilidadEstratoMgl();
            viabilidadSelected.setEstadoRegistro((short) 1);
            renderDetalle = true;
        } catch (Exception ex) {
            LOGGER.error("Error al crear viavilidad. EX000 " + ex.getMessage(), ex);
            String msn = Constant.MSN_PROCESO_FALLO + " " + ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));

        }
        return null;
    }

    public String crearViabilidad() {
        try {

            viabilidadSelected = cmtConvergenciaViabilidadEstratoMglFacadeLocal.create(viabilidadSelected, usuarioVT, perfilVT);
            renderDetalle = false;
            listInfoByPage(actual);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Constant.MSN_PROCESO_EXITOSO, ""));
        } catch (ApplicationException e) {
            String msn = Constant.MSN_PROCESO_FALLO;
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ViabilidadSegmentoEstratoBean: crearViabilidad(). " + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public String actualizarViabilidad() {
        try {
            viabilidadSelected = cmtConvergenciaViabilidadEstratoMglFacadeLocal.update(viabilidadSelected, usuarioVT, perfilVT);
            renderDetalle = false;
            listInfoByPage(actual);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Constant.MSN_PROCESO_EXITOSO, ""));
        } catch (ApplicationException e) {
           String msn = Constant.MSN_PROCESO_FALLO ;
           FacesUtil.mostrarMensajeError(msn+ e.getMessage(), e, LOGGER);

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ViabilidadSegmentoEstratoBean: actualizarViabilidad(). " + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public String verDetalleViabilidad(CmtConvergenciaViabilidadEstratoMgl viabilidad) {
        viabilidadSelected = viabilidad;
        renderDetalle = true;
        return null;
    }

    public String volver() {
        try {
            renderDetalle = false;
        } catch (Exception ex) {
            LOGGER.error("Error al volver. EX000 " + ex.getMessage(), ex);
            String msn = Constant.MSN_PROCESO_FALLO + " " + ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));

        }
        return null;
    }

    /**
     * 
     * @return 
     */
    public boolean validarEdicion() {
        return validarAccion(ValidacionUtil.OPC_EDICION);
    }

    /**
     * 
     * @return 
     */
    public boolean validarCreacion() {
        return validarAccion(ValidacionUtil.OPC_CREACION);
    }

    /**
     * M&eacute;todo para validar las acciones a realizar en el formulario
     * 
     * @param opcion String nombre de la opci&oacute;n que realizar&aacute; el componente
     * @return boolean indicador para verificar si se visualizan o no los componentes
     */
    private boolean validarAccion(String opcion) {
        try {
            return ValidacionUtil.validarVisualizacion(FORMULARIO, opcion, cmtOpcionesRolMglFacadeLocal, securityLogin);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION + e.getMessage(), e, LOGGER);
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ViabilidadSegmentoEstratoBean: validarAccion(). " + e.getMessage(), e, LOGGER);
        }
        return false;
    }
    
    public void filtrarInfo() {
        listInfoByPage(1);
    }
    
    public boolean validarIdRol() {
        return validarEdicion(ROLOPCIDVIASEG);
    }
    
    public boolean validarCrearRol() {
        return validarEdicion(ROLOPCCREATEVIASEG);
    }
    
    private boolean validarEdicion(String formulario) {
        try {
            boolean tieneRolPermitido = ValidacionUtil.validarVisualizacion(formulario, ValidacionUtil.OPC_EDICION, cmtOpcionesRolMglFacadeLocal, securityLogin);
            return tieneRolPermitido;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION + e.getMessage(), e, LOGGER);
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al OrdenTrabajoGestionarBean. " + e.getMessage(), e, LOGGER);
        }
        return false;
    }


    public boolean isRenderDetalle() {
        return renderDetalle;
    }

    public void setRenderDetalle(boolean renderDetalle) {
        this.renderDetalle = renderDetalle;
    }

    public List<CmtConvergenciaViabilidadEstratoMgl> getListaViabilidadEstratos() {
        return listaViabilidadEstratos;
    }

    public void setListaViabilidadEstratos(List<CmtConvergenciaViabilidadEstratoMgl> listaViabilidadEstratos) {
        this.listaViabilidadEstratos = listaViabilidadEstratos;
    }

    public CmtConvergenciaViabilidadEstratoMgl getViabilidadSelected() {
        return viabilidadSelected;
    }

    public void setViabilidadSelected(CmtConvergenciaViabilidadEstratoMgl viabilidadSelected) {
        this.viabilidadSelected = viabilidadSelected;
    }

    public int getFilasPag() {
        return ConstantsCM.PAGINACION_QUINCE_FILAS;
    }

    public int getActual() {
        return actual;
    }

    public void setActual(int actual) {
        this.actual = actual;
    }

    public String getNumPagina() {
        return numPagina;
    }

    public void setNumPagina(String numPagina) {
        this.numPagina = numPagina;
    }

    public List<Integer> getPaginaList() {
        return paginaList;
    }

    public void setPaginaList(List<Integer> paginaList) {
        this.paginaList = paginaList;
    }

    public List<SelectItem> getListaSegmentos() {
        return listaSegmentos;
    }

    public void setListaSegmentos(List<SelectItem> listaSegmentos) {
        this.listaSegmentos = listaSegmentos;
    }

    public List<SelectItem> getListaOpcionesViabilidad() {
        return listaOpcionesViabilidad;
    }

    public void setListaOpcionesViabilidad(List<SelectItem> listaOpcionesViabilidad) {
        this.listaOpcionesViabilidad = listaOpcionesViabilidad;
    }

    public List<SelectItem> getListaEstadosRegistro() {
        return listaEstadosRegistro;
    }

    public void setListaEstadosRegistro(List<SelectItem> listaEstadosRegistro) {
        this.listaEstadosRegistro = listaEstadosRegistro;
    }

    public List<SelectItem> getListaEstratos() {
        return listaEstratos;
    }

    public void setListaEstratos(List<SelectItem> listaEstratos) {
        this.listaEstratos = listaEstratos;
    }

    public FiltroConsultaViabilidadSegmentoDto getFiltroConsultaViabilidadSegmentoDto() {
        return filtroConsultaViabilidadSegmentoDto;
    }

    public void setFiltroConsultaViabilidadSegmentoDto(FiltroConsultaViabilidadSegmentoDto filtroConsultaViabilidadSegmentoDto) {
        this.filtroConsultaViabilidadSegmentoDto = filtroConsultaViabilidadSegmentoDto;
    }

}
