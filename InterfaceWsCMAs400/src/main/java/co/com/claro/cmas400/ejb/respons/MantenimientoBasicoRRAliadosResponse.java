/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.cmas400.ejb.respons;

import java.io.Serializable;

/**
 *
 * @author Orlando Velasquez
 */
public class MantenimientoBasicoRRAliadosResponse implements Serializable {

    private String IDUSER;
    private String CODIGO;
    private String DESCRIP;
    private String ESTADO;
    private String IDENDM;
    private String ENDMSG;

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
