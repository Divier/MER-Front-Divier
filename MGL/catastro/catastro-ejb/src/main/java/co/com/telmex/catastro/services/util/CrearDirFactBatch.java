package co.com.telmex.catastro.services.util;

import co.com.claro.mgl.utils.ClassUtils;
import co.com.telmex.catastro.data.AddressFactResultBatch;
import co.com.telmex.catastro.data.AddressRequest;
import co.com.telmex.catastro.data.AddressService;
import co.com.telmex.catastro.services.solicitud.SolicitudNegocioEJB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Clase CrearDirFactBatch
 *
 * @author Deiver Rovira.
 * @version	1.0
 */
public class CrearDirFactBatch {

    public int size;
    public String dir_tipoTransaccion;
    public String message;
    public String nombreA;
    public String delimitadorCol;
    public boolean validaFormato;
    public boolean validaExtension;
    public boolean validaContenido;
    public boolean validaTamano;
    public boolean validaCabecera;
    private String messageOk;
    private String messageError;
    private ArrayList<String> listaDatos;
    private ArrayList<ArrayList<String>> contenidoArchivo;
    FileReader fr;
    BufferedReader br;
    ArrayList<AddressRequest> fileAddressRequest;
    ArrayList<AddressFactResultBatch> fileAddressResultLog;
    AddressRequest direccion;
    AddressFactResultBatch direccionLog;
    AddressService direccionTradRec;
    private static final Logger LOGGER = LogManager.getLogger(CrearDirFactBatch.class);

    /**
     *
     */
    public CrearDirFactBatch() {
    }

    /**
     *
     * @param size
     * @param dir_tipoTransaccion
     * @param message
     * @param nombreA
     * @param delimitadorCol
     * @param validaFormato
     * @param validaExtension
     * @param validaContenido
     * @param validaTamano
     * @param validaCabecera
     * @param listaDatos
     * @param contenidoArchivo
     * @param fileAddressRequest
     */
    public CrearDirFactBatch(int size,
            String dir_tipoTransaccion,
            String message,
            String nombreA,
            String delimitadorCol,
            boolean validaFormato,
            boolean validaExtension,
            boolean validaContenido,
            boolean validaTamano,
            boolean validaCabecera,
            ArrayList<String> listaDatos,
            ArrayList<ArrayList<String>> contenidoArchivo,
            ArrayList<AddressRequest> fileAddressRequest) {
        this.size = size;
        this.dir_tipoTransaccion = dir_tipoTransaccion;
        this.message = message;
        this.nombreA = nombreA;
        this.delimitadorCol = delimitadorCol;
        this.validaFormato = validaFormato;
        this.validaExtension = validaExtension;
        this.validaContenido = validaContenido;
        this.validaTamano = validaTamano;
        this.validaCabecera = validaCabecera;
        this.listaDatos = listaDatos;
        this.contenidoArchivo = contenidoArchivo;
        this.fileAddressRequest = fileAddressRequest;
    }

    private void inizialice() {
        size = 0;
        dir_tipoTransaccion = "";
        message = "";
        nombreA = "";
        delimitadorCol = ",";
        validaFormato = Boolean.FALSE;
        validaExtension = Boolean.FALSE;
        validaContenido = Boolean.FALSE;
        validaTamano = Boolean.FALSE;
        validaCabecera = Boolean.FALSE;
        listaDatos = null;
        contenidoArchivo = null;
        messageError = "";
        messageOk = "";
        fr = null;
        br = null;
        fileAddressRequest = null;
        fileAddressResultLog = null;
    }

