/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.cm;

import co.com.claro.mgl.dtos.ResponseValidarDireccionDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.error.ApplicationExceptionCM;
import co.com.claro.mgl.facade.GeograficoPoliticoMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtDireccionMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtSubEdificioMglFacadeLocal;
import co.com.claro.mgl.facade.ptlus.UsuarioServicesFacadeLocal;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.claro.mgl.rest.dtos.UsuariosServicesDTO;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

/**
 * La clase implementa las funcionalidades y validaciones requeridas para el la
 * administracion de Direcciones Alternas.
 *
 * La clase administra los valores y las validaciones de la vista que
 * corresponden a la Relacion Direcciones Alternas, tambien invoca las
 * funcionalidades para la insercion, consulta y edicion de registros.
 *
 * @author Admin
 * @versión 1.0
 */
@ManagedBean(name = "direccionAlternaMglBean")
@ViewScoped
public class DireccionAlternaMglBean implements Serializable {
    

    @EJB
    private UsuarioServicesFacadeLocal usuariosFacade;
    //@EJB
    @EJB
    private CmtDireccionMglFacadeLocal cmtDireccionMglFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacadeLocal;
    @EJB
    private GeograficoPoliticoMglFacadeLocal geograficoPoliticoMglFacadeLocal;
    @EJB
    private CmtSubEdificioMglFacadeLocal subEdificioFacade;
   
    
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpServletResponse response
            = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private static final Logger LOGGER = LogManager.getLogger(DireccionAlternaMglBean.class);
    private final String FORMULARIO = "DIRECCIONALTERNA";

    private String usuarioVT = null;
    private int perfilVT = 0;
    private String cedulaUsuarioVT = null;
    private CmtDireccionMgl cmtdireccionMgl = null;
    private List<CmtDireccionMgl> cmtdireccionMglList;
    private CmtCuentaMatrizMgl cmtCuentaMatrizMgl;
    public CuentaMatrizBean cuentaMatrizBean;
    private String pageActual;
    private String numPagina = "1";
    private List<Integer> paginaList;
    private int actual;
    private String direccion = "";
    private String comentario = "";
    private DrDireccion drDireccion;
    private ValidadorDireccionBean validadorDireccionBean;
    private ResponseValidarDireccionDto responseValidarDireccionDto;
    private List<String> barrioslist = new ArrayList<String>();
    private String selectedBarrio = "";
    private boolean direccionValidada = false;
    private boolean isUpdate;
    private CmtDireccionMgl registrocmtDireccionMglEliminar;
    private List<AuditoriaDto> informacionAuditoria = null;
    private boolean otroBarrio = false;
    private boolean mostrarLinkMatriz = false;
    private CmtCuentaMatrizMgl cuentaMatrizMglRepetidaDir = null;
    private CmtSubEdificioMgl selectedCmtSubEdificioMgl;
    public SecurityLogin securityLogin;
    private UsuariosServicesDTO usuarioLogin;
    private VisitaTecnicaBean visitaTecnicaBean;
    private boolean isValidacionOk = false;
    
  

    public enum Presentar implements Serializable {

        AUDITORIA("Auditoria"),
        DIRECCION("Direccion"),
        LISTA("Lista"),
        ELIMINAR("Eliminar");

        private final String view;

        private Presentar(String view) {
            this.view = view;
        }

        public String getView() {
            return view;
        }
    }

    Presentar vistas = Presentar.LISTA;

