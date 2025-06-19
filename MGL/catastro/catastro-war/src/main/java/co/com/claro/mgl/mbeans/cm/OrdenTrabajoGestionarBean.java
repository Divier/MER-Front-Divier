package co.com.claro.mgl.mbeans.cm;

import co.com.claro.mer.blockreport.BlockReportServBean;
import co.com.claro.mer.estadoxflujo.facade.EstadoFlujoFacadeLocal;
import co.com.claro.mgl.dtos.CmtFiltroConsultaOrdenesDto;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.ParametrosMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOrdenTrabajoMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtRegionalRRFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.ptlus.UsuarioServicesFacadeLocal;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtOrdenTrabajoMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtRegionalRr;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.mbeans.cm.ot.OtMglBean;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.mbeans.util.JSFUtil;
import co.com.claro.mgl.rest.dtos.UsuariosServicesDTO;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import java.io.IOException;
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
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author ortizjaf
 */
@ManagedBean(name = "ordenTrabajoGestionarBean")
@ViewScoped
@Log4j2
public class OrdenTrabajoGestionarBean {

    @EJB
    private CmtOrdenTrabajoMglFacadeLocal ordenTrabajoMglFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacadeLocal;
    @EJB
    private CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMglFacadeLocal;
    @EJB
    private CmtBasicaMglFacadeLocal cmtBasicaMglFacadeLocal;
    @EJB
    private ParametrosMglFacadeLocal parametrosFacade;
    @EJB
    private CmtRegionalRRFacadeLocal regionalRRFacadeLocal;
    @EJB
    private UsuarioServicesFacadeLocal usuariosFacade;
    @EJB
    private EstadoFlujoFacadeLocal estadoFlujoFacadeLocal;
    @Inject
    private BlockReportServBean blockReportServBean;


    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private final String FORMULARIOOT = "OTGESTIONARORDEN";
    private final String FORMULARIOCM = "OTGESTIONARCUENTA";
    private final String FORMULARIO_DESBLOQUEAR = "ORDENESGESTIONARDESBLOQUEAR";
    private final String ROLOPCEXPGEST = "ROLOPCEXPGEST";
    private final String ROLOPCIDGEST = "ROLOPCIDGEST";

    @Getter
    private String pageActualInfo;
    private String numPagina = "1";
    private List<Integer> paginaList;
    private int actual;
    private boolean showFooterTable;
    private CmtTipoBasicaMgl tipoBasicaTipoEstadoInternoOt;
    private List<BigDecimal> estadosFLujoUsuario;
    private List<CmtOrdenTrabajoMgl> ordenTrabajoList;
    private List<CmtOrdenTrabajoMgl> ordenTrabajoListAux;   
    private List<CmtBasicaMgl> estadoOtList;
    private CmtBasicaMgl estadoOtSelected;
    private String idOtToFind;
    private SecurityLogin securityLogin;
    private List<CmtBasicaMgl> tecnologiaList;
    private CmtTipoBasicaMgl tipoBasicaTipoProyecto;
    private boolean habilitaObj;
    private List<CmtRegionalRr> regionalList;   
    private Boolean validarGestionDesbloqueo;
    private String usuarioSesion;
    private UsuariosServicesDTO usuarioLogin = new UsuariosServicesDTO();
    private static final String[] NOM_COLUMNAS = {"Nombre Cuenta Matriz", "No. Cuenta en RR", 
                "No. Cuenta en MGL","Regional","Departamento", "Ciudad", 
                "Centro Poblado", "No. OT", "Tecnologia", "Segmento",
                "Tipo Trabajo", "SubTipo de Orden", "Estado Interno", "Estado Externo",
                "Fecha Creación", "Fecha Sugerida OT", "SLA", "Solicitante", "ANS", "Clase de OT"};
    private CmtFiltroConsultaOrdenesDto filtroConsultaOrdenesDto = new CmtFiltroConsultaOrdenesDto();
    private boolean flag;

