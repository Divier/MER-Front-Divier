package co.com.claro.mgl.mbeans.direcciones;

import co.com.claro.mer.utils.FileToolUtils;
import co.com.claro.mer.utils.enums.ParametrosMerEnum;
import co.com.claro.mgl.client.ftp.ClienteFTP;
import co.com.claro.mgl.dtos.ActEstComercialDto;
import co.com.claro.mgl.dtos.AnchoBanRetoDTO;
import co.com.claro.mgl.dtos.ArchActEstComercialDto;
import co.com.claro.mgl.dtos.ArchivoReporteDTO;
import co.com.claro.mgl.dtos.CentroPobladoDTO;
import co.com.claro.mgl.dtos.CiudadDTO;
import co.com.claro.mgl.dtos.DepartamentoDTO;
import co.com.claro.mgl.dtos.DivisionalDTO;
import co.com.claro.mgl.dtos.EstadoReporteDTO;
import co.com.claro.mgl.dtos.NodoEstadoDTO;
import co.com.claro.mgl.dtos.ReporteConsultaQueryDTO;
import co.com.claro.mgl.dtos.TecnologiaDTO;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.IAnchoBanRetoMglFacadeLocal;
import co.com.claro.mgl.facade.ICentroPobladoMglFacadeLocal;
import co.com.claro.mgl.facade.ICiudadMglFacadeLocal;
import co.com.claro.mgl.facade.IConActEstComercialMglFacadeLocal;
import co.com.claro.mgl.facade.IDepartamentoMglFacadeLocal;
import co.com.claro.mgl.facade.IDivisionalFacadeLocal;
import co.com.claro.mgl.facade.NodoMglFacadeLocal;
import co.com.claro.mgl.facade.IReporteMglFacadeLocal;
import co.com.claro.mgl.facade.ITecnologiaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.ptlus.UsuarioServicesFacadeLocal;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOpcionesRolMgl;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.rest.dtos.UsuariosServicesDTO;
import co.com.claro.mgl.utils.EncriptadorAESUtils;
import co.com.claro.mgl.utils.ParametrosMerUtil;
import co.com.telmex.catastro.services.comun.Utilidades;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.model.file.UploadedFile;

import javax.annotation.PostConstruct;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.Objects.nonNull;

/**
 * Bean de la pantalla Actualización Estructura comercial.
 * @author Dayver De la hoz - Vass Latam
 * @author Fernando Caycho - Oracle
 * @version Brief100417
 */
@ManagedBean(name = "actEstComercialMglBean")
@ViewScoped
public class ActEstComercialMglBean implements Serializable {

    private static final Logger LOG = LogManager.getLogger(ActEstComercialMglBean.class);

    public static final String ACTIVO = "A";

    public static final String SELECT = "SELECT";
    public static final String ACTION_INSERT = "I";
    public static final String ACTION_UPDATE = "U";
    public static final String ACTION_QUERY = "Q";
    public static final String STATE_OK = "EXITOSO";
    public static final String CONSULTA = "CONSULTA";
    public static final String CARGUE = "CARGUE";
    public static final String UPDATE = "UPDATE";
    public static final String SEPARADOR = ";";

    @EJB
    private IConActEstComercialMglFacadeLocal conActEstComercialMglFacadeLocal;
    @EJB
    private ITecnologiaMglFacadeLocal tecnologiaMglFacadeLocal;
    @EJB
    private IDepartamentoMglFacadeLocal departamentoMglFacadeLocal;
    @EJB
    private IAnchoBanRetoMglFacadeLocal anchoBanRetoMglFacadeLocal;
    @EJB
    private ICiudadMglFacadeLocal ciudadMglFacadeLocal;
    @EJB
    private ICentroPobladoMglFacadeLocal centroPobladoMglFacadeLocal;
    @EJB
    private IDivisionalFacadeLocal divisionalFacadeLocal;
    @EJB
    private NodoMglFacadeLocal nodoMglFacadeLocal;
    @EJB
    private UsuarioServicesFacadeLocal usuarioServicesFacade;
    @EJB
    private IReporteMglFacadeLocal reporteMglFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal operacionesRolFacade;

    private List<NodoEstadoDTO> nodoEstadoDTOList;
    private List<TecnologiaDTO> listTablaBasicaTecnologias;

    private SecurityLogin securityLogin;
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private String usuarioVT = null;
    private List<TecnologiaDTO> tecnologias;
    private List<DepartamentoDTO> departamentos;
    private List<CiudadDTO> ciudades;
    private List<CentroPobladoDTO> centrosPoblados;
    private List<DivisionalDTO> divisionales;
    private List<String> estadosNodo;
    private List<String> anchosBanda;
    private String estiloObligatorio = "<font color='red'>*</font>";
    private ActEstComercialDto actEstComercialDto;
    private UploadedFile cargueArchivo;
    public List<DepartamentoDTO> departamentoList;
    public List<CiudadDTO> ciudadList;
    public List<AnchoBanRetoDTO> anchoBanRetoList;
    private CmtCuentaMatrizMgl cuentaMatriz;
    private UsuariosServicesDTO usuarioLogin;
    private GeograficoPoliticoMgl ciudadSeleccionada;
    public List<CentroPobladoDTO> centroPobladoList;
    public List<DivisionalDTO> divisionalList;
    private List<ArchActEstComercialDto> archivoConsulta;
    private List<ArchActEstComercialDto> archivoCarga;
    private List<ArchActEstComercialDto> archivoConsultaPag;
    private List<ArchActEstComercialDto> archivoCargaPag;
    private List<Integer> paginaListConsulta;
    private List<Integer> paginaListCargue;
    private String pageActualConsulta;
    private String numPaginaConsulta = "1";
    private String pageActualCargue;
    private String numPaginaCargue = "1";
    private int actualConsulta;
    private int actualCargue;
    private boolean pintarPaginado = true;
    private boolean disabled;
    private int filasPag = ConstantsCM.PAGINACION_SEIS_FILAS;
    private String message;
    private String departamento;
    private String tecnologia;
    private String municipio;
    private String centroProblado;
    private String estadoNodo;
    private String divisional;
    private String anchoBandReto;
    private String estateProcess = "PROCESADO";
    private String estateProcessError = "PROCESADO CON ERRORES";