    /**
     *
     * @param archivoPlano
     * @return
     * @throws IOException
     */
    public ArrayList<AddressRequest> cargaArchivo(StringBuffer archivoPlano) throws IOException {
        try {
            inizialice();
            fileAddressRequest = new ArrayList<AddressRequest>();
            fileAddressResultLog = new ArrayList<AddressFactResultBatch>();

            String[] Archivo;
            String[] Fila;
            String Campo;
            String Registro;
            String Separator;
            int Estrato = 0;
            int tamanoFila = 0;
            String Dir1 = "";
            String Dir2 = "";

            Separator = System.getProperty("line.separator");
            Archivo = archivoPlano.toString().split(Separator);
            for (int i = 0; i < Archivo.length; i++) {
                Fila = Archivo[i].split(",");
                message = "";
                tamanoFila = Fila.length;
                if (tamanoFila > 0) {
                    direccion = new AddressRequest();
                    direccionLog = new AddressFactResultBatch();
                    for (int j = 0; j < Fila.length; j++) {
                        Campo = Fila[j].trim();
                        
                        if (j == 0) {
                            direccionLog.setcuenta(Campo);
                            if (Campo.isEmpty() || "".equals(Campo)) {
                                message = message + "campo cuenta vacío.";
                            }
                        } else if (j == 1) {
                            direccionLog.setnombre(Campo);
                            if (Campo.isEmpty() || "".equals(Campo)) {
                            }
                        } else if (j == 2) {
                            direccion.setNeighborhood(Campo);
                            direccionLog.setNeighborhood(Campo);
                            if (Campo.isEmpty() || "".equals(Campo)) {
                                direccionLog.setNeighborhood(Campo);
                            }
                        } else if (j == 3) {
                            Dir1 = Campo;
                            direccionLog.setNo_Unidad(Campo);
                            if (Campo.isEmpty() || "".equals(Campo)) {
                                message = message + " campo No. de la unidad vacío.";
                            }
                        } else if (j == 4) {
                            Dir1 = Campo + " " + Dir1;
                            direccionLog.setCalle_Carrera(Campo);
                            if (Campo.isEmpty() || "".equals(Campo)) {
                                message = message + " campo calle o cra vacío.";
                            }
                        } else if (j == 5) {
                            Dir2 = Campo;
                            direccionLog.setApto(Campo);
                            if (Campo.isEmpty() || "".equals(Campo)) {
                            }

                        } else if (j == 6) {
                            Dir2 = Campo + Dir2;
                            direccionLog.setComplemento(Campo);
                            if (Campo.isEmpty() || "".equals(Campo)) {
                            }
                        } else if (j == 7) {
                            direccion.setCity(Campo);
                            direccionLog.setCity(Campo);
                            if (Campo.isEmpty() || "".equals(Campo)) {
                                message = message + " campo Ciudad vacío.";
                            }

                        } else if (j == 8) {
                            direccion.setState(Campo);
                            direccionLog.setState(Campo);
                            if (Campo.isEmpty() || "".equals(Campo)) {
                                message = message + " campo Departamento vacío.";
                            }
                        } /*else
                        if (j == 9) {
                        direccionLog.setTransaction(Campo.toUpperCase());
                        if (Campo.isEmpty() || "".equals(Campo)) {
                        message = " campo Acción vacío.";
                        }
                        
                        }
                         */ else if (j == 9) {
                            direccionLog.setFlagoperacion(Campo.toUpperCase());
                            if (Campo.isEmpty() || "".equals(Campo)) {
                                message = message + " campo Validar vacío.";
                            }
                            if (!"".equals(message)) {
                                direccionLog.setLogTraslate(message);
                            }
                        } else {
                            message = "Se encontro una columna de más en el registro.";
                            if (!"".equals(message)) {
                                direccionLog.setMensaje(message);
                            }
                        }
                    }
                    direccionLog.setTransaction("INSERTAR"); //Se envia por defecto la tarea de insercion
                    direccion.setAddress(Dir1 + " " + Dir2);
                    direccionLog.setAddress(Dir1 + " " + Dir2);
                    direccion.setLevel("S");
                    direccionLog.setLevel("S");
                    fileAddressRequest.add(direccion);
                    fileAddressResultLog.add(direccionLog);
                } else {
                    message = "El registro llegó vacio, sin información";
                    setMessageError(message);
                }
            }
            return fileAddressRequest;
        } catch (Exception e) {
            message = "Error en el proceso de carga de archivo, intento más tarde, si persiste comuniquese con el administrado del sistema.";
            setMessageError(message);
            fileAddressRequest = null;
            LOGGER.error("Se produjo un error al momento de ejecutar el método .'"+ClassUtils.getCurrentMethodName(this.getClass())+"':"+ e.getMessage());
            return fileAddressRequest;
        }
    }

