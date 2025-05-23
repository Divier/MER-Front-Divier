package co.com.claro.mgl.mbeans.cm;

import co.com.claro.mer.blockreport.BlockReportServBean;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtEstadoInternoGaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoBasicaMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtEstadoInternoGaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.mbeans.util.ConstantsCM;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author User
 */
@ManagedBean(name = "estadoInternoGABean")
@ViewScoped
@Log4j2
public class EstadoInternoGABean implements Serializable {

    @EJB
    CmtBasicaMglFacadeLocal cmtBasicaMglFacadeLocal;
    @EJB
    CmtTipoBasicaMglFacadeLocal cmtTipoBasicaMglFacade;
    @EJB
    CmtEstadoInternoGaMglFacadeLocal estadoInternoGAFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacade;
    @Inject
    private BlockReportServBean blockReportServBean;

    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private final String FORMULARIO = "ESTADOINTERNOGA";
    //Opciones agregadas para Rol
    private final String FLTBTNSTD = "FLTBTNSTD";
    private final String ROLOPCCODESTINT = "ROLOPCCODESTINT";
    private final String ROLOPCCREATEESTINT = "ROLOPCCREATEESTINT";
    private final String ROLOPCEXPESTINT = "ROLOPCEXPESTINT";

    private CmtEstadoInternoGaMgl estadoInternoGaMgl;
    private BigDecimal estadoIniCm;
    private BigDecimal estadoFinalCm;
    private BigDecimal estadoExternoAntGa;
    private BigDecimal estadoExternoPostGa;
    private BigDecimal estadoInternoGa;
    private BigDecimal tipoGa;
    private BigDecimal estadoIniCmFiltro;
    private BigDecimal estadoFinalCmFiltro;
    private BigDecimal estadoExternoAntGaFiltro;
    private BigDecimal estadoExternoPostGaFiltro;
    private BigDecimal estadoInternoGaFiltro;
    private BigDecimal tipoGaFiltro;
    private BigDecimal idEstadoSelected;
    private String descripcionEstadoInternoGa;
    private String documentacionEstadoInternoGa;
    private List<CmtBasicaMgl> estadoCmList;
    private List<CmtBasicaMgl> estadoExternoGaList;
    private List<CmtBasicaMgl> descEstadoInternoGaList;
    private List<CmtBasicaMgl> listTipoGa;
    private List<CmtEstadoInternoGaMgl> estadoInternoGaMgList;

    private boolean renderAuditoria = false;
    private List<AuditoriaDto> informacionAuditoria = null;
    private String descripcionEstadoInternoGaAuditoria = "";
    private String usuarioVT = null;
    private int perfilVT = 0;
    private boolean isDetalle = false;
    private SecurityLogin securityLogin;

