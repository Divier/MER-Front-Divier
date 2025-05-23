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
 * Clase usada para la administraci&oacute;n del PCML de Paginaci&oacute;n de HHPP
 * (Anteriormente <i>INSHOMEPS</i>).
 * 
 * @author bocanegravm
 */
@Program(programName = "CLHMEIR0", documentName = "co.com.claro.cmas400.ejb.pcml.CLHMEIR0")
public class CLHMEIR0 {

    @Data(pcmlName = "IDPRC", usage = UsageType.INPUTOUTPUT)
    private String IDPRC = new String();
    @Data(pcmlName = "IDUSER", usage = UsageType.INPUTOUTPUT)
    private String IDUSER = new String();
    @Data(pcmlName = "DATEIN", usage = UsageType.INPUTOUTPUT)
    private String DATEIN = new String();
    @Data(pcmlName = "HOURIN", usage = UsageType.INPUTOUTPUT)
    private String HOURIN = new String();
    @Data(pcmlName = "DATEEN", usage = UsageType.INPUTOUTPUT)
    private String DATEEN = new String();
    @Data(pcmlName = "HOUREN", usage = UsageType.INPUTOUTPUT)
    private String HOUREN = new String();
    @Data(pcmlName = "CTDRGE", usage = UsageType.INPUTOUTPUT)
    private String CTDRGE = new String();
    @Data(pcmlName = "CTDRGR", usage = UsageType.INPUTOUTPUT)
    private String CTDRGR = new String();
    @Data(pcmlName = "CTDRGS", usage = UsageType.INPUTOUTPUT)
    private String CTDRGS = new String();
    @Data(pcmlName = "UNAKY#", usage = UsageType.INPUTOUTPUT)
    private String UNAKYN = new String();
    @Data(pcmlName = "ARRUNKY", usage = UsageType.INPUTOUTPUT)
    private String ARRUNKY = new String();
    @Data(pcmlName = "IDENDM", usage = UsageType.INPUTOUTPUT)
    private String IDENDM = new String();
    @Data(pcmlName = "ENDMSG", usage = UsageType.INPUTOUTPUT)
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

    public String getDATEIN() {
        return DATEIN;
    }

    public void setDATEIN(String DATEIN) {
        this.DATEIN = DATEIN;
    }

    public String getDATEEN() {
        return DATEEN;
    }

    public void setDATEEN(String DATEEN) {
        this.DATEEN = DATEEN;
    }

    public String getCTDRGE() {
        return CTDRGE;
    }

    public void setCTDRGE(String CTDRGE) {
        this.CTDRGE = CTDRGE;
    }

    public String getCTDRGR() {
        return CTDRGR;
    }

    public void setCTDRGR(String CTDRGR) {
        this.CTDRGR = CTDRGR;
    }

    public String getUNAKYN() {
        return UNAKYN;
    }

    public void setUNAKYN(String UNAKYN) {
        this.UNAKYN = UNAKYN;
    }

    public String getARRUNKY() {
        return ARRUNKY;
    }

    public void setARRUNKY(String ARRUNKY) {
        this.ARRUNKY = ARRUNKY;
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

    public String getHOURIN() {
        return HOURIN;
    }

    public void setHOURIN(String HOURIN) {
        this.HOURIN = HOURIN;
    }

    public String getHOUREN() {
        return HOUREN;
    }

    public void setHOUREN(String HOUREN) {
        this.HOUREN = HOUREN;
    }

    public String getCTDRGS() {
        return CTDRGS;
    }

    public void setCTDRGS(String CTDRGS) {
        this.CTDRGS = CTDRGS;
    }

}
