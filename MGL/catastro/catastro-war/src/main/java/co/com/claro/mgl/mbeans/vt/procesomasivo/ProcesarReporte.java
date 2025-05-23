/*
 * Copyright (c) 2017, and/or its affiliates. All rights reserved.
 * CLARO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package co.com.claro.mgl.mbeans.vt.procesomasivo;

import co.com.claro.mgl.businessmanager.address.OtHhppMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtBasicaMglManager;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.DireccionMgl;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.OtHhppMgl;
import co.com.claro.mgl.jpa.entities.SubDireccionMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionDetalladaMgl;
import co.com.claro.mgl.utils.ClassUtils;
import co.com.claro.mgl.utils.Constant;
import co.com.telmex.catastro.data.Geografico;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

/**
 * Objetivo:
 * <p>
 * Descripción:
 *
 * @author becerraarmr
 *
 * @version 1.0 revisión 1.0
 */
class ProcesarReporte {

    private static final Logger LOGGER = LogManager.getLogger(ProcesarReporte.class);
    /**
     * Registros encontrados
     */
    private int registrosEncontrados = -1;//Inicia sin datos
    /**
     * Bean que hace la peticion
     */
    @Inject
    private ProcesarReporteBean control;
    /**
     * Paginador
     */
    private PaginationHelper pagination;
    /**
     * Tamaño de la página
     */
    private final int pageSize = 10000;
    /**
     * Cantidad de registros procesados
     */
    private int registrosProcesados = 0;

    /**
     * Para guardar la data encontrada en la base de datos.
     */
    private Workbook workbook;

    /**
     * Número de filas procesadas
     */
    private int rownum = 0;

    /**
     * Máximo de filas por hoja
     */
    private final int MAX_ROW_SHEET = 1000000;

    /**
     * Número de hojas creadas
     */
    private int numSheet = 0;

    /**
     * Atributo para la exportación de archivos Csv y Txt
     */
    private StringBuilder headerCsvTxt;

    private OtHhppMglManager otHhppMglManager = new OtHhppMglManager();

    /**
     * Crear la instancia
     * <p>
     * Crea una instancia
     *
     * @author becerraarmr
     *
     * @param control bean padre de la peticion;
     */
    public ProcesarReporte(ProcesarReporteBean control) {
        this.control = control;
    }

    /**
     * Crear Archivo temporal
     * <p>
     * Crea un archivo con el fin de guardarlo una ves termine de realizar el
     * reporte.
     *
     * @author becerraarmr
     */
    private void createFileTmp() {
        try {
            SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss-a");
            String fecha = formato.format(new Date());
            String filename = "PM_TMP_" + fecha + ".xlsx";
            File file = new File("tmp", filename);
            try (FileOutputStream outputStream = new FileOutputStream(file)) {
                workbook.write(outputStream);
            }
            control.setNameFileTmp(filename);
        } catch (FileNotFoundException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
        } catch (IOException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
        }
    }

