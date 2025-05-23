package co.com.claro.mgl.mbeans.cm;

import co.com.claro.mgl.dtos.FiltroConsultaViabilidadHhppDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtConvergenciaViabilidadHhppMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoBasicaMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtConvergenciaViabilidadHhppMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
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
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@ManagedBean(name = "viabilidadHhppBean")
@ViewScoped
public class ViabilidadHhppBean implements Serializable {

    @EJB
    private CmtConvergenciaViabilidadHhppMglFacadeLocal cmtConvergenciaViabilidadFacadeLocal;
    @EJB
    CmtBasicaMglFacadeLocal cmtBasicaMglFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacade;
    @EJB
    private CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMglFacadeLocal;

    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private static final Logger LOGGER = LogManager.getLogger(ViabilidadHhppBean.class);
    private final String FORMULARIO = "VIABILIDADHHPP";
    
    
      //Opciones agregadas para Rol
    private final String BTNCRAVBA = "BTNCRAVBA";
    //Opciones agregadas para Rol
    private final String BTNMODVBA = "BTNMODVBA";
    //Opciones agregadas para Rol
    private final String TBLCRAVBA = "TBLCRAVBA";
    //Opciones agregadas para Rol
    private final String BTNIDVBA = "BTNIDVBA";

    private String usuarioVT = null;
    private int perfilVT = 0;

    private boolean renderDetalle;
    private List<CmtConvergenciaViabilidadHhppMgl> listaViabilidadHHPP;
    private CmtConvergenciaViabilidadHhppMgl viabilidadSelected;
    private int filasPag = ConstantsCM.PAGINACION_QUINCE_FILAS;
    private int actual;
    private String numPagina = "1";
    private List<Integer> paginaList;
    private String pageActual;
    public List<SelectItem> listaEstadosNodos;
    public List<SelectItem> listaEstadosCm;
    public List<SelectItem> listaEstadosHhpp;
    public List<SelectItem> listaOpcionesViabilidad;
    public List<SelectItem> listaEstadosRegistro;
    private SecurityLogin securityLogin;
    private FiltroConsultaViabilidadHhppDto filtroConsultaViabilidadHhppDto = new FiltroConsultaViabilidadHhppDto();

