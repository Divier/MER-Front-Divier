package co.com.claro.cmas400.ejb.pcmlinterface;

import co.com.claro.cmas400.ejb.core.Array;
import co.com.claro.cmas400.ejb.core.Data;
import co.com.claro.cmas400.ejb.core.Program;
import co.com.claro.cmas400.ejb.core.UsageType;
import java.util.ArrayList;

@Program(programName = "CMMATER0", documentName = "co.com.claro.cmas400.ejb.pcml.CMMATER0")
public class CMMATER0 {

    @Data(pcmlName = "PCML", usage = UsageType.INPUTOUTPUT)
    private String PCML = new String();
    @Data(pcmlName = "PNFCOD_A", usage = UsageType.INPUTOUTPUT)
    private String PNFCOD_A = new String();
    @Data(pcmlName = "PNFDES_A", usage = UsageType.INPUTOUTPUT)
    private String PNFDES_A = new String();
    @Data(pcmlName = "PNFTIP_A", usage = UsageType.INPUTOUTPUT)
    private String PNFTIP_A = new String();
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
    @Array(pcmlName = "TABLARTA", size = 350, type = java.lang.String.class, usage = UsageType.INPUTOUTPUT)
    private ArrayList TABLARTA = new ArrayList();

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

    public String getPNFTIP_A() {
        return PNFTIP_A;
    }

    public void setPNFTIP_A(String PNFTIP_A) {
        this.PNFTIP_A = PNFTIP_A;
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

    public ArrayList getTABLARTA() {
        return TABLARTA;
    }

    public void setTABLARTA(ArrayList TABLARTA) {
        this.TABLARTA = TABLARTA;
    }

    public String getPNFDES_A() {
        return PNFDES_A;
    }

    public void setPNFDES_A(String PNFDES_A) {
        this.PNFDES_A = PNFDES_A;
    }

    public String getPUSERID() {
        return PUSERID;
    }

    public void setPUSERID(String PUSERID) {
        this.PUSERID = PUSERID;
    }
    
}
