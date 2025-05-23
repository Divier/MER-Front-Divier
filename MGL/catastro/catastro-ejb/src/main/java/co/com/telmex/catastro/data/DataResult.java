package co.com.telmex.catastro.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase DataResult
 * Representa el resultado de la información.
 *
 * @author 	Ana María Malambo
 * @version	1.0
 */
public class DataResult {

    private List<DataRow> listDataRow = null;
    private List<String> listNameColumn = null;

    /**
     * 
     */
    public DataResult() {
        listDataRow = new ArrayList<DataRow>();
        listNameColumn = new ArrayList<String>();
    }

    /**
     * 
     * @param datarow
     */
    public void addDataRow(DataRow datarow) {
        listDataRow.add(datarow);
    }

    /**
     * 
     * @param index
     * @return
     */
    public DataRow getDataRow(int index) {
        return listDataRow.get(index);
    }

    /**
     * 
     * @param name
     */
    public void addNameColumn(String name) {
        listNameColumn.add(name);
    }

    /**
     * 
     * @param index
     * @return
     */
    public String getNameColumn(int index) {
        return listNameColumn.get(index);
    }

    /**
     * 
     * @return
     */
    public List<DataRow> getListDataRow() {
        return listDataRow;
    }

    /**
     * 
     * @param listDataRow
     */
    public void setListDataRow(List<DataRow> listDataRow) {
        this.listDataRow = listDataRow;
    }

    /**
     * 
     * @return
     */
    public List<String> getListNameColumn() {
        return listNameColumn;
    }

    /**
     * 
     * @param listNameColumn
     */
    public void setListNameColumn(List<String> listNameColumn) {
        this.listNameColumn = listNameColumn;
    }

    /**
     * 
     * @return
     */
    public String auditoria() {
        return "DataResult:" + "listDataRow=" + listDataRow
                + ", listNameColumn=" + listNameColumn + '.';
    }

    @Override
    public String toString() {
        return "DataResult:" + "listDataRow=" + listDataRow
                + ", listNameColumn=" + listNameColumn + '.';
    }
}
