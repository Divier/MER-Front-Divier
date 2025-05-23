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
 * Clase para el manejo de PCML asociado al Mantenimiento de Aliados 
 * (Anteriormente <i>INSALIADO</i>).
 * 
 * @author bocanegravm
 */
@Program(programName = "CLALIIR0", documentName = "co.com.claro.cmas400.ejb.pcml.CLALIIR0")
public class CLALIIR0 {

    @Data(pcmlName = "IDPRC", usage = UsageType.INPUTOUTPUT)
    private String IDPRC = new String();
    @Data(pcmlName = "IDUSER", usage = UsageType.INPUTOUTPUT)
    private String IDUSER = new String();
    @Data(pcmlName = "CODIGO", usage = UsageType.INPUTOUTPUT)
    private String CODIGO = new String();
    @Data(pcmlName = "DESCRIP", usage = UsageType.INPUTOUTPUT)
    private String DESCRIP = new String();
    @Data(pcmlName = "ESTADO", usage = UsageType.INPUTOUTPUT)
    private String ESTADO = new String();
    @Data(pcmlName = "IDENDM", usage = UsageType.INPUTOUTPUT)
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

    public String getCODIGO() {
        return CODIGO;
    }

    public void setCODIGO(String CODIGO) {
        this.CODIGO = CODIGO;
    }

    public String getDESCRIP() {
        return DESCRIP;
    }

    public void setDESCRIP(String DESCRIP) {
        this.DESCRIP = DESCRIP;
    }

    public String getESTADO() {
        return ESTADO;
    }

    public void setESTADO(String ESTADO) {
        this.ESTADO = ESTADO;
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
