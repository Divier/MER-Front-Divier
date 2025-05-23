package co.com.claro.cmas400.ejb.pcmlinterface;

import co.com.claro.cmas400.ejb.core.Array;
import co.com.claro.cmas400.ejb.core.Data;
import co.com.claro.cmas400.ejb.core.Program;
import co.com.claro.cmas400.ejb.core.UsageType;
import java.util.ArrayList;

@Program(programName = "CMONEFR0", documentName = "co.com.claro.cmas400.ejb.pcml.CMONEFR0")
public class CMONEFR0 {

    @Data(pcmlName = "EDIFICIO", usage = UsageType.INPUTOUTPUT)
    private String EDIFICIO = new String();
    @Data(pcmlName = "NOTA", usage = UsageType.INPUTOUTPUT)
    private String NOTA = new String();
    @Data(pcmlName = "PNESTA", usage = UsageType.INPUTOUTPUT)
    private String PNESTA = new String();
    @Data(pcmlName = "PNNIED", usage = UsageType.INPUTOUTPUT)
    private String PNNIED = new String();
    @Data(pcmlName = "PNCALL", usage = UsageType.INPUTOUTPUT)
    private String PNCALL = new String();
    @Data(pcmlName = "PNNOTI", usage = UsageType.INPUTOUTPUT)
    private String PNNOTI = new String();
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
    @Array(pcmlName = "TABLARTA", size = 500, type = java.lang.String.class, usage = UsageType.INPUTOUTPUT)
    private ArrayList TABLARTA = new ArrayList();
    @Data(pcmlName = "MENSAJE", usage = UsageType.INPUTOUTPUT)
    private String MENSAJE = new String();

    public String getEDIFICIO() {
        return EDIFICIO;
    }

    public void setEDIFICIO(String EDIFICIO) {
        this.EDIFICIO = EDIFICIO;
    }

    public String getNOTA() {
        return NOTA;
    }

    public void setNOTA(String NOTA) {
        this.NOTA = NOTA;
    }

    public String getPNESTA() {
        return PNESTA;
    }

    public void setPNESTA(String PNESTA) {
        this.PNESTA = PNESTA;
    }

    public String getPNNIED() {
        return PNNIED;
    }

    public void setPNNIED(String PNNIED) {
        this.PNNIED = PNNIED;
    }

    public String getPNCALL() {
        return PNCALL;
    }

    public void setPNCALL(String PNCALL) {
        this.PNCALL = PNCALL;
    }

    public String getPNNOTI() {
        return PNNOTI;
    }

    public void setPNNOTI(String PNNOTI) {
        this.PNNOTI = PNNOTI;
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

    public ArrayList getTABLARTA() {
        return TABLARTA;
    }

    public void setTABLARTA(ArrayList TABLARTA) {
        this.TABLARTA = TABLARTA;
    }

    public String getMENSAJE() {
        return MENSAJE;
    }

    public void setMENSAJE(String MENSAJE) {
        this.MENSAJE = MENSAJE;
    }

}
