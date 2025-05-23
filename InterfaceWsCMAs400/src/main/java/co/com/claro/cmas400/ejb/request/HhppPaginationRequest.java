/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.cmas400.ejb.request;

/**
 *
 * @author aleal
 */
public class HhppPaginationRequest{
    
    /** ID de Usuario. */
    private String iduser;
    /** Fecha de Inicio. */
    private String datein;
    /** Fecha de Fin. */
    private String dateen;
    /** Cantidad de Registros para env&iacute;o. */
    private String ctdrge;
    /** Cantidad de Registros para el rango. */
    private String ctdrgr;
    /** Cantidad que se entregan en el lote. */
    private String ctdrgs;
    /** Address Key. */
    private String unakyn;
    /** Hora de Inicio. */
    private String hourin;
    /** Hora de Fin. */
    private String houren;    
  
    
    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    public String getDatein() {
        return datein;
    }

    public void setDatein(String datein) {
        this.datein = datein;
    }

    public String getDateen() {
        return dateen;
    }

    public void setDateen(String dateen) {
        this.dateen = dateen;
    }

    public String getCtdrge() {
        return ctdrge;
    }

    public void setCtdrge(String ctdrge) {
        this.ctdrge = ctdrge;
    }

    public String getCtdrgr() {
        return ctdrgr;
    }

    public void setCtdrgr(String ctdrgr) {
        this.ctdrgr = ctdrgr;
    }

    public String getUnakyn() {
        return unakyn;
    }

    public void setUnakyn(String unakyn) {
        this.unakyn = unakyn;
    }

    public String getHourin() {
        return hourin;
    }

    public void setHourin(String hourin) {
        this.hourin = hourin;
    }

    public String getHouren() {
        return houren;
    }

    public void setHouren(String houren) {
        this.houren = houren;
    }

    public String getCtdrgs() {
        return ctdrgs;
    }

    public void setCtdrgs(String ctdrgs) {
        this.ctdrgs = ctdrgs;
    }
    
}
