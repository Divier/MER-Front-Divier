package co.com.claro.mgl.mbeans.cm;

import co.com.claro.mer.blockreport.BlockReportServBean;
import co.com.claro.mgl.businessmanager.address.ComponenteDireccionesManager;
import co.com.claro.mgl.dtos.FiltroConsultaCompaniasDto;
import co.com.claro.mgl.dtos.ResponseValidarDireccionDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.GeograficoPoliticoMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtCompaniaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtHorarioRestriccionMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoCompaniaMglFacadeLocal;
import co.com.claro.mgl.facade.ptlus.UsuarioServicesFacadeLocal;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCompaniaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtHorarioRestriccionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoCompaniaMgl;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.claro.mgl.rest.dtos.UsuariosServicesDTO;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.data.AddressRequest;
import co.com.telmex.catastro.data.AddressService;
import co.com.telmex.catastro.data.AddressSuggested;
import co.com.telmex.catastro.services.georeferencia.AddressEJBRemote;
import co.com.telmex.catastro.services.util.Constant;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Parzifal de León
 */
@ManagedBean(name = "companiasAdministracionBean")
@ViewScoped
@Log4j2
public class CompaniasAdministracionBean implements Serializable {

    @EJB
    private CmtHorarioRestriccionMglFacadeLocal horarioRestriccionFacadeLocal;
    @EJB
    private CmtCompaniaMglFacadeLocal companiaFacade;
    @EJB
    private GeograficoPoliticoMglFacadeLocal geograficoPoliticoMglFacadeLocal;
    @EJB
    private AddressEJBRemote addressEJBRemote;
    @EJB
    private CmtTipoCompaniaMglFacadeLocal cmtTipoCompaniaMglFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacadeLocal;
    @EJB
    private UsuarioServicesFacadeLocal usuariosFacade;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacade;
    @Inject
    private BlockReportServBean blockReportServBean;
    
    public Integer tipo = ConstantsCM.TIPO_COMPANIA_ID_ADMINISTRACION;
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private ArrayList<CmtHorarioRestriccionMgl> horarioRestriccion = new ArrayList<>();
    private final String FORMULARIO = "COMPANIASADMINISTRACION";
    private final String FORMULARIO_DETALLE = "COMPANIASADMINISTRACIONDETALLE";
    private final String FORMULARIO_HORARIO = "COMPANIASADMINISTRACIONHORARIO";
    private final String ROLOPCIDCOMP = "ROLOPCIDCOMP";
    private final String ROLOPCCREATECOMP = "ROLOPCCREATECOMP";
    //Opciones agregadas para Rol
    private final String EXPBTNADM = "EXPBTNADM";

    public CmtCompaniaMgl companiaAdmin;
    public List<CmtCompaniaMgl> companyListTable;
    public List<GeograficoPoliticoMgl> departamentoList;
    public GeograficoPoliticoMgl departamentoSelected;
    public List<GeograficoPoliticoMgl> ciudadList;
    public GeograficoPoliticoMgl ciudadSelected;
    public List<GeograficoPoliticoMgl> centroPobladoList;
    public GeograficoPoliticoMgl centroPobladoSelected;
    public List<GeograficoPoliticoMgl> departamentoListConsulta;
    public List<GeograficoPoliticoMgl> ciudadListConsulta;
    public List<GeograficoPoliticoMgl> centroPobladoListConsulta;
    public List<String> barrioList = new ArrayList<>();
    // Jeisson Gómez  
    
    private boolean renderAuditoria = false;
    private String usuarioVT = null;
    private int perfilVt = 0;
    boolean validado = false;
    boolean cambioAdmin = false;
    boolean updateAdmin = false;
    private boolean isFirst = true;
    private boolean isLast = true;
    private boolean isConsult = false;
    public String tipoDoc;
    private String message = "";
    private boolean mostrarRestriccionHorarios = false;
    // Jeisson Gomez 
    /**
     * CmtCuentaMatrizMgl Paginacion Tabla
     */
    private HtmlDataTable dataTable = new HtmlDataTable();
    private String pageActual;
    private String numPagina = "1";
    private List<Integer> paginaList;
    private int actual;
    private List<AuditoriaDto> informacionAuditoria = null;
    private String descripcionCompania = "";
    private String tipoCompaniaDescripcion = "";
    private String estiloObligatorio = "<font color='red'>*</font>";
    private FiltroConsultaCompaniasDto filtroConsultaCompaniasDto = new FiltroConsultaCompaniasDto();
    private SecurityLogin securityLogin;
    
    private String comentario = "";
    private DrDireccion drDireccion;
    private ValidadorDireccionBean validadorDireccionBean;
    private UsuariosServicesDTO usuarioLogin;
    private boolean direccionValidada;
    private ResponseValidarDireccionDto responseValidarDireccionDto;
    /**
     *
     */
    public CompaniasAdministracionBean() {

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
            
            if (usuarioVT == null) {
                session.getAttribute("usuarioM");
                usuarioVT = (String) session.getAttribute("usuarioM");
            } else {
                session = (HttpSession) facesContext.getExternalContext().getSession(false);
                session.setAttribute("usuarioM", usuarioVT);
            }
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Se generea error en CompaniaAsministracionBean class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en CompaniaAsministracionBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    @PostConstruct
    public void init() {
        try {
            consultDir();
            fillData();
            consultarHorarioRestriccion();
            companiaFacade.setUser(usuarioVT, perfilVt);
            horarioRestriccionFacadeLocal.setUser(usuarioVT, perfilVt);
        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg);
        }


    }

