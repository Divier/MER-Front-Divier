package co.com.claro.mgl.mbeans.cm;

import co.com.claro.mer.blockreport.BlockReportServBean;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.cm.CmtBasicaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtRelacionEstadoCmTipoGaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtTipoBasicaMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.AuditoriaDto;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtRelacionEstadoCmTipoGaMgl;
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
 * Esta clase implementa las funcionalidades y validaciones requeridas para el
 * la administracion de Relacion Estado CM - Tipo GA.
 * 
* La clase administra los valores y las validaciones de la vista que
 * corresponden a la Relacion Estado CM - Tipo GA, tambien invoca las
 * funcionalidades para la insercion, consulta, edicion de registros, tambien
 * filtrar y exportar los registros.
 * 
* @author alejandro.martine.ext@claro.com.co
 * @versión 1.0
 */
@ManagedBean(name = "relacionEstadoCmTipoGaBean")
@ViewScoped
@Log4j2
public class RelacionEstadoCmTipoGaBean implements Serializable {

    /* ---------- Inyección de dependencias ---------- */
    @EJB
    CmtBasicaMglFacadeLocal cmtBasicaMglFacadeLocal;
    @EJB
    CmtRelacionEstadoCmTipoGaMglFacadeLocal relacionEstadoCmTipoGaFacadeLocal;
    @EJB
    CmtTipoBasicaMglFacadeLocal cmtTipoBasicaFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacade;
    @Inject
    private BlockReportServBean blockReportServBean;


    /* ------------- Atributos _____________________ */
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    private final String FORMULARIO = "RELACIONESTADOCMTIPOGAVIEW";
    //Opciones agregadas para Rol
    private final String FLTRECTGA = "FLTRECTGA";
    private CmtRelacionEstadoCmTipoGaMgl relacionEstadoCmTipoGaMgl;
    private String codigo;
    private String codigoFiltro;
    private BigDecimal estadoCmFiltro;
    private BigDecimal tipoGaFiltro;
    private String descripcionFiltro;
    private String estadoFiltro;
    private BigDecimal estadoCm;
    private BigDecimal tipoGa;
    private String descripcionRelEstCmTipoGa;
    private List<CmtBasicaMgl> estadoCmList;
    private List<CmtBasicaMgl> tipoGaList;
    private List<CmtRelacionEstadoCmTipoGaMgl> relacionEstadoCmTipoGaMgList;
    private boolean isDetalle = false;
    private final String ROLOPCCODRELEST = "ROLOPCCODRELEST";
    private final String ROLOPCCREATERELEST = "ROLOPCCREATERELEST";
    private final String ROLOPCEXPRELEST = "ROLOPCEXPRELEST";

    private String usuarioVT = null;
    private int perfilVT = 0;
    private boolean renderAuditoria = false;
    private List<AuditoriaDto> informacionAuditoria = null;
    private String descripcionEstadoCmTipoGa = "";
    private SecurityLogin securityLogin;

