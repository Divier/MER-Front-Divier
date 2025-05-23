package co.com.claro.cmas400.ejb.pcmlinterface;

import co.com.claro.cmas400.ejb.core.Array;
import co.com.claro.cmas400.ejb.core.Data;
import co.com.claro.cmas400.ejb.core.Program;
import co.com.claro.cmas400.ejb.core.UsageType;
import java.util.ArrayList;

@Program(programName = "CMEDFAR0", documentName = "co.com.claro.cmas400.ejb.pcml.CMEDFAR0")
public class CMEDFAR0 {

    @Data(pcmlName = "PACODG", usage = UsageType.INPUTOUTPUT)
    private String PACODG = new String();
    @Data(pcmlName = "PANOMB", usage = UsageType.INPUTOUTPUT)
    private String PANOMB = new String();
    @Data(pcmlName = "PNFCOD", usage = UsageType.INPUTOUTPUT)
    private String PNFCOD = new String();
    @Data(pcmlName = "PNFCOM", usage = UsageType.INPUTOUTPUT)
    private String PNFCOM = new String();
    @Data(pcmlName = "PCML", usage = UsageType.INPUTOUTPUT)
    private String PCML = new String();
    @Data(pcmlName = "NUM_REG", usage = UsageType.INPUTOUTPUT)
    private String NUM_REG = new String();
    @Data(pcmlName = "RESULTADO", usage = UsageType.INPUTOUTPUT)
    private String RESULTADO = new String();
    @Array(pcmlName = "TABLARTA", size = 1000, type = java.lang.String.class, usage = UsageType.INPUTOUTPUT)
    private ArrayList TABLARTA = new ArrayList();

    public String getPACODG() {
        return PACODG;
    }

    public void setPACODG(String PACODG) {
        this.PACODG = PACODG;
    }

    public String getPANOMB() {
        return PANOMB;
    }

    public void setPANOMB(String PANOMB) {
        this.PANOMB = PANOMB;
    }

    public String getPNFCOD() {
        return PNFCOD;
    }

    public void setPNFCOD(String PNFCOD) {
        this.PNFCOD = PNFCOD;
    }

    public String getPNFCOM() {
        return PNFCOM;
    }

    public void setPNFCOM(String PNFCOM) {
        this.PNFCOM = PNFCOM;
    }

    public String getPCML() {
        return PCML;
    }

    public void setPCML(String PCML) {
        this.PCML = PCML;
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

    public ArrayList getTABLARTA() {
        return TABLARTA;
    }

    public void setTABLARTA(ArrayList TABLARTA) {
        this.TABLARTA = TABLARTA;
    }
}
