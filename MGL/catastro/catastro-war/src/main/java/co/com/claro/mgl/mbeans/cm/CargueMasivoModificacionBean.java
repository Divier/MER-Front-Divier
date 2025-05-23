package co.com.claro.mgl.mbeans.cm;

import co.com.claro.mer.blockreport.BlockReportServBean;
import co.com.claro.mgl.businessmanager.cm.CmtTecnologiaSubMglManager;
import co.com.claro.mgl.dtos.CmtReporteCuentaMatrizDtoMgl;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.GeograficoPoliticoMglFacadeLocal;
import co.com.claro.mgl.facade.NodoMglFacadeLocal;
import co.com.claro.mgl.facade.ParametrosMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtCompaniaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtCuentaMatrizMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtSubEdificioMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTecnologiaSubMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoBasicaMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBlackListMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCompaniaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtSubEdificioMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTecnologiaSubMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoCompaniaMgl;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.CmtCargueMasivoEnum;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lombok.extern.log4j.Log4j2;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author valbuenayf
 */
@ManagedBean(name = "cargueMasivoModificacionBean")
@ViewScoped
@Log4j2
public class CargueMasivoModificacionBean implements Serializable {

    private static final long serialVersionUID = 1L;
    @EJB
    private CmtBasicaMglFacadeLocal cmtBasicaMglFacadeLocal;
    @EJB
    private NodoMglFacadeLocal nodoMglFacade;
    @EJB
    private CmtTecnologiaSubMglFacadeLocal tecnologiaSubMglFacade;
    @EJB
    private CmtCompaniaMglFacadeLocal companiaFacade;
    @EJB
    private GeograficoPoliticoMglFacadeLocal geograficoPoliticoMglFacadeLocal;
    @EJB
    private CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMglFacadeLocal;
    @EJB
    private ParametrosMglFacadeLocal parametrosFacade;
    @EJB
    private CmtSubEdificioMglFacadeLocal subEdificioMglFacadeLocal;
    @EJB
    private CmtCuentaMatrizMglFacadeLocal cmtCuentaMatrizMglFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacade;
    @Inject
    private BlockReportServBean blockReportServBean;
    
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private SecurityLogin securityLogin;
    private String usuarioVT = null;
    private int perfilVT = 0;
    private boolean generalDetalle;
    private Integer index;
    private String codigoNodo;
    private BigDecimal tecnologiaSelected;
    private BigDecimal blacklistSelected;
    private BigDecimal departamentoSelected;
    private BigDecimal ciudadSelected;
    private BigDecimal centroPobladoSelected;
    private List<CmtBasicaMgl> listBasicaTecnologia;
    private List<CmtBasicaMgl> listTipoProyecto;
    private List<CmtBasicaMgl> listOrigenDatos;
    private List<CmtBasicaMgl> listConfiguracion;
    private List<CmtBasicaMgl> listAlimentacion;
    private List<CmtBasicaMgl> listTipoDistribucion;
    private List<CmtBasicaMgl> listEstado;
    private List<CmtBasicaMgl> listTiposCcmm;
    private List<CmtBasicaMgl> listBlacklistTec;
    public List<SelectItem> departamentoList;
    public List<GeograficoPoliticoMgl> ciudadList;
    public List<SelectItem> centroPobladoList;
    private List<CmtBasicaMgl> listBasicaCargaMasiva;
    private List<SelectItem> cargaMasiva;
    List<CmtTecnologiaSubMgl> reporteGeneral;
    List<CmtTecnologiaSubMgl> reporteDetallado;
    List<CmtCompaniaMgl> listAdmin;
    List<CmtCompaniaMgl> listAscensor;
    List<CmtCompaniaMgl> listConstructora;
    private String carga;
    private String codigoSeleccion;
    private boolean nmc;
    private boolean cia;
    private boolean cis;
    private boolean adm;
    private boolean tpu;
    private boolean tpd;
    private boolean btp;
    private boolean bod;
    private boolean cnd;
    private boolean eti;
    private boolean btc;
    private boolean bae;
    private boolean btd;
    //Nuevos atributos
    private boolean tde;
    private boolean con;
    //Nuevos atributos
    private boolean consultar;
    //Filtros
    private String nombreCuentaFil;
    private BigDecimal idCompaniaAdminFil;
    private BigDecimal idCompaniaAscensorFil;
    private BigDecimal idCompaniaConstructoraFil;
    private String adminCompaniaFil;
    private String telefonoUnoFil;
    private String telefonoDosFil;
    private BigDecimal idCcmmRr;
    private BigDecimal idCcmmMgl;
    private String strIdCcmmRr;
    private String strIdCcmmMgl;
    private BigDecimal idTipoProyectoFil;
    private BigDecimal idOrigenDatosFil;
    private BigDecimal idNodoFil;
    private BigDecimal idEstadoTecnologiaFil;
    private BigDecimal idTipoConfDistbFil;
    private BigDecimal idAlimtElectFil;
    private BigDecimal idTipoDistribucionFil;
    private BigDecimal idTipoEdificioFil;

    private HashMap<String, Object> params;
    private Integer cantidadRegistros;
    private Integer count;
    private boolean hayRegistros;
    private String auxCabecera;
    private boolean filtroReporte;
    private boolean finish;
    private boolean progress;
    private String usuarioProceso;
    private String xlsMaxReg;
    private String csvMaxReg;
    private String txtMaxReg;
    private String codigoNodoParameter;
     //Opciones agregadas para Rol
    private final String GNPTBNACM = "GNPTBNACM";
    
    
    //
    private String estiloObligatorio = "<font size='3' color='red'>*</font>";
    private List<String> lstNodos;
    private boolean noCuentaRr = false;
    private boolean noCuentaMgl = false;
     private boolean btnReporte = true;
     
      String[] NOM_COLUMNAS = new String[]{Constant.TECNO_SUBEDIFICIO_ID, Constant.SUBEDIFICIO_ID,
            Constant.NOMBRE_SUBEDIFICIO, Constant.DIRECCION_SUBEDIFICIO,
            Constant.TIPO_SUBEDIFICIO, Constant.CUENTAMATRIZ_ID, Constant.NUMERO_CUENTA,
            Constant.TECNOLOGIA, Constant.DEPARTAMENTO, Constant.CIUDAD, Constant.CENTRO_POBLADO_ID,
            Constant.CENTRO_POBLADO, Constant.BARRIO,
            Constant.DIRECCION, Constant.NOMBRE, Constant.ADMINISTRACION, Constant.ASCENSOR, Constant.ADMINISTRADOR,
            Constant.TEL_UNO, Constant.TEL_DOS, Constant.PROYECTO, Constant.DATOS, Constant.NODO, Constant.ESTADO,
            Constant.CONFIGURACION, Constant.ALIMENTACION, Constant.DISTRIBUCION,
            Constant.TIPO_CCMM, Constant.CONSTRUCTORA, Constant.BLACKLIST_TECNOLOGIA,
            Constant.NOTA,  Constant.ID_BLACKLIST, Constant.FECHA_CREACION, Constant.FECHA_ULT_MOD};