    /**
     *
     * @param carga
     * @return
     */
    public byte[] generarArchivo(ArrayList<AddressFactResultBatch> carga) {
        try {
            String address = "";
            String Qual = "";
            String recomendaciones = "";
            int nivelConfiabilidad = 0;
            byte[] fileResult = null;
            StringBuilder cache = new StringBuilder();
            int topeArchivo = carga.size();
            int i = 0;
            int nivelConfianza = 0;
            //Encabezado  
            cache.append("CUENTA");
            cache.append(',');
            cache.append("NOMBRE");
            cache.append(',');
            cache.append("BARRIO");
            cache.append(',');
            cache.append("NO. DE LA UNIDAD");
            cache.append(',');
            cache.append("CALLE O CRA");
            cache.append(',');
            cache.append("APARTAMENTO");
            cache.append(',');
            cache.append("CIUDAD");
            cache.append(',');
            cache.append("DEPARTAMENTO");
            cache.append(',');
            cache.append("RESULTADO");
            cache.append("\n");
            while (i < topeArchivo) {
                if (i == 0) {
                } else {
                    direccionTradRec = new AddressService();
                    String CodDane = "";
                    String TipoDireccionByCodDane = "";
                    try {
                        CodDane = carga.get(i).getAddressResult().getCoddanemcpio().toString().trim();
                    } catch (Exception ecd) {
                        CodDane = "";
                    }
                    try {
                        TipoDireccionByCodDane = TipoDireccionByCodDane(CodDane, carga.get(i).getCodDir().toString());
                    } catch (Exception etdbcd) {
                        TipoDireccionByCodDane = "";
                    }
                    cache.append(carga.get(i).getcuenta().toString());
                    cache.append(delimitadorCol);
                    cache.append(carga.get(i).getnombre().toString());
                    cache.append(delimitadorCol);
                    cache.append(carga.get(i).getNeighborhood().toString());
                    cache.append(delimitadorCol);
                    try {
                        String Unidad = obtenerUnidad(carga.get(i).getCodDir().toString(), TipoDireccionByCodDane).replaceAll("  ", " ");
                        if ("".equals(Unidad)) {
                            Unidad = carga.get(i).getNo_Unidad();
                        }
                        cache.append(Unidad);
                    } catch (Exception eou) {
                        cache.append(carga.get(i).getNo_Unidad());
                    }
                    cache.append(delimitadorCol);
                    try {
                        String CalleCarrera = obtenerCalleCra(carga.get(i).getCodDir().toString(), TipoDireccionByCodDane).replaceAll("  ", " ");
                        if ("".equals(CalleCarrera)) {
                            CalleCarrera = carga.get(i).getCalle_Carrera() + " " + carga.get(i).getComplemento();
                        }
                        cache.append(CalleCarrera);
                    } catch (Exception eocc) {
                        cache.append(carga.get(i).getCalle_Carrera());
                    }
                    cache.append(delimitadorCol);
                    try {
                        String Apto = obtenerApto(carga.get(i).getCodDir().toString(), TipoDireccionByCodDane).replaceAll("  ", " ");
                        if ("".equals(Apto)) {
                            Apto = carga.get(i).getApto();
                        }
                        cache.append(Apto);
                    } catch (Exception eou) {
                        cache.append(carga.get(i).getApto());
                    }
                    cache.append(delimitadorCol);
                    cache.append(carga.get(i).getCity().toString());
                    cache.append(delimitadorCol);
                    cache.append(carga.get(i).getState().toString());
                    cache.append(delimitadorCol);
                    cache.append(carga.get(i).getLogTransactionBD().toString());
                    cache.append("\n");
                }
                i++;
            }
            try {
                fileResult = cache.toString().getBytes("utf-8");
                return fileResult;
            } catch (final UnsupportedEncodingException ex) {
                message = "Se produjo un error al Escribir el archivo.";
                LOGGER.error("Error al momento de generar el archivo. EX000: " + ex.getMessage(), ex);
                return null;
            }
        } catch (Exception ey) {
            LOGGER.error(ey);
            return null;
        }
    }

