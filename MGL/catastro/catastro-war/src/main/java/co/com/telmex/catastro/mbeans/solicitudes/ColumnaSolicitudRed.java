package co.com.telmex.catastro.mbeans.solicitudes;

/**
 * Clase ConsultaEspecificaMBean
 * Extiende de BaseMBean
 *
 * @author 	Nataly Orozco Torres
 * @version	1.0
 */
public class ColumnaSolicitudRed {

    /**
     * 
     */
    public String columnName;
    /**
     * 
     */
    public int value;

    /**
     * 
     */
    public ColumnaSolicitudRed() {
    }

    /**
     * 
     * @return
     */
    public String getColumnName() {
        return columnName;
    }

    /**
     * 
     * @param columnName
     */
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    /**
     * 
     * @return
     */
    public int getValue() {
        return value;
    }

    /**
     * 
     * @param value
     */
    public void setValue(int value) {
        this.value = value;
    }
}
