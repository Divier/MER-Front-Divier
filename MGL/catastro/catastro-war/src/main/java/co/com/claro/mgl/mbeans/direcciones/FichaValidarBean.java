/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.direcciones;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.PreFichaMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.PreFichaHHPPDetalleMgl;
import co.com.claro.mgl.jpa.entities.PreFichaMgl;
import co.com.claro.mgl.jpa.entities.PreFichaXlsMgl;
import co.com.telmex.catastro.services.util.EnvioCorreo;
import co.com.telmex.catastro.util.FacesUtil;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.activation.DataSource;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.FacesContext;
import javax.mail.util.ByteArrayDataSource;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
@ManagedBean(name = "validarFichaBean")
@ViewScoped
public class FichaValidarBean {

    private static final Logger LOGGER = LogManager.getLogger(FichaValidarBean.class);
    private HtmlDataTable dataTable = new HtmlDataTable();
    private BigDecimal idPrefichaSelected;
    private List<PreFichaMgl> fichaToGenerarList;
    private List<PreFichaXlsMgl> fichaXlsMglList;
    private List<PreFichaHHPPDetalleMgl> detHHHPPListToValidate;
    private PreFichaMgl prefichaCrear;
    private String showHHPPToValidate = String.valueOf(false);
    @EJB
    private PreFichaMglFacadeLocal preFichaMglFacadeLocal;

    @PostConstruct
    public void loadList() {
        try {
            fichaToGenerarList = new ArrayList<PreFichaMgl>();
            //se cargan las Preficha que hayan sido ya se georreferenciaron
            fichaToGenerarList = preFichaMglFacadeLocal.getListFichaToValidate();
        } catch (ApplicationException e) {
            LOGGER.error("PrefichaBean-loadList-" + e.getMessage());
        } catch (Exception e) {
            LOGGER.error("PrefichaBean-loadList-" + e.getMessage());
        }
    }

    public void cargarDetalleFicha() {
        try {
            showHHPPToValidate = String.valueOf(false);
            prefichaCrear = (PreFichaMgl) dataTable.getRowData();
            fichaXlsMglList = preFichaMglFacadeLocal.getListXLSByPrefichaFase(prefichaCrear.getPrfId(), prefichaCrear.getFase());
            detHHHPPListToValidate = new ArrayList<PreFichaHHPPDetalleMgl>();
            //buscamos los hhpp que no fueron georreferenciados para su proceso de validacion
            for (PreFichaXlsMgl p : fichaXlsMglList) {
                if (!p.getClasificacion().equalsIgnoreCase("HHPPSN")) {
                    for (PreFichaHHPPDetalleMgl dt : p.getDetalleHHPPList()) {
                        if (dt.getStratus().equalsIgnoreCase("NG")) {
                            detHHHPPListToValidate.add(dt);
                        }
                    }
                }

            }
            showHHPPToValidate = String.valueOf(true);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en cargarDetalleFicha. al cargar la informacion detallada de los HHPP a validar de la ficha selecionada. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en cargarDetalleFicha. al cargar la informacion detallada de los HHPP a validar de la ficha selecionada. ", e, LOGGER);
        }
    }

    public void crearXLS() {
        try {
            //Blank workbook            
            XSSFWorkbook workbook = new XSSFWorkbook();
            NodoMgl nodoCodigo = prefichaCrear.getNodoMgl();

            Object[] cabeceraDataGral = new Object[]{"IDHHPP", "DIRECCION", "ID", "CIUDAD", "BARRIO", "ESTRATO", "DIRECCION", "FUENTE", "DIR. ALTERNA", "LOCALIDAD", "MANZANA", "CX", "CY"};
            if (detHHHPPListToValidate != null && detHHHPPListToValidate.size() > 0) {
                Map<String, Object[]> dataHHPPToValidate = new TreeMap<String, Object[]>();
                int fila = 1;
                //llenamos el Map con los datos a ser exportados al excel
                for (PreFichaHHPPDetalleMgl dt : detHHHPPListToValidate) {
                    dataHHPPToValidate.put(String.valueOf(fila++),
                            new Object[]{dt.getId(), dt.getFichaGeorreferenciaMgl().getAddress(),
                        nodoCodigo.getNodCodigo(), nodoCodigo.getGpoId(),});
                }
                fillSheetbook(workbook,
                        "HHPP VALIDAR", dataHHPPToValidate, nodoCodigo, cabeceraDataGral, idPrefichaSelected);
                
                String fileName = "InfoFichaValidacion";
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
            }
        } catch (ApplicationException | IOException e) {
            FacesUtil.mostrarMensajeError("Error en crearXLS. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en crearXLS. ", e, LOGGER);
        }
    }
    