    /**
     *
     * @param CodDane
     * @param CodDir
     * @return
     */
    private String TipoDireccionByCodDane(String CodDane, String CodDir) {
        SolicitudNegocioEJB obj = new SolicitudNegocioEJB();
        String tipoDir;
        try {
            tipoDir = obj.ConsultaTipoDireccionByCodigoDane(CodDane);
        } catch (Exception ex) {
            tipoDir = "";
        }
        //Valor Z para las direcciones tipo manzana casa, B para en blanco
        return ((CodDir.length() > 90) ? "Z" : (("".equals(tipoDir) || tipoDir == null) ? "B" : tipoDir));
    }

    /**
     *
     * @param CodDir
     * @param TipoDireccionByCodDane
     * @return
     */
    public String obtenerUnidad(String CodDir, String TipoDireccionByCodDane) {
        String rta = "";
        int tempo = 0;

        try {
            if ("B".equals(TipoDireccionByCodDane)) {
                tempo = Integer.parseInt(CodDir.substring(15, 18));
                rta += tempo;
                rta += " " + CodDir.substring(19, 20).replace("0", "");
                rta += " " + CodDir.substring(23, 26).replace("0", "");
                rta += " " + CodDir.substring(27, 28).replace("0", "");
                tempo = (CodDir.length() == 78) ? Integer.parseInt(CodDir.substring(28, 30)) : Integer.parseInt(CodDir.substring(28, 31));
                rta += tempo;
                rta += " " + CodDir.substring(9, 10).replace("0", "");
            }

            if ("C".equals(TipoDireccionByCodDane)) {
                rta = CodDir.substring(12, 14);
                tempo = Integer.parseInt(CodDir.substring(17, 19));
                rta += " " + tempo;
                rta += " " + CodDir.substring(21, 22).replace("0", "");
                tempo = Integer.parseInt(CodDir.substring(24, 26));
                rta += "" + ((tempo == 0) ? "" : tempo);
                rta += " " + CodDir.substring(31, 34).replace("0", "");
                rta += " " + CodDir.substring(35, 36).replace("0", "");
                tempo = Integer.parseInt(CodDir.substring(36, 39));
                rta += tempo;
                rta += " " + CodDir.substring(9, 10).replace("0", "");
                rta += " " + obtenerComplemento(CodDir, TipoDireccionByCodDane);
            }

            if ("M".equals(TipoDireccionByCodDane)) {
                tempo = Integer.parseInt(CodDir.substring(15, 18));
                rta += tempo;
                rta += " " + CodDir.substring(25, 26).replace("0", "");
                rta += " " + CodDir.substring(27, 28).replace("0", "");
                tempo = Integer.parseInt(CodDir.substring(28, 31));
                rta += tempo;
                rta += " " + CodDir.substring(9, 10).replace("0", "");
            }
        } catch (NumberFormatException eou) {
            LOGGER.error(eou);
            rta = "";
        }
        return rta;
    }

