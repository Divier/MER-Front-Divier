/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.cmas400.ejb.pcmlinterface;

import co.com.claro.cmas400.ejb.core.Data;
import co.com.claro.cmas400.ejb.core.Program;
import co.com.claro.cmas400.ejb.core.UsageType;


/**
 * Clase usada para la administraci&oacute;n del PCML de Mantenimiento de Plantas
 * (Anteriormente <i>INSPLANTA</i>).
 * 
 * @author bocanegravm
 */
@Program(programName = "CLPLTIR0", documentName = "co.com.claro.cmas400.ejb.pcml.CLPLTIR0")
public class CLPLTIR0 {
    
    @Data(pcmlName = "IDPRC", usage = UsageType.INPUTOUTPUT)        //1
    private String IDPRC = new String();
    @Data(pcmlName = "IDUSER", usage = UsageType.INPUTOUTPUT)   //2
    private String IDUSER = new String();
    @Data(pcmlName = "PFHTYP", usage = UsageType.INPUTOUTPUT)//3
    private String PFHTYP = new String();
    @Data(pcmlName = "PFHEND", usage = UsageType.INPUTOUTPUT)//4
    private String PFHEND = new String();
    @Data(pcmlName = "PFSTYP", usage = UsageType.INPUTOUTPUT)//5
    private String PFSTYP = new String();
    @Data(pcmlName = "PFSNOD", usage = UsageType.INPUTOUTPUT)//6
    private String PFSNOD = new String();
    @Data(pcmlName = "PFRTYP", usage = UsageType.INPUTOUTPUT)//7
    private String PFRTYP = new String();
    @Data(pcmlName = "PFRNOD", usage = UsageType.INPUTOUTPUT)//8
    private String PFRNOD = new String();
    @Data(pcmlName = "PFDESC", usage = UsageType.INPUTOUTPUT)//9
    private String PFDESC = new String();
    @Data(pcmlName = "PF1DYNP", usage = UsageType.INPUTOUTPUT)//10
    private String PF1DYNP = new String();
    @Data(pcmlName = "PF1DYTP", usage = UsageType.INPUTOUTPUT)//11
    private String PF1DYTP = new String();
    @Data(pcmlName = "PF2DYNP", usage = UsageType.INPUTOUTPUT)//12
    private String PF2DYNP = new String();
    @Data(pcmlName = "PF2DYTP", usage = UsageType.INPUTOUTPUT)//13
    private String PF2DYTP = new String();
    @Data(pcmlName = "PF1WKNP", usage = UsageType.INPUTOUTPUT)//14
    private String PF1WKNP = new String();
    @Data(pcmlName = "PF1WKTP", usage = UsageType.INPUTOUTPUT)//15
    private String PF1WKTP = new String();
    @Data(pcmlName = "PF1MTNP", usage = UsageType.INPUTOUTPUT)//16
    private String PF1MTNP = new String();
    @Data(pcmlName = "PF1MTTP", usage = UsageType.INPUTOUTPUT)//17
    private String PF1MTTP = new String();
    @Data(pcmlName = "PF1YRNP", usage = UsageType.INPUTOUTPUT)//18
    private String PF1YRNP = new String();
    @Data(pcmlName = "PF1YRTP", usage = UsageType.INPUTOUTPUT)//19
    private String PF1YRTP = new String();
    @Data(pcmlName = "IDENDM", usage = UsageType.INPUTOUTPUT)//20
    private String IDENDM = new String();
    @Data(pcmlName = "ENDMSG", usage = UsageType.INPUTOUTPUT)//21
    private String ENDMSG = new String();

    
    
    
    public String getIDPRC() {
        return IDPRC;
    }

    public void setIDPRC(String IDPRC) {
        this.IDPRC = IDPRC;
    }

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

   

    public String getPF1YRTP() {
        return PF1YRTP;
    }

    public void setPF1YRTP(String PF1YRTP) {
        this.PF1YRTP = PF1YRTP;
    }

    public String getIDENDM() {
        return IDENDM;
    }

    public void setIDENDM(String IDENDM) {
        this.IDENDM = IDENDM;
    }

    public String getENDMSG() {
        return ENDMSG;
    }

    public void setENDMSG(String ENDMSG) {
        this.ENDMSG = ENDMSG;
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
    
}
