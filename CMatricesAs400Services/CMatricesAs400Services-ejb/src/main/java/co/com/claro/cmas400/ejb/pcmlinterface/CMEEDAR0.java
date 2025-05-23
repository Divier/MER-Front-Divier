package co.com.claro.cmas400.ejb.pcmlinterface;

import co.com.claro.cmas400.ejb.core.Array;
import co.com.claro.cmas400.ejb.core.Data;
import co.com.claro.cmas400.ejb.core.Program;
import co.com.claro.cmas400.ejb.core.UsageType;
import java.util.ArrayList;

@Program(programName = "CMEEDAR0", documentName = "co.com.claro.cmas400.ejb.pcml.CMEEDAR0")
public class CMEEDAR0 {

    @Data(pcmlName = "ENTRA", usage = UsageType.INPUTOUTPUT)
    private String ENTRA = new String();
    @Data(pcmlName = "PCML", usage = UsageType.INPUTOUTPUT)
    private String PCML = new String();
    @Data(pcmlName = "NUM_REG", usage = UsageType.INPUTOUTPUT)
    private String NUM_REG = new String();
    @Data(pcmlName = "RESULTADO", usage = UsageType.INPUTOUTPUT)
    private String RESULTADO = new String();
    @Array(pcmlName = "TABLA", size = 500, type = java.lang.String.class, usage = UsageType.INPUTOUTPUT)
    private ArrayList TABLA = new ArrayList();

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

    public String getENTRA() {
        return ENTRA;
    }

    public void setENTRA(String ENTRA) {
        this.ENTRA = ENTRA;
    }

    public ArrayList getTABLA() {
        return TABLA;
    }

    public void setTABLA(ArrayList TABLA) {
        this.TABLA = TABLA;
    }
}
