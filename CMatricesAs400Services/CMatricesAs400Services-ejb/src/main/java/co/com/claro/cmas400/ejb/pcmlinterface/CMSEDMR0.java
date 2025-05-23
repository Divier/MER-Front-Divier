package co.com.claro.cmas400.ejb.pcmlinterface;

import co.com.claro.cmas400.ejb.core.Array;
import co.com.claro.cmas400.ejb.core.Data;
import co.com.claro.cmas400.ejb.core.Program;
import co.com.claro.cmas400.ejb.core.UsageType;
import java.util.ArrayList;

@Program(programName = "CMSEDMR0", documentName = "co.com.claro.cmas400.ejb.pcml.CMSEDMR0")
public class CMSEDMR0 {

    @Data(pcmlName = "CUENTA", usage = UsageType.INPUTOUTPUT)
    private String CUENTA = new String();
    @Data(pcmlName = "PCML", usage = UsageType.INPUTOUTPUT)
    private String PCML = new String();
    @Data(pcmlName = "FUNCION", usage = UsageType.INPUTOUTPUT)
    private String FUNCION = new String();
    @Data(pcmlName = "PCONFI", usage = UsageType.INPUTOUTPUT)
    private String PCONFI = new String();
    @Data(pcmlName = "PUSERID", usage = UsageType.INPUTOUTPUT)
    private String PUSERID = new String();
    @Data(pcmlName = "NUM_REG", usage = UsageType.INPUTOUTPUT)
    private String NUM_REG = new String();
    @Data(pcmlName = "RESULTADO", usage = UsageType.INPUTOUTPUT)
    private String RESULTADO = new String();
    @Array(pcmlName = "TABLARTA", size = 250, type = java.lang.String.class, usage = UsageType.INPUTOUTPUT)
    private ArrayList TABLARTA = new ArrayList();
    @Data(pcmlName = "MENSAJE", usage = UsageType.INPUTOUTPUT)
    private String MENSAJE = new String();

    public String getPUSERID() {
        return PUSERID;
    }

    public void setPUSERID(String PUSERID) {
        this.PUSERID = PUSERID;
    }

    public String getCUENTA() {
        return CUENTA;
    }

    public void setCUENTA(String CUENTA) {
        this.CUENTA = CUENTA;
    }

    public String getPCML() {
        return PCML;
    }

    public void setPCML(String PCML) {
        this.PCML = PCML;
    }

    public String getFUNCION() {
        return FUNCION;
    }

    public void setFUNCION(String FUNCION) {
        this.FUNCION = FUNCION;
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
}
