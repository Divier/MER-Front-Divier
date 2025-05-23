package co.com.telmex.catastro.data;

/**
 * Clase CatalogAdc
 * Representa La relación de tablas Del catálogo (Menú).
 * implementa Serialización
 *
 * @author 	Ana María Malambo
 * @version	1.0
 */
public class CatalogTableRelacion {
    
    String cdt_table;

    /**
     * 
     * @return
     */
    public String getCdt_table() {
        return cdt_table;
    }

    /**
     * 
     * @param cdt_table
     */
    public void setCdt_table(String cdt_table) {
        this.cdt_table = cdt_table;
    }

    /**
     * 
     * @return
     */
    public String auditoria() {
        return "CatalogTableRelacion:" + "cdt_table=" + cdt_table + '.';
    }
    @Override
    public String toString() {
     return "CatalogTableRelacion:" + "cdt_table=" + cdt_table + '.';
    }
}
