package co.com.telmex.catastro.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase DataRow
 * Representa La información de la fila.
 *
 * @author 	Ana María Malambo.
 * @version	1.0
 */
public class DataRow {

    List<String> columns = null;

    /**
     * 
     */
    public DataRow() {
        columns = new ArrayList<String>();
    }

    /**
     * 
     * @param index
     * @return
     */
    public String getColumn(int index) {
        return columns.get(index);
    }

    /**
     * 
     * @param column
     */
    public void setColumn(String column) {
        columns.add(column);
    }

    /**
     * 
     * @return
     */
    public List<String> getColumns() {
        return columns;
    }

    /**
     * 
     * @param columns
     */
    public void setColumns(List<String> columns) {
        this.columns = columns;
    }
}
