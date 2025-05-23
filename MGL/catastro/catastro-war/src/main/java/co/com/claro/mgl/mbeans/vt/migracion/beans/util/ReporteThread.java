/*
 * Copyright (c) 2017, and/or its affiliates. All rights reserved.
 * CLARO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package co.com.claro.mgl.mbeans.vt.migracion.beans.util;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.mbeans.vt.migracion.beans.ReporteBean;
import co.com.claro.mgl.utils.ClassUtils;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Procesar el reporte general y el detallado
 * <p>
 * Permite procesar el reporte general y detallado mostrando la cantidad de
 * registros encontrados, se pude ver la cantidad de registros procesados. Al
 * finalizar de procesar se crea un objeto Workbook y StringBuilder con la
 * información de la data procesada. Para procesar la data se hacen llamados a
 * la base de datos en cantidades de 1000 y se procesan. Para determinar si
 * el proceso se termino se puede usar un atributo par ver si se está
 * procesando.
 *
 * @author becerraarmr
 *
 * @version 1.0 revisión 1.0
 */
public class ReporteThread extends Thread implements Serializable {

    
  private static final Logger LOGGER = LogManager.getLogger(ReporteThread.class);
  /**
   * Bean que solicita el reporte
   */
  private final ReporteBean reporteBean;

  /**
   * Paginador para realizar petición
   */
  private PaginationList pagination;
  /**
   * Cantidad de registros a solicitar a la base de datos.
   */
  private final int pageSize = 4000;
  /**
   * Para crear el archivo de excel
   */
  private XSSFWorkbook workbook;
  /**
   * Para crear el archivo CSV o TXT
   */
  private StringBuilder headerCsvTxt;
  /**
   * Para saber si está procesando el archivo
   */
  private boolean procesando;
  /**
   * Cantidad de registros encontrados
   */
  private int countRegistros;
  /**
   * Cantidad de filas insertadas
   */
  private int countProcess = 0;
  /**
   * Para guardar mensajes que describan lo que se ha realizado.
   */
  private String descripcionProcess = "No se encontraron datos";
  
  private List<Object[]> listReporteGeneral;

  /**
   * Crear la instancia
   * <p>
   * Crea una instancia
   *
   * @author becerraarmr
   * @param reporteBean valor del bean padre que realiza la solicitud
   */
  public ReporteThread(ReporteBean reporteBean) {
    this.reporteBean = reporteBean;
  }

  /**
   * Lanzar la tarea a procesar
   * <p>
   * Se hace la solicitud iniciando con la busqueda de la cantidad de registros
   * y luego si es mayor a 0 se procede a procesar, finalizando con la actualiza
   * ción del estado del proceso a false, que notifica que no se está
   * procesando.
   *
   * @author becerraarmr
   *
   */

  @Override
  public void run(){
    synchronized (this) {
      procesando = true;//inicia el procesamiento
      descripcionProcess="Procesando su solicitud";
      switch (reporteBean.getTipoReporteSeleccionado()) {
        case DETALLADO: {
          try {
              countRegistros = reporteBean.countRegistrosReporteDetallado();//Contar registros
            } catch (ApplicationException ex) {
                String msg = "Se produjo un error al momento de ejecutar el método '" + 
                        ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
                LOGGER.error(msg);
            }catch (Exception ex) {
                String msg = "Se produjo un error al momento de ejecutar el método '" + 
                        ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
                LOGGER.error(msg);
            }
          if (countRegistros > 0) {
            procesarReporteDetallado();
          } else {
            descripcionProcess="No se encontraron datos para su solicitud";
          }
          break;
        }
        case GENERAL: {
          try {
              //Rango nulo para que los busque todos
              listReporteGeneral = reporteBean.findDataReporteGeneral(null);
          } catch (ApplicationException ex) {
                String msg = "Se produjo un error al momento de ejecutar el método '"
                        + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
                LOGGER.error(msg);
            } catch (Exception ex) {
                String msg = "Se produjo un error al momento de ejecutar el método '"
                        + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
                LOGGER.error(msg);
            }
          if (!listReporteGeneral.isEmpty()) {
            countRegistros = listReporteGeneral.size();
            if (countRegistros > 0) {
              procesarReporteGeneral(listReporteGeneral);
            } else {
              descripcionProcess="No se encontraron datos para su solicitud";
            }
          }
          break;
        }
      }
      procesando = false;//Finaliza de procesar
    }
  }

