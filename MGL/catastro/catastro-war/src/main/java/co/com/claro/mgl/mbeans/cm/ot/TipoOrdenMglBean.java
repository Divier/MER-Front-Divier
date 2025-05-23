package co.com.claro.mgl.mbeans.cm.ot;

import co.com.claro.mgl.dtos.FiltroConsultaSlaOtDto;
import co.com.claro.mgl.dtos.FiltroConsultaSubTipoDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOrdenTrabajoMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoOtMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoOtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoSolicitudMgl;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
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
@ManagedBean(name = "tipoOrdenMglBean")
@ViewScoped
public class TipoOrdenMglBean {

    @EJB
    private CmtTipoOtMglFacadeLocal cmtTipoOtMglFacadeLocal;
    @EJB
    private CmtBasicaMglFacadeLocal cmtBasicaMglFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacade;
    @EJB
    private CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMglFacadeLocal;
    @EJB
    private CmtOrdenTrabajoMglFacadeLocal cmtOrdenTrabajoMglFacadeLocal;
    
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private static final Logger LOGGER = LogManager.getLogger(TipoOrdenMglBean.class);
    private final String FORMULARIO = "SLAOT";
    private String usuarioVT = null;
    private int perfilVT = 0;
    private CmtTipoOtMgl cmtTipoOtMgl;
    private CmtTipoSolicitudMgl cmtTipoSolicitudMgl;
    private String estiloObligatorio = "<font color='red'>*</font>";
    private String message;
    private List<CmtTipoOtMgl> listTipoTrabajoOt;
    private List<CmtBasicaMgl> listTablaBasicaTipoOt;
    private List<CmtBasicaMgl> listTablaBasicaFlujoOt;
    private String estadoSelected;
    //paginacion
    private FiltroConsultaSlaOtDto filtroConsultaSlaOtDto = new FiltroConsultaSlaOtDto();
    private List<CmtTipoOtMgl> listTableCmtTipoOtMgl;
    private int filasPag = ConstantsCM.PAGINACION_OCHO_FILAS;
    private boolean habilitaObj = false;
    private int actual;
    private String numPagina;
    private boolean pintarPaginado;
    private List<Integer> paginaList;
    private String pageActual;
    private boolean btnActivo = false;
    private boolean cargar = true;
    private String descripcionTipoOt;
    private String basicaIdTipoFlujo;
    private String basicaIdEstadoCm;
    private String otAgendable = "N";
    private String ans;
    private String ans_aviso;
    private String id_subGrupo;
    private String descTipoOt;
    private int sla;
    private int slaAviso;
    private SecurityLogin securityLogin;
    private String esDeTipoVT = "false";
    private BigDecimal tipoOtSelected;
    private boolean requiereOnyx;
    private boolean mostrarPanelCrearModSubTipoWork;
    private boolean mostrarPanelListSubTiposWork;
    private List<CmtTipoOtMgl> listTipoOtAcometidasMgl;
    private FiltroConsultaSubTipoDto filtroConsultaSubTipoDto = new FiltroConsultaSubTipoDto();
    private boolean actualizaInfoTecnica;
    
    //Opciones agregadas para Rol
    private final String BTSUBORSLACRE = "BTSUBORSLACRE";
    private final String BTSUBORSLACOD = "BTSUBORSLACOD";
    private final String BTSUBORSLADEL = "BTSUBORSLADEL";
    
