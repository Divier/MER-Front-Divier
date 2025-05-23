package co.com.claro.cmas400.ejb.pcmlinterface;

import co.com.claro.cmas400.ejb.core.Array;
import co.com.claro.cmas400.ejb.core.Data;
import co.com.claro.cmas400.ejb.core.Program;
import co.com.claro.cmas400.ejb.core.UsageType;
import java.util.ArrayList;

@Program(programName = "CMNBAR01", documentName = "co.com.claro.cmas400.ejb.pcml.CMNBAR01")
public class CMNBAR01 {

    @Data(pcmlName = "PCML", usage = UsageType.INPUTOUTPUT)
    private String PCML = new String();
    @Data(pcmlName = "CODBAR_A", usage = UsageType.INPUTOUTPUT)
    private String CODBAR_A = new String();
    @Data(pcmlName = "DESBAR", usage = UsageType.INPUTOUTPUT)
    private String DESBAR = new String();
    @Data(pcmlName = "CODDIV", usage = UsageType.INPUTOUTPUT)
    private String CODDIV = new String();
    @Data(pcmlName = "CODCOM", usage = UsageType.INPUTOUTPUT)
    private String CODCOM = new String();
    @Data(pcmlName = "PFOPCI", usage = UsageType.INPUTOUTPUT)
    private String PFOPCI = new String();
    @Data(pcmlName = "PCONFI", usage = UsageType.INPUTOUTPUT)
    private String PCONFI = new String();
    @Data(pcmlName = "PUSERID", usage = UsageType.INPUTOUTPUT)
    private String PUSERID = new String();
    @Data(pcmlName = "NUM_REG", usage = UsageType.INPUTOUTPUT)
    private String NUM_REG = new String();
    @Data(pcmlName = "RESULTADO", usage = UsageType.INPUTOUTPUT)
    private String RESULTADO = new String();
    @Array(pcmlName = "TABLARTA", size = 721, type = java.lang.String.class, usage = UsageType.INPUTOUTPUT)
    private ArrayList TABLARTA = new ArrayList();
    @Data(pcmlName = "MENSAJE", usage = UsageType.INPUTOUTPUT)
    private String MENSAJE = new String();

    public String getPCML() {
        return PCML;
    }

    public void setPCML(String PCML) {
        this.PCML = PCML;
    }

    public String getCODBAR_A() {
        return CODBAR_A;
    }

    public void setCODBAR_A(String CODBAR_A) {
        this.CODBAR_A = CODBAR_A;
    }

    public String getDESBAR() {
        return DESBAR;
    }

    public void setDESBAR(String DESBAR) {
        this.DESBAR = DESBAR;
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

    public String getPFOPCI() {
        return PFOPCI;
    }

    public void setPFOPCI(String PFOPCI) {
        this.PFOPCI = PFOPCI;
    }

    public String getPCONFI() {
        return PCONFI;
    }

    public void setPCONFI(String PCONFI) {
        this.PCONFI = PCONFI;
    }

    public String getNUM_REG() {
        return NUM_REG;
    }

    public void setNUM_REG(String NUM_REG) {
        this.NUM_REG = NUM_REG;
    }

    public String getRESULTADO() {
        return RESULTADO;
    }

    public void setRESULTADO(String RESULTADO) {
        this.RESULTADO = RESULTADO;
    }

    public String getMENSAJE() {
        return MENSAJE;
    }

    public void setMENSAJE(String MENSAJE) {
        this.MENSAJE = MENSAJE;
    }

    public ArrayList getTABLARTA() {
        return TABLARTA;
    }

    public void setTABLARTA(ArrayList TABLARTA) {
        this.TABLARTA = TABLARTA;
    }

    public String getPUSERID() {
        return PUSERID;
    }

    public void setPUSERID(String PUSERID) {
        this.PUSERID = PUSERID;
    }

}
