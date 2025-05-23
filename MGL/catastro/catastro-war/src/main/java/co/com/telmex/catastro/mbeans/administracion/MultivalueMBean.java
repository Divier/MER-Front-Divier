package co.com.telmex.catastro.mbeans.administracion;

import co.com.telmex.catastro.data.GrupoMultivalor;
import co.com.telmex.catastro.data.Multivalor;
import co.com.telmex.catastro.delegate.AdministracionDelegate;
import co.com.telmex.catastro.mbeans.comun.BaseMBean;
import co.com.telmex.catastro.util.FacesUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIParameter;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

/**
 * Clase MultivalueMBean
 * Extiende de BaseMBean
 *
 * @author 	Nataly Orozco Torres
 * @version	1.0
 */
@ManagedBean(name = "multivalueMBean")
@SessionScoped
public class MultivalueMBean extends BaseMBean {
    
    private static final Logger LOGGER = LogManager.getLogger(MultivalueMBean.class);

    /**
     * 
     */
    public List<GrupoMultivalor> groups;
    /**
     * 
     */
    public List<Multivalor> multivalues;
    /**
     * 
     */
    public List<SelectItem> listGroup;
    /**
     * 
     */
    public String groupselected = "";
    /**
     * 
     */
    public String desgmv = "";
    /**
     * 
     */
    public String value = "";
    /**
     * 
     */
    public String description = "";
    /**
     * 
     */
    public String idmulti = "";
    /**
     * 
     */
    public Multivalor multivalor;
    /**
     * 
     */
    public GrupoMultivalor groupM;
    /**
     * 
     */
    public boolean showQuery = false;
    private int scrollerPage = 0;

