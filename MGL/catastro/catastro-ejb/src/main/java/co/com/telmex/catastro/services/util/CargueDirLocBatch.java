package co.com.telmex.catastro.services.util;

import co.com.claro.mgl.utils.ClassUtils;
import co.com.telmex.catastro.data.AddressRequest;
import co.com.telmex.catastro.data.AddressResultBatch;
import co.com.telmex.catastro.data.AddressService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Clase CargueDirLocBatch
 *
 * @author 	Ana María Malambo
 * @version	1.0
 * @version     1.1   Modificado fase 1 direcciones carlos villamil l013-05-23 
 */
public class CargueDirLocBatch {

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
    ArrayList<AddressResultBatch> fileAddressResultLog;
    AddressRequest direccion;
    AddressResultBatch direccionLog;
    AddressService direccionTradRec;
    private static final Logger LOGGER = LogManager.getLogger(CargueDirLocBatch.class);
    /**
     * 
     */
    public CargueDirLocBatch() {
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
    public CargueDirLocBatch(int size,
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
            fileAddressResultLog = new ArrayList<AddressResultBatch>();

            String[] Archivo;
            String[] Fila;
            String Campo;
            String Registro;
            String Separator;
            int Estrato = 0;
            int tamanoFila = 0;

            Separator = System.getProperty("line.separator");
            Archivo = archivoPlano.toString().split(Separator);
            for (int i = 0; i < Archivo.length; i++) {
                Fila = Archivo[i].split(",");
                message = "";
                tamanoFila = Fila.length;
                if (tamanoFila > 0) {
                    direccion = new AddressRequest();
                    direccionLog = new AddressResultBatch();
                    for (int j = 0; j < Fila.length; j++) {
                        Campo = Fila[j].trim();

                        if (j == 0) {
                            direccion.setState(Campo);
                            direccionLog.setState(Campo);
                            if (Campo.isEmpty() || "".equals(Campo)) {
                                message = message + "campo Departamento vacío.";
                            }
                        } else if (j == 1) {
                            direccion.setCity(Campo);
                            direccionLog.setCity(Campo);
                            if (Campo.isEmpty() || "".equals(Campo)) {
                                message = message + " campo Ciudad vacío.";
                            }
                        } else if (j == 2) {
                            direccion.setNeighborhood(Campo);
                            direccionLog.setNeighborhood(Campo);
                            if (Campo.isEmpty() || "".equals(Campo)) {

                            }
                        } else if (j == 3) {
                            direccion.setAddress(Campo);
                            direccionLog.setAddress(Campo);
                            if (Campo.isEmpty() || "".equals(Campo)) {
                                message = message + " campo Dirección vacío.";
                            }
                        } else if (j == 4) {
                            direccion.setLevel("C");
                            direccionLog.setLevel(Campo);
                            try {
                                if (!Campo.equals("N/A")) {
                                    Estrato = Integer.valueOf(Campo);
                                    if (Estrato > 9 || Estrato < 0) {
                                        message = message + " Estrato no valido.";
                                    }
                                }
                            } catch (NumberFormatException ex) {
								String msg = "Se produjo un error al momento de ejecutar el método '"+
								ClassUtils.getCurrentMethodName(this.getClass())+"': " + ex.getMessage();
								LOGGER.error(msg);
                                message = message + " Estrato no valido.";
                                j = 4;
                            }
                        } else if (j == 5) {
                            direccionLog.setTransaction(Campo.toUpperCase());
                            if (Campo.isEmpty() || "".equals(Campo)) {
                                message = message + " campo Acción vacío.";
                            }
                        } else if (j == 6) {
                            direccionLog.setFlagoperacion(Campo.toUpperCase());
                            if (Campo.isEmpty() || "".equals(Campo)) {
                                message = message + " campo Validar vacío.";
                            }
                        } else {
                            message = message + "Se encontro una columna de más en el registro.";
                        }
                        if (!"".equals(message)) {
                            direccionLog.setLogTraslate(message);
                        }
                    }
                    fileAddressRequest.add(direccion);
                    fileAddressResultLog.add(direccionLog);
                } else {
                    message = "El registro llegó vacio, sin información";
                    setMessageError(message);
                }
            }
            return fileAddressRequest;
        } catch (Exception e) {

            message = "error en clase cargueDirLocbatch,metodo cargaArchivo error: " + e;
            //message = "Error en el proceso de carga de archivo, intento más tarde, si persiste comuniquese con el administrado del sistema."
            setMessageError(message);
            LOGGER.error(message);
            fileAddressRequest = null;
            return fileAddressRequest;
        }
    }

