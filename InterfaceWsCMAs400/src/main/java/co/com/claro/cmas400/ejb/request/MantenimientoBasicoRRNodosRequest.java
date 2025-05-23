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
 * @author Felipe Garcia
 */
@XmlRootElement
public class MantenimientoBasicoRRNodosRequest {    
    
     @XmlElement
      private String IDUSER;
     @XmlElement
      private String CODNOD;
     @XmlElement
      private String CODNOM;
     @XmlElement
      private String CODALI;
     @XmlElement
      private String CODDIV;
     @XmlElement
      private String CODDVS;
     @XmlElement
      private String CODARE;
     @XmlElement
      private String CODZON;
     @XmlElement
      private String CODCOM;
     @XmlElement
      private String CODDIS;
     @XmlElement
      private String CODUNI;
     @XmlElement
      private String TIPRED;
     @XmlElement
      private String EDONOD;
      

    public MantenimientoBasicoRRNodosRequest() {
    }

    public String getIDUSER() {
        return IDUSER;
    }

    public void setIDUSER(String IDUSER) {
        this.IDUSER = IDUSER;
    }

    public String getCODNOD() {
        return CODNOD;
    }

    public void setCODNOD(String CODNOD) {
        this.CODNOD = CODNOD;
    }

    public String getCODNOM() {
        return CODNOM;
    }

    public void setCODNOM(String CODNOM) {
        this.CODNOM = CODNOM;
    }

    public String getCODALI() {
        return CODALI;
    }

    public void setCODALI(String CODALI) {
        this.CODALI = CODALI;
    }

    public String getCODDIV() {
        return CODDIV;
    }

    public void setCODDIV(String CODDIV) {
        this.CODDIV = CODDIV;
    }

    public String getCODDVS() {
        return CODDVS;
    }

    public void setCODDVS(String CODDVS) {
        this.CODDVS = CODDVS;
    }

    public String getCODARE() {
        return CODARE;
    }

    public void setCODARE(String CODARE) {
        this.CODARE = CODARE;
    }

    public String getCODZON() {
        return CODZON;
    }

    public void setCODZON(String CODZON) {
        this.CODZON = CODZON;
    }

    public String getCODCOM() {
        return CODCOM;
    }

    public void setCODCOM(String CODCOM) {
        this.CODCOM = CODCOM;
    }

    public String getCODDIS() {
        return CODDIS;
    }

    public void setCODDIS(String CODDIS) {
        this.CODDIS = CODDIS;
    }

    public String getCODUNI() {
        return CODUNI;
    }

    public void setCODUNI(String CODUNI) {
        this.CODUNI = CODUNI;
    }

    public String getTIPRED() {
        return TIPRED;
    }

    public void setTIPRED(String TIPRED) {
        this.TIPRED = TIPRED;
    }

    public String getEDONOD() {
        return EDONOD;
    }

    public void setEDONOD(String EDONOD) {
        this.EDONOD = EDONOD;
    }
    
    
     
    
}