    public OrdenTrabajoGestionarBean() {
        try {
            securityLogin = new SecurityLogin(facesContext);
            if (!securityLogin.isLogin()) {
                if (!response.isCommitted()) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                }
            }
             usuarioSesion = securityLogin.getLoginUser();
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error al OrdenTrabajoGestionarBean. " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al OrdenTrabajoGestionarBean. " + e.getMessage(), e, LOGGER);
        }

    }

    @PostConstruct
    public void init() {
        try {
            estadosFLujoUsuario = estadoFlujoFacadeLocal.findEstadoFlujoUsuario();

            if (CollectionUtils.isEmpty(estadosFLujoUsuario)) {
                estadosFLujoUsuario = ordenTrabajoMglFacadeLocal.getEstadosFLujoUsuario(facesContext);
            }

            obtenerTipoBasicaEstadoInternoOt();
            obtenerRegionalesList();
            filtrarInfoTec();
            showFooterTable = true;
            listInfoByPage(1);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en  OrdenTrabajoGestionarBean:init(). " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en OrdenTrabajoGestionarBean:init(). " + e.getMessage(), e, LOGGER);
        }
    }
    
    
     /**
     * Obtiene el listado de regionales de la base de datos.
     *
     * @author Victor Bocanegra
     */
    public void obtenerRegionalesList() {
        try {
            regionalList = regionalRRFacadeLocal.findAllRegional();
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en OrdenTrabajoGestionarBean:obtenerRegionalesList()  al obtener listado de regionales " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en OrdenTrabajoGestionarBean:obtenerRegionalesList(). " + e.getMessage(), e, LOGGER);
        }
    }
    

     /**
     * Obtiene el listado estados internos de ot
     *
     * @author Juan David Hernandez
     */
    public void obtenerTipoBasicaEstadoInternoOt() {
        try {
            tipoBasicaTipoEstadoInternoOt = cmtTipoBasicaMglFacadeLocal.
                    findByCodigoInternoApp(Constant.TIPO_BASICA_ESTADO_INTERNO_OT);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en OrdenTrabajoGestionarBean:obtenerTipoBasicaEstadoInternoOt() al obtener listado estados internos " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en OrdenTrabajoGestionarBean:obtenerTipoBasicaEstadoInternoOt(). " + e.getMessage(), e, LOGGER);
        }
    }

    public String findById() {
        try {
            if (idOtToFind != null && !idOtToFind.trim().isEmpty()) {
                BigDecimal idOt = new BigDecimal(idOtToFind.trim());

                ParametrosMgl result = parametrosFacade.findByAcronimoName(Constant.CONSULTA_OT_WITH_ROLES);
                CmtOrdenTrabajoMgl otFound;
                boolean conPermisos = result.getParValor().equalsIgnoreCase("1");
                
                if (conPermisos) {
                    otFound = ordenTrabajoMglFacadeLocal.findOtByIdAndPermisos(idOt, estadosFLujoUsuario);
                } else {
                    otFound = ordenTrabajoMglFacadeLocal.findOtById(idOt);
                }
             
                 if (otFound != null) {
                    ordenTrabajoList = new ArrayList<>();
                    ordenTrabajoListAux = new ArrayList<>();
                    ordenTrabajoList.add(otFound);
                    ordenTrabajoListAux.add(otFound);
                    habilitaObj = !ordenTrabajoListAux.isEmpty();
                    showFooterTable = false;
                }else{
                    if (conPermisos) {
                        otFound = ordenTrabajoMglFacadeLocal.findOtById(idOt);
                        if (otFound != null) {
                            String msn = "El usuario no tiene acceso a la orden ingresada";
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                        } else {
                            String msn = "No fue encontrada la OT: " + idOt + " en Base de Datos.";
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                        }
                    } else {
                        String msn = "No fue encontrada la OT: " + idOt + " en Base de Datos.";
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                    }
                }
            } else {
                String msn = "Debe Ingresar un numero de OT";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            }
        } catch (ApplicationException e) {
             FacesUtil.mostrarMensajeError( "Ocurrio un Error en OrdenTrabajoGestionarBean:findById()"  + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Ocurrio un Error en OrdenTrabajoGestionarBean:findById(). " + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    public String verTodas() {
        showFooterTable = true;
        idOtToFind = "";     
        listInfoByPage(1);
        return null;
    }

    public void filtrarInfo() {
        listInfoByPage(1);
    }
    
    public String filtrarInfoTec() {
        try {
            tipoBasicaTipoProyecto = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                    co.com.claro.mgl.utils.Constant.TIPO_BASICA_TECNOLOGIA);
            tecnologiaList = cmtBasicaMglFacadeLocal.
                    findByTipoBasica(tipoBasicaTipoProyecto);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en OrdenTrabajoGestionarBean:filtrarInfoTec(). " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en OrdenTrabajoGestionarBean:filtrarInfoTec(). " + e.getMessage(), e, LOGGER);
        }

        return null;
    }
    
  
    public String goGestionOt(CmtOrdenTrabajoMgl cmtOrdenTrabajoMgl) throws ApplicationException {

        OtMglBean otMglBean = JSFUtil.getSessionBean(OtMglBean.class);
        
        CmtOrdenTrabajoMgl orden = ordenTrabajoMglFacadeLocal.findOtById(cmtOrdenTrabajoMgl.getIdOt());
        
        if (orden != null) {
            boolean disponible = orden.getDisponibilidadGestion() == null;

            if (disponible) {
                return otMglBean.ingresarGestion(orden);
            } else {
                if (usuarioSesion.equalsIgnoreCase(orden.getDisponibilidadGestion())) {
                    return otMglBean.ingresarGestion(orden);
                } else {
                    String telefono = "";
                    String correo = "";
                    try {
                        usuarioLogin = usuariosFacade.consultaInfoUserPorUsuario(orden.getDisponibilidadGestion());
                        if (usuarioLogin != null && usuarioLogin.getDescripcionArea() != null) {
                            telefono = usuarioLogin.getTelefono();
                            correo = usuarioLogin.getEmail();
                        }
                    } catch (ApplicationException e) {
                        FacesUtil.mostrarMensajeWarn("No se pudo obtener datos del usuario " + orden.getDisponibilidadGestion());
                    }

                    String msnErr = "La orden " + orden.getIdOt() + " se encuentra en proceso de Gestión por el usuario: "
                            + orden.getDisponibilidadGestion() + (telefono.isEmpty() ? "" : ", teléfono: " + telefono) + (correo.isEmpty() ? "" : ", correo: " + correo);
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "En Proceso", msnErr);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msnErr, ""));
                }
            }
        }
        
        return listInfoByPage(1);
    }

    public String goCuentaMatriz(CmtCuentaMatrizMgl cuentaMatriz) {
        try {
            if (cuentaMatriz != null) {
            CuentaMatrizBean cuentaMatrizBean = JSFUtil.getSessionBean(CuentaMatrizBean.class);
            cuentaMatrizBean.setCuentaMatriz(cuentaMatriz);
            ConsultaCuentasMatricesBean consultaCuentasMatricesBean = JSFUtil.getSessionBean(ConsultaCuentasMatricesBean.class);
            consultaCuentasMatricesBean.goCuentaMatriz();
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se presentó un error al procesar"
                    + " la petición: " + e.getMessage(), e, LOGGER);
        }
        return null;
    }

    private String listInfoByPage(int page) {
        try {
            actual = page;
            getPageActual();
            ordenTrabajoList = ordenTrabajoMglFacadeLocal.
                    findByFiltroParaGestionPaginacion(estadosFLujoUsuario,
                    page, ConstantsCM.PAGINACION_QUINCE_FILAS, filtroConsultaOrdenesDto);
            
            habilitaObj = true;
           
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(Constant.MSN_PROCESO_FALLO + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en  OrdenTrabajoGestionarBean:listInfoByPage(). " + e.getMessage(), e, LOGGER);
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
            FacesUtil.mostrarMensajeError("Error al OrdenTrabajoGestionarBean:irPagina. " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al OrdenTrabajoGestionarBean:irPagina. " + e.getMessage(), e, LOGGER);
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
        try {
            int pageSol = ordenTrabajoMglFacadeLocal.getCountByFiltroParaGestion(
                    estadosFLujoUsuario, filtroConsultaOrdenesDto);
            int totalPaginas = (int) ((pageSol % ConstantsCM.PAGINACION_QUINCE_FILAS != 0)
                    ? (pageSol / ConstantsCM.PAGINACION_QUINCE_FILAS) + 1
                    : pageSol / ConstantsCM.PAGINACION_QUINCE_FILAS);

            return totalPaginas;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error al OrdenTrabajoGestionarBean:getTotalPaginas(). " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al OrdenTrabajoGestionarBean:getTotalPaginas() " + e.getMessage(), e, LOGGER);
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

            pageActualInfo = actual + " de " + totalPaginas;

            if (numPagina == null) {
                numPagina = "1";
            }
            numPagina = String.valueOf(actual);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al OrdenTrabajoGestionarBean:getTotalPaginas(). " + e.getMessage(), e, LOGGER);
        }

        return pageActualInfo;
    }

    public boolean validarEdicionOt() {
        return validarFormulario(FORMULARIOOT);
    }

    public boolean validarEdicionCM() {
        return validarFormulario(FORMULARIOCM);
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
    
  public String exportExcel() throws ApplicationException {
        try {
            // Se verifica si está bloqueada la generación de reportes y si
            // el usuario en sesión está autorizado para realizar el proceso.
            if (blockReportServBean.isReportGenerationBlock("Exportar Gestión de Ordenes de Trabajo")) return StringUtils.EMPTY;

            //Cantidad de registros inicial a consultar de la DB
            int limitIni = 20;
            //Cantidad de registros por pagina a consultar de la DB
            int limit = 1000;
            //numero total de registros del reporte
            int init = 0;
            int fin = 0;

            if (this.flag){
                String msn = "Ya se encuentra en proceso una descarga, intente en unos minutos. ";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, msn, ""));
            } else {
                this.setFlag(true);

                int total = ordenTrabajoMglFacadeLocal.getCountByFiltroParaGestion(estadosFLujoUsuario,
                        filtroConsultaOrdenesDto);

                if (total > limitIni) {
                    fin = limitIni;
                } else {
                    fin = total;
                }

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
                String fileName = "OrdenesMGLConsulte" + "_" + todayStr + "." + "csv";
                FacesContext fc = FacesContext.getCurrentInstance();
                HttpServletResponse response1 = (HttpServletResponse) fc.getExternalContext().getResponse();
                response1.setHeader("Content-disposition", "attached; filename=\""
                        + fileName + "\"");
                response1.setContentType("application/octet-stream;charset=UTF-8;");
                response1.setCharacterEncoding("UTF-8");

                csvData = sb.toString().getBytes();
                response1.getOutputStream().write(csvData);


                boolean continuar = true;

                while (continuar) {

                    ordenTrabajoListAux = ordenTrabajoMglFacadeLocal.
                            findByFiltroParaGestionPaginacion(estadosFLujoUsuario, init, fin, filtroConsultaOrdenesDto);

                    if (ordenTrabajoListAux != null
                            && !ordenTrabajoListAux.isEmpty()) {

                        for (CmtOrdenTrabajoMgl ot : ordenTrabajoListAux) {

                            if (sb.toString().length() > 1) {
                                sb.delete(0, sb.toString().length());
                            }

                            String centroPobla = "";
                            String ciudad = "";
                            String dpto = "";
                            CmtCuentaMatrizMgl cuenta = ot.getCmObj();

                            if (cuenta != null) {
                                dpto = cuenta.getDepartamento().getGpoNombre();
                                ciudad = cuenta.getMunicipio().getGpoNombre();
                                centroPobla = cuenta.getCentroPoblado().getGpoNombre();
                            }
                            if (cuenta != null) {
                                sb.append(cuenta.getNombreCuenta());
                                sb.append(";");
                                sb.append(cuenta.getNumeroCuenta().toString());
                                sb.append(";");
                                sb.append(cuenta.getCuentaMatrizId().toString());
                                sb.append(";");
                                sb.append(cuenta.getDivision());
                                sb.append(";");
                            }

                            sb.append(dpto);
                            sb.append(";");

                            sb.append(ciudad);
                            sb.append(";");

                            sb.append(centroPobla);
                            sb.append(";");

                            sb.append(ot.getIdOt().toString());
                            sb.append(";");

                            if (ot.getBasicaIdTecnologia() != null) {
                                sb.append(ot.getBasicaIdTecnologia().getNombreBasica());
                                sb.append(";");
                            } else {
                                sb.append("");
                                sb.append(";");
                            }

                            if (ot.getSegmento() != null) {
                                sb.append(ot.getSegmento().getNombreBasica());
                                sb.append(";");
                            } else {
                                sb.append("");
                                sb.append(";");
                            }

                            if (ot.getBasicaIdTipoTrabajo() != null) {
                                sb.append(ot.getBasicaIdTipoTrabajo().getNombreBasica());
                                sb.append(";");
                            } else {
                                sb.append("");
                                sb.append(";");
                            }

                            if (ot.getTipoOtObj() != null) {
                                sb.append(ot.getBasicaIdTipoTrabajo().getNombreBasica());
                                sb.append(";");
                            } else {
                                sb.append("");
                                sb.append(";");
                            }

                            if (ot.getEstadoInternoObj() != null) {
                                sb.append(ot.getEstadoInternoObj().getNombreBasica());
                                sb.append(";");
                            } else {
                                sb.append("");
                                sb.append(";");
                            }

                            if (ot.getEstadoInternoObj() != null) {
                                sb.append(ot.getEstadoInternoObj().getNombreBasica()==null?"":ot.getEstadoInternoObj().getNombreBasica());
                                sb.append(";");
                            }

                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SS");
                            String fechaCreacion = "";
                            if (ot.getFechaCreacion() != null) {
                                fechaCreacion = sdf.format(ot.getFechaCreacion());
                                sb.append(fechaCreacion);
                                sb.append(";");
                            } else {
                                sb.append("");
                                sb.append(";");
                            }

                            String fechaProgramacion = "";
                            if (ot.getFechaProgramacion() != null) {
                                fechaProgramacion = sdf.format(ot.getFechaProgramacion());
                                sb.append(fechaProgramacion);
                                sb.append(";");
                            } else {
                                sb.append("");
                                sb.append(";");
                            }

                            String ans = "";
                            if (ot.getTipoOtObj() != null) {
                                ans = String.valueOf(ot.getTipoOtObj().getAns());
                                sb.append(ans);
                                sb.append(";");
                            } else {
                                sb.append("");
                                sb.append(";");
                            }

                            if (ot.getUsuarioCreacion() != null) {
                                sb.append(ot.getUsuarioCreacion());
                                sb.append(";");
                            } else {
                                sb.append("");
                                sb.append(";");
                            }

                            if (ot.getAns() != null) {
                                sb.append(ot.getAns());
                                sb.append(";");
                            } else {
                                sb.append("");
                                sb.append(";");
                            }

                            if (ot.getClaseOrdenTrabajo() != null) {
                                sb.append(ot.getClaseOrdenTrabajo().getNombreBasica());
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
                    if (fin >= total) {
                        continuar = false;
                    }

                    init = fin;
                    fin = fin + limit;
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
    
    
    
        public String desbloquearOrden(CmtOrdenTrabajoMgl orden) {
        try {
            if (orden != null && orden.getIdOt() != null) {
                ordenTrabajoMglFacadeLocal.bloquearDesbloquearOrden(orden, false);
            }
        } catch (ApplicationException e) {
            String msn = "Ocurrio un error desbloqueando la orden ";
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en OrdenTrabajoGestionarBean:desbloquearOrden. " + e.getMessage(), e, LOGGER);
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
    
    public boolean validarExportarRol() {
        return validarEdicion(ROLOPCEXPGEST);
    }
    
    public boolean validarIdOtRol() {
        return validarEdicion(ROLOPCIDGEST);
    }

    private boolean validarEdicion(String formulario) {
        try {
            boolean tieneRolPermitido = ValidacionUtil.validarVisualizacion(formulario, 
                    ValidacionUtil.OPC_EDICION, cmtOpcionesRolMglFacadeLocal, securityLogin);
            return tieneRolPermitido;
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError(Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION + e.getMessage(), e, LOGGER);
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al OrdenTrabajoGestionarBean. " + e.getMessage(), e, LOGGER);
        }
        return false;
    }

    public List<CmtOrdenTrabajoMgl> getOrdenTrabajoList() {
        return ordenTrabajoList;
    }

    public void setOrdenTrabajoList(List<CmtOrdenTrabajoMgl> ordenTrabajoList) {
        this.ordenTrabajoList = ordenTrabajoList;
    }

    public int getFilasPag() {
        return ConstantsCM.PAGINACION_QUINCE_FILAS;
    }

    public List<Integer> getPaginaList() {
        return paginaList;
    }

    public void setPaginaList(List<Integer> paginaList) {
        this.paginaList = paginaList;
    }

    public List<CmtBasicaMgl> getEstadoOtList() {
        return estadoOtList;
    }

    public void setEstadoOtList(List<CmtBasicaMgl> estadoOtList) {
        this.estadoOtList = estadoOtList;
    }

    public CmtBasicaMgl getEstadoOtSelected() {
        return estadoOtSelected;
    }

    public void setEstadoOtSelected(CmtBasicaMgl estadoOtSelected) {
        this.estadoOtSelected = estadoOtSelected;
    }

    public String getNumPagina() {
        return numPagina;
    }

    public void setNumPagina(String numPagina) {
        this.numPagina = numPagina;
    }

    public boolean isShowFooterTable() {
        return showFooterTable;
    }

    public void setShowFooterTable(boolean showFooterTable) {
        this.showFooterTable = showFooterTable;
    }

    public String getIdOtToFind() {
        return idOtToFind;
    }

    public void setIdOtToFind(String idOtToFind) {
        this.idOtToFind = idOtToFind;
    }

    public List<CmtBasicaMgl> getTecnologiaList() {
        return tecnologiaList;
    }

    public void setTecnologiaList(List<CmtBasicaMgl> tecnologiaList) {
        this.tecnologiaList = tecnologiaList;
    }

    public boolean isHabilitaObj() {
        return habilitaObj;
    }

    public void setHabilitaObj(boolean habilitaObj) {
        this.habilitaObj = habilitaObj;
    }

    public List<CmtRegionalRr> getRegionalList() {
        return regionalList;
    }

    public void setRegionalList(List<CmtRegionalRr> regionalList) {
        this.regionalList = regionalList;
    }

    public String getUsuarioSesion() {
        return usuarioSesion;
    }

    public void setUsuarioSesion(String usuarioSesion) {
        this.usuarioSesion = usuarioSesion;
    }

    public CmtFiltroConsultaOrdenesDto getFiltroConsultaOrdenesDto() {
        return filtroConsultaOrdenesDto;
    }

    public void setFiltroConsultaOrdenesDto(CmtFiltroConsultaOrdenesDto filtroConsultaOrdenesDto) {
        this.filtroConsultaOrdenesDto = filtroConsultaOrdenesDto;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
