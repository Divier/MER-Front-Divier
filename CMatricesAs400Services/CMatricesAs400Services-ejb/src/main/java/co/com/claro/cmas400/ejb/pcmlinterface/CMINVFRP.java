package co.com.claro.cmas400.ejb.pcmlinterface;

import co.com.claro.cmas400.ejb.core.Array;
import co.com.claro.cmas400.ejb.core.Data;
import co.com.claro.cmas400.ejb.core.Program;
import co.com.claro.cmas400.ejb.core.UsageType;
import java.util.ArrayList;

@Program(programName = "CMINVFRP", documentName = "co.com.claro.cmas400.ejb.pcml.CMINVFRP")
public class CMINVFRP {

    @Data(pcmlName = "PAEDIF", usage = UsageType.INPUTOUTPUT)
    private String PAEDIF = new String();
    @Data(pcmlName = "PNESTA", usage = UsageType.INPUTOUTPUT)
    private String PNESTA = new String();
    @Data(pcmlName = "PNNIED", usage = UsageType.INPUTOUTPUT)
    private String PNNIED = new String();
    @Data(pcmlName = "PNCALL", usage = UsageType.INPUTOUTPUT)
    private String PNCALL = new String();
    @Data(pcmlName = "XNCCLL", usage = UsageType.INPUTOUTPUT)
    private String XNCCLL = new String();
    @Data(pcmlName = "PNNOTI", usage = UsageType.INPUTOUTPUT)
    private String PNNOTI = new String();
    @Data(pcmlName = "PNHOME", usage = UsageType.INPUTOUTPUT)
    private String PNHOME = new String();
    @Data(pcmlName = "PMSJTYP", usage = UsageType.INPUTOUTPUT)
    private String PMSJTYP = new String();
    @Data(pcmlName = "PMSJERR", usage = UsageType.INPUTOUTPUT)
    private String PMSJERR = new String();
    @Array(pcmlName = "WRESPTA", size = 500, type = java.lang.String.class, usage = UsageType.INPUTOUTPUT)
    private ArrayList WRESPTA = new ArrayList();

    public String getPAEDIF() {
        return PAEDIF;
    }

    public void setPAEDIF(String PAEDIF) {
        this.PAEDIF = PAEDIF;
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
        this.PAEDIF = PNNIED;
    }

    public String getPNCALL() {
        return PNCALL;
    }

    public void setPNCALL(String PNCALL) {
        this.PNCALL = PNCALL;
    }

    public String getXNCCLL() {
        return XNCCLL;
    }

    public void setXNCCLL(String XNCCLL) {
        this.XNCCLL = XNCCLL;
    }

    public String getPNNOTI() {
        return PNNOTI;
    }

    public void setPNNOTI(String PNNOTI) {
        this.PNNOTI = PNNOTI;
    }

    public String getPNHOME() {
        return PNHOME;
    }

    public void setPNHOME(String PNHOME) {
        this.PNHOME = PNHOME;
    }

    public String getPMSJTYP() {
        return PMSJTYP;
    }

    public void setPMSJTYP(String PMSJTYP) {
        this.PMSJTYP = PMSJTYP;
    }

    public String getPMSJERR() {
        return PMSJERR;
    }

    public void setPMSJERR(String PMSJERR) {
        this.PMSJERR = PMSJERR;
    }

    public ArrayList getWRESPTA() {
        return WRESPTA;
    }

    public void setWRESPTA(ArrayList WRESPTA) {
        this.WRESPTA = WRESPTA;
    }
}
