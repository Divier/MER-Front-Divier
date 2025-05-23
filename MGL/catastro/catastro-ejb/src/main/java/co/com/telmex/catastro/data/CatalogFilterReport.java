package co.com.telmex.catastro.data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Clase CatalogAdc
 * Representa La información los filtros del reporte en el catálogo (Menú).
 * implementa Serialización
 *
 * @author 	Jose Luis Caicedo G.
 * @version	1.0
 */
public class CatalogFilterReport implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal cfrId;
    private String cfrLabel;
    private String cfrColumn;
    private String cfrType;
    private String creSqldata;
    private String creSqldata1;

    /**
     * 
     * @return
     */
    public String getCreSqldata1() {
        return creSqldata1;
    }

    /**
     * 
     * @param creSqldata1
     */
    public void setCreSqldata1(String creSqldata1) {
        this.creSqldata1 = creSqldata1;
    }
    private String creExportCvs;
    private String creExportPdf;
    private String creExportExcel;

    /**
     * 
     * @return
     */
    public String getCreExportCvs() {
        return creExportCvs;
    }

    /**
     * 
     * @param creExportCvs
     */
    public void setCreExportCvs(String creExportCvs) {
        this.creExportCvs = creExportCvs;
    }

    /**
     * 
     * @return
     */
    public String getCreExportExcel() {
        return creExportExcel;
    }

    /**
     * 
     * @param creExportExcel
     */
    public void setCreExportExcel(String creExportExcel) {
        this.creExportExcel = creExportExcel;
    }

    /**
     * 
     * @return
     */
    public String getCreExportPdf() {
        return creExportPdf;
    }

    /**
     * 
     * @param creExportPdf
     */
    public void setCreExportPdf(String creExportPdf) {
        this.creExportPdf = creExportPdf;
    }

    /**
     * 
     * @return
     */
    public String getCreSqldata() {
        return creSqldata;
    }

    /**
     * 
     * @param creSqldata
     */
    public void setCreSqldata(String creSqldata) {
        this.creSqldata = creSqldata;
    }

    /**
     * 
     * @return
     */
    public String getCfrColumn() {
        return cfrColumn;
    }

    /**
     * 
     * @param cfrColumn
     */
    public void setCfrColumn(String cfrColumn) {
        this.cfrColumn = cfrColumn;
    }

    /**
     * 
     * @return
     */
    public BigDecimal getCfrId() {
        return cfrId;
    }

    /**
     * 
     * @param cfrId
     */
    public void setCfrId(BigDecimal cfrId) {
        this.cfrId = cfrId;
    }

    /**
     * 
     * @return
     */
    public String getCfrLabel() {
        return cfrLabel;
    }

    /**
     * 
     * @param cfrLabel
     */
    public void setCfrLabel(String cfrLabel) {
        this.cfrLabel = cfrLabel;
    }

    /**
     * 
     * @return
     */
    public String getCfrType() {
        return cfrType;
    }

    /**
     * 
     * @param cfrType
     */
    public void setCfrType(String cfrType) {
        this.cfrType = cfrType;
    }
    private CatalogAdc catalogAdc;

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

        return "CatalogFilter:" + "cfrId=" + cfrId + ", cfrLabel=" + cfrLabel
                + ", cfrColumn=" + cfrColumn + ", cfrType=" + cfrType + '.';
    }

    @Override
    public String toString() {
        return "CatalogFilter:" + "cfrId=" + cfrId + ", cfrLabel=" + cfrLabel
                + ", cfrColumn=" + cfrColumn + ", cfrType=" + cfrType + '.';
    }
}