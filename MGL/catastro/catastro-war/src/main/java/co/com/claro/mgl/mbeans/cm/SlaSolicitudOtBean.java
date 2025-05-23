/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.cm;

import co.com.claro.mgl.dtos.FiltroConsultaSlaSolicitudDto;
import co.com.claro.mgl.dtos.FiltroConsultaSolicitudDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoSolicitudMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoSolicitudMgl;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
 * @author cardenaslb
 */
@ManagedBean(name = "slaSolicitudOtBean")
@ViewScoped
public class SlaSolicitudOtBean {

    @EJB
    private CmtTipoSolicitudMglFacadeLocal cmtTipoSolicitudMglFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacade;

    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private static final Logger LOGGER = LogManager.getLogger(SlaSolicitudOtBean.class);
    private final String FORMULARIO = "SLASOL";

    private String usuarioVT = null;
    private int perfilVT = 0;
    private CmtTipoSolicitudMgl cmtTipoSolicitudMgl;
    private String estiloObligatorio = "<font color='red'>*</font>";
    private String message;
    private FiltroConsultaSlaSolicitudDto filtroConsultaSlaSolicitudDto = new FiltroConsultaSlaSolicitudDto();
    private List<CmtTipoSolicitudMgl> listCmtTipoSolicitudMgl;
    private int filasPag = ConstantsCM.PAGINACION_OCHO_FILAS;
    private boolean habilitaObj = false;
    private int actual;
    private String numPagina;
    private boolean pintarPaginado;
    private List<Integer> paginaList;
    private String pageActual;
    private boolean btnActivo = false;
    private SecurityLogin securityLogin;
    private boolean mostrarPanelCrearModtipoSol;
    private boolean mostrarPanelListTipoSol;
    private FiltroConsultaSolicitudDto filtroConsultaSolicitudDto = new FiltroConsultaSolicitudDto();
    
    //Opciones agregadas para Rol
    private final String BTNTIPSOLSLACRE = "BTNTIPSOLSLACRE"; 
    private final String BTNTIPSOLSLACOD = "BTNTIPSOLSLACOD"; 
    private final String BTNTIPSOLSLADEL = "BTNTIPSOLSLADEL"; 

