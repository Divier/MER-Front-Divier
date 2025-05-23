/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.util;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.GeograficoPoliticoMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.PreFichaMgl;
import co.com.claro.mgl.jpa.entities.PreFichaTxtMgl;
import co.com.claro.mgl.jpa.entities.PreFichaXlsMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtCuentaMatrizMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionDetalladaMgl;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.visitasTecnicas.entities.DetalleDireccionEntity;
import co.com.telmex.catastro.util.FacesUtil;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import javax.ejb.EJB;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author castrofo
 */
public class ExportArchivosFichas {

    private static final Logger LOGGER = LogManager.getLogger(ExportArchivosFichas.class);
    String fileName;
    PreFichaMgl preFichaMgl;

    private List<PreFichaTxtMgl> edificiosVtXls;
    private List<PreFichaTxtMgl> casasRedExternaXls;
    private List<PreFichaTxtMgl> conjCasasVtXls;
    private List<PreFichaXlsMgl> hhppSNXls;
    private HashMap<PreFichaXlsMgl, CmtCuentaMatrizMgl> mapaCCMMCreadas;
    private List<AuxExportFichas> mapaHHPPCreadas;
    private List<AuxExportFichas> mapaErroresYDuplicados;

    private NodoMgl nodoCodigo;

    @EJB
    private GeograficoPoliticoMglFacadeLocal geograficoPoliticoMglFacadeLocal;

    public ExportArchivosFichas(GeograficoPoliticoMglFacadeLocal geograficoPoliticoMglFacadeLocal) {
        this.geograficoPoliticoMglFacadeLocal = geograficoPoliticoMglFacadeLocal;
    }

    public ExportArchivosFichas(String fileName, PreFichaMgl preFichaMgl, List<PreFichaTxtMgl> edificiosVtXls, List<PreFichaTxtMgl> casasRedExternaXls, List<PreFichaTxtMgl> conjCasasVtXls, List<PreFichaXlsMgl> hhppSNXls, HashMap<PreFichaXlsMgl, CmtCuentaMatrizMgl> mapaCCMMCreadas, List<AuxExportFichas> mapaHHPPCreadas, List<AuxExportFichas> mapaErroresYDuplicados, GeograficoPoliticoMglFacadeLocal geograficoPoliticoMglFacadeLocal) {
        this.fileName = fileName;
        this.preFichaMgl = preFichaMgl;
        this.edificiosVtXls = edificiosVtXls;
        this.casasRedExternaXls = casasRedExternaXls;
        this.conjCasasVtXls = conjCasasVtXls;
        this.hhppSNXls = hhppSNXls;
        this.mapaCCMMCreadas = mapaCCMMCreadas;
        this.mapaHHPPCreadas = mapaHHPPCreadas;
        this.mapaErroresYDuplicados = mapaErroresYDuplicados;
        this.nodoCodigo = preFichaMgl.getNodoMgl();
        this.geograficoPoliticoMglFacadeLocal = geograficoPoliticoMglFacadeLocal;
        dataHistorico = new DataArchivoExportFichas();
    }

