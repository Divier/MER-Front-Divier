/*
 * Clase creada para manipular los listado de unidades que se encuentran 
 * en conflicto y las que se resolvieron conflictos
 */
package co.com.claro.ejb.mgl.address.dto;

import co.com.claro.mgl.jpa.entities.UnidadStructPcml;
import java.util.List;

/**
 *
 * @author Juan David Hernandez
 */
public class UnidadesConflictoDto {
    
    private List <UnidadStructPcml> unidadesConflicto;
    private List <UnidadStructPcml> unidadesConflictoResuelto;
    
       
    public List<UnidadStructPcml> getUnidadesConflicto() {
        return unidadesConflicto;
    }

    public void setUnidadesConflicto(List<UnidadStructPcml> unidadesConflicto) {
        this.unidadesConflicto = unidadesConflicto;
    }

    public List<UnidadStructPcml> getUnidadesConflictoResuelto() {
        return unidadesConflictoResuelto;
    }

    public void setUnidadesConflictoResuelto(List<UnidadStructPcml> unidadesConflictoResuelto) {
        this.unidadesConflictoResuelto = unidadesConflictoResuelto;
    }
    
    
    
}
