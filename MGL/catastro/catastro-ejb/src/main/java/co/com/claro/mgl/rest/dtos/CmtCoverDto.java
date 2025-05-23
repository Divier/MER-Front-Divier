/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.rest.dtos;

import co.com.claro.mgl.jpa.entities.HhppMgl;
import co.com.claro.mgl.jpa.entities.cm.CmtTecnologiaSubMgl;
import org.primefaces.model.map.Polygon;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author valbuenayf
 */
@XmlRootElement(name = "cover")
public class CmtCoverDto {

    @XmlElement
    private String technology;
    @XmlElement
    private String node;
 
    @XmlElement
    private String state;
    @XmlElement
    private String qualificationDate;
    
    @XmlTransient
    private boolean hhppExistente;
    
    @XmlTransient
    private boolean isChecked;
    
    @XmlTransient
    private Polygon poligono;
    
    @XmlTransient
    private HhppMgl hhppMgl;
     
    @XmlTransient
    private String colorTecno;
    
    @XmlTransient
    private boolean isCobertura;
    
    @XmlTransient
    private CmtTecnologiaSubMgl tecnologiaSubMgl;
    
    @XmlTransient
    private boolean validaCobertura;
    
    @XmlTransient
    private boolean zona3G4G5G;

    /* Brief 98062 */
    @XmlTransient
    private String nodoSitiData;
    /* Cierre Brief 98062 */

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getQualificationDate() {
        return qualificationDate;
    }

    public void setQualificationDate(String qualificationDate) {
        this.qualificationDate = qualificationDate;
    }

    public boolean isHhppExistente() {
        return hhppExistente;
    }

    public void setHhppExistente(boolean hhppExistente) {
        this.hhppExistente = hhppExistente;
    }
    
     public boolean isIsChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public Polygon getPoligono() {
        return poligono;
    }

    public void setPoligono(Polygon poligono) {
        this.poligono = poligono;
    }

    public HhppMgl getHhppMgl() {
        return hhppMgl;
    }

    public void setHhppMgl(HhppMgl hhppMgl) {
        this.hhppMgl = hhppMgl;
    }

    public boolean isIsCobertura() {
        return isCobertura;
    }

    public void setIsCobertura(boolean isCobertura) {
        this.isCobertura = isCobertura;
    }
    
     public String getColorTecno() {
        return colorTecno;
    }

    public void setColorTecno(String colorTecno) {
        this.colorTecno = colorTecno;
    }

    public CmtTecnologiaSubMgl getTecnologiaSubMgl() {
        return tecnologiaSubMgl;
    }

    public void setTecnologiaSubMgl(CmtTecnologiaSubMgl tecnologiaSubMgl) {
        this.tecnologiaSubMgl = tecnologiaSubMgl;
    }

    public boolean isValidaCobertura() {
        return validaCobertura;
    }

    public void setValidaCobertura(boolean validaCobertura) {
        this.validaCobertura = validaCobertura;
    }

    public boolean isZona3G4G5G() {
        return zona3G4G5G;
    }

    public void setZona3G4G5G(boolean zona3G4G5G) {
        this.zona3G4G5G = zona3G4G5G;
    }

    /* Brief 98062 */
    public String getNodoSitiData() {
        return nodoSitiData;
    }

    public void setNodoSitiData(String nodoSitiData) {
        this.nodoSitiData = nodoSitiData;
    }
    /* Cierre Brief 98062 */
    
    
    /**
     * Obtiene el nombre del Estado.
     * @return Nombre asociado al identificador del Estado.
     */
    @XmlTransient
    public String getStateName() {
        String name = null;
        
        if (state != null && !state.isEmpty()) {
            if (state.toUpperCase().trim().equals("A")) {
                name = "ACTIVO";
            } else if (state.toUpperCase().trim().equals("I")) {
                name = "INACTIVO";
            }
        }
        
        return (name);
    }
}
