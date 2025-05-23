package co.com.claro.mgl.mbeans.direcciones;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.PreFichaMglFacadeLocal;
import co.com.claro.mgl.facade.cm.CmtOpcionesRolMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.PreFichaHHPPDetalleMgl;
import co.com.claro.mgl.jpa.entities.PreFichaMgl;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.ValidacionUtil;
import co.com.claro.visitasTecnicas.business.DireccionRRManager;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.model.file.UploadedFile;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 *
 * @author User
 */
@ManagedBean(name = "actualizarFichaBean")
@ViewScoped
public class FichaActualizarBean {

    private static final Logger LOGGER = LogManager.getLogger(FichaActualizarBean.class);
    private static final String EXT_FILE_XLS = "XLS";
    private static final String EXT_FILE_XLSX = "XLSX";
    private UploadedFile uploadedFile;
    private List<PreFichaHHPPDetalleMgl> detalleHHppModified;

    @EJB
    private PreFichaMglFacadeLocal preFichaMglFacadeLocal;
    @EJB
    private CmtOpcionesRolMglFacadeLocal cmtOpcionesRolMglFacade;
    
    private SecurityLogin securityLogin;
    private FacesContext facesContext = FacesContext.getCurrentInstance();
    private HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    private HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
    
    private String usuarioVT = null;
    private int perfilVT = 0;
      
