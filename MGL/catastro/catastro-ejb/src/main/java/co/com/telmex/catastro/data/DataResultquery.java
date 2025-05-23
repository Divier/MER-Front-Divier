package co.com.telmex.catastro.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase DataResultQuery
 * Representa El resultado de la información del query.
 *
 * @author 	Ana María Malambo.
 * @version	1.0
 */
public class DataResultquery {

    private List<DataRow> listDataRowq = null;
    private List<String> listNameColumnq = null;

    /**
     * 
     */
    public DataResultquery() {
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
}
