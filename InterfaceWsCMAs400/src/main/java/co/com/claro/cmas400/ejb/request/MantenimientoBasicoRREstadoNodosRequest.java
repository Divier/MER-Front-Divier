/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.cmas400.ejb.request;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author User
 */
@XmlRootElement
public class MantenimientoBasicoRREstadoNodosRequest {
    
  
    @XmlElement
    private String IDUSER = new String();
    @XmlElement
    private String NDCODEP = new String();
    @XmlElement
    private String NDDESEP = new String();
    @XmlElement
    private String NDSTATP = new String();    

    public MantenimientoBasicoRREstadoNodosRequest() {
    }
    
    public String getIDUSER() {
        return IDUSER;
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
          
}
