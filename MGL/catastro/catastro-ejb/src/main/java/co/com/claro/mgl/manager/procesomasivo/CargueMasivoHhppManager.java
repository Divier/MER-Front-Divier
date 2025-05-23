/*
 * Copyright (c) 2017, and/or its affiliates. All rights reserved.
 * CLARO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package co.com.claro.mgl.manager.procesomasivo;

import co.com.claro.mgl.businessmanager.address.CmtNotasHhppDetalleVtMglManager;
import co.com.claro.mgl.businessmanager.address.CmtNotasHhppVtMglManager;
import co.com.claro.mgl.businessmanager.address.DireccionMglManager;
import co.com.claro.mgl.businessmanager.address.DireccionesValidacionCargueManager;
import co.com.claro.mgl.businessmanager.address.DrDireccionManager;
import co.com.claro.mgl.businessmanager.address.EstadoHhppMglManager;
import co.com.claro.mgl.businessmanager.address.GeograficoMglManager;
import co.com.claro.mgl.businessmanager.address.GeograficoPoliticoManager;
import co.com.claro.mgl.businessmanager.address.HhppMglManager;
import co.com.claro.mgl.businessmanager.address.MarcasHhppMglManager;
import co.com.claro.mgl.businessmanager.address.MarcasMglManager;
import co.com.claro.mgl.businessmanager.address.NodoMglManager;
import co.com.claro.mgl.businessmanager.address.NotasAdicionalesMglManager;
import co.com.claro.mgl.businessmanager.address.ParametrosMglManager;
import co.com.claro.mgl.businessmanager.address.RrCiudadesManager;
import co.com.claro.mgl.businessmanager.address.SubDireccionMglManager;
import co.com.claro.mgl.businessmanager.address.TipoHhppConexionMglManager;
import co.com.claro.mgl.businessmanager.address.TipoHhppMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtBasicaMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtDireccionDetalleMglManager;
import co.com.claro.mgl.businessmanager.cm.CmtTipoBasicaMglManager;
import co.com.claro.mgl.businessmanager.cm.ComunidadRrManager;
import co.com.claro.mgl.businessmanager.cm.RegionalMglManager;
import co.com.claro.mgl.businessmanager.cm.UsuariosManager;
import co.com.claro.mgl.dao.impl.MarcasMglDaoImpl;
import co.com.claro.mgl.dtos.CmtDefaultBasicResponse;
import co.com.claro.mgl.entities.procesomasivo.CargueArchivoLogItem;
import co.com.claro.mgl.entities.procesomasivo.HhppCargueArchivoLog;
import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.jpa.entities.CmtNotasHhppDetalleVtMgl;
import co.com.claro.mgl.jpa.entities.CmtNotasHhppVtMgl;
import co.com.claro.mgl.jpa.entities.DireccionMgl;
import co.com.claro.mgl.jpa.entities.DrDireccion;
import co.com.claro.mgl.jpa.entities.DrDireccionMgl;
import co.com.claro.mgl.jpa.entities.EstadoHhppMgl;
import co.com.claro.mgl.jpa.entities.GeograficoPoliticoMgl;
import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.MarcasHhppMgl;
import co.com.claro.mgl.jpa.entities.MarcasMgl;
import co.com.claro.mgl.jpa.entities.NodoMgl;
import co.com.claro.mgl.jpa.entities.NotasAdicionalesMgl;
import co.com.claro.mgl.jpa.entities.ParametrosMgl;
import co.com.claro.mgl.jpa.entities.SubDireccionMgl;
import co.com.claro.mgl.jpa.entities.TipoHhppConexionMgl;
import co.com.claro.mgl.jpa.entities.TipoHhppMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtComunidadRr;
import co.com.claro.mgl.jpa.entities.cm.CmtDireccionDetalladaMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtRegionalRr;
import co.com.claro.mgl.jpa.entities.cm.CmtTipoBasicaMgl;
import co.com.claro.mgl.jpa.entities.cm.Usuarios;
import co.com.claro.mgl.utils.Constant;
import co.com.claro.mgl.utils.SFTPUtils;
import co.com.claro.mgl.vo.cm.DetalleCargaHhppMgl;
import co.com.claro.mgl.ws.cm.request.RequestConstruccionDireccion;
import co.com.claro.mgl.ws.cm.response.ResponseConstruccionDireccion;
import co.com.claro.visitasTecnicas.business.DireccionRRManager;
import co.com.claro.visitasTecnicas.entities.CityEntity;
import co.com.claro.visitasTecnicas.entities.DireccionRREntity;
import co.com.claro.visitasTecnicas.entities.HhppResponseRR;
import co.com.telmex.catastro.data.Geografico;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import javax.servlet.http.Part;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import co.com.claro.mgl.utils.ClassUtils;
import java.nio.file.Files;


/**
 * Gestionar el cargue masivo
 * <p>
 * Permite gestionar el cargue masivo según la regla establecida: Primero se
 * intenta procesar los primeros 1500 o los que estén parametrizados luego se
 * pasan los siguientes a cola para ser procesados en horas nocturnas o según
 * como esté parametrizado.
 *
 * @author becerraarmr
 *
 */
public class CargueMasivoHhppManager extends Thread {

    private static final Logger LOGGER = LogManager.getLogger(CargueMasivoHhppManager.class);
    
    /**
     * Calendar para guardar la hora de inicio del proceso.
     */
    private Calendar horaInicioProceso;
    /**
     * Calendar para guardar la Hora final del proceso.
     */
    private Calendar horaFinalProceso;
    /**
     * Para realizar las acciones con hhpp
     */
    protected HhppMglManager hhppManager = new HhppMglManager();
    /**
     * Para realizar las acciones con rr ciudades
     */
    protected RrCiudadesManager rrCiudadesManager;
    /**
     * Para realizar las acciones con el tipo HHpp conexiones
     */
    protected TipoHhppConexionMglManager tipoHhppConexionManager;
    /**
     * Para realizar las acciones con NodoMgl
     */
    protected NodoMglManager nodoManager;
    /**
     * Para realizar las acciones con direcciones RR
     */
    protected DireccionRRManager direccionRrManager;
    /**
     * Para realizar las acciones con direcciones mgl
     */
    protected DireccionMglManager ejbDireccionMgl;

    protected SubDireccionMglManager ejbSubdireccionMgl = new SubDireccionMglManager();
    /**
     * Para realizar las acciones con el estado hhpp
     */
    protected EstadoHhppMglManager estadoHhppManager;
    /**
     * para realizar las acciones con basicas
     */
    protected CmtBasicaMglManager basicaManager;
    /**
     * Para realizar acciones con cargue archivo log
     */
    protected HhppCargueArchivoLogManager cargeArchivoManager;
    /**
     * Para realizar acciones con las notas.
     */
    protected NotasAdicionalesMglManager notasAdicionalesManager;
    /**
     * Para buscar los parámetros configurados para el cargue
     */
    protected ParametrosMglManager parametrosMglManager;
    /**
     * Para saber el nombre del usuario
     */
    protected String usuario;
    /**
     * Para saber el numero del perfil
     */
    protected int perfil;
    /**
     * Guarda el nombre del archivo
     */
    protected String fileName;
    /**
     * Guarda el mensaje al instante de procesamiento
     */
    protected String msgProceso;
    /**
     * Data con el archivo que se carga.
     */
    protected Part part;
    /**
     * Objeto para guardar el archivo de excel de respuesta
     */
    protected Workbook workbook;
    /**
     * para establecer el estado del proceso
     */
    protected boolean archivoProcesado;
    /**
     * Cantidad de registros encontrados.
     */
    protected int countRowData = 0;
    /**
     * Cantidad minima para procesar, el cual se estableció 1500 para no
     * bloquear RR
     */
    protected int countMinProcess = 1500;
    /**
     * Franja de tiempo para ejecutar cada registro del excel, se procede que
     * cada 4 segundo se procesará para no bloquear RR.
     */
    protected int franjaTiempo = 4000;
    /**
     * Para conocer la cabecera del archivo cargado.
     */
    private String[] headerSel;

    /**
     *
     */
    private String headerFiltros;
    /**
     * Para saber si se puede validar direccion.
     */
    private boolean isHeaderDirecciones = false;
    /**
     * Para saber si se puede validar la cabecera de georefencia.
     */
    private boolean isHeaderDirGeo = false;
    /**
     * Para saber si se puede validar la cabecera de Inhabilitar.
     */
    private boolean isHeaderInhabilitar = false;

    /**
     * Nombre del archivo procesado.
     */
    private String fileNameProcesado;

    /**
     * Nombre del archivo procesado.
     */
    private String tipoReporte = "";

    /**
     * decision si el archivo requeire proceso nocturno
     */
    private boolean procesoNocturno = false;

    /**
     * manager de los logs de cada cargue
     */
    CargueArchivoLogItemManager calim = new CargueArchivoLogItemManager();

     /**
     * Manager para hacer las transacciones en RR
     */
    DireccionRRManager direccionRRManager = new DireccionRRManager(true);
   
     CmtDireccionDetalleMglManager managerDetalleDirs = new CmtDireccionDetalleMglManager();
    
    /**
     * Temporizador de tareas
     */
   
    private TimerTask timerTask;
    private HhppMgl hhppMgl;
    protected CmtTipoBasicaMglManager tipoBasicaMglManager = new CmtTipoBasicaMglManager();
    protected GeograficoPoliticoManager geograficoPoliticoManager = new GeograficoPoliticoManager();
    protected CmtDireccionDetalleMglManager direccionDetalleMglmanager = new CmtDireccionDetalleMglManager();
    private CmtNotasHhppVtMglManager cmtNotasHhppVtMglManager = new CmtNotasHhppVtMglManager();
    private CmtNotasHhppDetalleVtMglManager cmtNotasHhppDetalleVtMglManager = new CmtNotasHhppDetalleVtMglManager();
    private static int NUM_REGISTROS_PAGINA = 50;
    List<CargueArchivoLogItem> registrosBd = null; 
    private List<HhppMgl> listaHhppActualizar = null;
    private StringBuilder  datosModificados; 
    private String atributoCargado;
    List<CmtBasicaMgl> atributosHhpp = null; 

    /**
     * Crear la instancia de CargueMasivoHhppManager Se carga el usuario y el
     * objeto part con el archivo a procesar.
     *
     * @author becerraarmr
     * @param usuario - usuario quien solicita el cargue
     * @param perfil
     * @param part - Archivo con la información del cargue.
     */
    public CargueMasivoHhppManager(String usuario, int perfil, Part part) {
        this.part = part;
        this.usuario = usuario;
        this.perfil=perfil;
    }

    /**
     * Crear instancia para proceso automático Se crea la instancia para poder
     * realizar la tarea programada.
     */
    public CargueMasivoHhppManager() {
    }

    /**
     * Ejecutar el Thread
     * <p>
     * Pone en ejecución el Thread, solicitando la creación del archivo y
     * escribiendo el archivo.
     *
     * @author becerraarmr
     */
    @Override
    public void run() {
        synchronized (this) {
            archivoProcesado = false;//Inicia el proceso
            msgProceso = "Revisando el archivo";
            //Cargar el archivo xlsx
            if (cargaArchivo()) {
                if (!validarArchivoCargueAnteriores(fileName)) {
                    //Validar el archivo en la hoja 0 y en la fila 2
                    if (validaArchivo(0, 2)) {
                        msgProceso = "Revisando la data del archivo";
                        //Cargar la data del archivo
                        List<String[]> listData = cargarData();
                        if (listData != null) {
                            //Procesar la lista cargada 
                            procesarData(listData);
                        } else {
                            msgProceso = "No se pudo cargar los registros del archivo";
                        }
                    } else {
                        msgProceso = "Archivo no es válido";
                    }
                } else {
                    msgProceso = "Archivo " + fileName + " fue cargado anteriormente";
                }
            } else {
                msgProceso = "Archivo no se pudo cargar";
            }
        }
        //Termina el proceso
        archivoProcesado = true;
    }

    /**
     * Se procesa la data.
     *
     * Con el listado de datos que tiene la variable listData se procesa según
     * las establecidas. Teniendo en cuenta que solo se procesan la cantidad de
     * registros configurados,
     *
     * @author becerraarmr
     * @param listData listado con la data a procesar.
     */
    private void procesarData(List<String[]> listData) {
        try {
            countRowData = listData.size();
            msgProceso = "Total de filas encontradas:" + listData.size();
            if (countRowData > 0) {
                iniciarParametros();
                int i = countRowData < countMinProcess ? countRowData : countMinProcess;
                List<String[]> list1 = listData.subList(0, i);

                if (!cargarHeader()) {
                    msgProceso = "No se puede crear el archivo";
                    return;
                }

                msgProceso = "Preparando la respuesta";
                int j = 0;
                HhppCargueArchivoLog cargueArchivoLog = crearArchivoLogBD(j);

                for (int k = 0; k < list1.size(); k++) {
                    iniciarParametros();
                    Date timeInicial = new Date();//Capturar el tiempo de arranque del proceso
                    DetalleCargaHhppMgl procesarItem
                            = procesarData(list1.get(k), k + 3, DetalleCargaHhppMgl.PROCESADO_SINCAMBIO, null);
                    //Preparar la respuesta 
                    prepararRespuesta(procesarItem);
                    //Creo el historico de los registros
                    guardarRegistrosEnBd(procesarItem, list1.get(k), cargueArchivoLog);
                    //Capturo el tiempo de terminación de procesar cada registro
                    Date timeFinal = new Date();
                    long timeTotal = timeFinal.getTime() - timeInicial.getTime();

                    long franja = franjaTiempo - timeTotal;

                    if (franja > 0) {
                        try {
                            sleep(franja);
                        } catch (InterruptedException e) {
                            //si no puede detener un breve tiempo continua.
                            LOGGER.error("Interrupción en el sleep al procesar data.");
                        }
                    }
                    msgProceso = "Se ha procesado " + k
                            + " de " + list1.size() + ", para un total de registros "
                            + countRowData;
                    j++;
                }
                //notificar que ha terminado el proceso
                msgProceso = Constant.MSG_REPORTE_EXITOSO;

                //Cargar el log del archivo cargado
                cargueArchivoLog.setCantidadProcesada(new BigInteger("" + j));
                getCargeArchivoManager().update(cargueArchivoLog);

                //Enviar a archivo a TCRM
                //Autor bocanegra Vm
                if (!isHeaderInhabilitar && 
                        !tipoReporte.equalsIgnoreCase(Constant.CODIGO_BASICA_ETIQUETA)) {
                    enviarArchivoForTcrm(cargueArchivoLog);
                    enviarArchivoForEOC_BCSC(cargueArchivoLog);
                }
                //cargar los registros del archivo

                if (countRowData > countMinProcess) {
                    int registrosFaltantes = countRowData - countMinProcess;
                    procesoNocturno = true;
                    List<String[]> list2 = listData.subList(j, countRowData);
                    //filasProcesoNocturno(list2);

                    msgProceso = "El archivo tenía mas de "
                            + countMinProcess + " registros los "
                            + registrosFaltantes
                            + " faltantes se programaron para "
                            + Constant.MSG_REPORTE_NOCTURNO;
                    //activarTimer();
                }

            } else {
                msgProceso = "No se puede procesar.";
            }
        } catch (ApplicationException e) {
            msgProceso = "No se puede procesar la data cargada del archivo de Excel: "+e.getMessage();
            LOGGER.error(msgProceso, e);
        }
    }

