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
 * Clase usada para la administraci&oacute;n del PCML de Mantenimiento de Nodos 
 * (Anteriormente <i>INSNODES</i>).
 * 
 * @author bocanegravm
 */
@Program(programName = "CLNODIR0", documentName = "co.com.claro.cmas400.ejb.pcml.CLNODIR0")
public class CLNODIR0 {

    @Data(pcmlName = "IDPRC", usage = UsageType.INPUTOUTPUT)
    private String IDPRC = new String();
    @Data(pcmlName = "IDUSER", usage = UsageType.INPUTOUTPUT)
    private String IDUSER = new String();
    @Data(pcmlName = "CODNOD", usage = UsageType.INPUTOUTPUT)
    private String CODNOD = new String();
    @Data(pcmlName = "CODNOM", usage = UsageType.INPUTOUTPUT)
    private String CODNOM = new String();
    @Data(pcmlName = "CODALI", usage = UsageType.INPUTOUTPUT)
    private String CODALI = new String();
    @Data(pcmlName = "CODDIV", usage = UsageType.INPUTOUTPUT)
    private String CODDIV = new String();
    @Data(pcmlName = "CODDVS", usage = UsageType.INPUTOUTPUT)
    private String CODDVS = new String();
    @Data(pcmlName = "CODARE", usage = UsageType.INPUTOUTPUT)
    private String CODARE = new String();
    @Data(pcmlName = "CODZON", usage = UsageType.INPUTOUTPUT)
    private String CODZON = new String();
    @Data(pcmlName = "CODCOM", usage = UsageType.INPUTOUTPUT)
    private String CODCOM = new String();
    @Data(pcmlName = "CODDIS", usage = UsageType.INPUTOUTPUT)
    private String CODDIS = new String();
    @Data(pcmlName = "CODUNI", usage = UsageType.INPUTOUTPUT)
    private String CODUNI = new String();
    @Data(pcmlName = "TIPRED", usage = UsageType.INPUTOUTPUT)
    private String TIPRED = new String();
    @Data(pcmlName = "EDONOD", usage = UsageType.INPUTOUTPUT)
    private String EDONOD = new String();
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

    public String getCODNOD() {
        return CODNOD;
    }

    public void setCODNOD(String CODNOD) {
        this.CODNOD = CODNOD;
    }

    public String getCODNOM() {
        return CODNOM;
    }

    public void setCODNOM(String CODNOM) {
        this.CODNOM = CODNOM;
    }

    public String getCODALI() {
        return CODALI;
    }

    public void setCODALI(String CODALI) {
        this.CODALI = CODALI;
    }

    public String getCODDIV() {
        return CODDIV;
    }

    public void setCODDIV(String CODDIV) {
        this.CODDIV = CODDIV;
    }

    public String getCODDVS() {
        return CODDVS;
    }

    public void setCODDVS(String CODDVS) {
        this.CODDVS = CODDVS;
    }

    public String getCODARE() {
        return CODARE;
    }

    public void setCODARE(String CODARE) {
        this.CODARE = CODARE;
    }

    public String getCODZON() {
        return CODZON;
    }

    public void setCODZON(String CODZON) {
        this.CODZON = CODZON;
    }

    public String getCODCOM() {
        return CODCOM;
    }

    public void setCODCOM(String CODCOM) {
        this.CODCOM = CODCOM;
    }

    public String getCODDIS() {
        return CODDIS;
    }

    public void setCODDIS(String CODDIS) {
        this.CODDIS = CODDIS;
    }

    public String getCODUNI() {
        return CODUNI;
    }

    public void setCODUNI(String CODUNI) {
        this.CODUNI = CODUNI;
    }

    public String getTIPRED() {
        return TIPRED;
    }

    public void setTIPRED(String TIPRED) {
        this.TIPRED = TIPRED;
    }

    public String getEDONOD() {
        return EDONOD;
    }

    public void setEDONOD(String EDONOD) {
        this.EDONOD = EDONOD;
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
