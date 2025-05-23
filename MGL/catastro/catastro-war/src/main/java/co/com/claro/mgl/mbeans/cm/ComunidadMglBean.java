/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.cm;

import co.com.claro.mgl.dtos.CmtFiltroConsultaComunidadDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.ComunidadMglFacade;
import co.com.claro.mgl.facade.ComunidadMglFacadeLocal;
import co.com.claro.mgl.facade.GeograficoPoliticoMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtRegionalMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoBasicaMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.PaginacionDto;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtComunidadRr;
import co.com.claro.mgl.jpa.entities.cm.CmtRegionalRr;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
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
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author aleal
 */
@ManagedBean(name = "comunidadesBean")
@ViewScoped
public class ComunidadMglBean implements Serializable {

    @EJB
    private ComunidadMglFacadeLocal comunidadMglFacadeLocal = new ComunidadMglFacade();
    @EJB
    private GeograficoPoliticoMglFacadeLocal geograficoPoliticoMglFacadeLocal;
    @EJB
    private CmtBasicaMglFacadeLocal cmtBasicaMglFacadeLocal;
    @EJB
    private CmtRegionalMglFacadeLocal cmtRegionalMglFacadeLocal;
    @EJB
    private CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMglFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacade;
    
    private SecurityLogin securityLogin;
    private String estiloObligatorio = "<font color='red'>*</font>";
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private static final Logger LOGGER = LogManager.getLogger(ComunidadMglBean.class);

    private List<GeograficoPoliticoMgl> listGeograficoPoliticoMgl;
    private List<GeograficoPoliticoMgl> listGeograficoMgl;
    private List<GeograficoPoliticoMgl> listCentrosPoblados;
    private List<CmtRegionalRr> listRegional;

    private GeograficoPoliticoMgl departamentoTabla;
    private boolean guardado;
    private String tecRegional;
    private List<CmtBasicaMgl> listBasica = null;
    private List<CmtBasicaMgl> listBasicaMgl;
    private String idSqlSelected;

    private String usuarioVT = null;
    private int usuarioID = 0;
    private CmtComunidadRr cmtComunidadRr = null;
    private List<CmtComunidadRr> comunidadRrList;
    int cod = 160;
    BigDecimal tipoBasicaId = new BigDecimal(cod);
    private CmtTipoBasicaMgl cmtTipoBasicaMgl;

    private String message;
    private HtmlDataTable dataTable = new HtmlDataTable();
    private String numPagina;
    private List<Integer> paginaList;
    private String pageActual;
    private int actual;
    private static final int numeroRegistrosConsulta = 15;

    private BigDecimal regional;
    private BigDecimal tecnologia;
    private BigDecimal centroPoblado; 

    private BigDecimal geograficoPoliticoMglDpto;
    private BigDecimal geograficoMglCiudad;

    private CmtFiltroConsultaComunidadDto filtroConsultaComunidadDto = new CmtFiltroConsultaComunidadDto();
    
    //Opciones agregadas para Rol
    private final String COMUBTNCR = "COMUBTNCR";
    private final String VALIDAROPCIDROLCOMU = "VALIDAROPCIDROLCOMU";

    public ComunidadMglBean() {
        try {
            try {
                securityLogin = new SecurityLogin(facesContext);
                if (!securityLogin.isLogin()) {
                    if (!response.isCommitted()) {
                        response.sendRedirect(securityLogin.redireccionarLogin());
                    }
                    return;
                }
                usuarioVT = securityLogin.getLoginUser();
                if (usuarioVT == null) {
                    session.getAttribute("usuarioM");
                    usuarioVT = (String) session.getAttribute("usuarioM");
                } else {
                    session = (HttpSession) facesContext.getExternalContext().getSession(false);
                    session.setAttribute("usuarioM", usuarioVT);
                }
                usuarioID = securityLogin.getPerfilUsuario();
                if (usuarioID == 0) {
                    session.getAttribute("usuarioIDM");
                    usuarioID = Integer.parseInt((String) session.getAttribute("usuarioIDM"));
                } else {
                    session = (HttpSession) facesContext.getExternalContext().getSession(false);
                    session.setAttribute("usuarioIDM", usuarioID);
                }
            } catch (IOException e) {
                FacesUtil.mostrarMensajeError("Se generea error en ComunidadMglBean class ..." + e.getMessage(), e, LOGGER);
            } catch (Exception e) {
                FacesUtil.mostrarMensajeError("Se generea error en ComunidadMglBean class ..." + e.getMessage(), e, LOGGER);
            }
        } catch (NumberFormatException e) {
            String msgError = "Error  en " + ClassUtils.getCurrentMethodName(this.getClass()) + ": " + e.getMessage();
            LOGGER.error(msgError, e);
        }
    }

