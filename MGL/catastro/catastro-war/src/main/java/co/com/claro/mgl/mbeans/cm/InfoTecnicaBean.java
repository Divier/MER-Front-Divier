/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.cm;

import co.com.claro.mgl.dtos.FiltroInformacionTecnicaDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.GeograficoPoliticoMglFacadeLocal;
import co.com.claro.mgl.facade.cm.*;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtInfoTecnicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTecnologiaSubMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author cardenaslb
 */
@ManagedBean(name = "infoTecnicaBean")
@ViewScoped
public class InfoTecnicaBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LogManager.getLogger(InfoTecnicaBean.class);
    private static final String TAB_INF_TECNICA_CCMM = "TABINFTECNICACCMM";
    private String usuarioVT = null;
    private int perfilVT = 0;
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private CmtSubEdificioMgl subEdificioMgl;
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    @EJB
    private CmtInfoTecnicaMglFacadeLocal cmtInfoTecnicaMglFacadeLocal;
    private List<CmtInfoTecnicaMgl> listInfoTecnica;
    private String message;
    private CmtInfoTecnicaMgl infoTecnicaMgl;
    private int filasPag = ConstantsCM.PAGINACION_OCHO_FILAS;
    private FiltroInformacionTecnicaDto filtroInformacionTecnicaDto;
    private boolean habilitaObj = false;
    private int actual;
    private String numPagina = "1";
    private boolean pintarPaginado = true;
    private List<Integer> paginaList;
    private String pageActual;
    private HtmlDataTable datatableInfoTecnica;
    private CmtInfoTecnicaMgl cmtInfoTecnicaMgl = new CmtInfoTecnicaMgl();
    private String space = "&nbsp;&nbsp;&nbsp;&nbsp;";
    private CuentaMatrizBean cuentaMatrizBean;
    @EJB
    private CmtBasicaMglFacadeLocal cmtBasicaMglFacadeLocal;
    @EJB
    private CmtTecnologiaSubMglFacadeLocal cmtTecnologiaSubMglFacadeLocal;
    @EJB
    private CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMglFacadeLocal;
    @EJB
    private CmtValidadorDireccionesFacadeLocal direccionesFacadeLocal;
    @EJB
    private GeograficoPoliticoMglFacadeLocal geograficoPoliticoMglFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacadeLocal;
    
    List<CmtTecnologiaSubMgl> lsCmtTecnologiaSubMgls;
    List<CmtBasicaMgl> listInfTecN1;
    List<CmtBasicaMgl> listInfTecN2;
    List<CmtBasicaMgl> listInfTecN3;
    List<CmtBasicaMgl> listInfTecN4;
    private boolean cargar = true;
    private boolean btnActivo = true;
    private BigDecimal tecnologiaSubId = null;
    private BigDecimal infoTecNivel1 = null;
    private BigDecimal infoTecNivel2 = null;
    private BigDecimal infoTecNivel3 = null;
    private BigDecimal infoTecNivel4 = null;
    private BigDecimal tecnologiaSubUpdateID = null;
    private BigDecimal infoTecNivel1Update = null;
    private BigDecimal infoTecNivel2Update = null;
    private BigDecimal infoTecNivel3Update = null;
    private BigDecimal infoTecNivel4Update = null;
    private boolean disableTecno = false;
    private String estiloObligatorio = "<font color='red'>*</font>";
    private BigDecimal nodos = null;
    private DrDireccion drDireccionDireccionCm;
    private BigDecimal ciudad;
    private GeograficoPoliticoMgl centroPoblado;
    private GeograficoPoliticoMgl departamento;
    private GeograficoPoliticoMgl municipio;
    private String nodoCobertura = "";
    private  CmtTecnologiaSubMgl tecnologiaSubMgl = null;
    private  CmtTecnologiaSubMgl tecnologiaSubMglUpd = null;
    private SecurityLogin securityLogin;

    public InfoTecnicaBean() {
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
            infoTecnicaMgl = new CmtInfoTecnicaMgl();
            this.cuentaMatrizBean = JSFUtil.getSessionBean(CuentaMatrizBean.class);
            subEdificioMgl = this.cuentaMatrizBean.getSelectedCmtSubEdificioMgl();
            tecnologiaSubId = null;
            tecnologiaSubUpdateID = null;
            listInfTecN1 = null;
            infoTecNivel1Update = null;
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Se generea error en InfoTecnicaBean class ..." + e.getMessage(), e, LOGGER);
         } catch (Exception e) {
           FacesUtil.mostrarMensajeError("Se generea error en InfoTecnicaBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    @PostConstruct
    public void init() {
        try {

            cmtInfoTecnicaMglFacadeLocal.setUser(usuarioVT, perfilVT);
            lsCmtTecnologiaSubMgls = cmtTecnologiaSubMglFacadeLocal.findTecnoSubBySubEdi(subEdificioMgl);
         

            CmtTipoBasicaMgl tipoBasicaInfTecN1;
            tipoBasicaInfTecN1 = cmtTipoBasicaMglFacadeLocal.
                    findByCodigoInternoApp(Constant.TIPO_BASICA_PARAM_INFO_TEC_N1);
            listInfTecN1 = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasicaInfTecN1);

            CmtTipoBasicaMgl tipoBasicaInfTecN2;
            tipoBasicaInfTecN2 = cmtTipoBasicaMglFacadeLocal.
                    findByCodigoInternoApp(Constant.TIPO_BASICA_PARAM_INFO_TEC_N2);
            listInfTecN2 = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasicaInfTecN2);

            CmtTipoBasicaMgl tipoBasicaInfTecN3;
            tipoBasicaInfTecN3 = cmtTipoBasicaMglFacadeLocal.
                    findByCodigoInternoApp(Constant.TIPO_BASICA_PARAM_INFO_TEC_N3);
            listInfTecN3 = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasicaInfTecN3);

            CmtTipoBasicaMgl tipoBasicaInfTecN4;
            tipoBasicaInfTecN4 = cmtTipoBasicaMglFacadeLocal.
                    findByCodigoInternoApp(Constant.TIPO_BASICA_PARAM_INFO_TEC_N4);
            listInfTecN4 = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasicaInfTecN4);
            
            cuentaMatrizBean.getCuentaMatriz().getDireccionesList();
            direccionesFacadeLocal.setUser(usuarioVT, perfilVT);
            centroPoblado = cuentaMatrizBean.getCuentaMatriz().getCentroPoblado();
            municipio = geograficoPoliticoMglFacadeLocal.findById(centroPoblado.getGeoGpoId());
            departamento = geograficoPoliticoMglFacadeLocal.findById(municipio.getGeoGpoId());
            drDireccionDireccionCm = direccionesFacadeLocal.convertirDireccionStringADrDireccion(cuentaMatrizBean.getCuentaMatriz().getSubEdificioGeneral().getDireccion(), centroPoblado.getGpoId());
           
            listInfoByPage(1);
            
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error en InfoTecnicaBean class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en InfoTecnicaBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Metodo para validar si el usuario tiene permisos de creacion
     * @return  {@code boolean} true si tiene permisos de creacion, false en caso contrario
     * @author Manuel Hernandez
     */
    public boolean isRolCrear(){
        try {
            return ValidacionUtil.validarVisualizacion(TAB_INF_TECNICA_CCMM, ValidacionUtil.OPC_CREACION, cmtOpcionesRolMglFacadeLocal, securityLogin);
        } catch (ApplicationException e) {
            LOGGER.error("Error al validar el rol de creación. ");
        }
        return false;
    }

    /**
     * Metodo para validar si el usuario tiene permisos de edicion
     * @return  {@code boolean} true si tiene permisos de edicion, false en caso contrario
     * @author Manuel Hernandez
     */
    public boolean isRolEditar(){
        try {
            return ValidacionUtil.validarVisualizacion(TAB_INF_TECNICA_CCMM, ValidacionUtil.OPC_EDICION, cmtOpcionesRolMglFacadeLocal, securityLogin);
        } catch (ApplicationException e) {
            LOGGER.error("Error al validar el rol de edición. ");
        }
        return false;
    }

    /**
     * Metodo para validar si el usuario tiene permisos de eliminacion
     * @return  {@code boolean} true si tiene permisos de eliminacion, false en caso contrario
     * @author Manuel Hernandez
     */
    public boolean isRolEliminar(){
        try {
            return ValidacionUtil.validarVisualizacion(TAB_INF_TECNICA_CCMM, ValidacionUtil.OPC_BORRADO, cmtOpcionesRolMglFacadeLocal, securityLogin);
        } catch (ApplicationException e) {
            LOGGER.error("Error al validar el rol de eliminación. ");
        }
        return false;
    }

    public void listInfoByPage(int page) {
        try {
            int firstResult = 0;
            if (page > 1) {
                firstResult = (filasPag * (page - 1));
            }

            filtroInformacionTecnicaDto = cmtInfoTecnicaMglFacadeLocal.findInformacionTecnica(subEdificioMgl.getSubEdificioId(), ConstantsCM.PAGINACION_DATOS, firstResult, filasPag);
            listInfoTecnica = filtroInformacionTecnicaDto.getListaTablasTipoBasica();
            habilitaObj = !listInfoTecnica.isEmpty();
            actual = page;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("No se encontraron resultados para la consulta:.." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en InfoTecnicaBean class ..." + e.getMessage(), e, LOGGER);
        }
        
    }

    public void goActualizar(CmtInfoTecnicaMgl item) {
        try {

            infoTecnicaMgl = item;
            tecnologiaSubUpdateID = infoTecnicaMgl.getTecnologiaSubMglObj().getTecnoSubedificioId();
          
            infoTecNivel1Update = infoTecnicaMgl.getBasicaIdInfoN1().getBasicaId();

            if (infoTecnicaMgl.getBasicaIdInfoN2() != null) {
                infoTecNivel2Update = infoTecnicaMgl.getBasicaIdInfoN2().getBasicaId();
            }
            if (infoTecnicaMgl.getBasicaIdInfoN3() != null) {
                infoTecNivel3Update = infoTecnicaMgl.getBasicaIdInfoN3().getBasicaId();
            }
            if (infoTecnicaMgl.getBasicaIdInfoN4() != null) {
                infoTecNivel4Update = infoTecnicaMgl.getBasicaIdInfoN4().getBasicaId();
            }
            nodoCobertura = infoTecnicaMgl.getTecnologiaSubMglObj().getNodoId().getNodCodigo();
            
            change();
        
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en InfoTecnicaBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public void change()  {
        try {
            cargar = false;
            btnActivo = false;
            disableTecno = true;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en InfoTecnicaBean class ..." + e.getMessage(), e, LOGGER);
        }
        
    }

    public boolean validarGuardarInfoTec() {
        List<BigDecimal> listaCmtBasicaMgl = new ArrayList<BigDecimal>();

        if (!listInfoTecnica.isEmpty()) {
            for (CmtInfoTecnicaMgl tec : listInfoTecnica) {
                listaCmtBasicaMgl.add(tec.getTecnologiaSubMglObj().getBasicaIdTecnologias().getBasicaId());
            }

            return listaCmtBasicaMgl.contains(tecnologiaSubMgl.getBasicaIdTecnologias().getBasicaId());
        } else {
            return false;
        }

    }

    public boolean validarModificarInfoTec() {
        if (!listInfoTecnica.isEmpty()) {
            List<BigDecimal> listaCmtBasicaMgl = new ArrayList<BigDecimal>();
            for (CmtInfoTecnicaMgl tec : listInfoTecnica) {
                listaCmtBasicaMgl.add(tec.getTecnologiaSubMglObj().getBasicaIdTecnologias().getBasicaId());
            }

            return listaCmtBasicaMgl.contains(tecnologiaSubUpdateID);
        } else {
            return false;
        }

    }

    public void guardarInfoTec() {
        if (!validarGuardarInfoTec()) {
            crearInfoTecMgl();
        } else if (infoTecnicaMgl == null) {
            limpiarCampos();
        } else {
            String msn = "Esta tecnologia ya existe  ";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            limpiarCampos();

        }

    }

    public void actualizaInfoTec() {
           actualizarlInfoTecMgl();
    }

    public void crearInfoTecMgl() {
        try {
            CmtInfoTecnicaMgl cmtInfTec;

            if (tecnologiaSubId == null && infoTecNivel1 == null ) {
                String msn = "Los campos Tecnologia y Informacion Tecnica Nivel 1 son obligatorios";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return;
            }
            if (tecnologiaSubId == null ) {
                String msn = "El campo Tecnologia es obligatorio";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return;
            }
             if (infoTecNivel1 == null ) {
                String msn = "El campo Informacion Tecnica Nivel 1 es obligatorio";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return;
            }
            CmtInfoTecnicaMgl cmtInfoTecni = new CmtInfoTecnicaMgl();
            cmtInfoTecni.setId(null);
            cmtInfoTecni.setIdSubedificio(subEdificioMgl);
            CmtBasicaMgl cmtBasicaMglInfoN1 = new CmtBasicaMgl();
            cmtBasicaMglInfoN1.setBasicaId(infoTecNivel1);
            cmtInfoTecni.setBasicaIdInfoN1(cmtBasicaMglInfoN1);
            
            CmtBasicaMgl cmtBasicaMglInfoN2 = new CmtBasicaMgl();
            if (infoTecNivel2 == null) {
                cmtBasicaMglInfoN2.setBasicaId(null);
            } else {
                cmtBasicaMglInfoN2.setBasicaId(infoTecNivel2);
                cmtInfoTecni.setBasicaIdInfoN2(cmtBasicaMglInfoN2);
            }
          
          
            CmtBasicaMgl cmtBasicaMglInfoN3 = new CmtBasicaMgl();
            if (infoTecNivel3 == null) {
                cmtBasicaMglInfoN3.setBasicaId(null);
            } else {
                cmtBasicaMglInfoN3.setBasicaId(infoTecNivel3);
                cmtInfoTecni.setBasicaIdInfoN3(cmtBasicaMglInfoN3);

            }
            CmtBasicaMgl cmtBasicaMglInfoN4 = new CmtBasicaMgl();
             if (infoTecNivel4 == null) {
                cmtBasicaMglInfoN4.setBasicaId(null);
            } else {
                cmtBasicaMglInfoN4.setBasicaId(infoTecNivel4);
                cmtInfoTecni.setBasicaIdInfoN4(cmtBasicaMglInfoN4);
            }

            cmtInfoTecni.setFechaCreacion(new Date());
            cmtInfoTecni.setFechaEdicion(null);
            cmtInfoTecni.setUsuarioEdicion("");
            cmtInfoTecni.setEstadoRegistro(ConstantsCM.REGISTRO_ENUSO_ID);
            cmtInfoTecni.setPerfilEdicion(0);
            cmtInfoTecni.setTecnologiaSubMglObj(tecnologiaSubMgl);
            cmtInfoTecnicaMglFacadeLocal.setUser(usuarioVT, perfilVT);
            cmtInfTec = cmtInfoTecnicaMglFacadeLocal.create(cmtInfoTecni);
            if (cmtInfTec != null) {
                listInfoByPage(1);
                limpiarCampos();
                String msn = "Proceso exitoso";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
            }

        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg, ex);
            String msn = "Proceso falló: " + ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
        }
    }
    
        public void actualizarlInfoTecMgl() {
            try {
                if (tecnologiaSubUpdateID == null && infoTecNivel1Update == null) {
                    String msn = "Los campos Tecnologia y Informacion Tecnica Nivel 1 son obligatorios";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                    return;
                }
                if (tecnologiaSubUpdateID == null) {
                    String msn = "El campo Tecnologia es obligatorio";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                    return;
                }
                if (infoTecNivel1Update == null) {
                    String msn = "El campo Informacion Tecnica Nivel 1 es obligatorio";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                    return;
                }
                CmtInfoTecnicaMgl cmtInfTec;
                if (infoTecNivel1Update != null) {
                    infoTecnicaMgl.setBasicaIdInfoN1(cmtBasicaMglFacadeLocal.findById(infoTecNivel1Update));
                }

                if (infoTecNivel2Update != null) {
                    infoTecnicaMgl.setBasicaIdInfoN2(cmtBasicaMglFacadeLocal.findById(infoTecNivel2Update));
                }

                if (infoTecNivel3Update != null) {
                    infoTecnicaMgl.setBasicaIdInfoN3(cmtBasicaMglFacadeLocal.findById(infoTecNivel3Update));
                }

                if (infoTecNivel4Update != null) {
                    infoTecnicaMgl.setBasicaIdInfoN4(cmtBasicaMglFacadeLocal.findById(infoTecNivel4Update));
                }

                infoTecnicaMgl.setUsuarioCreacion(infoTecnicaMgl.getUsuarioCreacion());
                infoTecnicaMgl.setFechaCreacion(infoTecnicaMgl.getFechaCreacion());
                infoTecnicaMgl.setFechaEdicion(new Date());
                infoTecnicaMgl.setUsuarioEdicion(usuarioVT);
                infoTecnicaMgl.setPerfilEdicion(perfilVT);

                cmtInfTec = cmtInfoTecnicaMglFacadeLocal.update(infoTecnicaMgl);
                if (cmtInfTec != null) {
                    listInfoByPage(1);
                    limpiarCampos();
                    cargar = true;
                    btnActivo = true;
                    disableTecno = false;
                    String msn = "Proceso exitoso";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                }
            } catch (ApplicationException e) {
                String msn = "Proceso falló: ";
                FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
            } catch (Exception e) {
                FacesUtil.mostrarMensajeError("Se generea error en InfoTecnicaBean class ..." + e.getMessage(), e, LOGGER);
            }
    }
   

    public void eliminarlInfoTecnica(CmtInfoTecnicaMgl cmtInfoTecnicaMgl) {

        try {

            if (cmtInfoTecnicaMgl.getId() == null) {
                String msn = "Seleccione un Registro a eliminar";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return;
            }

            boolean cmtInfTec = cmtInfoTecnicaMglFacadeLocal.delete(cmtInfoTecnicaMgl);
            if (cmtInfTec) {
                listInfoByPage(1);
                limpiarCampos();
                cargar = true;
                btnActivo = true;
                disableTecno = false;
                String msn = "Proceso exitoso";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
            }

        } catch (ApplicationException e) {
            String msn = "Proceso falló : ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en InfoTecnicaBean class ..." + e.getMessage(), e, LOGGER);
        }

    }

    public void limpiarCampos() {

        tecnologiaSubId = null;
        infoTecNivel1 = null;
        infoTecNivel2 = null;
        infoTecNivel3 = null;
        infoTecNivel4 = null;
        infoTecNivel1Update = null;
        infoTecNivel2Update = null;
        infoTecNivel3Update = null;
        infoTecNivel4Update = null;
        tecnologiaSubUpdateID = null;
        nodoCobertura = null;

    }

    public void reload(){
        try {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            FacesUtil.navegarAPagina(((HttpServletRequest) ec.getRequest()).getRequestURI());
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se genera error al recargar: " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genera error al recargar: " + e.getMessage(), e, LOGGER);
        }
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
            FacesUtil.mostrarMensajeError("Se generea error en InfoTecnicaBean class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en InfoTecnicaBean class ..." + e.getMessage(), e, LOGGER);
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
                int totalPaginas = 0;
                filtroInformacionTecnicaDto = cmtInfoTecnicaMglFacadeLocal.findInformacionTecnica(subEdificioMgl.getSubEdificioId(), ConstantsCM.PAGINACION_CONTAR, 0, 0);
                Long count = filtroInformacionTecnicaDto.getNumRegistros();
                totalPaginas = (int) ((count % filasPag != 0) ? (count / filasPag) + 1 : count / filasPag);
                return totalPaginas;
            } catch (ApplicationException e) {
                FacesUtil.mostrarMensajeError("Se generea error en InfoTecnicaBean class ..." + e.getMessage(), e, LOGGER);
            } catch (Exception e) {
                FacesUtil.mostrarMensajeError("Se generea error en InfoTecnicaBean class ..." + e.getMessage(), e, LOGGER);
            }
        }
        return 1;

    }
    
    public String escribeNombreCobertura(BigDecimal idTecnoSub)
            throws ApplicationException {

        tecnologiaSubMgl = cmtTecnologiaSubMglFacadeLocal.findByIdTecnoSub(idTecnoSub);
        if (tecnologiaSubMgl != null && tecnologiaSubMgl.getNodoId() != null) {
            nodoCobertura = tecnologiaSubMgl.getNodoId().getNodCodigo();
        }else{
            nodoCobertura="";
        }

        return nodoCobertura;
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
            FacesUtil.mostrarMensajeError("Se generea error en InfoTecnicaBean class ..." + e.getMessage(), e, LOGGER);
        }
        return pageActual;
    }


    public List<CmtInfoTecnicaMgl> getListInfoTecnica() {
        return listInfoTecnica;
    }

    public void setListInfoTecnica(List<CmtInfoTecnicaMgl> listInfoTecnica) {
        this.listInfoTecnica = listInfoTecnica;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CmtSubEdificioMgl getSubEdificioMgl() {
        return subEdificioMgl;
    }

    public void setSubEdificioMgl(CmtSubEdificioMgl subEdificioMgl) {
        this.subEdificioMgl = subEdificioMgl;
    }

    public CmtInfoTecnicaMgl getInfoTecnicaMgl() {
        return infoTecnicaMgl;
    }

    public void setInfoTecnicaMgl(CmtInfoTecnicaMgl infoTecnicaMgl) {
        this.infoTecnicaMgl = infoTecnicaMgl;
    }

    public HtmlDataTable getDatatableInfoTecnica() {
        return datatableInfoTecnica;
    }

    public void setDatatableInfoTecnica(HtmlDataTable datatableInfoTecnica) {
        this.datatableInfoTecnica = datatableInfoTecnica;
    }

    public int getFilasPag() {
        return filasPag;
    }

    public void setFilasPag(int filasPag) {
        this.filasPag = filasPag;
    }

    public FiltroInformacionTecnicaDto getFiltroInformacionTecnicaDto() {
        return filtroInformacionTecnicaDto;
    }

    public void setFiltroInformacionTecnicaDto(FiltroInformacionTecnicaDto filtroInformacionTecnicaDto) {
        this.filtroInformacionTecnicaDto = filtroInformacionTecnicaDto;
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

    public boolean isPintarPaginado() {
        return pintarPaginado;
    }

    public void setPintarPaginado(boolean pintarPaginado) {
        this.pintarPaginado = pintarPaginado;
    }

    public List<Integer> getPaginaList() {
        return paginaList;
    }

    public void setPaginaList(List<Integer> paginaList) {
        this.paginaList = paginaList;
    }

    public CmtInfoTecnicaMgl getCmtInfoTecnicaMgl() {
        return cmtInfoTecnicaMgl;
    }

    public void setCmtInfoTecnicaMgl(CmtInfoTecnicaMgl cmtInfoTecnicaMgl) {
        this.cmtInfoTecnicaMgl = cmtInfoTecnicaMgl;
    }

    public String getSpace() {
        return space;
    }

    public void setSpace(String space) {
        this.space = space;
    }

    public boolean isCargar() {
        return cargar;
    }

    public void setCargar(boolean cargar) {
        this.cargar = cargar;
    }
    
    public BigDecimal getTecnologiaSubId() {
        return tecnologiaSubId;
    }

    public void setTecnologiaSubId(BigDecimal tecnologiaSubId) {
        this.tecnologiaSubId = tecnologiaSubId;
    }
    

    public BigDecimal getInfoTecNivel1() {
        return infoTecNivel1;
    }

    public void setInfoTecNivel1(BigDecimal infoTecNivel1) {
        this.infoTecNivel1 = infoTecNivel1;
    }

    public BigDecimal getInfoTecNivel2() {
        return infoTecNivel2;
    }

    public void setInfoTecNivel2(BigDecimal infoTecNivel2) {
        this.infoTecNivel2 = infoTecNivel2;
    }

    public BigDecimal getInfoTecNivel3() {
        return infoTecNivel3;
    }

    public void setInfoTecNivel3(BigDecimal infoTecNivel3) {
        this.infoTecNivel3 = infoTecNivel3;
    }

    public BigDecimal getInfoTecNivel4() {
        return infoTecNivel4;
    }

    public void setInfoTecNivel4(BigDecimal infoTecNivel4) {
        this.infoTecNivel4 = infoTecNivel4;
    }

    public List<CmtBasicaMgl> getListInfTecN1() {
        return listInfTecN1;
    }

    public void setListInfTecN1(List<CmtBasicaMgl> listInfTecN1) {
        this.listInfTecN1 = listInfTecN1;
    }

    public List<CmtBasicaMgl> getListInfTecN3() {
        return listInfTecN3;
    }

    public void setListInfTecN3(List<CmtBasicaMgl> listInfTecN3) {
        this.listInfTecN3 = listInfTecN3;
    }

    public List<CmtBasicaMgl> getListInfTecN4() {
        return listInfTecN4;
    }

    public void setListInfTecN4(List<CmtBasicaMgl> listInfTecN4) {
        this.listInfTecN4 = listInfTecN4;
    }

    public List<CmtBasicaMgl> getListInfTecN2() {
        return listInfTecN2;
    }

    public void setListInfTecN2(List<CmtBasicaMgl> listInfTecN2) {
        this.listInfTecN2 = listInfTecN2;
    }

    public BigDecimal getInfoTecNivel1Update() {
        return infoTecNivel1Update;
    }

    public void setInfoTecNivel1Update(BigDecimal infoTecNivel1Update) {
        this.infoTecNivel1Update = infoTecNivel1Update;
    }

    public BigDecimal getInfoTecNivel2Update() {
        return infoTecNivel2Update;
    }

    public void setInfoTecNivel2Update(BigDecimal infoTecNivel2Update) {
        this.infoTecNivel2Update = infoTecNivel2Update;
    }

    public BigDecimal getInfoTecNivel3Update() {
        return infoTecNivel3Update;
    }

    public void setInfoTecNivel3Update(BigDecimal infoTecNivel3Update) {
        this.infoTecNivel3Update = infoTecNivel3Update;
    }

    public BigDecimal getInfoTecNivel4Update() {
        return infoTecNivel4Update;
    }

    public void setInfoTecNivel4Update(BigDecimal infoTecNivel4Update) {
        this.infoTecNivel4Update = infoTecNivel4Update;
    }
    
    public BigDecimal getTecnologiaSubUpdateID() {
        return tecnologiaSubUpdateID;
    }

    public void setTecnologiaSubUpdateID(BigDecimal tecnologiaSubUpdateID) {
        this.tecnologiaSubUpdateID = tecnologiaSubUpdateID;
    }
    
    public boolean isBtnActivo() {
        return btnActivo;
    }

    public void setBtnActivo(boolean btnActivo) {
        this.btnActivo = btnActivo;
    }

    public boolean isDisableTecno() {
        return disableTecno;
    }

    public void setDisableTecno(boolean disableTecno) {
        this.disableTecno = disableTecno;
    }

    public List<CmtTecnologiaSubMgl> getLsCmtTecnologiaSubMgls() {
        return lsCmtTecnologiaSubMgls;
    }

    public void setLsCmtTecnologiaSubMgls(List<CmtTecnologiaSubMgl> lsCmtTecnologiaSubMgls) {
        this.lsCmtTecnologiaSubMgls = lsCmtTecnologiaSubMgls;
    }

    public String getEstiloObligatorio() {
        return estiloObligatorio;
    }

    public void setEstiloObligatorio(String estiloObligatorio) {
        this.estiloObligatorio = estiloObligatorio;
    }

    public BigDecimal getNodos() {
        return nodos;
    }

    public void setNodos(BigDecimal nodos) {
        this.nodos = nodos;
    }

    public DrDireccion getDrDireccionDireccionCm() {
        return drDireccionDireccionCm;
    }

    public void setDrDireccionDireccionCm(DrDireccion drDireccionDireccionCm) {
        this.drDireccionDireccionCm = drDireccionDireccionCm;
    }

    public BigDecimal getCiudad() {
        return ciudad;
    }

    public void setCiudad(BigDecimal ciudad) {
        this.ciudad = ciudad;
    }

    public GeograficoPoliticoMgl getCentroPoblado() {
        return centroPoblado;
    }

    public void setCentroPoblado(GeograficoPoliticoMgl centroPoblado) {
        this.centroPoblado = centroPoblado;
    }

    public GeograficoPoliticoMgl getDepartamento() {
        return departamento;
    }

    public void setDepartamento(GeograficoPoliticoMgl departamento) {
        this.departamento = departamento;
    }

    public String getNodoCobertura() {
        return nodoCobertura;
    }

    public void setNodoCobertura(String nodoCobertura) {
        this.nodoCobertura = nodoCobertura;
    }


 
    
}