    public FichaActualizarBean(){
         try {
            securityLogin = new SecurityLogin(FacesContext.getCurrentInstance());
            if (!securityLogin.isLogin()) {
                if (!response.isCommitted()) {
                    response.sendRedirect(securityLogin.redireccionarLogin());
                }
                return;
            }
            usuarioVT = securityLogin.getLoginUser();
            perfilVT = securityLogin.getPerfilUsuario();

        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error al TipoBasicaMglBean. " + e.getMessage(), e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error al TipoBasicaMglBean. " + e.getMessage(), e, LOGGER);
        }
    }

    public String uploadFile() {
        try {
            if(uploadedFile != null && uploadedFile.getFileName() != null){
                
            }else{
                return null;
            }
            
            String extFile = FilenameUtils.getExtension(uploadedFile.getFileName());
            //validacion del tipo de archivo cargado
            if (!extFile.toUpperCase().contains(EXT_FILE_XLS) && !extFile.toUpperCase().contains(EXT_FILE_XLSX)) {
                String msn = "Tipo de Archivo no permitido para cargar la información, debe ser un archivo EXCEL ";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return null;
            }
            //validacion para determinar si el archivo cargado corresponde a la preficha seleccionada
            XSSFWorkbook workbook = new XSSFWorkbook(uploadedFile.getInputStream());

            XSSFSheet sheet = workbook.getSheetAt(0);
            BigDecimal idPreFichaSheet = null;
            Cell idPreFichaCell = sheet.getRow(0).getCell(0);
            if (idPreFichaCell != null) {
                if (idPreFichaCell.getCellType() == Cell.CELL_TYPE_STRING) {
                    idPreFichaSheet = new BigDecimal(idPreFichaCell.getStringCellValue());
                } else if (idPreFichaCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    idPreFichaSheet = new BigDecimal(idPreFichaCell.getNumericCellValue());
                }
            } else {
                String msn = "El Archivo no tiene una ficha asociada en el repositorio, cargue un archivo adecuado";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return null;
            }
            //buscamos la preficha en la base de datos 
            PreFichaMgl fichaMgl = preFichaMglFacadeLocal.getPreFichaById(idPreFichaSheet);

            if (fichaMgl == null) {
                String msn = "El Archivo no tiene una ficha asociada en el repositorio, cargue un archivo adecuado";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return null;
            }

            //revisamos cada registro para determinar si tiene cambio 

            Iterator<Row> rowIterator = sheet.rowIterator();
            //avanzamos la fila 0 que contiene el titulo y el id de la ficha
            rowIterator.next();
            //avanzamos la fila 1 que contiene que contiene los encabezados
            Row rowCabecera = rowIterator.next();
            int lastColumn = rowCabecera.getLastCellNum();
            int numeroColInfoAntes = 3;
            detalleHHppModified = new ArrayList<PreFichaHHPPDetalleMgl>();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                validarCambio(row, lastColumn, numeroColInfoAntes, detalleHHppModified);
            }
            if (detalleHHppModified.isEmpty()) {
                String msn = "No se encontraron cambios en el archivo para ser actualizados";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return null;
            } else {
                String msn = "Se encontraron cambios en el archivo para ser actualizados";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msn, ""));
                return null;
            }
        } catch (ApplicationException | IOException e) {
            FacesUtil.mostrarMensajeError("Error en uploadFile. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en uploadFile. ", e, LOGGER);
        }
        return null;
    }

    private boolean validarCambio(Row row, int lastColumn, int numeroColInfoAntes, List<PreFichaHHPPDetalleMgl> detalleHHppModified) throws ApplicationException {
        boolean result = false;
        PreFichaHHPPDetalleMgl detalleHHPPMgl = null;
        for (int i = numeroColInfoAntes + 1; i < lastColumn; i++) {
            Cell cell = row.getCell(i);
            result = isCellFill(cell);

            if (result) {
                //si existe un cambio buscamos el detalle en la BD con el primer cambio encontrado
                if (detalleHHPPMgl == null) {
                    BigDecimal idHHppDet = null;
                    Cell cellDetalle = row.getCell(0);
                    if (cellDetalle.getCellType() == Cell.CELL_TYPE_STRING) {
                        idHHppDet = new BigDecimal(cellDetalle.getStringCellValue());
                    } else if (cellDetalle.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                        idHHppDet = new BigDecimal(cellDetalle.getNumericCellValue());
                    }
                    detalleHHPPMgl = preFichaMglFacadeLocal.getPreFichaDetalleHHPPById(idHHppDet);
                }
                switch (i) {//segun la informacion que ha cambiado la asignamos al detalle
                    case 4://Barrio
                        detalleHHPPMgl.getFichaGeorreferenciaMgl().setNeighborhood(getValueInStringFromCell(cell));
                        break;
                    case 5://Estrato
                        String stt = getValueInStringFromCell(cell);
                        Double valueStt = new Double(stt);
                        detalleHHPPMgl.getFichaGeorreferenciaMgl().setLevelEconomic(String.valueOf(valueStt.intValue()));
                        detalleHHPPMgl.setModifiedEstrato(true);
                        break;
                    case 6://Direccion
                        detalleHHPPMgl.getFichaGeorreferenciaMgl().setAddress(getValueInStringFromCell(cell));
                        break;
                    case 7://Fuente
                        detalleHHPPMgl.getFichaGeorreferenciaMgl().setSource(getValueInStringFromCell(cell));
                        break;
                    case 8://DirAlterna
                        detalleHHPPMgl.getFichaGeorreferenciaMgl().setAlternateAddress(getValueInStringFromCell(cell));
                        break;
                    case 9://Localidad
                        detalleHHPPMgl.getFichaGeorreferenciaMgl().setLocality(getValueInStringFromCell(cell));
                        detalleHHPPMgl.setModifiedInfoCatastral(true);
                        break;
                    case 10://Manzana
                        detalleHHPPMgl.getFichaGeorreferenciaMgl().setAppletStandar(getValueInStringFromCell(cell));
                        detalleHHPPMgl.setModifiedInfoCatastral(true);
                        break;
                    case 11://CX
                        detalleHHPPMgl.getFichaGeorreferenciaMgl().setCx(getValueInStringFromCell(cell));
                        detalleHHPPMgl.setModifiedInfoCatastral(true);
                        break;
                    case 12://CY
                        detalleHHPPMgl.getFichaGeorreferenciaMgl().setCy(getValueInStringFromCell(cell));
                        detalleHHPPMgl.setModifiedInfoCatastral(true);
                        break;
                    default:
                        break;
                }
            }
        }
        if (detalleHHPPMgl != null) {
            detalleHHppModified.add(detalleHHPPMgl);
        }
        return result;
    }

    private boolean isCellFill(Cell cell) {
        boolean result = false;
        if (cell != null && cell.getCellType() == Cell.CELL_TYPE_STRING) {
            String valueStr = cell.getStringCellValue();
            if (!valueStr.trim().isEmpty()) {
                result = true;
            }
        } else if (cell != null && cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            double valueDouble = cell.getNumericCellValue();
            if (valueDouble != -1.0) {
                result = true;
            }
        }
        return result;
    }

    private String getValueInStringFromCell(Cell cell) {
        String resultValueStr = "";
        if (cell != null && cell.getCellType() == Cell.CELL_TYPE_STRING) {
            resultValueStr = cell.getStringCellValue();
        } else if (cell != null && cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            double valueDouble = cell.getNumericCellValue();
            resultValueStr = String.valueOf(valueDouble);
        }
        return resultValueStr;
    }

    public void actualizarHHPP() {
        DireccionRRManager direccionRRManager = new DireccionRRManager(true);
        direccionRRManager.actualizarHHPPFicha(detalleHHppModified);
    }

    public void volver() {
        detalleHHppModified = null;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public List<PreFichaHHPPDetalleMgl> getDetalleHHppModified() {
        return detalleHHppModified;
    }

    public void setDetalleHHppModified(List<PreFichaHHPPDetalleMgl> detalleHHppModified) {
        this.detalleHHppModified = detalleHHppModified;
    }

    private final String UPDBTNCAF = "UPDBTNCAF";
    
    // Validar Opciones por Rol
    
    public boolean validarOpcionCargarArchivo() {
        return validarEdicionRol(UPDBTNCAF);
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