    public ViabilidadHhppBean() {

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
        } catch (IOException ex) {
            LOGGER.error("Se generea error en ViabilidadHhppBean class ...", ex);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al ViabilidadHhppBean. " + e.getMessage(), e, LOGGER);
        }
    }

    @PostConstruct
    public void init() {

        renderDetalle = false;
        cargarListaViabilidad();
        cargarListaEstadosNodos();
        cargarListaEstadosCm();
        cargarListaEstadosHhpp();
        cargarListaEstadosRegistro();
        listaOpcionesViabilidad();

    }

    private void cargarListaViabilidad() {
        try {
            listaViabilidadHHPP = cmtConvergenciaViabilidadFacadeLocal.findAll();
            listInfoByPage(1);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en ViabilidadHhppBean: cargarListaViabilidad(). " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ViabilidadHhppBean: cargarListaViabilidad(). " + e.getMessage(), e, LOGGER);
        }
    }

    private void cargarListaEstadosNodos() {
        listaEstadosNodos = new ArrayList<>();
        SelectItem item = new SelectItem();
        item.setValue("A");
        item.setLabel("Activo");
        listaEstadosNodos.add(item);

        item = new SelectItem();
        item.setValue("I");
        item.setLabel("Inactivo");
        listaEstadosNodos.add(item);

        item = new SelectItem();
        item.setValue("P");
        item.setLabel("Preventa");
        listaEstadosNodos.add(item);
    }

    private void cargarListaEstadosCm() {
        try {
            SelectItem item;
            listaEstadosCm = new ArrayList<>();
            item = new SelectItem();
            item.setValue("NA");
            item.setLabel("NA");
            listaEstadosCm.add(item);
            CmtTipoBasicaMgl cmtTipoBasicaMgl;
            cmtTipoBasicaMgl = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_ESTADO_CUENTA_MATRIZ);
            List<CmtBasicaMgl> listaEstadosCmBasicaMgl = cmtBasicaMglFacadeLocal.findByTipoBasica(cmtTipoBasicaMgl);
            if (listaEstadosCmBasicaMgl != null && !listaEstadosCmBasicaMgl.isEmpty()) {
                for (CmtBasicaMgl basica : listaEstadosCmBasicaMgl) {
                    item = new SelectItem();
                    item.setValue(StringUtils.leftPad(basica.getCodigoBasica(), 4, "0"));
                    item.setLabel(basica.getNombreBasica());
                    listaEstadosCm.add(item);
                }
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en ViabilidadHhppBean: cargarListaEstadosCm(). " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ViabilidadHhppBean: cargarListaEstadosCm(). " + e.getMessage(), e, LOGGER);
        }
    }

    private void cargarListaEstadosHhpp() {
        listaEstadosHhpp = new ArrayList<>();
        SelectItem item = new SelectItem();
        item.setValue("D");
        item.setLabel("DISCONNECT");
        listaEstadosHhpp.add(item);

        item = new SelectItem();
        item.setValue("M");
        item.setLabel("MOVE LETTER");
        listaEstadosHhpp.add(item);

        item = new SelectItem();
        item.setValue("N");
        item.setLabel("NEVER SERV");
        listaEstadosHhpp.add(item);

        item = new SelectItem();
        item.setValue("P");
        item.setLabel("PRE-WIRE");
        listaEstadosHhpp.add(item);

        item = new SelectItem();
        item.setValue("S");
        item.setLabel("SERVICE");
        listaEstadosHhpp.add(item);

        item = new SelectItem();
        item.setValue("X");
        item.setLabel("DIR INCONSISTENTE");
        listaEstadosHhpp.add(item);

        item = new SelectItem();
        item.setValue("E");
        item.setLabel("EXISTS");
        listaEstadosHhpp.add(item);
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
            listaViabilidadHHPP = cmtConvergenciaViabilidadFacadeLocal.
                    findViabilidadHhppPaginacion(filtroConsultaViabilidadHhppDto, page, filasPag);
            actual = page;
            getPageActual();
        } catch (ApplicationException e) {
            String msn = Constant.MSN_PROCESO_FALLO + " " + e.getMessage();
             FacesUtil.mostrarMensajeError(msn+ e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ViabilidadHhppBean: listInfoByPage(). " + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public void pageFirst() {
        try {
            listInfoByPage(1);
        } catch (Exception ex) {
            LOGGER.error("Error al momento de navegar a la primera página. EX000: " + ex.getMessage(), ex);
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
            LOGGER.error("Error al navegar a la página anterior. EX000: " + ex.getMessage(), ex);
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
             FacesUtil.mostrarMensajeError("Error en ViabilidadHhppBean: irPagina(). " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ViabilidadHhppBean: irPagina(). " + e.getMessage(), e, LOGGER);
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
            LOGGER.error("Error al momento de navegar a la página siguiente. EX000: " + ex.getMessage(), ex);
        }
    }

    public void pageLast() {
        try {
            int totalPaginas = getTotalPaginas();
            listInfoByPage(totalPaginas);
        } catch (Exception ex) {
            LOGGER.error("Error al momento de navegar a la última página. EX000: " + ex.getMessage(), ex);
        }
    }

    public int getTotalPaginas() {
        try {
            int pageSol = cmtConvergenciaViabilidadFacadeLocal.getCountFindByAll(filtroConsultaViabilidadHhppDto);
            int totalPaginas = (int) ((pageSol % filasPag != 0) ? (pageSol / filasPag) + 1 : pageSol / filasPag);
            return totalPaginas;
        } catch (ApplicationException e) {
             FacesUtil.mostrarMensajeError("Error en ViabilidadHhppBean: getTotalPaginas(). " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ViabilidadHhppBean: getTotalPaginas(). " + e.getMessage(), e, LOGGER);
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
            FacesUtil.mostrarMensajeError("Error en ViabilidadHhppBean: getPageActual(). " + e.getMessage(), e, LOGGER);
        }
        return pageActual;
    }

    public String irCrearViabilidad() {
        try {
            viabilidadSelected = new CmtConvergenciaViabilidadHhppMgl();
            viabilidadSelected.setEstadoRegistro((short) 1);
            renderDetalle = true;
        } catch (Exception ex) {
            LOGGER.error("Error al momento de navegar a crear viabilidad. EX000: " + ex.getMessage(), ex);
            String msn = Constant.MSN_PROCESO_FALLO + " " + ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));

        }
        return null;
    }

    public String crearViabilidad() {
        try {

            viabilidadSelected = cmtConvergenciaViabilidadFacadeLocal.create(viabilidadSelected, usuarioVT, perfilVT);
            renderDetalle = false;
            listInfoByPage(actual);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Constant.MSN_PROCESO_EXITOSO, ""));
        } catch (ApplicationException e) {
            String msn = Constant.MSN_PROCESO_FALLO ;
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ViabilidadHhppBean: crearViabilidad(). " + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public String actualizarViabilidad() {
        try {
            viabilidadSelected = cmtConvergenciaViabilidadFacadeLocal.update(viabilidadSelected, usuarioVT, perfilVT);
            renderDetalle = false;
            listInfoByPage(actual);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Constant.MSN_PROCESO_EXITOSO, ""));
        } catch (ApplicationException e) {
            String msn = Constant.MSN_PROCESO_FALLO;
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ViabilidadHhppBean: actualizarViabilidad() " + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public String verDetalleViabilidad(CmtConvergenciaViabilidadHhppMgl viabilidad) {
        viabilidadSelected = viabilidad;
        renderDetalle = true;
        return null;
    }

    public String volver() {
        try {
            renderDetalle = false;
        } catch (Exception ex) {
            LOGGER.error("Error al momento de regresar. EX000: " + ex.getMessage(), ex);
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
     * @param opcion String nombre de la opci&oacute;n que realizar&aacute; el
     * componente
     * @return boolean indicador para verificar si se visualizan o no los
     * componentes
     */
    private boolean validarAccion(String opcion) {
        try {
            return ValidacionUtil.validarVisualizacion(FORMULARIO, opcion, cmtOpcionesRolMglFacade, securityLogin);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION+ e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ViabilidadHhppBean: validarAccion(). " + e.getMessage(), e, LOGGER);
        }
        return false;
    }
    
    public void filtrarInfo(){
        listInfoByPage(1);
    }


    public boolean isRenderDetalle() {
        return renderDetalle;
    }

    public void setRenderDetalle(boolean renderDetalle) {
        this.renderDetalle = renderDetalle;
    }

    public List<CmtConvergenciaViabilidadHhppMgl> getListaViabilidadHHPP() {
        return listaViabilidadHHPP;
    }

    public void setListaViabilidadHHPP(List<CmtConvergenciaViabilidadHhppMgl> listaViabilidadHHPP) {
        this.listaViabilidadHHPP = listaViabilidadHHPP;
    }

    public CmtConvergenciaViabilidadHhppMgl getViabilidadSelected() {
        return viabilidadSelected;
    }

    public void setViabilidadSelected(CmtConvergenciaViabilidadHhppMgl viabilidadSelected) {
        this.viabilidadSelected = viabilidadSelected;
    }

    public int getFilasPag() {
        return filasPag;
    }

    public void setFilasPag(int filasPag) {
        this.filasPag = filasPag;
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

    public List<SelectItem> getListaEstadosNodos() {
        return listaEstadosNodos;
    }

    public void setListaEstadosNodos(List<SelectItem> listaEstadosNodos) {
        this.listaEstadosNodos = listaEstadosNodos;
    }

    public List<SelectItem> getListaEstadosCm() {
        return listaEstadosCm;
    }

    public void setListaEstadosCm(List<SelectItem> listaEstadosCm) {
        this.listaEstadosCm = listaEstadosCm;
    }

    public List<SelectItem> getListaEstadosHhpp() {
        return listaEstadosHhpp;
    }

    public void setListaEstadosHhpp(List<SelectItem> listaEstadosHhpp) {
        this.listaEstadosHhpp = listaEstadosHhpp;
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

    public FiltroConsultaViabilidadHhppDto getFiltroConsultaViabilidadHhppDto() {
        return filtroConsultaViabilidadHhppDto;
    }

    public void setFiltroConsultaViabilidadHhppDto(FiltroConsultaViabilidadHhppDto filtroConsultaViabilidadHhppDto) {
        this.filtroConsultaViabilidadHhppDto = filtroConsultaViabilidadHhppDto;
    }
    
    
 
    // Validar Opciones por Rol
    
    public boolean validarOpcionCrearTabla() {
        return validarEdicionRol(TBLCRAVBA);
    }
    
    public boolean validarOpcionCrear() {
        return validarEdicionRol(BTNCRAVBA);
    }
    
    public boolean validarOpcionModificar() {
        return validarEdicionRol(BTNMODVBA);
    }
      
    public boolean validarOpcionLinkID() {
        return validarEdicionRol(BTNIDVBA);
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
            FacesUtil.mostrarMensajeError("Error al OrdenTrabajoGestionarBean. " + e.getMessage(), e, LOGGER);
        }
        return false;
    }

      
      
      
}
