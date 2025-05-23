package co.com.telmex.catastro.mbeans.administracion;

import co.com.telmex.catastro.data.Multivalor;
import co.com.telmex.catastro.delegate.AdministracionDelegate;
import co.com.telmex.catastro.mbeans.comun.BaseMBean;
import co.com.telmex.catastro.util.FacesUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIParameter;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;

/**
 * Clase TestMBean1
 * Extiende de BaseMbean
 *
 * @author 	Nataly Orozco Torres.
 * @version	1.0
 */
@ManagedBean
public class TestMBean1 extends BaseMBean {
    
    private static final Logger LOGGER = LogManager.getLogger(TestMBean1.class);

    int count = 0;
    private String name = null;
    private String message = null;
    List<Multivalor> listmultivalue = null;
    long row = 2;
    private int scrollerPage;
    private String selectedName = null;

    /**
     * 
     */
    public TestMBean1() {
        super();
    }

    /**
     * 
     * @return
     */
    public String onQuery() {
        try {
            listmultivalue = AdministracionDelegate.queryMultivalueTest(new Long(row));
            if (listmultivalue != null && listmultivalue.size() > 0) {
                message = "Cantidad de registros: " + listmultivalue.size();
            } else {
                message = "resultado es nulo";
            }
        } catch (Exception ex) {
            message = "Error con el servicio - onQuery";
            FacesUtil.mostrarMensajeError(message + ex.getMessage() , ex, LOGGER);
        }
        row = +5;
        return "";
    }

    /**
     * 
     * @param ev
     */
    public void onSelectionAction(ActionEvent ev) {
        int index = (Integer) (((UIParameter) ev.getComponent().findComponent("indiceRegistro")).getValue());
    }

    /**
     * 
     * @return
     */
    public String onAction() {
        return "test2";
    }

    /**
     * 
     * @return
     */
    public String onMessage() {
        message = "Ok";
        return "";
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
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     */
    public int getCount() {
        return count;
    }

    /**
     * 
     * @param count
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * 
     * @return
     */
    public List<Multivalor> getListmultivalue() {
        return listmultivalue;
    }

    /**
     * 
     * @param listmultivalue
     */
    public void setListmultivalue(List<Multivalor> listmultivalue) {
        this.listmultivalue = listmultivalue;
    }

    /**
     * 
     * @return
     */
    public long getRow() {
        return row;
    }

    /**
     * 
     * @param row
     */
    public void setRow(long row) {
        this.row = row;
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

    /**
     * 
     * @return
     */
    public String getSelectedName() {
        return selectedName;
    }

    /**
     * 
     * @param selectedName
     */
    public void setSelectedName(String selectedName) {
        this.selectedName = selectedName;
    }

    /**
     * 
     * @param event
     * @throws AbortProcessingException
     */
    public void processAction(ActionEvent event) throws AbortProcessingException {
    }
}