    /**
     *
     * @param CodDir
     * @param TipoDireccionByCodDane
     * @return
     */
    public String obtenerCalleCra(String CodDir, String TipoDireccionByCodDane) {
        String rta = "";
        int tempo = 0;

        try {
            if ("B".equals(TipoDireccionByCodDane)) {
                rta = CodDir.substring(10, 12);
                tempo = Integer.parseInt(CodDir.substring(12, 15));
                rta += " " + tempo;
                rta += " " + CodDir.substring(18, 19).replace("0", "");
                rta += " " + CodDir.substring(20, 23).replace("0", "");
                rta += " " + CodDir.substring(26, 27).replace("0", "");
                rta += " " + CodDir.substring(8, 9).replace("0", "");
                rta += " " + obtenerComplemento(CodDir, TipoDireccionByCodDane);
            }

            if ("C".equals(TipoDireccionByCodDane)) {
                rta = CodDir.substring(10, 12);
                tempo = Integer.parseInt(CodDir.substring(14, 17));
                rta += " " + tempo;
                rta += " " + CodDir.substring(20, 21).replace("0", "");
                tempo = Integer.parseInt(CodDir.substring(22, 24));
                rta += "" + ((tempo == 0) ? "" : tempo);
                rta += " " + CodDir.substring(28, 31).replace("0", "");
                rta += " " + CodDir.substring(34, 35).replace("0", "");
                rta += " " + CodDir.substring(8, 9).replace("0", "");
                rta += " " + obtenerComplemento(CodDir, TipoDireccionByCodDane);
            }

            if ("M".equals(TipoDireccionByCodDane)) {
                rta = CodDir.substring(10, 12);
                tempo = Integer.parseInt(CodDir.substring(12, 15));
                rta += " " + tempo;
                rta += " " + CodDir.substring(24, 25).replace("0", "");
                rta += " " + CodDir.substring(26, 27).replace("0", "");
                rta += " " + CodDir.substring(18, 21).replace("0", "");
                rta += " " + CodDir.substring(8, 9).replace("0", "");
                rta += " " + obtenerComplemento(CodDir, TipoDireccionByCodDane);
            }
        } catch (NumberFormatException occ) {
            LOGGER.error(occ);
            rta = "";
        }
        return rta;
    }

    /**
     *
     * @param CodDir
     * @param TipoDireccionByCodDane
     * @return
     */
    public String obtenerApto(String CodDir, String TipoDireccionByCodDane) {
        String rta = "";
        try {
            if (("B".equals(TipoDireccionByCodDane)) || ("M".equals(TipoDireccionByCodDane))) {
                rta += ((CodDir.length() == 78) ? (("00".equals(CodDir.substring(54, 56))) ? "" : CodDir.substring(54, 62)) : (("00".equals(CodDir.substring(55, 57))) ? "" : CodDir.substring(55, 63)));
            }

            if ("C".equals(TipoDireccionByCodDane)) {
                rta += (("00".equals(CodDir.substring(63, 65))) ? "" : CodDir.substring(63, 71));
            }
        } catch (Exception eoa) {
            LOGGER.error(eoa);
            rta = "";
        }
        return rta;
    }

    /**
     *
     * @param CodDir
     * @param TipoDireccionByCodDane
     * @return
     */
    public String obtenerComplemento(String CodDir, String TipoDireccionByCodDane) {
        String rta = "";
        try {
            if (("B".equals(TipoDireccionByCodDane)) || ("M".equals(TipoDireccionByCodDane))) {
                rta += " " + ((CodDir.length() == 78) ? (("00".equals(CodDir.substring(30, 32))) ? "" : CodDir.substring(30, 38)) : (("00".equals(CodDir.substring(31, 33))) ? "" : CodDir.substring(31, 39)));
                rta += " " + ((CodDir.length() == 78) ? (("00".equals(CodDir.substring(38, 40))) ? "" : CodDir.substring(38, 46)) : (("00".equals(CodDir.substring(39, 41))) ? "" : CodDir.substring(39, 47)));
                rta += " " + ((CodDir.length() == 78) ? (("00".equals(CodDir.substring(46, 48))) ? "" : CodDir.substring(46, 54)) : (("00".equals(CodDir.substring(47, 49))) ? "" : CodDir.substring(47, 55)));
                rta += " " + ((CodDir.length() == 78) ? (("00".equals(CodDir.substring(62, 64))) ? "" : CodDir.substring(62, 70)) : (("00".equals(CodDir.substring(63, 65))) ? "" : CodDir.substring(63, 71)));
                rta += " " + ((CodDir.length() == 78) ? (("00".equals(CodDir.substring(70, 72))) ? "" : CodDir.substring(70, 78)) : (("00".equals(CodDir.substring(71, 73))) ? "" : CodDir.substring(71, 79)));
            }

            if ("C".equals(TipoDireccionByCodDane)) {
                rta += " " + (("00".equals(CodDir.substring(39, 41))) ? "" : CodDir.substring(39, 47));
                rta += " " + (("00".equals(CodDir.substring(47, 49))) ? "" : CodDir.substring(47, 55));
                rta += " " + (("00".equals(CodDir.substring(55, 57))) ? "" : CodDir.substring(55, 63));
                rta += " " + (("00".equals(CodDir.substring(71, 73))) ? "" : CodDir.substring(71, 79));
                rta += " " + (("00".equals(CodDir.substring(79, 81))) ? "" : CodDir.substring(79, 87));
            }
        } catch (Exception eoc) {
            LOGGER.error(eoc);
            rta = "";
        }
        return rta;
    }

