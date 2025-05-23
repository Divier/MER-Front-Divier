package co.com.telmex.catastro.data;

import java.util.ArrayList;

/**
 * Clase DataTable
 * Representa El datatable a generar en presentación.
 *
 * @author 	Ana María Malambo.
 * @version	1.0
 */
public class DataTable {
    private String name=null;
    private String namesystem=null;
    private String type=null;
    private String nametTable=null;
    private String ctlfk=null;
    private String value=null;
    private String isnull=null;
    private String isedit=null;
    private Boolean isvisibility;
    private Boolean pk;
    private String longitud;
 
    private String typer;
    private String table;
    private String namecamporel;

    /**
     * 
     * @return
     */
    public String getNamecamporel() {
        return namecamporel;
    }

    /**
     * 
     * @param namecamporel
     */
    public void setNamecamporel(String namecamporel) {
        this.namecamporel = namecamporel;
    }
    /**
     * 
     * @return
     */
    public String getLongitud() {
        return longitud;
    }

    /**
     * 
     * @param longitud
     */
    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    /**
     * 
     * @return
     */
    public String getTable() {
        return table;
    }

    /**
     * 
     * @param table
     */
    public void setTable(String table) {
        this.table = table;
    }

    /**
     * 
     * @return
     */
    public String getNamesystem() {
        return namesystem;
    }

    /**
     * 
     * @param namesystem
     */
    public void setNamesystem(String namesystem) {
        this.namesystem = namesystem;
    }
    /**
     * 
     * @return
     */
    public String getTyper() {
        return typer;
    }

    /**
     * 
     * @param typer
     */
    public void setTyper(String typer) {
        this.typer = typer;
    }

    /**
     * 
     * @return
     */
    public Boolean getPk() {
        return pk;
    }

    /**
     * 
     * @param pk
     */
    public void setPk(Boolean pk) {
        this.pk = pk;
    }
    /**
     * 
     * @return
     */
    public Boolean getIsvisibility() {
        return isvisibility;
    }

    /**
     * 
     * @param isvisibility
     */
    public void setIsvisibility(Boolean isvisibility) {
        this.isvisibility = isvisibility;
    }

    
    private ArrayList tablarel=null;

    /**
     * 
     * @return
     */
    public ArrayList getTablarel() {
        return tablarel;
    }

    /**
     * 
     * @param tablarel
     */
    public void setTablarel(ArrayList tablarel) {
        this.tablarel = tablarel;
    }
    /**
     * 
     * @return
     */
    public String getCtlfk() {
        return ctlfk;
    }

    /**
     * 
     * @param ctlfk
     */
    public void setCtlfk(String ctlfk) {
        this.ctlfk = ctlfk;
    }
   

   

    /**
     * 
     * @return
     */
    public String getIsedit() {
        return isedit;
    }

    /**
     * 
     * @param isedit
     */
    public void setIsedit(String isedit) {
        this.isedit = isedit;
    }

    /**
     * 
     * @return
     */
    public String getIsnull() {
        return isnull;
    }

    /**
     * 
     * @param isnull
     */
    public void setIsnull(String isnull) {
        this.isnull = isnull;
    }

    /**
     * 
     */
    public DataTable(){}

    /**
     * 
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 
     * @return
     */
    public String getNametTable() {
        return nametTable;
    }

    /**
     * 
     * @param nametTable
     */
    public void setNametTable(String nametTable) {
        this.nametTable = nametTable;
    }
    
     
    /**
     * 
     * @return
     */
    public String getValue() {
        return value;
    }

    /**
     * 
     * @param value
     */
    public void setValue(String value) {
        this.value = value;
    }
}
