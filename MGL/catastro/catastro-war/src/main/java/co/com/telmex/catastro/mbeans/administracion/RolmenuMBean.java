package co.com.telmex.catastro.mbeans.administracion;

import co.com.telmex.catastro.data.Menu;
import co.com.telmex.catastro.data.Rol;
import co.com.telmex.catastro.delegate.AdministracionDelegate;
import co.com.telmex.catastro.delegate.SeguridadDelegate;
import co.com.telmex.catastro.mbeans.comun.BaseMBean;
import co.com.telmex.catastro.util.FacesUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

/**
 * Clase RolMenuMBean
 * Extiende de BaseMbean
 *
 * @author 	Nataly Orozco Torres.
 * @version	1.0
 */
@ManagedBean(name = "rolmenuMBean")
@SessionScoped
public class RolmenuMBean extends BaseMBean {
    
    private static final Logger LOGGER = LogManager.getLogger(RolmenuMBean.class);

    private static String MESSAGE_NOT_ROL = "El usuario no tiene asignado roles";
    private List<Menu> funcionalidades;
    private List<Menu> grupos;
    private List<RolmenuVisual> menus;
    boolean showtable;
    private List<SelectItem> listSelection = null;
    private String selectedRol = "";
    private List<Rol> listRol = null;

    /**
     * 
     */
    public RolmenuMBean() {
        funcionalidades = new ArrayList<Menu>();
        grupos = new ArrayList<Menu>();
        menus = new ArrayList<RolmenuVisual>();
        showtable = false;
        querymenus();
        queryroles();

    }

    /**
     * 
     */
    private void querymenus() {
        try {
            funcionalidades = AdministracionDelegate.queryMenu();
            grupos = AdministracionDelegate.queryGruposMenu();
            if ((funcionalidades != null && funcionalidades.size() > 0)
                    && (grupos != null && grupos.size() > 0)) {
                message = "Cantidad de funcionalidades: " + funcionalidades.size();
            } else {
                message = "resultado es nulo";
            }
        } catch (Exception ex) {
            FacesUtil.mostrarMensajeError("Error con el servicio - querymenus" + ex.getMessage() , ex, LOGGER);
        }
        for (int i = 0; i < funcionalidades.size(); i++) {
            RolmenuVisual rmv = new RolmenuVisual();
            Menu funcionalidad = funcionalidades.get(i);
            rmv.setIdFuncionalidad(funcionalidad.getMenId());
            rmv.setNameFuncionalidad(funcionalidad.getMenNombre());
            rmv.setIdGmenu(funcionalidad.getMenuParte().getMenId());
            for (int x = 0; x < grupos.size(); x++) {
                if (grupos.get(x).getMenId().equals(funcionalidad.getMenuParte().getMenId())) {
                    rmv.setNameGMenu(grupos.get(x).getMenNombre());
                }
            }
            rmv.setStatus(false);
            menus.add(rmv);
        }
    }

    /**
     * 
     */
    private void queryroles() {
        listSelection = new ArrayList<SelectItem>();
        try {
            listRol = SeguridadDelegate.queryListRol(this.user.getUsuId());
            if (listRol == null) {
                message = MESSAGE_NOT_ROL;
            } else {
                for (Rol r : listRol) {
                    SelectItem item = new SelectItem();
                    item.setValue(r.getRolId().toString());
                    item.setLabel(r.getRolNombre());
                    listSelection.add(item);
                }
            }
        } catch (Exception ex) {
            FacesUtil.mostrarMensajeError("Error en queryroles" + ex.getMessage() , ex, LOGGER);
        }
    }

    /**
     * 
     * @param ev
     */
    public void onClick(ActionEvent ev) {
        showtable = true;
    }

    /**
     * 
     * @return
     */
    public List<SelectItem> getListSelection() {
        return listSelection;
    }

    /**
     * 
     * @param listSelection
     */
    public void setListSelection(List<SelectItem> listSelection) {
        this.listSelection = listSelection;
    }

    /**
     * 
     * @return
     */
    public List<RolmenuVisual> getMenus() {
        return menus;
    }

    /**
     * 
     * @param menus
     */
    public void setMenus(List<RolmenuVisual> menus) {
        this.menus = menus;
    }

    /**
     * 
     * @return
     */
    public String getSelectedRol() {
        return selectedRol;
    }

    /**
     * 
     * @param selectedRol
     */
    public void setSelectedRol(String selectedRol) {
        this.selectedRol = selectedRol;
    }

    /**
     * 
     * @return
     */
    public boolean isShowtable() {
        return showtable;
    }

    /**
     * 
     * @param showtable
     */
    public void setShowtable(boolean showtable) {
        this.showtable = showtable;
    }
}