    private boolean rolConsultar = false;

    private boolean rolEditar = false;
    private String rol;

    private static final Logger LOGGER = LogManager.getLogger(ActEstComercialMglBean.class);

    public ActEstComercialMglBean() {

        try {
            securityLogin = new SecurityLogin(facesContext);
            if (!securityLogin.isLogin()) {
                if (!response.isCommitted()) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                }
                return;
            }
            usuarioVT = securityLogin.getLoginUser();
            String usuario = "usuarioM";

            if (usuarioVT == null) {
                session.getAttribute(usuario);
                usuarioVT = (String) session.getAttribute(usuario);
            } else {
                session = (HttpSession) facesContext.getExternalContext().getSession(false);
                session.setAttribute(usuario, usuarioVT);
            }
        } catch (IOException e) {
            LOGGER.error("Ocurrió en ActEstComercialMglBean.", e);
            FacesUtil.mostrarMensajeError("Se genera error en ConsultaCuentasMatricesBean class ..." + e.getMessage(), e, LOG);
        }

    }

    @PostConstruct
    public void init() {
        try {
            validarRoles();
            nodoEstadoDTOList = nodoMglFacadeLocal.consultarEstadosDeNodo();
            listTablaBasicaTecnologias = tecnologiaMglFacadeLocal.listarTecnologias();
            departamentoList = departamentoMglFacadeLocal.listarDepartamentos();
            anchoBanRetoList = anchoBanRetoMglFacadeLocal.listarAnchoBanReto();
            archivoConsulta = conActEstComercialMglFacadeLocal.listarTodosConsulta();
            archivoCarga = conActEstComercialMglFacadeLocal.listarTodosCargue();
            listInfoByPage(1);
            listInfoByPageCargue(1);
            validarDisponibilidad();
        } catch (ApplicationException e) {
            LOGGER.error("Ocurrio un error Inicializando.", e);
            FacesUtil.mostrarMensajeError(e.getMessage(), e, LOG);
        } catch (Exception e) {
            LOGGER.fatal("Ocurrio un error Inicializando.", e);
            FacesUtil.mostrarMensajeError("Se genera error iniciando clase." + e.getMessage(), e, LOG);
        }

    }

    private void validarRoles() throws ApplicationException {
        List<CmtOpcionesRolMgl> opcionesRolMgl = operacionesRolFacade.consultarOpcionesRol("ACTESTCOMER");
        if(Boolean.parseBoolean(ParametrosMerUtil.findValor(ParametrosMerEnum.FLAG_VALIDACION_ROLES.getAcronimo()))){
            for(CmtOpcionesRolMgl opc:opcionesRolMgl){
                if (securityLogin.usuarioTieneRoll(opc.getRol())){
                    if (opc.getNombreOpcion().contains("EDITAR")){
                        rolConsultar = true;
                        rolEditar = true;
                        rol = opc.getRol();
                    }else if (opc.getNombreOpcion().equals("CONSULTAR")){
                        rolConsultar = true;
                        rol = opc.getRol();
                    }
                }
            }
        }else{
            rolConsultar = true;
            rolEditar = true;
            rol = operacionesRolFacade.consultarOpcionRol("ACTESTCOMER", "EDITAR").getRol();
        }

    }

    public void nuevaConsulta() {
        tecnologia = "";
        departamento = "";
        municipio = "";
        centroProblado = "";
        divisional = "";
        estadoNodo = "";
        anchoBandReto = "";
        verificarEstado();
    }

    public boolean validarDisponibilidad() throws ApplicationException {
        if (this.getOrUpdateAvaliableReport(SELECT, null).getEstado().equalsIgnoreCase(ACTIVO)) {
            disabled = false;
            return true;
        }
        disabled = true;
        return false;
    }

    public void consultarCiudades() {
        try {
            municipio = null;
            centroProblado = null;
            divisional = null;
            if (departamento != null) {
                ciudadList = ciudadMglFacadeLocal.listarCiudadesPorDepartamento(departamento);
            } else {
                ciudadList = new ArrayList();
            }
        } catch (Exception e) {
            LOGGER.fatal("Se generó un error en consultarCiudades.", e);
            FacesUtil.mostrarMensajeError("Se generó un error: " + e.getMessage(), e, LOG);
        }

    }

    public void consultarCentroPoblado() {
        try {
            centroProblado = null;
            centroPobladoList = centroPobladoMglFacadeLocal.listarCentroPobladoPorDepartamentoYCiudad(departamento, municipio);
        } catch (Exception e) {
            LOGGER.fatal(e);
            FacesUtil.mostrarMensajeError("Se generó un error: " + e.getMessage(), e, LOG);
        }
    }

    public void consultarDivisional() {
        try {
            divisional = null;
            divisionalList = divisionalFacadeLocal.listarDivisionalPorDepartamentoCiudadYCentroPoblado(departamento, municipio, centroProblado);
        } catch (Exception e) {
            LOGGER.fatal("Se generó un error en consultarDivisional.", e);
            FacesUtil.mostrarMensajeError("Se generó un error: " + e.getMessage(), e, LOG);
        }
    }

    public String createFilter(String tipoTecnologia, String departamento, String ciudad, String centroProblado, String divisional, String estadoNodo, String anchoBandReto) {

        String createFilter = "";
        if (nonNull(tipoTecnologia) && !tecnologia.trim().isEmpty()) {
            createFilter = "TIPO_TECNOLOGIA = '" + tipoTecnologia + "'";
        }
        if (nonNull(departamento) && !departamento.trim().isEmpty()) {
            createFilter = createFilter.concat(SEPARADOR).concat("DEPARTAMENTO = '" + departamento + "'");
        }
        if (nonNull(ciudad) && !ciudad.trim().isEmpty()) {
            createFilter = createFilter.concat(SEPARADOR).concat("CIUDAD = '" + ciudad + "'");
        }
        if (nonNull(centroProblado) && !centroProblado.trim().isEmpty()) {
            createFilter = createFilter.concat(SEPARADOR).concat("CENTRO_POBLADO = '" + centroProblado + "'");
        }
        if (nonNull(divisional) && !divisional.trim().isEmpty()) {
            createFilter = createFilter.concat(SEPARADOR).concat("DIVISIONAL = '" + divisional + "'");
        }
        if (nonNull(estadoNodo) && !estadoNodo.trim().isEmpty()) {
            createFilter = createFilter.concat(SEPARADOR).concat("ESTADO_NODO = '" + estadoNodo + "'");
        }
        if (nonNull(anchoBandReto) && !anchoBandReto.trim().isEmpty()) {
            createFilter = createFilter.concat(SEPARADOR).concat("ANCHO_BAN_RETO = '" + anchoBandReto + "'");
        }
        return createFilter;
    }

    public void programarConsulta() {
        try {
            if(!rolConsultar){
                throw new ApplicationException("No tiene autorización para realizar esta solicitud.");
            }
            if (tecnologia == null || tecnologia.equals("")) {
                throw new ApplicationException("EL filtro tecnología es obligatorio.");
            }
            String filter = createFilter(tecnologia, departamento, municipio, centroProblado, divisional, estadoNodo, anchoBandReto);
            if (!validarDisponibilidad()) {
                throw new ApplicationException("El reporte no se encuentra disponible para generación, intente mas tarde.");
            }
            ReporteConsultaQueryDTO status = this.getOrUpdateAvaliableReport(SELECT, null);
            ReporteConsultaQueryDTO update = this.getOrUpdateAvaliableReport(UPDATE, "U");
            if (!Objects.equals(null, update) && Objects.equals(0, update.getCodigo())) {
                EstadoReporteDTO reportStatus = this.getORInsertOrUpdateEstadoReporte(EstadoReporteDTO.builder().accion(ACTION_INSERT).tipoProceso(CONSULTA).idReporte(Integer.parseInt(ParametrosMerUtil.findValor("REPORTE_NODOS_ID"))).rolEjecucion(rol).nombreArchivo(update.getNombreArchivo()).filtro(filter).ruta(status.getRuta()).build());

                if (Objects.equals(null, reportStatus)){
                    throw new ApplicationException("Se presentó un error generando el registro de consulta.");
                }else if(!Objects.equals(0, reportStatus.getCodigo())){
                    throw new ApplicationException(reportStatus.getResultado());
                } else {
                    FacesUtil.mostrarMensaje("Se generó consulta correctamente");
                    verificarEstado();
                }
            }
        } catch (ApplicationException e) {
            LOGGER.error("Ocurrió un error en programarConsulta.", e);
            FacesUtil.mostrarMensajeError("Error Programando Consulta: " + e.getMessage(), e, LOG);
        }

    }

    public ReporteConsultaQueryDTO getOrUpdateAvaliableReport(String sentence, String estado) throws ApplicationException {
        ReporteConsultaQueryDTO rcq = null;
        String action = sentence == SELECT ? ACTION_QUERY : ACTION_UPDATE;
        try {
            rcq = reporteMglFacadeLocal.consultarEstadoReporte(action, Integer.parseInt(ParametrosMerUtil.findValor("REPORTE_NODOS_ID")), estado);
        } catch (ApplicationException e) {
            LOGGER.error("Ocurrió un error getOrUpdateAvaliableReport.", e);
            FacesUtil.mostrarMensajeError("Se generó un error: " + e.getMessage(), e, LOG);
        }
        if(Objects.equals(null, rcq) || !rcq.getResultado().equals(STATE_OK)){
            LOGGER.error("No se encontró información relacionada con la consulta tipo "+ action + ", validar parametrización.");
            throw new ApplicationException("No se encontró información relacionada con la consulta tipo "+ action);
        }
        return rcq;
    }

    public EstadoReporteDTO getORInsertOrUpdateEstadoReporte(EstadoReporteDTO dto) {
        try {
            return reporteMglFacadeLocal.insertOrUpdateEstadoReporte(dto);
        } catch (ApplicationException e) {
            LOGGER.error("Ocurrió un error getORInsertOrUpdateEstadoReporte.", e);
            FacesUtil.mostrarMensajeError("Se generó un error: " + e.getMessage(), e, LOG);
        }
        return null;
    }

    public void cargueArchivoCsv() {
        try {
            if(!rolEditar){
                throw new ApplicationException("No tiene autorización para realizar esta solicitud.");
            }
            if (cargueArchivo.getFileName() == null) {
                throw new ApplicationException("Por favor seleccione un archivo para el proceso.");
            }

            ArchivoReporteDTO archivoReporteDTO = validarNombreArchivo(Integer.parseInt(ParametrosMerUtil.findValor("CARGUE_NODOS_ID")), cargueArchivo.getFileName());

            if (archivoReporteDTO==null) throw new ApplicationException("Ocurrió un error consultando información del reporte");

            if (archivoReporteDTO.getResultado().equals(STATE_OK)) {
                if (!validarFecha(archivoReporteDTO.getFechaEntrega())) {//Validacion menor a 90 dias
                    throw new ApplicationException("El reporte seleccionado excede el límite de 90 días.");
                }
                int[] total = Utilidades.countRegsCsv(cargueArchivo);
                String[] totalConsulta = archivoReporteDTO.getTotalRegistros().trim().split("\\|");
                if (Integer.parseInt(totalConsulta[1]) != total[1]) {
                    throw new ApplicationException("La estructura del archivo fue alterada, por favor verifique.");
                }
                if (Integer.parseInt(totalConsulta[0]) < (total[0] - 1)) {
                    throw new ApplicationException("La cantidad de registros del archivo a cargar no puede ser mayor a los generados en el reporte.");
                } else if ((total[0] - 1) < 1) {
                    throw new ApplicationException("La cantidad de registros del archivo a cargar no puede ser cero.");
                }
                //Colocar archivo en FTP
                guardaArchivoFTP(archivoReporteDTO.getRutaCargue(), cargueArchivo.getInputStream(), cargueArchivo.getFileName());

                EstadoReporteDTO reportStatus = this.getORInsertOrUpdateEstadoReporte(EstadoReporteDTO.builder().accion(ACTION_INSERT).tipoProceso(CARGUE).idReporte(Integer.parseInt(ParametrosMerUtil.findValor("CARGUE_NODOS_ID")))
                        .rolEjecucion(rol).nombreArchivo(cargueArchivo.getFileName()).filtro("").ruta(archivoReporteDTO.getRutaCargue()).build());

                if (Objects.equals(null, reportStatus)){
                    throw new ApplicationException("Se presentó un error generando el registro de cargue de archivo.");
                }else if(!Objects.equals(0, reportStatus.getCodigo())){
                    throw new ApplicationException(reportStatus.getResultado());
                } else {
                    FacesUtil.mostrarMensaje("Se realizó el cargue de archivo correctamente.");
                }
                verificarEstado();
            } else {
                throw new ApplicationException("El archivo seleccionado no se encuentra entre los reportes generados.");
            }
        } catch (ApplicationException | IOException e) {
            LOGGER.error("Ocurrió un error en el proceso cargueArchivoCsv.", e);
            FacesUtil.mostrarMensajeError(e.getMessage(), e, LOG);
        }
    }

    private boolean validarFecha(String fechaEntrega) {
        try {
            LocalDate date = new SimpleDateFormat("dd/MM/yyyy").parse(fechaEntrega).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if ((ChronoUnit.DAYS.between(date, LocalDate.now()) > 90)) {
                return false;
            }
            return true;
        } catch (ParseException e) {
            LOGGER.fatal("Error en validarFecha.", e);
            return false;
        }
    }

    public void descargaArchivoFTP(String path, String pathLocal, String fileName) throws ApplicationException {
        try {
            ClienteFTP ftp = getClienteFTP();
            ftp.getFile(path, pathLocal, fileName);
            ftp.cerrarConexion();
        } catch (JSchException | SftpException | IllegalAccessException | IOException | NoSuchPaddingException |
                 IllegalBlockSizeException | NoSuchAlgorithmException | BadPaddingException | InvalidKeyException e) {
            LOGGER.fatal("Error descargaArchivoFTP.", e);
            throw new ApplicationException(e.getMessage());
        }
    }

    public void guardaArchivoFTP(String path, InputStream file, String name) throws ApplicationException {
        try {
            ClienteFTP ftp = getClienteFTP();
            ftp.putFile(path, file, name);
            ftp.cerrarConexion();
        } catch (JSchException | SftpException | IOException | NoSuchPaddingException | IllegalBlockSizeException |
                 NoSuchAlgorithmException | BadPaddingException | InvalidKeyException e) {
            LOGGER.fatal(e);
            throw new ApplicationException("Error en guardaArchivoFTP.", e);
        }
    }

    private ClienteFTP getClienteFTP() throws ApplicationException, UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, JSchException {
        String host = validateParameter(ParametrosMerUtil.findValor("HOST_FTP_REPORTES"));
        String port = validateParameter(ParametrosMerUtil.findValor("PORT_FTP_REPORTES"));
        String user = validateParameter(ParametrosMerUtil.findValor("USER_FTP_REPORTES"));
        String pass = new EncriptadorAESUtils().desencriptar(validateParameter(ParametrosMerUtil.findValor("PASS_FTP_REPORTES")), "CLARO");

        ClienteFTP ftp = ClienteFTP.builder().host(host).port(Integer.parseInt(port)).user(user).password(pass).build();
        ftp.abrirConexion();
        return ftp;
    }

    String validateParameter(String parameter) throws ApplicationException {
        if (parameter.equalsIgnoreCase("")) {
            throw new ApplicationException("Se deben parametrizar los datos de conexión a FTP.");
        }
        return parameter;
    }

    public void actualizarListas() {
        try {
            archivoConsulta = conActEstComercialMglFacadeLocal.listarTodosConsulta();
            archivoCarga = conActEstComercialMglFacadeLocal.listarTodosCargue();
            listInfoByPage(actualConsulta);
            listInfoByPageCargue(actualCargue);
        } catch (ApplicationException e) {
            LOGGER.fatal("Error en actualizarListas.", e);
            FacesUtil.mostrarMensajeError("Se generó un error consultando reportes", e, LOG);
        }
    }

    public void verificarEstado() {
        try{
            validarDisponibilidad();
            actualizarListas();
        }catch (ApplicationException e){
            LOGGER.fatal("Error verificando estado.", e);
            FacesUtil.mostrarMensajeError("Se generó un error verificando disponibilidad, por favor contacte al administrador del sistema.", e, LOG);
        }
    }

    public void descargarArchivoCsv(String path, String fileName) {
        try {
            String pathLocal = "../";
            descargaArchivoFTP(path, pathLocal, fileName);
            generarCsv(fileName, pathLocal);
        } catch (ApplicationException e) {
            LOGGER.fatal("Error en descargarArchivoCsv.", e);
            FacesUtil.mostrarMensajeError("Se generó un error descargando archivo", e, LOG);
        }
    }

    private void generarCsv(String fileName, String pathLocal) throws ApplicationException {
        try {
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletResponse responseExp = (HttpServletResponse) fc.getExternalContext().getResponse();
            responseExp.setHeader("Content-disposition", "attached; filename=\"" + fileName + "\"");
            responseExp.setContentType("application/octet-stream;charset=UTF-8;");
            responseExp.setCharacterEncoding("UTF-8");

            File file = new File(new StringBuilder(pathLocal).append(fileName).toString());
            byte[] bytesArray = FileUtils.readFileToByteArray(file);
            responseExp.getOutputStream().write(bytesArray);
            responseExp.getOutputStream().flush();
            responseExp.flushBuffer();
            responseExp.getOutputStream().close();
            fc.responseComplete();
            FileToolUtils.deleteFile(file);
        } catch (IOException e) {
            LOGGER.fatal("Error en generarCsv.", e);
            throw new ApplicationException(e.getMessage());
        }
    }

    public ArchivoReporteDTO validarNombreArchivo(Integer idReporte, String nombreArchivo) throws ApplicationException {
        return reporteMglFacadeLocal.nombreArchivo(idReporte, nombreArchivo);
    }

    public String retColor(String estado) {
        return map_state_color.get(estado) == null ? "purple" : map_state_color.get(estado).toString();
    }

    public static final Map map_state_color = new HashMap() {
        {
            put("PROCESADO", "green");
            put("POR PROCESAR", "red");
            put("EN PROCESO", "yellow");
            put("PROCESADO CON ERRORES", "red");
        }
    };

    public String getPageActualConsulta() {
        paginaListConsulta = new ArrayList<>();
        int totalPaginas = getTotalPaginas();
        for (int i = 1; i <= totalPaginas; i++) {
            paginaListConsulta.add(i);
        }
        if (totalPaginas == 0) {
            actualConsulta = 1;
            totalPaginas = 1;
        }
        pageActualConsulta = actualConsulta + " de " + totalPaginas;

        numPaginaConsulta = String.valueOf(actualConsulta);
        return pageActualConsulta;
    }

    public int getTotalPaginas() {

        int totalPaginas = 0;
        try {
            int count = archivoConsulta == null ? 1 : archivoConsulta.size();
            totalPaginas = ((count % filasPag != 0) ? (count / filasPag) + 1 : count / filasPag);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Ocurrió un error:. " + e.getMessage(), e, LOG);
        }
        return totalPaginas;
    }

    public String listInfoByPage(int page) {
        try {
            int firstResult = 0;
            int ultResult = filasPag;
            if (page > 1) {
                firstResult = (filasPag * (page - 1));
                ultResult = firstResult + filasPag;
            }
            if(archivoConsulta != null){
                int size = archivoConsulta == null ? 0 : archivoConsulta.size();
                if (ultResult > size) ultResult = size;
                archivoConsultaPag = archivoConsulta.subList(firstResult, ultResult);
                actualConsulta = page;
            }else{
                actualConsulta = 1;
            }

        } catch (Exception e) {
            LOGGER.fatal("Error en listInfoByPage.", e);
            FacesUtil.mostrarMensajeError("Ocurrió un error:. " + e.getMessage(), e, LOG);
        }
        return "";
    }

    public void pageFirst() {
        listInfoByPage(1);
    }

    public void pagePrevious() {
        irPagina(actualConsulta - 1);
    }

    public void irPagina() {
        irPagina(Integer.parseInt(numPaginaConsulta));
    }

    public void pageNext() {
        irPagina(actualConsulta + 1);
    }

    private void irPagina(Integer pagina) {
        int totalPaginas = getTotalPaginas();
        int nuevaPageActual = pagina;
        if (nuevaPageActual > totalPaginas) {
            nuevaPageActual = totalPaginas;
        }
        if (nuevaPageActual <= 0) {
            nuevaPageActual = 1;
        }
        listInfoByPage(nuevaPageActual);
    }

    public void pageLast() {
        int totalPaginas = getTotalPaginas();
        listInfoByPage(totalPaginas);
    }

    public String getPageActualCargue() {
        paginaListCargue = new ArrayList<>();
        int totalPaginas = getTotalPaginasCargue();
        for (int i = 1; i <= totalPaginas; i++) {
            paginaListCargue.add(i);
        }
        if (totalPaginas == 0) {
            actualCargue = 1;
            totalPaginas = 1;
        }
        pageActualCargue = actualCargue + " de " + totalPaginas;

        numPaginaCargue = String.valueOf(actualCargue);
        return pageActualCargue;
    }

    public int getTotalPaginasCargue() {
        int totalPaginas = 0;
        try {
            int count = archivoCarga == null ? 1 : archivoCarga.size();
            totalPaginas = ((count % filasPag != 0) ? (count / filasPag) + 1 : count / filasPag);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Ocurrió un error:. " + e.getMessage(), e, LOG);
        }
        return totalPaginas;
    }

    public String listInfoByPageCargue(int page) {
        try {
            int firstResult = 0;
            int ultResult = filasPag;
            if (page > 1) {
                firstResult = (filasPag * (page - 1));
                ultResult = firstResult + filasPag;
            }
            if (archivoCarga != null) {
                int size = archivoCarga.size();
                if (ultResult > size) ultResult = size;
                archivoCargaPag = archivoCarga.subList(firstResult, ultResult);
                actualCargue = page;
            }else{
                actualCargue = 1;
            }
        } catch (Exception e) {
            LOGGER.fatal(e);
            FacesUtil.mostrarMensajeError("Ocurrió un error:. " + e.getMessage(), e, LOG);
        }
        return "";
    }

    public void pageFirstCargue() {
        listInfoByPageCargue(1);
    }

    public void pagePreviousCargue() {
        irPaginaCargue(actualCargue - 1);
    }

    public void irPaginaCargue() {
        irPaginaCargue(Integer.parseInt(numPaginaCargue));
    }

    public void pageNextCargue() {
        irPaginaCargue(actualCargue + 1);
    }

    private void irPaginaCargue(Integer pagina) {
        int totalPaginas = getTotalPaginasCargue();
        int nuevaPageActual = pagina;
        if (nuevaPageActual > totalPaginas) {
            nuevaPageActual = totalPaginas;
        }
        if (nuevaPageActual <= 0) {
            nuevaPageActual = 1;
        }
        listInfoByPageCargue(nuevaPageActual);
    }

    public void pageLastCargue() {
        int totalPaginas2 = getTotalPaginasCargue();
        listInfoByPageCargue(totalPaginas2);
    }

    public IConActEstComercialMglFacadeLocal getConActEstComercialMglFacadeLocal() {
        return conActEstComercialMglFacadeLocal;
    }

    public void setConActEstComercialMglFacadeLocal(IConActEstComercialMglFacadeLocal IConActEstComercialMglFacadeLocal) {
        this.conActEstComercialMglFacadeLocal = IConActEstComercialMglFacadeLocal;
    }

    public ITecnologiaMglFacadeLocal getTecnologiaMglFacadeLocal() {
        return tecnologiaMglFacadeLocal;
    }

    public void setTecnologiaMglFacadeLocal(ITecnologiaMglFacadeLocal ITecnologiaMglFacadeLocal) {
        this.tecnologiaMglFacadeLocal = ITecnologiaMglFacadeLocal;
    }

    public IDepartamentoMglFacadeLocal getDepartamentoMglFacadeLocal() {
        return departamentoMglFacadeLocal;
    }

    public void setDepartamentoMglFacadeLocal(IDepartamentoMglFacadeLocal IDepartamentoMglFacadeLocal) {
        this.departamentoMglFacadeLocal = IDepartamentoMglFacadeLocal;
    }

    public IAnchoBanRetoMglFacadeLocal getAnchoBanRetoMglFacadeLocal() {
        return anchoBanRetoMglFacadeLocal;
    }

    public void setAnchoBanRetoMglFacadeLocal(IAnchoBanRetoMglFacadeLocal IAnchoBanRetoMglFacadeLocal) {
        this.anchoBanRetoMglFacadeLocal = IAnchoBanRetoMglFacadeLocal;
    }

    public ICiudadMglFacadeLocal getCiudadMglFacadeLocal() {
        return ciudadMglFacadeLocal;
    }

    public void setCiudadMglFacadeLocal(ICiudadMglFacadeLocal ciudadMglFacadeLocal) {
        this.ciudadMglFacadeLocal = ciudadMglFacadeLocal;
    }

    public ICentroPobladoMglFacadeLocal getCentroPobladoMglFacadeLocal() {
        return centroPobladoMglFacadeLocal;
    }

    public void setCentroPobladoMglFacadeLocal(ICentroPobladoMglFacadeLocal centroPobladoMglFacadeLocal) {
        this.centroPobladoMglFacadeLocal = centroPobladoMglFacadeLocal;
    }

    public IDivisionalFacadeLocal getDivisionalFacadeLocal() {
        return divisionalFacadeLocal;
    }

    public void setDivisionalFacadeLocal(IDivisionalFacadeLocal divisionalFacadeLocal) {
        this.divisionalFacadeLocal = divisionalFacadeLocal;
    }

    public NodoMglFacadeLocal getNodoMglFacadeLocal() {
        return nodoMglFacadeLocal;
    }

    public void setNodoMglFacadeLocal(NodoMglFacadeLocal nodoMglFacadeLocal) {
        this.nodoMglFacadeLocal = nodoMglFacadeLocal;
    }

    public UsuarioServicesFacadeLocal getUsuarioServicesFacade() {
        return usuarioServicesFacade;
    }

    public void setUsuarioServicesFacade(UsuarioServicesFacadeLocal usuarioServicesFacade) {
        this.usuarioServicesFacade = usuarioServicesFacade;
    }

    public List<NodoEstadoDTO> getNodoEstadoDTOList() {
        return nodoEstadoDTOList;
    }

    public void setNodoEstadoDTOList(List<NodoEstadoDTO> nodoEstadoDTOList) {
        this.nodoEstadoDTOList = nodoEstadoDTOList;
    }

    public List<TecnologiaDTO> getListTablaBasicaTecnologias() {
        return listTablaBasicaTecnologias;
    }

    public void setListTablaBasicaTecnologias(List<TecnologiaDTO> listTablaBasicaTecnologias) {
        this.listTablaBasicaTecnologias = listTablaBasicaTecnologias;
    }

    public SecurityLogin getSecurityLogin() {
        return securityLogin;
    }

    public void setSecurityLogin(SecurityLogin securityLogin) {
        this.securityLogin = securityLogin;
    }

    public FacesContext getFacesContext() {
        return facesContext;
    }

    public void setFacesContext(FacesContext facesContext) {
        this.facesContext = facesContext;
    }

    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public String getUsuarioVT() {
        return usuarioVT;
    }

    public void setUsuarioVT(String usuarioVT) {
        this.usuarioVT = usuarioVT;
    }

    public List<TecnologiaDTO> getTecnologias() {
        return tecnologias;
    }

    public void setTecnologias(List<TecnologiaDTO> tecnologias) {
        this.tecnologias = tecnologias;
    }

    public List<DepartamentoDTO> getDepartamentos() {
        return departamentos;
    }

    public void setDepartamentos(List<DepartamentoDTO> departamentos) {
        this.departamentos = departamentos;
    }

    public List<CiudadDTO> getCiudades() {
        return ciudades;
    }

    public void setCiudades(List<CiudadDTO> ciudades) {
        this.ciudades = ciudades;
    }

    public List<CentroPobladoDTO> getCentrosPoblados() {
        return centrosPoblados;
    }

    public void setCentrosPoblados(List<CentroPobladoDTO> centrosPoblados) {
        this.centrosPoblados = centrosPoblados;
    }

    public List<DivisionalDTO> getDivisionales() {
        return divisionales;
    }

    public void setDivisionales(List<DivisionalDTO> divisionales) {
        this.divisionales = divisionales;
    }

    public List<String> getEstadosNodo() {
        return estadosNodo;
    }

    public void setEstadosNodo(List<String> estadosNodo) {
        this.estadosNodo = estadosNodo;
    }

    public List<String> getAnchosBanda() {
        return anchosBanda;
    }

    public void setAnchosBanda(List<String> anchosBanda) {
        this.anchosBanda = anchosBanda;
    }

    public String getEstiloObligatorio() {
        return estiloObligatorio;
    }

    public void setEstiloObligatorio(String estiloObligatorio) {
        this.estiloObligatorio = estiloObligatorio;
    }

    public ActEstComercialDto getActEstComercialDto() {
        return actEstComercialDto;
    }

    public void setActEstComercialDto(ActEstComercialDto actEstComercialDto) {
        this.actEstComercialDto = actEstComercialDto;
    }

    public UploadedFile getCargueArchivo() {
        return cargueArchivo;
    }

    public void setCargueArchivo(UploadedFile cargueArchivo) {
        this.cargueArchivo = cargueArchivo;
    }

    public List<DepartamentoDTO> getDepartamentoList() {
        return departamentoList;
    }

    public void setDepartamentoList(List<DepartamentoDTO> departamentoList) {
        this.departamentoList = departamentoList;
    }

    public List<CiudadDTO> getCiudadList() {
        return ciudadList;
    }

    public void setCiudadList(List<CiudadDTO> ciudadList) {
        this.ciudadList = ciudadList;
    }

    public List<AnchoBanRetoDTO> getAnchoBanRetoList() {
        return anchoBanRetoList;
    }

    public void setAnchoBanRetoList(List<AnchoBanRetoDTO> anchoBanRetoList) {
        this.anchoBanRetoList = anchoBanRetoList;
    }

    public CmtCuentaMatrizMgl getCuentaMatriz() {
        return cuentaMatriz;
    }

    public void setCuentaMatriz(CmtCuentaMatrizMgl cuentaMatriz) {
        this.cuentaMatriz = cuentaMatriz;
    }

    public UsuariosServicesDTO getUsuarioLogin() {
        return usuarioLogin;
    }

    public void setUsuarioLogin(UsuariosServicesDTO usuarioLogin) {
        this.usuarioLogin = usuarioLogin;
    }

    public GeograficoPoliticoMgl getCiudadSeleccionada() {
        return ciudadSeleccionada;
    }

    public void setCiudadSeleccionada(GeograficoPoliticoMgl ciudadSeleccionada) {
        this.ciudadSeleccionada = ciudadSeleccionada;
    }

    public List<CentroPobladoDTO> getCentroPobladoList() {
        return centroPobladoList;
    }

    public void setCentroPobladoList(List<CentroPobladoDTO> centroPobladoList) {
        this.centroPobladoList = centroPobladoList;
    }

    public List<DivisionalDTO> getDivisionalList() {
        return divisionalList;
    }

    public void setDivisionalList(List<DivisionalDTO> divisionalList) {
        this.divisionalList = divisionalList;
    }

    public List<ArchActEstComercialDto> getArchivoConsulta() {
        return archivoConsulta;
    }

    public void setArchivoConsulta(List<ArchActEstComercialDto> archivoConsulta) {
        this.archivoConsulta = archivoConsulta;
    }

    public List<ArchActEstComercialDto> getArchivoConsultaPag() {
        return archivoConsultaPag;
    }

    public void setArchivoConsultaPag(List<ArchActEstComercialDto> archivoConsultaPag) {
        this.archivoConsultaPag = archivoConsultaPag;
    }

    public int getFilasPag() {
        return filasPag;
    }

    public void setFilasPag(int filasPag) {
        this.filasPag = filasPag;
    }

    public List<ArchActEstComercialDto> getArchivoCarga() {
        return archivoCarga;
    }

    public void setArchivoCarga(List<ArchActEstComercialDto> archivoCarga) {
        this.archivoCarga = archivoCarga;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getCentroProblado() {
        return centroProblado;
    }

    public void setCentroProblado(String centroProblado) {
        this.centroProblado = centroProblado;
    }

    public String getDivisional() {
        return divisional;
    }

    public void setDivisional(String divisional) {
        this.divisional = divisional;
    }

    public String getAnchoBandReto() {
        return anchoBandReto;
    }

    public void setAnchoBandReto(String anchoBandReto) {
        this.anchoBandReto = anchoBandReto;
    }

    public String getEstateProcess() {
        return estateProcess;
    }

    public void setEstateProcess(String estateProcess) {
        this.estateProcess = estateProcess;
    }

    public List<Integer> getPaginaListConsulta() {
        return paginaListConsulta;
    }

    public void setPaginaListConsulta(List<Integer> paginaListConsulta) {
        this.paginaListConsulta = paginaListConsulta;
    }

    public void setPageActualConsulta(String pageActualConsulta) {
        this.pageActualConsulta = pageActualConsulta;
    }

    public String getNumPaginaConsulta() {
        return numPaginaConsulta;
    }

    public void setNumPaginaConsulta(String numPaginaConsulta) {
        this.numPaginaConsulta = numPaginaConsulta;
    }

    public boolean isPintarPaginado() {
        return pintarPaginado;
    }

    public void setPintarPaginado(boolean pintarPaginado) {
        this.pintarPaginado = pintarPaginado;
    }

    public int getActualConsulta() {
        return actualConsulta;
    }

    public void setActualConsulta(int actualConsulta) {
        this.actualConsulta = actualConsulta;
    }

    public List<ArchActEstComercialDto> getArchivoCargaPag() {
        return archivoCargaPag;
    }

    public void setArchivoCargaPag(List<ArchActEstComercialDto> archivoCargaPag) {
        this.archivoCargaPag = archivoCargaPag;
    }

    public void setPageActualCargue(String pageActualCargue) {
        this.pageActualCargue = pageActualCargue;
    }

    public String getNumPaginaCargue() {
        return numPaginaCargue;
    }

    public void setNumPaginaCargue(String numPaginaCargue) {
        this.numPaginaCargue = numPaginaCargue;
    }

    public int getActualCargue() {
        return actualCargue;
    }

    public void setActualCargue(int actualCargue) {
        this.actualCargue = actualCargue;
    }

    public List<Integer> getPaginaListCargue() {
        return paginaListCargue;
    }

    public void setPaginaListCargue(List<Integer> paginaListCargue) {
        this.paginaListCargue = paginaListCargue;
    }

    public String getEstateProcessError() {
        return estateProcessError;
    }

    public void setEstateProcessError(String estateProcessError) {
        this.estateProcessError = estateProcessError;
    }

    public String getTecnologia() {
        return tecnologia;
    }

    public void setTecnologia(String tecnologia) {
        this.tecnologia = tecnologia;
    }

    public String getEstadoNodo() {
        return estadoNodo;
    }

    public void setEstadoNodo(String estadoNodo) {
        this.estadoNodo = estadoNodo;
    }

    public IReporteMglFacadeLocal getReporteMglFacadeLocal() {
        return reporteMglFacadeLocal;
    }

    public void setReporteMglFacadeLocal(IReporteMglFacadeLocal reporteMglFacadeLocal) {
        this.reporteMglFacadeLocal = reporteMglFacadeLocal;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isRolEditar() {
        return rolEditar;
    }

    public void setRolEditar(boolean rolEditar) {
        this.rolEditar = rolEditar;
    }

    public boolean isRolConsultar() {
        return rolConsultar;
    }

    public void setRolConsultar(boolean rolConsultar) {
        this.rolConsultar = rolConsultar;
    }
}