  private void procesarReporteGeneral(List<Object[]> listGeneralReport) {
    if (listGeneralReport == null) {
      return;
    }

    if (listGeneralReport.isEmpty()) {
      return;
    }

    workbook = new XSSFWorkbook();

    //Create a blank sheet
    XSSFSheet sheet = workbook.createSheet("SOLICITUDES");
    int rowCount = 0;
    //Iterate over data and write to sheet
    
      String[] header = {"Cantidad", "Fecha de Ingreso", "Estado", "Descripción", "Regional"};
      Row row = sheet.createRow(rowCount++);

      for (int i = 0; i < header.length; i++) {
        Cell cell = row.createCell(i);
        cell.setCellValue(header[i]);
      }

      for (Object[] objects : listGeneralReport) {
          Row rowData = sheet.createRow(rowCount++);
          for (int i = 0; i < objects.length; i++) {
              Cell cell = rowData.createCell(i);
              if (objects[i] != null) {
                  if (objects[i].getClass().getTypeName().equals("java.sql.Timestamp")) {
                      String fechaHora = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(objects[i]);
                      cell.setCellValue(fechaHora == null ? "" : fechaHora + "");
                  } else {
                      cell.setCellValue(objects[i] == null ? "" : objects[i] + "");
                  }

              } else {
                  cell.setCellValue(objects[i] == null ? "" : objects[i] + "");
              }
          }
          countProcess++;
      }
  }

  /**
   * Se procesa la data que fue solicitada
   * <p>
   * Se crear la cabecera del archivo que se va a exportar
   *
   * @author becerraarmr
   */
  private void procesarReporteDetallado() {
    workbook = new XSSFWorkbook();
    headerCsvTxt = new StringBuilder();

    createHeader();

    do {
      List<Object[]> list = getPagination().createPageData();
      createXlsCsvTxt(list);
      getPagination().nextPage();
    } while (getPagination().isHasNextPage());
  }

  /**
   * Crea la cabecera del archivo
   * <p>
   * Se crear la cabecera del archivo que se va a exportar
   *
   * @author becerraarmr
   */
  private void createHeader() {
    XSSFSheet sheet = workbook.createSheet("SOLICITUDES");

    Row rowCabecera = sheet.createRow(0);

    int countCol = headFileDetallado().length;

    for (int j = 0; j < countCol; j++) {
      Cell cell = rowCabecera.createCell(j);
      cell.setCellValue(headFileDetallado()[j]);
      headerCsvTxt.append(headFileDetallado()[j]);
      if (j != countCol) {
        headerCsvTxt.append(";");
      }
    }
    headerCsvTxt.append("\n");

  }

  /**
   * Crear los archivos a exportar
   * <p>
   * Se crear el archivo de excel, el CSV y TXT
   *
   * @author becerraarmr
   */
  private void createXlsCsvTxt(List<Object[]> list) {
    if (list != null) {

        XSSFSheet sheet = workbook.getSheet("SOLICITUDES");

        int countCol = headFileDetallado().length;

        Iterator<Object[]> it = list.iterator();

        int rowCount = 1;
        while (it.hasNext()) {
          sheet=itemDataProcess(sheet, it, rowCount++, countCol);
        }
    }
  }

  private XSSFSheet itemDataProcess(
          XSSFSheet sheet, Iterator<Object[]> it, int rowCount, int countCol){
    Object[] objects = it.next();
    Row rowData = sheet.createRow(rowCount);

    for (int c = 0; c < countCol; c++) {
      Cell cell = rowData.createCell(c);
      cell.setCellValue(objects[c] + "");
      headerCsvTxt.append(objects[c]);

      if (c != countCol) {
        headerCsvTxt.append(";");
      }
    }

    if (it.hasNext()) {
      headerCsvTxt.append("\n");
    }
    countProcess++;
    descripcionProcess = "Se ha procesado: " + countProcess + " de " + countRegistros;
    return sheet;
  }

  /**
   * Crear el paginador de accesso a los datos
   * <p>
   * Se crea el paginador que permite acceder a la base de datos
   *
   * @author becerraarmr
   *
   * @return PaginationList que controlará el llamado a los datos
   */
  private PaginationList getPagination() {
    if (pagination == null) {
      pagination = new PaginationList(pageSize) {
        @Override
        public int getItemsCount() {
          return countRegistros;
        }

        @Override
        public List<Object[]> createPageData()  {
            try {
                return reporteBean.findDataReporteDetallado(new int[]{getPageFirstItem(),
                  getPageFirstItem() + getPageSize()});
            } catch (ApplicationException ex) {
               String msg = "Se produjo un error al momento de ejecutar el método '"
                        + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
                LOGGER.error(msg);            
            }catch (Exception ex) {
                String msg = "Se produjo un error al momento de ejecutar el método '"
                        + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + ex.getMessage();
                LOGGER.error(msg);
            }
            return null;
        }
      };
    }
    return pagination;
  }

