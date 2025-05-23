/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.vt.otHhpp;

import co.com.claro.mer.blockreport.BlockReportServBean;
import co.com.claro.mgl.dtos.FiltroConsultaOtDireccionesDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.OtHhppMglFacadeLocal;
import co.com.claro.mgl.facade.TipoOtHhppMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtRegionalMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.ptlus.UsuarioServicesFacadeLocal;
import co.com.claro.mgl.jpa.entities.OtHhppMgl;
import co.com.claro.mgl.jpa.entities.TipoOtHhppMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtRegionalRr;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.claro.mgl.rest.dtos.UsuariosServicesDTO;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.primefaces.PrimeFaces;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author Juan David Hernandez
 */
@ManagedBean(name = "otHhppBean")
@ViewScoped
@Log4j2
public class OtHhppBean implements Serializable {

    private String usuarioVT = null;
    private int perfilVt = 0;
    private SecurityLogin securityLogin;
    private List<String> rolesUsuarioList;
    private List<OtHhppMgl> otHhppList;
    private List<OtHhppMgl> otHhppListAux;
    private String pageActual;
    private String numPagina = "1";
    private String inicioPagina = "<< - Inicio";
    private String anteriorPagina = "< - Anterior";
    private String finPagina = "Fin - >>";
    private String siguientePagina = "Siguiente - >";
    private List<Integer> paginaList;
    private int actual;
    private int filasPag = ConstantsCM.PAGINACION_DIEZ_FILAS;
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    private FiltroConsultaOtDireccionesDto filtroConsultaOtDireccionesDto = new FiltroConsultaOtDireccionesDto();
    private BigDecimal tipoDeOtSeleccionadaParaFiltro;
    private BigDecimal estadoGeneralSeleccionadaParaFiltro;
    private List<TipoOtHhppMgl> tipoOtList = new ArrayList<>();
    private List<CmtBasicaMgl> estadoList = new ArrayList<>();
    private Date fechaFiltro = null;
    private int totalPaginasConsulta = 0;
    private BigDecimal regionalSeleccionadaParaFiltro;
    private List<CmtRegionalRr> regionalesList = new ArrayList<>();
    private Boolean validarGestionDesbloqueo;
    private final String FORMULARIO_DESBLOQUEAR = "ORDENESHHPPGESTIONARDESBLOQUEAR";
    private UsuariosServicesDTO usuarioLogin = new UsuariosServicesDTO();
    private static final String[] NOM_COLUMNAS = {"Número Ot", "Nombre Tipo Ot", 
                "Nombre Contacto","Telefono Contacto", "Correo Contacto", 
                "Fecha Creación", "Estado General", "Regional"};
    private boolean flag;
    
    @EJB
    private OtHhppMglFacadeLocal otHhppMglFacadeLocal;
    @EJB
    private TipoOtHhppMglFacadeLocal tipoOtHhppMglFacadeLocal;
    @EJB
    private CmtBasicaMglFacadeLocal cmtBasicaMglFacadeLocal;
    @EJB
    private CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMglFacadeLocal;
    @EJB
    private CmtRegionalMglFacadeLocal regionalMglFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacadeLocal;
    @EJB
    private UsuarioServicesFacadeLocal usuariosFacade;
    @Inject
    private BlockReportServBean blockReportServBean;


    //Opciones agregadas para Rol
    private final String BEGSOTBTN = "BEGSOTBTN";
    private final String EXGSOTBTN = "EXGSOTBTN";
    private final String EDGSOTBTN = "EDGSOTBTN";


