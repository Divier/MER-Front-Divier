/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.mbeans.cm;

import co.com.claro.mgl.error.ApplicationException;
import co.com.claro.mgl.facade.cm.CmtComponenteDireccionesMglFacadeLocal;
import co.com.claro.mgl.jpa.entities.ConfigurationAddressComponent;
import co.com.claro.mgl.seguridad.MetadataProyecto;
import co.com.telmex.catastro.util.FacesUtil;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author camargomf
 */
@Log4j2
@ManagedBean(eager = true, name = "ApplicationBean")
@ApplicationScoped
public class ApplicationBean {
    /* Injection EJB */
    @EJB
    private CmtComponenteDireccionesMglFacadeLocal cmtComponenteDireccionesMglFacadeLocal;
    private ConfigurationAddressComponent configurationAddressComponent = null;
    /* Variables */
    @Getter
    private String hostName = "";
    @Getter
    private String serverWlName = "";
    @Getter
    private MetadataProyecto metadataProyecto;


    /**
     * Constructor de la clase
     */
    public ApplicationBean() {
        try {
            hostName = InetAddress.getLocalHost().getHostName();
            serverWlName = System.getProperty("weblogic.Name");
        } catch (UnknownHostException ex) {
            FacesUtil.mostrarMensajeError("No se pudo obtener el servidor contenedor" + ex.getMessage(), ex, LOGGER);
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error no se pudo obtener el servidor contenedor" + e.getMessage(), e, LOGGER);
        }
    }

    @PostConstruct
    public void init() {
        LOGGER.error("init()");
        this.metadataProyecto = MetadataProyecto.getInstance();
    }

    /**
     * Obtiene la configuración para el componente de direcciones, carga las
     * listas correspondientes a los tipos de dirección, sección Calle -
     * Carrera, sección Barrio - Manzana, Sección Intraducible.
     *
     * @return configurationAddressComponent
     */
    public ConfigurationAddressComponent getConfigurationAddressComponent() {
        if (configurationAddressComponent == null) {
            try {
                configurationAddressComponent = cmtComponenteDireccionesMglFacadeLocal
                        .getConfiguracionComponente();
            } catch (ApplicationException ex) {
                FacesUtil.mostrarMensajeError("Error al momento de obtener la configuración de la dirección. EX000: " + ex.getMessage(), ex, LOGGER);
            } catch (Exception e) {
                FacesUtil.mostrarMensajeError("No se pudo obtener el servidor contenedor en ApplicationBean: getConfigurationAddressComponent()" + e.getMessage(), e, LOGGER);
            }
        }
        return configurationAddressComponent;
    }

}
