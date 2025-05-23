/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.cmas400.ejb.respons;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author User
 */
@XmlRootElement
public class MantenimientoBasicoRREstadoNodosResponse {
    
   
     @XmlElement
      private String IDUSER;
     @XmlElement
      private String NDCODEP;
     @XmlElement
      private String NDDESEP; 
     @XmlElement
      private String NDSTATP;
     @XmlElement
      private String IDENDM;
     @XmlElement
      private String ENDMSG;

    public MantenimientoBasicoRREstadoNodosResponse() {
    }

    public void setIDUSER(String IDUSER) {
        this.IDUSER = IDUSER;
    }

    public String getNDCODEP() {
        return NDCODEP;
    }

    public void setNDCODEP(String NDCODEP) {
        this.NDCODEP = NDCODEP;
    }

    public String getNDDESEP() {
        return NDDESEP;
    }

    public void setNDDESEP(String NDDESEP) {
        this.NDDESEP = NDDESEP;
    }

    public String getNDSTATP() {
        return NDSTATP;
    }

    public void setNDSTATP(String NDSTATP) {
        this.NDSTATP = NDSTATP;
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
