/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.dtos;

import co.com.claro.mgl.jpa.entities.PlantaMgl;
import java.math.BigDecimal;

/**
 *
 * @author bocanegravm
 */
public class CmtFiltroConsultaPlantaDto {
    private BigDecimal configuracionPlantaId;
    private String locationType;
    private String locationCode;
    private String description;
    private PlantaMgl padrePlanta;
    public BigDecimal getConfiguracionPlantaId() {
        return configuracionPlantaId;
    }

    public void setConfiguracionPlantaId(BigDecimal configuracionPlantaId) {
        this.configuracionPlantaId = configuracionPlantaId;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }    

    public PlantaMgl getPadrePlanta() {
        return padrePlanta;
    }

    public void setPadrePlanta(PlantaMgl padrePlanta) {
        this.padrePlanta = padrePlanta;
    }
    
}
