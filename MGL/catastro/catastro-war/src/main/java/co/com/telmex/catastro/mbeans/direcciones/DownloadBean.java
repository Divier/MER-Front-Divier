package co.com.telmex.catastro.mbeans.direcciones;

import co.com.telmex.catastro.util.FacesUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * Clase DownloadBean
 * Extiende de BaseMBean
 *
 * @author 	Ana María Malambo
 * @version	1.0
 */
@ManagedBean(name = "downloadBean")
public class DownloadBean {

    private static final Logger LOGGER = LogManager.getLogger(DownloadBean.class);
    
    /**
     * 
     * @param event
     */
    public void viewPdf(ActionEvent event) {
        String filename = "C:/Users/alquiler/Desktop/prueba.xls";

        // use your own method that reads file to the byte array
        byte[] file = null;
        try {
            file = getTheContentOfTheFile(filename);
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error en getTheContentOfTheFile" + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en getTheContentOfTheFile" + e.getMessage() , e, LOGGER);
        }

        FacesContext faces = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) faces.getExternalContext().getResponse();
        response.setContentType("application/xls");
        response.setContentLength(file.length);
        response.setHeader("Content-disposition", "inline; filename=" + filename + "");
        try {
            ServletOutputStream out;
            out = response.getOutputStream();
            out.write(file);
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error en ServletOutputStream" + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en ServletOutputStream" + e.getMessage() , e, LOGGER);
        }
        faces.responseComplete();
    }

    /**
     * 
     * @param filename
     * @return
     * @throws FileNotFoundException
     * @throws IOException 
     */
    private byte[] getTheContentOfTheFile(String filename) throws FileNotFoundException, IOException {
        File file = new File(filename);

        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        try {
            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                bos.write(buf, 0, readNum); //no doubt here is 0
                //Writes len bytes from the specified byte array starting at offset off to this byte array output stream.
            }
        } catch (IOException e) {
            FacesUtil.mostrarMensajeError("Error en getTheContentOfTheFile. " + e.getMessage() , e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en getTheContentOfTheFile. " + e.getMessage() , e, LOGGER);
        }
        byte[] bytes = bos.toByteArray();

        //below is the different part
        File someFile = new File("C:/Users/alquiler/Desktop/prueba.xls");
        FileOutputStream fos = new FileOutputStream(someFile);
        fos.write(bytes);
        fos.flush();
        fos.close();
        return bytes;
    }
}