    public EstadoInternoGABean() {
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
                perfilVT = securityLogin.getPerfilUsuario();
                if (usuarioVT == null) {
                    session.getAttribute("usuarioM");
                    usuarioVT = (String) session.getAttribute("usuarioM");
                } else {
                    session = (HttpSession) facesContext.getExternalContext().getSession(false);
                    session.setAttribute("usuarioM", usuarioVT);
                }


            } catch (IOException e) {
                FacesUtil.mostrarMensajeError("Se generea error en EstadoInternoGABean class ..." + e.getMessage(), e, LOGGER);
            }

         } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en EstadoInternoGABean class ..." + e.getMessage(), e, LOGGER);
        }

    }

    @PostConstruct
    public void loadList() {
        try {
            estadoInternoGAFacadeLocal.setUser(usuarioVT, perfilVT);

            CmtTipoBasicaMgl cmtTipoBasicaMgl;
            cmtTipoBasicaMgl = cmtTipoBasicaMglFacade.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_ESTADO_CUENTA_MATRIZ);
            estadoCmList = cmtBasicaMglFacadeLocal.findByTipoBasica(cmtTipoBasicaMgl);

            cmtTipoBasicaMgl=cmtTipoBasicaMglFacade.
                    findByCodigoInternoApp(Constant.TIPO_BASICA_ESTADO_EXTERNO_GA);
            cmtTipoBasicaMgl = cmtTipoBasicaMglFacade.findById(cmtTipoBasicaMgl);
            estadoExternoGaList = cmtBasicaMglFacadeLocal.findByTipoBasica(cmtTipoBasicaMgl);

            cmtTipoBasicaMgl=cmtTipoBasicaMglFacade.
                    findByCodigoInternoApp(Constant.TIPO_BASICA_ESTADO_INTERNO_GA);
            cmtTipoBasicaMgl = cmtTipoBasicaMglFacade.findById(cmtTipoBasicaMgl);
            descEstadoInternoGaList = cmtBasicaMglFacadeLocal.findByTipoBasica(cmtTipoBasicaMgl);
            estadoInternoGaMgList = estadoInternoGAFacadeLocal.findAll();

            cmtTipoBasicaMgl=cmtTipoBasicaMglFacade.
                    findByCodigoInternoApp(Constant.TIPO_BASICA_TIPO_GA);
            cmtTipoBasicaMgl = cmtTipoBasicaMglFacade.findById(cmtTipoBasicaMgl);
            listTipoGa = cmtBasicaMglFacadeLocal.findByTipoBasica(cmtTipoBasicaMgl);



            estadoInternoGaMgList = estadoInternoGAFacadeLocal.findAll();


            estadoInternoGaMgl = new CmtEstadoInternoGaMgl();
            estadoInternoGaMgl.setEstado("N");
        } catch (ApplicationException e) {
           FacesUtil.mostrarMensajeError("Se generea error en EstadoInternoGABean class ..." + e.getMessage(), e, LOGGER);
         } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en EstadoInternoGABean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public void exportExcel() {
        try {
            // Se verifica si está bloqueada la generación de reportes y si
            // el usuario en sesión está autorizado para realizar el proceso.
            if (blockReportServBean.isReportGenerationBlock("Exportar Estado Interno GA")) return;

            if (estadoInternoGaMgList != null && !estadoInternoGaMgList.isEmpty()) {
                //Blank workbook            
                XSSFWorkbook workbook = new XSSFWorkbook();
                String sheetName = "Estado Interno de la GA";
                Object[] cabeceraDataGral = new Object[]{"CODIGO", "ABREVIATURA", "ESTADO INICIAL CM", "TIPO GESTION AVANZADA", "ESTADO EXTERNO ANTERIOR GA", "ESTADO INTERNO GA", "ESTADO EXTERNO POSTERIOR GA", "ESTADO FINAL CM", "DESCRIPCION", "DOCUMENTACION", "ESTADO"};

                Map<String, Object[]> mapDataEstado = new TreeMap<String, Object[]>();
                int fila = 1;
                //se ingresa la cabecera de los datos
                mapDataEstado.put(String.valueOf(fila++), cabeceraDataGral);
                for (CmtEstadoInternoGaMgl e : estadoInternoGaMgList) {
                    mapDataEstado.put(String.valueOf(fila++),
                            new Object[]{e.getEstadoInternoGaid(), e.getAbreviatura(),
                        e.getEstadoInicialCM().getNombreBasica(),
                        e.getTipoGaObj().getNombreBasica(),
                        e.getEstadoExternoAnteriorGA().getNombreBasica(),
                        e.getEstadoInternoGA().getNombreBasica(),
                        e.getEstadoExternoPosteriorGA().getNombreBasica(),
                        e.getEstadoFinalCM().getNombreBasica(),
                        e.getDescripcion(),
                        e.getDocumentacion(),
                        e.getEstado()});
                }
                fillSheetbook(workbook, sheetName, mapDataEstado);
                String fileName = "EstadoInternoGA";
                FacesContext fc = FacesContext.getCurrentInstance();
                HttpServletResponse response = (HttpServletResponse) fc.getExternalContext().getResponse();
                response.reset();
                response.setContentType("application/vnd.ms-excel");

                SimpleDateFormat formato = new SimpleDateFormat("ddMMMyyyy_HH_mm_ss");

                response.setHeader("Content-Disposition", "attachment; filename=" + fileName + formato.format(new Date()) + ".xlsx");
                OutputStream output = response.getOutputStream();

                workbook.write(output);
                output.flush();
                output.close();
                fc.responseComplete();
            } else {
                String msn = "No Existen Registros a exportar";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
            }
        } catch (IOException e) {
            String msn = "Ocurrio un error en la creacion del archivo excel";
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en EstadoInternoGABean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    private void fillSheetbook(XSSFWorkbook workbook, String sheetName, Map<String, Object[]> data)  {
        try {
            //Create a blank sheet
            XSSFSheet sheet = workbook.createSheet(sheetName);
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
            FacesUtil.mostrarMensajeError("Se generea error en EstadoInternoGABean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public void eliminarTable() {

    }

    public void refreshDelete() {

    }

    public void actualizarDetalle(CmtEstadoInternoGaMgl estadoSelected) {

        estadoInternoGaMgl = new CmtEstadoInternoGaMgl();
        estadoInternoGaMgl = estadoSelected;
        isDetalle = true;

        estadoIniCm = estadoInternoGaMgl.getEstadoInicialCM().getBasicaId();
        estadoFinalCm = estadoInternoGaMgl.getEstadoFinalCM().getBasicaId();
        estadoExternoAntGa = estadoInternoGaMgl.getEstadoExternoAnteriorGA().getBasicaId();
        estadoExternoPostGa = estadoInternoGaMgl.getEstadoExternoPosteriorGA().getBasicaId();
        estadoInternoGa = estadoInternoGaMgl.getEstadoInternoGA().getBasicaId();
        tipoGa = estadoInternoGaMgl.getTipoGaObj().getBasicaId();
        estadoInternoGaMgl.setJustificacion("");

    }

    public void crearNuevoRegsitro() {
        isDetalle = true;
        estadoInternoGaMgl = new CmtEstadoInternoGaMgl();
        estadoInternoGaMgl.setEstadoInternoGaid(null);

        estadoIniCm = null;
        estadoFinalCm = null;
        estadoExternoAntGa = null;
        estadoExternoPostGa = null;
        estadoInternoGa = null;
        tipoGa = null;

        estadoInternoGaMgl.setEstado("N");
    }

    public void volver()  {
        try {
            estadoInternoGaMgList = estadoInternoGAFacadeLocal.findAll();
            isDetalle = false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en EstadoInternoGABean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public void filtrarTabla() {
        try {
            boolean isFiltro = false;
            CmtBasicaMgl cmtBasicaEstadoIniCmFiltro = null;
            if (estadoIniCmFiltro != null && estadoIniCmFiltro.compareTo(BigDecimal.ZERO) != 0) {
                isFiltro = true;
                cmtBasicaEstadoIniCmFiltro = new CmtBasicaMgl();
                cmtBasicaEstadoIniCmFiltro.setBasicaId(estadoIniCmFiltro);
            }

            CmtBasicaMgl cmtBasicaEstadoExternoAntGaFiltro = null;
            if (estadoExternoAntGaFiltro != null && estadoExternoAntGaFiltro.compareTo(BigDecimal.ZERO) != 0) {
                isFiltro = true;
                cmtBasicaEstadoExternoAntGaFiltro = new CmtBasicaMgl();
                cmtBasicaEstadoExternoAntGaFiltro.setBasicaId(estadoExternoAntGaFiltro);
            }

            CmtBasicaMgl cmtBasicaEstadoInternoGaFiltro = null;
            if (estadoInternoGaFiltro != null && estadoInternoGaFiltro.compareTo(BigDecimal.ZERO) != 0) {
                isFiltro = true;
                cmtBasicaEstadoInternoGaFiltro = new CmtBasicaMgl();
                cmtBasicaEstadoInternoGaFiltro.setBasicaId(estadoInternoGaFiltro);
            }

            CmtBasicaMgl cmtBasicaEstadoExternoPostGaFiltro = null;
            if (estadoExternoPostGaFiltro != null && estadoExternoPostGaFiltro.compareTo(BigDecimal.ZERO) != 0) {
                isFiltro = true;
                cmtBasicaEstadoExternoPostGaFiltro = new CmtBasicaMgl();
                cmtBasicaEstadoExternoPostGaFiltro.setBasicaId(estadoExternoPostGaFiltro);
            }

            CmtBasicaMgl cmtBasicaEstadoFinalCmFiltro = null;
            if (estadoFinalCmFiltro != null && estadoFinalCmFiltro.compareTo(BigDecimal.ZERO) != 0) {
                isFiltro = true;
                cmtBasicaEstadoFinalCmFiltro = new CmtBasicaMgl();
                cmtBasicaEstadoFinalCmFiltro.setBasicaId(estadoFinalCmFiltro);
            }

            CmtBasicaMgl cmtBasicaTipoGa = null;
            if (tipoGaFiltro != null && tipoGaFiltro.compareTo(BigDecimal.ZERO) != 0) {
                isFiltro = true;
                cmtBasicaTipoGa = new CmtBasicaMgl();
                cmtBasicaTipoGa.setBasicaId(tipoGaFiltro);
            }


            if (isFiltro) {
                LOGGER.error("tiene filtro....");
                estadoInternoGaMgList = estadoInternoGAFacadeLocal.findByFiltro(cmtBasicaEstadoIniCmFiltro,
                        cmtBasicaEstadoExternoAntGaFiltro,
                        cmtBasicaEstadoInternoGaFiltro,
                        cmtBasicaEstadoExternoPostGaFiltro,
                        cmtBasicaEstadoFinalCmFiltro,
                        cmtBasicaTipoGa);
            } else {
                LOGGER.error("Sin filtro....");
            }

        } catch (ApplicationException e) {
            String msn = "Ocurrio un error al realizar la consulta de los filtro";
             FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en EstadoInternoGABean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public void limpiarFiltro() {
        try {
            estadoIniCmFiltro = BigDecimal.ZERO;
            estadoFinalCmFiltro = BigDecimal.ZERO;
            estadoExternoAntGaFiltro = BigDecimal.ZERO;
            estadoExternoPostGaFiltro = BigDecimal.ZERO;
            estadoInternoGaFiltro = BigDecimal.ZERO;
            tipoGaFiltro = BigDecimal.ZERO;
            estadoInternoGaMgList = estadoInternoGAFacadeLocal.findAll();
        } catch (ApplicationException e) {
            String msn = "Ocurrio un error al limpiar el filtro";
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en EstadoInternoGABean class ..." + e.getMessage(), e, LOGGER);
        }

    }

    public void eliminarEstado() {
        try {
            if (estadoInternoGAFacadeLocal.delete(estadoInternoGaMgl)) {
                estadoInternoGaMgl = new CmtEstadoInternoGaMgl();

                String msn = "Registro Eliminado con Exito";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
            } else {
                String msn = "Ocurrio un error Eliminando el estado Interno";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            }

        } catch (ApplicationException e) {
            String msn = "Ocurrio un error Eliminando el estado Interno";
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en EstadoInternoGABean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public void actualizarEstado() {
        try {
            CmtBasicaMgl estadoInicialCmTb = new CmtBasicaMgl();
            estadoInicialCmTb.setBasicaId(estadoIniCm);
            estadoInicialCmTb = cmtBasicaMglFacadeLocal.findById(estadoInicialCmTb);
            estadoInternoGaMgl.setEstadoInicialCM(estadoInicialCmTb);

            CmtBasicaMgl estadoFinalCmTb = new CmtBasicaMgl();
            estadoFinalCmTb.setBasicaId(estadoFinalCm);
            estadoFinalCmTb = cmtBasicaMglFacadeLocal.findById(estadoFinalCmTb);
            estadoInternoGaMgl.setEstadoFinalCM(estadoFinalCmTb);

            CmtBasicaMgl estadoExtAntCmTb = new CmtBasicaMgl();
            estadoExtAntCmTb.setBasicaId(estadoExternoAntGa);
            estadoExtAntCmTb = cmtBasicaMglFacadeLocal.findById(estadoExtAntCmTb);
            estadoInternoGaMgl.setEstadoExternoAnteriorGA(estadoExtAntCmTb);

            CmtBasicaMgl estadoExtPostCmTb = new CmtBasicaMgl();
            estadoExtPostCmTb.setBasicaId(estadoExternoPostGa);
            estadoExtPostCmTb = cmtBasicaMglFacadeLocal.findById(estadoExtPostCmTb);
            estadoInternoGaMgl.setEstadoExternoPosteriorGA(estadoExtPostCmTb);

            CmtBasicaMgl estadoIntGa = new CmtBasicaMgl();
            estadoIntGa.setBasicaId(estadoInternoGa);
            estadoIntGa = cmtBasicaMglFacadeLocal.findById(estadoIntGa);
            estadoInternoGaMgl.setEstadoInternoGA(estadoIntGa);


            CmtBasicaMgl tipGa = new CmtBasicaMgl();
            tipGa.setBasicaId(tipoGa);
            tipGa = cmtBasicaMglFacadeLocal.findById(tipGa);
            estadoInternoGaMgl.setTipoGaObj(tipGa);


            estadoInternoGaMgl.setDetalleOperacion("ACTUALIZACION");
            estadoInternoGAFacadeLocal.update(estadoInternoGaMgl);
            String msn = "Registro Actualizado con Exito";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
        } catch (ApplicationException e) {
            String msn = "Ocurrio un error Actualizando el estado Interno";
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en EstadoInternoGABean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public void crearEstado() {
        try {
            if (estadoInternoGAFacadeLocal.existeCodigo(estadoInternoGaMgl.getCodigo().toUpperCase())) {
                String msn = "El Codigo ya existe para otra configuración de estado interno GA";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return;
            }
            CmtBasicaMgl estadoInicialCmTb = new CmtBasicaMgl();
            estadoInicialCmTb.setBasicaId(estadoIniCm);
            estadoInternoGaMgl.setEstadoInicialCM(estadoInicialCmTb);

            CmtBasicaMgl estadoFinalCmTb = new CmtBasicaMgl();
            estadoFinalCmTb.setBasicaId(estadoFinalCm);
            estadoInternoGaMgl.setEstadoFinalCM(estadoFinalCmTb);

            CmtBasicaMgl estadoExtAntCmTb = new CmtBasicaMgl();
            estadoExtAntCmTb.setBasicaId(estadoExternoAntGa);
            estadoInternoGaMgl.setEstadoExternoAnteriorGA(estadoExtAntCmTb);

            CmtBasicaMgl estadoExtPostCmTb = new CmtBasicaMgl();
            estadoExtPostCmTb.setBasicaId(estadoExternoPostGa);
            estadoInternoGaMgl.setEstadoExternoPosteriorGA(estadoExtPostCmTb);

            CmtBasicaMgl tipGa = new CmtBasicaMgl();
            tipGa.setBasicaId(tipoGa);
            estadoInternoGaMgl.setTipoGaObj(tipGa);


            CmtBasicaMgl estadoIntGa = new CmtBasicaMgl();
            estadoIntGa.setBasicaId(estadoInternoGa);
            estadoInternoGaMgl.setEstadoInternoGA(estadoIntGa);
            estadoInternoGaMgl.setDetalleOperacion("CREACION");
            estadoInternoGaMgl.setEstadoRegistro(ConstantsCM.REGISTRO_ENUSO_ID);
            estadoInternoGaMgl.setCodigo(estadoInternoGaMgl.getCodigo().toUpperCase());
            estadoInternoGaMgl = estadoInternoGAFacadeLocal.create(estadoInternoGaMgl);

            String msn = "Registro Creado con Exito";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
        } catch (ApplicationException e) {
            String msn = "Ocurrio un error Creando el Registro";
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Se generea error en EstadoInternoGABean class ..." + e.getMessage(), e, LOGGER);
        }
    }

    public void mostrarAuditoria(CmtEstadoInternoGaMgl cmtEstadoInternoGaMgl) {
        if (cmtEstadoInternoGaMgl != null) {
            try {
                informacionAuditoria = estadoInternoGAFacadeLocal.construirAuditoria(cmtEstadoInternoGaMgl);
                descripcionEstadoInternoGaAuditoria = cmtEstadoInternoGaMgl.getDescripcion();
                renderAuditoria = true;
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                String msn = "Se presenta error al mostrar la auditoria";
                 FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
            } catch (Exception e) {
                FacesUtil.mostrarMensajeError("Se generea error en EstadoInternoGABean class ..." + e.getMessage(), e, LOGGER);
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
        return validarAccion(ValidacionUtil.OPC_CREACION);
    }

    /**
     *
     * @return
     */
    public boolean validarEdicion() {
        return validarAccion(ValidacionUtil.OPC_EDICION);
    }

    /**
     *
     * @return
     */
    public boolean validarBorrado() {
        return validarAccion(ValidacionUtil.OPC_BORRADO);
    }

    /**
     * M&eacute;todo para validar las acciones a realizar en el formulario
     *
     * @param opcion String nombre de la opci&oacute;n que realizar&aacute; el
     * componente
     * @return boolean indicador para verificar si se visualizan o no los
     * componentes
     */
    private boolean validarAccion(String opcion) {
        try {
            return ValidacionUtil.validarVisualizacion(FORMULARIO, opcion, cmtOpcionesRolMglFacadeLocal, securityLogin);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError( Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION+ e.getMessage(), e, LOGGER);
            return false;
       } catch (Exception e) {
                FacesUtil.mostrarMensajeError("Se generea error en EstadoInternoGABean class ..." + e.getMessage(), e, LOGGER);
            }
        return false;
    }
    
    public boolean validarCodigoRol() {
        return validarEdicion(ROLOPCCODESTINT);
    }
    
    public boolean validarCrearRol() {
        return validarEdicion(ROLOPCCREATEESTINT);
    }
    
    public boolean validarExportarRol() {
        return validarEdicion(ROLOPCEXPESTINT);
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

    public void ocultarAuditoria() {
        renderAuditoria = false;
    }

    public CmtEstadoInternoGaMgl getEstadoInternoGaMgl() {
        return estadoInternoGaMgl;
    }

    public void setEstadoInternoGaMgl(CmtEstadoInternoGaMgl estadoInternoGaMgl) {
        this.estadoInternoGaMgl = estadoInternoGaMgl;
    }

    public List<CmtBasicaMgl> getEstadoCmList() {
        return estadoCmList;
    }

    public void setEstadoCmList(List<CmtBasicaMgl> estadoCmList) {
        this.estadoCmList = estadoCmList;
    }

    public List<CmtBasicaMgl> getEstadoExternoGaList() {
        return estadoExternoGaList;
    }

    public void setEstadoExternoGaList(List<CmtBasicaMgl> estadoExternoGaList) {
        this.estadoExternoGaList = estadoExternoGaList;
    }

    public BigDecimal getEstadoIniCm() {
        return estadoIniCm;
    }

    public void setEstadoIniCm(BigDecimal estadoIniCm) {
        this.estadoIniCm = estadoIniCm;
    }

    public List<CmtBasicaMgl> getDescEstadoInternoGaList() {
        return descEstadoInternoGaList;
    }

    public void setDescEstadoInternoGaList(List<CmtBasicaMgl> DescEstadoInternoGaList) {
        this.descEstadoInternoGaList = DescEstadoInternoGaList;
    }

    public BigDecimal getEstadoFinalCm() {
        return estadoFinalCm;
    }

    public void setEstadoFinalCm(BigDecimal estadoFinalCm) {
        this.estadoFinalCm = estadoFinalCm;
    }

    public BigDecimal getEstadoExternoAntGa() {
        return estadoExternoAntGa;
    }

    public void setEstadoExternoAntGa(BigDecimal estadoExternoAntGa) {
        this.estadoExternoAntGa = estadoExternoAntGa;
    }

    public BigDecimal getEstadoExternoPostGa() {
        return estadoExternoPostGa;
    }

    public void setEstadoExternoPostGa(BigDecimal estadoExternoPostGa) {
        this.estadoExternoPostGa = estadoExternoPostGa;
    }

    public BigDecimal getEstadoInternoGa() {
        return estadoInternoGa;
    }

    public void setEstadoInternoGa(BigDecimal estadoInternoGa) {
        this.estadoInternoGa = estadoInternoGa;
    }

    public String getDescripcionEstadoInternoGa() {
        return descripcionEstadoInternoGa;
    }

    public void setDescripcionEstadoInternoGa(String descripcionEstadoInternoGa) {
        this.descripcionEstadoInternoGa = descripcionEstadoInternoGa;
    }

    public String getDocumentacionEstadoInternoGa() {
        return documentacionEstadoInternoGa;
    }

    public void setDocumentacionEstadoInternoGa(String documentacionEstadoInternoGa) {
        this.documentacionEstadoInternoGa = documentacionEstadoInternoGa;
    }

    public List<CmtEstadoInternoGaMgl> getEstadoInternoGaMgList() {
        return estadoInternoGaMgList;
    }

    public void setEstadoInternoGaMgList(List<CmtEstadoInternoGaMgl> estadoInternoGaMgList) {
        this.estadoInternoGaMgList = estadoInternoGaMgList;
    }

    public BigDecimal getEstadoIniCmFiltro() {
        return estadoIniCmFiltro;
    }

    public void setEstadoIniCmFiltro(BigDecimal estadoIniCmFiltro) {
        this.estadoIniCmFiltro = estadoIniCmFiltro;
    }

    public BigDecimal getEstadoFinalCmFiltro() {
        return estadoFinalCmFiltro;
    }

    public void setEstadoFinalCmFiltro(BigDecimal estadoFinalCmFiltro) {
        this.estadoFinalCmFiltro = estadoFinalCmFiltro;
    }

    public BigDecimal getEstadoExternoAntGaFiltro() {
        return estadoExternoAntGaFiltro;
    }

    public void setEstadoExternoAntGaFiltro(BigDecimal estadoExternoAntGaFiltro) {
        this.estadoExternoAntGaFiltro = estadoExternoAntGaFiltro;
    }

    public BigDecimal getEstadoExternoPostGaFiltro() {
        return estadoExternoPostGaFiltro;
    }

    public void setEstadoExternoPostGaFiltro(BigDecimal estadoExternoPostGaFiltro) {
        this.estadoExternoPostGaFiltro = estadoExternoPostGaFiltro;
    }

    public BigDecimal getEstadoInternoGaFiltro() {
        return estadoInternoGaFiltro;
    }

    public void setEstadoInternoGaFiltro(BigDecimal estadoInternoGaFiltro) {
        this.estadoInternoGaFiltro = estadoInternoGaFiltro;
    }

    public boolean isIsDetalle() {
        return isDetalle;
    }

    public void setIsDetalle(boolean isDetalle) {
        this.isDetalle = isDetalle;
    }

    public BigDecimal getIdEstadoSelected() {
        return idEstadoSelected;
    }

    public void setIdEstadoSelected(BigDecimal idEstadoSelected) {
        this.idEstadoSelected = idEstadoSelected;
    }

    public List<CmtBasicaMgl> getListTipoGa() {
        return listTipoGa;
    }

    public void setListTipoGa(List<CmtBasicaMgl> listTipoGa) {
        this.listTipoGa = listTipoGa;
    }

    public BigDecimal getTipoGa() {
        return tipoGa;
    }

    public void setTipoGa(BigDecimal tipoGa) {
        this.tipoGa = tipoGa;
    }

    public BigDecimal getTipoGaFiltro() {
        return tipoGaFiltro;
    }

    public void setTipoGaFiltro(BigDecimal tipoGaFiltro) {
        this.tipoGaFiltro = tipoGaFiltro;
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

    public String getDescripcionEstadoInternoGaAuditoria() {
        return descripcionEstadoInternoGaAuditoria;
    }

    public void setDescripcionEstadoInternoGaAuditoria(String descripcionEstadoInternoGaAuditoria) {
        this.descripcionEstadoInternoGaAuditoria = descripcionEstadoInternoGaAuditoria;
    }
    
    
        
    // Validar Opciones por Rol
   
      public boolean validarOpcionFiltrar() {
        return validarEdicionRol(FLTBTNSTD);
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
