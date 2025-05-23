package co.com.telmex.catastro.mbeans.comun;

import co.com.telmex.catastro.data.CatalogAdc;
import co.com.telmex.catastro.delegate.ComunDelegate;
import co.com.telmex.catastro.util.FacesUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIParameter;
import javax.faces.event.ActionEvent;

/**
 * Clase CatalogMBean
 * Extiende de BaseMBean
 *
 * @author 	Jose Luis Caicedo
 * @version	1.0
 */
@ManagedBean
public class CatalogMBean extends BaseMBean {

    
    private static final Logger LOGGER = LogManager.getLogger(CatalogMBean.class);
    /**
     * 
     */
    public List<CatalogAdc> catalogs = null;

    /**
     * 
     */
    public CatalogMBean() {
        String username = "";
        executeQuery(username);
    }

    /**
     * 
     * @param ev
     */
    public void onSeleccionarFila(ActionEvent ev) {
        int indiceRegistro = (Integer) (((UIParameter) ev.getComponent().findComponent("indiceRegistro")).getValue());
        CatalogAdc c = catalogs.get(indiceRegistro);
    }

    /**
     * 
     * @return
     */
    public List<CatalogAdc> getCatalogs() {
        return catalogs;
    }

    /**
     * 
     * @param catalogs
     */
    public void setCatalogs(List<CatalogAdc> catalogs) {
        this.catalogs = catalogs;
    }

    /**
     * 
     * @param username 
     */
    private void executeQuery(String username) {
        try {
            catalogs = ComunDelegate.queryListCatalog(username);

            if (catalogs == null) {
                catalogs = new ArrayList<CatalogAdc>();
                message = "No se encuentran catqalogos disponibles";
            }
        } catch (Exception ex) {
            message = "Error con el servicio - executeQuery";
            FacesUtil.mostrarMensajeError(message + ex.getMessage() , ex, LOGGER);
        }
    }
}
