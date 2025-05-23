package co.com.claro.cmas400.ejb.pcmlinterface;

import co.com.claro.cmas400.ejb.core.Data;
import co.com.claro.cmas400.ejb.core.Program;
import co.com.claro.cmas400.ejb.core.UsageType;

@Program(programName = "CMCPEFR1", documentName = "co.com.claro.cmas400.ejb.pcml.CMCPEFR1")
public class CMCPEFR1 {
    
    @Data(pcmlName = "PCML", usage = UsageType.INPUTOUTPUT)
    private String PCML = new String();
    @Data(pcmlName = "PNNUED_CHR", usage = UsageType.INPUTOUTPUT)
    private String PNNUED_CHR = new String();
    @Data(pcmlName = "RESULTADO", usage = UsageType.INPUTOUTPUT)
    private String RESULTADO = new String();
    @Data(pcmlName = "T_CMCEDFR1", usage = UsageType.INPUTOUTPUT)
    private String T_CMCEDFR1 = new String();
    @Data(pcmlName = "MENSAJE", usage = UsageType.INPUTOUTPUT)
    private String MENSAJE = new String();

    public String getPCML() {
        return PCML;
    }

    public void setPCML(String PCML) {
        this.PCML = PCML;
    }

    public String getPNNUED_CHR() {
        return PNNUED_CHR;
    }

    public void setPNNUED_CHR(String PNNUED_CHR) {
        this.PNNUED_CHR = PNNUED_CHR;
    }

    public String getRESULTADO() {
        return RESULTADO;
    }

    public void setRESULTADO(String RESULTADO) {
        this.RESULTADO = RESULTADO;
    }

    public String getT_CMCEDFR1() {
        return T_CMCEDFR1;
    }

    public void setT_CMCEDFR1(String T_CMCEDFR1) {
        this.T_CMCEDFR1 = T_CMCEDFR1;
    }

    public String getMENSAJE() {
        return MENSAJE;
    }

    public void setMENSAJE(String MENSAJE) {
        this.MENSAJE = MENSAJE;
    }
}