    public SlaSolicitudOtBean() {
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
            cmtTipoSolicitudMgl = new CmtTipoSolicitudMgl();

        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error al SlaSolicitudBean. " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al SlaSolicitudBean. " + e.getMessage(), e, LOGGER);
        }
    }

    @PostConstruct
    public void cargarListas() {
        try {

            pintarPaginado = true;
            listInfoByPage(1);
        } catch (Exception e) {
            LOGGER.error("Error al cargar listas. EX000 " + e.getMessage(), e);
        }
    }

    public void goActualizar(CmtTipoSolicitudMgl item) {
        try {

            cmtTipoSolicitudMgl = item;
            btnActivo = false;
            mostrarPanelCrearModtipoSol=true;
            mostrarPanelListTipoSol = false;
            
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg, ex);
        }
    }
   
    public boolean validarAsn(int asn, int asnAviso) {
        if (asnAviso > asn) {

            return true;
        } else {
            if (asnAviso == asn) {
                return true;
            }

        }
        return false;
    }

    public void crearSlaMgl() {
        try {
            if (cmtTipoSolicitudMgl == null || cmtTipoSolicitudMgl == null) {
                String msn = "Todos los campos son obligatorios";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return;
            }
            if (cmtTipoSolicitudMgl.getNombreTipo() == null || cmtTipoSolicitudMgl.getNombreTipo().isEmpty()) {
                String msn = "El campo Nombre Tipo es obligatorio";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return;
            }
             if (cmtTipoSolicitudMgl.getCreacionRol() == null || cmtTipoSolicitudMgl.getCreacionRol().isEmpty()) {
                String msn = "El campo Creacion de Rol es obligatorio";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return;
            }
             if (cmtTipoSolicitudMgl.getGestionRol() == null || cmtTipoSolicitudMgl.getGestionRol().isEmpty()) {
                String msn = "El campo Gestion Tipo es obligatorio";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return;
            }
             
            cmtTipoSolicitudMgl.setCodigoTipo("1");
            cmtTipoSolicitudMgl.setAbreviatura(cmtTipoSolicitudMgl.getNombreTipo().length() > 49 ? cmtTipoSolicitudMgl.getNombreTipo().substring(0,49) : cmtTipoSolicitudMgl.getNombreTipo());
            cmtTipoSolicitudMgl.setFechaCreacion(new Date());
            cmtTipoSolicitudMgl.setUsuarioCreacion(usuarioVT);
            cmtTipoSolicitudMgl.setPerfilCreacion(perfilVT);
            cmtTipoSolicitudMgl.setFechaEdicion(new Date());
            cmtTipoSolicitudMgl.setCreacionRol(cmtTipoSolicitudMgl.getCreacionRol());
            cmtTipoSolicitudMgl.setGestionRol(cmtTipoSolicitudMgl.getGestionRol());
            cmtTipoSolicitudMgl.setEstadoRegistro(cmtTipoSolicitudMgl.getEstadoRegistro());
            cmtTipoSolicitudMgl.setAns(cmtTipoSolicitudMgl.getAns());
            cmtTipoSolicitudMgl.setAnsAviso(cmtTipoSolicitudMgl.getAnsAviso());
            cmtTipoSolicitudMgl.setAplicacion("CM");

            if (validarAsn(cmtTipoSolicitudMgl.getAns(), cmtTipoSolicitudMgl.getAnsAviso())) {
                String msn = "El valor de  Sla Aviso no puede ser mayor o igual a Sla";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            } else {
                cmtTipoSolicitudMgl = cmtTipoSolicitudMglFacadeLocal.create(cmtTipoSolicitudMgl);

                if (cmtTipoSolicitudMgl != null) {
                    listInfoByPage(1);
                    limpiarCampos();
                    String msn = "Proceso exitoso";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                }

            }

        } catch (ApplicationException e) {
            String msn = "Proceso falló: ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en SlaSolicitudBean:crearSlaMgl(). " + e.getMessage(), e, LOGGER);
        }
    }

    public void actualizarSlaMgl() {
        try {
            if (cmtTipoSolicitudMgl == null || cmtTipoSolicitudMgl == null) {
                String msn = "Todos los campos son obligatorios";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return;
            }
            
             if (cmtTipoSolicitudMgl.getNombreTipo() == null || cmtTipoSolicitudMgl.getNombreTipo().isEmpty()) {
                String msn = "El campo Nombre Tipo es obligatorio";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return;
            }
             if (cmtTipoSolicitudMgl.getCreacionRol() == null || cmtTipoSolicitudMgl.getCreacionRol().isEmpty()) {
                String msn = "El campo Creacion de Rol es obligatorio";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return;
            }
             if (cmtTipoSolicitudMgl.getGestionRol() == null || cmtTipoSolicitudMgl.getGestionRol().isEmpty()) {
                String msn = "El campo Gestion Tipo es obligatorio";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return;
            }
             
            cmtTipoSolicitudMgl.setCodigoTipo("1");
            cmtTipoSolicitudMgl.setEstadoRegistro(cmtTipoSolicitudMgl.getEstadoRegistro());
            cmtTipoSolicitudMgl.setFechaCreacion(cmtTipoSolicitudMgl.getFechaCreacion());
            cmtTipoSolicitudMgl.setUsuarioCreacion(cmtTipoSolicitudMgl.getUsuarioCreacion());
            cmtTipoSolicitudMgl.setPerfilCreacion(cmtTipoSolicitudMgl.getPerfilCreacion());
            cmtTipoSolicitudMgl.setEstadoRegistro(cmtTipoSolicitudMgl.getEstadoRegistro());
            cmtTipoSolicitudMgl.setFechaEdicion(new Date());
            cmtTipoSolicitudMgl.setPerfilEdicion(perfilVT);
            cmtTipoSolicitudMgl.setUsuarioEdicion(usuarioVT);
            cmtTipoSolicitudMgl.setCreacionRol(cmtTipoSolicitudMgl.getCreacionRol());
            cmtTipoSolicitudMgl.setGestionRol(cmtTipoSolicitudMgl.getGestionRol());
            cmtTipoSolicitudMgl.setAns(cmtTipoSolicitudMgl.getAns());
            cmtTipoSolicitudMgl.setAnsAviso(cmtTipoSolicitudMgl.getAnsAviso());

            if (validarAsn(cmtTipoSolicitudMgl.getAns(), cmtTipoSolicitudMgl.getAnsAviso())) {
                String msn = "El valor de  Sla Aviso no puede ser mayor o igual a Sla";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            } else {
                cmtTipoSolicitudMgl = cmtTipoSolicitudMglFacadeLocal.update(cmtTipoSolicitudMgl);

                if (cmtTipoSolicitudMgl != null) {
                    listInfoByPage(1);
                    limpiarCampos();
                    String msn = "Proceso exitoso";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                }

            }

        } catch (ApplicationException e) {
            String msn = "Proceso falló: ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en SlaSolicitudBean:actualizarSlaMgl(). " + e.getMessage(), e, LOGGER);
        }
    }

    public void eliminarSlaSolMgl(CmtTipoSolicitudMgl cmtTipoSolicitudMgl) {

        try {
            if (cmtTipoSolicitudMgl.getTipoSolicitudId() == null) {
                String msn = "Seleccione un registro a eliminar";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return;
            }

            cmtTipoSolicitudMglFacadeLocal.delete(cmtTipoSolicitudMgl);
            listInfoByPage(1);
            limpiarCampos();
            String msn = "Proceso exitoso";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));

        } catch (ApplicationException e) {
            String msn = "Proceso falló : ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en SlaSolicitudBean:eliminarSlaSolMgl. " + e.getMessage(), e, LOGGER);
        }

    }

    public void limpiarCampos() {
        
        cmtTipoSolicitudMgl = new CmtTipoSolicitudMgl();
        cmtTipoSolicitudMgl.setAns(0);
        cmtTipoSolicitudMgl.setAnsAviso(0);
        cmtTipoSolicitudMgl.setNombreTipo("");
        cmtTipoSolicitudMgl.setCreacionRol("");
        cmtTipoSolicitudMgl.setGestionRol("");
        cmtTipoSolicitudMgl.setEstadoRegistro(1);
        btnActivo=true;

    }

    public CmtTipoSolicitudMgl getCmtTipoSolicitudMgl() {
        return cmtTipoSolicitudMgl;
    }

    public void setCmtTipoSolicitudMgl(CmtTipoSolicitudMgl cmtTipoSolicitudMgl) {
        this.cmtTipoSolicitudMgl = cmtTipoSolicitudMgl;
    }

    public String getEstiloObligatorio() {
        return estiloObligatorio;
    }

    public void setEstiloObligatorio(String estiloObligatorio) {
        this.estiloObligatorio = estiloObligatorio;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void pageFirst() {
        try {
            listInfoByPage(1);
        } catch (Exception ex) {
            LOGGER.error("Error al ir  a la primera página. EX000 " + ex.getMessage(), ex);
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
            LOGGER.error("Error al ir a la página anterior EX000 " + ex.getMessage(), ex);
        }
    }

    public String listInfoByPage(int page) {
        try {
            int firstResult = 0;
            if (page > 1) {
                firstResult = (filasPag * (page - 1));
            }
            filtroConsultaSlaSolicitudDto = cmtTipoSolicitudMglFacadeLocal.findTablasSlaTipoSolicitud(filtroConsultaSolicitudDto, ConstantsCM.PAGINACION_DATOS, firstResult, filasPag);
            listCmtTipoSolicitudMgl = filtroConsultaSlaSolicitudDto.getListaCmtTipoSolicitud();
            habilitaObj = !listCmtTipoSolicitudMgl.isEmpty();
            if (listCmtTipoSolicitudMgl.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "No se encontraron resultados para la consulta.", ""));
                return "";
            }
            actual = page;
            mostrarPanelListTipoSol = true;
            mostrarPanelCrearModtipoSol= false;
            
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en SlaSolicitudBean:listInfoByPage. " + e.getMessage(), e, LOGGER);
            return "";

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en SlaSolicitudBean:listInfoByPage. " + e.getMessage(), e, LOGGER);
        }
        return "";
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
             FacesUtil.mostrarMensajeError("Error en SlaSolicitudBean: irPagina(). " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en SlaSolicitudBean: irPagina(). " + e.getMessage(), e, LOGGER);
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
        if (pintarPaginado) {
            try {
                FiltroConsultaSlaSolicitudDto filtro
                        = cmtTipoSolicitudMglFacadeLocal.findTablasSlaTipoSolicitud(filtroConsultaSolicitudDto, ConstantsCM.PAGINACION_CONTAR, 0, 0);
                Long count = filtro.getNumRegistros();
                int totalPaginas = (int) ((count % filasPag != 0) ? (count / filasPag) + 1 : count / filasPag);
                return totalPaginas;
            } catch (ApplicationException e) {
                FacesUtil.mostrarMensajeError("Error en SlaSolicitudBean:getTotalPaginas(). " + e.getMessage(), e, LOGGER);
            } catch (Exception e) {
                FacesUtil.mostrarMensajeError("Error en SlaSolicitudBean:getTotalPaginas(). " + e.getMessage(), e, LOGGER);
            }
        }
        return 1;

    }

    public String getPageActual() {
        try {
            paginaList = new ArrayList<Integer>();
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
            FacesUtil.mostrarMensajeError("Error en SlaSolicitudBean:getPageActual(). " + e.getMessage(), e, LOGGER);
        }
        return pageActual;
    }

    /**
     * 
     * @return 
     */
    public boolean validarCreacion() {
        return validarAccion(ValidacionUtil.OPC_CREACION);
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
    public boolean validarBorrado() {
        return validarAccion(ValidacionUtil.OPC_BORRADO);
    }

    /**
     * M&eacute;todo para validar las acciones a realizar en el formulario
     * 
     * @param opcion String nombre de la opci&oacute;n que realizar&aacute; el componente
     * @return boolean indicador para verificar si se visualizan o no los componentes
     */
    private boolean validarAccion(String opcion) {
        try {
            return ValidacionUtil.validarVisualizacion(FORMULARIO, opcion, cmtOpcionesRolMglFacade, securityLogin);
        } catch (ApplicationException ex) {
            LOGGER.error("Error al validar las acciones a realizar en el formulario. EX000 " + ex.getMessage(), ex);
            FacesContext.getCurrentInstance().addMessage(
                    null, new FacesMessage(
                        FacesMessage.SEVERITY_ERROR, Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION, ""));
            return false;
        }
    }
    
    public String volverListTipoSol() {

        mostrarPanelListTipoSol = true;
        mostrarPanelCrearModtipoSol = false;

        return "";
    }
    
    public String nuevoTipoSolicitud() {

        mostrarPanelListTipoSol = false;
        mostrarPanelCrearModtipoSol = true;
        limpiarCampos();

        return "";
    }
    
    public void filtrarInfo(){
        listInfoByPage(1);
    }
    
    // Validar Opciones por Rol    
    public boolean validarOpcionNuevo() {
        return validarEdicionRol(BTNTIPSOLSLACRE);
    }
    
    public boolean validarOpcionLinkCod() {
        return validarEdicionRol(BTNTIPSOLSLACOD);
    }
    
    public boolean validarOpcionEliminar() {
        return validarEdicionRol(BTNTIPSOLSLADEL);
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
            FacesUtil.mostrarMensajeError("Error al SubtipoOtVtTecBean. " + e.getMessage(), e, LOGGER);
        }
        return false;
    }	
    
    public List<CmtTipoSolicitudMgl> getListCmtTipoSolicitudMgl() {
        return listCmtTipoSolicitudMgl;
    }

    public void setListCmtTipoSolicitudMgl(List<CmtTipoSolicitudMgl> listCmtTipoSolicitudMgl) {
        this.listCmtTipoSolicitudMgl = listCmtTipoSolicitudMgl;
    }

    public boolean isPintarPaginado() {
        return pintarPaginado;
    }

    public void setPintarPaginado(boolean pintarPaginado) {
        this.pintarPaginado = pintarPaginado;
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

    public int getFilasPag() {
        return filasPag;
    }

    public void setFilasPag(int filasPag) {
        this.filasPag = filasPag;
    }

    public boolean isHabilitaObj() {
        return habilitaObj;
    }

    public void setHabilitaObj(boolean habilitaObj) {
        this.habilitaObj = habilitaObj;
    }

    public int getActual() {
        return actual;
    }

    public void setActual(int actual) {
        this.actual = actual;
    }

    public boolean isBtnActivo() {
        return btnActivo;
    }

    public void setBtnActivo(boolean btnActivo) {
        this.btnActivo = btnActivo;
    }

    public boolean isMostrarPanelCrearModtipoSol() {
        return mostrarPanelCrearModtipoSol;
    }

    public void setMostrarPanelCrearModtipoSol(boolean mostrarPanelCrearModtipoSol) {
        this.mostrarPanelCrearModtipoSol = mostrarPanelCrearModtipoSol;
    }

    public boolean isMostrarPanelListTipoSol() {
        return mostrarPanelListTipoSol;
    }

    public void setMostrarPanelListTipoSol(boolean mostrarPanelListTipoSol) {
        this.mostrarPanelListTipoSol = mostrarPanelListTipoSol;
    }

    public FiltroConsultaSolicitudDto getFiltroConsultaSolicitudDto() {
        return filtroConsultaSolicitudDto;
    }

    public void setFiltroConsultaSolicitudDto(FiltroConsultaSolicitudDto filtroConsultaSolicitudDto) {
        this.filtroConsultaSolicitudDto = filtroConsultaSolicitudDto;
    }
}
