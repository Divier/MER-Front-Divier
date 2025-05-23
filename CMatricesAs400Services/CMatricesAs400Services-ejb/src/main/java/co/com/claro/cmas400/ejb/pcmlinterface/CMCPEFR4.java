package co.com.claro.cmas400.ejb.pcmlinterface;

import co.com.claro.cmas400.ejb.core.Data;
import co.com.claro.cmas400.ejb.core.Program;
import co.com.claro.cmas400.ejb.core.UsageType;

@Program(programName = "CMCPEFR4", documentName = "co.com.claro.cmas400.ejb.pcml.CMCPEFR4")
public class CMCPEFR4 {

    @Data(pcmlName = "PCML", usage = UsageType.INPUTOUTPUT)
    private String PCML = new String();
    @Data(pcmlName = "PNTEL1_A", usage = UsageType.INPUTOUTPUT)
    private String PNTEL1_A = new String();
    @Data(pcmlName = "RESULTADO", usage = UsageType.INPUTOUTPUT)
    private String RESULTADO = new String();
    @Data(pcmlName = "MENSAJE", usage = UsageType.INPUTOUTPUT)
    private String MENSAJE = new String();
    @Data(pcmlName = "PRNUED", usage = UsageType.INPUTOUTPUT)
    private String PRNUED = new String();
    @Data(pcmlName = "T_CMCEDFR1", usage = UsageType.INPUTOUTPUT)
    private String T_CMCEDFR1 = new String();

    public String getPCML() {
        return PCML;
    }

    public void setPCML(String PCML) {
        this.PCML = PCML;
    }

    public String getPNTEL1_A() {
        return PNTEL1_A;
    }

    public void setPNTEL1_A(String PNTEL1_A) {
        this.PNTEL1_A = PNTEL1_A;
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

    public String getPRNUED() {
        return PRNUED;
    }

    public void setPRNUED(String PRNUED) {
        this.PRNUED = PRNUED;
    }

    public String getT_CMCEDFR1() {
        return T_CMCEDFR1;
    }

    public void setT_CMCEDFR1(String T_CMCEDFR1) {
        this.T_CMCEDFR1 = T_CMCEDFR1;
    }
}
