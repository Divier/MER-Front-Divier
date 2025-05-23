/*
 * Copyright (c) 2017, and/or its affiliates. All rights reserved.
 * CLARO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package co.com.claro.mgl.mbeans.vt.procesomasivo;

import co.com.claro.mer.blockreport.BlockReportServBean;
import co.com.claro.mer.dtos.request.procedure.TblLineasMasivoResquestDto;
import co.com.claro.mer.utils.CarguesMasivosMensajeria;
import co.com.claro.mer.utils.StringToolUtils;
import co.com.claro.mer.utils.pagination.PaginatorUtil;
import co.com.claro.mer.dtos.sp.cursors.CargueInformacionDto;
import co.com.claro.mer.dtos.sp.cursors.InfoGeneralCargueDto;
import co.com.claro.mer.dtos.response.procedure.TblLineasMasivoResponseDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.*;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtDireccionDetalleMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.procesomasivo.CarguesMasivosFacadeLocal;
import co.com.claro.mgl.facade.ptlus.UsuarioServicesFacadeLocal;
import co.com.claro.mgl.jpa.entities.*;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionDetalladaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.claro.mgl.rest.dtos.UsuariosServicesDTO;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.data.Geografico;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static co.com.claro.mgl.mbeans.util.ConstantsCM.PAGINACION_CINCO_FILAS;

/**
 * Para conectar la UI de procesar reporte y la lógica.
 * <p>
 * Se establece la clase con el fin de crear una conexión entre la interfaz de
 * procesar reporte y la lógica del mismo.
 *
 * @author becerraarmr
 */