    public void consultDir()  {
        try {
            preFillData();
            CmtTipoCompaniaMgl cmtTipoCompaniaMgl = new CmtTipoCompaniaMgl();
            cmtTipoCompaniaMgl.setTipoCompaniaId(new BigDecimal(tipo));
            cmtTipoCompaniaMgl = cmtTipoCompaniaMglFacadeLocal.findById(cmtTipoCompaniaMgl);
            filtroConsultaCompaniasDto = new FiltroConsultaCompaniasDto();
            filtroConsultaCompaniasDto.setTipoCompania(cmtTipoCompaniaMgl.getTipoCompaniaId());
            listInfoByPage(1);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error en CompaniaAsministracionBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public void preFillData() {
        try {
            Object type = FacesContext.getCurrentInstance().getExternalContext().getFlash().get("tipo");
            if (type != null) {
                tipo = (Integer) type;
                FacesContext.getCurrentInstance().getExternalContext().getFlash().put("tipo", null);
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en CompaniaAsministracionBean class ..." + e.getMessage(), e, LOGGER);
        }
    }



    public void fillData()  {
        try {
            departamentoListConsulta = geograficoPoliticoMglFacadeLocal.findDptos();
            departamentoList = geograficoPoliticoMglFacadeLocal.findDptos();
            Object com = FacesContext.getCurrentInstance().getExternalContext().getFlash().get("CompaniaAdmin");
            if (com != null) {
                validado = true;
                companiaAdmin = (CmtCompaniaMgl) com;
                if (tipo == 3) {
                    ciudadList = geograficoPoliticoMglFacadeLocal.findCiudades(companiaAdmin.getDepartamento().getGpoId());
                    centroPobladoList = geograficoPoliticoMglFacadeLocal.findCentrosPoblados(companiaAdmin.getMunicipio().getGpoId());
                    AddressService responseSrv;
                    AddressRequest requestSrv = new AddressRequest();
                    requestSrv.setCity(companiaAdmin.getMunicipio().getGpoNombre());
                    requestSrv.setState(companiaAdmin.getDepartamento().getGpoNombre());
                    requestSrv.setAddress(companiaAdmin.getDireccion());
                    requestSrv.setCodDaneVt(companiaAdmin.getCentroPoblado().getGeoCodigoDane());
                    requestSrv.setLevel(Constant.TIPO_CONSULTA_COMPLETA);
                    responseSrv = addressEJBRemote.queryAddressEnrich(requestSrv);
                    barrioList.clear();
                    List<AddressSuggested> barrios = responseSrv.getAddressSuggested();
                    if (barrios != null && barrios.size() > 0) {
                        for (AddressSuggested addressSuggested : barrios) {
                            if (responseSrv.getAddress().trim().equalsIgnoreCase(addressSuggested.getAddress().trim())) {
                                barrioList.add(addressSuggested.getNeighborhood());
                            }
                        }
                    }
                    companiaAdmin.setJustificacion("");
                }
                FacesContext.getCurrentInstance().getExternalContext().getFlash().put("CompaniaAdmin", null);
            } else {
                companiaAdmin = new CmtCompaniaMgl();
                companiaAdmin.setDepartamento(new GeograficoPoliticoMgl());
                companiaAdmin.setMunicipio(new GeograficoPoliticoMgl());
                companiaAdmin.setCentroPoblado(new GeograficoPoliticoMgl());
                companiaAdmin.setEstadoRegistro(ConstantsCM.REGISTRO_ENUSO_ID);
                if (tipo != null) {
                    CmtTipoCompaniaMgl cmtTipoCompaniaMglMy = new CmtTipoCompaniaMgl();
                    cmtTipoCompaniaMglMy.setTipoCompaniaId(new BigDecimal(tipo));
                    cmtTipoCompaniaMglMy = cmtTipoCompaniaMglFacadeLocal.findById(cmtTipoCompaniaMglMy);
                    companiaAdmin.setCodigoRr(companiaFacade.buscarUltimoCodigoNumerico(cmtTipoCompaniaMglMy));
                }
                validado = false;
                companiaAdmin.setAbreviatura("ADM");
                companiaAdmin.setActivado("N");
            }

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error en CompaniaAsministracionBean class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en CompaniaAsministracionBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public void findByFiltro() {
        try {
            if (ciudadListConsulta == null) {
                ciudadListConsulta = new ArrayList<>();
            }
            if (centroPobladoListConsulta == null) {
                centroPobladoListConsulta = new ArrayList<>();
            }
            if (filtroConsultaCompaniasDto.departamento == null) {
                ciudadListConsulta.clear();
                centroPobladoListConsulta.clear();
                filtroConsultaCompaniasDto.municipio = null;
                filtroConsultaCompaniasDto.centroPoblado = null;
            } else {
                ciudadListConsulta = geograficoPoliticoMglFacadeLocal.findCiudades(filtroConsultaCompaniasDto.departamento);
                if (filtroConsultaCompaniasDto.municipio == null) {
                    centroPobladoListConsulta.clear();
                    filtroConsultaCompaniasDto.centroPoblado = null;
                } else {
                    centroPobladoListConsulta = geograficoPoliticoMglFacadeLocal.findCentrosPoblados(filtroConsultaCompaniasDto.municipio);
                }
            }
            listInfoByPage(1);
            
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error en CompaniaAsministracionBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public void consultarCiudades() {
        try {
            companiaAdmin.setMunicipio(new GeograficoPoliticoMgl());
            companiaAdmin.setCentroPoblado(new GeograficoPoliticoMgl());
            companiaAdmin.setDepartamento(geograficoPoliticoMglFacadeLocal.findById(companiaAdmin.getDepartamento().getGpoId()));
            ciudadList = geograficoPoliticoMglFacadeLocal.findCiudades(companiaAdmin.getDepartamento().getGpoId());
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en CompaniaAsministracionBean class ..." + e.getMessage(), e, LOGGER);
        }

    }

    public void consultarCentrosPoblados()  {
        try {
            companiaAdmin.setCentroPoblado(new GeograficoPoliticoMgl());
            companiaAdmin.setMunicipio(geograficoPoliticoMglFacadeLocal.findById(companiaAdmin.getMunicipio().getGpoId()));
            centroPobladoList = geograficoPoliticoMglFacadeLocal.findCentrosPoblados(companiaAdmin.getMunicipio().getGpoId());
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error en CompaniaAsministracionBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public void asignarCentroPoblado()  {
        try {
            companiaAdmin.setCentroPoblado(geograficoPoliticoMglFacadeLocal.findById(companiaAdmin.getCentroPoblado().getGpoId()));
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error en CompaniaAsministracionBean class ..." + e.getMessage(), e, LOGGER);
        }
    }
    /**
     * hay que validar el formulario en este punto
     *
     * @return 
     */
    public String validoFormulario() {
        //nit obligatorio
        if (companiaAdmin.getNitCompania() == null || companiaAdmin.getNitCompania().isEmpty()) {
            return "El campo de Identificación es obligatorio.";
        }
       
        if (companiaAdmin.getNombreContacto() == null || companiaAdmin.getNombreContacto().isEmpty()) {
            return "El campo Nombre administrador es obligatorio.";
        }
        if (companiaAdmin.getTelefonos() == null || companiaAdmin.getTelefonos().isEmpty()) {
            return "El campo Teléfono es obligatorio.";
        }
        return null;
    }

    public void crearCompania() {
        try {
            String cadenaValidacion = validoFormulario();
            if (cadenaValidacion == null) {
                if (tipo == 3) {
                    ComponenteDireccionesManager componenteDireccionesManager = new ComponenteDireccionesManager();
                    String respuesta = null;
                    respuesta = componenteDireccionesManager.guardarDireccionRepo(
                            companiaAdmin.getDireccion(), companiaAdmin.getMunicipio().getGpoNombre(),
                            companiaAdmin.getDepartamento().getGpoNombre(), companiaAdmin.getBarrio(), 
                            usuarioVT, drDireccion, companiaAdmin.getCentroPoblado().getGeoCodigoDane());
                    companiaAdmin.setTipoDocumento("NIT");
                } else {
                    companiaAdmin.setDepartamento(null);
                    companiaAdmin.setMunicipio(null);
                    companiaAdmin.setCentroPoblado(null);
                    companiaAdmin.setNombreCompania(companiaAdmin.getNombreContacto());
                }
                CmtTipoCompaniaMgl cmtTipoCompaniaMgl = new CmtTipoCompaniaMgl();
                cmtTipoCompaniaMgl.setTipoCompaniaId(new BigDecimal(tipo));
                cmtTipoCompaniaMgl = cmtTipoCompaniaMglFacadeLocal.findById(cmtTipoCompaniaMgl);
                companiaAdmin.setTipoCompaniaObj(cmtTipoCompaniaMgl);
                companiaFacade.create(companiaAdmin);
                String msn = "Compañia creada con éxito.";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, cadenaValidacion, ""));
            }
        } catch (ApplicationException e) {
            String msn2 = "Error creando la compañia de administracion : ";
            FacesUtil.mostrarMensajeError(msn2 + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en CompaniaAsministracionBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public void consultarHorarioRestriccion() {
        try {
            Object compania = companiaAdmin;
            if (compania != null) {
                companiaAdmin = (CmtCompaniaMgl) compania;
            }
            List<CmtHorarioRestriccionMgl> alhorarios = horarioRestriccionFacadeLocal.findByHorarioCompania(companiaAdmin);

            horarioRestriccion = null;
            if (alhorarios != null) {
                horarioRestriccion = new ArrayList<>();

                if (!alhorarios.isEmpty() && alhorarios.size() > 0) {
                    for (CmtHorarioRestriccionMgl cmtHorarioRestriccionMgl : alhorarios) {
                        horarioRestriccion.add(cmtHorarioRestriccionMgl);
                    }
                }

            }
        } catch (ApplicationException e) {
            String msn2 = "Error creando la compañia de administracion : ";
            FacesUtil.mostrarMensajeError(msn2 + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en CompaniaAsministracionBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public void guardarHorarioRestriccion()  {
        try {
            if (session.getAttribute("ComponenteHorario") != null) {
                horarioRestriccion = (ArrayList<CmtHorarioRestriccionMgl>) session.getAttribute("ComponenteHorario");
                session.removeAttribute("ComponenteHorario");
                if (horarioRestriccion != null && !horarioRestriccion.isEmpty() && horarioRestriccion.size() == 2) {
                    if (horarioRestriccion.get(0).getDiaInicio().compareTo(horarioRestriccion.get(1).getDiaInicio()) == 0
                            && horarioRestriccion.get(0).getDiaFin().compareTo(horarioRestriccion.get(1).getDiaFin()) == 0
                            && horarioRestriccion.get(0).getHoraInicio().equalsIgnoreCase(horarioRestriccion.get(1).getHoraInicio())
                            && horarioRestriccion.get(0).getHoraFin().equalsIgnoreCase(horarioRestriccion.get(1).getHoraFin())) {
                        horarioRestriccion.remove(1);
                    }

                }

            }

            if (companiaAdmin != null) {

                if (horarioRestriccion != null && horarioRestriccion != null) {

                    if (!horarioRestriccionFacadeLocal.deleteByCompania(companiaAdmin)) {
                        String msn = "Ocurrio un error al actualizar horarios";
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                    }
                    try {

                        for (CmtHorarioRestriccionMgl hr : horarioRestriccion) {
                            hr.setCompaniaObj(companiaAdmin);
                            hr.setHorRestriccionId(null);
                            hr.setEstadoRegistro(ConstantsCM.REGISTRO_ENUSO_ID);
                            horarioRestriccionFacadeLocal.create(hr);
                        }
                        String msn = "Horario actualizado con éxito";
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                    } catch (ApplicationException ex) {

                        String msn = "Error actualizando horario";
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                    }

                } else {
                    String msn = "No puede agregar horario vacío.";
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                }
            } else {
                String msn = "No puede agregar horario si la compañía no existe.";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            }

        } catch (ApplicationException e) {
            String msn2 = "Error creando la compañia de administracion : ";
            FacesUtil.mostrarMensajeError(msn2 + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en CompaniaAsministracionBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public String cargarCompania() {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("tipo", tipo);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("CompaniaAdmin", null);
        return "companias-administracion-detalle";
    }

    public String cargarAdminMod(CmtCompaniaMgl compania) {
        try {
            companiaAdmin = compania;
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("tipo", tipo);
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("CompaniaAdmin", companiaAdmin);
        
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en CompaniaAsministracionBean class ..." + e.getMessage(), e, LOGGER);
        }
        return "companias-administracion-detalle";
    }

    public void modificacionSettings() throws ApplicationException {

    }

    public void gestionSettings() throws ApplicationException {

    }

    public void consultaSettings() throws ApplicationException {

    }

    public void modificarCompania() {
        try {
            if (companiaAdmin.getJustificacion() == null || companiaAdmin.getJustificacion().trim().isEmpty() || companiaAdmin.getJustificacion().length() > 200) {
                String msn = "Campo justificacion debe tener valor y ser menor a 200 caracteres";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return;
            }

            if (tipo == 3) {
                ComponenteDireccionesManager componenteDireccionesManager = new ComponenteDireccionesManager();
                String respuesta = componenteDireccionesManager.guardarDireccionRepo(
                        companiaAdmin.getDireccion(), companiaAdmin.getMunicipio().getGpoNombre(),
                        companiaAdmin.getDepartamento().getGpoNombre(), companiaAdmin.getBarrio(), 
                        usuarioVT, drDireccion, companiaAdmin.getCentroPoblado().getGeoCodigoDane());
                companiaAdmin.setTipoDocumento("NIT");
            } else {
                companiaAdmin.setDepartamento(null);
                companiaAdmin.setMunicipio(null);
                companiaAdmin.setCentroPoblado(null);
                companiaAdmin.setNombreCompania(companiaAdmin.getNombreContacto());
            }
            CmtTipoCompaniaMgl cmtTipoCompaniaMgl = new CmtTipoCompaniaMgl();
            cmtTipoCompaniaMgl.setTipoCompaniaId(new BigDecimal(tipo));
            cmtTipoCompaniaMgl = cmtTipoCompaniaMglFacadeLocal.findById(cmtTipoCompaniaMgl);
            companiaAdmin.setTipoCompaniaObj(cmtTipoCompaniaMgl);

            companiaFacade.update(companiaAdmin);
            String msn = "Compañia modificada con éxito.";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
        } catch (ApplicationException e) {
            String msn2 = "Error modificando la compañia de administracion : ";
            FacesUtil.mostrarMensajeError(msn2 + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en CompaniaAsministracionBean class ..." + e.getMessage(), e, LOGGER);
        }

    }

    public void selectOneRow(BigDecimal id) {

    }

    public void refreshDelete() {

    }

    public void validarDireccion() {
        validado = false;
        try {
            if (companiaAdmin.getDireccion() == null || companiaAdmin.getDireccion().equals("")) {
                String msn = "Debe dijitar una dirección";
                FacesContext.getCurrentInstance().addMessage(
                        null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return;
            }
            if (companiaAdmin.getCentroPoblado().getGpoNombre() == null || companiaAdmin.getCentroPoblado().getGpoNombre().equals("")) {
                String msn = "Para validar la direccion se requiere el departamento, ciudad y centro poblado.";
                FacesContext.getCurrentInstance().addMessage(
                        null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return;
            }
            AddressService responseSrv;
            AddressRequest requestSrv = new AddressRequest();
            requestSrv.setCity(companiaAdmin.getMunicipio().getGpoNombre());
            requestSrv.setState(companiaAdmin.getDepartamento().getGpoNombre());
            requestSrv.setAddress(companiaAdmin.getDireccion());
            requestSrv.setCodDaneVt(companiaAdmin.getCentroPoblado().getGeoCodigoDane());
            requestSrv.setLevel(Constant.TIPO_CONSULTA_COMPLETA);

            responseSrv = addressEJBRemote.queryAddressEnrich(requestSrv);
            if (responseSrv.getTraslate().equalsIgnoreCase("true")) {
                companiaAdmin.setDireccion(responseSrv.getAddress());
            }

            barrioList.clear();
            List<AddressSuggested> barrios = responseSrv.getAddressSuggested();
            if (barrios != null && barrios.size() > 0) {
                for (AddressSuggested addressSuggested : barrios) {
                    if (responseSrv.getAddress().trim().equalsIgnoreCase(addressSuggested.getAddress().trim())) {
                        barrioList.add(addressSuggested.getNeighborhood());
                    }
                }
            }
            if (responseSrv.getExist().trim().equalsIgnoreCase("false")) {
                String msn = "La dirección no existe. Si la direccion es de tipo [barrio Manzana] "
                        + "asegúrese de haber digitado el barrio en la direccion";
                FacesContext.getCurrentInstance().addMessage(
                        null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                if (responseSrv.getTraslate().trim().equalsIgnoreCase("true")) {
                    companiaAdmin.setDireccion(responseSrv.getAddress());
                    validado = true;
                }
            } else {
                String msn = "La dirección existe, si en el campo barrios hay informacion seleccione uno";
                FacesContext.getCurrentInstance().addMessage(
                        null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                if (responseSrv.getTraslate().trim().equalsIgnoreCase("true")) {
                    companiaAdmin.setDireccion(responseSrv.getAddress());
                    validado = true;
                }
            }
        } catch (ApplicationException e) {
            String msn = "Error en la validación de dirección";
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en CompaniaAsministracionBean class ..." + e.getMessage(), e, LOGGER);
        }

    }




    public String exportExcel()  {
        try {
            // Se verifica si está bloqueada la generación de reportes y si
            // el usuario en sesión está autorizado para realizar el proceso.
            if (blockReportServBean.isReportGenerationBlock("Exportar Compañías de Administración")) return StringUtils.EMPTY;

            XSSFWorkbook workbook = new XSSFWorkbook();
            //Create a blank sheet
            XSSFSheet sheet = workbook.createSheet("ReporteAsc");
            //Blank workbook
            String[] objArr = {"Nombre Compañia", "NIT", "Página Web", "Departamento", "Municipio", "Centro poblado", "Barrio", "Dirección", "Nombre contacto", "Telefonos", "e-mail", "Estado"};
            int rownum = 0;
            
            int expLonPag = 1000;
     
            long totalPag = companiaFacade.countByCompaFiltro(filtroConsultaCompaniasDto, true);

            for (int exPagina = 1; exPagina <= ((totalPag / expLonPag) + (totalPag % expLonPag != 0 ? 1 : 0));
                    exPagina++) {

                companyListTable = companiaFacade.findByfiltroAndPaginado(exPagina, expLonPag,
                        filtroConsultaCompaniasDto, true);
                
                if (companyListTable != null && !companyListTable.isEmpty()) {
                    for (CmtCompaniaMgl cmtCompaniaMgl : companyListTable) {
                        Row row = sheet.createRow(rownum);

                        int cellnum = 0;

                        if (rownum == 0) {
                            for (String c : objArr) {
                                Cell cell = row.createCell(cellnum++);
                                cell.setCellValue(c);
                            }
                            rownum++;
                            row = sheet.createRow(rownum);
                            cellnum = 0;
                            Cell cell = row.createCell(cellnum++);
                            cell.setCellValue(cmtCompaniaMgl.getNombreCompania());
                            Cell cell2 = row.createCell(cellnum++);
                            cell2.setCellValue(cmtCompaniaMgl.getNitCompania());
                            Cell cell3 = row.createCell(cellnum++);
                            cell3.setCellValue(cmtCompaniaMgl.getPaginaWeb());
                            Cell cell4 = row.createCell(cellnum++);
                            cell4.setCellValue((cmtCompaniaMgl.getDepartamento() != null) ? cmtCompaniaMgl.getDepartamento().getGpoNombre() : "");
                            Cell cell5 = row.createCell(cellnum++);
                            cell5.setCellValue((cmtCompaniaMgl.getMunicipio() != null) ? cmtCompaniaMgl.getMunicipio().getGpoNombre() : "");
                            Cell cell6 = row.createCell(cellnum++);
                            cell6.setCellValue((cmtCompaniaMgl.getCentroPoblado() != null) ? cmtCompaniaMgl.getCentroPoblado().getGpoNombre() : "");
                            Cell cell7 = row.createCell(cellnum++);
                            cell7.setCellValue(cmtCompaniaMgl.getBarrio());
                            Cell cell8 = row.createCell(cellnum++);
                            cell8.setCellValue(cmtCompaniaMgl.getDireccion());
                            Cell cell9 = row.createCell(cellnum++);
                            cell9.setCellValue(cmtCompaniaMgl.getNombreContacto());
                            Cell cell10 = row.createCell(cellnum++);
                            cell10.setCellValue(cmtCompaniaMgl.getTelefonos());
                            Cell cell11 = row.createCell(cellnum++);
                            cell11.setCellValue(cmtCompaniaMgl.getEmail());
                            Cell cell12 = row.createCell(cellnum++);
                            cell12.setCellValue(cmtCompaniaMgl.getEstadoRegistro());
                        } else {
                            Cell cell = row.createCell(cellnum++);
                            cell.setCellValue(cmtCompaniaMgl.getNombreCompania());
                            Cell cell2 = row.createCell(cellnum++);
                            cell2.setCellValue(cmtCompaniaMgl.getNitCompania());
                            Cell cell3 = row.createCell(cellnum++);
                            cell3.setCellValue(cmtCompaniaMgl.getPaginaWeb());
                            Cell cell4 = row.createCell(cellnum++);
                            cell4.setCellValue((cmtCompaniaMgl.getDepartamento() != null) ? cmtCompaniaMgl.getDepartamento().getGpoNombre() : "");
                            Cell cell5 = row.createCell(cellnum++);
                            cell5.setCellValue((cmtCompaniaMgl.getMunicipio() != null) ? cmtCompaniaMgl.getMunicipio().getGpoNombre() : "");
                            Cell cell6 = row.createCell(cellnum++);
                            cell6.setCellValue((cmtCompaniaMgl.getCentroPoblado() != null) ? cmtCompaniaMgl.getCentroPoblado().getGpoNombre() : "");
                            Cell cell7 = row.createCell(cellnum++);
                            cell7.setCellValue(cmtCompaniaMgl.getBarrio());
                            Cell cell8 = row.createCell(cellnum++);
                            cell8.setCellValue(cmtCompaniaMgl.getDireccion());
                            Cell cell9 = row.createCell(cellnum++);
                            cell9.setCellValue(cmtCompaniaMgl.getNombreContacto());
                            Cell cell10 = row.createCell(cellnum++);
                            cell10.setCellValue(cmtCompaniaMgl.getTelefonos());
                            Cell cell11 = row.createCell(cellnum++);
                            cell11.setCellValue(cmtCompaniaMgl.getEmail());
                            Cell cell12 = row.createCell(cellnum++);
                            cell12.setCellValue(cmtCompaniaMgl.getEstadoRegistro());
                        }

                        rownum++;
                    }
                     System.gc();
                }

            }

            FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletResponse response1 = (HttpServletResponse) fc.getExternalContext().getResponse();
            response1.reset();
            response1.setContentType("application/vnd.ms-excel");

            SimpleDateFormat formato = new SimpleDateFormat("ddMMMyyyy_HH_mm_ss");

            response1.setHeader("Content-Disposition", "attachment; filename=\"" + "Reporte_Administradores_" + formato.format(new Date()) + ".xlsx\"");
            OutputStream output = response1.getOutputStream();

            workbook.write(output);
            output.flush();
            output.close();
            fc.responseComplete();
        } catch (IOException | ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error en CompaniaAsministracionBean class ..." + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public void verHorarioRestriccion() {
        try {
            consultarHorarioRestriccion();
        }  catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en CompaniaAsministracionBean class ..." + e.getMessage(), e, LOGGER);
        }
        mostrarRestriccionHorarios = true;
    }

    public void ocultarHorarioRestriccion() {
        horarioRestriccion = null;
        mostrarRestriccionHorarios = false;
    }

    public String eliminarCompania() {
        try {
            if (companiaAdmin.getJustificacion() == null || companiaAdmin.getJustificacion().trim().isEmpty() || companiaAdmin.getJustificacion().length() > 200) {
                String msn = "Campo justificacion debe tener valor y ser menor a 200 caracteres";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return "";
            }

            if (companiaFacade.delete(companiaAdmin)) {
                companiaAdmin = new CmtCompaniaMgl();
                String msn = "Registro Eliminado con Exito";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
            } else {
                String msn = "Ocurrio un error Eliminando el estado Interno";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            }
        } catch (ApplicationException e) {
            String msn = "Error eliminando la compañia de administracion : ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        }  catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en CompaniaAsministracionBean class ..." + e.getMessage(), e, LOGGER);
        }
        return "companias-administracion.jsf";
    }

    public void eliminarTable() {

    }

    public void navigate(String directionNavigation) {
        try{
        if (directionNavigation != null && !directionNavigation.trim().isEmpty()) {
            int posEstadoSelected = companyListTable.indexOf(companiaAdmin);
            if (directionNavigation.equalsIgnoreCase("back")) {
                if (posEstadoSelected > 0) {
                    companiaAdmin = companyListTable.get(posEstadoSelected - 1);
                }
            } else if (directionNavigation.equalsIgnoreCase("forward")) {
                if (posEstadoSelected + 1 < companyListTable.size()) {
                    companiaAdmin = companyListTable.get(posEstadoSelected + 1);
                }
            }
        }
        }  catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en CompaniaAsministracionBean class ..." + e.getMessage(), e, LOGGER);
        }
        try {
            organizeNavigation(companiaAdmin);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en CompaniaAsministracionBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    private void organizeNavigation(CmtCompaniaMgl estadoSelected) {
        isFirst = false;
        isLast = false;
        try {
            int posEstadoSelected = companyListTable.indexOf(estadoSelected);
            //si el registro existe en la lista se verifica si es el primero o el ultimo
            if (posEstadoSelected != -1) {
                if (posEstadoSelected == 0) {
                    isFirst = true;
                } else if (posEstadoSelected + 1 == companyListTable.size()) {
                    isLast = true;
                }
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en CompaniaAsministracionBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public void mostrarAuditoria(CmtCompaniaMgl cmtCompaniaMgl) {
        if (cmtCompaniaMgl != null) {
            try {
                informacionAuditoria = companiaFacade.construirAuditoria(cmtCompaniaMgl);
                descripcionCompania = cmtCompaniaMgl.getNombreCompania();
                tipoCompaniaDescripcion = cmtCompaniaMgl.getTipoCompaniaObj().getNombreTipo();
                renderAuditoria = true;
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                String msn = "Se presenta error al mostrar la auditoria";
                FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
            } 
        } else {
            String msn = "No se encontro informacion de auditoria";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
        }

    }

    /**
     * 
     * @return 
     */
    public boolean validarCreacion() {
        return validarAccion(ValidacionUtil.OPC_CREACION, FORMULARIO);
    }

    /**
     * 
     * @return 
     */
    public boolean validarEdicion() {
        return validarAccion(ValidacionUtil.OPC_EDICION, FORMULARIO);
    }

    /**
     * 
     * @return 
     */
    public boolean validarCreacionDetalle() {
        return validarAccion(ValidacionUtil.OPC_CREACION, FORMULARIO_DETALLE);
    }

    /**
     * 
     * @return 
     */
    public boolean validarEdicionDetalle() {
        return validarAccion(ValidacionUtil.OPC_EDICION, FORMULARIO_DETALLE);
    }

    /**
     * 
     * @return 
     */
    public boolean validarBorradoDetalle() {
        return validarAccion(ValidacionUtil.OPC_BORRADO, FORMULARIO_DETALLE);
    }

    /**
     * 
     * @return 
     */
    public boolean validarEdicionHorario() {
        return validarAccion(ValidacionUtil.OPC_EDICION, FORMULARIO_HORARIO);
    }

    /**
     * M&eacute;todo para validar las acciones a realizar en el formulario
     * 
     * @param opcion String nombre de la opci&oacute;n que realizar&aacute; el componente
     * @param formulario String con el componente que se esta validando
     * @return boolean indicador para verificar si se visualizan o no los componentes
     */
    private boolean validarAccion(String opcion, String formulario) {
        try {
            return ValidacionUtil.validarVisualizacion(formulario, opcion, cmtOpcionesRolMglFacadeLocal, securityLogin);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(co.com.claro.mgl.utils.Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION + e.getMessage(), e, LOGGER);
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en CompaniaAsministracionBean class ..." + e.getMessage(), e, LOGGER);
        }
        return false;
    }
    
    public boolean validarIdRol() {
        return validarEdicion(ROLOPCIDCOMP);
    }
    
    public boolean validarCrearRol() {
        return validarEdicion(ROLOPCCREATECOMP);
    }

    private boolean validarEdicion(String formulario) {
        try {
            boolean tieneRolPermitido = ValidacionUtil.validarVisualizacion(formulario, ValidacionUtil.OPC_EDICION, cmtOpcionesRolMglFacadeLocal, securityLogin);
            return tieneRolPermitido;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(co.com.claro.mgl.utils.Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION + e.getMessage(), e, LOGGER);
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al OrdenTrabajoGestionarBean. " + e.getMessage(), e, LOGGER);
        }
        return false;
    }

    public void recargar(ResponseValidarDireccionDto direccionDto) {
        direccionValidada = true;
        responseValidarDireccionDto = direccionDto;
        drDireccion = responseValidarDireccionDto.getDrDireccion();
        companiaAdmin.setDireccion(responseValidarDireccionDto.getDireccion());
        barrioList = responseValidarDireccionDto.getBarrios();
        companiaAdmin.setBarrio(responseValidarDireccionDto.getDrDireccion().getBarrio());
        validado = true;
    }
    
    public String irPopUpDireccion() {
        comentario = "";
        try {
            if (companiaAdmin.getMunicipio() != null && companiaAdmin.getCentroPoblado().getGpoId() != null) {
                drDireccion = new DrDireccion();
                validadorDireccionBean = (ValidadorDireccionBean) JSFUtil.getSessionBean(ValidadorDireccionBean.class);
                usuarioLogin = usuariosFacade.consultaInfoUserPorUsuario(usuarioVT);
                validadorDireccionBean.setCiudadDelBean(companiaAdmin.getMunicipio());
                validadorDireccionBean.setIdCentroPoblado(companiaAdmin.getCentroPoblado().getGpoId());
                validadorDireccionBean.setUsuarioLogin(usuarioLogin);
                validadorDireccionBean.setTecnologia(null);
                validadorDireccionBean.validarDireccion(drDireccion, companiaAdmin.getDireccion(),
                        companiaAdmin.getCentroPoblado(), companiaAdmin.getBarrio(), this,
                        CompaniasAdministracionBean.class, co.com.claro.mgl.utils.Constant.TIPO_VALIDACION_DIR_CM, co.com.claro.mgl.utils.Constant.DIFERENTE_MODIFICACION_CM);
            } else {
                String cadenaError = "Municipio o Centro"
                        + " Poblado vacios.";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, cadenaError, ""));
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error en CompaniaAsministracionBean class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en CompaniaAsministracionBean class ..." + e.getMessage(), e, LOGGER);
        }
        return null;
    }
    
    public void ocultarAuditoria() {
        renderAuditoria = false;
    }
    
    //Jeisson Gómez
    /**
     *
     * @return
     */
    public ArrayList<CmtHorarioRestriccionMgl> getHorarioRestriccion() {
        return horarioRestriccion;
    }

    public List<CmtCompaniaMgl> getCompanyListTable() {
        return companyListTable;
    }

    public void setCompanyList(List<CmtCompaniaMgl> companyListTable) {
        this.companyListTable = companyListTable;
    }

    public CmtCompaniaMgl getCompaniaAdmin() {
        return companiaAdmin;
    }

    public void setCompaniaAdmin(CmtCompaniaMgl companiaAdmin) {
        this.companiaAdmin = companiaAdmin;
    }

    public List<GeograficoPoliticoMgl> getDepartamentoList() {
        return departamentoList;
    }

    public void setDepartamentoList(List<GeograficoPoliticoMgl> departamentoList) {
        this.departamentoList = departamentoList;
    }

    public List<GeograficoPoliticoMgl> getCiudadList() {
        return ciudadList;
    }

    public void setCiudadList(List<GeograficoPoliticoMgl> ciudadList) {
        this.ciudadList = ciudadList;
    }

    public List<String> getBarrioList() {
        return barrioList;
    }

    public void setBarrioList(List<String> barrioList) {
        this.barrioList = barrioList;
    }

    public boolean isValidado() {
        return validado;
    }

    public void setValidado(boolean validado) {
        this.validado = validado;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public String getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(String tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    //Jeisson Gómez 
    public void setHorarioRestriccion(ArrayList<CmtHorarioRestriccionMgl> horarioRestriccion) {
        this.horarioRestriccion = horarioRestriccion;
    }

    
    public String listInfoByPage(int page) {
        try {
            companyListTable = companiaFacade.findByfiltroAndPaginado(page, ConstantsCM.PAGINACION_QUINCE_FILAS,
                    filtroConsultaCompaniasDto, true);
            actual = page;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en listInfoByPage: " + e.getMessage() + "", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en listInfoByPage: " + e.getMessage() + "", e, LOGGER);
        }
        return null;
    }


    public void pageFirst() {
        listInfoByPage(1);
    }

    public void pagePrevious() {
        int totalPaginas = getTotalPaginas();
        int nuevaPageActual = actual - 1;
        if (nuevaPageActual > totalPaginas) {
            nuevaPageActual = totalPaginas;
        }
        if (nuevaPageActual <= 0) {
            nuevaPageActual = 1;
        }
        listInfoByPage(nuevaPageActual);
    }

    public void irPagina() {
        int totalPaginas = getTotalPaginas();
        int nuevaPageActual = Integer.parseInt(numPagina);
        if (nuevaPageActual > totalPaginas) {
            nuevaPageActual = totalPaginas;
        }
        listInfoByPage(nuevaPageActual);
    }

    public void pageNext() {
        int totalPaginas = getTotalPaginas();
        int nuevaPageActual = actual + 1;
        if (nuevaPageActual > totalPaginas) {
            nuevaPageActual = totalPaginas;
        }
        listInfoByPage(nuevaPageActual);
    }

    public void pageLast() {
        int totalPaginas = getTotalPaginas();
        listInfoByPage(totalPaginas);
    }

    public String getPageActual() {
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
    }

    public int getTotalPaginas() {
        try {
            Long count = companiaFacade.countByCompaFiltro(filtroConsultaCompaniasDto, true);
            int totalPaginas = (int) ((count % ConstantsCM.PAGINACION_QUINCE_FILAS != 0)
                    ? (count / ConstantsCM.PAGINACION_QUINCE_FILAS) + 1
                    : count / ConstantsCM.PAGINACION_QUINCE_FILAS);
            return totalPaginas;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en getTotalPaginas. Al cargar lista de Nodos: "+e.getMessage()+"", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en getTotalPaginas. Al cargar lista de Nodos: "+e.getMessage()+"", e, LOGGER);
        }
        return 1;
    }
    
    public List<Integer> getPaginaList() {
        return paginaList;
    }

    public void setPaginaList(List<Integer> paginaList) {
        this.paginaList = paginaList;
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

    public void setPageActual(String pageActual) {
        this.pageActual = pageActual;
    }

    public boolean isCambioAdmin() {
        return cambioAdmin;
    }

    public void setCambioAdmin(boolean cambioAdmin) {
        this.cambioAdmin = cambioAdmin;
    }

    public boolean isUpdateAdmin() {
        return updateAdmin;
    }

    public void setUpdateAdmin(boolean updateAdmin) {
        this.updateAdmin = updateAdmin;
    }

    public boolean isIsFirst() {
        return isFirst;
    }

    public void setIsFirst(boolean isFirst) {
        this.isFirst = isFirst;
    }

    public boolean isIsLast() {
        return isLast;
    }

    public void setIsLast(boolean isLast) {
        this.isLast = isLast;
    }

    public boolean isIsConsult() {
        return isConsult;
    }

    public void setIsConsult(boolean isConsult) {
        this.isConsult = isConsult;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<GeograficoPoliticoMgl> getCentroPobladoList() {
        return centroPobladoList;
    }

    public void setCentroPobladoList(List<GeograficoPoliticoMgl> centroPobladoList) {
        this.centroPobladoList = centroPobladoList;
    }

    public boolean isMostrarRestriccionHorarios() {
        return mostrarRestriccionHorarios;
    }

    public void setMostrarRestriccionHorarios(boolean mostrarRestriccionHorarios) {
        this.mostrarRestriccionHorarios = mostrarRestriccionHorarios;
    }

    public boolean isRenderAuditoria() {
        return renderAuditoria;
    }

    public void setRenderAuditoria(boolean renderAuditoria) {
        this.renderAuditoria = renderAuditoria;
    }

    public List<AuditoriaDto> getInformacionAuditoria() {
        return informacionAuditoria;
    }

    public void setInformacionAuditoria(List<AuditoriaDto> informacionAuditoria) {
        this.informacionAuditoria = informacionAuditoria;
    }

    public String getDescripcionCompania() {
        return descripcionCompania;
    }

    public void setDescripcionCompania(String descripcionCompania) {
        this.descripcionCompania = descripcionCompania;
    }

    public String getTipoCompaniaDescripcion() {
        return tipoCompaniaDescripcion;
    }

    public void setTipoCompaniaDescripcion(String tipoCompaniaDescripcion) {
        this.tipoCompaniaDescripcion = tipoCompaniaDescripcion;
    }

    public String getEstiloObligatorio() {
        return estiloObligatorio;
    }

    public void setEstiloObligatorio(String estiloObligatorio) {
        this.estiloObligatorio = estiloObligatorio;
    }

    public FiltroConsultaCompaniasDto getFiltroConsultaCompaniasDto() {
        return filtroConsultaCompaniasDto;
    }

    public void setFiltroConsultaCompaniasDto(FiltroConsultaCompaniasDto filtroConsultaCompaniasDto) {
        this.filtroConsultaCompaniasDto = filtroConsultaCompaniasDto;
    }

    public List<GeograficoPoliticoMgl> getDepartamentoListConsulta() {
        return departamentoListConsulta;
    }

    public void setDepartamentoListConsulta(List<GeograficoPoliticoMgl> departamentoListConsulta) {
        this.departamentoListConsulta = departamentoListConsulta;
    }

    public List<GeograficoPoliticoMgl> getCiudadListConsulta() {
        return ciudadListConsulta;
    }

    public void setCiudadListConsulta(List<GeograficoPoliticoMgl> ciudadListConsulta) {
        this.ciudadListConsulta = ciudadListConsulta;
    }

    public List<GeograficoPoliticoMgl> getCentroPobladoListConsulta() {
        return centroPobladoListConsulta;
    }

    public void setCentroPobladoListConsulta(List<GeograficoPoliticoMgl> centroPobladoListConsulta) {
        this.centroPobladoListConsulta = centroPobladoListConsulta;
    }
    
        // Validar Opciones por Rol
    
      public boolean validarOpcionExportar() {
        return validarEdicionRol(EXPBTNADM);
    }
    
    //funcion General
      private boolean validarEdicionRol(String formulario) {
        try {
            boolean tieneRolPermitido = ValidacionUtil.validarVisualizacion(formulario, ValidacionUtil.OPC_EDICION, cmtOpcionesRolMglFacade, securityLogin);
            return tieneRolPermitido;
        }catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(co.com.claro.mgl.utils.Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION + e.getMessage(), e, LOGGER);
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al OrdenTrabajoGestionarBean. " + e.getMessage(), e, LOGGER);
        }
        return false;
    }
}
