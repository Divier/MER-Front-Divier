/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.cmas400.ejb.pcmlinterface;

import co.com.claro.cmas400.ejb.core.Data;
import co.com.claro.cmas400.ejb.core.Program;
import co.com.claro.cmas400.ejb.core.UsageType;


/**
 * Clase usada para la administraci&oacute;n del PCML de Municipios Centros Poblados DANE
 * (Anteriormente <i>INSMUNICI</i>).
 *
 * @author bocanegravm
 */
@Program(programName = "CLMUNIR0", documentName = "co.com.claro.cmas400.ejb.pcml.CLMUNIR0")
public class CLMUNIR0 {
    
    @Data(pcmlName = "IDPRC" , usage = UsageType.INPUTOUTPUT)
    private String IDPRC = new String();
    @Data(pcmlName = "IDUSER" , usage = UsageType.INPUTOUTPUT)
    private String IDUSER = new String();
    @Data(pcmlName = "CDDANE" , usage = UsageType.INPUTOUTPUT)
    private String CDDANE = new String();
    @Data(pcmlName = "DSPOBLD" , usage = UsageType.INPUTOUTPUT)
    private String DSPOBLD = new String();
    @Data(pcmlName = "CDDEPTO" , usage = UsageType.INPUTOUTPUT)
    private String CDDEPTO = new String();
    @Data(pcmlName = "DSDEPTO" , usage = UsageType.INPUTOUTPUT)
    private String DSDEPTO = new String();
    @Data(pcmlName = "CDMPIO" , usage = UsageType.INPUTOUTPUT)
    private String CDMPIO = new String();
    @Data(pcmlName = "DSMPIO" , usage = UsageType.INPUTOUTPUT)
    private String DSMPIO = new String();
    @Data(pcmlName = "CDCLASE" , usage = UsageType.INPUTOUTPUT)
    private String CDCLASE = new String();
    @Data(pcmlName = "NUMMANZ" , usage = UsageType.INPUTOUTPUT)
    private String NUMMANZ = new String();
    @Data(pcmlName = "ANOCREA" , usage = UsageType.INPUTOUTPUT)
    private String ANOCREA = new String();
    @Data(pcmlName = "ESTVIG" , usage = UsageType.INPUTOUTPUT)
    private String ESTVIG = new String();
    @Data(pcmlName = "IDENDM" , usage = UsageType.INPUTOUTPUT)
    private String IDENDM = new String();
    @Data(pcmlName = "ENDMSG", usage = UsageType.INPUTOUTPUT)
    private String ENDMSG = new String();

    public String getIDPRC() {
        return IDPRC;
    }

    public void setIDPRC(String IDPRC) {
        this.IDPRC = IDPRC;
    }

    public String getIDUSER() {
        return IDUSER;
    }

    public void setIDUSER(String IDUSER) {
        this.IDUSER = IDUSER;
    }

    public String getCDDANE() {
        return CDDANE;
    }

    public void setCDDANE(String CDDANE) {
        this.CDDANE = CDDANE;
    }

    public String getDSPOBLD() {
        return DSPOBLD;
    }

    public void setDSPOBLD(String DSPOBLD) {
        this.DSPOBLD = DSPOBLD;
    }

    public String getCDDEPTO() {
        return CDDEPTO;
    }

    public void setCDDEPTO(String CDDEPTO) {
        this.CDDEPTO = CDDEPTO;
    }

    public String getDSDEPTO() {
        return DSDEPTO;
    }

    public void setDSDEPTO(String DSDEPTO) {
        this.DSDEPTO = DSDEPTO;
    }

    public String getCDMPIO() {
        return CDMPIO;
    }

    public void setCDMPIO(String CDMPIO) {
        this.CDMPIO = CDMPIO;
    }

    public String getDSMPIO() {
        return DSMPIO;
    }

    public void setDSMPIO(String DSMPIO) {
        this.DSMPIO = DSMPIO;
    }

    public String getCDCLASE() {
        return CDCLASE;
    }

    public void setCDCLASE(String CDCLASE) {
        this.CDCLASE = CDCLASE;
    }

    public String getNUMMANZ() {
        return NUMMANZ;
    }

    public void setNUMMANZ(String NUMMANZ) {
        this.NUMMANZ = NUMMANZ;
    }

    public String getANOCREA() {
        return ANOCREA;
    }

    public void setANOCREA(String ANOCREA) {
        this.ANOCREA = ANOCREA;
    }

    public String getESTVIG() {
        return ESTVIG;
    }

    public void setESTVIG(String ESTVIG) {
        this.ESTVIG = ESTVIG;
    }

    public String getIDENDM() {
        return IDENDM;
    }

    public void setIDENDM(String IDENDM) {
        this.IDENDM = IDENDM;
    }

    public String getENDMSG() {
        return ENDMSG;
    }

    public void setENDMSG(String ENDMSG) {
        this.ENDMSG = ENDMSG;
    }
    
    
}
