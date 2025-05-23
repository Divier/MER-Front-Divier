package co.com.telmex.catastro.mbeans.direcciones;

import co.com.claro.mgl.error.ApplicationException;
import co.com.telmex.catastro.delegate.CargueDirBatchDelegate;
import co.com.telmex.catastro.mbeans.comun.BaseMBean;
import co.com.telmex.catastro.mbeans.comun.ConstantSystem;
import co.com.telmex.catastro.util.FacesUtil;
import co.com.telmex.catastro.utilws.SecurityLogin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Clase CargaDirBatchMBean
 * Extiende de BaseMBean
 * Implementa Serialización
 *
 * @author 	Deiver Rovira
 * @version	1.0
 */
@ViewScoped
@ManagedBean(name = "cargaDirBatchMBean")
public class CargaDirBatchMBean extends BaseMBean implements Serializable {

    private static final String MSG_ERRROR_LECTURA_ARCHIVO = "ERROR ARCHIVO VACIO.";
    private static final String ERROR_CANTIDAD_ARCHIVOS = "Error, sólo debe subir un archivo.";
    private static final String ERROR_FORMATO_ARCHIVO = "Error, el archivo .";
    private static final String ERROR_CONTENIDO_ARCHIVO = "Error, el contenido del archivo no pudo ser cargado.";
    private static final String MSG_ERROR_ESTRUCTURA = "Error, verifique la estructura del archivo a cargar.";
    private static final String MSG_FORMATO_CORRECTO = "Archivo Ok, presione Procesar para cargar el archivo.";
    private String messageOk;
    private String messageError;
    private String flagIndicator;
    private String messageValidation;
    private String message;
    private int cantidadArchivos;
    private String nameFileUp;
    private boolean linkButton;
    private StringBuffer fileLoaded;
    /**
     * 
     */
    public byte[] fileLoad;
    /**
     * 
     */
    public List<byte[]> filesLoad;
    private byte[] fileDonwload;
    /**
     * 
     */
    public Date Time;
    /**
     * 
     */
    public int size;
    /**
     * 
     */
    public boolean err;
    /**
     * 
     */
    public boolean upload = true;
    private boolean process;
    private boolean download;
    FacesContext facesContext = FacesContext.getCurrentInstance();
    HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
    HttpServletResponse responsePage = (HttpServletResponse) facesContext.getCurrentInstance().getExternalContext().getResponse();

    
     private static final Logger LOGGER = LogManager.getLogger(CargaDirBatchMBean.class);
    /**
     * 
     */
    public CargaDirBatchMBean() {
        super();
        try {
            SecurityLogin securityLogin = new SecurityLogin(facesContext);
            if (!securityLogin.isLogin()) {
                responsePage.sendRedirect(securityLogin.redireccionarLogin());
                return;
            }
        } catch (IOException ex) {
            FacesUtil.mostrarMensajeError("Se generea error en CargaDirBatchMBean class" + ex.getMessage() , ex, LOGGER);
        } catch (Exception ex) {
            FacesUtil.mostrarMensajeError("Se generea error en CargaDirBatchMBean class" + ex.getMessage() , ex, LOGGER);
        }
             size = 0;
        setDownload(false);
        setProcess(false);
        setLinkButton(true);
        err = false;
        messageError = "";
        messageValidation = "";
        messageOk = "";
        nameFileUp = "";
        message = "";

        Enumeration e = session.getAttributeNames();
        while (e.hasMoreElements()) {
            String attr = (String) e.nextElement();
            if (attr.toUpperCase().equals("MyArchivo".toUpperCase())) {
                byte[] bytes = (byte[]) session.getAttribute(attr);
                session.removeAttribute(attr);
                try {
                    listenerUploadFile(bytes);
                } catch (Exception ex) {
                    message = MSG_ERROR_ESTRUCTURA;
                    FacesUtil.mostrarMensajeError(message + ex.getMessage() , ex, LOGGER);
                }
            }
        }
           

    }

    /**
     * 
     * @return
     */
    public boolean getLinkButton() {
        return linkButton;
    }

    /**
     * 
     * @param linkButton
     */
    public void setLinkButton(boolean linkButton) {
        this.linkButton = linkButton;
    }

    /**
     * 
     * @return
     */
    public byte[] getFileDonwload() {
        return fileDonwload;
    }

    /**
     * 
     * @param fileDonwload
     */
    public void setFileDonwload(byte[] fileDonwload) {
        this.fileDonwload = fileDonwload;
    }