    /**
     *
     * @return
     */
    public ArrayList<AddressRequest> getFileAddressRequest() {
        return fileAddressRequest;
    }

    /**
     *
     * @param fileAddressRequest
     */
    public void setFileAddressRequest(ArrayList<AddressRequest> fileAddressRequest) {
        this.fileAddressRequest = fileAddressRequest;
    }

    /**
     *
     * @return
     */
    public ArrayList<AddressFactResultBatch> getFileAddressResultLog() {
        return fileAddressResultLog;
    }

    /**
     *
     * @param fileAddressResultLog
     */
    public void setFileAddressResultLog(ArrayList<AddressFactResultBatch> fileAddressResultLog) {
        this.fileAddressResultLog = fileAddressResultLog;
    }

    /**
     *
     * @return
     */
    public ArrayList<String> getListaDatos() {
        return listaDatos;
    }

    /**
     *
     * @param listaDatos
     */
    public void setListaDatos(ArrayList<String> listaDatos) {
        this.listaDatos = listaDatos;
    }

    /**
     *
     * @return
     */
    public ArrayList<ArrayList<String>> getcontenidoArchivo() {
        return contenidoArchivo;
    }

    /**
     *
     * @param ContenidoArchivo
     */
    public void setcontenidoArchivo(ArrayList<ArrayList<String>> ContenidoArchivo) {
        this.contenidoArchivo = ContenidoArchivo;
    }

    /**
     *
     * @return
     */
    public String getdelimitadorCol() {
        return delimitadorCol;
    }

    /**
     *
     * @param Delimitador
     */
    public void setdelimitadorCol(String Delimitador) {
        this.delimitadorCol = Delimitador;
    }

    /**
     *
     * @return
     */
    public String getDir_tipoTransaccion() {
        return dir_tipoTransaccion;
    }

    /**
     *
     * @param dir_tipoConsulta
     */
    public void setDir_tipoTransaccion(String dir_tipoConsulta) {
        this.dir_tipoTransaccion = dir_tipoConsulta;
    }

    /**
     *
     * @return
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     *
     * @return
     */
    public String getNombreA() {
        return nombreA;
    }

    /**
     *
     * @param nombreA
     */
    public void setNombreA(String nombreA) {
        this.nombreA = nombreA;
    }

    /**
     *
     * @return
     */
    public int getSize() {
        return size;
    }

    /**
     *
     * @param size
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     *
     * @return
     */
    public String getMessageError() {
        return messageError;
    }

    /**
     *
     * @param messageError
     */
    public void setMessageError(String messageError) {
        this.messageError = messageError;
    }

    /**
     *
     * @return
     */
    public String getMessageOk() {
        return messageOk;
    }

    /**
     *
     * @param messageOk
     */
    public void setMessageOk(String messageOk) {
        this.messageOk = messageOk;
    }
}
