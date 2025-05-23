package co.com.telmex.catastro.mbeans.seguridad;

import co.com.telmex.catastro.data.Rol;
import co.com.telmex.catastro.delegate.SeguridadDelegate;
import co.com.telmex.catastro.mbeans.comun.BaseMBean;
import co.com.telmex.catastro.util.ConstantWEB;
import co.com.telmex.catastro.util.FacesUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Clase SelectionRol Extiende de BaseMBean
 *
 * @author Jose Luis Caicedo
 * @version	1.0
 */
@ManagedBean
public class SelectionRol extends BaseMBean {

    private static final Logger LOGGER = LogManager.getLogger(SelectionRol.class);

    private static String MESSAGE_NOT_ROL = "El usuario no tiene asignado roles";
    private static String MESSAGE_NOT_SELECTED = "Debe seleccionar un rol para ingresar";
    private static String MESSAGE_NOT_ROL_WITH_MENU = "Este rol no tiene un menú asociado";
    private static String VIEW_MAIN = "/view/template/main.jsp";
    private List<SelectItem> listSelection = null;
    private List<Rol> listRol = null;
    private String selectedRol = "";

    /**
     *
     */
    public SelectionRol() {
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
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error en SelectionRol" + e.getMessage() , e, LOGGER);
        }
    }

    /**
     *
     */
    public void onAction() {
        if (selectedRol == null || "".equals(selectedRol)) {
            message = MESSAGE_NOT_SELECTED;
        } else {
            BigDecimal id = new BigDecimal(selectedRol);
            boolean rolConMenu = false;
            //Se valida que el rol tenga un menu asociado
            try {
                rolConMenu = SeguridadDelegate.validateRolMenu(id.toString());
            } catch (Exception ex) {
                FacesUtil.mostrarMensajeError("Error en rolConMenu" + ex.getMessage() , ex, LOGGER);
            }
            if (!rolConMenu) {
                message = MESSAGE_NOT_ROL_WITH_MENU;
            } else {
                for (Rol rol : listRol) {
                    if (id.longValue() == rol.getRolId().longValue()) {
                        addDataOnSession(rol);
                        deleteRolMenuOnSession();
                        FacesContext context = FacesContext.getCurrentInstance();
                        try {
                            context.getExternalContext().dispatch(VIEW_MAIN);
                        } catch (IOException ex) {
                            FacesUtil.mostrarMensajeError("Error en Rol" + ex.getMessage() , ex, LOGGER);
                        } catch (Exception ex) {
                            FacesUtil.mostrarMensajeError("Error en Rol" + ex.getMessage() , ex, LOGGER);
                        } finally {
                            context.responseComplete();
                        }

                        break;
                    }
                }
            }
        }
    }

    /**
     *
     * @param rol
     */
    private void addDataOnSession(Rol rol) {
        HttpServletRequest httpRequest = FacesUtil.getServletRequest();
        HttpSession httpSession = httpRequest.getSession();
        httpSession.setAttribute(ConstantWEB.ROL_SESSION, rol);
    }

    /**
     *
     */
    private void deleteRolMenuOnSession() {
        HttpServletRequest httpRequest = FacesUtil.getServletRequest();
        HttpSession httpSession = httpRequest.getSession();
        httpSession.setAttribute(ConstantWEB.MENU_SESSION, null);
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
    public List<Rol> getListRol() {
        return listRol;
    }

    /**
     *
     * @param listRol
     */
    public void setListRol(List<Rol> listRol) {
        this.listRol = listRol;
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
}
