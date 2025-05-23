/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.cm;

import co.com.claro.mgl.dtos.FiltroNotasDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.cm.*;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtNotaOtMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtNotasDetalleMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtNotasMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
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
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
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
import javax.servlet.http.HttpSession;

/**
 *
 * @author Admin
 */
@ManagedBean(name = "notasMglBean")
@ViewScoped
public class NotasMglBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LogManager.getLogger(NotasMglBean.class);
    private static final String TABNOTASCCMM = "TABNOTASCCMM";
    private String usuarioVT = null;
    private int perfilVT = 0;
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private CmtNotasMgl notaMgl;
    private CmtSubEdificioMgl subEdificioMgl;
    private CmtBasicaMgl tipoNotaSelected;
    private String notaOtComentarioStr;
    private boolean renderDetalle;
    private boolean renderAuditoria;
    private String msnRequired = Constant.MSN_REQUIRED_MESSAGE;
    private BigDecimal notaFiltroSelect;
    private BigDecimal notaOtFiltroSelect;
    private List<CmtNotasMgl> listNotasListar;
    private List<CmtNotaOtMgl> notasOtList;
    private List<AuditoriaDto> listAuditoria;
    private List<CmtBasicaMgl> tipoNotaList;
    private int tipoVerTabla;
    private SecurityLogin securityLogin;
    @EJB
    private CmtNotasDetalleMglFacadeLocal notasDetalleMglFacadeLocal;
    @EJB
    private CmtNotasMglFacadeLocal notasMglFacadeLocal;
    @EJB
    private CmtBasicaMglFacadeLocal basicaMglFacadeLocal;
    @EJB
    private CmtSubEdificioMglFacadeLocal subEdificioMglFacadeLocal;
    @EJB
    private CmtNotaOtMglFacadeLocal notaOtMglFacadeLocal;
    @EJB
    CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMglFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacade;
    private HashMap<String, Object> params;
    private boolean habilitaObj = false;
    private int actual;
    private String numPagina = "1";
    private boolean pintarPaginado = true;
    private List<Integer> paginaList;
    private String pageActual;
    private String notas_id;
    private String basica_id_tipo_nota;
    private String descripcion;
    private String nota;
    private String nombreArchivo;
    private String ubicCaja;
    private HtmlDataTable datatableInfoTecnica;
    private FiltroNotasDto filtroNotasDto;
    private int filasPag = ConstantsCM.PAGINACION_OCHO_FILAS;
    private CuentaMatrizBean cuentaMatrizBean;
    private boolean btnActivo = true;
    private boolean btnModiActivo = true;
    private boolean txtAreaComent = false;
    private boolean txtAgregar = false;

    public NotasMglBean() {
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
            notaMgl = new CmtNotasMgl();
            tipoNotaSelected = new CmtBasicaMgl();
            renderAuditoria = false;
            renderDetalle = false;
            this.cuentaMatrizBean = JSFUtil.getSessionBean(CuentaMatrizBean.class);
            if( this.cuentaMatrizBean.getCuentaMatriz() != null){
               subEdificioMgl = this.cuentaMatrizBean.getSelectedCmtSubEdificioMgl();  
            }
           

        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Se generea error en NotasMglBean class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en NotasMglBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    @PostConstruct
    public void cargarListas() {
        try {
            notasMglFacadeLocal.setUser(usuarioVT, perfilVT);
            notasDetalleMglFacadeLocal.setUser(usuarioVT, perfilVT);
            CmtTipoBasicaMgl tipoBasicaNotaOt;
            tipoBasicaNotaOt = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_TIPO_DE_NOTAS);
            tipoNotaList = basicaMglFacadeLocal.findByTipoBasica(tipoBasicaNotaOt);
            buscar();
            listInfoByPage(1);

            notasOtList = notaOtMglFacadeLocal.findByCm(subEdificioMgl.getCuentaMatrizObj(), notaOtFiltroSelect);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error en NotasMglBean class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en NotasMglBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Método para validar si el usuario tiene permisos para editar notas
     * @return {@code boolean} Retorna true si tiene permisos, false en caso contrario
     * @author Gildardo Mora
     */
    public boolean isRolEditar() {
        try {
            return ValidacionUtil.validarVisualizacion(TABNOTASCCMM, ValidacionUtil.OPC_EDICION, cmtOpcionesRolMglFacade, securityLogin);
        } catch (ApplicationException e) {
            LOGGER.error("Error al verificar si tiene rol de permisos de edicion de notas. " , e);
        }

        return false;
    }

    /**
     * Método para validar si el usuario tiene permisos para crear notas
     * @return {@code boolean} Retorna true si tiene permisos, false en caso contrario
     * @author Gildardo Mora
     */
    public boolean isRolCrear() {
        try {
             return ValidacionUtil.validarVisualizacion(TABNOTASCCMM, ValidacionUtil.OPC_CREACION, cmtOpcionesRolMglFacade, securityLogin);
        } catch (ApplicationException e) {
            LOGGER.error("Error al verificar si tiene rol de permisos de creación de notas. " , e);
        }

        return false;
    }

    public void listInfoByPage(int page) {
        try {
            int firstResult = 0;
            if (page > 1) {
                firstResult = (filasPag * (page - 1));
            }
            filtroNotasDto = notasMglFacadeLocal.findNotas(params, subEdificioMgl.getSubEdificioId(), ConstantsCM.PAGINACION_DATOS, firstResult, filasPag);
            listNotasListar = filtroNotasDto.getListaNotas();
            habilitaObj = !listNotasListar.isEmpty();
            if (listNotasListar.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "No se encontraron resultados para la consulta.", ""));
                return;
            }

            actual = page;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("No se encontraron resultados para la consulta:.." + e.getMessage(), e, LOGGER);
            return;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en NotasMglBean class ..." + e.getMessage(), e, LOGGER);
        }
        return;
    }

    public void goActualizar(CmtNotasMgl item) {
        try {
            notaMgl = item;
            tipoNotaSelected = notaMgl.getTipoNotaObj();
            btnActivo = true;
            renderDetalle = true;
            txtAgregar = false;
            if (notaMgl.getComentarioList().isEmpty()) {
                renderDetalle = false;
            }

        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
        }
    }

    public void hidePanelAgregarComentario() {

        txtAreaComent = false;

    }

    public String activarCampos() {
        try {
            notaMgl.setDescripcion("");
            notaMgl.setNota("");
            notaMgl.setTipoNotaObj(null);
            notaMgl.setNotasId(null);
            renderDetalle = false;
            hidePanelAgregarComentario();
            tipoNotaSelected = new CmtBasicaMgl();
            listNotasListar = notasMglFacadeLocal.findNotasBySubEdificioId(subEdificioMgl.getSubEdificioId(), notaFiltroSelect);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(NotasMglBean.class.getName() + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en NotasMglBean class ..." + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public void agregarComentario() {
        if (notaMgl.getNotasId() != null) {
            renderDetalle = true;
            btnModiActivo = true;
            txtAreaComent = true;

        } else {
            String msn = "Debe Seleccionar un registro para agregar un comentario";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));
        }

    }

    public void guardarNota() {
        try {
            if (!isRolEditar() || !isRolCrear()) {
                FacesUtil.mostrarMensajeWarn("No tiene permisos para realizar esta acción");
                return;
            }

            if (notaMgl.getDescripcion() == null || notaMgl.getDescripcion().isEmpty() || notaMgl.getNota() == null || notaMgl.getNota().isEmpty()
                    || tipoNotaSelected.getBasicaId() == null) {
                String msn = "Todos los campos son obligatorios";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return;
            }else{
                if (notaMgl.getNota().length() > 4000) {
                    String msn = "La cantidad de caracteres no debe ser mayor a 4000 ";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                    return;
                }
            }

            notaMgl.setSubEdificioObj(subEdificioMgl);
            notaMgl.setTipoNotaObj(tipoNotaSelected);
            notaMgl.setDescripcion(notaMgl.getDescripcion());
            notaMgl.setDetalle(notaMgl.getNota());
            notaMgl = notasMglFacadeLocal.create(notaMgl);
            if (notaMgl != null && notaMgl.getNotasId() != null) {
                subEdificioMgl = subEdificioMglFacadeLocal.findById(subEdificioMgl);
                tipoNotaSelected = new CmtBasicaMgl();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Constant.MSN_PROCESO_EXITOSO, ""));
                limpiarCamposNota();
                listNotasListar = notasMglFacadeLocal.findNotasBySubEdificioId(subEdificioMgl.getSubEdificioId(), notaFiltroSelect);
                renderDetalle = false;
                renderAuditoria = false;
            }
            
            listInfoByPage(1);

        } catch (ApplicationException e) {
            String msn = Constant.MSN_PROCESO_FALLO + " ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en NotasMglBean class ..." + e.getMessage(), e, LOGGER);
        }

    }

    public void ModificarNota() {
        try {

            if (notaMgl.getDescripcion() == null || notaMgl.getDescripcion().isEmpty() || notaMgl.getNota() == null || notaMgl.getNota().isEmpty()
                    || tipoNotaSelected.getBasicaId() == null) {
                String msn = "Todos los campos son obligatorios";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return;
            }
            notaMgl.setSubEdificioObj(subEdificioMgl);
            notaMgl.setTipoNotaObj(tipoNotaSelected);
            notaMgl = notasMglFacadeLocal.update(notaMgl);
            btnActivo = true;
            if (notaMgl != null && notaMgl.getNotasId() != null) {
                subEdificioMgl = subEdificioMglFacadeLocal.findById(subEdificioMgl);
                tipoNotaSelected = new CmtBasicaMgl();
                notaMgl = new CmtNotasMgl();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Constant.MSN_PROCESO_EXITOSO, ""));
                listNotasListar = notasMglFacadeLocal.findNotasBySubEdificioId(subEdificioMgl.getSubEdificioId(), notaFiltroSelect);
                renderDetalle = false;
                renderAuditoria = false;
            }
            listInfoByPage(1);
            limpiarCamposNota();
        } catch (ApplicationException e) {
            String msn = Constant.MSN_PROCESO_FALLO;
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en NotasMglBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public void guardarComentarioNota() {
        try {
            if (notaOtComentarioStr != null && !notaOtComentarioStr.isEmpty()) {
                if (notaOtComentarioStr.length() > 4000) {
                    String msn = "La cantidad de caracteres no debe ser mayor a 4000 ";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                    return;
                }
            } else {
                String msn = "El campo agregar comentario es obligatorio";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return;
            }
            CmtNotasDetalleMgl notaComentarioMgl = new CmtNotasDetalleMgl();
            notaComentarioMgl.setNota(notaOtComentarioStr);
            notaComentarioMgl.setNotaObj(notaMgl);
            notaComentarioMgl = notasDetalleMglFacadeLocal.create(notaComentarioMgl);
            if (notaComentarioMgl.getNotasDetalleId() != null) {
                notaOtComentarioStr = "";
                subEdificioMgl = subEdificioMglFacadeLocal.findById(subEdificioMgl);
                listNotasListar = notasMglFacadeLocal.findNotasBySubEdificioId(subEdificioMgl.getSubEdificioId(), notaFiltroSelect);
                tipoNotaSelected = new CmtBasicaMgl();
                notaMgl = new CmtNotasMgl();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, Constant.MSN_PROCESO_EXITOSO, ""));
                renderDetalle = true;
                renderAuditoria = false;

            }

            listInfoByPage(1);
            hidePanelAgregarComentario();
            limpiarCamposNota();

        } catch (ApplicationException e) {
            String msn = Constant.MSN_PROCESO_FALLO;
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en NotasMglBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public void limpiarCamposNota() {
        notaMgl.setDescripcion("");
        notaMgl.setNota("");
        notaMgl.setTipoNotaObj(null);
        notaMgl.setNotasId(null);

    }

    public void reload() throws ApplicationException {
        try {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            FacesUtil.navegarAPagina(((HttpServletRequest) ec.getRequest()).getRequestURI());
        } catch (ApplicationException e) {
            throw new ApplicationException("Error al momento de recargar: " + e.getMessage(), e);
        }

    }

    public String volver() {
        try {
            renderDetalle = false;
            renderAuditoria = false;
        } catch (Exception ex) {
            LOGGER.error("Error al volver. EX000 " + ex.getMessage(), ex);
            String msn = Constant.MSN_PROCESO_FALLO + " " + ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));

        }
        return null;
    }

    public String irCrearNota() {
        try {
            notaMgl = new CmtNotasMgl();
            tipoNotaSelected = new CmtBasicaMgl();
            renderDetalle = true;
            renderAuditoria = false;
        } catch (Exception ex) {
            LOGGER.error("Error al crear nota. EX000 " + ex.getMessage(), ex);
            String msn = Constant.MSN_PROCESO_FALLO + " " + ex.getMessage();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));

        }
        return null;
    }

    public String mostrarAuditoriaCompetencia(CmtNotasMgl cmtNotasMgl) {
        renderDetalle = false;
        renderAuditoria = true;

        try {
            listAuditoria = notasMglFacadeLocal.construirAuditoria(cmtNotasMgl);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            FacesUtil.mostrarMensajeError("Se generea error en NotasMglBean class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en NotasMglBean class ..." + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public String busarnotasTipoSeleccionado() {
        try {
            listNotasListar = notasMglFacadeLocal.findNotasBySubEdificioId(subEdificioMgl.getSubEdificioId(), notaFiltroSelect);
        } catch (ApplicationException e) {
            String msn = Constant.MSN_PROCESO_FALLO;
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en NotasMglBean class ..." + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public String busarnotasCM() {
        try {
            listNotasListar = notasMglFacadeLocal.findNotasBySubEdificioId(subEdificioMgl.getSubEdificioId(), notaFiltroSelect);
        } catch (ApplicationException e) {
            String msn = Constant.MSN_PROCESO_FALLO;
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en NotasMglBean class ..." + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public String busarNotaOtTipoSeleccionado() {
        try {
            notasOtList = notaOtMglFacadeLocal.findByCm(subEdificioMgl.getCuentaMatrizObj(), notaOtFiltroSelect);
        } catch (ApplicationException e) {
            String msn = Constant.MSN_PROCESO_FALLO;
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en NotasMglBean class ..." + e.getMessage(), e, LOGGER);
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
            FacesUtil.mostrarMensajeError("Se generea error en NotasMglBean class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en NotasMglBean class ..." + e.getMessage(), e, LOGGER);
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
            LOGGER.error("Error al ir a la siguiente página EX000 " + ex.getMessage(), ex);
        }
    }

    public void pageLast() {
        try {
            int totalPaginas = getTotalPaginas();
            listInfoByPage(totalPaginas);
        } catch (Exception ex) {
            LOGGER.error("Error al ir a la última página EX000 " + ex.getMessage(), ex);
        }

    }

    public int getTotalPaginas() {
        if (pintarPaginado) {
            try {
                buscar();
                int totalPaginas = 0;
                FiltroNotasDto filtroNotasDto;
                filtroNotasDto = notasMglFacadeLocal.findNotas(params, subEdificioMgl.getSubEdificioId(), ConstantsCM.PAGINACION_CONTAR, 0, 0);
                Long count = filtroNotasDto.getNumRegistros();
                totalPaginas = (int) ((count % filasPag != 0) ? (count / filasPag) + 1 : count / filasPag);
                return totalPaginas;
            } catch (ApplicationException e) {
                FacesUtil.mostrarMensajeError("Se generea error en NotasMglBean class ..." + e.getMessage(), e, LOGGER);
            } catch (Exception e) {
                FacesUtil.mostrarMensajeError("Se generea error en NotasMglBean class ..." + e.getMessage(), e, LOGGER);
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
            FacesUtil.mostrarMensajeError("Se generea error en NotasMglBean class ..." + e.getMessage(), e, LOGGER);
        }
        return pageActual;
    }

    public void buscar() {
        try {
            params = new HashMap<String, Object>();

            params.put("all", "1");
            params.put("notas_id", this.notas_id);
            params.put("descripcion", this.descripcion);
            params.put("nota", this.nota);

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error al armar la consulta de CM.", ""));

        }
    }

    public CmtNotasMgl getNotaMgl() {
        return notaMgl;
    }

    public void setNotaMgl(CmtNotasMgl notaMgl) {
        this.notaMgl = notaMgl;
    }

    public CmtBasicaMgl getTipoNotaSelected() {
        return tipoNotaSelected;
    }

    public void setTipoNotaSelected(CmtBasicaMgl tipoNotaSelected) {
        this.tipoNotaSelected = tipoNotaSelected;
    }

    public List<CmtBasicaMgl> getTipoNotaList() {
        return tipoNotaList;
    }

    public void setTipoNotaList(List<CmtBasicaMgl> tipoNotaList) {
        this.tipoNotaList = tipoNotaList;
    }

    public String getNotaOtComentarioStr() {
        return notaOtComentarioStr;
    }

    public void setNotaOtComentarioStr(String notaOtComentarioStr) {
        this.notaOtComentarioStr = notaOtComentarioStr;
    }

    public CmtSubEdificioMgl getSubEdificioMgl() {
        return subEdificioMgl;
    }

    public void setSubEdificioMgl(CmtSubEdificioMgl subEdificioMgl) {
        this.subEdificioMgl = subEdificioMgl;
    }

    public boolean isRenderDetalle() {
        return renderDetalle;
    }

    public void setRenderDetalle(boolean renderDetalle) {
        this.renderDetalle = renderDetalle;
    }

    public boolean isRenderAuditoria() {
        return renderAuditoria;
    }

    public void setRenderAuditoria(boolean renderAuditoria) {
        this.renderAuditoria = renderAuditoria;
    }

    public String getMsnRequired() {
        return msnRequired;
    }

    public void setMsnRequired(String msnRequired) {
        this.msnRequired = msnRequired;
    }

    public List<AuditoriaDto> getListAuditoria() {
        return listAuditoria;
    }

    public void setListAuditoria(
            List<AuditoriaDto> listAuditoria) {
        this.listAuditoria = listAuditoria;
    }

    public BigDecimal getNotaFiltroSelect() {
        return notaFiltroSelect;
    }

    public void setNotaFiltroSelect(BigDecimal notaFiltroSelect) {
        this.notaFiltroSelect = notaFiltroSelect;
    }

    public List<CmtNotasMgl> getListNotasListar() {
        return listNotasListar;
    }

    public void setListNotasListar(List<CmtNotasMgl> listNotasListar) {
        this.listNotasListar = listNotasListar;
    }

    public int getTipoVerTabla() {
        return tipoVerTabla;
    }

    public void setTipoVerTabla(int tipoVerTabla) {
        this.tipoVerTabla = tipoVerTabla;
    }

    public List<CmtNotaOtMgl> getNotasOtList() {
        return notasOtList;
    }

    public void setNotasOtList(List<CmtNotaOtMgl> notasOtList) {
        this.notasOtList = notasOtList;
    }

    public BigDecimal getNotaOtFiltroSelect() {
        return notaOtFiltroSelect;
    }

    public void setNotaOtFiltroSelect(BigDecimal notaOtFiltroSelect) {
        this.notaOtFiltroSelect = notaOtFiltroSelect;
    }

    public String getNotas_id() {
        return notas_id;
    }

    public void setNotas_id(String notas_id) {
        this.notas_id = notas_id;
    }

    public String getBasica_id_tipo_nota() {
        return basica_id_tipo_nota;
    }

    public void setBasica_id_tipo_nota(String basica_id_tipo_nota) {
        this.basica_id_tipo_nota = basica_id_tipo_nota;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
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

    public boolean isBtnActivo() {
        return btnActivo;
    }

    public void setBtnActivo(boolean btnActivo) {
        this.btnActivo = btnActivo;
    }

    public boolean isBtnModiActivo() {
        return btnModiActivo;
    }

    public void setBtnModiActivo(boolean btnModiActivo) {
        this.btnModiActivo = btnModiActivo;
    }

    public boolean isTxtAreaComent() {
        return txtAreaComent;
    }

    public void setTxtAreaComent(boolean txtAreaComent) {
        this.txtAreaComent = txtAreaComent;
    }

    public boolean isTxtAgregar() {
        return txtAgregar;
    }

    public void setTxtAgregar(boolean txtAgregar) {
        this.txtAgregar = txtAgregar;
    }
 
}
