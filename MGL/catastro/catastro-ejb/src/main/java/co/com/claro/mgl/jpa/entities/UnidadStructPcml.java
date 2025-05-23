/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlRootElement;
import net.telmex.pcml.service.UnidadStruct;

/**
 *
 * @author User
 */
@XmlRootElement(name = "UnidadStructPcml")
public class UnidadStructPcml extends UnidadStruct implements Cloneable{

    private String newAparment;
    private boolean selected = false;
    private String tipoNivel5;
    private String tipoNivel6;
    private String valorNivel5;
    private String valorNivel6;
    private boolean renderN6 = false;
    private String errorValidator;
    private String conflictApto;
    private BigDecimal tecnologiaHabilitadaId;
    private BigDecimal tecnologiaId;
    private String nombreTecnologia;
    private HhppMgl hhppMgl;
    private BigDecimal tecHabilitadaIdNuevaDireccion;
    private String mallaDireccion;
    private boolean unidadRepetida;
    private HhppMgl hhppCambioVal;
    
    public String getNewAparment() {
        return newAparment;
    }

    public void setNewAparment(String newAparment) {
        this.newAparment = newAparment;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * Get the value of tipoNivel5
     *
     * @return the value of tipoNivel5
     */
    public String getTipoNivel5() {
        return tipoNivel5;
    }

    /**
     * Set the value of tipoNivel5
     *
     * @param tipoNivel5 new value of tipoNivel5
     */
    public void setTipoNivel5(String tipoNivel5) {
        this.tipoNivel5 = tipoNivel5;
    }

    /**
     * Get the value of tipoNivel6
     *
     * @return the value of tipoNivel6
     */
    public String getTipoNivel6() {
        return tipoNivel6;
    }

    /**
     * Set the value of tipoNivel6
     *
     * @param tipoNivel6 new value of tipoNivel6
     */
    public void setTipoNivel6(String tipoNivel6) {
        this.tipoNivel6 = tipoNivel6;
    }

    /**
     * Get the value of valorNivel5
     *
     * @return the value of valorNivel5
     */
    public String getValorNivel5() {
        return valorNivel5;
    }

    /**
     * Set the value of valorNivel5
     *
     * @param valorNivel5 new value of valorNivel5
     */
    public void setValorNivel5(String valorNivel5) {
        this.valorNivel5 = valorNivel5;
    }

    /**
     * Get the value of valorNivel6
     *
     * @return the value of valorNivel6
     */
    public String getValorNivel6() {
        return valorNivel6;
    }

    /**
     * Set the value of valorNivel6
     *
     * @param valorNivel6 new value of valorNivel6
     */
    public void setValorNivel6(String valorNivel6) {
        this.valorNivel6 = valorNivel6;
    }

    /**
     * Get the value of renderN6
     *
     * @return the value of renderN6
     */
    public boolean isRenderN6() {
        return renderN6;
    }

    /**
     * Set the value of renderN6
     *
     * @param renderN6 new value of renderN6
     */
    public void setRenderN6(boolean renderN6) {
        this.renderN6 = renderN6;
    }

    public String getErrorValidator() {
        return errorValidator;
    }

    public void setErrorValidator(String errorValidator) {
        this.errorValidator = errorValidator;
    }

    public String getConflictApto() {
        return conflictApto;
    }

    public void setConflictApto(String conflictApto) {
        this.conflictApto = conflictApto;
    }

    public BigDecimal getTecnologiaHabilitadaId() {
        return tecnologiaHabilitadaId;
    }

    public void setTecnologiaHabilitadaId(BigDecimal tecnologiaHabilitadaId) {
        this.tecnologiaHabilitadaId = tecnologiaHabilitadaId;
    }  

    public BigDecimal getTecnologiaId() {
        return tecnologiaId;
    }

    public void setTecnologiaId(BigDecimal tecnologiaId) {
        this.tecnologiaId = tecnologiaId;
    }

    public String getNombreTecnologia() {
        return nombreTecnologia;
    }

    public void setNombreTecnologia(String nombreTecnologia) {
        this.nombreTecnologia = nombreTecnologia;
    }

    public HhppMgl getHhppMgl() {
        return hhppMgl;
    }

    public void setHhppMgl(HhppMgl hhppMgl) {
        this.hhppMgl = hhppMgl;
    }

    public BigDecimal getTecHabilitadaIdNuevaDireccion() {
        return tecHabilitadaIdNuevaDireccion;
    }

    public void setTecHabilitadaIdNuevaDireccion(BigDecimal tecHabilitadaIdNuevaDireccion) {
        this.tecHabilitadaIdNuevaDireccion = tecHabilitadaIdNuevaDireccion;
    }    
    
    
    @Override
    public UnidadStructPcml clone() throws CloneNotSupportedException {
        return (UnidadStructPcml) super.clone();
    }

    public String getMallaDireccion() {
        return mallaDireccion;
    }

    public void setMallaDireccion(String mallaDireccion) {
        this.mallaDireccion = mallaDireccion;
    }   

    public boolean isUnidadRepetida() {
        return unidadRepetida;
    }

    public void setUnidadRepetida(boolean unidadRepetida) {
        this.unidadRepetida = unidadRepetida;
    }

    public HhppMgl getHhppCambioVal() {
        return hhppCambioVal;
    }

    public void setHhppCambioVal(HhppMgl hhppCambioVal) {
        this.hhppCambioVal = hhppCambioVal;
    }
    
    
    
    
}
