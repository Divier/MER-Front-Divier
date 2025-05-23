
package co.com.claro.cmas400.ejb.pcmlinterface;

import co.com.claro.cmas400.ejb.core.Array;
import co.com.claro.cmas400.ejb.core.Data;
import co.com.claro.cmas400.ejb.core.Program;
import co.com.claro.cmas400.ejb.core.UsageType;
import java.util.ArrayList;

@Program(programName = "CMAINFR0", documentName = "co.com.claro.cmas400.ejb.pcml.CMAINFR0")
public class CMAINFR0 {

    @Data(pcmlName = "PNFITM", usage = UsageType.INPUTOUTPUT)
    private String PNFITM = new String();
    @Data(pcmlName = "PNFMAN", usage = UsageType.INPUTOUTPUT)
    private String PNFMAN = new String();
    @Data(pcmlName = "PNFSER", usage = UsageType.INPUTOUTPUT)
    private String PNFSER = new String();
    @Data(pcmlName = "PCML", usage = UsageType.INPUTOUTPUT)
    private String PCML = new String();
    @Data(pcmlName = "NUM_REG", usage = UsageType.INPUTOUTPUT)
    private String NUM_REG = new String();
    @Data(pcmlName = "RESULTADO", usage = UsageType.INPUTOUTPUT)
    private String RESULTADO = new String();
    @Array(pcmlName = "TABLARTA", size = 3000, type = java.lang.String.class, usage = UsageType.INPUTOUTPUT)
    private ArrayList TABLARTA = new ArrayList();
    @Data(pcmlName = "MENSAJE", usage = UsageType.INPUTOUTPUT)
    private String MENSAJE = new String();

    public String getPNFITM() {
        return PNFITM;
    }

    public void setPNFITM(String PNFITM) {
        this.PNFITM = PNFITM;
    }

    public String getPNFMAN() {
        return PNFMAN;
    }

    public void setPNFMAN(String PNFMAN) {
        this.PNFMAN = PNFMAN;
    }

    public String getPNFSER() {
        return PNFSER;
    }

    public void setPNFSER(String PNFSER) {
        this.PNFSER = PNFSER;
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