  /**
   * Estructurar la cabecera del archivo a crear
   * <p>
   * Estructura los nombres de la cabecera del archivo a crear.
   *
   * @author becerraarmr
   *
   * @return El String correspondiente.
   */
    private static String[] headFileDetallado() {

        String head[] = {"USLOGUEO", "IDSOLICITUD",
            "CUENTAMATRIZ", "CUENTASUSCRIPTOR",
            "CALLE", "PLACA",
            "TIPOVIVIENDA", "COMPLEMENTO",
            "SOLICITANTE", "CONTACTO",
            "TELCONTACTO", "REGIONAL",
            "CIUDAD", "NODO",
            "TIPOSOL", "FECHAINGRESO",
            "FECHA_MODIFICACION", "FECHA_FINALIZACION",
            "TIEMPO", "MOTIVO",
            "ESTADO", "AREA",
            "USUARIO_GESTIÓN", "USUARIO_VERIFICADOR",
            "RGESTION", "RESPUESTA",
            "CORREGIR_HHPPP", "CAMBIO_NODO",
            "NOMBRE_NUEVOEDIFICIO", "NUEVO_PRODUCTO",
            "ESTRATO_ANTIGUO", "ESTRATO_NUEVO",
            "BARRIO", "HHP_ID",
            "DIRECCION_NO_ESTANDARIZADA",
            "NOD_NOMBRE", "TIPO_UNIDAD", "SDI_FORMATO_IGAC",
            "TIPO_CONEXION", "TIPO_RED",
            "HHP_FECHA_CREACION", "HHP_USUARIO_CREACION",
            "HHP_FECHA_MODIFICACION", "HHP_USUARIO_MODIFICACION",
            "ESTADO_HHPP", "HHP_ID_RR",
            "HHP_CALLE", "HHP_PLACA",
            "HHP_APART", "HHP_COMUNIDAD",
            "HHP_DIVISION", "HHP_ESTADO_UNIT",
            "HHP_VENDEDOR", "HHP_CODIGO_POSTAL",
            "HHP_EDIFICIO", "HHP_TIPO_ACOMET",
            "HHP_ULT_UBICACION", "HHP_HEAD_END",
            "HHP_TIPO", "HHP_TIPO_UNIDAD",
            "HHP_TIPO_CBL_ACOMETIDA", "HHP_FECHA_AUDIT",
            "CONFIABILIDAD_DIRECCION",
            "CONFIABILIDAD_COMPLEMENTO", "ESTADO_DIR",
            "NOTAADD", "DIRECCION", "DEPARTAMENTO",
            "CIUDAD","CENTRO_POBLADO"};
        return head;
    }


  /**
   * Busca le workbook creado para el archivo de excel.
   * <p>
   * Muestra el valor del workbook creado.
   *
   * @author becerraarmr
   *
   * @return el valor que representa el atributo
   */
  public XSSFWorkbook getWorkbook() {
    return workbook;
  }

  /**
   * Buscar el StringBuilder de CSV y TXT
   * <p>
   * Muestra el valor de la StringBuilder que representa la data para el archivo
   * de CSV y TXT.
   *
   * @author becerraarmr
   *
   * @return el StringBuilder que representa el atributo
   */
  public StringBuilder getHeaderCsvTxt() {
    return headerCsvTxt;
  }

  /**
   * Verificar si se está procesando
   * <p>
   * Verifica si el proceso se está procesando o no.
   *
   * @author becerraarmr
   *
   * @return true si está procesando y false en caso contrario
   */
  public boolean isProcesando() {
    return procesando;
  }
  
   /**
   * Buscar la cantidad de registros
   * <p>
   * Muestra la cantidad de registros encontrados
   *
   * @author becerraarmr
   *
   * @return el entero que representa el atributo
   */
  public int getCountRegistros() {
    return countRegistros;
  }
  
  /**
   * Buscar la descripción del proceso.
   * <p>
   * Muestra el valor que describe lo que se ha realizado en el proceso
   *
   * @author becerraarmr
   *
   * @return el String que representa el atributo
   */
  public String getDescripcionProcess() {
    return descripcionProcess;
  }
  /**
   * Buscar el valor del atributo countProcess.
   * 
   * Muestra el valor que contiene le atributo countProcess
   * 
   * @author becerraarmr
   * 
   * @return el entero que representa el atributo.
   */
  public int getCountProcess() {
    return countProcess;
  }

  /**
   * Buscar el listado de la data de reportes general
   * 
   * Muestra el valor de listReporteGeneral con el valor correspondiente
   * 
   * @author becerraarmr
   * 
   * @return un listado de arreglos 
   */
  public List<Object[]> getListReporteGeneral() {
    return listReporteGeneral;
  }
}
