package co.com.claro.cmas400.ejb.pcmlinterface;

import co.com.claro.cmas400.ejb.core.Data;
import co.com.claro.cmas400.ejb.core.Program;
import co.com.claro.cmas400.ejb.core.UsageType;

@Program(programName = "CMINFSR0", documentName = "co.com.claro.cmas400.ejb.pcml.CMINFSR0")
public class CMINFSR0 {

    @Data(pcmlName = "PACODG", usage = UsageType.INPUTOUTPUT)
    private String PACODG = new String();
    @Data(pcmlName = "PASEQU", usage = UsageType.INPUTOUTPUT)
    private String PASEQU = new String();
    @Data(pcmlName = "PCML", usage = UsageType.INPUTOUTPUT)
    private String PCML = new String();
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
    @Data(pcmlName = "TABLARTA", usage = UsageType.INPUTOUTPUT)
    private String TABLARTA = new String();
    @Data(pcmlName = "MENSAJE", usage = UsageType.INPUTOUTPUT)
    private String MENSAJE = new String();

    public String getPACODG() {
        return PACODG;
    }

    public void setPACODG(String PACODG) {
        this.PACODG = PACODG;
    }

    public String getPASEQU() {
        return PASEQU;
    }

    public void setPASEQU(String PASEQU) {
        this.PASEQU = PASEQU;
    }

    public String getPCML() {
        return PCML;
    }

    public void setPCML(String PCML) {
        this.PCML = PCML;
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

    public String getTABLARTA() {
        return TABLARTA;
    }

    public void setTABLARTA(String TABLARTA) {
        this.TABLARTA = TABLARTA;
    }

    public String getPUSERID() {
        return PUSERID;
    }

    public void setPUSERID(String PUSERID) {
        this.PUSERID = PUSERID;
    }
    
}