    public void enviarMailXLS() {
        try {
            //Blank workbook            
            XSSFWorkbook workbook = new XSSFWorkbook();
            NodoMgl nodoCodigo = prefichaCrear.getNodoMgl();

            Object[] cabeceraDataGral = new Object[]{"IDHHPP", "DIRECCION", "ID", "CIUDAD", "BARRIO", "ESTRATO", "DIRECCION", "FUENTE", "DIR. ALTERNA", "LOCALIDAD", "MANZANA", "CX", "CY"};
            if (detHHHPPListToValidate != null && detHHHPPListToValidate.size() > 0) {
                Map<String, Object[]> dataHHPPToValidate = new TreeMap<String, Object[]>();
                int fila = 1;
                //llenamos el Map con los datos a ser exportados al excel
                for (PreFichaHHPPDetalleMgl dt : detHHHPPListToValidate) {
                    dataHHPPToValidate.put(String.valueOf(fila++),
                            new Object[]{dt.getId(), dt.getFichaGeorreferenciaMgl().getAddress(),
                        nodoCodigo.getNodCodigo(), nodoCodigo.getGpoId()});
                }
                fillSheetbook(workbook,
                        "HHPP VALIDAR", dataHHPPToValidate, nodoCodigo, cabeceraDataGral, idPrefichaSelected);
                
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                workbook.write(bos);
                
                
                DataSource fds = new ByteArrayDataSource(bos.toByteArray(), "application/vnd.ms-excel");
                bos.close();
                EnvioCorreo.sendMailWithAttach("10.244.140.20", 
                                    "modulovisitas@claro.com.co", 
                                    "johnnatan.rodrig.ext@claro.com.co", "", "",
                                    "prueba envio correo", 
                                    false, new StringBuffer("cuerpo del correo prueba" ), true, fds);
            }
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en enviarMailXLS. ", e, LOGGER);
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error en enviarMailXLS. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en enviarMailXLS. ", e, LOGGER);
        }
    }

    private void fillSheetbook(XSSFWorkbook workbook, String sheetName,
            Map<String, Object[]> data,
            NodoMgl nodo, Object[] cabeceraDataGral,
            BigDecimal idPreficha) throws ApplicationException {
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
            Row row1 = sheet.createRow(rownum++);
            Cell cell1 = row1.createCell(0);
            cell1.setCellValue(String.valueOf(idPreficha));
            
            cell1 = row1.createCell(1);
            cell1.setCellValue("info validar");
            
            cell1 = row1.createCell(4);
            cell1.setCellValue("informacion catastral");
            Row rowCabecera = sheet.createRow(rownum++);
            for (int i = 0; i < cabeceraDataGral.length; i++) {
                Cell cell = rowCabecera.createCell(i);
                cell.setCellValue((String) cabeceraDataGral[i]);
            }
            //adicionamos la info al archivo
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
                for (int u = 0; u < 9; u++) {
                    Cell cell = row.createCell(cellnum++);
                    cell.setCellStyle(unlockedCellStyle);
                    cell.setCellValue(" ");
                }
            }

        } catch (RuntimeException e) {
            LOGGER.error("Error en fillSheetbook. " +e.getMessage(), e);  
            throw new ApplicationException("Error en fillSheetbook. " + e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Error en fillSheetbook. " +e.getMessage(), e);  
            throw new ApplicationException("Error en fillSheetbook. " + e.getMessage(), e);
        }
    }

    public HtmlDataTable getDataTable() {
        return dataTable;
    }

    public void setDataTable(HtmlDataTable dataTable) {
        this.dataTable = dataTable;
    }

    public BigDecimal getIdPrefichaSelected() {
        return idPrefichaSelected;
    }

    public void setIdPrefichaSelected(BigDecimal idPrefichaSelected) {
        this.idPrefichaSelected = idPrefichaSelected;
    }

    public List<PreFichaMgl> getFichaToGenerarList() {
        return fichaToGenerarList;
    }

    public void setFichaToGenerarList(List<PreFichaMgl> fichaToGenerarList) {
        this.fichaToGenerarList = fichaToGenerarList;
    }

    public List<PreFichaHHPPDetalleMgl> getDetHHHPPListToValidate() {
        return detHHHPPListToValidate;
    }

    public void setDetHHHPPListToValidate(List<PreFichaHHPPDetalleMgl> detHHHPPListToValidate) {
        this.detHHHPPListToValidate = detHHHPPListToValidate;
    }

    public String getShowHHPPToValidate() {
        return showHHPPToValidate;
    }

    public void setShowHHPPToValidate(String showHHPPToValidate) {
        this.showHHPPToValidate = showHHPPToValidate;
    }
}