    /**
     * Creates a new instance of CargueMasivoModificacionBean
     */
    public CargueMasivoModificacionBean() {
        index = 0;
        this.cantidadRegistros = 0;
        this.count = 0;
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
            if (usuarioVT == null) {
                session.getAttribute("usuarioM");
                usuarioVT = (String) session.getAttribute("usuarioM");
            } else {
                session = (HttpSession) facesContext.getExternalContext().getSession(false);
                session.setAttribute("usuarioM", usuarioVT);
            }
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Se generea error en CargueMasivoModificacionBean() ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en CargueMasivoModificacionBean() ..." + e.getMessage(), e, LOGGER);
        }
    }

    @PostConstruct
    public void init() {
        try {
            this.centroPobladoSelected = BigDecimal.ZERO;    
            limpiarValores();
            resetFilterDetallado();
            reporteDetallado = null;
            reporteGeneral = null;
            setGeneralDetalle(false);
            setCodigoNodo(null);
            setProgress(false);
            setFinish(false);
            cargarListas();
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en Cargue Masivo en CargueMasivoModificacionBean: init() ..." + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * valbuenayf metodo para cargar todas las listas
     */
    private void cargarListas() {
        try {
            if (listBasicaTecnologia == null || listBasicaTecnologia.isEmpty()) {
                CmtTipoBasicaMgl tipoBasicaTecnologia;
                tipoBasicaTecnologia = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                        Constant.TIPO_BASICA_TECNOLOGIA);
                listBasicaTecnologia = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasicaTecnologia);
            }

            if (listBasicaCargaMasiva == null || listBasicaCargaMasiva.isEmpty()) {
                cargaMasiva = new ArrayList<SelectItem>();
                CmtTipoBasicaMgl tipoBasicaCargaMasiva;
                tipoBasicaCargaMasiva = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                        Constant.TIPO_BASICA_ATRIBUTOS_CAMBIO_MASIVO);
                listBasicaCargaMasiva = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasicaCargaMasiva);
                for (CmtBasicaMgl c : listBasicaCargaMasiva) {
                    cargaMasiva.add(new SelectItem(c.getAbreviatura(), c.getNombreBasica()));
                }
            }

            if (departamentoList == null || departamentoList.isEmpty()) {

                List< GeograficoPoliticoMgl> departamentoAux = geograficoPoliticoMglFacadeLocal.findDptos();

                if (departamentoAux != null || !departamentoAux.isEmpty()) {
                    departamentoList = new ArrayList<SelectItem>();
                    for (GeograficoPoliticoMgl d : departamentoAux) {
                        departamentoList.add(new SelectItem(d.getGpoId(), d.getGpoNombre()));
                    }
                }
            }

            if (listTipoProyecto == null || listTipoProyecto.isEmpty()) {
                CmtTipoBasicaMgl tipoBasicaTecnologia;
                tipoBasicaTecnologia = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                        Constant.TIPO_BASICA_TIPO_DE_PROYECTO);
                listTipoProyecto = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasicaTecnologia);
            }

            if (listOrigenDatos == null || listOrigenDatos.isEmpty()) {
                CmtTipoBasicaMgl tipoBasicaTecnologia
                        = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                                Constant.TIPO_BASICA_ORIGEN_DE_DATOS);
                listOrigenDatos = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasicaTecnologia);
            }

            if (listTiposCcmm == null || listTiposCcmm.isEmpty()) {
                CmtTipoBasicaMgl tipoBasicaTecnologia
                        = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                                Constant.TIPO_BASICA_TIPO_CUENTA_MATRIZ);
                listTiposCcmm = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasicaTecnologia);
            }

            if (listConfiguracion == null || listConfiguracion.isEmpty()) {
                CmtTipoBasicaMgl tipoBasicaTecnologia;
                tipoBasicaTecnologia = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                        Constant.TIPO_BASICA_CONFIGURACION_DISTRIBUCION);
                listConfiguracion = cmtBasicaMglFacadeLocal.findByTipoBasica(
                        tipoBasicaTecnologia);
            }

            if (listAlimentacion == null || listAlimentacion.isEmpty()) {
                CmtTipoBasicaMgl tipoBasicaTecnologia;
                tipoBasicaTecnologia = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                        Constant.TIPO_BASICA_TIPO_DE_ALIMENTACION_ELECTRICA);
                listAlimentacion = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasicaTecnologia);
            }

            if (listTipoDistribucion == null || listTipoDistribucion.isEmpty()) {
                CmtTipoBasicaMgl tipoBasicaTecnologia;
                tipoBasicaTecnologia = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                        Constant.TIPO_BASICA_DISTRIBUCION);
                listTipoDistribucion = cmtBasicaMglFacadeLocal.findByTipoBasica(
                        tipoBasicaTecnologia);
            }

            if (listBlacklistTec == null || listBlacklistTec.isEmpty()) {
                CmtTipoBasicaMgl tipoBasicaTecnologia;
                tipoBasicaTecnologia = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                        Constant.TIPO_BASICA_BLACK_LIST_CM);
                listBlacklistTec = cmtBasicaMglFacadeLocal.findByTipoBasica(
                        tipoBasicaTecnologia);
            }

            if (listEstado == null || listEstado.isEmpty()) {
                CmtTipoBasicaMgl tipoBasicaTecnologia
                        = cmtTipoBasicaMglFacadeLocal.findByCodigoInternoApp(
                                Constant.TIPO_BASICA_ESTADO_CUENTA_MATRIZ);
                listEstado = cmtBasicaMglFacadeLocal.findByTipoBasica(tipoBasicaTecnologia);
            }

            if (listAscensor == null || listAscensor.isEmpty()) {
                CmtTipoCompaniaMgl cmtTipoCompaniaMgl = new CmtTipoCompaniaMgl();
                cmtTipoCompaniaMgl.setTipoCompaniaId(Constant.TIPO_COMPANIA_ID_ASCENSORES);
                listAscensor = companiaFacade.findByTipoCompania(cmtTipoCompaniaMgl);
            }

            if (listAdmin == null || listAdmin.isEmpty()) {
                CmtTipoCompaniaMgl cmtTipoCompaniaMgl = new CmtTipoCompaniaMgl();
                cmtTipoCompaniaMgl.setTipoCompaniaId(Constant.TIPO_COMPANIA_ID_ADMINISTRACION);
                listAdmin = companiaFacade.findByTipoCompania(cmtTipoCompaniaMgl);
            }

            if (listConstructora == null || listConstructora.isEmpty()) {
                CmtTipoCompaniaMgl cmtTipoCompaniaMgl = new CmtTipoCompaniaMgl();
                cmtTipoCompaniaMgl.setTipoCompaniaId(Constant.TIPO_COMPANIA_ID_CONSTRUCTORAS);
                listConstructora = companiaFacade.findByTipoCompania(cmtTipoCompaniaMgl);
            }
        } catch (ApplicationException ex) {
            LOGGER.error("Se genero error en cargarListas en CargueMasivoModificacionBean: cargarListas() ...", ex);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en CargueMasivoModificacionBean: cargarListas() ..." + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * valbuenayf metodo para hacer el cambio del tipo de reporte
     *
     */
    public void cambioGeneraldetallado() {
        if (getIndex() == 1) {
            setGeneralDetalle(true);
        } else {
            setGeneralDetalle(false);
        }
        resetFilterDetallado();
        setHayRegistros(false);
    }

    /**
     * valbuenayf metodo para generar el reporte general y detallado
     *
     */
    public void generaReporte() {
        boolean isNodo = false;
        params = null;
        this.reporteDetallado = null;
        this.reporteGeneral = null;
        try {
            // Se verifica si está bloqueada la generación de reportes y si
            // el usuario en sesión está autorizado para realizar el proceso.
            if (blockReportServBean.isReportGenerationBlock("Reporte CONSULTA ATRIBUTOS CUENTA MATRIZ")) return;

            if (tecnologiaSelected == null) {
                String msn1 = Constant.MSG_TIPO_TECNOLOGIA_OBLIGATORIO;
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, msn1, null));
                return;
            }
            //se realiza validacion de los departamentos por incidencia reportada el 29/05/2018 espinosadiea
            if (this.departamentoSelected.compareTo(BigDecimal.ZERO) == 0) {
                String msn1 = Constant.MSG_DEPARTAMENTO_OBLIGATORIO;
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, msn1, null));
                return;
            }

            if (ciudadSelected == null) {
                String msn1 = Constant.MSG_CIUDAD_OBLIGATORIO;
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, msn1, null));
                return;
            }

            if (centroPobladoSelected == null) {
                centroPobladoSelected = new BigDecimal(BigInteger.ZERO);
            }

            if (blacklistSelected == null) {
                blacklistSelected = new BigDecimal(BigInteger.ZERO);
            }

            if (this.strIdCcmmRr == null || this.strIdCcmmRr.isEmpty()) {
                idCcmmRr = new BigDecimal(BigInteger.ZERO);
            } else {
                try {
                    idCcmmRr = new BigDecimal(strIdCcmmRr);
                } catch (NumberFormatException ex) {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN, "El id Ccmm Rr es numerico", null));
                    noCuentaRr = true;
                }
            }

            if (noCuentaRr) {
                return;
            }

            if (this.strIdCcmmMgl == null || this.strIdCcmmMgl.isEmpty()) {
                idCcmmMgl = new BigDecimal(BigInteger.ZERO);
            } else {
                try {
                    idCcmmMgl = new BigDecimal(strIdCcmmMgl);
                } catch (NumberFormatException ex) {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN, "El id Ccmm Mgl es numerico", null));
                    noCuentaMgl = true;
                }
            }
            if (noCuentaMgl) {
                return;
            }

            if (tecnologiaSelected != null && centroPobladoSelected != null
                    && centroPobladoSelected.compareTo(BigDecimal.ZERO) != 0
                    && codigoNodo != null && !codigoNodo.trim().isEmpty()) {
                if (validarNodo()) {
                    isNodo = true;
                } else {
                    return;
                }
            }
            params = new HashMap<String, Object>();
            params.put(Constant.CAR_MAS_BLACKLIST_TECNOLOGIA, this.blacklistSelected);
            params.put(Constant.CAR_MAS_ID_CCMM_RR, this.idCcmmRr);
            params.put(Constant.CAR_MAS_ID_CCMM_MGL, this.idCcmmMgl);
            params.put(Constant.CAR_MAS_ID_TECNOLOGIA, this.tecnologiaSelected);
            params.put(Constant.CAR_MAS_ID_CIUDAD, this.ciudadSelected);
            params.put(Constant.CAR_MAS_ID_CENTRO_POBLADO, this.centroPobladoSelected);
            if (isNodo) {
                params.put(Constant.CAR_MAS_CODIGO_NODO, this.codigoNodo);
            }
            //index igual a UNO es para detallado 
            if (index != null && index == 1) { 
                if (this.codigoSeleccion == null || this.codigoSeleccion.isEmpty()) {
                    String msn3 = Constant.MSN_ATRIBUTO_CCMM_OBLIGATORIO;
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN, msn3, null));
                    return;
                } else {
                    if (this.codigoSeleccion.equalsIgnoreCase(Constant.ABREVIATURA_BASICA_BLK_LIST_TEC)
                            && (blacklistSelected.equals(BigDecimal.ZERO) || blacklistSelected == null)) {
                        String msn3 = "Para generar reporte de BlackList Tecnología es "
                                + "necesario seleccionar un valor del filtro.";
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_WARN, msn3, null));
                        return;
                    }
                }

                params.put(Constant.CAR_MAS_REPORTE, "0");
                HashMap<String, Object> auxPara = buscar();
                if (auxPara == null) {
                    return;
                }
                params.putAll(auxPara);
            } //index igual a CERO es para general 
            else if (index != null && index == 0) {
                params.put(Constant.CAR_MAS_REPORTE, "1");

            }
            
            tecnologiaSubMglFacade.findReporteGeneralDetallado(params, usuarioVT);
            this.cantidadRegistros = tecnologiaSubMglFacade.findCountRepGeneralDetallado(params);
            if (this.cantidadRegistros == 0) { 
                btnReporte = true;
                String msn5 = "Para la consulta indicada no se encuentra información a generar.";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, msn5, null));
                return;
            } else {        
                btnReporte = false;
                setHayRegistros(false);
                setFiltroReporte(false);                
                xlsMaxReg = this.cantidadRegistros > 2000 ? Constant.EXPORT_XLS_MAX : "";               
                FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("idFormCarque");
                FacesContext.getCurrentInstance().getPartialViewContext().setRenderAll(true);
            }
        } catch (Exception e) {
            LOGGER.error("Se genero error en generando reporte  en CargueMasivoModificacionBean: generaReporte() ...", e);
        }
    }

    /**
     * valbuenayf metodo para asignar los parametros de busqueda
     *
     * @return
     */
    private HashMap<String, Object> buscar() {
        HashMap<String, Object> parametro = new HashMap<String, Object>();
        String msn;
        switch (CmtCargueMasivoEnum.getCodigoId(this.codigoSeleccion)) {
            case 1:
                if (this.nombreCuentaFil == null || this.nombreCuentaFil.isEmpty()) {
                    msn = Constant.MSN_NOMBRE_CUENTA;
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, null));
                    return null;
                }
                auxCabecera = Constant.NOMBRE;
                parametro.put(Constant.CAR_MAS_CUENTA, this.nombreCuentaFil);
                break;
            case 2:
                if (this.idCompaniaAdminFil == null) {
                    msn = Constant.MSN_COMPANIA_ID_ADMINISTRACION;
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, null));
                    return null;
                }
                auxCabecera = Constant.ADMINISTRACION;
                parametro.put(Constant.CAR_MAS_COMPANIA, this.idCompaniaAdminFil);
                break;
            case 3:
                if (this.idCompaniaAscensorFil == null) {
                    msn = Constant.MSN_COMPANIA_ID_ASCENSOR;
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, null));
                    return null;
                }
                auxCabecera = Constant.ASCENSOR;
                parametro.put(Constant.CAR_MAS_ASCENSORES, this.idCompaniaAscensorFil);
                break;
            case 4:
                if (this.adminCompaniaFil == null || this.adminCompaniaFil.isEmpty()) {
                    msn = Constant.MSN_ADMINISTRADOR;
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, null));
                    return null;
                }
                auxCabecera = Constant.ADMINISTRADOR;
                parametro.put(Constant.CAR_MAS_ADMINISTRADOR, this.adminCompaniaFil);
                break;
            case 5:
                if (this.telefonoUnoFil == null || this.telefonoUnoFil.isEmpty()) {
                    msn = Constant.MSN_TELEFONO_PORTERIA;
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, null));
                    return null;
                } else {
                    try {
                        Long numero = Long.valueOf(telefonoUnoFil);
                        if (String.valueOf(numero).length() < 7 || String.valueOf(numero).length() > 10) {
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "El telefono debe tener 7 o 10 digitos", null));
                            return null;
                        }
                    } catch (NumberFormatException ex) {
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR, "El telefono es numerico", null));
                        return null;
                    }
                }
                auxCabecera = Constant.TEL_UNO;
                parametro.put(Constant.CAR_MAS_TEL_UNO, this.telefonoUnoFil);
                break;
            case 6:
                if (this.telefonoDosFil == null || this.telefonoDosFil.isEmpty()) {
                    msn = Constant.MSN_TELEFONO_PORTERIA2;
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, null));
                    return null;
                } else {
                    try {
                        Long numero = Long.valueOf(telefonoDosFil);
                        if (String.valueOf(numero).length() < 7 || String.valueOf(numero).length() > 10) {
                            FacesContext.getCurrentInstance().addMessage(null,
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "El telefono debe tener 7 o 10 digitos", null));
                            return null;
                        }
                    } catch (NumberFormatException ex) {
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR, "El telefono es numerico", null));
                        return null;
                    }
                }
                auxCabecera = Constant.TEL_DOS;
                parametro.put(Constant.CAR_MAS_TEL_DOS, this.telefonoDosFil);
                break;
            case 7:
                if (this.idTipoProyectoFil == null) {
                    msn = Constant.MSN_BASICA_ID_TIPO_PROYECTO;
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, null));
                    return null;
                }
                auxCabecera = Constant.PROYECTO;
                parametro.put(Constant.CAR_MAS_PROYECTO, this.idTipoProyectoFil);
                break;
            case 8:
                if (this.idOrigenDatosFil == null) {
                    msn = Constant.MSN_BASICA_ID_ORIGEN_DATOS;
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, null));
                    return null;
                }
                auxCabecera = Constant.DATOS;
                parametro.put(Constant.CAR_MAS_DATOS, this.idOrigenDatosFil);
                break;
            case 9:
                if (this.codigoNodoParameter == null || this.codigoNodoParameter.isEmpty()) {
                    msn = Constant.MSN_CODIGO_NODO;
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, null));
                    return null;
                }
                auxCabecera = Constant.NODO;
                parametro.put(Constant.CAR_MAS_NODO, this.codigoNodoParameter);
                break;
            case 10:
                if (this.idEstadoTecnologiaFil == null) {
                    msn = Constant.MSN_ESTADO_TECNOLOGIA_ID;
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, null));
                    return null;
                }
                auxCabecera = Constant.ESTADO;
                parametro.put(Constant.CAR_MAS_ESTADO, this.idEstadoTecnologiaFil);
                break;
            case 11:
                if (this.idTipoConfDistbFil == null) {
                    msn = Constant.MSN_BASICA_ID_TIPO_CONF_DISTB;
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, null));
                    return null;
                }
                auxCabecera = Constant.CONFIGURACION;
                parametro.put(Constant.CAR_MAS_CONFIGURACION, this.idTipoConfDistbFil);
                break;
            case 12:
                if (this.idAlimtElectFil == null) {
                    msn = Constant.MSN_BASICA_ID_ALIMT_ELECT;
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, null));
                    return null;
                }
                auxCabecera = Constant.ALIMENTACION;
                parametro.put(Constant.CAR_MAS_ALIMENTACION, this.idAlimtElectFil);
                break;
            case 13:
                if (this.idTipoDistribucionFil == null) {
                    msn = Constant.MSN_BASICA_ID_TIPO_DISTRIBUCION;
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, null));
                    return null;
                }
                auxCabecera = Constant.DISTRIBUCION;
                parametro.put(Constant.CAR_MAS_DSITRIBUCION, this.idTipoDistribucionFil);
                break;
            case 14:
                if (this.idTipoEdificioFil == null) {
                    msn = Constant.MSN_BASICA_ID_TIPO_CCMM;
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, null));
                    return null;
                }
                auxCabecera = Constant.TIPO_CCMM;
                parametro.put(Constant.CAR_MAS_TIPO_CCMM, this.idTipoEdificioFil);
                break;
            case 15:
                if (this.idCompaniaConstructoraFil == null) {
                    msn = Constant.MSN_COMPANIA_ID_CONSTRUCTORA;
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, null));
                    return null;
                }
                auxCabecera = Constant.CONSTRUCTORA;
                parametro.put(Constant.CAR_MAS_CONSTRUCTORA, this.idCompaniaConstructoraFil);
                break;
            default:
                break;
        }
        return parametro;
    }

    /**
     * valbuenayf metodo para validar el nodo
     *
     * @return
     */
    private boolean validarNodo() {
        boolean respuesta = false;
        try {
            NodoMgl nodo = nodoMglFacade.findByCodigoNodo(codigoNodo, centroPobladoSelected, tecnologiaSelected);
            if (nodo != null && nodo.getNodId() != null) {
                respuesta = true;
            } else {
                String msn2 = Constant.MSG_NODO_NO_VALIDO;
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, msn2, null));
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se generea error validando nodo en CargueMasivoModificacionBean: validarNodo() ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error validando nodo en CargueMasivoModificacionBean: validarNodo() ..." + e.getMessage(), e, LOGGER);
        }
        return respuesta;
    }

    /**
     * valbuenayf metodo para resetear los parametros de busqueda
     */
    public void limpiarValores() {
        setCantidadRegistros(0);
        setHayRegistros(false);
        setFiltroReporte(true);
        setIndex(0);
        setDepartamentoSelected(BigDecimal.ZERO);
        setCiudadSelected(BigDecimal.ZERO);
        this.ciudadList = null;
        this.centroPobladoList = null;
        setCentroPobladoSelected(null);
        setTecnologiaSelected(null);
        setCodigoSeleccion(null);
        setBlacklistSelected(null);
        setStrIdCcmmRr("");
        setStrIdCcmmMgl("");
    }
    
    public void limpiar() throws IOException, ApplicationException {

        setCantidadRegistros(0);
        setHayRegistros(false);
        setFiltroReporte(true);
        setIndex(0);
        setDepartamentoSelected(BigDecimal.ZERO);
        setCiudadSelected(BigDecimal.ZERO);
        this.ciudadList = null;
        this.centroPobladoList = null;
        setCentroPobladoSelected(null);
        setTecnologiaSelected(null);
        setCodigoSeleccion(null);
        setBlacklistSelected(null);
        setStrIdCcmmRr("");
        setStrIdCcmmMgl("");
        btnReporte = true;
        setHayRegistros(false);
        setFiltroReporte(false);
        xlsMaxReg = this.cantidadRegistros > 2000 ? Constant.EXPORT_XLS_MAX : "";
        FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("idFormCarque");
        

    }

    /**
     * valbuenayf metodo para reseterar los valores del filtro
     */
    private void resetFilterDetallado() {
        nmc = false;
        cia = false;
        cis = false;
        adm = false;
        tpu = false;
        tpd = false;
        btp = false;
        bod = false;
        cnd = false;
        eti = false;
        btc = false;
        bae = false;
        btd = false;
        tde = false;
        con = false;
        consultar = false;
        nombreCuentaFil = null;
        idCompaniaAdminFil = null;
        idCompaniaAscensorFil = null;
        idCompaniaConstructoraFil = null;
        adminCompaniaFil = null;
        telefonoUnoFil = null;
        telefonoDosFil = null;
        idTipoProyectoFil = null;
        idOrigenDatosFil = null;
        idNodoFil = null;
        idEstadoTecnologiaFil = null;
        idTipoConfDistbFil = null;
        idAlimtElectFil = null;
        idTipoDistribucionFil = null;
        idTipoEdificioFil = null;
        cantidadRegistros = 0;
        codigoSeleccion = null;
        auxCabecera = null;
        idCcmmRr = null;
        idCcmmMgl = null;
    }

    /**
     * valbuenayf metodo para la seleccion del filtro de la columna
     *
     */
    public void consultaSelect() {
        try {
            if (this.codigoSeleccion == null || this.codigoSeleccion.isEmpty()) {
                LOGGER.error("Error -->>>");
                return;
            }
            Integer codigoId = CmtCargueMasivoEnum.getCodigoId(this.codigoSeleccion);
            if (codigoId > 15) {
                return;
            }
            renderValores(codigoId);
        } catch (Exception e) {
            LOGGER.error("Se genero error en la consulta de seleccion en CargueMasivoModificacionBean: consultaSelect() ...", e);
        }
    }

    /**
     * valbuenayf metodo para asignar un valor de true solo a un atributo
     *
     * @param num
     */
    private void renderValores(Integer num) {
        nmc = num == 1;
        cia = num == 2;
        cis = num == 3;
        adm = num == 4;
        tpu = num == 5;
        tpd = num == 6;
        btp = num == 7;
        bod = num == 8;
        cnd = num == 9;
        eti = num == 10;
        btc = num == 11;
        bae = num == 12;
        btd = num == 13;
        tde = num == 14;
        con = num == 15;
        consultar = true;
        nombreCuentaFil = null;
        idCompaniaAdminFil = null;
        idCompaniaAscensorFil = null;
        idCompaniaConstructoraFil = null;
        adminCompaniaFil = null;
        telefonoUnoFil = null;
        telefonoDosFil = null;
        idTipoProyectoFil = null;
        idOrigenDatosFil = null;
        idNodoFil = null;
        idEstadoTecnologiaFil = null;
        idTipoConfDistbFil = null;
        idAlimtElectFil = null;
        idTipoDistribucionFil = null;
        idTipoEdificioFil = null;
        idCcmmRr = null;
        idCcmmMgl = null;
    }

    /**
     * valbuenayf metodo para llamar los respectivos tipos de descargar de
     * archivos
     *
     * @param num
     */
    public void generarReporteFinal(int num) throws IOException, ApplicationException {
        boolean reporteGeneral = false;
        boolean reporteDetallado = false;
        if (this.index == 1) {
            reporteDetallado = true;
            reporteGeneral = false;       
        } else if (this.index == 0) {
            reporteGeneral = true;
            reporteDetallado = false;          
        }
        switch (num) {
            case 1:
                //excel
                if (reporteGeneral) {
                    exportarArchivoXlsGeneral();
                } else if (reporteDetallado) {
                    exportarArchivoXlsDetallado();
                }
                break;
            case 2:
                //CVS
                if (reporteGeneral) {
                    exportCsvTxt("csv");
                } else if (reporteDetallado) {
                    exportCsvTxtDetallado("csv");
                }
                break;
            case 3:
                //TXT
                if (reporteGeneral) {
                    exportCsvTxt("txt");
                } else if (reporteDetallado) {
                    exportCsvTxtDetallado("txt");
                }
                break;
        }
    }
    
 //Inicio Reporte General 
    /**
     * valbuenayf metodo para descargar el archivo xls de reporte general
     *
     * @param reporte
     */
    private void exportarArchivoXlsGeneral() {
        try {
            
            CmtTecnologiaSubMglManager cmMglManager = new CmtTecnologiaSubMglManager(); 
            List<CmtTecnologiaSubMgl> reporteGeneralList = new ArrayList();
            params.put(Constant.CAR_MAS_REPORTE, "0");
            reporteGeneralList = cmMglManager.findReporteGeneralDetallado(params, 0, 2000);
  
            if (reporteGeneralList != null && !reporteGeneralList.isEmpty()) {
                //Blank workbook            
                HSSFWorkbook workbook = new HSSFWorkbook();
                Map<String, Object[]> mapDataEstado = new TreeMap<String, Object[]>();
                int fila = 1;
                //se ingresa la cabecera de los datos
                mapDataEstado.put(String.valueOf(fila++), getCabeceraXls());
                for (CmtTecnologiaSubMgl e : reporteGeneralList) {
                    String idTecnologiaSub = "";
                    String idSubEdificio = "";
                    String nombreSubEdificio = "";
                    String dirSubEdificio = "";
                    String tipoSubedificio = "";
                    String idCuentaMatriz = "";
                    String numeroCuenta = "";
                    String tecnologia = "";
                    String departamento = "";
                    String ciudad = "";
                    String centroPoblado = "";
                    String idCentroPoblado = "";
                    String barrio = "";
                    String direccion = "";
                    String nombreCuenta = "";
                    String idComAdmin = "";
                    String idComAsc = "";
                    String admin = "";
                    String teleUno = "";
                    String teleDos = "";
                    String idProyecto = "";
                    String idDatos = "";
                    String idNodo = "";
                    String idEstado = "";
                    String idConfiguracion = "";
                    String idAlimentacion = "";
                    String idDistribucion = "";
                    String tipoCcmm="";
                    String idComCon="";
                    String nombreBlackTec="";
                    String idBlackTec="";
                    String fechaCreacion = "";
                    String fechaUltMod = "";
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                    if (e.getTecnoSubedificioId() != null) {
                        idTecnologiaSub = e.getTecnoSubedificioId().toString();
                        if (e.getBasicaIdTecnologias() != null) {
                            tecnologia = e.getBasicaIdTecnologias().getNombreBasica();
                        }
                        if (e.getSubedificioId() != null && e.getSubedificioId().getSubEdificioId() != null) {
                            idSubEdificio = e.getSubedificioId().getSubEdificioId().toString();
                            if (e.getSubedificioId().getCmtCuentaMatrizMglObj() != null && e.getSubedificioId().getCmtCuentaMatrizMglObj().getCuentaMatrizId() != null) {
                                nombreSubEdificio = e.getSubedificioId().getNombreSubedificio();
                                if (e.getSubedificioId().getDireccion() != null) {
                                    dirSubEdificio = e.getSubedificioId().getDireccion();
                                }
                                CmtSubEdificioMgl subEdificioGenralRr = subEdificioMglFacadeLocal.findById(e.getSubedificioId().getSubEdificioId());
                                if (subEdificioGenralRr != null && subEdificioGenralRr.getSubEdificioId() != null
                                        && subEdificioGenralRr.getEstadoSubEdificioObj() != null && subEdificioGenralRr.getEstadoSubEdificioObj().getIdentificadorInternoApp() != null
                                        && subEdificioGenralRr.getEstadoSubEdificioObj().getIdentificadorInternoApp().equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO)) {
                                    tipoSubedificio = "CM";
                                } else {
                                    long edificios = subEdificioMglFacadeLocal.countSubEdificiosCuentaMatriz(e.getSubedificioId().getCmtCuentaMatrizMglObj().getCuentaMatrizId());
                                    if (edificios == 1) {
                                        tipoSubedificio = "CM";
                                    } else {
                                        tipoSubedificio = "SUBEDIFICIO";
                                    }
                                }
                                departamento = e.getSubedificioId().getCmtCuentaMatrizMglObj().getDepartamento().getGpoNombre();
                                idCuentaMatriz = e.getSubedificioId().getCmtCuentaMatrizMglObj().getCuentaMatrizId().toString();
                                numeroCuenta = e.getSubedificioId().getCmtCuentaMatrizMglObj().getNumeroCuenta().toString();
                            }
                        }
                    }
                    if (e.getSubedificioId() != null) {
                        if (e.getSubedificioId().getCuentaMatrizObj() != null
                                && e.getSubedificioId().getCuentaMatrizObj().getMunicipio() != null) {
                            ciudad = e.getSubedificioId().getCuentaMatrizObj().getMunicipio().getGpoNombre();
                        }
                        if (e.getSubedificioId().getCuentaMatrizObj() != null
                                && e.getSubedificioId().getCuentaMatrizObj().getCentroPoblado() != null) {
                            centroPoblado = e.getSubedificioId().getCuentaMatrizObj().getCentroPoblado().getGpoNombre();
                            idCentroPoblado = e.getSubedificioId().getCuentaMatrizObj().getCentroPoblado().getGpoId().toString();
                        }

                        if (e.getSubedificioId().getCuentaMatrizObj() != null) {
                            if (e.getSubedificioId().getCuentaMatrizObj().getDireccionPrincipal() != null) {
                                direccion = e.getSubedificioId().getCuentaMatrizObj().getDireccionPrincipal().getDireccionObj().getDirFormatoIgac();
                                barrio = e.getSubedificioId().getCuentaMatrizObj().getDireccionPrincipal().getBarrio();
                            }
                        }

                        if (e.getSubedificioId().getCuentaMatrizObj() != null) {
                            nombreCuenta = e.getSubedificioId().getCuentaMatrizObj().getNombreCuenta();
                        }
                        if (e.getSubedificioId().getCompaniaAdministracionObj() != null) {
                            idComAdmin = e.getSubedificioId().getCompaniaAdministracionObj().getNombreCompania();
                        }
                        if (e.getSubedificioId().getCompaniaAscensorObj() != null) {
                            idComAsc = e.getSubedificioId().getCompaniaAscensorObj().getNombreCompania();
                        }
                        admin = e.getSubedificioId().getAdministrador();
                        teleUno = e.getSubedificioId().getTelefonoPorteria();
                        teleDos = e.getSubedificioId().getTelefonoPorteria2();
                        if (e.getSubedificioId().getTipoProyectoObj() != null) {
                            idProyecto = e.getSubedificioId().getTipoProyectoObj().getNombreBasica();
                        }
                        if (e.getSubedificioId().getOrigenDatosObj() != null) {
                            idDatos = e.getSubedificioId().getOrigenDatosObj().getNombreBasica();
                        }
                    }
                    if (e.getNodoId() != null) {
                        idNodo = e.getNodoId().getNodCodigo();
                    }
                    if (e.getBasicaIdEstadosTec() != null) {
                        idEstado = e.getBasicaIdEstadosTec().getNombreBasica();
                    }
                    if (e.getTipoConfDistribObj() != null) {
                        idConfiguracion = e.getTipoConfDistribObj().getNombreBasica();
                    }
                    if (e.getAlimentElectObj() != null) {
                        idAlimentacion = e.getAlimentElectObj().getNombreBasica();
                    }
                    if (e.getTipoDistribucionObj() != null) {
                        idDistribucion = e.getTipoDistribucionObj().getNombreBasica();
                    }
                    
                    if (e.getSubedificioId().getTipoEdificioObj() != null) {
                        tipoCcmm = e.getSubedificioId().getTipoEdificioObj().getNombreBasica();
                    }

                    if (e.getSubedificioId().getCompaniaConstructoraObj() != null) {
                        idComCon = e.getSubedificioId().getCompaniaConstructoraObj().getNombreCompania();
                    }
                    
                    if (params.get(Constant.CAR_MAS_BLACKLIST_TECNOLOGIA) != null
                           && new BigDecimal(params.get(Constant.CAR_MAS_BLACKLIST_TECNOLOGIA).toString()).compareTo(BigDecimal.ZERO) != 0) {

                        if (e.getSubedificioId().getListCmtBlackListMgl() != null
                                && !e.getSubedificioId().getListCmtBlackListMgl().isEmpty()) {

                            for (CmtBlackListMgl blackListMgl : e.getSubedificioId().getListCmtBlackListMgl()) {
                                BigDecimal idBack = null;
                                if (params.get(Constant.CAR_MAS_BLACKLIST_TECNOLOGIA) instanceof BigDecimal) {
                                    idBack = (BigDecimal) params.get(Constant.CAR_MAS_BLACKLIST_TECNOLOGIA);
                                    if ((blackListMgl.getBlackListObj().getBasicaId().compareTo(idBack) == 0)) {                                  
                                        nombreBlackTec = blackListMgl.getBlackListObj().getNombreBasica();
                                        idBlackTec = blackListMgl.getBlackListId().toString();
                                    }
                                }
                            }
                        }
                    }
    
                     
                    if (e.getSubedificioId().getCmtCuentaMatrizMglObj() != null
                            && e.getSubedificioId().getCmtCuentaMatrizMglObj().getFechaCreacion() != null) {

                        fechaCreacion = format.format(e.getSubedificioId().getCmtCuentaMatrizMglObj().getFechaCreacion());
                    }

                    if (e.getSubedificioId().getCmtCuentaMatrizMglObj() != null
                            && e.getSubedificioId().getCmtCuentaMatrizMglObj().getFechaEdicion() != null) {

                        fechaUltMod = format.format(e.getSubedificioId().getCmtCuentaMatrizMglObj().getFechaEdicion());
                    }
                    Object[] registros = new Object[]{idTecnologiaSub, idSubEdificio, nombreSubEdificio,
                        dirSubEdificio, tipoSubedificio, idCuentaMatriz, numeroCuenta, tecnologia,
                        departamento, ciudad, idCentroPoblado, centroPoblado, barrio, direccion,
                        nombreCuenta, idComAdmin, idComAsc, admin, teleUno, teleDos, idProyecto, idDatos,
                        idNodo, idEstado, idConfiguracion, idAlimentacion, idDistribucion, tipoCcmm, 
                        idComCon, nombreBlackTec, "", idBlackTec, fechaCreacion, fechaUltMod};
                    mapDataEstado.put(String.valueOf(fila++), registros);
                }
                fillSheetbook(workbook, Constant.SHEET_NAME, mapDataEstado);
                FacesContext fc = FacesContext.getCurrentInstance();
                HttpServletResponse responseRls = (HttpServletResponse) fc.getExternalContext().getResponse();
                responseRls.reset();
                responseRls.setContentType("application/vnd.ms-excel");
                responseRls.setCharacterEncoding("UTF-8");
                SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss-a");
                responseRls.setHeader("Content-Disposition", "attachment; filename=ReporteGeneralCCMM_" + formato.format(new Date()) + ".xls");
                OutputStream output = responseRls.getOutputStream();
                workbook.write(output);
                output.flush();
                output.close();
                fc.responseComplete();
            } else {
                String msn = "No Existen Registros a exportar";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
            }
        } catch (ApplicationException | IOException e) {
            FacesUtil.mostrarMensajeError("Ocurrio un error en la creacion del archivo excel en CargueMasivoModificacionBean: exportarArchivoXlsGeneral()" + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Ocurrio un error en la creacion del archivo excel en CargueMasivoModificacionBean: exportarArchivoXlsGeneral()" + e.getMessage(), e, LOGGER);
        }
    }
    
    
    
     public String exportCsvTxt(String ext) throws ApplicationException {
        try {
            CmtTecnologiaSubMglManager cmMglManager = new CmtTecnologiaSubMglManager(); 
            List<CmtTecnologiaSubMgl> reporteGeneralList = new ArrayList();
            //Cantidad de registros por pagina a consultar de la DB            
            int expLonPag = 50;
            
            //numero total de registros del reporte
         
            long totalPag = this.cantidadRegistros;
            StringBuilder sb = new StringBuilder();
            byte[] csvData;
            SimpleDateFormat formato = new SimpleDateFormat("dd_MMM_yyyy-HH:mm:ss");

            for (int j = 0; j < NOM_COLUMNAS.length; j++) {
                sb.append(NOM_COLUMNAS[j]);
                if (j < NOM_COLUMNAS.length) {
                    sb.append("|");
                }
            }
            sb.append("\n");
            
            String todayStr = formato.format(new Date());
            String fileName = "ReporteGeneralCCMM" + "_" + todayStr + "." + ext;
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletResponse response1 = (HttpServletResponse) fc.getExternalContext().getResponse();
            response1.setHeader("Content-disposition", "attached; filename=\""
                    + fileName + "\"");
            response1.setContentType("application/octet-stream;charset=UTF-8;");           
            response1.setCharacterEncoding("UTF-8");
            
            csvData = sb.toString().getBytes();
            response1.getOutputStream().write(csvData);
            //cero es para reporte general
            params.put(Constant.CAR_MAS_REPORTE, "0");
            
 
        for (int exPagina = 1; exPagina <= ((totalPag/expLonPag)+ (totalPag%expLonPag != 0 ? 1: 0)); exPagina++) {    
                 
            int inicioRegistros = 0;
            if (exPagina > 1) {
                inicioRegistros = (expLonPag * (exPagina - 1));
            }
    
               //consulta paginada de los resultados que se van a imprimir en el reporte                              
               reporteGeneralList = cmMglManager.findReporteGeneralDetallado(params, inicioRegistros, expLonPag);
                      
               
                //listado de nodos cargados      
                if (reporteGeneralList != null && !reporteGeneralList.isEmpty()) {
                    
                    for (CmtTecnologiaSubMgl e : reporteGeneralList) {
                        
                        if (sb.toString().length() > 1) {
                            sb.delete(0, sb.toString().length());
                        }

                    String idTecnologiaSub = "";
                    String idSubEdificio = "";
                    String nombreSubedificio = "";
                    String dirSubEdificio = "";
                    String tipoSubedificio = "";
                    String idCuentaMatriz = "";
                    String numeroCuenta = "";
                    String tecnologia = "";
                    String departamento = "";
                    String ciudad = "";
                    String centroPoblado = "";
                    String idCentroPoblado = "";
                    String barrio = "";
                    String direccion = "";
                    String nombreCuenta = "";
                    String idComAdmin = "";
                    String idComAsc = "";
                    String admin = "";
                    String teleUno = "";
                    String teleDos = "";
                    String idProyecto = "";
                    String idDatos = "";
                    String idNodo = "";
                    String idEstado = "";
                    String idConfiguracion = "";
                    String idAlimentacion = "";
                    String idDistribucion = "";
                    String tipoCcmm="";
                    String idComCon="";
                    String nombreBlackTec="";
                    String idBlackTec="";
                    String fechaCreacion = "";
                    String fechaUltMod = "";
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                    if (e.getTecnoSubedificioId() != null) {
                        idTecnologiaSub = e.getTecnoSubedificioId().toString();
                        if (e.getBasicaIdTecnologias() != null) {
                            tecnologia = e.getBasicaIdTecnologias().getNombreBasica();
                        }
                        if (e.getSubedificioId() != null && e.getSubedificioId().getSubEdificioId() != null) {
                            idSubEdificio = e.getSubedificioId().getSubEdificioId().toString();
                            if (e.getSubedificioId().getCmtCuentaMatrizMglObj() != null && e.getSubedificioId().getCmtCuentaMatrizMglObj().getCuentaMatrizId() != null) {
                                nombreSubedificio = e.getSubedificioId().getNombreSubedificio();
                                if (e.getSubedificioId().getDireccion() != null) {
                                    dirSubEdificio = e.getSubedificioId().getDireccion();
                                }
                                CmtSubEdificioMgl subEdificioGenralRr = subEdificioMglFacadeLocal.findById(e.getSubedificioId().getSubEdificioId());
                                if (subEdificioGenralRr != null && subEdificioGenralRr.getSubEdificioId() != null
                                        && subEdificioGenralRr.getEstadoSubEdificioObj() != null && subEdificioGenralRr.getEstadoSubEdificioObj().getIdentificadorInternoApp() != null
                                        && subEdificioGenralRr.getEstadoSubEdificioObj().getIdentificadorInternoApp().equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO)) {
                                    tipoSubedificio = "CM";
                                } else {
                                    long edificios = subEdificioMglFacadeLocal.countSubEdificiosCuentaMatriz(e.getSubedificioId().getCmtCuentaMatrizMglObj().getCuentaMatrizId());
                                    if (edificios == 1) {
                                        tipoSubedificio = "CM";
                                    } else {
                                        tipoSubedificio = "SUBEDIFICIO";
                                    }
                                }
                                departamento = e.getSubedificioId().getCmtCuentaMatrizMglObj().getDepartamento().getGpoNombre();
                                idCuentaMatriz = e.getSubedificioId().getCmtCuentaMatrizMglObj().getCuentaMatrizId().toString();
                                numeroCuenta = e.getSubedificioId().getCmtCuentaMatrizMglObj().getNumeroCuenta().toString();
                            }
                        }
                    }
                    if (e.getSubedificioId() != null) {
                        if (e.getSubedificioId().getCuentaMatrizObj() != null
                                && e.getSubedificioId().getCuentaMatrizObj().getMunicipio() != null) {
                            ciudad = e.getSubedificioId().getCuentaMatrizObj().getMunicipio().getGpoNombre();
                        }
                        if (e.getSubedificioId().getCuentaMatrizObj() != null
                                && e.getSubedificioId().getCuentaMatrizObj().getCentroPoblado() != null) {
                            centroPoblado = e.getSubedificioId().getCuentaMatrizObj().getCentroPoblado().getGpoNombre();
                            idCentroPoblado = e.getSubedificioId().getCuentaMatrizObj().getCentroPoblado().getGpoId().toString();
                        }
                        if (e.getSubedificioId().getCuentaMatrizObj() != null) {
                            if (e.getSubedificioId().getCuentaMatrizObj().getDireccionPrincipal() != null) {
                                direccion = e.getSubedificioId().getCuentaMatrizObj().getDireccionPrincipal().getDireccionObj().getDirFormatoIgac();
                                barrio = e.getSubedificioId().getCuentaMatrizObj().getDireccionPrincipal().getBarrio();
                            }
                        }
                        if (e.getSubedificioId().getCuentaMatrizObj() != null) {
                            nombreCuenta = e.getSubedificioId().getCuentaMatrizObj().getNombreCuenta();
                        }
                        if (e.getSubedificioId().getCompaniaAdministracionObj() != null) {
                            idComAdmin = e.getSubedificioId().getCompaniaAdministracionObj().getNombreCompania();
                        }
                        if (e.getSubedificioId().getCompaniaAscensorObj() != null) {
                            idComAsc = e.getSubedificioId().getCompaniaAscensorObj().getNombreCompania();
                        }
                        admin = e.getSubedificioId().getAdministrador();
                        teleUno = e.getSubedificioId().getTelefonoPorteria();
                        teleDos = e.getSubedificioId().getTelefonoPorteria2();
                        if (e.getSubedificioId().getTipoProyectoObj() != null) {
                            idProyecto = e.getSubedificioId().getTipoProyectoObj().getNombreBasica();
                        }
                        if (e.getSubedificioId().getOrigenDatosObj() != null) {
                            idDatos = e.getSubedificioId().getOrigenDatosObj().getNombreBasica();
                        }
                    }
                    if (e.getNodoId() != null) {
                        idNodo = e.getNodoId().getNodCodigo();
                    }
                    if (e.getBasicaIdEstadosTec() != null) {
                        idEstado = e.getBasicaIdEstadosTec().getNombreBasica();
                    }
                    if (e.getTipoConfDistribObj() != null) {
                        idConfiguracion = e.getTipoConfDistribObj().getNombreBasica();
                    }
                    if (e.getAlimentElectObj() != null) {
                        idAlimentacion = e.getAlimentElectObj().getNombreBasica();
                    }
                    if (e.getTipoDistribucionObj() != null) {
                        idDistribucion = e.getTipoDistribucionObj().getNombreBasica();
                    }

                    if (e.getSubedificioId().getTipoEdificioObj() != null) {
                        tipoCcmm = e.getSubedificioId().getTipoEdificioObj().getNombreBasica();
                    }

                    if (e.getSubedificioId().getCompaniaConstructoraObj() != null) {
                        idComCon = e.getSubedificioId().getCompaniaConstructoraObj().getNombreCompania();
                    }

                    if (params.get(Constant.CAR_MAS_BLACKLIST_TECNOLOGIA) != null
                            && new BigDecimal(params.get(Constant.CAR_MAS_BLACKLIST_TECNOLOGIA).toString()).compareTo(BigDecimal.ZERO) != 0) {

                        if (e.getSubedificioId().getListCmtBlackListMgl() != null
                                && !e.getSubedificioId().getListCmtBlackListMgl().isEmpty()) {

                            for (CmtBlackListMgl blackListMgl : e.getSubedificioId().getListCmtBlackListMgl()) {
                                BigDecimal idBack = null;
                                if (params.get(Constant.CAR_MAS_BLACKLIST_TECNOLOGIA) instanceof BigDecimal) {
                                    idBack = (BigDecimal) params.get(Constant.CAR_MAS_BLACKLIST_TECNOLOGIA);
                                    if ((blackListMgl.getBlackListObj().getBasicaId().compareTo(idBack) == 0)) {

                                        nombreBlackTec = blackListMgl.getBlackListObj().getNombreBasica();
                                        idBlackTec = blackListMgl.getBlackListId().toString();
                                    }
                                }
                            }
                        }
                    }

                    if (e.getSubedificioId().getCmtCuentaMatrizMglObj() != null
                            && e.getSubedificioId().getCmtCuentaMatrizMglObj().getFechaCreacion() != null) {

                        fechaCreacion = format.format(e.getSubedificioId().getCmtCuentaMatrizMglObj().getFechaCreacion());
                    }

                    if (e.getSubedificioId().getCmtCuentaMatrizMglObj() != null
                            && e.getSubedificioId().getCmtCuentaMatrizMglObj().getFechaEdicion() != null) {

                        fechaUltMod = format.format(e.getSubedificioId().getCmtCuentaMatrizMglObj().getFechaEdicion());
                    }

                        //StringBuilder registro = new StringBuilder();
                        sb.append(idTecnologiaSub).append(Constant.SEPARADOR).append(idSubEdificio).append(Constant.SEPARADOR)
                                .append(nombreSubedificio).append(Constant.SEPARADOR)
                                .append(tipoSubedificio).append(Constant.SEPARADOR).append(idCuentaMatriz).append(Constant.SEPARADOR)
                                .append(numeroCuenta).append(Constant.SEPARADOR).append(tecnologia).append(Constant.SEPARADOR)
                                .append(departamento).append(Constant.SEPARADOR).append(ciudad).append(Constant.SEPARADOR)
                                .append(idCentroPoblado).append(Constant.SEPARADOR).append(centroPoblado).append(Constant.SEPARADOR)
                                .append(barrio).append(Constant.SEPARADOR).append(direccion).append(Constant.SEPARADOR)
                                .append(nombreCuenta).append(Constant.SEPARADOR).append(idComAdmin).append(Constant.SEPARADOR)
                                .append(idComAsc).append(Constant.SEPARADOR).append(admin).append(Constant.SEPARADOR)
                                .append(teleUno).append(Constant.SEPARADOR).append(teleDos).append(Constant.SEPARADOR)
                                .append(idProyecto).append(Constant.SEPARADOR).append(idDatos).append(Constant.SEPARADOR)
                                .append(idNodo).append(Constant.SEPARADOR).append(idEstado).append(Constant.SEPARADOR)
                                .append(idConfiguracion).append(Constant.SEPARADOR).append(idAlimentacion).append(Constant.SEPARADOR)
                                .append(idDistribucion).append(Constant.SEPARADOR).append(tipoCcmm).append(Constant.SEPARADOR)
                                .append(idComCon).append(Constant.SEPARADOR).append(nombreBlackTec).append(Constant.SEPARADOR)
                                .append("").append(Constant.SEPARADOR).append(idBlackTec).append(Constant.SEPARADOR)
                                .append(fechaCreacion).append(Constant.SEPARADOR).append(fechaUltMod).append(Constant.SEPARADOR);

                        sb.append("\n");
                        
                        csvData = sb.toString().getBytes();
                        response1.getOutputStream().write(csvData);
                        response1.getOutputStream().flush();
                        response1.flushBuffer();

                    }
                    System.gc();                    
                }
            }     
            response1.getOutputStream().close();
            fc.responseComplete();    
          
        } catch (IOException | ApplicationException e) {   
            FacesUtil.mostrarMensajeError("Error en exportExcel. ", e, LOGGER);
        }
        return null;
    }
     
     
     public String exportCsvTxtDetallado(String ext) throws ApplicationException {
        try {
            CmtTecnologiaSubMglManager cmMglManager = new CmtTecnologiaSubMglManager(); 
            List<CmtTecnologiaSubMgl> reporteDetalladoList = new ArrayList();
            //Cantidad de registros por pagina a consultar de la DB            
            int expLonPag = 50;
            
            //numero total de registros del reporte         
            long totalPag = this.cantidadRegistros;
            StringBuilder sb = new StringBuilder();
            byte[] csvData;
            SimpleDateFormat formato = new SimpleDateFormat("dd_MMM_yyyy-HH:mm:ss");

            for (int j = 0; j < NOM_COLUMNAS.length; j++) {
                sb.append(NOM_COLUMNAS[j]);
                if (j < NOM_COLUMNAS.length) {
                    sb.append("|");
                }
            }
            sb.append("\n");
            
            String todayStr = formato.format(new Date());
            String fileName = "ReporteDetalladoCCMM" + "_" + todayStr + "." + ext;
            FacesContext fc = FacesContext.getCurrentInstance();
            HttpServletResponse response1 = (HttpServletResponse) fc.getExternalContext().getResponse();
            response1.setHeader("Content-disposition", "attached; filename=\""
                    + fileName + "\"");
            response1.setContentType("application/octet-stream;charset=UTF-8;");           
            response1.setCharacterEncoding("UTF-8");
            
            csvData = sb.toString().getBytes();
            response1.getOutputStream().write(csvData);
            //cero es para reporte general
            params.put(Constant.CAR_MAS_REPORTE, "1");
            
 
        for (int exPagina = 1; exPagina <= ((totalPag/expLonPag)+ (totalPag%expLonPag != 0 ? 1: 0)); exPagina++) {    
                 
            int inicioRegistros = 0;
            if (exPagina > 1) {
                inicioRegistros = (expLonPag * (exPagina - 1));
            }
           
               reporteDetalladoList = cmMglManager.findReporteGeneralDetallado(params, inicioRegistros, expLonPag);
                      
               
                //listado de nodos cargados      
                if (reporteDetalladoList != null && !reporteDetalladoList.isEmpty()) {
                    
                    for (CmtTecnologiaSubMgl e : reporteDetalladoList) {
                        
                        if (sb.toString().length() > 1) {
                            sb.delete(0, sb.toString().length());
                        }

                     String idTecnologiaSub = "";
                    String idSubEdificio = "";
                    String nombreSubedificio = "";
                    String tipoSubedificio = "";
                    String idCuentaMatriz = "";
                    String numeroCuenta = "";
                    String tecnologia = "";
                    String departamento = "";
                    String ciudad = "";
                    String centroPoblado = "";
                    String idCentroPoblado = "";
                    String barrio = "";
                    String direccion = "";
                    String idBlackTec = "";
                    String valor = "";
                    String fechaCreacion = "";
                    String fechaUltMod = "";
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                    if (e.getTecnoSubedificioId() != null) {
                        idTecnologiaSub = e.getTecnoSubedificioId().toString();
                        if (e.getBasicaIdTecnologias() != null) {
                            tecnologia = e.getBasicaIdTecnologias().getNombreBasica();
                        }
                        if (e.getSubedificioId() != null && e.getSubedificioId().getSubEdificioId() != null) {
                            idSubEdificio = e.getSubedificioId().getSubEdificioId().toString();
                            if (e.getSubedificioId().getCmtCuentaMatrizMglObj() != null && e.getSubedificioId().getCmtCuentaMatrizMglObj().getCuentaMatrizId() != null) {
                                nombreSubedificio = e.getSubedificioId().getNombreSubedificio();
                                CmtSubEdificioMgl subEdificioGenralRr = subEdificioMglFacadeLocal.findById(e.getSubedificioId().getSubEdificioId());
                                if (subEdificioGenralRr != null && subEdificioGenralRr.getSubEdificioId() != null
                                        && subEdificioGenralRr.getEstadoSubEdificioObj() != null && subEdificioGenralRr.getEstadoSubEdificioObj().getIdentificadorInternoApp() != null
                                        && subEdificioGenralRr.getEstadoSubEdificioObj().getIdentificadorInternoApp().equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO)) {
                                    tipoSubedificio = "CM";
                                } else {
                                    long edificios = subEdificioMglFacadeLocal.countSubEdificiosCuentaMatriz(e.getSubedificioId().getCmtCuentaMatrizMglObj().getCuentaMatrizId());
                                    if (edificios == 1) {
                                        tipoSubedificio = "CM";
                                    } else {
                                        tipoSubedificio = "SUBEDIFICIO";
                                    }
                                }
                                departamento = e.getSubedificioId().getCmtCuentaMatrizMglObj().getDepartamento().getGpoNombre();
                                idCuentaMatriz = e.getSubedificioId().getCmtCuentaMatrizMglObj().getCuentaMatrizId().toString();
                                numeroCuenta = e.getSubedificioId().getCmtCuentaMatrizMglObj().getNumeroCuenta().toString();
                            }
                        }
                    }

                    if (e.getSubedificioId() != null && e.getSubedificioId().getCuentaMatrizObj() != null
                            && e.getSubedificioId().getCuentaMatrizObj().getMunicipio() != null) {
                        ciudad = e.getSubedificioId().getCuentaMatrizObj().getMunicipio().getGpoNombre();
                    }
                    if (e.getSubedificioId() != null && e.getSubedificioId().getCuentaMatrizObj() != null
                            && e.getSubedificioId().getCuentaMatrizObj().getCentroPoblado() != null) {
                        centroPoblado = e.getSubedificioId().getCuentaMatrizObj().getCentroPoblado().getGpoNombre();
                        idCentroPoblado = e.getSubedificioId().getCuentaMatrizObj().getCentroPoblado().getGpoId().toString();
                    }

                    if (e.getSubedificioId().getCuentaMatrizObj() != null) {
                        if (e.getSubedificioId().getCuentaMatrizObj().getDireccionPrincipal() != null) {
                            direccion = e.getSubedificioId().getCuentaMatrizObj().getDireccionPrincipal().getDireccionObj().getDirFormatoIgac();
                            barrio = e.getSubedificioId().getCuentaMatrizObj().getDireccionPrincipal().getBarrio();
                        }
                    }

                    if (params.get(Constant.CAR_MAS_BLACKLIST_TECNOLOGIA) != null
                            && new BigDecimal(params.get(Constant.CAR_MAS_BLACKLIST_TECNOLOGIA).toString()).compareTo(BigDecimal.ZERO) != 0) {

                        if (e.getSubedificioId().getListCmtBlackListMgl() != null
                                && !e.getSubedificioId().getListCmtBlackListMgl().isEmpty()) {

                            for (CmtBlackListMgl blackListMgl : e.getSubedificioId().getListCmtBlackListMgl()) {
                                BigDecimal idBack = null;
                                if (params.get(Constant.CAR_MAS_BLACKLIST_TECNOLOGIA) instanceof BigDecimal) {
                                    idBack = (BigDecimal) params.get(Constant.CAR_MAS_BLACKLIST_TECNOLOGIA);
                                    if ((blackListMgl.getBlackListObj().getBasicaId().compareTo(idBack) == 0)) {

                                        idBlackTec = blackListMgl.getBlackListObj().getNombreBasica();
                                    }
                                }
                            }
                        }
                    }

                    if (e.getSubedificioId().getCmtCuentaMatrizMglObj() != null
                            && e.getSubedificioId().getCmtCuentaMatrizMglObj().getFechaCreacion() != null) {

                        fechaCreacion = format.format(e.getSubedificioId().getCmtCuentaMatrizMglObj().getFechaCreacion());
                    }

                    if (e.getSubedificioId().getCmtCuentaMatrizMglObj() != null
                            && e.getSubedificioId().getCmtCuentaMatrizMglObj().getFechaEdicion() != null) {

                        fechaUltMod = format.format(e.getSubedificioId().getCmtCuentaMatrizMglObj().getFechaEdicion());
                    }

                    if (auxCabecera != null && auxCabecera.equals(Constant.NOMBRE)) {
                        if (e.getSubedificioId() != null && e.getSubedificioId().getCuentaMatrizObj() != null) {
                            valor = e.getSubedificioId().getCuentaMatrizObj().getNombreCuenta();
                        }
                    } else if (auxCabecera != null && auxCabecera.equals(Constant.ADMINISTRACION)) {
                        if (e.getSubedificioId() != null && e.getSubedificioId().getCompaniaAdministracionObj() != null) {
                            valor = e.getSubedificioId().getCompaniaAdministracionObj().getNombreCompania();
                        }
                    } else if (auxCabecera != null && auxCabecera.equals(Constant.ASCENSOR)) {
                        if (e.getSubedificioId() != null && e.getSubedificioId().getCompaniaAscensorObj() != null) {
                            valor = e.getSubedificioId().getCompaniaAscensorObj().getNombreCompania();
                        }
                    } else if (auxCabecera != null && auxCabecera.equals(Constant.ADMINISTRADOR)) {
                        valor = e.getSubedificioId().getAdministrador();
                    } else if (auxCabecera != null && auxCabecera.equals(Constant.TEL_UNO)) {
                        valor = e.getSubedificioId().getTelefonoPorteria();
                    } else if (auxCabecera != null && auxCabecera.equals(Constant.TEL_DOS)) {
                        valor = e.getSubedificioId().getTelefonoPorteria2();
                    } else if (auxCabecera != null && auxCabecera.equals(Constant.PROYECTO)) {
                        if (e.getSubedificioId() != null && e.getSubedificioId().getTipoProyectoObj() != null) {
                            valor = e.getSubedificioId().getTipoProyectoObj().getNombreBasica();
                        }
                    } else if (auxCabecera != null && auxCabecera.equals(Constant.DATOS)) {
                        if (e.getSubedificioId() != null && e.getSubedificioId().getOrigenDatosObj() != null) {
                            valor = e.getSubedificioId().getOrigenDatosObj().getNombreBasica();
                        }
                    } else if (auxCabecera != null && auxCabecera.equals(Constant.NODO)) {

                        if (e.getNodoId() != null) {
                            valor = e.getNodoId().getNodCodigo();
                        }
                    } else if (auxCabecera != null && auxCabecera.equals(Constant.ESTADO)) {
                        if (e.getBasicaIdEstadosTec() != null) {
                            valor = e.getBasicaIdEstadosTec().getNombreBasica();
                        }
                    } else if (auxCabecera != null && auxCabecera.equals(Constant.CONFIGURACION)) {
                        if (e.getTipoConfDistribObj() != null) {
                            valor = e.getTipoConfDistribObj().getNombreBasica();
                        }
                    } else if (auxCabecera != null && auxCabecera.equals(Constant.ALIMENTACION)) {
                        if (e.getAlimentElectObj() != null) {
                            valor = e.getAlimentElectObj().getNombreBasica();
                        }
                    } else if (auxCabecera != null && auxCabecera.equals(Constant.DISTRIBUCION)) {
                        if (e.getTipoDistribucionObj() != null) {
                            valor = e.getTipoDistribucionObj().getNombreBasica();
                        }
                    } else if (auxCabecera != null && auxCabecera.equals(Constant.CONSTRUCTORA)) {
                        if (e.getSubedificioId() != null && e.getSubedificioId().getCompaniaConstructoraObj() != null) {
                            valor = e.getSubedificioId().getCompaniaConstructoraObj().getNombreCompania();
                        }
                    } else if (auxCabecera != null && auxCabecera.equals(Constant.TIPO_CCMM)) {
                        if (e.getSubedificioId() != null && e.getSubedificioId().getTipoEdificioObj() != null) {
                            valor = e.getSubedificioId().getTipoEdificioObj().getNombreBasica();
                        }
                    }
       
                        sb.append(idTecnologiaSub).append(Constant.SEPARADOR)
                                .append(idSubEdificio).append(Constant.SEPARADOR).append(nombreSubedificio);
                        sb.append(Constant.SEPARADOR).append(tipoSubedificio)
                                .append(Constant.SEPARADOR).append(idCuentaMatriz).append(Constant.SEPARADOR);
                        sb.append(numeroCuenta).append(Constant.SEPARADOR);
                        sb.append(tecnologia).append(Constant.SEPARADOR)
                                .append(departamento).append(Constant.SEPARADOR)
                                .append(ciudad).append(Constant.SEPARADOR).append(idCentroPoblado);
                        sb.append(Constant.SEPARADOR).append(centroPoblado)
                                .append(Constant.SEPARADOR).append(barrio)
                                .append(Constant.SEPARADOR).append(direccion)
                                .append(Constant.SEPARADOR).append(idBlackTec).append(Constant.SEPARADOR);
                        sb.append(valor).append(Constant.SEPARADOR).append("")
                                .append(Constant.SEPARADOR).append("")
                                .append(Constant.SEPARADOR).append(fechaCreacion)
                                .append(Constant.SEPARADOR).append(fechaUltMod).append(Constant.SEPARADOR);

                        sb.append("\n");

                        csvData = sb.toString().getBytes();
                        response1.getOutputStream().write(csvData);
                        response1.getOutputStream().flush();
                        response1.flushBuffer();

                    }
                    System.gc();                    
                }
            }     
            response1.getOutputStream().close();
            fc.responseComplete();    
          
        } catch (IOException | ApplicationException e) {   
            FacesUtil.mostrarMensajeError("Error en exportCsv detallado. ", e, LOGGER);
        }
        return null;
    }
    
    
    /**
     * valbuenayf metodo para descargar el archivo csv de reporte general
     *
     * @param reporte
     */
    private void exportarArchivoCsvTxtGeneralOld(List<CmtTecnologiaSubMgl> reporte, String extensionArchivo) throws IOException, ApplicationException {
        try {
            if (reporte != null && !reporte.isEmpty()) {

                StringBuilder sb = new StringBuilder();
                byte[] csvData;
                SimpleDateFormat formato = new SimpleDateFormat("dd_MMM_yyyy-HH:mm:ss");
                sb.append(getCabecera());

                sb.append("\n");

                String todayStr = formato.format(new Date());
                String fileName = "ReporteGeneralCCMM" + "_" + todayStr + "." + extensionArchivo;
                FacesContext fc = FacesContext.getCurrentInstance();
                HttpServletResponse response1 = (HttpServletResponse) fc.getExternalContext().getResponse();
                response1.setHeader("Content-disposition", "attached; filename=\""
                        + fileName + "\"");
                response1.setContentType("application/octet-stream;charset=UTF-8;");
                response1.setCharacterEncoding("UTF-8");

                csvData = sb.toString().getBytes();
                response1.getOutputStream().write(csvData);

                for (CmtTecnologiaSubMgl e : reporte) {
                      if (sb.toString().length() > 1) {
                            sb.delete(0, sb.toString().length());
                        }

                    String idTecnologiaSub = "";
                    String idSubEdificio = "";
                    String nombreSubedificio = "";
                    String dirSubEdificio = "";
                    String tipoSubedificio = "";
                    String idCuentaMatriz = "";
                    String numeroCuenta = "";
                    String tecnologia = "";
                    String departamento = "";
                    String ciudad = "";
                    String centroPoblado = "";
                    String idCentroPoblado = "";
                    String barrio = "";
                    String direccion = "";
                    String nombreCuenta = "";
                    String idComAdmin = "";
                    String idComAsc = "";
                    String admin = "";
                    String teleUno = "";
                    String teleDos = "";
                    String idProyecto = "";
                    String idDatos = "";
                    String idNodo = "";
                    String idEstado = "";
                    String idConfiguracion = "";
                    String idAlimentacion = "";
                    String idDistribucion = "";
                    String tipoCcmm="";
                    String idComCon="";
                    String nombreBlackTec="";
                    String idBlackTec="";
                    String fechaCreacion = "";
                    String fechaUltMod = "";
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                    if (e.getTecnoSubedificioId() != null) {
                        idTecnologiaSub = e.getTecnoSubedificioId().toString();
                        if (e.getBasicaIdTecnologias() != null) {
                            tecnologia = e.getBasicaIdTecnologias().getNombreBasica();
                        }
                        if (e.getSubedificioId() != null && e.getSubedificioId().getSubEdificioId() != null) {
                            idSubEdificio = e.getSubedificioId().getSubEdificioId().toString();
                            if (e.getSubedificioId().getCmtCuentaMatrizMglObj() != null && e.getSubedificioId().getCmtCuentaMatrizMglObj().getCuentaMatrizId() != null) {
                                nombreSubedificio = e.getSubedificioId().getNombreSubedificio();
                                if (e.getSubedificioId().getDireccion() != null) {
                                    dirSubEdificio = e.getSubedificioId().getDireccion();
                                }
                                CmtSubEdificioMgl subEdificioGenralRr = subEdificioMglFacadeLocal.findById(e.getSubedificioId().getSubEdificioId());
                                if (subEdificioGenralRr != null && subEdificioGenralRr.getSubEdificioId() != null
                                        && subEdificioGenralRr.getEstadoSubEdificioObj() != null && subEdificioGenralRr.getEstadoSubEdificioObj().getIdentificadorInternoApp() != null
                                        && subEdificioGenralRr.getEstadoSubEdificioObj().getIdentificadorInternoApp().equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO)) {
                                    tipoSubedificio = "CM";
                                } else {
                                    long edificios = subEdificioMglFacadeLocal.countSubEdificiosCuentaMatriz(e.getSubedificioId().getCmtCuentaMatrizMglObj().getCuentaMatrizId());
                                    if (edificios == 1) {
                                        tipoSubedificio = "CM";
                                    } else {
                                        tipoSubedificio = "SUBEDIFICIO";
                                    }
                                }
                                departamento = e.getSubedificioId().getCmtCuentaMatrizMglObj().getDepartamento().getGpoNombre();
                                idCuentaMatriz = e.getSubedificioId().getCmtCuentaMatrizMglObj().getCuentaMatrizId().toString();
                                numeroCuenta = e.getSubedificioId().getCmtCuentaMatrizMglObj().getNumeroCuenta().toString();
                            }
                        }
                    }
                    if (e.getSubedificioId() != null) {
                        if (e.getSubedificioId().getCuentaMatrizObj() != null
                                && e.getSubedificioId().getCuentaMatrizObj().getMunicipio() != null) {
                            ciudad = e.getSubedificioId().getCuentaMatrizObj().getMunicipio().getGpoNombre();
                        }
                        if (e.getSubedificioId().getCuentaMatrizObj() != null
                                && e.getSubedificioId().getCuentaMatrizObj().getCentroPoblado() != null) {
                            centroPoblado = e.getSubedificioId().getCuentaMatrizObj().getCentroPoblado().getGpoNombre();
                            idCentroPoblado = e.getSubedificioId().getCuentaMatrizObj().getCentroPoblado().getGpoId().toString();
                        }
                        if (e.getSubedificioId().getCuentaMatrizObj() != null) {
                            if (e.getSubedificioId().getCuentaMatrizObj().getDireccionPrincipal() != null) {
                                direccion = e.getSubedificioId().getCuentaMatrizObj().getDireccionPrincipal().getDireccionObj().getDirFormatoIgac();
                                barrio = e.getSubedificioId().getCuentaMatrizObj().getDireccionPrincipal().getBarrio();
                            }
                        }
                        if (e.getSubedificioId().getCuentaMatrizObj() != null) {
                            nombreCuenta = e.getSubedificioId().getCuentaMatrizObj().getNombreCuenta();
                        }
                        if (e.getSubedificioId().getCompaniaAdministracionObj() != null) {
                            idComAdmin = e.getSubedificioId().getCompaniaAdministracionObj().getNombreCompania();
                        }
                        if (e.getSubedificioId().getCompaniaAscensorObj() != null) {
                            idComAsc = e.getSubedificioId().getCompaniaAscensorObj().getNombreCompania();
                        }
                        admin = e.getSubedificioId().getAdministrador();
                        teleUno = e.getSubedificioId().getTelefonoPorteria();
                        teleDos = e.getSubedificioId().getTelefonoPorteria2();
                        if (e.getSubedificioId().getTipoProyectoObj() != null) {
                            idProyecto = e.getSubedificioId().getTipoProyectoObj().getNombreBasica();
                        }
                        if (e.getSubedificioId().getOrigenDatosObj() != null) {
                            idDatos = e.getSubedificioId().getOrigenDatosObj().getNombreBasica();
                        }
                    }
                    if (e.getNodoId() != null) {
                        idNodo = e.getNodoId().getNodCodigo();
                    }
                    if (e.getBasicaIdEstadosTec() != null) {
                        idEstado = e.getBasicaIdEstadosTec().getNombreBasica();
                    }
                    if (e.getTipoConfDistribObj() != null) {
                        idConfiguracion = e.getTipoConfDistribObj().getNombreBasica();
                    }
                    if (e.getAlimentElectObj() != null) {
                        idAlimentacion = e.getAlimentElectObj().getNombreBasica();
                    }
                    if (e.getTipoDistribucionObj() != null) {
                        idDistribucion = e.getTipoDistribucionObj().getNombreBasica();
                    }

                    if (e.getSubedificioId().getTipoEdificioObj() != null) {
                        tipoCcmm = e.getSubedificioId().getTipoEdificioObj().getNombreBasica();
                    }

                    if (e.getSubedificioId().getCompaniaConstructoraObj() != null) {
                        idComCon = e.getSubedificioId().getCompaniaConstructoraObj().getNombreCompania();
                    }

                    if (params.get(Constant.CAR_MAS_BLACKLIST_TECNOLOGIA) != null
                            && new BigDecimal(params.get(Constant.CAR_MAS_BLACKLIST_TECNOLOGIA).toString()).compareTo(BigDecimal.ZERO) != 0) {

                        if (e.getSubedificioId().getListCmtBlackListMgl() != null
                                && !e.getSubedificioId().getListCmtBlackListMgl().isEmpty()) {

                            for (CmtBlackListMgl blackListMgl : e.getSubedificioId().getListCmtBlackListMgl()) {
                                BigDecimal idBack = null;
                                if (params.get(Constant.CAR_MAS_BLACKLIST_TECNOLOGIA) instanceof BigDecimal) {
                                    idBack = (BigDecimal) params.get(Constant.CAR_MAS_BLACKLIST_TECNOLOGIA);
                                    if ((blackListMgl.getBlackListObj().getBasicaId().compareTo(idBack) == 0)) {

                                        nombreBlackTec = blackListMgl.getBlackListObj().getNombreBasica();
                                        idBlackTec = blackListMgl.getBlackListId().toString();
                                    }
                                }
                            }
                        }
                    }

                    if (e.getSubedificioId().getCmtCuentaMatrizMglObj() != null
                            && e.getSubedificioId().getCmtCuentaMatrizMglObj().getFechaCreacion() != null) {

                        fechaCreacion = format.format(e.getSubedificioId().getCmtCuentaMatrizMglObj().getFechaCreacion());
                    }

                    if (e.getSubedificioId().getCmtCuentaMatrizMglObj() != null
                            && e.getSubedificioId().getCmtCuentaMatrizMglObj().getFechaEdicion() != null) {

                        fechaUltMod = format.format(e.getSubedificioId().getCmtCuentaMatrizMglObj().getFechaEdicion());
                    }

                    //StringBuilder registro = new StringBuilder();
                    sb.append(idTecnologiaSub).append(Constant.SEPARADOR).append(idSubEdificio).append(Constant.SEPARADOR)
                            .append(nombreSubedificio).append(Constant.SEPARADOR)
                            .append(tipoSubedificio).append(Constant.SEPARADOR).append(idCuentaMatriz).append(Constant.SEPARADOR)
                            .append(numeroCuenta).append(Constant.SEPARADOR).append(tecnologia).append(Constant.SEPARADOR)
                            .append(departamento).append(Constant.SEPARADOR).append(ciudad).append(Constant.SEPARADOR)
                            .append(idCentroPoblado).append(Constant.SEPARADOR).append(centroPoblado).append(Constant.SEPARADOR)
                            .append(barrio).append(Constant.SEPARADOR).append(direccion).append(Constant.SEPARADOR)
                            .append(nombreCuenta).append(Constant.SEPARADOR).append(idComAdmin).append(Constant.SEPARADOR)
                            .append(idComAsc).append(Constant.SEPARADOR).append(admin).append(Constant.SEPARADOR)
                            .append(teleUno).append(Constant.SEPARADOR).append(teleDos).append(Constant.SEPARADOR)
                            .append(idProyecto).append(Constant.SEPARADOR).append(idDatos).append(Constant.SEPARADOR)
                            .append(idNodo).append(Constant.SEPARADOR).append(idEstado).append(Constant.SEPARADOR)
                            .append(idConfiguracion).append(Constant.SEPARADOR).append(idAlimentacion).append(Constant.SEPARADOR)
                            .append(idDistribucion).append(Constant.SEPARADOR).append(tipoCcmm).append(Constant.SEPARADOR)
                            .append(idComCon).append(Constant.SEPARADOR).append(nombreBlackTec).append(Constant.SEPARADOR)
                            .append("").append(Constant.SEPARADOR).append(idBlackTec).append(Constant.SEPARADOR)
                            .append(fechaCreacion).append(Constant.SEPARADOR).append(fechaUltMod).append(Constant.SEPARADOR);
                    
                    sb.append("\n");
                    csvData = sb.toString().getBytes();
                    response1.getOutputStream().write(csvData);
                    response1.getOutputStream().flush();
                    response1.flushBuffer();
                }

                System.gc();
                response1.getOutputStream().close();
                fc.responseComplete();

            } else {
                String msn = "No Existen Registros a exportar";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
            }
        } catch (ApplicationException | IOException e) {
            FacesUtil.mostrarMensajeError("Ocurrio un error en la creacion del archivo excel en CargueMasivoModificacionBean: exportarArchivoCsvGeneral()" + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Ocurrio un error en la creacion del archivo excel en CargueMasivoModificacionBean: exportarArchivoCsvGeneral()" + e.getMessage(), e, LOGGER);
        }
    }

   

    //inicio Reporte Detallado 
    /**
     * valbuenayf metodo para descargar el archivo xls de reporte detallado
     *
     * @param reporte
     */
    private void exportarArchivoXlsDetallado() {
        try {
            
              CmtTecnologiaSubMglManager cmMglManager = new CmtTecnologiaSubMglManager(); 
            List<CmtTecnologiaSubMgl> reporteDetalladoList = new ArrayList();
            params.put(Constant.CAR_MAS_REPORTE, "1");
            reporteDetalladoList = cmMglManager.findReporteGeneralDetallado(params, 0, 2000);
            
            
            if (reporteDetalladoList != null && !reporteDetalladoList.isEmpty()) {
                //Blank workbook            
                HSSFWorkbook workbook = new HSSFWorkbook();
                Map<String, Object[]> mapDataEstado = new TreeMap<String, Object[]>();
                int fila = 1;
                //se ingresa la cabecera de los datos
                mapDataEstado.put(String.valueOf(fila++), getCabeceraXlsDetallado());
                for (CmtTecnologiaSubMgl e : reporteDetalladoList) {
                    String idTecnologiaSub = "";
                    String idSubEdificio = "";
                    String nombreSubedificio = "";
                    String tipoSubedificio = "";
                    String idCuentaMatriz = "";
                    String numeroCuenta = "";
                    String tecnologia = "";
                    String departamento = "";
                    String ciudad = "";
                    String centroPoblado = "";
                    String idCentroPoblado = "";
                    String barrio = "";
                    String direccion = "";
                    String idBlackTec="";
                    String valor = "";
                    String nuevo = "";
                    String nota = "";
                    String fechaCreacion = "";
                    String fechaUltMod = "";
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                    if (e.getTecnoSubedificioId() != null) {
                        idTecnologiaSub = e.getTecnoSubedificioId().toString();
                        if (e.getBasicaIdTecnologias() != null) {
                            tecnologia = e.getBasicaIdTecnologias().getNombreBasica();
                        }
                        if (e.getSubedificioId() != null && e.getSubedificioId().getSubEdificioId() != null) {
                            idSubEdificio = e.getSubedificioId().getSubEdificioId().toString();
                            if (e.getSubedificioId().getCmtCuentaMatrizMglObj() != null && e.getSubedificioId().getCmtCuentaMatrizMglObj().getCuentaMatrizId() != null) {
                                nombreSubedificio = e.getSubedificioId().getNombreSubedificio();
                                CmtSubEdificioMgl subEdificioGenralRr = subEdificioMglFacadeLocal.findById(e.getSubedificioId().getSubEdificioId());
                                if (subEdificioGenralRr != null && subEdificioGenralRr.getSubEdificioId() != null
                                        && subEdificioGenralRr.getEstadoSubEdificioObj() != null && subEdificioGenralRr.getEstadoSubEdificioObj().getIdentificadorInternoApp() != null
                                        && subEdificioGenralRr.getEstadoSubEdificioObj().getIdentificadorInternoApp().equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO)) {
                                    tipoSubedificio = "CM";
                                } else {
                                    long edificios = subEdificioMglFacadeLocal.countSubEdificiosCuentaMatriz(e.getSubedificioId().getCmtCuentaMatrizMglObj().getCuentaMatrizId());
                                    if (edificios == 1) {
                                        tipoSubedificio = "CM";
                                    } else {
                                        tipoSubedificio = "SUBEDIFICIO";
                                    }
                                }
                                departamento = e.getSubedificioId().getCmtCuentaMatrizMglObj().getDepartamento().getGpoNombre();
                                idCuentaMatriz = e.getSubedificioId().getCmtCuentaMatrizMglObj().getCuentaMatrizId().toString();
                                numeroCuenta = e.getSubedificioId().getCmtCuentaMatrizMglObj().getNumeroCuenta().toString();
                            }
                        }
                    }
                    if (e.getSubedificioId() != null && e.getSubedificioId().getCuentaMatrizObj() != null
                            && e.getSubedificioId().getCuentaMatrizObj().getMunicipio() != null) {
                        ciudad = e.getSubedificioId().getCuentaMatrizObj().getMunicipio().getGpoNombre();
                    }
                    if (e.getSubedificioId() != null && e.getSubedificioId().getCuentaMatrizObj() != null
                            && e.getSubedificioId().getCuentaMatrizObj().getCentroPoblado() != null) {
                        centroPoblado = e.getSubedificioId().getCuentaMatrizObj().getCentroPoblado().getGpoNombre();
                        idCentroPoblado = e.getSubedificioId().getCuentaMatrizObj().getCentroPoblado().getGpoId().toString();
                    }

                    if (e.getSubedificioId().getCuentaMatrizObj() != null) {
                        if (e.getSubedificioId().getCuentaMatrizObj().getDireccionPrincipal() != null) {
                            direccion = e.getSubedificioId().getCuentaMatrizObj().getDireccionPrincipal().getDireccionObj().getDirFormatoIgac();
                            barrio = e.getSubedificioId().getCuentaMatrizObj().getDireccionPrincipal().getBarrio();
                        }
                    }

                    if (params.get(Constant.CAR_MAS_BLACKLIST_TECNOLOGIA) != null
                            && new BigDecimal(params.get(Constant.CAR_MAS_BLACKLIST_TECNOLOGIA).toString()).compareTo(BigDecimal.ZERO) != 0){

                        if (e.getSubedificioId().getListCmtBlackListMgl() != null
                                && !e.getSubedificioId().getListCmtBlackListMgl().isEmpty()) {

                            for (CmtBlackListMgl blackListMgl : e.getSubedificioId().getListCmtBlackListMgl()) {
                                BigDecimal idBack = null;
                                if (params.get(Constant.CAR_MAS_BLACKLIST_TECNOLOGIA) instanceof BigDecimal) {
                                    idBack = (BigDecimal) params.get(Constant.CAR_MAS_BLACKLIST_TECNOLOGIA);
                                    if ((blackListMgl.getBlackListObj().getBasicaId().compareTo(idBack) == 0)) {

                                        idBlackTec = blackListMgl.getBlackListObj().getNombreBasica();
                                    }
                                }
                            }
                        }
                    }

                    if (e.getSubedificioId().getCmtCuentaMatrizMglObj() != null
                            && e.getSubedificioId().getCmtCuentaMatrizMglObj().getFechaCreacion() != null) {

                        fechaCreacion = format.format(e.getSubedificioId().getCmtCuentaMatrizMglObj().getFechaCreacion());
                    }

                    if (e.getSubedificioId().getCmtCuentaMatrizMglObj() != null
                            && e.getSubedificioId().getCmtCuentaMatrizMglObj().getFechaEdicion() != null) {

                        fechaUltMod = format.format(e.getSubedificioId().getCmtCuentaMatrizMglObj().getFechaEdicion());
                    }

                    if (auxCabecera != null && auxCabecera.equals(Constant.NOMBRE)) {
                        if (e.getSubedificioId() != null && e.getSubedificioId().getCuentaMatrizObj() != null) {
                            valor = e.getSubedificioId().getCuentaMatrizObj().getNombreCuenta();
                        }
                    } else if (auxCabecera != null && auxCabecera.equals(Constant.ADMINISTRACION)) {
                        if (e.getSubedificioId() != null && e.getSubedificioId().getCompaniaAdministracionObj() != null) {
                            valor = e.getSubedificioId().getCompaniaAdministracionObj().getNombreCompania();
                        }
                    } else if (auxCabecera != null && auxCabecera.equals(Constant.ASCENSOR)) {
                        if (e.getSubedificioId() != null && e.getSubedificioId().getCompaniaAscensorObj() != null) {
                            valor = e.getSubedificioId().getCompaniaAscensorObj().getNombreCompania();
                        }
                    } else if (auxCabecera != null && auxCabecera.equals(Constant.ADMINISTRADOR)) {
                        valor = e.getSubedificioId().getAdministrador();
                    } else if (auxCabecera != null && auxCabecera.equals(Constant.TEL_UNO)) {
                        valor = e.getSubedificioId().getTelefonoPorteria();
                    } else if (auxCabecera != null && auxCabecera.equals(Constant.TEL_DOS)) {
                        valor = e.getSubedificioId().getTelefonoPorteria2();
                    } else if (auxCabecera != null && auxCabecera.equals(Constant.PROYECTO)) {
                        if (e.getSubedificioId() != null && e.getSubedificioId().getTipoProyectoObj() != null) {
                            valor = e.getSubedificioId().getTipoProyectoObj().getNombreBasica();
                        }
                    } else if (auxCabecera != null && auxCabecera.equals(Constant.DATOS)) {
                        if (e.getSubedificioId() != null && e.getSubedificioId().getOrigenDatosObj() != null) {
                            valor = e.getSubedificioId().getOrigenDatosObj().getNombreBasica();
                        }
                    } else if (auxCabecera != null && auxCabecera.equals(Constant.NODO)) {

                        if (e.getNodoId() != null) {
                            valor = e.getNodoId().getNodCodigo();
                        }
                    } else if (auxCabecera != null && auxCabecera.equals(Constant.ESTADO)) {
                        if (e.getBasicaIdEstadosTec() != null) {
                            valor = e.getBasicaIdEstadosTec().getNombreBasica();
                        }
                    } else if (auxCabecera != null && auxCabecera.equals(Constant.CONFIGURACION)) {
                        if (e.getTipoConfDistribObj() != null) {
                            valor = e.getTipoConfDistribObj().getNombreBasica();
                        }
                    } else if (auxCabecera != null && auxCabecera.equals(Constant.ALIMENTACION)) {
                        if (e.getAlimentElectObj() != null) {
                            valor = e.getAlimentElectObj().getNombreBasica();
                        }
                    } else if (auxCabecera != null && auxCabecera.equals(Constant.DISTRIBUCION)) {
                        if (e.getTipoDistribucionObj() != null) {
                            valor = e.getTipoDistribucionObj().getNombreBasica();
                        }
                    }else if (auxCabecera != null && auxCabecera.equals(Constant.CONSTRUCTORA)) {
                        if (e.getSubedificioId() != null && e.getSubedificioId().getCompaniaConstructoraObj() != null) {
                            valor = e.getSubedificioId().getCompaniaConstructoraObj().getNombreCompania();
                        }
                    }else if (auxCabecera != null && auxCabecera.equals(Constant.TIPO_CCMM)) {
                        if (e.getSubedificioId() != null && e.getSubedificioId().getTipoEdificioObj() != null) {
                            valor = e.getSubedificioId().getTipoEdificioObj().getNombreBasica();
                        }
                    }

                    Object[] registros = new Object[]{idTecnologiaSub, idSubEdificio, nombreSubedificio, tipoSubedificio, idCuentaMatriz, numeroCuenta,
                        tecnologia, departamento, ciudad, idCentroPoblado, centroPoblado, barrio,
                        direccion, idBlackTec, valor, nuevo, nota, fechaCreacion, fechaUltMod};

                    mapDataEstado.put(String.valueOf(fila++), registros);
                }
                fillSheetbookDetalle(workbook, Constant.SHEET_NAME, mapDataEstado);
                String fileName = "ReporteDetalladoCCMM_" + auxCabecera + "_";
                FacesContext fc = FacesContext.getCurrentInstance();
                HttpServletResponse responseRl = (HttpServletResponse) fc.getExternalContext().getResponse();
                responseRl.reset();
                responseRl.setContentType("application/vnd.ms-excel");
                responseRl.setCharacterEncoding("UTF-8");

                SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss-a");

                responseRl.setHeader("Content-Disposition", "attachment; filename=" + fileName + formato.format(new Date()) + ".xls");
                OutputStream output = responseRl.getOutputStream();

                workbook.write(output);
                output.flush();
                output.close();
                fc.responseComplete();
            } else {
                String msn = "No Existen Registros a exportar";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
            }
        } catch (ApplicationException | IOException e) {
            FacesUtil.mostrarMensajeError("Se genero error generando archivo excel detallado en CargueMasivoModificacionBean: exportarArchivoXlsDetallado()" + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genero error generando archivo excel detallado en CargueMasivoModificacionBean: exportarArchivoXlsDetallado()" + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * valbuenayf metodo para descargar el archivo csv de reporte detallado
     *
     * @param reporte
     */
    private void exportarArchivoCsvTxtDetalladoOld(List<CmtTecnologiaSubMgl> reporte, String extensionArchivo) {
        try {
            if (reporte != null && !reporte.isEmpty()) {
                
                StringBuilder sb = new StringBuilder();
                byte[] csvData;
                SimpleDateFormat formato = new SimpleDateFormat("dd_MMM_yyyy-HH:mm:ss");
                sb.append(getCabeceraDetallado());

                sb.append("\n");
               
                String todayStr = formato.format(new Date());
                String fileName = "ReporteDetalladoCCMM" + "_" + todayStr + "." + extensionArchivo;
                FacesContext fc = FacesContext.getCurrentInstance();
                HttpServletResponse response1 = (HttpServletResponse) fc.getExternalContext().getResponse();
                response1.setHeader("Content-disposition", "attached; filename=\""
                        + fileName + "\"");
                response1.setContentType("application/octet-stream;charset=UTF-8;");
                response1.setCharacterEncoding("UTF-8");

                csvData = sb.toString().getBytes();
                response1.getOutputStream().write(csvData);                

                for (CmtTecnologiaSubMgl e : reporte) {
                    if (sb.toString().length() > 1) {
                        sb.delete(0, sb.toString().length());
                    }
                    String idTecnologiaSub = "";
                    String idSubEdificio = "";
                    String nombreSubedificio = "";
                    String tipoSubedificio = "";
                    String idCuentaMatriz = "";
                    String numeroCuenta = "";
                    String tecnologia = "";
                    String departamento = "";
                    String ciudad = "";
                    String centroPoblado = "";
                    String idCentroPoblado = "";
                    String barrio = "";
                    String direccion = "";
                    String idBlackTec = "";
                    String valor = "";
                    String fechaCreacion = "";
                    String fechaUltMod = "";
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                    if (e.getTecnoSubedificioId() != null) {
                        idTecnologiaSub = e.getTecnoSubedificioId().toString();
                        if (e.getBasicaIdTecnologias() != null) {
                            tecnologia = e.getBasicaIdTecnologias().getNombreBasica();
                        }
                        if (e.getSubedificioId() != null && e.getSubedificioId().getSubEdificioId() != null) {
                            idSubEdificio = e.getSubedificioId().getSubEdificioId().toString();
                            if (e.getSubedificioId().getCmtCuentaMatrizMglObj() != null && e.getSubedificioId().getCmtCuentaMatrizMglObj().getCuentaMatrizId() != null) {
                                nombreSubedificio = e.getSubedificioId().getNombreSubedificio();
                                CmtSubEdificioMgl subEdificioGenralRr = subEdificioMglFacadeLocal.findById(e.getSubedificioId().getSubEdificioId());
                                if (subEdificioGenralRr != null && subEdificioGenralRr.getSubEdificioId() != null
                                        && subEdificioGenralRr.getEstadoSubEdificioObj() != null && subEdificioGenralRr.getEstadoSubEdificioObj().getIdentificadorInternoApp() != null
                                        && subEdificioGenralRr.getEstadoSubEdificioObj().getIdentificadorInternoApp().equals(Constant.BASICA_ESTADO_SUBEDIFICIO_MULTIEDIFICIO)) {
                                    tipoSubedificio = "CM";
                                } else {
                                    long edificios = subEdificioMglFacadeLocal.countSubEdificiosCuentaMatriz(e.getSubedificioId().getCmtCuentaMatrizMglObj().getCuentaMatrizId());
                                    if (edificios == 1) {
                                        tipoSubedificio = "CM";
                                    } else {
                                        tipoSubedificio = "SUBEDIFICIO";
                                    }
                                }
                                departamento = e.getSubedificioId().getCmtCuentaMatrizMglObj().getDepartamento().getGpoNombre();
                                idCuentaMatriz = e.getSubedificioId().getCmtCuentaMatrizMglObj().getCuentaMatrizId().toString();
                                numeroCuenta = e.getSubedificioId().getCmtCuentaMatrizMglObj().getNumeroCuenta().toString();
                            }
                        }
                    }

                    if (e.getSubedificioId() != null && e.getSubedificioId().getCuentaMatrizObj() != null
                            && e.getSubedificioId().getCuentaMatrizObj().getMunicipio() != null) {
                        ciudad = e.getSubedificioId().getCuentaMatrizObj().getMunicipio().getGpoNombre();
                    }
                    if (e.getSubedificioId() != null && e.getSubedificioId().getCuentaMatrizObj() != null
                            && e.getSubedificioId().getCuentaMatrizObj().getCentroPoblado() != null) {
                        centroPoblado = e.getSubedificioId().getCuentaMatrizObj().getCentroPoblado().getGpoNombre();
                        idCentroPoblado = e.getSubedificioId().getCuentaMatrizObj().getCentroPoblado().getGpoId().toString();
                    }

                    if (e.getSubedificioId().getCuentaMatrizObj() != null) {
                        if (e.getSubedificioId().getCuentaMatrizObj().getDireccionPrincipal() != null) {
                            direccion = e.getSubedificioId().getCuentaMatrizObj().getDireccionPrincipal().getDireccionObj().getDirFormatoIgac();
                            barrio = e.getSubedificioId().getCuentaMatrizObj().getDireccionPrincipal().getBarrio();
                        }
                    }

                    if (params.get(Constant.CAR_MAS_BLACKLIST_TECNOLOGIA) != null
                            && new BigDecimal(params.get(Constant.CAR_MAS_BLACKLIST_TECNOLOGIA).toString()).compareTo(BigDecimal.ZERO) != 0) {

                        if (e.getSubedificioId().getListCmtBlackListMgl() != null
                                && !e.getSubedificioId().getListCmtBlackListMgl().isEmpty()) {

                            for (CmtBlackListMgl blackListMgl : e.getSubedificioId().getListCmtBlackListMgl()) {
                                BigDecimal idBack = null;
                                if (params.get(Constant.CAR_MAS_BLACKLIST_TECNOLOGIA) instanceof BigDecimal) {
                                    idBack = (BigDecimal) params.get(Constant.CAR_MAS_BLACKLIST_TECNOLOGIA);
                                    if ((blackListMgl.getBlackListObj().getBasicaId().compareTo(idBack) == 0)) {

                                        idBlackTec = blackListMgl.getBlackListObj().getNombreBasica();
                                    }
                                }
                            }
                        }
                    }

                    if (e.getSubedificioId().getCmtCuentaMatrizMglObj() != null
                            && e.getSubedificioId().getCmtCuentaMatrizMglObj().getFechaCreacion() != null) {

                        fechaCreacion = format.format(e.getSubedificioId().getCmtCuentaMatrizMglObj().getFechaCreacion());
                    }

                    if (e.getSubedificioId().getCmtCuentaMatrizMglObj() != null
                            && e.getSubedificioId().getCmtCuentaMatrizMglObj().getFechaEdicion() != null) {

                        fechaUltMod = format.format(e.getSubedificioId().getCmtCuentaMatrizMglObj().getFechaEdicion());
                    }

                    if (auxCabecera != null && auxCabecera.equals(Constant.NOMBRE)) {
                        if (e.getSubedificioId() != null && e.getSubedificioId().getCuentaMatrizObj() != null) {
                            valor = e.getSubedificioId().getCuentaMatrizObj().getNombreCuenta();
                        }
                    } else if (auxCabecera != null && auxCabecera.equals(Constant.ADMINISTRACION)) {
                        if (e.getSubedificioId() != null && e.getSubedificioId().getCompaniaAdministracionObj() != null) {
                            valor = e.getSubedificioId().getCompaniaAdministracionObj().getNombreCompania();
                        }
                    } else if (auxCabecera != null && auxCabecera.equals(Constant.ASCENSOR)) {
                        if (e.getSubedificioId() != null && e.getSubedificioId().getCompaniaAscensorObj() != null) {
                            valor = e.getSubedificioId().getCompaniaAscensorObj().getNombreCompania();
                        }
                    } else if (auxCabecera != null && auxCabecera.equals(Constant.ADMINISTRADOR)) {
                        valor = e.getSubedificioId().getAdministrador();
                    } else if (auxCabecera != null && auxCabecera.equals(Constant.TEL_UNO)) {
                        valor = e.getSubedificioId().getTelefonoPorteria();
                    } else if (auxCabecera != null && auxCabecera.equals(Constant.TEL_DOS)) {
                        valor = e.getSubedificioId().getTelefonoPorteria2();
                    } else if (auxCabecera != null && auxCabecera.equals(Constant.PROYECTO)) {
                        if (e.getSubedificioId() != null && e.getSubedificioId().getTipoProyectoObj() != null) {
                            valor = e.getSubedificioId().getTipoProyectoObj().getNombreBasica();
                        }
                    } else if (auxCabecera != null && auxCabecera.equals(Constant.DATOS)) {
                        if (e.getSubedificioId() != null && e.getSubedificioId().getOrigenDatosObj() != null) {
                            valor = e.getSubedificioId().getOrigenDatosObj().getNombreBasica();
                        }
                    } else if (auxCabecera != null && auxCabecera.equals(Constant.NODO)) {

                        if (e.getNodoId() != null) {
                            valor = e.getNodoId().getNodCodigo();
                        }
                    } else if (auxCabecera != null && auxCabecera.equals(Constant.ESTADO)) {
                        if (e.getBasicaIdEstadosTec() != null) {
                            valor = e.getBasicaIdEstadosTec().getNombreBasica();
                        }
                    } else if (auxCabecera != null && auxCabecera.equals(Constant.CONFIGURACION)) {
                        if (e.getTipoConfDistribObj() != null) {
                            valor = e.getTipoConfDistribObj().getNombreBasica();
                        }
                    } else if (auxCabecera != null && auxCabecera.equals(Constant.ALIMENTACION)) {
                        if (e.getAlimentElectObj() != null) {
                            valor = e.getAlimentElectObj().getNombreBasica();
                        }
                    } else if (auxCabecera != null && auxCabecera.equals(Constant.DISTRIBUCION)) {
                        if (e.getTipoDistribucionObj() != null) {
                            valor = e.getTipoDistribucionObj().getNombreBasica();
                        }
                    } else if (auxCabecera != null && auxCabecera.equals(Constant.CONSTRUCTORA)) {
                        if (e.getSubedificioId() != null && e.getSubedificioId().getCompaniaConstructoraObj() != null) {
                            valor = e.getSubedificioId().getCompaniaConstructoraObj().getNombreCompania();
                        }
                    } else if (auxCabecera != null && auxCabecera.equals(Constant.TIPO_CCMM)) {
                        if (e.getSubedificioId() != null && e.getSubedificioId().getTipoEdificioObj() != null) {
                            valor = e.getSubedificioId().getTipoEdificioObj().getNombreBasica();
                        }
                    }
                    
                    sb.append(idTecnologiaSub).append(Constant.SEPARADOR)
                            .append(idSubEdificio).append(Constant.SEPARADOR).append(nombreSubedificio);
                    sb.append(Constant.SEPARADOR).append(tipoSubedificio)
                            .append(Constant.SEPARADOR).append(idCuentaMatriz).append(Constant.SEPARADOR);
                    sb.append(numeroCuenta).append(Constant.SEPARADOR);
                    sb.append(tecnologia).append(Constant.SEPARADOR)
                            .append(departamento).append(Constant.SEPARADOR)
                            .append(ciudad).append(Constant.SEPARADOR).append(idCentroPoblado);
                    sb.append(Constant.SEPARADOR).append(centroPoblado)
                            .append(Constant.SEPARADOR).append(barrio)
                            .append(Constant.SEPARADOR).append(direccion)
                            .append(Constant.SEPARADOR).append(idBlackTec).append(Constant.SEPARADOR);
                    sb.append(valor).append(Constant.SEPARADOR).append("")
                            .append(Constant.SEPARADOR).append("")
                            .append(Constant.SEPARADOR).append(fechaCreacion)
                            .append(Constant.SEPARADOR).append(fechaUltMod).append(Constant.SEPARADOR);                    

                    sb.append("\n");
                    csvData = sb.toString().getBytes();
                    response1.getOutputStream().write(csvData);
                    response1.getOutputStream().flush();
                    response1.flushBuffer();
                }

                System.gc();
                response1.getOutputStream().close();
                fc.responseComplete();
            } else {
                String msn = "No Existen Registros a exportar";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
            }
        } catch (ApplicationException | IOException e) {
            FacesUtil.mostrarMensajeError("Se genero error generando archivo cvs en CargueMasivoModificacionBean: exportarArchivoCsvDetallado()" + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se genero error generando archivo cvs en CargueMasivoModificacionBean: exportarArchivoCsvDetallado()" + e.getMessage(), e, LOGGER);
        }
    }

    
    // Fin reporte detallado
    /**
     *
     * @param workbook
     * @param sheetName
     * @param data
     * @throws Exception
     */
    private void fillSheetbook(HSSFWorkbook workbook, String sheetName, Map<String, Object[]> data) throws ApplicationException {
        try {
            //Create a blank sheet
            HSSFSheet sheet = workbook.createSheet(sheetName);
            HSSFDataFormat format = workbook.createDataFormat();
            //turn off gridlines
            sheet.setDisplayGridlines(true);
            sheet.setPrintGridlines(true);
            sheet.setFitToPage(true);
            sheet.setHorizontallyCenter(true);
            sheet.setVerticallyCenter(true);
            PrintSetup printSetup = sheet.getPrintSetup();
            printSetup.setLandscape(true);
            HSSFCellStyle unlockedCellStyle = workbook.createCellStyle();
            unlockedCellStyle.setDataFormat(format.getFormat("@"));
            unlockedCellStyle.setLocked(false);
            for (int i = 0; i < 27; i++) {
                sheet.setColumnWidth(i, 7000);
            }
            //Iterate over data and write to sheet
            int rownum = 0;

            HSSFCellStyle style = workbook.createCellStyle();
            style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
            style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            HSSFFont font = workbook.createFont();
            font.setColor(HSSFColor.BLACK.index);
            style.setFont(font);
            for (int i = 1; i <= data.size(); i++) {
                HSSFRow row = sheet.createRow(rownum++);
                Object[] objArr = data.get(Integer.toString(i));
                int cellnum = 0;
                for (Object obj : objArr) {
                    HSSFCell cell = row.createCell(cellnum++);
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    String valor = (String) obj;
                    HSSFRichTextString texto = new HSSFRichTextString(valor);
                    cell.setCellValue(texto);
                    cell.setCellStyle(style);

                    if (i > 1 && cellnum > 14) {
                        cell.setCellStyle(unlockedCellStyle);
                    }
                    if (cellnum == 32 || cellnum == 33 || cellnum == 34) {
                        cell.setCellStyle(style);
                    }                  
                }
            }
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg, e);
            throw new ApplicationException("Error al momento de generar la hoja del reporte. EX000: " + e.getMessage(), e);
        }
    }
// Fin reporte detallado

    /**
     *
     * @param workbook
     * @param sheetName
     * @param data
     * @throws Exception
     */
    private void fillSheetbookDetalle(HSSFWorkbook workbook, String sheetName, Map<String, Object[]> data) throws ApplicationException {
        try {
            //Create a blank sheet
            HSSFSheet sheet = workbook.createSheet(sheetName);
            HSSFDataFormat format = workbook.createDataFormat();
            //turn off gridlines
            sheet.setDisplayGridlines(true);
            sheet.setPrintGridlines(true);
            sheet.setFitToPage(true);
            sheet.setHorizontallyCenter(true);
            sheet.setVerticallyCenter(true);
            PrintSetup printSetup = sheet.getPrintSetup();
            printSetup.setLandscape(true);
            HSSFCellStyle unlockedCellStyle = workbook.createCellStyle();
            unlockedCellStyle.setDataFormat(format.getFormat("@"));
            unlockedCellStyle.setLocked(false);
            for (int i = 0; i < 14; i++) {
                sheet.setColumnWidth(i, 7000);
            }

            //Iterate over data and write to sheet
            int rownum = 0;

            HSSFCellStyle style = workbook.createCellStyle();
            style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
            style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

            HSSFFont font = workbook.createFont();
            font.setColor(HSSFColor.BLACK.index);
            style.setFont(font);

            for (int i = 1; i <= data.size(); i++) {
                HSSFRow row = sheet.createRow(rownum++);
                Object[] objArr = data.get(Integer.toString(i));
                int cellnum = 0;
                for (Object obj : objArr) {
                    HSSFCell cell = row.createCell(cellnum++);
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    String valor = (String) obj;
                    HSSFRichTextString texto = new HSSFRichTextString(valor);
                    cell.setCellValue(texto);

                    cell.setCellStyle(style);

                    if (i > 1 && cellnum > 14) {
                        cell.setCellStyle(unlockedCellStyle);
                    }

                    if (cellnum == 18 || cellnum == 19) {
                        cell.setCellStyle(style);
                    }
                }
            }

        } catch (Exception e) {
            throw new ApplicationException("Error al momento de generar la hoja detallada del reporte. EX000: " + e.getMessage(), e);
        }
    }

    // Fin reporte detallado
    /**
     *
     * @param workbook
     * @param sheetName
     * @param data
     * @throws Exception
     */
    private void fillSheetbookCsv(HSSFWorkbook workbook, String sheetName, Map<String, Object[]> data) throws ApplicationException {
        try {
            //Create a blank sheet
            HSSFSheet sheet = workbook.createSheet(sheetName);
            //turn off gridlines
            sheet.setDisplayGridlines(true);
            sheet.setPrintGridlines(true);
            sheet.setFitToPage(true);
            sheet.setHorizontallyCenter(true);
            sheet.setVerticallyCenter(true);
            PrintSetup printSetup = sheet.getPrintSetup();
            printSetup.setLandscape(true);
            CellStyle unlockedCellStyle = workbook.createCellStyle();
            unlockedCellStyle.setLocked(false);
            //Iterate over data and write to sheet
            int rownum = 0;
            for (int i = 1; i <= data.size(); i++) {
                Row row = sheet.createRow(rownum++);
                Object[] objArr = data.get(Integer.toString(i));
                int cellnum = 0;
                for (Object obj : objArr) {
                    Cell cell = row.createCell(cellnum++);
                    if (obj instanceof String) {
                        cell.setCellValue((String) obj);
                    } else if (obj instanceof BigDecimal) {
                        cell.setCellValue(((BigDecimal) obj).doubleValue());
                    }
                }
            }

        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg, e);
            throw new ApplicationException("Error al momento de generar la hoja del detalle del reporte. EX000: " + e.getMessage(), e);
        }
    }

    /**
     * valbuenayf metodo para el cambio de la Departamento
     */
    public void cambioDepartamento() {
        try {
            this.setCiudadSelected(BigDecimal.ZERO);
            this.setCentroPobladoSelected(null);
            this.centroPobladoList = null;
            if (this.departamentoSelected.compareTo(BigDecimal.ZERO) != 0) {
                this.ciudadList = geograficoPoliticoMglFacadeLocal.findCiudades(this.departamentoSelected);
            } else {
                this.ciudadList = null;
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se genero error en CargueMasivoModificacionBean :cambioDepartamento() ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en CargueMasivoModificacionBean :cambioDepartamento() ..." + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * valbuenayf metodo para el cambio de la ciudad
     */
    public void cambioCiudad() {
        try {
            this.setCentroPobladoSelected(null);
            if (this.ciudadSelected != null) {
                List<GeograficoPoliticoMgl> listCentroPoblado = geograficoPoliticoMglFacadeLocal.findCentrosPoblados(this.ciudadSelected);
                if (listCentroPoblado != null && !listCentroPoblado.isEmpty()) {
                    this.centroPobladoList = new ArrayList<SelectItem>();
                    this.centroPobladoList.add(new SelectItem(BigDecimal.ZERO, "TODOS"));
                    for (GeograficoPoliticoMgl c : listCentroPoblado) {
                        this.centroPobladoList.add(new SelectItem(c.getGpoId(), c.getGpoNombre()));
                    }
                }
            } else {
                this.centroPobladoList = null;
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Se genero error en CargueMasivoModificacionBean : cambioCiudad() ..." + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en CargueMasivoModificacionBean : cambioCiudad() ..." + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * valbuenayf metodo para el cambio de la Centro Poblado
     *
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void cambioCentroPoblado() throws ApplicationException {
        if (this.centroPobladoSelected == null || this.centroPobladoSelected.compareTo(BigDecimal.ZERO) == 0) {
            this.codigoNodo = null;
        } else {
            //Consulto lista de nodos
            lstNodos = nodoMglFacade.findCodigoNodoByCentroP(centroPobladoSelected);
        }
    }

    /**
     * valbuenayf metodo para encambio de panel
     */
    public void cambioPanel() {   
            limpiarValores();
            resetFilterDetallado();
            reporteDetallado = null;
            reporteGeneral = null;
            setGeneralDetalle(false);
            setCodigoNodo(null);
            setProgress(false);
            setFinish(false);    
    }

    // Inicio armar las cabeceras de los archivos para exportar
    private static String getCabecera() {
        StringBuilder registro = new StringBuilder();
        registro.append(Constant.TECNO_SUBEDIFICIO_ID).append(Constant.SEPARADOR).append(Constant.SUBEDIFICIO_ID).append(Constant.SEPARADOR);
        registro.append(Constant.NOMBRE_SUBEDIFICIO).append(Constant.SEPARADOR).append(Constant.TIPO_SUBEDIFICIO).append(Constant.SEPARADOR);
        registro.append(Constant.CUENTAMATRIZ_ID).append(Constant.SEPARADOR).append(Constant.NUMERO_CUENTA).append(Constant.SEPARADOR).append(Constant.TECNOLOGIA).append(Constant.SEPARADOR);
        registro.append(Constant.DEPARTAMENTO).append(Constant.SEPARADOR).append(Constant.CIUDAD).append(Constant.SEPARADOR).append(Constant.CENTRO_POBLADO_ID).append(Constant.SEPARADOR);
        registro.append(Constant.CENTRO_POBLADO).append(Constant.SEPARADOR).append(Constant.BARRIO).append(Constant.SEPARADOR);
        registro.append(Constant.DIRECCION).append(Constant.SEPARADOR).append(Constant.NOMBRE).append(Constant.SEPARADOR);
        registro.append(Constant.ADMINISTRACION).append(Constant.SEPARADOR).append(Constant.ASCENSOR).append(Constant.SEPARADOR);
        registro.append(Constant.ADMINISTRADOR).append(Constant.SEPARADOR).append(Constant.TEL_UNO).append(Constant.SEPARADOR);
        registro.append(Constant.TEL_DOS).append(Constant.SEPARADOR).append(Constant.PROYECTO).append(Constant.SEPARADOR);
        registro.append(Constant.DATOS).append(Constant.SEPARADOR).append(Constant.NODO).append(Constant.SEPARADOR);
        registro.append(Constant.ESTADO).append(Constant.SEPARADOR).append(Constant.CONFIGURACION).append(Constant.SEPARADOR);
        registro.append(Constant.ALIMENTACION).append(Constant.SEPARADOR);
        registro.append(Constant.DISTRIBUCION).append(Constant.SEPARADOR).append(Constant.TIPO_CCMM).append(Constant.SEPARADOR);
        registro.append(Constant.CONSTRUCTORA).append(Constant.SEPARADOR).append(Constant.BLACKLIST_TECNOLOGIA).append(Constant.SEPARADOR);
        registro.append(Constant.NOTA).append(Constant.SEPARADOR).append(Constant.ID_BLACKLIST).append(Constant.SEPARADOR);
        registro.append(Constant.FECHA_CREACION).append(Constant.SEPARADOR).append(Constant.FECHA_ULT_MOD).append(Constant.SEPARADOR);
        return registro.toString();
    }

    private static String[] getCabeceraXls() {
        String[] cabeceraDataGral = new String[]{Constant.TECNO_SUBEDIFICIO_ID, Constant.SUBEDIFICIO_ID,
            Constant.NOMBRE_SUBEDIFICIO, Constant.DIRECCION_SUBEDIFICIO,
            Constant.TIPO_SUBEDIFICIO, Constant.CUENTAMATRIZ_ID, Constant.NUMERO_CUENTA,
            Constant.TECNOLOGIA, Constant.DEPARTAMENTO, Constant.CIUDAD, Constant.CENTRO_POBLADO_ID,
            Constant.CENTRO_POBLADO, Constant.BARRIO,
            Constant.DIRECCION, Constant.NOMBRE, Constant.ADMINISTRACION, Constant.ASCENSOR, Constant.ADMINISTRADOR,
            Constant.TEL_UNO, Constant.TEL_DOS, Constant.PROYECTO, Constant.DATOS, Constant.NODO, Constant.ESTADO,
            Constant.CONFIGURACION, Constant.ALIMENTACION, Constant.DISTRIBUCION,
            Constant.TIPO_CCMM, Constant.CONSTRUCTORA, Constant.BLACKLIST_TECNOLOGIA,
            Constant.NOTA,  Constant.ID_BLACKLIST, Constant.FECHA_CREACION, Constant.FECHA_ULT_MOD};
        return cabeceraDataGral;
    }

    private Object[] getCabeceraXlsDetallado() {
        Object[] cabeceraDataGral = new Object[]{Constant.TECNO_SUBEDIFICIO_ID, Constant.SUBEDIFICIO_ID,
            Constant.NOMBRE_SUBEDIFICIO,
            Constant.TIPO_SUBEDIFICIO, Constant.CUENTAMATRIZ_ID, Constant.NUMERO_CUENTA,
            Constant.TECNOLOGIA, Constant.DEPARTAMENTO, Constant.CIUDAD,
            Constant.CENTRO_POBLADO_ID, Constant.CENTRO_POBLADO,
            Constant.BARRIO, Constant.DIRECCION, Constant.BLACKLIST_TECNOLOGIA, auxCabecera, Constant.NUEVO,
            Constant.NOTA, Constant.FECHA_CREACION, Constant.FECHA_ULT_MOD};
        return cabeceraDataGral;
    }

    private String getCabeceraDetallado() {
        StringBuilder registro = new StringBuilder();
        registro.append(Constant.TECNO_SUBEDIFICIO_ID).append(Constant.SEPARADOR).append(Constant.SUBEDIFICIO_ID).append(Constant.SEPARADOR);
        registro.append(Constant.NOMBRE_SUBEDIFICIO).append(Constant.SEPARADOR).append(Constant.TIPO_SUBEDIFICIO).append(Constant.SEPARADOR);
        registro.append(Constant.CUENTAMATRIZ_ID).append(Constant.SEPARADOR).append(Constant.NUMERO_CUENTA).append(Constant.SEPARADOR).append(Constant.TECNOLOGIA).append(Constant.SEPARADOR);
        registro.append(Constant.DEPARTAMENTO).append(Constant.SEPARADOR).append(Constant.CIUDAD).append(Constant.SEPARADOR).append(Constant.CENTRO_POBLADO_ID).append(Constant.SEPARADOR);
        registro.append(Constant.CENTRO_POBLADO).append(Constant.SEPARADOR).append(Constant.BARRIO).append(Constant.SEPARADOR);
        registro.append(Constant.DIRECCION).append(Constant.SEPARADOR).append(Constant.BLACKLIST_TECNOLOGIA).append(Constant.SEPARADOR).append(auxCabecera).append(Constant.SEPARADOR);
        registro.append(Constant.NUEVO).append(Constant.SEPARADOR).append(Constant.NOTA).append(Constant.SEPARADOR);
        registro.append(Constant.FECHA_CREACION).append(Constant.SEPARADOR).append(Constant.FECHA_ULT_MOD).append(Constant.SEPARADOR);
        return registro.toString();
    }
    // Fin armar las cabeceras de los archivos para exportar

    /**
     * metodo para sumar minutos a la fecha de terminacion del reporte
     *
     * @param fecha
     * @param minutos
     * @return
     */
    private Date sumarTiempoFecha(Date fecha, int minutos) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.MINUTE, minutos);
        return calendar.getTime();
    }

    /**
     * valbuenayf Metodo para buscar el numero tiempo de espera para borrar el
     * reporte
     *
     * @param nombreParametro
     * @return
     */
    private Integer tiempoReporte(String nombreParametro) {
        Integer numero = null;
        Integer DEFAULT = 5;
        try {
            ParametrosMgl parametrosMgl = parametrosFacade.findByAcronimoName(nombreParametro);
            if (parametrosMgl != null) {
                numero = Integer.parseInt(parametrosMgl.getParValor());
            } else {
                numero = DEFAULT;
            }
        } catch (ApplicationException | NumberFormatException e) {
            FacesUtil.mostrarMensajeError("Error en MasivoModificacionBean : tiempoReporte()" + e.getMessage(), e, LOGGER);
            return DEFAULT;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en MasivoModificacionBean : tiempoReporte()" + e.getMessage(), e, LOGGER);
        }
        return numero;
    }

    public boolean isGeneralDetalle() {
        return generalDetalle;
    }

    public void setGeneralDetalle(boolean generalDetalle) {
        this.generalDetalle = generalDetalle;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getCodigoNodo() {
        return codigoNodo;
    }

    public void setCodigoNodo(String codigoNodo) {
        this.codigoNodo = codigoNodo;
    }

    public BigDecimal getTecnologiaSelected() {
        return tecnologiaSelected;
    }

    public void setTecnologiaSelected(BigDecimal tecnologiaSelected) {
        this.tecnologiaSelected = tecnologiaSelected;
    }

    public List<CmtBasicaMgl> getListBasicaTecnologia() {
        return listBasicaTecnologia;
    }

    public void setListBasicaTecnologia(List<CmtBasicaMgl> listBasicaTecnologia) {
        this.listBasicaTecnologia = listBasicaTecnologia;
    }

    public BigDecimal getDepartamentoSelected() {
        return departamentoSelected;
    }

    public void setDepartamentoSelected(BigDecimal departamentoSelected) {
        this.departamentoSelected = departamentoSelected;
    }

    public BigDecimal getCiudadSelected() {
        return ciudadSelected;
    }

    public void setCiudadSelected(BigDecimal ciudadSelected) {
        this.ciudadSelected = ciudadSelected;
    }

    public BigDecimal getCentroPobladoSelected() {
        return centroPobladoSelected;
    }

    public void setCentroPobladoSelected(BigDecimal centroPobladoSelected) {
        this.centroPobladoSelected = centroPobladoSelected;
    }

    public List<SelectItem> getDepartamentoList() {
        return departamentoList;
    }

    public void setDepartamentoList(List<SelectItem> departamentoList) {
        this.departamentoList = departamentoList;
    }

    public List<GeograficoPoliticoMgl> getCiudadList() {
        return ciudadList;
    }

    public void setCiudadList(List<GeograficoPoliticoMgl> ciudadList) {
        this.ciudadList = ciudadList;
    }

    public List<SelectItem> getCentroPobladoList() {
        return centroPobladoList;
    }

    public void setCentroPobladoList(List<SelectItem> centroPobladoList) {
        this.centroPobladoList = centroPobladoList;
    }

    public List<CmtBasicaMgl> getListBasicaCargaMasiva() {
        return listBasicaCargaMasiva;
    }

    public void setListBasicaCargaMasiva(List<CmtBasicaMgl> listBasicaCargaMasiva) {
        this.listBasicaCargaMasiva = listBasicaCargaMasiva;
    }

    public String getCarga() {
        return carga;
    }

    public void setCarga(String carga) {
        this.carga = carga;
    }

    public boolean isNmc() {
        return nmc;
    }

    public void setNmc(boolean nmc) {
        this.nmc = nmc;
    }

    public boolean isCia() {
        return cia;
    }

    public void setCia(boolean cia) {
        this.cia = cia;
    }

    public boolean isCis() {
        return cis;
    }

    public void setCis(boolean cis) {
        this.cis = cis;
    }

    public boolean isAdm() {
        return adm;
    }

    public void setAdm(boolean adm) {
        this.adm = adm;
    }

    public boolean isTpu() {
        return tpu;
    }

    public void setTpu(boolean tpu) {
        this.tpu = tpu;
    }

    public boolean isTpd() {
        return tpd;
    }

    public void setTpd(boolean tpd) {
        this.tpd = tpd;
    }

    public boolean isBtp() {
        return btp;
    }

    public void setBtp(boolean btp) {
        this.btp = btp;
    }

    public boolean isBod() {
        return bod;
    }

    public void setBod(boolean bod) {
        this.bod = bod;
    }

    public boolean isEti() {
        return eti;
    }

    public void setEti(boolean eti) {
        this.eti = eti;
    }

    public boolean isBtc() {
        return btc;
    }

    public void setBtc(boolean btc) {
        this.btc = btc;
    }

    public boolean isBae() {
        return bae;
    }

    public void setBae(boolean bae) {
        this.bae = bae;
    }

    public boolean isBtd() {
        return btd;
    }

    public void setBtd(boolean btd) {
        this.btd = btd;
    }

    public boolean isTde() {
        return tde;
    }

    public void setTde(boolean tde) {
        this.tde = tde;
    }

    public boolean isCon() {
        return con;
    }

    public void setCon(boolean con) {
        this.con = con;
    }

    public String getCodigoSeleccion() {
        return codigoSeleccion;
    }

    public void setCodigoSeleccion(String codigoSeleccion) {
        this.codigoSeleccion = codigoSeleccion;
    }

    public List<SelectItem> getCargaMasiva() {
        return cargaMasiva;
    }

    public void setCargaMasiva(List<SelectItem> cargaMasiva) {
        this.cargaMasiva = cargaMasiva;
    }

    public BigDecimal getIdCompaniaAdminFil() {
        return idCompaniaAdminFil;
    }

    public void setIdCompaniaAdminFil(BigDecimal idCompaniaAdminFil) {
        this.idCompaniaAdminFil = idCompaniaAdminFil;
    }

    public BigDecimal getIdCompaniaAscensorFil() {
        return idCompaniaAscensorFil;
    }

    public void setIdCompaniaAscensorFil(BigDecimal idCompaniaAscensorFil) {
        this.idCompaniaAscensorFil = idCompaniaAscensorFil;
    }

    public List<CmtBasicaMgl> getListTiposCcmm() {
        return listTiposCcmm;
    }

    public void setListTiposCcmm(List<CmtBasicaMgl> listTiposCcmm) {
        this.listTiposCcmm = listTiposCcmm;
    }

    public BigDecimal getIdCompaniaConstructoraFil() {
        return idCompaniaConstructoraFil;
    }

    public void setIdCompaniaConstructoraFil(BigDecimal idCompaniaConstructoraFil) {
        this.idCompaniaConstructoraFil = idCompaniaConstructoraFil;
    }

    public String getAdminCompaniaFil() {
        return adminCompaniaFil;
    }

    public void setAdminCompaniaFil(String adminCompaniaFil) {
        this.adminCompaniaFil = adminCompaniaFil;
    }

    public String getTelefonoUnoFil() {
        return telefonoUnoFil;
    }

    public void setTelefonoUnoFil(String telefonoUnoFil) {
        this.telefonoUnoFil = telefonoUnoFil;
    }

    public String getTelefonoDosFil() {
        return telefonoDosFil;
    }

    public void setTelefonoDosFil(String telefonoDosFil) {
        this.telefonoDosFil = telefonoDosFil;
    }

    public BigDecimal getIdCcmmRr() {
        return idCcmmRr;
    }

    public void setIdCcmmRr(BigDecimal idCcmmRr) {
        this.idCcmmRr = idCcmmRr;
    }

    public BigDecimal getIdCcmmMgl() {
        return idCcmmMgl;
    }

    public void setIdCcmmMgl(BigDecimal idCcmmMgl) {
        this.idCcmmMgl = idCcmmMgl;
    }

    public BigDecimal getIdTipoProyectoFil() {
        return idTipoProyectoFil;
    }

    public void setIdTipoProyectoFil(BigDecimal idTipoProyectoFil) {
        this.idTipoProyectoFil = idTipoProyectoFil;
    }

    public BigDecimal getIdOrigenDatosFil() {
        return idOrigenDatosFil;
    }

    public void setIdOrigenDatosFil(BigDecimal idOrigenDatosFil) {
        this.idOrigenDatosFil = idOrigenDatosFil;
    }

    public BigDecimal getIdNodoFil() {
        return idNodoFil;
    }

    public void setIdNodoFil(BigDecimal idNodoFil) {
        this.idNodoFil = idNodoFil;
    }

    public BigDecimal getIdEstadoTecnologiaFil() {
        return idEstadoTecnologiaFil;
    }

    public void setIdEstadoTecnologiaFil(BigDecimal idEstadoTecnologiaFil) {
        this.idEstadoTecnologiaFil = idEstadoTecnologiaFil;
    }

    public BigDecimal getIdTipoConfDistbFil() {
        return idTipoConfDistbFil;
    }

    public void setIdTipoConfDistbFil(BigDecimal idTipoConfDistbFil) {
        this.idTipoConfDistbFil = idTipoConfDistbFil;
    }

    public BigDecimal getIdAlimtElectFil() {
        return idAlimtElectFil;
    }

    public void setIdAlimtElectFil(BigDecimal idAlimtElectFil) {
        this.idAlimtElectFil = idAlimtElectFil;
    }

    public BigDecimal getIdTipoDistribucionFil() {
        return idTipoDistribucionFil;
    }

    public void setIdTipoDistribucionFil(BigDecimal idTipoDistribucionFil) {
        this.idTipoDistribucionFil = idTipoDistribucionFil;
    }

    public BigDecimal getIdTipoEdificioFil() {
        return idTipoEdificioFil;
    }

    public void setIdTipoEdificioFil(BigDecimal idTipoEdificioFil) {
        this.idTipoEdificioFil = idTipoEdificioFil;
    }

    public BigDecimal getBlacklistSelected() {
        return blacklistSelected;
    }

    public void setBlacklistSelected(BigDecimal blacklistSelected) {
        this.blacklistSelected = blacklistSelected;
    }

    public String getNombreCuentaFil() {
        return nombreCuentaFil;
    }

    public void setNombreCuentaFil(String nombreCuentaFil) {
        this.nombreCuentaFil = nombreCuentaFil;
    }

    public List<CmtBasicaMgl> getListTipoProyecto() {
        return listTipoProyecto;
    }

    public void setListTipoProyecto(List<CmtBasicaMgl> listTipoProyecto) {
        this.listTipoProyecto = listTipoProyecto;
    }

    public List<CmtBasicaMgl> getListOrigenDatos() {
        return listOrigenDatos;
    }

    public void setListOrigenDatos(List<CmtBasicaMgl> listOrigenDatos) {
        this.listOrigenDatos = listOrigenDatos;
    }

    public List<CmtBasicaMgl> getListConfiguracion() {
        return listConfiguracion;
    }

    public void setListConfiguracion(List<CmtBasicaMgl> listConfiguracion) {
        this.listConfiguracion = listConfiguracion;
    }

    public List<CmtBasicaMgl> getListAlimentacion() {
        return listAlimentacion;
    }

    public void setListAlimentacion(List<CmtBasicaMgl> listAlimentacion) {
        this.listAlimentacion = listAlimentacion;
    }

    public List<CmtBasicaMgl> getListTipoDistribucion() {
        return listTipoDistribucion;
    }

    public void setListTipoDistribucion(List<CmtBasicaMgl> listTipoDistribucion) {
        this.listTipoDistribucion = listTipoDistribucion;
    }

    public List<CmtBasicaMgl> getListBlacklistTec() {
        return listBlacklistTec;
    }

    public void setListBlacklistTec(List<CmtBasicaMgl> listBlacklistTec) {
        this.listBlacklistTec = listBlacklistTec;
    }

    public List<CmtBasicaMgl> getListEstado() {
        return listEstado;
    }

    public void setListEstado(List<CmtBasicaMgl> listEstado) {
        this.listEstado = listEstado;
    }

    public boolean isConsultar() {
        return consultar;
    }

    public void setConsultar(boolean consultar) {
        this.consultar = consultar;
    }

    public List<CmtTecnologiaSubMgl> getReporteGeneral() {
        return reporteGeneral;
    }

    public void setReporteGeneral(List<CmtTecnologiaSubMgl> reporteGeneral) {
        this.reporteGeneral = reporteGeneral;
    }

    public List<CmtTecnologiaSubMgl> getReporteDetallado() {
        return reporteDetallado;
    }

    public void setReporteDetallado(List<CmtTecnologiaSubMgl> reporteDetallado) {
        this.reporteDetallado = reporteDetallado;
    }

    public List<CmtCompaniaMgl> getListAdmin() {
        return listAdmin;
    }

    public void setListAdmin(List<CmtCompaniaMgl> listAdmin) {
        this.listAdmin = listAdmin;
    }

    public List<CmtCompaniaMgl> getListAscensor() {
        return listAscensor;
    }

    public void setListAscensor(List<CmtCompaniaMgl> listAscensor) {
        this.listAscensor = listAscensor;
    }

    public List<CmtCompaniaMgl> getListConstructora() {
        return listConstructora;
    }

    public void setListConstructora(List<CmtCompaniaMgl> listConstructora) {
        this.listConstructora = listConstructora;
    }

    public String getEstiloObligatorio() {
        return estiloObligatorio;
    }

    public void setEstiloObligatorio(String estiloObligatorio) {
        this.estiloObligatorio = estiloObligatorio;
    }

    public Integer getCantidadRegistros() {
        return cantidadRegistros;
    }

    public void setCantidadRegistros(Integer cantidadRegistros) {
        this.cantidadRegistros = cantidadRegistros;
    }

    public boolean isHayRegistros() {
        return hayRegistros;
    }

    public void setHayRegistros(boolean hayRegistros) {
        this.hayRegistros = hayRegistros;
    }

    public boolean isFiltroReporte() {
        return filtroReporte;
    }

    public void setFiltroReporte(boolean filtroReporte) {
        this.filtroReporte = filtroReporte;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public boolean isProgress() {
        return progress;
    }

    public void setProgress(boolean progress) {
        this.progress = progress;
    }

    public Integer getCount() {
        this.count = CmtReporteCuentaMatrizDtoMgl.getNumeroRegistrosProcesados();
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getUsuarioProceso() {
        return usuarioProceso;
    }

    public void setUsuarioProceso(String usuarioProceso) {
        this.usuarioProceso = usuarioProceso;
    }

    public String getXlsMaxReg() {
        return xlsMaxReg;
    }

    public void setXlsMaxReg(String xlsMaxReg) {
        this.xlsMaxReg = xlsMaxReg;
    }

    public String getCsvMaxReg() {
        return csvMaxReg;
    }

    public void setCsvMaxReg(String csvMaxReg) {
        this.csvMaxReg = csvMaxReg;
    }

    public String getTxtMaxReg() {
        return txtMaxReg;
    }

    public void setTxtMaxReg(String txtMaxReg) {
        this.txtMaxReg = txtMaxReg;
    }

    public String getCodigoNodoParameter() {
        return codigoNodoParameter;
    }

    public void setCodigoNodoParameter(String codigoNodoParameter) {
        this.codigoNodoParameter = codigoNodoParameter;
    }

    public boolean isCnd() {
        return cnd;
    }

    public void setCnd(boolean cnd) {
        this.cnd = cnd;
    }

    public List<String> getLstNodos() {
        return lstNodos;
    }

    public void setLstNodos(List<String> lstNodos) {
        this.lstNodos = lstNodos;
    }

    public String getStrIdCcmmRr() {
        return strIdCcmmRr;
    }

    public void setStrIdCcmmRr(String strIdCcmmRr) {
        this.strIdCcmmRr = strIdCcmmRr;
    }

    public String getStrIdCcmmMgl() {
        return strIdCcmmMgl;
    }

    public void setStrIdCcmmMgl(String strIdCcmmMgl) {
        this.strIdCcmmMgl = strIdCcmmMgl;
    }

    public boolean isBtnReporte() {
        return btnReporte;
    }

    public void setBtnReporte(boolean btnReporte) {
        this.btnReporte = btnReporte;
    }
    
    
            
    // Validar Opciones por Rol
 
    public boolean validarOpcionGeneraReporte() {
        return validarEdicionRol(GNPTBNACM);
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
            FacesUtil.mostrarMensajeError("Error al OrdenTrabajoGestionarBean. " + e.getMessage(), e, LOGGER);
        }
        return false;
    }
    
    

}
