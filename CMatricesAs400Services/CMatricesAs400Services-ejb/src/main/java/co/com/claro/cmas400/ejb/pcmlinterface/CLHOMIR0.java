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
 * Clase usada para la administraci&oacute;n del PCML de Homologaciones DANE
 * (Anteriormente <i>INSHOMOLO</i>).
 * 
 * @author bocanegravm
 */
@Program(programName = "CLHOMIR0", documentName = "co.com.claro.cmas400.ejb.pcml.CLHOMIR0")
public class CLHOMIR0 {

    @Data(pcmlName = "IDPRC", usage = UsageType.INPUTOUTPUT)        //1
    private String IDPRC = new String();
    @Data(pcmlName = "IDUSER", usage = UsageType.INPUTOUTPUT)   //2
    private String IDUSER = new String();
    @Data(pcmlName = "CODDIV", usage = UsageType.INPUTOUTPUT)   //3
    private String CODDIV = new String();
    @Data(pcmlName = "CODCOM", usage = UsageType.INPUTOUTPUT)   //4
    private String CODCOM = new String();
    @Data(pcmlName = "NOMREG", usage = UsageType.INPUTOUTPUT)   //5
    private String NOMREG = new String();
    @Data(pcmlName = "NOMDEP", usage = UsageType.INPUTOUTPUT)   //6
    private String NOMDEP = new String();
    @Data(pcmlName = "NOMMUN", usage = UsageType.INPUTOUTPUT)   //7
    private String NOMMUN = new String();
    @Data(pcmlName = "ISMUNI", usage = UsageType.INPUTOUTPUT)   //8
    private String ISMUNI = new String();
    @Data(pcmlName = "HOMDEP", usage = UsageType.INPUTOUTPUT)   //9
    private String HOMDEP = new String();
    @Data(pcmlName = "HOMMUN", usage = UsageType.INPUTOUTPUT)   //10
    private String HOMMUN = new String();
    @Data(pcmlName = "CODDAN", usage = UsageType.INPUTOUTPUT)   //11
    private String CODDAN = new String();
    @Data(pcmlName = "CODUBI", usage = UsageType.INPUTOUTPUT)   //12
    private String CODUBI = new String();
    @Data(pcmlName = "ESTREG", usage = UsageType.INPUTOUTPUT)   //13
    private String ESTREG = new String();
    @Data(pcmlName = "IDENDM", usage = UsageType.INPUTOUTPUT)   //14
    private String IDENDM = new String();
    @Data(pcmlName = "ENDMSG", usage = UsageType.INPUTOUTPUT)   //15
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

    public String getCODDIV() {
        return CODDIV;
    }

    public void setCODDIV(String CODDIV) {
        this.CODDIV = CODDIV;
    }

    public String getCODCOM() {
        return CODCOM;
    }

    public void setCODCOM(String CODCOM) {
        this.CODCOM = CODCOM;
    }

    public String getNOMREG() {
        return NOMREG;
    }

    public void setNOMREG(String NOMREG) {
        this.NOMREG = NOMREG;
    }

    public String getNOMDEP() {
        return NOMDEP;
    }

    public void setNOMDEP(String NOMDEP) {
        this.NOMDEP = NOMDEP;
    }

    public String getNOMMUN() {
        return NOMMUN;
    }

    public void setNOMMUN(String NOMMUN) {
        this.NOMMUN = NOMMUN;
    }

    public String getISMUNI() {
        return ISMUNI;
    }

    public void setISMUNI(String ISMUNI) {
        this.ISMUNI = ISMUNI;
    }

    public String getHOMDEP() {
        return HOMDEP;
    }

    public void setHOMDEP(String HOMDEP) {
        this.HOMDEP = HOMDEP;
    }

    public String getHOMMUN() {
        return HOMMUN;
    }

    public void setHOMMUN(String HOMMUN) {
        this.HOMMUN = HOMMUN;
    }

    public String getCODDAN() {
        return CODDAN;
    }

    public void setCODDAN(String CODDAN) {
        this.CODDAN = CODDAN;
    }

    public String getCODUBI() {
        return CODUBI;
    }

    public void setCODUBI(String CODUBI) {
        this.CODUBI = CODUBI;
    }

    public String getESTREG() {
        return ESTREG;
    }

    public void setESTREG(String ESTREG) {
        this.ESTREG = ESTREG;
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
