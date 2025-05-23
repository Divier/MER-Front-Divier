/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

/**
 * Entity TEC_PREFICHA_GEOREFERENCIA_NEW
 * 
 * @author Miguel Barrios Hitss
 * @version 1.1 Rev Miguel Barrios Hitss
 */

@Entity
@Table(name = "TEC_PREFICHA_GEOREFERENCIA_NEW", schema = "MGL_SCHEME")
public class PreFichaGeorreferenciaMglNew implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "PreFichaGeorreferenciaMglNew.preFichaGeorreferenciaMglNewSeq",
            sequenceName = "MGL_SCHEME.TEC_PREFICHA_GEOREF_NEW_SQ", allocationSize = 1)
    @GeneratedValue(generator = "PreFichaGeorreferenciaMglNew.preFichaGeorreferenciaMglNewSeq")
    @Column(name = "PREFICHA_GEOREF_ID", nullable = false)
    private BigDecimal id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PREFICHA_XLS_ID", nullable = true)
    private PreFichaXlsMglNew preFichaXlsMgl;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PREFICHA_TEC_HAB_DETA_ID", nullable = true)
    private PreFichaHHPPDetalleMglNew preFichaHHPPDetalleMgl;
    @Column(name = "ACTIVITY_ECONOMIC", nullable = true, length = 200)
    private String activityEconomic;
    @Column(name = "ADDRESS", nullable = true, length = 200)
    private String address;
    @Column(name = "ADDRESS_CODE", nullable = true, length = 200)
    private String addressCode;
    @Column(name = "ADDRESS_CODE_FOUND", nullable = true, length = 200)
    private String addressCodeFound;
    @Column(name = "ALTERNATE_ADDRESS", nullable = true, length = 200)
    private String alternateAddress;
    @Column(name = "APPLET_STANDAR", nullable = true, length = 200)
    private String appletStandar;
    @Column(name = "CATEGORY", nullable = true, length = 200)
    private String category;
    @Column(name = "CHAGE_NUMBER", nullable = true, length = 200)
    private String changeNumber;
    @Column(name = "COD_DANE_MCPIO", nullable = true, length = 200)
    private String codDaneMcpio;
    @Column(name = "CX", nullable = true, length = 200)
    private String cx;
    @Column(name = "CY", nullable = true, length = 200)
    private String cy;
    @Column(name = "DANE_CITY", nullable = true, length = 200)
    private String daneCity;
    @Column(name = "DANE_POP_AREA", nullable = true, length = 200)
    private String danePopArea;
    @Column(name = "EXIST", nullable = true, length = 200)
    private String exist;
    @Column(name = "ID_ADDRESS", nullable = true, length = 200)
    private String idAddress;
    @Column(name = "LEVEL_ECONOMIC", nullable = true, length = 200)
    private String levelEconomic;
    @Column(name = "LEVEL_LIVE", nullable = true, length = 200)
    private String levelLive;
    @Column(name = "LOCALITY", nullable = true, length = 200)
    private String locality;
    @Column(name = "NEIGHBORHOOD", nullable = true, length = 200)
    private String neighborhood;
    @Column(name = "NODO_UNO", nullable = true, length = 200)
    private String nodoUno;
    @Column(name = "NODO_DOS", nullable = true, length = 200)
    private String nodoDos;
    @Column(name = "NODO_TRES", nullable = true, length = 200)
    private String nodoTres;
    @Column(name = "QUALIFIERS", nullable = true, length = 200)
    private String qualifiers;
    @Column(name = "SOURCE", nullable = true, length = 200)
    private String source;
    @Column(name = "STATE_DEF", nullable = true, length = 200)
    private String state;
    @Column(name = "TRASLATE", nullable = true, length = 200)
    private String translate;
    @Column(name = "ZIP_CODE", nullable = true, length = 200)
    private String zipCode;
    @Column(name = "ZIP_CODE_DISTRICT", nullable = true, length = 200)
    private String zipCodeDistrict;
    @Column(name = "ZIP_CODE_STATE", nullable = true, length = 200)
    private String zipCodeState;
    @Column(name = "FECHA_CREACION", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "USUARIO_CREACION", nullable = false)
    private String usuarioCreacion;
    @Column(name = "FECHA_EDICION", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaEdicion;
    @Column(name = "USUARIO_EDICION", nullable = false)
    private String usuarioEdicion;
    @Column(name = "ESTADO_REGISTRO", nullable = false)
    private int estadoRegistro;
    @Column(name = "CAMBIO_ESTRATO", nullable = true, length = 200)
    private String cambioEstrato;

    @Transient
    private String resultadoGeorref;
    @Transient
    private String barrioGeorref;
    @Transient
    private String direccionFormatoRR;
    @Transient
    private DrDireccion drDireccionPreficha;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public PreFichaXlsMglNew getPreFichaXlsMgl() {
        return preFichaXlsMgl;
    }

    public void setPreFichaXlsMglNew(PreFichaXlsMglNew preFichaXlsMgl) {
        this.preFichaXlsMgl = preFichaXlsMgl;
    }

    public String getActivityEconomic() {
        return activityEconomic;
    }

    public void setActivityEconomic(String activityEconomic) {
        this.activityEconomic = activityEconomic;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressCode() {
        return addressCode;
    }

    public void setAddressCode(String addressCode) {
        this.addressCode = addressCode;
    }

    public String getAddressCodeFound() {
        return addressCodeFound;
    }

    public void setAddressCodeFound(String addressCodeFound) {
        this.addressCodeFound = addressCodeFound;
    }

    public String getAlternateAddress() {
        return alternateAddress;
    }

    public void setAlternateAddress(String alternateAddress) {
        this.alternateAddress = alternateAddress;
    }

    public String getAppletStandar() {
        return appletStandar;
    }

    public void setAppletStandar(String appletStandar) {
        this.appletStandar = appletStandar;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getChangeNumber() {
        return changeNumber;
    }

    public void setChangeNumber(String changeNumber) {
        this.changeNumber = changeNumber;
    }

    public String getCodDaneMcpio() {
        return codDaneMcpio;
    }

    public void setCodDaneMcpio(String codDaneMcpio) {
        this.codDaneMcpio = codDaneMcpio;
    }

    public String getCx() {
        return cx;
    }

    public void setCx(String cx) {
        this.cx = cx;
    }

    public String getCy() {
        return cy;
    }

    public void setCy(String cy) {
        this.cy = cy;
    }

    public String getDaneCity() {
        return daneCity;
    }

    public void setDaneCity(String daneCity) {
        this.daneCity = daneCity;
    }

    public String getDanePopArea() {
        return danePopArea;
    }

    public void setDanePopArea(String danePopArea) {
        this.danePopArea = danePopArea;
    }

    public String getExist() {
        return exist;
    }

    public void setExist(String exist) {
        this.exist = exist;
    }

    public String getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(String idAddress) {
        this.idAddress = idAddress;
    }

    public String getLevelEconomic() {
        return levelEconomic;
    }

    public void setLevelEconomic(String levelEconomic) {
        this.levelEconomic = levelEconomic;
    }

    public String getLevelLive() {
        return levelLive;
    }

    public void setLevelLive(String levelLive) {
        this.levelLive = levelLive;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getNodoUno() {
        return nodoUno;
    }

    public void setNodoUno(String nodoUno) {
        this.nodoUno = nodoUno;
    }

    public String getNodoDos() {
        return nodoDos;
    }

    public void setNodoDos(String nodoDos) {
        this.nodoDos = nodoDos;
    }

    public String getNodoTres() {
        return nodoTres;
    }

    public void setNodoTres(String nodoTres) {
        this.nodoTres = nodoTres;
    }

    public String getQualifiers() {
        return qualifiers;
    }

    public void setQualifiers(String qualifiers) {
        this.qualifiers = qualifiers;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getZipCodeDistrict() {
        return zipCodeDistrict;
    }

    public void setZipCodeDistrict(String zipCodeDistrict) {
        this.zipCodeDistrict = zipCodeDistrict;
    }

    public String getZipCodeState() {
        return zipCodeState;
    }

    public void setZipCodeState(String zipCodeState) {
        this.zipCodeState = zipCodeState;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public Date getFechaEdicion() {
        return fechaEdicion;
    }

    public void setFechaEdicion(Date fechaEdicion) {
        this.fechaEdicion = fechaEdicion;
    }

    public String getUsuarioEdicion() {
        return usuarioEdicion;
    }

    public void setUsuarioEdicion(String usuarioEdicion) {
        this.usuarioEdicion = usuarioEdicion;
    }

    public int getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(int estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    public PreFichaHHPPDetalleMglNew getPreFichaHHPPDetalleMgl() {
        return preFichaHHPPDetalleMgl;
    }

    public void setPreFichaHHPPDetalleMgl(PreFichaHHPPDetalleMglNew preFichaHHPPDetalleMgl) {
        this.preFichaHHPPDetalleMgl = preFichaHHPPDetalleMgl;
    }

    public String getResultadoGeorref() {

        String resultado = "";
        if (levelEconomic == null
                || levelEconomic.trim().isEmpty()
                || levelEconomic.equalsIgnoreCase("NG")
                || cx == null || cx.trim().isEmpty()
                || address == null
                || cy == null || cy.trim().isEmpty()) {
            resultado += "Datos Incompletos ";
        } else {
            resultado = "Información completa";
        }
        resultadoGeorref = resultado;
        return resultadoGeorref;
    }

    /**
     * @return the barrioGeorref
     */
    public String getBarrioGeorref() {
        return barrioGeorref;
    }

    /**
     * @param barrioGeorref the barrioGeorref to set
     */
    public void setBarrioGeorref(String barrioGeorref) {
        this.barrioGeorref = barrioGeorref;
    }

    public String getDireccionFormatoRR() {
        return direccionFormatoRR;
    }

    public void setDireccionFormatoRR(String direccionFormatoRR) {
        this.direccionFormatoRR = direccionFormatoRR;
    }

    public String getCambioEstrato() {
        return cambioEstrato;
    }

    public void setCambioEstrato(String cambioEstrato) {
        this.cambioEstrato = cambioEstrato;
    }

    public DrDireccion getDrDireccionPreficha() {
        return drDireccionPreficha;
    }

    public void setDrDireccionPreficha(DrDireccion drDireccionPreficha) {
        this.drDireccionPreficha = drDireccionPreficha;
    }    
}
