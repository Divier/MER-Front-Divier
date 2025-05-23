package co.com.telmex.catastro.data;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * Clase Restriccion
 * Implementa Serialización.
 *
 * @author 	
 * @version	1.0
 */
public class Restriccion implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigInteger atrId = null;
    private BigInteger rolId = null;
    private String tableName = null;

    /**
     * Obtiene el Id
     * @return Entero con el Id.
     */
    public BigInteger getAtrId() {
        return atrId;
    }

    /**
     * Establece el Id.
     * @param atrId Entero con el Id.
     */
    public void setAtrId(BigInteger atrId) {
        this.atrId = atrId;
    }

    /**
     * Obtiene el Id del rol.
     * @return Entero con el Id del rol.
     */
    public BigInteger getRolId() {
        return rolId;
    }

    /**
     * Establece el Id del rol.
     * @param rolId Entero con el Id del rol.
     */
    public void setRolId(BigInteger rolId) {
        this.rolId = rolId;
    }

    /**
     * Obtiene el nombre de la tabla.
     * @return Cadena con el nombre de la tabla.
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * Establece el nombre de la tabla.
     * @param tableName Cadena con el nombre de la tabla.
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * Cadena con la configuración para auditoría de carga.
     * @return Cadena con los campos que contiene la clase.
     */
    public String auditoria() {
        return "Restriccion:" + "atrId=" + atrId + ", rolId=" + rolId
                + ", tableName=" + tableName + '.';
    }

    /**
     * Sobre escritura del método toString().
     * @return Cadena con información de auditoría.
     */
    @Override
    public String toString() {
        return "Restriccion:" + "atrId=" + atrId + ", rolId=" + rolId
                + ", tableName=" + tableName + '.';
    }
}
