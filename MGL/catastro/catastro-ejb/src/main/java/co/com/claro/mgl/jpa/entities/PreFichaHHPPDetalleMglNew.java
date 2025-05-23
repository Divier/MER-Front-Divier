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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

/**
 * Entity TEC_PREF_TEC_HAB_NEW
 * 
 * @author Miguel Barrios Hitss
 * @version 1.1 Rev Miguel Barrios Hitss
 */

@Entity
@Table(name = "TEC_PREF_TEC_HAB_NEW", schema = "MGL_SCHEME")
public class PreFichaHHPPDetalleMglNew implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(
            name = "PreFichaHHPPDetalleMglNew.preFichaHHPPDetalleMglNewSeq",
            sequenceName = "MGL_SCHEME.TEC_PREF_TEC_HAB_NEW_SQ", allocationSize = 1)
    @GeneratedValue(generator = "PreFichaHHPPDetalleMglNew.preFichaHHPPDetalleMglNewSeq")
    @Column(name = "PREFICHA_TEC_HAB_DETA_ID", nullable = false)
    private BigDecimal id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PREFICHA_XLS_ID", referencedColumnName = "PREFICHA_XLS_ID", nullable = false)
    private PreFichaXlsMglNew preFichaXlsMgl;
    @Column(name = "STREET_NAME", nullable = false, length = 100)
    private String streetName;
    @Column(name = "HOUSE_NUMBER", nullable = false, length = 10)
    private String houseNumber;
    @Column(name = "APTO_NUMBER", nullable = false, length = 100)
    private String apartmentNumber;
    @Column(name = "COMMUNITY", nullable = false, length = 3)
    private String cummunity;
    @Column(name = "DIVISION", nullable = false, length = 3)
    private String division;
    @Column(name = "STRATUS", nullable = false, length = 2)
    private String unitStatus;
    @Transient
    private String stratus ;
    @Column(name = "DROP_TYPE", nullable = false, length = 2)
    private String dropType;
    @Column(name = "DROP_TYPE_CABLE", nullable = false, length = 2)
    private String dropTypeCable;
    @Column(name = "GRID_POSITION", nullable = false, length = 4)
    private String gridPosition;
    @Column(name = "PLANT_LOC_TYPE", nullable = false, length = 2)
    private String plantLocType;
    @Column(name = "PLANT_LOCATION", nullable = false, length = 10)
    private String plantLocation;
    @Column(name = "HEAD_END", nullable = false, length = 4)
    private String headEnd;
    @Column(name = "POSTAL_CODE", nullable = false, length = 10)
    private String postalCode;
    @Column(name = "DEALER", nullable = false, length = 4)
    private String dealer;
    @Column(name = "UNIT_TYPE", nullable = false, length = 2)
    private String unitType;
    @Column(name = "BUILDING_NAME", nullable = false, length = 25)
    private String buildingName;
    @Column(name = "PROB_UNIT_CODES", nullable = false, length = 100)
    private String problemUnitCodes;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PREFICHA_GEOREF_ID", nullable = false)
    private PreFichaGeorreferenciaMglNew fichaGeorreferenciaMgl;
    @Column(name = "TIPO_DIRECCION", nullable = false, length = 100)
    private String tipoDireccion;
    @Column(name = "FECHA_CREACION", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "USUARIO_CREACION", nullable = false)
    private String usuarioCreacion;
    @Column(name = "PERFIL_CREACION", nullable = false)
    private int perfilCreacion;
    @Column(name = "FECHA_EDICION", nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaEdicion;
    @Column(name = "USUARIO_EDICION", nullable = false)
    private String usuarioEdicion;
    @Column(name = "PERFIL_EDICION", nullable = false)
    private int perfilEdicion;
    @Column(name = "ESTADO_REGISTRO", nullable = false)
    private int estadoRegistro;
    @Column(name = "DIRECCION_MGL")
    private String direccionMGL;
    @Column(name = "TIPO_VIVIENDA", nullable = false)
    private String tipoVivienda;
 
    @Transient
    private String observaciones;
    @Transient
    private boolean modified = false;
    @Transient
    private boolean modifiedEstrato = false;
    @Transient
    private boolean modifiedInfoCatastral = false;
    @Transient
    private String direccionRR;
    
    @Column(name = "CREATE_IN_RR", nullable = false, length = 1)
    private String createInRR = "0";
    @Column(name = "CREATE_IN_REPO", nullable = false, length = 1)
    private String createInRepo = "0";

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public String getCummunity() {
        return cummunity;
    }

    public void setCummunity(String cummunity) {
        this.cummunity = cummunity;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getUnitStatus() {
        return unitStatus;
    }

    public void setUnitStatus(String unitStatus) {
        this.unitStatus = unitStatus;
    }

    public String getStratus() {
        return stratus;
    }

    public void setStratus(String stratus) {
        this.stratus = stratus;
    }

    public String getDropType() {
        return dropType;
    }

    public void setDropType(String dropType) {
        this.dropType = dropType;
    }

    public String getDropTypeCable() {
        return dropTypeCable;
    }

    public void setDropTypeCable(String dropTypeCable) {
        this.dropTypeCable = dropTypeCable;
    }

    public String getGridPosition() {
        return gridPosition;
    }

    public void setGridPosition(String gridPosition) {
        this.gridPosition = gridPosition;
    }

    public String getPlantLocType() {
        return plantLocType;
    }

    public void setPlantLocType(String plantLocType) {
        this.plantLocType = plantLocType;
    }

    public String getPlantLocation() {
        return plantLocation;
    }

    public void setPlantLocation(String plantLocation) {
        this.plantLocation = plantLocation;
    }

    public String getHeadEnd() {
        return headEnd;
    }

    public void setHeadEnd(String headEnd) {
        this.headEnd = headEnd;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getDealer() {
        return dealer;
    }

    public void setDealer(String dealer) {
        this.dealer = dealer;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getProblemUnitCodes() {
        return problemUnitCodes;
    }

    public void setProblemUnitCodes(String problemUnitCodes) {
        this.problemUnitCodes = problemUnitCodes;
    }

    public PreFichaXlsMglNew getPreFichaXlsMgl() {
        return preFichaXlsMgl;
    }

    public void setPreFichaXlsMgl(PreFichaXlsMglNew preFichaXlsMgl) {
        this.preFichaXlsMgl = preFichaXlsMgl;
    }

    public PreFichaGeorreferenciaMglNew getFichaGeorreferenciaMgl() {
        return fichaGeorreferenciaMgl;
    }

    public void setFichaGeorreferenciaMgl(PreFichaGeorreferenciaMglNew fichaGeorreferenciaMgl) {
        this.fichaGeorreferenciaMgl = fichaGeorreferenciaMgl;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getTipoDireccion() {
        return tipoDireccion;
    }

    public void setTipoDireccion(String tipoDireccion) {
        this.tipoDireccion = tipoDireccion;
    }

    public boolean isModified() {
        return modified;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
    }

    public boolean isModifiedEstrato() {
        return modifiedEstrato;
    }

    public void setModifiedEstrato(boolean modifiedEstrato) {
        this.modifiedEstrato = modifiedEstrato;
    }

    public boolean isModifiedInfoCatastral() {
        return modifiedInfoCatastral;
    }

    public void setModifiedInfoCatastral(boolean modifiedInfoCatastral) {
        this.modifiedInfoCatastral = modifiedInfoCatastral;
    }

    public String getCreateInRR() {
        return createInRR;
    }

    public void setCreateInRR(String createInRR) {
        this.createInRR = createInRR;
    }

    public String getCreateInRepo() {
        return createInRepo;
    }

    public void setCreateInRepo(String createInRepo) {
        this.createInRepo = createInRepo;
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

    public int getPerfilCreacion() {
        return perfilCreacion;
    }

    public void setPerfilCreacion(int perfilCreacion) {
        this.perfilCreacion = perfilCreacion;
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

    public int getPerfilEdicion() {
        return perfilEdicion;
    }

    public void setPerfilEdicion(int perfilEdicion) {
        this.perfilEdicion = perfilEdicion;
    }

    public int getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(int estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    public String getDireccionMGL() {
        return direccionMGL;
    }

    public void setDireccionMGL(String direccionMGL) {
        this.direccionMGL = direccionMGL;
    }
    
    public String getTipoVivienda() {
        return tipoVivienda;
    }

    public void setTipoVivienda(String tipoVivienda) {
        this.tipoVivienda = tipoVivienda;
    }
    
    public String getDireccionRR() {
        return direccionRR;
    }

    public void setDireccionRR(String direccionRR) {
        this.direccionRR = direccionRR;
    }

}