    @PostConstruct
    public void fillSqlList() {
        try {
            CmtComunidadRr idComunidadRr = (CmtComunidadRr) session.getAttribute("idComunidadRr");
            session.removeAttribute("idComunidadRr");
            if (idComunidadRr != null) {
                cmtComunidadRr = idComunidadRr;
                irComunidadMgl();
            } else {
                cmtComunidadRr = new CmtComunidadRr();
                cmtComunidadRr.setComunidadRrId(BigDecimal.ZERO);
                listGeograficoMgl = new ArrayList<GeograficoPoliticoMgl>();
                listCentrosPoblados = new ArrayList<GeograficoPoliticoMgl>();
            }
            comunidadRrList = new ArrayList<CmtComunidadRr>();
            listInfoByPage(1);

            listGeograficoPoliticoMgl = geograficoPoliticoMglFacadeLocal.findDptos();
            listRegional = cmtRegionalMglFacadeLocal.findAll();
            cmtTipoBasicaMgl = cmtTipoBasicaMglFacadeLocal.findByTipoBasicaId(new BigDecimal(Constant.TIPO_BASICA_ID));
            listBasicaMgl = cmtBasicaMglFacadeLocal.findByTipoBasica(cmtTipoBasicaMgl);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(ComunidadMglBean.class.getName()+ e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ComunidadMglBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public void irComunidadMgl()  {  
        try {

            tecnologia = cmtComunidadRr.getTecnologia() == null ? new BigDecimal("0")
                    : cmtComunidadRr.getTecnologia().getBasicaId();

            regional = cmtComunidadRr.getRegionalRr() == null ? new BigDecimal("0")
                    : cmtComunidadRr.getRegionalRr().getRegionalRrId();
            /*
         * Se utiliza para los registros que no contiene codigo de ciudad
             */
            if (cmtComunidadRr.getCiudad() == null) {
                consultarCiudades();
                consultarCentrosPoblados();
            } else {

                centroPoblado = cmtComunidadRr.getCiudad() == null ? new BigDecimal("0")
                        : cmtComunidadRr.getCiudad().getGpoId();

                GeograficoPoliticoMgl cuidad = geograficoPoliticoMglFacadeLocal
                        .findById(cmtComunidadRr.getCiudad().getGeoGpoId());

                geograficoMglCiudad = cuidad.getGpoId() == null ? new BigDecimal("0") : cuidad.getGpoId();

                GeograficoPoliticoMgl depto = geograficoPoliticoMglFacadeLocal.findById(cuidad.getGeoGpoId());

                geograficoPoliticoMglDpto = depto.getGpoId() == null ? new BigDecimal("0") : depto.getGpoId();

                consultarCiudades();
                consultarCentrosPoblados();

            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ComunidadMglBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public void buscarGeograficoPoliticoById(BigDecimal gpoId) {
        try {
            departamentoTabla = geograficoPoliticoMglFacadeLocal.findById(gpoId);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error en ComunidadMglBean class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ComunidadMglBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public void crearComunidadesMgl() {
        try {
            cmtComunidadRr.setComunidadRrId(null);
            cmtComunidadRr.setEstadoRegistro(1);
            if (configurarComunidad()) {
                cmtComunidadRr.setUsuarioCreacion(usuarioVT);
                cmtComunidadRr.setFechaCreacion(new Date());
                comunidadMglFacadeLocal.setUser(usuarioVT, usuarioID);
                cmtComunidadRr = comunidadMglFacadeLocal.create(cmtComunidadRr);
                message = "Proceso exitoso: se ha creado la comunidad  <b>"
                        + cmtComunidadRr.getComunidadRrId() + "</b>  "
                        + "satisfactoriamente";
            }
            session.setAttribute("message", message);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, ""));
        } catch (ApplicationException e) {
            cmtComunidadRr.setComunidadRrId(new BigDecimal(0));
            message = "Proceso falló";
            FacesUtil.mostrarMensajeError(message + e.getMessage(), e, LOGGER);
            session.setAttribute("message", message);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ComunidadMglBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public void actualizarlComunidadesMgl() {
        try {
            if (configurarComunidad()) {
                cmtComunidadRr.setUsuarioEdicion((String) session.getAttribute("usuarioM"));
                cmtComunidadRr.setFechaEdicion(new Date());
                comunidadMglFacadeLocal.setUser(usuarioVT, usuarioID);
                comunidadMglFacadeLocal.update(cmtComunidadRr);
                message = "Proceso exitoso: Se ha actualizado la comunidad:  <b>"
                        + cmtComunidadRr.getNombreComunidad() + "</b> "
                        + " satisfatoriamente";
            }
            session.setAttribute("message", message);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, ""));
        } catch (ApplicationException e) {
            message = "Proceso falló: ";
            FacesUtil.mostrarMensajeError(message + e.getMessage(), e, LOGGER);
            session.setAttribute("message", message);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ComunidadMglBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public String eliminarlComunidadesMgl() {
        try {
            if (configurarComunidad()){
                cmtComunidadRr.setUsuarioEdicion((String) session.getAttribute("usuarioM"));
                cmtComunidadRr.setFechaEdicion(new Date());
                comunidadMglFacadeLocal.setUser(usuarioVT, usuarioID);
                comunidadMglFacadeLocal.delete(cmtComunidadRr);
                message = "Proceso exitoso: Se ha eliminado la comunidad:  <b>" + cmtComunidadRr.getNombreComunidad() + "</b> "
                        + " satisfatoriamente";
                session.setAttribute("message", message);
            }
            session.setAttribute("message", message);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, ""));
        } catch (ApplicationException e) {
            message = "Proceso falló: " + e.getMessage();
            FacesUtil.mostrarMensajeError(message+ e.getMessage(), e, LOGGER);
            session.setAttribute("message", message);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ComunidadMglBean class ..." + e.getMessage(), e, LOGGER);
        }
        return "comunidadesMglViewList";
    }

    private boolean configurarComunidad()  {
        try {
            if (cmtComunidadRr.getCodigoRr() == null || cmtComunidadRr.getCodigoRr().trim().isEmpty()) {
                message = "Campo Código es requerido";
            }

            CmtRegionalRr regionalTabla = new CmtRegionalRr();
            regionalTabla.setRegionalRrId(regional);
            regionalTabla = cmtRegionalMglFacadeLocal.findById(regionalTabla);
            if (regionalTabla.getRegionalRrId().equals(BigDecimal.ZERO)) {
                message = "Campo Regional es requerido";
            } else {
                cmtComunidadRr.setRegionalRr(regionalTabla);
            }
            CmtBasicaMgl basicaTabla = new CmtBasicaMgl();
            basicaTabla.setBasicaId(tecnologia);
            if (basicaTabla.getBasicaId().equals(BigDecimal.ZERO)) {
                message = "Campo Tecnología es requerido";
            } else {
                cmtComunidadRr.setTecnologia(basicaTabla);
            }

            GeograficoPoliticoMgl centrosTabla;
            centrosTabla = geograficoPoliticoMglFacadeLocal.findById(centroPoblado);
            if (centrosTabla.getGpoId().equals(BigDecimal.ZERO)) {
                message = "Campo Centro poblado es requerido";
            } else {
                cmtComunidadRr.setCiudad(centrosTabla);
            }

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ComunidadMglBean class ..." + e.getMessage(), e, LOGGER);
        }
        return true;
    }

    public String goActualizar() {
        try {
            cmtComunidadRr = (CmtComunidadRr) dataTable.getRowData();
            session.setAttribute("idComunidadRr", cmtComunidadRr);
            listGeograficoPoliticoMgl = geograficoPoliticoMglFacadeLocal.findDptos();
            listBasicaMgl = cmtBasicaMglFacadeLocal.findAll();
            return "comunidadesMglView";
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error en ComunidadMglBean class ..." + e.getMessage(), e, LOGGER);
            if (session == null) {
                return "comunidadesMglViewList";
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ComunidadMglBean class ..." + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public String nuevaComunidadMgl() {
        cmtComunidadRr = null;
        cmtComunidadRr = new CmtComunidadRr();
        cmtComunidadRr.setComunidadRrId(BigDecimal.ZERO);
        setGuardado(true);
        return "comunidadesMglView";
    }

    public void listBasicaChange() {
        if (this.tecRegional != null && this.tecRegional.equals("0")) {
            cmtComunidadRr.setTecnologia(null);
            return;
        }

        for (CmtBasicaMgl b : listBasica) {
            if (b.getBasicaId().compareTo(new BigDecimal(this.tecRegional)) == 0) {
                cmtComunidadRr.setTecnologia(b);
                return;
            }
        }
        cmtComunidadRr.setTecnologia(null);
    }

    public void consultarCiudades() {
        try {
            listGeograficoMgl = geograficoPoliticoMglFacadeLocal.findCiudades((geograficoPoliticoMglDpto));
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error en ComunidadMglBean class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ComunidadMglBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public void consultarCentrosPoblados() {
        try {
            listCentrosPoblados
                    = geograficoPoliticoMglFacadeLocal.findCentrosPoblados((geograficoMglCiudad));
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error en ComunidadMglBean class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ComunidadMglBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public void findByFiltro() {
        try {
            pageFirst();
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ComunidadMglBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public void pageFirst() {
        try {
            listInfoByPage(1);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ComunidadMglBean class ..." + e.getMessage(), e, LOGGER);
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

        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ComunidadMglBean class ..." + e.getMessage(), e, LOGGER);
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
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ComunidadMglBean class ..." + e.getMessage(), e, LOGGER);
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
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ComunidadMglBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public void pageLast() {
        try {
            int totalPaginas = getTotalPaginas();
            listInfoByPage(totalPaginas);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ComunidadMglBean class ..." + e.getMessage(), e, LOGGER);
        }
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
            FacesUtil.mostrarMensajeError("Se generea error en ComunidadMglBean class ..." + e.getMessage(), e, LOGGER);
        }
        return pageActual;
    }

    public String listInfoByPage(int page) {
        try {
            PaginacionDto<CmtComunidadRr> paginacionDto
                    = comunidadMglFacadeLocal.findAllPaginado(page, numeroRegistrosConsulta, filtroConsultaComunidadDto);
            comunidadRrList = paginacionDto.getListResultado();
            actual = page;
        } catch (ApplicationException e) {
            String msn = Constant.MSN_PROCESO_FALLO + " " + e.getMessage();
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ComunidadMglBean class ..." + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public int getTotalPaginas() {
        try {
            PaginacionDto<CmtComunidadRr> paginacionDto
                    = comunidadMglFacadeLocal.findAllPaginado(0, numeroRegistrosConsulta, filtroConsultaComunidadDto);
            Long count = paginacionDto.getNumPaginas();
            int totalPaginas = (int) ((count % numeroRegistrosConsulta != 0)
                    ? (count / numeroRegistrosConsulta) + 1
                    : count / numeroRegistrosConsulta);
            return totalPaginas;
        } catch (ApplicationException e) {
            String msn = "Se generea al cargar lista de Nodos:" ;
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ComunidadMglBean class ..." + e.getMessage(), e, LOGGER);
        }
        return 1;
    }
    
    // Validar Opciones por Rol    
    public boolean validarOpcionNuevo() {
        return validarEdicionRol(COMUBTNCR);
    }
    
    public boolean validarIdRol() {
        return validarEdicionRol(VALIDAROPCIDROLCOMU);
    }
    
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

    public HtmlDataTable getDataTable() {
        return dataTable;
    }

    public void setDataTable(HtmlDataTable dataTable) {
        this.dataTable = dataTable;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<CmtComunidadRr> getComunidadRrList() {
        return comunidadRrList;
    }

    public void setComunidadRrList(List<CmtComunidadRr> comunidadRrList) {
        this.comunidadRrList = comunidadRrList;
    }

    public BigDecimal getGeograficoPoliticoMglDpto() {
        return geograficoPoliticoMglDpto;
    }

    public void setGeograficoPoliticoMglDpto(BigDecimal geograficoPoliticoMglDpto) {
        this.geograficoPoliticoMglDpto = geograficoPoliticoMglDpto;
    }

    public BigDecimal getGeograficoMgl() {
        return geograficoMglCiudad;
    }

    public void setGeograficoMgl(BigDecimal geograficoMgl) {
        this.geograficoMglCiudad = geograficoMgl;
    }

    public GeograficoPoliticoMgl getGeograficoPoliticoMglDpo() {
        return departamentoTabla;
    }

    public void setGeograficoPoliticoMglDpo(GeograficoPoliticoMgl geograficoPoliticoMglDpo) {
        this.departamentoTabla = geograficoPoliticoMglDpo;
    }

    public boolean isGuardado() {
        return guardado;
    }

    public void setGuardado(boolean guardado) {
        this.guardado = guardado;
    }

    public CmtFiltroConsultaComunidadDto getFiltroConsultaComunidadDto() {
        return filtroConsultaComunidadDto;
    }

    public void setFiltroConsultaComunidadDto(CmtFiltroConsultaComunidadDto filtroConsultaComunidadDto) {
        this.filtroConsultaComunidadDto = filtroConsultaComunidadDto;
    }

    public CmtComunidadRr getCmtComunidadRr() {
        return cmtComunidadRr;
    }

    public void setCmtComunidadRr(CmtComunidadRr cmtComunidadRr) {
        this.cmtComunidadRr = cmtComunidadRr;
    }

    public List<GeograficoPoliticoMgl> getListGeograficoPoliticoMgl() {
        return listGeograficoPoliticoMgl;
    }

    public void setListGeograficoPoliticoMgl(List<GeograficoPoliticoMgl> listGeograficoPoliticoMgl) {
        this.listGeograficoPoliticoMgl = listGeograficoPoliticoMgl;
    }

    public List<GeograficoPoliticoMgl> getListGeograficoMgl() {
        return listGeograficoMgl;
    }

    public void setListGeograficoMgl(List<GeograficoPoliticoMgl> listGeograficoMgl) {
        this.listGeograficoMgl = listGeograficoMgl;
    }

    public List<GeograficoPoliticoMgl> getListCentrosPoblados() {
        return listCentrosPoblados;
    }

    public void setListCentrosPoblados(List<GeograficoPoliticoMgl> listCentrosPoblados) {
        this.listCentrosPoblados = listCentrosPoblados;
    }

    public String getEstiloObligatorio() {
        return estiloObligatorio;
    }

    public void setEstiloObligatorio(String estiloObligatorio) {
        this.estiloObligatorio = estiloObligatorio;
    }

    public GeograficoPoliticoMglFacadeLocal getGeograficoPoliticoMglFacadeLocal() {
        return geograficoPoliticoMglFacadeLocal;
    }

    public void setGeograficoPoliticoMglFacadeLocal(GeograficoPoliticoMglFacadeLocal geograficoPoliticoMglFacadeLocal) {
        this.geograficoPoliticoMglFacadeLocal = geograficoPoliticoMglFacadeLocal;
    }

    public List<CmtBasicaMgl> getListBasica() {
        return listBasica;
    }

    public void setListBasica(List<CmtBasicaMgl> listBasica) {
        this.listBasica = listBasica;
    }

    public List<CmtRegionalRr> getListRegional() {
        return listRegional;
    }

    public void setListRegional(List<CmtRegionalRr> listRegional) {
        this.listRegional = listRegional;
    }

    public String getTecRegional() {
        return tecRegional;
    }

    public void setTecRegional(String tecRegional) {
        this.tecRegional = tecRegional;
    }

    public List<CmtBasicaMgl> getListBasicaMgl() {
        return listBasicaMgl;
    }

    public void setListBasicaMgl(List<CmtBasicaMgl> listBasicaMgl) {
        this.listBasicaMgl = listBasicaMgl;
    }

    public String getIdSqlSelected() {
        return idSqlSelected;
    }

    public void setIdSqlSelected(String idSqlSelected) {
        this.idSqlSelected = idSqlSelected;
    }

    public BigDecimal getRegional() {
        return regional;
    }

    public void setRegional(BigDecimal regional) {
        this.regional = regional;
    }

    public BigDecimal getTecnologia() {
        return tecnologia;
    }

    public void setTecnologia(BigDecimal tecnologia) {
        this.tecnologia = tecnologia;
    }

    public BigDecimal getCentroPoblado() {
        return centroPoblado;
    }

    public void setCentroPoblado(BigDecimal centroPoblado) {
        this.centroPoblado = centroPoblado;
    }   
}
