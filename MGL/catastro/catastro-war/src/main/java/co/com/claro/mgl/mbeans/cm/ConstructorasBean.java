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
import co.com.claro.mgl.jpa.entities.cm.CmtEstablecimientoCmMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtHorarioRestriccionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoCompaniaMgl;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.claro.mgl.rest.dtos.UsuariosServicesDTO;
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
@ManagedBean(name = "constructorasBean")
@ViewScoped
@Log4j2
public class ConstructorasBean implements Serializable {

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
    
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private final String FORMULARIO = "CONSTRUCTORAS";
    private final String FORMULARIO_DETALLE = "CONSTRUCTORASDETALLE";
    private final String FORMULARIO_HORARIO = "CONSTRUCTORASHORARIO";
    private final String ROLOPCIDCONSTR = "ROLOPCIDCONSTR";
    private final String ROLOPCCREATECONSTR = "ROLOPCCREATECONSTR";
    //Opciones agregadas para Rol
    private final String EXPBTNCTR = "EXPBTNCTR";

    private FiltroConsultaCompaniasDto filtroConsultaCompaniasDto = new FiltroConsultaCompaniasDto();
    public List<CmtEstablecimientoCmMgl> tipoEstablecimientoList = new ArrayList<>();
    private ArrayList<CmtHorarioRestriccionMgl> horarioRestriccion = new ArrayList<>();

    public CmtCompaniaMgl constructora = new CmtCompaniaMgl();
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