    public void generarArchivo(OutputStream output, DataArchivoExportFichas data) {
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            XSSFWorkbook workbook = new XSSFWorkbook();
            this.dataHistorico = data;
            crearHojaEdificiosVT(workbook, data.getCabecerasHojaEdificiosVT(), data.getHojaEdificiosVT(), data.getDatosAdicionalesEdificiosVT());
            crearHojaCasasRedExterna(workbook, data.getCabecerasHojaCasasRedExterna(), data.getHojaCasasRedExterna(), data.getDatosAdicionalesCasasRedExterna());
            crearHojaConjuntoCasasVT(workbook, data.getCabecerasHojaConjuntoCasasVT(), data.getHojaConjuntoCasasVT(), data.getDatosAdicionalesConjuntoCasasVT());
            crearHojaHHPPSNFichas(workbook, data.getCabecerasHojaHHPPSNFichas(), data.getHojaHHPPSNFichas(), data.getDatosAdicionalesHHPPSNFichas());
            crearHojaCCMMCreadas(workbook, data.getCabecerasHojaCCMMCreadas(), data.getHojaCCMMCreadas(), data.getDatosAdicionalesCCMMCreadas());
            crearHojaHHPPCreados(workbook, data.getCabecerasHojaHHPPCreados(), data.getHojaHHPPCreados(), data.getDatosAdicionalesHHPPCreados());
            crearHojaErroresDuplicados(workbook, data.getCabecerasHojaErroresDuplicados(), data.getHojaErroresDuplicados(), data.getDatosAdicionalesErroresDuplicados());
            workbook.write(buffer);
            buffer.writeTo(output);
            output.flush();
            output.close();
        } catch (IOException | ApplicationException ex) {
            FacesUtil.mostrarMensajeError("Error en generarXlsValidacion. No se pudo validar el XLS ", ex, LOGGER);
        }
    }

    public void generarArchivoCorreo(ByteArrayOutputStream output, DataArchivoExportFichas data) {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            this.dataHistorico = data;
            crearHojaEdificiosVT(workbook, data.getCabecerasHojaEdificiosVT(), data.getHojaEdificiosVT(), data.getDatosAdicionalesEdificiosVT());
            crearHojaCasasRedExterna(workbook, data.getCabecerasHojaCasasRedExterna(), data.getHojaCasasRedExterna(), data.getDatosAdicionalesCasasRedExterna());
            crearHojaConjuntoCasasVT(workbook, data.getCabecerasHojaConjuntoCasasVT(), data.getHojaConjuntoCasasVT(), data.getDatosAdicionalesConjuntoCasasVT());
            crearHojaHHPPSNFichas(workbook, data.getCabecerasHojaHHPPSNFichas(), data.getHojaHHPPSNFichas(), data.getDatosAdicionalesHHPPSNFichas());
            crearHojaCCMMCreadas(workbook, data.getCabecerasHojaCCMMCreadas(), data.getHojaCCMMCreadas(), data.getDatosAdicionalesCCMMCreadas());
            crearHojaHHPPCreados(workbook, data.getCabecerasHojaHHPPCreados(), data.getHojaHHPPCreados(), data.getDatosAdicionalesHHPPCreados());
            crearHojaErroresDuplicados(workbook, data.getCabecerasHojaErroresDuplicados(), data.getHojaErroresDuplicados(), data.getDatosAdicionalesErroresDuplicados());
            workbook.write(output);
            output.flush();
            output.close();
        } catch (IOException | ApplicationException ex) {
            FacesUtil.mostrarMensajeError("Error en generarArchivoCorreo. No se pudo validar el XLS ", ex, LOGGER);
        }
    }

    public void generarArchivo(OutputStream output) {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();

            crearHojaEdificiosVT(workbook, null, null, null);
            crearHojaCasasRedExterna(workbook, null, null, null);
            crearHojaConjuntoCasasVT(workbook, null, null, null);
            crearHojaHHPPSNFichas(workbook, null, null, null);
            crearHojaCCMMCreadas(workbook, null, null, null);
            crearHojaHHPPCreados(workbook, null, null, null);
            crearHojaErroresDuplicados(workbook, null, null, null);
            workbook.write(output);
            output.flush();
            output.close();
        } catch (IOException | ApplicationException ex) {
            FacesUtil.mostrarMensajeError("Error en generarXlsValidacion. No se pudo validar el XLS ", ex, LOGGER);
        }
    }

    private void crearDatosHoja(XSSFSheet sheet, XSSFWorkbook workbook, Object[] cabeceraDataGral, Map<String, Object[]> datosHoja, int filaInicioData, boolean filaunlock) {
        sheet.setDisplayGridlines(true);
        sheet.setPrintGridlines(true);
        sheet.setFitToPage(true);
        sheet.setHorizontallyCenter(true);
        sheet.setVerticallyCenter(true);
        PrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(true);

        CellStyle unlockedCellStyle = workbook.createCellStyle();

        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFillForegroundColor((short) 70);
        cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

        Font font = workbook.createFont();
        font.setColor((short) 0);
        cellStyle.setFont(font);

        Row rowCabecera = sheet.createRow(filaInicioData);
        for (int i = 0; i < cabeceraDataGral.length; i++) {
            Cell cell = rowCabecera.createCell(i);
            cell.setCellStyle(cellStyle);
            cell.setCellValue((String) cabeceraDataGral[i]);
        }
        if (filaunlock) {
            for (int i = 0; i < cabeceraDataGral.length - 1; i++) {
                Cell cell = rowCabecera.createCell(cabeceraDataGral.length + i);
                cell.setCellValue((String) cabeceraDataGral[i + 1]);
            }
        }

        unlockedCellStyle.setLocked(false);
        int rownum = filaInicioData + 1;
        for (int i = 1; i <= datosHoja.size(); i++) {
            Row row = sheet.createRow(rownum++);
            Object[] objArr = datosHoja.get(Integer.toString(i));
            int cellnum = 0;
            for (Object obj : objArr) {
                Cell cell = row.createCell(cellnum++);
                if (obj instanceof String) {
                    cell.setCellValue((String) obj);
                } else if (obj instanceof BigDecimal) {
                    cell.setCellValue(((BigDecimal) obj).doubleValue());
                } else if (obj instanceof Double) {
                    cell.setCellValue(((Double) obj).doubleValue());
                } else if (obj instanceof Integer) {
                    cell.setCellValue(((Integer) obj).doubleValue());
                }
            }
            if (filaunlock) {
                for (int u = objArr.length; u <= (objArr.length * 2) - 1; u++) {
                    Cell cell = row.createCell(cellnum++);
                    cell.setCellStyle(unlockedCellStyle);
                }
            }
        }
    }

    private Object[] getDefaultCabeceras() {
        return new Object[]{
            "ID",
            "NOMBRE CONJUNTO / USO DE PREDIO",
            "VIA PRINCIPAL",
            "PLACA",
            "TOTAL HHPP",
            "APTOS",
            "LOCALES",
            "OFICINAS",
            "PISOS",
            "BARRIO",
            "DISTRIBUCION",
            "NODO / TRONCAL",
            "NAP",
            "CLASIFICACION",
            "TIPO DIRECCION"
        };
    }

    private void crearHojaEdificiosVT(XSSFWorkbook workbook, Object[] cabecerasHist, Map<String, Object[]> datosHojaHist, Map<String, String> datosAddHist) {
        XSSFSheet sheet = workbook.createSheet("EDIFICIOS VT FICHA");
        BigDecimal totalHhhp = BigDecimal.ZERO;
        Map<String, Object[]> datosHoja = new TreeMap<String, Object[]>();
        int fila = 1;
        Object[] cabeceras = cabecerasHist != null ? cabecerasHist : getDefaultCabeceras();

        if (edificiosVtXls != null && !edificiosVtXls.isEmpty() && datosHojaHist == null) {
            for (PreFichaTxtMgl p : edificiosVtXls) {
                BigDecimal totalHHPPPreficha = new BigDecimal(BigInteger.ZERO).add(p.getAptos()).add(p.getLocales()).add(p.getOficinas());
                totalHhhp = totalHhhp.add(totalHHPPPreficha);
                datosHoja.put(String.valueOf(fila), new Object[]{
                    p.getId(),
                    p.getNombreConj(),
                    p.getNombreCall(),
                    p.getPlacaUnida(),
                    totalHHPPPreficha,
                    p.getAptos(),
                    p.getLocales(),
                    p.getOficinas(),
                    p.getPisos(),
                    p.getBarrio(),
                    p.getDistribucion(),
                    preFichaMgl.getNodoMgl().getNodCodigo(),
                    p.getAmp(),
                    p.getBlockName().toUpperCase().equals("N_EDIFICIO") ? "EDIFICIOS VT" : "CASAS RED EXTERNA",
                    p.getTipoDireccion()
                });
                fila++;
            }
            datosHoja.put(String.valueOf(fila++), new Object[]{
                "", "", "", "Total HHPP", totalHhhp, "", "", "", "", "", "", "", "", "", "", "Total HHPP", 0
            });

            dataHistorico.setCabecerasHojaEdificiosVT(cabeceras);
            dataHistorico.setHojaEdificiosVT(datosHoja);
            dataHistorico.setDatosAdicionalesEdificiosVT(new HashMap<>());

            dataHistorico.getDatosAdicionalesEdificiosVT().put(0 + "," + 0, "");
            dataHistorico.getDatosAdicionalesEdificiosVT().put(1 + "," + 0, "");
            dataHistorico.getDatosAdicionalesEdificiosVT().put(0 + "," + 1, "NODO " + nodoCodigo.getNodCodigo());
            dataHistorico.getDatosAdicionalesEdificiosVT().put(0 + "," + cabeceras.length + 1, "NODO " + nodoCodigo.getNodCodigo());
            dataHistorico.getDatosAdicionalesEdificiosVT().put(1 + "," + 1, "ANTES INFORMACION BASICA NODO");
            dataHistorico.getDatosAdicionalesEdificiosVT().put(1 + "," + cabeceras.length + 1, "DESPUES INFORMACION BASICA NODO");

            sheet.createRow(0).createCell(0).setCellValue("");
            sheet.createRow(1).createCell(0).setCellValue("");
            sheet.getRow(0).createCell(1).setCellValue("NODO " + nodoCodigo.getNodCodigo());
            sheet.getRow(0).createCell(cabeceras.length + 1).setCellValue("NODO " + nodoCodigo.getNodCodigo());
            sheet.getRow(1).createCell(1).setCellValue("ANTES INFORMACION BASICA NODO");
            sheet.getRow(1).createCell(cabeceras.length + 1).setCellValue("DESPUES INFORMACION BASICA NODO");

        } else if (datosHojaHist != null && !datosHojaHist.isEmpty()) {
            datosHoja = datosHojaHist;
            addDatosAdicionales(datosAddHist, sheet);
        }

        crearDatosHoja(sheet, workbook, cabeceras, datosHoja, 2, true);

    }

    private void addDatosAdicionales(Map<String, String> datosAddHist, XSSFSheet sheet) {
        if (datosAddHist != null && !datosAddHist.isEmpty()) {
            for (String key : datosAddHist.keySet()) {
                String[] filCel = key.split(",");
                XSSFRow row = sheet.getRow(Integer.parseInt(filCel[0]));
                if (row == null) {
                    row = sheet.createRow(Integer.parseInt(filCel[0]));
                }
                row.createCell(Integer.parseInt(filCel[1])).setCellValue(datosAddHist.get(key));
            }
        }
    }

    private void crearHojaCasasRedExterna(XSSFWorkbook workbook, Object[] cabecerasHist, Map<String, Object[]> datosHojaHist, Map<String, String> datosAddHist) {
        XSSFSheet sheet = workbook.createSheet("CASAS RED EXTERNA FICHA");

        BigDecimal totalHhhp = BigDecimal.ZERO;
        Map<String, Object[]> datosHoja = new TreeMap<String, Object[]>();
        int fila = 1;
        Object[] cabeceras = cabecerasHist != null ? cabecerasHist : getDefaultCabeceras();

        if (casasRedExternaXls != null && !casasRedExternaXls.isEmpty() && datosHojaHist == null) {
            for (PreFichaTxtMgl p : casasRedExternaXls) {
                BigDecimal totalHHPPPreficha = new BigDecimal(BigInteger.ZERO).add(p.getAptos()).add(p.getLocales()).add(p.getOficinas());
                totalHhhp = totalHhhp.add(totalHHPPPreficha);
                datosHoja.put(String.valueOf(fila),
                        new Object[]{p.getId(), p.getNombreConj(),
                            p.getNombreCall(),
                            p.getPlacaUnida(),
                            totalHHPPPreficha,
                            p.getAptos(),
                            p.getLocales(),
                            p.getOficinas(),
                            p.getPisos(),
                            p.getBarrio(),
                            p.getDistribucion(),
                            preFichaMgl.getNodoMgl().getNodCodigo(),
                            p.getAmp(),
                            "CASAS RED EXTERNA",
                            p.getTipoDireccion()});
                fila++;
            }
            datosHoja.put(String.valueOf(fila++),
                    new Object[]{"", "", "", "Total HHPP", totalHhhp, "", "", "", "", "", "", "", "", "", "", "Total HHPP", 0,});

            dataHistorico.setCabecerasHojaCasasRedExterna(cabeceras);
            dataHistorico.setHojaCasasRedExterna(datosHoja);
            dataHistorico.setDatosAdicionalesCasasRedExterna(new HashMap<>());

            dataHistorico.getDatosAdicionalesCasasRedExterna().put(0 + "," + 0, "");
            dataHistorico.getDatosAdicionalesCasasRedExterna().put(1 + "," + 0, "");
            dataHistorico.getDatosAdicionalesCasasRedExterna().put(0 + "," + 1, "NODO " + nodoCodigo.getNodCodigo());
            dataHistorico.getDatosAdicionalesCasasRedExterna().put(0 + "," + cabeceras.length + 1, "NODO " + nodoCodigo.getNodCodigo());
            dataHistorico.getDatosAdicionalesCasasRedExterna().put(1 + "," + 1, "ANTES INFORMACION BASICA NODO");
            dataHistorico.getDatosAdicionalesCasasRedExterna().put(1 + "," + cabeceras.length + 1, "DESPUES INFORMACION BASICA NODO");

            sheet.createRow(0).createCell(0).setCellValue("");
            sheet.createRow(1).createCell(0).setCellValue("");
            sheet.getRow(0).createCell(1).setCellValue("NODO " + nodoCodigo.getNodCodigo());
            sheet.getRow(0).createCell(cabeceras.length + 1).setCellValue("NODO " + nodoCodigo.getNodCodigo());
            sheet.getRow(1).createCell(1).setCellValue("ANTES INFORMACION BASICA NODO");
            sheet.getRow(1).createCell(cabeceras.length + 1).setCellValue("DESPUES INFORMACION BASICA NODO");
        } else if (datosHojaHist != null && !datosHojaHist.isEmpty()) {
            datosHoja = datosHojaHist;
            addDatosAdicionales(datosAddHist, sheet);
        }

        crearDatosHoja(sheet, workbook, cabeceras, datosHoja, 2, true);

    }

    private void crearHojaConjuntoCasasVT(XSSFWorkbook workbook, Object[] cabecerasHist, Map<String, Object[]> datosHojaHist, Map<String, String> datosAddHist) {
        XSSFSheet sheet = workbook.createSheet("CONJUNTO CASAS VT FICHA");

        BigDecimal totalHhhp = BigDecimal.ZERO;
        Map<String, Object[]> datosHoja = new TreeMap<String, Object[]>();
        int fila = 1;
        Object[] cabeceras = cabecerasHist != null ? cabecerasHist : getDefaultCabeceras();
        if (conjCasasVtXls != null && !conjCasasVtXls.isEmpty()) {
            for (PreFichaTxtMgl p : conjCasasVtXls) {
                BigDecimal totalHHPPPreficha = new BigDecimal(BigInteger.ZERO).add(p.getAptos()).add(p.getLocales()).add(p.getOficinas());
                totalHhhp = totalHhhp.add(totalHHPPPreficha);
                datosHoja.put(String.valueOf(fila),
                        new Object[]{p.getId(), p.getNombreConj(),
                            p.getNombreCall(),
                            p.getPlacaUnida(),
                            totalHHPPPreficha,
                            p.getAptos(),
                            p.getLocales(),
                            p.getOficinas(),
                            p.getPisos(),
                            p.getBarrio(),
                            p.getDistribucion(),
                            preFichaMgl.getNodoMgl().getNodCodigo(),
                            p.getAmp(),
                            "CONJUNTO DE CASAS VT",
                            p.getTipoDireccion()});
                fila++;
            }
            datosHoja.put(String.valueOf(fila++),
                    new Object[]{"", "", "", "Total HHPP", totalHhhp, "", "", "", "", "", "", "", "", "", "", "", "Total HHPP", 0});

            dataHistorico.setCabecerasHojaConjuntoCasasVT(cabeceras);
            dataHistorico.setHojaConjuntoCasasVT(datosHoja);
            dataHistorico.setDatosAdicionalesConjuntoCasasVT(new HashMap<>());

            dataHistorico.getDatosAdicionalesConjuntoCasasVT().put(0 + "," + 0, "");
            dataHistorico.getDatosAdicionalesConjuntoCasasVT().put(1 + "," + 0, "");
            dataHistorico.getDatosAdicionalesConjuntoCasasVT().put(0 + "," + 1, "NODO " + nodoCodigo.getNodCodigo());
            dataHistorico.getDatosAdicionalesConjuntoCasasVT().put(0 + "," + cabeceras.length + 1, "NODO " + nodoCodigo.getNodCodigo());
            dataHistorico.getDatosAdicionalesConjuntoCasasVT().put(1 + "," + 1, "ANTES INFORMACION BASICA NODO");
            dataHistorico.getDatosAdicionalesConjuntoCasasVT().put(1 + "," + cabeceras.length + 1, "DESPUES INFORMACION BASICA NODO");

            sheet.createRow(0).createCell(0).setCellValue("");
            sheet.createRow(1).createCell(0).setCellValue("");
            sheet.getRow(0).createCell(1).setCellValue("NODO " + nodoCodigo.getNodCodigo());
            sheet.getRow(0).createCell(cabeceras.length + 1).setCellValue("NODO " + nodoCodigo.getNodCodigo());
            sheet.getRow(1).createCell(1).setCellValue("ANTES INFORMACION BASICA NODO");
            sheet.getRow(1).createCell(cabeceras.length + 1).setCellValue("DESPUES INFORMACION BASICA NODO");
        } else if (datosHojaHist != null && !datosHojaHist.isEmpty()) {
            datosHoja = datosHojaHist;
            addDatosAdicionales(datosAddHist, sheet);
        }

        crearDatosHoja(sheet, workbook, cabeceras, datosHoja, 2, true);

    }

    private void crearHojaHHPPSNFichas(XSSFWorkbook workbook, Object[] cabecerasHist, Map<String, Object[]> datosHojaHist, Map<String, String> datosAddHist) {
        XSSFSheet sheet = workbook.createSheet("HHPP SN FICHA");
        BigDecimal totalHhhp = BigDecimal.ZERO;
        Map<String, Object[]> datosHoja = new TreeMap<String, Object[]>();
        int fila = 1;
        Object[] cabeceras = cabecerasHist != null ? cabecerasHist : getDefaultCabeceras();
        if (hhppSNXls != null && !hhppSNXls.isEmpty()) {

            for (PreFichaXlsMgl p : hhppSNXls) {
                totalHhhp = totalHhhp.add(p.getTotalHHPP());
                datosHoja.put(String.valueOf(fila),
                        new Object[]{p.getId(), p.getNombreConj(),
                            p.getViaPrincipal(),
                            p.getPlaca(),
                            p.getTotalHHPP(),
                            p.getAptos(),
                            p.getLocales(),
                            p.getOficinas(),
                            p.getPisos(),
                            p.getBarrio(),
                            p.getDistribucion(),
                            preFichaMgl.getNodoMgl().getNodCodigo(),
                            p.getAmp(),
                            p.getClasificacion(),
                            p.getIdTipoDireccion()});
                fila++;
            }

            datosHoja.put(String.valueOf(fila++),
                    new Object[]{"", "", "", "Total HHPP", totalHhhp, "", "", "", "", "", "", "", "", "", "", "", "Total HHPP", 0});

            dataHistorico.setCabecerasHojaHHPPSNFichas(cabeceras);
            dataHistorico.setHojaHHPPSNFichas(datosHoja);
            dataHistorico.setDatosAdicionalesHHPPSNFichas(new HashMap<>());

            dataHistorico.getDatosAdicionalesHHPPSNFichas().put(0 + "," + 0, "");
            dataHistorico.getDatosAdicionalesHHPPSNFichas().put(1 + "," + 0, "");
            dataHistorico.getDatosAdicionalesHHPPSNFichas().put(0 + "," + 1, "NODO " + nodoCodigo.getNodCodigo());
            dataHistorico.getDatosAdicionalesHHPPSNFichas().put(0 + "," + cabeceras.length + 1, "NODO " + nodoCodigo.getNodCodigo());
            dataHistorico.getDatosAdicionalesHHPPSNFichas().put(1 + "," + 1, "ANTES INFORMACION BASICA NODO");
            dataHistorico.getDatosAdicionalesHHPPSNFichas().put(1 + "," + cabeceras.length + 1, "DESPUES INFORMACION BASICA NODO");

            sheet.createRow(0).createCell(0).setCellValue("");
            sheet.createRow(1).createCell(0).setCellValue("");
            sheet.getRow(0).createCell(1).setCellValue("NODO " + nodoCodigo.getNodCodigo());
            sheet.getRow(0).createCell(cabeceras.length + 1).setCellValue("NODO " + nodoCodigo.getNodCodigo());
            sheet.getRow(1).createCell(1).setCellValue("ANTES INFORMACION BASICA NODO");
            sheet.getRow(1).createCell(cabeceras.length + 1).setCellValue("DESPUES INFORMACION BASICA NODO");
        } else if (datosHojaHist != null && !datosHojaHist.isEmpty()) {
            datosHoja = datosHojaHist;
            addDatosAdicionales(datosAddHist, sheet);
        }

        crearDatosHoja(sheet, workbook, cabeceras, datosHoja, 2, true);

    }

    private void crearHojaCCMMCreadas(XSSFWorkbook workbook, Object[] cabecerasHist, Map<String, Object[]> datosHojaHist, Map<String, String> datosAddHist) throws ApplicationException {

        XSSFSheet sheet = workbook.createSheet("CCMM  CREADAS");

        BigDecimal totalHhhp = BigDecimal.ZERO;
        Map<String, Object[]> datosHoja = new TreeMap<>();

        GeograficoPoliticoMgl centro = geograficoPoliticoMglFacadeLocal.findById(nodoCodigo != null ? nodoCodigo.getGpoId() : dataHistorico.getIdNodo());
        GeograficoPoliticoMgl city = geograficoPoliticoMglFacadeLocal.findById(centro.getGeoGpoId());
        GeograficoPoliticoMgl state = geograficoPoliticoMglFacadeLocal.findById(city.getGeoGpoId());

        int fila = 1;
        Object[] cabeceras = cabecerasHist;
        if (cabeceras == null) {
            cabeceras = new Object[]{"NOMBRE CONJUNTO / USO DE PREDIO",
                "VIA PRINCIPAL RR", "VIA GENERADORA + PLACA RR", "VIA PRINCIPAL MGL",
                "VIA GENERADORA + PLACA MGL", "DEPARTAMENTO", "CIUDAD",
                "CENTRO POBLADO", "TOTAL HHPP", "APTOS", "LOCALES ", "OFICINAS", "PISOS",
                "CLASIFICACION", "BARRIO FICHA", "DISTRIBUCION MGL", "NODO / TRONCAL", "NAP", "ESTRATO",
                "BARRIO GEO", "NOV_SOCIO GEO", "DIRECCION  GEO", "FUENTE GEO",
                "DIR. ALTERNA GEO", "LOCALIDAD GEO", "MANZANA GEO", "CX GEO", "CY GEO",
                "MATRIZ RR", "MATRIZ MGL"};
        }

        if (mapaCCMMCreadas != null && mapaCCMMCreadas.keySet() != null && !mapaCCMMCreadas.keySet().isEmpty()) {
            for (PreFichaXlsMgl p : mapaCCMMCreadas.keySet()) {

                if (mapaCCMMCreadas.get(p) == null) {
                    continue;
                }

                DetalleDireccionEntity drDireccion = mapaCCMMCreadas.get(p).getDireccionPrincipal().getDetalleDireccionEntity();

                String nombreViaCCMM = "";
                String placaCCMM = "";
                if (p.getIdTipoDireccion().equals(Constant.ADDRESS_TIPO_CK)) {
                    nombreViaCCMM += drDireccion.getTipoviaprincipal() != null
                            ? !drDireccion.getTipoviaprincipal().isEmpty() ? drDireccion.getTipoviaprincipal() + " " : "" : "";
                    nombreViaCCMM += drDireccion.getNumviaprincipal() != null
                            ? !drDireccion.getNumviaprincipal().isEmpty() ? " " + drDireccion.getNumviaprincipal() + " " : "" : "";
                    nombreViaCCMM += drDireccion.getLtviaprincipal() != null
                            ? !drDireccion.getLtviaprincipal().isEmpty() ? " " + drDireccion.getLtviaprincipal() + " " : "" : "";
                    nombreViaCCMM += drDireccion.getNlpostviap() != null
                            ? !drDireccion.getNlpostviap().isEmpty() ? " " + drDireccion.getNlpostviap() + " " : "" : "";
                    nombreViaCCMM += drDireccion.getBisviaprincipal() != null
                            ? !drDireccion.getBisviaprincipal().isEmpty() ? " - " + drDireccion.getBisviaprincipal() + " " : "" : "";
                    nombreViaCCMM += drDireccion.getCuadviaprincipal() != null
                            ? !drDireccion.getCuadviaprincipal().isEmpty() ? " " + drDireccion.getCuadviaprincipal() + " " : "" : "";

                    placaCCMM += drDireccion.getTipoviageneradora() != null
                            ? !drDireccion.getTipoviageneradora().isEmpty() ? " " + drDireccion.getTipoviageneradora() + " " : "" : "";
                    placaCCMM += drDireccion.getNumviageneradora() != null
                            ? !drDireccion.getNumviageneradora().isEmpty() ? " " + drDireccion.getNumviageneradora() + " " : "" : "";
                    placaCCMM += drDireccion.getLtviageneradora() != null
                            ? !drDireccion.getLtviageneradora().isEmpty() ? " " + drDireccion.getLtviageneradora() + " " : "" : "";
                    placaCCMM += drDireccion.getNlpostviag() != null
                            ? !drDireccion.getNlpostviag().isEmpty() ? " - " + drDireccion.getNlpostviag() + " " : "" : "";
                    placaCCMM += drDireccion.getLetra3g() != null
                            ? !drDireccion.getLetra3g().isEmpty() ? " - " + drDireccion.getLetra3g() + " " : "" : "";
                    placaCCMM += drDireccion.getBisviageneradora() != null
                            ? !drDireccion.getBisviageneradora().isEmpty() ? " Bis " + drDireccion.getBisviageneradora() + " " : "" : "";
                    placaCCMM += drDireccion.getCuadviageneradora() != null
                            ? !drDireccion.getCuadviageneradora().isEmpty() ? " " + drDireccion.getCuadviageneradora() + " " : "" : "";
                    placaCCMM += drDireccion.getPlacadireccion() != null
                            ? !drDireccion.getPlacadireccion().isEmpty() ? " " + drDireccion.getPlacadireccion() + " " : "" : "";

                } else if (p.getIdTipoDireccion().equals(Constant.ADDRESS_TIPO_BM)) {
                    nombreViaCCMM += drDireccion.getMztiponivel1() != null
                            ? !drDireccion.getMztiponivel1().isEmpty() ? drDireccion.getMztiponivel1() + " " : "" : "";
                    nombreViaCCMM += drDireccion.getMzvalornivel1() != null
                            ? !drDireccion.getMzvalornivel1().isEmpty() ? drDireccion.getMzvalornivel1() + " " : "" : "";

                    if (nombreViaCCMM.isEmpty()) {
                        nombreViaCCMM += "BARRIO ";
                        nombreViaCCMM += drDireccion.getBarrio() + " ";
                        //Add Barrio para evitar Vacios;
                    }

                    nombreViaCCMM += drDireccion.getMztiponivel2() != null
                            ? !drDireccion.getMztiponivel2().isEmpty() ? drDireccion.getMztiponivel2() + " " : "" : "";
                    nombreViaCCMM += drDireccion.getMzvalornivel2() != null
                            ? !drDireccion.getMzvalornivel2().isEmpty() ? drDireccion.getMzvalornivel2() + " " : "" : "";
                    nombreViaCCMM += drDireccion.getMztiponivel3() != null
                            ? !drDireccion.getMztiponivel3().isEmpty() ? drDireccion.getMztiponivel3() + " " : "" : "";
                    nombreViaCCMM += drDireccion.getMzvalornivel3() != null
                            ? !drDireccion.getMzvalornivel3().isEmpty() ? drDireccion.getMzvalornivel3() + " " : "" : "";

                    placaCCMM += drDireccion.getMztiponivel4() != null
                            ? !drDireccion.getMztiponivel4().isEmpty() ? drDireccion.getMztiponivel4() + " " : "" : "";
                    placaCCMM += drDireccion.getMzvalornivel4() != null
                            ? !drDireccion.getMzvalornivel4().isEmpty() ? drDireccion.getMzvalornivel4() + " " : "" : "";
                    placaCCMM += drDireccion.getMztiponivel5() != null
                            ? !drDireccion.getMztiponivel5().isEmpty() ? drDireccion.getMztiponivel5() + " " : "" : "";
                    placaCCMM += drDireccion.getMzvalornivel5() != null
                            ? !drDireccion.getMzvalornivel5().isEmpty() ? drDireccion.getMzvalornivel5() + " " : "" : "";

                } else if (p.getIdTipoDireccion().equals(Constant.ADDRESS_TIPO_IT)) {

                    nombreViaCCMM += drDireccion.getMztiponivel1() != null
                            ? !drDireccion.getMztiponivel1().isEmpty() ? drDireccion.getMztiponivel1() + " " : "" : "";
                    nombreViaCCMM += drDireccion.getMzvalornivel1() != null
                            ? !drDireccion.getMzvalornivel1().isEmpty() ? drDireccion.getMzvalornivel1() + " " : "" : "";
                    nombreViaCCMM += drDireccion.getMztiponivel2() != null
                            ? !drDireccion.getMztiponivel2().isEmpty() ? drDireccion.getMztiponivel2() + " " : "" : "";
                    nombreViaCCMM += drDireccion.getMzvalornivel2() != null
                            ? !drDireccion.getMzvalornivel2().isEmpty() ? drDireccion.getMzvalornivel2() + " " : "" : "";
                    nombreViaCCMM += drDireccion.getMztiponivel3() != null
                            ? !drDireccion.getMztiponivel3().isEmpty() ? drDireccion.getMztiponivel3() + " " : "" : "";
                    nombreViaCCMM += drDireccion.getMzvalornivel3() != null
                            ? !drDireccion.getMzvalornivel3().isEmpty() ? drDireccion.getMzvalornivel3() + " " : "" : "";
                    nombreViaCCMM += drDireccion.getMztiponivel4() != null
                            ? !drDireccion.getMztiponivel4().isEmpty() ? drDireccion.getMztiponivel4() + " " : "" : "";
                    nombreViaCCMM += drDireccion.getMzvalornivel4() != null
                            ? !drDireccion.getMzvalornivel4().isEmpty() ? drDireccion.getMzvalornivel4() + " " : "" : "";
                    nombreViaCCMM += drDireccion.getMztiponivel5() != null
                            ? !drDireccion.getMztiponivel5().isEmpty() ? drDireccion.getMztiponivel5() + " " : "" : "";
                    nombreViaCCMM += drDireccion.getMzvalornivel5() != null
                            ? !drDireccion.getMzvalornivel5().isEmpty() ? drDireccion.getMzvalornivel5() + " " : "" : "";
                    nombreViaCCMM += drDireccion.getMztiponivel6() != null
                            ? !drDireccion.getMztiponivel6().isEmpty() ? drDireccion.getMztiponivel6() + " " : "" : "";
                    nombreViaCCMM += drDireccion.getMzvalornivel6() != null
                            ? !drDireccion.getMzvalornivel6().isEmpty() ? drDireccion.getMzvalornivel6() + " " : "" : "";

                    placaCCMM += drDireccion.getItValorPlaca() != null
                            ? !drDireccion.getItValorPlaca().isEmpty() ? drDireccion.getItValorPlaca() + " " : "" : "";
                }

                totalHhhp = totalHhhp.add(p.getTotalHHPP());
                datosHoja.put(String.valueOf(fila),
                        new Object[]{p.getNombreConj(),
                            mapaCCMMCreadas.get(p).getDireccionPrincipal().getCalleRr(),
                            mapaCCMMCreadas.get(p).getDireccionPrincipal().getUnidadRr(),
                            nombreViaCCMM,
                            placaCCMM,
                            state.getGpoNombre(),
                            city.getGpoNombre(),
                            centro.getGpoNombre(),
                            p.getTotalHHPP(),
                            p.getAptos(),
                            p.getLocales(),
                            p.getOficinas(),
                            p.getPisos(),
                            p.getClasificacion(),
                            p.getBarrio(),
                            p.getDistribucion(),
                            nodoCodigo.getNodNombre(),
                            p.getFichaGeorreferenciaMgl() == null ? "" : p.getFichaGeorreferenciaMgl().getCambioEstrato(),
                            p.getFichaGeorreferenciaMgl() == null ? "" : p.getFichaGeorreferenciaMgl().getNeighborhood(),
                            p.getFichaGeorreferenciaMgl() == null ? "" : p.getFichaGeorreferenciaMgl().getLevelEconomic(),
                            p.getFichaGeorreferenciaMgl() == null ? "" : p.getFichaGeorreferenciaMgl().getAddress(),
                            p.getFichaGeorreferenciaMgl() == null ? "" : p.getFichaGeorreferenciaMgl().getSource(),
                            p.getFichaGeorreferenciaMgl() == null ? "" : p.getFichaGeorreferenciaMgl().getAlternateAddress(),
                            p.getFichaGeorreferenciaMgl() == null ? "" : p.getFichaGeorreferenciaMgl().getLocality(),
                            p.getFichaGeorreferenciaMgl() == null ? "" : p.getFichaGeorreferenciaMgl().getAppletStandar(),
                            p.getFichaGeorreferenciaMgl() == null ? "" : p.getFichaGeorreferenciaMgl().getCx(),
                            p.getFichaGeorreferenciaMgl() == null ? "" : p.getFichaGeorreferenciaMgl().getCy(),
                            mapaCCMMCreadas.get(p).getNumeroCuenta(),
                            mapaCCMMCreadas.get(p).getCuentaMatrizId()});
                fila++;
            }

            datosHoja.put(String.valueOf(fila++),
                    new Object[]{"", "", "", "", "", "", "", "Total HHPP", totalHhhp});

            dataHistorico.setCabecerasHojaCCMMCreadas(cabeceras);
            dataHistorico.setHojaCCMMCreadas(datosHoja);
        } else if (datosHojaHist != null && !datosHojaHist.isEmpty()) {
            datosHoja = datosHojaHist;
            addDatosAdicionales(datosAddHist, sheet);
        }

        crearDatosHoja(sheet, workbook, cabeceras, datosHoja, 1, false);

    }

    private void crearHojaHHPPCreados(XSSFWorkbook workbook, Object[] cabecerasHist, Map<String, Object[]> datosHojaHist, Map<String, String> datosAddHist) throws ApplicationException {
        XSSFSheet sheet = workbook.createSheet("HHPP  CREADAS");

        BigDecimal totalHhhp = BigDecimal.ZERO;
        Map<String, Object[]> datosHoja = new TreeMap<>();

        GeograficoPoliticoMgl centro = geograficoPoliticoMglFacadeLocal.findById(nodoCodigo != null ? nodoCodigo.getGpoId() : dataHistorico.getIdNodo());
        GeograficoPoliticoMgl city = geograficoPoliticoMglFacadeLocal.findById(centro.getGeoGpoId());
        GeograficoPoliticoMgl state = geograficoPoliticoMglFacadeLocal.findById(city.getGeoGpoId());

        int fila = 1;
        Object[] cabeceras = cabecerasHist;
        if (cabeceras == null) {
            cabeceras = new Object[]{"VIA PRINCIPAL RR", "VIA GENERADORA + PLACA RR",
                "VIA PRINCIPAL MGL", "VIA GENERADORA + PLACA MGL", "DEPARTAMIENTO",
                "CIUDAD", "CENTRO POBLADO", "TOTAL HHPP", "APTOS", "LOCALES ",
                "PISOS", "BARRIO FICHA", "DISTRIBUCION", "NODO", "ESTRATO",
                "BARRIO GEO", "ESTRATO GEO", "DIRECCION  GEO", "FUENTE GEO",
                "DIR. ALTERNA GEO", "LOCALIDAD GEO", "MANZANA GEO", "CX GEO",
                "CY GEO", "ANALISIS"};
        }
        if (mapaHHPPCreadas != null && !mapaHHPPCreadas.isEmpty()) {
            for (AuxExportFichas aux : mapaHHPPCreadas) {

                if (aux.getPreficha() == null) {
                    continue;
                }

                totalHhhp = totalHhhp.add(BigDecimal.ONE);
                String viaPrincipalHHPP = "";
                String placaHHPP = "";

                if (aux.getHhppMGL() != null && aux.getHhppMGL().getDireccionObj() != null) {
                    CmtDireccionDetalladaMgl drDireccion = aux.getHhppMGL().getDireccionObj().getDireccionDetallada();
                    switch (aux.getPreficha().getIdTipoDireccion()) {
                        case Constant.ADDRESS_TIPO_CK:
                            viaPrincipalHHPP += drDireccion.getTipoViaPrincipal() != null
                                    ? !drDireccion.getTipoViaPrincipal().isEmpty() ? drDireccion.getTipoViaPrincipal() + " " : "" : "";
                            viaPrincipalHHPP += drDireccion.getNumViaPrincipal() != null
                                    ? !drDireccion.getNumViaPrincipal().isEmpty() ? " " + drDireccion.getNumViaPrincipal() + " " : "" : "";
                            viaPrincipalHHPP += drDireccion.getLtViaPrincipal() != null
                                    ? !drDireccion.getLtViaPrincipal().isEmpty() ? " " + drDireccion.getLtViaPrincipal() + " " : "" : "";
                            viaPrincipalHHPP += drDireccion.getNlPostViaP() != null
                                    ? !drDireccion.getNlPostViaP().isEmpty() ? " " + drDireccion.getNlPostViaP() + " " : "" : "";
                            viaPrincipalHHPP += drDireccion.getBisViaPrincipal() != null
                                    ? !drDireccion.getBisViaPrincipal().isEmpty() ? " - " + drDireccion.getBisViaPrincipal() + " " : "" : "";
                            viaPrincipalHHPP += drDireccion.getCuadViaPrincipal() != null
                                    ? !drDireccion.getCuadViaPrincipal().isEmpty() ? " " + drDireccion.getCuadViaPrincipal() + " " : "" : "";
                            placaHHPP += drDireccion.getTipoViaGeneradora() != null
                                    ? !drDireccion.getTipoViaGeneradora().isEmpty() ? " " + drDireccion.getTipoViaGeneradora() + " " : "" : "";
                            placaHHPP += drDireccion.getNumViaGeneradora() != null
                                    ? !drDireccion.getNumViaGeneradora().isEmpty() ? " " + drDireccion.getNumViaGeneradora() + " " : "" : "";
                            placaHHPP += drDireccion.getLtViaGeneradora() != null
                                    ? !drDireccion.getLtViaGeneradora().isEmpty() ? " " + drDireccion.getLtViaGeneradora() + " " : "" : "";
                            placaHHPP += drDireccion.getNlPostViaG() != null
                                    ? !drDireccion.getNlPostViaG().isEmpty() ? " - " + drDireccion.getNlPostViaG() + " " : "" : "";
                            placaHHPP += drDireccion.getLetra3G() != null
                                    ? !drDireccion.getLetra3G().isEmpty() ? " - " + drDireccion.getLetra3G() + " " : "" : "";
                            placaHHPP += drDireccion.getBisViaGeneradora() != null
                                    ? !drDireccion.getBisViaGeneradora().isEmpty() ? " Bis " + drDireccion.getBisViaGeneradora() + " " : "" : "";
                            placaHHPP += drDireccion.getCuadViaGeneradora() != null
                                    ? !drDireccion.getCuadViaGeneradora().isEmpty() ? " " + drDireccion.getCuadViaGeneradora() + " " : "" : "";
                            placaHHPP += drDireccion.getPlacaDireccion() != null
                                    ? !drDireccion.getPlacaDireccion().isEmpty() ? " " + drDireccion.getPlacaDireccion() + " " : "" : "";
                            break;
                        case Constant.ADDRESS_TIPO_BM:
                            viaPrincipalHHPP += drDireccion.getMzTipoNivel1() != null
                                    ? !drDireccion.getMzTipoNivel1().isEmpty() ? drDireccion.getMzTipoNivel1() + " " : "" : "";
                            viaPrincipalHHPP += drDireccion.getMzValorNivel1() != null
                                    ? !drDireccion.getMzValorNivel1().isEmpty() ? drDireccion.getMzValorNivel1() + " " : "" : "";
                            if (viaPrincipalHHPP.isEmpty()) {
                                viaPrincipalHHPP += drDireccion.getBarrio() != null
                                        ? !drDireccion.getBarrio().isEmpty() ? "BARRIO " : "" : "";
                                viaPrincipalHHPP += drDireccion.getBarrio() != null
                                        ? !drDireccion.getBarrio().isEmpty() ? drDireccion.getBarrio() + " " : "" : "";
                            }
                            viaPrincipalHHPP += drDireccion.getMzTipoNivel2() != null
                                    ? !drDireccion.getMzTipoNivel2().isEmpty() ? drDireccion.getMzTipoNivel2() + " " : "" : "";
                            viaPrincipalHHPP += drDireccion.getMzValorNivel2() != null
                                    ? !drDireccion.getMzValorNivel2().isEmpty() ? drDireccion.getMzValorNivel2() + " " : "" : "";
                            viaPrincipalHHPP += drDireccion.getMzTipoNivel3() != null
                                    ? !drDireccion.getMzTipoNivel3().isEmpty() ? drDireccion.getMzTipoNivel3() + " " : "" : "";
                            viaPrincipalHHPP += drDireccion.getMzValorNivel3() != null
                                    ? !drDireccion.getMzValorNivel3().isEmpty() ? drDireccion.getMzValorNivel3() + " " : "" : "";
                            placaHHPP += drDireccion.getMzTipoNivel4() != null
                                    ? !drDireccion.getMzTipoNivel4().isEmpty() ? drDireccion.getMzTipoNivel4() + " " : "" : "";
                            placaHHPP += drDireccion.getMzValorNivel4() != null
                                    ? !drDireccion.getMzValorNivel4().isEmpty() ? drDireccion.getMzValorNivel4() + " " : "" : "";
                            placaHHPP += drDireccion.getMzTipoNivel5() != null
                                    ? !drDireccion.getMzTipoNivel5().isEmpty() ? drDireccion.getMzTipoNivel5() + " " : "" : "";
                            placaHHPP += drDireccion.getMzValorNivel5() != null
                                    ? !drDireccion.getMzValorNivel5().isEmpty() ? drDireccion.getMzValorNivel5() + " " : "" : "";
                            break;
                        case Constant.ADDRESS_TIPO_IT:
                            viaPrincipalHHPP += drDireccion.getMzTipoNivel1() != null
                                    ? !drDireccion.getMzTipoNivel1().isEmpty() ? drDireccion.getMzTipoNivel1() + " " : "" : "";
                            viaPrincipalHHPP += drDireccion.getMzValorNivel1() != null
                                    ? !drDireccion.getMzValorNivel1().isEmpty() ? drDireccion.getMzValorNivel1() + " " : "" : "";
                            viaPrincipalHHPP += drDireccion.getMzTipoNivel2() != null
                                    ? !drDireccion.getMzTipoNivel2().isEmpty() ? drDireccion.getMzTipoNivel2() + " " : "" : "";
                            viaPrincipalHHPP += drDireccion.getMzValorNivel2() != null
                                    ? !drDireccion.getMzValorNivel2().isEmpty() ? drDireccion.getMzValorNivel2() + " " : "" : "";
                            viaPrincipalHHPP += drDireccion.getMzTipoNivel3() != null
                                    ? !drDireccion.getMzTipoNivel3().isEmpty() ? drDireccion.getMzTipoNivel3() + " " : "" : "";
                            viaPrincipalHHPP += drDireccion.getMzValorNivel3() != null
                                    ? !drDireccion.getMzValorNivel3().isEmpty() ? drDireccion.getMzValorNivel3() + " " : "" : "";
                            viaPrincipalHHPP += drDireccion.getMzTipoNivel4() != null
                                    ? !drDireccion.getMzTipoNivel4().isEmpty() ? drDireccion.getMzTipoNivel4() + " " : "" : "";
                            viaPrincipalHHPP += drDireccion.getMzValorNivel4() != null
                                    ? !drDireccion.getMzValorNivel4().isEmpty() ? drDireccion.getMzValorNivel4() + " " : "" : "";
                            viaPrincipalHHPP += drDireccion.getMzTipoNivel5() != null
                                    ? !drDireccion.getMzTipoNivel5().isEmpty() ? drDireccion.getMzTipoNivel5() + " " : "" : "";
                            viaPrincipalHHPP += drDireccion.getMzValorNivel5() != null
                                    ? !drDireccion.getMzValorNivel5().isEmpty() ? drDireccion.getMzValorNivel5() + " " : "" : "";
                            viaPrincipalHHPP += drDireccion.getMzTipoNivel6() != null
                                    ? !drDireccion.getMzTipoNivel6().isEmpty() ? drDireccion.getMzTipoNivel6() + " " : "" : "";
                            viaPrincipalHHPP += drDireccion.getMzValorNivel6() != null
                                    ? !drDireccion.getMzValorNivel6().isEmpty() ? drDireccion.getMzValorNivel6() + " " : "" : "";
                            placaHHPP += drDireccion.getItValorPlaca() != null
                                    ? !drDireccion.getItValorPlaca().isEmpty() ? drDireccion.getItValorPlaca() + " " : "" : "";
                            break;
                        default:
                            break;
                    }

                    datosHoja.put(String.valueOf(fila),
                            new Object[]{
                                aux.getHhppMGL().getHhpCalle(),
                                aux.getHhppMGL().getHhpPlaca(),
                                viaPrincipalHHPP,
                                placaHHPP,
                                state.getGpoNombre(),
                                city.getGpoNombre(),
                                centro.getGpoNombre(),
                                BigDecimal.ONE,
                                "",
                                "",
                                "",
                                aux.getPreficha().getBarrio(),
                                aux.getDireccionHHPP().split("\\|")[1],
                                nodoCodigo.getNodNombre(),
                                aux.getPreficha().getFichaGeorreferenciaMgl() == null ? "" : aux.getPreficha().getFichaGeorreferenciaMgl().getCambioEstrato(),
                                aux.getPreficha().getFichaGeorreferenciaMgl() == null ? "" : aux.getPreficha().getFichaGeorreferenciaMgl().getNeighborhood(),
                                aux.getPreficha().getFichaGeorreferenciaMgl() == null ? "" : aux.getPreficha().getFichaGeorreferenciaMgl().getLevelEconomic(),
                                aux.getPreficha().getFichaGeorreferenciaMgl() == null ? "" : aux.getPreficha().getFichaGeorreferenciaMgl().getAddress(),
                                aux.getPreficha().getFichaGeorreferenciaMgl() == null ? "" : aux.getPreficha().getFichaGeorreferenciaMgl().getSource(),
                                aux.getPreficha().getFichaGeorreferenciaMgl() == null ? "" : aux.getPreficha().getFichaGeorreferenciaMgl().getAlternateAddress(),
                                aux.getPreficha().getFichaGeorreferenciaMgl() == null ? "" : aux.getPreficha().getFichaGeorreferenciaMgl().getLocality(),
                                aux.getPreficha().getFichaGeorreferenciaMgl() == null ? "" : aux.getPreficha().getFichaGeorreferenciaMgl().getAppletStandar(),
                                aux.getPreficha().getFichaGeorreferenciaMgl() == null ? "" : aux.getPreficha().getFichaGeorreferenciaMgl().getCx(),
                                aux.getPreficha().getFichaGeorreferenciaMgl() == null ? "" : aux.getPreficha().getFichaGeorreferenciaMgl().getCy(),
                                "HHPP CREADO"});
                    fila++;
                }

                datosHoja.put(String.valueOf(fila++),
                        new Object[]{"", "", "", "", "", "", "Total HHPP", totalHhhp});

                dataHistorico.setCabecerasHojaHHPPCreados(cabeceras);
                dataHistorico.setHojaHHPPCreados(datosHoja);

            }

        } else if (datosHojaHist != null && !datosHojaHist.isEmpty()) {
            datosHoja = datosHojaHist;
            addDatosAdicionales(datosAddHist, sheet);
        }

        crearDatosHoja(sheet, workbook, cabeceras, datosHoja, 1, false);

    }

    private void crearHojaErroresDuplicados(XSSFWorkbook workbook, Object[] cabecerasHist, Map<String, Object[]> datosHojaHist, Map<String, String> datosAddHist) throws ApplicationException {

        XSSFSheet sheet = workbook.createSheet("ERRORES Y DUPLICADAS");

        BigDecimal totalHhhp = BigDecimal.ZERO;
        Map<String, Object[]> datosHoja = new TreeMap<>();

        GeograficoPoliticoMgl centro = geograficoPoliticoMglFacadeLocal.findById(nodoCodigo != null ? nodoCodigo.getGpoId() : dataHistorico.getIdNodo());
        GeograficoPoliticoMgl city = geograficoPoliticoMglFacadeLocal.findById(centro.getGeoGpoId());
        GeograficoPoliticoMgl state = geograficoPoliticoMglFacadeLocal.findById(city.getGeoGpoId());

        int fila = 1;
        Object[] cabeceras = cabecerasHist;
        if (cabeceras == null) {
            cabeceras = new Object[]{
                "VIA PRINCIPAL RR",
                "VIA GENERADORA + PLACA RR",
                "VIA PRINCIPAL MGL",
                "VIA GENERADORA + PLACA MGL",
                "Departamento",
                "Ciudad",
                "Centro Poblado",
                "TOTAL HHPP",
                "APTOS",
                "LOCALES ",
                "PISOS",
                "CLASIFICACION",
                "BARRIO FICHA",
                "DISTRIBUCION MGL",
                "NODO / TRONCAL",
                "NAP",
                "ESTRATO",
                "BARRIO GEO",
                "NOV_SOCIO GEO",
                "DIRECCION  GEO",
                "FUENTE GEO",
                "DIR. ALTERNA GEO",
                "LOCALIDAD GEO",
                "MANZANA GEO",
                "CX GEO",
                "CY GEO",
                "ANALISIS"};
        }
        if (mapaErroresYDuplicados != null && !mapaErroresYDuplicados.isEmpty()) {
            for (AuxExportFichas aux : mapaErroresYDuplicados) {

                totalHhhp = totalHhhp.add(aux.getTipoRegistro() == AuxExportFichas.TIPO_HHPP ? BigDecimal.ONE : aux.getPreficha().getTotalHHPP());
                datosHoja.put(String.valueOf(fila),
                        new Object[]{
                            "",
                            "",
                            aux.getPreficha().getViaPrincipal(),
                            aux.getPreficha().getPlaca(),
                            state.getGpoNombre(),
                            city.getGpoNombre(),
                            centro.getGpoNombre(),
                            aux.getTipoRegistro() == AuxExportFichas.TIPO_HHPP ? BigDecimal.ONE : aux.getPreficha().getTotalHHPP(),
                            aux.getTipoRegistro() == AuxExportFichas.TIPO_HHPP ? "" : aux.getPreficha().getAptos(),
                            aux.getTipoRegistro() == AuxExportFichas.TIPO_HHPP ? "" : aux.getPreficha().getLocales(),
                            aux.getTipoRegistro() == AuxExportFichas.TIPO_HHPP ? "" : aux.getPreficha().getPisos(),
                            aux.getPreficha().getClasificacion(),
                            aux.getPreficha().getBarrio(),
                            aux.getTipoRegistro() == AuxExportFichas.TIPO_HHPP ? aux.getDireccionHHPP().split("\\|")[1] : aux.getPreficha().getDistribucion(),
                            nodoCodigo.getNodNombre(),
                            aux.getPreficha().getFichaGeorreferenciaMgl() != null ? aux.getPreficha().getAmp() : "",
                            aux.getPreficha().getFichaGeorreferenciaMgl() != null ? aux.getPreficha().getFichaGeorreferenciaMgl().getCambioEstrato() : "",
                            aux.getPreficha().getFichaGeorreferenciaMgl() != null ? aux.getPreficha().getFichaGeorreferenciaMgl().getNeighborhood() : "",
                            aux.getPreficha().getFichaGeorreferenciaMgl() != null ? aux.getPreficha().getFichaGeorreferenciaMgl().getLevelEconomic() : "",
                            aux.getPreficha().getFichaGeorreferenciaMgl() != null ? aux.getPreficha().getFichaGeorreferenciaMgl().getAddress() : "",
                            aux.getPreficha().getFichaGeorreferenciaMgl() != null ? aux.getPreficha().getFichaGeorreferenciaMgl().getSource() : "",
                            aux.getPreficha().getFichaGeorreferenciaMgl() != null ? aux.getPreficha().getFichaGeorreferenciaMgl().getAlternateAddress() : "",
                            aux.getPreficha().getFichaGeorreferenciaMgl() != null ? aux.getPreficha().getFichaGeorreferenciaMgl().getLocality() : "",
                            aux.getPreficha().getFichaGeorreferenciaMgl() != null ? aux.getPreficha().getFichaGeorreferenciaMgl().getAppletStandar() : "",
                            aux.getPreficha().getFichaGeorreferenciaMgl() != null ? aux.getPreficha().getFichaGeorreferenciaMgl().getCx() : "",
                            aux.getPreficha().getFichaGeorreferenciaMgl() != null ? aux.getPreficha().getFichaGeorreferenciaMgl().getCy() : "",
                            (aux.getPreficha().getObservaciones() != null ? aux.getPreficha().getObservaciones() : "") + " - " + (aux.getPreficha().getObservacionFicha() != null ? aux.getPreficha().getObservacionFicha() : "") + (aux.getDrDireccion() != null ? aux.getDrDireccion().getObservacionFicha() : "")});
                fila++;
            }

            datosHoja.put(String.valueOf(fila++),
                    new Object[]{"", "", "", "", "", "", "Total HHPP", totalHhhp});

            dataHistorico.setCabecerasHojaErroresDuplicados(cabeceras);
            dataHistorico.setHojaErroresDuplicados(datosHoja);
        } else if (datosHojaHist != null && !datosHojaHist.isEmpty()) {
            datosHoja = datosHojaHist;
            addDatosAdicionales(datosAddHist, sheet);
        }

        crearDatosHoja(sheet, workbook, cabeceras, datosHoja, 1, false);

    }

    private void crearHojaHHPPSN(XSSFWorkbook workbook, Object[] cabecerasHist, Map<String, Object[]> datosHojaHist, Map<String, String> datosAddHist) throws ApplicationException {

        XSSFSheet sheet = workbook.createSheet("HHPP SN");

        BigDecimal totalHhhp = BigDecimal.ZERO;
        Map<String, Object[]> datosHoja = new TreeMap<String, Object[]>();

        GeograficoPoliticoMgl centro = geograficoPoliticoMglFacadeLocal.findById(nodoCodigo != null ? nodoCodigo.getGpoId() : dataHistorico.getIdNodo());
        GeograficoPoliticoMgl city = geograficoPoliticoMglFacadeLocal.findById(centro.getGeoGpoId());
        GeograficoPoliticoMgl state = geograficoPoliticoMglFacadeLocal.findById(city.getGeoGpoId());

        int fila = 1;
        Object[] cabeceras = cabecerasHist;
        if (cabeceras == null) {
            cabeceras = new Object[]{
                "NOMBRE CONJUNTO / USO DE PREDIO", "VIA PRINCIPAL MGL",
                "VIA GENERADORA + PLACA MGL", "DEPARTAMENTO", "CIUDAD",
                "CENTRO POBLADO", "TOTAL HHPP", "APTOS", "LOCALES ", "PISOS", "OFICINAS",
                "BARRIO FICHA", "CLASIFICACION", "DISTRIBUCION MGL", "NODO / TRONCAL", "NAP"};
        }
        if (hhppSNXls != null && !hhppSNXls.isEmpty()) {
            for (PreFichaXlsMgl p : hhppSNXls) {

                totalHhhp = totalHhhp.add(p.getTotalHHPP());
                datosHoja.put(String.valueOf(fila),
                        new Object[]{
                            p.getNombreConj(),
                            p.getViaPrincipal(),
                            p.getPlaca(),
                            state.getGpoNombre(),
                            city.getGpoNombre(),
                            centro.getGpoNombre(),
                            p.getTotalHHPP(),
                            p.getAptos(),
                            p.getLocales(),
                            p.getOficinas(),
                            p.getPisos(),
                            p.getBarrio(),
                            p.getClasificacion(),
                            p.getDistribucion(),
                            nodoCodigo.getNodNombre(),
                            p.getAmp()});
                fila++;
            }

            datosHoja.put(String.valueOf(fila++),
                    new Object[]{"", "", "", "", "", "Total HHPP", totalHhhp});

            dataHistorico.setCabecerasHojaHHPPSN(cabeceras);
            dataHistorico.setHojaHHPPSN(datosHoja);
        } else if (datosHojaHist != null && !datosHojaHist.isEmpty()) {
            datosHoja = datosHojaHist;
            addDatosAdicionales(datosAddHist, sheet);
        }

        crearDatosHoja(sheet, workbook, cabeceras, datosHoja, 1, false);

    }

    private DataArchivoExportFichas dataHistorico;

    public DataArchivoExportFichas getDataHistorico() {
        return this.dataHistorico;
    }

}
