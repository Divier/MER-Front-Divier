/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.cmas400.ejb.respons;

import java.io.Serializable;

/**
 *
 * @author JPeña
 */
public class MantenimientoBasicoRRPlantaResponse  extends MantenimientoBasicoRRBaseResponse implements Serializable{
    
    /** ID User. */
    private String IDUSER ;//1
    
    /** HE TYPE. */
    private String PFHTYP  ;//2
    
    /** HEAD END. */
    private String PFHEND ;//3
    
    /** LOC TYPE. */
    private String PFSTYP ;//4
    
    /** LOCATION. */
    private String PFSNOD ;//5
    
    /** REF LOC TYPE. */
    private String PFRTYP ;//6
    
    /** REF LOCATION. */
    private String PFRNOD ;//7
    
    /** FIELD LOCN DESCRIPTION. */
    private String PFDESC ;//8
    
    /** &Ntilde; OF S/C 24 HOURS. */
    private String PF1DYNP ;//9
    
    /** S/C &Ntilde; TRIGGER 24 HOURS. */
    private String PF1DYTP ;//10
    
    /** &Ntilde; OF S/C 48 HOURS. */
    private String PF2DYNP ;//11
    
    /** S/C &Ntilde; TRIGGER 48 HOURS. */
    private String PF2DYTP ;//12
    
    /** &Ntilde; OF S/C 1 WEEK. */
    private String PF1WKNP ;//13
    
    /** S/C &Ntilde; TRIGGER 1 WEEK. */
    private String PF1WKTP ;//14
    
    /** OF S/C 1 MONTH. */
    private String PF1MTNP ;//15
    
    /** S/C &Ntilde; TRIGGER 1 MONTH. */
    private String PF1MTTP ;//16
    
    /** &Ntilde; OF S/C 1 YEAR. */
    private String PF1YRNP ;//17
    
    /** S/C &Ntilde; TRIGGER 1 YEAR. */
    private String PF1YRTP ;//18
    
    
    
    public String getIDUSER() {
        return IDUSER;
    }

    public void setIDUSER(String IDUSER) {
        this.IDUSER = IDUSER;
    }

    public String getPFHTYP() {
        return PFHTYP;
    }

    public void setPFHTYP(String PFHTYP) {
        this.PFHTYP = PFHTYP;
    }

    public String getPFHEND() {
        return PFHEND;
    }

    public void setPFHEND(String PFHEND) {
        this.PFHEND = PFHEND;
    }

    public String getPFSTYP() {
        return PFSTYP;
    }

    public void setPFSTYP(String PFSTYP) {
        this.PFSTYP = PFSTYP;
    }

    public String getPFSNOD() {
        return PFSNOD;
    }

    public void setPFSNOD(String PFSNOD) {
        this.PFSNOD = PFSNOD;
    }

    public String getPFRTYP() {
        return PFRTYP;
    }

    public void setPFRTYP(String PFRTYP) {
        this.PFRTYP = PFRTYP;
    }

    public String getPFRNOD() {
        return PFRNOD;
    }

    public void setPFRNOD(String PFRNOD) {
        this.PFRNOD = PFRNOD;
    }

    public String getPFDESC() {
        return PFDESC;
    }

    public void setPFDESC(String PFDESC) {
        this.PFDESC = PFDESC;
    }

    
    public String getPF1DYTP() {
        return PF1DYTP;
    }

    public void setPF1DYTP(String PF1DYTP) {
        this.PF1DYTP = PF1DYTP;
    }

   

    public String getPF2DYTP() {
        return PF2DYTP;
    }

    public void setPF2DYTP(String PF2DYTP) {
        this.PF2DYTP = PF2DYTP;
    }

    

    public String getPF1WKTP() {
        return PF1WKTP;
    }

    public void setPF1WKTP(String PF1WKTP) {
        this.PF1WKTP = PF1WKTP;
    }

   

    public String getPF1MTTP() {
        return PF1MTTP;
    }

    public void setPF1MTTP(String PF1MTTP) {
        this.PF1MTTP = PF1MTTP;
    }

    public String getPF1DYNP() {
        return PF1DYNP;
    }

    public void setPF1DYNP(String PF1DYNP) {
        this.PF1DYNP = PF1DYNP;
    }

    public String getPF2DYNP() {
        return PF2DYNP;
    }

    public void setPF2DYNP(String PF2DYNP) {
        this.PF2DYNP = PF2DYNP;
    }

    public String getPF1WKNP() {
        return PF1WKNP;
    }

    public void setPF1WKNP(String PF1WKNP) {
        this.PF1WKNP = PF1WKNP;
    }

    public String getPF1MTNP() {
        return PF1MTNP;
    }

    public void setPF1MTNP(String PF1MTNP) {
        this.PF1MTNP = PF1MTNP;
    }

    public String getPF1YRNP() {
        return PF1YRNP;
    }

    public void setPF1YRNP(String PF1YRNP) {
        this.PF1YRNP = PF1YRNP;
    }

   

    public String getPF1YRTP() {
        return PF1YRTP;
    }

    public void setPF1YRTP(String PF1YRTP) {
        this.PF1YRTP = PF1YRTP;
    }
    
    
    
    
    
}
