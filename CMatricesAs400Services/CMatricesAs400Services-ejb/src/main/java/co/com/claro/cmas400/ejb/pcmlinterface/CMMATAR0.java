package co.com.claro.cmas400.ejb.pcmlinterface;

import co.com.claro.cmas400.ejb.core.Array;
import co.com.claro.cmas400.ejb.core.Data;
import co.com.claro.cmas400.ejb.core.Program;
import co.com.claro.cmas400.ejb.core.UsageType;
import java.util.ArrayList;

@Program(programName = "CMMATAR0", documentName = "co.com.claro.cmas400.ejb.pcml.CMMATAR0")
public class CMMATAR0 {

    @Data(pcmlName = "PACODG", usage = UsageType.INPUTOUTPUT)
    private String PACODG = new String();
    @Data(pcmlName = "PCML", usage = UsageType.INPUTOUTPUT)
    private String PCML = new String();
    @Data(pcmlName = "PNFCOD_A", usage = UsageType.INPUTOUTPUT)
    private String PNFCOD_A = new String();
    @Data(pcmlName = "PNFDES_A", usage = UsageType.INPUTOUTPUT)
    private String PNFDES_A = new String();
    @Data(pcmlName = "PNFTIP_A", usage = UsageType.INPUTOUTPUT)
    private String PNFTIP_A = new String();
    @Data(pcmlName = "NUM_REG", usage = UsageType.INPUTOUTPUT)
    private String NUM_REG = new String();
    @Data(pcmlName = "RESULTADO", usage = UsageType.INPUTOUTPUT)
    private String RESULTADO = new String();
    @Array(pcmlName = "TABLARTA", size = 500, type = java.lang.String.class, usage = UsageType.INPUTOUTPUT)
    private ArrayList TABLARTA = new ArrayList();

    public String getPACODG() {
        return PACODG;
    }

    public void setPACODG(String PACODG) {
        this.PACODG = PACODG;
    }

    public String getPCML() {
        return PCML;
    }

    public void setPCML(String PCML) {
        this.PCML = PCML;
    }

    public String getPNFCOD_A() {
        return PNFCOD_A;
    }

    public void setPNFCOD_A(String PNFCOD_A) {
        this.PNFCOD_A = PNFCOD_A;
    }

    public String getPNFDES_A() {
        return PNFDES_A;
    }

    public void setPNFDES_A(String PNFDES_A) {
        this.PNFDES_A = PNFDES_A;
    }

    public String getPNFTIP_A() {
        return PNFTIP_A;
    }

    public void setPNFTIP_A(String PNFTIP_A) {
        this.PNFTIP_A = PNFTIP_A;
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
