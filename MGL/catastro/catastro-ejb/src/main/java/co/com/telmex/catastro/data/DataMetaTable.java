package co.com.telmex.catastro.data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DataMetaTable
 * Representa los metadatos para el catalogo de datos.
 *
 * @author 	Ana María Malambo
 * @version	1.0
 */
public class DataMetaTable {

    private List<DataTable> datatable = null;
    private List<CatalogTableRelacion> tablerelacion = null;
    private String buttonUpdate = null;
    private String buttonDelete = null;
    private String sqlCreate = null;
    private String sqlUpdate = null;
    private String sqlDelete = null;
    private List relacion = null;
    private String buttonCreate = null;
    private String rel = null;
    private BigDecimal cdtId = null;
    private String tablealias = null;
    private String relacionalias = null;
    private String tablename = null;

    /**
     * 
     * @return
     */
    public String getTablename() {
        return tablename;
    }

    /**
     * 
     * @param tablename
     */
    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    /**
     * 
     * @return
     */
    public String getRelacionalias() {
        return relacionalias;
    }

    /**
     * 
     * @param relacionalias
     */
    public void setRelacionalias(String relacionalias) {
        this.relacionalias = relacionalias;
    }

    /**
     * 
     * @return
     */
    public String getTablealias() {
        return tablealias;
    }

    /**
     * 
     * @param tablealias
     */
    public void setTablealias(String tablealias) {
        this.tablealias = tablealias;
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
    public List<CatalogTableRelacion> getTablerelacion() {
        return tablerelacion;
    }

    /**
     * 
     * @param tablerelacion
     */
    public void setTablerelacion(List<CatalogTableRelacion> tablerelacion) {
        this.tablerelacion = tablerelacion;
    }

    /**
     * 
     * @return
     */
    public String getRel() {
        return rel;
    }

    /**
     * 
     * @param rel
     */
    public void setRel(String rel) {
        this.rel = rel;
    }

    /**
     * 
     * @return
     */
    public List getRelacion() {
        return relacion;
    }

    /**
     * 
     * @param relacion
     */
    public void setRelacion(ArrayList relacion) {
        this.relacion = relacion;
    }

    /**
     * 
     * @return
     */
    public String getSqlCreate() {
        return sqlCreate;
    }

    /**
     * 
     * @param sqlCreate
     */
    public void setSqlCreate(String sqlCreate) {
        this.sqlCreate = sqlCreate;
    }

    /**
     * 
     * @return
     */
    public String getSqlDelete() {
        return sqlDelete;
    }

    /**
     * 
     * @param sqlDelete
     */
    public void setSqlDelete(String sqlDelete) {
        this.sqlDelete = sqlDelete;
    }

    /**
     * 
     * @return
     */
    public String getSqlUpdate() {
        return sqlUpdate;
    }

    /**
     * 
     * @param sqlUpdate
     */
    public void setSqlUpdate(String sqlUpdate) {
        this.sqlUpdate = sqlUpdate;
    }

    /**
     * 
     * @return
     */
    public String getButtonCreate() {
        return buttonCreate;
    }

    /**
     * 
     * @param buttonCreate
     */
    public void setButtonCreate(String buttonCreate) {
        this.buttonCreate = buttonCreate;
    }

    /**
     * 
     * @return
     */
    public String getButtonDelete() {
        return buttonDelete;
    }

    /**
     * 
     * @param buttonDelete
     */
    public void setButtonDelete(String buttonDelete) {
        this.buttonDelete = buttonDelete;
    }

    /**
     * 
     * @return
     */
    public String getButtonUpdate() {
        return buttonUpdate;
    }

    /**
     * 
     * @param buttonUpdate
     */
    public void setButtonUpdate(String buttonUpdate) {
        this.buttonUpdate = buttonUpdate;
    }

    /**
     * 
     * @return
     */
    public List<DataTable> getDatatable() {
        return datatable;
    }

    /**
     * 
     * @param datatable
     */
    public void setDatatable(List<DataTable> datatable) {
        this.datatable = datatable;
    }

    /**
     * 
     * @return
     */
    public String auditoria() {
        return "DataMetaTable:" + "datatable=" + datatable
                + ", tablerelacion=" + tablerelacion
                + ", buttonUpdate=" + buttonUpdate
                + ", buttonDelete=" + buttonDelete + ", sqlCreate=" + sqlCreate
                + ", sqlUpdate=" + sqlUpdate + ", sqlDelete=" + sqlDelete
                + ", relacion=" + relacion + ", buttonCreate=" + buttonCreate
                + ", rel=" + rel + ", cdtId=" + cdtId
                + ", tablealias=" + tablealias
                + ", relacionalias=" + relacionalias
                + ", tablename=" + tablename + '.';
    }

    @Override
    public String toString() {
        return "DataMetaTable:" + "datatable=" + datatable
                + ", tablerelacion=" + tablerelacion
                + ", buttonUpdate=" + buttonUpdate
                + ", buttonDelete=" + buttonDelete + ", sqlCreate=" + sqlCreate
                + ", sqlUpdate=" + sqlUpdate + ", sqlDelete=" + sqlDelete
                + ", relacion=" + relacion + ", buttonCreate=" + buttonCreate
                + ", rel=" + rel + ", cdtId=" + cdtId
                + ", tablealias=" + tablealias
                + ", relacionalias=" + relacionalias
                + ", tablename=" + tablename + '.';
    }
}