    /**
     * 
     */
    public MultivalueMBean() {

        groupM = new GrupoMultivalor();
        groups = new ArrayList<GrupoMultivalor>();
        multivalues = new ArrayList<Multivalor>();
        listGroup = new ArrayList<SelectItem>();

        try {
            groups = AdministracionDelegate.queryGroupMultivalue();
            if (groups == null) {
                groups = new ArrayList<GrupoMultivalor>();
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error con el servicio - MultivalueMBean" + e.getMessage() , e, LOGGER);
        }

        for (GrupoMultivalor g : groups) {
            SelectItem item = new SelectItem();
            item.setValue(g.getGmuId());
            item.setLabel(g.getGmuNombre());
            listGroup.add(item);
        }

    }

    /**
     * 
     * @return
     */
    public String query() {
        try {
            this.scrollerPage = 0;
            BigDecimal id = new BigDecimal(this.groupselected);
            Long row = new Long(scrollerPage);
            multivalues = AdministracionDelegate.queryMultivalueByGroup(id, row);
            if (multivalues != null && multivalues.size() > 0) {
                message = "Cantidad de registros: " + multivalues.size();
                showQuery = true;
            } else {
                message = "resultado es nulo";
            }
        } catch (Exception e) {
            FacesUtil.mostrarMensajeError("Error con el servicio - query" + e.getMessage() , e, LOGGER);
        }
        return "";
    }

    /**
     * 
     * @param ev
     */
    public void delete(ActionEvent ev) {
        try {
            boolean res = false;
            BigDecimal idgroup = new BigDecimal(this.groupselected);
            BigDecimal idmultiv = new BigDecimal(this.idmulti);
            res = AdministracionDelegate.deleteMultivalueAtGroup(idmultiv, idgroup);
            if (res) {
                message = "Multivalor con id:" + idmultiv + ", eliminado de forma exitosa.";
                this.description = "";
                this.value = "";
                this.groupselected = "";
            } else {
                message = "error al eliminar Multivalor";
            }
        } catch (Exception ex) {
            FacesUtil.mostrarMensajeError("Error con el servicio - delete" + ex.getMessage() , ex, LOGGER);
        }
    }

    /**
     * 
     * @return
     */
    public String update() {
        String result = "";
        try {

            boolean res = false;
            BigDecimal idgroup = new BigDecimal(this.groupselected);
            BigDecimal idmultiv = new BigDecimal(this.idmulti);
            res = AdministracionDelegate.updateMultivalueAtGroup(value, description,
                    this.user.getUsuLogin(), idmultiv, idgroup);
            result = String.valueOf(res);
            if (res) {
                message = "Multivalor con id:" + idmultiv + ", actualizado de forma exitosa.";
            } else {
                message = "error al actualizar Multivalor";
            }
        } catch (Exception ex) {
            FacesUtil.mostrarMensajeError("Error con el servicio - update" + ex.getMessage() , ex, LOGGER);
        }
        return result;
    }

    /**
     * 
     * @return
     */
    public String create() {
        String result = "";
        try {
            BigDecimal id = new BigDecimal(this.groupselected);
            BigDecimal idnewM = null;
            idnewM = AdministracionDelegate.insertMultivalueAtGroup(id, value, description,
                    this.user.getUsuLogin(), this.user.getUsuLogin());
            result = idnewM.toString();
            message = "Multivalor creado con Id: " + idnewM;
        } catch (Exception ex) {
            FacesUtil.mostrarMensajeError("Error con el servicio - create" + ex.getMessage() , ex, LOGGER);
        }
        return result;
    }

    /**
     * 
     * @param ev
     */
    public void onSeleccionar(ActionEvent ev) {
        this.idmulti = ((BigDecimal) (((UIParameter) ev.getComponent().findComponent("multivid")).getValue())).toString();
        this.value = (String) (((UIParameter) ev.getComponent().findComponent("multivalue")).getValue());
        this.description = (String) (((UIParameter) ev.getComponent().findComponent("multides")).getValue());
    }

    /**
     * 
     * @return
     */
    public String onIrAccion() {
        return "multivalueadmin";
    }

    /**
     * 
     * @return
     */
    public String onDelete() {
        return "multivalue";
    }

    /**
     * 
     * @return
     */
    public List<SelectItem> getListGroup() {
        return listGroup;
    }

    /**
     * 
     * @param listGroup
     */
    public void setListGroup(List<SelectItem> listGroup) {
        this.listGroup = listGroup;
    }

    /**
     * 
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * 
     * @return
     */
    public String getGroupselected() {
        return groupselected;
    }

    /**
     * 
     * @param groupselected
     */
    public void setGroupselected(String groupselected) {
        this.groupselected = groupselected;
    }

    /**
     * 
     * @return
     */
    public List<GrupoMultivalor> getGroups() {
        return groups;
    }

    /**
     * 
     * @return
     */
    public String getValue() {
        return value;
    }

    /**
     * 
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 
     * @param groups
     */
    public void setGroups(List<GrupoMultivalor> groups) {
        this.groups = groups;
    }

    /**
     * 
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 
     * @return
     */
    public boolean isShowQuery() {
        return showQuery;
    }

    /**
     * 
     * @param showQuery
     */
    public void setShowQuery(boolean showQuery) {
        this.showQuery = showQuery;
    }

    /**
     * 
     * @return
     */
    public Multivalor getMultivalor() {
        return multivalor;
    }

    /**
     * 
     * @param multivalor
     */
    public void setMultivalor(Multivalor multivalor) {
        this.multivalor = multivalor;
    }

    /**
     * 
     * @return
     */
    public GrupoMultivalor getGroupM() {
        return groupM;
    }

    /**
     * 
     * @param groupM
     */
    public void setGroupM(GrupoMultivalor groupM) {
        this.groupM = groupM;
    }

    /**
     * 
     * @return
     */
    public List<Multivalor> getMultivalues() {
        return multivalues;
    }

    /**
     * 
     * @param multivalues
     */
    public void setMultivalues(ArrayList<Multivalor> multivalues) {
        this.multivalues = multivalues;
    }

    /**
     * 
     * @return
     */
    public String getDesgmv() {
        return desgmv;
    }

    /**
     * 
     * @param desgmv
     */
    public void setDesgmv(String desgmv) {
        this.desgmv = desgmv;
    }

    /**
     * 
     * @return
     */
    public String getIdmulti() {
        return idmulti;
    }

    /**
     * 
     * @param idmulti
     */
    public void setIdmulti(String idmulti) {
        this.idmulti = idmulti;
    }

    /**
     * 
     * @return
     */
    public int getScrollerPage() {
        return scrollerPage;
    }

    /**
     * 
     * @param scrollerPage
     */
    public void setScrollerPage(int scrollerPage) {
        this.scrollerPage = scrollerPage;
    }
}