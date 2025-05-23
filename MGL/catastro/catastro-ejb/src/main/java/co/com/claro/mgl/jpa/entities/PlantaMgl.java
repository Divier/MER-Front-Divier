/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.claro.mgl.jpa.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author JPeña
 */

@Entity
@Table(name = "TEC_CONFIGURACION_PLANTA", schema = "MGL_SCHEME")
@XmlRootElement
@NamedQueries({})
public class PlantaMgl implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
     @NotNull
    @SequenceGenerator(
            name = "PlantaMgl.PlantaMglSeq",
            sequenceName = "MGL_SCHEME.TEC_CONFIGURACION_PLANTA_SEQ", allocationSize = 1)
    @GeneratedValue(generator = "PlantaMgl.PlantaMglSeq")  
    @Column(name = "CONFIGURACIONPLANTAID")
    private BigDecimal configuracionplantaid;
    @Size(max = 2)
    @Column(name = "LOCATIONTYPE")
    private String locationtype;
    @Size(max = 6)
    @Column(name = "LOCATIONCODE")
    private String locationcode;
    @Size(max = 40)
    @Column(name = "DESCRIPTION")
    private String description;
    @Size(max = 1)
    @Column(name = "NOTESINDICATOR")
    private String notesindicator;
    @Column(name = "HOUR24")
    private BigDecimal hour24;
    @Column(name = "HOUR48")
    private BigDecimal hour48;
    @Column(name = "WEEK")
    private BigDecimal week;
    @Column(name = "MONTH")
    private BigDecimal month;
    @Column(name = "YEAR")
    private BigDecimal year;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ESTADO_REGISTRO")
    private int estadoRegistro;
    @Size(max = 50)
    @Column(name = "USUARIO_CREACION")
    private String usuarioCreacion;
    @Size(max = 50)
    @Column(name = "USUARIO_MODIFICACION")
    private String usuarioModificacion;
    @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "FECHA_MODIFICACION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;
    @Column(name = "PERFIL_CREACION")
    private BigDecimal perfilCreacion;
    @Column(name = "PERFIL_EDICION")
    private BigDecimal perfilEdicion;
    
    
    @JoinColumn(name = "CONFIGURACIONPLANTAPARENTID", referencedColumnName = "CONFIGURACIONPLANTAID")
    @ManyToOne
    private PlantaMgl configuracionplantaparentid;
    @OneToMany(mappedBy = "configuracionplantaid")
   private List<PlantaMglNotas> tecConfPlantaNotasList =new ArrayList<PlantaMglNotas>();

    

    public BigDecimal getConfiguracionplantaid() {
        return configuracionplantaid;
    }

    public void setConfiguracionplantaid(BigDecimal configuracionplantaid) {
        this.configuracionplantaid = configuracionplantaid;
    }

    public String getLocationtype() {
        return locationtype;
    }

    public void setLocationtype(String locationtype) {
        this.locationtype = locationtype;
    }

    public String getLocationcode() {
        return locationcode;
    }

    public void setLocationcode(String locationcode) {
        this.locationcode = locationcode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNotesindicator() {
        return notesindicator;
    }

    public void setNotesindicator(String notesindicator) {
        this.notesindicator = notesindicator;
    }

    public BigDecimal getHour24() {
        return hour24;
    }

    public void setHour24(BigDecimal hour24) {
        this.hour24 = hour24;
    }

    public BigDecimal getHour48() {
        return hour48;
    }

    public void setHour48(BigDecimal hour48) {
        this.hour48 = hour48;
    }

    public BigDecimal getWeek() {
        return week;
    }

    public void setWeek(BigDecimal week) {
        this.week = week;
    }

    public BigDecimal getMonth() {
        return month;
    }

    public void setMonth(BigDecimal month) {
        this.month = month;
    }

    public BigDecimal getYear() {
        return year;
    }

    public void setYear(BigDecimal year) {
        this.year = year;
    }

    public int getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(int estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public String getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(String usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public BigDecimal getPerfilCreacion() {
        return perfilCreacion;
    }

    public void setPerfilCreacion(BigDecimal perfilCreacion) {
        this.perfilCreacion = perfilCreacion;
    }

    public BigDecimal getPerfilEdicion() {
        return perfilEdicion;
    }

    public void setPerfilEdicion(BigDecimal perfilEdicion) {
        this.perfilEdicion = perfilEdicion;
    }

   

    public PlantaMgl getConfiguracionplantaparentid() {
        return configuracionplantaparentid;
    }

    public void setConfiguracionplantaparentid(PlantaMgl configuracionplantaparentid) {
        this.configuracionplantaparentid = configuracionplantaparentid;
    }

    public List<PlantaMglNotas> getTecConfPlantaNotasList() {
        return tecConfPlantaNotasList;
    }

    public void setTecConfPlantaNotasList(List<PlantaMglNotas> tecConfPlantaNotasList) {
        this.tecConfPlantaNotasList = tecConfPlantaNotasList;
    }
    
    public void limpiarCampos (){
    }
    
    @Override
    public PlantaMgl clone() throws CloneNotSupportedException {
        PlantaMgl nuevoClon = new PlantaMgl();
        nuevoClon.setConfiguracionplantaid(this.getConfiguracionplantaid());
        nuevoClon.setLocationtype(this.getLocationtype());
        nuevoClon.setLocationcode(this.getLocationcode());
        nuevoClon.setDescription(this.getDescription());
        nuevoClon.setNotesindicator(this.getNotesindicator());
        nuevoClon.setHour24(this.getHour24());
        nuevoClon.setHour48(this.getHour48());
        nuevoClon.setWeek(this.getWeek());
        nuevoClon.setMonth(this.getMonth());
        nuevoClon.setYear(this.getYear());
        nuevoClon.setEstadoRegistro(this.getEstadoRegistro());
        nuevoClon.setUsuarioCreacion(this.getUsuarioCreacion());
        nuevoClon.setUsuarioModificacion(this.getUsuarioModificacion());
        nuevoClon.setPerfilCreacion(this.getPerfilCreacion());
        nuevoClon.setPerfilEdicion(this.getPerfilEdicion());
        nuevoClon.setFechaCreacion(this.getFechaCreacion());
        nuevoClon.setFechaModificacion(this.getFechaModificacion());
        nuevoClon.setConfiguracionplantaparentid(this.getConfiguracionplantaparentid());
        return nuevoClon; //To change body of generated methods, choose Tools | Templates.
    }

    

    @Override
    public String toString() {
        return "co.com.claro.mgl.jpa.entities.TecConfiguracionPlanta[ configuracionplantaid=" + configuracionplantaid + " ]";
    }
    
}