    /**
     * Cargar la lista de datos en las variables de session.
     * <p>
     * Se carga la lista de datos en las variables de session.
     *
     * @author becerraarmr
     *
     * @param listaData lista de datos a cargar
     */
    private void cargarData() {
        try {
            @SuppressWarnings("unchecked")
            //BUSQUEDA DE LA INFORMACION PARA CARGAR EL ARCHIVO 
            List<HhppMgl> lista = getPagination().createPageData();
            if (lista == null) {
                return;
            }
            if (lista.isEmpty()) {
                return;
            }

            Sheet sheet = workbook.getSheet(numSheet + "");

            CellStyle style = workbook.createCellStyle();
            style.setFillForegroundColor((short) 70);
            style.setFillPattern(CellStyle.SOLID_FOREGROUND);

            Font font = workbook.createFont();
            font.setColor((short) 0);
            style.setFont(font);
            String fechaCreacion;
            String fechaUltMod;
            String nap;

            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

            while (!lista.isEmpty()) {
                if (rownum == MAX_ROW_SHEET) {
                    rownum = 0;
                    sheet = createSheetWithHeader();
                }
                HhppMgl hhppMgl = lista.remove(0);
                DireccionMgl dirMgl = control.getDireccionMgl(hhppMgl);
                Geografico geografico = control.getGeografico(hhppMgl);

                CmtDireccionDetalladaMgl detallada = null;
                try {
                    detallada = control.getDetallaadMgl(hhppMgl);
                } catch (Exception ex) {
                    String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
                    LOGGER.error(msg);
                }
                Object[] datos = ProcesoMasivoUtil.valoresHeadersBasic(hhppMgl, dirMgl, detallada);

                Row row = sheet.createRow(rownum);
                int i = 1;
                for (Object header : datos) {
                    Cell cell = row.createCell(i - 1);
                    String item = header == null ? "" : header.toString();
                    if (i >= 1 && i <= 4) {
                        cell.setCellStyle(style);
                    }

                    cell.setCellValue(item);

                    control.getDataCsvTxt().append(item);
                    if (i == datos.length) {
                        control.getDataCsvTxt().append(",");
                        continue;
                    }
                    control.getDataCsvTxt().append(",");
                    i++;
                }
                //<editor-fold defaultstate="collapsed" desc="REPORTE GENERAL">

                if ("REPORTE_GENERAL".equalsIgnoreCase(control.getTipoReporte())) {
                    Object[] headerGeneral = ProcesoMasivoUtil.headerGeneralSplit();
                    Object[] datosGenerales = ProcesoMasivoUtil.valoresHeadersGeneral(hhppMgl,
                            dirMgl, geografico);

                    int j = 1;

                    for (Object header : datosGenerales) {
                        String itemGeneral = header == null ? "" : header.toString();
                        Cell cell = row.createCell(i);
                        cell.setCellValue(itemGeneral);

                        if (control.isPuedeInhabilitarHhppp()) {

                            cell.setCellStyle(style);

                            control.getDataCsvTxt().append(itemGeneral);
                            if (j == headerGeneral.length) {
                                continue;
                            }
                            control.getDataCsvTxt().append(",");
                            i++;
                            j++;

                        } else {
                            if (j >= 1 && j <= 4) {
                                cell.setCellStyle(style);
                            }

                            if (j == 16) {
                                cell.setCellStyle(style);
                            }

                            control.getDataCsvTxt().append(itemGeneral);
                            if (j == headerGeneral.length) {
                                continue;
                            }
                            control.getDataCsvTxt().append(",");
                            i++;
                            j++;

                        }
                    }
                    if (!control.isPuedeInhabilitarHhppp()) {
                        Cell cellOrg = row.createCell(i);
                        cellOrg.setCellStyle(style);
                        cellOrg.setCellValue(hhppMgl.getHhppOrigenFicha() != null ? hhppMgl.getHhppOrigenFicha() : "");

                        control.getDataCsvTxt().append(hhppMgl.getHhppOrigenFicha() != null ? hhppMgl.getHhppOrigenFicha() : "");
                        control.getDataCsvTxt().append(",");

                        fechaCreacion = hhppMgl.getFechaCreacion() != null ? format.format(hhppMgl.getFechaCreacion()) : "";
                        i++;
                        Cell cellFechaCre = row.createCell(i);
                        cellFechaCre.setCellStyle(style);
                        cellFechaCre.setCellValue(fechaCreacion);

                        control.getDataCsvTxt().append(fechaCreacion);
                        control.getDataCsvTxt().append(",");

                        fechaUltMod = hhppMgl.getFechaEdicion() != null ? format.format(hhppMgl.getFechaEdicion()) : "";
                        i++;
                        Cell cellFechaMod = row.createCell(i);
                        cellFechaMod.setCellStyle(style);
                        cellFechaMod.setCellValue(fechaUltMod);

                        control.getDataCsvTxt().append(fechaUltMod);
                        control.getDataCsvTxt().append(",");
                        
                        if (control.getTipoTecnologia().equals(Constant.NODO_TIPO_RED_TECNOLOGIA_FTTH)) {
                            nap = hhppMgl.getNap() != null ? hhppMgl.getNap() : "";
                            i++;
                            Cell cellNap = row.createCell(i);
                            cellNap.setCellStyle(style);
                            cellNap.setCellValue(nap);
                            
                            control.getDataCsvTxt().append(nap);
                            control.getDataCsvTxt().append(",");
                        }

                    }

                }
                //</editor-fold>

                //<editor-fold defaultstate="collapsed" desc="REPORTE DETALLADO">
                if ("REPORTE_DETALLADO".equalsIgnoreCase(control.getTipoReporte())) {
                    String atributo = control.getAtributo();
                    CmtBasicaMgl basicaValidar = control.getCmtBasicaMgl(atributo);

                    if (Constant.CODIGO_BASICA_DIRECCION.equals(basicaValidar.getCodigoBasica())) {
                        Object[] headerDir = ProcesoMasivoUtil.headerDirecciones();
                        List<String> datosDir = null;
                        if (detallada != null) {
                            datosDir = datosDir(detallada);
                        }

                        if (control.isPuedeInhabilitarHhppp() && datosDir != null) {
                            headerDir = ProcesoMasivoUtil.valoresHeaders(headerDir, "0");
                            headerDir = ProcesoMasivoUtil.valoresHeaders(headerDir, "0");

                            SubDireccionMgl hhppSubDireccion = null;
                            if (hhppMgl.getSubDireccionObj() != null) {
                                hhppSubDireccion = hhppMgl.getSubDireccionObj();
                            }

                            CmtBasicaMglManager cmtBasicaMglManager = new CmtBasicaMglManager();

                            CmtBasicaMgl otDireccionesEstadoAbierto
                                    = cmtBasicaMglManager.findByCodigoInternoApp(
                                            Constant.ESTADO_GENERAL_OT_HHPP_RAZON_ABIERTO_COMPLETO);

                            OtHhppMgl otHhppMgl = otHhppMglManager.findOtByDireccionAndSubDireccion(
                                    hhppMgl.getDireccionObj(), hhppSubDireccion, otDireccionesEstadoAbierto);

                            if (otHhppMgl != null) {
                                String idOtHhppMgl = otHhppMgl.getOtHhppId().toString();
                                datosDir.add(idOtHhppMgl);
                            } else {
                                datosDir.add(null);
                            }

                            datosDir.add("1");
                            datosDir.add(hhppMgl.getHhppOrigenFicha() != null ? hhppMgl.getHhppOrigenFicha() : "");
                            fechaCreacion = hhppMgl.getFechaCreacion() != null ? format.format(hhppMgl.getFechaCreacion()) : "";
                            fechaUltMod = hhppMgl.getFechaEdicion() != null ? format.format(hhppMgl.getFechaEdicion()) : "";
                            datosDir.add(fechaCreacion);
                            datosDir.add(fechaUltMod);
                        }

                        if (datosDir != null) {
                            datosDir.add(hhppMgl.getHhppOrigenFicha() != null ? hhppMgl.getHhppOrigenFicha() : "");
                            fechaCreacion = hhppMgl.getFechaCreacion() != null ? format.format(hhppMgl.getFechaCreacion()) : "";
                            fechaUltMod = hhppMgl.getFechaEdicion() != null ? format.format(hhppMgl.getFechaEdicion()) : "";
                            datosDir.add(fechaCreacion);
                            datosDir.add(fechaUltMod);
                        }

                        int j = 1;
                        int k = 0;
                        String item = "";
                        for (Object header : headerDir) {
                            Cell cell = row.createCell(i);

                            if (datosDir != null) {
                                item = datosDir.get(k);
                                cell.setCellValue(item);
                            }
                            if (i < headerDir.length - 1) {
                                cell.setCellValue(item);
                            }
                            if (j == 45 || j == 46 || j == 47) {
                                cell.setCellStyle(style);
                            }
                            control.getDataCsvTxt().append(item);
                            if (j == headerDir.length) {
                                control.getDataCsvTxt().append(",");
                                continue;
                            }
                            control.getDataCsvTxt().append(",");
                            i++;
                            j++;
                            k++;
                        }
                    } else {
                        Object[] headerDetallada = ProcesoMasivoUtil.fusionHeader(new String[1], ProcesoMasivoUtil.headerDetalladoEstrato());
                        Object[] datosDetallada = {};

                        if (null != basicaValidar.getCodigoBasica()) {

                            switch (basicaValidar.getCodigoBasica()) {
                                case Constant.CODIGO_BASICA_ESTRATO:
                                    datosDetallada = ProcesoMasivoUtil.valoresHeadersDetalladoEstrato(dirMgl, hhppMgl);
                                    break;
                                case Constant.CODIGO_BASICA_ESTADO:
                                    datosDetallada = ProcesoMasivoUtil.valoresHeadersDetalladoEstado(hhppMgl);
                                    break;
                                case Constant.CODIGO_BASICA_COBERTURA:
                                    datosDetallada = ProcesoMasivoUtil.valoresHeadersDetalladoCobertura(hhppMgl);
                                    break;
                                case Constant.CODIGO_BASICA_TIPO_VIVIENDA:
                                    datosDetallada = ProcesoMasivoUtil.valoresHeadersDetalladoTipoVivienda(hhppMgl);
                                    break;
                                case Constant.CODIGO_BASICA_ETIQUETA:
                                    datosDetallada = ProcesoMasivoUtil.valoresHeadersDetalladoEtiqueta(hhppMgl);
                                    break;
                                default:
                                    break;
                            }
                        }
                        int j = 1;

                        for (Object header : datosDetallada) {
                            String itemGeneral = header == null ? "" : header.toString();
                            Cell cell = row.createCell(i);

                            if ((Constant.CODIGO_BASICA_ESTRATO.equalsIgnoreCase(basicaValidar.getCodigoBasica()))
                                    && (j == 1 || j == 2 || j == 5 || j == 6 || j == 7)) {
                                cell.setCellStyle(style);
                            } else if ((Constant.CODIGO_BASICA_ESTRATO.equalsIgnoreCase(basicaValidar.getCodigoBasica()))) {
                                LOGGER.info("No colorea celda");
                            } else if ((Constant.CODIGO_BASICA_ETIQUETA.equalsIgnoreCase(basicaValidar.getCodigoBasica()))
                                    && (j == 1 || j == 4 || j == 5 || j == 6 || j == 7)) {
                                cell.setCellStyle(style);
                            } else if (j == 1 || j == 4 || j == 5 || j == 6) {
                                cell.setCellStyle(style);
                            }

                            cell.setCellValue(itemGeneral);

                            control.getDataCsvTxt().append(itemGeneral);
                            if (j == headerDetallada.length) {
                                continue;
                            }
                            control.getDataCsvTxt().append(",");
                            i++;
                            j++;
                        }

                    }
                }
                //</editor-fold>

                if (control.isPuedeInhabilitarHhppp()) {

                    Cell cellOrg = row.createCell(i);
                    cellOrg.setCellStyle(style);
                    cellOrg.setCellValue(hhppMgl.getHhppOrigenFicha() != null ? hhppMgl.getHhppOrigenFicha() : "");

                    control.getDataCsvTxt().append(hhppMgl.getHhppOrigenFicha() != null ? hhppMgl.getHhppOrigenFicha() : "");
                    control.getDataCsvTxt().append(",");

                    fechaCreacion = hhppMgl.getFechaCreacion() != null ? format.format(hhppMgl.getFechaCreacion()) : "";
                    i++;
                    Cell cellFechaCre = row.createCell(i);
                    cellFechaCre.setCellStyle(style);
                    cellFechaCre.setCellValue(fechaCreacion);

                    control.getDataCsvTxt().append(fechaCreacion);
                    control.getDataCsvTxt().append(",");

                    fechaUltMod = hhppMgl.getFechaEdicion() != null ? format.format(hhppMgl.getFechaEdicion()) : "";
                    i++;
                    Cell cellFechaMod = row.createCell(i);
                    cellFechaMod.setCellStyle(style);
                    cellFechaMod.setCellValue(fechaUltMod);

                    control.getDataCsvTxt().append(fechaUltMod);
                    control.getDataCsvTxt().append(",");

                    i++;
                    Cell cell = row.createCell(i);
                    cell.setCellStyle(style);
                    cell.setCellValue(hhppMgl.getEstadoOt() != null ? hhppMgl.getEstadoOt() : "NA");

                    control.getDataCsvTxt().append(hhppMgl.getEstadoOt() != null ? hhppMgl.getEstadoOt() : "NA");
                    control.getDataCsvTxt().append(",");

                    i++;
                    Cell cellInha = row.createCell(i);
                    cellInha.setCellValue("");

                    control.getDataCsvTxt().append("");
                    control.getDataCsvTxt().append(",");
                }

                rownum++;
                registrosProcesados++;
                if (lista.isEmpty()) {
                    continue;
                }
                control.getDataCsvTxt().append("\n");
            }

        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
        }
    }

