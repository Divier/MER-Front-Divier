
package co.com.claro.cmas400.ejb.pcmlinterface;

import co.com.claro.cmas400.ejb.core.Array;
import co.com.claro.cmas400.ejb.core.Data;
import co.com.claro.cmas400.ejb.core.Program;
import co.com.claro.cmas400.ejb.core.UsageType;
import java.util.ArrayList;

@Program(programName = "CMDEAAR0", documentName = "co.com.claro.cmas400.ejb.pcml.CMDEAAR0")
public class CMDEAAR0 {

    @Data(pcmlName = "ENTRA", usage = UsageType.INPUTOUTPUT)
    private String ENTRA = new String();
    @Data(pcmlName = "BUSNOM", usage = UsageType.INPUTOUTPUT)
    private String BUSNOM = new String();
    @Data(pcmlName = "PCML", usage = UsageType.INPUTOUTPUT)
    private String PCML = new String();
    @Data(pcmlName = "NUM_REG", usage = UsageType.INPUTOUTPUT)
    private String NUM_REG = new String();
    @Data(pcmlName = "RESULTADO", usage = UsageType.INPUTOUTPUT)
    private String RESULTADO = new String();
    @Array(pcmlName = "TABLARTA", size = 1400, type = java.lang.String.class, usage = UsageType.INPUTOUTPUT)
    private ArrayList TABLARTA = new ArrayList();

    public String getENTRA() {
        return ENTRA;
    }

    public void setENTRA(String ENTRA) {
        this.ENTRA = ENTRA;
    }

    public String getBUSNOM() {
        return BUSNOM;
    }

    public void setBUSNOM(String BUSNOM) {
        this.BUSNOM = BUSNOM;
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

    public ArrayList getTABLARTA() {
        return TABLARTA;
    }

    public void setTABLARTA(ArrayList TABLARTA) {
        this.TABLARTA = TABLARTA;
    }
    
}
