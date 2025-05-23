package co.com.telmex.catastro.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Clase CatalogAdc
 * Representa La información Del catálogo (Menú).
 * implementa Serialización
 *
 * @author 	Jose Luis Caicedo G.
 * @version	1.0
 */
public class CatalogAdc implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal ctlId;
    private String ctlName;
    private String ctlSql;
    private String ctlSqlCount;
    private String ctlMode;
    private String ctlBcreate;
    private String ctlProgramam;
    private String ctlAlias;

    /**
     * 
     * @return
     */
    public String getCtlAlias() {
        return ctlAlias;
    }

    /**
     * 
     * @param ctlAlias
     */
    public void setCtlAlias(String ctlAlias) {
        this.ctlAlias = ctlAlias;
    }
    private List<CatalogFilter> catalogFilterList;
    private List<CatalogFilterReport> catalogFilterReport;

    /**
     * 
     * @return
     */
    public List<CatalogFilterReport> getCatalogFilterReport() {
        return catalogFilterReport;
    }

    /**
     * 
     * @param catalogFilterReport
     */
    public void setCatalogFilterReport(List<CatalogFilterReport> catalogFilterReport) {
        this.catalogFilterReport = catalogFilterReport;
    }
    private List<CatalogDetail> catalogDetailList;
    private String[] queryData;

    /**
     * 
     * @return
     */
    public String[] getQueryData() {
        return queryData;
    }

    /**
     * 
     * @param queryData
     */
    public void setQueryData(String[] queryData) {
        this.queryData = queryData;
    }

    /**
     * 
     */
    public CatalogAdc() {
    }

    /**
     * 
     * @param ctlId
     */
    public CatalogAdc(BigDecimal ctlId) {
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
    public void setCtlId(BigDecimal ctlId) {
        this.ctlId = ctlId;
    }

    /**
     * 
     * @return
     */
    public String getCtlName() {
        return ctlName;
    }

    /**
     * 
     * @param ctlName
     */
    public void setCtlName(String ctlName) {
        this.ctlName = ctlName;
    }

    /**
     * 
     * @return
     */
    public String getCtlSql() {
        return ctlSql;
    }

    /**
     * 
     * @param ctlSql
     */
    public void setCtlSql(String ctlSql) {
        this.ctlSql = ctlSql;
    }

    /**
     * 
     * @return
     */
    public String getCtlSqlCount() {
        return ctlSqlCount;
    }

    /**
     * 
     * @param ctlSqlCount
     */
    public void setCtlSqlCount(String ctlSqlCount) {
        this.ctlSqlCount = ctlSqlCount;
    }

    /**
     * 
     * @return
     */
    public String getCtlMode() {
        return ctlMode;
    }

    /**
     * 
     * @param ctlMode
     */
    public void setCtlMode(String ctlMode) {
        this.ctlMode = ctlMode;
    }

    /**
     * 
     * @return
     */
    public String getCtlBcreate() {
        return ctlBcreate;
    }

    /**
     * 
     * @param ctlBcreate
     */
    public void setCtlBcreate(String ctlBcreate) {
        this.ctlBcreate = ctlBcreate;
    }

    /**
     * 
     * @return
     */
    public String getCtlProgramam() {
        return ctlProgramam;
    }

    /**
     * 
     * @param ctlProgramam
     */
    public void setCtlProgramam(String ctlProgramam) {
        this.ctlProgramam = ctlProgramam;
    }

    /**
     * 
     * @return
     */
    public List<CatalogFilter> getCatalogFilterList() {
        return catalogFilterList;
    }

    /**
     * 
     * @param catalogFilterList
     */
    public void setCatalogFilterList(List<CatalogFilter> catalogFilterList) {
        this.catalogFilterList = catalogFilterList;
    }

    /**
     * 
     * @return
     */
    public List<CatalogDetail> getCatalogDetailList() {
        return catalogDetailList;
    }

    /**
     * 
     * @param catalogDetailList
     */
    public void setCatalogDetailList(List<CatalogDetail> catalogDetailList) {
        this.catalogDetailList = catalogDetailList;
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
        return "CatalogAdc:" + "ctlId=" + ctlId + ", ctlName=" + ctlName
                + ", ctlSql=" + ctlSql + ", ctlSqlCount=" + ctlSqlCount
                + ", ctlMode=" + ctlMode + ", ctlBcreate=" + ctlBcreate
                + ", ctlProgramam=" + ctlProgramam + ", ctlAlias=" + ctlAlias
                + ", catalogFilterList=" + catalogFilterList
                + ", catalogDetailList=" + catalogDetailList + '.';
    }

    @Override
    public String toString() {
        return "CatalogAdc:" + "ctlId=" + ctlId + ", ctlName=" + ctlName
                + ", ctlSql=" + ctlSql + ", ctlSqlCount=" + ctlSqlCount
                + ", ctlMode=" + ctlMode + ", ctlBcreate=" + ctlBcreate
                + ", ctlProgramam=" + ctlProgramam + ", ctlAlias=" + ctlAlias
                + ", catalogFilterList=" + catalogFilterList
                + ", catalogDetailList=" + catalogDetailList + '.';
    }
}