    private void filasProcesoNocturno(List<String[]> list2) throws ApplicationException {
        try {
            HhppCargueArchivoLog cargueArchivoLog = crearArchivoLogBD(0);
            for (int k = 0; k < list2.size(); k++) {
                DetalleCargaHhppMgl procesarItem
                        = procesarItemNocturno(list2.get(k), k, DetalleCargaHhppMgl.PROCESO_NOCTURNO);
                guardarRegistrosEnBd(procesarItem, list2.get(k), cargueArchivoLog);
            }

            cargueArchivoLog.setEstado(Short.valueOf("0"));
            getCargeArchivoManager().update(cargueArchivoLog);

        } catch (ApplicationException | NumberFormatException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg, e);
        }
    }

    private DetalleCargaHhppMgl procesarItemNocturno(String[] item, int row, String estado) {

        if (item == null) {
            return null;
        }

        DetalleCargaHhppMgl detalleCarga = new DetalleCargaHhppMgl();
        detalleCarga.setEstado(estado);
        HhppMgl original = null;
        BigDecimal idHhpp = null;
        try {
            idHhpp = findBigDecimal(findValor(item, 0));
            if (idHhpp != null) {
                original = getHhppManager().findById(new BigDecimal(idHhpp.intValue()));
            }
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg, e);
            
            original = null;
        }

        StringBuilder dataOrg = datosConsultaFin(idHhpp);
        detalleCarga.setInfoOriginal(dataOrg.toString());
        detalleCarga.setHhppMgl(original);
        detalleCarga.setRow(row);
        return detalleCarga;
    }

  
    /**
     * Crear un registro en la tabla carguelogitem Intenta crear un registro en
     * la tabla carguelogitem según el archivo que tenga y el info que se va a
     * insertar
     *
     * @author becerraarmr
     * @param cargueArchivoLog archivo donde se encuentra el registro
     * @param info info a insertar
     * @return CargueArchivoLogItem creado o null
     * @throws ApplicationException problemas para crear el registro.
     */
    private CargueArchivoLogItem createCargeLog(
            HhppCargueArchivoLog cargueArchivoLog,
            String info) throws ApplicationException {
        CargueArchivoLogItemManager calim1 = new CargueArchivoLogItemManager();
        CargueArchivoLogItem entity = new CargueArchivoLogItem();
        entity.setIdArchivoLog(cargueArchivoLog.getIdArchivoLog());
        entity.setInfo(info);
        entity.setFechaRegistro(new Date());
        entity.setIdComplemento(BigDecimal.ZERO);
        return calim1.create(entity);
    }

    /**
     * Agrega los campos faltantes a la cabecera del archivo de excel.
     *
     * @author becerraarmr
     * @return true si cargó la cabecera y false en caso contrario.
     */
    private boolean cargarHeader() {
        if (isHeaderDirecciones) {
            for (String hd : headerGeoReferenciar()) {
                if (!addColumna(hd, 2)) {
                    return false;
                }
            }
        } else {
            for (String hd : headerRpta()) {
                if (!addColumna(hd, 2)) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Adherir columna Intenta adherir una columna a la hoja del archivo de
     * excel.
     *
     * @author becerraarmr
     * @param cellValue valor de la celda
     * @param itemRow item de la fila
     *
     * @return true si pudo adherirla y false en caso contrario.
     */
    private boolean addColumna(String cellValue, int itemRow) {
        if (workbook == null || cellValue == null) {
            return false;
        }
        try {

            Sheet sheet = ((XSSFWorkbook) workbook).getSheetAt(0);

            if (sheet == null) {
                return false;
            }
            Row row = sheet.getRow(itemRow);

            if (row == null) {
                return false;
            }
            Cell cell = row.createCell(row.getLastCellNum());
            cell.setCellValue(cellValue);
            return true;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg, e);
        }
        return false;
    }

    /**
     * Referenciar datos según parámetros
     * <p>
     * Se busca en la base de datos en la tabla parámetros los valores que
     * corresponden para procesar la carga
     * 
     * @throws ApplicationException
     * @author becerraarmr
     */
    protected void iniciarParametros() throws ApplicationException {
        try {
            final String mglCargueFranjaTiempo = "MGL_PROCESO_MASIVO_MODIFICACION";
            ParametrosMgl pm = getParametrosMglManager().findParametroMgl(mglCargueFranjaTiempo);

            if (pm != null) {
                String valor = pm.getParValor();
                if (valor != null) {
                    String[] items = valor.split("\\|");
                    for (String item : items) {
                        definirValor(item);
                    }
                }
            }
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw e;
        }
    }

    /**
     * Definir el valor.
     * <p>
     * Para definir el valor para la carga. Teniendo en cuenta que el parámetro
     * MGL_PROCESO_MASIVO_MODIFICACION contien en su campo VALOR la
     * configuración del cargue masivo de la siguiente forma
     * "TC=Milisigundos|CMP=entero|CMN=entero|HIN=hora|HFN=hora" TC: Tiempo de
     * ejecución en milisegundos. Ejemplo 4000 CMP: Cantidad Mínima de
     * procesamiento. Ejemplo 1500 CMN: Cantidad Mínima de procesamiento
     * nocturno. Ejemplo 4000 HIN: Hora de inicio de procesamiento nocturno
     * rango de 19:00 hasta 24:00. Ejemplo 19:00 HFN: Hora final de
     * procesamiento nocturno 19:00 hasta 24:00. Ejemplo 23:00
     *
     * @author becerraarmr
     * @param itemParametro valor a definir
     * @throws ApplicationException
     */
    protected void definirValor(String itemParametro) throws ApplicationException {
        try {

            if (itemParametro != null) {
                String[] item = itemParametro.split("=");
                if (item.length == 2) {
                    String var = item[0];
                    String varValor = item[1];
                    if (!isInteger(varValor)) {
                        return;
                    }
                    Integer valor = Integer.valueOf(varValor);
                    if ("TC".equalsIgnoreCase(var)) {
                        franjaTiempo = valor == null ? franjaTiempo : valor;
                    } else if ("CMP".equalsIgnoreCase(var)) {
                        countMinProcess = valor == null ? countMinProcess : valor;
                    } else if ("HIN".equalsIgnoreCase(var)) {
                        horaInicioProceso = Calendar.getInstance();
                        horaInicioProceso.set(Calendar.HOUR_OF_DAY, valor);
                        horaInicioProceso.set(Calendar.MINUTE, 0);
                        horaInicioProceso.set(Calendar.SECOND, 0);
                    } else if ("HFN".equalsIgnoreCase(var)) {
                        horaFinalProceso = Calendar.getInstance();
                        horaFinalProceso.set(Calendar.HOUR_OF_DAY, valor);
                        horaFinalProceso.set(Calendar.MINUTE, 0);
                        horaFinalProceso.set(Calendar.SECOND, 0);
                    }

                }
            }
        } catch (NumberFormatException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg,  e);
        }
    }

    /**
     * Verificar si es entero Verifica si el valor ingresado por parámetro es
     * entero
     *
     * @author becerraarmr
     * @param valor a evaluar
     *
     * @return true si es entero y false si no lo es
     */
    protected boolean isInteger(String valor) {
        try {
            Integer.valueOf(valor);
            return true;
        } catch (NumberFormatException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg, e);
            
            return false;
        }
    }

    /**
     * Crear el archivo en disco
     * <p>
     * Crear el archivo en disco y guarda la referencia en base de datos para su
     * posterior procesamiento según la tarea programada.
     *
     * @author becerraarmr
     * @param regProcesados cantidad de registros procesados.
     * @return HhppCargueArchivoLog o null
     * @throws ApplicationException
     */
    protected HhppCargueArchivoLog crearArchivoLog(int regProcesados) throws ApplicationException {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss-a");
            String fecha = sdf.format(new Date());
            fileNameProcesado = "CM" + fecha + ".xlsx";
            File file = new File("tmp", fileNameProcesado);
            FileOutputStream outputStream = new FileOutputStream(file);
            workbook.write(outputStream);
            outputStream.close();
            HhppCargueArchivoLog hhppCargueArchivoLog
                    = new HhppCargueArchivoLog();
            hhppCargueArchivoLog.setNombreArchivoCargue(fileNameProcesado);
            hhppCargueArchivoLog.setCantidadTotal(new BigInteger("" + countRowData));
            hhppCargueArchivoLog.setCantidadProcesada(new BigInteger("" + regProcesados));
            hhppCargueArchivoLog.setEstado(Short.valueOf("0"));
            Date fechaRegistro = new Date();
            hhppCargueArchivoLog.setFechaRegistro(fechaRegistro);
            hhppCargueArchivoLog.setFechaModificacion(fechaRegistro);
            hhppCargueArchivoLog.setUsuario(usuario);
            //Asignar el usuario correcto
            getCargeArchivoManager().create(hhppCargueArchivoLog);
            return hhppCargueArchivoLog;
        } catch (IOException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg,  e);
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw e;
        }
    }

    /**
     * Crear el archivo temporal
     * <p>
     * Crear el archivo en disco de forma temporal
     *
     * @author becerraarmr
     * @throws ApplicationException
     */
    protected void crearArchivoTemp() throws ApplicationException {
        try {
            SXSSFWorkbook workbookTemp = new SXSSFWorkbook((XSSFWorkbook) workbook);
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss-a");
            String fecha = sdf.format(new Date());
            fileNameProcesado = fileName;
            File file = new File("tmp", fileNameProcesado);
            FileOutputStream outputStream = new FileOutputStream(file);
            workbookTemp.write(outputStream);
            outputStream.close();
        } catch (IOException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg,  e);
        }
    }

    /**
     * Crear el archivo en disco
     * <p>
     * Crear el archivo en disco y guarda la referencia en base de datos para su
     * posterior procesamiento según la tarea programada.
     *
     * @author bocanegra vm
     * @param regProcesados cantidad de registros procesados.
     * @return HhppCargueArchivoLog o null
     * @throws ApplicationException
     */
    protected HhppCargueArchivoLog crearArchivoLogBD(int regProcesados) throws ApplicationException {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss-a");
            String fecha = sdf.format(new Date());

            fileNameProcesado = fileName;
            if (procesoNocturno) {
                String Nombre[] = fileNameProcesado.split("\\.");
                fileNameProcesado = Nombre[0] + "_";
                fileNameProcesado += "NOCTURNO";
                fileNameProcesado += fecha;
                fileNameProcesado += "." + Nombre[1];
                countRowData -= countMinProcess;
                procesoNocturno = false;
            }
            HhppCargueArchivoLog hhppCargueArchivoLog
                    = new HhppCargueArchivoLog();
            hhppCargueArchivoLog.setNombreArchivoCargue(fileNameProcesado);
            hhppCargueArchivoLog.setCantidadTotal(new BigInteger("" + countRowData));
            hhppCargueArchivoLog.setCantidadProcesada(new BigInteger("" + regProcesados));
            hhppCargueArchivoLog.setEstado(Short.valueOf("1"));
            Date fechaRegistro = new Date();
            hhppCargueArchivoLog.setFechaRegistro(fechaRegistro);
            hhppCargueArchivoLog.setFechaModificacion(fechaRegistro);
            hhppCargueArchivoLog.setUsuario(usuario);
            hhppCargueArchivoLog.setUtilizoRollback("N");
            switch (tipoReporte) {
                case Constant.CODIGO_BASICA_COBERTURA:
                    hhppCargueArchivoLog.setTipoMod(1);
                    break;
                case Constant.CODIGO_BASICA_ESTRATO:
                    hhppCargueArchivoLog.setTipoMod(2);
                    break;
                case Constant.CODIGO_BASICA_DIRECCION:
                    hhppCargueArchivoLog.setTipoMod(3);
                    break;
                case Constant.CODIGO_BASICA_TIPO_VIVIENDA:
                    hhppCargueArchivoLog.setTipoMod(4);
                    break;
                case Constant.CODIGO_BASICA_ETIQUETA:
                    hhppCargueArchivoLog.setTipoMod(5);
                    break;
                default:
                    break;
            }
            hhppCargueArchivoLog.setEnvioTcrm("N");
            //Asignar el usuario correcto
            hhppCargueArchivoLog = getCargeArchivoManager().create(hhppCargueArchivoLog);
            return hhppCargueArchivoLog;
        } catch (ApplicationException e) {
            msgProceso = Constant.MSG_REPORTE_NOMBRE_ARCHIVO;
            
            String msg = msgProceso + ": " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg,  e);
        }
    }

    /**
     * Cargar el archivo de excel
     * <p>
     * Se procede a cargar el archivo de excel en el objeto Workbook, siempre y
     * cuando sea de formato .xlsx
     *
     * @author becerraarmr
     * @return true si pudo cargar y false si no se pudo.
     */
    protected boolean cargaArchivo() {
        try {
            if (part != null) {
                InputStream inputStream = part.getInputStream();
                if (inputStream != null) {
                    fileName = findFileName();
                    if (fileName != null) {
                        int j = fileName.indexOf(".xlsx");
                        if (j != -1) {
                            workbook = new XSSFWorkbook(inputStream);
                            return true;
                        }
                    }
                }
            }
        } catch (IOException e) {
            msgProceso = "El archivo no se pudo cargar";
            LOGGER.error(msgProceso + ": " + e.getMessage(), e);
        }
        return false;
    }

    /**
     * Cargar la data
     * <p>
     * Se carga la data que trae el archivo a procesar, con el fin de
     * convertirlo en un listado de vectores tipo String.
     *
     * @author becerraarmr
     * @return null o un listado vectores de String[]
     */
    protected List<String[]> cargarData() {
        try {
            //Para buferear el archivo
            List<String[]> lista = new ArrayList<String[]>();
            int cantSheet = ((XSSFWorkbook) workbook).getNumberOfSheets();

            int i = 0;

            boolean filtrosFin = false;

            while (cantSheet > 0) {
                Sheet sheet = ((XSSFWorkbook) workbook).getSheetAt(i);
                if (sheet == null) {
                    break;
                }

                int rowCount = sheet.getLastRowNum();

                StringBuilder filtro = new StringBuilder();
                if (filtrosFin != true) {
                    for (int k = 0; k < 2; k++) {
                        Row row = sheet.getRow(k);
                        int cellRowCount = row.getLastCellNum();
                        for (int c = 0; c < cellRowCount; c++) {
                            Cell cell = row.getCell(c);
                            filtro.append(toStringCell(cell));
                            filtro.append("|");
                        }
                        filtro.append("/");
                    }
                    headerFiltros = filtro.toString();
                    filtrosFin = true;
                }

                for (int r = 2; r <= rowCount; r++) {//Leer la fila despues del encabezado
                    Row row = sheet.getRow(r);
                    int cellRowCount = row.getLastCellNum();
                    if (r > 2) {
                        cellRowCount = headerSel.length;
                    }
                    String[] data = new String[cellRowCount];
                    for (int c = 0; c < cellRowCount; c++) {
                        Cell cell = row.getCell(c);
                        if (r > 2) {
                            data[c] = toStringCell(cell).toUpperCase();
                        } else {
                            data[c] = toStringCell(cell);
                        }
                    }
                    if (r == 2) {
                        if (!isHeaderValida(data)) {//revisar la cabecera
                            msgProceso = "Los encabezados del archivo no "
                                    + "corresponden al reportado.";
                            return null;//La lectura del archivo es descartada
                        }
                    } else if (data != null) {
                        lista.add(data);
                    }
                }
                i++;//Aumenta el item hoja a revisar
                cantSheet--;//Reducir la cantidad de hojas
            }
            return lista;
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);

            return null;
        }
    }

    /**
     * Validar el archivo.
     * <p>
     * Valida el archivo cargado el cual debe tener en la fila 2 la cabecera
     * correspondiente
     *
     * @author becerraarmr
     * @param indexSheet indice de hoja, inicia en 0
     * @param rowItem item de fila
     *
     * @return true o false
     */
    protected boolean validaArchivo(int indexSheet, int rowItem) {
        try {
            Sheet sheet = ((XSSFWorkbook) workbook).getSheetAt(indexSheet);
            String[] dataEnc;
            if (sheet != null) {
                Row rowEnc = sheet.getRow(rowItem-1);//fila donde está el encabezado
                
                if (rowEnc != null) {
                    int cellRowCountEnc = rowEnc.getLastCellNum();
                    dataEnc = new String[cellRowCountEnc];
                    for (int c = 0; c < cellRowCountEnc; c++) {
                        Cell cell = rowEnc.getCell(c);
                        dataEnc[c] = toStringCell(cell);
                    }
                    atributoCargado=dataEnc[6];
                }
                
                 CmtTipoBasicaMgl tipoBasica
                        = tipoBasicaMglManager.findByCodigoInternoApp(Constant.BASICA_TIPO_ATRIBUTOS_HHPP);
                if (tipoBasica != null) {
                    atributosHhpp = getBasicaManager().findByTipoBasica(tipoBasica);
                }

                Row row = sheet.getRow(rowItem);//fila donde está la cabecera
                if (row != null) {
                    int cellRowCount = row.getLastCellNum();
                    String[] data = new String[cellRowCount];
                    for (int c = 0; c < cellRowCount; c++) {
                        Cell cell = row.getCell(c);
                        data[c] = toStringCell(cell);
                    }
                    if (isHeaderValida(data)) {//revisar la cabecera
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            msgProceso = "No se pudo validar el archivo";
            
            LOGGER.error(msgProceso + ": " + e.getMessage(), e);
        }
        return false;
    }

    /**
     * Mostrar el valor de la celda
     * <p>
     * Convertir el valor de la celda en String
     *
     * @author becerraarmr
     * @param cell celda a verificar
     *
     * @return un String con el valor de la celda
     */
    protected String toStringCell(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case (Cell.CELL_TYPE_BLANK): {
                return "";
            }
            case (Cell.CELL_TYPE_BOOLEAN): {
                return cell.getBooleanCellValue() + "";
            }
            case (Cell.CELL_TYPE_ERROR): {
                return "";
            }
            case (Cell.CELL_TYPE_FORMULA): {
                return "";
            }
            case (Cell.CELL_TYPE_NUMERIC): {
                Double valor = cell.getNumericCellValue();
                try {
                    return (new BigDecimal(valor)).intValue() + "";
                } catch (Exception e) {
                    String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                    LOGGER.error(msg, e);
                    
                    return "" + valor;
                }
            }
            case (Cell.CELL_TYPE_STRING): {
                return cell.getStringCellValue();
            }
        }
        return "";
    }

    /**
     * Verificar si es BigDecimal
     * <p>
     * Verifica que el valor que llega es un número.
     *
     * @author becerraarmr
     * @param valor valor String a evaluar
     *
     * @return resultado Ineger o null de la validación.
     */
    public BigDecimal valorBigDecimal(String valor) {
        try {
            return new BigDecimal(valor);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg, e);

            return null;
        }
    }

    /**
     * Procesar registro del cargue Se intenta procesar la data contenida en el
     * item.
     *
     * @author becerraarmr
     * @param item arreglo con la data a procesar
     * @param row valor de la fila
     * @param estado estado del detalle de la carga
     * @param infoOriginalNocturno
     *
     * @return DetalleCargaHhppMgl con la información deseada
     */
    public DetalleCargaHhppMgl procesarData(String[] item, int row, String estado, String infoOriginalNocturno) {
        if (item == null) {
            return null;
        }
        datosModificados = new StringBuilder();
        DetalleCargaHhppMgl detalleCarga = new DetalleCargaHhppMgl();
        detalleCarga.setEstado(estado);
        HhppMgl original = null;
        BigDecimal idHhpp = null;
        try {
            idHhpp = findBigDecimal(findValor(item, 0));
            if (idHhpp != null) {
                original = getHhppManager().findById(new BigDecimal(idHhpp.intValue()));
            }
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg, e);
            
            original = null;
        }

        if (original == null) {
            detalleCarga.addDetalle("Hhpp no fue encontrado en la base de datos.", DetalleCargaHhppMgl.DETALLEWARNING);
            detalleCarga.setFechaProcesamiento(new Date());
            detalleCarga.setRow(row);
            detalleCarga.setEstado(DetalleCargaHhppMgl.NOPROCESADO);
            detalleCarga.setInfoOriginal("Data no encontrada en el sistema");
            return detalleCarga;
        }
        StringBuilder dataOrg;
        if (!DetalleCargaHhppMgl.PROCESO_NOCTURNO.equalsIgnoreCase(detalleCarga.getEstado())) {
            dataOrg = datosConsultaFin(idHhpp);
            if (dataOrg != null && !dataOrg.toString().isEmpty()) {
                detalleCarga.setInfoOriginal(dataOrg.toString());
            } else {
                detalleCarga.addDetalle("Hhpp no fue encontrado en la base de datos o data incompleta.", DetalleCargaHhppMgl.DETALLEWARNING);
                detalleCarga.setFechaProcesamiento(new Date());
                detalleCarga.setRow(row);
                detalleCarga.setEstado(DetalleCargaHhppMgl.NOPROCESADO);
                detalleCarga.setInfoOriginal("Data no encontrada en el sistema");
                return detalleCarga;
            }

        } else {
            detalleCarga.setInfoOriginal(infoOriginalNocturno);
        }

        detalleCarga.setHhppMgl(original);
        if (headerSel.length != item.length) {
            detalleCarga.addDetalle("Data incompleta. ", DetalleCargaHhppMgl.DETALLEWARNING);
            detalleCarga.setEstado(DetalleCargaHhppMgl.NOPROCESADO);
        }

        if (isHeaderInhabilitar) {//Procede a inhabilitar el registro
            try {
                inhabilitarHhpp(detalleCarga, item);
                if (detalleCarga.getDetalle() == null) {
                    if (DetalleCargaHhppMgl.PROCESO_NOCTURNO.equalsIgnoreCase(detalleCarga.getEstado())) {
                        detalleCarga.setEstado(DetalleCargaHhppMgl.PROCESO_NOCTURNO_SINCAMBIO);
                    } else {
                        detalleCarga.setEstado(DetalleCargaHhppMgl.PROCESADO_SINCAMBIO);
                    }
                }
            } catch (ApplicationException e) {
                String msg = "No se pudo inhabilitar el Hhpp: " + e.getMessage();
                LOGGER.error(msg, e);
                
                detalleCarga.addDetalle(msg, DetalleCargaHhppMgl.DETALLEWARNING);
                detalleCarga.setEstado(DetalleCargaHhppMgl.NOPROCESADO);
            } catch (Exception ex) {
                String msg = "No se pudo inhabilitar el Hhpp: " + ex.getMessage();
                LOGGER.error(msg, ex);
                
                detalleCarga.addDetalle(msg, DetalleCargaHhppMgl.DETALLEWARNING);
                detalleCarga.setEstado(DetalleCargaHhppMgl.NOPROCESADO);
            }
        } else if (isHeaderDirGeo) {//Validar georefernciada
            try {
                validarDireccionGeoreferenciada(detalleCarga, item);
                if (detalleCarga.getDetalle() == null) {
                    if (DetalleCargaHhppMgl.PROCESO_NOCTURNO.equalsIgnoreCase(detalleCarga.getEstado())) {
                        detalleCarga.setEstado(DetalleCargaHhppMgl.PROCESO_NOCTURNO_SINCAMBIO);
                    } else {
                        detalleCarga.setEstado(DetalleCargaHhppMgl.PROCESADO_SINCAMBIO);
                    }
                }
            } catch (ApplicationException e) {
                String msg = "No se pudo validar la direccion. " + e.getMessage();
                LOGGER.error(msg, e);
                
                detalleCarga.addDetalle(msg, DetalleCargaHhppMgl.DETALLEWARNING);
                detalleCarga.setEstado(DetalleCargaHhppMgl.NOPROCESADO);
            }
        } else if (isHeaderDirecciones) {//Validar Direcciones
            try { 
                findHhppbyDirAndSubDir(detalleCarga.getHhppMgl().getDireccionObj(),detalleCarga.getHhppMgl().getSubDireccionObj());
                
              DetalleCargaHhppMgl validarDir =  validarDireccion(detalleCarga, item);
                
                if (validarDir != null && validarDir.isDireccionValidada()) {
                    actualizarDirAndSubdirHhpp(detalleCarga.getHhppMgl());
                }
                
                if (detalleCarga.getDetalle() == null) {
                    if (DetalleCargaHhppMgl.PROCESO_NOCTURNO.equalsIgnoreCase(detalleCarga.getEstado())) {
                        detalleCarga.setEstado(DetalleCargaHhppMgl.PROCESO_NOCTURNO_SINCAMBIO);
                    } else {
                        detalleCarga.setEstado(DetalleCargaHhppMgl.PROCESADO_SINCAMBIO);
                    }
                }
            } catch (ApplicationException e) {
                String msg = "No se pudo validar la dirección: " + e.getMessage();
                LOGGER.error(msg, e);
                
                detalleCarga.addDetalle(msg, DetalleCargaHhppMgl.DETALLEWARNING);
                detalleCarga.setEstado(DetalleCargaHhppMgl.NOPROCESADO);
            }
        } else if (!tipoReporte.isEmpty()) { //valida los diferentes tipos de detallado, estado, estrato, nivel_soc 
            for (int i = 0; i < item.length; i++) {
                procesarItemDataDetallados(detalleCarga, i, item);
            }
        } else {//Validadr hhpp normal
            for (int i = 0; i < item.length; i++) {
                procesarItemData(detalleCarga, i, item);
            }
        }
        if (!detalleCarga.puedeActualizar() && !tipoReporte.equals(Constant.CODIGO_BASICA_COBERTURA) ) {
            try {
                if (detalleCarga.getDetalle() == null) {
                    if (DetalleCargaHhppMgl.PROCESO_NOCTURNO.equalsIgnoreCase(detalleCarga.getEstado())) {
                        detalleCarga.setEstado(DetalleCargaHhppMgl.PROCESO_NOCTURNO_SINCAMBIO);
                    } else {
                        detalleCarga.setEstado(DetalleCargaHhppMgl.PROCESADO_SINCAMBIO);
                    }
                } else {
                    getHhppManager().update(detalleCarga.getHhppMgl());                    
                }
            } catch (ApplicationException e) {
                String msg = "No se pudo actualizar Hhpp en base de datos: " + e.getMessage();
                LOGGER.error(msg, e);
                
                detalleCarga.setDetalle(msg);
                detalleCarga.setEstado(DetalleCargaHhppMgl.NOPROCESADO);
            }
        }

        detalleCarga.setFechaProcesamiento(new Date());
        detalleCarga.setRow(row);
        return detalleCarga;
    }

    /**
     * Se actualizan las direcciones y subdirecciones de los demás hhpps ya 
     * encontrados con la misma direccion
     *
     * @author Jonathan Peña
     * @param dir
     * @param subDir
     *
     * @throws ApplicationException  
     */
    public void findHhppbyDirAndSubDir(DireccionMgl dir, SubDireccionMgl subDir ) throws ApplicationException{
        listaHhppActualizar = hhppManager.findByDirAndSubDir(dir, subDir);
    
    }
    /**
     * Se actualizan las direcciones y subdirecciones de los demás hhpps ya 
     * encontrados con la misma direccion
     *
     * @author Jonathan Peña
     * @param hhppModificado
     *
     * @throws ApplicationException  
     */
    public void actualizarDirAndSubdirHhpp(HhppMgl hhppModificado) throws ApplicationException {
        try {
            if (listaHhppActualizar != null && !listaHhppActualizar.isEmpty()) {

                //Valida si RR se encuentra encendido o apagado para realizar las operaciones en RR
            boolean habilitarRR = false;
            ParametrosMgl parametroHabilitarRR = getParametrosMglManager().findParametroMgl(Constant.HABILITAR_RR);
            if (parametroHabilitarRR != null && parametroHabilitarRR.getParValor().equalsIgnoreCase(Constant.RR_HABILITADO)) {
                habilitarRR = true;
            }
                DireccionMgl dir = hhppModificado.getDireccionObj();
                SubDireccionMgl subDir = hhppModificado.getSubDireccionObj();
                String houseNumberNew = hhppModificado.getHhpPlaca();
                String streetNameNew = hhppModificado.getHhpCalle();
                String apartmentNumberNew = hhppModificado.getHhpApart();
                
                for (HhppMgl hhpp : listaHhppActualizar) {

                    DrDireccion dirOriginal = managerDetalleDirs.find(hhpp);
                    
                    //Si RR se encuentra encendido, va y realiza el cambio a RR
                    if (habilitarRR) {
                        HhppResponseRR hhppResponseRR;
                        //Consume servicio que busca hhpp por Id de RR
                        hhppResponseRR = direccionRRManager.getHhppByIdRR(hhpp.getHhpIdrR());

                        if (hhppResponseRR != null && hhppResponseRR.getTipoMensaje().equalsIgnoreCase("I")
                                && hhppResponseRR.getComunidad() != null) {
                            direccionRRManager.cambiarDirHHPPRR_Masiva(hhppResponseRR.getComunidad(), hhppResponseRR.getDivision(),
                                    hhppResponseRR.getHouse(), hhppResponseRR.getStreet(), hhppResponseRR.getApartamento(),
                                    hhppResponseRR.getComunidad(),
                                    hhppResponseRR.getDivision(), houseNumberNew, streetNameNew, apartmentNumberNew,
                                    dirOriginal.getIdTipoDireccion(), hhpp.getNodId().getComId(), hhppModificado.getHhpEdificio());
                        }
                    }
                    
                    hhpp.setBarrioHhpp(hhppModificado.getBarrioHhpp());
                    hhpp.setHhpPlaca(hhppModificado.getHhpPlaca());
                    hhpp.setHhpApart(hhppModificado.getHhpApart());
                    hhpp.setHhpCalle(hhppModificado.getHhpCalle());
                    hhpp.setHhpEdificio(hhppModificado.getHhpEdificio());
                    hhpp.setDireccionObj(dir);
                    hhpp.setSubDireccionObj(subDir);
                    
                    hhppManager.update(hhpp);
                    
                }
                CmtDireccionDetalleMglManager direccionDetalleMglManager = new CmtDireccionDetalleMglManager();
                CmtDireccionDetalladaMgl detalladaMgl = direccionDetalleMglManager.findByHhPP(hhppModificado);

                datosModificados.append(detalladaMgl != null ? detalladaMgl.getDireccionDetalladaId().toString() : "NA");
            }

        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg, e);
        }
        listaHhppActualizar = null;
    }

    /**
     * Procesar la dirección según el hhpp Se procesar la dirección, el estrato
     * o el nivel socio económico para el hhpp correspondiente, solicitando
     * actualización si es necesario.
     *
     * @author becerraarmr
     * @param hhpId id del hhpp a tratar
     * @param estrato valor del estrato
     * @param niv_socio valor del nivel socio económico
     *
     * @throws ApplicationException si hay alguna excepción la realizar la
     * petición de actualización.
     */
    private DetalleCargaHhppMgl actualizarDireccion(DetalleCargaHhppMgl detalle,
            String valor, int tipoDir)
            throws ApplicationException {
        if (detalle == null || detalle.getHhppMgl() == null || detalle.getHhppMgl().getDireccionObj() == null || valor == null || valor.isEmpty()) {
            return detalle;
        }

        final int ESTRATO = 1;
        final int NIVELSOCIO = 2;

        switch (tipoDir) {
            case ESTRATO: {
                BigDecimal estratoBigDecimal = findBigDecimal(valor);
                if (estratoBigDecimal != null) {
                    BigDecimal estratoOriginal = detalle.getHhppMgl().getDireccionObj().getDirEstrato();
                    estratoOriginal = estratoOriginal == null ? new BigDecimal("-1") : estratoOriginal;
                    if (estratoBigDecimal.compareTo(estratoOriginal) != 0) {
                        String msg = "Se cambió estrato:" + detalle.getHhppMgl().getDireccionObj().getDirEstrato()
                                + " por estrato:" + estratoBigDecimal.intValue();
                        detalle.getHhppMgl().getDireccionObj().setDirEstrato(estratoBigDecimal);
                        detalle.addDetalle(msg, DetalleCargaHhppMgl.DETALLEINFO);
                    }
                }
                break;
            }
            case NIVELSOCIO: {
                BigDecimal niv_socioBigDecimal = findBigDecimal(valor);
                if (niv_socioBigDecimal != null) {
                    BigDecimal nivelSocioOriginal = detalle.getHhppMgl().getDireccionObj().getDirNivelSocioecono();
                    nivelSocioOriginal = nivelSocioOriginal == null ? new BigDecimal("-1") : nivelSocioOriginal;
                    if (niv_socioBigDecimal.compareTo(nivelSocioOriginal) != 0) {
                        String msg = " Se cambió Nivel Socioeconómico:" + detalle.getHhppMgl().getDireccionObj().getDirNivelSocioecono()
                                + " por Nivel Socioeconómico:" + niv_socioBigDecimal.intValue();
                        detalle.getHhppMgl().getDireccionObj().setDirNivelSocioecono(niv_socioBigDecimal);
                        detalle.addDetalle(msg, DetalleCargaHhppMgl.DETALLEINFO);
                    }
                }
                break;
            }
        }
        return detalle;
    }

    /**
     * Guardar la nota del registro Se guarda la nota que contiene el registro
     * para el hhpp correspondiente.
     *
     * @author becerraarmr
     * @param hhpId id del hhpp
     * @param notas valor de la nota a insertar.
     *
     * @throws ApplicationException si hay algún error en el procesamiento.
     */
    private DetalleCargaHhppMgl guardarNotas(DetalleCargaHhppMgl detalle,
            String notas) throws ApplicationException {
        if (detalle == null || detalle.getHhppMgl() == null) {
            return null;
        }
        if (!notas.isEmpty()) {
            NotasAdicionalesMgl notasMgl = new NotasAdicionalesMgl();
            notasMgl.setHhppId(detalle.getHhppMgl().getHhpId() + "");
            notasMgl.setNota(notas);
            try {
                getNotasAdicionalesManager().create(notasMgl);
                detalle.addDetalle("Notas fueron Guardadas ", DetalleCargaHhppMgl.DETALLEINFO);
            } catch (ApplicationException e) {
                String msg = "Error al guardar notas: " + e.getMessage();
                LOGGER.error(msg, e);
                
                detalle.addDetalle(msg, DetalleCargaHhppMgl.DETALLEWARNING);
                detalle.setEstado(DetalleCargaHhppMgl.NOPROCESADO);
            }
        }
        return detalle;
    }

    /**
     * Validar la direccion del item. Se comprueba que la dirección sea válida y
     * diferente a la que existe.
     *
     * @author becerraarmr
     * @param hhppMgl hhpp a verificar
     * @param item item con la data nueva de direccion
     *
     * @return null si no pudo validar o un String con el itemDetalle si pudo
     * validar
     */
    public DetalleCargaHhppMgl validarDireccion(DetalleCargaHhppMgl detalle, String[] item)
            throws ApplicationException {
        
        DetalleCargaHhppMgl direccionValidada = new DetalleCargaHhppMgl();
        
        if (detalle == null || detalle.getHhppMgl() == null) {           
            direccionValidada.setDetalle("hhpp no válido");
            direccionValidada.setDireccionValidada(false);
            return direccionValidada;
        }

        RequestConstruccionDireccion rcd = prepareRequestDireccion(detalle, item);

        if (rcd == null) {            
            direccionValidada.setDetalle("Esta dirección no fue posible georeferenciar.");
            direccionValidada.setDireccionValidada(false);
            return direccionValidada;
        }
        rcd.getDrDireccion().setId(detalle.getHhppMgl().getHhpId());

        DireccionesValidacionCargueManager dvm = new DireccionesValidacionCargueManager();
        try {
            CityEntity ce = dvm.validaDireccion(rcd, false, detalle);

            if (rcd.getDrDireccion() != null && rcd.getDrDireccion().getIdTipoDireccion().equalsIgnoreCase("BM")
                    || rcd.getDrDireccion().getIdTipoDireccion().equalsIgnoreCase("IT")) {
                DrDireccionManager adminDireccion = new DrDireccionManager();
                String direccionTexto = adminDireccion.getDireccion(rcd.getDrDireccion());
                ce.setDireccion(direccionTexto);
            }

            //JDHT
            //Se agrega barrio al hhpp si se encuentra vacio y si el geo devuelve barrio
            if (rcd.getDrDireccion() != null
                    && rcd.getDrDireccion().getBarrio() != null
                    && !rcd.getDrDireccion().getBarrio().isEmpty()) {

                if (detalle.getHhppMgl() != null && (detalle.getHhppMgl().getBarrioHhpp() == null
                        || detalle.getHhppMgl().getBarrioHhpp().isEmpty())) {
                    detalle.getHhppMgl().setBarrioHhpp(rcd.getDrDireccion().getBarrio());
                }
            }
            detalle.setCityEntity(ce);
            detalle.addDetalle("Dirección ha sido georeferenciada", DetalleCargaHhppMgl.DETALLEINFO);
            direccionValidada.setDetalle("Dirección ha sido georeferenciada.");
            direccionValidada.setDireccionValidada(true);
            return direccionValidada;
        } catch (Exception e) {
            String msg = "Esta dirección no se pudo georeferenciar: " + e.getMessage();
            LOGGER.error(msg);          
            direccionValidada.setDetalle(msg);
            direccionValidada.setDireccionValidada(false);
            return direccionValidada;
        }
    }

    /**
     * Validar la direccion georeferenciada del item. Se comprueba que la
     * dirección sea válida y diferente a la que existe. Se valida si puede
     * actualizarse o crearse, teniendo en cuenta si el usuario seleccionó para
     * cambiar.
     *
     * @author becerraarmr
     * @param hhppMgl hhpp a verificar
     * @param item item con la data nueva de direccion
     *
     * @return null si no pudo validar o un String con el itemDetalle si pudo
     * validar
     */
    private void validarDireccionGeoreferenciada(DetalleCargaHhppMgl detalle, String[] item)
            throws ApplicationException {

        if (item[item.length - 1].equalsIgnoreCase("false")) {
            return;
        }

        BigDecimal centroPoblado;
        HhppMgl hhppMglVal = detalle.getHhppMgl();
        try {
            centroPoblado = hhppMglVal.getDireccionObj().getUbicacion().getGpoIdObj().getGpoId();
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);

            centroPoblado = null;
        }
        CmtDireccionDetalleMglManager manager = new CmtDireccionDetalleMglManager();

        //Validando la dirección.
        RequestConstruccionDireccion rcd = prepareRequestDireccion(detalle, item);

        if (rcd == null) {//vacia no valide
            return;
        }
        //Buscar la dirección
        CmtDireccionDetalladaMgl direccionDetalle = manager.find(rcd.getDrDireccion(), centroPoblado);

        if (direccionDetalle == null) {
            GeograficoPoliticoManager gpm = new GeograficoPoliticoManager();
            GeograficoPoliticoMgl gp = gpm.findById(hhppMglVal.getDireccionObj().getUbicacion().getGpoIdObj().getGeoGpoId());
            manager.create(rcd.getDrDireccion(), gp);
            direccionDetalle = manager.find(rcd.getDrDireccion(), centroPoblado);
        }

        if (direccionDetalle == null) {
            throw new ApplicationException("La dirección no se pudo cambiar");
        }

        hhppMglVal.setDireccionObj(direccionDetalle.getDireccion());
        hhppMglVal.setSubDireccionObj(direccionDetalle.getSubDireccion());

        try {
            (new HhppMglManager()).update(hhppMglVal);
            detalle.addDetalle("El hhpp ha cambiado de dirección.", DetalleCargaHhppMgl.DETALLEINFO);
        } catch (ApplicationException e) {
            String msg = "La direccion no se pudo referenciar con el hhpp: " + e.getMessage();
            LOGGER.error(msg);
            
            throw new ApplicationException(msg, e);
        }
    }

    /**
     * Intenta Inhabilitar el Item. Se comprueba que la dirección sea válida y
     * diferente a la que existe. Se valida si puede inhabilitar.
     *
     * @author Victor Bocanegra
     * @param hhppMgl hhpp a verificar
     * @param item item con la data del hhpp
     *
     */
    private void inhabilitarHhpp(DetalleCargaHhppMgl detalle, String[] item)
            throws ApplicationException {

    
        String estadoOt = findValor(item, 23);

        String inhabilitar = findValor(item, 24);

        HhppMgl hhppMglOr = detalle.getHhppMgl();//Original

        try {
            //JDHT            
            CmtBasicaMglManager manager = new CmtBasicaMglManager();
            CmtBasicaMgl estPo = manager.findByCodigoInternoApp(Constant.BASICA_EST_HHPP_TIPO_POTENCIAL);

            boolean habilitarRR = false;
            ParametrosMgl parametroHabilitarRR = getParametrosMglManager().findParametroMgl(co.com.claro.mgl.utils.Constant.HABILITAR_RR);
            if (parametroHabilitarRR != null && parametroHabilitarRR.getParValor().equalsIgnoreCase("1")) {
                habilitarRR = true;
            }
            if (estPo.getCodigoBasica().equalsIgnoreCase(hhppMglOr.getHhpEstadoUnit())
                    && hhppMglOr.getEstadoRegistro() == 1 && !estadoOt.equalsIgnoreCase("ABIERTO")) {

                if (inhabilitar != null && !inhabilitar.isEmpty() && inhabilitar.equalsIgnoreCase("S")) {
                    //Puede inhabilitar
                    String comunidad;
                    String division;
                    String placa;
                    String calle;
                    String apartamento;

                    if (habilitarRR) {
                        if (hhppMglOr.getHhpIdrR() != null) {
                            //Consultamos la informacion real del hhpp en RR
                            HhppResponseRR responseHhppRR = direccionRRManager.getHhppByIdRR(hhppMglOr.getHhpIdrR());
                            if (responseHhppRR.getTipoMensaje() != null
                                    && responseHhppRR.getTipoMensaje().equalsIgnoreCase("I")) {
                                comunidad = responseHhppRR.getComunidad();
                                division = responseHhppRR.getDivision();
                                calle = responseHhppRR.getStreet();
                                placa = responseHhppRR.getHouse();
                                apartamento = responseHhppRR.getApartamento();

                                // se consulta nodo NFI para la comunidad del nodo del hhppp
                                NodoMgl nodoNFI = null;
                                String codigoNFINodo;
                                if (hhppMglOr.getNodId() != null && hhppMglOr.getNodId().getComId() != null) {
                                    CmtComunidadRr comunidadRr = hhppMglOr.getNodId().getComId();
                                    nodoNFI = getNodoManager().findNodoNFIByComunidad(comunidadRr, Constant.NODO_NFI_COD_EQ);
                                }

                                if (nodoNFI != null) {
                                    codigoNFINodo = nodoNFI.getNodCodigo();
                                } else {
                                    codigoNFINodo = Constant.NODO_NFI_COD_EQ;
                                }
                                //Se manda a inactivar en RR si el hhpp tiene id RR y tiene la info de comunidad y division
                                DireccionRREntity result = direccionRRManager.inactivarHHPPRR(placa, calle,
                                        apartamento,
                                        comunidad,
                                        division,
                                        codigoNFINodo, "Proceso Masivo", "MGL User: Proceso Masivo");
                                LOGGER.error("Inactivado Hhpp con id: " + hhppMglOr.getHhpId() + " en RR");
                                if (result.isResptRegistroHHPPRR()) {

                                    //Inhabilitamos en MGL
                                    hhppMglOr.setEstadoRegistro(0);
                                    detalle.setHhppMgl(hhppMglOr);
                                    getHhppManager().update(hhppMglOr);
                                    detalle.addDetalle("El hhpp fue inactivado de forma correcta", DetalleCargaHhppMgl.DETALLEINFO);
                                    detalle.setEstado(DetalleCargaHhppMgl.PROCESADO);

                                    //Asignamos Marcas
                                    MarcasHhppMglManager marcasManager = new MarcasHhppMglManager();
                                    MarcasMgl HPI = marcasManager.findMarcasMglByCodigo("HPI");
                                    //Se asocia la marca en MGL
                                    //Creacion RR                                
                                    List<MarcasMgl> listaMarcasMgl = new ArrayList<>();
                                    //Agrega la nueva marca a la lista en la primera posicion (Marca que quedara como UniteProblemCode Actual)
                                    listaMarcasMgl.add(HPI);
                                    //Se agregan las marcas
                                    try {
                                        CmtDefaultBasicResponse response = getHhppManager().agregarMarcasHhpp(hhppMglOr, listaMarcasMgl, "MER_USER");
                                        if (!response.getMessageType().equalsIgnoreCase("E")) {
                                            LOGGER.error("Se realizo actualización de marcas en RR ");
                                        }

                                    } catch (Exception e) {
                                       LOGGER.error("Se produjo error al realizar la actualización de marcas en RR: " + e.getMessage());
                                       detalle.addDetalle("Se produjo error al realizar la actualización de marcas en RR: " + e.getMessage(), DetalleCargaHhppMgl.DETALLEINFO); 
                                    }
                                } else {
                                    detalle.addDetalle("Error al momento de inactivar el HHPP en RR: " +result.getMensaje()+ "", DetalleCargaHhppMgl.DETALLEINFO);
                                }
                            } else {
                                LOGGER.error("No existe informacion del Hhpp en RR. Se inhabilita solo en MGL");
                                //Inhabilitamos en MGL
                                hhppMglOr.setEstadoRegistro(0);
                                detalle.setHhppMgl(hhppMglOr);
                                getHhppManager().update(hhppMglOr);
                                detalle.addDetalle("El hhpp fue inactivado de forma correcta", DetalleCargaHhppMgl.DETALLEINFO);
                                detalle.setEstado(DetalleCargaHhppMgl.PROCESADO);
                            }
                        } else {
                            //No hay id rr se inhabilita solo en MGL
                            //Inhabilitamos en MGL
                            hhppMglOr.setEstadoRegistro(0);
                            detalle.setHhppMgl(hhppMglOr);
                            getHhppManager().update(hhppMglOr);
                            detalle.addDetalle("El hhpp fue inactivado de forma correcta", DetalleCargaHhppMgl.DETALLEINFO);
                            detalle.setEstado(DetalleCargaHhppMgl.PROCESADO);
                        }
                    } else {
                        //Inhabilitamos en MGL
                        hhppMglOr.setEstadoRegistro(0);
                        detalle.setHhppMgl(hhppMglOr);
                        getHhppManager().update(hhppMglOr);
                        detalle.addDetalle("El hhpp fue inactivado de forma correcta", DetalleCargaHhppMgl.DETALLEINFO);
                        detalle.setEstado(DetalleCargaHhppMgl.PROCESADO);
                    }
                } else {
                    detalle.addDetalle("No marco el registro con 'S' para inhabilitar", DetalleCargaHhppMgl.DETALLEINFO);
                }
            } else {
                detalle.addDetalle("El registro no cumple las condiciones para inhactivar", DetalleCargaHhppMgl.DETALLEINFO);
            }
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);

            throw new ApplicationException(msg, e);
        }
    }

    /**
     * Convertir un vector String a un objeto DrDireccion
     *
     * Teniendo en cuenta que un archivo de excel para validar direcciones tiene
     * una cabecera la cual consta de 63 columnas, se evalua del indice 21 hasta
     * el 62.
     *
     * @author becerraarmr
     * @param item vector String con la data a convertir a DrDireccion
     * @return un null o un DrDirección según cumpla las condiciones.
     *
     * @throws ApplicationException si hay un error al validar el
     * idtipodirección el cual debe ser CK,BM o IT.
     */
    private DrDireccion converter(String[] item, DetalleCargaHhppMgl detalle) throws ApplicationException {
        DrDireccion drDireccion = new DrDireccion();
        boolean sinData = true;

        for (int i = 4; i < item.length; i++) {
            //Obtener el valor de la celda
            Object valor = item[i];
            if (!"".equalsIgnoreCase((String) valor)) {
                addValor(drDireccion, valor, i, detalle);
                sinData = false;
                if (i == 5) {
                    String idTipoDireccion = (String) valor;
                    //Es tipo de direccion CK, BM o IT
                    if (!isTipoDireccionCKoBMoIT(idTipoDireccion)) {
                        throw new ApplicationException("Dirección no válida, "
                                + "idTipoDirección no es ni CK, BM o IT");
                    }
                }
            }
        }
        if (sinData) {
            return null;
        }
        return drDireccion;
    }

    /**
     * Procesa la direccion del item. Se comprueba que la dirección sea válida y
     * diferente a la que existe.
     *
     * @author becerraarmr
     * @param hhppMgl hhpp que se va a procesar la dirección
     * @param item item con la data nueva de direccion
     *
     * @return null o un objeto ResponseConstruccionDireccion con la dirección
     * válida,
     */
    private RequestConstruccionDireccion prepareRequestDireccion(
            DetalleCargaHhppMgl detalle, String[] item) throws ApplicationException {
        
        try {
            
      

        //Setear la data  a DrDireccion
        DrDireccion dirNueva = converter(item, detalle);

        if (dirNueva == null) {
            return null;
        }

        CmtDireccionDetalleMglManager manager = new CmtDireccionDetalleMglManager();

        DrDireccion dirOriginal = manager.find(detalle.getHhppMgl());
        //preparando para validar la dirección

        String idTipoDireccion = dirNueva.getIdTipoDireccion();

        if (validarDireccionesSinCambios(dirOriginal, dirNueva, item)) {
            return null;
        }

        BigDecimal ciudadCentroPoblado;

        hhppMgl = detalle.getHhppMgl();

        try {
            ciudadCentroPoblado = hhppMgl.getDireccionObj().getUbicacion().getGpoIdObj().getGeoGpoId();
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg, e);
            
            ciudadCentroPoblado = null;
        }
        RequestConstruccionDireccion rcc = new RequestConstruccionDireccion();
        
        Usuarios usuarios;
        UsuariosManager usuariosManager = new UsuariosManager();

        usuarios = usuariosManager.findUsuarioByUsuario(usuario);

        if (usuarios != null && usuarios.getIdUsuario() != null) {
            rcc.setIdUsuario(usuarios.getIdUsuario() + "");
        }
        
        //TODO: obtener centropoblado completo y establecer el valor del codigo dane en request.comunidad

        rcc.setBarrio(dirNueva.getBarrio());
        rcc.setComunidad(hhppMgl.getHhpComunidad());

        DrDireccionManager drDireccionManager = new DrDireccionManager();

        ResponseConstruccionDireccion rcd = null;
        if ("CK".equalsIgnoreCase(idTipoDireccion)) {
            rcc.setDrDireccion(dirNueva);
            rcc.getDrDireccion().setId(hhppMgl.getHhpId());
            String direccionStr = drDireccionManager.getDireccion(dirNueva);
            rcc.setTipoAdicion("N");
            rcc.setDireccionStr(direccionStr);
            rcd = drDireccionManager.construirDireccionSolicitud(rcc);
        } else if ("BM".equalsIgnoreCase(idTipoDireccion)
                || "IT".equalsIgnoreCase(idTipoDireccion)) {
            rcc.setTipoAdicion("P");
            DrDireccion drDirBM = new DrDireccion();
            drDirBM.setIdTipoDireccion(idTipoDireccion);
            rcc.setDrDireccion(drDirBM);
            //Agregar niveles de manzana o intransferible
            for (int i = 33; i <= 37; i++) {

                Object tipoNivel = item[i];
                if (!"".equalsIgnoreCase((String) tipoNivel)) {
                    Object valorNivel = item[i + 5];
                    if (!"".equalsIgnoreCase((String) valorNivel)) {
                        rcc.setTipoNivel((String) tipoNivel);
                        rcc.setValorNivel((String) valorNivel);
                        if (rcd != null && rcd.getDrDireccion() != null) {
                            rcc.setDrDireccion(rcd.getDrDireccion());
                        }
                        try {
                            rcd = drDireccionManager.construirDireccionSolicitud(rcc);
                        } catch (ApplicationException e) {
                            //continue
                            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                            LOGGER.error(msg);
                        }
                    }
                }
//                }
            }
        }
        //Agregar apartamento
        for (int i = 21; i <= 24; i++) {
            if (rcd != null) {
                Object tipoNivel = item[i];
                if (!"".equalsIgnoreCase((String) tipoNivel)) {
                    Object valorNivel = item[i + 6];
                    if (!"".equalsIgnoreCase((String) valorNivel)) {
                        rcc.setDrDireccion(rcd.getDrDireccion());
                        rcc.getDrDireccion().setId(hhppMgl.getHhpId());
                        rcc.setTipoNivel((String) tipoNivel);
                        rcc.setValorNivel((String) valorNivel);
                        rcc.setTipoAdicion("C");
                        rcd = drDireccionManager.construirDireccionSolicitud(rcc);
                    }
                }
            }
        }
        //Agregar apartamento
        for (int i = 25; i <= 26; i++) {
            if (rcd != null) {
                Object tipoNivel = item[i];
                if (!"".equalsIgnoreCase((String) tipoNivel)) {
                    Object valorNivel = item[i + 6];
                    if (!"".equalsIgnoreCase((String) valorNivel)) {
                        rcc.setDrDireccion(rcd.getDrDireccion());
                        rcc.getDrDireccion().setId(hhppMgl.getHhpId());
                        rcc.setTipoNivel((String) tipoNivel);
                        rcc.setValorNivel((String) valorNivel);
                        rcc.setTipoAdicion("A");
                        rcd = drDireccionManager.construirDireccionSolicitud(rcc);
                    }
                }
            }
        }

        for (int i = 45; i <= 45; i++) {
            if (rcd != null) {
                Object tipoNivel = item[i];
                if (!"".equalsIgnoreCase((String) tipoNivel)) {
                    Object valorNivel = item[i + 1];
                    if (!"".equalsIgnoreCase((String) valorNivel)) {
                        rcc.setDrDireccion(rcd.getDrDireccion());
                        rcc.setTipoNivel((String) tipoNivel);
                        rcc.setValorNivel((String) valorNivel);
                        rcc.setTipoAdicion("P");
                        rcd = drDireccionManager.construirDireccionSolicitud(rcc);
                    }
                }
            }
        }

        for (int i = 43; i <= 43; i++) {
            if (rcd != null) {
                Object tipoNivel = item[i];
                if (!"".equalsIgnoreCase((String) tipoNivel)) {
                    Object valorNivel = item[i + 1];
                    if (!"".equalsIgnoreCase((String) valorNivel)) {
                        rcc.setDrDireccion(rcd.getDrDireccion());
                        rcc.setTipoNivel((String) tipoNivel);
                        rcc.setValorNivel((String) valorNivel);
                        rcc.setTipoAdicion("P");
                        rcd = drDireccionManager.construirDireccionSolicitud(rcc);
                    }
                }
            }
        }
        if (rcd == null) {
            return null;
        }
        rcc.setDrDireccion(rcd.getDrDireccion());
        return rcc;
        
          } catch (Exception e) {
              return null;
        }
    }

    public boolean validarDireccionesSinCambios(DrDireccion dirOriginal, DrDireccion dirNueva, String[] item) {

        if (!direccionesIguales(dirOriginal.getIdTipoDireccion(), dirNueva.getIdTipoDireccion())) {
            return false;
        } else if (!direccionesIguales(dirOriginal.getDirPrincAlt(), dirNueva.getDirPrincAlt())) {
            return false;
        } else if (!direccionesIguales(dirOriginal.getBarrio(), dirNueva.getBarrio())) {
            return false;
        } else if (!direccionesIguales(dirOriginal.getTipoViaPrincipal(), dirNueva.getTipoViaPrincipal())) {
            return false;
        } else if (!direccionesIguales(dirOriginal.getNumViaPrincipal(), dirNueva.getNumViaPrincipal())) {
            return false;
        } else if (!direccionesIguales(dirOriginal.getLtViaPrincipal(), dirNueva.getLtViaPrincipal())) {
            return false;
        } else if (!direccionesIguales(dirOriginal.getNlPostViaP(), dirNueva.getNlPostViaP())) {
            return false;
        } else if (!direccionesIguales(dirOriginal.getBisViaPrincipal(), dirNueva.getBisViaPrincipal())) {
            return false;
        } else if (!direccionesIguales(dirOriginal.getCuadViaPrincipal(), dirNueva.getCuadViaPrincipal())) {
            return false;
        } else if (!direccionesIguales(dirOriginal.getTipoViaGeneradora(), dirNueva.getTipoViaGeneradora())) {
            return false;
        } else if (!direccionesIguales(dirOriginal.getNumViaGeneradora(), dirNueva.getNumViaGeneradora())) {
            return false;
        } else if (!direccionesIguales(dirOriginal.getLtViaGeneradora(), dirNueva.getLtViaGeneradora())) {
            return false;
        } else if (!direccionesIguales(dirOriginal.getNlPostViaG(), dirNueva.getNlPostViaG())) {
            return false;
        } else if (!direccionesIguales(dirOriginal.getBisViaGeneradora(), dirNueva.getBisViaGeneradora())) {
            return false;
        } else if (!direccionesIguales(dirOriginal.getCuadViaGeneradora(), dirNueva.getCuadViaGeneradora())) {
            return false;
        } else if (!direccionesIguales(dirOriginal.getPlacaDireccion(), dirNueva.getPlacaDireccion())) {
            return false;
        } else if (!direccionesIguales(dirOriginal.getCpTipoNivel1(), dirNueva.getCpTipoNivel1())) {
            return false;
        } else if (!direccionesIguales(dirOriginal.getCpTipoNivel2(), dirNueva.getCpTipoNivel2())) {
            return false;
        } else if (!direccionesIguales(dirOriginal.getCpTipoNivel3(), dirNueva.getCpTipoNivel3())) {
            return false;
        } else if (!direccionesIguales(dirOriginal.getCpTipoNivel4(), dirNueva.getCpTipoNivel4())) {
            return false;
        } else if (!direccionesIguales(dirOriginal.getCpTipoNivel5(), dirNueva.getCpTipoNivel5())) {
            return false;
        } else if (!direccionesIguales(dirOriginal.getCpTipoNivel6(), dirNueva.getCpTipoNivel6())) {
            return false;
        } else if (!direccionesIguales(dirOriginal.getCpValorNivel1(), dirNueva.getCpValorNivel1())) {
            return false;
        } else if (!direccionesIguales(dirOriginal.getCpValorNivel2(), dirNueva.getCpValorNivel2())) {
            return false;
        } else if (!direccionesIguales(dirOriginal.getCpValorNivel3(), dirNueva.getCpValorNivel3())) {
            return false;
        } else if (!direccionesIguales(dirOriginal.getCpValorNivel4(), dirNueva.getCpValorNivel4())) {
            return false;
        } else if (!direccionesIguales(dirOriginal.getCpValorNivel5(), dirNueva.getCpValorNivel5())) {
            return false;
        } else if (!direccionesIguales(dirOriginal.getCpValorNivel6(), dirNueva.getCpValorNivel6())) {
            return false;
        } else if (!direccionesIguales(dirOriginal.getMzTipoNivel1(), dirNueva.getMzTipoNivel1())) {
            return false;
        } else if (!direccionesIguales(dirOriginal.getMzTipoNivel2(), dirNueva.getMzTipoNivel2())) {
            return false;
        } else if (!direccionesIguales(dirOriginal.getMzTipoNivel3(), dirNueva.getMzTipoNivel3())) {
            return false;
        } else if (!direccionesIguales(dirOriginal.getMzTipoNivel4(), dirNueva.getMzTipoNivel4())) {
            return false;
        } else if (!direccionesIguales(dirOriginal.getMzTipoNivel5(), dirNueva.getMzTipoNivel5())) {
            return false;
        } else if (!direccionesIguales(dirOriginal.getMzValorNivel1(), dirNueva.getMzValorNivel1())) {
            return false;
        } else if (!direccionesIguales(dirOriginal.getMzValorNivel2(), dirNueva.getMzValorNivel2())) {
            return false;
        } else if (!direccionesIguales(dirOriginal.getMzValorNivel3(), dirNueva.getMzValorNivel3())) {
            return false;
        } else if (!direccionesIguales(dirOriginal.getMzValorNivel4(), dirNueva.getMzValorNivel4())) {
            return false;
        } else if (!direccionesIguales(dirOriginal.getMzValorNivel5(), dirNueva.getMzValorNivel5())) {
            return false;
        } else if (!direccionesIguales(dirOriginal.getMzTipoNivel6(), dirNueva.getMzTipoNivel6())) {
            return false;
        } else if (!direccionesIguales(dirOriginal.getMzValorNivel6(), dirNueva.getMzValorNivel6())) {
            return false;
        } else if (!direccionesIguales(dirOriginal.getItTipoPlaca(), dirNueva.getItTipoPlaca())) {
            return false;
        } else if (!direccionesIguales(dirOriginal.getItValorPlaca(), dirNueva.getItValorPlaca())) {
            return false;
        } else if (!direccionesIguales(dirOriginal.getLetra3G(), dirNueva.getLetra3G())) {
            return false;
        } else if (!"".equalsIgnoreCase((String) item[4])) {
            return false;
        }
        return true;
    }

    public boolean direccionesIguales(String dirOriginal, String dirNueva) {

        if (dirOriginal != null && dirNueva == null) {
            return false;
        } else if (dirOriginal == null && dirNueva != null) {
            return false;
        } else if (dirOriginal == null && dirNueva == null) {
            return true;
        } else if (dirOriginal != null && dirNueva != null) {
            if (!dirOriginal.equalsIgnoreCase(dirNueva)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Adherir tipo de dirección Intenta adherir el tipo de dirección al objeto
     * si el parámetro tipoDirección es CK, BM o IT.
     *
     * @author becerraarmr
     * @param drDireccion objeto donde se va agregar el tipo de de dirección
     * @param tipoDireccion valor a adherir
     *
     * @return true si pudo adherirlo y false en caso contrario.
     */
    private boolean isTipoDireccionCKoBMoIT(String valorTipoDireccion) {
        String[] tiposDireccion = {"CK", "BM", "IT"};

        for (String item : tiposDireccion) {
            if (item.equalsIgnoreCase(valorTipoDireccion)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Convertir Vector a DrDireccion Se pasan los datos del vector entre el
     * indice 21-62
     *
     * @param item vector con la data
     *
     * @return null si no se puede pasar o DrDireccion con la data incluida
     */
    private boolean addValor(DrDireccion drDireccion, Object valor, int i, DetalleCargaHhppMgl detalle) {

        if (drDireccion == null || valor == null || i < 4 || i > 48) {
            return false;
        }

        if (valor instanceof String && valor.toString().isEmpty()) {
            return false;
        }

        switch (i) {
            case 4: {//notas
                procesarNotas(detalle, (String) valor);
                break;
            }
            case 5: {//idTipoDireccion
                drDireccion.setIdTipoDireccion((String) valor);
                return true;
            }
            case 6: {//DirPrincAlt
                drDireccion.setDirPrincAlt((String) valor);
                return true;
            }
            case 7: {//Barrio
                drDireccion.setBarrio((String) valor);
                return true;
            }
            case 8: {//TipoViaPrincipal
                drDireccion.setTipoViaPrincipal((String) valor);
                break;
            }
            case 9: {//NumViaPrincipal
                drDireccion.setNumViaPrincipal((String) valor);
                break;
            }
            case 10: {//LtViaPrincipal
                drDireccion.setLtViaPrincipal((String) valor);
                break;
            }
            case 11: {//NlPostViaP
                drDireccion.setNlPostViaP((String) valor);
                break;
            }
            case 12: {//BisViaPrincipal
                drDireccion.setBisViaPrincipal((String) valor);
                break;
            }
            case 13: {//CuadViaPrincipal
                drDireccion.setCuadViaPrincipal((String) valor);
                break;
            }
            case 14: {//TipoViaGeneradora
                drDireccion.setTipoViaGeneradora((String) valor);
                break;
            }
            case 15: {//NumViaGeneradora
                drDireccion.setNumViaGeneradora((String) valor);
                break;
            }
            case 16: {//LtViaGeneradora
                drDireccion.setLtViaGeneradora((String) valor);
                break;
            }
            case 17: {//NlPostViaG
                drDireccion.setNlPostViaG((String) valor);
                break;
            }
            case 18: {//BisViaGeneradora
                drDireccion.setBisViaGeneradora((String) valor);
                break;
            }
            case 19: {//CuadViaGeneradora
                drDireccion.setCuadViaGeneradora((String) valor);
                break;
            }
            case 20: {//PlacaDireccion
                drDireccion.setPlacaDireccion((String) valor);
                break;
            }
            case 21: {//CpTipoNivel1
                drDireccion.setCpTipoNivel1((String) valor);
                break;
            }
            case 22: {//CpTipoNivel2
                drDireccion.setCpTipoNivel2((String) valor);
                break;
            }
            case 23: {//CpTipoNivel3
                drDireccion.setCpTipoNivel3((String) valor);
                break;
            }
            case 24: {//CpTipoNivel4
                drDireccion.setCpTipoNivel4((String) valor);
                break;
            }
            case 25: {//CpTipoNivel5
                drDireccion.setCpTipoNivel5((String) valor);
                break;
            }
            case 26: {//CpTipoNivel6
                drDireccion.setCpTipoNivel6((String) valor);
                break;
            }
            case 27: {//CpValorNivel1
                drDireccion.setCpValorNivel1((String) valor);
                break;
            }
            case 28: {//CpValorNivel2
                drDireccion.setCpValorNivel2((String) valor);
                break;
            }
            case 29: {//CpValorNivel3
                drDireccion.setCpValorNivel3((String) valor);
                break;
            }
            case 30: {//CpValorNivel4
                drDireccion.setCpValorNivel4((String) valor);
                break;
            }
            case 31: {//CpValorNivel5
                drDireccion.setCpValorNivel5((String) valor);
                break;
            }
            case 32: {//CpValorNivel6
                drDireccion.setCpValorNivel6((String) valor);
                break;
            }
            case 33: {//MzTipoNivel1
                drDireccion.setMzTipoNivel1((String) valor);
                break;
            }
            case 34: {//MzTipoNivel2
                drDireccion.setMzTipoNivel2((String) valor);
                break;
            }
            case 35: {//MzTipoNivel3
                drDireccion.setMzTipoNivel3((String) valor);
                break;
            }
            case 36: {//MzTipoNivel4
                drDireccion.setMzTipoNivel4((String) valor);
                break;
            }
            case 37: {//MzTipoNivel5
                drDireccion.setMzTipoNivel5((String) valor);
                break;
            }
            case 38: {//MzValorNivel1
                drDireccion.setMzValorNivel1((String) valor);
                break;
            }
            case 39: {//MzValorNivel2
                drDireccion.setMzValorNivel2((String) valor);
                break;
            }
            case 40: {//MzValorNivel3
                drDireccion.setMzValorNivel3((String) valor);
                break;
            }
            case 41: {//MzValorNivel4
                drDireccion.setMzValorNivel4((String) valor);
                break;
            }
            case 42: {//MzValorNivel5
                drDireccion.setMzValorNivel5((String) valor);
                break;
            }
            case 43: {//MzTipoNivel6
                drDireccion.setMzTipoNivel6((String) valor);
                break;
            }
            case 44: {//MzValorNivel6
                drDireccion.setMzValorNivel6((String) valor);
                break;
            }
            case 45: {//ItTipoPlaca
                drDireccion.setItTipoPlaca((String) valor);
                break;
            }
            case 46: {//ItValorPlaca
                drDireccion.setItValorPlaca((String) valor);
                break;
            }

        }
        return false;
    }

    /**
     * Procesar el hhppIdrR Se verifica si le valor que llega es una id de un
     * hhpp válido.
     *
     * @author becerraarmr
     * @param detalleCarga control del proceso
     * @param valor valor del idhhppoidrr
     */
    protected void procesarHhpIdrR(
            DetalleCargaHhppMgl detalleCarga,
            String valor) {
        try {

            if (detalleCarga.getHhppMgl() != null) {
                BigDecimal HhpIdrR = detalleCarga.getHhppMgl().getHhpIdrR() == null
                        ? new BigDecimal("-1") : new BigDecimal(detalleCarga.getHhppMgl().getHhpIdrR());
                BigDecimal valorDec = findBigDecimal(valor) == null
                        ? new BigDecimal("-1") : new BigDecimal(valor);
                if (valorDec.compareTo(HhpIdrR) != 0) {
                    detalleCarga.addDetalle("El HhpIdrR:" + valorDec.intValue()
                            + " no corresponde al hhpp:" + detalleCarga.getHhppMgl().getHhpId() + "  ", DetalleCargaHhppMgl.DETALLEWARNING);

                }
            }
        } catch (Exception e) {
            String msg = "El HhpIdrR no se pudo validar: " + e.getMessage();
            LOGGER.error(msg);
            
            detalleCarga.addDetalle(msg, DetalleCargaHhppMgl.DETALLEWARNING);

        }
    }

    /**
     * Procesar el HhpCalle Se verifica si le valor que llega es una id de un
     * hhpp válido.
     *
     * @author becerraarmr
     * @param detalleCarga control del proceso
     * @param valor valor de HhpCalle
     */
    protected void procesarHhpCalle(
            DetalleCargaHhppMgl detalleCarga,
            String valor) {
        try {
            if (detalleCarga.getHhppMgl() == null) {
                detalleCarga.addDetalle("No se pudo procesar HhpCalle", DetalleCargaHhppMgl.DETALLEWARNING);
                detalleCarga.setEstado(DetalleCargaHhppMgl.NOPROCESADO);
            } else {
                String HhpCalle = detalleCarga.getHhppMgl().getHhpCalle() == null
                        ? "" : detalleCarga.getHhppMgl().getHhpCalle();

                if (!valor.equalsIgnoreCase(HhpCalle)) {
                    detalleCarga.addDetalle("El HhpCalle:" + valor
                            + " no corresponde al hhpp:" + detalleCarga.getHhppMgl().getHhpId() + "  ", DetalleCargaHhppMgl.DETALLEWARNING);
                    detalleCarga.setEstado(DetalleCargaHhppMgl.NOPROCESADO);
                }
            }
        } catch (Exception e) {
            String msg = "El HhppCalle no se pudo validar: " + e.getMessage();
            LOGGER.error(msg);

            detalleCarga.addDetalle(msg, DetalleCargaHhppMgl.DETALLEWARNING);
            detalleCarga.setEstado(DetalleCargaHhppMgl.NOPROCESADO);
        }
    }

    /**
     * Procesar el HhpPlaca Se verifica si le valor que llega es una id de un
     * hhpp válido.
     *
     * @author becerraarmr
     * @param detalleCarga control del proceso
     * @param valor valor de HhpPlaca
     */
    protected void procesarHhpPlaca(
            DetalleCargaHhppMgl detalleCarga,
            String valor) {
        try {
            if (detalleCarga.getHhppMgl() == null) {
                detalleCarga.addDetalle("No se pudo procesar HhpPlaca", DetalleCargaHhppMgl.DETALLEWARNING);
                detalleCarga.setEstado(DetalleCargaHhppMgl.NOPROCESADO);
            }

            String HhpPlaca = detalleCarga.getHhppMgl().getHhpPlaca() == null
                    ? "" : detalleCarga.getHhppMgl().getHhpPlaca();
            if (!valor.equalsIgnoreCase(HhpPlaca)) {
                detalleCarga.addDetalle("El HhpPlaca:" + valor
                        + " no corresponde al hhpp:" + detalleCarga.getHhppMgl().getHhpId() + "  ", DetalleCargaHhppMgl.DETALLEWARNING);
                detalleCarga.setEstado(DetalleCargaHhppMgl.NOPROCESADO);
            }
        } catch (Exception e) {
            String msg = "El HhppPlaca no se pudo validar: " + e.getMessage();
            LOGGER.error(msg);
            
            detalleCarga.addDetalle(msg, DetalleCargaHhppMgl.DETALLEWARNING);
            detalleCarga.setEstado(DetalleCargaHhppMgl.NOPROCESADO);
        }
    }

    /**
     * Procesar el HhpApart Se verifica si le valor que llega es una id de un
     * hhpp válido.
     *
     * @author becerraarmr
     * @param detalleCarga control del proceso
     * @param valor valor de HhpApart
     */
    protected void procesarHhpApart(
            DetalleCargaHhppMgl detalleCarga,
            String valor) {
        try {

            if (detalleCarga.getHhppMgl() == null) {
                detalleCarga.addDetalle("No se pudo procesar HhpApart", DetalleCargaHhppMgl.DETALLEWARNING);
                detalleCarga.setEstado(DetalleCargaHhppMgl.NOPROCESADO);
            } else {
                String HhpPlaca = detalleCarga.getHhppMgl().getHhpApart() == null
                        ? "" : detalleCarga.getHhppMgl().getHhpApart();
                if (!valor.equalsIgnoreCase(HhpPlaca)) {
                    detalleCarga.addDetalle("El HhpApart:" + valor
                            + " no corresponde al hhpp:" + detalleCarga.getHhppMgl().getHhpId() + "  ", DetalleCargaHhppMgl.DETALLEWARNING);
                    detalleCarga.setEstado(DetalleCargaHhppMgl.NOPROCESADO);
                }
            }
        } catch (Exception e) {
            String msg = "El HhppApart no es válido: " + e.getMessage();
            LOGGER.error(msg);
            
            detalleCarga.addDetalle(msg, DetalleCargaHhppMgl.DETALLEWARNING);
            detalleCarga.setEstado(DetalleCargaHhppMgl.NOPROCESADO);
        }
    }

    /**
     * Procesar el HhpDireccion para su valdiacion
     *
     * @author Jonathan Peña
     * @param detalleCarga control del proceso
     * @param valor valor de HhpDireccion
     */
    protected void procesarHhpDireccion(DetalleCargaHhppMgl detalleCarga, String valor) {
        try {

            if (detalleCarga.getHhppMgl() != null) {
                String drieccion = "";
                if (detalleCarga.getHhppMgl().getSubDireccionObj() != null) {
                    drieccion = detalleCarga.getHhppMgl().getSubDireccionObj().getSdiFormatoIgac() != null
                            ? detalleCarga.getHhppMgl().getSubDireccionObj().getSdiFormatoIgac() : "";

                } else if (detalleCarga.getHhppMgl().getDireccionObj() != null) {
                    drieccion = detalleCarga.getHhppMgl().getDireccionObj().getDirFormatoIgac() != null
                            ? detalleCarga.getHhppMgl().getDireccionObj().getDirFormatoIgac() : "";

                }
                valor = valor == null || valor.trim().isEmpty() ? "" : valor;
                drieccion = drieccion.trim();
                if (!valor.equals(drieccion)) {
                    detalleCarga.addDetalle("El HhppDireccion:" + valor
                            + " no corresponde al hhpp:" + detalleCarga.getHhppMgl().getHhpId() + "  ", DetalleCargaHhppMgl.DETALLEWARNING);

                }
            }
        } catch (Exception e) {
            String msg = "El HhppDireccion no es válido: " + e.getMessage();
            LOGGER.error(msg);
            
            detalleCarga.addDetalle(msg, DetalleCargaHhppMgl.DETALLEWARNING);

        }
    }

    /**
     * Procesar el Estrato Se verifica si le valor que llega es una id de un
     * hhpp válido.
     *
     * @author becerraarmr
     * @param detalleCarga control del proceso
     * @param valor valor de Estrato
     * @param estadoUnit valor del estado
     */
    protected void procesarEstrato(
            DetalleCargaHhppMgl detalleCarga,
            String valor, String estadoUnit) {
        MarcasMglDaoImpl marcasMglDaoImpl = new MarcasMglDaoImpl();

        try {
            BigDecimal estratoBigDecimal = findBigDecimal(valor);

            if (detalleCarga.getHhppMgl() != null) {

                boolean subditreccion = false;
                BigDecimal estratoOriginal;
                if (detalleCarga.getHhppMgl().getSubDireccionObj() != null) {
                    estratoOriginal = detalleCarga.getHhppMgl().getSubDireccionObj().getSdiEstrato();
                    subditreccion = true;
                } else {
                    estratoOriginal = detalleCarga.getHhppMgl().getDireccionObj().getDirEstrato();
                }

                if (estratoBigDecimal.compareTo(estratoOriginal) != 0) {
                    if (!"PO".equalsIgnoreCase(estadoUnit)) {
                        detalleCarga.addDetalle("El Estrato:" + estratoBigDecimal.intValue()
                                + " no se puede actualizar porque el "
                                + "estado es " + estadoUnit, DetalleCargaHhppMgl.DETALLEWARNING);

                    } else if (estratoBigDecimal.intValue() < 0 && estratoBigDecimal.intValue() > 9) {
                        detalleCarga.addDetalle("El Estrato:" + estratoBigDecimal.intValue()
                                + " no corresponde alguno de los valores:"
                                + "0,1,2,3,4,5,6,7,8,9  ", DetalleCargaHhppMgl.DETALLEWARNING);

                    } else {//actualice estrato valor 1

                        String msg = "Se cambió estrato:" + estratoOriginal
                                + " por estrato:" + estratoBigDecimal.intValue();

                        detalleCarga.addDetalle(msg, DetalleCargaHhppMgl.DETALLEINFO);
                        detalleCarga.setEstado(DetalleCargaHhppMgl.PROCESADO);
                        if (subditreccion) {
                            SubDireccionMgl subDireccion = detalleCarga.getHhppMgl().getSubDireccionObj();
                            subDireccion.setSdiEstrato(estratoBigDecimal);
                            ejbSubdireccionMgl.update(subDireccion);
                        } else {
                            DireccionMgl direccionMgl = detalleCarga.getHhppMgl().getDireccionObj();
                            direccionMgl.setDirEstrato(estratoBigDecimal);
                            getEjbDireccionMgl().update(direccionMgl);
                        }

                        MarcasHhppMglManager marcasHhppMglManager = new MarcasHhppMglManager();
                        //Se busca la marca a agregar
                        MarcasMgl ESO = marcasMglDaoImpl.findMarcasMglByCodigo("ESO");
                        //Se asocia la marca en MGL
                        //Creacion RR
                        HhppMglManager hhppMglManager = new HhppMglManager();
                        List<MarcasMgl> listaMarcasMgl = new ArrayList<MarcasMgl>();
                        //Agrega la nueva marca a la lista en la primera posicion (Marca que quedara como UniteProblemCode Actual)
                        listaMarcasMgl.add(ESO);
                        //Traer los datos anteriores
                        listaMarcasMgl.addAll(marcasMglDaoImpl.findMarcasMglByHhpp(detalleCarga.getHhppMgl()));
                        //Se agregan las marcas
                        try {
                            hhppMglManager.agregarMarcasHhpp(detalleCarga.getHhppMgl(), listaMarcasMgl, "MER_USER");
                            msg = "\n Se realizo actualización de marcas en RR ";
                            detalleCarga.addDetalle(msg, DetalleCargaHhppMgl.DETALLEINFO);
                        } catch (Exception e) {
                            msg = "\n Se produjo error al realizar la actualización de marcas en RR: " + e.getMessage();
                            LOGGER.error(msg, e);
                            
                            detalleCarga.addDetalle(msg, DetalleCargaHhppMgl.DETALLEWARNING);

                        }
                        direccionRrManager = new DireccionRRManager(true);


                        String comunidad = detalleCarga.getHhppMgl().getHhpComunidad();
                        String division = detalleCarga.getHhppMgl().getHhpDivision();
                        String placa = detalleCarga.getHhppMgl().getHhpPlaca();
                        String calle = detalleCarga.getHhppMgl().getHhpCalle();
                        String apartamento = detalleCarga.getHhppMgl().getHhpApart();

                        if (detalleCarga.getHhppMgl().getHhpIdrR() != null) {
                            HhppResponseRR responseHhppRR = direccionRrManager.getHhppByIdRR(detalleCarga.getHhppMgl().getHhpIdrR());
                            if (responseHhppRR.getTipoMensaje() != null
                                    && responseHhppRR.getTipoMensaje().equalsIgnoreCase("I")) {
                                comunidad = responseHhppRR.getComunidad();
                                division = responseHhppRR.getDivision();
                                calle = responseHhppRR.getStreet();
                                placa = responseHhppRR.getHouse();
                                apartamento = responseHhppRR.getApartamento();
                            } else {
                                System.err.println("Ocurrio un error consultando la data del hhpp en RR");

                            }
                        }
                        DireccionRREntity dirRREntity = new DireccionRREntity();
                        dirRREntity.setCalle(calle);
                        dirRREntity.setNumeroUnidad(placa);
                        dirRREntity.setNumeroApartamento(apartamento);
                        dirRREntity.setComunidad(comunidad);
                        dirRREntity.setDivision(division);

                        //JDHT
                        //Valida si RR se encuentra encendido o apagado para realizar las operaciones en RR
                        boolean habilitarRR = false;
                        ParametrosMgl parametroHabilitarRR = getParametrosMglManager().findParametroMgl(Constant.HABILITAR_RR);
                        if (parametroHabilitarRR != null && parametroHabilitarRR.getParValor().equalsIgnoreCase(Constant.RR_HABILITADO)) {
                            habilitarRR = true;
                        }
                        if (habilitarRR) {
                            try {
                                direccionRrManager.cambioEstratoRR(detalleCarga.getHhppMgl().getHhpComunidad(),
                                        detalleCarga.getHhppMgl().getHhpDivision(), estratoBigDecimal.toString(), dirRREntity);
                                msg = "\n Se realizo actualización de estrato en RR ";
                                detalleCarga.addDetalle(msg, DetalleCargaHhppMgl.DETALLEINFO);
                            } catch (Exception e) {
                                msg = "\n Se produjo error al realizar la actualización de estrato en RR: " + e.getMessage();
                                LOGGER.error(msg, e);
                                
                                detalleCarga.addDetalle(msg, DetalleCargaHhppMgl.DETALLEWARNING);
                            }
                        }
                    }
                }
            }
        } catch (ApplicationException e) {
            String msg = "El Estrato:" + valor + " no se pudo actualizar: " + e.getMessage();
            LOGGER.error(msg);
            
            detalleCarga.addDetalle(msg, DetalleCargaHhppMgl.DETALLEWARNING);
            detalleCarga.setEstado(DetalleCargaHhppMgl.NOPROCESADO);
        }
    }

    /**
     * Procesar el NivSocio Se verifica si le valor que llega es una id de un
     * hhpp válido.
     *
     * @author becerraarmr
     * @param detalleCarga control del proceso
     * @param valor valor de NivSocio
     */
    protected void procesarNivSocio(
            DetalleCargaHhppMgl detalleCarga,
            String valor) {
        try {
            if (detalleCarga.getHhppMgl() != null) {

                BigDecimal nivelSocio = findBigDecimal(valor);
                BigDecimal nivelSocioOriginal = null;
                if (detalleCarga.getHhppMgl().getSubDireccionObj() != null) {
                    nivelSocioOriginal = detalleCarga.getHhppMgl().getSubDireccionObj().getSdiNivelSocioecono() == null
                            ? null : detalleCarga.getHhppMgl().getSubDireccionObj().getSdiNivelSocioecono();
                } else if (detalleCarga.getHhppMgl().getDireccionObj() != null) {
                    nivelSocioOriginal = detalleCarga.getHhppMgl().getDireccionObj().getDirNivelSocioecono() == null
                            ? null : detalleCarga.getHhppMgl().getDireccionObj().getDirNivelSocioecono();

                }
                if (nivelSocio != null && nivelSocioOriginal != null) {
                    if (nivelSocio.compareTo(nivelSocioOriginal) != 0) {//no son iguales
                        detalleCarga.addDetalle("El Niv_Socio: no se puede modificar ", DetalleCargaHhppMgl.DETALLEWARNING);

                    }
                }
            }
        } catch (Exception e) {
            String msg = "No se pudo actualizar el nivel socioeconómico: " + e.getMessage();
            LOGGER.error(msg);
            
            detalleCarga.addDetalle(msg, DetalleCargaHhppMgl.DETALLEWARNING);

        }
    }

    /**
     * Procesar el Barrio
     * <p>
     * Se verifica si le valor es válido
     *
     * @author becerraarmr
     * @param detalleCarga control del proceso
     * @param valor valor de Barrio
     */
    protected void procesarBarrio(
            DetalleCargaHhppMgl detalleCarga,
            String valor) {
        try {
            if (detalleCarga.getHhppMgl() != null) {
                HhppMgl hhppMglVal = detalleCarga.getHhppMgl();
                String barrioOriginal = "";
                valor = valor == null || valor.trim().isEmpty() ? "" : valor;
                try {
                    CmtDireccionDetalladaMgl detallada = getDetallaadMgl(hhppMglVal);
                    if (detallada != null) {
                        barrioOriginal = detallada.getBarrio() == null || detallada.getBarrio().trim().isEmpty()
                                ? "" : detallada.getBarrio();
                    }

                } catch (Exception e) {
                    String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                    LOGGER.error(msg, e);
                    
                    barrioOriginal = "";
                }
                if (!valor.equalsIgnoreCase(barrioOriginal)) {
                    detalleCarga.addDetalle("El Barrio:" + valor
                            + " no corresponde al hhpp:" + hhppMglVal.getHhpId() + "  ", DetalleCargaHhppMgl.DETALLEWARNING);
                }
            }
        } catch (Exception e) {
            String msg = "El barrio no pudo validarse: " + e.getMessage();
            LOGGER.error(msg, e);
            
            detalleCarga.addDetalle(msg, DetalleCargaHhppMgl.DETALLEWARNING);

        }
    }

    /**
     * Mostrar la Direccion detallada del Hhpp
     *
     * @author bocanegra Vm
     * @param hhppMgl hhpp para verficar el valor.
     * @return la Direccion detallada o el null
     */
    public CmtDireccionDetalladaMgl getDetallaadMgl(HhppMgl hhppMgl) {

        if (hhppMgl != null) {
            if (hhppMgl.getDirId() != null) {
                BigDecimal subDireccion = null;
                if (hhppMgl.getSubDireccionObj() != null) {
                    subDireccion = hhppMgl.getSubDireccionObj().getSdiId();
                }
                try {
                    List<CmtDireccionDetalladaMgl> detalladaMgl;
                    detalladaMgl = direccionDetalleMglmanager.findDireccionDetallaByDirIdSdirId(hhppMgl.getDirId(), subDireccion);
                    if (detalladaMgl.size() > 0) {
                        return detalladaMgl.get(0);
                    }
                } catch (ApplicationException e) {
                    String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                    LOGGER.error(msg, e);
                }

            }
        }
        return null;
    }

    /**
     * Procesar el HhpComunidad
     * <p>
     * Se verifica si le valor es válido
     *
     * @author becerraarmr
     * @param detalleCarga control del proceso
     * @param valor valor de HhpComunidad
     */
    protected void procesarHhpComunidad(
            DetalleCargaHhppMgl detalleCarga,
            String valor) {
        try {
            String comunidadOriginal = detalleCarga.getHhppMgl().
                    getHhpComunidad() == null ? "" : detalleCarga.getHhppMgl().
                                    getHhpComunidad();

            if ((valor != null && valor.isEmpty() != true)
                    && (comunidadOriginal != null && comunidadOriginal.isEmpty() != true)) {

                if (!valor.equalsIgnoreCase(comunidadOriginal)) {
                    detalleCarga.addDetalle("El Hhpcomunidad:" + valor
                            + " es igual al actual del Hhpp:" + detalleCarga.getHhppMgl().
                                    getHhpId() + "  ", DetalleCargaHhppMgl.DETALLEWARNING);

                } else {
                    ComunidadRrManager regionalMglManager = new ComunidadRrManager();
                    CmtComunidadRr nuevaComunidad = regionalMglManager.findComunidadByCodigo(valor);

                    if (nuevaComunidad != null) {
                        detalleCarga.addDetalle(" Se cambió HhpComunidad: "
                                + comunidadOriginal + " por HhpComunidad: " + valor, DetalleCargaHhppMgl.DETALLEINFO);
                        detalleCarga.getHhppMgl().setHhpDivision(valor);
                    } else {
                        detalleCarga.addDetalle("El HhpComunidad:" + valor
                                + " no existe ", DetalleCargaHhppMgl.DETALLEWARNING);

                    }

                }

            } else {
                detalleCarga.addDetalle("No se pudo procesar HhpComunidad", DetalleCargaHhppMgl.DETALLEWARNING);

            }
        } catch (Exception e) {
            String msg = "El HhpComunidad no se pudo validar: " + e.getMessage();
            LOGGER.error(msg, e);
            
            detalleCarga.addDetalle(msg, DetalleCargaHhppMgl.DETALLEWARNING);

        }
    }

    /**
     * Procesar el HhpDivision
     * <p>
     * Se verifica si le valor es válido
     *
     * @author becerraarmr
     * @param detalleCarga control del proceso
     * @param valor valor de HhpDivision
     */
    protected void procesarHhpDivision(
            DetalleCargaHhppMgl detalleCarga,
            String valor) {
        try {
            String divisionOriginal = detalleCarga.getHhppMgl().
                    getHhpDivision() == null ? "" : detalleCarga.getHhppMgl().
                                    getHhpDivision();

            if ((valor != null && valor.isEmpty() != true)
                    && (divisionOriginal != null && divisionOriginal.isEmpty() != true)) {

                if (!valor.equalsIgnoreCase(divisionOriginal)) {
                    detalleCarga.addDetalle("El HhpDivision:" + valor
                            + " es igual al actual del Hhpp:" + detalleCarga.getHhppMgl().
                                    getHhpId() + "  ", DetalleCargaHhppMgl.DETALLEWARNING);

                } else {
                    RegionalMglManager regionalMglManager = new RegionalMglManager();
                    CmtRegionalRr nuevoDivisional = regionalMglManager.findByCodigoRR(valor);

                    if (nuevoDivisional != null) {
                        detalleCarga.addDetalle(" Se cambió HhpDivision: "
                                + divisionOriginal + " por HhpDivision: " + valor, DetalleCargaHhppMgl.DETALLEINFO);
                        detalleCarga.getHhppMgl().setHhpDivision(valor);
                    } else {
                        detalleCarga.addDetalle("El HhpDivision:" + valor
                                + " no existe ", DetalleCargaHhppMgl.DETALLEWARNING);

                    }

                }

            } else {
                detalleCarga.addDetalle("No se pudo procesar HhpComunidad", DetalleCargaHhppMgl.DETALLEWARNING);

            }

        } catch (ApplicationException e) {
            String msg = "El HhpDivision no pudo ser validado: " + e.getMessage();
            LOGGER.error(msg, e);
            
            detalleCarga.addDetalle(msg, DetalleCargaHhppMgl.DETALLEWARNING);
        }
    }

    /**
     * Procesar el HhpEstadoUnit
     * <p>
     * Se verifica si le valor es válido
     *
     * @author becerraarmr
     * @param detalleCarga control del proceso
     * @param valor valor de HhpEstadoUnit
     */
    protected void procesarHhpEstadoUnit(
            DetalleCargaHhppMgl detalleCarga,
            String valor) {

        try {

            if (detalleCarga.getHhppMgl() != null) {
                String hhpEstadoUnit = detalleCarga.getHhppMgl().getHhpEstadoUnit() == null
                        ? "" : detalleCarga.getHhppMgl().getHhpEstadoUnit();
                if (!hhpEstadoUnit.equalsIgnoreCase(valor)) {
                    detalleCarga.addDetalle("El HhpEstadoUnit no se "
                            + "puede modificar ", DetalleCargaHhppMgl.DETALLEWARNING);
                }
            }

        } catch (Exception e) {
            String msg = "El HhpEstadoUnit " + valor
                    + " no pudo validarse: " + e.getMessage();
            LOGGER.error(msg, e);
            
            detalleCarga.addDetalle(msg, DetalleCargaHhppMgl.DETALLEWARNING);

        }
    }

    /**
     * Procesar el HhpCodigoPostal
     * <p>
     * Se verifica si le valor es válido
     *
     * @author becerraarmr
     * @param detalleCarga control del proceso
     * @param valor valor de HhpCodigoPostal
     * @param comunidad valor de comunidad
     *
     */
    protected void procesarHhpCodigoPostal(
            DetalleCargaHhppMgl detalleCarga,
            String valor, String comunidad) {
        try {
            if (detalleCarga.getHhppMgl() != null) {
                String HhpCodigoPostal = detalleCarga.getHhppMgl().getHhpCodigoPostal() == null
                        ? "" : detalleCarga.getHhppMgl().getHhpCodigoPostal();
                if (!HhpCodigoPostal.equalsIgnoreCase(valor)) {
                    String comunidadO = detalleCarga.getHhppMgl().getHhpComunidad() == null
                            ? "" : detalleCarga.getHhppMgl().getHhpComunidad();
                    if (!comunidadO.equalsIgnoreCase(comunidad)) {
                        detalleCarga.addDetalle("El HhpCodigoPostal:" + valor
                                + " no se puede validar porque la "
                                + "comunidad:" + comunidad
                                + " no corresponde al hhpp:" + detalleCarga.getHhppMgl().getHhpId(), DetalleCargaHhppMgl.DETALLEWARNING);

                    } else {
                        if (!valor.equalsIgnoreCase(HhpCodigoPostal)) {
                            detalleCarga.addDetalle(" Se cambio código postal: "
                                    + HhpCodigoPostal + " por código postal: "
                                    + valor, DetalleCargaHhppMgl.DETALLEINFO);
                            if (detalleCarga.getEstado().equals(DetalleCargaHhppMgl.NOPROCESADO)
                                    || detalleCarga.getEstado().equals(DetalleCargaHhppMgl.PROCESADO_INCOMPLETO)
                                    ||detalleCarga.getEstado().equals(DetalleCargaHhppMgl.PROCESADO_SINCAMBIO)) {
                                detalleCarga.setEstado(DetalleCargaHhppMgl.PROCESADO_INCOMPLETO);
                            } else {
                                detalleCarga.setEstado(DetalleCargaHhppMgl.PROCESADO);
                            }
                            detalleCarga.getHhppMgl().setHhpCodigoPostal(valor);
                        }

                    }
                }
            }

        } catch (Exception e) {
            String msg = "El HhpCodigoPostal "
                    + valor + " no se puede validar: " + e.getMessage();
            LOGGER.error(msg, e);
            
            detalleCarga.addDetalle(msg, DetalleCargaHhppMgl.DETALLEWARNING);
            if (detalleCarga.getEstado().equals(DetalleCargaHhppMgl.PROCESADO)
                    || detalleCarga.getEstado().equals(DetalleCargaHhppMgl.PROCESADO_INCOMPLETO)
                    ||detalleCarga.getEstado().equals(DetalleCargaHhppMgl.PROCESADO_SINCAMBIO)) {
                detalleCarga.setEstado(DetalleCargaHhppMgl.PROCESADO_INCOMPLETO);
            } else {
                detalleCarga.setEstado(DetalleCargaHhppMgl.NOPROCESADO);
            }
        }
    }

    /**
     * Procesar el UltUbicacion
     * <p>
     * Se verifica si le valor es válido
     *
     * @author becerraarmr
     * @param detalleCarga control del proceso
     * @param valor valor de HhpUltUbicacion
     *
     */
    protected void procesarHhpUltUbicacion(
            DetalleCargaHhppMgl detalleCarga, String valor) {

        try {
            if (detalleCarga.getHhppMgl() != null) {
                String ultbicacionO = detalleCarga.getHhppMgl().getHhpUltUbicacion() == null
                        ? "" : detalleCarga.getHhppMgl().getHhpUltUbicacion();
                if (!ultbicacionO.equalsIgnoreCase(valor)) {
                    NodoMgl nodoOriginal = detalleCarga.getHhppMgl().getNodId();
                    if (nodoOriginal != null) {
                        List<NodoMgl> listNodos;
                        listNodos = getNodoManager().
                                findNodos(nodoOriginal.getNodTipo(),
                                        nodoOriginal.getGpoId(), valor.trim().toUpperCase());

                        if (listNodos == null || listNodos.isEmpty()) {
                            detalleCarga.addDetalle("El Nodo:" + valor + " no corresponde a la "
                                    + "misma ciudad ni la misma tecnología del hhpp:"
                                    + detalleCarga.getHhppMgl().getHhpId() + "  ", DetalleCargaHhppMgl.DETALLEWARNING);

                                    detalleCarga.setEstado(DetalleCargaHhppMgl.PROCESADO_SINCAMBIO);
                                
                        } else {
                            NodoMgl nodoNuevo = listNodos.get(0);
                            if (nodoNuevo != null) {
                                String msg  = "";
                                //JDHT
                                //Valida si RR se encuentra encendido o apagado para realizar las operaciones en RR
                                boolean habilitarRR = false;   
                                ParametrosMgl parametroHabilitarRR = getParametrosMglManager().findParametroMgl(Constant.HABILITAR_RR);
                                if (parametroHabilitarRR != null && parametroHabilitarRR.getParValor().equalsIgnoreCase(Constant.RR_HABILITADO)) {
                                    habilitarRR = true;
                                }
                                if (habilitarRR && detalleCarga.getHhppMgl() != null 
                                        && detalleCarga.getHhppMgl().getHhpIdrR() != null 
                                        && !detalleCarga.getHhppMgl().getHhpIdrR().isEmpty() ) {

                                    HhppResponseRR hhppResponseRR;
                                    //Consume servicio que busca hhpp por Id de RR
                                    hhppResponseRR = direccionRRManager.getHhppByIdRR(detalleCarga.getHhppMgl().getHhpIdrR());

                                    if (hhppResponseRR != null && hhppResponseRR.getTipoMensaje().equalsIgnoreCase("I")) {
                                        
                                        msg = direccionRRManager.cambioNodoHHPP(hhppResponseRR.getHouse(), hhppResponseRR.getStreet(),
                                                hhppResponseRR.getApartamento(), hhppResponseRR.getComunidad(),
                                                hhppResponseRR.getDivision(), nodoNuevo.getNodCodigo().toUpperCase());
                                    }
                                 //JDHT
                                if (msg != null && !msg.contains("CAB0074")) {
                                    detalleCarga.addDetalle(" NO Se realizó el cambio Nodo en RR: "
                                            + ultbicacionO + " por Nodo: " + valor, DetalleCargaHhppMgl.DETALLEWARNING);
                                    detalleCarga.addDetalle(msg, DetalleCargaHhppMgl.DETALLEWARNING);
                                    detalleCarga.setEstado(DetalleCargaHhppMgl.PROCESADO_INCOMPLETO);
                                    } else {
                                        detalleCarga.addDetalle(" Se cambió el Nodo en RR: "
                                                + ultbicacionO + " por Nodo: " + valor, DetalleCargaHhppMgl.DETALLEWARNING);
                                        detalleCarga.setEstado(DetalleCargaHhppMgl.PROCESADO);
                                    }
                                }
                                
                                detalleCarga.getHhppMgl().setHhpUltUbicacion(valor);
                                detalleCarga.getHhppMgl().setNodId(nodoNuevo);
                                //Si RR esta encendido y se guardo correctamente en RR
                                if(habilitarRR && msg != null &&  msg.contains("CAB0074") && detalleCarga.getHhppMgl() != null 
                                        && detalleCarga.getHhppMgl().getHhpIdrR() != null 
                                        && !detalleCarga.getHhppMgl().getHhpIdrR().isEmpty()){
                                    hhppManager.update(detalleCarga.getHhppMgl());
                                }else{
                                    //Si RR esta encendido y en RR ocurrio un error porque no devuelve el codigo exitoso. Proceso incompleto
                                    if(habilitarRR && detalleCarga.getHhppMgl() != null 
                                        && detalleCarga.getHhppMgl().getHhpIdrR() != null 
                                        && !detalleCarga.getHhppMgl().getHhpIdrR().isEmpty()){                                       
                                    detalleCarga.addDetalle(msg, DetalleCargaHhppMgl.DETALLEWARNING);
                                    detalleCarga.setEstado(DetalleCargaHhppMgl.PROCESADO_INCOMPLETO);
                                    
                                    }else{
                                        //Si RR esta apagado solo hace el cambio en MGL
                                        getHhppManager().update(detalleCarga.getHhppMgl());
                                        detalleCarga.setEstado(DetalleCargaHhppMgl.PROCESADO);
                                    }
                                }
                                
                            }
                        }
                    }
                }

            }
        } catch (Exception e) {
            String msg = "No se pudo procesar HhppUltUbicacion: " + e.getMessage();
            LOGGER.error(msg, e);
            
            detalleCarga.addDetalle(msg, DetalleCargaHhppMgl.DETALLEWARNING);
            detalleCarga.setEstado(DetalleCargaHhppMgl.NOPROCESADO);
            
        }
    }

    /**
     * Procesar el HhpHeadEnd
     * <p>
     * Se verifica si le valor es válido
     *
     * @author becerraarmr
     * @param detalleCarga control del proceso
     * @param valor valor de HhpHeadEnd
     * @param comunidad valor de comunidad
     */
    protected void procesarHhpHeadEnd(
            DetalleCargaHhppMgl detalleCarga,
            String valor,
            String comunidad) {
        try {
            if (detalleCarga.getHhppMgl() != null) {
                comunidad = comunidad == null || comunidad.trim().isEmpty() ? "" : comunidad;

                String auxHeadEnd = detalleCarga.getHhppMgl().getHhpHeadEnd() == null
                        ? "" : detalleCarga.getHhppMgl().getHhpHeadEnd();
                String aux = detalleCarga.getHhppMgl().getHhpComunidad() == null
                        ? "" : detalleCarga.getHhppMgl().getHhpComunidad();

                if (!valor.equalsIgnoreCase(auxHeadEnd)) {
                    if (!comunidad.equalsIgnoreCase(aux)) {
                        detalleCarga.addDetalle("El HhpHeadEnd:" + valor + " no se puede validar porque "
                                + " la comunidad del registro no corresponde "
                                + " al hhpp:" + detalleCarga.getHhppMgl().getHhpId() + "  ", DetalleCargaHhppMgl.DETALLEWARNING);

                    } else {
                        String headEnd = getDireccionRrManager().traerHeadEnd(detalleCarga.getHhppMgl().getHhpComunidad());
                        if (!valor.equalsIgnoreCase(headEnd)) {
                            detalleCarga.addDetalle(" Se cambió el headEnd: "
                                    + headEnd + " por : " + valor, DetalleCargaHhppMgl.DETALLEINFO);
                            detalleCarga.getHhppMgl().setHhpHeadEnd(valor);
                            if (detalleCarga.getEstado().equals(DetalleCargaHhppMgl.NOPROCESADO)
                                    || detalleCarga.getEstado().equals(DetalleCargaHhppMgl.PROCESADO_INCOMPLETO)
                                    ||detalleCarga.getEstado().equals(DetalleCargaHhppMgl.PROCESADO_SINCAMBIO)) {
                                detalleCarga.setEstado(DetalleCargaHhppMgl.PROCESADO_INCOMPLETO);
                            } else {
                                detalleCarga.setEstado(DetalleCargaHhppMgl.PROCESADO);
                            }
                        } else {
                            detalleCarga.addDetalle("El Head-End " + valor + " a cambiar, no corresponde a la comunidad", DetalleCargaHhppMgl.DETALLEWARNING);

                        }
                    }
                }

            } else {
                detalleCarga.addDetalle("No se pudo procesar HhpHeadEnd", DetalleCargaHhppMgl.DETALLEWARNING);

            }
        } catch (Exception e) {
            String msg = "No se pudo procesar HhpHeadEnd: " + e.getMessage();
            LOGGER.error(msg, e);
            
            detalleCarga.addDetalle(msg, DetalleCargaHhppMgl.DETALLEWARNING);
            if (detalleCarga.getEstado().equals(DetalleCargaHhppMgl.PROCESADO)
                    || detalleCarga.getEstado().equals(DetalleCargaHhppMgl.PROCESADO_INCOMPLETO) 
                    ||detalleCarga.getEstado().equals(DetalleCargaHhppMgl.PROCESADO_SINCAMBIO)) {
                detalleCarga.setEstado(DetalleCargaHhppMgl.PROCESADO_INCOMPLETO);
            } else {
                detalleCarga.setEstado(DetalleCargaHhppMgl.NOPROCESADO);
            }
        }
    }

    /**
     * Procesar el HhpVendedor
     * <p>
     * Se verifica si le valor es válido
     *
     * @author becerraarmr
     * @param detalleCarga control del proceso
     * @param valor valor de HhpVendedor
     */
    protected void procesarHhpVendedor(
            DetalleCargaHhppMgl detalleCarga,
            String valor) {
        try {
            if (detalleCarga.getHhppMgl() != null) {
                String hhppVendedor = detalleCarga.getHhppMgl().getHhpVendedor() == null ? ""
                        : detalleCarga.getHhppMgl().getHhpVendedor();
                if (!hhppVendedor.equalsIgnoreCase(valor)) {
                    detalleCarga.addDetalle(" Se cambio vendedor:"
                            + hhppVendedor + " por:" + valor, DetalleCargaHhppMgl.DETALLEINFO);
                    detalleCarga.getHhppMgl().setHhpVendedor(valor);
                    if (detalleCarga.getEstado().equals(DetalleCargaHhppMgl.NOPROCESADO)
                            || detalleCarga.getEstado().equals(DetalleCargaHhppMgl.PROCESADO_INCOMPLETO)
                            ||detalleCarga.getEstado().equals(DetalleCargaHhppMgl.PROCESADO_SINCAMBIO)) {
                        detalleCarga.setEstado(DetalleCargaHhppMgl.PROCESADO_INCOMPLETO);
                    } else {
                        detalleCarga.setEstado(DetalleCargaHhppMgl.PROCESADO);
                    }
                }

            }
        } catch (Exception e) {
            String msg = "No se pudo procesar HhpVendedor " + e.getMessage();
            LOGGER.error(msg, e);
            
            detalleCarga.addDetalle(msg, DetalleCargaHhppMgl.DETALLEWARNING);
            if (detalleCarga.getEstado().equals(DetalleCargaHhppMgl.PROCESADO)
                    || detalleCarga.getEstado().equals(DetalleCargaHhppMgl.PROCESADO_INCOMPLETO) 
                    ||detalleCarga.getEstado().equals(DetalleCargaHhppMgl.PROCESADO_SINCAMBIO)) {
                detalleCarga.setEstado(DetalleCargaHhppMgl.PROCESADO_INCOMPLETO);
            } else {
                detalleCarga.setEstado(DetalleCargaHhppMgl.NOPROCESADO);
            }

        }
    }

    /**
     * Procesar HhpTipo
     * <p>
     * Se verifica si le valor es válido
     *
     * @author becerraarmr
     * @param detalleCarga control del proceso
     * @param valor valor de HhpTipo
     */
    protected void procesarHhpTipo(
            DetalleCargaHhppMgl detalleCarga,
            String valor) {
        try {
            if (detalleCarga.getHhppMgl() != null) {
                String hhppTipo = detalleCarga.getHhppMgl().getHhpTipo() == null
                        ? "" : detalleCarga.getHhppMgl().getHhpTipo();
                valor = valor == null ? "" : valor;
                if (!hhppTipo.equalsIgnoreCase(valor)) {
                    detalleCarga.addDetalle(" Se cambio tipo:"
                            + hhppTipo + " por:" + valor, DetalleCargaHhppMgl.DETALLEINFO);
                    detalleCarga.getHhppMgl().setHhpTipo(valor);
                    if (detalleCarga.getEstado().equals(DetalleCargaHhppMgl.NOPROCESADO)
                            || detalleCarga.getEstado().equals(DetalleCargaHhppMgl.PROCESADO_INCOMPLETO) 
                            ||detalleCarga.getEstado().equals(DetalleCargaHhppMgl.PROCESADO_SINCAMBIO)) {
                        detalleCarga.setEstado(DetalleCargaHhppMgl.PROCESADO_INCOMPLETO);
                    } else {
                        detalleCarga.setEstado(DetalleCargaHhppMgl.PROCESADO);
                    }
                }

            }
        } catch (Exception e) {
            String msg = "No se pudo procesar HhpTipo: " + e.getMessage();
            LOGGER.error(msg, e);
            
            detalleCarga.addDetalle(msg, DetalleCargaHhppMgl.DETALLEWARNING);
            if (detalleCarga.getEstado().equals(DetalleCargaHhppMgl.PROCESADO)
                    || detalleCarga.getEstado().equals(DetalleCargaHhppMgl.PROCESADO_INCOMPLETO) 
                    ||detalleCarga.getEstado().equals(DetalleCargaHhppMgl.PROCESADO_SINCAMBIO)) {
                detalleCarga.setEstado(DetalleCargaHhppMgl.PROCESADO_INCOMPLETO);
            } else {
                detalleCarga.setEstado(DetalleCargaHhppMgl.NOPROCESADO);
            }
        }
    }

    /**
     * Procesar el HhpTipoUnidad (tipo vivienda)
     * <p>
     * Se verifica si le valor es válido
     *
     * @author Juan David Hernandez
     * @param detalleCarga control del proceso
     * @param valor valor de HhpTipoUnidad (tipo vivienda)
     */
    protected void procesarHhpTipoVivienda(
            DetalleCargaHhppMgl detalleCarga, String valor) {
        try {
            TipoHhppMglManager tipoHhppMglManager = new TipoHhppMglManager();
            List<TipoHhppMgl> tipoViviendaList = tipoHhppMglManager.findAll();

            if (tipoViviendaList != null && !tipoViviendaList.isEmpty()
                    && valor != null && !valor.isEmpty()) {

                boolean tipoVivienda = false;
                for (TipoHhppMgl tipoHhppMgl : tipoViviendaList) {
                    if (tipoHhppMgl.getThhID().equalsIgnoreCase(valor)) {
                        tipoVivienda = true;
                        break;
                    }
                }
                if (!tipoVivienda) {
                    detalleCarga.addDetalle("El valor ingresado para el cambio no corresponde"
                            + " como un tipo de vivienda", DetalleCargaHhppMgl.DETALLEWARNING);
                   detalleCarga.setEstado(DetalleCargaHhppMgl.PROCESADO_SINCAMBIO);
                   
                }

                if (detalleCarga.getHhppMgl() != null && tipoVivienda) {
                    String hhppTipoVivienda = detalleCarga.getHhppMgl().getThhId() == null
                            ? "" : detalleCarga.getHhppMgl().getThhId();
                    valor = valor == null ? "" : valor;
                    if (!hhppTipoVivienda.equalsIgnoreCase(valor)) {
                        detalleCarga.addDetalle(" Se cambio tipo: "
                                + hhppTipoVivienda + " por:" + valor, DetalleCargaHhppMgl.DETALLEINFO);
                        detalleCarga.getHhppMgl().setThhId(valor.toUpperCase());
                        //si tiene valor en tipo de unidad de RR.
                        if (detalleCarga.getHhppMgl() != null && detalleCarga.getHhppMgl().getHhpTipoUnidad() != null
                                && !detalleCarga.getHhppMgl().getHhpTipoUnidad().isEmpty()) {
                            detalleCarga.getHhppMgl().setHhpTipoUnidad(valor.toUpperCase());
                        }
                        detalleCarga.setEstado(DetalleCargaHhppMgl.PROCESADO);
                        
                        //Valida si RR se encuentra encendido o apagado para realizar las operaciones en RR
                        boolean habilitarRR = false;
                        ParametrosMglManager parametrosMglManager = new ParametrosMglManager();
                        ParametrosMgl parametroHabilitarRR = parametrosMglManager.findParametroMgl(Constant.HABILITAR_RR);
                        if (parametroHabilitarRR != null && parametroHabilitarRR.getParValor().equalsIgnoreCase("1")) {
                            habilitarRR = true;
                        }
                        
                        //Si el hhpp tiene id de RR
                        if (habilitarRR && detalleCarga.getHhppMgl().getHhpIdrR() != null
                                && !detalleCarga.getHhppMgl().getHhpIdrR().isEmpty()) {
                            DireccionRRManager direccionRrManager = new DireccionRRManager(true);
                            HhppResponseRR responseHhppRR = direccionRrManager.getHhppByIdRR(detalleCarga.getHhppMgl().getHhpIdrR());
                            
                            if (responseHhppRR.getTipoMensaje() != null
                                    && responseHhppRR.getTipoMensaje().equalsIgnoreCase("I")) {
                                
                                HhppMgl hhppMglActualizado = new HhppMgl();
                                hhppMglActualizado.setHhpComunidad(responseHhppRR.getComunidad());
                                hhppMglActualizado.setHhpDivision(responseHhppRR.getDivision());
                                hhppMglActualizado.setHhpCalle(responseHhppRR.getStreet());
                                hhppMglActualizado.setHhpPlaca(responseHhppRR.getHouse());
                                hhppMglActualizado.setHhpApart(responseHhppRR.getApartamento());                                
                                hhppMglActualizado.setThhId(valor.toUpperCase());
                                
                                direccionRrManager.cambioTipoUnidadViviendaHhppRR(hhppMglActualizado);                                
                                
                            } else {
                                    LOGGER.error("Ocurrió un error consultando la data del hhpp en RR");                               
                            }
                        }                        
                    }else{
                        detalleCarga.addDetalle(" No se proceso el registro ya que no se evidenció cambio alguno",
                                DetalleCargaHhppMgl.DETALLEINFO);
                        detalleCarga.setEstado(DetalleCargaHhppMgl.NOPROCESADO);
                    }
                }
            }else{
                detalleCarga.addDetalle(" No se proceso el registro ya que el valor se encuentra vacio",
                        DetalleCargaHhppMgl.DETALLEINFO);
                detalleCarga.setEstado(DetalleCargaHhppMgl.NOPROCESADO);
            }


    
        } catch (Exception e) {
            String msg = "No se pudo procesar HhhpTipo: " + e.getMessage();
            LOGGER.error(msg, e);
            
            detalleCarga.addDetalle(msg, DetalleCargaHhppMgl.DETALLEWARNING);
            if (detalleCarga.getEstado().equals(DetalleCargaHhppMgl.PROCESADO)
                    || detalleCarga.getEstado().equals(DetalleCargaHhppMgl.PROCESADO_INCOMPLETO) 
                    ||detalleCarga.getEstado().equals(DetalleCargaHhppMgl.PROCESADO_SINCAMBIO)) {
                detalleCarga.setEstado(DetalleCargaHhppMgl.PROCESADO_INCOMPLETO);
            } else {
                detalleCarga.setEstado(DetalleCargaHhppMgl.NOPROCESADO);
            }
        }
    }

    /**
     * Procesar HhpEdificio
     * <p>
     * Se verifica si le valor es válido
     *
     * @author becerraarmr
     * @param detalleCarga control del proceso
     * @param valor valor de HhpEdificio
     */
    protected void procesarHhpEdificio(
            DetalleCargaHhppMgl detalleCarga,
            String valor) {
        try {
            if (valor != null && valor.isEmpty() != true) {
                String hhppEdificio = detalleCarga.getHhppMgl().getHhpEdificio() == null
                        ? "" : detalleCarga.getHhppMgl().getHhpEdificio();
                if (!hhppEdificio.equalsIgnoreCase(valor)) {
                    detalleCarga.addDetalle(" Se cambio edificio:"
                            + hhppEdificio + " por:" + valor, DetalleCargaHhppMgl.DETALLEINFO);
                    detalleCarga.getHhppMgl().setHhpEdificio(valor);
                }
            } else {
                detalleCarga.addDetalle("No se pudo procesar HhpEdificio", DetalleCargaHhppMgl.DETALLEWARNING);

            }

        } catch (Exception e) {
            String msg = "No se pudo procesar HhppEdificio: " + e.getMessage();
            LOGGER.error(msg, e);
            
            detalleCarga.addDetalle(msg, DetalleCargaHhppMgl.DETALLEWARNING);

        }
    }

    /**
     * Procesar HhpTipoUnidad
     * <p>
     * Se verifica si le valor es válido
     *
     * @author becerraarmr
     * @param detalleCarga control del proceso
     * @param valor valor de HhpTipoUnidad
     */
    protected void procesarHhpTipoUnidad(
            DetalleCargaHhppMgl detalleCarga,
            String valor) {
        try {
            if (detalleCarga.getHhppMgl() != null) {
                String hhppTipoUnidad = detalleCarga.getHhppMgl().getHhpTipoUnidad() == null
                        ? "" : detalleCarga.getHhppMgl().getHhpTipoUnidad();
                if (!hhppTipoUnidad.equalsIgnoreCase(valor)) {
                    detalleCarga.addDetalle(" Se cambio Tipo de Unidad:"
                            + hhppTipoUnidad + " por:" + valor, DetalleCargaHhppMgl.DETALLEINFO);
                    detalleCarga.getHhppMgl().setHhpTipoUnidad(valor);
                    if (detalleCarga.getEstado().equals(DetalleCargaHhppMgl.NOPROCESADO)
                            || detalleCarga.getEstado().equals(DetalleCargaHhppMgl.PROCESADO_INCOMPLETO)
                            ||detalleCarga.getEstado().equals(DetalleCargaHhppMgl.PROCESADO_SINCAMBIO)) {
                        detalleCarga.setEstado(DetalleCargaHhppMgl.PROCESADO_INCOMPLETO);
                    } else {
                        detalleCarga.setEstado(DetalleCargaHhppMgl.PROCESADO);
                    }
                }
            }
        } catch (Exception e) {
            String msg = "No se pudo procesar Tipo de Unidad: " + e.getMessage();
            LOGGER.error(msg, e);
            
            detalleCarga.addDetalle(msg, DetalleCargaHhppMgl.DETALLEWARNING);
            if (detalleCarga.getEstado().equals(DetalleCargaHhppMgl.PROCESADO)
                    || detalleCarga.getEstado().equals(DetalleCargaHhppMgl.PROCESADO_INCOMPLETO)
                    ||detalleCarga.getEstado().equals(DetalleCargaHhppMgl.PROCESADO_SINCAMBIO)) {
                detalleCarga.setEstado(DetalleCargaHhppMgl.PROCESADO_INCOMPLETO);
            } else {
                detalleCarga.setEstado(DetalleCargaHhppMgl.NOPROCESADO);
            }
        }
    }

    /**
     * Procesar HhpTipoCblAcometida
     * <p>
     * Se verifica si le valor es válido
     *
     * @author becerraarmr
     * @param detalleCarga control del proceso
     * @param valor valor de HhpTipoCblAcometida
     */
    protected void procesarHhpTipoCblAcometida(
            DetalleCargaHhppMgl detalleCarga,
            String valor) {

        try {
            if (valor != null && valor.isEmpty() != true) {
                String hhppTipoCblAcometida = detalleCarga.getHhppMgl().getHhpTipoCblAcometida() == null
                        ? "" : detalleCarga.getHhppMgl().getHhpTipoCblAcometida();
                if (!hhppTipoCblAcometida.equalsIgnoreCase(valor)) {
                    detalleCarga.addDetalle(" Se cambio tipo cable acometida:"
                            + hhppTipoCblAcometida + " por:" + valor, DetalleCargaHhppMgl.DETALLEINFO);
                    detalleCarga.getHhppMgl().setHhpTipoCblAcometida(valor);
                    if (detalleCarga.getEstado().equals(DetalleCargaHhppMgl.NOPROCESADO)
                            || detalleCarga.getEstado().equals(DetalleCargaHhppMgl.PROCESADO_INCOMPLETO)
                            ||detalleCarga.getEstado().equals(DetalleCargaHhppMgl.PROCESADO_SINCAMBIO)) {
                        detalleCarga.setEstado(DetalleCargaHhppMgl.PROCESADO_INCOMPLETO);
                    } else {
                        detalleCarga.setEstado(DetalleCargaHhppMgl.PROCESADO);
                    }
                }

            }
        } catch (Exception e) {
            String msg = "No se pudo procesar HhpTipoCblAcometida: " + e.getMessage();
            LOGGER.error(msg, e);
            
            detalleCarga.addDetalle(msg, DetalleCargaHhppMgl.DETALLEWARNING);
            if (detalleCarga.getEstado().equals(DetalleCargaHhppMgl.PROCESADO)
                    || detalleCarga.getEstado().equals(DetalleCargaHhppMgl.PROCESADO_INCOMPLETO) 
                    ||detalleCarga.getEstado().equals(DetalleCargaHhppMgl.PROCESADO_SINCAMBIO)) {
                detalleCarga.setEstado(DetalleCargaHhppMgl.PROCESADO_INCOMPLETO);
            } else {
                detalleCarga.setEstado(DetalleCargaHhppMgl.NOPROCESADO);
            }
        }
    }
    
     /**
     * Procesar Etiquetas de hhpp
     * <p>
     * Se verifica si le valor es válido
     *
     * @author cardenaslb
     * @param marcaId  identificador de la marac
     * @param valorNew valor marca nuevo
     * @param valorAnt valor marca actual
     * @param detalleCarga control del proceso
     * @param tipoReporte
     */
    protected void procesarHhpMarcas(
            DetalleCargaHhppMgl detalleCarga,
            String valorNew, String valorAnt, String marcaId, int tipoReporte){
       
        try {

            MarcasMglManager marcasMglManager = new MarcasMglManager();
            MarcasHhppMglManager marcasHhppMglManager = new MarcasHhppMglManager();

            if (valorNew != null && !valorNew.isEmpty()) {
                //Solicitaron cambio de etiqueta
                if (valorNew.equalsIgnoreCase(valorAnt)) {
                    if (tipoReporte == 1) {
                        //Reporte General
                        //Se busca la marca a agregar
                        MarcasMgl marcaNew = marcasMglManager.findMarcasMglByCodigo(valorNew);
                        // actualizacion de la etiqueta 
                        if (marcaNew != null) {

                            if (marcaId != null && !marcaId.isEmpty()) {
                                //Busco la marca asociada al hhpp
                                MarcasHhppMgl marcasHhppUpd = marcasHhppMglManager.finfById(new BigDecimal(marcaId));

                                if (marcasHhppUpd != null) {

                                    marcasHhppUpd.setMarId(marcaNew);
                                    marcasHhppUpd = marcasHhppMglManager.update(marcasHhppUpd);

                                    if (marcasHhppUpd.getMarId().getMarId().compareTo(marcaNew.getMarId()) == 0) {
                                        //Actualizacion satisfactoria de la marca asociada al hhpp

                                        boolean enviarInformacionRR = false;
                                        ParametrosMgl parametroHabilitarRR = parametrosMglManager.findParametroMgl(Constant.HABILITAR_RR);
                                        if (parametroHabilitarRR != null && parametroHabilitarRR.getParValor().equalsIgnoreCase(Constant.RR_HABILITADO)) {
                                            enviarInformacionRR = true;
                                        }
                                        if (enviarInformacionRR
                                                && detalleCarga.getHhppMgl() != null
                                                && detalleCarga.getHhppMgl().getHhpIdrR() != null
                                                && !detalleCarga.getHhppMgl().getHhpIdrR().isEmpty()) {
                                            //Actualizo en RR
                                            DireccionRRManager drrm = new DireccionRRManager(true);
                                            //se busca de nuevo el hhpp para tener todas las marcas actualizadas al instante  
                                            List<MarcasMgl> marcasMgls = marcasMglManager.findMarcasMglByHhpp(detalleCarga.getHhppMgl());
                                            if (marcasMgls != null && !marcasMgls.isEmpty()) {
                                                drrm.editarMarcasHhppRR(detalleCarga.getHhppMgl(), marcasMgls);
                                                detalleCarga.addDetalle("Se actualizaron correctamente las marcas del hhpp: " + detalleCarga.getHhppMgl().getHhpId() + " en MGL y en RR. ", DetalleCargaHhppMgl.DETALLEINFO);
                                                detalleCarga.setEstado(DetalleCargaHhppMgl.PROCESADO);
                                            } else {
                                                //No se encontraron marcas asociadas al hhpp para actualizar 
                                                detalleCarga.addDetalle("No se encontraron marcas asociadas al hhpp: " + detalleCarga.getHhppMgl().getHhpId() + " para actualizar en RR. ", DetalleCargaHhppMgl.DETALLEWARNING);
                                                detalleCarga.setEstado(DetalleCargaHhppMgl.PROCESADO_INCOMPLETO);
                                            }
                                        } else {
                                            detalleCarga.addDetalle("Se actualizaron correctamente las marcas del hhpp: " + detalleCarga.getHhppMgl().getHhpId() + " en MGL. ", DetalleCargaHhppMgl.DETALLEINFO);
                                            detalleCarga.setEstado(DetalleCargaHhppMgl.PROCESADO);
                                        }
                                    } else {
                                        //Error actualizando la marca asociada al hhpp
                                        detalleCarga.addDetalle("No se pudo actualizar las marcas del hhpp: " + detalleCarga.getHhppMgl().getHhpId() + " en MGL. ", DetalleCargaHhppMgl.DETALLEWARNING);
                                        detalleCarga.setEstado(DetalleCargaHhppMgl.PROCESADO_SINCAMBIO);
                                    }
                                } else {
                                    //No existe marca asociada al hhpp para actualizar
                                    detalleCarga.addDetalle("No existe la marca No " + marcaId + "asociada al hhpp: " + detalleCarga.getHhppMgl().getHhpId() + "", DetalleCargaHhppMgl.DETALLEWARNING);
                                    detalleCarga.setEstado(DetalleCargaHhppMgl.PROCESADO_SINCAMBIO);
                                }
                            } else {
                                //No viene el id de la marca en el archivo se procede a agregar la marca                         
                                List<MarcasMgl> listaMarcasMgl = new ArrayList<>();
                                //Agrega la nueva marca a la lista en la primera posicion (Marca que quedara como UniteProblemCode Actual)
                                listaMarcasMgl.add(marcaNew);
                                //Se agregan las marcas     
                                CmtDefaultBasicResponse response = getHhppManager().agregarMarcasHhpp(detalleCarga.getHhppMgl(), listaMarcasMgl, "MER_USER");
                                if (response.getMessageType().equalsIgnoreCase("I")) {
                                    //Agrego la marca correctamente
                                    detalleCarga.addDetalle(response.getMessage(), DetalleCargaHhppMgl.DETALLEINFO);
                                    detalleCarga.setEstado(DetalleCargaHhppMgl.PROCESADO);
                                } else {
                                    //Ocurrio un error agregando la marca 
                                    detalleCarga.addDetalle(response.getMessage(), DetalleCargaHhppMgl.DETALLEWARNING);
                                    detalleCarga.setEstado(DetalleCargaHhppMgl.PROCESADO_INCOMPLETO);
                                }
                            }
                        } else {
                            //No existe la marca en el sistema
                            detalleCarga.addDetalle("Las marca nueva: " + valorNew + " no EXISTE en el sistema. ", DetalleCargaHhppMgl.DETALLEWARNING);
                            detalleCarga.setEstado(DetalleCargaHhppMgl.PROCESADO_SINCAMBIO);
                        }
                    } else {
                        //Validamos si son iguales las etiquetas
                        detalleCarga.addDetalle("Las marca nueva: " + valorNew + " es igual a la anterior: " + valorAnt + " no se producen cambios. ", DetalleCargaHhppMgl.DETALLEWARNING);
                        detalleCarga.setEstado(DetalleCargaHhppMgl.PROCESADO_SINCAMBIO);
                    }

                } else {
                    //Se busca la marca a agregar
                    MarcasMgl marcaNew = marcasMglManager.findMarcasMglByCodigo(valorNew);
                    // actualizacion de la etiqueta 
                    if (marcaNew != null) {

                        if (marcaId != null && !marcaId.isEmpty()) {
                            //Busco la marca asociada al hhpp
                            MarcasHhppMgl marcasHhppUpd = marcasHhppMglManager.finfById(new BigDecimal(marcaId));

                            if (marcasHhppUpd != null) {

                                marcasHhppUpd.setMarId(marcaNew);
                                marcasHhppUpd = marcasHhppMglManager.update(marcasHhppUpd);

                                if (marcasHhppUpd.getMarId().getMarId().compareTo(marcaNew.getMarId()) == 0) {
                                    //Actualizacion satisfactoria de la marca asociada al hhpp

                                    boolean enviarInformacionRR = false;
                                    ParametrosMgl parametroHabilitarRR = parametrosMglManager.findParametroMgl(Constant.HABILITAR_RR);
                                    if (parametroHabilitarRR != null && parametroHabilitarRR.getParValor().equalsIgnoreCase(Constant.RR_HABILITADO)) {
                                        enviarInformacionRR = true;
                                    }
                                    if (enviarInformacionRR
                                            && detalleCarga.getHhppMgl() != null
                                            && detalleCarga.getHhppMgl().getHhpIdrR() != null
                                            && !detalleCarga.getHhppMgl().getHhpIdrR().isEmpty()) {
                                        //Actualizo en RR
                                        DireccionRRManager drrm = new DireccionRRManager(true);
                                        //se busca de nuevo el hhpp para tener todas las marcas actualizadas al instante  
                                        List<MarcasMgl> marcasMgls = marcasMglManager.findMarcasMglByHhpp(detalleCarga.getHhppMgl());
                                        if (marcasMgls != null && !marcasMgls.isEmpty()) {
                                            drrm.editarMarcasHhppRR(detalleCarga.getHhppMgl(), marcasMgls);
                                            detalleCarga.addDetalle("Se actualizaron correctamente las marcas del hhpp: " + detalleCarga.getHhppMgl().getHhpId() + " en MGL y en RR. ", DetalleCargaHhppMgl.DETALLEINFO);
                                            detalleCarga.setEstado(DetalleCargaHhppMgl.PROCESADO);
                                        } else {
                                            //No se encontraron marcas asociadas al hhpp para actualizar 
                                            detalleCarga.addDetalle("No se encontraron marcas asociadas al hhpp: " + detalleCarga.getHhppMgl().getHhpId() + " para actualizar en RR. ", DetalleCargaHhppMgl.DETALLEWARNING);
                                            detalleCarga.setEstado(DetalleCargaHhppMgl.PROCESADO_INCOMPLETO);
                                        }
                                    } else {
                                        detalleCarga.addDetalle("Se actualizaron correctamente las marcas del hhpp: " + detalleCarga.getHhppMgl().getHhpId() + " en MGL. ", DetalleCargaHhppMgl.DETALLEINFO);
                                        detalleCarga.setEstado(DetalleCargaHhppMgl.PROCESADO);
                                    }
                                } else {
                                    //Error actualizando la marca asociada al hhpp
                                    detalleCarga.addDetalle("No se pudo actualizar las marcas del hhpp: " + detalleCarga.getHhppMgl().getHhpId() + " en MGL. ", DetalleCargaHhppMgl.DETALLEWARNING);
                                    detalleCarga.setEstado(DetalleCargaHhppMgl.PROCESADO_SINCAMBIO);
                                }
                            } else {
                                //No existe marca asociada al hhpp para actualizar
                                detalleCarga.addDetalle("No existe la marca No " + marcaId + "asociada al hhpp: " + detalleCarga.getHhppMgl().getHhpId() + "", DetalleCargaHhppMgl.DETALLEWARNING);
                                detalleCarga.setEstado(DetalleCargaHhppMgl.PROCESADO_SINCAMBIO);
                            }
                        } else {
                            //No viene el id de la marca en el archivo asociada al hhpp  
                            detalleCarga.addDetalle("No viene valor en el campo 'Etiqueta_Id' no se puede actualizar la marca", DetalleCargaHhppMgl.DETALLEWARNING);
                            detalleCarga.setEstado(DetalleCargaHhppMgl.PROCESADO_SINCAMBIO);
                        }

                    } else {
                        //No existe la marca en el sistema
                        detalleCarga.addDetalle("Las marca nueva: " + valorNew + " no EXISTE en el sistema. ", DetalleCargaHhppMgl.DETALLEWARNING);
                        detalleCarga.setEstado(DetalleCargaHhppMgl.PROCESADO_SINCAMBIO);
                    }
                }

            } else if (marcaId != null && !marcaId.isEmpty()) {
                //Solicitaron borrar etiqueta
                //Busco la marca asociada al hhpp
                MarcasHhppMgl marcasHhppDel = marcasHhppMglManager.finfById(new BigDecimal(marcaId));
                if (marcasHhppDel != null && marcasHhppDel.getMarId() != null) {
                    MarcasMgl marcasMglEli = marcasHhppDel.getMarId();
                    List<MarcasMgl> lstMarcasMgls = new ArrayList<>();
                    lstMarcasMgls.add(marcasMglEli);
                    //Se agregan las marcas     
                    CmtDefaultBasicResponse response = getHhppManager().eliminarMarcasHhpp(detalleCarga.getHhppMgl(), lstMarcasMgls);
                    if (response.getMessageType().equalsIgnoreCase("I")) {
                        //Elimino la marca correctamente
                        detalleCarga.addDetalle(response.getMessage(), DetalleCargaHhppMgl.DETALLEINFO);
                        detalleCarga.setEstado(DetalleCargaHhppMgl.PROCESADO);
                    } else {
                        //Ocurrio un error eliminando la marca 
                        detalleCarga.addDetalle(response.getMessage(), DetalleCargaHhppMgl.DETALLEWARNING);
                        detalleCarga.setEstado(DetalleCargaHhppMgl.PROCESADO_INCOMPLETO);
                    }
                }
            }
        } catch (Exception e) {
            String msg = "No se pudo procesar la actualizacion de la Etiqueta: " + e.getMessage();
            LOGGER.error(msg, e);
            detalleCarga.addDetalle(msg, DetalleCargaHhppMgl.DETALLEWARNING);
            detalleCarga.setEstado(DetalleCargaHhppMgl.PROCESADO_INCOMPLETO);
        }
    }

    /**
     * Procesar un item del vector Se llama al método encargado de procesar cada
     * uno de los items.
     *
     * @author becerraarmr
     * @param detalle control del proceso
     * @param i valor del indice
     * @param item vector con la data
     *
     * @return null o el DetalleCargaHhppMgl
     */
    private synchronized void procesarItemData(
            DetalleCargaHhppMgl detalle,
            int i,
            String[] item) {

        String valor = findValor(item, i);
        valor = valor == null ? "" : valor.trim();
        switch (i) {
            case 0: {//HhpIdrR
                procesarHhpp(detalle, valor);
                break;
            }
            case 1: {//HhpIdrR
                procesarHhpIdrR(detalle, valor);
                break;
            }
            case 2: {//Direccfion
                procesarHhpDireccion(detalle, valor);
                break;
            }

            case 3: {//Barrio
                procesarBarrio(detalle, valor);
                break;
            }
            case 6: {//HhpEstadoUnit           
                procesarHhpEstadoUnit(detalle, valor);
                break;
            }
            case 7: {//Niv_Socio
                procesarNivSocio(detalle, valor);
                break;
            }

            case 8: {//Estrato
                String estadoUnit = findValor(item, 6);//valor del estado
                procesarEstrato(detalle, valor, estadoUnit);
                datosModificados.append(valor);
                break;
            }
            case 9: {//HhpVendedor
                procesarHhpVendedor(detalle, valor);
                break;
            }
            case 10: {//HhpCodigoPostal
                String comunidad = findValor(item, 4);//Buscar valor comunidad
                procesarHhpCodigoPostal(detalle, valor, comunidad);
                break;
            }
            case 11: {//HhpTipoAcomet
                procesarTipoAcomet(detalle, valor);
                break;
            }
            case 12: {//HhpUltUbicacion
                procesarHhpUltUbicacion(detalle, valor);
                datosModificados.append("|");
                datosModificados.append(valor);
                break;
            }
            case 13: {//HhpHeadEnd
                String comunidad = findValor(item, 4);//Comunidad
                procesarHhpHeadEnd(detalle, valor, comunidad);
                break;
            }
            case 14: {//HhpTipo
                procesarHhpTipo(detalle, valor);
                break;
            }
            case 15: {//HhpTipoUnidad
                procesarHhpTipoUnidad(detalle, valor);
                break;
            }
            case 16: {//HhpTipoCblAcometida
                procesarHhpTipoCblAcometida(detalle, valor);
                break;
            }
             case 17: {//ETIQUETAS
                 String valorMarcaAct = findValor(item, 17);
                 String valorMarcaId = findValor(item, 19);
                 procesarHhpMarcas(detalle, valor,valorMarcaAct, valorMarcaId, 1);
                break;
            }
            case 18: {//notas
                procesarNotas(detalle, valor);
                break;
            }
        }
    }
    
    
    /**
     * Procesar un item del vector Se llama al método encargado de procesar cada
     * uno de los items de la reversion en un archivo general.
     *
     * @author bocanegra vm
     * @param detalle control del proceso
     * @param i valor del indice
     * @param item vector con la data
     * @param estadoUnit estado del hhpp
     * @return null o el DetalleCargaHhppMgl
     */
    private synchronized void procesarItemDataGral(
            DetalleCargaHhppMgl detalle,
            int i,
            String[] item, String estadoUnit) {

        String valor = findValor(item, i);
        valor = valor == null ? "" : valor.trim();
        switch (i) {

            case 2: {//Estrato
                procesarEstrato(detalle, valor, estadoUnit);
                break;
            }
            case 3: {//HhpUltUbicacion
                procesarHhpUltUbicacion(detalle, valor);
                break;
            }

        }
    }

    /**
     * Procesar un item del vector Se llama al método encargado de procesar cada
     * uno de los items. para los reportes detallados de estrato, estado o nivel
     *
     * @author Jonathan Peña
     * @param detalle control del proceso
     * @param i valor del indice
     * @param item vector con la data
     *
     * @return null o el DetalleCargaHhppMgl
     */
    private synchronized void procesarItemDataDetallados(
            DetalleCargaHhppMgl detalle,
            int i,
            String[] item) {

        String valor = findValor(item, i);
        valor = valor == null ? "" : valor.trim();

        if (tipoReporte.equals(Constant.CODIGO_BASICA_ESTRATO)) {
            if (i == 6) {
                String estadoUnit = findValor(item, 4);//valor del estado
                procesarEstrato(detalle, valor, estadoUnit);
                datosModificados.append(valor);
            }
        } else if (i == 5) {
            switch (tipoReporte) {
                case Constant.CODIGO_BASICA_ESTADO:
                    procesarHhpEstadoUnit(detalle, valor);
                    break;
                case Constant.CODIGO_BASICA_COBERTURA:
                    procesarHhpUltUbicacion(detalle, valor);
                    datosModificados.append(valor);
                    break;
                case Constant.CODIGO_BASICA_TIPO_VIVIENDA:
                    procesarHhpTipoVivienda(detalle, valor);
                    datosModificados.append(valor);
                    break;
                 case Constant.CODIGO_BASICA_ETIQUETA:
                      String valorMarcaAct = findValor(item, 4);
                      String valorMarcaId = findValor(item, 7);
                      procesarHhpMarcas(detalle, valor,valorMarcaAct,valorMarcaId, 2);                      
                      datosModificados.append(valor);
                    break;
                default:
                    break;
            }

        }
        if (!tipoReporte.equals(Constant.CODIGO_BASICA_ESTRATO) && i == 6) {
            procesarNotas(detalle, valor);
        } else if (tipoReporte.equals(Constant.CODIGO_BASICA_ESTRATO) && i == 7) {
            procesarNotas(detalle, valor);
        }

    }

    /**
     * Buscar valor item en un arreglo.
     * <p>
     * Busca el valor en el arreglo data, en el indice i.
     *
     * @author becerraarmr
     * @param data arreglo String con la data,
     * @param i indice a buscar.
     *
     * @return return null o el valor correspondiente.
     */
    private String findValor(String[] data, int i) {
        try {
            return data[i];
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            
            //Si hay algún error al buscar en el arreglo retorne null.
            return null;
        }
    }

    /**
     * Procesar los otros datos del registro
     * <p>
     * Se procesan los campos faltantes del registro como: el vendedor, el tipo,
     * el edificio, el tipo de unidad y el tipo de cable acometida.
     *
     * @author becerraarmr
     * @param detalleCarga data con el hhpp y los detalles del caso.
     * @param item vector con la data encontrada en el registro del excel
     *
     * @return un objeto con DetalleCargaHhppMgl y sus cambio correspondientes.
     */
    protected DetalleCargaHhppMgl procesarOtrosHhppMgl(
            DetalleCargaHhppMgl detalleCarga,
            String[] item) {
        if (detalleCarga == null || item == null) {
            return null;
        }

        String detalle = detalleCarga.getDetalle();

        if (detalleCarga.getHhppMgl() != null) {
            int i = 11;
            while (i < 20) {
                if (!(i == 12 || i == 13 || i == 14 || i == 15)) {
                    item[i] = item[i] == null ? "" : item[i].trim();
                }
                i++;
            }
            String hhppVendedor = detalleCarga.getHhppMgl().getHhpVendedor() == null ? ""
                    : detalleCarga.getHhppMgl().getHhpVendedor();
            if (!item[11].equalsIgnoreCase(hhppVendedor)) {
                detalle += " Se cambio vendedor:"
                        + hhppVendedor + " por:" + item[11];
                detalleCarga.getHhppMgl().setHhpVendedor(item[11]);
            }
            String hhppTipo = detalleCarga.getHhppMgl().getHhpTipo() == null
                    ? "" : detalleCarga.getHhppMgl().getHhpTipo();
            if (!item[16].equalsIgnoreCase(hhppTipo)) {
                detalle += " Se cambio tipo:"
                        + hhppTipo + " por:" + item[16];
                detalleCarga.getHhppMgl().setHhpTipo(item[16]);
            }
            String hhppEdificio = detalleCarga.getHhppMgl().getHhpEdificio() == null
                    ? "" : detalleCarga.getHhppMgl().getHhpEdificio();
            if (!item[17].equalsIgnoreCase(hhppEdificio)) {
                detalle += " Se cambio edificio:"
                        + hhppEdificio + " por:" + item[17];
                detalleCarga.getHhppMgl().setHhpEdificio(item[17]);
            }
            String hhppTipoUnidad = detalleCarga.getHhppMgl().getHhpTipoUnidad() == null
                    ? "" : detalleCarga.getHhppMgl().getHhpTipoUnidad();
            if (!item[18].equalsIgnoreCase(hhppTipoUnidad)) {
                detalle += " Se cambio Tipo de Unidad:"
                        + hhppTipoUnidad + " por:" + item[18];
                detalleCarga.getHhppMgl().setHhpTipoUnidad(item[18]);
            }
            String hhppTipoCblAcometida = detalleCarga.getHhppMgl().getHhpTipoCblAcometida() == null
                    ? "" : detalleCarga.getHhppMgl().getHhpTipoCblAcometida();
            if (!item[19].equalsIgnoreCase(hhppTipoCblAcometida)) {
                detalle += " Se cambio tipo cable acometida:"
                        + hhppTipoCblAcometida + " por:" + item[19];
                detalleCarga.getHhppMgl().setHhpTipoCblAcometida(item[19]);
            }
        }
        detalleCarga.addDetalle(detalle, DetalleCargaHhppMgl.DETALLEINFO);
        return detalleCarga;
    }

    /**
     * Procesar las notas
     * <p>
     * Se procesa las notas que corresponden al registro y para el hhpp en
     * cuestión. Las notas son guardadas en una tabla aparte pero refenciada con
     *
     * @author becerraarmr
     * @param detalleCarga data con el hhpp y los detalles iniciales del cambio
     * @param notas valor a cambiar con los ajustes necesarios
     */
    protected void procesarNotas(
            DetalleCargaHhppMgl detalleCarga, String notas) {
        try {
            if (detalleCarga.getHhppMgl() != null && notas != null) {
                if (!notas.isEmpty()) {
                    NotasAdicionalesMgl notasMgl = new NotasAdicionalesMgl();
                    notasMgl.setHhppId(detalleCarga.getHhppMgl().getHhpId() + "");
                    notasMgl.setEstadoRegistro(1);
                    notasMgl.setFechaCreacion(new Date());
                    notasMgl.setNota(notas);
                    getNotasAdicionalesManager().create(notasMgl);
                    DireccionRRManager direccionRrManagerVal = new DireccionRRManager(true);
                    List<NotasAdicionalesMgl> nota = new ArrayList<NotasAdicionalesMgl>();
                    nota.add(notasMgl);
                    direccionRrManagerVal.agregarNotasHhppRR(detalleCarga.getHhppMgl(), nota);
                    detalleCarga.addDetalle(" Notas guardadas", DetalleCargaHhppMgl.DETALLEINFO);

                    if (detalleCarga.getEstado().equals(DetalleCargaHhppMgl.NOPROCESADO)
                            || detalleCarga.getEstado().equals(DetalleCargaHhppMgl.PROCESADO_INCOMPLETO) 
                            ||detalleCarga.getEstado().equals(DetalleCargaHhppMgl.PROCESADO_SINCAMBIO)) {
                        detalleCarga.setEstado(DetalleCargaHhppMgl.PROCESADO_INCOMPLETO);
                    } else {
                        detalleCarga.setEstado(DetalleCargaHhppMgl.PROCESADO);
                    }

                    //Persistir nota en MGL
                    CmtBasicaMgl tipoNota = getBasicaManager().findByCodigoInternoApp(Constant.BASICA_NOTA_HHHPP);
                    CmtNotasHhppVtMgl cmtNotasHhppVtMgl = new CmtNotasHhppVtMgl();
                    cmtNotasHhppVtMgl.setNota(notas);
                    cmtNotasHhppVtMgl.setDescripcion(notas);
                    cmtNotasHhppVtMgl.setTipoNotaObj(tipoNota);
                    cmtNotasHhppVtMgl.setHhppId(detalleCarga.getHhppMgl());

                    cmtNotasHhppVtMgl = cmtNotasHhppVtMglManager.create(cmtNotasHhppVtMgl, usuario, perfil);

                    CmtNotasHhppDetalleVtMgl cmtNotasHhppDetalleVtMgl = new CmtNotasHhppDetalleVtMgl();
                    cmtNotasHhppDetalleVtMgl.setNotaHhpp(cmtNotasHhppVtMgl);
                    cmtNotasHhppDetalleVtMgl.setNota(notas);
                    cmtNotasHhppDetalleVtMglManager.create(cmtNotasHhppDetalleVtMgl, usuario, perfil);

                }
            }
        } catch (ApplicationException e) {
            String msg = "No se puede registrar notas: " + e.getMessage();
            LOGGER.error(msg);
            
            detalleCarga.addDetalle(msg, DetalleCargaHhppMgl.DETALLEWARNING);
            if (detalleCarga.getEstado().equals(DetalleCargaHhppMgl.PROCESADO)
                    || detalleCarga.getEstado().equals(DetalleCargaHhppMgl.PROCESADO_INCOMPLETO)) {
                detalleCarga.setEstado(DetalleCargaHhppMgl.PROCESADO_INCOMPLETO);
            } else {
                detalleCarga.setEstado(DetalleCargaHhppMgl.NOPROCESADO);
            }
        }
    }

    /**
     * Procesar la dirección
     * <p>
     * Se procesa la dirección verificando el estrato y el nivel socio
     * económico.
     *
     * @author becerraarmr
     * @param detalleCarga data con el hhpp y el detalle del mismo.
     * @param estadoUnit estado de la unidad. Si estado ->'S' entonces no puede
     * cambiar estrato.
     * @param estrato estrato para actualizar
     * @param niv_socio data a cambiar
     *
     * @return un objeto DetalleCargaHhppMgl con la data y el resultado
     */
    protected DetalleCargaHhppMgl procesarDireccion(
            DetalleCargaHhppMgl detalleCarga,
            String estadoUnit,
            String estrato,
            String niv_socio) {

        if (detalleCarga == null) {
            return null;
        }

        if (detalleCarga.getHhppMgl() == null) {
            return detalleCarga;
        }

        if (detalleCarga.getHhppMgl().getDirId() != null) {
            DireccionMgl dir = new DireccionMgl();
            dir.setDirId(detalleCarga.getHhppMgl().getDirId());
            try {
                dir = getEjbDireccionMgl().findById(dir);
            } catch (ApplicationException e) {
                String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                LOGGER.error(msg);
                
                dir = null;
            }
            estadoUnit = estadoUnit == null ? "" : estadoUnit.trim();
            boolean cambioDir = false;
            String detalle = detalleCarga.getDetalle();
            if (dir != null) {
                if (estrato != null) {
                    estrato = estrato.trim();
                    BigDecimal estratoBigDecimal
                            = findBigDecimal(estrato);
                    if (estratoBigDecimal != null) {
                        Integer valInt = estratoBigDecimal.intValue();
                        Integer dirEstrato = null;
                        if (dir.getDirEstrato() != null) {
                            dirEstrato = dir.getDirEstrato().intValue();
                        }
                        if (!valInt.equals(dirEstrato)) {
                            if (!"S".equalsIgnoreCase(estadoUnit)) {
                                detalle += " Se cambió el estrato: "
                                        + dirEstrato
                                        + " por : " + valInt;
                                dir.setDirEstrato(new BigDecimal(valInt));
                                cambioDir = true;
                            } else {
                                detalle += " En estado S no se puede "
                                        + " hacer cambio de estrato.";
                            }
                        }
                    }
                }
                niv_socio = niv_socio == null ? "" : niv_socio.trim();
                BigDecimal niv_socioBigDecimal
                        = findBigDecimal(niv_socio);
                if (niv_socioBigDecimal != null) {
                    Integer nivSocioCambiar = niv_socioBigDecimal.intValue();
                    Integer dirNivelSocio = null;
                    if (dir.getDirNivelSocioecono() != null) {
                        dirNivelSocio = dir.getDirNivelSocioecono().intValue();
                    }
                    if (!nivSocioCambiar.equals(dirNivelSocio)) {
                        detalle += " Se cambió el nivel socio económico: "
                                + dirNivelSocio
                                + " por : " + nivSocioCambiar;
                        dir.setDirNivelSocioecono(new BigDecimal(nivSocioCambiar));
                        cambioDir = true;
                    }
                }
                if (cambioDir) {
                    updateDireccion(dir);
                }
            }
            detalleCarga.addDetalle(detalle, DetalleCargaHhppMgl.DETALLEINFO);
        }
        return detalleCarga;
    }

    /**
     * Procesar el estado de la unidad
     * <p>
     * Se procesa el estado de la unidad con los datos correspondientes y se
     * solicita la actualización del caso.
     *
     * @author becerraarmr
     * @param detalleCarga data con el hhpp correspondiente y la data actualizar
     * @param estadoUnit estado que se quiere actualizar.
     *
     * @return un objeto DetalleCargaHhppMgl con la data a actualizar y los
     * detalles
     */
    protected DetalleCargaHhppMgl procesarEstadoUnit(
            DetalleCargaHhppMgl detalleCarga, String estadoUnit) {
        if (detalleCarga == null) {
            return null;
        }

        if (detalleCarga.getHhppMgl() == null) {
            return detalleCarga;
        }
        estadoUnit = estadoUnit == null ? "" : estadoUnit.trim();
        String estadoUnitO = detalleCarga.getHhppMgl().getHhpEstadoUnit() == null
                ? "" : detalleCarga.getHhppMgl().getHhpEstadoUnit();
        String detalle = detalleCarga.getDetalle();
        if (!estadoUnitO.equalsIgnoreCase(estadoUnit)) {
            EstadoHhppMgl estaHhpp;
            try {
                estaHhpp = getEstadoHhppManager().find(estadoUnit.toUpperCase());
            } catch (ApplicationException e) {
                String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                LOGGER.error(msg);
                
                estaHhpp = null;
            }

            if (estaHhpp != null) {
                detalle += " Se cambió el estado: "
                        + estadoUnitO
                        + " por : " + estadoUnit;
                detalleCarga.getHhppMgl().setHhpEstadoUnit(estadoUnit);
                //TODO:revisar asignar
                detalleCarga.getHhppMgl().setEhhId(null);
            } else {
                detalle += "Estado " + estadoUnit + " no existe, por tanto no se puede"
                        + " hacer el cambio";
            }
        }
        detalleCarga.addDetalle(detalle, DetalleCargaHhppMgl.DETALLEINFO);
        return detalleCarga;
    }

    /**
     * Procesar el tipo de acometida
     * <p>
     * Se procesa el campo tipo de acometida que contiene el excel y se refleja
     * el ajuste en el parámetro de entrada hhppMglOriginal.
     *
     * @author becerraarmr
     * @param detalleCarga con la data para actualizar
     * @param hhpTipoAcomet valor que quiere actualizarse
     */
    protected void procesarTipoAcomet(
            DetalleCargaHhppMgl detalleCarga, String hhpTipoAcomet) {
        try {

            if (detalleCarga.getHhppMgl() != null) {

                hhpTipoAcomet = hhpTipoAcomet == null || hhpTipoAcomet.trim().isEmpty()
                        ? "" : hhpTipoAcomet.trim();

                String hhpTipoAcometO = detalleCarga.getHhppMgl().getHhpTipoAcomet() == null
                        ? "" : detalleCarga.getHhppMgl().getHhpTipoAcomet();

                if (!hhpTipoAcometO.equalsIgnoreCase(hhpTipoAcomet)) {
                    TipoHhppConexionMgl aux = getTipoHhppConexionManager().
                            findById(detalleCarga.getHhppMgl().getThcId());

                    if (aux != null) {
                        if (aux.getThcCodigo() != null) {
                            if (!aux.getThcCodigo().equalsIgnoreCase(hhpTipoAcomet)) {
                                detalleCarga.addDetalle(" Se cambió tipo de acometida: "
                                        + hhpTipoAcometO + " por tipo de acometida: "
                                        + hhpTipoAcomet, DetalleCargaHhppMgl.DETALLEINFO);
                                detalleCarga.getHhppMgl().setHhpTipoAcomet(hhpTipoAcomet);
                                if (detalleCarga.getEstado().equals(DetalleCargaHhppMgl.NOPROCESADO)
                                        || detalleCarga.getEstado().equals(DetalleCargaHhppMgl.PROCESADO_INCOMPLETO)
                                        ||detalleCarga.getEstado().equals(DetalleCargaHhppMgl.PROCESADO_SINCAMBIO)) {
                                    detalleCarga.setEstado(DetalleCargaHhppMgl.PROCESADO_INCOMPLETO);
                                } else {
                                    detalleCarga.setEstado(DetalleCargaHhppMgl.PROCESADO);
                                }
                            }
                        }
                    }
                }

            }
        } catch (ApplicationException e) {
            String msg = "No se pudo validar el Tipo de acometida: " + e.getMessage();
            LOGGER.error(msg);
            
            detalleCarga.addDetalle(msg, DetalleCargaHhppMgl.DETALLEWARNING);
            if (detalleCarga.getEstado().equals(DetalleCargaHhppMgl.PROCESADO)
                    || detalleCarga.getEstado().equals(DetalleCargaHhppMgl.PROCESADO_INCOMPLETO) 
                    ||detalleCarga.getEstado().equals(DetalleCargaHhppMgl.PROCESADO_SINCAMBIO)) {
                detalleCarga.setEstado(DetalleCargaHhppMgl.PROCESADO_INCOMPLETO);
            } else {
                detalleCarga.setEstado(DetalleCargaHhppMgl.NOPROCESADO);
            }
        }
    }

    /**
     * Procesar el hhpp Se verifica si le valor que llega es una id de un hhpp
     * válido.
     *
     * @author becerraarmr
     * @param detalleCarga control del proceso
     * @param valor valor del id hhpp
     *
     */
    protected void procesarHhpp(
            DetalleCargaHhppMgl detalleCarga,
            String valor) {
        if (detalleCarga != null) {
            try {
                BigDecimal idHhpp = findBigDecimal(valor);
                if (idHhpp != null) {
                    HhppMgl hhppMglVal = getHhppManager().findById(new BigDecimal(idHhpp.intValue()));
                    detalleCarga.setHhppMgl(hhppMglVal);
                }
            } catch (ApplicationException e) {
                String msg = "El hhpp:" + valor + " no es un valor válido: " + e.getMessage();
                LOGGER.error(msg);
                
                detalleCarga.addDetalle(msg, DetalleCargaHhppMgl.DETALLEWARNING);

            }
        }
    }

    /**
     * Actualizar objeto DireccionMgl
     * <p>
     * Se solicita la actualización del objeto DireccionMgl según el parámetro
     * ingresado.
     *
     * @author becerraarmr
     * @param direccionMgl objeto que va ser actualizado.
     * @return 
     */
    protected String updateDireccion(DireccionMgl direccionMgl) {
        try {
            getEjbDireccionMgl().update(direccionMgl);
        } catch (ApplicationException e) {
            String msg = "No se pude realizar la actualización de la dirección: " + e.getMessage();
            LOGGER.error(msg);
            return msg;
        }
        return null;
    }

    /**
     * Preparar la respuesta que va ser mostrada.
     * <p>
     * Prepara la respuesta en el workbook de tal forma que se edita el excel
     * con la información correspondiente de la presentación.
     *
     * @author becerraarmr
     * @param dataProcesada listado de objetos DetalleCargaHhppMgl con los hhpp
     * y sus acciones.
     */
    protected void prepararRespuesta(DetalleCargaHhppMgl dataProcesada) {
        if (workbook == null || dataProcesada == null) {
            return;
        }
        Sheet sheet = ((XSSFWorkbook) workbook).getSheetAt(0);
        if (sheet == null) {
            return;
        }
        Row row = sheet.getRow(dataProcesada.getRow());
        if (row == null) {
            return;
        }
        int tamHeader = headerSel.length;
        if (isHeaderDirecciones) {
            String[] geoData = dataProcesada.respuestaDirecciones();
            for (String item : geoData) {
                Cell cell = row.createCell(tamHeader);
                cell.setCellValue(item);
                tamHeader++;
            }
        }
        Cell cell = row.createCell(tamHeader);
        cell.setCellValue(dataProcesada.getEstado());
        tamHeader++;
        Cell detalle = row.createCell(tamHeader);
        detalle.setCellValue(dataProcesada.getDetalle());
        tamHeader++;
        Cell fechaProceso = row.createCell(tamHeader);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
        String fecha = sdf.format(dataProcesada.getFechaProcesamiento());
        fechaProceso.setCellValue(fecha);
        tamHeader++;
        if (isHeaderDirecciones) {
            tamHeader++;
            Cell cambio = row.createCell(tamHeader);
            cambio.setCellValue(dataProcesada.puedeActualizar());
        }
        Cell original = row.createCell(tamHeader);
        original.setCellValue(dataProcesada.getInfoOriginal());
        tamHeader++;
    }

    /**
     * Buscar el nombre del archivo.
     * <p>
     * Busca en el atributo part el nombre del archivo.
     *
     * @author becerraarmr
     * @return el String con el valor correspondiente.
     */
    protected String findFileName() {
        final String partHeader;
        partHeader = part.getHeader("content-disposition");
        LOGGER.info(String.format("Part Header = {%s}", partHeader));
        for (String content : part.
                getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    /**
     * Mostrar el valor del atributo msgProceso
     * <p>
     * Muestra el valor del atributo msgProceso, que corresponde al mensaje del
     * proceso que se ha realizado hasta el momento.
     *
     * @author becerraarmr
     *
     * @return el String con el valor que representa el atributo
     */
    public String getMsgProceso() {
        return msgProceso;
    }

    public void setMsgProceso(String msgProceso) {
        this.msgProceso = msgProceso;
    }

    /**
     * Mostrar el valor del atributo fileName
     * <p>
     * Muestra el valor del atributo fileName de tal forma que se muestre el
     * nombre del archivo.
     *
     * @author becerraarmr
     *
     * @return el String con el valor que representa el atributo
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Fija el nombre del archivo
     *
     * @param fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Mostrar el valor del atributo.
     * <p>
     * Muestra el valor del atributo workbook.
     *
     * @author becerraarmr
     *
     * @return Workbook con el valor que representa el atributo
     */

    /**
     * ver si archivo está procesado
     * <p>
     * Muestra el estado del atributo archivoProcesado
     *
     * @author becerraarmr
     * @return 
     *
     */
    public boolean isArchivoProcesado() {
        return archivoProcesado;
    }

    /**
     * Convertir los parámetros de entrada en un arreglo de objetos
     * <p>
     * Se convierten cada uno de los datos que contiene el parámetro hhppMgl con
     * direccionesMgl y Geografico, pasando cada uno de los datos a un arreglo
     * de objetos.
     *
     * @author bocanegravm
     * @param hhppMgl valor con la data principal
     * @param direccionMgl direccion relacionada con el hhppMgl
     *
     * @return un arreglo de objetos.
     * @throws co.com.claro.mgl.error.ApplicationException
     */
   public static Object[] valoresHeadersFin(HhppMgl hhppMgl,
          DireccionMgl direccionMgl) throws ApplicationException {
    
        String estrato = "";
        Object[] retornar = null;

        if (hhppMgl != null) {
            if (hhppMgl.getDireccionObj() != null && hhppMgl.getSubDireccionObj() != null) {
                estrato = hhppMgl.getSubDireccionObj() != null
                        ? ("NULL".equalsIgnoreCase(hhppMgl.getSubDireccionObj().getSdiEstrato() + "")
                        ? "NA" : hhppMgl.getSubDireccionObj().getSdiEstrato() + "") : "NA";
            } else if (hhppMgl.getDireccionObj() != null) {
                estrato = direccionMgl != null ? ("NULL".equalsIgnoreCase(direccionMgl.getDirEstrato() + "")
                        ? "NA" : direccionMgl.getDirEstrato() + "") : "NA";
            }
           
            CmtDireccionDetalleMglManager direccionDetalleMglManager = new CmtDireccionDetalleMglManager();
            CmtDireccionDetalladaMgl detalladaMgl = direccionDetalleMglManager.findByHhPP(hhppMgl);
            
            String marcasList="";
            String marcas;
            if(hhppMgl.getListMarcasHhpp() != null && !hhppMgl.getListMarcasHhpp().isEmpty()){
                for(MarcasHhppMgl marcasHhppMgl:hhppMgl.getListMarcasHhpp()){     
                    marcas = marcasHhppMgl.getMarId().getMarCodigo();
                    marcasList=marcasList+marcas+"-";
                }   
            }
            
            Object[] header = {
                hhppMgl.getHhpId(),
                detalladaMgl != null ? detalladaMgl.getDireccionDetalladaId(): "NA",
                estrato,
                hhppMgl.getHhpUltUbicacion() != null ? hhppMgl.getHhpUltUbicacion() : "NA",
                hhppMgl.getThhId() != null ? hhppMgl.getThhId() : "NA",
                !marcasList.isEmpty() ? marcasList : "NA"};

            retornar = header;

        }

        return retornar;
    }


    /**
     * Cargar los nombres de los filtros con que se hace el reporte.
     * <p>
     * Se carga cada uno de los valores que hacen parte del filtro en el que se
     * puede realizar reporte.
     *
     * @author becerraarmr
     * @return el String[] con los valores de los filtros.
     */
    public static String[] filtrosHeaders() {
        String[] filtrosHeaders = {"Tipo Reporte", "Tipo Tecnologia", "Departamento",
            "Ciudad", "Centro Poblado", "Nodo", "Atributo", "ValorAtributo"};
        return filtrosHeaders;
    }

    /**
     * Cargar la cabecera del archivo que se crea al realizar el reporte.
     * <p>
     * Se carga cada uno de los campos que tendrá cada columna como cabecera
     * para poder realizar el archivo de excel.
     *
     * @author becerraarmr
     * @return el String[] correspondiente.
     *
     */
    public static String[] headerHhpp() {
        String aux = headerHhppGeneral();
        return aux.split(",");
    }

    /**
     * Cargar la cabecera del archivo que se crea al realizar el reporte.
     * <p>
     * Se carga cada uno de los campos que tendrá cada columna como cabecera
     * para poder realizar el archivo de excel.
     *
     * @author becerraarmr
     * @return el String[] correspondiente.
     *
     */
    public static String[] headerHhppBasic() {
        String aux = headerHhppBasicSplit();
        return aux.split(",");
    }

    /**
     * Cargar la cabecera con inhabilitar del archivo que se crea al realizar el
     * reporte.
     * <p>
     * Se carga cada uno de los campos que tendrá cada columna como cabecera
     * para poder realizar el archivo de excel.
     *
     * @author becerraarmr
     * @return el String[] correspondiente.
     *
     */
    public static String[] headerHhppInhabilitar() {
        String aux = "Estado_OT,Inhabilitar";
        return aux.split(",");
    }

    /**
     * Cargar la cabecera general del archivo que se crea al realizar el
     * reporte.
     * <p>
     * Se carga cada uno de los campos que tendrá cada columna como cabecera
     * para poder realizar el archivo de excel.
     *
     * @author becerraarmr
     * @return el String[] correspondiente.
     *
     */
    public static String headerHhppGeneral() {
        String aux = "hhpId,HhpIdrR,HhpDireccion,Barrio,HhpComunidad,HhpDivision"
                + ",HhpEstadoUnit,Niv_Socio,Estrato,HhpVendedor,HhpCodigoPostal"
                + ",HhpTipoAcomet,NodoId,HhpHeadEnd,HhpTipo,HhpTipoUnidad"
                + ",HhpTipoCblAcometida,Etiqueta,Notas,EtiquetaId,Origen,Fecha_Creación,Fecha_Ultima_Modificación";

        return aux;
    }

    /**
     * Cargar la cabecera general del archivo que se crea al realizar el
     * reporte.
     * <p>
     * Se carga cada uno de los campos que tendrá cada columna como cabecera
     * para poder realizar el archivo de excel.
     *
     * @author jonathan Peña
     * @return el String correspondiente.
     *
     */
    public static String headerHhppBasicSplit() {
        String aux = "hhpId,HhpIdrR,HhpDireccion,Barrio";

        return aux;
    }

    public static String headerHhppGeneralNuevo() {
        String aux = "Estrato,Niv_Socio,HhpComunidad,HhpDivision,HhpEstadoUnit,"
                + "HhpVendedor,HhpCodigoPostal,HhpTipoAcomet,NodoId,HhpHeadEnd,"
                + "HhpTipo,HhpEdificio,HhpTipoUnidad"
                + ",HhpTipoCblAcometida,Etiqueta,Notas,EtiquetaId,Origen,Fecha_Creación,Fecha_Ultima_Modificación";

        return aux;
    }

    /**
     * Carga los campos de georeferenciación
     * <p>
     * Se establecen los campos que harán parte de la georeferenciación
     *
     * @author becerraarmr
     * @return el String[] correspondiente.
     *
     */
    public static String[] headerGeoReferenciar() {
        String aux = "Geo{Existencia},Geo{Nodo 1},Geo{Nodo 2},Geo{Nodo 3},"
                + "Geo{Nodo DTH},Geo{EstratoDir},Geo{EstadoDir},"
                + "Geo{ConfiabilidadDir},Geo{Fuente},Geo{ESTADO_PROCESO},"
                + "Geo{DETALLE,FECHA_PROCESAMIENTO},Geo{Cambiar}";
        return aux.split(",");

    }

    /**
     * Carga los campos de la respuesta
     * <p>
     * Se establecen los campos que harán parte de la respuesta
     *
     * @author becerraarmr
     * @return el String[] correspondiente.
     *
     */
    public static String[] headerRpta() {
        String aux = "ESTADO_PROCESO,DETALLE,FECHA_PROCESAMIENTO,INFO_ORIGINAL";
        return aux.split(",");
    }

    /**
     * Carga los campos de reporte detallado por estrato
     * <p>
     * Se establecen los campos que harán parte de la respuesta
     *
     * @author Jonathan Peña
     * @return el String[] correspondiente.
     *
     */
    public static String[] headerDetalladoEstrato() {
        String aux = "hhpId,HhpIdrR,HhpDireccion,Barrio,HhpEstadoUnit,Estrato,Estrato_Nuevo,Notas,Origen,Fecha_Creación,Fecha_Ultima_Modificación";
        return aux.split(",");
    }

    /**
     * Carga los campos de reporte detallado por Tipo Vivienda
     * <p>
     * Se establecen los campos que harán parte de la respuesta
     *
     * @author Juan David Hernandez
     * @return el String[] correspondiente.
     *
     */
    public static String[] headerDetalladoTipoVivienda() {
        String aux = "hhpId,HhpIdrR,HhpDireccion,Barrio,HhpTipoUnidad,HhpTipoUnidad_Nuevo,Notas,Origen,Fecha_Creación,Fecha_Ultima_Modificación";
        return aux.split(",");
    }

    /**
     * Carga los campos de reporte detallado por estrato
     * <p>
     * Se establecen los campos que harán parte de la respuesta
     *
     * @author Jonathan Peña
     * @return el String[] correspondiente.
     *
     */
    public static String[] headerDetalladoEstado() {
        String aux = "hhpId,HhpIdrR,HhpDireccion,Barrio,HhpEstadoUnit,HhpEstadoUnit_Nuevo,Notas,Origen,Fecha_Creación,Fecha_Ultima_Modificación";
        return aux.split(",");
    }

    /**
     * Carga los campos de reporte detallado por estrato
     * <p>
     * Se establecen los campos que harán parte de la respuesta
     *
     * @author Jonathan Peña
     * @return el String[] correspondiente.
     *
     */
    public static String[] headerDetalladoCobertura() {
        String aux = "hhpId,HhpIdrR,HhpDireccion,Barrio,NodoId,NodoId_Nuevo,Notas,Origen,Fecha_Creación,Fecha_Ultima_Modificación";
        return aux.split(",");
    }
    
    /**
     * Carga los campos de reporte detallado por Etiqueta
     * <p>
     * Se establecen los campos que harán parte de la respuesta
     *
     * @author Victor Bocanegra
     * @return el String[] correspondiente.
     *
     */
    public static String[] headerDetalladoEtiqueta() {
        String aux = "hhpId,HhpIdrR,HhpDireccion,Barrio,Etiqueta,Etiqueta_Nueva,Notas,Etiqueta_Id,Origen,Fecha_Creación,Fecha_Ultima_Modificación";
        return aux.split(",");
    }

    /**
     * Cargar la cabecera de direcciones
     * <p>
     * Se prepara la cabecera de direcciones según los atributos que tiene el
     * objeto DrDireccionesMgl, exceptuando algunos campos.
     *
     * @author becerraarmr
     *
     * @return el String[] correspondiente.
     */
    public static String[] headerDirecciones() {
        Class p = DrDireccionMgl.class;

        Field[] f = p.getDeclaredFields();
        StringBuilder aux = new StringBuilder();
        aux.append("Notas,");
        for (int i = 0; i < f.length; i++) {
            Field field = f[i];
            if ("serialVersionUID".equalsIgnoreCase(field.getName())) {
                continue;
            }
            if ("Id".equalsIgnoreCase(field.getName())) {
                continue;
            }
            if ("IdSolicitud".equalsIgnoreCase(field.getName())) {
                continue;
            }
            if ("Estrato".equalsIgnoreCase(field.getName())) {
                continue;
            }
            if ("EstadoDirGeo".equalsIgnoreCase(field.getName())) {
                continue;
            }
            if ("IdDirCatastro".equalsIgnoreCase(field.getName())) {
                continue;
            }
            if ("_persistence_primaryKey".equalsIgnoreCase(field.getName())) {
                continue;
            }
            if ("_persistence_listener".equalsIgnoreCase(field.getName())) {
                continue;
            }
            if ("_persistence_fetchGroup".equalsIgnoreCase(field.getName())) {
                continue;
            }
            if ("_persistence_shouldRefreshFetchGroup".equalsIgnoreCase(field.getName())) {
                continue;
            }
            if ("_persistence_session".equalsIgnoreCase(field.getName())) {
                continue;
            }
            if ("fechaCreacion".equalsIgnoreCase(field.getName())) {
                continue;
            }
            if ("usuarioCreacion".equalsIgnoreCase(field.getName())) {
                continue;
            }
            if ("perfilCreacion".equalsIgnoreCase(field.getName())) {
                continue;
            }
            if ("fechaEdicion".equalsIgnoreCase(field.getName())) {
                continue;
            }
            if ("usuarioEdicion".equalsIgnoreCase(field.getName())) {
                continue;
            }
            if ("perfilEdicion".equalsIgnoreCase(field.getName())) {
                continue;
            }
            if ("estadoRegistro".equalsIgnoreCase(field.getName())) {
                continue;
            }
            if ("_persistence_links".equalsIgnoreCase(field.getName())) {
                continue;
            }
            if ("_persistence_href".equalsIgnoreCase(field.getName())) {
                continue;
            }
            if ("_persistence_relationshipInfo".equalsIgnoreCase(field.getName())) {
                continue;
            }
            if ("_persistence_cacheKey".equalsIgnoreCase(field.getName())) {
                continue;
            }
            
            aux.append(field.getName());
            if (i != f.length) {
                aux.append(",");
            }
        }
        
        aux.append("Origen,Fecha_Creación,Fecha_Ultima_Modificación");

        return aux.toString().split(",");
    }

    /**
     * Cargar la data de HhppMgl
     * <p>
     * Recibe un vector e intenta setearlos a cada uno de los parámetros que
     * corresponden al HhppMgl
     *
     * @author becerraarmr
     * @param data vector a convertir.
     *
     * @return null o el HhppMgl correspondiente.
     */
    public static HhppMgl cargaData(String[] data) {
        if (data == null) {
            return null;//no hay data
        }
        if (data.length != headerHhpp().length) {
            return null;// no tiene el mismo tamaño
        }
        BigDecimal auxHhpId = findBigDecimal(data[0]);
        if (auxHhpId == null) {
            return null;//no se validó el id del hhpp
        }
        HhppMgl hhppMgl = new HhppMgl();

        hhppMgl.setHhpId(auxHhpId);
        hhppMgl.setHhpIdrR(data[1]);
        hhppMgl.setHhpCalle(data[2]);
        hhppMgl.setHhpPlaca(data[3]);
        hhppMgl.setHhpApart(data[4]);
        hhppMgl.setHhpComunidad(data[8]);
        hhppMgl.setHhpDivision(data[9]);
        hhppMgl.setHhpEstadoUnit(data[10]);
        hhppMgl.setHhpVendedor(data[11]);
        hhppMgl.setHhpCodigoPostal(data[12]);
        hhppMgl.setHhpTipoAcomet(data[13]);
        hhppMgl.setHhpUltUbicacion(data[14]);
        hhppMgl.setHhpHeadEnd(data[15]);
        hhppMgl.setHhpTipo(data[16]);
        hhppMgl.setHhpEdificio(data[17]);
        hhppMgl.setHhpTipoUnidad(data[18]);
        hhppMgl.setHhpTipoCblAcometida(data[19]);
        return hhppMgl;
    }

    /**
     * Convertir el valor a BigDecimal
     * <p>
     * Toma un valor String y trata de convertirlo a BigDecimal
     *
     * @author becerraarmr
     * @param valor parámetro que se revisa para convertir
     *
     * @return un null o el BigDecimal representativo.
     */
    public static BigDecimal findBigDecimal(String valor) {
        try {
            return new BigDecimal(valor);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(CargueMasivoHhppManager.class)+"': " + e.getMessage();
            LOGGER.error(msg);

            return null;
        }
    }

    /**
     * Convertir el valor a Integer
     * <p>
     * Toma un valor String y trata de convertirlo a Integer
     *
     * @author becerraarmr
     * @param valor parámetro que se revisa para convertir
     *
     * @return un null o el Integer representativo.
     */
    public static Integer getInteger(String valor) {
        try {
            return new Integer(valor);
        } catch (NumberFormatException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(CargueMasivoHhppManager.class)+"': " + e.getMessage();
            LOGGER.error(msg);
            
            return null;
        }
    }

    /**
     * Verificar si es cabecera válida
     * <p>
     * Verifica el parametro header y establece si es o no cabecera.
     *
     * @author becerraarmr
     * @param headerIn vector con la data a verificar
     *
     * @return un true o un false según la verificación.
     */
    public boolean isHeaderValida(String[] headerIn) {
        
        if (headerIn == null) {
            return false;// no hay data
        }
        CmtBasicaMgl atributoCarga = validarAtributoDetallado(atributoCargado);
        String codigoBasicaCarga="";
        
        if(atributoCarga != null){
           codigoBasicaCarga=atributoCarga.getCodigoBasica();
        }
        
        String[] aux;
      
        //Archivo General
        if (headerIn.length == headerHhpp().length) {
            aux = headerHhpp();

        }//Archivo Inhabilitar Hhpp
        else if (headerIn.length == headerHhpp().length + headerHhppInhabilitar().length) {
            aux = new String[headerHhpp().length + headerHhppInhabilitar().length];
            int i = 0;
            for (; i < headerHhpp().length; i++) {
                aux[i] = headerHhpp()[i];
            }
            for (int j = 0; j < headerHhppInhabilitar().length; i++, j++) {
                aux[i] = headerHhppInhabilitar()[j];
            }
            isHeaderInhabilitar = true;

        }//Archivo Detallado Direcciones 
        else if (headerIn.length == (headerHhppBasic().length + headerDirecciones().length)) {
            //se habilita el tipo de header 
            isHeaderDirecciones = true;
            tipoReporte = Constant.CODIGO_BASICA_DIRECCION;
            aux = new String[headerDirecciones().length + headerHhppBasic().length];
            int i = 0;
            for (; i < headerHhppBasic().length; i++) {
                aux[i] = headerHhppBasic()[i];
            }

            for (int j = 0; j < headerDirecciones().length; i++, j++) {
                aux[i] = headerDirecciones()[j];
            }

        }//Archivo Detallado Estrato 
        else if (codigoBasicaCarga.equalsIgnoreCase(Constant.CODIGO_BASICA_ESTRATO)
                && headerIn.length == headerDetalladoEstrato().length) {
            tipoReporte = Constant.CODIGO_BASICA_ESTRATO;
            aux = headerDetalladoEstrato();

        }//Archivo Detallado Tipo Vivienda  
        else if (codigoBasicaCarga.equalsIgnoreCase(Constant.CODIGO_BASICA_TIPO_VIVIENDA)
                && headerIn.length == headerDetalladoTipoVivienda().length) {
            tipoReporte = Constant.CODIGO_BASICA_TIPO_VIVIENDA;
            aux = headerDetalladoTipoVivienda();

        }//Archivo Detallado Cobertura
        else if (codigoBasicaCarga.equalsIgnoreCase(Constant.CODIGO_BASICA_COBERTURA) 
                && headerIn.length == headerDetalladoCobertura().length) {
            aux = headerDetalladoCobertura();
            tipoReporte = Constant.CODIGO_BASICA_COBERTURA;

        }//Archivo Detallado Etiqueta 
        else if (codigoBasicaCarga.equalsIgnoreCase(Constant.CODIGO_BASICA_ETIQUETA)
                && headerIn.length == headerDetalladoEtiqueta().length) {
            aux = headerDetalladoEtiqueta();
            tipoReporte = Constant.CODIGO_BASICA_ETIQUETA;

        } else {
            return false;//no tiene la cabeceras correctas
        }

        
        headerSel = aux;
        return true;//Validada correctamente
    }

    /**
     * @return the hhppManager
     */
    public HhppMglManager getHhppManager() {
        if (hhppManager == null) {
            hhppManager = new HhppMglManager();
        }
        return hhppManager;
    }

    /**
     * @return the rrCiudadesManager
     */
    public RrCiudadesManager getRrCiudadesManager() {
        if (rrCiudadesManager == null) {
            rrCiudadesManager = new RrCiudadesManager();
        }
        return rrCiudadesManager;
    }

    /**
     * @return the tipoHhppConexionManager
     */
    public TipoHhppConexionMglManager getTipoHhppConexionManager() {
        if (tipoHhppConexionManager == null) {
            tipoHhppConexionManager = new TipoHhppConexionMglManager();
        }
        return tipoHhppConexionManager;
    }

    /**
     * @return the nodoManager
     */
    public NodoMglManager getNodoManager() {
        if (nodoManager == null) {
            nodoManager = new NodoMglManager();
        }
        return nodoManager;
    }

    /**
     * @return the direccionRrManager
     */
    public DireccionRRManager getDireccionRrManager() {
        if (direccionRrManager == null) {
            direccionRrManager = new DireccionRRManager(false);
        }
        return direccionRrManager;
    }

    /**
     * @return the ejbDireccionMgl
     */
    public DireccionMglManager getEjbDireccionMgl() {
        if (ejbDireccionMgl == null) {
            ejbDireccionMgl = new DireccionMglManager();
        }
        return ejbDireccionMgl;
    }

    /**
     * @return the estadoHhppManager
     */
    public EstadoHhppMglManager getEstadoHhppManager() {
        if (estadoHhppManager == null) {
            estadoHhppManager = new EstadoHhppMglManager();
        }
        return estadoHhppManager;
    }

    /**
     * @return the basicaManager
     */
    public CmtBasicaMglManager getBasicaManager() {
        if (basicaManager == null) {
            basicaManager = new CmtBasicaMglManager();
        }
        return basicaManager;
    }

    /**
     * @return the cargeArchivoManager
     */
    public HhppCargueArchivoLogManager getCargeArchivoManager() {
        if (cargeArchivoManager == null) {
            cargeArchivoManager = new HhppCargueArchivoLogManager();
        }
        return cargeArchivoManager;
    }

    /**
     * @return the notasAdicionalesManager
     */
    public NotasAdicionalesMglManager getNotasAdicionalesManager() {
        if (notasAdicionalesManager == null) {
            notasAdicionalesManager = new NotasAdicionalesMglManager();
        }
        return notasAdicionalesManager;
    }

    /**
     * @return the parametrosMglManager
     */
    public ParametrosMglManager getParametrosMglManager() {
        if (parametrosMglManager == null) {
            parametrosMglManager = new ParametrosMglManager();
        }
        return parametrosMglManager;
    }

    /**
     * Activar la tarea programada
     * <p>
     * Se procede a activar la tarea programada, de tal forma que se pueda
     * ejecutar los cambios previstos.
     *
     * @author becerraarmr
     */
    public void activarTimer() {
        try {
            timerTask = null;
            final Timer timer = new Timer();
            iniciarParametros();
            if (horaInicioProceso != null && horaFinalProceso != null) {
                timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            LOGGER.info("entro a proceso nocturno");
                            iniciarParametros();
                            Calendar cal = Calendar.getInstance();
                            Calendar fin = horaFinalProceso;
                            if (cal.get(Calendar.HOUR_OF_DAY) >= fin.get(Calendar.HOUR_OF_DAY)) {
                                cancel();
                            } else {
                                HhppCargueArchivoLog aux = archivoProcesar();
                                while (aux != null) {
                                    if (aux == null) {
                                        cancel();
                                    }
                                    procesarCargueMasivoNocturno(aux);
                                    aux = archivoProcesar();
                                }
                            }
                        } catch (ApplicationException e) {
                            String msg = "Se produjo un error al momento de ejecutar el hilo '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                            LOGGER.error(msg, e);
                        }
                    }
                };
                if (franjaTiempo < 1000) {
                    franjaTiempo = 1000;
                }
                timer.schedule(timerTask, horaInicioProceso.getTime(), franjaTiempo);
            }
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg, e);
        }
    }

    /**
     * Buscar archivo a procesar
     *
     * @author becerraarmr
     * @return el objeto con los datos del archivo a procesar o null
     */
    private HhppCargueArchivoLog archivoProcesar() {
        try {
            HhppCargueArchivoLogManager managerCargueMasivo
                    = new HhppCargueArchivoLogManager();

            return managerCargueMasivo.findToProcesar();
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg, e);
            
            return null;
        }
    }

    /**
     * Prepara a procesar cargue masivo nocturno
     * <p>
     * Se lee la tabla donde quedó registrado los arhivos que se van a leer y se
     * empieza a procesar cada 4 segundos.
     *
     * @author becerraarmr
     */
    private synchronized void procesarCargueMasivoNocturno(HhppCargueArchivoLog aux) {
        HhppCargueArchivoLogManager managerCargueMasivo
                = new HhppCargueArchivoLogManager();
        FileOutputStream fos = null;
        try {
            if (aux == null) {
                return;//No encontró
            }

            CargueArchivoLogItemManager cargueArchivoLogItemManager = new CargueArchivoLogItemManager();
            List<CargueArchivoLogItem> listaRegistros
                    = cargueArchivoLogItemManager.findByIdArchivoGeneral(aux.getIdArchivoLog());
            int i = 0;
            for (CargueArchivoLogItem dato : listaRegistros) {

                String[] data = dato.getInfoMod().split("\\|");
                if (isHeaderValida(data)) {
                    DetalleCargaHhppMgl detalleCargaHhppMgl = procesarData(data, i + 3,
                            DetalleCargaHhppMgl.PROCESO_NOCTURNO, dato.getInfo());
                    dato.setEstadoProceso(detalleCargaHhppMgl.getEstado());
                    String detalleGuardar = detalleCargaHhppMgl.getDetalle().replace(";", "");
                    dato.setDetalle(detalleGuardar.trim());
                    calim.update(dato);
                }
                i++;
            }
            aux.setEstado((short) 1);
            aux.setCantidadProcesada(new BigInteger(Integer.toString(i)));
            managerCargueMasivo.update(aux);
        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg, ex);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                    LOGGER.error(msg);
                }
            }
        }
    }

    private void validarProcesoNocturno(String[] data) {

    }

    /**
     * Buscar data a procesar
     * <p>
     * Se procesa el registro correspondiente.
     *
     * @author becerraarmr
     * @param workbook objeto con archivo Excel con la información
     *
     * @return null si no encontro nada y un vector de datos si hay informacion
     */
    private Map<String, Object> registroToProcesar(Workbook workbook) {
        if (workbook == null) {
            return null;
        }
        int cantSheet = workbook.getNumberOfSheets();

        int i = 0;

        while (cantSheet > 0) {
            Sheet sheet = workbook.getSheetAt(i);
            if (sheet == null) {
                break;
            }
            int rowCount = sheet.getLastRowNum();

            Row row = sheet.getRow(2);
            int cellRowCount = row.getLastCellNum();
            String[] data;
            int auxCellRowCount = cellRowCount - 3;
            data = new String[auxCellRowCount];
            for (int c = 0; c < auxCellRowCount; c++) {
                Cell cell = row.getCell(c);
                data[c] = toStringCell(cell);
            }
            if (!isHeaderValida(data)) {//revisar la cabecera
                return null;//La lectura del archivo es descartada
            }
            for (int r = 3; r <= rowCount; r++) {//Leer la fila despues del encabezado
                row = sheet.getRow(r);
                //Quien indica si esta procesado o no o está vacio
                Cell cellRev = row.getCell(auxCellRowCount);
                if (toStringCell(cellRev).isEmpty()) {
                    data = new String[auxCellRowCount];
                    for (int c = 0; c < auxCellRowCount; c++) {
                        Cell cell = row.getCell(c);
                        data[c] = toStringCell(cell);
                    }
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("data", data);
                    map.put("item", r);
                    return map;
                }
            }
            i++;//Aumenta el item hoja a revisar
            cantSheet--;//Reducir la cantidad de hojas
        }
        return null;
    }

    private boolean isArchivoNocturnoViable(Workbook workbook) {
        try {
            if (workbook == null) {
                return false;
            }
            int cantSheet = workbook.getNumberOfSheets();

            int i = 0;

            do {
                Sheet sheet = workbook.getSheetAt(i++);
                if (sheet == null) {
                    break;
                }

                Row row = sheet.getRow(2);//Fila de la cabecera

                int cellRowCount = row.getLastCellNum();

                int auxCellRowCount = cellRowCount - 3;
                String[] data = new String[auxCellRowCount];

                for (int c = 0; c < auxCellRowCount; c++) {
                    Cell cell = row.getCell(c);
                    data[c] = toStringCell(cell);
                }

                if (!isHeaderValida(data)) {//revisar la cabecera
                    return false;//Es un archivo no válido
                }
            } while (--cantSheet > 0);
        } catch (Exception e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg, e);
            
            return false;
        }
        return true;
    }

    /**
     * Prepara el cambio nocturno. Prepara el archivo para realizar el cambio.
     *
     * @author becerraarmr
     * @param item registro procesado.
     * @param workbook para manipular el archivo para el cambio.
     *
     * @return el workbook con el cambio si se realizó
     */
    private Workbook prepararCambio(DetalleCargaHhppMgl item,
            Workbook workbook) {
        if (workbook == null || item == null) {
            return null;
        }

        boolean procesNocturno = false;//no ha sido procesado;
        Sheet sheet = workbook.getSheetAt(0);
        if (sheet == null) {
            return null;
        }
        Row rowHeader = sheet.getRow(2);
        int cellRowCount = rowHeader.getLastCellNum() - 3;
        String[] data;
        data = new String[cellRowCount];

        for (int c = 0; c < data.length; c++) {
            Cell cell = rowHeader.getCell(c);
            data[c] = toStringCell(cell);
        }

        if (!isHeaderValida(data)) {//revisar la cabecera
            return null;//La lectura del archivo es descartada
        }

        Row row = sheet.getRow(item.getRow());
        if (row == null) {
            return null;
        }
        Cell cellFirst = row.getCell(0);
        if (cellFirst != null) {
            BigDecimal valorId = new BigDecimal(cellFirst.toString());
            HhppMgl hhppMgl1 = item.getHhppMgl();
            if (hhppMgl1 != null) {
                if (valorId.equals(hhppMgl1.getHhpId())) {

                    Cell cellEstado = row.createCell(cellRowCount);
                    cellEstado.setCellValue(item.getEstado());
                    Cell cellDetalle = row.createCell(cellRowCount + 1);
                    cellDetalle.setCellValue(item.getDetalle());
                    Cell fechaProceso = row.createCell(cellRowCount + 2);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
                    String fecha = sdf.format(new Date());
                    fechaProceso.setCellValue(fecha);
                    procesNocturno = true;
                }
            }
        }
        if (procesNocturno) {
            return workbook;
        }
        return null;
    }

    /**
     * Referenciar datos según parámetros
     * <p>
     * Se busca en la base de datos en la tabla parámetros los valores que
     * corresponden para procesar la carga
     *
     * @author becerraarmr
     * @return Valor del parametro encontrado
     * @throws ApplicationException
     */
    public String verParametros() throws ApplicationException {
        try {
            final String mglCargueFranjaTiempo = "MGL_PROCESO_MASIVO_MODIFICACION";
            ParametrosMgl pm = getParametrosMglManager().findParametroMgl(mglCargueFranjaTiempo);
            if (pm != null) {
                String valor = pm.getParValor();
                if (valor != null) {
                    return valor;
                }
            }
            return "";
        } catch (ApplicationException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
            LOGGER.error(msg);
            throw new ApplicationException(msg,  e);

        }
    }

    /**
     * Muestra el nombre del archivo creado
     *
     * @return String nombre.
     */
    public String getFileNameProcesado() {
        return fileNameProcesado;
    }

    public StringBuilder datosConsultaFin(BigDecimal idHhpp) {

        StringBuilder infoReal = null;

        try {
            HhppMgl datoOriginal = hhppManager.findById(idHhpp);
            if (datoOriginal != null && datoOriginal.getHhpId() != null) {
                infoReal = new StringBuilder();

                DireccionMgl dirMgl = getDireccionMgl(datoOriginal);

                Object[] datos = valoresHeadersFin(datoOriginal,
                        dirMgl);

                if (datos != null) {

                    for (Object header : datos) {
                        String item = header == null ? "" : header.toString();
                        infoReal.append(item);
                        infoReal.append("|");
                    }
                }
            }
        } catch (ApplicationException ex) {
            msgProceso = "No se pudo validar el archivo: " + ex.getMessage();
            LOGGER.error(msgProceso, ex);
        }
        return infoReal;
    }

    /**
     * Consigue el Geografico de un Hhpp.
     *
     * @author bocanegra vm.
     * @param hhppMgl hhpp al que se le va a buscar el valor.
     * @return null o el Geografico correspondiente.
     */
    public Geografico getGeografico(HhppMgl hhppMgl) {
        if (hhppMgl != null) {
            try {
                GeograficoMglManager manager = new GeograficoMglManager();
                Geografico barrio = manager.findBarrioHhpp(hhppMgl.getHhpId());
                if (barrio != null) {
                    return barrio;
                }
            } catch (ApplicationException e) {
                String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                LOGGER.error(msg, e);
            }
        }
        return null;
    }

    /**
     * Mostrar la DireccionMgl del Hhpp
     *
     * @author bocanegra vm
     * @param hhppMgl hhpp para verficar el valor.
     * @return la DireccionMGl o el null
     */
    public DireccionMgl getDireccionMgl(HhppMgl hhppMgl) {
        if (hhppMgl != null) {
            if (hhppMgl.getDirId() != null) {
                try {
                    DireccionMgl basica = new DireccionMgl();
                    basica.setDirId(hhppMgl.getDirId());
                    DireccionMglManager manager = new DireccionMglManager();
                    basica = manager.findById(basica);
                    if (basica != null) {
                        return basica;
                    }
                } catch (ApplicationException e) {
                    String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + e.getMessage();
                    LOGGER.error(msg, e);
                }
            }
        }
        return null;
    }

    public void guardarRegistrosEnBd(DetalleCargaHhppMgl detalleCargaHhppMgl, String[] item,
            HhppCargueArchivoLog idCargueArchivoLog)
            throws ApplicationException {

        if(item != null && item.length > 0 && !item[0].isEmpty()){
        
        StringBuilder infoMod = new StringBuilder();
        for (String s : item) {
            if (s.isEmpty()) {
                infoMod.append("---");
            } else {
                infoMod.append(s);
            }
            infoMod.append("|");
        }

        StringBuilder encabezadosCampos = new StringBuilder();
        encabezadosCampos.append(headerFiltros);
        for (String s : headerSel) {
            encabezadosCampos.append(s);
            encabezadosCampos.append("|");
        }

        CargueArchivoLogItem entity = new CargueArchivoLogItem();
        entity.setIdArchivoLog(idCargueArchivoLog.getIdArchivoLog());
        entity.setInfo(detalleCargaHhppMgl.getInfoOriginal());
        entity.setInfoMod(infoMod.toString());
        entity.setEstadoProceso(detalleCargaHhppMgl.getEstado());
        if(detalleCargaHhppMgl != null && detalleCargaHhppMgl.getDetalle() != null
                && !detalleCargaHhppMgl.getDetalle().isEmpty()){
        String detalleGuardar = detalleCargaHhppMgl.getDetalle().replace(";", "");
        detalleGuardar = detalleGuardar.replaceAll("\\r\\n|\\r|\\n", " ");
        entity.setDetalle(detalleGuardar.trim());
        }
        entity.setFechaProcesamiento(detalleCargaHhppMgl.getFechaProcesamiento());
        entity.setFechaRegistro(new Date());
        entity.setIdComplemento(BigDecimal.ZERO);
        entity.setEncabezadoCampos(encabezadosCampos.toString());
        entity.setLlegoRollback("N");
        entity.setModRoolback("N");
        entity.setNuevosValores(datosModificados.toString());
        calim.create(entity);

        if (entity.getIdCargueLog() != null) {
            entity.setIdComplemento(entity.getIdCargueLog());
            calim.update(entity);
        }
        }

    }

    public void enviarArchivoForTcrm(HhppCargueArchivoLog archivoLog) {

         File archive= null;
        try {

            String[] header;
            CargueArchivoLogItemManager cargueArchivoLogItemManager = new CargueArchivoLogItemManager();
            if (archivoLog.getIdArchivoLog() != null) {
                registrosBd = cargueArchivoLogItemManager.findByIdArchivoGeneralAndProcesado(archivoLog.getIdArchivoLog());
                if (registrosBd.size() > 0) {

                    header = headerEnviarArchivoTCRM();

                    final StringBuffer sb = new StringBuffer();

                    String[] datosMod;
                    String[] datosOrg;
                    String[] nuevosValores;

                    int numeroRegistros = registrosBd.size();
                    int numPaginas = numeroRegistros / NUM_REGISTROS_PAGINA;
                    byte[] csvData;
                    if (numeroRegistros > 0) {
                        for (int i = 0; i <= numPaginas; i++) {
                            LOGGER.error("pagina " + i);
                            for (int j = 0; j < header.length; j++) {
                                sb.append(header[j]);
                                if (j < header.length) {
                                    sb.append(",");
                                }
                            }
                        }
                        sb.append("\n");
                        
                        CmtDireccionDetalleMglManager detalleMglManager = new CmtDireccionDetalleMglManager();
                        CmtDireccionDetalladaMgl detalladaMgl;
                        
                        for (CargueArchivoLogItem archivo : registrosBd) {

                            datosMod = archivo.getInfoMod().split("\\|");
                            datosOrg = archivo.getInfo().split("\\|");
                            nuevosValores = archivo.getNuevosValores().split("\\|");

                            if (datosMod[0] != null) {
                                BigDecimal idHhpp = new BigDecimal(datosMod[0]);
                                HhppMgl hhppMglreg = hhppManager.findById(idHhpp);
                                if (hhppMglreg != null) {
                                    String idHhp = hhppMglreg.getHhpId().toString();
                                    detalladaMgl = detalleMglManager.findByHhPP(hhppMglreg);
                                    String dirDetAnt;
                                    String dirDetNue = "---";
                                    String codigoDane;
                                    String estadoUnit;
                                    GeograficoPoliticoMgl geograficoPoliticoMgl;
                                    String direccionEst = "---";
                                    String estrato = "---";
                                    String nodo = "---";
                                    String na = "---";
                                    String tipoUnidadViviendaHhpp = "---";

                                    switch (archivoLog.getTipoMod()) {
                                        case 1:
                                            //Modificaron cobertura
                                            dirDetAnt = detalladaMgl != null ? detalladaMgl.getDireccionDetalladaId().toString():"---";
                                            sb.append(idHhp);
                                            sb.append(",");
                                            sb.append(dirDetAnt);
                                            sb.append(",");
                                            sb.append(dirDetNue);
                                            sb.append(",");
                                            geograficoPoliticoMgl = hhppMglreg.getDireccionObj().getUbicacion().getGpoIdObj();
                                            codigoDane = geograficoPoliticoMgl != null ? geograficoPoliticoMgl.getGeoCodigoDane() : "---";
                                            sb.append(codigoDane);
                                            sb.append(",");
                                            estadoUnit = hhppMglreg.getHhpEstadoUnit() != null ? hhppMglreg.getHhpEstadoUnit() : "---";
                                            sb.append(estadoUnit);
                                            sb.append(",");
                                            sb.append(direccionEst);
                                            sb.append(",");
                                            sb.append(estrato);
                                            sb.append(",");
                                            nodo = nuevosValores[0];
                                            sb.append(nodo);
                                            sb.append(",");
                                            //JDHT
                                            tipoUnidadViviendaHhpp = hhppMglreg.getThhId() != null ? hhppMglreg.getThhId() : "---";
                                            sb.append(tipoUnidadViviendaHhpp);
                                            sb.append(",");
                                            for (int i = 0; i < headerDirTCRM().length; i++) {
                                                sb.append(na);
                                                sb.append(",");
                                            }   break;
                                        case 2:
                                            //Modificaron estrato
                                            dirDetAnt = detalladaMgl != null ? detalladaMgl.getDireccionDetalladaId().toString():"---";
                                            sb.append(idHhp);
                                            sb.append(",");
                                            sb.append(dirDetAnt);
                                            sb.append(",");
                                            sb.append(dirDetNue);
                                            sb.append(",");
                                            geograficoPoliticoMgl = hhppMglreg.getDireccionObj().getUbicacion().getGpoIdObj();
                                            codigoDane = geograficoPoliticoMgl != null ? geograficoPoliticoMgl.getGeoCodigoDane() : "---";
                                            sb.append(codigoDane);
                                            sb.append(",");
                                            estadoUnit = hhppMglreg.getHhpEstadoUnit() != null ? hhppMglreg.getHhpEstadoUnit() : "---";
                                            sb.append(estadoUnit);
                                            sb.append(",");
                                            sb.append(direccionEst);
                                            sb.append(",");
                                            estrato = nuevosValores[0];
                                            sb.append(estrato);
                                            sb.append(",");
                                            sb.append(nodo);
                                            sb.append(",");
                                             //JDHT
                                            tipoUnidadViviendaHhpp = hhppMglreg.getThhId() != null ? hhppMglreg.getThhId() : "---";
                                            sb.append(tipoUnidadViviendaHhpp);
                                            sb.append(",");
                                            for (int i = 0; i < headerDirTCRM().length; i++) {
                                                sb.append(na);
                                                sb.append(",");
                                            }   break;
                                        case 3:
                                            //Modificaron direccion
                                            dirDetAnt = datosOrg[1];
                                            sb.append(idHhp);
                                            sb.append(",");
                                            sb.append(dirDetAnt);
                                            sb.append(",");
                                            dirDetNue = nuevosValores[0];
                                            sb.append(dirDetNue);
                                            sb.append(",");
                                            geograficoPoliticoMgl = hhppMglreg.getDireccionObj().getUbicacion().getGpoIdObj();
                                            codigoDane = geograficoPoliticoMgl != null ? geograficoPoliticoMgl.getGeoCodigoDane() : "---";
                                            sb.append(codigoDane);
                                            sb.append(",");
                                            estadoUnit = hhppMglreg.getHhpEstadoUnit() != null ? hhppMglreg.getHhpEstadoUnit() : "---";
                                            sb.append(estadoUnit);
                                            sb.append(",");
                                            detalladaMgl = detalleMglManager.findById(new BigDecimal(dirDetNue));
                                            direccionEst = detalladaMgl != null ? detalladaMgl.getDireccionTexto():"---";
                                            sb.append(direccionEst);
                                            sb.append(",");
                                            sb.append(estrato);
                                            sb.append(",");
                                            sb.append(nodo);
                                            sb.append(",");
                                             //JDHT
                                            tipoUnidadViviendaHhpp = hhppMglreg.getThhId() != null ? hhppMglreg.getThhId() : "---";
                                            sb.append(tipoUnidadViviendaHhpp);
                                            sb.append(",");
                                            int k = 0;
                                            int j = datosMod.length -3;
                                            for (String dato : datosMod) {
                                                if (k >= 5 && k < j ) {
                                                    String celda = dato;
                                                    sb.append(celda);
                                                    sb.append(",");
                                                }
                                                k++;
                                            }  break;
                                          case 4:
                                            //Modificaron tipo unidad (vivienda)
                                            dirDetAnt = detalladaMgl != null ? detalladaMgl.getDireccionDetalladaId().toString() : "---";
                                            sb.append(idHhp);
                                            sb.append(",");
                                            sb.append(dirDetAnt);
                                            sb.append(",");
                                            sb.append(dirDetNue);
                                            sb.append(",");
                                            geograficoPoliticoMgl = hhppMglreg.getDireccionObj().getUbicacion().getGpoIdObj();
                                            codigoDane = geograficoPoliticoMgl != null ? geograficoPoliticoMgl.getGeoCodigoDane() : "---";
                                            sb.append(codigoDane);
                                            sb.append(",");
                                            estadoUnit = hhppMglreg.getHhpEstadoUnit() != null ? hhppMglreg.getHhpEstadoUnit() : "---";
                                            sb.append(estadoUnit);
                                            sb.append(",");                                            
                                            sb.append(direccionEst);
                                            sb.append(",");
                                            sb.append(estrato);
                                            sb.append(",");
                                            sb.append(nodo);
                                            sb.append(",");
                                            //JDHT
                                            tipoUnidadViviendaHhpp = nuevosValores[0];
                                            sb.append(tipoUnidadViviendaHhpp);
                                            sb.append(",");                                            
                                            for (int i = 0; i < headerDirTCRM().length; i++) {
                                                sb.append(na);
                                                sb.append(",");
                                            }
                                            break;
                                        default:
                                            //Modificaron Gral
                                            dirDetAnt = detalladaMgl != null ? detalladaMgl.getDireccionDetalladaId().toString():"---";
                                            sb.append(idHhp);
                                            sb.append(",");
                                            sb.append(dirDetAnt);
                                            sb.append(",");
                                            sb.append(dirDetNue);
                                            sb.append(",");
                                            geograficoPoliticoMgl = hhppMglreg.getDireccionObj().getUbicacion().getGpoIdObj();
                                            codigoDane = geograficoPoliticoMgl != null ? geograficoPoliticoMgl.getGeoCodigoDane() : "---";
                                            sb.append(codigoDane);
                                            sb.append(",");
                                            estadoUnit = hhppMglreg.getHhpEstadoUnit() != null ? hhppMglreg.getHhpEstadoUnit() : "---";
                                            sb.append(estadoUnit);
                                            sb.append(",");
                                            sb.append(direccionEst);
                                            sb.append(",");
                                            estrato = nuevosValores[0];
                                            sb.append(estrato);
                                            sb.append(",");
                                            nodo = nuevosValores[1];
                                            sb.append(nodo);
                                            sb.append(",");
                                             //JDHT
                                            tipoUnidadViviendaHhpp = hhppMglreg.getThhId() != null ? hhppMglreg.getThhId() : "---";
                                            sb.append(tipoUnidadViviendaHhpp);
                                            sb.append(",");
                                            for (int i = 0; i < headerDirTCRM().length; i++) {
                                                sb.append(na);
                                                sb.append(",");
                                            }   break;
                                    }
                                }

                                // filas finales

                                String idReg = archivo.getIdComplemento().toString();
                                sb.append(idReg);
                                sb.append(",");

                                String celdaDet = archivo.getDetalle();
                                if (celdaDet != null) {
                                    celdaDet = celdaDet.replaceAll("\\r\\n|\\r|\\n", " ");
                                    sb.append(celdaDet);
                                } else {
                                    sb.append("---");
                                }
                                sb.append(",");

                                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                                String fechaProcesamiento = "";
                                if (archivo.getFechaProcesamiento() != null) {
                                    fechaProcesamiento = sdf.format(archivo.getFechaProcesamiento());
                                }

                                String celdaFecha = fechaProcesamiento;
                                sb.append(celdaFecha);

                                sb.append("\n");
                            }
                        }
                    }
                    csvData = sb.toString().getBytes("UTF-8");

                    String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss",
                            Locale.getDefault()).format(new Date());

                    String nombreArchivoTcrm = "AddressUpdate_" + archivoLog.getIdArchivoLog() + "_" + timeStamp + "";
                    
                    String nombreFinalArchivo = nombreArchivoTcrm+".csv";

                    File file = new File(System.getProperty("user.dir"));
                    archive = File.createTempFile(nombreArchivoTcrm, ".csv", file);
                    FileOutputStream output = new FileOutputStream(archive);
                    output.write(csvData);
                    output.close();

                    ParametrosMglManager parametros = new ParametrosMglManager();

                    String usuarioTcrm = parametros.findByAcronimo(Constant.USARIO_TCRM_SFTP)
                            .iterator().next().getParValor();
                    String hostTcrm = parametros.findByAcronimo(Constant.HOST_TCRM_SFTP)
                            .iterator().next().getParValor();
                    String passTcrm = parametros.findByAcronimo(Constant.PASSWORD_TCRM_SFTP)
                            .iterator().next().getParValor();
                    String puertoTcrm = parametros.findByAcronimo(Constant.PUERTO_TCRM_SFTP)
                            .iterator().next().getParValor();
                    String rutaTcrm = parametros.findByAcronimo(Constant.RUTA_CARGAR_ARCHIVO_TCRM)
                            .iterator().next().getParValor();

                    if (usuarioTcrm != null && hostTcrm != null && passTcrm != null
                            && puertoTcrm != null && rutaTcrm != null) {

                        int puerto = Integer.parseInt(puertoTcrm);

                        SFTPUtils conexionSftp = new SFTPUtils();
                        //conexion al servidor
                        conexionSftp.connect(usuarioTcrm, passTcrm, hostTcrm, puerto);
                        // subir archivo
                        conexionSftp.putFile(rutaTcrm, archive.getPath(), nombreFinalArchivo);
                        archivoLog.setEnvioTcrm("Y");
                        
                        archivoLog.setNombreArchivoTcrm(nombreFinalArchivo);

                        getCargeArchivoManager().update(archivoLog);
                        if (archive.delete()) {
                            LOGGER.error("El fichero " + archive.getName() + " ha sido borrado correctamente");
                        } else {
                            LOGGER.error("El fichero " + archive.getName() + " no se ha podido borrar");
                        }

                    } else {
                        System.err.println("Faltan parametros para conectar via SFTP");
                    }

                } else {
                    System.err.println("No se encontraron registros para enviar a TCRM");
                }
            }
        } catch (ApplicationException | JSchException | SftpException | IOException | IllegalAccessException | NumberFormatException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg, e);
            if (archive != null) {
                try {
                    Files.deleteIfExists(archive.toPath());
                } catch (IOException ex) {
                     LOGGER.error(ex.getMessage());
                }
            }
        }

    }

    /**
     * Procesar la data contenida en el item.
     *
     * @author bocanegra Vm
     * @param item arreglo con la data a procesar
     * @param estado estado del detalle de la carga
     * @param archivoResumen archivo resumen del cargue
     * @param estadoUnit estado del hhpp
     * @return DetalleCargaHhppMgl con la información deseada
     */
    public DetalleCargaHhppMgl procesarDataRevertir(String[] item, String estado,
            HhppCargueArchivoLog archivoResumen, String estadoUnit){

        if (item == null) {
            return null;
        }

        DetalleCargaHhppMgl detalleCarga = new DetalleCargaHhppMgl();
        detalleCarga.setEstado(estado);
        HhppMgl original = null;
        BigDecimal idHhpp;
        try {
            idHhpp = findBigDecimal(findValor(item, 0));
            if (idHhpp != null) {
                original = getHhppManager().findById(new BigDecimal(idHhpp.intValue()));
            }
       

        if (original == null) {
            detalleCarga.addDetalle("Hhpp no fue encontrado en la base de datos.", DetalleCargaHhppMgl.DETALLEWARNING);
            detalleCarga.setFechaProcesamiento(new Date());
            detalleCarga.setRow(0);
            detalleCarga.setEstado(DetalleCargaHhppMgl.NOPROCESADO);
            return detalleCarga;
        }

        detalleCarga.setHhppMgl(original);

            switch (archivoResumen.getTipoMod()) {
                case 1:
                    {
                        //Modificacion cobertura
                        String valor = findValor(item, 3);//valor del nodo antiguo
                        procesarHhpUltUbicacion(detalleCarga, valor);
                        break;
                    }
                case 2:
                    {
                        //Modificacion estrato
                        String valor = findValor(item, 2);//valor del estrato antiguo
                        procesarEstrato(detalleCarga, valor, estadoUnit);
                        break;
                    }
                case 3:
                    //Modificacion direccion
                    BigDecimal direccionDetId;
                    direccionDetId = findBigDecimal(findValor(item, 1));
                    CmtDireccionDetalleMglManager manager = new CmtDireccionDetalleMglManager();
                    CmtDireccionDetalladaMgl detalladaMgl;
                    DireccionMgl direccionMgl = null;
                    SubDireccionMgl subDireccionMgl = null;
                    if (direccionDetId != null) {
                        detalladaMgl = manager.buscarDireccionIdDireccion(direccionDetId);
                        
                        if (detalladaMgl != null) {
                            direccionMgl = detalladaMgl.getDireccion();
                            subDireccionMgl = detalladaMgl.getSubDireccion();
                        }
                        original.setDireccionObj(direccionMgl);
                        original.setSubDireccionObj(subDireccionMgl);
                        
                        getHhppManager().update(original);
                        detalleCarga.setEstado(DetalleCargaHhppMgl.PROCESADO);
                        detalleCarga.setDetalle("Direccion cambiada al HHPP");
                        
                    }       break;
                default:
                    //Modificacion general
                    for (int i = 0; i < item.length; i++) {
                        procesarItemDataGral(detalleCarga, i, item, estadoUnit);
                    }   break;
            }
        
        } catch (ApplicationException ex) {
            String msg = "Ocurrio un error procesando el registro: " + ex.getMessage();
            LOGGER.error(msg, ex);
            
            detalleCarga.setEstado(DetalleCargaHhppMgl.PROCESADO);
            detalleCarga.setDetalle(msg);
        }
        
        detalleCarga.setFechaProcesamiento(new Date());
        detalleCarga.setRow(0);
        return detalleCarga;
    }
    
    /**
     * Se procede a validar si el archivo fue cargado
     * anteriormente
     *
     * @author bocanegra vm
     * @param nombreArchivo
     * @return false si no fue cargado anteriormente true si fue cargado anteriormente
     */
    protected boolean validarArchivoCargueAnteriores(String nombreArchivo) {

        List<HhppCargueArchivoLog> itemsProcesosUsuarioLoginAux;
        HhppCargueArchivoLogManager manager = new HhppCargueArchivoLogManager();
        short estado = 1;
        boolean respuesta = false;
        int control=0;
        try {
            
            itemsProcesosUsuarioLoginAux = manager.findRangeEstado(null, estado, usuario);

            if (itemsProcesosUsuarioLoginAux != null && !itemsProcesosUsuarioLoginAux.isEmpty()) {

                for (HhppCargueArchivoLog archivoLog : itemsProcesosUsuarioLoginAux) {
                    if (archivoLog.getNombreArchivoCargue().equalsIgnoreCase(nombreArchivo)) {
                        control++;
                    } 
                }
            }
            if (control > 0) {
                respuesta = true;
            }

        } catch (ApplicationException ex) {
            String msg = "Se produjo un error al momento de ejecutar el método '"+ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
            LOGGER.error(msg, ex);
        }
        return respuesta;
    }
    
        /**
     * Carga los campos de reporte que se envia a TCRM
     * <p>
     * Se establecen los campos que harán parte del archivo enviado a TCRM
     *
     * @author Victor Bocanegra
     * @return el String[] correspondiente.
     *
     */
    public static String[] headerEnviarArchivoTCRM() {
        String aux = "tecnologiaHabilitadaId,DireccionIdAntiguo,DireccionIdNuevo,"
                + "Centro Poblado,HhpEstadoUnit,Direccion Estandarizada,"
                + "Estrato,NodoId,TipoUnidad,idTipoDireccion,DirPrincAlt,Barrio,"
                + "TipoViaPrincipal,NumViaPrincipal,LtViaPrincipal,"
                + "NlPostViaP,BisViaPrincipal,CuadViaPrincipal,TipoViaGeneradora,"
                + "NumViaGeneradora,LtViaGeneradora,NlPostViaG,BisViaGeneradora,"
                + "CuadViaGeneradora,PlacaDireccion,CpTipoNivel1,"
                + "CpTipoNive2,CpTipoNivel3,CpTipoNivel4,CpTipoNivel5,"
                + "CpTipoNivel6,CpValorNivel1,CpValorNivel2,CpValorNivel3,"
                + "CpValorNivel4,CpValorNivel5,CpValorNivel6,MzTipoNivel1,"
                + "MzTipoNivel2,MzTipoNivel3,MzTipoNivel4,MzTipoNivel5,"
                + "MzValorNivel1,MzValorNivel2,MzValorNivel3,MzValorNivel4,"
                + "MzValorNivel5,MzTipoNivel6,MzValorNivel6,ItTipoPlaca,"
                + "ItValorPlaca,letra3g,UpdateId,Comentario_Cambio,Fecha_proceso";
        
        return aux.split(",");
    }
    
          /**
     * Carga los campos de reporte que se envia a TCRM
     * <p>
     * Se establecen los campos que harán parte de la direccion enviado a TCRM
     *
     * @author Victor Bocanegra
     * @return el String[] correspondiente.
     *
     */
    public static String[] headerDirTCRM() {
        String aux = "idTipoDireccion,DirPrincAlt,Barrio, "
                + "TipoViaPrincipal,NumViaPrincipal,LtViaPrincipal,"
                + "NlPostViaP,BisViaPrincipal,CuadViaPrincipal,TipoViaGeneradora,"
                + "NumViaGeneradora,LtViaGeneradora,NlPostViaG,BisViaGeneradora,"
                + "CuadViaGeneradora,PlacaDireccion,CpTipoNivel1,"
                + "CpTipoNive2,CpTipoNivel3,CpTipoNivel4,CpTipoNivel5,"
                + "CpTipoNivel6,CpValorNivel1,CpValorNivel2,CpValorNivel3,"
                + "CpValorNivel4,CpValorNivel5,CpValorNivel6,MzTipoNivel1,"
                + "MzTipoNivel2,MzTipoNivel3,MzTipoNivel4,MzTipoNivel5,"
                + "MzValorNivel1,MzValorNivel2,MzValorNivel3,MzValorNivel4,"
                + "MzValorNivel5,MzTipoNivel6,MzValorNivel6,ItTipoPlaca,"
                + "ItValorPlaca,letra3g";
        
        return aux.split(",");
    }
    
    public CmtBasicaMgl validarAtributoDetallado(String atributoHhpp) {

        CmtBasicaMgl respuesta = null;

        if (atributosHhpp != null && !atributosHhpp.isEmpty()
                && atributoHhpp != null && !atributoHhpp.isEmpty()) {

            for (CmtBasicaMgl basicaMgl : atributosHhpp) {
                if (basicaMgl.getNombreBasica().equalsIgnoreCase(atributoHhpp)) {
                    respuesta = basicaMgl;
                }
            }

        }
        return respuesta;
    }
    
           /**
     * Carga los campos de reporte que se envia a TCRM
     * <p>
     * Se establecen los campos que harán parte del archivo enviado a TCRM
     *
     * @author Victor Bocanegra
     * @return el String[] correspondiente.
     *
     */
    public static String[] headerEnviarArchivoEOC_BCSC() {
        String aux = "IDCRM,CustomerID,AddressID,Address,"
                + "Regional,City,City_Code,District,"
                + "DaneCode,Stratum,HPPID,Node,ContractIds";
        
        return aux.split(",");
    }
    
    public void enviarArchivoForEOC_BCSC(HhppCargueArchivoLog archivoLog) {

        File archive = null;
        try {

            String[] header;
            CargueArchivoLogItemManager cargueArchivoLogItemManager = new CargueArchivoLogItemManager();
            if (archivoLog.getIdArchivoLog() != null) {
                registrosBd = cargueArchivoLogItemManager.findByIdArchivoGeneralAndProcesado(archivoLog.getIdArchivoLog());
                if (registrosBd.size() > 0) {

                    header = headerEnviarArchivoEOC_BCSC();

                    final StringBuffer sb = new StringBuffer();

                    String[] datosMod;

                    int numeroRegistros = registrosBd.size();
                    int numPaginas = numeroRegistros / NUM_REGISTROS_PAGINA;
                    byte[] csvData;
                    if (numeroRegistros > 0) {
                        for (int i = 0; i <= numPaginas; i++) {
                            LOGGER.error("pagina " + i);
                            for (int j = 0; j < header.length; j++) {
                                sb.append(header[j]);
                                if (j < header.length) {
                                    sb.append(",");
                                }
                            }
                        }
                        sb.append("\n");

                        CmtDireccionDetalleMglManager detalleMglManager = new CmtDireccionDetalleMglManager();
                        CmtDireccionDetalladaMgl detalladaMgl;

                        for (CargueArchivoLogItem archivo : registrosBd) {

                            datosMod = archivo.getInfoMod().split("\\|");

                            if (datosMod[0] != null) {
                                BigDecimal idHhpp = new BigDecimal(datosMod[0]);
                                HhppMgl hhppMglreg = hhppManager.findById(idHhpp);
                                if (hhppMglreg != null) {
                                    String idHhp = hhppMglreg.getHhpId().toString();
                                    detalladaMgl = detalleMglManager.findByHhPP(hhppMglreg);
                                    String direccionIgac = "";
                                    String codigoDane;
                                    String centroPoblado = "";
                                    String codigoDaneCity = "";
                                    String codigoDaneDpto = "";
                                    GeograficoPoliticoMgl geograficoPoliticoMgl;
                                    String estrato = "";

                                    String idReg = archivo.getIdComplemento().toString();
                                    sb.append(idReg);
                                    sb.append(",");

                                    sb.append(hhppMglreg.getSuscriptor() != null
                                            ? hhppMglreg.getSuscriptor() : "");
                                    sb.append(",");

                                    sb.append(detalladaMgl.getDireccionDetalladaId() != null
                                            ? detalladaMgl.getDireccionDetalladaId() : "");
                                    sb.append(",");

                                    SubDireccionMgl subDir = hhppMglreg.getSubDireccionObj();
                                    DireccionMgl dir = hhppMglreg.getDireccionObj();

                                    if (subDir != null) {
                                        direccionIgac = subDir.getSdiFormatoIgac();
                                        estrato = subDir.getSdiEstrato().toString();
                                    } else if (dir != null) {
                                        direccionIgac = dir.getDirFormatoIgac();
                                        estrato = dir.getDirEstrato().toString();
                                    }

                                    sb.append(direccionIgac);
                                    sb.append(",");

                                    sb.append(hhppMglreg.getHhpComunidad() != null
                                            ? hhppMglreg.getHhpComunidad() : "");
                                    sb.append(",");

                                    geograficoPoliticoMgl = hhppMglreg.getDireccionObj().getUbicacion().getGpoIdObj();
                                    codigoDane = geograficoPoliticoMgl != null
                                            ? geograficoPoliticoMgl.getGeoCodigoDane() : "";

                                    if (geograficoPoliticoMgl != null) {
                                        centroPoblado = geograficoPoliticoMgl.getGpoNombre();

                                        GeograficoPoliticoMgl city = geograficoPoliticoManager.
                                                findById(geograficoPoliticoMgl.getGeoGpoId());

                                        if (city != null) {
                                            codigoDaneCity = city.getGeoCodigoDane();

                                            GeograficoPoliticoMgl dpto = geograficoPoliticoManager.
                                                    findById(city.getGeoGpoId());

                                            if (dpto != null) {
                                                codigoDaneDpto = dpto.getGeoCodigoDane();
                                            }
                                        }
                                    }
                                    
                                    sb.append(centroPoblado);
                                    sb.append(",");
                                    
                                    sb.append(codigoDane);
                                    sb.append(",");
                                    
                                    sb.append(codigoDaneCity);
                                    sb.append(",");
                                    
                                    sb.append(codigoDaneDpto);
                                    sb.append(",");
                                    
                                    sb.append(estrato);
                                    sb.append(",");
                                            
                                    sb.append(idHhp);
                                    sb.append(",");
                                    
                                    sb.append(hhppMglreg.getNodId() != null ? hhppMglreg.getNodId() : "");
                                    sb.append(",");
                                    
                                    sb.append("");
                                    sb.append(",");
                                }

                                sb.append("\n");
                            }
                        }
                    }
                    csvData = sb.toString().getBytes("UTF-8");

                    String timeStamp = new SimpleDateFormat("ddMMyyyyHHmmss",
                            Locale.getDefault()).format(new Date());

                    String nombreArchivoEOC = "MADDRESSCHANGE_BATCH_" + timeStamp + "";
                    
                    String nombreFinalArchivo = nombreArchivoEOC+".txt";

                    File file = new File(System.getProperty("user.dir"));
                    archive = File.createTempFile(nombreArchivoEOC, ".txt", file);
                    FileOutputStream output = new FileOutputStream(archive);
                    output.write(csvData);
                    output.close();

                    ParametrosMglManager parametros = new ParametrosMglManager();

                    String usuarioTcrm = parametros.findByAcronimo(Constant.USARIO_TCRM_SFTP)
                            .iterator().next().getParValor();
                    String hostTcrm = parametros.findByAcronimo(Constant.HOST_TCRM_SFTP)
                            .iterator().next().getParValor();
                    String passTcrm = parametros.findByAcronimo(Constant.PASSWORD_TCRM_SFTP)
                            .iterator().next().getParValor();
                    String puertoTcrm = parametros.findByAcronimo(Constant.PUERTO_TCRM_SFTP)
                            .iterator().next().getParValor();
                    String rutaTcrm = parametros.findByAcronimo(Constant.RUTA_CARGAR_ARCHIVO_TCRM)
                            .iterator().next().getParValor();

                    if (usuarioTcrm != null && hostTcrm != null && passTcrm != null
                            && puertoTcrm != null && rutaTcrm != null) {

                        int puerto = Integer.parseInt(puertoTcrm);

                        SFTPUtils conexionSftp = new SFTPUtils();
                        //conexion al servidor
                        conexionSftp.connect(usuarioTcrm, passTcrm, hostTcrm, puerto);
                        // subir archivo
                        conexionSftp.putFile(rutaTcrm, archive.getPath(), nombreFinalArchivo);
                        archivoLog.setEnvioEocBcsc("Y");
                        
                        archivoLog.setNombreArchivoEOC(nombreFinalArchivo);

                        getCargeArchivoManager().update(archivoLog);
                        if (archive.delete()) {
                            LOGGER.error("El fichero " + archive.getName() + " ha sido borrado correctamente");
                        } else {
                            LOGGER.error("El fichero " + archive.getName() + " no se ha podido borrar");
                        }

                    } else {
                        System.err.println("Faltan parametros para conectar via SFTP");
                    }

                } else {
                    System.err.println("No se encontraron registros para enviar a EOC/BCSC");
                }
            }
        } catch (ApplicationException | JSchException | SftpException | IOException | IllegalAccessException | NumberFormatException e) {
            String msg = "Se produjo un error al momento de ejecutar el método '" + ClassUtils.getCurrentMethodName(this.getClass()) + "': " + e.getMessage();
            LOGGER.error(msg, e);
            if (archive != null) {
                try {
                    Files.deleteIfExists(archive.toPath());
                } catch (IOException ex) {
                     LOGGER.error(ex.getMessage());
                }
            }
        }

    }

}