    private boolean renderAuditoria = false;    
    private String usuarioVT = null;
    private int perfilVt = 0;
    private String cedulaUsuarioVT = null;
    boolean validado = false;
    boolean updateCompania = false;
    private boolean isFirst = true;
    private boolean isLast = true;
    private boolean isConsult = false;
    public SubEdificiosMglBean subEdificiosBean;
    public String tipoDoc = "NIT";
    // Jeisson Gomez 
    private String message = "";
    private boolean mostrarRestriccionHorarios = false;
    /**
     * Paginacion Tabla
     */
    private HtmlDataTable dataTable = new HtmlDataTable();
    private String pageActual;
    private String numPagina = "1";
    private int actual;
    private List<Integer> paginaList;
    private List<AuditoriaDto> informacionAuditoria = null;
    private String descripcionCompania = "";
    private String tipoCompaniaDescripcion = "";
    private String estiloObligatorio = "<font color='red'>*</font>";
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
    public ConstructorasBean() {
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
            cedulaUsuarioVT = securityLogin.getIdUser();
            if (usuarioVT == null) {
                session.getAttribute("usuarioM");
                usuarioVT = (String) session.getAttribute("usuarioM");
            } else {
                session = (HttpSession) facesContext.getExternalContext().getSession(false);
                session.setAttribute("usuarioM", usuarioVT);
            }
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Se generea error en ConstructorasBean class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ConstructorasBean class ..." + e.getMessage(), e, LOGGER);
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
            LOGGER.error(ex.getMessage());
            String msn2 = "Error mostrando el formulario";
            FacesContext.getCurrentInstance()
                    .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, ex.getMessage()));
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
            String msn2 = "Error mostrando el formulario";
            FacesContext.getCurrentInstance()
                    .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, ex.getMessage()));
        }
    }

    public void consultDir()  {
        try {
            CmtTipoCompaniaMgl cmtTipoCompaniaMgl = new CmtTipoCompaniaMgl();
            cmtTipoCompaniaMgl.setTipoCompaniaId(new BigDecimal(ConstantsCM.TIPO_COMPANIA_ID_CONSTRUCTORAS));
            cmtTipoCompaniaMgl = cmtTipoCompaniaMglFacadeLocal.findById(cmtTipoCompaniaMgl);
            filtroConsultaCompaniasDto = new FiltroConsultaCompaniasDto();
            filtroConsultaCompaniasDto.setTipoCompania(cmtTipoCompaniaMgl.getTipoCompaniaId());
            listInfoByPage(1);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error en ConstructorasBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

   

    public void fillData() {
        try {
            departamentoListConsulta = geograficoPoliticoMglFacadeLocal.findDptos();
            departamentoList = geograficoPoliticoMglFacadeLocal.findDptos();
            Object com = FacesContext.getCurrentInstance().getExternalContext().getFlash().get("Constructora");
            if (com != null) {
                validado = true;
                constructora = (CmtCompaniaMgl) com;
                ciudadList = geograficoPoliticoMglFacadeLocal.findCiudades(constructora.getDepartamento().getGpoId());
                centroPobladoList = geograficoPoliticoMglFacadeLocal
                        .findCentrosPoblados(constructora.getMunicipio().getGpoId());
                AddressService responseSrv;
                AddressRequest requestSrv = new AddressRequest();
                requestSrv.setCity(constructora.getMunicipio().getGpoNombre());
                requestSrv.setState(constructora.getDepartamento().getGpoNombre());
                requestSrv.setAddress(constructora.getDireccion());
                requestSrv.setCodDaneVt(constructora.getMunicipio().getGeoCodigoDane());
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
                constructora.setJustificacion("");
                FacesContext.getCurrentInstance().getExternalContext().getFlash().put("Constructora", null);
            } else {
                constructora = new CmtCompaniaMgl();
                constructora.setDepartamento(new GeograficoPoliticoMgl());
                constructora.setMunicipio(new GeograficoPoliticoMgl());
                constructora.setCentroPoblado(new GeograficoPoliticoMgl());
                constructora.setEstadoRegistro(ConstantsCM.REGISTRO_ENUSO_ID);
                CmtTipoCompaniaMgl cmtTipoCompaniaMglMy = new CmtTipoCompaniaMgl();
                cmtTipoCompaniaMglMy.setTipoCompaniaId(new BigDecimal(ConstantsCM.TIPO_COMPANIA_ID_CONSTRUCTORAS));
                cmtTipoCompaniaMglMy = cmtTipoCompaniaMglFacadeLocal.findById(cmtTipoCompaniaMglMy);
                constructora.setCodigoRr(companiaFacade.buscarUltimoCodigoNumerico(cmtTipoCompaniaMglMy));
                constructora.setAbreviatura("CON");
                constructora.setActivado("N");
                validado = false;
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error en ConstructorasBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public void findByFiltro()  {
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
                ciudadListConsulta = geograficoPoliticoMglFacadeLocal
                        .findCiudades(filtroConsultaCompaniasDto.departamento);
                if (filtroConsultaCompaniasDto.municipio == null) {
                    centroPobladoListConsulta.clear();
                    filtroConsultaCompaniasDto.centroPoblado = null;
                } else {
                    centroPobladoListConsulta = geograficoPoliticoMglFacadeLocal
                            .findCentrosPoblados(filtroConsultaCompaniasDto.municipio);
                }
            }
            listInfoByPage(1);
            
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error en ConstructorasBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public void consultarCiudades()  {
        try {
            constructora.setMunicipio(new GeograficoPoliticoMgl());
            constructora.setCentroPoblado(new GeograficoPoliticoMgl());
            constructora.setDepartamento(geograficoPoliticoMglFacadeLocal
                    .findById(constructora.getDepartamento().getGpoId()));
            ciudadList = geograficoPoliticoMglFacadeLocal.findCiudades(constructora.getDepartamento().getGpoId());
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error en ConstructorasBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public void consultarCentrosPoblados()  {
        try {
            constructora.setCentroPoblado(new GeograficoPoliticoMgl());
            constructora.setMunicipio(geograficoPoliticoMglFacadeLocal.findById(constructora.getMunicipio().getGpoId()));
            centroPobladoList = geograficoPoliticoMglFacadeLocal.findCentrosPoblados(constructora.getMunicipio().getGpoId());
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error en ConstructorasBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public void asignarCentroPoblado() {
        try {
            constructora.setCentroPoblado(geograficoPoliticoMglFacadeLocal.findById(constructora.getCentroPoblado().getGpoId()));
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error en ConstructorasBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * hay que validar el formulario en este punto
     *
     * @return 
     */
    public String validoFormulario() {
        //nit obligatorio
        if (constructora.getNitCompania() == null || constructora.getNitCompania().isEmpty()) {
            return "El campo NIT es obligatorio.";
        }
        //Nombre de la compañia o razon social
        if (constructora.getNombreCompania() == null || constructora.getNombreCompania().isEmpty()) {
            return "El campo Nombre Compañia es obligatorio.";
        }
        if (constructora.getNombreContacto() == null || constructora.getNombreContacto().isEmpty()) {
            return "El campo Nombre Contacto es obligatorio.";
        }
        if (constructora.getTelefonos() == null || constructora.getTelefonos().isEmpty()) {
            return "El campo Telefonos es obligatorio.";
        }
        return null;
    }
    
    public void crearConstructora()  {
        try {
            String cadenaValidacion = validoFormulario();
            if (cadenaValidacion == null) {
                ComponenteDireccionesManager componenteDireccionesManager = new ComponenteDireccionesManager();
                String respuesta = componenteDireccionesManager.guardarDireccionRepo(
                        constructora.getDireccion(), constructora.getMunicipio().getGpoNombre(),
                        constructora.getDepartamento().getGpoNombre(), constructora.getBarrio(), usuarioVT, drDireccion,
                        constructora.getCentroPoblado().getGeoCodigoDane());
                constructora.setTipoDocumento(tipoDoc);
                CmtTipoCompaniaMgl cmtTipoCompaniaMgl = new CmtTipoCompaniaMgl();
                cmtTipoCompaniaMgl.setTipoCompaniaId(new BigDecimal(ConstantsCM.TIPO_COMPANIA_ID_CONSTRUCTORAS));
                cmtTipoCompaniaMgl = cmtTipoCompaniaMglFacadeLocal.findById(cmtTipoCompaniaMgl);
                constructora.setTipoCompaniaObj(cmtTipoCompaniaMgl);
                constructora.setEstadoRegistro(ConstantsCM.REGISTRO_ENUSO_ID);
                companiaFacade.create(constructora);
                String msn = "Compañia creada con éxito.";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
            }else{
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, cadenaValidacion, ""));
            }
        } catch (ApplicationException e) {
            String msn2 = "Error creando la compañia : ";
            FacesUtil.mostrarMensajeError(msn2 + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ConstructorasBean class ..." + e.getMessage(), e, LOGGER);
        }
    }
    public void recargar(ResponseValidarDireccionDto direccionDto) {
        direccionValidada = true;
        responseValidarDireccionDto = direccionDto;
        drDireccion = responseValidarDireccionDto.getDrDireccion();
        constructora.setDireccion(responseValidarDireccionDto.getDireccion());
        barrioList = responseValidarDireccionDto.getBarrios();
        constructora.setBarrio(responseValidarDireccionDto.getDrDireccion().getBarrio());
        validado = true;
    }

    public String cargarConstructora() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("Constructora", null);
            return "constructoras-detalle";
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ConstructorasBean class ..." + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public String cargarConstructoraMod(CmtCompaniaMgl compania) {
        try {
            constructora = compania;
            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("Constructora", constructora);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ConstructorasBean class ..." + e.getMessage(), e, LOGGER);
        }
        return "constructoras-detalle";
    }


    public void eliminarTable() {

    }

    public void modificacionSettings() throws ApplicationException {

    }

    public void gestionSettings() throws ApplicationException {

    }

    public void proyectoSettings() throws ApplicationException {

    }

    public void consultaSettings() throws ApplicationException {

    }

    public String crearProyecto() throws ApplicationException {

        return "gestion-constructoras";
    }

    public void modificarConstructora() {
        try {
            if (constructora.getJustificacion() == null || constructora.getJustificacion().trim().isEmpty() || constructora.getJustificacion().length() > 200) {
                String msn = "Campo justificacion debe tener valor y ser menor a 200 caracteres";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return;
            }
            ComponenteDireccionesManager componenteDireccionesManager = new ComponenteDireccionesManager();
            String respuesta = componenteDireccionesManager.guardarDireccionRepo(
                    constructora.getDireccion(), constructora.getMunicipio().getGpoNombre(),
                    constructora.getDepartamento().getGpoNombre(), constructora.getBarrio(), 
                    usuarioVT, drDireccion, constructora.getCentroPoblado().getGeoCodigoDane());
            constructora.setTipoDocumento("NIT");
            CmtTipoCompaniaMgl cmtTipoCompaniaMgl = new CmtTipoCompaniaMgl();
            cmtTipoCompaniaMgl.setTipoCompaniaId(new BigDecimal(ConstantsCM.TIPO_COMPANIA_ID_CONSTRUCTORAS));
            cmtTipoCompaniaMgl = cmtTipoCompaniaMglFacadeLocal.findById(cmtTipoCompaniaMgl);
            constructora.setTipoCompaniaObj(cmtTipoCompaniaMgl);
            companiaFacade.update(constructora);
            String msn = "Compañia modificada con éxito.";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
        } catch (ApplicationException e) {
            String msn2 = "Error modificando la compañia : ";
            FacesUtil.mostrarMensajeError(msn2 + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ConstructorasBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public void selectOneRow(BigDecimal id) {

    }

    public void refreshDelete() {

    }

    public void validarDireccion() {
        validado = false;
        try {
            if (constructora.getDireccion() == null || constructora.getDireccion().equals("")) {
                String msn = "Debe dijitar una dirección";
                FacesContext.getCurrentInstance().addMessage(
                        null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return;
            }
            if (constructora.getCentroPoblado().getGpoNombre() == null || constructora.getCentroPoblado().getGpoNombre().equals("")) {
                String msn = "Para validar la direccion se requiere el departamento, ciudad y centro poblado.";
                FacesContext.getCurrentInstance().addMessage(
                        null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return;
            }            
            AddressService responseSrv;
            AddressRequest requestSrv = new AddressRequest();
            requestSrv.setCity(constructora.getCentroPoblado().getGpoNombre());
            requestSrv.setState(constructora.getDepartamento().getGpoNombre());
            requestSrv.setAddress(constructora.getDireccion());
            requestSrv.setCodDaneVt(constructora.getCentroPoblado().getGeoCodigoDane());
            requestSrv.setLevel(Constant.TIPO_CONSULTA_COMPLETA);

            responseSrv = addressEJBRemote.queryAddressEnrich(requestSrv);
            if (responseSrv.getTraslate().equalsIgnoreCase("true")) {
                constructora.setDireccion(responseSrv.getAddress());
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
                    constructora.setDireccion(responseSrv.getAddress());
                    validado = true;
                }
            } else {
                String msn = "La dirección existe, si en el campo barrios hay informacion seleccione uno";
                FacesContext.getCurrentInstance().addMessage(
                        null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                if (responseSrv.getTraslate().trim().equalsIgnoreCase("true")) {
                    constructora.setDireccion(responseSrv.getAddress());
                    validado = true;
                }
            }
        } catch (ApplicationException e) {
            String msn = "Error en la validación de dirección";
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ConstructorasBean class ..." + e.getMessage(), e, LOGGER);
        }

    }

    public void actualizar() throws ApplicationException {

    }

    public String nuevoProyecto() {
        return "nuevo-proyecto-constructora";
    }

    public void limpiarCampos() {
    }


    public String exportExcel() {
        try {
            // Se verifica si está bloqueada la generación de reportes y si
            // el usuario en sesión está autorizado para realizar el proceso.
            if (blockReportServBean.isReportGenerationBlock("Exportar Compañías Constructoras")) return StringUtils.EMPTY;

            XSSFWorkbook workbook = new XSSFWorkbook();
            //Create a blank sheet
            XSSFSheet sheet = workbook.createSheet("ReporteAsc");
            //Blank workbook
            String[] objArr = {"Nombre Compañia", "NIT", "Página Web",
                "Departamento", "Municipio", "Centro poblado", "Barrio",
                "Dirección", "Nombre contacto", "Telefonos", "e-mail", "Estado"};
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
        } catch (IOException e) {
            String msn = "Error generardo el archivo";
             FacesUtil.mostrarMensajeError(msn+ e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ConstructorasBean class ..." + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public String eliminarConstructora() {
        try {
            if (constructora.getJustificacion() == null || constructora.getJustificacion().trim().isEmpty() || constructora.getJustificacion().length() > 200) {
                String msn = "Campo justificacion debe tener valor y ser menor a 200 caracteres";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return "";
            }

            if (companiaFacade.delete(constructora)) {
                constructora = new CmtCompaniaMgl();
                String msn = "Registro Eliminado con Exito";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
            } else {
                String msn = "Ocurrio un error Eliminando el estado Interno";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            }
        } catch (ApplicationException e) {
            String msn = "Ocurrio un error Eliminando compañia : ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ConstructorasBean class ..." + e.getMessage(), e, LOGGER);
        }
        return "constructoras";
    }

    public void consultarHorarioRestriccion()  {
        try {
            Object compania = constructora;
            if (compania != null) {
                constructora = (CmtCompaniaMgl) compania;
            }
            List<CmtHorarioRestriccionMgl> alhorarios = horarioRestriccionFacadeLocal.findByHorarioCompania(constructora);

            if (alhorarios != null) {
                horarioRestriccion = new ArrayList<>();

                if (!alhorarios.isEmpty() && alhorarios.size() > 0) {
                    for (CmtHorarioRestriccionMgl cmtHorarioRestriccionMgl : alhorarios) {
                        horarioRestriccion.add(cmtHorarioRestriccionMgl);
                    }
                }
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error en ConstructorasBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public void guardarHorarioRestriccion() {
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

            if (constructora != null) {
                if (horarioRestriccion != null && !horarioRestriccion.isEmpty()) {
                    if (!horarioRestriccionFacadeLocal.deleteByCompania(constructora)) {
                        String msn = "Ocurrio un error actualizar horario";
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                    }
                    try {
                        for (CmtHorarioRestriccionMgl hr : horarioRestriccion) {
                            hr.setCompaniaObj(constructora);
                            hr.setHorRestriccionId(null);
                            hr.setEstadoRegistro(ConstantsCM.REGISTRO_ENUSO_ID);
                            horarioRestriccionFacadeLocal.create(hr);
                        }
                        String msn = "Horarios actualizado con éxito";
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
                    } catch (ApplicationException ex) {
                        String msn = "Error actualizado horario";
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
            FacesUtil.mostrarMensajeError("Se generea error en ConstructorasBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public void navigate(String directionNavigation) {
        try {
            if (directionNavigation != null && !directionNavigation.trim().isEmpty()) {
                int posEstadoSelected = companyListTable.indexOf(constructora);
                if (directionNavigation.equalsIgnoreCase("back")) {
                    if (posEstadoSelected > 0) {
                        constructora = companyListTable.get(posEstadoSelected - 1);
                    }
                } else if (directionNavigation.equalsIgnoreCase("forward")) {
                    if (posEstadoSelected + 1 < companyListTable.size()) {
                        constructora = companyListTable.get(posEstadoSelected + 1);
                    }
                }
            }
            organizeNavigation(constructora);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ConstructorasBean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    private void organizeNavigation(CmtCompaniaMgl estadoSelected) {
        try {
            isFirst = false;
            isLast = false;
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
            FacesUtil.mostrarMensajeError("Se generea error en ConstructorasBean class ..." + e.getMessage(), e, LOGGER);
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
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            }
        } else {
            String msn = "No se encontro informacion de auditoria";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
        }
    }
    public String irPopUpDireccion() {
        comentario = "";
        try {
            if (constructora.getMunicipio() != null && constructora.getCentroPoblado().getGpoId() != null) {
                drDireccion = new DrDireccion();
                validadorDireccionBean = (ValidadorDireccionBean) JSFUtil.getSessionBean(ValidadorDireccionBean.class);
                usuarioLogin = usuariosFacade.consultaInfoUserPorUsuario(usuarioVT);
                validadorDireccionBean.setCiudadDelBean(constructora.getMunicipio());
                validadorDireccionBean.setIdCentroPoblado(constructora.getCentroPoblado().getGpoId());
                validadorDireccionBean.setUsuarioLogin(usuarioLogin);
                validadorDireccionBean.setTecnologia(null);
                validadorDireccionBean.validarDireccion(drDireccion, constructora.getDireccion(),
                        constructora.getCentroPoblado(), constructora.getBarrio(), this,
                        ConstructorasBean.class, co.com.claro.mgl.utils.Constant.TIPO_VALIDACION_DIR_CM,co.com.claro.mgl.utils.Constant.DIFERENTE_MODIFICACION_CM);
            } else {
                String cadenaError = "Municipio o Centro"
                        + " Poblado vacios.";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, cadenaError, ""));
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error en ConstructorasBean class ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en ConstructorasBean class ..." + e.getMessage(), e, LOGGER);
        }
        return null;
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
        } catch (ApplicationException ex) {
            LOGGER.error("Error al validar la acción en el formulario. EX000 " + ex.getMessage(), ex);
            FacesContext.getCurrentInstance().addMessage(
                    null, new FacesMessage(
                        FacesMessage.SEVERITY_ERROR, co.com.claro.mgl.utils.Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION, ""));
            return false;
        }
    }
    
    public boolean validarIdRol() {
        return validarEdicion(ROLOPCIDCONSTR);
    }
    
    public boolean validarCrearRol() {
        return validarEdicion(ROLOPCCREATECONSTR);
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
    
    public void ocultarAuditoria() {
        renderAuditoria = false;
    }

    public ArrayList<CmtHorarioRestriccionMgl> getHorarioRestriccion() {
        return horarioRestriccion;
    }

    public void setHorarioRestriccion(ArrayList<CmtHorarioRestriccionMgl> horarioRestriccion) {
        this.horarioRestriccion = horarioRestriccion;
    }

    public void verHorarioRestriccion() {
        mostrarRestriccionHorarios = true;
    }

    public void ocultarHorarioRestriccion() {
        mostrarRestriccionHorarios = false;
    }

    public List<CmtCompaniaMgl> getCompanyListTable() {
        return companyListTable;
    }

    public void setCompanyList(List<CmtCompaniaMgl> companyListTable) {
        this.companyListTable = companyListTable;
    }

    public CmtCompaniaMgl getConstructora() {
        return constructora;
    }

    public void setConstructora(CmtCompaniaMgl constructora) {
        this.constructora = constructora;
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

    public boolean isUpdateCompania() {
        return updateCompania;
    }

    public void setUpdateCompania(boolean updateCompania) {
        this.updateCompania = updateCompania;
    }

    public List<CmtEstablecimientoCmMgl> getTipoEstablecimientoList() {
        return tipoEstablecimientoList;
    }

    public void setTipoEstablecimientoList(List<CmtEstablecimientoCmMgl> tipoEstablecimientoList) {
        this.tipoEstablecimientoList = tipoEstablecimientoList;
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

    public int getPerfilVt() {
        return perfilVt;
    }

    public void setPerfilVt(int perfilVt) {
        this.perfilVt = perfilVt;
    }

    public boolean isMostrarRestriccionHorarios() {
        return mostrarRestriccionHorarios;
    }

    public void setMostrarRestriccionHorarios(boolean mostrarRestriccionHorarios) {
        this.mostrarRestriccionHorarios = mostrarRestriccionHorarios;
    }

    public List<GeograficoPoliticoMgl> getCentroPobladoList() {
        return centroPobladoList;
    }

    public void setCentroPobladoList(List<GeograficoPoliticoMgl> centroPobladoList) {
        this.centroPobladoList = centroPobladoList;
    }

    public boolean isRenderAuditoria() {
        return renderAuditoria;
    }

    public void setRenderAuditoria(boolean renderAuditoria) {
        this.renderAuditoria = renderAuditoria;
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

    public List<AuditoriaDto> getInformacionAuditoria() {
        return informacionAuditoria;
    }

    public void setInformacionAuditoria(List<AuditoriaDto> informacionAuditoria) {
        this.informacionAuditoria = informacionAuditoria;
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
        return validarEdicionRol(EXPBTNCTR);
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
