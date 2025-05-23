package co.com.telmex.catastro.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase DataRelaciones
 * Representa las relaciones entre datos.
 *
 * @author 	Ana María Malambo
 * @version	1.0
 */
public class DataRelaciones {

    private List<DataRow> listDataRowq = null;
    private List<String> listNameColumnq = null;

    /**
     * 
     */
    public DataRelaciones() {
        listDataRowq = new ArrayList<DataRow>();
        listNameColumnq = new ArrayList<String>();
    }

    /**
     * 
     * @param datarow
     */
    public void addDataRow(DataRow datarow) {
        listDataRowq.add(datarow);
    }

    /**
     * 
     * @param index
     * @return
     */
    public DataRow getDataRow(int index) {
        return listDataRowq.get(index);
    }

    /**
     * 
     * @param name
     */
    public void addNameColumnq(String name) {
        listNameColumnq.add(name);
    }

    /**
     * 
     * @param index
     * @return
     */
    public String getNameColumnq(int index) {
        return listNameColumnq.get(index);
    }

    /**
     * 
     * @return
     */
    public List<DataRow> getListDataRowq() {
        return listDataRowq;
    }

    /**
     * 
     * @param listDataRow
     */
    public void setListDataRowq(List<DataRow> listDataRow) {
        this.listDataRowq = listDataRow;
    }

    /**
     * 
     * @return
     */
    public List<String> getListNameColumnq() {
        return listNameColumnq;
    }

    /**
     * 
     * @param listNameColumnq
     */
    public void setListNameColumnq(List<String> listNameColumnq) {
        this.listNameColumnq = listNameColumnq;
    }

    /**
     * 
     * @return
     */
    public String auditoria() {
        return "DataRelaciones:" + "listDataRowq=" + listDataRowq
                + ", listNameColumnq=" + listNameColumnq + '.';
    }

    @Override
    public String toString() {
        return "DataRelaciones:" + "listDataRowq=" + listDataRowq
                + ", listNameColumnq=" + listNameColumnq + '.';
    }
}
