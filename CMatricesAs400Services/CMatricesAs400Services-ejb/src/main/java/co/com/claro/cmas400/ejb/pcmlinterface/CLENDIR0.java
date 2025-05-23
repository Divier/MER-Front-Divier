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
 * Clase que administra el PCML de Estado de Nodos 
 * (Anteriormente <i>INSENODES</i>).
 * @author bocanegravm
 */
@Program(programName = "CLENDIR0", documentName = "co.com.claro.cmas400.ejb.pcml.CLENDIR0")
public class CLENDIR0 {

    @Data(pcmlName = "IDPRC", usage = UsageType.INPUTOUTPUT)
    private String IDPRC = new String();
    @Data(pcmlName = "IDUSER", usage = UsageType.INPUTOUTPUT)
    private String IDUSER = new String();
    @Data(pcmlName = "NDCODEP", usage = UsageType.INPUTOUTPUT)
    private String NDCODEP = new String();
    @Data(pcmlName = "NDDESEP", usage = UsageType.INPUTOUTPUT)
    private String NDDESEP = new String();
    @Data(pcmlName = "NDSTATP", usage = UsageType.INPUTOUTPUT)
    private String NDSTATP = new String();
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

    public String getNDCODEP() {
        return NDCODEP;
    }

    public void setNDCODEP(String NDCODEP) {
        this.NDCODEP = NDCODEP;
    }

    public String getNDDESEP() {
        return NDDESEP;
    }

    public void setNDDESEP(String NDDESEP) {
        this.NDDESEP = NDDESEP;
    }

    public String getNDSTATP() {
        return NDSTATP;
    }

    public void setNDSTATP(String NDSTATP) {
        this.NDSTATP = NDSTATP;
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