    public RelacionEstadoCmTipoGaBean() {

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


        } catch (IOException ex) {
            LOGGER.error("Se generea error en RelacionEstadoCmTipoGaBean class ...", ex);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al RelacionEstadoCmTipoGaBean. " + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * loadList carga las listas de Estado CM y Tipo GA y la lista general de
     * registros de las relaciones que se encuentran en la base de datos
     *
     * @author alejandro.martine.ext@claro.com.co
     *
     */
    @PostConstruct
    public void loadList() {
        try {
            relacionEstadoCmTipoGaFacadeLocal.setUser(usuarioVT, perfilVT);
            CmtTipoBasicaMgl cmtTipoBasicaMgl;
            cmtTipoBasicaMgl = cmtTipoBasicaFacadeLocal.findByCodigoInternoApp(
                    Constant.TIPO_BASICA_ESTADO_CUENTA_MATRIZ);
            cmtTipoBasicaMgl = cmtTipoBasicaFacadeLocal.findById(cmtTipoBasicaMgl);
            
            estadoCmList = cmtBasicaMglFacadeLocal.findByTipoBasica(cmtTipoBasicaMgl);
            cmtTipoBasicaMgl=cmtTipoBasicaFacadeLocal.
                    findByCodigoInternoApp(Constant.TIPO_BASICA_ESTADO_INTERNO_GA);
            cmtTipoBasicaMgl = cmtTipoBasicaFacadeLocal.findById(cmtTipoBasicaMgl);
            tipoGaList = cmtBasicaMglFacadeLocal.findByTipoBasica(cmtTipoBasicaMgl);
            relacionEstadoCmTipoGaMgList =
                    relacionEstadoCmTipoGaFacadeLocal.findAll();
            relacionEstadoCmTipoGaMgl = new CmtRelacionEstadoCmTipoGaMgl();
            relacionEstadoCmTipoGaMgl.setEstado("I");
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en RelacionEstadoCmTipoGaBean:loadList(). " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en RelacionEstadoCmTipoGaBean:loadList() " + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * exportExcel exporta los datos de restrados en la tabla.Crea un docuemnto
 de formato Excel y serializa en este los datos de la tabla
 correspondiente, es posible para el usuario filtrar los registros y
 generar un archivo solo con el resultado de la consulta filtrada.
     *
     ** @author alejandro.martine.ext@claro.com.co
     *
     * @throws RelacionEstadoCmTipoGaBean, Ocurrio un error en la creacion del
     * archivo excel
     */
    public void exportExcel() {
        try {
            // Se verifica si está bloqueada la generación de reportes y si
            // el usuario en sesión está autorizado para realizar el proceso.
            if (blockReportServBean.isReportGenerationBlock("Exportar Relación Estado CM – Tipo GA")) return;

            if (relacionEstadoCmTipoGaMgList != null
                    && !relacionEstadoCmTipoGaMgList.isEmpty()) {
                //Libro en blanco
                XSSFWorkbook workbook = new XSSFWorkbook();
                String sheetName = "Relación Estado CM – Tipo GA";
                Object[] cabeceraDataGral = new Object[]{"CODIGO", "ESTADO CM",
                    "TIPO GA", "DESCRIPCION", "ESTADO"};

                Map<String, Object[]> mapDataEstado = new TreeMap<String, Object[]>();
                int fila = 1;
                //se ingresa la cabecera de los datos
                mapDataEstado.put(String.valueOf(fila++), cabeceraDataGral);
                for (CmtRelacionEstadoCmTipoGaMgl e
                        : relacionEstadoCmTipoGaMgList) {
                    mapDataEstado.put(String.valueOf(fila++),
                            new Object[]{e.getCodigo(),
                        e.getBasicaEstadoCMObj().getNombreBasica(),
                        e.getBasicaTiipoGa().getNombreBasica(),
                        e.getDescripcion(),
                        e.getEstado()});
                }
                fillSheetbook(workbook, sheetName, mapDataEstado);
                String fileName = "RelacionEstadoCMTipoGA";
                FacesContext fc = FacesContext.getCurrentInstance();
                HttpServletResponse response =
                        (HttpServletResponse) fc.getExternalContext().getResponse();
                response.reset();
                response.setContentType("application/vnd.ms-excel");

                SimpleDateFormat formato = new SimpleDateFormat("ddMMMyyyy_HH_mm_ss");

                response.setHeader("Content-Disposition", "attachment; filename=" + fileName
                        + formato.format(new Date()) + ".xlsx");
                OutputStream output = response.getOutputStream();

                workbook.write(output);
                output.flush();
                output.close();
                fc.responseComplete();
            } else {
                String msn = "No Existen Registros a exportar";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msn, ""));
            }
        } catch ( IOException e) {
            String msn = "Ocurrio un error en la creacion del archivo excel";
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en RelacionEstadoCmTipoGaBean:exportExcel(). " + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * fillSheetbook serializa la informacion para exportar los datos a un
     * archivo en formato Excel, iterando los registros para definir el tamaño
     * del libro.
     *
     * @author alejandro.martine.ext@claro.com.co
     *
     * @param workbook, Define el tipo de docuemento que sera construido.
     * @param sheetName, Nombre del libro en que se seliazan los datos.
     * @param data, Mapa de datos a serializar en el documento.
     * @throws Error generado por excepcion del metodo.
     */
    private void fillSheetbook(XSSFWorkbook workbook, String sheetName,
            Map<String, Object[]> data)  {
        try {
            //Crea un libro en blanco
            XSSFSheet sheet = workbook.createSheet(sheetName);

            sheet.setDisplayGridlines(true);
            sheet.setPrintGridlines(true);
            sheet.setFitToPage(true);
            sheet.setHorizontallyCenter(true);
            sheet.setVerticallyCenter(true);
            PrintSetup printSetup = sheet.getPrintSetup();
            printSetup.setLandscape(true);

            CellStyle unlockedCellStyle = workbook.createCellStyle();
            unlockedCellStyle.setLocked(false);

            //Itera sobre los datos que seran escritos en el libro
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
              FacesUtil.mostrarMensajeError("Error en RelacionEstadoCmTipoGaBean:fillSheetbook. " + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * El metodo inicializa las variables para la edicion de los registros y
     * carga las listas desplegables.
     *
     * @author alejandro.martine.ext@claro.com.co
     * @param relacionSelected
     */
    public void actualizarDetalle(CmtRelacionEstadoCmTipoGaMgl relacionSelected) {
        relacionEstadoCmTipoGaMgl = relacionSelected;
        isDetalle = true;
        estadoCm = relacionEstadoCmTipoGaMgl.getBasicaEstadoCMObj().getBasicaId();
        tipoGa = relacionEstadoCmTipoGaMgl.getBasicaTiipoGa().getBasicaId();
        relacionEstadoCmTipoGaMgl.setJustificacion("");
    }

    /**
     * El metodo inicializa las variables para la creacion de los registros y
     * carga las listas desplegables.
     *
     * @author alejandro.martine.ext@claro.com.co
     */
    public void crearNuevoRegistro() {
        isDetalle = true;
        relacionEstadoCmTipoGaMgl = new CmtRelacionEstadoCmTipoGaMgl();
        relacionEstadoCmTipoGaMgl.setEstadoCmTipoGaId(null);

        estadoCm = null;
        tipoGa = null;
        relacionEstadoCmTipoGaMgl.setEstado("N");
    }

    /**
     * El metodo asigna valor false a las variables booleanas y carga las listas
     * general de registros.
     *
     * @author alejandro.martine.ext@claro.com.co
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void volver()  {
        try {
            isDetalle = false;
            relacionEstadoCmTipoGaMgList = relacionEstadoCmTipoGaFacadeLocal.findAll();
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en RelacionEstadoCmTipoGaBean:volver(). " + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * El metodo captura los parametros de filtro y ejecuta la respectiva
     * consulta para desplegar los registros que cumplan con las restricciones,
     * el metodo evalua cada uno de los filtros y crea un parametro de consulta
     * con cada uno de ellos que contenga un valor diferente de nulo o vacio.
     *
     * @author alejandro.martine.ext@claro.com.co
     * @throws RelacionEstadoCmTipoGaBean, Ocurrio un error al realizar la
     * consulta de los filtro.
     */
    public void filtrarTabla() {
        try {
            boolean isFiltro = false;

            if (codigoFiltro != null && !codigoFiltro.isEmpty()) {
                isFiltro = true;
                codigoFiltro = codigoFiltro.toUpperCase();
            }

            CmtBasicaMgl cmtBasicaEstadoCmFiltro = null;
            if (estadoCmFiltro != null
                    && estadoCmFiltro.compareTo(BigDecimal.ZERO) != 0) {
                isFiltro = true;
                cmtBasicaEstadoCmFiltro = new CmtBasicaMgl();
                cmtBasicaEstadoCmFiltro.setBasicaId(estadoCmFiltro);
            }

            CmtBasicaMgl cmtBasicaTGaFiltro = null;
            if (tipoGaFiltro != null
                    && tipoGaFiltro.compareTo(BigDecimal.ZERO) != 0) {
                isFiltro = true;
                cmtBasicaTGaFiltro = new CmtBasicaMgl();
                cmtBasicaTGaFiltro.setBasicaId(tipoGaFiltro);
            }

            if (descripcionFiltro != null && !descripcionFiltro.isEmpty()) {
                isFiltro = true;
            }

            if (estadoFiltro != null && !estadoFiltro.isEmpty()) {
                isFiltro = true;
            }

            if (isFiltro) {
                relacionEstadoCmTipoGaMgList =
                        relacionEstadoCmTipoGaFacadeLocal.findByFiltro(codigoFiltro, cmtBasicaEstadoCmFiltro, cmtBasicaTGaFiltro,
                        descripcionFiltro, estadoFiltro);
            } else {
                String msn = "No se ha seleccionado ningún criterio de busqueda";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
            }
        } catch (ApplicationException e) {
            String msn = "Ocurrio un error al realizar la consulta de los filtro";
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en RelacionEstadoCmTipoGaBean:filtrarTabla(). " + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * En el metodo asigna valores de cero o nulos a los filtros.
     *
     * @author alejandro.martine.ext@claro.com.co
     * @throws RelacionEstadoCmTipoGaBean, Ocurrio un error al limpiar el
     * filtro.
     */
    public void limpiarFiltro() {
        try {
            setCodigoFiltro("");
            estadoCmFiltro = BigDecimal.ZERO;
            tipoGaFiltro = BigDecimal.ZERO;
            setDescripcionFiltro("");
            setEstadoFiltro("");

            relacionEstadoCmTipoGaMgList = relacionEstadoCmTipoGaFacadeLocal.findAll();
        } catch (ApplicationException e) {
            String msg = "Ocurrio un error al limpiar el filtro";
            FacesUtil.mostrarMensajeError(msg + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en RelacionEstadoCmTipoGaBean:limpiarFiltro() " + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Modifica el estado de un registro a valor Eliminado, esto hace que el
     * registro no sea visible en la tabla general, para que la eliminacion del
     * registro sea exitosa se debe ingresar un justificacion.
     *
     * @author alejandro.martine.ext@claro.com.co
     * @throws RelacionEstadoCmTipoGaBean, Ocurrio un error Eliminando el estado
     * Interno.
     */
    public void eliminarRelacion() {
        try {
            relacionEstadoCmTipoGaMgl.setEstadoRegistro(ConstantsCM.REGISTRO_ELIMINADO_ID);
            relacionEstadoCmTipoGaFacadeLocal.delete(relacionEstadoCmTipoGaMgl);
            relacionEstadoCmTipoGaMgl = new CmtRelacionEstadoCmTipoGaMgl();
            estadoCm = BigDecimal.ZERO;
            tipoGa = BigDecimal.ZERO;
            String msg = "Registro Eliminado con Exito";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, ""));
        } catch (ApplicationException e) {
            String msn = "Ocurrio un error Eliminando el estado Interno";
            FacesUtil.mostrarMensajeError(msn + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en RelacionEstadoCmTipoGaBean:eliminarRelacion() " + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * Modifica cualquiera de campos del registro y requiere una justificacion
     * para la edicion exitasa.
     *
     * @author alejandro.martine.ext@claro.com.co
     * @throws RelacionEstadoCmTipoGaBean, Ocurrio un error Eliminando el estado
     * Interno.
     */
    public void actualizarRelacion() {
        try {
            CmtBasicaMgl estadoCmTb = new CmtBasicaMgl();
            estadoCmTb.setBasicaId(estadoCm);
            estadoCmTb = cmtBasicaMglFacadeLocal.findById(estadoCmTb);
            relacionEstadoCmTipoGaMgl.setBasicaEstadoCMObj(estadoCmTb);
            CmtBasicaMgl tipoGaTb = new CmtBasicaMgl();
            tipoGaTb.setBasicaId(tipoGa);
            tipoGaTb = cmtBasicaMglFacadeLocal.findById(tipoGaTb);
            relacionEstadoCmTipoGaMgl.setBasicaTiipoGa(tipoGaTb);
            relacionEstadoCmTipoGaMgl.setDetalleOperacion("ACTUALIZACION");
            relacionEstadoCmTipoGaMgl =
                    relacionEstadoCmTipoGaFacadeLocal.update(relacionEstadoCmTipoGaMgl);
            String msg = "Registro Actualizado con Exito";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, ""));
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en RelacionEstadoCmTipoGaBean:actualizarRelacion(). " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en RelacionEstadoCmTipoGaBean:actualizarRelacion() " + e.getMessage(), e, LOGGER);
        }
    }

    /**
     * El metodo permite la inclusion de nuevos regisstros, asigna los valores
     * correspondientes a cada uno de los campos, valida que los campos no
     * contenga valores nullos o vacios, carga las lista de Estado CM y Tipo GA
     * con sus valores correspondientes y persiste en la base de datos los
     * registros .
     *
     * @author alejandro.martine.ext@claro.com.co
     *
     * @throws RelacionEstadoCmTipoGaBean, Ocurrio un error Creando el Registro.
     */
    public void crearRelacion() {
        try {
            if (relacionEstadoCmTipoGaFacadeLocal.exisiteCodigo(relacionEstadoCmTipoGaMgl.getCodigo().toUpperCase())) {
                String msg = "El campo código del registro existe para otra relación";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, ""));
                return;
            }

            CmtBasicaMgl estadoCmTb = new CmtBasicaMgl();
            estadoCmTb.setBasicaId(estadoCm);
            estadoCmTb = cmtBasicaMglFacadeLocal.findById(estadoCmTb);
            relacionEstadoCmTipoGaMgl.setBasicaEstadoCMObj(estadoCmTb);
            CmtBasicaMgl tipoGaTb = new CmtBasicaMgl();

            tipoGaTb.setBasicaId(tipoGa);
            tipoGaTb = cmtBasicaMglFacadeLocal.findById(tipoGaTb);
            relacionEstadoCmTipoGaMgl.setBasicaTiipoGa(tipoGaTb);
            relacionEstadoCmTipoGaMgl.setCodigo(relacionEstadoCmTipoGaMgl.getCodigo().toUpperCase());
            relacionEstadoCmTipoGaMgl.setDetalleOperacion("CREACION");
            relacionEstadoCmTipoGaMgl.setEstadoRegistro(ConstantsCM.REGISTRO_ENUSO_ID);
            relacionEstadoCmTipoGaMgl =
                    relacionEstadoCmTipoGaFacadeLocal.create(relacionEstadoCmTipoGaMgl);
            String msg = "Registro Creado con Exito";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, ""));
        } catch (ApplicationException e) {
            relacionEstadoCmTipoGaMgl.setEstadoCmTipoGaId(null);
            String msg = "Ocurrio un error Creando el Registro";
            LOGGER.error(msg + "-" + e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, ""));
        }
    }

    public void mostrarAuditoria(CmtRelacionEstadoCmTipoGaMgl cmtRelacionEstadoCmTipoGaMglaud) {
        if (cmtRelacionEstadoCmTipoGaMglaud != null) {
            try {
                informacionAuditoria = relacionEstadoCmTipoGaFacadeLocal.construirAuditoria(cmtRelacionEstadoCmTipoGaMglaud);
                descripcionEstadoCmTipoGa = relacionEstadoCmTipoGaMgl.getDescripcion();
                renderAuditoria = true;
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
                String msn = "Se presenta error al mostrar la auditoria";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
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
            FacesUtil.mostrarMensajeError(Constant.MSG_ERROR_VALIDANDO_PERMISOS_EDICION + e.getMessage(), e, LOGGER);
            return false;
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en RelacionEstadoCmTipoGaBean:validarAccion. " + e.getMessage(), e, LOGGER);
        }
        return false;
    }

    public void ocultarAuditoria() {
        renderAuditoria = false;
    }

    public CmtRelacionEstadoCmTipoGaMgl getRelacionEstadoCmTipoGaMgl() {
        return relacionEstadoCmTipoGaMgl;
    }

    public void setRelacionEstadoCmTipoGaMgl(CmtRelacionEstadoCmTipoGaMgl relacionEstadoCmTipoGaMgl) {
        this.relacionEstadoCmTipoGaMgl = relacionEstadoCmTipoGaMgl;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public BigDecimal getEstadoCm() {
        return estadoCm;
    }

    public void setEstadoCm(BigDecimal estadoCm) {
        this.estadoCm = estadoCm;
    }

    public BigDecimal getTipoGa() {
        return tipoGa;
    }

    public void setTipoGa(BigDecimal tipoGa) {
        this.tipoGa = tipoGa;
    }

    public String getDescripcionRelEstCmTipoGa() {
        return descripcionRelEstCmTipoGa;
    }

    public void setDescripcionRelEstCmTipoGa(String descripcionRelEstCmTipoGa) {
        this.descripcionRelEstCmTipoGa = descripcionRelEstCmTipoGa;
    }

    public List<CmtBasicaMgl> getEstadoCmList() {
        return estadoCmList;
    }

    public void setEstadoCmList(List<CmtBasicaMgl> estadoCmList) {
        this.estadoCmList = estadoCmList;
    }

    public List<CmtBasicaMgl> getTipoGaList() {
        return tipoGaList;
    }

    public void setTipoGaList(List<CmtBasicaMgl> tipoGaList) {
        this.tipoGaList = tipoGaList;
    }

    public List<CmtRelacionEstadoCmTipoGaMgl> getRelacionEstadoCmTipoGaMgList() {
        return relacionEstadoCmTipoGaMgList;
    }

    public void setRelacionEstadoCmTipoGaMgList(List<CmtRelacionEstadoCmTipoGaMgl> relacionEstadoCmTipoGaMgList) {
        this.relacionEstadoCmTipoGaMgList = relacionEstadoCmTipoGaMgList;
    }

    public boolean isIsDetalle() {
        return isDetalle;
    }

    public void setIsDetalle(boolean isDetalle) {
        this.isDetalle = isDetalle;
    }

    public CmtBasicaMglFacadeLocal getCmtBasicaMglFacadeLocal() {
        return cmtBasicaMglFacadeLocal;
    }

    public void setCmtBasicaMglFacadeLocal(CmtBasicaMglFacadeLocal cmtBasicaMglFacadeLocal) {
        this.cmtBasicaMglFacadeLocal = cmtBasicaMglFacadeLocal;
    }

    public String getCodigoFiltro() {
        return codigoFiltro;
    }

    public void setCodigoFiltro(String codigoFiltro) {
        this.codigoFiltro = codigoFiltro;
    }

    public BigDecimal getEstadoCmFiltro() {
        return estadoCmFiltro;
    }

    public void setEstadoCmFiltro(BigDecimal estadoCmFiltro) {
        this.estadoCmFiltro = estadoCmFiltro;
    }

    public BigDecimal getTipoGaFiltro() {
        return tipoGaFiltro;
    }

    public void setTipoGaFiltro(BigDecimal tipoGaFiltro) {
        this.tipoGaFiltro = tipoGaFiltro;
    }

    /**
     *
     * @return
     */
    public String getDescripcionFiltro() {
        return descripcionFiltro;
    }

    public void setDescripcionFiltro(String descripcionFiltro) {
        this.descripcionFiltro = descripcionFiltro;
    }

    public String getEstadoFiltro() {
        return estadoFiltro;
    }

    public void setEstadoFiltro(String estadoFiltro) {
        this.estadoFiltro = estadoFiltro;
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

    public String getDescripcionEstadoCmTipoGa() {
        return descripcionEstadoCmTipoGa;
    }

    public void setDescripcionEstadoCmTipoGa(String descripcionEstadoCmTipoGa) {
        this.descripcionEstadoCmTipoGa = descripcionEstadoCmTipoGa;
    }
    
    
       // Validar Opciones por Rol
    
    public boolean validarOpcionFiltrar() {
        return validarEdicionRol(FLTRECTGA);
    }
    
    public boolean validarCodigoRol() {
        return validarEdicionRol(ROLOPCCODRELEST);
    }
    
    public boolean validarCrearRol() {
        return validarEdicionRol(ROLOPCCREATERELEST);
    }
    
    public boolean validarExportarRol() {
        return validarEdicionRol(ROLOPCEXPRELEST);
    }
  
    //funcion General
      private boolean validarEdicionRol(String formulario) {
        try {
            boolean tieneRolPermitido = ValidacionUtil.validarVisualizacion(formulario, 
                    ValidacionUtil.OPC_EDICION, cmtOpcionesRolMglFacade, securityLogin);
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
