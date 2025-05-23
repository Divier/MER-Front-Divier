package co.com.claro.cmas400.ejb.pcmlinterface;

import co.com.claro.cmas400.ejb.core.Data;
import co.com.claro.cmas400.ejb.core.Program;
import co.com.claro.cmas400.ejb.core.UsageType;

@Program(programName = "CMCPEFR2", documentName = "co.com.claro.cmas400.ejb.pcml.CMCPEFR2")
public class CMCPEFR2 {

@Data(pcmlName = "PCML", usage = UsageType.INPUTOUTPUT)
    private String PCML = new String();
    @Data(pcmlName = "PNNCAL", usage = UsageType.INPUTOUTPUT)
    private String PNNCAL = new String();
    @Data(pcmlName = "PNCOMU", usage = UsageType.INPUTOUTPUT)
    private String PNCOMU = new String();
    @Data(pcmlName = "PNDIVI", usage = UsageType.INPUTOUTPUT)
    private String PNDIVI = new String();
    @Data(pcmlName = "PNHOME", usage = UsageType.INPUTOUTPUT)
    private String PNHOME = new String();
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

    public String getPNNCAL() {
        return PNNCAL;
    }

    public void setPNNCAL(String PNNCAL) {
        this.PNNCAL = PNNCAL;
    }

    public String getPNCOMU() {
        return PNCOMU;
    }

    public void setPNCOMU(String PNCOMU) {
        this.PNCOMU = PNCOMU;
    }

    public String getPNDIVI() {
        return PNDIVI;
    }

    public void setPNDIVI(String PNDIVI) {
        this.PNDIVI = PNDIVI;
    }

    public String getPNHOME() {
        return PNHOME;
    }

    public void setPNHOME(String PNHOME) {
        this.PNHOME = PNHOME;
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