    /**
     * Crea la data del filtro seleccionado
     * <p>
     * Se crea la data del filtro seleccionado; el tipo de reporte, el tipo de
     * tec nología, el departamento, la ciudad, el centro poblado, el nodo,
     * buscando en las tablas el valor correspondiente
     *
     * @author becerraarmr
     *
     * @return String[] con los valores de filtro
     */
    private String[] getDataFilters() {
        String tipoReporte = control.getTipoReporte();
        String tipoTecnologia = control.getTipoTecnologia();
        String departamento = control.getDepartamento();
        String ciudad = control.getCiudad();
        String centroPoblado = control.getCentroPoblado();
        String nodo = control.getNodo();
        String atributo = control.getAtributo();
        Object valorAtributo = control.getValorAtributo();

        String valorAtributoString;
        if (valorAtributo instanceof DrDireccion) {
            valorAtributoString = ((DrDireccion) valorAtributo).getGeoReferenciadaString();
        } else {
            valorAtributoString = (String) valorAtributo;
        }

        String[] filtrosValores = {tipoReporte, tipoTecnologia, departamento, ciudad,
            centroPoblado, nodo, atributo, valorAtributoString};
        CmtBasicaMgl basTipoTecnologia = control.getCmtBasicaMgl(tipoTecnologia);
        filtrosValores[1] = basTipoTecnologia != null
                ? basTipoTecnologia.getNombreBasica() : "";
        GeograficoPoliticoMgl geoDptoCiudadCentroPob
                = control.getGeograficoMgl(departamento);
        filtrosValores[2] = geoDptoCiudadCentroPob != null
                ? geoDptoCiudadCentroPob.getGpoNombre() : "";
        geoDptoCiudadCentroPob = control.getGeograficoMgl(ciudad);
        filtrosValores[3] = geoDptoCiudadCentroPob != null
                ? geoDptoCiudadCentroPob.getGpoNombre() : "";
        geoDptoCiudadCentroPob = control.getGeograficoMgl(centroPoblado);
        filtrosValores[4] = geoDptoCiudadCentroPob != null
                ? geoDptoCiudadCentroPob.getGpoNombre() : "";
        NodoMgl nodoMglFilt = control.getNodoMgl(nodo);
        filtrosValores[5] = nodoMglFilt != null
                ? nodoMglFilt.getNodCodigo() : "";
        basTipoTecnologia = control.getCmtBasicaMgl(atributo);
        filtrosValores[6] = basTipoTecnologia != null
                ? basTipoTecnologia.getNombreBasica() : "";
        return filtrosValores;
    }