    public OtHhppBean()  {
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
                if (!response.isCommitted()) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                }
            }
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error en OtHhppBean. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en OtHhppBean. ", e, LOGGER);
        }
    }

    @PostConstruct
    private void init() {
        try {
            obtenerListadoRolesUsuario();
            cargarOtHhppList();
            cargarTipoOtHhppList();
            cargarEstadoHhppList();
            cargarRegionalesList();
            otHhppMglFacadeLocal.setUser(usuarioVT, perfilVt);

        } catch (ApplicationException | IOException | ParserConfigurationException | SAXException e) {
            LOGGER.error("Error en init, al realizar cargue inicial de la pantalla. " +e.getMessage(), e);
        } 
    }

    /**
     * Función que obtiene el listado de roles del usuario
     *
     * @author Juan David Hernandez
     * @throws org.xml.sax.SAXException
     * @throws java.io.IOException
     */
    public void obtenerListadoRolesUsuario() throws ParserConfigurationException, IOException, SAXException {

        HttpSession session1 = (HttpSession) facesContext.getExternalContext().getSession(false);
        String valoresUsuarioLogeado = (String) session1.getAttribute("cookieXml");
        org.w3c.dom.Document doc;
        NodeList nList;
        String roles;

        doc = (org.w3c.dom.Document) DocumentBuilderFactory.newInstance()
                .newDocumentBuilder().parse(new InputSource(new StringReader(valoresUsuarioLogeado)));
        nList = doc.getElementsByTagName("ROLES");
        roles = nList.item(0).getTextContent();
        rolesUsuarioList = new ArrayList();

        String[] rolesUsuario = roles.split("/");

        for (String string : rolesUsuario) {
            rolesUsuarioList.add(string);
        }

    }

    /**
     * Función que carga el listado de Ot creadas en la base de datos.
     *
     * @author Juan David Hernandez
     */
    public void cargarOtHhppList() {
        OtHhppBean.this.listInfoByPage(1);
    }

    /**
     * Función que carga el listado de Tipo Ot para filtro
     *
     * @author Orlando Velasquez
     */
    public void cargarTipoOtHhppList() {
        try {
            tipoOtList = tipoOtHhppMglFacadeLocal.findAll();
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en cargarTipoOtHhppList, al realizar cargue del listado de Tipo Ot. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en cargarTipoOtHhppList, al realizar cargue del listado de Tipo Ot. ", e, LOGGER);
        }
    }

    /**
     * Función que carga el listado de Estados para filtro
     *
     * @author Orlando Velasquez
     */
    public void cargarEstadoHhppList() throws ApplicationException {
        try {
            CmtTipoBasicaMgl cmtTipoBasicaMgl = cmtTipoBasicaMglFacadeLocal.
                    findByCodigoInternoApp(Constant.ESTADO_GENERAL_OT_HHPP);
            estadoList = cmtBasicaMglFacadeLocal.findByTipoBasica(cmtTipoBasicaMgl);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en cargarEstadoHhppList, al realizar cargue del listado de Estados Ot. ", e, LOGGER);
            throw e;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en cargarEstadoHhppList, al realizar cargue del listado de Estados Ot. ", e, LOGGER);
            throw e;
        }
    }
    
    /**
     * Función que carga el listado de regionales para filtro
     *
     * @author Victor Bocanegra
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void cargarRegionalesList() throws ApplicationException {
        try {
            regionalesList = regionalMglFacadeLocal.findAll();
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en cargarRegionalesList, al realizar cargue del listado de regionales. ", e, LOGGER);
            throw e;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en cargarRegionalesList, al realizar cargue del listado de regionales. ", e, LOGGER);
            throw e;
        }
    }

    /**
     * Función utilizada para redireccionar a la pantalla de creación de Ot en
     * la base de datos.
     *
     * @author Juan David Hernandez
     */
    public void irCrearOtHhpp() {
        try {
            FacesUtil.navegarAPagina("/view/MGL/VT/otHhpp/"
                            + "crearOtHhpp.jsf");
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en irCrearOtHhpp, al redireccionar a creación de Tipo Ot. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en irCrearOtHhpp, al redireccionar a creación de Tipo Ot. ", e, LOGGER);
        }
    }

    /**
     * Función utilizada para redireccionar a la pantalla de edición de una ot
     * existente en la base de datos.
     *
     * @author Juan David Hernandez
     * @param otSeleccionada
     */
    public void irEditarOtHhpp(OtHhppMgl otSeleccionada) {
        try {
            if (otSeleccionada != null
                    && otSeleccionada.getOtHhppId() != null) {
                // Instancia Bean de Session para obtener la solicitud seleccionada
                OtHhppMglSessionBean otHhppMglSessionBean
                        = (OtHhppMglSessionBean) JSFUtil.getSessionBean(OtHhppMglSessionBean.class);
                otHhppMglSessionBean.setOtHhppMglSeleccionado(otSeleccionada);
                otHhppMglSessionBean.setDetalleOtHhpp(true);

                OtHhppMgl orden = otHhppMglFacadeLocal.findOtByIdOt(otSeleccionada.getOtHhppId());

                if (orden != null) {
                    boolean disponible = orden.getDisponibilidadGestion() == null;

                    if (disponible) {
                        otHhppMglFacadeLocal.bloquearDesbloquearOrden(orden, true);
                        FacesUtil.navegarAPagina("/view/MGL/VT/otHhpp/"
                                + "editarOtHhpp.jsf");
                    } else {
                        if (usuarioVT.equalsIgnoreCase(orden.getDisponibilidadGestion())) {
                            FacesUtil.navegarAPagina("/view/MGL/VT/otHhpp/"
                                    + "editarOtHhpp.jsf");
                        } else {
                            String telefono = "";
                            String correo = "";
                            String errorUser = "";
                            try {
                                usuarioLogin = usuariosFacade.consultaInfoUserPorUsuario(orden.getDisponibilidadGestion());
                                if (usuarioLogin != null && usuarioLogin.getDescripcionArea() != null) {
                                    telefono = usuarioLogin.getTelefono();
                                    correo = usuarioLogin.getEmail();
                                }
                            } catch (ApplicationException e) {
                                errorUser = "No se pudo obtener datos del usuario " + orden.getDisponibilidadGestion() +". ";
                            }
                            
                            otHhppMglSessionBean.setOtHhppMglSeleccionado(null);
                            
                            String msnErr = "La orden " + orden.getOtHhppId() + " se encuentra en proceso de Gestión por el usuario: " 
                                    + orden.getDisponibilidadGestion() + (telefono.isEmpty() ? "" : ", teléfono: " + telefono) + (correo.isEmpty() ? "" : ", correo: " + correo);
                            Severity severity = errorUser == "" ? FacesMessage.SEVERITY_INFO : FacesMessage.SEVERITY_WARN;
                            FacesMessage message = new FacesMessage(severity, "En Proceso", errorUser + msnErr);
                            PrimeFaces.current().dialog().showMessageDynamic(message);
                        }
                    }
                }

            } else {
                String msnError = "Ha ocurrido un error seleccionando la"
                        + " ot, intente de nuevo por favor.";
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "En Proceso", msnError);
                            PrimeFaces.current().dialog().showMessageDynamic(message);
            }

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en irEditarOtHhpp, al redireccionar a editar a la Ot. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en irEditarOtHhpp, al redireccionar a editar a la Ot. ", e, LOGGER);
        }
    }
    
    /**
     * Reporte  de xls de ot gestion hhpp
     * Autor: Juan David Hernandez 
     * Modificado: Dayver De la hoz
     * @return 
     */
    public String exportExcel() throws ApplicationException {
        try {
            // Se verifica si está bloqueada la generación de reportes y si
            // el usuario en sesión está autorizado para realizar el proceso.
            if (blockReportServBean.isReportGenerationBlock("Exportar GESTIÓN DE OT")) return StringUtils.EMPTY;

            if (this.flag){
                String msn = "Ya se encuentra en proceso una descarga, intente en unos minutos. ";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));
            } else {

                this.setFlag(true);

                int expLonPag = 1000;
                FiltroConsultaOtDireccionesDto consultaOtDireccionesDtoExport = new FiltroConsultaOtDireccionesDto();
                long totalPag = otHhppMglFacadeLocal.countByFiltro(consultaOtDireccionesDtoExport);

                StringBuilder sb = new StringBuilder();
                byte[] csvData;
                SimpleDateFormat formato = new SimpleDateFormat("dd_MMM_yyyy-HH:mm:ss");

                for (int j = 0; j < NOM_COLUMNAS.length; j++) {
                    sb.append(NOM_COLUMNAS[j]);
                    if (j < NOM_COLUMNAS.length) {
                        sb.append(";");
                    }
                }
                sb.append("\n");

                String todayStr = formato.format(new Date());
                String fileName = "OtHhppGestion" + "_" + todayStr + "." + "csv";
                FacesContext fc = FacesContext.getCurrentInstance();
                HttpServletResponse response1 = (HttpServletResponse) fc.getExternalContext().getResponse();
                response1.setHeader("Content-disposition", "attached; filename=\""
                        + fileName + "\"");
                response1.setContentType("application/octet-stream;charset=UTF-8;");
                response1.setCharacterEncoding("UTF-8");

                csvData = sb.toString().getBytes();
                response1.getOutputStream().write(csvData);


                for (int exPagina = 1; exPagina <= ((totalPag / expLonPag) + (totalPag % expLonPag != 0 ? 1 : 0)); exPagina++) {

                    List<OtHhppMgl> otHhppListExport = otHhppMglFacadeLocal
                            .findAllOtHhppPaginada(exPagina, expLonPag, rolesUsuarioList, consultaOtDireccionesDtoExport);

                    if (otHhppListExport != null && !otHhppListExport.isEmpty()) {

                        for (OtHhppMgl otHhpp : otHhppListExport) {

                            if (sb.toString().length() > 1) {
                                sb.delete(0, sb.toString().length());
                            }

                            if (otHhpp.getOtHhppId() != null) {
                                sb.append(otHhpp.getOtHhppId().toString());
                                sb.append(";");
                            } else {
                                sb.append("");
                                sb.append(";");
                            }

                            if (otHhpp.getTipoOtHhppId() != null) {
                                sb.append(otHhpp.getTipoOtHhppId().getNombreTipoOt());
                                sb.append(";");
                            } else {
                                sb.append("");
                                sb.append(";");
                            }

                            if (otHhpp.getTelefonoContacto() != null) {
                                sb.append(otHhpp.getTelefonoContacto());
                                sb.append(";");
                            } else {
                                sb.append("");
                                sb.append(";");
                            }

                            if (otHhpp.getNombreContacto() != null) {
                                sb.append(otHhpp.getNombreContacto());
                                sb.append(";");
                            } else {
                                sb.append("");
                                sb.append(";");
                            }

                            if (otHhpp.getCorreoContacto() != null) {
                                sb.append(otHhpp.getCorreoContacto());
                                sb.append(";");
                            } else {
                                sb.append("");
                                sb.append(";");
                            }

                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                            String fechaCreacionOt = "";
                            if (otHhpp.getFechaCreacionOt() != null) {
                                fechaCreacionOt = sdf.format(otHhpp.getFechaCreacion());
                                sb.append(fechaCreacionOt);
                                sb.append(";");
                            } else {
                                sb.append("");
                                sb.append(";");
                            }

                            if (otHhpp.getEstadoGeneral() != null) {
                                sb.append(otHhpp.getEstadoGeneral().getNombreBasica());
                                sb.append(";");
                            } else {
                                sb.append("");
                                sb.append(";");
                            }

                            if (otHhpp.getRegionalRr() != null) {
                                sb.append(otHhpp.getRegionalRr().getNombreRegional());
                                sb.append(";");
                            } else {
                                sb.append("");
                                sb.append(";");
                            }

                            sb.append("\n");
                            csvData = sb.toString().getBytes();
                            response1.getOutputStream().write(csvData);
                            response1.getOutputStream().flush();
                            response1.flushBuffer();

                        }
                    }
                }
                response1.getOutputStream().close();
                fc.responseComplete();
                this.setFlag(false);
            }
        } catch (IOException e) {
            this.setFlag(false);
            FacesUtil.mostrarMensajeError("Error en exportExcel. ", e, LOGGER);
        } catch (Exception e) {
            this.setFlag(false);
            FacesUtil.mostrarMensajeError("Error en exportExcel. ", e, LOGGER);
        }
        return null;
    }

    
    /**
     * Función que realiza una nueva consulta con los parametros de filtro modificados
     *
     * @author Orlando Velasquez
     * 
     */
    public void filtrar() {
        listInfoByPage(1);
    }
    
    public void showMessage() {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "What we do in life", "Echoes in eternity.");
        PrimeFaces.current().dialog().showMessageDynamic(message);
    }

    /**
     * Función que realiza paginación de la tabla y consulta dependiendo los filtros
     * que se han elejido
     *
     * @param page;
     * @author Juan David Hernandez
     * @return 
     */
    public String listInfoByPage(int page) {
        try {

            //Validar filtro tipo Ot
            if (tipoDeOtSeleccionadaParaFiltro != null) {
                TipoOtHhppMgl tipoOtHhppMgl = tipoOtHhppMglFacadeLocal.findTipoOtHhppById(tipoDeOtSeleccionadaParaFiltro);
                filtroConsultaOtDireccionesDto.setTipo(tipoOtHhppMgl);
            } else {
                filtroConsultaOtDireccionesDto.setTipo(null);
            }

            //Validar filtro estado general
            if (estadoGeneralSeleccionadaParaFiltro != null) {
                CmtBasicaMgl estado = cmtBasicaMglFacadeLocal.findById(estadoGeneralSeleccionadaParaFiltro);
                filtroConsultaOtDireccionesDto.setEstado(estado);
            } else {
                filtroConsultaOtDireccionesDto.setEstado(null);
            }
            
            //Validar filtro regional
            if (regionalSeleccionadaParaFiltro != null) {
                CmtRegionalRr regional = new CmtRegionalRr();
                if(regionalSeleccionadaParaFiltro.compareTo(new BigDecimal("0"))==0){
                    regional.setRegionalRrId(regionalSeleccionadaParaFiltro);
                    filtroConsultaOtDireccionesDto.setRegional(regional);
                } else {
                    regional.setRegionalRrId(regionalSeleccionadaParaFiltro);
                    regional = regionalMglFacadeLocal.findById(regional);
                    filtroConsultaOtDireccionesDto.setRegional(regional);
                }
            } else {
                filtroConsultaOtDireccionesDto.setRegional(null);
            }
            
            filtroConsultaOtDireccionesDto.setFechaCreacion(fechaFiltro);

            actual = page;
            //Cuenta el numero de registros dependiendo los parametros de filtro
            //Seleccionados para obtener 
            totalPaginasConsulta = otHhppMglFacadeLocal.countByFiltro(filtroConsultaOtDireccionesDto);
            getPageActual();

            otHhppList = otHhppMglFacadeLocal
                    .findAllOtHhppPaginada(page, filasPag, rolesUsuarioList, filtroConsultaOtDireccionesDto);
            
            if (otHhppList != null && !otHhppList.isEmpty()) {
                for (OtHhppMgl otHhpp : otHhppList) {
                    otHhpp.setColorAlerta(otHhppMglFacadeLocal.obtenerColorAlerta(otHhpp));
                }
            }

        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en listInfoByPage, en lista de paginación. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en listInfoByPage, en lista de paginación. ", e, LOGGER);
        }
        return null;
    }

    /**
     * Función que carga la primera página de resultados
     *
     * @author Juan David Hernandez
     */
    public void pageFirst() {

        OtHhppBean.this.listInfoByPage(1);

    }

    /**
     * Función que carga la página anterior de resultados.
     *
     * @author Juan David Hernandez
     */
    public void pagePrevious() {

            int totalPaginas = getTotalPaginas();
            int nuevaPageActual = actual - 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            if (nuevaPageActual <= 0) {
                nuevaPageActual = 1;
            }
            OtHhppBean.this.listInfoByPage(nuevaPageActual);

    }

    /**
     * Función que cpermite ir directamente a la página seleccionada de
     * resultados.
     *
     * @author Juan David Hernandez
     */
    public void irPagina() {
        try {
            int totalPaginas = getTotalPaginas();
            int nuevaPageActual = Integer.parseInt(numPagina);
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            OtHhppBean.this.listInfoByPage(nuevaPageActual);
        } catch (NumberFormatException e) {
            FacesUtil.mostrarMensajeError("Error en irPagina. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en irPagina. ", e, LOGGER);
        }
    }

    /**
     * Función que carga la siguiente página de resultados.
     *
     * @author Juan David Hernandez
     */
    public void pageNext() {
        try {
            int totalPaginas = getTotalPaginas();
            int nuevaPageActual = actual + 1;
            if (nuevaPageActual > totalPaginas) {
                nuevaPageActual = totalPaginas;
            }
            OtHhppBean.this.listInfoByPage(nuevaPageActual);
        } catch (NumberFormatException e) {
            FacesUtil.mostrarMensajeError("Error en pageNext, navegando a la siguiente página. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en pageNext, navegando a la siguiente página. ", e, LOGGER);
        }
    }

    /**
     * Función que carga la última página de resultados
     *
     * @author Juan David Hernandez
     */
    public void pageLast() {
        try {
            int totalPaginas = getTotalPaginas();
            OtHhppBean.this.listInfoByPage(totalPaginas);
        } catch (NumberFormatException e) {
            FacesUtil.mostrarMensajeError("Error en pageLast, navegando a la última página. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en pageLast, navegando a la última página. ", e, LOGGER);
        }
    }

    /**
     * Función que permite obtener el total de páginas de resultados.
     *
     * @author Juan David Hernandez
     * @return 
     */
    public int getTotalPaginas() {
        try {
            int totalPaginas;
            int pageSol;
            pageSol = totalPaginasConsulta;

            totalPaginas = (int) ((pageSol % filasPag != 0)
                    ? (pageSol / filasPag) + 1 : pageSol / filasPag);
            return totalPaginas;
        } catch (NumberFormatException e) {
            FacesUtil.mostrarMensajeError("Error en getTotalPaginas, direccionando a la primera página. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en getTotalPaginas, direccionando a la primera página. ", e, LOGGER);
        }
        return 1;
    }

    /**
     * Función que conocer la página actual en la que se encuentra el usuario en
     * los resultados.
     *
     * @author Juan David Hernandez
     * @return 
     */
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
    
    public String traerRegional(CmtRegionalRr regionalRr) {

        String region;

        if (regionalRr != null) {
            region = regionalRr.getCodigoRr();
        } else {
            region = "SIN REGIONAL";
        }

        return region;
    }
    
     public String desbloquearOrden(OtHhppMgl orden) {
        try {
            if (orden != null && orden.getOtHhppId() != null) {
                otHhppMglFacadeLocal.bloquearDesbloquearOrden(orden, false);
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en OtHhppBean:desbloquearOrden. " + e.getMessage(), e, LOGGER);
        }
        return listInfoByPage(1);
    }
        
    /**
     *
     * @return
     */
    public boolean validarGestionDesbloqueo() {
        if (validarGestionDesbloqueo == null) {
            validarGestionDesbloqueo = validarFormulario(FORMULARIO_DESBLOQUEAR);
        }
        return validarGestionDesbloqueo;
    }
    
     private boolean validarFormulario(String formulario) {
        try {
            return ValidacionUtil.validarVisualizacion(formulario, ValidacionUtil.OPC_EDICION, cmtOpcionesRolMglFacadeLocal, securityLogin);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION + e.getMessage(), e, LOGGER);
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al OrdenTrabajoGestionarBean. " + e.getMessage(), e, LOGGER);
        }
        return false;
    }
    
    public List<OtHhppMgl> getOtHhppList() {
        return otHhppList;
    }

    public void setOtHhppList(List<OtHhppMgl> otHhppList) {
        this.otHhppList = otHhppList;
    }

    public String getNumPagina() {
        return numPagina;
    }

    public void setNumPagina(String numPagina) {
        this.numPagina = numPagina;
    }

    public String getInicioPagina() {
        return inicioPagina;
    }

    public void setInicioPagina(String inicioPagina) {
        this.inicioPagina = inicioPagina;
    }

    public String getAnteriorPagina() {
        return anteriorPagina;
    }

    public void setAnteriorPagina(String anteriorPagina) {
        this.anteriorPagina = anteriorPagina;
    }

    public String getFinPagina() {
        return finPagina;
    }

    public void setFinPagina(String finPagina) {
        this.finPagina = finPagina;
    }

    public String getSiguientePagina() {
        return siguientePagina;
    }

    public void setSiguientePagina(String siguientePagina) {
        this.siguientePagina = siguientePagina;
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
        return filasPag;
    }

    public void setFilasPag(int filasPag) {
        this.filasPag = filasPag;
    }

    public FiltroConsultaOtDireccionesDto getFiltroConsultaOtDireccionesDto() {
        return filtroConsultaOtDireccionesDto;
    }

    public void setFiltroConsultaOtDireccionesDto(FiltroConsultaOtDireccionesDto filtroConsultaOtDireccionesDto) {
        this.filtroConsultaOtDireccionesDto = filtroConsultaOtDireccionesDto;
    }

    public BigDecimal getTipoDeOtSeleccionadaParaFiltro() {
        return tipoDeOtSeleccionadaParaFiltro;
    }

    public void setTipoDeOtSeleccionadaParaFiltro(BigDecimal tipoDeOtSeleccionadaParaFiltro) {
        this.tipoDeOtSeleccionadaParaFiltro = tipoDeOtSeleccionadaParaFiltro;
    }

    public List<TipoOtHhppMgl> getTipoOtList() {
        return tipoOtList;
    }

    public void setTipoOtList(List<TipoOtHhppMgl> tipoOtList) {
        this.tipoOtList = tipoOtList;
    }

    public BigDecimal getEstadoGeneralSeleccionadaParaFiltro() {
        return estadoGeneralSeleccionadaParaFiltro;
    }

    public void setEstadoGeneralSeleccionadaParaFiltro(BigDecimal estadoGeneralSeleccionadaParaFiltro) {
        this.estadoGeneralSeleccionadaParaFiltro = estadoGeneralSeleccionadaParaFiltro;
    }

    public List<CmtBasicaMgl> getEstadoList() {
        return estadoList;
    }

    public void setEstadoList(List<CmtBasicaMgl> estadoList) {
        this.estadoList = estadoList;
    }

    public Date getFechaFiltro() {
        return fechaFiltro;
    }

    public void setFechaFiltro(Date fechaFiltro) {
        this.fechaFiltro = fechaFiltro;
    }

    public BigDecimal getRegionalSeleccionadaParaFiltro() {
        return regionalSeleccionadaParaFiltro;
    }

    public void setRegionalSeleccionadaParaFiltro(BigDecimal regionalSeleccionadaParaFiltro) {
        this.regionalSeleccionadaParaFiltro = regionalSeleccionadaParaFiltro;
    }

    public List<CmtRegionalRr> getRegionalesList() {
        return regionalesList;
    }

    public void setRegionalesList(List<CmtRegionalRr> regionalesList) {
        this.regionalesList = regionalesList;
    }

    public String getUsuarioVT() {
        return usuarioVT;
    }

    public void setUsuarioVT(String usuarioVT) {
        this.usuarioVT = usuarioVT;
    }

    // Validar Opciones por Rol
    public boolean validarOpcionCrearOT() {
        return validarEdicionRol(BEGSOTBTN);
    }

    public boolean validarOpcionVerOT() {
        return validarEdicionRol(EDGSOTBTN);
    }

    public boolean validarOpcionExportar() {
        return validarEdicionRol(EXGSOTBTN);
    }

    //funcion General
      private boolean validarEdicionRol(String formulario) {
        try {
            boolean tieneRolPermitido = ValidacionUtil.validarVisualizacion(formulario, ValidacionUtil.OPC_EDICION, cmtOpcionesRolMglFacadeLocal, securityLogin);
            return tieneRolPermitido;
        }catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION + e.getMessage(), e, LOGGER);
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al OrdenTrabajoGestionarBean. " + e.getMessage(), e, LOGGER);
        }
        return false;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
