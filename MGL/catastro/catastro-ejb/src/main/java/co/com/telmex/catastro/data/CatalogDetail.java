package co.com.telmex.catastro.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Clase CatalogAdc
 * Representa La información Del detalle del catálogo (Menú).
 * implementa Serialización
 *
 * @author 	Jose Luis Caicedo G.
 * @version	1.0
 */
public class CatalogDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigDecimal cdtId;
    private String cdtSql;
    private String cdtTable;
    private BigInteger cdtType;
    private String cdtBcreate;
    private String cdtBupdate;
    private String cdtBdelete;
    private String cdtSqlcreate;
    private String cdtSqlupdate;
    private String cdtSqldelete;
    private String cdtProgram;

    /**
     * 
     */
    public CatalogDetail() {
    }

    /**
     * 
     * @param cdtId
     */
    public CatalogDetail(BigDecimal cdtId) {
        this.cdtId = cdtId;
    }

    /**
     * 
     * @return
     */
    public BigDecimal getCdtId() {
        return cdtId;
    }

    /**
     * 
     * @param cdtId
     */
    public void setCdtId(BigDecimal cdtId) {
        this.cdtId = cdtId;
    }

    /**
     * 
     * @return
     */
    public String getCdtSql() {
        return cdtSql;
    }

    /**
     * 
     * @param cdtSql
     */
    public void setCdtSql(String cdtSql) {
        this.cdtSql = cdtSql;
    }

    /**
     * 
     * @return
     */
    public String getCdtTable() {
        return cdtTable;
    }

    /**
     * 
     * @param cdtTable
     */
    public void setCdtTable(String cdtTable) {
        this.cdtTable = cdtTable;
    }

    /**
     * 
     * @return
     */
    public BigInteger getCdtType() {
        return cdtType;
    }

    /**
     * 
     * @param cdtType
     */
    public void setCdtType(BigInteger cdtType) {
        this.cdtType = cdtType;
    }

    /**
     * 
     * @return
     */
    public String getCdtBcreate() {
        return cdtBcreate;
    }

    /**
     * 
     * @param cdtBcreate
     */
    public void setCdtBcreate(String cdtBcreate) {
        this.cdtBcreate = cdtBcreate;
    }

    /**
     * 
     * @return
     */
    public String getCdtBupdate() {
        return cdtBupdate;
    }

    /**
     * 
     * @param cdtBupdate
     */
    public void setCdtBupdate(String cdtBupdate) {
        this.cdtBupdate = cdtBupdate;
    }

    /**
     * 
     * @return
     */
    public String getCdtBdelete() {
        return cdtBdelete;
    }

    /**
     * 
     * @param cdtBdelete
     */
    public void setCdtBdelete(String cdtBdelete) {
        this.cdtBdelete = cdtBdelete;
    }

    /**
     * 
     * @return
     */
    public String getCdtSqlcreate() {
        return cdtSqlcreate;
    }

    /**
     * 
     * @param cdtSqlcreate
     */
    public void setCdtSqlcreate(String cdtSqlcreate) {
        this.cdtSqlcreate = cdtSqlcreate;
    }

    /**
     * 
     * @return
     */
    public String getCdtSqlupdate() {
        return cdtSqlupdate;
    }

    /**
     * 
     * @param cdtSqlupdate
     */
    public void setCdtSqlupdate(String cdtSqlupdate) {
        this.cdtSqlupdate = cdtSqlupdate;
    }

    /**
     * 
     * @return
     */
    public String getCdtSqldelete() {
        return cdtSqldelete;
    }

    /**
     * 
     * @param cdtSqldelete
     */
    public void setCdtSqldelete(String cdtSqldelete) {
        this.cdtSqldelete = cdtSqldelete;
    }

    /**
     * 
     * @return
     */
    public String getCdtProgram() {
        return cdtProgram;
    }

    /**
     * 
     * @param cdtProgram
     */
    public void setCdtProgram(String cdtProgram) {
        this.cdtProgram = cdtProgram;
    }

    /**
     * 
     * @return
     */
    public String auditoria() {
        return "CatalogDetail:" + "cdtId=" + cdtId + ", cdtSql=" + cdtSql
                + ", cdtTable=" + cdtTable + ", cdtType=" + cdtType
                + ", cdtBcreate=" + cdtBcreate + ", cdtBupdate=" + cdtBupdate
                + ", cdtBdelete=" + cdtBdelete + ", cdtSqlcreate=" + cdtSqlcreate
                + ", cdtSqlupdate=" + cdtSqlupdate + ", cdtSqldelete=" + cdtSqldelete
                + ", cdtProgram=" + cdtProgram + '.';
    }

    @Override
    public String toString() {
        return "CatalogDetail:" + "cdtId=" + cdtId + ", cdtSql=" + cdtSql
                + ", cdtTable=" + cdtTable + ", cdtType=" + cdtType
                + ", cdtBcreate=" + cdtBcreate + ", cdtBupdate=" + cdtBupdate
                + ", cdtBdelete=" + cdtBdelete + ", cdtSqlcreate=" + cdtSqlcreate
                + ", cdtSqlupdate=" + cdtSqlupdate + ", cdtSqldelete=" + cdtSqldelete
                + ", cdtProgram=" + cdtProgram + '.';
    }
}