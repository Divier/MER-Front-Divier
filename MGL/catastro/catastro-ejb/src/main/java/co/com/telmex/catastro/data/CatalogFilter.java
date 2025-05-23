package co.com.telmex.catastro.data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Clase CatalogAdc
 * Representa La información De los filtros del catálogo (Menú).
 * implementa Serialización
 *
 * @author 	Jose Luis Caicedo G.
 * @version	1.0
 */
public class CatalogFilter implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal cfiId;
    private String cfiLabel;
    private String cfiColumn;
    private CatalogAdc catalogAdc;

    /**
     * 
     */
    public CatalogFilter() {
    }

    /**
     * 
     * @param cfiId
     */
    public CatalogFilter(BigDecimal cfiId) {
        this.cfiId = cfiId;
    }

    /**
     * 
     * @return
     */
    public BigDecimal getCfiId() {
        return cfiId;
    }

    /**
     * 
     * @param cfiId
     */
    public void setCfiId(BigDecimal cfiId) {
        this.cfiId = cfiId;
    }

    /**
     * 
     * @return
     */
    public String getCfiLabel() {
        return cfiLabel;
    }

    /**
     * 
     * @param cfiLabel
     */
    public void setCfiLabel(String cfiLabel) {
        this.cfiLabel = cfiLabel;
    }

    /**
     * 
     * @return
     */
    public String getCfiColumn() {
        return cfiColumn;
    }

    /**
     * 
     * @param cfiColumn
     */
    public void setCfiColumn(String cfiColumn) {
        this.cfiColumn = cfiColumn;
    }

    /**
     * 
     * @return
     */
    public CatalogAdc getCatalogAdc() {
        return catalogAdc;
    }

    /**
     * 
     * @param catalogAdc
     */
    public void setCatalogAdc(CatalogAdc catalogAdc) {
        this.catalogAdc = catalogAdc;
    }

    /**
     * 
     * @return
     */
    public String auditoria() {
        return "CatalogFilter:" + "cfiId=" + cfiId + ", cfiLabel=" + cfiLabel
                + ", cfiColumn=" + cfiColumn + ", catalogAdc=" + catalogAdc + '.';
    }

    @Override
    public String toString() {
        return "CatalogFilter:" + "cfiId=" + cfiId + ", cfiLabel=" + cfiLabel
                + ", cfiColumn=" + cfiColumn + ", catalogAdc=" + catalogAdc + '.';
    }
}
