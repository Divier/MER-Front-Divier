/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dtos;

import co.com.claro.mgl.jpa.entities.HhppMgl;
import java.math.BigDecimal;

/**
 *
 * @author Juan David Hernandez
 */
public class EstadoTecnologiaHhppDto {
    
    private BigDecimal idTecnologia;
    private String nombreTecnologia;
    private String estadoTecnologia;
    private String nombreEstadoTecnologia;
    private String nombreEstadoTecnologCM;
    private HhppMgl hhppMglSelected;
    private boolean enableLink;
    private String stateNodeTecnologCM;
    private boolean enableLinkNode;

    public String getNombreTecnologia() {
        return nombreTecnologia;
    }

    public void setNombreTecnologia(String nombreTecnologia) {
        this.nombreTecnologia = nombreTecnologia;
    }

    public String getEstadoTecnologia() {
        return estadoTecnologia;
    }

    public void setEstadoTecnologia(String estadoTecnologia) {
        this.estadoTecnologia = estadoTecnologia;
    }

    public BigDecimal getIdTecnologia() {
        return idTecnologia;
    }

    public void setIdTecnologia(BigDecimal idTecnologia) {
        this.idTecnologia = idTecnologia;
    }

    public String getNombreEstadoTecnologia() {
        return nombreEstadoTecnologia;
    }

    public void setNombreEstadoTecnologia(String nombreEstadoTecnologia) {
        this.nombreEstadoTecnologia = nombreEstadoTecnologia;
    }

    public String getNombreEstadoTecnologCM() {
        return nombreEstadoTecnologCM;
    }

    public void setNombreEstadoTecnologCM(String nombreEstadoTecnologCM) {
        this.nombreEstadoTecnologCM = nombreEstadoTecnologCM;
    }

    public HhppMgl getHhppMglSelected() {
        return hhppMglSelected;
    }

    public void setHhppMglSelected(HhppMgl hhppMglSelected) {
        this.hhppMglSelected = hhppMglSelected;
    }

    public boolean isEnableLink() {
        return enableLink;
    }

    public void setEnableLink(boolean enableLink) {
        this.enableLink = enableLink;
    }

    public String getStateNodeTecnologCM() {
        return stateNodeTecnologCM;
    }

    public void setStateNodeTecnologCM(String stateNodeTecnologCM) {
        this.stateNodeTecnologCM = stateNodeTecnologCM;
    }

    public boolean isEnableLinkNode() {
        return enableLinkNode;
    }

    public void setEnableLinkNode(boolean enableLinkNode) {
        this.enableLinkNode = enableLinkNode;
    }
    
    
    
    
    
    
}