    /**
     * Creates a new instance of direccionMglBean
     */
    public DireccionAlternaMglBean() {
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

            validadorDireccionBean = JSFUtil.getSessionBean(ValidadorDireccionBean.class);
            validadorDireccionBean.setMostrarPopupSub(false);
            validadorDireccionBean.limpiarCamposTipoDireccion();
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Se generea error en DireccionAlternaMglBean class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en DireccionAlternaMglBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    @PostConstruct
    public void fillSqlList() {
        try {
            cmtDireccionMglFacadeLocal.setUser(usuarioVT, perfilVT);
            cuentaMatrizBean = JSFUtil.getSessionBean(CuentaMatrizBean.class);
            cmtCuentaMatrizMgl = cuentaMatrizBean.getCuentaMatriz();
            selectedCmtSubEdificioMgl = this.cuentaMatrizBean.getSelectedCmtSubEdificioMgl();
            listInfoByPage(1);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(DireccionAlternaMglBean.class.getName() + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en DireccionAlternaMglBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    private String listInfoByPage(int page) {
        try {
            cmtdireccionMglList = cmtDireccionMglFacadeLocal.findByCuentaMatriz(
                    cmtCuentaMatrizMgl, page, ConstantsCM.PAGINACION_CUATRO_FILAS, Constant.TYPE_QUERY.PER_PAGE);
            actual = page;
            getPageActual();
        } catch (ApplicationException e) {
            String msn = Constant.MSN_PROCESO_FALLO;
             FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en DireccionAlternaMglBean class ..." + e.getMessage(), e, LOGGER);
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
            FacesUtil.mostrarMensajeError("Se generea error en DireccionAlternaMglBean class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en DireccionAlternaMglBean class ..." + e.getMessage(), e, LOGGER);
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
            LOGGER.error("Error al ir a la útlima página. EX000 " + ex.getMessage(), ex);
        }
    }

    public int getTotalPaginas() {
        try {
            long pageSol = cmtDireccionMglFacadeLocal.countFindByCm(cmtCuentaMatrizMgl);
            int totalPaginas = (int) ((pageSol % ConstantsCM.PAGINACION_CUATRO_FILAS != 0)
                    ? (pageSol / ConstantsCM.PAGINACION_CUATRO_FILAS) + 1
                    : pageSol / ConstantsCM.PAGINACION_CUATRO_FILAS);
            return totalPaginas;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error en DireccionAlternaMglBean class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en DireccionAlternaMglBean class ..." + e.getMessage(), e, LOGGER);
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
            FacesUtil.mostrarMensajeError("Se generea error en DireccionAlternaMglBean class ..." + e.getMessage(), e, LOGGER);
        }
        return pageActual;
    }

    public String irModificar(CmtDireccionMgl cmtDireccionMgl) {
        cuentaMatrizMglRepetidaDir = null;
        mostrarLinkMatriz = false;
        isUpdate = true;
        direccionValidada = false;
        comentario = "";
        cmtdireccionMgl = cmtDireccionMgl;
        direccion = cmtDireccionMgl.getDireccionObj().getDirFormatoIgac();
        drDireccion = new DrDireccion();
        drDireccion.obtenerFromCmtDireccionMgl(cmtDireccionMgl);
        selectedBarrio = cmtDireccionMgl.getBarrio();
        if (selectedBarrio != null && !selectedBarrio.isEmpty()) {
            barrioslist.add(selectedBarrio);
        }
        vistas = Presentar.DIRECCION;
        return null;
    }

    public String irCrear() {
        mostrarLinkMatriz = false;
        cuentaMatrizMglRepetidaDir = null;
        isUpdate = false;
        direccionValidada = false;
        cmtdireccionMgl = new CmtDireccionMgl();
        direccion = "";
        comentario = "";
        selectedBarrio = "";
        barrioslist = new ArrayList<String>();
        CmtDireccionMgl cmtDireccionMgl = new CmtDireccionMgl();
        drDireccion = new DrDireccion();
        drDireccion.obtenerFromCmtDireccionMgl(cmtDireccionMgl);
        vistas = Presentar.DIRECCION;
        return null;
    }

    public String guardar() {
        try {
            
            if (comentario == null || comentario.trim().isEmpty()) {
                String msn = "El campo Comentario es requerido para cualquier modificación o eliminacion del registro";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return null;
            }
            
            if (comentario.length() > 200) {
                String msn = "Campo comentario debe ser menor a 200 caracteres";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return null;
            }
            
            CmtDireccionMgl cmtdireccionMglTemp = drDireccion.convertToCmtDireccionMgl();
            cmtdireccionMglTemp.setComentario(comentario);
            cmtdireccionMglTemp.setCuentaMatrizObj(cmtCuentaMatrizMgl);
            cmtdireccionMglTemp.setTdiId(1);
            cmtdireccionMglTemp.setDireccionId(cmtdireccionMgl.getDireccionId());
            if (responseValidarDireccionDto.getDrDireccion() != null) {
                
                if(selectedCmtSubEdificioMgl != null && selectedCmtSubEdificioMgl.getCmtCuentaMatrizMglObj() != null 
                        && selectedCmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getDireccionPrincipal() != null 
                        && selectedCmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getDireccionPrincipal().getCodTipoDir() != null){
                    // validacion para no ingresar direcciones diferente a la de la cuenta matriz
                    if (selectedCmtSubEdificioMgl.getCmtCuentaMatrizMglObj().getDireccionPrincipal().getCodTipoDir().
                            equalsIgnoreCase(responseValidarDireccionDto.getDrDireccion().getIdTipoDireccion())) {
                        isValidacionOk = true;
                    }                    
                }else{
                    isValidacionOk = true;
                }  
            } 

            if (!isValidacionOk) {
                String msn2 = "Ud no ha validado la direccion o  ha ingresado un tipo de direccion que no corresponde con  la cuenta matriz";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
                return null;
            } else {

                cmtDireccionMglFacadeLocal.actualizar(selectedCmtSubEdificioMgl, cmtdireccionMglTemp, drDireccion, selectedBarrio,
                        responseValidarDireccionDto, direccion, usuarioVT, isUpdate, usuarioVT, perfilVT, false);
                listInfoByPage(actual);
                vistas = Presentar.LISTA;
                cuentaMatrizMglRepetidaDir = null;
                mostrarLinkMatriz = false;
                String msn = "Proceso exitoso";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
            }

        } catch (ApplicationExceptionCM e) {
            String msn = Constant.MSN_PROCESO_FALLO ;
             FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
            if (e.getMatrizMgl() != null) {
                mostrarLinkMatriz = true;
                cuentaMatrizMglRepetidaDir = e.getMatrizMgl();
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en DireccionAlternaMglBean class ..." + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public void recargar(ResponseValidarDireccionDto direccionDto) {
        direccionValidada = true;
        responseValidarDireccionDto = direccionDto;
        drDireccion = responseValidarDireccionDto.getDrDireccion();
        direccion = responseValidarDireccionDto.getDireccion();
        barrioslist = responseValidarDireccionDto.getBarrios();
        selectedBarrio = responseValidarDireccionDto.getDrDireccion().getBarrio();
    }

    public String eliminarDireccion(CmtDireccionMgl cmtDireccionMgl) {
        registrocmtDireccionMglEliminar = cmtDireccionMgl;
        comentario = "";
        vistas = Presentar.ELIMINAR;
        return null;
    }

    public String confirmarEliminar() {
        try {
            if (comentario.length() > 200) {
                String msn = "Campo comentario debe ser menor a 200 caracteres";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return null;
            }
            registrocmtDireccionMglEliminar.setComentario(comentario);
            cmtDireccionMglFacadeLocal.delete(registrocmtDireccionMglEliminar);
            //Elimina en RR
        
            if(selectedCmtSubEdificioMgl.getSubEdificioId()!= null){
                subEdificioFacade.update(selectedCmtSubEdificioMgl);
            }
            
            registrocmtDireccionMglEliminar = null;
            listInfoByPage(actual);
            String msn = "Direccion eliminada correctamente";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
        } catch (ApplicationException e) {
            String msn = Constant.MSN_PROCESO_FALLO ;
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en DireccionAlternaMglBean class ..." + e.getMessage(), e, LOGGER);
        }
        vistas = Presentar.LISTA;
        return null;
    }

    public String irAuditoria(CmtDireccionMgl cmtDireccionMgl) {
        try {
            informacionAuditoria = cmtDireccionMglFacadeLocal.construirAuditoria(cmtDireccionMgl);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            String msn = Constant.MSN_PROCESO_FALLO;
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en DireccionAlternaMglBean class ..." + e.getMessage(), e, LOGGER);
        }
        vistas = Presentar.AUDITORIA;
        return null;
    }

    public void irCmdeDireccion() {
        try {
            cuentaMatrizBean.setCuentaMatriz(cuentaMatrizMglRepetidaDir);
            FacesUtil.navegarAPagina("/view/MGL/CM/tabs/general.jsf");
        } catch (ApplicationException e) {
            String msn = Constant.MSN_PROCESO_FALLO;
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en DireccionAlternaMglBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public String irLista() {
        vistas = Presentar.LISTA;
        return null;
    }

    public String irPopUpDireccion() {
        if(direccionValidada){
            comentario = "";
            direccion = "";
            direccionValidada = false;
        }
        try {
            
            drDireccion = new DrDireccion();
            validadorDireccionBean = (ValidadorDireccionBean) JSFUtil.getSessionBean(ValidadorDireccionBean.class);
            usuarioLogin = usuariosFacade.consultaInfoUserPorUsuario(usuarioVT);
            validadorDireccionBean.setCiudadDelBean(cuentaMatrizBean.getCuentaMatriz().getMunicipio());
            validadorDireccionBean.setIdCentroPoblado(cmtCuentaMatrizMgl.getCentroPoblado().getGpoId());
            validadorDireccionBean.setUsuarioLogin(usuarioLogin);
            validadorDireccionBean.setTecnologia(null);
            if(selectedBarrio == null || !selectedBarrio.isEmpty()){
                if(cmtCuentaMatrizMgl != null && cmtCuentaMatrizMgl.getDireccionPrincipal() != null 
                        && cmtCuentaMatrizMgl.getDireccionPrincipal().getBarrio() != null 
                        && !cmtCuentaMatrizMgl.getDireccionPrincipal().getBarrio().isEmpty()){
                    selectedBarrio = cmtCuentaMatrizMgl.getDireccionPrincipal().getBarrio();
                }
            }
            validadorDireccionBean.validarDireccion(drDireccion, direccion, cmtCuentaMatrizMgl.getCentroPoblado(), selectedBarrio, this, DireccionAlternaMglBean.class, Constant.TIPO_VALIDACION_DIR_CM,Constant.DIFERENTE_MODIFICACION_CM);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(DireccionAlternaMglBean.class.getName() + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en DireccionAlternaMglBean class ..." + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public String avisaCambiaDireccion() {
        mostrarLinkMatriz = false;
        cuentaMatrizMglRepetidaDir = null;
        direccionValidada = false;
        return null;
    }

    public String mostrarOtrobarrio() {
        otroBarrio = !otroBarrio;
        return null;
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
            return ValidacionUtil.validarVisualizacion(FORMULARIO, opcion, cmtOpcionesRolMglFacadeLocal, securityLogin);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION + e.getMessage(), e, LOGGER);
            return false;
         } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en DireccionAlternaMglBean class ..." + e.getMessage(), e, LOGGER);
        }
        return false;
    }

    /**
     * @return cmtdireccionMgl
     */
    public CmtDireccionMgl getCmtdireccionMgl() {
        return cmtdireccionMgl;
    }

    /**
     * @param cmtdireccionMgl
     */
    public void setCmtdireccionMgl(CmtDireccionMgl cmtdireccionMgl) {
        this.cmtdireccionMgl = cmtdireccionMgl;
    }

    /**
     * @return cmtdireccionMglList
     */
    public List<CmtDireccionMgl> getCmtdireccionMglList() {
        return cmtdireccionMglList;
    }

    /**
     * @param cmtdireccionMglList
     */
    public void setCmtdireccionMglList(List<CmtDireccionMgl> cmtdireccionMglList) {
        this.cmtdireccionMglList = cmtdireccionMglList;
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

    public int getActual() {
        return actual;
    }

    public void setActual(int actual) {
        this.actual = actual;
    }

    public int getFilasPag() {
        return ConstantsCM.PAGINACION_CUATRO_FILAS;
    }

    public Presentar getVistas() {
        return vistas;
    }

    public void setVistas(Presentar vistas) {
        this.vistas = vistas;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public DrDireccion getDrDireccion() {
        return drDireccion;
    }

    public void setDrDireccion(DrDireccion drDireccion) {
        this.drDireccion = drDireccion;
    }

    public List<String> getBarrioslist() {
        return barrioslist;
    }

    public void setBarrioslist(List<String> barrioslist) {
        this.barrioslist = barrioslist;
    }

    public String getSelectedBarrio() {
        return selectedBarrio;
    }

    public void setSelectedBarrio(String selectedBarrio) {
        this.selectedBarrio = selectedBarrio;
    }

    public CmtCuentaMatrizMgl getCmtCuentaMatrizMgl() {
        return cmtCuentaMatrizMgl;
    }

    public void setCmtCuentaMatrizMgl(CmtCuentaMatrizMgl cmtCuentaMatrizMgl) {
        this.cmtCuentaMatrizMgl = cmtCuentaMatrizMgl;
    }

    public boolean isDireccionValidada() {
        return direccionValidada;
    }

    public void setDireccionValidada(boolean direccionValidada) {
        this.direccionValidada = direccionValidada;
    }

    public CmtDireccionMgl getRegistrocmtDireccionMglEliminar() {
        return registrocmtDireccionMglEliminar;
    }

    public void setRegistrocmtDireccionMglEliminar(CmtDireccionMgl registrocmtDireccionMglEliminar) {
        this.registrocmtDireccionMglEliminar = registrocmtDireccionMglEliminar;
    }

    public List<AuditoriaDto> getInformacionAuditoria() {
        return informacionAuditoria;
    }

    public void setInformacionAuditoria(List<AuditoriaDto> informacionAuditoria) {
        this.informacionAuditoria = informacionAuditoria;
    }

    public boolean isOtroBarrio() {
        return otroBarrio;
    }

    public void setOtroBarrio(boolean otroBarrio) {
        this.otroBarrio = otroBarrio;
    }

    public boolean isMostrarLinkMatriz() {
        return mostrarLinkMatriz;
    }

    public void setMostrarLinkMatriz(boolean mostrarLinkMatriz) {
        this.mostrarLinkMatriz = mostrarLinkMatriz;
    }

    public CmtSubEdificioMgl getSelectedCmtSubEdificioMgl() {
        return selectedCmtSubEdificioMgl;
    }

    public void setSelectedCmtSubEdificioMgl(CmtSubEdificioMgl selectedCmtSubEdificioMgl) {
        this.selectedCmtSubEdificioMgl = selectedCmtSubEdificioMgl;
    }

    public boolean isIsValidacionOk() {
        return isValidacionOk;
    }

    public void setIsValidacionOk(boolean isValidacionOk) {
        this.isValidacionOk = isValidacionOk;
    }
    
}
