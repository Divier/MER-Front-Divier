/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.cmas400.ejb.respons;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author aleal
 */
public class HhppPaginationResponse{
    
    private List<HhppId> arrunky;
    private String idendm;
    private String endmsg;
    
    public HhppPaginationResponse(){
        arrunky = new ArrayList<HhppId>();
    }
    
    public List<HhppId> getArrunky() {
        return arrunky;
    }

    public void setArrunky(List<HhppId> arrunky) {
        this.arrunky = arrunky;
    }

    public String getIdendm() {
        return idendm;
    }

    public void setIdendm(String idendm) {
        this.idendm = idendm;
    }

    public String getEndmsg() {
        return endmsg;
    }

    public void setEndmsg(String endmsg) {
        this.endmsg = endmsg;
    }
    
    
}
