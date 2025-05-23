package co.com.claro.cmas400.ejb.pcmlinterface;

import co.com.claro.cmas400.ejb.core.Data;
import co.com.claro.cmas400.ejb.core.Program;
import co.com.claro.cmas400.ejb.core.UsageType;

@Program(programName = "CMCPEFR3", documentName = "co.com.claro.cmas400.ejb.pcml.CMCPEFR3")
public class CMCPEFR3 {

    @Data(pcmlName = "PCML", usage = UsageType.INPUTOUTPUT)
    private String PCML = new String();
    @Data(pcmlName = "PNITMC", usage = UsageType.INPUTOUTPUT)
    private String PNITMC = new String();
    @Data(pcmlName = "PNMANC", usage = UsageType.INPUTOUTPUT)
    private String PNMANC = new String();
    @Data(pcmlName = "PNSERI", usage = UsageType.INPUTOUTPUT)
    private String PNSERI = new String();
    @Data(pcmlName = "RESULTADO", usage = UsageType.INPUTOUTPUT)
    private String RESULTADO = new String();
    @Data(pcmlName = "PRNUED", usage = UsageType.INPUTOUTPUT)
    private String PRNUED = new String();
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

    public String getPNITMC() {
        return PNITMC;
    }

    public void setPNITMC(String PNITMC) {
        this.PNITMC = PNITMC;
    }

    public String getPNMANC() {
        return PNMANC;
    }

    public void setPNMANC(String PNMANC) {
        this.PNMANC = PNMANC;
    }

    public String getPNSERI() {
        return PNSERI;
    }

    public void setPNSERI(String PNSERI) {
        this.PNSERI = PNSERI;
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