    public TipoOrdenMglBean() {
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
            cmtTipoOtMgl = new CmtTipoOtMgl();

        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error en  TipoOrdenMglBean. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en  TipoOrdenMglBean. ", e, LOGGER);
        }
    }

    @PostConstruct
    public void cargarListas() {

        try {

            CmtTipoBasicaMgl tipoBasicaTipoOt;
            tipoBasicaTipoOt = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_TIPO_GENERAL_OT);
            listTablaBasicaTipoOt = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasicaTipoOt);

            CmtTipoBasicaMgl tipoBasicaTipoflujoOt;
            tipoBasicaTipoflujoOt = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_TIPO_FLUJO_OT);
            listTablaBasicaFlujoOt = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasicaTipoflujoOt);


            pintarPaginado = true;
            cmtTipoOtMgl.setAnsAviso(0);
            cmtTipoOtMgl.setAns(0);
            listInfoByPage(1);
            limpiarCampos();
        } catch (ApplicationException e) {
            LOGGER.error("Error en cargarListas " + e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Error en cargarListas " + e.getMessage(), e);
        }
    }

    public void goActualizar(CmtTipoOtMgl item) {
        try {

            cmtTipoOtMgl = item;
            esDeTipoVT=cmtTipoOtMgl.getEsTipoVT();
            tipoOtSelected=cmtTipoOtMgl.getOtAcoAGenerar();
            change();
            mostrarPanelCrearModSubTipoWork= true;
            mostrarPanelListSubTiposWork = false;
            
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en goActualizar. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en goActualizar. ", e, LOGGER);
        }
    }

    public void change() throws ApplicationException {
        cargar = false;
        btnActivo = true;

    }

    public String listInfoByPage(int page) throws ApplicationException {
        try {
            int firstResult = 0;
            if (page > 1) {
                firstResult = (filasPag * (page - 1));
            }
            filtroConsultaSlaOtDto = cmtTipoOtMglFacadeLocal.findListTablaSlaOt(filtroConsultaSubTipoDto, ConstantsCM.PAGINACION_DATOS, firstResult, filasPag);
            listTableCmtTipoOtMgl = filtroConsultaSlaOtDto.getListaCmtTipoOtMgl();
            habilitaObj = !listTableCmtTipoOtMgl.isEmpty();
            
            listTipoOtAcometidasMgl = cmtTipoOtMglFacadeLocal.findAllTipoOtAcometidas();
            
            if (listTableCmtTipoOtMgl.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "No se encontraron resultados para la consulta.", ""));
                return "";
            }

            actual = page;
            mostrarPanelCrearModSubTipoWork=false;
            mostrarPanelListSubTiposWork = true;
            
        } catch (ApplicationException e) {
            LOGGER.error("Error en listInfoByPage. ", e);           
            throw e;

        } catch (Exception e) {
            LOGGER.error("Error en listInfoByPage. ", e);           
            throw new ApplicationException(e);

        }
        return "";
    }

    public void crearSlaMgl() {
        try {
                if (cmtTipoOtMgl.getBasicaIdTipoOt().getBasicaId().compareTo(BigDecimal.ZERO) == 0
                    || cmtTipoOtMgl.getTipoFlujoOt().getBasicaId().compareTo(BigDecimal.ZERO) == 0
                    || cmtTipoOtMgl.getAns() == 0
                    || cmtTipoOtMgl.getAnsAviso() == 0
                    || (cmtTipoOtMgl.getDescTipoOt() == null || cmtTipoOtMgl.getDescTipoOt().isEmpty())) {
                String msn = "Faltan campos obligatorios por llenar, Sla y Sla Aviso no pueden ser cero";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return;
            }
            
            cmtTipoOtMgl.setDetalle("CREACION");
            cmtTipoOtMgl.setUsuarioCreacion(usuarioVT);
            cmtTipoOtMgl.setPerfilCreacion(perfilVT);
            cmtTipoOtMgl.setFechaCreacion(new Date());  
            cmtTipoOtMgl.setEsTipoVT(esDeTipoVT);
            cmtTipoOtMgl.setOtAcoAGenerar(tipoOtSelected);
            cmtTipoOtMgl.setRequiereOnyx(requiereOnyx == true ? "Y" : "N");
            cmtTipoOtMgl.setActualizaInfoTec(actualizaInfoTecnica == true ? "Y" : "N");

            if (validarAsn(cmtTipoOtMgl.getAns(), cmtTipoOtMgl.getAnsAviso())) {
                String msn = "El valor de  Sla Aviso no puede ser mayor o igual a Sla";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            } else {
                cmtTipoOtMgl = cmtTipoOtMglFacadeLocal.create(cmtTipoOtMgl);
                if (cmtTipoOtMgl != null) {
                    listInfoByPage(1);
                    String msn = "Proceso exitoso: Se ha creado el sub tipo de trabajo  <b>" + cmtTipoOtMgl.getIdTipoOt() + "</b>  en BD.";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                    limpiarCampos();
                }
            }

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en crearSlaMgl. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en crearSlaMgl. ", e, LOGGER);
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

    public void actualizaSlaOtMgl() {
        try {
          if (cmtTipoOtMgl.getBasicaIdTipoOt().getBasicaId().compareTo(BigDecimal.ZERO) == 0
                    || cmtTipoOtMgl.getTipoFlujoOt().getBasicaId().compareTo(BigDecimal.ZERO) == 0
                    || cmtTipoOtMgl.getAns() == 0
                    || cmtTipoOtMgl.getAnsAviso() == 0
                    || (cmtTipoOtMgl.getDescTipoOt() == null || cmtTipoOtMgl.getDescTipoOt().isEmpty())) {
                String msn = "Faltan campos obligatorios por llenar, Sla y Sla Aviso no pueden ser cero";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return;
            }
          
            cmtTipoOtMgl.setDetalle("EDICION");
            cmtTipoOtMgl.setUsuarioEdicion(usuarioVT);
            cmtTipoOtMgl.setPerfilEdicion(perfilVT);
            cmtTipoOtMgl.setFechaEdicion(new Date());
            cmtTipoOtMgl.setEsTipoVT(esDeTipoVT);
            cmtTipoOtMgl.setOtAcoAGenerar(tipoOtSelected);
            cmtTipoOtMgl.setRequiereOnyx(requiereOnyx == true ? "Y" : "N");
            cmtTipoOtMgl.setActualizaInfoTec(actualizaInfoTecnica == true ? "Y" : "N");

            if (validarAsn(cmtTipoOtMgl.getAns(), cmtTipoOtMgl.getAnsAviso())) {
                String msn = "El valor de  Sla Aviso no puede ser mayor o igual a Sla ";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            } else {
                cmtTipoOtMgl = cmtTipoOtMglFacadeLocal.update(cmtTipoOtMgl);
                if (cmtTipoOtMgl != null) {
                    listInfoByPage(1);
                    String msn = "Proceso exitoso: Se ha modificado el sub tipo de trabajo  <b>"+cmtTipoOtMgl.getIdTipoOt()+"</b>  en BD. ";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                    limpiarCampos();
                    cargar = true;
                    btnActivo = false;
                }
            }

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en actualizaSlaOtMgl. ", e, LOGGER);            
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en actualizaSlaOtMgl. ", e, LOGGER);            
        }

    }

    public void eliminarSlaOtMgl(CmtTipoOtMgl cmtTipoOtMgl) {

        try {    
            List<CmtOrdenTrabajoMgl> ordenes = cmtOrdenTrabajoMglFacadeLocal.findByTipoTrabajo(cmtTipoOtMgl);
            if(ordenes.size() > 0){
              String msn = "El sub tipo de trabajo tiene ordenes asociadas.";
              FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
              return;  
                
            }
            cmtTipoOtMglFacadeLocal.delete(cmtTipoOtMgl);
            listInfoByPage(1);
            String msn = "Proceso exitoso: Se ha eliminado el sub tipo de trabajo  <b>"+cmtTipoOtMgl.getIdTipoOt()+"</b>  en BD.";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en eliminarSlaOtMgl. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en eliminarSlaOtMgl. ", e, LOGGER);
        }

    }

    public void limpiarCampos() {
        cmtTipoOtMgl = new CmtTipoOtMgl();
        cmtTipoOtMgl.setBasicaIdTipoOt(new CmtBasicaMgl());
        cmtTipoOtMgl.setTipoFlujoOt(new CmtBasicaMgl());
        esDeTipoVT= "false";
        cargar = true;
        btnActivo = false;
    }

 

    public void pageFirst() {
        try {
            listInfoByPage(1);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en pageFirst. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en pageFirst. ", e, LOGGER);
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
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en pagePrevious. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en pagePrevious. ", e, LOGGER);
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
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en irPagina. ", e, LOGGER);
        } catch (NumberFormatException e) {
            FacesUtil.mostrarMensajeError("Error en irPagina. ", e, LOGGER);
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
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en pageNext. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en pageNext. ", e, LOGGER);
        }
    }

    public void pageLast() {
        try {
            int totalPaginas = getTotalPaginas();
            listInfoByPage(totalPaginas);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en pageLast. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en pageLast. ", e, LOGGER);
        }

    }

    public int getTotalPaginas() throws ApplicationException {
        if (pintarPaginado) {
            try {
                FiltroConsultaSlaOtDto filtro
                        = cmtTipoOtMglFacadeLocal.findListTablaSlaOt(filtroConsultaSubTipoDto, ConstantsCM.PAGINACION_CONTAR, 0, 0);
                Long count = filtro.getNumRegistros();
                int totalPaginas = (int) ((count % filasPag != 0) ? (count / filasPag) + 1 : count / filasPag);
                return totalPaginas;
            } catch (ApplicationException e) {
                LOGGER.error("Error en getTotalPaginas. EX. " + e.getMessage(), e);
                throw e;
            } catch (Exception e) {
                LOGGER.error("Error en getTotalPaginas. EX. " + e.getMessage(), e);
                throw new ApplicationException(e);
            }
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
            return pageActual;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en getPageActual. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en getPageActual. ", e, LOGGER);
        }
        return null;
    }

    /**
     *
     * @return
     */
    public boolean validarCreacion() {
        try {
            return validarAccion(ValidacionUtil.OPC_CREACION);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en validarBorrado. " + Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION + " ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en validarBorrado. " + Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION + " ", e, LOGGER);
        }
        return false;
    }

    /**
     *
     * @return
     */
    public boolean validarEdicion() {
        try {
            return validarAccion(ValidacionUtil.OPC_EDICION);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en validarBorrado. " + Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION + " ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en validarBorrado. " + Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION + " ", e, LOGGER);
        }
        return false;
    }

    /**
     *
     * @return
     */
    public boolean validarBorrado() {
        try {
            return validarAccion(ValidacionUtil.OPC_BORRADO);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en validarBorrado. " + Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION + " ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en validarBorrado. " + Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION + " ", e, LOGGER);
        }
        return false;
    }

    /**
     * M&eacute;todo para validar las acciones a realizar en el formulario
     *
     * @param opcion String nombre de la opci&oacute;n que realizar&aacute; el
     * componente
     * @return boolean indicador para verificar si se visualizan o no los
     * componentes
     */
    private boolean validarAccion(String opcion) throws ApplicationException {
        try {
            return ValidacionUtil.validarVisualizacion(FORMULARIO, opcion, cmtOpcionesRolMglFacade, securityLogin);
        } catch (ApplicationException ex) {
            LOGGER.error("Error en validarAccion. " + ex.getMessage(), ex);
            throw ex;
        } catch (Exception ex) {
            LOGGER.error("Error en validarAccion. " + ex.getMessage(), ex);
            throw new ApplicationException(ex);
        }
    }
    
    public String traerNombreSubTipo(BigDecimal idSubTipo) {

        String nombreSubtipo = "";
        try {
            CmtTipoOtMgl cmtTipoOtMgl1 = cmtTipoOtMglFacadeLocal.findById(idSubTipo);
            if (cmtTipoOtMgl1.getIdTipoOt() != null) {
                nombreSubtipo = cmtTipoOtMgl1.getDescTipoOt();
            }

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en traerNombreSubTipo. o se encontro sub tipo de orden. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en traerNombreSubTipo. o se encontro sub tipo de orden. ", e, LOGGER);
        }
        return nombreSubtipo;
    }
    
    public String volverListSubTiposWork() {

        mostrarPanelCrearModSubTipoWork = false;
        mostrarPanelListSubTiposWork = true;

        return "";
    }

    public String nuevoSubTipo() {
       
        mostrarPanelCrearModSubTipoWork = true;
        mostrarPanelListSubTiposWork = false;
        limpiarCampos();

        return "";
    }
    
    // Validar Opciones por Rol    
    public boolean validarOpcionNuevo() {
        return validarEdicionRol(BTSUBORSLACRE);
    }
    
    public boolean validarOpcionLikCod() {
        return validarEdicionRol(BTSUBORSLACOD);
    }
    
    public boolean validarOpcionEliminar() {
        return validarEdicionRol(BTSUBORSLADEL);
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

    public CmtTipoOtMgl getCmtTipoOtMgl() {
        return cmtTipoOtMgl;
    }

    public void setCmtTipoOtMgl(CmtTipoOtMgl cmtTipoOtMgl) {
        this.cmtTipoOtMgl = cmtTipoOtMgl;
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

    public List<CmtTipoOtMgl> getListTipoTrabajoOt() {
        return listTipoTrabajoOt;
    }

    public void setListTipoTrabajoOt(List<CmtTipoOtMgl> listTipoTrabajoOt) {
        this.listTipoTrabajoOt = listTipoTrabajoOt;
    }

    public String getEstadoSelected() {
        return estadoSelected;
    }

    public void setEstadoSelected(String estadoSelected) {
        this.estadoSelected = estadoSelected;
    }

    public List<CmtTipoOtMgl> getListTableCmtTipoOtMgl() {
        return listTableCmtTipoOtMgl;
    }

    public void setListTableCmtTipoOtMgl(List<CmtTipoOtMgl> listTableCmtTipoOtMgl) {
        this.listTableCmtTipoOtMgl = listTableCmtTipoOtMgl;
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

    public boolean isBtnActivo() {
        return btnActivo;
    }

    public void setBtnActivo(boolean btnActivo) {
        this.btnActivo = btnActivo;
    }

    public boolean isCargar() {
        return cargar;
    }

    public void setCargar(boolean cargar) {
        this.cargar = cargar;
    }

    public String getDescripcionTipoOt() {
        return descripcionTipoOt;
    }

    public void setDescripcionTipoOt(String descripcionTipoOt) {
        this.descripcionTipoOt = descripcionTipoOt;
    }

    public String getBasicaIdTipoFlujo() {
        return basicaIdTipoFlujo;
    }

    public void setBasicaIdTipoFlujo(String basicaIdTipoFlujo) {
        this.basicaIdTipoFlujo = basicaIdTipoFlujo;
    }

    public String getBasicaIdEstadoCm() {
        return basicaIdEstadoCm;
    }

    public void setBasicaIdEstadoCm(String basicaIdEstadoCm) {
        this.basicaIdEstadoCm = basicaIdEstadoCm;
    }

    public boolean getOtAgendable() {
        if (otAgendable.equals("N")) {
            return false;
        } else {

            return true;
        }
    }

    public void setOtAgendable(boolean otAgendable) {
        this.otAgendable = otAgendable ? "Y" : "N";
    }

    public boolean isPintarPaginado() {
        return pintarPaginado;
    }

    public void setPintarPaginado(boolean pintarPaginado) {
        this.pintarPaginado = pintarPaginado;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<CmtBasicaMgl> getListTablaBasicaFlujoOt() {
        return listTablaBasicaFlujoOt;
    }

    public void setListTablaBasicaFlujoOt(List<CmtBasicaMgl> listTablaBasicaFlujoOt) {
        this.listTablaBasicaFlujoOt = listTablaBasicaFlujoOt;
    }

    public String getDescTipoOt() {
        return descTipoOt;
    }

    public void setDescTipoOt(String descTipoOt) {
        this.descTipoOt = descTipoOt;
    }

    public int getSla() {
        return sla;
    }

    public void setSla(int sla) {
        this.sla = sla;
    }

    public int getSlaAviso() {
        return slaAviso;
    }

    public void setSlaAviso(int slaAviso) {
        this.slaAviso = slaAviso;
    }

    public int getFilasPag() {
        return filasPag;
    }

    public void setFilasPag(int filasPag) {
        this.filasPag = filasPag;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    public String getAns_aviso() {
        return ans_aviso;
    }

    public void setAns_aviso(String ans_aviso) {
        this.ans_aviso = ans_aviso;
    }

    public String getId_subGrupo() {
        return id_subGrupo;
    }

    public void setId_subGrupo(String id_subGrupo) {
        this.id_subGrupo = id_subGrupo;
    }

    public List<CmtBasicaMgl> getListTablaBasicaTipoOt() {
        return listTablaBasicaTipoOt;
    }

    public void setListTablaBasicaTipoOt(List<CmtBasicaMgl> listTablaBasicaTipoOt) {
        this.listTablaBasicaTipoOt = listTablaBasicaTipoOt;
    }

    public String getEsDeTipoVT() {
        
        if (esDeTipoVT.equals("Y")) {
            esDeTipoVT = "true";
        } else {
            esDeTipoVT = "false";
        }
        return esDeTipoVT;
    }

    public void setEsDeTipoVT(String esDeTipoVT) {
        if (esDeTipoVT.equals("true")) {
            esDeTipoVT = "Y";
        } else {
            esDeTipoVT = "N";
        }

        this.esDeTipoVT = esDeTipoVT;
    }

    public BigDecimal getTipoOtSelected() {
        return tipoOtSelected;
    }

    public void setTipoOtSelected(BigDecimal tipoOtSelected) {
        this.tipoOtSelected = tipoOtSelected;
    }

    public boolean getRequiereOnyx() {
        if (cmtTipoOtMgl.getRequiereOnyx() != null) {
            if (cmtTipoOtMgl.getRequiereOnyx().equals("Y")) {
                requiereOnyx = true;
            } else {
                requiereOnyx = false;
            }
        } else {
            requiereOnyx = false;
        }
        return requiereOnyx;
    }

    public boolean isActualizaInfoTecnica() {
        if (cmtTipoOtMgl.getActualizaInfoTec() != null) {
            if (cmtTipoOtMgl.getActualizaInfoTec().equals("Y")) {
                actualizaInfoTecnica = true;
            } else {
                actualizaInfoTecnica = false;
            }
        } else {
            actualizaInfoTecnica = false;
        }
        return actualizaInfoTecnica;
    }

    public void setActualizaInfoTecnica(boolean actualizaInfoTecnica) {
        this.actualizaInfoTecnica = actualizaInfoTecnica;
    }
    
    public void filtrarInfo() throws ApplicationException{
        listInfoByPage(1);
    }

    public void setRequiereOnyx(boolean requiereOnyx) {
        this.requiereOnyx = requiereOnyx;
    }

    public boolean isMostrarPanelCrearModSubTipoWork() {
        return mostrarPanelCrearModSubTipoWork;
    }

    public void setMostrarPanelCrearModSubTipoWork(boolean mostrarPanelCrearModSubTipoWork) {
        this.mostrarPanelCrearModSubTipoWork = mostrarPanelCrearModSubTipoWork;
    }

    public boolean isMostrarPanelListSubTiposWork() {
        return mostrarPanelListSubTiposWork;
    }

    public void setMostrarPanelListSubTiposWork(boolean mostrarPanelListSubTiposWork) {
        this.mostrarPanelListSubTiposWork = mostrarPanelListSubTiposWork;
    }

    public List<CmtTipoOtMgl> getListTipoOtAcometidasMgl() {
        return listTipoOtAcometidasMgl;
    }

    public void setListTipoOtAcometidasMgl(List<CmtTipoOtMgl> listTipoOtAcometidasMgl) {
        this.listTipoOtAcometidasMgl = listTipoOtAcometidasMgl;
    }

    public FiltroConsultaSubTipoDto getFiltroConsultaSubTipoDto() {
        return filtroConsultaSubTipoDto;
    }

    public void setFiltroConsultaSubTipoDto(FiltroConsultaSubTipoDto filtroConsultaSubTipoDto) {
        this.filtroConsultaSubTipoDto = filtroConsultaSubTipoDto;
    }
}