@ManagedBean(name = "proReporteBean")
@ViewScoped
@Log4j2
public class ProcesarReporteBean implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private PaginatorUtil<CargueInformacionDto> paginator;

    @Getter
    @Setter
    private int selectedPageCargue = 1;

    @Getter
    @Setter
    private List<Integer> pageNumberSelectorCargue;
    
    @Getter
    @Setter
    private InfoGeneralCargueDto informacionIncialReportes;
    private List<CargueInformacionDto> listaInformacionCargados = new ArrayList<>();//Tercer SP
    private TblLineasMasivoResponseDto listaInformacionConsulta = new TblLineasMasivoResponseDto(); //Tercer SP

    @Getter
    @Setter
    List<CargueInformacionDto> registrosFiltro = new ArrayList<>();//auxiliares

    /**
     * Fecha Inicio Reporte inhabilitar.
     */
    private Date fechaInicioInhabilitarHhpp;
    /**
     * Fecha Final reporte inhabilitar.
     */
    private Date fechaFinInhabilitarHhpp;
    /**
     * Tipo de reporte a procesar.
     */
    private String tipoReporte;

    /**
     * Regional o división donde quiere buscar.
     */
    private String division;

    /**
     * Comunidad o ciudad donde se quiere realizar la busqueda
     */
    private String comunidad;

    /**
     * Centro poblado de una ciudad seleccionada
     */
    private String centroPoblado;

    /**
     * Tipo de tecnología para trabajar
     */
    private String tipoTecnologia;

    /**
     * Departamento a revisar
     */
    private String departamento;

    /**
     * Ciudad según el departamento seleccionado
     */
    private String ciudad;
    /**
     * Para sabe si es una ciudad multiorigen
     */
    private boolean ciudadMultiOrigen;

    /**
     * Nodo con el que se quiere realizar el reporte
     */
    private String nodo;

    /**
     * Atributo para el reporte detallado
     */
    private String atributo;
    
    private String idCcmmMgl;
    private String idCcmmRr;

    /**
     * Valor del atributo según el atributo sea un String o un DrDireccion
     */
    private Object valorAtributo;

    /**
     * Para controlar si se muestran o no algunos atributos
     */
    private boolean atributosVisible;

    /**
     * Para ver si se puede exportar el archivo XLS
     */
    private boolean puedeExportarXls;

    /**
     * Para ver si se puede exportar el archivo TXT
     */
    private boolean puedeExportarTxt;

    /**
     * Para ver si se puede exportar el archivo CSV
     */
    private boolean puedeExportarCsv;

    /**
     * Establecer si se puede verificar.
     */
    private boolean puedeVerificar;

    /**
     * Establecer si se puede crear reporte de inhabilitar Hhppp
     */
    private boolean puedeInhabilitarHhppp;

    /**
     * Puede establecer si puede procesar el reporte.
     */
    private boolean puedeProcesar = true;

    /**
     * Para conocer si se está procesando la solicitud.
     */
    private boolean procesandoSolicitud;

    /**
     * Para establecer si puede mostrar el panel de mensaje.
     */
    private boolean mostrarPanelMensaje;
    /**
     * Saber si la ciudad seleccionada es multiorigen.
     */

    /**
     * Para conocer el mensaje de procesamiento.
     */
    private String msgProcesamiento = "";

    /**
     * Para conocer el nombre del archivo temporall
     */
    private String nameFileTmp;

    /**
     * Para guardar la data del archivo csv y txt.
     */
    private StringBuilder dataCsvTxt = null;

    /**
     * Cmt_Basica Tipo_Tecnologia
     */
    private final String TIPO_TECNOLOGIA = "TIPO_TECNOLOGIA";

    /**
     * Cmt_Basica ATRIBUTOS_HHPP
     */
    private final String ATRIBUTOS_HHPP = "ATRIBUTOS HHPP";

    /**
     * Constante que identifica el reporte General
     */
    private final String REPORTE_GENERAL = "REPORTE_GENERAL";

    /**
     * Constante que identifica el reporte detallado.
     */
    private final String REPORTE_DETALLADO = "REPORTE_DETALLADO";

    /**
     * Para instanciar el FacesContext
     */
    private FacesContext facesContext = FacesContext.getCurrentInstance();

    /**
     * Para instanciar el HttpServletResponse
     */
    private HttpServletResponse response
            = (HttpServletResponse) facesContext.getExternalContext().getResponse();

    /**
     * Usuario logeado
     */
    private String usuarioVT = null;

    /**
     * Valor del usuario logeado
     */
    protected UsuariosServicesDTO usuarioLogin = new UsuariosServicesDTO();

    /**
     * Cedula del usuario logeado
     */
    private String cedulaUsuarioVT = null;

    /**
     * Registros encontrados
     */
    private int registrosEncontrados = 0;

    /**
     * Habilita o deshabilita el Boton "Procesar reporte"
     */
    private boolean procesarReporteBtn;

    /**
     * Bean consulta direccion
     */
    private ConsultaDireccionBean direccionBean;

    /**
     * Datos de seguridad del login.
     */
    private SecurityLogin securityLogin;
    private ProcesarReporte procesarReporte;
    private boolean creadoXFicha = false;

    @Getter
    private SelectItem[] itemsTiposTecnologiasList;

    @Getter
    private SelectItem[] itemsDepartamentosList;

    private SelectItem[] itemsCiudades;

    @Getter
    private SelectItem[] itemsCentrosPobladosList;

    @Getter
    private SelectItem[] itemsNodosList;

    @Getter
    private SelectItem[] itemsEtiquetasList;

    @Getter
    private SelectItem[] itemsAtributosList;



    @EJB
    private UsuarioServicesFacadeLocal ejbUsuario;
    @EJB
    private GeograficoMglFacadeLocal ejbGeografico;
    @EJB
    private DireccionMglFacadeLocal ejbDireccion;
    @EJB
    private CmtBasicaMglFacadeLocal ejbBasica;
    @EJB
    private CmtTipoBasicaMglFacadeLocal ejbTipoBasica;
    @EJB
    private GeograficoPoliticoMglFacadeLocal ejbGeograficoPolitico;
    @EJB
    private NodoMglFacadeLocal ejbNodo;
    @EJB
    private HhppMglFacadeLocal ejbHhpp;
    @EJB
    private RrRegionalesFacadeLocal ejbRegionales;
    @EJB
    private RrCiudadesFacadeLocal ejbComunidades;
    @EJB
    private CmtBasicaMglFacadeLocal cmtBasicaMglFacadeLocal;
    @EJB
    private CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMglFacadeLocal;
    @EJB
    private CmtDireccionDetalleMglFacadeLocal detalleMglFacadeLocal;
    @EJB
    private TipoHhppMglFacadeLocal tipoHhppMglFacadeLocal;
    
    @EJB
    private MarcasMglFacadeLocal marcasMglFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacade;

    @EJB
    private CarguesMasivosFacadeLocal servicioMasivos;
    @Inject
    private BlockReportServBean blockReportServBean;
    private String etiquetaSeleccionada;
    private String estiloObligatorio = "<font color='red'>*</font>";

    //Opciones agregadas para Rol
    private final String BTNOHHPPS = "BTNOHHPPS";
    //Opciones agregadas para Rol
    private final String BTNINHRPT = "BTNINHRPT";

    private static final String NOMBRE_PROCESO = "REPORTE_MAS_HHPP";

    private static final String FILTROS_APLICADOS = "FILTROS_APLICADOS";
    private static final String FILTROS_TRADUCIDOS = "FILTROS_TRADUCIDOS";
    /**
     * constructor para crear instancia el cual se redirecciona si el usaurio y
     * el perfil no existe
     *
     * @author becerraarmr
     */
    public ProcesarReporteBean() {
        try {
            securityLogin = new SecurityLogin(facesContext);
            if (!securityLogin.isLogin()) {
                if (!response.isCommitted()) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                }
                return;
            }
            usuarioVT = securityLogin.getLoginUser();
            cedulaUsuarioVT = securityLogin.getIdUser();
            
            if (usuarioVT == null) {
                if (!response.isCommitted()) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                }
            } else {
                if (ejbUsuario != null && usuarioVT != null) {
                    usuarioLogin = ejbUsuario.consultaInfoUserPorUsuario(usuarioVT);
                }
            }
        } catch (ApplicationException | IOException e) {
            FacesUtil.mostrarMensajeError("Error en ProcesarReporteBean. Validando Autenticacion. ", e, LOGGER);
        }
    }

    @PostConstruct
    public void init()  {
        try {
            List<InfoGeneralCargueDto> listaInformacionCargue = servicioMasivos.contblMasivosSp(NOMBRE_PROCESO); //Aprocionamiento inicial para el proceso
            informacionIncialReportes = listaInformacionCargue.get(0);
            listaInformacionCargados=servicioMasivos.contblLineasMasivoSp(NOMBRE_PROCESO, informacionIncialReportes.getTipo(), null); //Informacion de reportes creados
            listInfoByPageCargues(1);

            //leer informacion de formularios

            this.itemsTiposTecnologiasList = getItemsTiposTecnologias();
            this.itemsDepartamentosList = getItemsDepartamentos();

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en ProcesarReporteBean. No se pudo cargar la información inicial ", e, LOGGER);
        }

    }

    /**
     * Verificar el estado del proceso
     * <p>
     * Verifica como va el procesamiento de la solicitud de reporte
     *
     * @author becerraarmr
     *
     */
    public void verificarEstado() {
        if (procesarReporte.getRegistrosProcesados() > 0) {
            msgProcesamiento = "Se ha procesado un total de: "
                    + procesarReporte.getRegistrosProcesados();
        }
        if (!procesandoSolicitud) {
            msgProcesamiento = "Se ha procesado todo";
            if (procesarReporte.getRegistrosEncontrados() > 0) {
                puedeExportarXls = true;
                puedeExportarTxt = true;
                puedeExportarCsv = true;
            }
            puedeVerificar = false;
        }
    }

    /**
     * Valida si la ciudad es multiorigen.
     *
     * @author becerraarmr.
     */
    public void validaCiudadMultiorigen() {
        ciudadMultiOrigen = false;
        try {
            BigDecimal aux = new BigDecimal(ciudad);
            GeograficoPoliticoMgl gp = ejbGeograficoPolitico.findById(aux);
            if ("1".equalsIgnoreCase(gp.getGpoMultiorigen())) {
                ciudadMultiOrigen = true;
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en validaCiudadMultiorigen. ", e, LOGGER);
            ciudadMultiOrigen = false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en validaCiudadMultiorigen. ", e, LOGGER);
            ciudadMultiOrigen = false;
        }
    }

    /**
     * Procesar el reporte
     * <p>
     * Valida los valores de tipo Tecnología, ciudad y tipo de reporte para
     * luego solicitar al Thread ProcesarReporteThread que procese la petición.
     *
     * @author becerraarmr
     * @param puedeInhabilitarHhppp boolean si puede inhabilitar o no.
     * @throws ApplicationException Si ocurre un error al encontrar los nodos o
     * al contar la data del reporte.
     */
    public void procesarReporte(boolean puedeInhabilitarHhppp) throws ApplicationException {
        procesarReporteBtn = true;

        try {
            // Se verifica si está bloqueada la generación de reportes y si
            // el usuario en sesión está autorizado para realizar el proceso.
            if (blockReportServBean.isReportGenerationBlock("Reporte Inhabilitar Hhpp")) return;
            //Validaciones
            if ((idCcmmMgl == null || idCcmmMgl.isEmpty())
                    && (idCcmmRr == null || idCcmmRr.isEmpty())) {
                if (tipoTecnologia == null || tipoTecnologia.isEmpty()) {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    "Es necesario seleccionar un un tipo de tecnología", ""));
                    procesarReporteBtn = false;
                    return;
                } else {
                    if (departamento == null || departamento.isEmpty()) {
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_WARN,
                                        "Es necesario seleccionar un departamento ", ""));
                        procesarReporteBtn = false;
                        return;
                    } else {
                        if (ciudad == null || ciudad.isEmpty()) {
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                            "Es necesario seleccionar una ciudad ", ""));
                            procesarReporteBtn = false;
                            return;
                        } else {
                            if (centroPoblado == null || centroPoblado.isEmpty()) {
                                FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                "Es necesario seleccionar una ciudad ", ""));
                                procesarReporteBtn = false;
                                return;
                            }
                        }
                    }
                }
            }

            procesarReporte = new ProcesarReporte(this);
            if (puedeInhabilitarHhppp) {
                if (nodo == null && etiquetaSeleccionada == null
                        && fechaInicioInhabilitarHhpp == null
                        && fechaFinInhabilitarHhpp == null && !creadoXFicha) {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    "Es necesario seleccionar al menos un valor: Nodo - Etiqueta - "
                                            + "Fecha Inicial Creación - Fecha Final Creación - Creado por Ficha  "
                                            + "para generar el reporte.", ""));
                    procesarReporteBtn = false;
                    return;
                }
                tipoReporte = REPORTE_GENERAL;
            }
            if (tipoReporte != null) {
                if (REPORTE_DETALLADO.equalsIgnoreCase(tipoReporte)) {

                    if (atributo == null || atributo.isEmpty()) {
                        msgProcesamiento = "No ha seleccionado un atributo";
                        return;
                    } else {
                        BigDecimal idBasica = new BigDecimal(atributo);
                        CmtBasicaMgl atributoDir = ejbBasica.findByCodigoInternoApp(Constant.BASICA_ATRIBUTO_CCMM_DIRECCION);
                        CmtBasicaMgl atributoSel = ejbBasica.findById(idBasica);

                        if (atributoDir != null && atributoSel != null) {
                            if (atributoDir.getBasicaId().compareTo(atributoSel.getBasicaId()) == 0) {
                                LOGGER.info("No se valida el valor del atributo");
                                //JDHT //Si es dirección

                                if ((nodo == null || nodo.isEmpty()) && valorAtributo == null) {
                                    FacesContext.getCurrentInstance().addMessage(null,
                                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                    "Es necesario seleccionar un nodo "
                                                            + "para generar un reporte de direccion sin construir una dirección.", ""));
                                    procesarReporteBtn = false;
                                    return;
                                }

                            } else {
                                if (Constant.CODIGO_BASICA_COBERTURA.equals(atributoSel.getCodigoBasica())) {
                                    //JDHT
                                    //Si es dirección
                                    if ((nodo == null || nodo.isEmpty()) && valorAtributo == null) {
                                        FacesContext.getCurrentInstance().addMessage(null,
                                                new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                        "Es necesario seleccionar un nodo "
                                                                + "para generar un reporte de direccion sin construir una dirección.", ""));
                                        procesarReporteBtn = false;
                                        return;
                                    }

                                }
                                if (Constant.CODIGO_BASICA_ETIQUETA.equals(atributoSel.getCodigoBasica())) {
                                    if (etiquetaSeleccionada == null || etiquetaSeleccionada.isEmpty()) {
                                        FacesContext.getCurrentInstance().addMessage(null,
                                                new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                        "Es necesario seleccionar una etiqueta en el filtro", ""));
                                        procesarReporteBtn = false;
                                        return;
                                    }
                                }

                                if (Constant.CODIGO_BASICA_ESTRATO.equals(atributoSel.getCodigoBasica())) {
                                    String valor = (String) valorAtributo;
                                    if (valor.matches("[a-z]*") || valor.matches("[A-Z]*")) {
                                        FacesContext.getCurrentInstance().addMessage(null,
                                                new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                        "Valor no permitido para el estrato", ""));
                                        procesarReporteBtn = false;
                                        return;
                                    }
                                }

                                if (Constant.CODIGO_BASICA_TIPO_VIVIENDA.equals(atributoSel.getCodigoBasica())) {
                                    if (valorAtributo == null || valorAtributo.toString().isEmpty()) {
                                        FacesContext.getCurrentInstance().addMessage(null,
                                                new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                        "Es necesario seleccionar un tipo de vivienda", ""));
                                        procesarReporteBtn = false;
                                        return;
                                    }
                                }
                            }
                        }

                    }
                }
                this.puedeInhabilitarHhppp = puedeInhabilitarHhppp;
                procesandoSolicitud = true;
                procesarReporte.procesar();
                registrosEncontrados = procesarReporte.getRegistrosEncontrados();
                puedeVerificar = true;
                puedeProcesar = true;
                msgProcesamiento = "Procesando el archivo";
                if (registrosEncontrados > 0) {
                    puedeExportarXls = true;
                    puedeExportarTxt = true;
                    puedeExportarCsv = true;
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO,
                                    "El reporte se ha procesado exitosamente con <b>" + registrosEncontrados + "</b> registros encontrados", ""));
                } else {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    "No se han encontrado registros", ""));
                }

            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en procesarReporte. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en procesarReporte. ", e, LOGGER);
        }

    }

    /**
     * Procesar el reporte
     * <p>
     * Valida los valores de tipo Tecnología, ciudad y tipo de reporte para
     * luego solicitar al Thread ProcesarReporteThread que procese la petición.
     *
     * @author Manuel Hernandez
     * @param puedeInhabilitarHhppp boolean si puede inhabilitar o no.
     */
    public void procesarReporteMasivo(boolean puedeInhabilitarHhppp, String usuarioReporte) {
        procesarReporteBtn = true;
        //
        try {
            // Se verifica si está bloqueada la generación de reportes y si
            // el usuario en sesión está autorizado para realizar el proceso.
            if (blockReportServBean.isReportGenerationBlock("Solicitar Reporte General o Detallado...")) return;
            //Validaciones
            if ((idCcmmMgl == null || idCcmmMgl.isEmpty())
                    && (idCcmmRr == null || idCcmmRr.isEmpty())) {

                if (StringUtils.isBlank(tipoReporte)){
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    "Tipo Reporte Requerido", ""));
                    procesarReporteBtn = false;
                    return;
                }
                if (tipoTecnologia == null || tipoTecnologia.isEmpty()) {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    "Es necesario seleccionar un un tipo de tecnología", ""));
                    procesarReporteBtn = false;
                    return;
                } else {
                    if (departamento == null || departamento.isEmpty()) {
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_WARN,
                                        "Es necesario seleccionar un departamento ", ""));
                        procesarReporteBtn = false;
                        return;
                    } else {
                        if (ciudad == null || ciudad.isEmpty()) {
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                            "Es necesario seleccionar una ciudad ", ""));
                            procesarReporteBtn = false;
                            return;
                        } else {
                            if (centroPoblado == null || centroPoblado.isEmpty()) {
                                FacesContext.getCurrentInstance().addMessage(null,
                                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                "Es necesario seleccionar una ciudad ", ""));
                                procesarReporteBtn = false;
                                return;
                            }
                        }
                    }
                }
            }

            procesarReporte = new ProcesarReporte(this);
            if (puedeInhabilitarHhppp) {
                if (nodo == null && etiquetaSeleccionada == null
                        && fechaInicioInhabilitarHhpp == null
                        && fechaFinInhabilitarHhpp == null && !creadoXFicha) {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    "Es necesario seleccionar al menos un valor: Nodo - Etiqueta - "
                                            + "Fecha Inicial Creación - Fecha Final Creación - Creado por Ficha  "
                                            + "para generar el reporte.", ""));
                    procesarReporteBtn = false;
                    return;
                }
                tipoReporte = REPORTE_GENERAL;
            }
            if (tipoReporte != null) {
                if (REPORTE_DETALLADO.equalsIgnoreCase(tipoReporte)) {

                    if (atributo == null || atributo.isEmpty()) {
                        msgProcesamiento = "No ha seleccionado un atributo";
                        return;
                    } else {
                        BigDecimal idBasica = new BigDecimal(atributo);
                        CmtBasicaMgl atributoDir = ejbBasica.findByCodigoInternoApp(Constant.BASICA_ATRIBUTO_CCMM_DIRECCION);
                        CmtBasicaMgl atributoSel = ejbBasica.findById(idBasica);

                        if (atributoDir != null && atributoSel != null) {
                            if (atributoDir.getBasicaId().compareTo(atributoSel.getBasicaId()) == 0) {
                                LOGGER.info("No se valida el valor del atributo");
                                //JDHT //Si es dirección

                                if ((nodo == null || nodo.isEmpty()) && valorAtributo == null) {
                                    FacesContext.getCurrentInstance().addMessage(null,
                                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                    "Es necesario seleccionar un nodo "
                                                            + "para generar un reporte de direccion sin construir una dirección.", ""));
                                    procesarReporteBtn = false;
                                    return;
                                }

                            } else {
                                if (Constant.CODIGO_BASICA_COBERTURA.equals(atributoSel.getCodigoBasica())) {
                                    //JDHT
                                    //Si es dirección
                                    if ((nodo == null || nodo.isEmpty()) && valorAtributo == null) {
                                        FacesContext.getCurrentInstance().addMessage(null,
                                                new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                        "Es necesario seleccionar un nodo "
                                                                + "para generar un reporte de direccion sin construir una dirección.", ""));
                                        procesarReporteBtn = false;
                                        return;
                                    }

                                }
                                if (Constant.CODIGO_BASICA_ETIQUETA.equals(atributoSel.getCodigoBasica())) {
                                    if (etiquetaSeleccionada == null || etiquetaSeleccionada.isEmpty()) {
                                        FacesContext.getCurrentInstance().addMessage(null,
                                                new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                        "Es necesario seleccionar una etiqueta en el filtro", ""));
                                        procesarReporteBtn = false;
                                        return;
                                    }
                                }

                                if (Constant.CODIGO_BASICA_ESTRATO.equals(atributoSel.getCodigoBasica())) {
                                    String valor = (String) valorAtributo;
                                    if (valor.matches("[a-z]*") || valor.matches("[A-Z]*")) {
                                        FacesContext.getCurrentInstance().addMessage(null,
                                                new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                        "Valor no permitido para el estrato", ""));
                                        procesarReporteBtn = false;
                                        return;
                                    }
                                }

                                if (Constant.CODIGO_BASICA_TIPO_VIVIENDA.equals(atributoSel.getCodigoBasica())) {
                                    if (valorAtributo == null || valorAtributo.toString().isEmpty()) {
                                        FacesContext.getCurrentInstance().addMessage(null,
                                                new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                        "Es necesario seleccionar un tipo de vivienda", ""));
                                        procesarReporteBtn = false;
                                        return;
                                    }
                                }
                            }
                        }

                    }
                }
                this.puedeInhabilitarHhppp = puedeInhabilitarHhppp;
                procesandoSolicitud = true;

                final HashMap<String, String> filtrosConsulta = obtenerFiltros();
                TblLineasMasivoResquestDto resquestDto = new TblLineasMasivoResquestDto("I",
                        informacionIncialReportes.getMasivoId(),
                        informacionIncialReportes.getTipo(),
                        null, filtrosConsulta.get(FILTROS_APLICADOS), filtrosConsulta.get(FILTROS_TRADUCIDOS),
                        null,usuarioReporte, "LIKNX", null,"","");

                listaInformacionConsulta = servicioMasivos.tblLineasMasivoSp(resquestDto);
                consultarRegistrosSegunEstado();

            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en procesarReporte. ", e, LOGGER);
        }

    }

    /**
     * Notifica el cambio
     *
     * @author becerraarmr
     * @return String Vacio.
     */
    public String notificar() {
        FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("formReporte:msgProcesamiento");
        FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("formReporte:exportar");
        return "";
    }

    /**
     * verificacion de campos obligatorios para reporte detallado direccion
     *
     * @author Andres leal
     * @return boolean verificador si los campos tienen contenido
     */
    public boolean verificarDireccions() {
        HttpSession session;
        session = (HttpSession) FacesContext.getCurrentInstance().
                getExternalContext().getSession(false);
        direccionBean = (ConsultaDireccionBean) session.getAttribute("CONSULTAR_DIRECCION_BEAN");
        
        if (direccionBean.getTipoDireccion().equals("CK")) {
            if (direccionBean.getDireccionCk() == null
                    || direccionBean.getDireccionCk().isEmpty()) {
                return false;
            }
        } else if (direccionBean.getTipoDireccion().equals("BM")) {
            if (direccionBean.getNivelValorBm() == null
                    || direccionBean.getNivelValorBm().isEmpty()
                    || direccionBean.getNivelValorBm() == null
                    || direccionBean.getNivelValorBm().isEmpty()
                    || direccionBean.getNivelValorBm() == null
                    || direccionBean.getNivelValorBm().isEmpty()) {
                return false;
            }
            
        } else if (direccionBean.getTipoDireccion().equals("IT")) {
            if (direccionBean.getNivelValorIt() == null
                    || direccionBean.getNivelValorIt().isEmpty()
                    || direccionBean.getComplemento() == null
                    || direccionBean.getComplemento().isEmpty()
                    || direccionBean.getApartamento() == null
                    || direccionBean.getApartamento().isEmpty()) {
                return false;
            }
            
        }
        
        return true;
    }

    /**
     * Preparar una nueva petición
     * <p>
     * Se inicializan las variables de petición con el fin de poder establecer
     * una nueva petición de reporte.
     *
     * @author becerraarmr
     */
    public void prepareNuevo() {
        tipoReporte = null;
        division = null;
        comunidad = null;
        centroPoblado = null;
        tipoTecnologia = null;
        departamento = null;
        ciudad = null;
        nodo = null;
        atributo = null;
        valorAtributo = null;
        atributosVisible = false;
        puedeExportarXls = false;
        puedeExportarTxt = false;
        puedeExportarCsv = false;
        puedeInhabilitarHhppp = false;
        fechaFinInhabilitarHhpp = null;
        fechaInicioInhabilitarHhpp = null;
        puedeProcesar = true;
        puedeVerificar = false;
        procesandoSolicitud = false;
        procesarReporte = null;
        msgProcesamiento = "";
        dataCsvTxt = null;
        procesarReporteBtn = false;
    }

    /*
     * Recarga la pagina para que se limpien los campos
     */
    public void reload() throws ApplicationException {
        try {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            FacesUtil.navegarAPagina(((HttpServletRequest) ec.getRequest()).getRequestURI());
        } catch (ApplicationException e) {
            throw new ApplicationException("Error al momento de recargar: " + e.getMessage(), e);
        }
    }

    /**
     * Volver al menú principal de visitas técnicas. Si no está procesando una
     * solicitud en background entonces llama a prepareNuevo e inicializa todos
     * las variables.
     *
     * @author becerraarmr
     * @throws IOException si hay algún error al intentar volver.
     */
    public void volverMenu() throws IOException {
        if (!procesandoSolicitud) {
            prepareNuevo();
        }
        FacesContext.getCurrentInstance().getExternalContext().
                redirect("/Visitas_Tecnicas/faces/Gestion.jsp");
    }

    /**
     * Exportar el reporte a Excel
     * <p>
     * Se prepara para reportar al archivo realizado a quien realiza la
     * petición.
     *
     * @author becerraarmr
     */
    public void exportarXls() {
        ServletOutputStream outputStream = null;
        try {
            java.util.Date fecha = new Date();
            SimpleDateFormat formato
                    = new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss-a");
            String fec = formato.format(fecha);
            String filename;
            if (puedeInhabilitarHhppp) {
                filename = tipoReporte + "_" + "INHABILITAR_" + fec + ".xlsx";
            } else {
                filename = (tipoReporte.equalsIgnoreCase(REPORTE_DETALLADO))
                        ? tipoReporte + "_" + mostrarNombreAtributo(atributo) + "_" + fec + ".xlsx" : tipoReporte + "_" + fec + ".xlsx";
            }
            
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletResponse response1 = (HttpServletResponse) context.getExternalContext().getResponse();
            response1.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response1.setHeader("Content-Disposition", "filename=" + filename);
            outputStream = response1.getOutputStream();
            outputStream.write(converterFileToByte(new File("tmp", nameFileTmp)));
            outputStream.flush();
            outputStream.close();
            context.responseComplete();
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error en exportarXls. ", e, LOGGER);
            throw new Error(e.getMessage());
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en exportarXls. ", e, LOGGER);
            throw new Error(e.getMessage());
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    FacesUtil.mostrarMensajeError("Error en exportarXls. ", e, LOGGER);
                    throw new Error(e.getMessage());
                } catch (Exception e) {
                    FacesUtil.mostrarMensajeError("Error en exportarXls. ", e, LOGGER);
                    throw new Error(e.getMessage());
                }
            }
        }
    }

    /**
     * Solita exportar el reporte a TXT
     * <p>
     * Solicita al método general de exportar Csv y Txt que lo exporte en CSV.
     *
     * @author becerraarmr
     */
    public void exportarTxt() {
        try {
            exportarTxtCsv("txt");
            notificar();
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en exportarTxt. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en exportarTxt. ", e, LOGGER);
        }
    }

    /**
     * Convertir un archivo a byte
     * <p>
     * Convierte un archivo a su representación en Bytes.
     *
     * @author becerraarmr
     *
     * @param fileXls archivo a convertir
     *
     * @return un vector de bytes
     */
    private byte[] converterFileToByte(File fileXls) throws ApplicationException {
        try {
            byte[] bytesArray = new byte[(int) fileXls.length()];
            FileInputStream fis = new FileInputStream(fileXls);
            fis.read(bytesArray); //read file into bytes[]
            fis.close();
            return bytesArray;
        } catch (IOException e) {
            LOGGER.error("Error en converterFileToByte. " + e.getMessage(), e);
            throw new ApplicationException(e);
        } catch (Exception e) {
            LOGGER.error("Error en converterFileToByte. " + e.getMessage(), e);
            throw new ApplicationException(e);
        }
    }

    /**
     * Exporta el reporte a CSC o TXT
     * <p>
     * Según la petición del usuario se exporta el archivo del reporte a su
     * correspondiente CSVo TXT.
     *
     * @author becerraarmr
     *
     * @param tipoArchivo CSV o TXT
     */
    private void exportarTxtCsv(String tipoArchivo) throws ApplicationException {
        try {
            java.util.Date fecha = new Date();
            SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss-a");
            String fec = formato.format(fecha);
            String filename;
            if (puedeInhabilitarHhppp) {
                filename = tipoReporte + "_" + "INHABILITAR_" + fec + "." + tipoArchivo;
            } else {
                filename = (tipoReporte.equalsIgnoreCase(REPORTE_DETALLADO))
                        ? tipoReporte + "_" + mostrarNombreAtributo(atributo) + "_" + fec + "." + tipoArchivo
                        : tipoReporte + "_" + fec + "." + tipoArchivo;
            }
            
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletResponse response1 = (HttpServletResponse) context.getExternalContext().getResponse();
            response1.setContentType("application/force.download");
            response1.setHeader("Content-Disposition", "filename=" + filename);
            byte[] bytesArray = getDataCsvTxt().toString().getBytes("UTF-8");
            response1.getOutputStream().write(bytesArray);
            response1.getOutputStream().flush();
            response1.getOutputStream().close();
            context.responseComplete();
            LOGGER.info(">> Creacion de archivo completada");
        } catch (IOException e) {
            LOGGER.error(">>> 1 Error en exportarTxtCsv. " + e.getMessage(), e);
            throw new ApplicationException("Error en exportarTxtCsv. ", e);
        } catch (Exception e) {
            LOGGER.error(">>> 2 Error en exportarTxtCsv. " + e.getMessage(), e);
            throw new ApplicationException("Error en exportarTxtCsv. ", e);
        }
    }

    /**
     * Convierte un archivo de excel a CSV o TXT
     * <p>
     * Lee el archivo del reporte y lo convierte a byte.
     *
     * @author becerraarmr
     * @return vector de bytes que representan el archivo
     */
    private byte[] converterXlsToCsv(File file) {
        try {
            // For storing data into CSV files
            StringBuilder data = new StringBuilder();
            // Get the workbook object for XLS file
            FileInputStream inputStream = new FileInputStream(file);
            
            Workbook workbook = new XSSFWorkbook(inputStream);

            // Get first sheet from the workbook
            int ns = 1;
            Sheet sheet = workbook.getSheet(ns + "");
            
            while (sheet != null) {
                Cell cell;
                Row row;

                // Iterate through each rows from first sheet
                Iterator<Row> rowIterator = sheet.iterator();
                while (rowIterator.hasNext()) {
                    if (ns > 1) {
                        rowIterator.next();
                        rowIterator.next();
                        rowIterator.next();
                    }
                    row = rowIterator.next();
                    // For each row, iterate through each columns
                    Iterator<Cell> cellIterator = row.cellIterator();
                    while (cellIterator.hasNext()) {
                        cell = cellIterator.next();
                        
                        switch (cell.getCellType()) {
                            case Cell.CELL_TYPE_BOOLEAN:
                                data.append(cell.getBooleanCellValue());
                                break;
                            
                            case Cell.CELL_TYPE_NUMERIC:
                                data.append(cell.getNumericCellValue());
                                break;
                            
                            case Cell.CELL_TYPE_STRING:
                                data.append(cell.getStringCellValue());
                                break;
                            
                            case Cell.CELL_TYPE_BLANK:
                                data.append("");
                                break;
                            
                            default:
                                data.append(cell);
                        }
                        if (cellIterator.hasNext()) {
                            data.append(',');
                        }
                    }
                    data.append('\n');
                }
                ns++;
                sheet = workbook.getSheet(ns + "");
            }
            return data.toString().getBytes("UTF-8");
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error en converterXlsToCsv. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en converterXlsToCsv. ", e, LOGGER);
        }
        return null;
    }

    /**
     * Solicitar exportar a CSV
     * <p>
     * Solicita al método exportarTxtCsv que exporte el reporte a CSV
     *
     * @author becerraarmr
     *
     */
    public void exportarCsv() {
        try {
            exportarTxtCsv("csv");
            notificar();
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en exportarCsv. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en exportarCsv. ", e, LOGGER);
        }
        
    }

    /**
     * Cambiar el valor del atributo tipo reporte
     * <p>
     * Actualiza la variable tipo reporte y la atributos visibles con el fin de
     * notificar la acción realizada.
     *
     * @author becerraarmr
     *
     * @param event
     */
    public void tipoReporteChange(ValueChangeEvent event) {
        tipoReporte = (String) event.getNewValue();
        atributosVisible = tipoReporte.equalsIgnoreCase(REPORTE_DETALLADO);
        atributo = null;
        valorAtributo = null;
    }

    /**
     * Actualizar el valor del atributo tipotecnologia
     * <p>
     * Al momento de seleccionar el tipo de tecnología se debe actualizar la
     * variable.
     *
     * @author becerraarmr
     * @param event
     */
    public void tiposTecnologiasChange(ValueChangeEvent event) {
        tipoTecnologia = (String) event.getNewValue();
    }

    /**
     * Actualizar el valor del atributo tipotecnologia
     * <p>
     * Al momento de seleccionar el tipo de tecnología se debe actualizar la
     * variable.
     *
     * @author becerraarmr
     * @param event
     */
    public void atributosChange(ValueChangeEvent event) {
        atributo = (String) event.getNewValue();
        valorAtributo = null;
    }

    /**
     * Actualizar el valor del atributo división
     * <p>
     * Al momento de seleccionar la división se debe actualizar y limpiar
     * comunidad, departamento, ciudad, centroPoblado y nodo.
     *
     * @author becerraarmr
     * @param event
     */
    public void divisionChange(ValueChangeEvent event) {
        division = (String) event.getNewValue();
        comunidad = null;
        departamento = null;
        ciudad = null;
        centroPoblado = null;
        nodo = null;
        
    }

    /**
     * Actualizar los campos de ubicación.
     *
     * Actauliza el departamento y la ciduad.
     *
     * @param event acción
     * @throws ApplicationException si hay algún error en la busqueda.
     */
    public void comunidadChange(ValueChangeEvent event)
            throws ApplicationException {
        comunidad = (String) event.getNewValue();
        departamento = null;
        ciudad = null;
        centroPoblado = null;
        nodo = null;
        GeograficoPoliticoMgl geograficoPoliticoMgl
                = ejbGeograficoPolitico.findCityByComundidad(comunidad);
        if (geograficoPoliticoMgl != null) {
            departamento = geograficoPoliticoMgl.getGeoGpoId() + "";
            ciudad = geograficoPoliticoMgl.getGpoId() + "";
        }
    }

    /**
     * Actualizar division comunidad ciudad centro poblado y nodo cuando cambien
     * de departamento
     *
     * @author becerraarmr
     * @param event acción
     */
    public void departamentosChange(ValueChangeEvent event) {
        departamento = (String) event.getNewValue();
        division = null;
        comunidad = null;
        ciudad = null;
        centroPoblado = null;
        nodo = null;
    }

    /**
     * Actualizar el centro poblado y el nodo cuando cambie la ciudad.
     *
     * @author becerraarmr
     * @param event acción
     */
    public void ciudadesChange(ValueChangeEvent event) {
        ciudad = (String) event.getNewValue();
        centroPoblado = null;
        nodo = null;
        ciudadMultiOrigen = false;
    }

    /**
     * Actualizar el nodo cuadno cambien de centro poblado
     *
     * @author becerraarmr
     * @param event acción
     */
    public void centroPobladoChange(ValueChangeEvent event) {
        centroPoblado = (String) event.getNewValue();
        nodo = null;
    }

    /**
     * Buscar los nodos que corresponden al filtro.
     * <p>
     * Solicita la busqueda de los nodos que correspondan con la ciudad/centro
     * poblado y tipo de tecnologia
     *
     * @author becerraarmr
     *
     * @return un SelectItem[] con la información encontrada
     */
    public SelectItem[] getItemsNodos() {
        try {
            List<NodoMgl> lista = new ArrayList<>();
            //Verificar conexion con el EJB y que la ciudad y el tipo de tecnología 
            //tengan información
            if (ejbNodo != null && getCiudad() != null && tipoTecnologia != null) {
                CmtBasicaMgl tipoTecnologiaBD = getCmtBasicaMgl(tipoTecnologia);
                if (centroPoblado != null) {
                    if (!centroPoblado.isEmpty()) {
                        BigDecimal centroPobladoBD = new BigDecimal(centroPoblado);
                        lista = ejbNodo.
                                findNodosCentroPobladoAndTipoTecnologia(centroPobladoBD, tipoTecnologiaBD);
                    }
                } else if (!ciudad.isEmpty()) {
                    BigDecimal aux = new BigDecimal(getCiudad());
                    lista = ejbNodo.
                            findNodosCiudadAndTipoTecnologia(aux, tipoTecnologiaBD);
                }
            }
            this.itemsNodosList = ProcesoMasivoUtil.getSelectItems(lista, true);
            return this.itemsNodosList;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en getItemsNodos. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en getItemsNodos. ", e, LOGGER);
        }
        return null;
    }

    /**
     * Listar centros poblados
     *
     * @author becerraarmr
     * @return el listado de centros poblados.
     * @throws ApplicationException si hay algún error
     */
    public SelectItem[] getItemsCentrosPoblados()
            throws ApplicationException {
        List<GeograficoPoliticoMgl> lista = new ArrayList<>();
        if (ejbGeograficoPolitico != null && getCiudad() != null) {
            if (!ciudad.isEmpty()) {
                BigDecimal aux = new BigDecimal(getCiudad());
                lista = ejbGeograficoPolitico.findCentroPoblado(aux);
            }
        }
        this.itemsCentrosPobladosList = ProcesoMasivoUtil.getSelectItems(lista, true);
        return this.itemsCentrosPobladosList;
    }

    /**
     * Listar los departamentos
     *
     * @author becerraarmr
     * @return listado de departamentos
     * @throws ApplicationException si hay algún error en la consulta.
     */
    public SelectItem[] getItemsDepartamentos() throws ApplicationException {
        List<GeograficoPoliticoMgl> lista = new ArrayList<>();
        if (ejbGeograficoPolitico != null) {
            lista = ejbGeograficoPolitico.findDptos();
        }
        return ProcesoMasivoUtil.getSelectItems(lista, true);
    }

    /**
     * Listar los tipos de tecnología
     *
     * @author becerraarmr
     * @return el listado de tipos de tecnología
     * @throws ApplicationException si hay algún error.
     */
    public SelectItem[] getItemsTiposTecnologias()
            throws ApplicationException {
        List<CmtBasicaMgl> lista = new ArrayList<CmtBasicaMgl>();
        if (ejbBasica != null && ejbTipoBasica != null) {
            
            CmtTipoBasicaMgl tipoBasicaTipoTecnologia;
            tipoBasicaTipoTecnologia = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_TECNOLOGIA);
            
            lista = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasicaTipoTecnologia);
            if (lista != null) {
                for (CmtBasicaMgl cmtBasicaMgl : lista) {
                    if ("Sin Tecnologia".equalsIgnoreCase(cmtBasicaMgl.getNombreBasica())) {
                        lista.remove(cmtBasicaMgl);
                        break;
                    }
                }
            }
        }
        return ProcesoMasivoUtil.getSelectItems(lista, true);
    }

    /**
     * Listar las regionales
     *
     * @author becerraarmr
     * @return el listado de regionales.
     * @throws ApplicationException si haya algún error.
     */
    public SelectItem[] getItemsRegionales()
            throws ApplicationException {
        List<RrRegionales> lista = new ArrayList<RrRegionales>();
        if (ejbRegionales != null) {
        }
        return ProcesoMasivoUtil.getSelectItems(lista, true);
    }

    /**
     * Listar las comunidades
     *
     * @author becerraarmr
     * @return el listado de comunidades presentes en una regional
     * @throws ApplicationException si hay algún error
     */
    public SelectItem[] getItemsComunidades() throws ApplicationException {
        List<RrCiudades> lista = new ArrayList<RrCiudades>();
        if (ejbComunidades != null && division != null) {
            if (!division.isEmpty()) {
            }
        }
        return ProcesoMasivoUtil.getSelectItems(lista, true);
    }

    /**
     * Listar las ciudades
     *
     * @author becerraarmr
     * @return el lista de ciudades del departamento escogido
     * @throws ApplicationException si hay algún error en la consulta
     */
    public SelectItem[] getItemsCiudades() throws ApplicationException {
        List<GeograficoPoliticoMgl> lista = new ArrayList<GeograficoPoliticoMgl>();
        if (ejbGeograficoPolitico != null && departamento != null) {
            if (!departamento.isEmpty()) {
                BigDecimal idCiudad = new BigDecimal(departamento);
                lista = ejbGeograficoPolitico.findCiudades(idCiudad);
            }
        }
        this.itemsCiudades = ProcesoMasivoUtil.getSelectItems(lista, true);
        return this.itemsCiudades;
    }

    /**
     * Listar Tipo Vivienda
     *
     * @return los Tipo de Vivienda
     * @throws ApplicationException si hay algún error en la consulta
     */
    public SelectItem[] getItemsTipoVivienda() throws ApplicationException {
        List<TipoHhppMgl> lista = new ArrayList<TipoHhppMgl>();
        if (tipoHhppMglFacadeLocal != null) {
            lista = tipoHhppMglFacadeLocal.findAll();
        }
        return ProcesoMasivoUtil.getSelectItems(lista, true);
    }

    /**
     * Mostrar el listado de reportes
     * <p>
     * Muestra un listado con los dos reportes que se pueden realizar.
     *
     * @author becerraarmr
     * @return un vector SelectItem con la data relacionada.
     *
     * @throws ApplicationException
     */
    public SelectItem[] getItemstipoReportes()
            throws ApplicationException {
        SelectItem[] items = new SelectItem[2];
        items[0] = new SelectItem(REPORTE_GENERAL, "General");
        items[1] = new SelectItem(REPORTE_DETALLADO, "Detallado");
        return items;
    }

    /**
     * Mostrar el listado de atributos a seleccionar
     * <p>
     * Prepara un listado de atributos a seleccionar para realizar el reporte
     * detallado.
     *
     * @author becerraarmr
     * @return un vector SelectItem con la data relacionada.
     *
     * @throws ApplicationException si hay un error al consultar los atributos.
     */
    public SelectItem[] getItemsAtributos()
            throws ApplicationException {
        List<CmtBasicaMgl> lista = new ArrayList<CmtBasicaMgl>();
        if (ejbBasica != null && ejbTipoBasica != null) {
            CmtTipoBasicaMgl tipoBasica
                    = ejbTipoBasica.findByNombreTipoBasica(ATRIBUTOS_HHPP);
            if (tipoBasica != null) {
                lista = ejbBasica.findByTipoBasica(tipoBasica);
                if (lista != null) {
                    List<CmtBasicaMgl> listaAux = new ArrayList<CmtBasicaMgl>();
                    for (CmtBasicaMgl cmtBasicaMgl : lista) {
                        listaAux.add(cmtBasicaMgl);
                    }
                    lista = listaAux;
                }
            }
        }
        this.itemsAtributosList = ProcesoMasivoUtil.getSelectItems(lista, true);
        return this.itemsAtributosList;
    }

    /**
     * Adherir un mensaje de error
     * <p>
     * Adhiere al FacesMessage un mensaje de error con el valor del parametro.
     *
     * @author becerraarmr
     * @param msg mensaje a mostrar.
     */
    public void addErrorMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(
                FacesMessage.SEVERITY_ERROR, msg, msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    /**
     * Adherir un mensaje de satisfactorio
     * <p>
     * Adhiere al FacesMessage un mensaje satisfactorio con el valor del
     * parametro.
     *
     * @author becerraarmr
     * @param msg mensaje a mostrar.
     */
    public void addSuccessMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(
                FacesMessage.SEVERITY_INFO, msg, msg);
        FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
    }

    /**
     * Muestra el tipotecnología
     *
     * @author becerraarmr
     * @return el String
     */
    public String getTipoTecnologia() {
        return tipoTecnologia;
    }

    /**
     * Actualizar el tipo de tecnología.
     *
     * @author becerraarmr
     * @param tipoTecnologia valor a actualizar
     */
    public void setTipoTecnologia(String tipoTecnologia) {
        this.tipoTecnologia = tipoTecnologia;
    }

    /**
     * Muestra el departamento
     *
     * @author becerraarmr
     * @return el String
     */
    public String getDepartamento() {
        return departamento;
    }

    /**
     * Actualizar departamento.
     *
     * @author becerraarmr.
     * @param departamento valor a actualizar.
     */
    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    /**
     * Muestra el valor de ciudad.
     *
     * @author becerraarmr
     * @return el valor String
     */
    public String getCiudad() {
        return ciudad;
    }

    /**
     * Actualiza el valor de la ciudad.
     *
     * @author becerraarmr
     * @param ciudad valor a actualizar.
     */
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    /**
     * Muestra el valor del nodo.
     *
     * @author becerraarmr
     * @return the nodo
     */
    public String getNodo() {
        return nodo;
    }

    /**
     * Actualizar el nodo.
     *
     * @author becerraarmr.
     * @param nodo valor a actualizar.
     */
    public void setNodo(String nodo) {
        this.nodo = nodo;
    }

    /**
     * Muestra el valor del tipo de reporte.
     *
     * @author becerraarmr
     * @return el String
     */
    public String getTipoReporte() {
        return tipoReporte;
    }

    /**
     * Actualiza el valor del tipo de reporte.
     *
     * @author becerraarmr.
     * @param tipoReporte valor a actaulizar.
     */
    public void setTipoReporte(String tipoReporte) {
        this.tipoReporte = tipoReporte;
    }

    /**
     * Muestra el valor de division.
     *
     * @author becerraarmr
     * @return the division
     */
    public String getDivision() {
        return division;
    }

    /**
     * Actualizar el valor de la division
     *
     * @author becerraarmr
     * @param division a actualizar
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Muestra el valor de la comunidad
     *
     * @author becerraarmr
     * @return el String
     */
    public String getComunidad() {
        return comunidad;
    }

    /**
     * Actualizar la comunidad
     *
     * @author becerraarmr
     * @param comunidad a actualizar.
     */
    public void setComunidad(String comunidad) {
        this.comunidad = comunidad;
    }

    /**
     * Muestra el valor del centroPoblado
     *
     * @author becerraarmr
     * @return el valor del centroPoblado
     */
    public String getCentroPoblado() {
        return centroPoblado;
    }

    /**
     * Actualizar centroPoblado.
     *
     * @author becerraarmr.
     * @param centroPoblado vaor a actualizar.
     */
    public void setCentroPoblado(String centroPoblado) {
        this.centroPoblado = centroPoblado;
    }

    /**
     * Muestra el valor de valorAtributo
     *
     * @author becerraarmr
     * @return the valorAtributo
     */
    public Object getValorAtributo() {
        return valorAtributo;
    }

    /**
     * Actualiza valorAtributo.
     *
     * @author becerraarmr
     * @param valorAtributo the valorAtributo to set
     */
    public void setValorAtributo(Object valorAtributo) {
        this.valorAtributo = valorAtributo;
    }

    /**
     * Ver si atributosVisibles
     *
     * @author becerraarmr
     * @return the atributosVisible
     */
    public boolean isAtributosVisible() {
        return atributosVisible;
    }

    /**
     * Actualizar el valor dela atributosVisible.
     *
     * @author becerraarmr
     * @param atributosVisible the atributosVisible to set
     */
    public void setAtributosVisible(boolean atributosVisible) {
        this.atributosVisible = atributosVisible;
    }

    /**
     * Muestra el atributo.
     *
     * @author becerraarmr
     * @return el String del atributo.
     */
    public String getAtributo() {
        return atributo;
    }

    /**
     * Actualizar el valor del atributo.
     *
     * @param atributo nuevo atributo.
     */
    public void setAtributo(String atributo) {
        this.atributo = atributo;
    }

    /**
     * Buscar el valor del geográfico politico Busca el valor del objeto
     * geográfico político correspondiente según la id.
     *
     * @author becerraarmr
     * @param geo_id
     *
     * @return el objeto relacionado.
     */
    public GeograficoPoliticoMgl getGeograficoMgl(String geo_id) {
        BigDecimal valor = ProcesoMasivoUtil.getBigDecimal(geo_id);
        if (valor != null) {
            try {
                GeograficoPoliticoMgl valorCiudad
                        = ejbGeograficoPolitico.findById(valor);
                if (valorCiudad != null) {
                    return valorCiudad;
                }
            } catch (ApplicationException e) {
                FacesUtil.mostrarMensajeError("Error en getGeograficoMgl. ", e, LOGGER);
            } catch (Exception e) {
                FacesUtil.mostrarMensajeError("Error en getGeograficoMgl. ", e, LOGGER);
            }
        }
        return null;
    }

    /**
     * Buscar la básica
     * <p>
     * Busca el objeto CmtBasicaMgl correpondiente según el parámetro insertado.
     *
     * @author becerraarmr
     * @param idBasica
     *
     * @return
     */
    public CmtBasicaMgl getCmtBasicaMgl(String idBasica) {
        BigDecimal valor = ProcesoMasivoUtil.getBigDecimal(idBasica);
        if (valor != null) {
            try {
                CmtBasicaMgl basica = ejbBasica.findById(valor);
                if (basica != null) {
                    return basica;
                }
            } catch (ApplicationException e) {
                LOGGER.error("Error en getCmtBasicaMgl. " + e.getMessage(), e);
            } catch (Exception e) {
                LOGGER.error("Error en getCmtBasicaMgl. " + e.getMessage(), e);
            }
        }
        return null;
    }

    /**
     * Consigue el Geografico de un Hhpp.
     *
     * @author becerraarmr.
     * @param hhppMgl hhpp al que se le va a buscar el valor.
     * @return null o el Geografico correspondiente.
     */
    public Geografico getGeografico(HhppMgl hhppMgl) {
        if (hhppMgl != null) {
            try {
                Geografico barrio = ejbGeografico.findBarrioHhpp(hhppMgl.getHhpId());
                if (barrio != null) {
                    return barrio;
                }
            } catch (ApplicationException e) {
                LOGGER.error("Error en getGeografico. " + e.getMessage(), e);
            } catch (Exception e) {
                LOGGER.error("Error en getGeografico. " + e.getMessage(), e);
            }
        }
        return null;
    }

    /**
     * Mostrar la DireccionMgl del Hhpp
     *
     * @author becerraarmr
     * @param hhppMgl hhpp para verficar el valor.
     * @return la DireccionMGl o el null
     */
    public DireccionMgl getDireccionMgl(HhppMgl hhppMgl) {
        if (hhppMgl != null) {
            if (hhppMgl.getDirId() != null) {
                try {
                    DireccionMgl basica = new DireccionMgl();
                    basica.setDirId(hhppMgl.getDirId());
                    basica = ejbDireccion.findById(basica);
                    if (basica != null) {
                        return basica;
                    }
                } catch (ApplicationException e) {
                    LOGGER.error("Error en getDireccionMgl. " + e.getMessage(), e);
                } catch (Exception e) {
                    LOGGER.error("Error en getDireccionMgl. " + e.getMessage(), e);
                }
            }
        }
        return null;
    }

    /**
     * Buscar el nodo
     * <p>
     * Busca el objeto nodo según el parámetro.
     *
     * @author becerraarmr
     *
     * @param idNodo id del nodo a buscar.
     *
     * @return un objeto NodoMgl
     */
    public NodoMgl getNodoMgl(String idNodo) {
        BigDecimal valor = ProcesoMasivoUtil.getBigDecimal(idNodo);
        if (valor != null) {
            try {
                NodoMgl aux = ejbNodo.findById(valor);
                if (aux != null) {
                    return aux;
                }
            } catch (ApplicationException e) {
                LOGGER.error("Error en getNodoMgl. " + e.getMessage(), e);
            } catch (Exception e) {
                LOGGER.error("Error en getNodoMgl. " + e.getMessage(), e);
            }
        }
        return null;
    }

    /**
     * Ver si puede no exportar a XLS
     *
     * @author becerraarmr
     * @return true o false
     */
    public boolean isPuedeExportarXls() {
        return puedeExportarXls;
    }

    /**
     * Ver si puede exportar a CSV
     *
     * @author becerraarmr
     * @return true o false
     */
    public boolean isPuedeExportarCsv() {
        return puedeExportarCsv;
    }

    /**
     * Ver si puede exportar a TXT.
     *
     * @author becerraarmr.
     * @return true o false.
     */
    public boolean isPuedeExportarTxt() {
        return puedeExportarTxt;
    }

    /**
     * Ver si está procesando solicitud.
     *
     * @author becerraarmr.
     * @return true o false.
     */
    public boolean isProcesandoSolicitud() {
        return procesandoSolicitud;
    }

    /**
     * Actualizar el estado procesandoSolicitud
     *
     * @author becerraarmr
     * @param procesandoSolicitud
     */
    public void setProcesandoSolicitud(boolean procesandoSolicitud) {
        this.procesandoSolicitud = procesandoSolicitud;
    }

    /**
     * Contar la data que va a procesar
     * <p>
     * Se solicita a la base de datos la cantidad de registros que se van a
     * procesar según los filtros.
     *
     * @author becerraarmr
     *
     * @return un entero con el valor
     */
    public int countDataReporte() throws ApplicationException {
        int result = 0;
        try {
            
            BigDecimal idBasicaTecnologia = ProcesoMasivoUtil.getBigDecimal(this.getTipoTecnologia());
            BigDecimal idGpoCiudad = ProcesoMasivoUtil.getBigDecimal(this.getCiudad());
            BigDecimal idGpoCentroPoblado = ProcesoMasivoUtil.getBigDecimal(this.getCentroPoblado());
            BigDecimal idNodo = ProcesoMasivoUtil.getBigDecimal(this.getNodo());
            BigDecimal etiqueta = ProcesoMasivoUtil.getBigDecimal(this.getEtiquetaSeleccionada());
            
            CmtBasicaMgl atributoBasica = getCmtBasicaMgl(atributo);
            String nameAtributo = atributoBasica != null ? atributoBasica.getNombreBasica() : null;

            //JDHT 
            BigDecimal identificadorCcmmMgl = BigDecimal.ZERO;
            BigDecimal identificadorCcmmRr = BigDecimal.ZERO;
            if (idCcmmMgl != null && !idCcmmMgl.isEmpty()) {
                identificadorCcmmMgl = new BigDecimal(idCcmmMgl);
            } else {
                if (idCcmmRr != null && !idCcmmRr.isEmpty()) {
                    identificadorCcmmRr = new BigDecimal(idCcmmRr);
                }
            }
            
            result = ejbHhpp.countHhpp(idGpoCiudad, idGpoCentroPoblado, idNodo,
                    idBasicaTecnologia, nameAtributo, valorAtributo,
                    fechaInicioInhabilitarHhpp, fechaFinInhabilitarHhpp, null,
                    puedeInhabilitarHhppp, creadoXFicha, etiqueta, identificadorCcmmMgl, identificadorCcmmRr);
            
        } catch (ApplicationException e) {
            LOGGER.error("Error en countDataReporte. " + e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("Error en countDataReporte. " + e.getMessage(), e);
            throw new ApplicationException(e);
        }
        return result;
    }

    /**
     * Solicitar un Hhpp según los datos del filtro.
     * <p>
     * Se solicita a la base de datos los Hhpp que cumplen la condición con el:
     * tipo de tecnología, ciudad, nodo y el rango a busscar
     *
     * @author becerraarmr
     *
     * @param rango Arreglo con el valor inicial y final del rango
     *
     * @return un Listado con los Hhpp que cumplen las condiciones
     *
     * @see HhppMgl
     */
    public List<HhppMgl> findDataReporte(int[] rango) throws ApplicationException {
        List<HhppMgl> result = null;
        try {
            BigDecimal idBasicaTecnologia = ProcesoMasivoUtil.getBigDecimal(this.getTipoTecnologia());
            BigDecimal idGpoCiudad = ProcesoMasivoUtil.getBigDecimal(this.getCiudad());
            BigDecimal idGpoCentroPoblado = ProcesoMasivoUtil.getBigDecimal(this.getCentroPoblado());
            BigDecimal idNodo = ProcesoMasivoUtil.getBigDecimal(this.getNodo());
            CmtBasicaMgl atributoBasica = getCmtBasicaMgl(this.getAtributo());
            BigDecimal etiqueta = ProcesoMasivoUtil.getBigDecimal(this.getEtiquetaSeleccionada());

            //JDHT 
            BigDecimal identificadorCcmmMgl = BigDecimal.ZERO;
            BigDecimal identificadorCcmmRr = BigDecimal.ZERO;
            if (idCcmmMgl != null && !idCcmmMgl.isEmpty()) {
                identificadorCcmmMgl = new BigDecimal(idCcmmMgl);
            } else {
                if (idCcmmRr != null && !idCcmmRr.isEmpty()) {
                    identificadorCcmmRr = new BigDecimal(idCcmmRr);
                }
            }
            
            String nameAtributo = atributoBasica != null ? atributoBasica.getNombreBasica() : null;
            
            result = ejbHhpp.findHhpp(idGpoCiudad, idGpoCentroPoblado, idNodo,
                    idBasicaTecnologia, nameAtributo, valorAtributo,
                    fechaInicioInhabilitarHhpp, fechaFinInhabilitarHhpp,
                    rango, null, puedeInhabilitarHhppp, creadoXFicha, etiqueta,
                    identificadorCcmmMgl, identificadorCcmmRr);
            
            
            LOGGER.info("Cantidad de registros : " + result.size());
        } catch (ApplicationException e) {
            LOGGER.error("Error en findDataReporte. " + e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("Error en findDataReporte. " + e.getMessage(), e);
            throw new ApplicationException("Error en findDataReporte. ", e);
        }
        return result;
    }

    /**
     * @return the mostrarPanelMensaje
     */
    public boolean isMostrarPanelMensaje() {
        return mostrarPanelMensaje;
    }

    /**
     * Buscar los nodos Busca los nodos que hacen parte de la ciudad o del
     * codnodo
     *
     * @author becerraarmr
     *
     * @return un lista con los NodoMgl correspondientes
     *
     * @see NodoMgl entidad que identifica al nodo
     */
    public List<NodoMgl> findNodos() {
        try {
            BigDecimal gpoId = ProcesoMasivoUtil.getBigDecimal(getCiudad());
            if (tipoTecnologia != null && gpoId != null) {
                CmtBasicaMgl basTipoTecnologia = getCmtBasicaMgl(tipoTecnologia);
                return ejbNodo.findNodos(basTipoTecnologia, gpoId, nodo);
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en findNodos, al buscar la lista de nodos correspondientes. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en findNodos, al buscar la lista de nodos correspondientes. ", e, LOGGER);
        }
        return null;
    }

    /**
     * Muestra el mensaje de procesamiento
     *
     * @author becerraarmr
     * @return un String
     */
    public String getMsgProcesamiento() {
        return msgProcesamiento;
    }

    /**
     * Actualizar el msgProcesamiento.
     *
     * @author becerraarmr
     * @param msgProcesamiento actaulizar el valor.
     */
    public void setMsgProcesamiento(String msgProcesamiento) {
        this.msgProcesamiento = msgProcesamiento;
    }

    /**
     * Ver si puede verificar
     *
     * @author becerraarmr
     * @return true o false
     */
    public boolean isPuedeVerificar() {
        return puedeVerificar;
    }

    /**
     * Ver si puede procesar
     *
     * @author becerraarmr
     * @return true o false
     */
    public boolean isPuedeProcesar() {
        return puedeProcesar;
    }

    /**
     * Actualizar el nameFileTmp
     *
     * @author becerraarmr
     * @param nameFileTmp nombre del archivo temporal
     */
    public void setNameFileTmp(String nameFileTmp) {
        this.nameFileTmp = nameFileTmp;
    }

    /**
     * Mostrar el valor para CSV o TXT
     *
     * @author becerraarmr
     * @return el StringBuilder con el valor.
     */
    public StringBuilder getDataCsvTxt() {
        if (dataCsvTxt == null) {
            dataCsvTxt = new StringBuilder();
        }
        return dataCsvTxt;
    }

    /**
     * Buscar los registrados encontrados
     *
     * Se muestra los registros encontrados como un mensaje.
     *
     * @author becerraarmr
     * @return el mensaje de los registros encontrdos
     */
    public String getRegistrosEncontrados() {
        if (procesarReporte != null) {
            registrosEncontrados
                    = procesarReporte.getRegistrosEncontrados();
            if (registrosEncontrados != -1) {
                return "Registros encontrados: " + registrosEncontrados;
            }
        }
        return "";
    }

    /**
     * Mostrar el nombre del atributo.
     *
     * Se muestra el nombre del atributo que corresponde al idBasica que llega.
     *
     * @author becerraarmr
     * @param idBasica id de la básica que está en Cmt_Basica
     * @return "NA" si no hay dato o el valor correspondiente al idBasica
     * ingresado
     */
    public String mostrarNombreAtributo(String idBasica) {
        if (idBasica != null) {
            if (!idBasica.isEmpty()) {
                CmtBasicaMgl basica = getCmtBasicaMgl(idBasica);
                if (basica != null) {
                    return basica.getNombreBasica();
                }
            }
        }
        return "NA";
    }

    /**
     * Actualizar el valor del atributo.
     *
     * Actualiza el valor del atributo correspondiente.
     *
     * @author becerraarmr
     *
     * @param puedeExportarXls actualizar
     */
    public void setPuedeExportarXls(boolean puedeExportarXls) {
        this.puedeExportarXls = puedeExportarXls;
    }

    /**
     * Muestra el usuario logeado.
     *
     * @return usuarios
     */
    public UsuariosServicesDTO getUsuarioLogin() {
        return usuarioLogin;
    }

    /**
     * Muestra el estado de la ciudad Multi-Origen
     *
     * @author becerraarmr
     * @return the ciudadMultiOrigen
     */
    public boolean isCiudadMultiOrigen() {
        return ciudadMultiOrigen;
    }

    /**
     * Cambiar estado de ciudad multiorigen
     *
     * @author becerraarmr
     * @param ciudadMultiOrigen
     */
    public void setCiudadMultiOrigen(boolean ciudadMultiOrigen) {
        this.ciudadMultiOrigen = ciudadMultiOrigen;
        
    }

    /**
     * Establece si puede o no inhabilitar Hhhpp
     *
     * @author becerraarmr
     * @return true o false
     */
    public boolean isPuedeInhabilitarHhppp() {
        return puedeInhabilitarHhppp;
    }

    /**
     * Muesta la fecha Inicial para inhabilitar hhpp
     *
     * @author becerraarmr
     * @return una fecha en tipo Date
     */
    public Date getFechaInicioInhabilitarHhpp() {
        return fechaInicioInhabilitarHhpp;
    }

    /**
     * Cambia la fecha de inhabilitación Inicial.
     *
     * @author becerraarmr
     * @param fechaInicioInhabilitarHhpp Fecha a cambiar
     */
    public void setFechaInicioInhabilitarHhpp(Date fechaInicioInhabilitarHhpp) {
        this.fechaInicioInhabilitarHhpp = fechaInicioInhabilitarHhpp;
    }

    /**
     * Muesta la fecha final para inhabilitar hhpp
     *
     * @author becerraarmr
     * @return una fecha en tipo Date
     */
    public Date getFechaFinInhabilitarHhpp() {
        return fechaFinInhabilitarHhpp;
    }

    /**
     * Cambia la fecha de inhabilitación final.
     *
     * @author becerraarmr
     * @param fechaFinInhabilitarHhpp Fecha a cambiar
     */
    public void setFechaFinInhabilitarHhpp(Date fechaFinInhabilitarHhpp) {
        this.fechaFinInhabilitarHhpp = fechaFinInhabilitarHhpp;
    }

    /**
     * Mostrar la Direccion detallada del Hhpp
     *
     * @author bocanegra Vm
     * @param hhppMgl hhpp para verficar el valor.
     * @return la Direccion detallada o el null
     */
    public CmtDireccionDetalladaMgl getDetallaadMgl(HhppMgl hhppMgl) {
        if (hhppMgl != null) {
            if (hhppMgl.getDirId() != null) {
                BigDecimal subDireccion = null;
                if (hhppMgl.getSubDireccionObj() != null) {
                    subDireccion = hhppMgl.getSubDireccionObj().getSdiId();
                }
                try {
                    List<CmtDireccionDetalladaMgl> detalladaMgl;
                    detalladaMgl = detalleMglFacadeLocal.findDireccionDetallaByDirIdSdirId(hhppMgl.getDirId(), subDireccion);
                    if (detalladaMgl.size() > 0) {
                        return detalladaMgl.get(0);
                    }
                } catch (ApplicationException e) {
                    LOGGER.error("Error en getDetallaadMgl. " + e.getMessage(), e);
                } catch (Exception e) {
                    LOGGER.error("Error en getDetallaadMgl. " + e.getMessage(), e);
                }
                
            }
        }
        return null;
    }

    /**
     * Listar etiquetas
     *
     * @author @cardenaslb
     * @return el listado de etiquetas.
     * @throws ApplicationException si hay algún error
     */
    public SelectItem[] getItemsEtiquetas()
            throws ApplicationException {
        List<MarcasMgl> lista = new ArrayList<>();
        lista = marcasMglFacadeLocal.findAll();
        this.itemsEtiquetasList = ProcesoMasivoUtil.getSelectItems(lista, true);
        return this.itemsEtiquetasList;
    }

    /**
     * Buscar la marca segun el parametro
     * <p>
     * Busca el objeto nodo según el parámetro.
     *
     * @author @cardenaslb
     *
     * @param idMarca id de la marcar.
     *
     * @return un objeto NodoMgl
     */
    public MarcasMgl getMarcasMgl(String idMarcasMgl) {
        BigDecimal valor = ProcesoMasivoUtil.getBigDecimal(idMarcasMgl);
        if (valor != null) {
            try {
                MarcasMgl aux = marcasMglFacadeLocal.findById(valor);
                if (aux != null) {
                    return aux;
                }
            } catch (ApplicationException e) {
                LOGGER.error("Error en getNodoMgl. " + e.getMessage(), e);
            } catch (Exception e) {
                LOGGER.error("Error en getNodoMgl. " + e.getMessage(), e);
            }
        }
        return null;
    }
    
    public boolean isProcesarReporteBtn() {
        return procesarReporteBtn;
    }
    
    public void setProcesarReporteBtn(boolean procesarReporteBtn) {
        this.procesarReporteBtn = procesarReporteBtn;
    }
    
    public boolean isCreadoXFicha() {
        return creadoXFicha;
    }
    
    public void setCreadoXFicha(boolean creadoXFicha) {
        this.creadoXFicha = creadoXFicha;
    }
    
    public String getEtiquetaSeleccionada() {
        return etiquetaSeleccionada;
    }
    
    public void setEtiquetaSeleccionada(String etiquetaSeleccionada) {
        this.etiquetaSeleccionada = etiquetaSeleccionada;
    }
    
    public String getIdCcmmMgl() {
        return idCcmmMgl;
    }
    
    public void setIdCcmmMgl(String idCcmmMgl) {
        this.idCcmmMgl = idCcmmMgl;
    }
    
    public String getIdCcmmRr() {
        return idCcmmRr;
    }
    
    public void setIdCcmmRr(String idCcmmRr) {
        this.idCcmmRr = idCcmmRr;
    }
    
    public String getEstiloObligatorio() {
        return estiloObligatorio;
    }
    
    public void setEstiloObligatorio(String estiloObligatorio) {
        this.estiloObligatorio = estiloObligatorio;
    }

    // Validar Opciones por Rol
    public boolean validarOpcionGenerarReporte() {
        return validarEdicionRol(BTNOHHPPS);
    }
    
    public boolean validarOpcionGenerarReporteInhabilitar() {
        return validarEdicionRol(BTNINHRPT);
    }

    //funcion General
    private boolean validarEdicionRol(String formulario) {
        try {
            boolean tieneRolPermitido = ValidacionUtil.validarVisualizacion(formulario, ValidacionUtil.OPC_EDICION, cmtOpcionesRolMglFacade, securityLogin);
            return tieneRolPermitido;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION + e.getMessage(), e, LOGGER);
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al OrdenTrabajoGestionarBean. " + e.getMessage(), e, LOGGER);
        }
        return false;
    }

    private HashMap<String, String> obtenerFiltros() throws ApplicationException {
        final HashMap<String, String> filtrosAplicados = new HashMap<>();
        final HashMap<String, String> filtrosTraducidos = new HashMap<>();

        if (StringUtils.isNotBlank(tipoTecnologia)) {
            filtrosAplicados.put("IDTIPOTECNOLOGIA", tipoTecnologia);

            for (SelectItem selectItem: itemsTiposTecnologiasList){
                if (selectItem.getValue().equals(tipoTecnologia)){
                    filtrosTraducidos.put("TIPO TECNOLOGIA", selectItem.getLabel());
                    break;
                }
            }
        }

        if (StringUtils.isNotBlank(centroPoblado)) {
            filtrosAplicados.put("GPOIDCENTROPOBLADO", centroPoblado);

            for (SelectItem selectItem: itemsDepartamentosList){
                if (selectItem.getValue().equals(departamento)){
                    filtrosTraducidos.put("DEPARTAMENTO", selectItem.getLabel());
                    break;
                }
            }

            for (SelectItem selectItem: itemsCiudades){
                if (selectItem.getValue().equals(ciudad)){
                    filtrosTraducidos.put("CIUDAD", selectItem.getLabel());
                    break;
                }
            }

            for (SelectItem selectItem: itemsCentrosPobladosList){
                if (selectItem.getValue().equals(centroPoblado)){
                    filtrosTraducidos.put("CENTRO POBLADO", selectItem.getLabel());
                    break;
                }
            }
        }

        if (StringUtils.isNotBlank(nodo)) {
            filtrosAplicados.put("NODO_ID", nodo);

            for (SelectItem selectItem: itemsNodosList){
                if (selectItem.getValue().toString().equals(nodo)){
                    filtrosTraducidos.put("NODO", selectItem.getLabel());
                    break;
                }
            }
        }

        if (StringUtils.isNotBlank(etiquetaSeleccionada)) {
            filtrosAplicados.put("ETIQUETAID", etiquetaSeleccionada);

            for (SelectItem selectItem: itemsEtiquetasList){
                if (selectItem.getValue().equals(etiquetaSeleccionada)){
                    filtrosTraducidos.put("ETIQUETA", selectItem.getLabel());
                    break;
                }
            }
        }

        if (creadoXFicha) {
            filtrosAplicados.put("ORIGEN", "IS NOT NULL");
            filtrosTraducidos.put("ORIGEN", "SI");
        }

        if (StringUtils.isNotBlank(idCcmmMgl)) {
            filtrosAplicados.put("ID_CCMM_MGL", idCcmmMgl);

            filtrosTraducidos.put("ID_CCMM_MGL", idCcmmMgl);
        }

        if (StringUtils.isNotBlank(idCcmmRr)) {
            filtrosAplicados.put("ID_CCMM_RR", idCcmmRr);
            filtrosTraducidos.put("ID_CCMM_RR", idCcmmRr);
        }

        if (StringUtils.isNotBlank(atributo)) {
            for (SelectItem selectItem : this.itemsAtributosList) {
                if (selectItem.getValue().equals(atributo)) {
                    if (atributo.equals("3081")) {
                        ConsultaDireccionBean direccionBean = (ConsultaDireccionBean) JSFUtil.getBean("consultaDireccionBean");
                        filtrosAplicados.put(selectItem.getLabel(), "'" + direccionBean.getResponseConstruirDireccion().getDrDireccion().getDireccionRespuestaGeo() + "'");
                        filtrosTraducidos.put(selectItem.getLabel(), "'" + direccionBean.getResponseConstruirDireccion().getDrDireccion().getDireccionRespuestaGeo() + "'");
                    } else {
                        filtrosAplicados.put(selectItem.getLabel(), valorAtributo.toString());
                        filtrosTraducidos.put(selectItem.getLabel(), valorAtributo.toString());
                    }

                }
            }
        }

        final HashMap<String, String> filtrosConsulta = new HashMap<>();
        filtrosConsulta.put(FILTROS_APLICADOS, "LIT "+StringToolUtils.mapToString(filtrosAplicados));
        filtrosConsulta.put(FILTROS_TRADUCIDOS, StringToolUtils.mapToString(filtrosTraducidos));

        return  filtrosConsulta;
    }

    public void consultarRegistrosSegunEstado() throws ApplicationException {
        registrosFiltro.clear();
        listaInformacionCargados=servicioMasivos.contblLineasMasivoSp(NOMBRE_PROCESO, informacionIncialReportes.getTipo(), null); //Informacion de reportes creados
        listInfoByPageCargues(1);
    }


    /* proceso de paginación cargues */

    /**
     * Carga los resultados de la primera página disponible por mostrar.
     */
    public void pageFirstCargues(){
        listInfoByPageCargues(paginator.goFirstPage());
    }

    /**
     * Carga los resultados de reportes de la página anterior disponible por mostrar.
     */
    public void pagePreviousCargues(){
        listInfoByPageCargues(paginator.goPreviousPage());
    }

    /**
     * Carga los resultados de cargues de la página siguiente disponible por mostrar.
     */
    public void pageNextCargues(){
        listInfoByPageCargues(paginator.goNextPage());
    }

    /**
     * Carga los resultados de cargues de la última página disponible por mostrar.
     */
    public void pageLastCargues(){
        int totalPages = paginator.goLastPage();
        listInfoByPageCargues(totalPages);
    }

    /**
     * Redirige hacia una posición de página de resultados de cargues seleccionados.
     */
    public void goToSelectedPage(){
        listInfoByPageCargues(selectedPageCargue);
    }

    /**
     * Obtiene la información de la página actual seleccionada en
     * la tabla de los cargues
     *
     * @return Retorna la descripción de la posición actual de la página de
     * los resultados de cargues y la cantidad de páginas disponibles.
     */
    public String obtainActualPageCargues(){
        pageNumberSelectorCargue = paginator.getData().getPageNumberSelector();
        selectedPageCargue = paginator.getData().getSelectedPage();
        return paginator.getData().getDescriptionPages();
    }

    /**
     * Lista la información a visualizar en la tabla de cargues
     *
     * @param numPage Número de página de la que se quiere mostrar los cargues
     */
    public void listInfoByPageCargues(int numPage) {
        try {
            if (Objects.isNull(paginator)) paginator = new PaginatorUtil<>();
            List<CargueInformacionDto> listaAux = listaInformacionCargados.stream()
                    .filter(cargue -> cargue.getTipo().equals(CarguesMasivosMensajeria.CARGUE_MASIVO_TIPO_CONSULTA))
                    .collect(Collectors.toList());
            paginator.pagingProcess(numPage, PAGINACION_CINCO_FILAS, listaAux);
            registrosFiltro = paginator.getData().getPaginatedList();

        } catch (ApplicationException | NullPointerException e) {
            String msgError = "Ocurrió un error al paginar la información de Cargues: "
                    + ClassUtils.getCurrentMethodName(this.getClass());
            LOGGER.error(msgError, e);
            FacesUtil.mostrarMensajeError(msgError, e);
        }
    }
    
}
