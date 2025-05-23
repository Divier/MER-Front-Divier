package co.com.claro.mgl.mbeans.direcciones;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.DireccionMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.DireccionMgl;
import co.com.telmex.catastro.util.FacesUtil;
import java.io.Serializable;
import java.util.Date;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.model.file.UploadedFile;


/**
 * Clase encargada de manipular los componentes de la vista cambioEstratoMasivo.xhtml
 * 
 * @author Gonzalo Andrés Galindo
 * @version 1.0
 */
@ViewScoped
@ManagedBean(name = "cambioEstratoMasivoBean")
public class CambioEstratoMasivoBean implements Serializable{
    
    private static final Logger LOGGER = LogManager.getLogger(CambioEstratoMasivoBean.class);

    /*Atributo para el usuario, se ingresa en la interfaz gráfica*/
    private String usuario;

    /*Atributo para la fecha de cargue del archivo, se ingresa en la interfaz gráfica*/
    private Date fechaCargue;
    
    /*Atributo para perfil de un usuario, se ingresa en la interfaz gráfica*/
    private String perfil;
    
    /*Atributo para la razón del cargue del archivo, se ingresa en la interfaz gráfica*/
    private String detalle;
    
    /*Atributo para el archivo a procesar, se carga en la interfaz gráfica*/
    private UploadedFile uploadedFile;
    
    @EJB
    private DireccionMglFacadeLocal direccionMglFacade;
    
    public void cargarArchivo(){
        procesarArchivo();
        
        // TODO validacion de georeferencia por roles para saber que
        //id tiene la dirección a la cual se va a modificar el estrato
        
        DireccionMgl direccion = new DireccionMgl();
        try {
            direccionMglFacade.findById(direccion);
        } catch (ApplicationException e) {
            FacesUtil.mostrarMensajeError("Error en cargarArchivo. ", e, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en cargarArchivo. ", e, LOGGER);
        }
        
        //TODO Invocar servicio de RR para cambiar el estrato de cada una de las direcciones que 
        // se debe procesar
    }
    
    private void procesarArchivo(){
        //TODO procesamiento archivo, no se tiene la estructura
    }
    
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Date getFechaCargue() {
        return fechaCargue;
    }

    public void setFechaCargue(Date fechaCargue) {
        this.fechaCargue = fechaCargue;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }
}