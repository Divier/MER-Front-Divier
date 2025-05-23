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
 * Clase usada para la administraci&oacute;n del PCML de Tipificaci&oacute;n de Red
 * (Anteriormente <i>INSTNETS</i>).
 * 
 * @author bocanegravm
 */
@Program(programName = "CLNETIR0", documentName = "co.com.claro.cmas400.ejb.pcml.CLNETIR0")
public class CLNETIR0 {

    @Data(pcmlName = "IDPRC", usage = UsageType.INPUTOUTPUT)
    private String IDPRC = new String();
    @Data(pcmlName = "IDUSER", usage = UsageType.INPUTOUTPUT)
    private String IDUSER = new String();
    @Data(pcmlName = "TRCODR", usage = UsageType.INPUTOUTPUT)
    private String TRCODR = new String();
    @Data(pcmlName = "TRDESR", usage = UsageType.INPUTOUTPUT)
    private String TRDESR = new String();
    @Data(pcmlName = "TRSTAT", usage = UsageType.INPUTOUTPUT)
    private String TRSTAT = new String();
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

    public String getTRCODR() {
        return TRCODR;
    }

    public void setTRCODR(String TRCODR) {
        this.TRCODR = TRCODR;
    }

    public String getTRDESR() {
        return TRDESR;
    }

    public void setTRDESR(String TRDESR) {
        this.TRDESR = TRDESR;
    }

    public String getTRSTAT() {
        return TRSTAT;
    }

    public void setTRSTAT(String TRSTAT) {
        this.TRSTAT = TRSTAT;
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

}