    /**
     * 
     * @param carga
     * @return
     */
    public byte[] generarArchivo(ArrayList<AddressResultBatch> carga) {
        try {
            String address = "";
            String Qual = "";
            String recomendaciones = "";
            int nivelConfiabilidad = 0;
            byte[] fileResult = null;
            StringBuffer cache = new StringBuffer();
            int topeArchivo = carga.size();
            int i = 0;
            int nivelConfianza = 0;

            //Encabezado  
            cache.append("DEPARTAMENTO");
            cache.append(',');
            cache.append("CIUDAD");
            cache.append(',');
            cache.append("BARRIO");
            cache.append(',');
            cache.append("DIRECCION");
            cache.append(',');
            cache.append("ESTRATO");
            cache.append(',');
            cache.append("ACCION");
            cache.append(',');
            cache.append("ESTANDARIZAR");
            cache.append(',');
            cache.append("DIRECCION ESTANDARIZADA");
            cache.append(',');
            cache.append("NIVEL CONFIABILIDAD");
            cache.append(',');
            //Inicio Modificacion Fase I nodos Carlos Villamil V 1.1                    
            cache.append("NODO BI");
            cache.append(',');
            cache.append("NODO UNIUNO");
            cache.append(',');
            cache.append("NODO UNIDOS");
            cache.append(',');
            //Fin Modificacion Fase I nodos Carlos Villamil V 1.1                                
            cache.append("RECOMENDACIONES");
            cache.append(',');
            cache.append("LOG ESTANDARIZACION");
            cache.append(',');
            cache.append("RESULTADO REPOSITORIO");
            cache.append(',');
            cache.append("LOG REPOSITORIO");
            cache.append("\n");
            while (i < topeArchivo) {
                if (i == 0) {
                } else {
                    direccionTradRec = new AddressService();


                    cache.append(carga.get(i).getState().toString());
                    cache.append(delimitadorCol);
                    cache.append(carga.get(i).getCity().toString());
                    cache.append(delimitadorCol);
                    cache.append(carga.get(i).getNeighborhood().toString());
                    cache.append(delimitadorCol);
                    cache.append(carga.get(i).getAddress().toString());
                    cache.append(delimitadorCol);
                    cache.append(carga.get(i).getLevel().toString());
                    cache.append(delimitadorCol);
                    cache.append(carga.get(i).getTransaction().toString());
                    cache.append(delimitadorCol);
                    cache.append(carga.get(i).getFlagoperacion().toString());
                    cache.append(delimitadorCol);
                    try {

                        direccionTradRec = carga.get(i).getAddressResult();
                        if (direccionTradRec == null) {
                            i++;
                            continue;
                        }

                        try {
                            if (direccionTradRec.getAddress().isEmpty() || "".equals(direccionTradRec.getAddress())) {
                                address = " ";
                                direccionTradRec.setAddress(address);
                            }
                        } catch (Exception ae) {
                            LOGGER.error("Error al momento de generar el archivo. EX000: " + ae.getMessage(), ae);
                            address = "null";
                            direccionTradRec.setAddress(address);
                        }

                        try {
                            if (direccionTradRec.getQualifiers().isEmpty() || "".equals(direccionTradRec.getQualifiers())) {
                                Qual = "0";
                                direccionTradRec.setQualifiers(Qual);
                            }
                        } catch (Exception qe) {
                            LOGGER.error("Error al momento de generar el archivo. EX000: " + qe.getMessage(), qe);
                            Qual = "0";
                            direccionTradRec.setQualifiers(Qual);
                        }

                        try {
                            if (direccionTradRec.getRecommendations().isEmpty() || "".equals(direccionTradRec.getRecommendations())) {
                                recomendaciones = " verifique los campos que conforman el registro.";
                                direccionTradRec.setQualifiers(recomendaciones);
                            }
                        } catch (Exception re) {
                            recomendaciones = " verifique los campos que conforman el registro.";
                            LOGGER.error(recomendaciones + re.getMessage(), re);
                            direccionTradRec.setQualifiers(recomendaciones);
                        }
                    } catch (Exception ed) {
                        //La información de esta excepción se agrego a los if en la parte superior bajo un try if end if catch
                    }

                    if ((direccionTradRec.getAddress().toString()).isEmpty() || (direccionTradRec.getAddress().toString()).equalsIgnoreCase("null") || "".equals(direccionTradRec.getAddress().toString())) {
                        address = "Dirección no estandarizada";
                        cache.append(address);
                    } else {
                        cache.append(direccionTradRec.getAddress());
                    }
                    cache.append(delimitadorCol);
                    if ((direccionTradRec.getQualifiers()).isEmpty() || "".equals(direccionTradRec.getQualifiers()) || (direccionTradRec.getQualifiers()).equalsIgnoreCase("null")) {
                        Qual = "0";
                        cache.append(Qual);
                    } else {
                        nivelConfiabilidad = Integer.parseInt(direccionTradRec.getQualifiers());
                        if (nivelConfiabilidad >= Constant.PORCENTAJE_CONFIABILIDAD_ALTA) {
                            cache.append(nivelConfiabilidad);
                        } else {
                            Qual = "0";
                            cache.append(Qual);
                        }
                    }
                    cache.append(delimitadorCol);
                    //INICIO Direcciones face I Carlos Vilamil 2013-05-24 V 1.1                    
                    if(direccionTradRec.getNodoUno()!=null && !direccionTradRec.getNodoUno().equals("")){
                        cache.append(direccionTradRec.getNodoUno());                            
                    }else{
                        cache.append("");
                    }    
                    cache.append(delimitadorCol);
                    if(direccionTradRec.getNodoDos()!=null && !direccionTradRec.getNodoDos().equals("")){
                        cache.append(direccionTradRec.getNodoDos());                            
                    }else{
                        cache.append("");
                    }    
                    cache.append(delimitadorCol);
                    if(direccionTradRec.getNodoTres()!=null && !direccionTradRec.getNodoTres().equals("")){
                        cache.append(direccionTradRec.getNodoTres());                            
                    }else{
                        cache.append("");
                    }    
                    //INICIO Direcciones face I Carlos Vilamil 2013-05-24 V 1.1                    
                    
                    cache.append(delimitadorCol);
                    recomendaciones = direccionTradRec.getRecommendations();
                    if (recomendaciones.isEmpty() || recomendaciones.equalsIgnoreCase("null") || "".equals(recomendaciones) || recomendaciones == null) {
                        recomendaciones = "Rechazada";
                        cache.append(recomendaciones);
                    } else {
                        cache.append(recomendaciones);
                    }
                    cache.append(delimitadorCol);
                    cache.append(carga.get(i).getLogTraslate());
                    cache.append(delimitadorCol);
                    cache.append(carga.get(i).getFlagoperacion());
                    cache.append(delimitadorCol);
                    cache.append(carga.get(i).getLogTransactionBD());
                    cache.append(delimitadorCol);
                    cache.append("\n");
                }
                i++;
            }
            try {
                fileResult = cache.toString().getBytes("utf-8");
                return fileResult;
            } catch (final UnsupportedEncodingException ex) {
                message = "Se produjo un error al Escribir el archivo en el metodo.'"+ClassUtils.getCurrentMethodName(this.getClass())+"':"+ ex.getMessage();
                LOGGER.error(message);
                return null;
            }
        } catch (NumberFormatException ey) {
                LOGGER.error("Se produjo un error al Escribir el archivo en el metodo .'"+ClassUtils.getCurrentMethodName(this.getClass())+"':"+ ey.getMessage());
            return null;
        }
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
    public ArrayList<AddressResultBatch> getFileAddressResultLog() {
        return fileAddressResultLog;
    }

    /**
     * 
     * @param fileAddressResultLog
     */
    public void setFileAddressResultLog(ArrayList<AddressResultBatch> fileAddressResultLog) {
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
