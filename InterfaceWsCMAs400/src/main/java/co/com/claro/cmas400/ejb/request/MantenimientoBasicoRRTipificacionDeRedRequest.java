/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.cmas400.ejb.request;

/**
 *
 * @author Orlando Velasquez
 */
public class MantenimientoBasicoRRTipificacionDeRedRequest {

    private String IDUSER;
    private String TRCODR;
    private String TRDESR;
    private String TRSTAT;

    public String getIDUSER() {
        return IDUSER;
    }

    public void setIDUSER(String IDUSER) {
        this.IDUSER = IDUSER;
    }

    public String getTRCODR() {
        return TRCODR;
    }

    public void setTRCODR(String TRCODR) {
        this.TRCODR = TRCODR;
    }

    public String getTRDESR() {
        return TRDESR;
    }

    public void setTRDESR(String TRDESR) {
        this.TRDESR = TRDESR;
    }

    public String getTRSTAT() {
        return TRSTAT;
    }

    public void setTRSTAT(String TRSTAT) {
        this.TRSTAT = TRSTAT;
    }

}
