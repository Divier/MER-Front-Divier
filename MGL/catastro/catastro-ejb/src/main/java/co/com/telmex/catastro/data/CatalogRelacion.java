package co.com.telmex.catastro.data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Clase CatalogAdc
 * Representa La relación de información al interior del catálogo (Menú).
 * implementa Serialización
 *
 * @author 	Jose Luis Caicedo G.
 * @version	1.0
 */
public class CatalogRelacion implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal ctlId;
    private String ctlNamerelacion;
    private String ctlSqlrelacion;
    private String ctlModerelacion;
    private String ctlBcreaterelacion;
    private String ctlProgramamrelacion;

    /**
     * 
     */
    public CatalogRelacion() {
    }

    /**
     * 
     * @param ctlId
     */
    public CatalogRelacion(BigDecimal ctlId) {
        this.ctlId = ctlId;
    }

    /**
     * 
     * @return
     */
    public BigDecimal getCtlId() {
        return ctlId;
    }

    /**
     * 
     * @param ctlId
     */
    public void setCtlIdrelacion(BigDecimal ctlId) {
        this.ctlId = ctlId;
    }

    /**
     * 
     * @return
     */
    public String getCtlNamerelacion() {
        return ctlNamerelacion;
    }

    /**
     * 
     * @param ctlNamerelacion
     */
    public void setCtlNamerelacion(String ctlNamerelacion) {
        this.ctlNamerelacion = ctlNamerelacion;
    }

    /**
     * 
     * @return
     */
    public String getCtlSqlrelacion() {
        return ctlSqlrelacion;
    }

    /**
     * 
     * @param ctlSqlrelacion
     */
    public void setCtlSqlrelacion(String ctlSqlrelacion) {
        this.ctlSqlrelacion = ctlSqlrelacion;
    }

    /**
     * 
     * @return
     */
    public String getCtlModerelacion() {
        return ctlModerelacion;
    }

    /**
     * 
     * @param ctlModerelacion
     */
    public void setCtlModerelacion(String ctlModerelacion) {
        this.ctlModerelacion = ctlModerelacion;
    }

    /**
     * 
     * @return
     */
    public String getCtlBcreaterelacion() {
        return ctlBcreaterelacion;
    }

    /**
     * 
     * @param ctlBcreaterelacion
     */
    public void setCtlBcreaterelacion(String ctlBcreaterelacion) {
        this.ctlBcreaterelacion = ctlBcreaterelacion;
    }

    /**
     * 
     * @return
     */
    public String getCtlProgramamrelacion() {
        return ctlProgramamrelacion;
    }

    /**
     * 
     * @param ctlProgramamrelacion
     */
    public void setCtlProgramamrelacion(String ctlProgramamrelacion) {
        this.ctlProgramamrelacion = ctlProgramamrelacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ctlId != null ? ctlId.hashCode() : 0);
        return hash;
    }

    /**
     * 
     * @return
     */
    public String auditoria() {
        return "CatalogRelacion:" + "ctlId=" + ctlId
                + ", ctlNamerelacion=" + ctlNamerelacion
                + ", ctlSqlrelacion=" + ctlSqlrelacion
                + ", ctlModerelacion=" + ctlModerelacion
                + ", ctlBcreaterelacion=" + ctlBcreaterelacion
                + ", ctlProgramamrelacion=" + ctlProgramamrelacion + '.';
    }

    @Override
    public String toString() {
        return "CatalogRelacion:" + "ctlId=" + ctlId
                + ", ctlNamerelacion=" + ctlNamerelacion
                + ", ctlSqlrelacion=" + ctlSqlrelacion
                + ", ctlModerelacion=" + ctlModerelacion
                + ", ctlBcreaterelacion=" + ctlBcreaterelacion
                + ", ctlProgramamrelacion=" + ctlProgramamrelacion + '.';
    }
}
