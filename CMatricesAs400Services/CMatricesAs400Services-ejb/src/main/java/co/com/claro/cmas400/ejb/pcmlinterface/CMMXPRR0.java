package co.com.claro.cmas400.ejb.pcmlinterface;

import co.com.claro.cmas400.ejb.core.Array;
import co.com.claro.cmas400.ejb.core.Data;
import co.com.claro.cmas400.ejb.core.Program;
import co.com.claro.cmas400.ejb.core.UsageType;
import java.util.ArrayList;

@Program(programName = "CMMXPRR0", documentName = "co.com.claro.cmas400.ejb.pcml.CMMXPRR0")
public class CMMXPRR0 {

    @Data(pcmlName = "PCML", usage = UsageType.INPUTOUTPUT)
    private String PCML = new String();
    @Data(pcmlName = "PNFPRO_A", usage = UsageType.INPUTOUTPUT)
    private String PNFPRO_A = new String();
    @Data(pcmlName = "PNFMAT_A", usage = UsageType.INPUTOUTPUT)
    private String PNFMAT_A = new String();
    @Data(pcmlName = "PNFFEC_A", usage = UsageType.INPUTOUTPUT)
    private String PNFFEC_A = new String();
    @Data(pcmlName = "PFUNCI", usage = UsageType.INPUTOUTPUT)
    private String PFUNCI = new String();
    @Data(pcmlName = "PCONFI", usage = UsageType.INPUTOUTPUT)
    private String PCONFI = new String();
    @Data(pcmlName = "PUSERID", usage = UsageType.INPUTOUTPUT)
    private String PUSERID = new String();
    @Data(pcmlName = "NUM_REG", usage = UsageType.INPUTOUTPUT)
    private String NUM_REG = new String();
    @Data(pcmlName = "RESULTADO", usage = UsageType.INPUTOUTPUT)
    private String RESULTADO = new String();
    @Data(pcmlName = "MENSAJE", usage = UsageType.INPUTOUTPUT)
    private String MENSAJE = new String();
    @Array(pcmlName = "TABLARTA", size = 250, type = java.lang.String.class, usage = UsageType.INPUTOUTPUT)
    private ArrayList TABLARTA = new ArrayList();

    public String getPCML() {
        return PCML;
    }

    public void setPCML(String PCML) {
        this.PCML = PCML;
    }

    public String getPNFPRO_A() {
        return PNFPRO_A;
    }

    public void setPNFPRO_A(String PNFPRO_A) {
        this.PNFPRO_A = PNFPRO_A;
    }

    public String getPNFMAT_A() {
        return PNFMAT_A;
    }

    public void setPNFMAT_A(String PNFMAT_A) {
        this.PNFMAT_A = PNFMAT_A;
    }

    public String getPNFFEC_A() {
        return PNFFEC_A;
    }

    public void setPNFFEC_A(String PNFFEC_A) {
        this.PNFFEC_A = PNFFEC_A;
    }

    public String getPFUNCI() {
        return PFUNCI;
    }

    public void setPFUNCI(String PFUNCI) {
        this.PFUNCI = PFUNCI;
    }

    public String getPCONFI() {
        return PCONFI;
    }

    public void setPCONFI(String PCONFI) {
        this.PCONFI = PCONFI;
    }

    public String getPUSERID() {
        return PUSERID;
    }

    public void setPUSERID(String PUSERID) {
        this.PUSERID = PUSERID;
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
}
