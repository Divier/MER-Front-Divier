package co.com.claro.cmas400.ejb.pcmlinterface;

import co.com.claro.cmas400.ejb.core.Array;
import co.com.claro.cmas400.ejb.core.Data;
import co.com.claro.cmas400.ejb.core.Program;
import co.com.claro.cmas400.ejb.core.UsageType;
import java.util.ArrayList;

@Program(programName = "CMNEDI01", documentName = "co.com.claro.cmas400.ejb.pcml.CMNEDI01")
public class CMNEDI01 {

    @Data(pcmlName = "PCML", usage = UsageType.INPUTOUTPUT)
    private String PCML = new String();
    @Data(pcmlName = "SFOPCI", usage = UsageType.INPUTOUTPUT)
    private String SFOPCI = new String();
    @Data(pcmlName = "PCONFI", usage = UsageType.INPUTOUTPUT)
    private String PCONFI = new String();
    @Data(pcmlName = "PUSERID", usage = UsageType.INPUTOUTPUT)
    private String PUSERID = new String();
    @Data(pcmlName = "CODEDI_A", usage = UsageType.INPUTOUTPUT)
    private String CODEDI_A = new String();
    @Data(pcmlName = "DESEDI", usage = UsageType.INPUTOUTPUT)
    private String DESEDI = new String();
    @Data(pcmlName = "CODIVI", usage = UsageType.INPUTOUTPUT)
    private String CODIVI = new String();
    @Data(pcmlName = "CODCOM", usage = UsageType.INPUTOUTPUT)
    private String CODCOM = new String();
    @Data(pcmlName = "CODBAR_A", usage = UsageType.INPUTOUTPUT)
    private String CODBAR_A = new String();
    @Data(pcmlName = "NUM_REG", usage = UsageType.INPUTOUTPUT)
    private String NUM_REG = new String();
    @Data(pcmlName = "RESULTADO", usage = UsageType.INPUTOUTPUT)
    private String RESULTADO = new String();
    @Array(pcmlName = "TABLARTA", size = 500, type = java.lang.String.class, usage = UsageType.INPUTOUTPUT)
    private ArrayList TABLARTA = new ArrayList();
    @Data(pcmlName = "PMENSAJE", usage = UsageType.INPUTOUTPUT)
    private String PMENSAJE = new String();

    public String getPCML() {
        return PCML;
    }

    public void setPCML(String PCML) {
        this.PCML = PCML;
    }

    public String getSFOPCI() {
        return SFOPCI;
    }

    public void setSFOPCI(String SFOPCI) {
        this.SFOPCI = SFOPCI;
    }

    public String getPCONFI() {
        return PCONFI;
    }

    public void setPCONFI(String PCONFI) {
        this.PCONFI = PCONFI;
    }

    public String getCODEDI_A() {
        return CODEDI_A;
    }

    public void setCODEDI_A(String CODEDI_A) {
        this.CODEDI_A = CODEDI_A;
    }

    public String getDESEDI() {
        return DESEDI;
    }

    public void setDESEDI(String DESEDI) {
        this.DESEDI = DESEDI;
    }

    public String getCODIVI() {
        return CODIVI;
    }

    public void setCODIVI(String CODIVI) {
        this.CODIVI = CODIVI;
    }

    public String getCODCOM() {
        return CODCOM;
    }

    public void setCODCOM(String CODCOM) {
        this.CODCOM = CODCOM;
    }

    public String getCODBAR_A() {
        return CODBAR_A;
    }

    public void setCODBAR_A(String CODBAR_A) {
        this.CODBAR_A = CODBAR_A;
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

    public String getPMENSAJE() {
        return PMENSAJE;
    }

    public void setPMENSAJE(String PMENSAJE) {
        this.PMENSAJE = PMENSAJE;
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