    /**
     * 
     * @return
     */
    public StringBuffer getFileLoaded() {
        return fileLoaded;
    }

    /**
     * 
     * @param fileLoaded
     */
    public void setFileLoaded(StringBuffer fileLoaded) {
        this.fileLoaded = fileLoaded;
    }

    /**
     * 
     * @return
     */
    public int getCantidadArchivos() {
        return cantidadArchivos;
    }

    /**
     * 
     * @param cantidadArchivos
     */
    public void setCantidadArchivos(int cantidadArchivos) {
        this.cantidadArchivos = cantidadArchivos;
    }

    /**
     * 
     * @return
     */
    public String getFlagIndicator() {
        return flagIndicator;
    }

    /**
     * 
     * @param flagIndicator
     */
    public void setFlagIndicator(String flagIndicator) {
        this.flagIndicator = flagIndicator;
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

    /**
     * 
     * @return
     */
    public String getMessageValidation() {
        return messageValidation;
    }

    /**
     * 
     * @param messageValidation
     */
    public void setMessageValidation(String messageValidation) {
        this.messageValidation = messageValidation;
    }

    /**
     * 
     * @return
     */
    public String getNameFileUp() {
        return nameFileUp;
    }

    /**
     * 
     * @param nameFileUp
     */
    public void setNameFileUp(String nameFileUp) {
        this.nameFileUp = nameFileUp;
    }

    /**
     * 
     * @return 
     */
    @Override
    public String getMessage() {
        return message;
    }

    /**
     * 
     * @param message 
     */
    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 
     * @return
     */
    public boolean getDownload() {
        return download;
    }

    /**
     * 
     * @param download
     */
    public void setDownload(boolean download) {
        this.download = download;
    }

    /**
     * 
     * @return
     */
    public boolean getProcess() {
        return process;
    }

    /**
     * 
     * @param process
     */
    public void setProcess(boolean process) {
        this.process = process;
    }

    /**
     * 
     */
    private void inicialize() {
        cantidadArchivos = 0;
        fileDonwload = null;
        filesLoad = null;
        fileLoaded = null;
        Time = null;
    }

    /**
     * 
     * @param file
     * @return
     * @throws IOException
     */
    public String validateLabels(byte[] file) throws IOException {
        String Separator = null; //OJO validar
        StringBuffer validador = new StringBuffer();

        String[] Archivo;
        String[] Fila;
        String Campo;
        file.toString();
        for (int i = 0; i < file.length; i++) {

            char c = (char) file[i];
            validador.append(c);             
        }

        Separator = System.getProperty("line.separator");
        Archivo = validador.toString().split(Separator);

        if (Archivo.length != 0 && !"".equals(Archivo.toString()) && Archivo != null) {

            for (int contFila = 0; contFila < Archivo.length; contFila++) {
                Fila = Archivo[contFila].split(",");
                if (Fila.length != 7) {
                    messageValidation = MSG_ERROR_ESTRUCTURA;
                    err = true;
                }
            }

            Fila = Archivo[0].split(",");

            for (int column = 0; column < Fila.length; column++) {
                Campo = Fila[column].trim();
                switch (column) {

                    case 0:

                        if (ConstantSystem.cargarPropiedad("DEPARTAMENTO").equals(Campo) == false) {
                            messageValidation = MSG_ERROR_ESTRUCTURA;
                            err = true;
                        }

                        break;

                    case 1:
                        if (ConstantSystem.cargarPropiedad("CIUDAD").equals(Campo) == false) {
                            messageValidation = MSG_ERROR_ESTRUCTURA;
                            err = true;
                        }
                        break;

                    case 2:
                        if (ConstantSystem.cargarPropiedad("BARRIO").equals(Campo) == false) {
                            messageValidation = MSG_ERROR_ESTRUCTURA;
                            err = true;
                        }
                        break;

                    case 3:
                        if (ConstantSystem.cargarPropiedad("DIRECCION").equals(Campo) == false) {
                            messageValidation = MSG_ERROR_ESTRUCTURA;
                            err = true;
                        }
                        break;
                    case 4:
                        if ("ESTRATO".equals(Campo) == false) {
                            messageValidation = MSG_ERROR_ESTRUCTURA;
                            err = true;
                        }
                        break;
                    case 5:
                        if (ConstantSystem.cargarPropiedad("ACCION").equals(Campo) == false) {
                            messageValidation = MSG_ERROR_ESTRUCTURA;
                            err = true;
                        }
                        break;
                    case 6:
                        if (ConstantSystem.cargarPropiedad("VALIDAR").equals(Campo) == false) {
                            messageValidation = MSG_ERROR_ESTRUCTURA;
                            err = true;
                        }
                        break;
                    case 7:
                        messageValidation = MSG_ERROR_ESTRUCTURA;
                        err = true;
                        break;
                }
            }
        }
        if (err) {
            setMessage(messageValidation);
            return message;
        } else {
            setMessage(MSG_FORMATO_CORRECTO);
            return message;
        }
    }

    /**
     * 
     * @param fileData
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void listenerUploadFile(byte[] fileData) throws ApplicationException {
        try {
            inicialize();

            fileLoaded = new StringBuffer();
            StringBuffer text = new StringBuffer();

            message = validateLabels(fileData);

            if (message.equals(MSG_FORMATO_CORRECTO)) {

                fileLoad = fileData;

                for (int i = 0; i < fileLoad.length; i++) {
                    char c = (char) fileLoad[i];
                    text.append(c);
                }

                setFileLoaded(text);
                if (fileLoaded != null) {
                    messageOk = "Carga completa, ejecute procesar para cargar la información";
                    setMessage(messageOk);
                    setProcess(true);
                    setLinkButton(false);
                } else {
                    message = ERROR_CONTENIDO_ARCHIVO;
                }
            } else {
                message = MSG_ERROR_ESTRUCTURA;
            }

        } catch (IOException e) {
            messageError = ERROR_CONTENIDO_ARCHIVO;
            FacesUtil.mostrarMensajeError(messageError + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            messageError = ERROR_CONTENIDO_ARCHIVO;
            FacesUtil.mostrarMensajeError(messageError + e.getMessage() , e, LOGGER);
        }
    }

    /**
     * 
     * @param files
     * @return
     */
    public int cantFiles(List<byte[]> files) {

        for (int i = 0; i < files.size(); i++) {

            cantidadArchivos++;
        }
        if (cantidadArchivos > 0) {
            messageValidation = ERROR_CANTIDAD_ARCHIVOS;
        }
        return cantidadArchivos;
    }

    /**
     * 
     * @throws co.com.claro.mgl.error.ApplicationException
     */
    public void doProcess() throws ApplicationException {
        String flag = "";

        try {
            setFileDonwload(CargueDirBatchDelegate.getCargueDirBatchEjb(getFileLoaded(), user.getUsuLogin()));
        } catch (Exception er) {
            FacesUtil.mostrarMensajeError("Error en doProcess" + er.getMessage() , er, LOGGER);
            try {
                flag = CargueDirBatchDelegate.getFlagIndicator();
                messageError = CargueDirBatchDelegate.getMessageError();
                setMessage(messageError);
            } catch (Exception e) {
                FacesUtil.mostrarMensajeError("Error en CargueDirBatchDelegate" + e.getMessage() , e, LOGGER);
            }

        }
        if (fileDonwload != null) {
            if (flag.equals("1001")) {
                setProcess(false);
                messageOk = "El archivo cargado tiene errores de estructura. Intente con otro archivo.";
                setMessage(messageOk);
                setDownload(false);
                setLinkButton(false);
            } else {
                setProcess(false);
                messageOk = "Archivo cargado. Presione Descargar para obtener el archivo resultante del proceso.";
                setMessage(messageOk);
                setDownload(true);
            }
        }
    }

    /**
     * 
     */
    public void doDownloadCvs() {


        final FacesContext context = FacesContext.getCurrentInstance();
        final HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmmss");
        String fileName = "Resultado_" + sdf.format(today);
        response.setHeader("Content-disposition", "attached; filename=\"" + fileName + ".csv\"");

        response.setContentType("application/force.download");

        try {
            if (fileDonwload != null) {
                messageOk = "Finalizo el proceso con exito.";
                setMessage(messageOk);
            }
            response.getOutputStream().write(getFileDonwload());
            response.getOutputStream().flush();
            response.getOutputStream().close();
            context.responseComplete();

            FacesContext.getCurrentInstance().responseComplete();

        } catch (final IOException e) {
            setProcess(false);
            setDownload(false);
            messageError = "Revise su navegador, posiblemente tiene bloqueada ventana emergente.";
            setMessage(messageError);
            FacesUtil.mostrarMensajeError(messageError + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            setProcess(false);
            setDownload(false);
            messageError = "Revise su navegador, posiblemente tiene bloqueada ventana emergente.";
            setMessage(messageError);
            FacesUtil.mostrarMensajeError(messageError + e.getMessage() , e, LOGGER);
        }
    }
}