    /**
     * Crear cabecera del archivo de excel
     * <p>
     * Crea la cabecera en tres filas, donde la primera son los datos del
     * filtro, la segunda los valores del filtro y la tercera las cabeceras del
     * Hhpp.
     *
     * @author becerraarmr
     *
     * @return XSSFWorkbook con los valores de inicio del archivo
     */
    private Sheet createSheetWithHeader() {
        try {

            String[] filters = getDataFilters();
            numSheet++;

            Sheet sheet = workbook.createSheet(numSheet + "");//Create Sheet 
            Row row = sheet.createRow(rownum);//Create 1 row
            //Create Sheet 
            CellStyle lockedCellStyle = workbook.createCellStyle();
            lockedCellStyle.setLocked(true);

            CellStyle style = workbook.createCellStyle();
            style.setFillForegroundColor((short) 70);
            style.setFillPattern(CellStyle.SOLID_FOREGROUND);

            Font font = workbook.createFont();
            font.setColor((short) 0);
            style.setFont(font);

            rownum++;
            for (int i = 0; i < ProcesoMasivoUtil.filtrosHeaders().length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellStyle(style);
                cell.setCellValue(ProcesoMasivoUtil.filtrosHeaders()[i]);
                headerCsvTxt.append(ProcesoMasivoUtil.filtrosHeaders()[i]);
                if (i < ProcesoMasivoUtil.filtrosHeaders().length) {
                    headerCsvTxt.append(",");
                }
            }
            headerCsvTxt.append("\n");
            row = sheet.createRow(rownum);//Create 2 row
            rownum++;
            for (int i = 0; i < filters.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellStyle(style);
                cell.setCellValue(filters[i]);
                headerCsvTxt.append(filters[i]);
                if (i < ProcesoMasivoUtil.filtrosHeaders().length) {
                    headerCsvTxt.append(",");
                }
            }
            headerCsvTxt.append("\n");
            //Create 3 row
            row = sheet.createRow(rownum);
            rownum++;
            String[] header = ProcesoMasivoUtil.header();
            String atributo = "";
            CmtBasicaMgl basicaFiltro = null;

            if ("REPORTE_DETALLADO".equalsIgnoreCase(control.getTipoReporte())) {
                atributo = control.getAtributo();
                basicaFiltro = control.getCmtBasicaMgl(atributo);

                if (Constant.CODIGO_BASICA_DIRECCION.equals(basicaFiltro.getCodigoBasica())) {
                    header = ProcesoMasivoUtil.
                            fusionHeader(header, ProcesoMasivoUtil.headerDireccionesGeneral());

                } else if (Constant.CODIGO_BASICA_ESTRATO.equals(basicaFiltro.getCodigoBasica())) {
                    //si el reporte es detallado por estrato 
                    header = ProcesoMasivoUtil.
                            fusionHeader(header, ProcesoMasivoUtil.headerDetalladoEstrato());
                } else if (Constant.CODIGO_BASICA_ESTADO.equals(basicaFiltro.getCodigoBasica())) {
                    //si el reporte es detallado por estado 
                    header = ProcesoMasivoUtil.
                            fusionHeader(header, ProcesoMasivoUtil.headerDetalladoEstado());
                } else if (Constant.CODIGO_BASICA_COBERTURA.equals(basicaFiltro.getCodigoBasica())) {
                    //si el reporte es detallado por nivel socioeconomico 
                    header = ProcesoMasivoUtil.
                            fusionHeader(header, ProcesoMasivoUtil.headerDetalladoCobertura());
                } else if (Constant.CODIGO_BASICA_TIPO_VIVIENDA.equals(basicaFiltro.getCodigoBasica())) {
                    //si el reporte es detallado por tipo vivienda
                    header = ProcesoMasivoUtil.
                            fusionHeader(header, ProcesoMasivoUtil.headerDetalladoTipoVivienda());
                } else if (Constant.CODIGO_BASICA_ETIQUETA.equals(basicaFiltro.getCodigoBasica())) {
                    //si el reporte es detallado Etiqueta
                    header = ProcesoMasivoUtil.
                            fusionHeader(header, ProcesoMasivoUtil.headerDetalladoEtiqueta());
                }

            } else { // si el reporte es general
                if (control.getTipoTecnologia().equals(Constant.NODO_TIPO_RED_TECNOLOGIA_FTTH)) {
                    header = ProcesoMasivoUtil.
                        fusionHeader(header, ProcesoMasivoUtil.headerGeneralTecnologiaFtth());
                } else {
                    header = ProcesoMasivoUtil.
                        fusionHeader(header, ProcesoMasivoUtil.headerGeneral());
                }
                
            }
            if (control.isPuedeInhabilitarHhppp()) {
                header = ProcesoMasivoUtil.
                        fusionHeader(header, "Estado_OT");
                header = ProcesoMasivoUtil.
                        fusionHeader(header, "Inhabilitar");
            }

            int i = 0;
            for (; i < header.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(header[i]);

                if (!"".equals(atributo)) {
                    if ((Constant.CODIGO_BASICA_ESTRATO.equals(basicaFiltro.getCodigoBasica()) && i == 5)) {
                        cell.setCellStyle(style);
                    }
                    if ((!Constant.CODIGO_BASICA_DIRECCION.equals(basicaFiltro.getCodigoBasica()) && i == 4)) {
                        cell.setCellStyle(style);
                    }
                } else if ((i >= 4 && i <= 7)) {
                    cell.setCellStyle(style);
                }
                if ((i >= 0 && i <= 3)) {
                    cell.setCellStyle(style);
                }
                headerCsvTxt.append(header[i]);
                if (i < header.length) {
                    headerCsvTxt.append(",");
                }
            }

            headerCsvTxt.append("\n");

            return sheet;
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"
                    + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);

        }
        return null;
    }

    /**
     * Crear el paginador de accesso a los datos
     * <p>
     * Se crea el paginador que permite acceder a la base de datos
     *
     * @author becerraarmr
     *
     * @return PaginationHelper que controlará el llamado a los datos
     * @throws ApplicationException
     */
    private PaginationHelper getPagination() throws ApplicationException {
        if (pagination == null) {
            pagination = new PaginationHelper(pageSize) {
                @Override
                public int getItemsCount() {
                    return registrosEncontrados;
                }
                //BUSQUEDA DE LA INFORMACION PARA CARGAR EL ARCHIVO 

                @Override
                public List<HhppMgl> createPageData() throws ApplicationException {
                    return control.findDataReporte(new int[] {
                        getPageFirstItem(),
                        getPageFirstItem() + getPageSize()
                    });
                }
            };
        }
        return pagination;
    }

    /**
     * Buscar el valor de regProcesados.
     * <p>
     * Muestra el valor de registros que han sido procesados
     *
     * @author becerraarmr
     *
     * @return el valor que representa el atributo
     */
    public int getRegistrosProcesados() {
        return registrosProcesados;
    }

    /**
     * Buscar el valor del atributo.
     * <p>
     * Muestra el valor que contiene registrosEncontrados
     *
     * @author becerraarmr
     *
     * @return el entero con el valor encontrado
     */
    public int getRegistrosEncontrados() {
        return registrosEncontrados;
    }

    public void setRegistrosEncontrados(int registrosEncontrados) {
        this.registrosEncontrados = registrosEncontrados;
    }

    /**
     * Buscar el valor del atributo.
     * <p>
     * Muestra el valor del workbook
     *
     * @author becerraarmr
     *
     * @return Workbook con el valor
     */
    public Workbook getWorkbook() {
        return workbook;
    }

    public List<String> datosDir(CmtDireccionDetalladaMgl direccionDetalladaMgl) {

        List<String> datos = new ArrayList<String>();
        datos.add("");
        datos.add(direccionDetalladaMgl.getIdTipoDireccion() != null ? direccionDetalladaMgl.getIdTipoDireccion() : "");
        datos.add(direccionDetalladaMgl.getDirPrincAlt() != null ? direccionDetalladaMgl.getDirPrincAlt() : "");
        datos.add(direccionDetalladaMgl.getBarrio() != null ? direccionDetalladaMgl.getBarrio() : "");
        datos.add(direccionDetalladaMgl.getTipoViaPrincipal() != null ? direccionDetalladaMgl.getTipoViaPrincipal() : "");
        datos.add(direccionDetalladaMgl.getNumViaPrincipal() != null ? direccionDetalladaMgl.getNumViaPrincipal() : "");
        datos.add(direccionDetalladaMgl.getLtViaPrincipal() != null ? direccionDetalladaMgl.getLtViaPrincipal() : "");
        datos.add(direccionDetalladaMgl.getNlPostViaP() != null ? direccionDetalladaMgl.getNlPostViaP() : "");
        datos.add(direccionDetalladaMgl.getBisViaPrincipal() != null ? direccionDetalladaMgl.getBisViaPrincipal() : "");
        datos.add(direccionDetalladaMgl.getCuadViaPrincipal() != null ? direccionDetalladaMgl.getCuadViaPrincipal() : "");
        datos.add(direccionDetalladaMgl.getTipoViaGeneradora() != null ? direccionDetalladaMgl.getTipoViaGeneradora() : "");
        datos.add(direccionDetalladaMgl.getNumViaGeneradora() != null ? direccionDetalladaMgl.getNumViaGeneradora() : "");
        datos.add(direccionDetalladaMgl.getLtViaGeneradora() != null ? direccionDetalladaMgl.getLtViaGeneradora() : "");
        datos.add(direccionDetalladaMgl.getNlPostViaG() != null ? direccionDetalladaMgl.getNlPostViaG() : "");
        datos.add(direccionDetalladaMgl.getBisViaGeneradora() != null ? direccionDetalladaMgl.getBisViaGeneradora() : "");
        datos.add(direccionDetalladaMgl.getCuadViaGeneradora() != null ? direccionDetalladaMgl.getCuadViaGeneradora() : "");
        datos.add(direccionDetalladaMgl.getPlacaDireccion() != null ? direccionDetalladaMgl.getPlacaDireccion() : "");
        datos.add(direccionDetalladaMgl.getCpTipoNivel1() != null ? direccionDetalladaMgl.getCpTipoNivel1() : "");
        datos.add(direccionDetalladaMgl.getCpTipoNivel2() != null ? direccionDetalladaMgl.getCpTipoNivel2() : "");
        datos.add(direccionDetalladaMgl.getCpTipoNivel3() != null ? direccionDetalladaMgl.getCpTipoNivel3() : "");
        datos.add(direccionDetalladaMgl.getCpTipoNivel4() != null ? direccionDetalladaMgl.getCpTipoNivel4() : "");
        datos.add(direccionDetalladaMgl.getCpTipoNivel5() != null ? direccionDetalladaMgl.getCpTipoNivel5() : "");
        datos.add(direccionDetalladaMgl.getCpTipoNivel6() != null ? direccionDetalladaMgl.getCpTipoNivel6() : "");
        datos.add(direccionDetalladaMgl.getCpValorNivel1() != null ? direccionDetalladaMgl.getCpValorNivel1() : "");
        datos.add(direccionDetalladaMgl.getCpValorNivel2() != null ? direccionDetalladaMgl.getCpValorNivel2() : "");
        datos.add(direccionDetalladaMgl.getCpValorNivel3() != null ? direccionDetalladaMgl.getCpValorNivel3() : "");
        datos.add(direccionDetalladaMgl.getCpValorNivel4() != null ? direccionDetalladaMgl.getCpValorNivel4() : "");
        datos.add(direccionDetalladaMgl.getCpValorNivel5() != null ? direccionDetalladaMgl.getCpValorNivel5() : "");
        datos.add(direccionDetalladaMgl.getCpValorNivel6() != null ? direccionDetalladaMgl.getCpValorNivel6() : "");
        datos.add(direccionDetalladaMgl.getMzTipoNivel1() != null ? direccionDetalladaMgl.getMzTipoNivel1() : "");
        datos.add(direccionDetalladaMgl.getMzTipoNivel2() != null ? direccionDetalladaMgl.getMzTipoNivel2() : "");
        datos.add(direccionDetalladaMgl.getMzTipoNivel3() != null ? direccionDetalladaMgl.getMzTipoNivel3() : "");
        datos.add(direccionDetalladaMgl.getMzTipoNivel4() != null ? direccionDetalladaMgl.getMzTipoNivel4() : "");
        datos.add(direccionDetalladaMgl.getMzTipoNivel5() != null ? direccionDetalladaMgl.getMzTipoNivel5() : "");
        datos.add(direccionDetalladaMgl.getMzValorNivel1() != null ? direccionDetalladaMgl.getMzValorNivel1() : "");
        datos.add(direccionDetalladaMgl.getMzValorNivel2() != null ? direccionDetalladaMgl.getMzValorNivel2() : "");
        datos.add(direccionDetalladaMgl.getMzValorNivel3() != null ? direccionDetalladaMgl.getMzValorNivel3() : "");
        datos.add(direccionDetalladaMgl.getMzValorNivel4() != null ? direccionDetalladaMgl.getMzValorNivel4() : "");
        datos.add(direccionDetalladaMgl.getMzValorNivel5() != null ? direccionDetalladaMgl.getMzValorNivel5() : "");
        datos.add(direccionDetalladaMgl.getMzTipoNivel6() != null ? direccionDetalladaMgl.getMzTipoNivel6() : "");
        datos.add(direccionDetalladaMgl.getMzValorNivel6() != null ? direccionDetalladaMgl.getMzValorNivel6() : "");
        datos.add(direccionDetalladaMgl.getItTipoPlaca() != null ? direccionDetalladaMgl.getItTipoPlaca() : "");
        datos.add(direccionDetalladaMgl.getItValorPlaca() != null ? direccionDetalladaMgl.getItValorPlaca() : "");
        datos.add(direccionDetalladaMgl.getLetra3G() != null ? direccionDetalladaMgl.getLetra3G() : "");

        return datos;
    }

    public void procesar() throws ApplicationException {

        /* registrosEncontrados = control.countDataReporte(); */
        int result = control.countDataReporte();
        this.setRegistrosEncontrados(result);

        try {

            if (this.getRegistrosEncontrados() == 0) {
                control.setMsgProcesamiento("No hay registros con los parámetros insertados");
            } else {
                control.setMsgProcesamiento("Se empieza a procesar el reporte");
                workbook = new SXSSFWorkbook(-1);
                headerCsvTxt = new StringBuilder();
                createSheetWithHeader();
                control.getDataCsvTxt().append(headerCsvTxt);
                cargarData();
                while (getPagination().isHasNextPage()) {
                    getPagination().nextPage();
                    cargarData();
                }
                createFileTmp();
            }
            control.setProcesandoSolicitud(false);
        } catch (Exception ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
            LOGGER.error(msg);
            throw new Error(ex.getMessage());
        }

    }

}
