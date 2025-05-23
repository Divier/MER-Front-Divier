/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.direcciones;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.visitasTecnicas.facade.HhpprrEJBRemote;
import co.com.claro.visitastecnicas.ws.proxy.ChangeUnitAddressRequest;
import co.com.telmex.catastro.util.FacesUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.model.file.UploadedFile;

/**
 *
 * @author User
 */
@ManagedBean(name = "cambioDireccionMasivo")
@ViewScoped
public class CambioDireccionMasivo {
    private static final Logger LOGGER = LogManager.getLogger(CambioDireccionMasivo.class);

    @EJB
    private HhpprrEJBRemote hhpprrEJB;
    private UploadedFile uploadedFile;

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public void uploadFile() {
        try {

            if(uploadedFile != null && uploadedFile.getFileName() != null){
            String fileName = FilenameUtils.getName(uploadedFile.getFileName());
            String contentType = uploadedFile.getContentType();
            byte[] bytes = uploadedFile.getContent();
            LOGGER.error("bytes:" + bytes.length);

            if (validateFile(uploadedFile)) {
                XSSFWorkbook workbook = new XSSFWorkbook(uploadedFile.getInputStream());
                XSSFSheet sheet = workbook.getSheetAt(0);
                Iterator<Row> rowIterator = sheet.rowIterator();
                //avanzamos le primera fila que contiene los encabezados
                rowIterator.next();
                //Recorremos cada fila del archivo
                LOGGER.error("Numero Filas: " + sheet.getLastRowNum());
                List<ChangeUnitAddressRequest> changeUnitAddressList = new ArrayList<ChangeUnitAddressRequest>();
                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    if (validateRow(row)) {
                        int i = 0;
                        ChangeUnitAddressRequest changeUnitAddressRequest = new ChangeUnitAddressRequest();
                        changeUnitAddressRequest.setCommunity(row.getCell(i++).getStringCellValue());
                        changeUnitAddressRequest.setNewCommunity(row.getCell(i++).getStringCellValue());
                        changeUnitAddressRequest.setDivision(row.getCell(i++).getStringCellValue());
                        changeUnitAddressRequest.setNewDivision(row.getCell(i++).getStringCellValue());
                        changeUnitAddressRequest.setStreetName(row.getCell(i++).getStringCellValue());
                        changeUnitAddressRequest.setNewStreetName(row.getCell(i++).getStringCellValue());
                        changeUnitAddressRequest.setHouseNumber(row.getCell(i++).getStringCellValue());
                        changeUnitAddressRequest.setNewHouseNumber(row.getCell(i++).getStringCellValue());
                        changeUnitAddressRequest.setApartment(row.getCell(i++).getStringCellValue());
                        changeUnitAddressRequest.setNewApartment(row.getCell(i++).getStringCellValue());
                        changeUnitAddressRequest.setNewPostalCode(row.getCell(i++).getStringCellValue());
                        changeUnitAddressList.add(changeUnitAddressRequest);
                    }
                }
                List<ChangeUnitAddressRequest> noProcesados = hhpprrEJB.cambioDireccionRRMasivo(changeUnitAddressList);
                if (noProcesados != null && !noProcesados.isEmpty()) {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage("no todos los registros fueron procesadsos."));
                } else {


                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage("Archivo Procesado Exitosamente."));
                }
            }
            }
        } catch (ApplicationException | IOException e) {
            FacesUtil.mostrarMensajeError("Error en uploadFile. ", e, LOGGER);            
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en uploadFile. ", e, LOGGER);            
        }
    }

    private boolean validateRow(Row row) {
        boolean result = true;
        Iterator<Cell> cellIterator = row.cellIterator();
        //recorremos cada columna de la fila
        while (cellIterator.hasNext() && result) {
            Cell cell = cellIterator.next();
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_BLANK:
                    result = false;
                    break;
            }

        }
        return result;
    }

    private boolean validateFile(UploadedFile uf) throws ApplicationException {
        try {
            boolean result = true;
            XSSFWorkbook workbookOri = new XSSFWorkbook(uf.getInputStream());

            XSSFSheet sheet = workbookOri.getSheetAt(0);

            Iterator<Row> rowIterator = sheet.iterator();
            //recorremos cada Fila del archivo
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                //recorremos cada columna de la fila
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    //Check the cell type and format accordingly
                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_NUMERIC:
                            Double valueCell = cell.getNumericCellValue();
                            if (valueCell == null || valueCell.toString().trim().isEmpty()) {
                                result = false;
                                return result;
                            }
                            break;
                        case Cell.CELL_TYPE_STRING:
                            String valueCellStr = cell.getStringCellValue();
                            if (valueCellStr == null || valueCellStr.toString().trim().isEmpty()) {
                                result = false;
                                return result;
                            }
                            break;
                    }
                }

            }
            return result;
        } catch (IOException e) {
            LOGGER.error("Error en validateFile. " + e.getMessage(), e);      
            throw new ApplicationException("Error en validateFile " + e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error("Error en validateFile. " + e.getMessage(), e);      
            throw new ApplicationException("Error en validateFile " + e.getMessage(), e);
        }
    }
}
