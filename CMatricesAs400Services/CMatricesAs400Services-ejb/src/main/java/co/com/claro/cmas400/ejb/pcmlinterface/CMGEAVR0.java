
package co.com.claro.cmas400.ejb.pcmlinterface;

import co.com.claro.cmas400.ejb.core.Array;
import co.com.claro.cmas400.ejb.core.Data;
import co.com.claro.cmas400.ejb.core.Program;
import co.com.claro.cmas400.ejb.core.UsageType;
import java.util.ArrayList;

@Program(programName = "CMGEAVR0", documentName = "co.com.claro.cmas400.ejb.pcml.CMGEAVR0")
public class CMGEAVR0 {

    @Data(pcmlName = "PCML", usage = UsageType.INPUTOUTPUT)
    private String PCML = new String();
    @Data(pcmlName = "PNFCOD_A", usage = UsageType.INPUTOUTPUT)
    private String PNFCOD_A = new String();
    @Data(pcmlName = "PNFCOM_A", usage = UsageType.INPUTOUTPUT)
    private String PNFCOM_A = new String();
    @Data(pcmlName = "PNFASE_A", usage = UsageType.INPUTOUTPUT)
    private String PNFASE_A = new String();
    @Data(pcmlName = "PNFNOM_A", usage = UsageType.INPUTOUTPUT)
    private String PNFNOM_A = new String();
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
    @Array(pcmlName = "TABLARTA", size = 950, type = java.lang.String.class, usage = UsageType.INPUTOUTPUT)
    private ArrayList TABLARTA = new ArrayList();
    @Data(pcmlName = "MENSAJE", usage = UsageType.INPUTOUTPUT)
    private String MENSAJE = new String();

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

    public String getPNFCOM_A() {
        return PNFCOM_A;
    }

    public void setPNFCOM_A(String PNFCOM_A) {
        this.PNFCOM_A = PNFCOM_A;
    }

    public String getPNFASE_A() {
        return PNFASE_A;
    }

    public void setPNFASE_A(String PNFASE_A) {
        this.PNFASE_A = PNFASE_A;
    }

    public String getPNFNOM_A() {
        return PNFNOM_A;
    }

    public void setPNFNOM_A(String PNFNOM_A) {
        this.PNFNOM_A = PNFNOM_A;
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

    public String getPUSERID() {
        return PUSERID;
    }

    public void setPUSERID(String PUSERID) {
        this.PUSERID = PUSERID;
    }

}
